package meituan.dong.jvm;

import meituan.dong.jvm.lang.JvmClass;
import meituan.dong.jvm.lang.JvmClassLoader;
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

        String fileName = className + "/" + className.replace(".","/")+".class";

        Path path = Paths.get(fileName);

        if (Files.exists(path)){ // 加载自己所写的类
            return JvmOpcodeClass.read(this, path);
        }else {

        }

    }


}
