package com.cool.jerry.rt_engine.define


sealed class Node(
    open val source: String
) {
    data class Program(
        val nodes: List<Node>,
        override val source: String
    ):Node(source){
        override fun toString(): String {
            return "${nodes.joinToString("\n")}\nsource='$source'"
        }
    }

    sealed class Statement(
        source: String
    ) : Node(source) {
        data class NewVariableAssignmentStatement(
            val id: Expression.IdExpression,
            val value: Node,
            override val source: String
        ) : Statement(source)

        data class VariableAssignmentStatement(
            val id: Expression.IdExpression,
            val value: Node,
            override val source: String
        ) : Statement(source)

        data class VariablePropertiesAssignmentStatement(
            val id: Expression.IdExpression,
            val propertiesIdExpression: Expression.IdExpression.Id,
            val value: Node,
            override val source: String
        ) : Statement(source)

        data class InvokeStatement(val expression: Expression, override val source: String) : Statement(source)
        data class FunctionStatement(
            val functionName: Expression.IdExpression.Id,
            val params: List<Expression.IdExpression.Id>,
            val statements: List<Node>,
            val returnExpression: Node?,
            override val source: String
        ) : Statement(source)
    }

    sealed class Expression(source: String) : Node(source) {
        sealed class IdExpression(open val name: String,source: String) : Expression(source) {
            data class Id(
                override val name: String,
                override val source: String
            ) : IdExpression(name,source)

            data class IdRef(
                override val name: String,
                override val source: String
            ) : IdExpression(name,source)
        }

        sealed class TypeExpression(source: String) : Expression(source) {
            data class IntType(
                val value: Long,
                override val source: String
            ) : TypeExpression(source)

            data class StringType(
                val value: String,
                override val source: String
            ) : TypeExpression(source)

            data class FloatType(
                val value: Double,
                override val source: String
            ) : TypeExpression(source)
        }

        data class MethodCallExpression(
            val callIdExpression: IdExpression?,
            val methodIdExpression: IdExpression.Id,
            val params: List<Node>,
            override val source: String
        ) : Expression(source)

        data class MulDivExpression(
            val op: String,
            val leftExpression: Expression,
            val rightExpression: Expression,
            override val source: String
        ) : Expression(source)

        data class AddSubExpression(
            val op: String,
            val leftExpression: Expression,
            val rightExpression: Expression,
            override val source: String
        ) : Expression(source)

    }
}