package meituan.dong.jvm;


import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年09月09日下午5:05
 * @Function : todo
 */
public class JJVM {

    public static void main(String[] args) {
        String []strings = {"/Users/dongfucai/ideadongjvm/target/classes","meituan/dong/jvm/HelloWorld"};
       // String []strings = {"/Users/dongfucai/ideadongjvm/target/classes","meituan/dong/jvm/demo/Main"};
        VirtualMachine vm = new VirtualMachine(Paths.get(strings[0]), strings[1]);
        try {
            strings = Arrays.copyOfRange(strings, 2, strings.length);
            vm.run(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
