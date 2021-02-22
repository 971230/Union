package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Supp implements Serializable {
	@ZteSoftCommentAnnotationParam(name = "供货商编码", type = "String", isNecessary = "Y", desc = "SuppCode：供货商编码")
	private String SuppCode;

	@ZteSoftCommentAnnotationParam(name = "供货商名称", type = "String", isNecessary = "Y", desc = "SuppName：供货商名称")
	private String SuppName;

	public String getSuppCode() {
		return SuppCode;
	}

	public void setSuppCode(String suppCode) {
		SuppCode = suppCode;
	}

	public String getSuppName() {
		return SuppName;
	}

	public void setSuppName(String suppName) {
		SuppName = suppName;
	}

}
