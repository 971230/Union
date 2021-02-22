package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;

import java.util.List;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderDetailVO;
import com.ztesoft.net.framework.database.NotDbField;

public class OrderDetailReq extends ZteRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "receive_system：接收方系统标识")
	private String receive_system;

	@ZteSoftCommentAnnotationParam(name = "查询参数", type = "", isNecessary = "Y", desc = "query_info：查询参数")
	private List<OrderDetailVO> query_info;

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.queryOrderDetailByRequ";
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	public List<OrderDetailVO> getQuery_info() {
		return query_info;
	}

	public void setQuery_info(List<OrderDetailVO> query_info) {
		this.query_info = query_info;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

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

}
