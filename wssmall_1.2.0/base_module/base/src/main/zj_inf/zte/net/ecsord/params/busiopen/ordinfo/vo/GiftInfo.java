package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 赠品信息
 */
public class GiftInfo implements Serializable {
	private static final long serialVersionUID = -8623181023230640805L;

	@ZteSoftCommentAnnotationParam(name = "礼品名称", type = "String", isNecessary = "Y", desc = "礼品名称")
	private String giftName;

	@ZteSoftCommentAnnotationParam(name = "面值", type = "String", isNecessary = "N", desc = "面值")
	private Integer giftFace;

	@ZteSoftCommentAnnotationParam(name = "数量", type = "String", isNecessary = "Y", desc = "数量")
	private String giftNum;

	@ZteSoftCommentAnnotationParam(name = "礼品内容", type = "String", isNecessary = "N", desc = "礼品内容")
	private String giftText;

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public Integer getGiftFace() {
		return giftFace;
	}

	public void setGiftFace(Integer giftFace) {
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
