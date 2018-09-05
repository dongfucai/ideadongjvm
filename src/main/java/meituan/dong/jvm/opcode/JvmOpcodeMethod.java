package meituan.dong.jvm.opcode;

import com.sun.tools.classfile.Code_attribute;
import com.sun.tools.classfile.ConstantPoolException;
import com.sun.tools.classfile.Descriptor;
import com.sun.tools.classfile.Method;
import meituan.dong.jvm.lang.JvmMethod;
import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.StackFrame;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年08月28日下午9:28
 * @Function : 字节码中的方法
 */

/**
 * 字节码中的方法 （不同于native的方法）
 */
public class JvmOpcodeMethod implements JvmMethod {

    // 类对象
    private final JvmOpcodeClass clazz;

    // class 中的method 地址
    private final Method method;

    // 这个函数 所有字节码的反射执行
    private final OpcodeInvoker[] opcodes;

    // 方法函数中code属性
    private final Code_attribute codeAttribute;

    // 方法的名称
    private final String name;

    // 方法的参数个数
    private final int parameterCount;

    public JvmOpcodeMethod(JvmOpcodeClass clazz, Method method) throws ConstantPoolException, Descriptor.InvalidDescriptor{
        this.clazz = clazz;
        this.method = method;
        this.name = method.getName(clazz.getClassFile().constant_pool);
        this.parameterCount = method.descriptor.getParameterCount(clazz.getClassFile().constant_pool);
        this.codeAttribute = (Code_attribute)method.attributes.get("Code");
        opcodes = BytecodeInterpreter.parseCodes(codeAttribute.code);
    }

    @Override
    public int getParameterCount(){
        return parameterCount;
    }
    @Override
    public String getName(){
        return name;
    }

    /**
     * 解析方法执行的字节码
     * @param env
     * @param thiz
     * @param args
     * @throws Exception
     */
    @Override
    void call(Env env, Object thiz, Object ...args) throws Exception{

        // 每次的方法调用都会产生一个新的栈帧，当前的方法返回后，将栈帧设置已经返回，ByteCodeInterpreter.run 会检查到返回后，将栈帧进行
        // 出帧栈，并将函数的返回值（如果有）押入上一个栈帧的操作数栈帧

        StackFrame frame = env.getStack().newFrame(  // 每次一个函数调用  都会产生一个 建立了一个新的栈帧
                clazz,
                this,
                clazz.getClassFile().constant_pool,
                opcodes,
                codeAttribute.max_locals,
                codeAttribute.max_stack);

    }


    public static void main(String[] args) {

    }

}
