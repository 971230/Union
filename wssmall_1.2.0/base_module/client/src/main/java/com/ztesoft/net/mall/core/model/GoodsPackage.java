package com.ztesoft.net.mall.core.model;


/**
 * 商品包
 * @author kingapex
 *2010-4-25下午09:40:24
 */
public class GoodsPackage implements java.io.Serializable {
	
	private String goods_id;
	private String p_code;
	private String sn;
	private String source_from;
	private String hs_matnr;
	
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getP_code() {
		return p_code;
	}
	public void setP_code(String p_code) {
		this.p_code = p_code;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getSource_from() {
		return source_from;
	}
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
	public String getHs_matnr() {
		return hs_matnr;
	}
	public void setHs_matnr(String hs_matnr) {
		this.hs_matnr = hs_matnr;
	}

	
}