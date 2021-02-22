package com.ztesoft.common.application;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;



public class AppContext {
	private static Logger logger = Logger.getLogger(AppContext.class);
public static void main(String []args){
	AppContext myapp=new AppContext();
	try {
		
		String code="new com.ztesoft.crm.business.common.query.SqlMapExe app;" +
	    "setField app null;" +
	    "setField app tempParam;" +
	    "exeret app getInstance null app tempParam;"+
		"loadInst app tempParam newApp;"+
		"setField newApp sql;" +
		"setField newApp tmpResult;" +
		"setString newApp sql=select 1 from dual;" +
		"exeret newApp queryForMapList sql newApp tmpResult;" +
		"getValue newApp tmpResult;" ;
		HashMap vo=new HashMap();
		myapp.process(code,vo);
		logger.info(vo);
	} catch (Throwable e) {
		//e.printStackTrace();
		logger.info(Error.getStackTraceAsString(e));
	} 
}
//定义执行类
private AppContext myapp=null;//保存所有执行的上下文数据
//定义类规格
private HashMap _clsApp=new HashMap();
//定义类实例(存放AppClass的实例:少字段)
private HashMap _instApp=new HashMap();

public HashMap getAllClsApp(){
	return this._clsApp;
}
public HashMap getAllInstApp(){
	return this._instApp;
}

//设置类规格(存放AppClass的实例:多字段)
public void addClass(AppClass inAppClass) throws Exception{
	if(inAppClass==null)throw new Exception("class不能为null");
	_clsApp.put(inAppClass.getClassName(),inAppClass);
}
//添加拦截器
//public void addIntercs(String interTime,String className,String methodName,Object _instObj) throws Exception{
//	AppClass _tmpApp= (AppClass)_clsApp.get(className);
//	Object []_objParam=null;
//	if(_tmpApp!=null && !_instApp.isEmpty()){
//		_tmpApp.addIntercs(interTime, className, methodName, _instObj);
//	}
//}
//添加类方法
public void addClassMethod(String className,String methodName,Object _instObj) throws Exception{
	AppClass _tmpApp= (AppClass)_clsApp.get(className);
	if(_tmpApp!=null && !_instApp.isEmpty()){
		_tmpApp.addMethod(className, methodName,methodName, _instObj);
	}
}

//添加类方法
public void addClassMethod(String className,String methodName) throws Exception{
	this.addClassMethod(className,methodName,"");
}
//添加类方法
public void addClassMethod(String className,String methodName,String _instObj) throws Exception{
	this.addClassMethod(className,methodName,_instObj,null);
}
//添加类方法
public void addClassMethod(String className,String methodName,String _instObj,String newMethodName) throws Exception{
	AppClass _tmpApp= (AppClass)_clsApp.get(className);
	Object []_objParam=null;
	if(_tmpApp!=null && !_instApp.isEmpty()){
		if(_instObj!=null && !_instObj.equals("")){
			String[]strbParam=_instObj.split(",");
			_objParam=new Object[strbParam.length];
			for(int i=0;i<strbParam.length;i++){
				_objParam[i]=_tmpApp.getValue(strbParam[i]);
			}
		}
		this.addClassMethod(className, methodName,_objParam,newMethodName);
	}
}
//添加类方法
public void addClassMethod(String className,String methodName,Object[] _instObj) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	addClassMethod(className,methodName,_instObj,null);
}
//添加类方法
public void addClassMethod(String className,String methodName,Object[] _instObj,String newMethodName) throws SecurityException, IllegalArgumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
	AppClass _tmpApp= (AppClass)_clsApp.get(className);
	if(_tmpApp!=null && !_instApp.isEmpty()){
		_tmpApp.addMethod(className, methodName, newMethodName,_instObj);
	}
}

//设置类规格(存放AppClass的实例:多字段)
public void addClass(String strAppClass) throws Exception{
	if(strAppClass==null || strAppClass.equals(""))throw new Exception("class不能为null");
	AppClass inAppClass=new AppClass();
	inAppClass.loadFromClass(strAppClass, null);
	this.addClass(inAppClass);
}

