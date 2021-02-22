package com.ztesoft.net.outter.inf.iservice;

import java.util.List;
import java.util.Map;

import com.ztesoft.net.model.Outer;

public interface OuterInf {

	public List<Outer> executeInfService(String start_time,String end_time,Map params,String order_from) throws Exception;
	/**
	 * 回调方法
	 * @作者 MoChunrun
	 * @创建日期 2014-4-21 
	 * @param orderCollectList
	 */
	public void callback(List<Outer> list);
	
}
