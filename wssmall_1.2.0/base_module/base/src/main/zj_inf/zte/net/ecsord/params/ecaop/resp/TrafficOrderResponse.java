package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author XMJ
 * @date 2015-06-24
 *
 */
public class TrafficOrderResponse  extends ZteResponse{
	
	
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "返回编码", type = "String", isNecessary = "N", desc = "code:返回编码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "detail:返回描述")
	private String detail;

	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParamsVo", isNecessary = "N", desc = "para：保留字段")
	private List<ParaVo> para;

	
	
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

	public List<ParaVo> getPara() {
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}	
}