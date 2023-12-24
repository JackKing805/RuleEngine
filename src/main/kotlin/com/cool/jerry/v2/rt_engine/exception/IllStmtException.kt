package com.cool.jerry.v2.rt_engine.exception

class IllStmtException(stmt: String) : RuntimeException("stmt $stmt is ill")