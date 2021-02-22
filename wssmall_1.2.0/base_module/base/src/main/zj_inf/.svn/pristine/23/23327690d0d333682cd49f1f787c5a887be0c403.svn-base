package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RespHeadRG implements Serializable {
	
	public RespHeadRG(String code, String message){
		this.code = code;
		this.message = message;
	}

	@ZteSoftCommentAnnotationParam(name="code",type="String",isNecessary="Y",desc="code：结果编码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name="message",type="String",isNecessary="Y",desc="message：处理结果描述")
	private String message;
	
	@ZteSoftCommentAnnotationParam(name="sequence",type="String",isNecessary="Y",desc="sequence")
	private String sequence;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
}
