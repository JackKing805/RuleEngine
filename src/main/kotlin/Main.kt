import com.cool.jerry.i.InjectMethod
import com.cool.jerry.rt_engine.RtRuleEngine

fun main(args: Array<String>) {
    val program = """
        val c = 123123
        2131 + c
        "asdad"-"as"
        
        5*"asdas" * 2
        
        @a.getAName(1231+8788)
        
        print("asdasdasd")
    """.trimIndent()

    val visitor =  RtRuleEngine()
    visitor.setEnvironment("a",A())
    visitor.setEnvironmentMethod("print", InjectMethod(A(),A::class.java.getMethod("print",String::class.java)))
    println(visitor.execute(program))
}

class A{
    fun getAName(name:Long):String{
        return "i'm $name"
    }

    fun print(str:String){
        println(str)
    }
}