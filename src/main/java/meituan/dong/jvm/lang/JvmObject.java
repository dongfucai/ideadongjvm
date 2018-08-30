package meituan.dong.jvm.lang;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午4:56
 * @Function : todo
 */
public interface JvmObject {

    /**
     * 获取父亲类的实力
     * @return
     */
    JvmObject getSuper();


    /**
     * 获取实力的类
     * @return
     */
    JvmClass getClazz();
}
