package com.cool.jerry.v2.rt_engine.exception

class NotRefVariableMethodException(variable: String) :
    RuntimeException("variable $variable is not Ref variable")