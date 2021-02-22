package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class IccidBody implements Serializable {

	@ZteSoftCommentAnnotationParam(name="detail",type="String",isNecessary="Y",desc="detail：描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name="cardNo",type="String",isNecessary="Y",desc="cardNo：卡号")
	private String cardNo;

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
	
}
