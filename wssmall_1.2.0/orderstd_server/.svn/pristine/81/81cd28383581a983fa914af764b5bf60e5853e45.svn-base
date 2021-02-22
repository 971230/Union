package com.ztesoft.newstd.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class PropertyDescriptorUtil {
    /**
     * 获取对象所有属性
     * 
     * @作者 MoChunrun
     * @创建日期 2014-3-12
     * @param obj
     * @return
     */
    public static PropertyDescriptor[] getObjectProperty(Object obj) throws IntrospectionException {
        return getClassProperty(obj.getClass());
    }

    public static PropertyDescriptor[] getClassProperty(Class clazz) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz); // 获取类属性
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        return propertyDescriptors;
    }

    public static void invote(PropertyDescriptor[] propertyDescriptors, String proName, Object value, Object obj) {
        if (value == null)
            return;
        for (PropertyDescriptor property : propertyDescriptors) {
            String mName = property.getName();
            if (mName.equalsIgnoreCase(proName)) {
                Class propertyType = property.getPropertyType();
                Object propValue = value;
                if (propertyType.getName().equals("boolean") || propertyType.getName().equals("java.lang.Boolean")) {
                    propValue = Boolean.valueOf(String.valueOf(propValue));
                } else if (propertyType.getName().equals("int") || propertyType.getName().equals("java.lang.Integer")) {
                    propValue = Integer.valueOf(String.valueOf(propValue));
                } else if (propertyType.getName().equals("long") || propertyType.getName().equals("java.lang.Long")) {
                    propValue = Long.valueOf(String.valueOf(propValue));
                } else if (propertyType.getName().equals("float") || propertyType.getName().equals("java.lang.Float")) {
                    propValue = Float.valueOf(String.valueOf(propValue));
                } else if (propertyType.getName().equals("double") || propertyType.getName().equals("java.lang.Double")) {
                    propValue = Double.valueOf(String.valueOf(propValue));
                } else if (propertyType.getName().equals("java.lang.String")) {
                    propValue = String.valueOf(propValue);
                }
                try {
                    if (propValue != null && property.getWriteMethod() != null)
                        property.getWriteMethod().invoke(obj, new Object[] {propValue});
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}
