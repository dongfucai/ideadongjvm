package meituan.dong.jvm.opcode;

import com.sun.tools.classfile.ConstantPoolException;
import com.sun.tools.classfile.Descriptor;
import com.sun.tools.classfile.Field;
import meituan.dong.jvm.lang.JvmField;
import meituan.dong.jvm.runtime.Env;


/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午6:06
 * @Function : 静态成员 字段
 */
public class JvmOpcodeStaticField implements JvmField {

    // 常量池中字段 地址
    private final Field field;
    // class 对象
    private final JvmOpcodeClass clazz;

    // 字段的值
    private Object value;

    // 字段描述的类型
    private final String type;

    public JvmOpcodeStaticField(JvmOpcodeClass clazz, Field field) throws Descriptor.InvalidDescriptor, ConstantPoolException {
        this.field = field;
        this.clazz = clazz;
        this.type = field.descriptor.getFieldType(this.clazz.getClassFile().constant_pool);
        // 初始化默认值
        switch (type){
            case "byte":
                value = (byte)0;
                break;
            case "char":
                value = '\u0000';
                break;
            case "double":
                value = 0.;
                break;
            case "float":
                value = 0.f;
                break;
            case "int":
                value = 0;
                break;
            case "long":
                value = 0L;
                break;
            case "short":
                value = (short)0;
                break;
            case "value":
                value = false;
                break;
            default:
                value = null;
                break;

        }

    }

    @Override
    public void set(Env env, Object thiz, Object value) throws IllegalAccessException{
        this.value = value;
    }

    @Override
    public Object get(Env env, Object thiz) throws IllegalAccessException{
        return value;
    }
    public static void main(String[] args) {

    }

}
