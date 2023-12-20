import com.cool.jerry.rt_engine.RtRuleEngine

fun main(args: Array<String>) {
    val program = """
       val c = @a.a.c
       log(c)
    """.trimIndent()

    val visitor =  RtRuleEngine()
    visitor.setEnvironment("a",A())
    visitor.setEnvironment("c",C())
    visitor.execute(program)
}

class C{
    val c = "ADWEE"

    fun getCName() = "CName"
}

class A{
    var a = C()

    fun printC(c:C){
        println(c.getCName() + ",a:$a")
    }

    fun isDebug():Boolean{
        return true
    }
}