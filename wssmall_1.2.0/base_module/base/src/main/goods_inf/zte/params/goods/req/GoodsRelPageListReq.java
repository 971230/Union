package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsRelPageListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="关联类型",type="String",isNecessary="N",desc="rel_type：关联类型，如终端关联计划：TERMINAL_PLAN。")
	private String rel_type;
	@ZteSoftCommentAnnotationParam(name="关联商品ID",type="String",isNecessary="N",desc="a_goods_id：关联商品ID。")
	private String a_goods_id;
		
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="Y",desc="页码")
	private int pageNo=1;
	@ZteSoftCommentAnnotationParam(name="每页数量",type="String",isNecessary="Y",desc="每页数量")
	private int pageSize=10;
    @ZteSoftCommentAnnotationParam(name="商品子服务类型",type="String",isNecessary="N",desc="sub_stype_id：商品子服务类型。")
    private String sub_stype_id;

	
	public String getRel_type() {
		return rel_type;
	}

	public void setRel_type(String rel_type) {
		this.rel_type = rel_type;
	}

	public String getA_goods_id() {
		return a_goods_id;
	}

	public void setA_goods_id(String a_goods_id) {
		this.a_goods_id = a_goods_id;
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

    public String getSub_stype_id() {
        return sub_stype_id;
    }

    public void setSub_stype_id(String sub_stype_id) {
        this.sub_stype_id = sub_stype_id;
    }

    @Override
	public void check() throws ApiRuleException {
		if(rel_type==null || "".equals(rel_type))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "rel_type不能为空"));
		if(a_goods_id==null || "".equals(a_goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "a_goods_id不能为空"));
	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.relGoods.list";
	}

}
