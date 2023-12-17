package com.cool.jerry.rt_engine.exception

class NotRefVariableMethodException(variable: String) :
    RuntimeException("variable $variable is not Ref variable")