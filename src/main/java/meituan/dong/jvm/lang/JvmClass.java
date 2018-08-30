package meituan.dong.jvm.lang;

import meituan.dong.jvm.runtime.Env;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午4:52
 * @Function : todo
 */
public interface JvmClass {


    /**
     * 分配实例的内存空间，但不执行实例的构造函数
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public JvmObject newInstance(Env env) throws InstantiationException, IllegalAccessException;


    /**
     * 返回父类
     */
    public JvmClass getSuperClass() throws ClassNotFoundException;


    /**
     *  返回类名字
     */
    public String getName();
}
