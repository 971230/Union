package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Gift implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1061187705961504552L;

	@ZteSoftCommentAnnotationParam(name="礼品名称",type="String",isNecessary="Y",desc="giftName：礼品名称")
	private String giftName;

	@ZteSoftCommentAnnotationParam(name="面值",type="String",isNecessary="N",desc="giftFace：面值")
	private String giftFace;

	@ZteSoftCommentAnnotationParam(name="数量",type="String",isNecessary="Y",desc="giftNum：数量")
	private String giftNum;	

	@ZteSoftCommentAnnotationParam(name="礼品内容",type="String",isNecessary="N",desc="giftText：礼品内容")
	private String giftText;

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftFace() {
		return giftFace;
	}

	public void setGiftFace(String giftFace) {
		this.giftFace = giftFace;
	}

	public String getGiftNum() {
		return giftNum;
	}

	public void setGiftNum(String giftNum) {
		this.giftNum = giftNum;
	}

	public String getGiftText() {
		return giftText;
	}

	public void setGiftText(String giftText) {
		this.giftText = giftText;
	}	
	
	
}
