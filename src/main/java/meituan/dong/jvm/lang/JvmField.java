package meituan.dong.jvm.lang;

import meituan.dong.jvm.runtime.Env;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午4:55
 * @Function : todo
 */
public interface JvmField {

    /**
     * 设置字段的值
     * @param env
     * @param thiz 类对象的 实力
     * @param value
     * @throws IllegalAccessException
     */
    void set(Env env, Object thiz, Object value) throws IllegalAccessException;

    /**
     * 获得字段的值
     * @param env
     * @param thiz
     * @return
     * @throws IllegalAccessException
     */
    Object get(Env env, Object thiz) throws IllegalAccessException;


}
