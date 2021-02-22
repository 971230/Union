package zte.params.store.req;

import com.ztesoft.api.ApiRuleException;
import org.apache.commons.lang3.StringUtils;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteError;
import params.ZteRequest;
import zte.params.store.resp.InventoryReduceResp;

public class InventoryReduceReq extends ZteRequest<InventoryReduceResp> {
	
	@ZteSoftCommentAnnotationParam(name="订单标识", type="String", isNecessary="Y", desc="订单标识")
	private String order_id;
	
	@ZteSoftCommentAnnotationParam(name="商品标识", type="String", isNecessary="Y", desc="商品标识")
	private String goods_id;
	
	@ZteSoftCommentAnnotationParam(name="物理仓标识", type="String", isNecessary="Y", desc="物理仓标识")
	private String house_id;
	
	@ZteSoftCommentAnnotationParam(name="销售组织标识", type="String", isNecessary="Y", desc="销售组织标识")
	private String org_id;
	
	@ZteSoftCommentAnnotationParam(name="商品数量", type="int", isNecessary="Y", desc="商品下单数量")
	private int num;
	
	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public String getGoods_id() {
		return goods_id;
	}


	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}


	public String getHouse_id() {
		return house_id;
	}


	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}
	
	public String getOrg_id() {
		return org_id;
	}


	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}
	
	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(order_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "订单标识不允许为空"));
		}
		
		if (StringUtils.isEmpty(goods_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "商品标识不允许为空"));
		}
		
		if (StringUtils.isEmpty(house_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "物理仓标识不允许为空"));
		}
		
		if (StringUtils.isEmpty(org_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "销售组织标识不允许为空"));
		}
	}

	
	@Override
	public String getApiMethodName() {
		return "com.warehouseService.inventory.reduce";
	}

}
