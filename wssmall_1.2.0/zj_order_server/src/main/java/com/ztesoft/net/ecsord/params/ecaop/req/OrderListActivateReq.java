package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderQueryActivateInfo;

import params.ZteRequest;

public class OrderListActivateReq extends ZteRequest{
	
	private static final long serialVersionUID = 1L;
	
	
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name = "业务模板ID", type = "String", isNecessary = "Y", desc = "模板ID:01.身份证后六位和SIM卡后七位查询模板")
	private String templet_id;
	@ZteSoftCommentAnnotationParam(name = "查询参数", type = "String", isNecessary = "Y", desc = "查询参数")
	private OrderQueryActivateInfo query_info;

	
	
	public String getSource_system() {
		return source_system;
	}

	public void setSource_system(String source_system) {
		this.source_system = source_system;
	}

	public String getReceive_system() {
		return receive_system;
	}

	public void setReceive_system(String receive_system) {
		this.receive_system = receive_system;
	}

	public String getTemplet_id() {
		return templet_id;
	}

	public void setTemplet_id(String templet_id) {
		this.templet_id = templet_id;
	}

	public OrderQueryActivateInfo getQuery_info() {
		return query_info;
	}

	public void setQuery_info(OrderQueryActivateInfo query_info) {
		this.query_info = query_info;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.iservice.impl.ZJInfServices.queryOrderActivate";
	}

}
