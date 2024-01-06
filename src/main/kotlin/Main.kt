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


//todo 新增：lambda表达式，参数可以传递lambda表达式，变量可以定义为lambda表达式
fun main(args: Array<String>) {
    testParse()
}

private fun testParse() {
    val program = """
        def class Context(){
            def a = true
        
            def isDebug(c){
                return c(a)
            }
        }
        
        def context = Context()
        println(toString(context))
        
        def result = if(context.isDebug((debug)->{
            return debug
        })){
            return "debugMode"
        }else{
            return "releaseMode"
        }
        //fgfg
        
        println(result)
        println(currentThread())
        @MAX_INT
        c()
        def c(){
            println(@MAX_INT)
            println(@MIN_INT)
        }
    """
    val visitor = R3Engine()

    runBlocking {
        visitor.execute(program).let {
            println(it.result)
        }
    }
}

