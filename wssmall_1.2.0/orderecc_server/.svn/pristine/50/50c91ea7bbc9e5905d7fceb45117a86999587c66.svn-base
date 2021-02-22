package com.ztesoft.check.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ztesoft.form.util.StringUtil;

public class IdentifyReq {
	private String order_id;
	private String exe_time;
	private String tache_code;
	private HashMap extParam;
	private String biz_id;
	private String fun_id;
	private String impl;
	private String clazz;
	private String inf_log_id;		//接口日志id
	
	public Map getFullMapParam(){
		Map map = new HashMap();		
		map.put("order_id", order_id);
		map.put("exe_time", exe_time);
		map.put("tache_code", tache_code);
		map.put("biz_id", biz_id);
		map.put("fun_id", fun_id);
		map.put("impl", impl);
		map.put("clazz", clazz);
		map.put("impl", impl);
		if(null != extParam && !extParam.isEmpty()){
			map.putAll(extParam);
		}
		return map;
	}
	//  cust_id,1234455~cust_name,frank
	public String getStrParam(){
		String str = "";
		if(null != extParam && !extParam.isEmpty()){
	        for (Iterator it = extParam.entrySet().iterator(); it.hasNext();)
	        {
	            Map.Entry map = (Map.Entry) it.next();
	            String key = (String) map.getKey();
	            Object val = map.getValue()==null?"":map.getValue();
	            String v = key +","+val;
	            if(StringUtil.isEmpty(str)){
	            	str = v;
	            }else{
	            	str = str+"~"+v;
	            }
	        }
		}
		return str;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getExe_time() {
		return exe_time;
	}
	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}
	public String getTache_code() {
		return tache_code;
	}
	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}
	public HashMap getExtParam() {
		return extParam;
	}
	public void setExtParam(HashMap extParam) {
		this.extParam = extParam;
	}
	public String getBiz_id() {
		return biz_id;
	}
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	public String getFun_id() {
		return fun_id;
	}
	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
	}
	public String getImpl() {
		return impl;
	}
	public void setImpl(String impl) {
		this.impl = impl;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getInf_log_id() {
		return inf_log_id;
	}
	public void setInf_log_id(String inf_log_id) {
		this.inf_log_id = inf_log_id;
	}
	
}
