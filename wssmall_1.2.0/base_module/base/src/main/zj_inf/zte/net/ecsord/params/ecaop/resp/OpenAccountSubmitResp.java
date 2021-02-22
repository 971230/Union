package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.OpenAccountSubmitVO;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * 
 * @author song.qi
 * @version 2018年5月17日
 * @see 开户处理提交(正式提交)
 */
public class OpenAccountSubmitResp extends ZteResponse {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String res_code;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private String res_message;
	@ZteSoftCommentAnnotationParam(name = "", type = "String", isNecessary = "Y", desc = "")
	private OpenAccountSubmitVO resultMsg;

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

	public OpenAccountSubmitVO getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(OpenAccountSubmitVO resultMsg) {
		this.resultMsg = resultMsg;
	}

}
