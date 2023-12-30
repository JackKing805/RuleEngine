package com.cool.jerry.exception

class IllStmtException(stmt: String) : RuntimeException("stmt $stmt is ill")