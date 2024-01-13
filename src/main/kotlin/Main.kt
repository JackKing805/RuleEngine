import com.cool.jerry.bridge.FunctionCallBridge0
import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.v3.R3Engine
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


//todo task: 增加main方法入口,应用必须有main方法才会执行,增加多文件执行,增加map定义，map访问,fixbug:修复方法,变量和类可以重复定义的bug
fun main(args: Array<String>) {
    testParse()
}

private fun testParse() {
    val program = """
//        def class Context(){
//            def a = true
//        
//            def isDebug(c){
//                return c(a)
//            }
//        }
//        
//        def changeThread(IO,run){
//            
//        }
//        
//        def context = Context()
//        println(toString(context))
//        
//        def result = if(context.isDebug((debug)->{
//            return debug
//        })){
//            return "debugMode"
//        }else{
//            return "releaseMode"
//        }
//        //fgfg
//        println(result)
//        println(currentThread())
//        @MAX_INT
//        c()
//        def c(){
//            println(@MAX_INT)
//            println(@MIN_INT)
//        }
//        1
    """

    val pp2 = """
       def mainName = "mainName"     
     
       def map = { mainName:"cc" }
       def array = [1]
       def main(){
            println(@C.b["a"])
            println(map[mainName])
            println(array[0])
       }
    """

    val pp3 = """
       def mainName1 = "mainName1"
    """
    val visitor = R3Engine()
    visitor.setEnvironment("C", C())
    visitor.setEnvironmentMethod(
        InjectMethod(
            "call",
            C::class.java.getMethod(
                "callBack",
                Int::class.java,
                FunctionCallBridge2::class.java
            ),
            C()
        )
    )
    println(visitor.execute(pp2,pp3).result?.result)
}

open class A {
    open val a = "A.a"
}

class C : A() {
    val c = a
    val b = mapOf("a" to 123123)

    fun callBack(c: Int, functionCallBridge0: FunctionCallBridge2): String {
        functionCallBridge0.call(c, 2)
        return "false"
    }
}
