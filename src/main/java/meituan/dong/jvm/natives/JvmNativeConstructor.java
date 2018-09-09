package meituan.dong.jvm.natives;

import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmMethod;
import meituan.dong.jvm.runtime.Env;
import meituan.dong.jvm.runtime.StackFrame;

import java.lang.reflect.Constructor;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年09月09日下午5:00
 * @Function : todo
 */
public class JvmNativeConstructor implements JvmMethod {



    private final Constructor constructor;
    private final JvmClass clazz;

    public JvmNativeConstructor(JvmClass clazz, Constructor constructor){
        this.clazz = clazz;
        this.constructor = constructor;
    }
    @Override
    public void call(Env env, Object thiz, Object... args) throws Exception {
        assert (thiz instanceof JvmNativeObject);

        StackFrame frame = env.getStack().newFrame(clazz, this);
        Object object = constructor.newInstance(JvmNativeObject.multiUnwrap(args));
        ((JvmNativeObject) thiz).setNativeObject(object);
        //将返回值推入调用者的操作数栈
        frame.setReturn(null, "void");
    }

    @Override
    public int getParameterCount() {
        return constructor.getParameterCount();
    }

    @Override
    public String getName() {
        return "<init>";
    }


    public static void main(String[] args) {

    }

}
