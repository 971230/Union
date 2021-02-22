package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.BrandInfoList;
import zte.net.ecsord.params.ecaop.resp.vo.ResourcePreCheckVO;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ResourcePreCheckResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="调用结果",type="String",isNecessary="Y",desc="00000表示成功")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="错误描述")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name="具体数据",type="String",isNecessary="Y",desc="具体数据")
	private ResourcePreCheckVO respJson;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResourcePreCheckVO getRespJson() {
		return respJson;
	}

	public void setRespJson(ResourcePreCheckVO respJson) {
		this.respJson = respJson;
	}

}
