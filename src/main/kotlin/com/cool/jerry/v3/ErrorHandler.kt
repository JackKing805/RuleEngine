package com.cool.jerry.v3

import org.antlr.v4.runtime.DefaultErrorStrategy
import org.antlr.v4.runtime.InputMismatchException
import org.antlr.v4.runtime.Parser
import org.antlr.v4.runtime.RecognitionException

class ErrorHandler: DefaultErrorStrategy() {
    override fun reportError(recognizer: Parser, e: RecognitionException) {
        throw e
    }

    override fun reportInputMismatch(recognizer: Parser?, e: InputMismatchException) {
        throw e
    }

    override fun reportUnwantedToken(recognizer: Parser?) {
        super.reportUnwantedToken(recognizer)
        val t = recognizer!!.currentToken
        val tokenName = getTokenErrorDisplay(t)
        val expecting = getExpectedTokens(recognizer)
        val msg = "extraneous input " + tokenName + " expecting " +
                expecting.toString(recognizer.vocabulary)
        throw RuntimeException(msg)
    }
}