```jq
    val c = 1 //定义整型
    val c = 1.1//定义浮点型
    val c = "asda"//定义字符
    val c = false   //定义布尔值
    val c = @a //取环境变量
    
    @a.getName()//调用方法
    @a.a //取属性
    @a.a.getName() //属性调用方法
    
    fun a(aa,dd,bb){ //定义方法
        
    }
    
    a(1,2,3) //调用定义的方法
    
    //数学运算
    1+1
    “1”+1 + 1.1
    1 * “123”
    1-1
    2.2
    
    @a.a = 1231//属性赋值
```

# antlr 学习 [https://zhuanlan.zhihu.com/p/347329881]
# antlr 学习2 [https://daichangya.github.io/antlr4-doc/#/?id=%e5%be%ae%e4%bf%a1%e5%85%ac%e4%bc%97%e5%8f%b7]



# VERSION 3

```code
    def a = 1
    def a = [1,2,3]
    
    def class C(cc,cc1){
        def a = 1
        def a = [1,2,3]
        
        C(cc,cc1){
        
        }
        
        def fun View(a){
            if(a==1){
                return 1
            }else{
                
            }
        
            return a
        }
    }
    
    
    def array = 0->5
    
    loop array to item{
        log(item)
    }
    
    loop{
    
    }
    
    watch{
    
    }error(e){
        
    }
    
    a+=2
    def c = 1+1+(2-6)
    
    sleep(1000)
    
    
    def time = currentTimestamp()
    log(time)
    
    def array2 = [1,2,3]
    def map = { 1:1,"sd":"asd"}
    
    map["sd"]
    array2[1]
    
```