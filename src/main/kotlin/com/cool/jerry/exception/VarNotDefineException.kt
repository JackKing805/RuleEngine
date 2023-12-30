package com.cool.jerry.exception

import java.lang.RuntimeException

class VarNotDefineException(
     name:String
):RuntimeException("val label[$name] is not define")