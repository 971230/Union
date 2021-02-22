package zte.net.ecsord.params.wyg.req;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.wyg.resp.StatuSynchResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class StatuSynchReq extends ZteRequest<StatuSynchResp> {
	public StatuSynchReq(){
		
	}
	
	public StatuSynchReq (String order_id, String trace_code, String trace_name, String deal_status, String deal_desc, String connects){
		this.ORDER_ID = order_id;
		this.TRACE_CODE = trace_code;
		this.TRACE_NAME = trace_name;
		this.DEAL_STATUS = deal_status;
		this.DEAL_DESC = deal_desc;
		this.CONTENTS = connects;
	}
	
	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="N",desc="外部系统下单生成的订单号(必填)")
	private String ORDER_ID;
	
	@ZteSoftCommentAnnotationParam(name="流水号",type="String",isNecessary="N",desc="流水号")
	private String ActiveNo;
	
	@ZteSoftCommentAnnotationParam(name="环节编码",type="String",isNecessary="N",desc="最近一次处理的环节")
	private String TRACE_CODE;	
	
	@ZteSoftCommentAnnotationParam(name="环节名称",type="String",isNecessary="N",desc="环节名称")
	private String TRACE_NAME;	
	
	@ZteSoftCommentAnnotationParam(name="环节处理状态",type="String",isNecessary="N",desc="环节处理状态")
	private String DEAL_STATUS;	
	
	@ZteSoftCommentAnnotationParam(name="处理结果描述",type="String",isNecessary="N",desc="处理结果描述")
	private String DEAL_DESC;	
	
	@ZteSoftCommentAnnotationParam(name="处理结果描述",type="String",isNecessary="N",desc="订单系统的订单编码")
	private String ORDER_ID_ECS;
	
	@ZteSoftCommentAnnotationParam(name="消息通知内容",type="String",isNecessary="N",desc="可以是一些比较重要的信息，比如：写卡脚本。或其他信息")
	private String CONTENTS;			
	
	@ZteSoftCommentAnnotationParam(name="BSS办理成功的业务列表",type="String",isNecessary="N",desc="BSS办理成功的业务列表")
	private String SUCC_SKU_LST;	
	
	@ZteSoftCommentAnnotationParam(name="BSS办理失败的业务列表",type="String",isNecessary="N",desc="BSS办理失败的业务列表")
	private String FAIL_SKU_LST;
	
	public String getORDER_ID() {
		return ORDER_ID;
	}
	
	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getActiveNo() {
		return CommonDataFactory.getInstance().getActiveNo(
				AttrConsts.ACTIVE_NO_ON_OFF);
	}

	public void setActiveNo(String activeNo) {
		ActiveNo = activeNo;
	}

	public String getTRACE_CODE() {
		return TRACE_CODE;
	}
	
	public void setTRACE_CODE(String tRACE_CODE) {
		TRACE_CODE = tRACE_CODE;
	}
	
	public String getTRACE_NAME() {
		return TRACE_NAME;
	}
	
	public void setTRACE_NAME(String tRACE_NAME) {
		TRACE_NAME = tRACE_NAME;
	}
	
	public String getDEAL_STATUS() {
		return DEAL_STATUS;
	}
	
	public void setDEAL_STATUS(String dEAL_STATUS) {
		DEAL_STATUS = dEAL_STATUS;
	}
	
	public String getDEAL_DESC() {
		return DEAL_DESC;
	}
	
	public void setDEAL_DESC(String dEAL_DESC) {
		DEAL_DESC = dEAL_DESC;
	}
	
	public String getCONTENTS() {
		return CONTENTS;
	}

	public void setCONTENTS(String cONTENTS) {
		CONTENTS = cONTENTS;
	}

	public String getORDER_ID_ECS() {
		return ORDER_ID_ECS;
	}
	
	public void setORDER_ID_ECS(String oRDER_ID_ECS) {
		ORDER_ID_ECS = oRDER_ID_ECS;
	}
	
	public String getSUCC_SKU_LST() {
		return SUCC_SKU_LST;
	}
	
	public void setSUCC_SKU_LST(String sUCC_SKU_LST) {
		SUCC_SKU_LST = sUCC_SKU_LST;
	}
	
	public String getFAIL_SKU_LST() {
		return FAIL_SKU_LST;
	}
	
	public void setFAIL_SKU_LST(String fAIL_SKU_LST) {
		FAIL_SKU_LST = fAIL_SKU_LST;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.wyg.statuSynch";
	}
	
}
