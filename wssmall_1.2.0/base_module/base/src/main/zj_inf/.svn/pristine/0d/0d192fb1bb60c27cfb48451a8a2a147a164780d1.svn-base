package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UNI_BSS_HEAD implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "发起方应用域代码", type = "String", isNecessary = "Y", desc = "发起方应用域代码")
	private String ORIG_DOMAIN;	
	
	@ZteSoftCommentAnnotationParam(name = "服务名称", type = "String", isNecessary = "Y", desc = "服务名称")
	private String SERVICE_NAME;
	
	@ZteSoftCommentAnnotationParam(name = "操作名称", type = "String", isNecessary = "Y", desc = "操作名称")
	private String OPERATE_NAME;
	
	@ZteSoftCommentAnnotationParam(name = "操作动作代码", type = "String", isNecessary = "Y", desc = "操作动作代码")
	private String ACTION_CODE;	
	
	@ZteSoftCommentAnnotationParam(name = "交易关联性", type = "String", isNecessary = "Y", desc = "与其他交易关联；0：无关联；1：有关联。例如：在一卡充系统中对于异地卡充异地用户且卡和帐号归属不同省时接入省分缴费卡系统发送的扣款请求和缴费请求交易需要设置此标记为1")
	private String ACTION_RELATION;	
	
	@ZteSoftCommentAnnotationParam(name = "路由信息", type = "String", isNecessary = "Y", desc = "路由信息")
	private ROUTING ROUTING;	
	
	@ZteSoftCommentAnnotationParam(name = "发起方业务流水号", type = "String", isNecessary = "Y", desc = "发起方填写的业务流水号，一个业务流程中所有服务调用使用同一个业务流水号。")
	private String PROC_ID;
	
	@ZteSoftCommentAnnotationParam(name = "服务调用方流水号", type = "String", isNecessary = "Y", desc = "在发起方唯一标识一个服务的流水号，系统内唯一。调用方最长不超过28位，最后两位保留给总部平台扩展使用。")
	private String TRANS_IDO;
	
	@ZteSoftCommentAnnotationParam(name = "服务提供方流水号", type = "String", isNecessary = "Y", desc = "请求中不填，由落地方在应答中填，系统内唯一。提供方最长不超过28位，最后两位保留给总部平台扩展使用。")
	private String TRANS_IDH;
	
	@ZteSoftCommentAnnotationParam(name = "服务处理时间", type = "String", isNecessary = "Y", desc = "YYYYMMDDHHMISS请求中此字段由发起方填写，内容为发起方处理时间 应答中此字段由落地方填写，内容为落地方处理时间")
	private String PROCESS_TIME;
	
	@ZteSoftCommentAnnotationParam(name = "应答/错误信息", type = "String", isNecessary = "Y", desc = "请求中不填，应答中填")
	private RESPONSE RESPONSE;	
	
	@ZteSoftCommentAnnotationParam(name = "公共信息", type = "String", isNecessary = "Y", desc = "公共信息")
	private COM_BUS_INFO COM_BUS_INFO;	
	
	@ZteSoftCommentAnnotationParam(name = "SP", type = "String", isNecessary = "Y", desc = "SP")
	private SP_RESERVE SP_RESERVE;	
	
	@ZteSoftCommentAnnotationParam(name = "测试标记", type = "String", isNecessary = "Y", desc = "发起方填写，0：非测试交易，1：测试交易；需要注意的是测试必须是业务级别，即在同一个业务流水中所有操作必须具有相同的测试标记")
	private String TEST_FLAG;	
	
	@ZteSoftCommentAnnotationParam(name = "消息发送方代码", type = "String", isNecessary = "Y", desc = "发起消息的应用系统代码，参见系统代码表")
	private String MSG_SENDER;
	
	@ZteSoftCommentAnnotationParam(name = "消息直接接收方代码", type = "String", isNecessary = "Y", desc = "该消息送往的下一方代码")
	private String MSG_RECEIVER;

	public String getORIG_DOMAIN() {
		return ORIG_DOMAIN;
	}

	public void setORIG_DOMAIN(String oRIG_DOMAIN) {
		ORIG_DOMAIN = oRIG_DOMAIN;
	}

	public String getSERVICE_NAME() {
		return SERVICE_NAME;
	}

	public void setSERVICE_NAME(String sERVICE_NAME) {
		SERVICE_NAME = sERVICE_NAME;
	}

	public String getOPERATE_NAME() {
		return OPERATE_NAME;
	}

	public void setOPERATE_NAME(String oPERATE_NAME) {
		OPERATE_NAME = oPERATE_NAME;
	}

	public String getACTION_CODE() {
		return ACTION_CODE;
	}

	public void setACTION_CODE(String aCTION_CODE) {
		ACTION_CODE = aCTION_CODE;
	}

	public String getACTION_RELATION() {
		return ACTION_RELATION;
	}

	public void setACTION_RELATION(String aCTION_RELATION) {
		ACTION_RELATION = aCTION_RELATION;
	}

	public ROUTING getROUTING() {
		return ROUTING;
	}

	public void setROUTING(ROUTING rOUTING) {
		ROUTING = rOUTING;
	}

	public String getPROC_ID() {
		return PROC_ID;
	}

	public void setPROC_ID(String pROC_ID) {
		PROC_ID = pROC_ID;
	}

	public String getTRANS_IDO() {
		return TRANS_IDO;
	}

	public void setTRANS_IDO(String tRANS_IDO) {
		TRANS_IDO = tRANS_IDO;
	}

	public String getTRANS_IDH() {
		return TRANS_IDH;
	}

	public void setTRANS_IDH(String tRANS_IDH) {
		TRANS_IDH = tRANS_IDH;
	}

	public String getPROCESS_TIME() {
		return PROCESS_TIME;
	}

	public void setPROCESS_TIME(String pROCESS_TIME) {
		PROCESS_TIME = pROCESS_TIME;
	}

	public RESPONSE getRESPONSE() {
		return RESPONSE;
	}

	public void setRESPONSE(RESPONSE rESPONSE) {
		RESPONSE = rESPONSE;
	}

	public COM_BUS_INFO getCOM_BUS_INFO() {
		return COM_BUS_INFO;
	}

	public void setCOM_BUS_INFO(COM_BUS_INFO cOM_BUS_INFO) {
		COM_BUS_INFO = cOM_BUS_INFO;
	}
	
	public SP_RESERVE getSP_RESERVE() {
		return SP_RESERVE;
	}

	public void setSP_RESERVE(SP_RESERVE sP_RESERVE) {
		SP_RESERVE = sP_RESERVE;
	}

	public String getTEST_FLAG() {
		return TEST_FLAG;
	}

	public void setTEST_FLAG(String tEST_FLAG) {
		TEST_FLAG = tEST_FLAG;
	}

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
	
}
