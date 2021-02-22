package com.ztesoft.net.mall.core.action.backend;

import javax.annotation.Resource;

import zte.params.process.req.UosFlowListReq;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.IWorkFlowManager;

/**
 * 工作流处理类
 */
public class WorkFlowAction extends WWAction{
	private static final long serialVersionUID = -2659792374836857169L;
	
	@Resource
	private IWorkFlowManager workFlowManager;
	
	private String flow_name;

	/**
	 * 查询工作流列表
	 * @return
	 */
	public String getWorkFlowPage(){
		UosFlowListReq req = new UosFlowListReq();
		req.setPageIndex(this.getPage());
		req.setPageSize(this.getPageSize());
		req.setProcessName(flow_name);
		this.webpage = workFlowManager.getWorkFlowPage(req);
		return "work_flow_list";
	}
	
	public String getFlow_name() {
		return flow_name;
	}
	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}
}
