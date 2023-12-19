import com.cool.jerry.i.InjectMethod
import com.cool.jerry.rt_engine.RtRuleEngine

fun main(args: Array<String>) {
    val program = """
        val c = 123123
        print("c is:" + c)
        c = 2131 + c
        print("c2 is:" + c)
        val ak47 = "asdad"-"as"
        
        
        val jackLove = 5*"asdas" * 2 
        
        print(ak47 + jackLove)
        print("asdasdasd2" + "M1128")
        print(1)
        
        c(c)
        
        @a.getCName("cccc").printD("DDD")
        
       
        
        fun c(aaaaa){
            val e = "cMethod"
            print("c:"+aaaaa + e)
            a()
        }
        
        fun a(){
            print("a execute")
        }
        
        if 1==2 {
            print("i'm 2")
        }else{
            print("i'm not 2")
        }
    """.trimIndent()

    val visitor =  RtRuleEngine()
    visitor.setEnvironment("a",A.getAName())
    visitor.setEnvironmentMethod(InjectMethod("print",A::class.java.getMethod("print",String::class.java)))
    visitor.setEnvironmentMethod(InjectMethod("print",A::class.java.getMethod("print",Int::class.java)))
    visitor.execute(program)
}

class D(val name:String){
    fun printD(c:String):String{
        println("name:${name}, printD:$c")
        return "I'm D"
    }
}

class C(val name:String){
    fun getCName(cName:String):D{
        return D(cName)
    }
}
object A{
    @JvmStatic
    fun getAName():C{
        return C("CCCCC")
    }

    @JvmStatic
    fun print(str:String){
        println(str)
    }

    @JvmStatic
    fun print(str:Int){
        println(str)
    }
}