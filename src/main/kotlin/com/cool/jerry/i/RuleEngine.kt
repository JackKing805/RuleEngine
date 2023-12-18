package com.cool.jerry.i

import com.cool.jerry.rt_engine.define.Node
import com.example.testsensor.rule.EngineInformation
import java.lang.reflect.Method

interface RuleEngine {
    fun engineInformation() : EngineInformation {
        return EngineInformation("1.0.0")
    }

    fun setEnvironment(key:String,value:Any)

    fun setEnvironmentMethod(key: String,method:InjectMethod)

    fun execute(rule:String):Node.Program
}
