import com.cool.jerry.rt_engine.RtRuleEngine

fun main(args: Array<String>) {
    val program = """
        val c = 123123
        2131 + c
        "asdad"-"as"
        
        5*"asdas" * 20
    """.trimIndent()

    val visitor =  RtRuleEngine()
    visitor.setEnvironment("a",A())
    println(visitor.execute(program))
}

class A{
    fun getAName():String{
        return "i'm a"
    }
}