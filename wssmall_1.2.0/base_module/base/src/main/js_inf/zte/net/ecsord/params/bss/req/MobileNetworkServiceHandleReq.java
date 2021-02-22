package zte.net.ecsord.params.bss.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.bss.resp.MobileNetworkServiceHandleResp;

public class MobileNetworkServiceHandleReq extends ZteRequest<MobileNetworkServiceHandleResp> {

	private static final long serialVersionUID = -8073182328656633175L;

	@ZteSoftCommentAnnotationParam(name = "订单号 ", type = "String", isNecessary = "Y", desc = "订单号 ")
	private String orderid;

	@ZteSoftCommentAnnotationParam(name = "开户手机号码 ", type = "String", isNecessary = "Y", desc = "开户手机号码 ")
	private String mobileNo;

	@ZteSoftCommentAnnotationParam(name = "流量包选择标识 ", type = "String", isNecessary = "Y", desc = "a:5元1GB闲时省内流量包; b:10元3GB闲时省内流量包 ; ")
	private String packFlag;
	
	@ZteSoftCommentAnnotationParam(name = "cBSS登陆对象", type = "HttpLoginClient", isNecessary = "Y", desc = "cBSS登陆对象")
	private Object httpLoginclient;
	
	public Object getHttpLoginclient() {
		return httpLoginclient;
	}

	public void setHttpLoginclient(Object httpLoginclient) {
		this.httpLoginclient = httpLoginclient;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderid() {
		return orderid;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	public String getPackFlag() {
		return packFlag;
	}

	public void setPackFlag(String packFlag) {
		this.packFlag = packFlag;
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "com.zte.unicomService.bss.mobilenetworkservicehandle";
	}

}
