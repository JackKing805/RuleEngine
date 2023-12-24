package com.cool.jerry.v2.rt_engine.exception

class RefNotDefinePropertiesException(ref: String, method: String) :
    RuntimeException("$ref not define $method")