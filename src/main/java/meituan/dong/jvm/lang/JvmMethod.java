package meituan.dong.jvm.lang;

import meituan.dong.jvm.runtime.Env;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午4:55
 * @Function : todo
 */
public interface JvmMethod {


    /**
     * 获取方法的参数的个数
     * @return
     */
    int getParameterCount();

    /**
     * 获取方法的名称
     * @return
     */
    String getName();

    /**
     * 执行对象或者类的方法
     * 方法调用是的时候，会产生一个新的帧栈，并押入当前的线程
     * 方法执行结束后，栈帧被退出，同时期返回值被推入上一个栈帧（当前方法的调用者）的操作数栈
     * @param env
     * @param thiz
     * @param args
     * @throws Exception
     */
    void call(Env env, Object thiz, Object ...args) throws Exception;

}
