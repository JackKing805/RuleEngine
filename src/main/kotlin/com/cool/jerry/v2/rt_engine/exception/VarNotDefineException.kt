package com.cool.jerry.v2.rt_engine.exception

import java.lang.RuntimeException

class VarNotDefineException(
     name:String
):RuntimeException("val label[$name] is not define")