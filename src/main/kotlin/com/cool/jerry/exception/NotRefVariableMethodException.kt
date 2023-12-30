package com.cool.jerry.exception

class NotRefVariableMethodException(variable: String) :
    RuntimeException("variable $variable is not Ref variable")