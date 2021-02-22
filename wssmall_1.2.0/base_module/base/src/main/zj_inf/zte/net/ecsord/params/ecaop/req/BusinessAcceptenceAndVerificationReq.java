package zte.net.ecsord.params.ecaop.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class BusinessAcceptenceAndVerificationReq extends ZteRequest {
	
	
	/**
	 * 2.1.15	续约活动校验和受理接口 请求
	 */
	private static final long serialVersionUID = -5370766455278927937L;
	
	@ZteSoftCommentAnnotationParam(name = "内部订单号 ", type = "String", isNecessary = "Y", desc = "notNeedReqStrOrderId：内部订单号 ")
	private String notNeedReqStrOrderId;

	@ZteSoftCommentAnnotationParam(name="查询类型",type="String",isNecessary="Y",desc="非空[1] (1=服务号码2=上网账号)")
	private String query_type;
	
	@ZteSoftCommentAnnotationParam(name="服务号码/上网账号",type="String",isNecessary="Y",desc="非空[30]")
	private String query_data;
	
	@ZteSoftCommentAnnotationParam(name="提交模式",type="String",isNecessary="Y",desc="非空[1](0=预提交1=正式提交2=订单作废3=精准校验)，非空")
	private String submit_type;
	
	@ZteSoftCommentAnnotationParam(name="活动编码",type="String",isNecessary="Y",desc="非空[8]")
	private String scheme_id;
	
	@ZteSoftCommentAnnotationParam(name="预提交订单号",type="String",isNecessary="Y",desc="预提交订单号[17]")
	private String pre_submit_orderID;
	
	@ZteSoftCommentAnnotationParam(name="渠道类型",type="String",isNecessary="N",desc="渠道类型[10],可空")
	private String channel_type;
	
	@ZteSoftCommentAnnotationParam(name="第一发展人",type="String",isNecessary="N",desc="第一发展人[20],可空")
	private String developer_first;
	
	@ZteSoftCommentAnnotationParam(name="第二发展人",type="String",isNecessary="N",desc="第二发展人[20],可空")
	private String developer_second;
	
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="N",desc="产品ID[20],可空")
	private String product_id;
	
	@ZteSoftCommentAnnotationParam(name="营业点",type="String",isNecessary="N",desc="营业点,[可空]")
	private String office_id;
	
	@ZteSoftCommentAnnotationParam(name="营业员",type="String",isNecessary="N",desc="营业员,[可空]")
	private String operator_id;
	
	@ZteSoftCommentAnnotationParam(name="备用1",type="String",isNecessary="N",desc="备用1[40] packageflow表示订购流量半年包")
	private String standby1;
	
	@ZteSoftCommentAnnotationParam(name="备用2",type="String",isNecessary="N",desc="备用2[40] 终端串号")
	private String standby2;
	
	@ZteSoftCommentAnnotationParam(name="备用3",type="String",isNecessary="N",desc="备用3[40]")
	private String standby3;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getQuery_data() {
		return query_data;
	}

	public void setQuery_data(String query_data) {
		this.query_data = query_data;
	}

	public String getSubmit_type() {
		return submit_type;
	}

	public void setSubmit_type(String submit_type) {
		this.submit_type = submit_type;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getPre_submit_orderID() {
		return pre_submit_orderID;
	}

	public void setPre_submit_orderID(String pre_submit_orderID) {
		this.pre_submit_orderID = pre_submit_orderID;
	}

	public String getChannel_type() {
		return channel_type;
	}

	public void setChannel_type(String channel_type) {
		this.channel_type = channel_type;
	}

	public String getDeveloper_first() {
		return developer_first;
	}

	public void setDeveloper_first(String developer_first) {
		this.developer_first = developer_first;
	}

	public String getDeveloper_second() {
		return developer_second;
	}

	public void setDeveloper_second(String developer_second) {
		this.developer_second = developer_second;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getStandby1() {
		return standby1;
	}

	public void setStandby1(String standby1) {
		this.standby1 = standby1;
	}

	public String getStandby2() {
		return standby2;
	}

	public void setStandby2(String standby2) {
		this.standby2 = standby2;
	}

	public String getStandby3() {
		return standby3;
	}

	public void setStandby3(String standby3) {
		this.standby3 = standby3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		
			return "com.zte.unicomService.zj.BusinessAcceptenceAndVerification";
		
	}

}
