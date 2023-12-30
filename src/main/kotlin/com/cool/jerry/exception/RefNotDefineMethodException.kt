package com.cool.jerry.exception

class RefNotDefineMethodException(ref: String, method: String) :
    RuntimeException("$ref not define $method")