package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class SP_RESERVE implements Serializable {
	@ZteSoftCommentAnnotationParam(name="总部流水号",type="String",isNecessary="Y",desc="由总部平台填写")
	private String TRANS_IDC;
	
	@ZteSoftCommentAnnotationParam(name="逻辑交易日",type="String",isNecessary="Y",desc="格式：yyyymmdd，清分对帐用，由总部平台填写。")
	private String CUTOFFDAY;
	
	@ZteSoftCommentAnnotationParam(name="发起方代码",type="String",isNecessary="Y",desc="由发起方填写")
	private String OSNDUNS;	
	
	@ZteSoftCommentAnnotationParam(name="归属方代码",type="String",isNecessary="Y",desc="由总部平台填写")
	private String HSNDUNS;	
	
	@ZteSoftCommentAnnotationParam(name="处理标识",type="String",isNecessary="Y",desc="最后的17位为总部平台的处理时间，YYYYMMDDHHMISSsss精确到毫秒")
	private String CONV_ID;

	public String getTRANS_IDC() {
		return TRANS_IDC;
	}

	public void setTRANS_IDC(String tRANS_IDC) {
		TRANS_IDC = tRANS_IDC;
	}

	public String getCUTOFFDAY() {
		return CUTOFFDAY;
	}

	public void setCUTOFFDAY(String cUTOFFDAY) {
		CUTOFFDAY = cUTOFFDAY;
	}

	public String getOSNDUNS() {
		return OSNDUNS;
	}

	public void setOSNDUNS(String oSNDUNS) {
		OSNDUNS = oSNDUNS;
	}

	public String getHSNDUNS() {
		return HSNDUNS;
	}

	public void setHSNDUNS(String hSNDUNS) {
		HSNDUNS = hSNDUNS;
	}

	public String getCONV_ID() {
		return CONV_ID;
	}

	public void setCONV_ID(String cONV_ID) {
		CONV_ID = cONV_ID;
	}		
	
}
