package com.ztesoft.remote.basic.params.resp;

import java.util.Map;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2014-02-12 09:25
 * To change this template use File | Settings | File Templates.
 */
public class CurrentPackageResponse extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="套餐名称",type="String",isNecessary="N",desc="套餐名称")
	private String pack_name;
	
	@ZteSoftCommentAnnotationParam(name="销售实例ID",type="String",isNecessary="N",desc="销售实例ID")
	private String prodOfferInstId;
	
	@ZteSoftCommentAnnotationParam(name="产品ID",type="String",isNecessary="N",desc="产品ID")
	private String prodInstId;
	
	@ZteSoftCommentAnnotationParam(name="销售实例名称",type="String",isNecessary="N",desc="宽度")
	private String prodOfferName;
	
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="开始时间")
	private String startDt;
	
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="结束时间")
	private String endDt;
	
	@ZteSoftCommentAnnotationParam(name="本地商品信息",type="String",isNecessary="N",desc="本地商品信息")
	private Map goodsMap;
	
	@ZteSoftCommentAnnotationParam(name="主销售品业务编码",type="String",isNecessary="N",desc="主销售品业务编码")
	private String prodOfferNbr;

	public String getPack_name() {
		return pack_name;
	}

	public void setPack_name(String pack_name) {
		this.pack_name = pack_name;
	}

	public String getProdOfferInstId() {
		return prodOfferInstId;
	}

	public void setProdOfferInstId(String prodOfferInstId) {
		this.prodOfferInstId = prodOfferInstId;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public void setProdInstId(String prodInstId) {
		this.prodInstId = prodInstId;
	}

	public String getProdOfferName() {
		return prodOfferName;
	}

	public void setProdOfferName(String prodOfferName) {
		this.prodOfferName = prodOfferName;
	}

	public String getStartDt() {
		return startDt;
	}

	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	public String getEndDt() {
		return endDt;
	}

	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	public Map getGoodsMap() {
		return goodsMap;
	}

	public void setGoodsMap(Map goodsMap) {
		this.goodsMap = goodsMap;
	}

	public String getProdOfferNbr() {
		return prodOfferNbr;
	}

	public void setProdOfferNbr(String prodOfferNbr) {
		this.prodOfferNbr = prodOfferNbr;
	}
}
