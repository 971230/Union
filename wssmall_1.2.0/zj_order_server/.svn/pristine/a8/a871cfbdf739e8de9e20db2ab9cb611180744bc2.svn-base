package com.ztesoft.net.service;


import java.io.File;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.AuditZBParams;
import com.ztesoft.net.model.AuditZBVO;

public interface IAuditZBManager {
	
	public Page showList(AuditZBParams auditZBParams,int pageNo, int pageSize);//列表查询

	public String importacion(File file, String file_name) ; //导入
	
	public void  auditByHand(String out_tids) ;//人工审核
	
	public String checkStatus(String out_tids);//校验是否可以批量审核
	
	public String getOrderDesc(String order_id,String flow_trace_id);

	public Page qryBSSOrderStatus(AuditZBParams auditZBParams,int pageNo, int pageSize);
	
}