//设置类规格(存放AppClass的实例:多字段)
public void addClass(String strAppClass,Object _obj) throws Exception{
	if(strAppClass==null || strAppClass.equals(""))throw new Exception("class不能为null");
	AppClass inAppClass=new AppClass();
	inAppClass.loadFromClass(strAppClass, _obj);
	this.addClass(inAppClass);
}

public void setInst(String appName,Object _inst){
	//获取一个实例
	AppClass _tmpApp= (AppClass)_instApp.get(appName);
	_tmpApp.setInst(_inst);
}
//设置类规格(存放AppClass的实例:多字段)
public void addClass(String strAppClass,Object[]obj) throws Exception{
	if(strAppClass==null || strAppClass.equals(""))throw new Exception("class不能为null");
	AppClass inAppClass=new AppClass();
	inAppClass.loadFromClass(strAppClass, obj);
	this.addClass(inAppClass);
}
//获取类实例
public void newInstance(String className,String instanceName) throws Exception{
	AppClass _tmpApp= (AppClass)_instApp.get(instanceName);
	if(_tmpApp==null){
		this.setNewInstance(className, instanceName);
		_tmpApp= (AppClass)_instApp.get(instanceName);
	}
}
//获取类实例
public AppClass getInstance(String instanceName) throws Exception{
	return (AppClass)_instApp.get(instanceName);
}

//判断是否有这个类实例
public boolean isInstance(String instanceName) throws Exception{
	return _instApp.containsKey(instanceName);
}

//设置类实例的名字
private void setNewInstance(String className,String instanceName) throws Exception{
	if(_instApp.containsKey(instanceName))throw new Exception("实例名已经存在");
	//查找是否有这个类名
	AppClass _class=this.ClassForName(className);
	//复制一个实例并放在数组里
	AppClass _inst=(AppClass)_class.clone();
	_instApp.put(instanceName, _inst);
}
//返回一个AppClass实例
private AppClass ClassForName(String className) throws Exception{
	if(className==null || className.equals(""))throw new Exception("ClassNotFoundException");
	if(_clsApp==null || _clsApp.isEmpty())throw new Exception("ClassNotFoundException");
	AppClass _class=(AppClass)_clsApp.get(className);
	if(_class==null)throw new Exception("ClassNotFoundException");
	return _class;
}

//调用业务处理类的方法
public HashMap process(String acceptService,HashMap vo) throws Exception{
	if(acceptService==null || acceptService.equals(""))return null;
	String[]strAcceptService=acceptService.split(";");
	ArrayList _acceptService=new ArrayList();
	Complier mytrim=new Complier();
	for(int i=0;i<strAcceptService.length;i++){
		_acceptService.add(mytrim.trimSpace(strAcceptService[i]));
	}
	return this.process(null,_acceptService,vo);
}

//调用业务处理类的方法
public HashMap process(AppContext app,String acceptService,HashMap vo) throws Exception{
	if(acceptService==null || acceptService.equals(""))return null;
	String[]strAcceptService=acceptService.split(";");
	ArrayList _acceptService=new ArrayList();
	for(int i=0;i<strAcceptService.length;i++){
		_acceptService.add(strAcceptService[i]);
	}
	return this.process(app,_acceptService,vo);
}

//把外部引用导入当前类
public void loadOriFields(String appName,HashMap vo) throws Exception{
	if(myapp==null)myapp=new AppContext();
	AppClass _bo=myapp.getInstance(appName);
	Iterator it=vo.entrySet().iterator();
	while(it.hasNext()){
		Map.Entry map=(Map.Entry)it.next();
		String key=(String)map.getKey();
		Object val=map.getValue();
		if(!_bo.isContainField(key))
		_bo.setField("public String "+key+"=null");
		_bo.setValue(key, val);
	}	
}
//调用业务处理类的方法
public HashMap process(String acceptService) throws Exception{
	if(acceptService==null || acceptService.equals(""))return null;
	String[]strAcceptService=acceptService.split(";");
	ArrayList _acceptService=new ArrayList();
	for(int i=0;i<strAcceptService.length;i++){
		_acceptService.add(strAcceptService[i]);
	}
	return this.process(null,_acceptService,null);
}


