package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class GDBssHead implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "消息发送方代码", type = "String", isNecessary = "Y", desc = "新时空141028：BSS提供 创意园141111：5100")
	private String MSG_SENDER;

	@ZteSoftCommentAnnotationParam(name = "消息直接接收方代码", type = "String", isNecessary = "Y", desc = "5100")
	private String MSG_RECEIVER;

	@ZteSoftCommentAnnotationParam(name = "服务调用方流水号", type = "String", isNecessary = "Y", desc = "在发起方唯一标识一个服务的流水号，系统内唯一。调用方最长不超过28位，最后两位保留给总部平台扩展使用。")
	private String TRANS_IDO;

	@ZteSoftCommentAnnotationParam(name = "服务处理时间", type = "String", isNecessary = "Y", desc = "YYYYMMDDHHMISS 请求中此字段由发起方填写，内容为发起方处理时间 应答中此字段由落地方填写，内容为落地方处理时间")
	private String PROCESS_TIME;

	@ZteSoftCommentAnnotationParam(name = "地市编码", type = "String", isNecessary = "Y", desc = "")
	private String EPARCHY_CODE;

	@ZteSoftCommentAnnotationParam(name = "渠道编码", type = "String", isNecessary = "Y", desc = "")
	private String CHANNEL_ID;

	@ZteSoftCommentAnnotationParam(name = "操作员工号", type = "String", isNecessary = "Y", desc = "")
	private String OPER_ID;

	@ZteSoftCommentAnnotationParam(name = "服务名称", type = "String", isNecessary = "Y", desc = "")
	private String SERVICE_NAME;

	@ZteSoftCommentAnnotationParam(name = "操作名称", type = "String", isNecessary = "Y", desc = "")
	private String OPERATE_NAME;

	@ZteSoftCommentAnnotationParam(name = "应答/错误代码", type = "String", isNecessary = "Y", desc = "由BSS返回时填写，外围系统可根据此编码判断业务成功或者失败 新时空141028：0000为成功编码。")
	private String RSP_CODE;

	@ZteSoftCommentAnnotationParam(name = "应答/错误描述", type = "String", isNecessary = "Y", desc = "由BSS返回时填写，应答或错误描述")
	private String RSP_DESC;

	@ZteSoftCommentAnnotationParam(name = "测试标记", type = "String", isNecessary = "Y", desc = "发起方填写：1：连通性测试 0：正常业务")
	private String TEST_FLAG;
	
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;

	public String getMSG_SENDER() {
		return MSG_SENDER;
	}

	public void setMSG_SENDER(String mSG_SENDER) {
		MSG_SENDER = mSG_SENDER;
	}

	public String getMSG_RECEIVER() {
		return MSG_RECEIVER;
	}

	public void setMSG_RECEIVER(String mSG_RECEIVER) {
		MSG_RECEIVER = mSG_RECEIVER;
	}

	public String getTRANS_IDO() {
		return CommonDataFactory.getInstance().getActiveNo(AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setTRANS_IDO(String tRANS_IDO) {
		TRANS_IDO = tRANS_IDO;
	}

	public String getPROCESS_TIME() {
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PROCESS_TIME = dtf.format(new Date());
		return PROCESS_TIME;
	}

	public void setPROCESS_TIME(String pROCESS_TIME) {
		PROCESS_TIME = pROCESS_TIME;
	}

	public String getEPARCHY_CODE() {
		String city_code = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CITY_CODE);
		return CommonDataFactory.getInstance().getLanCode(city_code);//getDictRelationListData("bss_area_code")select * from es_dc_public_dict_relation i where i.other_system='bss' ;
	}

	public void setEPARCHY_CODE(String ePARCHY_CODE) {
		EPARCHY_CODE = ePARCHY_CODE;
	}

	public String getCHANNEL_ID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.ORDER_CHANNEL);//CHANNEL_MARK
	}

	public void setCHANNEL_ID(String cHANNEL_ID) {
		CHANNEL_ID = cHANNEL_ID;
	}

	public String getOPER_ID() {
		return CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.BSS_OPERATOR);
	}

	public void setOPER_ID(String oPER_ID) {
		OPER_ID = oPER_ID;
	}

	public String getSERVICE_NAME() {
		return SERVICE_NAME;
	}

	public void setSERVICE_NAME(String sERVICE_NAME) {
		SERVICE_NAME = sERVICE_NAME;
	}

	public String getOPERATE_NAME() {
		return OPERATE_NAME;//?
	}

	public void setOPERATE_NAME(String oPERATE_NAME) {
		OPERATE_NAME = oPERATE_NAME;
	}

	public String getRSP_CODE() {
		return RSP_CODE;
	}

	public void setRSP_CODE(String rSP_CODE) {
		RSP_CODE = rSP_CODE;
	}

	public String getRSP_DESC() {
		return RSP_DESC;
	}

	public void setRSP_DESC(String rSP_DESC) {
		RSP_DESC = rSP_DESC;
	}

	public String getTEST_FLAG() {
		return TEST_FLAG;
	}

	public void setTEST_FLAG(String tEST_FLAG) {
		TEST_FLAG = tEST_FLAG;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
}
