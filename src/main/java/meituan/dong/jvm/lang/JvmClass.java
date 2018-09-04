package meituan.dong.jvm.lang;

import meituan.dong.jvm.runtime.Env;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
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


    /**
     * 获取方法
     * @param name 方法名，如`main`
     * @param desc 方法类型描述，如`([Ljava/lang/String;)V`
     * @return
     * @throws NoSuchMethodException
     */
    public JvmMethod getMethod(String name, String desc) throws NoSuchMethodException;
}
