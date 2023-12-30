package com.cool.jerry.exception

class RefNotDefinePropertiesException(ref: String, method: String) :
    RuntimeException("$ref not define $method")