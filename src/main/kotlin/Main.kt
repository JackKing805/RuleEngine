import com.cool.jerry.v2.rt_engine.RtRuleEngine
import com.cool.jerry.v3.R3Engine
import com.cool.jerry.v3.R3Node


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
//    val program = """
//          def class dd(asd,wqe){
//               dd(asd,wqe){
//
//               }
//
//               def asd(asdas,asda){
//                    a.wd = 123
//                    if(1==1){
//                        def c= 123
//                    }else{
//                        a
//                    }
//
//                    loop a to c {
//
//                        continue
//                    }
//
//                    call()
//                    a.call()
//
//                    def array = [1,123]
//                    return array[0]
//                }
//          }
//
//        def a = dd()
//
//        def c = false
//        false
//        1
//        call()
//        a.call()
//    """.trimIndent()

    val program = """
        def value = 0
        def class A(a){
          def c(){
            def value2 = 0
            loop 0->2 to b {
              sleep(100)
                
                log("倒计时：" + b)
                a += (b*2)
            }
            log(a)
          }
        }
        
        def a = A(value)
        a.c()
        log(value)
    """
    val visitor = R3Engine()
    visitor.setEnvironment("a", 4)
    val result = visitor.execute(program)
//    println(result)
}