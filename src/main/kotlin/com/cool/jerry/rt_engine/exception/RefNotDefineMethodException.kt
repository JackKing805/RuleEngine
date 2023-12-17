package com.cool.jerry.rt_engine.exception

class RefNotDefineMethodException(ref: String, method: String) :
    RuntimeException("$ref not define $method")