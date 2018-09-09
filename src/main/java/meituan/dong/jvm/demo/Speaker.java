package meituan.dong.jvm.demo;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年09月10日上午7:11
 * @Function : todo
 */
public class Speaker implements SpeakerInterface {

    private String hello = "";

    Speaker(String hello){
        this.hello = hello;
    }

    public void helloTo(String somebody) {
        System.out.println(this.hello +" "+ somebody);
    }


}
