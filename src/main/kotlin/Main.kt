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


//todo 新增向kotlin传递回调函数的功能. bug:目前Range定义太大的话会将java虚拟机撑满
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
        def lll = ()->{
            return "i'm b"
        }
        
        println(lll())
    """
    val visitor = R3Engine()

    visitor.execute(pp2).let {
        println(it.result.result)
    }
}

