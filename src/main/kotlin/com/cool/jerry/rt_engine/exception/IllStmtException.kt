package com.cool.jerry.rt_engine.exception

class IllStmtException(stmt: String) : RuntimeException("stmt $stmt is ill")