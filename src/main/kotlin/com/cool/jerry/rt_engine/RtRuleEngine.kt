package com.cool.jerry.rt_engine

import com.cool.jerry.i.AbstractRuleEngine


/**
 *  val a = 1
 *
 *  #p
 *
 */
class RtRuleEngine : AbstractRuleEngine() {
    override fun execute(rule: String) {
        RtParser().parser(rule)
    }
}




