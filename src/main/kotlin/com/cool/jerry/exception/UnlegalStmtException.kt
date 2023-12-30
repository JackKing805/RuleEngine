package com.cool.jerry.exception

class UnlegalStmtException(stmt: String) : RuntimeException("$stmt is un legal")