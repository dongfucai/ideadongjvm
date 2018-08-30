package meituan.dong.jvm.opcode;

import com.sun.tools.classfile.ConstantPoolException;
import com.sun.tools.classfile.Field;
import meituan.dong.jvm.lang.JvmField;
import meituan.dong.jvm.runtime.Env;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午6:25
 * @Function :  new的对象的字段
 */
public class JvmOpcodeObjectField implements JvmField {
    // class 对象
    private final JvmOpcodeClass clazz;
    // 常量池中字段 地址
    private final Field field;

    //对象 字段的名称
    private final String fieldName;



    public JvmOpcodeObjectField(JvmOpcodeClass clazz, Field field) throws ConstantPoolException {
        this.clazz = clazz;
        this.field = field;
        this.fieldName = field.getName(clazz.getClassFile().constant_pool);
    }

    /**
     * c
     * @param env
     * @param thiz  JvmOpcodeObject
     * @param value
     * @throws IllegalAccessException
     */
    @Override
    public void set(Env env, Object thiz, Object value) throws IllegalAccessException{
        assert thiz instanceof JvmOpcodeObject;
        ((JvmOpcodeObject)thiz).setField(fieldName,value);
    }

    /**
     *
     * @param env
     * @param thiz 实力
     * @return
     * @throws IllegalAccessException
     */
    @Override
    public Object get(Env env, Object thiz) throws IllegalAccessException {
        assert thiz instanceof JvmOpcodeObject;
        return ((JvmOpcodeObject)thiz).getField(fieldName);
    }

    public static void main(String[] args) {

    }

}
