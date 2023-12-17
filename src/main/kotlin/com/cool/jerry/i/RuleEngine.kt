package com.example.testsensor.rule

interface RuleEngine {
    fun engineInformation() : EngineInformation{
        return EngineInformation("1.0.0")
    }

    fun setEnvironment(key:String,value:Any)

    fun getEnvironment(key: String):Any

    fun execute(rule:String)
}
