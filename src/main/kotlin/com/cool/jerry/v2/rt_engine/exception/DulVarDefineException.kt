package com.cool.jerry.v2.rt_engine.exception

import java.lang.RuntimeException

class DulVarDefineException(
    val name:String
):RuntimeException("val label[$name] is repeat")