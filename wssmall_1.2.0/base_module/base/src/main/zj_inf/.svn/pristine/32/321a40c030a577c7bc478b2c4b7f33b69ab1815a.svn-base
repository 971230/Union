package zte.net.ecsord.params.bss.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;

public class CustomerCheckReq extends ZteRequest {

	public CustomerCheckReq() {

	}

	public CustomerCheckReq(String notNeedReqStrOrderId, String check_type,
			String check_value, String busi_code) {

		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
		this.check_type = check_type;
		this.check_value = check_value;
		this.busi_code = busi_code;
	}

	@ZteSoftCommentAnnotationParam(name="内部订单号",type="String",isNecessary="N",desc="内部订单号")
	private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name="证件类型",type="String",isNecessary="N",desc="证件类型")
	private String check_type;
	
	@ZteSoftCommentAnnotationParam(name="证件号码",type="String",isNecessary="N",desc="证件号码")
	private String check_value;
	
	@ZteSoftCommentAnnotationParam(name="业务编码",type="String",isNecessary="N",desc="业务编码")
	private String busi_code;
	
	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.customerCheck";
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	public String getCheck_type() {//校验类型
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_OLD);//是否老用户
		if("1".equals(is_old)) {//老用户
			check_value = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);//开户号码
			check_type = "01";
			if(check_value == null || check_value == "") {//取不到号码
				check_type = "02";
			}
		} else {//新用户、直接证件号码校验
			check_type = "02";
		}
		return check_type;
	}

	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}

	public String getCheck_value() {
		String is_old = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.IS_OLD);//是否老用户
		if("1".equals(is_old)) {//老用户
			check_value = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.PHONE_NUM);//开户号码
			
			if(check_value == null || check_value == "") {//取不到号码
				check_value = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);//证件号码
			}
		} else {//新用户、直接证件号码校验
			check_value = CommonDataFactory.getInstance().getAttrFieldValue(notNeedReqStrOrderId, AttrConsts.CERT_CARD_NUM);//证件号码
		}
		if(check_value == null) 
			check_value = "";
		return check_value;
	}

	public void setCheck_value(String check_value) {
		this.check_value = check_value;
	}

	public String getBusi_code() {
		busi_code = "C111110";
		return busi_code;
	}

	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}

	
	
	
}
