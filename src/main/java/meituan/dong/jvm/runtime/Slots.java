package meituan.dong.jvm.runtime;

import java.util.NoSuchElementException;

/**
 * @Package Name : ${PACKAG_NAME}
 * @Author : 1766318593@qq.com
 * @Creation Date : 2018年09月05日下午10:19
 * @Function : todo
 */

/**
 * 可指定槽位大小的数组
 */
public class Slots<T> {

    private T[] buffer;

    public Slots(int size){
        buffer = (T[]) new Object[size];
    }

    public void set(int pos, T entity, int size) throws IllegalArgumentException{
        if(pos <0 || pos+size > buffer.length){
            throw new IllegalArgumentException("invalid entity size "+size);
        }
        buffer[pos] = entity;
        for(int i=1; i<size; i++){
            buffer[pos+i] = null;
        }
    }

    public Object get(int pos) throws NoSuchElementException {
        if(pos<0 || pos >= buffer.length){
            throw new NoSuchElementException();
        }
        return buffer[pos];
    }

    public int size(){
        return buffer.length;
    }
    public static void main(String[] args) {

    }

}
