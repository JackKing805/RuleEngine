import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.v3.R3Engine

//todo task: 目前async里面的语句无法修改外部变量
fun main(args: Array<String>) {
    testParse()
}
private fun testParse() {
    val pp2 = """
       def main(){
            def isLoop = true
            def save = isLoop
            println("isLoop:"+isLoop)
            async{
                def c = true
                def saveC = c
                println("t c:"+c + ",saveC:"+saveC)
                sleep(3000)
                c = false
                isLoop = false
                println("isLoopT:"+isLoop)
                println("t c:"+c + ",saveC:"+saveC)
                return "a"
            }
            
            loop isLoop{
            
            }
            println("isLoopEnd:"+isLoop)
            println("save:"+save)
       }
       
    """
    val visitor = R3Engine()
    visitor.setEnvironmentMethod(InjectMethod("call",A::class.java.getMethod("call",FunctionCallBridge1::class.java),A()))
    println(visitor.execute(pp2).result?.result)
}


class A{

    fun call(functionCallBridge1: FunctionCallBridge1){
        functionCallBridge1.call("aa").let {
            println("fromCode:" + it)
        }
    }
}