package com.cool.jerry.v3

import com.cool.jerry.model.Param
import com.cool.jerry.model.ParseResult

data class ClassEnvironmentDefine(
    val classDefine:ParseResult.Define.ClassDefine,
    val scopeParams: MutableList<Param>,
)