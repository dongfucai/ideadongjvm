package meituan.dong.jvm.runtime;

import meituan.dong.jvm.VirtualMachine;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年08月28日下午6:02
 * @Function : todo
 */


/**
 * 线程上下文
 */
public class Env  {


    /**
     * 虚拟机的栈
     */
    private JvmStack jvmStack = new JvmStack();

    public JvmStack getStack(){
        return jvmStack;
    }


    /**
     * 当前虚拟机
     */
    private VirtualMachine vm;

    public Env(VirtualMachine vm){
        this.vm = vm;
    }

    public VirtualMachine getVm(){
        return vm;
    }
    public static void main(String[] args) {

    }

}
