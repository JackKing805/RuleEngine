package com.cool.jerry.exception

class RefNotDefineException(ref: String) : RuntimeException("$ref not define in container")