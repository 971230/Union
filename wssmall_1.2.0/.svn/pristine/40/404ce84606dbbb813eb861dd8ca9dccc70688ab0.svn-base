package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class InventoryRollbackReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="货品ID",type="String",isNecessary="Y",desc="product_id：货品ID。")
	private String product_id;
	@ZteSoftCommentAnnotationParam(name="物理仓ID",type="String",isNecessary="Y",desc="house_id：物理仓ID。")
	private String house_id;
	@ZteSoftCommentAnnotationParam(name="回滚数量",type="int",isNecessary="Y",desc="rollback_num：库存回滚数量。")
	private int rollback_num;
	
	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	public int getRollback_num() {
		return rollback_num;
	}

	public void setRollback_num(int rollback_num) {
		this.rollback_num = rollback_num;
	}


	@Override
	public void check() throws ApiRuleException {
		if(product_id==null || "".equals(product_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "product_id不允许为空"));
		if(house_id==null || "".equals(house_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "house_id物理仓ID不允许为空"));
	}

	
	@Override
	public String getApiMethodName() {
		return "com.warehouseService.inventory.rollback";
	}

}
