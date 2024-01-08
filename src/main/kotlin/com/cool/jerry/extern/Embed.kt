package com.cool.jerry.extern

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.awt.KeyEventDispatcher
import java.util.UUID
import kotlin.coroutines.CoroutineContext

/**
 * 内嵌方法
 */
object Embed {
    @JvmStatic
    fun logln(msg:String){
        println(msg)
    }

    @JvmStatic
    fun logln(msg:Int){
        logln(msg.toString())
    }

    @JvmStatic
    fun logln(msg:Long){
        logln(msg.toString())
    }

    @JvmStatic
    fun logln(msg:Boolean){
        logln(msg.toString())
    }

    @JvmStatic
    fun logln(msg:Float){
        logln(msg.toString())
    }

    @JvmStatic
    fun logln(msg:Double){
        logln(msg.toString())
    }

    @JvmStatic
    fun log(msg:String){
        print(msg)
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
    fun currentTime():Long{
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

    @JvmStatic
    fun sleep(timeMill:Long){
        Thread.sleep(timeMill)
    }

    @JvmStatic
    fun currentThread():String{
        return Thread.currentThread().toString()
    }
}