package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PARA implements Serializable {
	@ZteSoftCommentAnnotationParam(name="保留字段ID",type="String",isNecessary="Y",desc="保留字段ID")
	private String PARA_ID;
	
	@ZteSoftCommentAnnotationParam(name="保留字段值",type="String",isNecessary="Y",desc="保留字段值")
	private String PARA_VALUE;

	public String getPARA_ID() {
		return PARA_ID;
	}

	public void setPARA_ID(String pARA_ID) {
		PARA_ID = pARA_ID;
	}

	public String getPARA_VALUE() {
		return PARA_VALUE;
	}

	public void setPARA_VALUE(String pARA_VALUE) {
		PARA_VALUE = pARA_VALUE;
	}
	
	
}
