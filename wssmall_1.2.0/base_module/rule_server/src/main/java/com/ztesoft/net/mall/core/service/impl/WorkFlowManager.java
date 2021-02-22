package com.ztesoft.net.mall.core.service.impl;

import zte.params.process.req.UosFlowListReq;
import zte.params.process.resp.UosFlowListResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.service.IWorkFlowManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 工作流实现类
 * @author xuefeng
 */
public class WorkFlowManager extends BaseSupport implements IWorkFlowManager {

	@Override
	public Page getWorkFlowPage(UosFlowListReq req) {
		Page page = new Page();
		ZteClient client = ClientFactory
				.getZteDubboClient(ManagerUtils.getSourceFrom());
		
		UosFlowListResp flowResp = client.execute(req, UosFlowListResp.class);
		page.setParam(flowResp.getPageIndex(), 
				flowResp.getTotalCount(), flowResp.getPageSize(), 
				flowResp.getFlowList());
		return page;
	}
}
