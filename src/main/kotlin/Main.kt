import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.bridge.FunctionCallBridge2
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.v3.R3Engine

//todo task:增加取反操作符 fixbug:修复无法数学符号后面的表达式无法被完整识别的问题,修复循环中使用break会导致结果丢失的情况,String,Float,Long,Boolean 现在修改为值传递，其他的都是引用传递,修复调用其他方法会触发类的构造方法的bug
fun main(args: Array<String>) {
    testParse()
}

private fun testParse() {
    val pp2 = """
       def da = DA(199)
       
       def main(){
            def array = 0->5
            def start = da.v
            println(da.v)
            println("asda")
            loop array to index{
                da.increase()
                println(da.v)
            }
            println("start")
            println(start)
            call((a)->{
                return "inner code"
            })
            return da.result()
       }
    """

    val pp3 = """
       def class DA(value){
            def v = 0
            
            DA(c){
                v = c
            }
            
            def increase(){
                v++
            }
            
            def result(){
                return v
            }
       }
    """
    val visitor = R3Engine()
    visitor.setEnvironmentMethod(InjectMethod("call",A::class.java.getMethod("call",FunctionCallBridge1::class.java),A()))
    println(visitor.execute(pp2,pp3).result?.result)
}


class A{

    fun call(functionCallBridge1: FunctionCallBridge1){
        functionCallBridge1.call("aa").let {
            println("fromCode:" + it)
        }
    }
}