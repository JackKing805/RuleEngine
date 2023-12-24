package com.cool.jerry.v2.rt_engine.exception

class NotDefineMethodException(method: String) :
    RuntimeException("not define $method")