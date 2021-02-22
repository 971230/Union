package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class SpInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="spId",type="String",isNecessary="Y",desc="spId：SP产品名称的ID,需要支持多个，如:8000623900, 8000623901, 8000623902")
	private String spId;
	
	@ZteSoftCommentAnnotationParam(name="spName",type="String",isNecessary="Y",desc="spName：SP产品别名,如:名称1,名称2,名称3")
	private String spName;

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}
}
