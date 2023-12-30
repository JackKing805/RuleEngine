package com.cool.jerry.exception

class OpNotSupport(
    name:String,
    source:String
):RuntimeException("$name can not into operation:$source")