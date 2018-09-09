package meituan.dong.jvm.opcode;

import com.sun.tools.classfile.*;
import meituan.dong.jvm.lang.*;
import meituan.dong.jvm.runtime.Env;

import java.io.IOException;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : dongfucai@meituan.com
 * @Creation Date : 2018年08月28日下午5:25
 * @Function :  字节码定义的java类
 */
public class JvmOpcodeClass implements JvmClass {

    /*
       加载的类文件
     */
    private final ClassFile classFile;
    private final String className;
    private final JvmClassLoader classLoader;

    // 方法的map
    private Map<Map.Entry<String, String>, JvmOpcodeMethod> methods = new HashMap<>();
    // 字段的map
    private Map<String, JvmField> fields = new HashMap<>();

    /**
     *  是否已经初始化
     */
    boolean inited = false;


    /**
     * 加载自己所写的类
     * @param classLoader
     * @param path
     * @return
     * @throws ClassNotFoundException
     */

    static public JvmOpcodeClass read(JvmClassLoader classLoader, Path path) throws ClassNotFoundException {
        try {
            return  new JvmOpcodeClass(classLoader, ClassFile.read(path));
        } catch (IOException e) {
            throw new ClassNotFoundException(e.toString());
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }


    private JvmOpcodeClass(JvmClassLoader classLoader, ClassFile classFile) throws ConstantPoolException, Descriptor.InvalidDescriptor {
        this.classFile = classFile;
        this.className = classFile.getName();
        this.classLoader = classLoader;

        for (Method method : classFile.methods){
            String name = method.getName(classFile.constant_pool);
            String desc = method.descriptor.getFieldType(classFile.constant_pool);
            methods.put(new AbstractMap.SimpleEntry<>(name,desc),new JvmOpcodeMethod(this,method));
        }
        //准备阶段
        prepare();
    }

    /**
     * 准备阶段（Preparation）
     * 分配静态变量，并初始化为默认值，但不会执行任何字节码，在初始化阶段（clinit) 会有显式的初始化器来初始化这些静态字段，所以准备阶段不做
     * 这些事情。
     * @see `http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-5.html#jvms-5.4.2`
     */
    private void prepare() throws ConstantPoolException, Descriptor.InvalidDescriptor {
        for(Field i : this.classFile.fields){
            if(i.access_flags.is(AccessFlags.ACC_STATIC)){
                fields.put(i.getName(classFile.constant_pool), new JvmOpcodeStaticField(this, i));
            }else{
                fields.put(i.getName(classFile.constant_pool), new JvmOpcodeObjectField(this, i));
            }
        }
    }

    public ClassFile getClassFile(){
        return classFile;
    }

    @Override
    public JvmClass getSuperClass() throws ClassNotFoundException{
        try {
            // 获取父亲类的名字 进行类加载
            return classLoader.loadClass(classFile.getSuperclassName());
        } catch (ConstantPoolException e) {
            throw new ClassNotFoundException(e.getMessage());
        }
    }


    @Override
    public String getName(){
        return className;
    }

    @Override
    public JvmObject newInstance(Env env) throws InstantiationException, IllegalAccessException{

        try {
            clinit(env); // clinit 静态的东西进行初始化
        } catch (Exception e) {
            throw new InstantiationException(e.getMessage());
        }
        return new JvmOpcodeObject(env, this);  // 建立实力的class 对象

    }


    /**
     * 初始化阶段（Initialization）
     * 调用类的<clinit>方法（如果有）
     * @see `http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-5.html#jvms-5.5`
     */
    public void clinit(Env env) throws Exception {
        if(inited){
            return;
        }
        synchronized(this){
            if(inited){
                return;
            }
            inited = true;
            JvmOpcodeMethod method = methods.get(new AbstractMap.SimpleEntry<>("<clinit>", "()V")); //＜clinit＞（）方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块（static{}块）中的语句合并产生的
            if(method != null){
                ////＜clinit＞（）方法是由编译器自动收集类中的所有类变量的赋值动作和静态语句块（static{}块）中的语句合并产生的
                method.call(env, null);
            }
        }
    }

    public JvmMethod getMethod(String name, String desc) throws NoSuchMethodException{
        JvmOpcodeMethod opcodeMethod = methods.get(new AbstractMap.SimpleEntry<>(name, desc));
        if(opcodeMethod == null){
            throw new NoSuchMethodException("method "+name+":"+ desc+" not exist");
        }
        return opcodeMethod;
    }
    // 根据字段的名字 获取 属性值
    public JvmField getField(String name) throws NoSuchFieldException, IllegalAccessException{
        JvmField field = fields.get(name);
        if(field == null){
            throw new NoSuchFieldException("field "+name+" of "+ className+" not exist");
        }
        return field;
    }

    @Override
    public boolean hasMethod(String name, String desc){
        JvmOpcodeMethod method = methods.get(new AbstractMap.SimpleEntry<>(name, desc));
        return method != null;
    }

    public static void main(String[] args) {

    }

}
