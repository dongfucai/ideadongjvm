package meituan.dong.jvm;

import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmClassLoader;
import meituan.dong.jvm.lang.JvmMethod;
import meituan.dong.jvm.runtime.Env;

import java.nio.file.Path;
import java.util.Hashtable;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年09月04日下午9:55
 * @Function : todo
 */
public class VirtualMachine {


    /**
     *  初始化main类的名称
     */
    private String initialClass;

    /**
     * 类加载器
     */
    private JvmClassLoader classLoader;

    /**
     *  方法区(method area)
     *  存储运行期类的信息
     *  key 类的名称
     *  jvmClass 类的信息
     */
    private Hashtable<String, JvmClass> methodArea = new Hashtable<>();


    public VirtualMachine(Path path, String initialClass){
        this.classLoader = new JvmDefaultClassLoader(path);
        this.initialClass = initialClass;
    }


    /**
     *  运行虚拟机
     * @param args
     */
    public void run(String []args) throws Exception{
        Env env = new Env(this);
        JvmClass clazz = methodArea.get(initialClass);

        // 需要找到入口的方法  根据名称和描述
        JvmMethod method = clazz.getMethod(
                "main",
                "([Ljava/lang/String;)V");

        // 执行入口的方法
        method.call(env, null, new Object[]{args});
    }

    public JvmClass getClass(String className) throws ClassNotFoundException{
        JvmClass found = methodArea.get(className);
        if (found == null){
            found = classLoader.loadClass(className);
            methodArea.put(className, found);
        }
        return found;
    }

    public static void main(String[] args) {

    }

}
