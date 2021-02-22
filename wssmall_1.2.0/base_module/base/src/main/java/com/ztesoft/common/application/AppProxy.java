package com.ztesoft.common.application;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.log4j.Logger;
/**
 * @author AyaKoizumi
 * @date 20100906
 * @desc 构造一个java代理，用于方法调用前后的业务校验
 * */
public class AppProxy implements InvocationHandler{
	private static Logger logger = Logger.getLogger(AppProxy.class);
	public static void main(String[]args){
		//有接口的代理
		HashMap vo=new HashMap();
		AppProxy apx=new AppProxy();
		Object obj=apx.getProxyObject(vo);
		try {
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addBfInterc("put", "com.ztesoft.common.application.AppProxy", "bfPut", apx);
			apx.addAfInterc("put", "com.ztesoft.common.application.AppProxy", "afPut", apx);
			apx.addAfInterc("put", "com.ztesoft.common.application.AppProxy", "afPut", apx);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		((Map)obj).put("cc","dd");
		logger.info(((Map)obj).get("cc"));
		try {
			apx.removeInterc("bfPut");
			apx.invoke("put", new Object[]{"ee","ff"});
			logger.info(((Map)vo).get("ee"));
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		无接口的代理
		HashMap vo1=new HashMap();
		AppProxy apx1=new AppProxy();
		apx1.setIsUseArrayListMethodParam(true);
		Object obj1=apx1.getProxyObject(vo1,null);
		try {
			apx1.addBfInterc("put", "com.ztesoft.common.application.AppTest", "bfprint2", null);
			apx1.addAfInterc("put", "com.ztesoft.common.application.AppTest", "afprint2", null);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			Object c=((AppProxy)obj1).invoke("put", new Object[]{"cc","mytest"});
			logger.info("--------------------------------------------------"+c);
//			((AppProxy)obj1).removeInterc("bfprint2");
//			((AppProxy)obj1).removeInterc("afprint2");
//			((AppProxy)obj1).removeInterc("aafprint2");
//			((AppProxy)obj1).removeInterc("aaafprint2");
			Object cd=((AppProxy)obj1).invoke("put", new Object[]{"dd","mytest111"});
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private long uuid=0;
	private long getUuid(){
		return uuid++;
	}
	//在调用拦截器的时候,是否把方法的参数封装成一个ArrayList的队列传入(意思是拦截器的方法写死入参是ArrayList)
	private boolean isUseArrayListMethodParam=false;
	public void setIsUseArrayListMethodParam(boolean setFlag){
		this.isUseArrayListMethodParam=setFlag;
	}
	public boolean getIsUseArrayListMethodParam(){
		return this.isUseArrayListMethodParam;
	}
	private Object _proxy;
	public Object getProxyOri(){
		return this._proxy;
	}
	//获取代理对象
	public Object getProxyObject(Object inObj){
		_proxy=inObj;
		Class clazz=inObj.getClass();
		ClassLoader clsLoader=clazz.getClassLoader();
		Class[]clsInterfaces=clazz.getInterfaces();
		if(clsInterfaces==null || clsInterfaces.length==0){
			return this;
		}else{
			return java.lang.reflect.Proxy.newProxyInstance(clsLoader, clsInterfaces,this);
		}
	}
	//获取代理对象:指定接口类型
	public Object getProxyObject(Object inObj,Class[]clsInterfaces){
		_proxy=inObj;
		Class clazz=inObj.getClass();
		ClassLoader clsLoader=clazz.getClassLoader();
		if(clsInterfaces==null || clsInterfaces.length==0){
			return this;
		}else{
			return java.lang.reflect.Proxy.newProxyInstance(clsLoader, clsInterfaces,this);
		}
	}
	public Object invoke(String methodName,Object[]args) throws Throwable{
		String className="";
		if(this._proxy!=null)
			className=this._proxy.getClass().getName();
		String intercCd=getIntercCd(className+methodName, "bf");

		callInterc(this._proxy,intercCd, args);
		
		AppClass aps=new AppClass();
		aps.loadFromClass(className, this._proxy);
		Object ret=aps.executeMethodNoAop(methodName, args,this._proxy);
		
		intercCd=getIntercCd(className+methodName, "af");
		callInterc(this._proxy,intercCd, args);
		
		return ret;
	}
	//执行原对象方法
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName=method.getName();
		String className="";
		if(this._proxy!=null)
			className=this._proxy.getClass().getName();
		String intercCd=getIntercCd(className+methodName, "bf");
//		logger.info(intercId);
		callInterc(proxy,intercCd, args);
		
		Object ret=method.invoke(_proxy, args);
		
		intercCd=getIntercCd(className+methodName, "af");
//		logger.info(intercId);
		
		callInterc(proxy,intercCd, args);
		
		return ret;
	}
	
	public AppProxy(){
	}

	public void callInterc(Object proxy,String intercCd,Object[] args) throws Throwable{
		if(intercCd==null || intercCd.equals(""))return;
		ArrayList interIds=new ArrayList();
		if(intercIdMap!=null && !intercIdMap.isEmpty()){
			for(Iterator it=intercIdMap.entrySet().iterator();it.hasNext();){
				Map.Entry map=(Map.Entry)it.next();
				String _intercId=(String)map.getKey();
				String _intercCd=(String)map.getValue();
				if(_intercCd!=null && !_intercCd.equals("") && _intercCd.equals(intercCd)){
					interIds.add(_intercId);
				}
			}
		}
		if(interIds!=null && !interIds.isEmpty()){
			for(int i=0;i<interIds.size();i++){
				String intercId=(String)interIds.get(i);
				AppProxy apx=(AppProxy)intercMapCls.get(intercId);
				String methodName=(String)intercMapInst.get(intercId);
				if(!isUseArrayListMethodParam)
					apx.invoke(methodName, args);
				else{
					ArrayList methodParam=new ArrayList();
					if(args!=null){
						for(int j=0;j<args.length;j++){
							methodParam.add(args[j]);
						}
					}
					apx.invoke(methodName, new Object[]{this,apx,methodParam});
				}
			}
		}
	}
	private AppProxy _addBfIInterc(AppProxy apx,String parentClassName,String classPath,String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		if(apx==null)return null;
		Object _proxyOri=apx.getProxyOri();
		if(_proxyOri!=null){//首先考虑当前层的拦截器
			String oriClassName="";
			if(_proxyOri!=null){
				oriClassName=_proxyOri.getClass().getName();
			}
			if(parentClassName!=null && !parentClassName.equals("")){
				if(oriClassName!=null)
					oriClassName=parentClassName+oriClassName+"/";//查找路径
				else
					oriClassName=parentClassName;//查找路径
			}else{
				oriClassName=oriClassName+"/";//查找路径
			}
			if(oriClassName!=null && !oriClassName.equals("") && classPath!=null && !classPath.equals("") &&
					oriClassName.equals(classPath)){
				apx.addBfInterc(oriMethodName, className, methodName, inst);
			}
			if((parentClassName==null || parentClassName.equals("")) && oriClassName!=null && !oriClassName.equals(""))
				parentClassName=oriClassName;
		}
		//遍历当前apx下的全部路径
		if(apx.intercMapCls!=null && !apx.intercMapCls.isEmpty()){
			for(Iterator it=apx.intercMapCls.entrySet().iterator();it.hasNext();){
				Map.Entry map=(Map.Entry)it.next();
				String intercId=(String)map.getKey();
				AppProxy _apx=(AppProxy)map.getValue();
				Object proxyOri=_apx.getProxyOri();
				String _oriClassName="";
				if(proxyOri!=null)
					_oriClassName=proxyOri.getClass().getName();
				if(parentClassName!=null && !parentClassName.equals("")){
					if(_oriClassName!=null)
						_oriClassName=parentClassName+_oriClassName+"/";//查找路径
					else
						_oriClassName=parentClassName;//查找路径
				}
					
				
				String _oriMethodName=(String)apx.intercMapInst.get(intercId);
				if(classPath!=null && oriMethodName!=null && _oriClassName!=null && _oriMethodName!=null &&
						classPath.equals(_oriClassName) && oriMethodName.equals(_oriMethodName)){
					_apx.addBfInterc(oriMethodName, className, methodName, inst);
				}else{//深度遍历
					_apx._addBfIInterc(_apx,_oriClassName, classPath, oriMethodName,className,methodName,inst);
				}
			}
		}
		return null;
	}
	private AppProxy _addAfIInterc(AppProxy apx,String parentClassName,String classPath,String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		if(apx==null)return null;
		if(apx==null)return null;
		Object _proxyOri=apx.getProxyOri();
		if(_proxyOri!=null){//首先考虑当前层的拦截器
			String oriClassName="";
			if(_proxyOri!=null){
				oriClassName=_proxyOri.getClass().getName();
			}
			if(parentClassName!=null && !parentClassName.equals("")){
				if(oriClassName!=null)
					oriClassName=parentClassName+oriClassName+"/";//查找路径
				else
					oriClassName=parentClassName;//查找路径
			}else{
				oriClassName=oriClassName+"/";//查找路径
			}
			if(oriClassName!=null && !oriClassName.equals("") && classPath!=null && !classPath.equals("") &&
					oriClassName.equals(classPath)){
				apx.addAfInterc(oriMethodName, className, methodName, inst);
			}
			if((parentClassName==null || parentClassName.equals("")) && oriClassName!=null && !oriClassName.equals(""))
				parentClassName=oriClassName;
		}
		//遍历apx下全部路径
		if(apx.intercMapCls!=null && !apx.intercMapCls.isEmpty()){
			for(Iterator it=apx.intercMapCls.entrySet().iterator();it.hasNext();){
				Map.Entry map=(Map.Entry)it.next();
				String intercId=(String)map.getKey();
				AppProxy _apx=(AppProxy)map.getValue();
				Object proxyOri=_apx.getProxyOri();
				String _oriClassName="";
				if(proxyOri!=null)
					_oriClassName=proxyOri.getClass().getName();
				if(parentClassName!=null && !parentClassName.equals("")){
					if(_oriClassName!=null)
						_oriClassName=parentClassName+_oriClassName+"/";//查找路径
					else
						_oriClassName=parentClassName;//查找路径
				}
					
				//当前intercId的对应的methodName,这个methodName是拦截器的方法名,内存存放的意思是:用_apx对象执行methodName方法拦截父节点的method
				String _oriMethodName=(String)apx.intercMapInst.get(intercId);
				if(classPath!=null && oriMethodName!=null && _oriClassName!=null && _oriMethodName!=null &&
						classPath.equals(_oriClassName) && oriMethodName.equals(_oriMethodName)){//如果用户需要对这个拦截器_apx增加拦截器,那么就是对_apx调用addAfInterc方法实现
					_apx.addAfInterc(oriMethodName, className, methodName, inst);
				}else{//深度遍历
					_apx._addAfIInterc(_apx,_oriClassName, classPath, oriMethodName,className,methodName,inst);
				}
			}
		}
		return null;
	}
	//为当前的apx的下的任何一层的拦截器的oriMethodName方法增加执行前拦截器,inst对象是className参数的实例,可以传null,classPath为具体的类层次路径
	public void addBfIInterc(String classPath,String oriMethodName,String className,String methodName,Object inst) throws Exception{
		Object _proxyOri=this.getProxyOri();
		if(_proxyOri!=null)//首先考虑当前层的拦截器
			classPath=_proxyOri.getClass().getName()+"/"+classPath;//默认补充当前的路径
		this._addBfIInterc(this, "",classPath, oriMethodName, className, methodName, inst);
	}
	//为当前的apx的下的任何一层的拦截器的oriMethodName方法增加执行后拦截器,inst对象是className参数的实例,可以传null,classPath为具体的类层次路径
	public void addAfIInterc(String classPath,String oriMethodName,String className,String methodName,Object inst) throws Exception{
		Object _proxyOri=this.getProxyOri();
		if(_proxyOri!=null)//首先考虑当前层的拦截器
			classPath=_proxyOri.getClass().getName()+"/"+classPath;//默认补充当前的路径
		this._addAfIInterc(this, "",classPath, oriMethodName, className, methodName, inst);
	}
	//为当前的apx的oriMethodName方法增加执行前拦截器,inst对象是className参数的实例,可以传null
	public void addBfInterc(String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String _className="";
		if(this._proxy!=null)
			_className=this._proxy.getClass().getName();
		
		String intercCd=this.getIntercCd(_className+oriMethodName, "bf");//类映射标示
		String intercId=String.valueOf(this.getUuid());//唯一标示
		//生成拦截器实例
		AppProxy __apx=this.getIntercInst(className,inst);
		intercMapCls.put(intercId, __apx);
		intercMapInst.put(intercId, methodName);
		intercIdMap.put(intercId, intercCd);//拦截器id映射
	}
	//为当前的apx的oriMethodName方法增加执行后拦截器,inst对象是className参数的实例,可以传null
	public void addAfInterc(String oriMethodName,String className,String methodName,Object inst) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String _className="";
		if(this._proxy!=null)
			_className=this._proxy.getClass().getName();
		
		String intercCd=this.getIntercCd(_className+oriMethodName, "af");//类映射标示
		String intercId=String.valueOf(this.getUuid());//唯一标示
		//生成拦截器实例
		AppProxy __apx=this.getIntercInst(className,inst);
		intercMapCls.put(intercId, __apx);
		intercMapInst.put(intercId, methodName);
		intercIdMap.put(intercId, intercCd);//拦截器id映射
	}
	
	//获取无接口代理的对象
	private AppProxy getIntercInst(String className,Object inst) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		AppProxy _apx=new AppProxy();
		Object _inst=inst;
		if(inst==null){
			Class clazz=Class.forName(className);
			_inst=clazz.newInstance();
		}
		//强制转换成无接口代理
		AppProxy __apx=(AppProxy)_apx.getProxyObject(_inst,null);
		return __apx;
	}

	private void __removeInterc(ArrayList delList,String intercCd){
		if(intercCd==null || intercCd.equals(""))return;
		ArrayList interIds=new ArrayList();
		if(this.intercIdMap!=null && !this.intercIdMap.isEmpty()){
			for(Iterator it=this.intercIdMap.entrySet().iterator();it.hasNext();){
				Map.Entry map=(Map.Entry)it.next();
				String _intercId=(String)map.getKey();
				String _intercCd=(String)map.getValue();
				if(_intercCd!=null && !_intercCd.equals("") && _intercCd.equals(intercCd)){
					interIds.add(_intercId);
				}
			}
		}
		if(interIds!=null && !interIds.isEmpty()){
			HashMap vo=new HashMap();
			vo.put("apx", this);
			vo.put("intercIds", interIds);
			delList.add(vo);
		}
	}
	public void removeInterc(String oriMethodName) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		ArrayList delList=new ArrayList();
		this.removeIInterc(delList,oriMethodName);
		if(delList!=null && !delList.isEmpty()){
			for(int i=0;i<delList.size();i++){
				HashMap vo=(HashMap)delList.get(i);
				AppProxy apx=(AppProxy)vo.get("apx");
				ArrayList interIds=(ArrayList)vo.get("intercIds");
				for(int j=0;j<interIds.size();j++){
					String intercId=(String)interIds.get(j);
					apx.intercIdMap.remove(intercId);
					apx.intercMapCls.remove(intercId);
					apx.intercMapInst.remove(intercId);
				}
			}
		}
	}
	public boolean isContainKey(String oriMethodName) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		ArrayList delList=new ArrayList();
		this.removeIInterc(delList,oriMethodName);
		if(delList!=null && !delList.isEmpty()){
			return true;
		}
		return false;
	}
	//删除某个拦截器
	public void removeIInterc(ArrayList delList,String oriMethodName) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		String intercCd="_________"+oriMethodName;
		this.__removeInterc(delList,intercCd);
		if(this.intercMapCls!=null && !this.intercMapCls.isEmpty()){
			for(Iterator it=this.intercMapCls.entrySet().iterator();it.hasNext();){
				Map.Entry map=(Map.Entry)it.next();
				String intercId=(String)map.getKey();
				String _oriMethodName=(String)this.intercMapInst.get(intercId);
				if(oriMethodName!=null && _oriMethodName!=null && oriMethodName.equals(_oriMethodName)){
					String interCd=(String)this.intercIdMap.get(intercId);
					this.__removeInterc(delList,interCd);
				}
				AppProxy _apx=(AppProxy)map.getValue();
				_apx.removeIInterc(delList,oriMethodName);//回调
			}
		}
	}
	
	private String getIntercCd(String oriMethodName,String intercTime){
		return "_________"+intercTime+oriMethodName;
	}
	
	public void bfPut(Object c,Object d){
		String msg="bf___";
		if(c!=null)
			msg=msg+c.toString();
		logger.info(msg);
	}
	public void afPut(Object c,Object d){
		String msg="af___";
		if(c!=null)
			msg=msg+c.toString();
		logger.info(msg);
	}
	
	private HashMap intercMapCls=new HashMap();//存放拦截时机对应的拦截器类实例
	public HashMap getIntercMapCls(){
		return intercMapCls;
	}
	private HashMap intercMapInst=new HashMap();//存放拦截时机对应的拦截器类方法名
	public HashMap getIntercMapInst(){
		return intercMapInst;
	}
	private LinkedHashMap intercIdMap=new LinkedHashMap();//存放拦截时机对应的拦截器类方法名
	public  LinkedHashMap getIntercIdMap(){
		return intercIdMap;
	}
	
}
