package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;
import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class GoodsPageListByTypeReq extends ZteRequest {

	@ZteSoftCommentAnnotationParam(name="商品类型ID",type="String",isNecessary="Y",desc="type_id：商品类型ID，获取该类型的所有商品。")
	private String type_id;
	@ZteSoftCommentAnnotationParam(name="商品价格",type="String",isNecessary="N",desc="price：商品价格，获取价格>=price的商品。")
	private String price;
	
	@ZteSoftCommentAnnotationParam(name="商品子类ID",type="String",isNecessary="N",desc="sub_stype_id:商品子类ID,1 主副卡套餐; 2上网卡套餐;3 box套餐;  0 或者空普通套餐;")
	private String sub_stype_id;
	//@ZteSoftCommentAnnotationParam(name="商品归属系统",type="String",isNecessary="Y",desc="source_from：商品归属系统。")
	private String source_from;
	private int pageNo=1;
	private int pageSize=10;
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getSource_from() {
		return source_from;
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
		if(type_id==null) CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL,"type_id不能为空"));

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goods.queryByType";
	}

}
