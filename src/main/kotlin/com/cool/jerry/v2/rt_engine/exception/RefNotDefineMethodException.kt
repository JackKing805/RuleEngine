package com.cool.jerry.v2.rt_engine.exception

class RefNotDefineMethodException(ref: String, method: String) :
    RuntimeException("$ref not define $method")