import com.cool.jerry.bridge.FunctionCallBridge0
import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2
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


//todo 新增 loop {} 无限循环语法,watch{}error(e){},RangeValue ,fixbug:修复 if then,if else 无法使用continue的问题, 修复return 无效的问题,修复外部类无法调用回调函数,
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
       def c = 0->@MAX_INT
       loop c to d{
        println(d)
        sleep(500)
       }
    """
    val visitor = R3Engine()
    visitor.execute(pp2).let {
        println(it.result.result)
    }
}
