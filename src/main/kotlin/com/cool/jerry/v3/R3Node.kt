package com.cool.jerry.v3

sealed class R3Node(
    open val source: String
) {
    data class Program(
        override val source: String,
        val statements: List<R3Node>
    ) : R3Node(source){
        override fun toString(): String {
            return statements.joinToString("\n")
        }
    }

    sealed class Statement(source: String) : R3Node(source) {
        data class FunctionStatement(
            override val source: String,
            val functionName: Expression.Define.Identifier.ID,
            val parameters: Expression.Define.Params,
            val functionBody: List<Expression>
        ) : Statement(source)

        data class ClassStatement(
            override val source: String,
            val className: Expression.Define.Identifier.ID,
            val parameters: Expression.Define.Params,
            val classBody: List<R3Node>
        ) : Statement(source)
    }


    sealed class Expression(source: String) : R3Node(source) {
        sealed class TypeExpression<T>(source: String, open val value: T) : Expression(source) {
            data class NumberTypeExpression(override val source: String, override val value: Long) :
                TypeExpression<Long>(source, value)

            data class StringTypeExpression(override val source: String, override val value: String) :
                TypeExpression<String>(source, value)

            data class BooleanTypeExpression(override val source: String, override val value: Boolean) :
                TypeExpression<Boolean>(source, value)

            data class FloatNumberTypeExpression(override val source: String, override val value: Double) :
                TypeExpression<Double>(source, value)

            data class ArrayExpression(override val source: String, override val value: Array<TypeExpression<*>>) :
                TypeExpression<Array<TypeExpression<*>>>(source, value) {
                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false

                    other as ArrayExpression

                    if (source != other.source) return false
                    return value.contentEquals(other.value)
                }

                override fun hashCode(): Int {
                    var result = source.hashCode()
                    result = 31 * result + value.contentHashCode()
                    return result
                }
            }

            data class OtherTypeExpression(override val source: String, override val value: Any) :
                TypeExpression<Any>(source, value)
        }

        data class ReturnExpression(override val source: String, val expression: Expression?) : Expression(source)

        data class ContinueExpression(override val source: String) : Expression(source)

        data class BreakExpression(override val source: String) : Expression(source)

        data class RangeExpression(override val source: String, val start: Expression, val end: Expression) :
            Expression(source)


        data class VariableExpression(
            override val source: String,
            val variableName: Define.Identifier.ID,
            val variableValue: Expression,
            val isConst: Boolean
        ) : Expression(source)


        data class IfExpression(
            override val source: String,
            val condition: Expression,
            val thenBody: List<Expression>,
            val elseBody: List<Expression>
        ) : Expression(source)

        data class LoopExpression(
            override val source: String,
            val condition: Expression,
            val conditionProxy: Define.Identifier.ID,
            val loopBody: List<Expression>
        ) : Expression(source)

        data class ArrayAccessExpression(
            override val source: String,
            val array: Expression,
            val index: TypeExpression.NumberTypeExpression
        ): Expression(source)

        data class ObjectMethodCallExpression(
            override val source: String,
            val objectExpression: Expression,
            val methodName: Define.Identifier.ID,
            val arguments: List<Expression>
        ): Expression(source)

        data class MethodCallExpression(
            override val source: String,
            val methodName: Define.Identifier.ID,
            val arguments: List<Expression>
        ): Expression(source)


        sealed class OperateExpression(source: String) : Expression(source) {
            sealed class BitOperateExpression(source: String, open val left: Expression, open val right: Expression) :
                OperateExpression(source) {
                data class BitLeftOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitRightOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitAndOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitOrOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitXorOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)
            }


            sealed class MathOperateExpression(source: String, open val left: Expression, open val right: Expression) :
                OperateExpression(source) {
                data class AddOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class SubOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class MulOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class DivOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class ModOperateExpression(//%
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)
            }

            sealed class MathAssignOperateExpression(
                source: String,
                open val left: Expression,
                open val right: Expression
            ) :
                OperateExpression(source) {
                data class AddAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class SubAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class MulAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class DivAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class AndAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class OrAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class XorAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class ModAssignOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)
            }

            sealed class CompareOperateExpression(
                source: String,
                open val left: Expression,
                open val right: Expression
            ) :
                OperateExpression(source) {
                data class AndCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class OrCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class EqualCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class NotEqualCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class LessEqualCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class MoreEqualCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class LessCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class MoreCompareOperateExpression(
                    override val source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)
            }

            sealed class NumberAutoOperateExpression(
                override val source: String,
                open val who: Define.Identifier
            ):OperateExpression(source) {
                data class NumberAutoIncreaseOperateExpression(
                    override val source: String,
                    override val who: Define.Identifier
                ): NumberAutoOperateExpression(source, who)

                data class NumberAutoDecreaseOperateExpression(
                    override val source: String,
                    override val who: Define.Identifier
                ): NumberAutoOperateExpression(source, who)
            }
        }

        sealed class Define(source: String) : Expression(source) {
            sealed class Identifier(source: String) : Define(source) {
                data class ID(override val source: String, val text: String) : Identifier(source)
                data class ID_REF(override val source: String, val text: String) : Identifier(source)
            }

            data class Params(
                override val source: String,
                val parameters: List<Identifier.ID>
            ) : Define(source)
        }
    }
}