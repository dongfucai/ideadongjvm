package meituan.dong.jvm.natives;

import jdk.internal.org.objectweb.asm.Type;
import meituan.dong.jvm.lang.*;
import meituan.dong.jvm.runtime.Env;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午5:31
 * @Function : native 类的包装
 */
public class JvmNativeClass implements JvmClass {

    private final Class nativeClass;
    private final String className;
    private final JvmClassLoader classLoader;
    private final HashMap<Map.Entry<String, String>, JvmMethod> methods = new HashMap<>();

    public JvmNativeClass(JvmClassLoader classLoader, Class nativeClass){
        this.nativeClass = nativeClass;
        this.classLoader = classLoader;
        this.className = nativeClass.getName();
        //普通方法
        for (Method method : nativeClass.getMethods()) {
            methods.put(
                    new AbstractMap.SimpleEntry<>(method.getName(), Type.getMethodDescriptor(method)),
                    new JvmNativeMethod(this, method)
            );
        }
        //构造方法
        for( Constructor constructor : nativeClass.getConstructors()){
            methods.put(
                    new AbstractMap.SimpleEntry<>("<init>", Type.getConstructorDescriptor(constructor)),
                    new JvmNativeConstructor(this, constructor)
            );
        }
    }

    @Override
    public JvmObject newInstance(Env env) throws InstantiationException, IllegalAccessException {
        return new JvmNativeObject(this);
    }

    @Override
    public JvmMethod getMethod(String name, String desc) throws NoSuchMethodException {
        JvmMethod found = methods.get(new AbstractMap.SimpleEntry<>(name, desc));
        if(found == null){
            throw new NoSuchMethodException(name+":"+desc+" not exist");
        }
        return found;
    }

    @Override
    public boolean hasMethod(String name, String desc) {
        JvmMethod found = methods.get(new AbstractMap.SimpleEntry<>(name, desc));
        return found != null;
    }

    @Override
    public JvmField getField(String name) throws NoSuchFieldException, IllegalAccessException {
        return new JvmNativeField(this, nativeClass.getField(name));
    }

    @Override
    public JvmClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public JvmClass getSuperClass() throws ClassNotFoundException {
        return classLoader.loadClass(nativeClass.getSuperclass().getName());
    }

    @Override
    public String getName() {
        return className;
    }

    public Class getNativeClass() {
        return nativeClass;
    }


}