//调用业务处理类的方法 vo是传入的一个引用
/*代码执行示例:
 * 
 * //新建类实例 new
	_lstClass.add("new com.ztesoft.common.Application.AppTest myapp");
	//设置类字段 setField
	_lstClass.add("setField myapp b");
	//设置类字段值 setValue
	_lstClass.add("setValue myapp b=\"mzptest\"");
	//执行类方法 exenon,参数是b,无返回值
	_lstClass.add("exenon myapp print b");
	//添加类方法 addmethod 类名是:AppTest 方法名是:print 类AppTest的实例的构造函数参数是c
	//_lstClass.add("addmothod myapp com.ztesoft.common.Application.AppTest print c");
	//对方法print设置调用前拦截器,拦截器调用的是类a的方法b,构造函数参数是c
	_lstClass.add("addbfinterc myapp print a b c;");
	//对方法print设置调用后拦截器,拦截器调用的是类a的方法b,构造函数参数是c
	_lstClass.add("addafinterc myapp print a b c;");
	//执行类方法 exeret,参数是a,并把值放在类字段b里
	_lstClass.add("exeret myapp print a b");
	//添加类方法 gUtils.commonDAO_ods是字段别名(需要预先有具体的实例) preUpdate类方法 mypreUpdate类方法别名
	_lstClass.add("setMethod myapp gUtils.commonDAO_ods preUpdate mypreUpdate;");
	//myapp是一个类实例，意思是添加一个myapp实例的类规格，并且把传入的vo里带的这个实例vo.get("myapp")的引用传给当前容器，只要执行这个类的方法就是用这个传入的实例去执行
	_lstClass.add("setInst myapp");
	//获取类实例myapp的a字段的值
	_lstClass.add("getValue myapp a");
 * 
 * */
