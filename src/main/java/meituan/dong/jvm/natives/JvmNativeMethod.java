package meituan.dong.jvm.natives;

import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmMethod;
import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.StackFrame;

import java.lang.reflect.Method;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年09月09日下午4:58
 * @Function : todo
 */
public class JvmNativeMethod implements JvmMethod {

    private final JvmClass clazz;
    private final Method method;
    private final String name;

    public JvmNativeMethod(JvmClass clazz, Method method){
        this.clazz = clazz;
        this.method = method;
        this.name = method.getName();
    }

    @Override
    public void call(Env env, Object thiz, Object... args) throws Exception {
        assert thiz instanceof JvmNativeObject;
        StackFrame frame = env.getStack().newFrame(clazz, this);

        Object object = ((JvmNativeObject) thiz).getNativeObject();
        Object res = method.invoke(object, JvmNativeObject.multiUnwrap(args));
        // 非基础类型，需要用JvmNativeObject包装
        res = JvmNativeObject.wrap(res, method.getReturnType(), clazz.getClassLoader());
        //将返回值推入调用者的操作数栈
        frame.setReturn(res, method.getReturnType().getName());
    }
    @Override
    public int getParameterCount() {
        return method.getParameterCount();
    }

    @Override
    public String getName() {
        return name;
    }

    public Method getNativeMethod() {
        return method;
    }

    public static void main(String[] args) {

    }

}
