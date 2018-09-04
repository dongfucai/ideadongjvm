package meituan.dong.jvm.opcode;

import com.sun.org.apache.bcel.internal.Constants;
import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.StackFrame;

import java.util.HashMap;
import java.util.Map;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年09月02日上午8:58
 * @Function : todo
 */
public enum  OpcodeRout{

    /**
     * 将第0个引用类型局部变量推送至栈顶
     */
    ALOAD_0(Constants.ALOAD_0){
        @Override
        public void invoke(Env env, StackFrame frame, byte[] operands) throws Exception {
            Object object = frame.getLocalVariables().get(0);
            frame.getOperandStack().push(object, 1);
        }
    },


    private static Map<Short, OpcodeRout> codeMapping =  new HashMap<>();
    private final short code;




    OpcodeRout(short code){
        this.code = code;
    }

    public abstract void invoke(Env env, StackFrame frame, byte[] operands) throws Exception;

    public short getCode() {
        return code;
    }

    public static OpcodeRout valueOf(short code){
        OpcodeRout op = codeMapping.get(code);
        if (op == null){
            throw new InternalError("The opcode "+Constants.OPCODE_NAMES[code]+" Not Impl");
        }
        return op;
    }


    static {
        for (OpcodeRout op : values()){
            codeMapping.put(op.getCode(), op);
        }
    }

}
