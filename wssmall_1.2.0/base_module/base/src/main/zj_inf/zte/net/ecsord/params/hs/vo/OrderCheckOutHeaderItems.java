package zte.net.ecsord.params.hs.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderCheckOutHeaderItems implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="串码号",type="String",isNecessary="Y",desc="串码号")
	private String CUANM;
	@ZteSoftCommentAnnotationParam(name="串码行项目",type="String",isNecessary="Y",desc="串码行项目")
	private String ZLINE;
	@ZteSoftCommentAnnotationParam(name="采购凭证的项目编号",type="String",isNecessary="Y",desc="采购凭证的项目编号")
	private String EBELP;
	@ZteSoftCommentAnnotationParam(name="预留字段4",type="String",isNecessary="Y",desc="预留字段4")
	private String RESERVE4;
	
	@ZteSoftCommentAnnotationParam(name="预留字段5",type="String",isNecessary="Y",desc="预留字段5")
	private String RESERVE5;

	public String getCUANM() {
		return CUANM;
	}

	public void setCUANM(String cUANM) {
		CUANM = cUANM;
	}

	public String getZLINE() {
		return ZLINE;
	}

	public void setZLINE(String zLINE) {
		ZLINE = zLINE;
	}

	public String getEBELP() {
		return EBELP;
	}

	public void setEBELP(String eBELP) {
		EBELP = eBELP;
	}

	public String getRESERVE4() {
		return RESERVE4;
	}

	public void setRESERVE4(String rESERVE4) {
		RESERVE4 = rESERVE4;
	}

	public String getRESERVE5() {
		return RESERVE5;
	}

	public void setRESERVE5(String rESERVE5) {
		RESERVE5 = rESERVE5;
	}

	
	
}
