package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import consts.ConstsCore;

/**
 * 新商城优惠券列表
 * @author duan.shaochu
 *
 */
public class CouponslistHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		
		String couponsList = (String) orderAttrValues.get(AttrConsts.COUPONS_LIST);
		if(!StringUtils.isEmpty(couponsList) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(couponsList)){
			saveCouponslist(order_id,inst_id,couponsList);
		}
		
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
		return resp;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 新商城优惠券列表
	 * @param order_id
	 * @param inst_id
	 * @param couponsList
	 */
	public void saveCouponslist(String order_id, String inst_id, String couponsList){		
		JSONArray jsonArray = JSONArray.fromObject(couponsList);
		String discountType = "";
		String discountID = "";
		String discountName = "";
		String discountValue = "";
		String disacount_range = "";
		String disacount_unit = "02";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			discountType = jsonObject.get("coupons_type")==null?"":jsonObject.get("coupons_type").toString();
			discountID = jsonObject.get("coupons_id")==null?"":jsonObject.get("coupons_id").toString();
			discountName = jsonObject.get("coupons_name")==null?"":jsonObject.get("coupons_name").toString();
			discountValue = jsonObject.get("coupons_fee")==null?"":jsonObject.get("coupons_fee").toString();

			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", discountID);
			mapValue.put("activity_name", discountName);
			mapValue.put("activity_desc", discountName);
			mapValue.put("activity_type", discountType);
			mapValue.put("discount_range", disacount_range);
			mapValue.put("discount_num", discountValue);
			mapValue.put("discount_unit", disacount_unit);
			mapValue.put("source_from", ManagerUtils.getSourceFrom());
			
			AttrDiscountInfoBusiRequest attrDiscountInfoBusiRequest = new AttrDiscountInfoBusiRequest();
			try {
				BeanUtils.copyProperties(attrDiscountInfoBusiRequest, mapValue);
				attrDiscountInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				attrDiscountInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				attrDiscountInfoBusiRequest.store();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
