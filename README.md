

ideadongjvm


**环境：idea + jdk8**

代码中写了一个helloworld 

public class HelloWorld {

    public static void main(String[] args) {
        int i = 2;
        System.out.println(i);
       // System.out.println("welcome dongfucai hello world");
    }

}


运行JJVM：结果如下


> meituan/dong/jvm/HelloWorld.main@0:ICONST_2
> meituan/dong/jvm/HelloWorld.main@1:ISTORE_1
> meituan/dong/jvm/HelloWorld.main@2:GETSTATIC
> meituan/dong/jvm/HelloWorld.main@3:ILOAD_1
> meituan/dong/jvm/HelloWorld.main@4:INVOKEVIRTUAL
2
> meituan/dong/jvm/HelloWorld.main@5:RETURN