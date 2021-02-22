package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * 工单相关订单列表查询接口
 * 
 * @author 宋琪
 * @date 2018年1月3日
 */
public class OrderListByWorkQueryReq extends ZteRequest {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "开始时间yyyymmdd", type = "String", isNecessary = "Y", desc = "开始时间")
	private String start_time;
	@ZteSoftCommentAnnotationParam(name = "结束时间yyyymmdd", type = "String", isNecessary = "Y", desc = "结束时间")
	private String end_time;
	@ZteSoftCommentAnnotationParam(name = "处理状态", type = "String", isNecessary = "Y", desc = "处理状态0 – 全部；1 – 未处理；2 – 已处理")
	private String status;
	@ZteSoftCommentAnnotationParam(name = "操作员手机号码", type = "String", isNecessary = "Y", desc = "操作员手机号码")
	private String operator_num;

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.queryOrderListByWork";
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperator_num() {
		return operator_num;
	}

	public void setOperator_num(String operator_num) {
		this.operator_num = operator_num;
	}

}
