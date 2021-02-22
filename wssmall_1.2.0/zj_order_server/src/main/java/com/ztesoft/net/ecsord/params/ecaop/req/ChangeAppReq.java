package com.ztesoft.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.util.ZjCommonUtils;

public class ChangeAppReq  extends ZteRequest{

	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
	private String notNeedReqStrOrderId;
	@ZteSoftCommentAnnotationParam(name="服务号码",type="String",isNecessary="Y",desc="服务号码")
	private String service_num="";
	@ZteSoftCommentAnnotationParam(name="原SIM卡号",type="String",isNecessary="Y",desc="换卡时传原SIM卡号")
	private String old_simid="";
	@ZteSoftCommentAnnotationParam(name="SIM卡号",type="String",isNecessary="Y",desc="SIM卡号")
	private String simId="";
	@ZteSoftCommentAnnotationParam(name="0:白卡 1:成卡",type="String",isNecessary="Y",desc="0:白卡 1:成卡")
	private String cardType="";
	@ZteSoftCommentAnnotationParam(name="1:补卡 2:换卡",type="String",isNecessary="Y",desc="1:补卡 2:换卡")
	private String chgType="";
	@ZteSoftCommentAnnotationParam(name="操作点",type="String",isNecessary="Y",desc="操作点")
	private String officeId="";
	@ZteSoftCommentAnnotationParam(name="操作员工号",type="String",isNecessary="Y",desc="操作员工号")
	private String operatorId="";
	@ZteSoftCommentAnnotationParam(name="发起方系统订单ID",type="String",isNecessary="Y",desc="发起方系统订单ID")
	private String ordersID="";
	@ZteSoftCommentAnnotationParam(name="证件类别编码",type="String",isNecessary="Y",desc="证件类别编码")
	private String certType="";
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="Y",desc="证件号码")
	private String certNum="";
	@ZteSoftCommentAnnotationParam(name="IMSI号",type="String",isNecessary="Y",desc="IMSI号(首次读卡必有)")
	private String imsi="";
	@ZteSoftCommentAnnotationParam(name="cardData写卡数据脚本",type="String",isNecessary="Y",desc="cardData写卡数据脚本")
	private String cardData="";
	@ZteSoftCommentAnnotationParam(name="读卡序列",type="String",isNecessary="Y",desc="读卡序列")
	private String procId="";
	@ZteSoftCommentAnnotationParam(name="拍照流水号",type="String",isNecessary="Y",desc="拍照流水号")
	private String req_swift_num="";
	@ZteSoftCommentAnnotationParam(name = "订单树", type = "OrderTreeBusiRequest", isNecessary = "Y", desc = "订单树")
	private OrderTreeBusiRequest orderTree	;
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}
	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	public String getService_num() {
		service_num = orderTree.getOrderItemBusiRequests().get(0).getOrderItemExtBusiRequest().getPhone_num();
		return service_num;
	}
	public void setService_num(String service_num) {
		this.service_num = service_num;
	}
	public String getOld_simid() {
		old_simid = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "old_iccid");
		return old_simid;
	}
	public void setOld_simid(String old_simid) {
		this.old_simid = old_simid;
	}
	public String getSimId() {
		simId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ICCID);
		return simId;
	}
	public void setSimId(String simId) {
		this.simId = simId;
	}
	public String getCardType() {
		cardType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "card_type");
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getChgType() {
		chgType = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "chg_type");
		return chgType;
	}
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}
	public String getOfficeId() {
		String OUT_OFFICE = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OFFICE);
		if(!StringUtil.isEmpty(OUT_OFFICE)){
			officeId = OUT_OFFICE;
		}else{
			officeId = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getDept_id();
		}
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getOperatorId() {
		String OUT_OPERATOR = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.OUT_OPERATOR);
		if(!StringUtil.isEmpty(OUT_OPERATOR)){
			operatorId = OUT_OPERATOR;
		}else{
			operatorId = ZjCommonUtils.getGonghaoInfoByOrderId(notNeedReqStrOrderId).getUser_code();
		}
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOrdersID() {
		ordersID = this.notNeedReqStrOrderId;
		return ordersID;
	}
	public void setOrdersID(String ordersID) {
		this.ordersID = ordersID;
	}
	public String getCertType() {
		certType = CommonDataFactory.getInstance().getDcPublicDataByPkey("DIC_BSS_CERT_TYPE", orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCerti_type());
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNum() {
		certNum = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests().get(0).getOrderItemProdExtBusiRequest().getCert_card_num();
		return certNum;
	}
	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}
	public String getImsi() {
		imsi = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "simid");
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getCardData() {
		cardData = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "card_data");
		return cardData;
	}
	public void setCardData(String cardData) {
		this.cardData = cardData;
	}
	public String getProcId() {
		procId = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, "proc_id");
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getReq_swift_num() {
		req_swift_num = "";
		String new_req_swift_num = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.COUPON_BATCH_ID);
		if(!StringUtil.isEmpty(new_req_swift_num)){
			req_swift_num = new_req_swift_num;
		}
		return req_swift_num;
	}
	public void setReq_swift_num(String req_swift_num) {
		this.req_swift_num = req_swift_num;
	}
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}
	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.ztesoft.net.ecsord.params.ecaop.req.changeAppReq";
	}
}
