package com.cool.jerry.v2.rt_engine.exception

class OpNotSupport(
    name:String,
    source:String
):RuntimeException("$name can not into operation:$source")