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


//todo 新增：对错误代码报错,修复lambda表达式中不能定义变量的错误
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
        def callLambda(a){
            a(()->{
                return "from CallLambdaInner2222"
            })
        }
        
        callLambda((a)->{
           def c= a()
           println(toString(a()) + ",thread:" + currentThread())        
        })
           def c= "ADasdsa"
           println(c)
           c
    """
    val visitor = R3Engine()

    runBlocking {
        visitor.execute(this,pp2).let {
            println(it.result)
        }
    }
}

