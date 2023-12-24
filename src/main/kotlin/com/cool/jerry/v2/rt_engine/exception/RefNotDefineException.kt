package com.cool.jerry.v2.rt_engine.exception

class RefNotDefineException(ref: String) : RuntimeException("$ref not define in container")