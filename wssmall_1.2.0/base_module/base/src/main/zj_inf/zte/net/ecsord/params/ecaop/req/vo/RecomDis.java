/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author songqi
 * @see 发展人信息
 */
public class RecomDis implements Serializable {

	private static final long serialVersionUID = -2821004926893253327L;
	@ZteSoftCommentAnnotationParam(name = "发展人姓名", type = "String", isNecessary = "N", desc = "发展人姓名")
	private String recomPersonName;// 发展人姓名
	@ZteSoftCommentAnnotationParam(name = "发展人联系电话", type = "String", isNecessary = "N", desc = "发展人联系电话")
	private String recomPersonPhone;// 发展人联系电话
	@ZteSoftCommentAnnotationParam(name = "发展人id", type = "String", isNecessary = "N", desc = "发展人id")
	private String recomPersonId;// 发展人id

	public String getRecomPersonName() {
		return recomPersonName;
	}

	public void setRecomPersonName(String recomPersonName) {
		this.recomPersonName = recomPersonName;
	}

	public String getRecomPersonPhone() {
		return recomPersonPhone;
	}

	public void setRecomPersonPhone(String recomPersonPhone) {
		this.recomPersonPhone = recomPersonPhone;
	}

	public String getRecomPersonId() {
		return recomPersonId;
	}

	public void setRecomPersonId(String recomPersonId) {
		this.recomPersonId = recomPersonId;
	}

}
