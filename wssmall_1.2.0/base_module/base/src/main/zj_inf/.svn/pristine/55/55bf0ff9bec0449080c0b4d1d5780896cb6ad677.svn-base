package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.InfConst;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueWriMachStaInBatchDataRGHeadReq implements Serializable {

	@ZteSoftCommentAnnotationParam(name="code",type="String",isNecessary="Y",desc="code")
	private String code;
	@ZteSoftCommentAnnotationParam(name="sequence",type="String",isNecessary="Y",desc="sequence")
	private String sequence;
	@ZteSoftCommentAnnotationParam(name="type",type="String",isNecessary="Y",desc="type")
	private String type;
	
	public String getCode() {
		code = "0";
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSequence() {
		sequence = "AC" + System.currentTimeMillis();
		return sequence;
	}
	
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public String getType() {
		type = InfConst.SR_BATCHGETSTATE;
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
