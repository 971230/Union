/**
 * 
 */
package zte.params.order.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.order.resp.GrabRedPkgResp;

/**
 * @author ZX
 * GrabRedPkgReq.java
 * 2015-3-17
 */
public class GrabRedPkgReq extends ZteRequest<GrabRedPkgResp> {

	@ZteSoftCommentAnnotationParam(name="促销红包ID",type="String",isNecessary="Y",desc="促销红包ID")
	private String red_id;
	@ZteSoftCommentAnnotationParam(name="会员ID",type="String",isNecessary="Y",desc="会员ID")
	private String member_id;
	
	public String getRed_id() {
		return red_id;
	}
	
	public void setRed_id(String red_id) {
		this.red_id = red_id;
	}
	
	public String getMember_id() {
		return member_id;
	}
	
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.orderService.promotionredpkg.grab";
	}

}
