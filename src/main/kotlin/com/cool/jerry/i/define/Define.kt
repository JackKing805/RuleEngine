package com.cool.jerry.i.define

sealed class Define{
    class IdDefine(val name: String) : Define()

    class RefIdDefine(val refName: String) : Define()
}