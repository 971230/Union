package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.ProdChangeApplyVO;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-06-26
 */
public class ProdChangeApplyResp extends ZteResponse {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String res_code;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String res_message;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private ProdChangeApplyVO resultMsg;

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_message() {
		return res_message;
	}

	public void setRes_message(String res_message) {
		this.res_message = res_message;
	}

	public ProdChangeApplyVO getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(ProdChangeApplyVO resultMsg) {
		this.resultMsg = resultMsg;
	}

}
