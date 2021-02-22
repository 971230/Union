package com.ztesoft.common.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.exception.CrmException;





public class Clazz {

	public static Method EQUALS_METHOD;
	public static Method HASHCODE_METHOD;
	public static Method TOSTRING_METHOD;

	static {	
		try {
			EQUALS_METHOD = 
				Object.class.getMethod("equals", new Class[] { Object.class });
			HASHCODE_METHOD = 
				Object.class.getMethod("hashCode", null);
			TOSTRING_METHOD = 
				Object.class.getMethod("toString", null);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Method[] OBJECT_METHODS = new Method[] { EQUALS_METHOD,
			HASHCODE_METHOD, TOSTRING_METHOD };
	public static Set OBJECT_METHODS_SET = 
			new HashSet(Arrays.asList(OBJECT_METHODS));
	
    private Clazz() {}

	public static Object newInstance(Class clazz) {
		try {
			return clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public static Object newInstance(Constructor constructor, 
			Object[] arguments) {
		try {
			return constructor.newInstance(arguments);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public static boolean implementsInterfaces(Class clazz, 
			Class[] interfaces) {
		for (int i = 0; i < interfaces.length; i++)
			if (!interfaces[i].isAssignableFrom(clazz))
				return false;
		return true;
	}
    public static Object invoke(Object target, Method method,
            Object[] args) throws Exception {
        /*if (target instanceof Proxy) {
        	return Proxy.getInvocationHandler(target).
        		invoke(target, method, args);
        }
        else {*/
			try {
				return method.invoke(target, args);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage());
			}
			catch (InvocationTargetException e) {
				Throwable cause =e.getTargetException();
				if(cause instanceof CrmException){
					throw  (CrmException)cause;
				}else if(cause instanceof DAOSystemException){
					
					throw ((DAOSystemException)cause);
					
				}else {
					throw e;
				}
			}catch(Exception e){
				throw e;
			}
       // }
    }
    public static ClassLoader getClassLoader(Class clazz) {
    	ClassLoader loader = clazz.getClassLoader();
        return (loader == null) ? ClassLoader.getSystemClassLoader() :
            loader;
    }
	public static ClassLoader getClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		return (loader == null) ? ClassLoader.getSystemClassLoader() :
			loader;
	}
    public static ClassLoader commonLoader(Class[] classes) {
    	if (classes.length == 0) throw new IllegalArgumentException(
            "No classes provided.");
        if (classes.length == 1) return getClassLoader(classes[0]);
		for (int i = 1; i < classes.length; i++) {
			try {
				ClassLoader loader = getClassLoader(classes[i]);
				for (int i2 = 0; i2 < classes.length; i2++) {
					if (i == i2)
						continue;
					loader.loadClass(classes[i2].getName());
				}
				return loader;
			}
			catch (ClassNotFoundException e) {}			
		}
		
		throw new RuntimeException("No common class loader: " + 
			Arrays.asList(classes));
    }
    public static ClassLoader commonLoader(Collection classes) {
    	return commonLoader(
    		(Class[]) classes.toArray(new Class[classes.size()]));
    }
 
    public static List getAllInterfacesAsList(Class clazz) {
    	List interfaces = null;
    	if (clazz.isInterface())
    		return Collections.singletonList(clazz);
        else {
        	interfaces = new ArrayList();
        	addAllInterfaces(clazz, interfaces);
        	Collections.reverse(interfaces);
        	return interfaces;
        }
    }
   
	public static Class[] getAllInterfaces(Class clazz) {
		return (Class[]) getAllInterfacesAsList(clazz).
			toArray(new Class[0]);
	}
  
    static void addAllInterfaces(Class clazz, List list) {
        while (clazz != null && clazz != Object.class) {
            Class[] interfaces = clazz.getInterfaces();
            for (int i = interfaces.length - 1; i >= 0; i--)
            	if (!list.contains(interfaces[i]))
            		list.add(interfaces[i]);
            clazz = clazz.getSuperclass();
        }
    }

    static final Map primitiveMap = new HashMap();

	static {
		primitiveMap.put("boolean", boolean.class);
		primitiveMap.put("byte", byte.class);
		primitiveMap.put("char", char.class);
		primitiveMap.put("short", short.class);
		primitiveMap.put("int", int.class);
		primitiveMap.put("long", long.class);
		primitiveMap.put("float", float.class);
		primitiveMap.put("double", double.class);
		primitiveMap.put("void", void.class);
	}
	public static Class forName(String name) throws ClassNotFoundException {
		Class clazz = (Class) primitiveMap.get(name);
		if (clazz != null) 
			return clazz;
		return getClassLoader().loadClass(name);
	}
	
	public static Method findMethod(Class beanClass, String methodName) {
		Method[] methods = beanClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method == null) {
				continue;
			}
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
	}
	
	public static List getAllMethods(Class beanClass){
		
		return Arrays.asList(beanClass.getDeclaredMethods());
	}
	

	public static Object newInstance(String className) {
		try {
			return forName(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}


	static {
		primitiveMap.put("boolean", boolean.class);

		primitiveMap.put("byte", byte.class);
		primitiveMap.put("char", char.class);
		primitiveMap.put("short", short.class);
		primitiveMap.put("int", int.class);
		primitiveMap.put("long", long.class);
		primitiveMap.put("float", float.class);
		primitiveMap.put("double", double.class);
		primitiveMap.put("void", void.class);

		primitiveMap.put(Boolean.class, boolean.class);
		primitiveMap.put(Byte.class, byte.class);
		primitiveMap.put(Character.class, char.class);
		primitiveMap.put(Short.class, short.class);
		primitiveMap.put(Integer.class, int.class);
		primitiveMap.put(Long.class, long.class);
		primitiveMap.put(Float.class, float.class);
		primitiveMap.put(Double.class, double.class);
		primitiveMap.put(Void.class, void.class);

	}

	/*
	 * public static Class forName(String name) throws ClassNotFoundException {
	 * Class clazz = (Class) primitiveMap.get(name); if (clazz != null) { return
	 * clazz; }
	 * 
	 * return getClassLoader().loadClass(name); }
	 */

	public static Method findMethod(Class beanClass, String methodName,
			Class<?>... parameterTypes) {
		Class<?> clazz = beanClass;
		try {
			Method method = clazz.getMethod(methodName, parameterTypes);
			return method;
		} catch (Exception e) {
		}

		return null;
	}

	public static Map<String, ObjectToken> convertType(Map<String, ?> params) {
		if (params == null) {
			return null;
		}
		Map<String, ObjectToken> convert_param = new HashMap<String, ObjectToken>();
		for (Iterator iterator = params.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object _object = params.get(key);
			if (_object != null) {
				if (!(_object instanceof ObjectToken)) {
					ObjectToken _objectToken = new ObjectToken();
					_objectToken.set_object_name(key);
					_objectToken.setValue(JsonUtil.toJson(_object));
					_objectToken.set_object_type(_object.getClass().getName());
					_object = _objectToken;
				}
			} else if (_object == null) {
				ObjectToken _objectToken = new ObjectToken();
				_objectToken.set_object_name(key);
				_objectToken.setValue("");
				_objectToken.set_object_type("void");
				_object = _objectToken;
			}

			convert_param.put(key, (ObjectToken) _object);
		}
		return convert_param;
	}

	public static <T> T getFieldValue(Object obj, Class defClass,
			String filedName, Class<T> filedClazz) {
		if (defClass != null) {
			try {
				Field field = defClass.getDeclaredField(filedName);
				field.setAccessible(true);
				return (T) field.get(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}

		}

		return null;
	}

	public static <T> T getStaticFieldValue(Class defClass, String filedName,
			Class<T> filedClazz) {
		if (defClass != null) {
			try {
				Field field = defClass.getDeclaredField(filedName);
				field.setAccessible(true);
				return (T) field.get(defClass);
			} catch (Exception e) {
				// TODO Auto-generated catch block

			}

		}

		return null;
	}

	public static Object invokeMethod(Object obj, String method_name,
			Object... args) {
		Class<?>[] parameterTypes = new Class<?>[args.length];
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				parameterTypes[i] = (Class<?>) primitiveMap.get(args[i]
						.getClass()) == null ? args[i].getClass()
						: (Class<?>) primitiveMap.get(args[i].getClass());
			}
		}
		Method method = Clazz.findMethod(obj.getClass(), method_name,
				parameterTypes);
		if (method != null) {
			try {
				return method.invoke(obj, args);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	 /**  
     * 循环向上转型, 获取对象的所有定义字段 DeclaredFields  
     * @return 所有的的属性对象  xiaof 121025
     */  
    public static  List<Field> getAllDeclaredFields(Class clazz){   
        List<Field> fields = new ArrayList<Field>(); ;   
           
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {   
            try {   
            	fields.addAll(Arrays.asList(clazz.getDeclaredFields()));;   
                
            } catch (Exception e) {   
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了   
            }    
        }   
        return fields ;   
    }   
    
    //获取属性值
    public static Object getFiledObject(Object obj,Field field ){
    	try {
    		if(field!=null){
    			field.setAccessible(true) ;
    			return  field.get(obj);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
}
