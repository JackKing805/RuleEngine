package com.cool.jerry.rt_engine.exception

class OpNotSupport(
    name:String
):RuntimeException("$name can not into operation")