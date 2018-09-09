package meituan.dong.jvm.natives;

import meituan.dong.jvm.lang.JvmField;
import meituan.dong.jvm.opcode.JvmOpcodeObject;
import meituan.dong.jvm.runtime.Env;

import java.lang.reflect.Field;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年09月09日下午4:34
 * @Function : todo
 */
public class JvmNativeField implements JvmField {

    JvmNativeClass clazz;

    // 常量池中字段 地址
    Field filed;

    public JvmNativeField(JvmNativeClass nativeClass, Field field){
        this.clazz = nativeClass;
        this.filed = field;
    }

    @Override
    public Object get(Env env, Object thiz) throws IllegalAccessException {
        // 非基础类型，需要用JvmNativeObject包装
        return JvmNativeObject.wrap(filed.get(thiz), filed.getType(), clazz.getClassLoader());
    }

    /**
     * 设置字段的值
     * @param env
     * @param thiz 类对象的 实力
     * @param value
     * @throws IllegalAccessException
     */

    @Override
    public void set(Env env, Object thiz,  Object value) throws IllegalAccessException {
        // 去掉JvmNativeObject包装
        filed.set(thiz, JvmNativeObject.unwrap(value));
    }


    public static void main(String[] args) {

    }

}
