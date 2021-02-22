package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.OrderInfoRespVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderQueryRespone  extends ZteResponse{
	private static final long serialVersionUID = -7749302764785465487L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name = "订单信息", type = "List", isNecessary = "Y", desc = "ordiInfo：订单信息")
	private List<OrderInfoRespVo> ordiInfo;
	
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

	public List<OrderInfoRespVo> getOrdiInfo() {
		return ordiInfo;
	}

	public void setOrdiInfo(List<OrderInfoRespVo> ordiInfo) {
		this.ordiInfo = ordiInfo;
	}

}
