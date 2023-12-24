package com.cool.jerry.v2.rt_engine.exception

class UnlegalStmtException(stmt: String) : RuntimeException("$stmt is un legal")