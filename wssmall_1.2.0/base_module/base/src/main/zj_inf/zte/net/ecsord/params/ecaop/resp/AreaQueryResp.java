package zte.net.ecsord.params.ecaop.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
 
import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.AreaQueryRespVo;
import zte.net.ecsord.params.ecaop.resp.vo.QryStdAddrRespVo;
 
public class AreaQueryResp extends ZteResponse {
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "返回编码", type = "String", isNecessary = "N", desc = "code:返回编码")
	private String code ;
	@ZteSoftCommentAnnotationParam(name = "错误描述", type = "String", isNecessary = "N", desc = "msg:错误描述")
	private String msg ;
	@ZteSoftCommentAnnotationParam(name = "返回内容", type = "String", isNecessary = "N", desc = "resp:返回内容")
	private AreaQueryRespVo respJson ;
	 
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
	public AreaQueryRespVo getRespJson() {
		return respJson;
	}
	public void setRespJson(AreaQueryRespVo respJson) {
		this.respJson = respJson;
	}
}
 