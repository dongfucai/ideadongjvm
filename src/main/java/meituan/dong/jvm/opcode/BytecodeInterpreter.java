package meituan.dong.jvm.opcode;

import com.sun.org.apache.bcel.internal.Constants;
import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.JvmStack;
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
        for(int i=0; i<codes.length; i++){  // for 循环已经把当前栈帧的操作码 填充好了
            short code = (short)(0xff&codes[i]);
            final OpcodeRout route = OpcodeRout.valueOf(code);
            // 字节码之后的字节数
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

    // 执行字节码的过程
    public static void run(Env env) throws Exception{
        // 只需要在最外层调用执行栈上的操作
        if (env.getStack().isRunning()){
            return ;
        }
        JvmStack stack = env.getStack();
        stack.setRunning(true);
        StackFrame frame;

        while ((frame = stack.currentFrame()) != null){
             // 如果栈帧被设置为返回， 则将返回值推入 上一个栈帧的操作数返回值
            if (frame.isReturned()){
                StackFrame oldFrame = frame;
                stack.popFrame();
                frame = stack.currentFrame();
                if (frame != null && !"void".equals(oldFrame.getReturnType())){
                    frame.getOperandStack().push(oldFrame.getReturn());
                }
                continue;
            }

            OpcodeInvoker[] codes = frame.getOpcodes();
            int pc = frame.increasePC();

            StringBuilder sb = new StringBuilder();
            sb.append("> ");
            sb.append(frame.getCurrentClass().getName());
            sb.append(".");
            sb.append(frame.getCurrentMethod().getName());
            sb.append("@");
            sb.append(pc);
            sb.append(":");
            sb.append(codes[pc]);
            System.out.println(sb);

            codes[pc].invoke(env, frame);
        }
    }


    public static void main(String[] args) {

    }

}
