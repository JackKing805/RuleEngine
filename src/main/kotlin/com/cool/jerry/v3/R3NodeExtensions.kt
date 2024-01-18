package com.cool.jerry.v3

import com.cool.jerry.model.Param
import com.cool.jerry.model.ParseResult


internal fun R3Node.haveIdEquals(name:String):Boolean{
    when(this){
        is R3Node.Expression.ArrayAccessExpression -> {
            return this.array.haveIdEquals(name)
        }
        is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
            val a  = this.left.haveIdEquals(name)
            if (a){
                return true
            }
            return right.haveIdEquals(name)
        }
        is R3Node.Expression.BreakExpression -> {
            return false
        }
        is R3Node.Expression.ContinueExpression -> {
            return false
        }
        is R3Node.Expression.Define.Identifier.ID -> {
            return name==text
        }
        is R3Node.Expression.Define.Identifier.ID_REF -> {
            return false
        }
        is R3Node.Expression.Define.Params -> {
            return false
        }
        is R3Node.Expression.IfExpression -> {
            val a = this.condition.haveIdEquals(name)
            if (a){
                return true
            }
            for (expression in this.thenBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            for (expression in this.elseBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Expression.LoopExpression -> {
            if (this.condition!=null){
                if (this.condition.haveIdEquals(name)){
                    return true
                }
            }

            for (expression in this.loopBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Expression.MethodCallExpression -> {
            for (argument in arguments) {
                if (argument.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Expression.ObjectMethodCallExpression -> {
            if (this.objectExpression.haveIdEquals(name)){
                return true
            }
            for (argument in this.arguments) {
                if (argument.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Expression.ObjectPropertiesExpression -> {
            if (this.objectExpression.haveIdEquals(name)){
                return true
            }
            return false
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression->{
            if (this.left.haveIdEquals(name)){
                return true
            }
            return this.right.haveIdEquals(name)
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression->{
            if (this.left.haveIdEquals(name)){
                return true
            }
            return this.right.haveIdEquals(name)
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression->{
            if (this.left.haveIdEquals(name)){
                return true
            }
            return this.right.haveIdEquals(name)
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression->{
            if (this.left.haveIdEquals(name)){
                return true
            }
            return this.right.haveIdEquals(name)
        }
        is R3Node.Expression.OperateExpression.NumberAutoOperateExpression->{
            return this.who.haveIdEquals(name)
        }
        is R3Node.Expression.RangeExpression -> {
            if (this.start.haveIdEquals(name)){
                return true
            }
            return this.end.haveIdEquals(name)
        }
        is R3Node.Expression.ReturnExpression -> {
            return this.expression?.haveIdEquals(name)?:false
        }
        is R3Node.Expression.TypeExpression.ArrayExpression -> {
            for (typeExpression in this.value) {
                if (typeExpression.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Expression.TypeExpression.BooleanTypeExpression -> {
            return false
        }
        is R3Node.Expression.TypeExpression.FloatNumberTypeExpression -> {
            return false
        }
        is R3Node.Expression.TypeExpression.NumberTypeExpression -> {
            return false
        }
        is R3Node.Expression.TypeExpression.StringTypeExpression -> {
            return false
        }
        is R3Node.Expression.VariableExpression -> {
            return this.variableValue.haveIdEquals(name)
        }
        is R3Node.Program -> {
            for (statement in this.statements) {
                if (statement.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Statement.ClassStatement -> {
            for (r3Node in this.classBody) {
                if (r3Node.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }
        is R3Node.Statement.ConstructorFunctionStatement -> {
            return this.functionStatement.haveIdEquals(name)
        }
        is R3Node.Statement.FunctionStatement -> {
            for (expression in this.functionBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }

        is R3Node.Expression.SignedExpression -> {
            return this.innerExpression.haveIdEquals(name)
        }

        is R3Node.Expression.LambdaExpression -> {
            for (expression in this.functionBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }

        is R3Node.Expression.CatchErrorExpression -> {
            for (expression in this.watchBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            if (this.errorName.haveIdEquals(name)){
                return true
            }
            for (expression in this.errorBody) {
                if (expression.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }

        is R3Node.Expression.MapExpression -> {
            for (entry in mapExpression) {
                if (entry.key.haveIdEquals(name)){
                    return true
                }
                if (entry.value.haveIdEquals(name)){
                    return true
                }
            }
            return false
        }

        is R3Node.Expression.ReserveExpression -> {
            return this.expression.haveIdEquals(name)
        }

        is R3Node.Expression.AsyncExpression -> {
            return this.asyncBody.any { it.haveIdEquals(name) }
        }
    }
}

@Deprecated("use version2", replaceWith = ReplaceWith("modifierOldIdToNewIdName2(oldName,newName)"))
internal fun R3Node.modifierOldIdToNewIdName(oldName:String,newName:String):R3Node{
    when(this){
        is R3Node.Expression.ArrayAccessExpression -> {
            copy(array = this.array.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
            copy(left=this.left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression, right = this.right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.BreakExpression -> {
            this
        }
        is R3Node.Expression.ContinueExpression -> {
            this
        }
        is R3Node.Expression.Define.Identifier.ID -> {
            if (oldName==text){
                val nn = text
                this.copy(text=newName, source = source.replace(nn,newName)).apply {
                    setParent(this@modifierOldIdToNewIdName.parent())
                }
            }else{
                this
            }
        }
        is R3Node.Expression.Define.Identifier.ID_REF -> {
            this
        }
        is R3Node.Expression.Define.Params -> {
            this
        }
        is R3Node.Expression.IfExpression -> {
            copy(
                condition = condition.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                thenBody = thenBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                },
                elseBody = elseBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.LoopExpression -> {
            copy(
                condition = condition?.modifierOldIdToNewIdName(oldName,newName) as? R3Node.Expression,
                loopBody = loopBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.MethodCallExpression -> {
            copy(
                arguments=arguments.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.ObjectMethodCallExpression -> {
            copy(
                objectExpression = objectExpression.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                arguments = arguments.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.ObjectPropertiesExpression -> {
            copy(
                objectExpression = objectExpression.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression.BitAndOperateExpression ->{
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression.BitLeftOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression.BitOrOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression.BitRightOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression.BitXorOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.AndCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.EqualCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.LessCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.LessEqualCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.MoreCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.MoreEqualCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.NotEqualCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression.OrCompareOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.AddAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.AndAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.DivAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.ModAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.MulAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.OrAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.SubAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression.XorAssignOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression.AddOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression.DivOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression.ModOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression.MulOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression.SubOperateExpression -> {
            copy(
                left=left.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                right = right.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoDecreaseOperateExpression -> {
            copy(
                who =who.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Identifier
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoIncreaseOperateExpression -> {
            copy(
                who =who.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Identifier
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.RangeExpression ->{
            copy(
                start = start.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression,
                end = end.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.ReturnExpression -> {
            copy(
                expression = expression?.modifierOldIdToNewIdName(oldName,newName) as? R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.TypeExpression.ArrayExpression -> {
            copy(
                value = value.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }.toTypedArray()
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Expression.TypeExpression.BooleanTypeExpression -> {
            this
        }
        is R3Node.Expression.TypeExpression.FloatNumberTypeExpression -> {
            this
        }
        is R3Node.Expression.TypeExpression.NumberTypeExpression -> {
            this
        }
        is R3Node.Expression.TypeExpression.StringTypeExpression -> {
            this
        }
        is R3Node.Expression.VariableExpression -> {
            copy(variableName = variableName.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Identifier.ID, variableValue = variableValue.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Program -> {
            copy(
                statements = statements.map {
                    it.modifierOldIdToNewIdName(oldName,newName)
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Statement.ClassStatement -> {
            copy(
                parameters= parameters.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Params,
                classBody = classBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName)
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Statement.ConstructorFunctionStatement -> {
            copy(
                functionStatement = functionStatement.modifierOldIdToNewIdName(oldName,newName) as R3Node.Statement.FunctionStatement
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
        is R3Node.Statement.FunctionStatement -> {
            copy(
                parameters = parameters.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Params,
                functionBody = functionBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }

        is R3Node.Expression.SignedExpression -> {
            copy(
                innerExpression = innerExpression.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }

        is R3Node.Expression.LambdaExpression -> {
            copy(
                parameters = parameters.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Params,
                functionBody = functionBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }

        is R3Node.Expression.CatchErrorExpression -> {
            copy(
                watchBody = watchBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                },
                errorBody = errorBody.map {
                    it.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
                },
                errorName = errorName.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression.Define.Identifier.ID
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }

        is R3Node.Expression.MapExpression -> {
            copy(
                mapExpression = mapExpression.map {
                    it.key.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression to it.value.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
                }.toMap()
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }

        is R3Node.Expression.ReserveExpression -> {
            copy(
                expression = expression.modifierOldIdToNewIdName(oldName,newName) as R3Node.Expression
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }

        is R3Node.Expression.AsyncExpression -> {
            copy(
                asyncBody = asyncBody.map {
                    it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
                }
            ).apply {
                setParent(this@modifierOldIdToNewIdName.parent())
            }
        }
    }.let {
        return it
    }
}


internal fun R3Node.modifierOldIdToNewIdName2(oldName:String,newName:String):R3Node{
    when(this){
        is R3Node.Expression.ArrayAccessExpression -> {
            this.array.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
            this.left.modifierOldIdToNewIdName2(oldName,newName)
            this.right.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.BreakExpression -> {
        }
        is R3Node.Expression.ContinueExpression -> {
        }
        is R3Node.Expression.Define.Identifier.ID -> {
            if (oldName==text){
                source = source.replace(text,newName)
                text = newName
            }
        }
        is R3Node.Expression.Define.Identifier.ID_REF -> {
        }
        is R3Node.Expression.Define.Params -> {
        }
        is R3Node.Expression.IfExpression -> {
            condition.modifierOldIdToNewIdName2(oldName,newName)
            thenBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
            elseBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
        }
        is R3Node.Expression.LoopExpression -> {
            condition?.modifierOldIdToNewIdName2(oldName,newName)
            loopBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
        }
        is R3Node.Expression.MethodCallExpression -> {
            arguments.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
        }
        is R3Node.Expression.ObjectMethodCallExpression -> {
            objectExpression.modifierOldIdToNewIdName2(oldName,newName)
            arguments.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
        }
        is R3Node.Expression.ObjectPropertiesExpression -> {
            objectExpression.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression ->{
            left.modifierOldIdToNewIdName2(oldName,newName)
            right.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression -> {
            left.modifierOldIdToNewIdName2(oldName,newName)
            right.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression -> {
            left.modifierOldIdToNewIdName2(oldName,newName)
            right.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.OperateExpression.NumberAutoOperateExpression -> {
            who.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.RangeExpression ->{
            start.modifierOldIdToNewIdName2(oldName,newName)
            end.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.ReturnExpression -> {
            expression?.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Expression.TypeExpression.ArrayExpression -> {
            value.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName)
            }
        }
        is R3Node.Expression.TypeExpression.BooleanTypeExpression -> {
        }
        is R3Node.Expression.TypeExpression.FloatNumberTypeExpression -> {
        }
        is R3Node.Expression.TypeExpression.NumberTypeExpression -> {
        }
        is R3Node.Expression.TypeExpression.StringTypeExpression -> {
        }
        is R3Node.Expression.VariableExpression -> {
            variableName.modifierOldIdToNewIdName2(oldName,newName)
            variableValue.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Program -> {
            statements.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName)
            }
        }
        is R3Node.Statement.ClassStatement -> {
            parameters.modifierOldIdToNewIdName2(oldName,newName)
            classBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName)
            }
        }
        is R3Node.Statement.ConstructorFunctionStatement -> {
            functionStatement.modifierOldIdToNewIdName2(oldName,newName)
        }
        is R3Node.Statement.FunctionStatement -> {
            parameters.modifierOldIdToNewIdName2(oldName,newName)
            functionBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
        }
        is R3Node.Expression.SignedExpression -> {
            innerExpression.modifierOldIdToNewIdName2(oldName,newName)
        }

        is R3Node.Expression.OperateExpression.CompareOperateExpression->{
            left.modifierOldIdToNewIdName2(oldName,newName)
            right.modifierOldIdToNewIdName2(oldName,newName)
        }

        is R3Node.Expression.LambdaExpression -> {
            parameters.modifierOldIdToNewIdName2(oldName,newName)
            functionBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName) as R3Node.Expression
            }
        }

        is R3Node.Expression.CatchErrorExpression -> {
            errorName.modifierOldIdToNewIdName2(oldName,newName)
            watchBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName)
            }
            errorBody.onEach {
                it.modifierOldIdToNewIdName2(oldName,newName)
            }
        }

        is R3Node.Expression.MapExpression -> {
            for (entry in mapExpression) {
                entry.key.modifierOldIdToNewIdName2(oldName,newName)
                entry.value.modifierOldIdToNewIdName2(oldName,newName)
            }
        }

        is R3Node.Expression.ReserveExpression -> {
            expression.modifierOldIdToNewIdName2(oldName,newName)
        }

        is R3Node.Expression.AsyncExpression -> {
            for (expression in asyncBody) {
                expression.modifierOldIdToNewIdName2(oldName, newName)
            }
        }
    }
    return this
}


internal fun R3Node.levelSet(parent:R3Node?){
    this.setParent(parent)
    when(this){
        is R3Node.Expression.ArrayAccessExpression -> {
            this.array.levelSet(parent)
            this.index.levelSet(parent)
        }
        is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
            this.left.levelSet(parent)
            this.right.levelSet(parent)
        }
        is R3Node.Expression.BreakExpression -> {
            this.setParent(parent)
        }
        is R3Node.Expression.ContinueExpression -> {
            this.setParent(parent)
        }
        is R3Node.Expression.Define.Identifier.ID -> {
            this.setParent(parent)
        }
        is R3Node.Expression.Define.Identifier.ID_REF -> {
            this.setParent(parent)
        }
        is R3Node.Expression.Define.Params -> {
            for (parameter in this.parameters) {
                parameter.levelSet(parent)
            }
        }
        is R3Node.Expression.IfExpression -> {
            this.condition.levelSet(parent)
            for (expression in this.thenBody) {
                expression.levelSet(this)
            }
            for (expression in this.elseBody) {
                expression.levelSet(this)
            }
        }
        is R3Node.Expression.LoopExpression -> {
            this.condition?.levelSet(parent)
            this.conditionProxy?.levelSet(this)
            for (expression in this.loopBody) {
                expression.levelSet(this)
            }
        }
        is R3Node.Expression.MethodCallExpression -> {
            this.methodName.levelSet(parent)
            for (argument in this.arguments) {
                argument.levelSet(parent)
            }
        }
        is R3Node.Expression.ObjectMethodCallExpression -> {
            this.objectExpression.levelSet(parent)
            this.methodName.levelSet(this)
            for (argument in this.arguments) {
                argument.levelSet(parent)
            }
        }
        is R3Node.Expression.ObjectPropertiesExpression -> {
            this.objectExpression.levelSet(parent)
            this.propertiesName.levelSet(this)
        }
        is R3Node.Expression.OperateExpression.BitOperateExpression -> {
            this.left.levelSet(parent)
            this.right.levelSet(parent)
        }
        is R3Node.Expression.OperateExpression.CompareOperateExpression->{
            this.left.levelSet(parent)
            this.right.levelSet(parent)
        }
        is R3Node.Expression.OperateExpression.MathAssignOperateExpression->{
            this.left.levelSet(parent)
            this.right.levelSet(parent)
        }
        is R3Node.Expression.OperateExpression.MathOperateExpression->{
            this.left.levelSet(parent)
            this.right.levelSet(parent)
        }
        is R3Node.Expression.OperateExpression.NumberAutoOperateExpression->{
            this.who.levelSet(parent)
        }
        is R3Node.Expression.RangeExpression -> {
            this.start.levelSet(parent)
            this.end.levelSet(parent)
        }
        is R3Node.Expression.ReturnExpression -> {
            this.expression?.levelSet(parent)
        }
        is R3Node.Expression.TypeExpression.ArrayExpression -> {
            for (typeExpression in this.value) {
                typeExpression.levelSet(parent)
            }
        }
        is R3Node.Expression.TypeExpression.BooleanTypeExpression -> {
        }
        is R3Node.Expression.TypeExpression.FloatNumberTypeExpression -> {

        }
        is R3Node.Expression.TypeExpression.NumberTypeExpression -> {

        }
        is R3Node.Expression.TypeExpression.StringTypeExpression -> {

        }
        is R3Node.Expression.VariableExpression -> {
            this.variableName.levelSet(parent)
            this.variableValue.levelSet(parent)
        }
        is R3Node.Program -> {
            for (statement in this.statements) {
                statement.levelSet(parent)
            }
        }
        is R3Node.Statement.ClassStatement -> {
            this.className.levelSet(parent)
            this.parameters.levelSet(this)
            for (r3Node in this.classBody) {
                r3Node.levelSet(this)
            }
        }
        is R3Node.Statement.FunctionStatement -> {
            this.functionName.levelSet(parent)
            this.parameters.levelSet(this)
            for (expression in this.functionBody) {
                expression.levelSet(this)
            }
        }

        is R3Node.Statement.ConstructorFunctionStatement -> {
//                    this.functionStatement.functionName.levelSet(this)
//                    this.functionStatement.parameters.levelSet(this)
            this.functionStatement.levelSet(this)
        }

        is R3Node.Expression.SignedExpression ->{
            this.innerExpression.levelSet(parent)
        }

        is R3Node.Expression.LambdaExpression -> {
            this.parameters.levelSet(this)
            for (expression in this.functionBody) {
                expression.levelSet(this)
            }
        }

        is R3Node.Expression.CatchErrorExpression -> {
            this.errorName.levelSet(this)
            for (expression in this.watchBody) {
                expression.levelSet(this)
            }
            for (expression in this.errorBody) {
                expression.levelSet(this)
            }
        }

        is R3Node.Expression.MapExpression -> {
            for (entry in mapExpression) {
                entry.key.levelSet(parent)
                entry.value.levelSet(parent)
            }
        }

        is R3Node.Expression.ReserveExpression -> {
            expression.levelSet(parent)
        }

        is R3Node.Expression.AsyncExpression -> {
            for (expression in asyncBody) {
                expression.levelSet(parent)
            }
        }
    }
}

internal fun List<R3Node>.haveReturnExpression():Boolean{
    return haveTypeExpression(R3Node.Expression.ReturnExpression::class.java)
}

internal fun R3Node.haveReturnExpression():Boolean{
    return haveTypeExpression(R3Node.Expression.ReturnExpression::class.java)
}

internal fun List<R3Node>.haveTypeExpression(r3Class:Class<out R3Node>):Boolean{
    for (r3Node in this) {
        if (r3Node.haveTypeExpression(r3Class)){
            return true
        }
    }
    return false
}
internal fun R3Node.haveTypeExpression(r3Class:Class<out R3Node>):Boolean{
     when(this){
        is R3Node.Expression.ArrayAccessExpression -> {
            if (r3Class==R3Node.Expression.ArrayAccessExpression::class.java){
                return true
            }
            return array.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.OperateExpression.AssignOperateExpression -> {
            if (r3Class==R3Node.Expression.OperateExpression.AssignOperateExpression::class.java){
                return true
            }
            return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.AsyncExpression -> {
            if (r3Class==R3Node.Expression.AsyncExpression::class.java){
                return true
            }
            return this.asyncBody.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.BreakExpression -> {
            if (r3Class==R3Node.Expression.BreakExpression::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.CatchErrorExpression -> {
            if (r3Class==R3Node.Expression.CatchErrorExpression::class.java){
                return true
            }
            return this.errorName.haveTypeExpression(r3Class) || this.watchBody.haveTypeExpression(r3Class) || errorBody.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.ContinueExpression -> {
            if (r3Class==R3Node.Expression.ContinueExpression::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.Define.Identifier.ID -> {
            if (r3Class==R3Node.Expression.Define.Identifier.ID::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.Define.Identifier.ID_REF -> {
            if (r3Class==R3Node.Expression.Define.Identifier.ID_REF::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.Define.Params -> {
            if (r3Class==R3Node.Expression.Define.Params::class.java){
                return true
            }
            return parameters.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.IfExpression -> {
            if (r3Class==R3Node.Expression.IfExpression::class.java){
                return true
            }
            return this.condition.haveTypeExpression(r3Class) || this.thenBody.haveTypeExpression(r3Class) || elseBody.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.LambdaExpression -> {
            if (r3Class==R3Node.Expression.LambdaExpression::class.java){
                return true
            }
            return parameters.haveTypeExpression(r3Class) || this.functionBody.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.LoopExpression -> {
            if (r3Class==R3Node.Expression.LoopExpression::class.java){
                return true
            }
            return this.condition?.haveTypeExpression(r3Class)?:false || this.loopBody.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.MapExpression -> {
            if (r3Class==R3Node.Expression.MapExpression::class.java){
                return true
            }
            return mapExpression.any {
                it.key.haveTypeExpression(r3Class) || it.value.haveTypeExpression(r3Class)
            }
        }
        is R3Node.Expression.MethodCallExpression -> {
            if (r3Class==R3Node.Expression.MethodCallExpression::class.java){
                return true
            }
            return this.methodName.haveTypeExpression(r3Class) || this.arguments.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.ObjectMethodCallExpression -> {
            if (r3Class==R3Node.Expression.ObjectPropertiesExpression::class.java){
                return true
            }
            return this.objectExpression.haveTypeExpression(r3Class) || this.methodName.haveTypeExpression(r3Class) || this.arguments.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.ObjectPropertiesExpression -> {
            if (r3Class==R3Node.Expression.ObjectPropertiesExpression::class.java){
                return true
            }
            return this.objectExpression.haveTypeExpression(r3Class) || this.propertiesName.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.RangeExpression -> {
            if (r3Class==R3Node.Expression.RangeExpression::class.java){
                return true
            }
            return this.start.haveTypeExpression(r3Class) || this.end.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.ReserveExpression -> {
            if (r3Class==R3Node.Expression.ReserveExpression::class.java){
                return true
            }
            return this.expression.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.ReturnExpression -> {
            if (r3Class==R3Node.Expression.ReturnExpression::class.java){
                return true
            }
            return this.expression?.haveTypeExpression(r3Class)?:false
        }
        is R3Node.Expression.SignedExpression -> {
            if (r3Class==R3Node.Expression.SignedExpression::class.java){
                return true
            }
            return this.innerExpression.haveTypeExpression(r3Class)
        }
        is R3Node.Expression.TypeExpression.ArrayExpression -> {
            if (r3Class==R3Node.Expression.TypeExpression.ArrayExpression::class.java){
                return true
            }
            return this.value.any {
                it.haveTypeExpression(r3Class)
            }
        }
        is R3Node.Expression.TypeExpression.BooleanTypeExpression -> {
            if (r3Class==R3Node.Expression.TypeExpression.BooleanTypeExpression::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.TypeExpression.FloatNumberTypeExpression -> {
            if (r3Class==R3Node.Expression.TypeExpression.FloatNumberTypeExpression::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.TypeExpression.NumberTypeExpression -> {
            if (r3Class==R3Node.Expression.TypeExpression.NumberTypeExpression::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.TypeExpression.StringTypeExpression -> {
            if (r3Class==R3Node.Expression.TypeExpression.StringTypeExpression::class.java){
                return true
            }
            return false
        }
        is R3Node.Expression.VariableExpression -> {
            if (r3Class==R3Node.Expression.VariableExpression::class.java){
                return true
            }
            return this.variableValue.haveTypeExpression(r3Class) || this.variableName.haveTypeExpression(r3Class)
        }
        is R3Node.Program -> {
            if (r3Class==R3Node.Program::class.java){
                return true
            }
            return this.statements.haveTypeExpression(r3Class)
        }
        is R3Node.Statement.ClassStatement -> {
            if (r3Class==R3Node.Statement.ClassStatement::class.java){
                return true
            }
            return this.parameters.haveTypeExpression(r3Class) || this.className.haveTypeExpression(r3Class) || this.classBody.haveTypeExpression(r3Class)
        }
        is R3Node.Statement.ConstructorFunctionStatement -> {
            if (r3Class==R3Node.Statement.ConstructorFunctionStatement::class.java){
                return true
            }
            return this.functionStatement.haveTypeExpression(r3Class)
        }
        is R3Node.Statement.FunctionStatement -> {
            if (r3Class==R3Node.Statement.FunctionStatement::class.java){
                return true
            }
            return this.functionBody.haveTypeExpression(r3Class) || this.functionName.haveTypeExpression(r3Class) || this.parameters.haveTypeExpression(r3Class)
        }
         is R3Node.Expression.OperateExpression.BitOperateExpression.BitAndOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.BitOperateExpression.BitAndOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.BitOperateExpression.BitLeftOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.BitOperateExpression.BitLeftOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.BitOperateExpression.BitOrOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.BitOperateExpression.BitOrOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.BitOperateExpression.BitRightOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.BitOperateExpression.BitRightOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.BitOperateExpression.BitXorOperateExpression ->  {
             if (r3Class==R3Node.Expression.OperateExpression.BitOperateExpression.BitXorOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.AndCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.AndCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.EqualCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.EqualCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.LessCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.LessCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.LessEqualCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.LessEqualCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.MoreCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.MoreCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.MoreEqualCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.MoreEqualCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.NotEqualCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.NotEqualCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.CompareOperateExpression.OrCompareOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.CompareOperateExpression.OrCompareOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.AddAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.AddAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.AndAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.AndAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.DivAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.DivAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.ModAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.ModAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.MulAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.MulAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.OrAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.OrAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.SubAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.SubAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathAssignOperateExpression.XorAssignOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathAssignOperateExpression.XorAssignOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathOperateExpression.AddOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathOperateExpression.AddOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathOperateExpression.DivOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathOperateExpression.DivOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathOperateExpression.ModOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathOperateExpression.ModOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathOperateExpression.MulOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathOperateExpression.MulOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.MathOperateExpression.SubOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.MathOperateExpression.SubOperateExpression::class.java){
                 return true
             }
             return this.left.haveTypeExpression(r3Class) || this.right.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoDecreaseOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoDecreaseOperateExpression::class.java){
                 return true
             }
             return this.who.haveTypeExpression(r3Class)
         }
         is R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoIncreaseOperateExpression -> {
             if (r3Class==R3Node.Expression.OperateExpression.NumberAutoOperateExpression.NumberAutoIncreaseOperateExpression::class.java){
                 return true
             }
             return this.who.haveTypeExpression(r3Class)
         }
     }
}