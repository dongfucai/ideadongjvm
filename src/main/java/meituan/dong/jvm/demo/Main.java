package meituan.dong.jvm.demo;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年09月10日上午7:10
 * @Function : todo
 */
public class Main {

    private final static SpeakerInterface speaker = new Speaker("welcome ");

    public static void main(String[] args) {
        speaker.helloTo("dongfucai you secone jvm");
    }


}
