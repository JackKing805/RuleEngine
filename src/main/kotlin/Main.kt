import com.cool.jerry.v2.rt_engine.RtRuleEngine
import com.cool.jerry.v3.R3Engine


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
    val program = """
        def class asdan(asda,asda){
            def a = 123
            const def b = 456
            
            def asd(dsd,wqeq){
                def array = [1,2,3,4,5,6,7,8,9]
                def item = array[0]
                
                loop array to item {
                    break
                }
                return
            }
        }
        
        asdan(123,456)
        a.dwe(123,123)
    """.trimIndent()

    val visitor =  R3Engine()
    visitor.execute(program).let {
        println(it)
    }
}