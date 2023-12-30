package com.cool.jerry.exception

import java.lang.RuntimeException

class DulVarDefineException(
    val name:String
):RuntimeException("val label[$name] is repeat")