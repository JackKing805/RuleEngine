package com.cool.jerry.exception

class NotDefineMethodException(method: String) :
    RuntimeException("not define $method")