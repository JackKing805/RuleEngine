package com.cool.jerry.model

import com.cool.jerry.v3.R3Node

data class ExecuteResult(
    val r3Node: R3Node,
    val result: Any?
)