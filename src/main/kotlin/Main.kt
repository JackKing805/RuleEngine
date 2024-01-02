import com.cool.jerry.v2.rt_engine.RtRuleEngine
import com.cool.jerry.v3.R3Engine
import com.cool.jerry.v3.R3Node
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


//版本2测试代码
//fun main(args: Array<String>) {
//    val program = """
//       val c = @a.a.c
//       log(c)
//    """.trimIndent()
//
//    val visitor =  RtRuleEngine()
//    visitor.setEnvironment("a",A())
//    visitor.setEnvironment("c",C())
//    visitor.execute(program)
//}
//
//class C{
//    val c = "ADWEE"
//
//    fun getCName() = "CName"
//}
//
//class A{
//    var a = C()
//
//    fun printC(c:C){
//        println(c.getCName() + ",a:$a")
//    }
//
//    fun isDebug():Boolean{
//        return true
//    }
//}
//


fun main(args: Array<String>) {
    testParse()
}


private fun testParse() {
    val program = """
        def class A(){
            const def name = "a"
       
        
            def printA(){
                println("i'm "+name*3)
            }
        }
        
        def class B(){
            def  printB(){
                return @a+9*8
            }
        }
        
        def c = A()
        c.printA()

        
        println(B().printB() + 8)
        @b.printA()
       
        println(c.name)
        
        @b.getB().printB()
    """
    val visitor = R3Engine()
    visitor.setEnvironment("a", 4)
    visitor.setEnvironment("b", A())
    runBlocking {
        visitor.execute(program).let {
//            println(it)
        }
    }
//    println(result)
}

class A{
    fun printA(){
        println("fuck a")
    }
    fun getB() = B()
}

class B(){
    fun printB(){
        println("fuck B")
    }
}