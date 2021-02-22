package com.ztesoft.net.mall.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lzg
 *
 */
public class JsonUtil {
	

    public static JSONObject getJsonByXmlGBK(String xml, String nodeName) {
        return getJsonByXml(xml, nodeName, "UTF-8");
    }

    private static JSONObject getJsonByXml(String xml, String nodeName, String charset) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = builder.build(new ByteArrayInputStream(xml.getBytes(charset)));
            Element root = document.getRootElement();
            List<Element> childs = null;
            if (!nodeName.equals(root.getName())) childs = root.getChildren(nodeName);
            else childs = root.getChildren();
            JSONObject json = new JSONObject();
            for (int j = 0; j < childs.size(); j++) {
                Element child = childs.get(j);
                String key = child.getName();
                String value = child.getValue();
                json.put(key, value);
            }
            return json;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Integer getInteger(JSONObject json, String key) {
        return getInteger(json, key, null);
    }

    private static Integer getInteger(JSONObject json, String key, Integer defaultValue) {
        try {
            Integer ret = json.getInteger(key);
            if (null != ret) return ret;
        }
        catch (Exception e) {
        }
        return defaultValue;
    }

    public static Long getLong(JSONObject json, String key) {
        return getLong(json, key, null);
    }

    private static Long getLong(JSONObject json, String key, Long defaultValue) {
        try {
            Long ret = json.getLong(key);
            if (null != ret) return ret;
        }
        catch (Exception e) {
        }
        return defaultValue;
    }


    public static String getString(JSONObject json, String key) {
    	String ret = null;
        try {
            ret = json.getString(key);
        }
        catch (Exception e) {
        }
        return ret;
    }
    
