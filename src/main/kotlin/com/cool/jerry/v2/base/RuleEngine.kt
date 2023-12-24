package com.cool.jerry.v2.base

import com.cool.jerry.v2.rt_engine.define.Node
import com.example.testsensor.rule.EngineInformation

interface RuleEngine {
    fun engineInformation() : EngineInformation {
        return EngineInformation("1.0.0")
    }

    fun setEnvironment(key:String,value:Any)

    fun setEnvironmentMethod(method: InjectMethod)

    fun execute(rule:String): Node.Program
}
