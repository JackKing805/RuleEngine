package com.cool.jerry.rt_engine.exception

class RefNotDefinePropertiesException(ref: String, method: String) :
    RuntimeException("$ref not define $method")