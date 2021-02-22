/**
 * 
 */
package zte.params.order.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

/**
 * @author ZX
 * CreateRedPkgResp.java
 * 2015-3-17
 */
public class CreateRedPkgResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="促销红包ID",type="String",isNecessary="Y",desc="促销红包ID")
	private String red_id;

	public String getRed_id() {
		return red_id;
	}
	public void setRed_id(String red_id) {
		this.red_id = red_id;
	}
}
