package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UserCountCheckRsp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="respCode：应答编码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="respDesc：错误描述")
	private String RespDesc;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="para：保留字段")
	private List<BSSParaVo> Para;
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		this.RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		this.RespDesc = respDesc;
	}
	public List<BSSParaVo> getPara() {
		return Para;
	}
	public void setPara(List<BSSParaVo> para) {
		this.Para = para;
	}

}
