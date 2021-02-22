package zte.net.ecsord.params.bss.req;

import params.ZteRequest;
import zte.net.ecsord.params.bss.resp.SSOLoginReqVerifyBSSResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * BSS页面嵌入登陆反调确认接口
 * 入参对象
 */
public class SSOLoginReqVerifyBSSReq extends ZteRequest<SSOLoginReqVerifyBSSResp>{
	
	@ZteSoftCommentAnnotationParam(name="请求标识",type="String",isNecessary="Y",desc="请求标识（请求字符串标志，BSS反调电子商城用）")
	private String req_id;
	@ZteSoftCommentAnnotationParam(name="session_id",type="String",isNecessary="Y",desc="当前登录商城的用户的sessionID")
	private String session_id;
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.bss.SSOLoginReqVerify";
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
}
