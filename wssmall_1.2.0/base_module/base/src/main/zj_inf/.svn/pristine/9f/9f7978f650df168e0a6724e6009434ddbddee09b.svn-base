/**
 * 
 */
package zte.net.ecsord.params.bss.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.bss.resp.SPBuyServiceHandleResp;

/**
 * @author ZX
 * SPBuyServiceHandleReq.java
 * 2015-1-21
 */
public class SPBuyServiceHandleReq extends ZteRequest<SPBuyServiceHandleResp> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "订单号 ", type = "String", isNecessary = "Y", desc = "订单号 ")
	private String orderid;

	@ZteSoftCommentAnnotationParam(name = "开户手机号码 ", type = "String", isNecessary = "Y", desc = "开户手机号码 ")
	private String mobileNo;
	
	@ZteSoftCommentAnnotationParam(name = "生效方式 ", type = "String", isNecessary = "Y", desc = "生效方式（0-立即生效，1-下月生效） ")
	private String take_effect_type="";
	
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
	
	public String getTake_effect_type() {
		return take_effect_type;
	}

	public void setTake_effect_type(String take_effect_type) {
		this.take_effect_type = take_effect_type;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.zte.unicomService.bss.spbuyservicehandle";
	}

}
