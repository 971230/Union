package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ThreadLocalUtil {
	protected static Map getData()
    {
        Map map = (Map)threadLocal.get();
        if(map == null)
        {
            map = Collections.synchronizedMap(new HashMap());
            threadLocal.set(map);
        }
        return map;
    }
    public static Object getProperty(String keyName)
    {
        Map map = getData();
        return map.get(keyName);
    }
    public static Object getOnceProperty(String keyName)
    {
        Map map = getData();
        Object keyValue = map.get(keyName);
        map.remove(keyName);
        return keyValue;
    }
    public static void setProperty(String keyName, Object keyValue)
    {
        Map map = getData();
        map.put(keyName, keyValue);
    }
    public static void removeProperty(String keyName)
    {
    	Map map = getData();
        map.remove(keyName);
    }
    public static void clearProperty()
    {
    	Map map = getData();
        map.clear();
    }
    
	private static final ThreadLocal threadLocal = new ThreadLocal();
}
