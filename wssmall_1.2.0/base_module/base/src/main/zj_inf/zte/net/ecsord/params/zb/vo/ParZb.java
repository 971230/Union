package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ParZb implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="保留字段ID",type="String",isNecessary="Y",desc="ParaID：保留字段ID")
	private String ParaID;
	
	@ZteSoftCommentAnnotationParam(name="保留字段值",type="String",isNecessary="Y",desc="ParaValue：保留字段值")
	private String ParaValue;

	public String getParaID() {
		return ParaID;
	}

	public void setParaID(String paraID) {
		ParaID = paraID;
	}

	public String getParaValue() {
		return ParaValue;
	}

	public void setParaValue(String paraValue) {
		ParaValue = paraValue;
	}
	
	

}
