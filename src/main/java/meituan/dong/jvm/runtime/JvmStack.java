package meituan.dong.jvm.runtime;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年09月05日上午8:16
 * @Function :
 */

import com.sun.tools.classfile.ConstantPool;
import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmMethod;
import meituan.dong.jvm.opcode.OpcodeInvoker;

/**
 * 虚拟机栈
 * 每个虚拟机线程持有一个独立的栈
 */

public class JvmStack {

    /**
     * 多个栈帧
     */
    private SlotsStack<StackFrame> frames = new SlotsStack<>(1024);

    private boolean running = false;


    public StackFrame newFrame(JvmClass clazz, JvmMethod method){
        StackFrame stackFrame = new StackFrame(clazz, method, null, null, 0,0);
        frames.push(stackFrame, 1);
        return stackFrame;
    }

    public StackFrame newFrame(JvmClass clazz, JvmMethod method, ConstantPool constantPool,
                               OpcodeInvoker[] opcodeInvokers, int variables, int stackSize){
        StackFrame stackFrame = new StackFrame(clazz, method, constantPool, opcodeInvokers, variables, stackSize);
        frames.push(stackFrame, 1);
        return stackFrame;
    }

    public StackFrame currentFrame(){
        return frames.pick();
    }
    public StackFrame popFrame(){
        return frames.pop();
    }
    public boolean isRunning(){
        return running;
    }
    public void setRunning(boolean running){
        this.running = running;
    }

    public static void main(String[] args) {

    }

}
