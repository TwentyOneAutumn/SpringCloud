package com.Core.Utils;

import java.util.Collection;

public class CollUtils<E> {

    /**
     * 均分数组或集合
     * @param object 要均分的数组或集合
     * @param collections 接收数据的集合数组
     * @param <T> 泛型,继承Collection
     */
    public  <T extends Collection<E>> void splitCollection(E object, T... collections){
        int length = collections.length;
        int i = 0;
        // 判断是否为Collection集合类型
        if(object instanceof Collection){
            Collection<E> collection = (Collection<E>)object;
            for (E e : collection) {
                collections[i++ % length].add(e);
            }
        }
        // 判断是否为数组类型
        else if(object.getClass().isArray()){
            E[] collection = (E[])object;
            for (E e : collection) {
                collections[i++ % length].add(e);
            }
        }
    }
}
