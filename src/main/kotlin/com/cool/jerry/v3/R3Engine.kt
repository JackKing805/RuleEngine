package com.cool.jerry.v3

import com.cool.jerry.bridge.FunctionCallBridge0
import com.cool.jerry.extern.Embed
import com.cool.jerry.model.ExecuteResult
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.model.ParseResult
import com.cool.jerry.model.Result
import com.cool.jerry.version3.RuleLexer
import com.cool.jerry.version3.RuleParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File
import java.nio.charset.Charset
import java.util.Collections

class R3Engine {
    private val environments = mutableMapOf<String, Any>()
    private val environmentMethods = mutableListOf<InjectMethod>()

    fun setEnvironment(key: String, value: Any) {
        environments[key] = value
    }

    fun setEnvironmentMethod(method: InjectMethod) {
        environmentMethods.find {
            it.method.name == method.method.name && it.parameterCount == method.parameterCount &&
                    it.method.parameters.zip(method.method.parameters)
                        .all { pair ->
                            pair.first.type == pair.second.type
                        }
        }?.let {
            throw RuntimeException("method:${method.name} already define")
        }

        environmentMethods.add(method)
    }

    private fun initR3(r3Parser: R3Parser) {
        r3Parser.reset()

        //注入常用方法
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", String::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Int::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Long::class.java)))
        r3Parser.setEnvironmentMethod(
            InjectMethod(
                "println",
                Embed::class.java.getMethod("logln", Boolean::class.java)
            )
        )
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Float::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("println", Embed::class.java.getMethod("logln", Double::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", String::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Int::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Long::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Boolean::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Float::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("print", Embed::class.java.getMethod("log", Double::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("currentTime", Embed::class.java.getMethod("currentTime")))
        r3Parser.setEnvironmentMethod(InjectMethod("uuid", Embed::class.java.getMethod("uuid")))
        r3Parser.setEnvironmentMethod(InjectMethod("sleep", Embed::class.java.getMethod("sleep", Long::class.java)))
        r3Parser.setEnvironmentMethod(
            InjectMethod(
                "randomInt",
                Embed::class.java.getMethod("randomInt", Boolean::class.java)
            )
        )
        r3Parser.setEnvironmentMethod(
            InjectMethod(
                "toString",
                Embed::class.java.getMethod("toString", Any::class.java)
            )
        )
        r3Parser.setEnvironmentMethod(InjectMethod("currentThread", Embed::class.java.getMethod("currentThread")))
        r3Parser.setEnvironmentMethod(InjectMethod("get", Embed::class.java.getMethod("get", String::class.java)))
        r3Parser.setEnvironmentMethod(InjectMethod("readLine", Embed::class.java.getMethod("readInput")))

        //注入常用变量
        r3Parser.setEnvironment("MAX_INT", Int.MAX_VALUE)
        r3Parser.setEnvironment("MIN_INT", Int.MIN_VALUE)

        r3Parser.setEnvironment(environments)
        r3Parser.setEnvironmentMethod(environmentMethods)
    }

    fun execute(file: File): ExecuteResult {
        if (!file.exists()) {
            throw RuntimeException("file:${file.absolutePath} not exists")
        }
        val readText = file.readText(Charset.defaultCharset())
        return execute(readText)
    }

    fun execute(files: List<File>):ExecuteResult{
        val text = StringBuilder("")
        for (file in files) {
            if (!file.exists()) {
                throw RuntimeException("file:${file.absolutePath} not exists")
            }
            text.append(file.readText(Charset.defaultCharset())).append("\n")
        }
        return execute(text.toString())
    }

    fun execute(vararg rule: String):ExecuteResult{
        val text = StringBuilder("")
        for (s in rule) {
            text.append(s).append("\n")
        }
        return execute(text.toString())
    }

    fun execute(rule: String): ExecuteResult {
        val visit = visit(rule.trim()) as R3Node.Program
        val r3Parser = R3Parser()
        initR3(r3Parser)

        val firstInitProgramStatements = visit.statements.filter {
            it is R3Node.Statement.ClassStatement ||
                    it is R3Node.Statement.FunctionStatement ||
                    it is R3Node.Expression.VariableExpression
        }.toMutableList()

        val crashStatements = visit.statements - firstInitProgramStatements.toSet()
        if (crashStatements.isNotEmpty()) {
            throw RuntimeException("[${crashStatements.joinToString("\n")}] can't define at this scope")
        }

//        r3Parser.parse(firstProgram, mutableListOf(), mutableListOf(), mutableListOf())

        val findMain = firstInitProgramStatements.filterIsInstance<R3Node.Statement.FunctionStatement>()
            .find {
                it.functionName.text == "main"
            }

        if (findMain != null) {
            if (findMain.parameters.parameters.isNotEmpty()) {
                throw RuntimeException("Main function can't have any param")
            }

            firstInitProgramStatements.add(
                R3Node.Expression.MethodCallExpression(
                    "main()",
                    R3Node.Expression.Define.Identifier.ID("main", "main"),
                    emptyList()
                )
            )

            val executeProgram = R3Node.Program(visit.source, firstInitProgramStatements).apply {
                levelSet(null)
            }

            val result = r3Parser.parse(executeProgram, Collections.synchronizedList(mutableListOf()), mutableListOf(), mutableListOf()).getResult()
            return ExecuteResult(executeProgram, Result(findMain, result))
        } else {
            r3Parser.parse(visit, Collections.synchronizedList(mutableListOf()), mutableListOf(), mutableListOf())
            return ExecuteResult(visit, null)
        }
    }

    private fun visit(rule: String): R3Node {
        val cs = CharStreams.fromString(rule)
        val lexer = RuleLexer(cs)
        val tokens = CommonTokenStream(lexer)
        val visitor = RuleParserVisitorImpl()
        val parser = RuleParser(tokens)
        parser.errorHandler = ErrorHandler()
        return visitor.visit(parser.program())
    }

    private fun ParseResult.getResult(): Any? {
        return when (this) {
            is ParseResult.Define.ClassDefine -> null
            is ParseResult.Define.ConstructorDefine -> null
            is ParseResult.Define.FunctionDefine -> null
            is ParseResult.Define.Variable -> null
            ParseResult.NoneResult -> null
            ParseResult.OperateResult.Break -> null
            ParseResult.OperateResult.Continue -> null
            is ParseResult.OperateResult.Return -> value?.getResult()
            is ParseResult.ValueResult.AnyValueResult -> value
            is ParseResult.ValueResult.ArrayValueResult -> value
            is ParseResult.ValueResult.BooleanValueResult -> value
            is ParseResult.ValueResult.FloatValueResult -> value
            is ParseResult.ValueResult.IntValueResult -> value
            is ParseResult.ValueResult.StringValueResult -> value
            is ParseResult.ValueResult.RangeValueResult -> value
            is ParseResult.ValueResult.MapValueResult -> value.map {
                it.key.getResult() to it.value.getResult()
            }.toMap()
        }
    }
}

