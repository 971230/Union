package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RESPONSE implements Serializable {
	@ZteSoftCommentAnnotationParam(name="应答/错误类型",type="String",isNecessary="Y",desc="应答或错误类型，参见应答/错误类型表")
	private String RSP_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="应答/错误代码",type="String",isNecessary="Y",desc="应答或错误代码，参见应答/错误代码表")
	private String RSP_CODE;
	
	@ZteSoftCommentAnnotationParam(name="应答/错误描述",type="String",isNecessary="Y",desc="应答或错误描述")
	private String RSP_DESC;

	public String getRSP_TYPE() {
		return RSP_TYPE;
	}

	public void setRSP_TYPE(String rSP_TYPE) {
		RSP_TYPE = rSP_TYPE;
	}

	public String getRSP_CODE() {
		return RSP_CODE;
	}

	public void setRSP_CODE(String rSP_CODE) {
		RSP_CODE = rSP_CODE;
	}

	public String getRSP_DESC() {
		return RSP_DESC;
	}

	public void setRSP_DESC(String rSP_DESC) {
		RSP_DESC = rSP_DESC;
	}	
	
	
}
