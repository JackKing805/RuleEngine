package com.cool.jerry.v2.rt_engine.define


sealed class Node(
    open val source: String,
) {
    data class Program(
        val nodes: List<Node>,
        override val source: String
    ): Node(source){
        override fun toString(): String {
            return "${nodes.joinToString("\n")}\nsource='$source'"
        }
    }

    sealed class Statement(
        source: String,
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
            abstract fun uniqueName():String

            data class Id(
                override val name: String,
                override val source: String,
            ) : IdExpression(name,source){
                override fun uniqueName():String {
                    return name
                }
            }
            data class IdRef(
                override val name: String,
                override val source: String
            ) : IdExpression(name,source){
                override fun uniqueName(): String {
                    throw RuntimeException("idRef not support uniqueName")
                }
            }
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

            data class BooleanType(
                val value: Boolean,
                override val source: String
            ): TypeExpression(source)

            data class ArrayType(
                val value: Array<TypeExpression>,
                override val source: String
            ): TypeExpression(source) {
                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false

                    other as ArrayType

                    if (!value.contentEquals(other.value)) return false
                    if (source != other.source) return false

                    return true
                }

                override fun hashCode(): Int {
                    var result = value.contentHashCode()
                    result = 31 * result + source.hashCode()
                    return result
                }
            }
        }

        data class ObjectMethodCallExpression(
            val callIdExpression: Node,
            val methodIdExpression: IdExpression.Id,
            val params: List<Node>,
            override val source: String
        ) : Expression(source)

        data class MethodCallExpression(
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

        data class EqualsExpression(
            val leftExpression: Expression,
            val rightExpression: Expression,
            override val source: String
        ) : Expression(source)

        data class AndExpression(
            val leftExpression: Expression,
            val rightExpression: Expression,
            override val source: String
        ) : Expression(source)

        data class OrExpression(
            val leftExpression: Expression,
            val rightExpression: Expression,
            override val source: String
        ) : Expression(source)

        data class IfExpression(
            val ifCondition: Expression,
            val ifStatements:List<Statement>,
            val elseStatements:List<Statement>,
            override val source: String
        ): Expression(source)

        data class GetPropertiesExpression(
            val expression: Expression,
            val properties: Properties.Properties,
            override val source: String
        ): Expression(source)


        data class LoopExpression(
            val targetExpression: Expression,
            val item: IdExpression.Id,
            val bloakStatements:List<Statement>,
            override val source: String
        ): Expression(source)


        data class ArrayAccessExpression(
            val targetExpression: Expression,
            val index: TypeExpression.IntType,
            override val source: String
        ): Expression(source)

        data class DefineArrayExpression(
            val items:List<Expression>,
            override val source: String
        ): Expression(source)
    }

    sealed class Properties(source: String): Node(source){
        data class Properties(
            val id: Expression.IdExpression.Id,
            val index: Expression.TypeExpression.IntType?,
            override val source: String
        ): Node.Properties(source)
    }
}