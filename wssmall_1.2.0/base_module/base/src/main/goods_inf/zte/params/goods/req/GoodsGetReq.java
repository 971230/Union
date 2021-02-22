package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsGetReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="N",desc="goods_id：商品ID。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="商品包ID",type="String",isNecessary="N",desc="package_id：商品包ID。")
	private String package_id;
	@ZteSoftCommentAnnotationParam(name="条形码",type="String",isNecessary="N",desc="sn：条形码。")
	private String sn;
	@ZteSoftCommentAnnotationParam(name="华盛物料号",type="String",isNecessary="N",desc="matnr：华盛物料号。")
	private String matnr;

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getPackage_id() {
		return package_id;
	}

	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Override
	public void check() throws ApiRuleException {
		if((goods_id==null | "".equals(goods_id))&&(package_id==null | "".equals(package_id)) && (sn==null | "".equals(sn)) ) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "组合查询，goods_id,package_id,sn不能同时为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.orderinfo.get";
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

}
