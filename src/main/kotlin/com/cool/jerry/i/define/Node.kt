package com.cool.jerry.i.define

sealed class Node{

     sealed class Statement: Node(){
        class AssignmentStatement(val id: Define, val value: Node): Statement()

    }

    sealed class Expression: Node(){

    }
}