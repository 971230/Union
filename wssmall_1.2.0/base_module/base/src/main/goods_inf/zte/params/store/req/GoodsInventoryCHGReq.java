package zte.params.store.req;

import org.apache.commons.lang3.StringUtils;

import params.ZteError;
import params.ZteRequest;
import zte.params.store.resp.GoodsInventoryCHGResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import commons.CommonTools;

import consts.ConstsCore;

public class GoodsInventoryCHGReq extends ZteRequest<GoodsInventoryCHGResp> {
	
	@ZteSoftCommentAnnotationParam(name="货品标识", type="String", isNecessary="Y", desc="货品标识")
	private String product_id;
	
	@ZteSoftCommentAnnotationParam(name="物理仓标识", type="String", isNecessary="Y", desc="物理仓标识")
	private String house_id;
	
	@ZteSoftCommentAnnotationParam(name="货品货主编码", type="String", isNecessary="Y", desc="货品货主编码")
	private String company_code;
	
	@ZteSoftCommentAnnotationParam(name="货品SKU", type="String", isNecessary="Y", desc="货品SKU")
	private String sku;
	
//	@ZteSoftCommentAnnotationParam(name="操作编码", type="String", isNecessary="Y", desc="操作编码：0-出库；1-入库；2-回收")
//	private String action_code;
	
	@ZteSoftCommentAnnotationParam(name="变动数量", type="String", isNecessary="Y", desc="变动数量(整数)")
	private String chg_num;
	
	@ZteSoftCommentAnnotationParam(name="变动原因", type="String", isNecessary="Y", desc="变动原因")
	private String chg_reason;
	
	@ZteSoftCommentAnnotationParam(name="采购订单编号", type="String", isNecessary="N", desc="采购订单编号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name="货品库存变动动作", type="String", isNecessary="N", desc="PI采购入库 SO订单发货  IR内部直接出库，IA调拨出库")
	private String action;
	
	
	
	public String getProduct_id() {
		return product_id;
	}


	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}


	public String getSku() {
		return sku;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}


	public String getHouse_id() {
		return house_id;
	}


	public void setHouse_id(String house_id) {
		this.house_id = house_id;
	}

//	public String getAction_code() {
//		return action_code;
//	}
//
//
//	public void setAction_code(String action_code) {
//		this.action_code = action_code;
//	}


	public String getChg_num() {
		return chg_num;
	}


	public void setChg_num(String chg_num) {
		this.chg_num = chg_num;
	}


	public String getChg_reason() {
		return chg_reason;
	}


	public void setChg_reason(String chg_reason) {
		this.chg_reason = chg_reason;
	}


	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getAction(){
		return action;
	}
	public void setAction(String action){
		this.action = action;
	}
	
	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}


	@Override
	public void check() throws ApiRuleException {
		
		if (StringUtils.isEmpty(product_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "货品标识不能为空"));
		}
		
		if (StringUtils.isEmpty(house_id)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "物理仓标识不能为空"));
		}
		
		if (StringUtils.isEmpty(sku)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "货品SKU不能为空"));
		}
		
//		if (StringUtils.isEmpty(action_code)) {
//			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "操作编码不能为空"));
//		}
		if (StringUtils.isEmpty(action)) {
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "货品库存变动动作不能为空"));
		}

		try{
			Integer.valueOf(chg_num);
		}catch(Exception e){
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "变动数量必须为整数"));
		}
		
	}

	
	@Override
	public String getApiMethodName() {
		return "zte.service.inventory.chg";
	}

}
