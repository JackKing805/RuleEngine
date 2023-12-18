package com.cool.jerry.rt_engine.exception

class NotDefineMethodException(method: String) :
    RuntimeException("not define $method")