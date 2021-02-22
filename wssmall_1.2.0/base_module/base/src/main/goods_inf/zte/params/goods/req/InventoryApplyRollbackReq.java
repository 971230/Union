package zte.params.goods.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class InventoryApplyRollbackReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="订单标识",type="String",isNecessary="Y",desc="order_id：订单标识。")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="goods_id：商品ID。")
	private String goods_id;
	@ZteSoftCommentAnnotationParam(name="物理仓ID",type="String",isNecessary="Y",desc="house_id：物理仓ID。")
	private String house_id;
	@ZteSoftCommentAnnotationParam(name="虚拟仓ID",type="String",isNecessary="N",desc="virtual_house_id：虚拟仓ID。")
	private String virtual_house_id;
	@ZteSoftCommentAnnotationParam(name="回滚数量",type="int",isNecessary="Y",desc="rollback_num：库存回滚数量。")
	private int rollback_num;
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getVirtual_house_id() {
		return virtual_house_id;
	}

	public void setVirtual_house_id(String virtual_house_id) {
		this.virtual_house_id = virtual_house_id;
	}

	public int getRollback_num() {
		return rollback_num;
	}

	public void setRollback_num(int rollback_num) {
		this.rollback_num = rollback_num;
	}

	public String getHouse_id() {
		return house_id;
	}

	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	@Override
	public void check() throws ApiRuleException {
		if(order_id==null || "".equals(order_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "order_id不允许为空"));
		if(goods_id==null || "".equals(goods_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "goods_id不允许为空"));
		if(house_id==null || "".equals(house_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "house_id不允许为空"));
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "com.warehouseService.inventory.apply.rollback";
	}

}
