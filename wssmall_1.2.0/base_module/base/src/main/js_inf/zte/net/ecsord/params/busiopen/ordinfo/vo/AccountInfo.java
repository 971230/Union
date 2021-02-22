package zte.net.ecsord.params.busiopen.ordinfo.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 开户信息
 */
public class AccountInfo implements Serializable {

	
	private static final long serialVersionUID = -3213172162995752394L;

	@ZteSoftCommentAnnotationParam(name = "开户号码", type = "String", isNecessary = "Y", desc = "开户号码")
	private String mobileNo;

	@ZteSoftCommentAnnotationParam(name = "开户或者校验结果", type = "String", isNecessary = "Y", desc = "开户或者校验结果")
	private String infoAccountStatus;

	@ZteSoftCommentAnnotationParam(name = "结果说明", type = "String", isNecessary = "N", desc = "结果说明")
	private String infoAccountDesc;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getInfoAccountStatus() {
		return infoAccountStatus;
	}

	public void setInfoAccountStatus(String infoAccountStatus) {
		this.infoAccountStatus = infoAccountStatus;
	}

	public String getInfoAccountDesc() {
		return infoAccountDesc;
	}

	public void setInfoAccountDesc(String infoAccountDesc) {
		this.infoAccountDesc = infoAccountDesc;
	}

}
