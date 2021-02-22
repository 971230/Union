package com.ztesoft.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Iterator;
import java.util.Map;


/**
 * 安全的httprequest包装器
 * @author wui
 *
 */
public class SafeHttpRequestWrapper extends HttpServletRequestWrapper {

	public SafeHttpRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 对字符进行安全过滤
	 * @param value
	 * @return
	 */
	private String safeFilter(String value){
		if(value==null) return null;
		//防止sql注入的过滤
	 	value=value.replaceAll("\\'", "‘");
	 	value=value.replaceAll("--", "－－");
	 	value=value.replaceAll("\\*", "×");
	 	value=value.replaceAll("\\=", "＝");
	 	
	 	//防止跨站点脚本攻击的过滤
	 	value=value.replaceAll("<", "&lt;");
		return value;
	}
	
	/**
	 * 对字串数组进行安全过滤
	 * @param values
	 */
	private void safeFilter(String[] values){
		 if(values!=null){
			 for(int i=0;i<values.length;i++){
				 values[i]= this.safeFilter(  values[i] );
			 }
		 }
	}
	
	@Override
	public String getParameter(String name) {
		String value = 	super.getParameter(name);
		value = this.safeFilter(value);
		return value;
	}

	
	@Override
	public Map getParameterMap() {
		 Map map =super.getParameterMap();
		 Iterator keiter = map.keySet().iterator();
		 while(keiter.hasNext()){
			 String name=keiter.next().toString();
			 Object value  =map.get(name);
			 if(value instanceof String){
				 value =  this.safeFilter( ((String)value) );
			 }
			 if(value instanceof String[]){
				 String[] values = (String[])value;
				 this.safeFilter(values);
			 }
			 
		 }
		return map;
	}
	public void transferParamterToAttr(){
		Map parMap =getParameterMap();
		Iterator keiter = parMap.keySet().iterator();
		 while(keiter.hasNext()){
			 String name=keiter.next().toString();
			 Object value  =parMap.get(name);
			 String p_value ="";
			 if(value instanceof String){
				 p_value =  (String)value;
			 }
			 if(value instanceof String[]){
				 String[] values = (String[])value;
				 String prefix =",";
				 for (int i = 0; i < values.length; i++) {
					 if(i==values.length-1)
						 prefix ="";
					 p_value+=values[i]+prefix;
				}
			 }
			 this.setAttribute(name,p_value);
		 }
		
	}
	
	@Override
	public String[] getParameterValues(String arg0) {
		 String[] values = super.getParameterValues(arg0);
		 this.safeFilter( values );
		return values;
	}


}
