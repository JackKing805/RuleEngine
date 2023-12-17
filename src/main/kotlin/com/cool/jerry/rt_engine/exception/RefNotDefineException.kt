package com.cool.jerry.rt_engine.exception

class RefNotDefineException(ref: String) : RuntimeException("$ref not define in container")