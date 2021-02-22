package zte.net.ecsord.params.bss.req;

import params.ZteRequest;
import zte.net.ecsord.params.bss.resp.SSOLoginBSSResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 单点登录BSS系统
 * 入参对象
 */
public class SSOLoginBSSReq extends ZteRequest<SSOLoginBSSResp>{
	
	@ZteSoftCommentAnnotationParam(name="登入、登出标识",type="String",isNecessary="Y",desc="登入、登出标识[0：登入、1：登出]")
	private String x_tag;
	@ZteSoftCommentAnnotationParam(name="ticket_id号",type="String",isNecessary="N",desc="登出时必填")
	private String ticket_id;
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
		
		return "com.zte.unicomService.bss.SSOLoginBSS";
	}

	public String getX_tag() {
		return x_tag;
	}

	public void setX_tag(String x_tag) {
		this.x_tag = x_tag;
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
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
