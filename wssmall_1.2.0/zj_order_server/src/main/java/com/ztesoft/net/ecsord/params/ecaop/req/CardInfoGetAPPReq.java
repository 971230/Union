package com.ztesoft.net.ecsord.params.ecaop.req;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;

public class CardInfoGetAPPReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name = "序列号", type = "String", isNecessary = "Y", desc = "卡信息获取->serial_no:唯一的接口流水号  ")
	private String serial_no = "";
	@ZteSoftCommentAnnotationParam(name = "时间", type = "String", isNecessary = "Y", desc = "卡信息获取->Time:yyyymmddhhmiss  ")
	private String Time = "";
	@ZteSoftCommentAnnotationParam(name = "发起方系统标识", type = "String", isNecessary = "Y", desc = "卡信息获取->source_system: 见附录系统类别")
	private String source_system = "";
	@ZteSoftCommentAnnotationParam(name = "接收方系统标识", type = "String", isNecessary = "Y", desc = "卡信息获取->receive_system: 见附录系统类别")
	private String receive_system = "";
	@ZteSoftCommentAnnotationParam(name = "业务类型", type = "String", isNecessary = "Y", desc = "卡信息获取->busi_type:同步时业务类型 01 宽带开通类 02 活动受理类 03 号卡开通类")
	private String busi_type = "";
	@ZteSoftCommentAnnotationParam(name = "业务号码", type = "String", isNecessary = "Y", desc = "卡信息获取->service_num:业务号码  ")
	private String service_num = "";
	@ZteSoftCommentAnnotationParam(name = "iccid", type = "String", isNecessary = "Y", desc = "卡信息获取->iccid  ")
	private String ICCID = "";
	@ZteSoftCommentAnnotationParam(name = "订单id", type = "String", isNecessary = "Y", desc = "卡信息获取->order_id:订单中心单号 订单中心单号")
	private String order_id = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作员", type = "String", isNecessary = "N", desc = "卡信息获取->deal_operator: 办理操作员")
	private String deal_operator = "";
	@ZteSoftCommentAnnotationParam(name = "办理操作点", type = "String", isNecessary = "N", desc = "卡信息获取->deal_office_id: 办理操作点")
	private String deal_office_id = "";
	@ZteSoftCommentAnnotationParam(name = "地区编码", type = "String", isNecessary = "Y", desc = "白卡获取->region_id:地区编码")
	private String region_id = "";
	
	
	
	public String getRegion_id() {
		String region_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.ORDER_CITY_CODE);
		region_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.BSS_AREA_CODE, region_id);
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
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

	public String getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	public String getService_num() {
		return service_num;
	}

	public void setService_num(String service_num) {
		this.service_num = service_num;
	}

	public String getICCID() {
		return ICCID;
	}

	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getDeal_operator() {
		return deal_operator;
	}

	public void setDeal_operator(String deal_operator) {
		this.deal_operator = deal_operator;
	}

	public String getDeal_office_id() {
		return deal_office_id;
	}

	public void setDeal_office_id(String deal_office_id) {
		this.deal_office_id = deal_office_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
				return "com.ztesoft.net.ecsord.params.ecaop.req.CardInfoGet";
	}
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setSerial_no((map.get("serial_no") == null?"":(map.get("serial_no").toString())));
		setTime((map.get("Time") == null?"":(map.get("Time").toString())));
		setSource_system((map.get("source_system") == null?"":(map.get("source_system").toString())));
		setReceive_system((map.get("receive_system") == null?"":(map.get("receive_system").toString())));
		setBusi_type((map.get("busi_type") == null?"":(map.get("busi_type").toString())));
		setService_num((map.get("service_num") == null?"":(map.get("service_num").toString())));
		setICCID((map.get("ICCID") == null?"":(map.get("ICCID").toString())));
		setOrder_id((map.get("order_id") == null?"":(map.get("order_id").toString())));
		setDeal_operator((map.get("deal_operator") == null?"":(map.get("deal_operator").toString())));
		setDeal_office_id((map.get("deal_office_id") == null?"":(map.get("deal_office_id").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("serial_no",getSerial_no());
		map.put("Time",getTime());
		map.put("source_system",getSource_system());
		map.put("receive_system",getReceive_system());
		map.put("busi_type",getBusi_type());
		map.put("service_num",getService_num());
		map.put("ICCID",getICCID());
		map.put("order_id",getOrder_id());
		map.put("deal_operator",getDeal_operator());
		map.put("deal_office_id",getDeal_office_id());
		return map;
	}
}
