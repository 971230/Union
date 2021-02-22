package zte.net.ecsord.params.zb.resp;

import java.util.List;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.zb.vo.ResourecsRsp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberStateChangeZBResponse extends  ZteBusiResponse{
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name = "", type = "List", isNecessary = "Y", desc = "resourcesRsp：")
	private List<ResourecsRsp> resourcesRsp;
	
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

	public List<ResourecsRsp> getResourcesRsp() {
		return resourcesRsp;
	}

	public void setResourcesRsp(List<ResourecsRsp> resourecsRsp) {
		this.resourcesRsp = resourecsRsp;
	}
	
}
