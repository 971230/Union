package com.ztesoft.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.ecsord.params.ecaop.vo.OrderResultQueryInfo;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;

/**
 * 2.3.8. 订单收单结果查询接口
 * 
 * @author 宋琪
 * @date 2017年6月29日
 */
public class OrderResultQueryReq extends ZteRequest {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "serial_no:序列号")
	private String serial_no;
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "time：时间")
	private String time;
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "source_system：发起方系统标识")
	private String source_system;
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "receive_system：接收方系统标识")
	private String receive_system;
	@ZteSoftCommentAnnotationParam(name = "外系统单号", type = "String", isNecessary = "Y", desc = "mall_order_id：外系统单号")
	private String mall_order_id; // 新增外部单号
	@ZteSoftCommentAnnotationParam(name = "查询参数", type = "OrderResultQueryInfo", isNecessary = "Y", desc = "query_info：查询参数")
	private OrderResultQueryInfo query_info;

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

	public String getMall_order_id() {
		return mall_order_id;
	}

	public void setMall_order_id(String mall_order_id) {
		this.mall_order_id = mall_order_id;
	}

	public OrderResultQueryInfo getQuery_info() {
		return query_info;
	}

	public void setQuery_info(OrderResultQueryInfo query_info) {
		this.query_info = query_info;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZJInfServices.queryOrderResultByRequ";
	}

}
