package com.ztesoft.common.application;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.log4j.Logger;

//import org.apache.catalina.loader.WebappLoader;

public class AppUrlClassLoader {//extends WebappLoader {
	private static Logger logger = Logger.getLogger(AppUrlClassLoader.class);
	public static void main(String []args){
		URL url=null;
		try {
			url = new URL("file:/D:/oracle/Middleware/user_projects/domains/base_domain/autodeploy/CrmWeb/WEB-INF/classes/");
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL[]urls=new URL[]{url};
		AppUrlClassLoader myloader=new AppUrlClassLoader();
		myloader.setClassPath("com.ztesoft.crm.business.common.concept.prod.");
		try {
			for(int i=0;i<10;i++){
				Class myClass=myloader.loadClass("com.ztesoft.common.Application.AppTest2");
				Method myMethod=myClass.getMethod("print", new Class[]{java.lang.String.class});
				myMethod.invoke(myClass.newInstance(), new String[]{"我的事"+i});
				if(i==5){
					String cc="";
					myloader.getNewClassLoader();//指定父节点的classLoader
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private MyClassLoader classLoader=null;
	private static URL[] _url=null;
	static{
		try {
			_url=new URL[]{new URL("file:D:/MyProject/CRM4/CrmWeb/WebRoot/WEB-INF/classes/")};
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String _classPath=null;
	public AppUrlClassLoader(){
	}

	public void setUrl(URL[] pUrl){
		AppUrlClassLoader._url=pUrl;
	}
	public void getNewClassLoader(){
		this.getClassLoader(true);//每次强制获取一个新的类
	}
	
	//程序入口
	public Class loadClass(String className) throws ClassNotFoundException{
		//获取MyClassLoader
		this.getClassLoader(false);
		//设置加载class的顺序,asc是遵循jvm规范,按照双亲委派方式加载,desc是优先自己加载
		this.classLoader.setClassList(_classPath);
		//加载class
		Class clazz=this.classLoader.loadClass(className);
		return clazz;
	}
	public void setClassPath(String inClassPath){
		this._classPath=inClassPath;
	}
	private void getClassLoader(boolean isForce){
		if(this.classLoader==null || isForce){
			this.classLoader=null;
			this.classLoader=new MyClassLoader(AppUrlClassLoader._url);
		}
	}
	
	
	private class MyClassLoader extends URLClassLoader {
		private ClassLoader parent=null;
		private String classPath=null;//默认先用线程的classLoader加载,desc是先用自己的classloader加载
		private URL[] _url=null;
	    public MyClassLoader(URL[] url) {
	    	super(url);
	    	_url=url;
	    	parent=Thread.currentThread().getContextClassLoader();
	    }  
	    //重置加载列表
	    public void setClassList(String inClassPath){
	    	this.classPath=inClassPath;
	    }
	    @Override
		public Class loadClass(String name) throws ClassNotFoundException{
	    	//jvm规范:如果曾经加载过,可以用缓存里的class
	        Class clazz=this.findLoadedClass(name);  
        	if(clazz!=null){
        		return clazz;
        	}

        	if(name.startsWith(this.classPath)){//如果是需要自己加载的列表
        		clazz=loadByOwn(name);
        	}else{
        		clazz=loadByParent(name);
        	}
        		
        	if(clazz==null)
            	throw new ClassNotFoundException(name);
            resolveClass(clazz);
	        if ( clazz == null ){  
	            throw new ClassNotFoundException(name);
	        }
	        return clazz;  
	    }  
	    
	    private Class loadByOwn(String name){
			Class clazz=null;
			try {
				//自己加载类
				clazz = this.findClass(name);
				if(clazz==null)
	    			throw new ClassNotFoundException();
				logger.info("当前节点加载:"+name);
			} catch (Throwable e1) {//如果加载不到
				try {
					//尝试委派为当前父节点的classloader去加载类
					clazz = this.getParent().loadClass(name);
					if(clazz==null)
	        			throw new ClassNotFoundException();
					logger.info("当前父节点加载:"+name);
				} catch (Throwable e2) {
					try{
						clazz=parent.loadClass(name);
						if(clazz==null)
		        			throw new ClassNotFoundException();
						logger.info("当前线程加载:"+name);
					}catch (Throwable ee) {
						//
					}
				}
			}
			return clazz;
		}
	    
	    private Class loadByParent(String name){
			Class clazz=null;
			try {
        		//尝试委派为当前父节点的classloader去加载类
        		clazz=this.getParent().loadClass(name);
        		if(clazz==null)
        			throw new ClassNotFoundException();
        		logger.info("当前父节点加载:"+name);
			} catch (Throwable e) {
				try {
					//自己加载类
					clazz = this.findClass(name);
					if(clazz==null)
	        			throw new ClassNotFoundException();
					logger.info("当前节点加载:"+name);
				} catch (Throwable e1) {
					try{
						clazz=parent.loadClass(name);
						if(clazz==null)
		        			throw new ClassNotFoundException();
						logger.info("当前线程加载:"+name);
					}catch (Throwable ee) {
						//
					}
				}
			}
			return clazz;
		}
	}
	
	//add by AyaKoizumi 101218
	public static AppUrlClassLoader getClassLoader(String classUrl,String subClassPath){
		URL url=null;
		try {
			url = new URL(classUrl);
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL[]urls=new URL[]{url};
		AppUrlClassLoader prodAttrloader=new AppUrlClassLoader();
		prodAttrloader.setUrl(urls);
		prodAttrloader.setClassPath(subClassPath);
		return prodAttrloader;
	}
}