    public static String simpleObjectToJsonStr(Object obj,List<Class> claList) throws IllegalArgumentException, IllegalAccessException{  
        if(obj==null){  
            return "null";  
        }  
        String jsonStr = "{";  
        Class<?> cla = obj.getClass();  
        Field fields[] = cla.getDeclaredFields();  
        for (Field field : fields) {  
            field.setAccessible(true);  
            if(field.getType() == long.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getLong(obj)+",";  
            }else if(field.getType() == double.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getDouble(obj)+",";  
            }else if(field.getType() == float.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj)+",";  
            }else if(field.getType() == int.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getInt(obj)+",";  
            }else if(field.getType() == boolean.class){  
                jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj)+",";  
            }else if(field.getType() == Integer.class||field.getType() == Boolean.class  
                    ||field.getType() == Double.class||field.getType() == Float.class                     
                    ||field.getType() == Long.class){                 
                jsonStr += "\""+field.getName()+"\":"+field.get(obj)+",";  
            }else if(field.getType() == String.class){  
                jsonStr += "\""+field.getName()+"\":\""+field.get(obj)+"\",";  
            }else if(field.getType() == List.class){  
                String value = simpleListToJsonStr((List<?>)field.get(obj),claList);  
                jsonStr += "\""+field.getName()+"\":"+value+",";                  
            }else{        
                if(claList!=null&&claList.size()!=0&&claList.contains(field.getType())){  
                    String value = simpleObjectToJsonStr(field.get(obj),claList);  
                    jsonStr += "\""+field.getName()+"\":"+value+",";                      
                }else{  
                    jsonStr += "\""+field.getName()+"\":null,";  
                }  
            }  
        }  
        jsonStr = jsonStr.substring(0,jsonStr.length()-1);  
        jsonStr += "}";  
        return jsonStr;       
    }
	
	public static String simpleListToJsonStr(List<?> list,List<Class> claList) throws IllegalArgumentException, IllegalAccessException{  
        if(list==null||list.size()==0){  
            return "[]";  
        }  
        String jsonStr = "[";  
        for (Object object : list) {  
            jsonStr += simpleObjectToJsonStr(object,claList)+",";  
        }  
        jsonStr = jsonStr.substring(0,jsonStr.length()-1);  
        jsonStr += "]";       
        return jsonStr;  
    }
	
	public static String objectToJson(Object obj) {  
        StringBuilder json = new StringBuilder();  
        if (obj == null) {  
            json.append("\"\"");  
        } else if (obj instanceof Number) {  
            json.append(numberToJson((Number) obj));  
        } else if (obj instanceof Boolean) {  
            json.append(booleanToJson((Boolean) obj));  
        } else if (obj instanceof String) {  
            json.append("\"").append(stringToJson(obj.toString())).append("\"");  
        } else if (obj instanceof Object[]) {  
            json.append(arrayToJson((Object[]) obj));  
        } else if (obj instanceof List) {  
            json.append(listToJson((List<?>) obj));  
        } else if (obj instanceof Map) {  
            json.append(mapToJson((Map<?, ?>) obj));  
        } else if (obj instanceof Set) {  
            json.append(setToJson((Set<?>) obj));  
        } else {  
            json.append(beanToJson(obj));  
        }  
        return json.toString();  
    }  
	
	public static String stringToJson(String s) {  
        if (s == null) {  
            return nullToJson();  
        }  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < s.length(); i++) {  
            char ch = s.charAt(i);  
            switch (ch) {  
            case '"':  
                sb.append("\\\"");  
                break;  
            case '\\':  
                sb.append("\\\\");  
                break;  
            case '\b':  
                sb.append("\\b");  
                break;  
            case '\f':  
                sb.append("\\f");  
                break;  
            case '\n':  
                sb.append("\\n");  
                break;  
            case '\r':  
                sb.append("\\r");  
                break;  
            case '\t':  
                sb.append("\\t");  
                break;  
            case '/':  
                sb.append("\\/");  
                break;  
            default:  
                if (ch >= '\u0000' && ch <= '\u001F') {  
                    String ss = Integer.toHexString(ch);  
                    sb.append("\\u");  
                    for (int k = 0; k < 4 - ss.length(); k++) {  
                        sb.append('0');  
                    }  
                    sb.append(ss.toUpperCase());  
                } else {  
                    sb.append(ch);  
                }  
            }  
        }  
        return sb.toString();  
    }  
  
    public static String nullToJson() {  
        return "";  
    }
	
	public static String numberToJson(Number number) {  
        return number.toString();  
    }  
  
    public static String booleanToJson(Boolean bool) {  
        return bool.toString();  
    }  
  
    /** 
     * @param bean 
     *            bean对象 
     * @return String 
     */  
    public static String beanToJson(Object bean) {  
        StringBuilder json = new StringBuilder();  
        json.append("{");  
        PropertyDescriptor[] props = null;  
        try {  
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)  
                    .getPropertyDescriptors();  
        } catch (IntrospectionException e) {  
        }  
        if (props != null && props.length>0) {  
            for (int i = 0; i < props.length; i++) {  
                try {  
                    String name = objectToJson(props[i].getName());  
                    String value = objectToJson(props[i].getReadMethod()  
                            .invoke(bean));  
                    json.append(name);  
                    json.append(":");  
                    json.append(value);  
                    json.append(",");  
                } catch (Exception e) {  
                }  
            }  
            json.setCharAt(json.length() - 1, '}');  
        } else {  
            json.append("}");  
        }  
        return json.toString();  
    }  
  
    /** 
     * @param list 
     *            list对象 
     * @return String 
     */  
    public static String listToJson(List<?> list) {  
        StringBuilder json = new StringBuilder();  
        json.append("[");  
        if (list != null && list.size() > 0) {  
            for (Object obj : list) {  
                json.append(objectToJson(obj));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, ']');  
        } else {  
            json.append("]");  
        }  
        return json.toString();  
    }  
  
    /** 
     * @param array 
     *            对象数组 
     * @return String 
     */  
    public static String arrayToJson(Object[] array) {  
        StringBuilder json = new StringBuilder();  
        json.append("[");  
        if (array != null && array.length > 0) {  
            for (Object obj : array) {  
                json.append(objectToJson(obj));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, ']');  
        } else {  
            json.append("]");  
        }  
        return json.toString();  
    }  
  
    /** 
     * @param map 
     *            map对象 
     * @return String 
     */  
    public static String mapToJson(Map<?, ?> map) {  
        StringBuilder json = new StringBuilder();  
        json.append("{");  
        if (map != null && map.size() > 0) {  
            for (Object key : map.keySet()) {  
                json.append(objectToJson(key));  
                json.append(":");  
                json.append(objectToJson(map.get(key)));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, '}');  
        } else {  
            json.append("}");  
        }  
        return json.toString();  
    }  
  
    /** 
     * @param set 
     *            集合对象 
     * @return String 
     */  
    public static String setToJson(Set<?> set) {  
        StringBuilder json = new StringBuilder();  
        json.append("[");  
        if (set != null && set.size() > 0) {  
            for (Object obj : set) {  
                json.append(objectToJson(obj));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, ']');  
        } else {  
            json.append("]");  
        }  
        return json.toString();  
    }
}
