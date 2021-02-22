package com.ztesoft.common.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class AppMethod {
//定义类名
private String className="";
//定义方法名
private String methodName="";
//定义类实例
private Object _instance=null;
public Object getInstance(){
	return _instance;
}
public void setInstance(Object _obj){
	_instance=_obj;
}
public void setMethod(String pClassName,String pMethodName,Object pInst) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
	this.className=pClassName;
	this.methodName=pMethodName;
	//定义执行类
	Class clazz=getClass(this.className);
	//获取类实例
	if(pInst==null){
		try{
			_instance=clazz.newInstance();
		}catch(Throwable ee){
//			ee.printStackTrace();
		}
	}else{
		_instance=pInst;
	}
}

public void setMethod(String pClassName,String pMethodName,Object[] pInst) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
	this.className=pClassName;
	this.methodName=pMethodName;
	//定义执行类
	Class clazz=getClass(this.className);
	//获取类实例
	if(pInst==null){
		try{
			_instance=clazz.newInstance();
		}catch(Throwable ee){
//			ee.printStackTrace();
		}
	}else{
		Constructor []parameterTypes=clazz.getConstructors();
		if(parameterTypes==null || parameterTypes.length==0)throw new ClassNotFoundException("无法获取类构造函数[classname:"+pClassName+"]");
		
		for(int i=0;i<parameterTypes.length;i++){
			boolean isCanInst=true;
			Constructor _constructor=parameterTypes[i];
			Class[]_paramTypes=_constructor.getParameterTypes();
			int paramLength=_paramTypes.length;
			if(paramLength==0 && pInst.length!=0){
				isCanInst=false;
			}
			for(int j=0;j<paramLength;j++){
				Class _paramType=_paramTypes[j];
				if(!_paramType.equals(pInst[j].getClass())){
					isCanInst=false;
				}
			}
			if(isCanInst){
				try{
					_instance=_constructor.newInstance(pInst);
				}catch(Throwable ee){
//					ee.printStackTrace();
				}
				break;
			}
		}
	}
}

public String getMethodName(){
	return this.methodName;
}
public String getClassName(){
	return this.className;
}
public void loadFromHashMap(HashMap methodVo){
	if(methodVo!=null && !methodVo.isEmpty()){
		this.className=(String)methodVo.get("className");
		this.methodName=(String)methodVo.get("methodName");
	}
}

//执行实例的方法:传入方法名+输入参数
public Object executeMethod(String methodName,Object []objParam) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
	return executeMethod(methodName,objParam,null);
}

//执行实例的方法:传入方法名+输入参数+是否是static方法
public Object executeMethod(String methodName,Object []objParam,Object newInst) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
	//定义执行类
	Class clazz=getClass(this.className);
	if(objParam==null)objParam=(new Object[]{});
	boolean isNull=false;//如果方法没有参数，那么全部都要置为空
	if(objParam.length==1){//如果全部参数都是空,并且是由一个参数,那么标志一个标志
		if(objParam[0]==null){
			isNull=true;
		}
	}
	if(isNull){//如果全部参数都是空,并且是由一个参数,那么
		objParam=null;
	}
	
	Method [] lstMethod=clazz.getMethods();
	Method _method=null;
	for(int i=0;i<lstMethod.length;i++){
		Method _tmpMethod=lstMethod[i];
		if(_tmpMethod.getName().equals(methodName)){//获取到的方法名可能存在重载，这里可能会调用出错
			//校验参数类型,如果参数其中一个为空,就没法校验类型了,所以这里是弱校验,只校验非空的参数类型
			if(computeMethod(objParam,_tmpMethod)){
				_method=_tmpMethod;
				break;
			}
		}
	}
	if(_method==null)throw new NoSuchMethodException(this.className+":"+methodName);
	Object objRet=null;
	if(newInst!=null){
		objRet=_method.invoke(newInst, objParam);
	}else{
		objRet=_method.invoke(_instance, objParam);
	}
	return objRet;
}
//校验参数格式是否符合方法入参:因为运行时只传入具体的Object[],无法得知调用的参数的类型，所以这里只是弱比较：
//只比较传入的Object[]中不为空的参数的类型和顺序是否满足.
private boolean computeMethod(Object[] _paramVal,Method _method){
	if(_method==null || _method==null)return false;
	Class []_lstParamType=_method.getParameterTypes();
	//判断空参数作为入参的空参数方法-----------------------------
	if(_paramVal==null){
		//获取方法的入参类型
		if(_lstParamType==null || _lstParamType.length==0)
			return true;
	}else{//------------------------------------------------------
		if( _paramVal.length==1 && _paramVal[0]==null){
			//获取方法的入参类型
			if(_lstParamType==null || _lstParamType.length==0)
				return true;
		}
		else{//判断非空参数作为入参的非空参数方法------------------
			//如果数量不等，那么返回不等
			if(_lstParamType.length!=_paramVal.length)return false;
			return true;
		}
	}
	return false;
}
//反射类名
private Class getClass(String className) throws ClassNotFoundException{
	//获取全局的classloader
	ClassLoader globeLoader=Thread.currentThread().getContextClassLoader();
	//定义执行类
	Class clazz=globeLoader.loadClass(className);
	return clazz;
}
}
