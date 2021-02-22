package com.ztesoft.gray.service;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.net.framework.database.Page;

public interface IRouterGrategyCfgManager {

	public Page qryRouterGrategyCfg(HashMap param, int page, int pageSize);
	public String qrySeparateOnOff() throws Exception  ;
	public void separateSet(String separate_status) throws Exception  ;

	public String operPolicy(HashMap param) throws Exception ;	
	public String initPolicy() throws Exception  ;
	public String initRunPolicy() throws Exception  ;
	public List getOrderEnvByOrderId(String order_id);
	public void setLimitCount(String limit_count,String execed_count)throws Exception;
	public int getLimitCount()throws Exception;//限流数
	public int getExecedCount()throws Exception;//已分流数
}
