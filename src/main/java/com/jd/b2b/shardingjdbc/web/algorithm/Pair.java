package com.jd.b2b.shardingjdbc.web.algorithm;

/**
 * @author songwanke
 * @date 2018/6/12
 * 构建一个Pair对象存储权重和对应的Key
 */
public class Pair<K,V> {

    private K key;

    private V value;

   public Pair(K key,V value){
       this.key = key;
       this.value = value;
   }

   public K getKey(){
       return key;
   }

   public V getValue(){
       return value;
   }
}
