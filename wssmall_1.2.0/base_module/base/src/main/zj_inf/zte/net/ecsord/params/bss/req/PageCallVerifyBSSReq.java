package zte.net.ecsord.params.bss.req;

import params.ZteRequest;
import zte.net.ecsord.params.bss.resp.PageCallVerifyBSSResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 页面功能调用反调确认接口
 * 入参对象
 */
public class PageCallVerifyBSSReq extends ZteRequest<PageCallVerifyBSSResp>{
	
	@ZteSoftCommentAnnotationParam(name="功能调用的地址",type="String",isNecessary="Y",desc="功能调用的地址(注意不是URL，即不需要包含服务器地址)")
	private String req_uri;
	@ZteSoftCommentAnnotationParam(name="请求标识",type="String",isNecessary="Y",desc="请求标识")
	private String req_id;
	@ZteSoftCommentAnnotationParam(name="ticket 状态",type="String",isNecessary="N",desc="状态（0：表示失效、1：表示状态正常）")
	private String ticket_stat;
	@ZteSoftCommentAnnotationParam(name="订单编号",type="String",isNecessary="Y",desc="notNeedReqStrOrderId：订单编号")
	private String notNeedReqStrOrderId;
	public String getReq_uri() {
		return req_uri;
	}

	public void setReq_uri(String req_uri) {
		this.req_uri = req_uri;
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}

	public String getTicket_stat() {
		return ticket_stat;
	}

	public void setTicket_stat(String ticket_stat) {
		this.ticket_stat = ticket_stat;
	}
	
	
	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "com.zte.unicomService.bss.PageCallVerify";
	}

}
