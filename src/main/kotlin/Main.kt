import com.cool.jerry.bridge.FunctionCallBridge1
import com.cool.jerry.model.InjectMethod
import com.cool.jerry.v3.R3Engine

//todo task: ,fixbug:修复lambda无法直接调用的问题
fun main(args: Array<String>) {
    testParse()
}
private fun testParse() {
    val p1 = """
        def main(){
            println("start Loop:"+@a.a)
            def readResult = {}
            readResult = async{
                print("waitInput:")
                def read = readLine()
                println("input:"+read)
                if(read!=""){
                    breakLoop()
                }
                return {"input":read}
            }
            waitLoop()
            @a.a = readResult["input"]
            println("end Loop:"+@a.a)
            return Result().success(@a.a*8)
        }
    """

    val p2 = """
       def isLoop = true
       
       def waitLoop(){
            loop isLoop{
            
            }
       }
       
       def breakLoop(){
            isLoop = false                   
       }
       
       def class Result(){
            def code = 0
            def msg = ""
            
            def success(msg2){
                msg = msg2
                code = 200
                return str()
            }             
                  
            def error2(msg2){
                msg = msg2
                code = -404
                return str()
            }  
                
            def str(){
                return "code:"+code + ",msg:"+msg
            }
       }
    """
    val visitor = R3Engine()
    visitor.setEnvironment("a",A())
    visitor.setEnvironmentMethod(InjectMethod("call",A::class.java.getMethod("call",FunctionCallBridge1::class.java),A()))
    println(visitor.execute(p1,p2).result?.result)
}


class A{
    val a = "Ads"

    fun call(functionCallBridge1: FunctionCallBridge1){
        functionCallBridge1.call("aa").let {
            println("fromCode:" + it)
        }
    }
}