package com.cool.jerry.v2.rt_engine.utils

import java.util.UUID

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

    @JvmStatic
    fun uuid():String{
        return UUID.randomUUID().toString()
    }

    @JvmStatic
    fun randomInt(signed:Boolean):Int{
        return if (signed){
            (Int.MIN_VALUE .. Int.MAX_VALUE).random()
        }else{
            (0 .. Int.MAX_VALUE).random()
        }
    }

    @JvmStatic
    fun toString(any:Any):String{
        if (any is String){
            return any
        }
        return any.toString()
    }
}