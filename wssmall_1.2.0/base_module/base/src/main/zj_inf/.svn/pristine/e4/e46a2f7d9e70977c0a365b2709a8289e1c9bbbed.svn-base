package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.CustomerInfoResponseVo;
import zte.net.ecsord.params.ecaop.req.vo.DevelopPersonResponseVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

@SuppressWarnings("all")
public class CustomerInfoResponse  extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name = "", type = "List", isNecessary = "Y", desc = "developPersonRsp：")
	private List<CustomerInfoResponseVo> custInfo;
	
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

	public List<CustomerInfoResponseVo> getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(List<CustomerInfoResponseVo> custInfo) {
		this.custInfo = custInfo;
	}
}
