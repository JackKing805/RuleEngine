package com.cool.jerry.rt_engine.utils

/**
 * 内嵌方法
 */
object Embed {
    @JvmStatic
    fun log(msg:String){
        println(msg)
    }

    @JvmStatic
    fun log(msg:Int){
        log(msg.toString())
    }

    @JvmStatic
    fun log(msg:Long){
        log(msg.toString())
    }

    @JvmStatic
    fun log(msg:Boolean){
        log(msg.toString())
    }

    @JvmStatic
    fun log(msg:Float){
        log(msg.toString())
    }

    @JvmStatic
    fun log(msg:Double){
        log(msg.toString())
    }

    @JvmStatic
    fun currentTimestamp():Long{
        return System.currentTimeMillis()
    }
}