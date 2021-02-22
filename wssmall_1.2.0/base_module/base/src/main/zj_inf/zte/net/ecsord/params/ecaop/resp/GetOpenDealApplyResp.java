/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.OpenDealApplyReq;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-18
 * @see 
 *
 */
public class GetOpenDealApplyResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "返回对象", type = "OpenDealApplyResp", isNecessary = "Y", desc = "openDealApply：返回对象")
	private OpenDealApplyReq openDealApply;

	public OpenDealApplyReq getOpenDealApply() {
		return openDealApply;
	}

	public void setOpenDealApply(OpenDealApplyReq openDealApply) {
		this.openDealApply = openDealApply;
	}
	
}
