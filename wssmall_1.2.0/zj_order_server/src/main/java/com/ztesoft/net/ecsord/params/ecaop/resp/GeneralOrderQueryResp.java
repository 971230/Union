package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.generalorderinfo.GeneralOrderInfo;

import params.ZteResponse;

public class GeneralOrderQueryResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="返回代码",type="String",isNecessary="N",desc="1未查询到意向单0查询成功-1 系统异常")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="N",desc="失败原因")
	private String resp_msg;
	@ZteSoftCommentAnnotationParam(name="总记录数",type="String",isNecessary="Y",desc="总记录数")
	private String total_count;
	@ZteSoftCommentAnnotationParam(name="信息",type="String",isNecessary="N",desc="信息")
	private List<GeneralOrderInfo> order_info;//需要调整
	
	public String getResp_code() {
		return resp_code;
	}
	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}
	public String getResp_msg() {
		return resp_msg;
	}
	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}
	public List<GeneralOrderInfo> getOrder_info() {
		return order_info;
	}
	public void setOrder_info(List<GeneralOrderInfo> order_info) {
		this.order_info = order_info;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	
	
}
