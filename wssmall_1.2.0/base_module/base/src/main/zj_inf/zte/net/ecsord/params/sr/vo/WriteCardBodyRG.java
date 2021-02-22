package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WriteCardBodyRG implements Serializable {

	@ZteSoftCommentAnnotationParam(name="detail",type="String",isNecessary="Y",desc="detail：描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name="cardNo",type="String",isNecessary="Y",desc="cardNo：卡号")
	private String cardNo;
	
	@ZteSoftCommentAnnotationParam(name="number",type="String",isNecessary="Y",desc="number：业务号码")
	private String number;
	
	@ZteSoftCommentAnnotationParam(name="result",type="String",isNecessary="Y",desc="result：写卡结果,0:成功，非0，表示失败 ")
	private String result;
	
	@ZteSoftCommentAnnotationParam(name="resultCode",type="String",isNecessary="Y",desc="resultCode: APDU指令操作的十六进制结果 ")
	private String resultCode;
	
	@ZteSoftCommentAnnotationParam(name="resultMsg",type="String",isNecessary="Y",desc="resultMsg: 读写卡结果文字描述信息 ")
	private String resultMsg;
	
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
