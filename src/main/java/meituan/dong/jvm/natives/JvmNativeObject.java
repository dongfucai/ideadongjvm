package meituan.dong.jvm.natives;

import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmClassLoader;
import meituan.dong.jvm.lang.JvmObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年09月09日下午4:01
 * @Function : todo
 */
public class JvmNativeObject implements JvmObject {

    private Object object;
    private final JvmNativeClass clazz; // class 对象

    public JvmNativeObject(JvmNativeClass clazz){
        this.clazz = clazz;
    }
    public Object getNativeObject(){
        return object;
    }

    public void setNativeObject(Object object){
        this.object = object;
    }
    @Override
    public JvmObject getSuper() {
        throw new NotImplementedException();
    }
    @Override
    public JvmClass getClazz(){
        return clazz;
    }

    /**
     *  包装 JvmNativeObject
     * @param object
     * @param clazz
     * @param classLoader
     * @return
     */
    static public Object wrap(Object object, Class clazz, JvmClassLoader classLoader){
        if(object == null){
            return null;
        }
        // 非基础类型，需要用JvmNativeObject包装
        String primitiveTypes[] = {
                byte.class.getName(),
                short.class.getName(),
                int.class.getName(),
                long.class.getName(),
                char.class.getName(),
                float.class.getName(),
                double.class.getName(),
                boolean.class.getName()
        };
        Arrays.sort(primitiveTypes);
        if(Arrays.binarySearch(primitiveTypes, clazz.getName()) <0 ){
            JvmNativeObject wrap = new JvmNativeObject(new JvmNativeClass(classLoader, clazz));
            wrap.setNativeObject(object);
            return wrap;
        }
        return object;
    }

    /**
     *  解包 JvmNativeObject
     * @param object
     * @return
     */
    static public Object unwrap(Object object){
        if(object instanceof JvmNativeObject){
            return ((JvmNativeObject) object).getNativeObject();
        }
        return object;
    }
    static public Object[] multiUnwrap(Object[] objects){
        Object[] res = new Object[objects.length];
        for (int i=0; i<objects.length; i++) {
            res[i] = unwrap(objects[i]);
        }
        return res;
    }



    public static void main(String[] args) {

    }

}
