package zte.net.ecsord.params.bss.req;

import java.util.HashMap;
import java.util.Map;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.bss.resp.ActionRecvBSSResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public class ActionRecvBSSReq extends ZteRequest<ActionRecvBSSResp>{
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="头部是公用模块，在运行时绑定数据；request要能复用")
	private Map GD_BSS_HEAD;
	
	@ZteSoftCommentAnnotationParam(name="订单号",type="String",isNecessary="Y",desc="电子商城订单系统订单号")
	private String ORDER_ID;
	
	@ZteSoftCommentAnnotationParam(name="客户电话",type="String",isNecessary="Y",desc="客户电话")
	private String SERIAL_NUMBER;	
	
	@ZteSoftCommentAnnotationParam(name="服务号码网别",type="String",isNecessary="Y",desc="移网:G")
	private String SERVICE_CLASS_CODE;
	
	@ZteSoftCommentAnnotationParam(name="活动ID",type="String",isNecessary="Y",desc="新时空141028：商品系统配置对应关系")
	private String ACTION_CODE;
	
	@ZteSoftCommentAnnotationParam(name="缴费方式",type="String",isNecessary="Y",desc="缴费方式，对应账务代码表新时空141028：BSS提供账务代码表。填哪个代码？如POS机、现金等方式。")
	private String PAY_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="缴费备注",type="String",isNecessary="Y",desc="手机IMEI或TAC序列号，当涉及手机政策类相关活动时需要校验这个序列号")
	private String ACTION_REASON;
	
	@ZteSoftCommentAnnotationParam(name="TAC序列号",type="String",isNecessary="Y",desc="TAC序列号")
	private String TAC_NO;

	public Map getGD_BSS_HEAD() {
		return GD_BSS_HEAD;
	}

	public void setGD_BSS_HEAD(Map gD_BSS_HEAD) {
		GD_BSS_HEAD = gD_BSS_HEAD;
	}

	public String getORDER_ID() {
		return ORDER_ID;
	}

	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}

	public String getSERIAL_NUMBER() {
		return CommonDataFactory.getInstance().getAttrFieldValue(ORDER_ID, AttrConsts.REFERENCE_PHONE);
	}

	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}

	public String getSERVICE_CLASS_CODE() {
		return CommonDataFactory.getInstance().getProductSpec(ORDER_ID, SpecConsts.TYPE_ID_10002, null, SpecConsts.NET_TYPE);
	}

	public void setSERVICE_CLASS_CODE(String sERVICE_CLASS_CODE) {
		SERVICE_CLASS_CODE = sERVICE_CLASS_CODE;
	}

	public String getACTION_CODE() {
		return ACTION_CODE;
	}

	public void setACTION_CODE(String aCTION_CODE) {
		ACTION_CODE = aCTION_CODE;
	}

	public String getPAY_TYPE() {
		return CommonDataFactory.getInstance().getAttrFieldValue(ORDER_ID, AttrConsts.PAY_TYPE);
	}

	public void setPAY_TYPE(String pAY_TYPE) {
		PAY_TYPE = pAY_TYPE;
	}

	public String getACTION_REASON() {
		return ACTION_REASON;
	}

	public void setACTION_REASON(String aCTION_REASON) {
		ACTION_REASON = aCTION_REASON;
	}

	public String getTAC_NO() {
		String imei = CommonDataFactory.getInstance().getAttrFieldValue(ORDER_ID, AttrConsts.TERMINAL_NUM);
		String tac = "";
		if(null != imei && !imei.equals("") && imei.length()>6){
			tac = imei.substring(0, 6);
		}
		return tac;
	}

	public void setTAC_NO(String tAC_NO) {
		TAC_NO = tAC_NO;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.bss.ActionRecv";
	}

}
