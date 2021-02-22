package zte.net.ecsord.params.hs.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4320683182238597250L;

	@ZteSoftCommentAnnotationParam(name="项次号",type="String",isNecessary="Y",desc="项次号")
	private String POSNR;

	@ZteSoftCommentAnnotationParam(name="数量",type="String",isNecessary="Y",desc="数量")
	private String MENGE;

	@ZteSoftCommentAnnotationParam(name="单位:PCS",type="String",isNecessary="Y",desc="单位:PCS")
	private String MEINS;

	@ZteSoftCommentAnnotationParam(name="商品编码",type="String",isNecessary="Y",desc="商品编码")
	private String MATNR;

	@ZteSoftCommentAnnotationParam(name="经代销标识 K为代销 空为经销",type="String",isNecessary="N",desc="经代销标识 K为代销 空为经销")
	private String ZJXDX;
	
	@ZteSoftCommentAnnotationParam(name="串码信息",type="String",isNecessary="N",desc="串码信息")
	private List<SNList> SNList;

	public String getPOSNR() {
		return POSNR;
	}

	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}

	public String getMENGE() {
		return MENGE;
	}

	public void setMENGE(String mENGE) {
		MENGE = mENGE;
	}

	public String getMEINS() {
		return MEINS;
	}

	public void setMEINS(String mEINS) {
		MEINS = mEINS;
	}

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getZJXDX() {
		return ZJXDX;
	}

	public void setZJXDX(String zJXDX) {
		ZJXDX = zJXDX;
	}

	public List<SNList> getSNList() {
		return SNList;
	}

	public void setSNList(List<SNList> sNList) {
		SNList = sNList;
	}
}
