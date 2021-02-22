package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class OfferChangeListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品类型",type="String",isNecessary="Y",desc="goodsType：商品类型， 不能为空。")
	private String goodsType;
	//@ZteSoftCommentAnnotationParam(name="页数",type="String",isNecessary="Y",desc="pageNo：页数， 不能为空。")
	private int pageNo;
	//@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="pageSize：分页大小， 不能为空。")
	private int pageSize;
	@ZteSoftCommentAnnotationParam(name="套餐子类型",type="String",isNecessary="N",desc="goodsType：套餐子类型， 不能为空。")
	private String sub_stype_id;
	public String getSub_stype_id() {
		return sub_stype_id;
	}

	public void setSub_stype_id(String sub_stype_id) {
		this.sub_stype_id = sub_stype_id;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		if(goodsType==null || "".equals(goodsType))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "goodsType不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.offer.change.list";
	}

}
