package meituan.dong.jvm.lang;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午4:51
 * @Function : todo
 */
public interface JvmClassLoader {

    JvmClass loadClass(String className) throws ClassNotFoundException;

}
