package meituan.dong.jvm.opcode;

import com.sun.org.apache.bcel.internal.Constants;
import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.StackFrame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月30日下午12:51
 * @Function : todo
 */
public class BytecodeInterpreter {



    /**
     * 解析字节码 找到字节码 对应的 栈帧 执行过程
     * @param codes
     * @return
     */
    public static OpcodeInvoker[] parseCodes(byte[] codes){
        ArrayList<OpcodeInvoker> opcodes = new ArrayList<>();
        for(int i=0; i<codes.length; i++){
            short code = (short)(0xff&codes[i]);
            final OpcodeRout route = OpcodeRout.valueOf(code);
            short noOfOperands = Constants.NO_OF_OPERANDS[code];
            byte[] operands = Arrays.copyOfRange(codes, i + 1, i + 1 + noOfOperands);
            opcodes.add(new OpcodeInvoker() {   //  自己可以想象 new了一个实现的类 真正函数调用的时候，才会调用ivoke
                @Override
                public void invoke(Env env, StackFrame frame) throws Exception {
                    route.invoke(env, frame, operands);
                }
                @Override
                public String toString() {
                    return route.name();
                }
            });
            i += noOfOperands;
        }
        return Arrays.copyOf(opcodes.toArray(), opcodes.size(), OpcodeInvoker[].class);
    }


    public static void main(String[] args) {

    }

}
