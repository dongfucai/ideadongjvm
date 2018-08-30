package meituan.dong.jvm.opcode;

import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.StackFrame;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月29日上午10:31
 * @Function : todo
 */
public interface OpcodeInvoker {

    void invoke(Env env, StackFrame frame) throws Exception;

}
