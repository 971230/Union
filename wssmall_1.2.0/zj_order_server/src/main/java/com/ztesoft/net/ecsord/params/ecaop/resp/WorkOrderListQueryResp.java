package com.ztesoft.net.ecsord.params.ecaop.resp;

import java.util.List;
import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 工单列表查询
 * 
 * @author 宋琪
 * @date 2017年12月1日
 */
public class WorkOrderListQueryResp extends ZteResponse {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "1失败、0成功")
	private String resp_code;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "失败原因")
	private String resp_msg;

	@ZteSoftCommentAnnotationParam(name = "返回结果组", type = "List", isNecessary = "Y", desc = "返回结果组")
	private List<Map<String, Object>> work_order_list;

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

	public List<Map<String, Object>> getWork_order_list() {
		return work_order_list;
	}

	public void setWork_order_list(List<Map<String, Object>> work_order_list) {
		this.work_order_list = work_order_list;
	}

}
