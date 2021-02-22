package com.powerise.ibss.util;

/**
此类继承java.util.HashMap类，重载了get方法。
 */
public class HashArray extends java.util.HashMap {
   java.util.HashMap ha;
   
   /**
   通过HashMap表进行实例构造
   @param ha
    */
   public HashArray(java.util.HashMap ha) {
      this.ha =ha;    
   }
   
   /**
   重载java.util.HashMap 的 
   get(Object)方法，如get(Object)返回的结果不为NULL，则返回gett(Object)的值，如为NU
   LL则返回空字符串
   @param o - HashMap的键值
   @return Object
    */
   @Override
public Object get(Object o) {
      return get(o, "");    
   }
   
   /**
   重载java.util.HashMap 的 
   get(Object)方法，如get(Object)返回的结果不为NULL，则返回get(Object)的值【如get(O
   bject)返回的数据集为数据库表中的日期字段，则转换成统一格式。】，如为NULL则返回指
   定的缺省替换对象
   @param o - HashMap键值
   @param d - 缺省替换值
   @return Object
    */
   public Object get(Object o, Object d) {
      if(ha.get(o) ==null)
         return d;
      else{
         //if(DateUtil.isDate(ha.get(o).toString(),"yyyy-MM-dd hh:mm:ss")){
            //判断是否是选取数据库中日期字段
         //   return ha.get(o).toString().substring(0, ha.get(o).toString().length()-2);
         //}else{
            return ha.get(o);
         //}
      
      }    
   }
}
