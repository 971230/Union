package com.ztesoft.net.mall.core.service;

import java.util.List;

import params.adminuser.req.MblWorkLogVO;

import com.ztesoft.net.eop.resource.model.Access;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.MblLoginLogVO;
import com.ztesoft.net.mall.core.model.OfferVo;


public interface IMblLogManager {
	public void addLogin(MblLoginLogVO log);
	public Page loginLogList(String staff_name,String mobile,String staff_code,String start_time,String end_time,int page,int pageSize); 
	public Page workLogList(String business_name,String service_offer_name,String business_status,String phoneno,String username,String city,String start_time,String end_time,int page,int pageSize); 
	public void addWork(MblWorkLogVO work);
	public void addAccessLog(Access access);
	public Page staffList(String username,int page,int pageSize);
	public List<OfferVo> listType();
	public Page workList(String username,int page,int pageSize);
	public Page callerLog(String  start_time,String end_time, int page, int pageSize);
}
