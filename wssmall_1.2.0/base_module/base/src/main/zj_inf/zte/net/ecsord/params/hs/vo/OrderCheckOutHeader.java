package zte.net.ecsord.params.hs.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderCheckOutHeader implements Serializable {

	@ZteSoftCommentAnnotationParam(name="采购凭证的项目编号",type="String",isNecessary="Y",desc="采购凭证的项目编号")
	private String EBELP;
	@ZteSoftCommentAnnotationParam(name="库存地点",type="String",isNecessary="Y",desc="库存地点")
	private String LGORT;
	@ZteSoftCommentAnnotationParam(name="商品编码",type="String",isNecessary="Y",desc="商品编码")
	private String MATNR;
	@ZteSoftCommentAnnotationParam(name="商品数量",type="String",isNecessary="Y",desc="商品数量")
	private String MENGE;
	@ZteSoftCommentAnnotationParam(name="物流公司入库单号",type="String",isNecessary="Y",desc="物流公司入库单号")
	private String XBLNR;
	@ZteSoftCommentAnnotationParam(name="物流公司订单号",type="String",isNecessary="Y",desc="物流公司订单号")
	private String BKTXT;
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="Y",desc="经代销标示")
	private String SOBKZ;
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="Y",desc="预留字段2")
	private String RESERVE2;
	@ZteSoftCommentAnnotationParam(name="预留字段3",type="String",isNecessary="Y",desc="预留字段3")
	private String RESERVE3;
	
	@ZteSoftCommentAnnotationParam(name="出库指令传输行项目",type="List",isNecessary="Y",desc="出库指令传输行项目")
	private List<OrderCheckOutHeaderItems> ITEMS = new ArrayList<OrderCheckOutHeaderItems>();

	public String getEBELP() {
		return EBELP;
	}

	public void setEBELP(String eBELP) {
		EBELP = eBELP;
	}

	public String getLGORT() {
		return LGORT;
	}

	public void setLGORT(String lGORT) {
		LGORT = lGORT;
	}

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getMENGE() {
		return MENGE;
	}

	public void setMENGE(String mENGE) {
		MENGE = mENGE;
	}

	public String getXBLNR() {
		return XBLNR;
	}

	public void setXBLNR(String xBLNR) {
		XBLNR = xBLNR;
	}

	public String getBKTXT() {
		return BKTXT;
	}

	public void setBKTXT(String bKTXT) {
		BKTXT = bKTXT;
	}

	public String getRESERVE2() {
		return RESERVE2;
	}

	public void setRESERVE2(String rESERVE2) {
		RESERVE2 = rESERVE2;
	}

	public String getRESERVE3() {
		return RESERVE3;
	}

	public void setRESERVE3(String rESERVE3) {
		RESERVE3 = rESERVE3;
	}

	public List<OrderCheckOutHeaderItems> getITEMS() {
		return ITEMS;
	}

	public void setITEMS(List<OrderCheckOutHeaderItems> iTEMS) {
		ITEMS = iTEMS;
	}

	public String getSOBKZ() {
		return SOBKZ;
	}

	public void setSOBKZ(String sOBKZ) {
		SOBKZ = sOBKZ;
	}

	
}
