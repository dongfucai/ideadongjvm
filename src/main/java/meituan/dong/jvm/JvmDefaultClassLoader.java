package meituan.dong.jvm;

import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmClassLoader;
import meituan.dong.jvm.natives.JvmNativeClass;
import meituan.dong.jvm.opcode.JvmOpcodeClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午5:17
 * @Function : todo
 */
public class JvmDefaultClassLoader implements JvmClassLoader {

    private Path classPath;

    public JvmDefaultClassLoader(Path classPath){
        this.classPath = classPath;
    }

    @Override
    public JvmClass loadClass(String className) throws ClassNotFoundException{

        String fileName = classPath + "/" + className.replace(".","/")+".class";

        Path path = Paths.get(fileName);
        //如果文件存在，加载文件字节码
        //否则尝试通过虚拟机宿主加载指定类，并将加载后的类当做 native 类

        if (Files.exists(path)){ // 加载自己所写的类
            return JvmOpcodeClass.read(this, path);
        }else {
            return new JvmNativeClass(this, Class.forName(className.replace("/",".")));

        }

    }


}
