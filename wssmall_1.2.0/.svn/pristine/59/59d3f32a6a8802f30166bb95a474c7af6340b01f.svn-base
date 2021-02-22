package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class EcsGoodsPageListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="商品货品名称",type="String",isNecessary="N",desc="name：商品货品名称。")
	private String name;
	@ZteSoftCommentAnnotationParam(name="商品货品分类ID",type="String",isNecessary="N",desc="cat_id：商品货品分类ID。")
	private String cat_id;
	@ZteSoftCommentAnnotationParam(name="商品货品品牌ID",type="String",isNecessary="N",desc="brand_id：商品货品品牌ID。")
	private String brand_id;
	@ZteSoftCommentAnnotationParam(name="类型",type="String",isNecessary="Y",desc="type：类型，查询商品：type=goods，查询货品：type=product。")
	private String type;
	@ZteSoftCommentAnnotationParam(name="商品货品状态",type="int",isNecessary="N",desc="market_enable：商品货品状态，1：启用，0：停用。")
	private int market_enable;
	@ZteSoftCommentAnnotationParam(name="货品发布组织",type="String",isNecessary="N",desc="org_ids：货品发布组织。")
	private String org_ids;
	
	@ZteSoftCommentAnnotationParam(name="页数",type="int",isNecessary="Y",desc="pageNo：页数。")
	private int pageNo;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="int",isNecessary="Y",desc="pageSize：分页大小。")
	private int pageSize;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCat_id() {
		return cat_id;
	}

	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public int getMarket_enable() {
		return market_enable;
	}

	public void setMarket_enable(int market_enable) {
		this.market_enable = market_enable;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrg_ids() {
		return org_ids;
	}

	public void setOrg_ids(String org_ids) {
		this.org_ids = org_ids;
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
		if(type==null | "".equals(type)) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"type不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.ecs.goods.pageList";
	}

}
