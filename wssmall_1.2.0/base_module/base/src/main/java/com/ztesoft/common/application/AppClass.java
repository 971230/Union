package com.ztesoft.common.application;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AppClass  {
public static void main(String[]args){
	AppClass bo=new AppClass();
	try {
		//测试类加载:构造函数传入对象为空的情况下
		Object inst=new AppTest();
		bo.loadFromClass("com.ztesoft.common.application.AppTest", inst);
		//测试类加载:构造函数传入对象不为空的情况下,传入构造函数参数
//		bo.loadFromClass("com.ztesoft.common.Application.AppTest", new Object[]{"abc"});
		//测试类加载:构造函数传入对象不为空的情况下,直接传入实例后的对象
//		bo.loadFromClass("com.ztesoft.common.Application.AppTest", new AppTest());
		//测试加载方法拦截器,第一个是被拦截的类和方法的特殊格式，第二个参数是拦截器类，第三个参数是拦截器方法，
		//最后一个参数是拦截器方法的执行实例
		bo.addIntercs("bfprintinterc","com.ztesoft.common.application.AppTest","print", 
				"com.ztesoft.common.application.AppTest", "bfprint", inst);
		//测试手工增加方法
		//bo.addMethod("com.ztesoft.common.Application.AppTest", "interc", "myMethod");
		//测试执行方法
		bo.executeMethod("print", new Object[]{"我的城市"});
	} catch (Exception e) {
		e.printStackTrace();
	}
}
//定义类名
private String _className="";
//定义字段
private ArrayList _fields=new ArrayList();
//定义字段名
private HashMap _fieldsName=new HashMap();
//定义方法
private HashMap _methods=new HashMap();
//定义拦截器:key:存放拦截时机 val:对应类实例的方法
private HashMap _intercs=new HashMap();
//定义拦截器:存放拦截时机->被拦截的类的方法
private HashMap _intercsEd=new HashMap();

//获取实例的真实实例
public Object getInst(){
	if(_methods!=null){
		Iterator it=_methods.entrySet().iterator();
		if(it.hasNext()){
			Map.Entry map=(Map.Entry)it.next();
			AppMethod _tmpMethod=(AppMethod)map.getValue();
			return _tmpMethod.getInstance();
		}
	}
	return null;
}
public void setInst(Object obj){
	//获取所有的方法
	Iterator it=_methods.entrySet().iterator();
	while(it.hasNext()){
		Map.Entry map=(Map.Entry)it.next();
		AppMethod _method=(AppMethod)map.getValue();
		_method.setInstance(obj);
	}
}
//定义添加拦截器方法
private void setIntercs(String interTime,String classNameEd,String methodNameEd,String className,String methodName,Object[]newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	String intercId=interTime.substring(0, 2)+"_______"+classNameEd+"________"+methodNameEd;
	AppMethod _tmpMethod=new AppMethod();
	//判断是否已经有这个方法
	if(!this._methods.containsKey(methodName)){
		//新建方法
		_tmpMethod.setMethod(className, methodName,newInst);
		this._methods.put(methodName, _tmpMethod);//添加了一个methodName方法
	}else{
		//新建方法
		_tmpMethod.setMethod(className, methodName,newInst);
		//可能存在方法重名,那么在这里需要重新命名:methodName+当前时间
		String synMethodName=methodName+String.valueOf(System.currentTimeMillis());
		this._methods.put(synMethodName, _tmpMethod);//添加了一个随机名字的方法
	}
	//添加拦截器标识
	if(this._intercs.containsKey(interTime)){//如果之前已经添加了这个拦截时机
		ArrayList _interc=(ArrayList)_intercs.get(interTime);
		if(_interc==null){//如果是null，就建一个新的
			_interc=new ArrayList();
			this._intercs.put(interTime, _interc);
		}
		_interc.add(_tmpMethod);//把这个方法插入到拦截器的时机里去
		
	}else{//如果没有这个拦截时机,那么新建一个
		ArrayList _interc=new ArrayList();
		_interc.add(_tmpMethod);//添加拦截方法
		this._intercs.put(interTime,_interc);//添加拦截时机
	}
	//记录被拦截的方法与拦截时机的关系
	_intercsEd.put(intercId,interTime);
}

//定义添加拦截器方法
private void setIntercs(String interTime,String classNameEd,String methodNameEd,String className,String methodName,Object newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	String intercId=interTime.substring(0, 2)+"_______"+classNameEd+"________"+methodNameEd;
	AppMethod _tmpMethod=new AppMethod();
	//判断是否已经有这个方法
	if(!this._methods.containsKey(methodName)){
		//新建方法
		_tmpMethod.setMethod(className, methodName,newInst);
		this._methods.put(methodName, _tmpMethod);//添加了一个methodName方法
	}else{
		//新建方法
		_tmpMethod.setMethod(className, methodName,newInst);
		//可能存在方法重名,那么在这里需要重新命名:methodName+当前时间
		String synMethodName=methodName+String.valueOf(System.currentTimeMillis());
		this._methods.put(synMethodName, _tmpMethod);//添加了一个随机名字的方法
	}
	//添加拦截器标识
	if(this._intercs.containsKey(interTime)){//如果之前已经添加了这个拦截时机
		ArrayList _interc=(ArrayList)_intercs.get(interTime);
		if(_interc==null){//如果是null，就建一个新的
			_interc=new ArrayList();
			this._intercs.put(interTime, _interc);
		}
		_interc.add(_tmpMethod);//把这个方法插入到拦截器的时机里去
	}else{//如果没有这个拦截时机,那么新建一个
		ArrayList _interc=new ArrayList();
		_interc.add(_tmpMethod);//添加拦截方法
		this._intercs.put(interTime,_interc);//添加拦截时机
	}
	//记录被拦截的方法与拦截时机的关系
	_intercsEd.put(intercId,interTime);
}

//添加拦截器
public void addIntercs(String interTime, String classNameEd,String methodNameEd,String className,String methodName,String _instObj) throws Exception{
	Object []_objParam=null;
		if(_instObj!=null && !_instObj.equals("")){
			String[]strbParam=_instObj.split(",");
			_objParam=new Object[strbParam.length];
			for(int i=0;i<strbParam.length;i++){
				_objParam[i]=this.getValue(strbParam[i]);
			}
	}
	this.setIntercs(interTime,classNameEd,methodNameEd,className, methodName, _objParam);
}

//添加拦截器,第一个参数是被拦截的类和方法的特殊命名，第二个参数是拦截器的类名，第三个参数是拦截器的类方法名，第四个参数是拦截器的实例
public void addIntercs(String interTime, String classNameEd,String methodNameEd,String className,String methodName,Object _instObj) throws Exception{
	AppMethod _tmpMethod=new AppMethod();
	String intercId=interTime.substring(0, 2)+"_______"+classNameEd+"________"+methodNameEd;
	//判断是否已经有这个方法
	if(!this._methods.containsKey(methodName)){
		//先添加方法
		_tmpMethod.setMethod(className, methodName,_instObj);
		this._methods.put(methodName, _tmpMethod);//添加了一个methodName方法
	}else{
		_tmpMethod=(AppMethod)this._methods.get(methodName);
	}
	//添加拦截器标识
	if(this._intercs.containsKey(interTime)){
		ArrayList _interc=(ArrayList)_intercs.get(interTime);
		if(_interc==null)_interc=new ArrayList();
		_interc.add(_tmpMethod);
	}else{
		ArrayList _interc=new ArrayList();
		_interc.add(_tmpMethod);
		this._intercs.put(interTime,_interc);//添加这个拦截时机下的方法
	}
	
	//记录被拦截的方法与拦截时机的关系
	_intercsEd.put(intercId,interTime);
}

public ArrayList getFields(){
	return _fields;
}
public void setFields(ArrayList lstInFields){
	this._fields=(ArrayList)lstInFields.clone();
}
public HashMap getMethods(){
	return _methods;
}
public void setMethods(HashMap lstInMethods){
	this._methods=(HashMap)lstInMethods.clone();
}
//从类名(全路径)直接加载类的字段和方法
public void loadFromClass(String className,Object[]obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	Class clazz=this.getClass(className);
	loadFromClass(clazz,obj);
}
//从类直接加载字段和方法 obj是传入的类实例引用
public void loadFromClass(String className,Object obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	Class clazz=this.getClass(className);
	loadFromClass(clazz,obj);
}

public void loadFromClass(Class clazz,Object obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	this._className=clazz.getName();
	Field[] _tmpFields=clazz.getDeclaredFields();
	for(int i=0;i<_tmpFields.length;i++){
		Field _field=_tmpFields[i];
		Class _type=_field.getType();
		String _name=_field.getName();
		AppField _myField=new AppField();
		_myField.setClassName(this._className);
		_myField.setFieldName(_name);
		_myField.setFieldType(_type.getName());
		_myField.setFieldPrivilege("public");
		_fieldsName.put(_name, _name);//添加名字的查询字段
		this._fields.add(_myField);
	}
	Method[] _tmpMethods=clazz.getDeclaredMethods();
	for(int i=0;i<_tmpMethods.length;i++){
		Method _method=_tmpMethods[i];
		String _name=_method.getName();
		AppMethod _myMethod=new AppMethod();
		_myMethod.setMethod(this._className, _name, obj);
		this._methods.put(_name, _myMethod);
	}
}

public void loadFromClass(Class clazz,Object[]obj) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	this._className=clazz.getName();
	Field[] _tmpFields=clazz.getDeclaredFields();
	for(int i=0;i<_tmpFields.length;i++){
		Field _field=_tmpFields[i];
		Class _type=_field.getType();
		String _name=_field.getName();
		AppField _myField=new AppField();
		_myField.setClassName(this._className);
		_myField.setFieldName(_name);
		_fieldsName.put(_name, _name);//添加名字的查询字段
		_myField.setFieldType(_type.getName());
		_myField.setFieldPrivilege("public");
		this._fields.add(_myField);
	}
	Method[] _tmpMethods=clazz.getDeclaredMethods();
	Object instance=null;//整个类的方式实例,所有方法都是一个实例的方法
	for(int i=0;i<_tmpMethods.length;i++){
		Method _method=_tmpMethods[i];
		String _name=_method.getName();
		AppMethod _myMethod=new AppMethod();
		if(instance==null){
			_myMethod.setMethod(this._className, _name, obj);
			instance=_myMethod.getInstance();
		}
		else
		_myMethod.setMethod(this._className, _name, instance);	
		this._methods.put(_name, _myMethod);
	}
}
//定义设置类名
public void setClassName(String pClassName){
	this._className=pClassName;
}
//获取类名
public String getClassName(){
	return this._className;
}
//定义添加字段
public void setField(HashMap fieldVo){
	AppField _tmpField=new AppField();
	_tmpField.loadFromHashMap(fieldVo);
	//如果是通过这样的方式添加的字段,那么classname是空的
	_fieldsName.put(_tmpField.getFieldName(), _tmpField.getFieldName());//添加名字的查询字段
	_fields.add(_tmpField);
}
//定义添加字段
public void setField(String fieldString) throws Exception{
	AppField _tmpField=new AppField();
	_tmpField.setField(fieldString);
	//如果是通过这样的方式添加的字段,那么classname是空的
	_fieldsName.put(_tmpField.getFieldName(), _tmpField.getFieldName());//添加名字的查询字段
	_fields.add(_tmpField);
}
//判断是否有这个字段
public boolean isField(String fieldName){
	return _fieldsName.containsKey(fieldName);
}
//定义修改字段值
public void setValue(String fieldName,Object fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//定义修改字段值
//public void setValue(String fieldName,int fieldVal) throws Exception{
//	AppField _tmpField=this.getField(fieldName);
//	_tmpField.setDefaultValue(fieldVal);
//}
//定义修改字段值
public void setValue(String fieldName,long fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//定义修改字段值
public void setValue(String fieldName,float fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//定义修改字段值
public void setValue(String fieldName,double fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//定义修改字段值
public void setValue(String fieldName,boolean fieldVal) throws Exception{
	AppField _tmpField=this.getField(fieldName);
	_tmpField.setDefaultValue(fieldVal);
}
//定义获取字段值
public Object getValue(String fieldName) throws Exception{
	AppField _field=this.getField(fieldName);
	if(_field!=null)return _field.getDefaultValue();
	return null;
}

//判断是否有这个fieldname
public boolean isContainField(String fieldName) throws Exception{
	if(fieldName==null || fieldName.equals(""))return false;
	if(_fields ==null || _fields.isEmpty()) return false;
	for(int i=0;i<_fields.size();i++){
		AppField _tmpField=(AppField)_fields.get(i);
		if(_tmpField!=null){
			String _fieldName=_tmpField.getFieldName();
			if(_fieldName.equals(fieldName))return true;
		}
	}
	return false;
}
//获取字段对象
public AppField getField(String fieldName){
	if(fieldName==null || fieldName.equals(""))return null;
	if(_fields ==null || _fields.isEmpty()) return null;
	for(int i=0;i<_fields.size();i++){
		AppField _tmpField=(AppField)_fields.get(i);
		if(_tmpField!=null){
			String _fieldName=_tmpField.getFieldName();
			if(_fieldName.equals(fieldName))return _tmpField;
		}
	}
	return null;
}

//手工添加某个类的某个方法，最后一个参数是这个类的构造函数的参数
public void addMethod(String className,String methodName,String newMethodName,Object[]newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	AppMethod _tmpMethod=new AppMethod();
	_tmpMethod.setMethod(className, methodName,newInst);
	_methods.put(newMethodName,_tmpMethod);
}

//手工添加某个类的某个方法，最后一个参数是这个类的实例
public void addMethod(String className,String methodName,String newMethodName,Object newInst) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	AppMethod _tmpMethod=new AppMethod();
	_tmpMethod.setMethod(className, methodName,newInst);
	_methods.put(newMethodName,_tmpMethod);
}

//手工添加某个类的某个方法，最后一个参数为空，如果是空的，那么默认调用Class.newInstance()方法
public void addMethod(String className,String methodName,String newMethodName) throws ClassNotFoundException, SecurityException, IllegalArgumentException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	this.addMethod(className, methodName, newMethodName, null);
}
//获取方法对象
public AppMethod getMethod(String pMethodName) {
	if(pMethodName ==null || pMethodName.equals("")) return null;
	if(_methods==null || _methods.isEmpty())return null;
	AppMethod _tmpMethod=(AppMethod)_methods.get(pMethodName);
	
	if (_tmpMethod==null){//避免没有加载到的父类方法
		try {
			_tmpMethod=new AppMethod();
			_tmpMethod.setMethod(this._className, pMethodName, null);
			this._methods.put(pMethodName, _tmpMethod);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	return _tmpMethod;
}
public void setFieldName(HashMap vo){
	this._fieldsName=(HashMap)vo.clone();
}
//复制当前类属性
@Override
public Object clone(){
	AppClass _appInst=new AppClass();
	_appInst.setClassName(this._className);
	_appInst.setFields(this._fields);
	_appInst.setMethods(this._methods);
	_appInst.setFieldName(this._fieldsName);
	return _appInst;
}
//执行当前类的方法
public Object executeMethodNoAop(String pMethodName,Object[] objParam,Object newInstance) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	//从_methods里获取一个方法
	AppMethod _tmpMethod=this.getMethod(pMethodName);
	//执行原来的方法
	return _tmpMethod.executeMethod(pMethodName, objParam, newInstance);
}
//执行当前类的方法
public Object executeMethod(String pMethodName,Object[] objParam,Object newInstance) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	//从_methods里获取一个方法
	AppMethod _tmpMethod=this.getMethod(pMethodName);
	//调用拦截器
	String intercKey="bf_______"+_tmpMethod.getClassName()+"________"+pMethodName;
	if(_intercsEd.containsKey(intercKey)){//如果有对这个类的这个方法的拦截时机
		//获取拦截时机
		String intercTime=(String)_intercsEd.get(intercKey);
		//根据拦截时机获取执行方法
		ArrayList _intercMethodName=(ArrayList)this._intercs.get(intercTime);
		if(_intercMethodName!=null && !_intercMethodName.isEmpty())
		for(int i=0;i<_intercMethodName.size();i++){
			AppMethod _md=(AppMethod)_intercMethodName.get(i);
			_md.executeMethod(_md.getMethodName(), objParam);
		}
	}
	//执行原来的方法
	Object _obj= _tmpMethod.executeMethod(pMethodName, objParam, newInstance);
	
	//调用拦截器
	intercKey="af_______"+_tmpMethod.getClassName()+"________"+pMethodName;
	if(_intercsEd.containsKey(intercKey)){//如果有对这个类的这个方法的拦截时机
		//获取拦截时机
		String intercTime=(String)_intercsEd.get(intercKey);
		//根据拦截时机获取执行方法
		ArrayList _intercMethodName=(ArrayList)this._intercs.get(intercTime);
		if(_intercMethodName!=null && !_intercMethodName.isEmpty())
		for(int i=0;i<_intercMethodName.size();i++){
			AppMethod _md=(AppMethod)_intercMethodName.get(i);
			_md.executeMethod(_md.getMethodName(), objParam);
		}
	}
	
	return _obj;
}
//执行当前类的方法
public Object executeMethod(String pMethodName,Object[] objParam) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	return executeMethod(pMethodName,objParam,null);
}
//执行当前类的方法
public Object executeMethod(String pMethodName,String strParam) throws SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
	String[]_strParam=strParam.split(",");
	Object[] objParam=new Object[_strParam.length];
	for(int i=0;i<_strParam.length;i++){
		AppField _field=this.getField(_strParam[i]);
		Object _obj=_field.getDefaultValue();
		objParam[i]=_obj;
	}
	return executeMethod(pMethodName,objParam,null);
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