private HashMap process(AppContext parentApp, ArrayList acceptService,HashMap vo) throws Exception{
	HashMap hsRet=vo;
	String errCode="";
	try{
	if(parentApp!=null)//如果有传app进来,就用传进来的app执行业务代码
	myapp=parentApp;
	//定义偏移量,用于支持goto语句
	boolean isFalse=true;
	if(acceptService!=null && !acceptService.isEmpty()){
		if(myapp==null)myapp=new AppContext();
		for(int i=0;i<acceptService.size();i++){
			//解析执行业务代码
			String _current=(String)acceptService.get(i);
//			logger.info("\n"+_current);
			errCode=_current;
			String []_code=_current.split(" ");
			String _head=_code[0];
			if(_head.equals("else")){
				isFalse=true;
				continue;
			}
			if(!isFalse)continue;
			
			if(_head.equals("load")){//如果是load代码,那么会把传入的vo的值赋值给一个对象实例
				//在这里特殊处理,因为传入了一个引用,需要把这个引用放在具体的一个对象的字段里,所以在这里写死了取vo的key和val
				if(vo!=null && !vo.isEmpty()){
					Iterator it=vo.entrySet().iterator();
					while(it.hasNext()){
						Map.Entry map=(Map.Entry)it.next();
						String []appNameField=((String)map.getKey()).split("/");
						String appName=appNameField[0];
						if(!myapp.isInstance(appName)){
							continue;//只要没有这个实例。
						}
						AppClass _bo=myapp.getInstance(appName);//获取一个实例化的class
						String fieldName=appNameField[1];
						//定义类字段
						if(!_bo.isField(fieldName)){
							_bo.setField("public String "+fieldName+"=null");
						}
						//设置类字段值
						_bo.setValue(fieldName, map.getValue());
					}
				}
				continue;
			}
			if(_head.equals("loadInst")){//如果是loadInst myapp a newApp
				//获取对象实例
				AppClass _bo=myapp.getInstance(_code[1]);
				Object _res=_bo.getValue(_code[2]);
				if(_res!=null){
					Class _clazz=_res.getClass();
					String _className=_clazz.getName();
					if(!_clsApp.containsKey(_className))
						myapp.addClass(_className,_res);//添加类规格+外部实例引用
					//实例化类规格
					myapp.newInstance(_className, _code[3]);
				}
				continue;
			}
			if(_head.equals("new")){//如果是new com.ztesoft.common.Application.AppTest myapp这样的形式
				if(_code.length==3){
					//判断是否有这个类规格,如果有,就不要添加了
					if(!_clsApp.containsKey(_code[1]))
					myapp.addClass(_code[1]);//添加类规格
					myapp.newInstance(_code[1], _code[2]);//实例化类规格
				}
				if(_code.length==5){//new com.ztesoft.common.Application.AppTest myapp2 myapp b
					AppClass _bo=myapp.getInstance(_code[3]);
					String []paramVal=_code[4].split(",");
					Object []paramObj=new Object[paramVal.length];
					for(int j=0;j<paramVal.length;j++){
						paramObj[j]=_bo.getValue(paramVal[j]);
					}
					myapp.addClass(_code[1], paramObj);
					myapp.newInstance(_code[1], _code[2]);//实例化类规格
				}
				
				continue;
			}
			if(_head.equals("setField")){//如果是setField myapp b这样的形式获取是setField myapp b=a
				AppClass _bo=myapp.getInstance(_code[1]);
				String _val=_code[2];
				String []val=_val.split("=");
				if(val.length==2){
					_bo.setField("public String "+val[0]+"=null");
					//获取val[1]的真实引用 val[1]是这样的形式app.field获取this或者asdfsdf
					String _value=val[1];
					//判断是否有.
					if(_value.indexOf("/")>=0){//如果是app.field
						//_value=_value.replaceAll(".", "/");//替换成app/field
						String [] _tmpApp=_value.split("/");
						AppClass _co=myapp.getInstance(_tmpApp[0]);//app
						if(_co.isContainField(_tmpApp[1])){//field
							_bo.setValue(val[0], _co.getValue(_tmpApp[1]));
						}
					}else{
						if(_value.equals("this")){//如果是this
							_bo.setValue(val[0], myapp);
						}else{//如果是asdfdsf
							_bo.setValue(val[0], _value);
						}
					}
				}else{
					_bo.setField("public String "+_code[2]+"=null");
				}
				continue;
			}
			if(_head.equals("setValue")){//如果是setValue myapp b=app.field"这样的形式
				AppClass _bo=myapp.getInstance(_code[1]);
				String secondSegment=_code[2];
				String[]fieldVal=secondSegment.split("=");
				String key=fieldVal[0].trim();
				String val=fieldVal[1].trim();
				//如果val是一个引用别名,那么形式应该是app.field
				String _tmpval=val;
				String [] _tmpApp=_tmpval.split("/");
				if(_tmpApp.length==2){//如果是setField myapp b=app.field"这样的形式
					AppClass _co=myapp.getInstance(_tmpApp[0]);//app
					if(_co.isContainField(_tmpApp[1])){//field
						_bo.setValue(key, _co.getValue(_tmpApp[1]));
					}
				}
				if(_tmpApp.length==1){//如果是setValue myapp b=this"或setValue myapp b=sdfdsfsd这样的形式
					if(_tmpval!=null && _tmpval.equals("this")){//如果是this,那么就把当前的引用赋给这个字段
						_bo.setValue(key.trim(), myapp);
//						hsRet.put(key.trim(),myapp);
					}else{
						//需要判断_tmpval是否是一个实例的名字,如果是,那么需要把这个实例的引用取出来
						AppClass _co=myapp.getInstance(_tmpval);//app
						if(_co==null)
							_bo.setValue(key, _tmpval);//直接赋值字符串
						else{//把当前实例名的真实对象实例引用赋值给_bo实例的key字段
							_bo.setValue(key, _co.getInst());
						}
					}
				}

				continue;
			}
			if(_head.equals("setString")){//如果是setString myapp b=asdf sd"这样的形式
				AppClass _bo=myapp.getInstance(_code[1]);
				String secondSegment=_code[2];
				int equalidx=secondSegment.indexOf("=");
				String key=secondSegment.trim().substring(0, equalidx);
				String val=secondSegment.trim().substring(equalidx+1);
				StringBuffer strbVal=new StringBuffer(val);
				if(_code.length>3){
					for(int j=3;j<_code.length;j++){
						strbVal.append(" ");
						strbVal.append(_code[j]);
					}
				}
//				logger.info(strbVal.toString());
				_bo.setValue(key, strbVal.toString());
				continue;
			}
			if(_head.equals("exenon")){//如果是exenon myapp print a这样的形式:执行myapp实例的print方法,方法参数是当前myapp实例的a字段
				AppClass _bo=myapp.getInstance(_code[1]);
				_bo.executeMethod(_code[2], _code[3]);
				continue;
			}
			if(_head.equals("exeret")){//如果是exeret myapp print a b c这样的形式:执行myapp实例的print方法,方法参数是当前myapp实例的a字段,并把执行后返回的值赋值给b实例的c字段
				AppClass _bo=myapp.getInstance(_code[1]);//执行方法的实例
				AppClass _co=myapp.getInstance(_code[4]);//setvalue的实例
				_co.setValue(_code[5], _bo.executeMethod(_code[2], _code[3]));
//				hsRet.put(_code[5], _co.getValue(_code[5]));
				continue;
			}
			if(_head.equals("addbfinterc")){//如果是addbfinterc myapp print a b c;这样的形式
				//对方法print设置调用前拦截器,拦截器调用的是类a的方法b,构造函数参数是c
				String c=null;
				if(_code.length==6)
					c=_code[5];
				AppClass _bo=myapp.getInstance(_code[1]);
				String interTime="bf_"+String.valueOf(System.currentTimeMillis());//拦截时机是bf_+原来被拦截的类名+被拦截的方法名
				_bo.addIntercs(interTime,_bo.getClassName(),_code[2],_code[3], _code[4], c);
				continue;
			}
			if(_head.equals("addafinterc")){//如果是addbfinterc myapp print a b c;这样的形式
				//对方法print设置调用后拦截器,拦截器调用的是类a的方法b,构造函数参数是c
				String c=null;
				if(_code.length==6)
					c=_code[5];
				AppClass _bo=myapp.getInstance(_code[1]);
				String interTime="af_"+String.valueOf(System.currentTimeMillis());//拦截时机是bf_+原来被拦截的类名+被拦截的方法名
				_bo.addIntercs(interTime,_bo.getClassName(),_code[2],_code[3], _code[4], c);
				continue;
			}
			if(_head.equals("eval")){//如果是eval myapp appContext
				//把appContext的字符串执行,并把执行的结果的真实引用映射到当前执行的myapp里来
				AppClass _bo=myapp.getInstance(_code[1]);
				String _strCode=(String)_bo.getValue(_code[2]);//获取实例对象,一段可执行的代码
				myapp.process(myapp,_strCode,vo);//使用当前的app执行这段代码
				continue;
			}
			if(_head.equals("getValue")){//如果是getValue myapp a这样的形式
				AppClass _bo=myapp.getInstance(_code[1]);
				hsRet.put(_code[2], _bo.getValue(_code[2]));
				continue;
			}
			if(_head.equals("setList")){//如果是setList myapp a c,d,e这样的形式
				AppClass _bo=myapp.getInstance(_code[1]);
				ArrayList _lst=new ArrayList();
				String _key=_code[2];
				String _val=_code[3];
				String[]__val=_val.split(",");
				if(__val!=null && __val.length>0){
					for(int j=0;j<__val.length;j++){
						String _obj=(String)_bo.getValue(__val[j]);
						_lst.add(_obj);
					}
				}
				_bo.setValue(_key, _lst);
				continue;
			}
			if(_head.equals("if")){//如果是if myapp a myapp b goto c这样的形式
				AppClass _lBo=myapp.getInstance(_code[1]);
				Object _objl=_lBo.getValue(_code[2]);
				AppClass _rBo=myapp.getInstance(_code[3]);
				Object _objr=_rBo.getValue(_code[4]);
				if(_objl!=null && _objr!=null){//如果相等,就置偏移量
					if(_objl.equals(_objr)){
						isFalse=true;
					}else{
						isFalse=false;
					}
				}
				continue;
			}
			if(_head.equals("Object")){//如果是Object myapp a myapp b这样的形式
				AppClass _bo=myapp.getInstance(_code[1]);
				Object val=_bo.getValue(_code[2]);
				AppClass _co=myapp.getInstance(_code[3]);
				Object[]_obj={val};
				_co.setValue(_code[4], _obj);
				continue;
			}
		}
	}
	}
	catch(Throwable ex){
		String errMsg="\n"+errCode+"\n"+Error.getStackTraceAsString(ex);
		throw new Exception(errMsg,ex);
	}
	return hsRet;
}


}
