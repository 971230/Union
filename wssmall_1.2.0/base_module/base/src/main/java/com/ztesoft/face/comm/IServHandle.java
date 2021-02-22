
package com.ztesoft.face.comm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.face.comm.FaceContext.ContextObj;
import com.ztesoft.face.vo.OperLog;
import com.ztesoft.form.processor.Processor;
import com.ztesoft.common.util.PageModel;

/**
 * @author Reason.Yea
 * @version Created Feb 5, 2013
 */
public abstract class IServHandle {
	
	public Logger logger = Logger.getLogger(this.getClass());
	//方法缓存
	private Map methodCache=new HashMap();
	
	public ContextObj context;
	public SqlMapExe sqlExe;
	public OperLog operLog;
	//处理
	public void exec(){
		this.operLog=new OperLog();
		this.context = FaceContext.get();
		this.sqlExe = getSqlExe();
		if(this.check()){
			this.process();
			ServHelper.log(this,operLog);
		}
	}

	/**
	 * 实际的方法处理
	 */
	private void process() {
		Method method = null;
		String curMethodName = getMethodName();
		if(this.methodCache.get(curMethodName)!=null){
			method = (Method) this.methodCache.get(curMethodName);
		}else{
			Method[] methods = this.getClass().getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method meth = methods[i];
				if (meth.getName().equals(curMethodName)) {
					method = meth;
					this.methodCache.put(curMethodName, meth);
					break;
				}
			}
		}
		
		try {
			Object obj = method.invoke(this, null);
			if(obj instanceof PageModel) {
				PageModel pm = (PageModel) obj;
				HashMap map = new HashMap();
				map.put("total", ""+pm.getTotalCount());
				map.put("rows", pm.getList());
				context.getMap().put("result", map);
			}else{
				context.getMap().put("result", obj);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 权限校验
	 */
	public boolean check(){
//		logger.info("IServHandle check==========");
		return true;
	} 
	

	
	public String getMethodName() {
		Object me = getVal(AServ.METHOD);
		return me==null?"":(String)me;
	}
	
	public String getModuleName() {
		Object me = getVal(AServ.MODULE);
		return me==null?"":(String)me;
	}

	private SqlMapExe getSqlExe() {
		return context.getSqlExe();
	}
	
	public Object getObj(String name){
		return context.getMap().get(name)==null?
				context.getMap().get(name.toUpperCase()):
					context.getMap().get(name);
	}
	
	public String getVal(String name){
		return getObj(name)==null?"":(String)getObj(name);
	}
	
	public int getInt(String name){
		String val = getVal(name);
		return Integer.parseInt(val);
	}
	
	public void setVal(String name,Object value){
		context.getMap().put(name.toUpperCase(), value);
	}
	
	public static final String EASY_UI_PAGE_INDEX="PAGE";
	public static final String EASY_UI_PAGE_SIZE="ROWS";
	public int getPageIndex(){
		int pi = getInt(EASY_UI_PAGE_INDEX);
		return pi;
	}
	public int getPageSize(){
		int pi = getInt(EASY_UI_PAGE_SIZE);
		return pi;
	}
	/**
	 * 获取Action对象
	 * @return
	 */
	public Processor getProcessor(){
		
		return (Processor)getObj("processor");
	}
	
}
