/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * CBSS订单激活返回信息
 * @author duan.shaochu
 *
 */
public class OrderActiveResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "cbssID为激活时使用", type = "String", isNecessary = "Y", desc = "orderId：cbssID为激活时使用")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParamsVo> para;
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public List<ParamsVo> getPara() {
		return para;
	}
	
	public void setPara(List<ParamsVo> para) {
		this.para = para;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
