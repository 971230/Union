package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.CustInfoModPara;
import zte.net.ecsord.params.ecaop.req.vo.CustInfoModVo;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 */
public class CustInfoModRspVO extends ZteResponse {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private List<CustInfoModVo> custInfo;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "N", desc = "")
	private List<CustInfoModPara> para;
	

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

	public List<CustInfoModVo> getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(List<CustInfoModVo> custInfo) {
		this.custInfo = custInfo;
	}

	public List<CustInfoModPara> getPara() {
		return para;
	}

	public void setPara(List<CustInfoModPara> para) {
		this.para = para;
	}

}
