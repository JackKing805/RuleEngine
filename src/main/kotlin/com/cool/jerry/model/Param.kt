package com.cool.jerry.model

data class Param(
    private var name: String,
    var parseResult: ParseResult,
    private var modifierName: String? = null,//方法内部或者class内部重新定义的变量会重命名，如果这个值名字不为空，则用这个值获取变量
) {
    fun paramName() = modifierName ?: name

    fun getName() = name

    fun getModifierName() = modifierName

    fun setNewName(newName: String) {
        this.name = newName
    }

    fun setNewModifierName(newModifierName: String?) {
        this.modifierName = newModifierName
    }
}