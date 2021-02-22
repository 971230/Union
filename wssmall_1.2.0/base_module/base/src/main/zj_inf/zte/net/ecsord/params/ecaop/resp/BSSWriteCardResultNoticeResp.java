/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @see 写卡结果通知
 *
 */
public class BSSWriteCardResultNoticeResp extends ZteResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4053717163834248180L;

	@ZteSoftCommentAnnotationParam(name = "预留字段:返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "预留字段:返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;	
	
	@ZteSoftCommentAnnotationParam(name = "应答码", type = "String", isNecessary = "N", desc = "RespCode")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "应答信息", type = "String", isNecessary = "N", desc = "RespDesc")
	private String RespDesc ;
	@ZteSoftCommentAnnotationParam(name = "Para1", type = "String", isNecessary = "N", desc = "Para1")
	private String Para1 ;
	@ZteSoftCommentAnnotationParam(name = "Para2", type = "String", isNecessary = "N", desc = "Para2")
	private String Para2 ;
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="RESP_CODE：应答编码")
	private String RESP_CODE;
	@ZteSoftCommentAnnotationParam(name="错误描述详细的描述错误原因",type="String",isNecessary="Y",desc="RESP_DESC：错误描述详细的描述错误原因")
	private String RESP_DESC;
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}
	public String getPara1() {
		return Para1;
	}
	public void setPara1(String para1) {
		Para1 = para1;
	}
	public String getPara2() {
		return Para2;
	}
	public void setPara2(String para2) {
		Para2 = para2;
	}
	
	
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
	public String getRESP_CODE() {
		return RESP_CODE;
	}
	public void setRESP_CODE(String rESP_CODE) {
		RESP_CODE = rESP_CODE;
	}
	public String getRESP_DESC() {
		return RESP_DESC;
	}
	public void setRESP_DESC(String rESP_DESC) {
		RESP_DESC = rESP_DESC;
	}
	
}
