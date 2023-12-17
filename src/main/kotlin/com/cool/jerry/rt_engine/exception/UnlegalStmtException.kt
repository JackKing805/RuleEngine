package com.cool.jerry.rt_engine.exception

class UnlegalStmtException(stmt: String) : RuntimeException("$stmt is un legal")