package com.cool.jerry.v3

sealed class R3Node(
    open var source: String
) {
    private var parent:R3Node?=null
    private var isParentSet = false

    @JvmName("sp")
    fun setParent(parent: R3Node?){
        if (isParentSet){
//            if (tree!=null){
//                if (tree!!.parent!=null && parent!=null){
//                    if (tree!!.parent!! == parent){
//                        return
//                    }
//                }
//            }
//            throw IllegalStateException("$source's parent can only set once")
            return
        }
        isParentSet = true
        this.parent = parent
    }

    fun parent() = parent


    data class Program(
        override var source: String,
        val statements: List<R3Node>
    ) : R3Node(source) {
        override fun toString(): String {
            return statements.joinToString("\n")
        }
    }


    sealed class Statement(source: String) : R3Node(source) {
        data class FunctionStatement(
            override var source: String,
            val functionName: Expression.Define.Identifier.ID,
            val parameters: Expression.Define.Params,
            val functionBody: List<Expression>
        ) : Statement(source)


        data class ConstructorFunctionStatement(
            override var source: String,
            val functionStatement: FunctionStatement
        ):Statement(source)

        data class ClassStatement(
            override var source: String,
            val className: Expression.Define.Identifier.ID,
            val parameters: Expression.Define.Params,
            val classBody: List<R3Node>,
        ) : Statement(source)
    }

    sealed class Expression(source: String) : R3Node(source) {
        sealed class TypeExpression<T>(source: String, open val value: T) : Expression(source) {
            data class NumberTypeExpression(override var source: String, override val value: Long) :
                TypeExpression<Long>(source, value)

            data class StringTypeExpression(override var source: String, override val value: String) :
                TypeExpression<String>(source, value)

            data class BooleanTypeExpression(override var source: String, override val value: Boolean) :
                TypeExpression<Boolean>(source, value)

            data class FloatNumberTypeExpression(override var source: String, override val value: Double) :
                TypeExpression<Double>(source, value)

            data class ArrayExpression(override var source: String, override val value: Array<Expression>) :
                TypeExpression<Array<Expression>>(source, value) {
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

//            data class OtherTypeExpression(override var source: String, override val value: Any) :
//                TypeExpression<Any>(source, value)
        }

        data class ReturnExpression(override var source: String, val expression: Expression?) : Expression(source)

        data class ContinueExpression(override var source: String) : Expression(source)

        data class BreakExpression(override var source: String) : Expression(source)

        data class RangeExpression(override var source: String, val start: Expression, val end: Expression) :
            Expression(source)

        data class VariableExpression(
            override var source: String,
            val variableName: Define.Identifier.ID,
            val variableValue: Expression,
            val isConst: Boolean
        ) : Expression(source)

        data class IfExpression(
            override var source: String,
            val condition: Expression,
            val thenBody: List<Expression>,
            val elseBody: List<Expression>
        ) : Expression(source)

        data class LoopExpression(
            override var source: String,
            val condition: Expression,
            val conditionProxy: Define.Identifier.ID,
            val loopBody: List<Expression>
        ) : Expression(source)

        data class ArrayAccessExpression(
            override var source: String,
            val array: Expression,
            val index: TypeExpression.NumberTypeExpression
        ) : Expression(source)

        data class ObjectMethodCallExpression(
            override var source: String,
            val objectExpression: Expression,
            val methodName: Define.Identifier.ID,
            val arguments: List<Expression>
        ) : Expression(source)

        data class MethodCallExpression(
            override var source: String,
            val methodName: Define.Identifier.ID,
            val arguments: List<Expression>
        ) : Expression(source)

        data class ObjectPropertiesExpression(
            override var source: String,
            val objectExpression: Expression,
            val propertiesName:Define.Identifier.ID
        ):Expression(source)


        data class SignedExpression(
            override var source: String,
            val innerExpression: Expression,
        ):Expression(source)

        data class LambdaExpression(
            override var source: String,
            val parameters: Define.Params,
            val functionBody: List<Expression>
        ):Expression(source)


        sealed class OperateExpression(source: String) : Expression(source) {
            sealed class BitOperateExpression(source: String, open val left: Expression, open val right: Expression) :
                OperateExpression(source) {
                data class BitLeftOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitRightOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitAndOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitOrOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)

                data class BitXorOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : BitOperateExpression(source, left, right)
            }

            sealed class MathOperateExpression(source: String, open val left: Expression, open val right: Expression) :
                OperateExpression(source) {
                data class AddOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class SubOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class MulOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class DivOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathOperateExpression(source, left, right)

                data class ModOperateExpression(//%
                    override var source: String,
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
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class SubAssignOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class MulAssignOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class DivAssignOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class AndAssignOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class OrAssignOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class XorAssignOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : MathAssignOperateExpression(source, left, right)

                data class ModAssignOperateExpression(
                    override var source: String,
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
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class OrCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class EqualCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class NotEqualCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class LessEqualCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class MoreEqualCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class LessCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)

                data class MoreCompareOperateExpression(
                    override var source: String,
                    override val left: Expression,
                    override val right: Expression
                ) : CompareOperateExpression(source, left, right)
            }

            sealed class NumberAutoOperateExpression(
                override var source: String,
                open val who: Define.Identifier
            ) :
                OperateExpression(source) {
                data class NumberAutoIncreaseOperateExpression(
                    override var source: String,
                    override val who: Define.Identifier
                ) : NumberAutoOperateExpression(source, who)

                data class NumberAutoDecreaseOperateExpression(
                    override var source: String,
                    override val who: Define.Identifier
                ) : NumberAutoOperateExpression(source, who)
            }

            data class AssignOperateExpression(
                override var source: String,
                val left: Expression,
                val right: Expression
            ) : Expression(source)
        }

        sealed class Define(source: String) : Expression(source) {
            sealed class Identifier(source: String,open var text: String) : Define(source) {
                data class ID(override var source: String,override var text: String) : Identifier(source,text)
                data class ID_REF(override var source: String,override var text: String) : Identifier(source,text)
            }

            data class Params(
                override var source: String,
                val parameters: List<Identifier.ID>
            ) : Define(source)
        }
    }
}

