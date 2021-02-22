package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PhotoInfoVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3225350313216092886L;

	@ZteSoftCommentAnnotationParam(name = "背面照片", type = "String", isNecessary = "Y", desc = "PhotoBack：背面照片")
	private String PhotoBack;

	@ZteSoftCommentAnnotationParam(name = "正面照片", type = "String", isNecessary = "Y", desc = "PhotoFront：正面照片")
	private String PhotoFront;

	@ZteSoftCommentAnnotationParam(name = "手持照片", type = "String", isNecessary = "N", desc = "PhotoHand：手持照片")
	private String PhotoHand;

	public String getPhotoBack() {
		return PhotoBack;
	}

	public void setPhotoBack(String photoBack) {
		PhotoBack = photoBack;
	}

	public String getPhotoFront() {
		return PhotoFront;
	}

	public void setPhotoFront(String photoFront) {
		PhotoFront = photoFront;
	}

	public String getPhotoHand() {
		return PhotoHand;
	}

	public void setPhotoHand(String photoHand) {
		PhotoHand = photoHand;
	}

}
