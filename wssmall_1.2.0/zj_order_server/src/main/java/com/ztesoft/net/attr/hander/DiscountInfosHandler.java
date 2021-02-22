package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.AttrDiscountInfoBusiRequest;
import zte.net.ecsord.params.spec.req.ActivitySpecReq;
import zte.net.ecsord.params.spec.resp.ActivitySpecResp;

import com.powerise.ibss.framework.Const;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;
/**
 * 总部优惠信息
 * @author zou.qh
 *
 */
public class DiscountInfosHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		String goods_id = params.getGoods_id(); // 商品ID 只有子订单或货品单才有值
		String pro_goods_id = params.getPro_goods_id(); // 货品ID 只有货品单才有值
		String order_from = params.getOrder_from(); // 订单来源
		String value = params.getValue(); // 原始值
		AttrDef attrDef = params.getAttrDef(); // 属性定议配置信息
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		String plat_type = (String) orderAttrValues.get(AttrConsts.PLAT_TYPE);
		boolean isTbOrder = CommonDataFactory.getInstance().isTbOrder(order_from);//是否淘宝订单,默认否
		if(CommonDataFactory.getInstance().isZbOrder(plat_type)){
			String discountInfos = (String) orderAttrValues.get(AttrConsts.DISCOUNT_INFOS);
			if(!StringUtils.isEmpty(discountInfos) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(discountInfos)){
				saveZBDiscountInfo(order_id, inst_id, discountInfos);
			}
		}
		else if(isTbOrder/*EcsOrderConsts.ORDER_FROM_10001.equals(order_from)*/){//淘宝优惠信息
			String pmt_code = Const.getStrValue(orderAttrValues, AttrConsts.RESERVE9);
			saveTbDiscountInfo(order_id, inst_id, pmt_code);
		}
		else{
			String discountInfos = (String) orderAttrValues.get(AttrConsts.DISCOUNT_INFOS);
			saveDiscountInfo(order_id, inst_id, discountInfos);
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
		return resp;
	}
	
	public void saveTbDiscountInfo(String order_id, String inst_id, String pmt_code){
		ActivitySpecReq req = new ActivitySpecReq();
		req.setActivity_code(pmt_code);
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		ActivitySpecResp response = client.execute(req, ActivitySpecResp.class);
		Map specs = response.getSpecs();
		if(specs != null && !specs.isEmpty()){
			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", Const.getStrValue(specs, "activity_code"));
			mapValue.put("activity_name", Const.getStrValue(specs, "activity_name"));
			mapValue.put("activity_desc", Const.getStrValue(specs, "activity_desc"));
			mapValue.put("activity_type", "3");
			mapValue.put("discount_range", "");
			String disacount_num = Const.getStrValue(specs, "disacount_num");
			if(StringUtils.isEmpty(disacount_num))
				disacount_num = "0";
			mapValue.put("discount_num", disacount_num);
			mapValue.put("discount_unit", "02");
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
		
		//this.baseDaoSupport.insert("es_attr_discount_info", mapValue);
	}
	
	/**
	 * 保存总部优惠信息到业务表，优惠信息取总部同步过来的优惠信息
	 * @param order_id
	 * @param inst_id
	 */
	public void saveZBDiscountInfo(String order_id, String inst_id, String discountInfos){
		
		JSONArray jsonArray = JSONArray.fromObject(discountInfos);
		String activity_type = "";
		String discountType = "";
		String discountID = "";
		String discountName = "";
		String discountValue = "";
		String disacount_range = "";
		String disacount_unit = "02";
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			discountType = jsonObject.get("discountType")==null?"":jsonObject.get("discountType").toString();
			discountID = jsonObject.get("discountID")==null?"":jsonObject.get("discountID").toString();
			discountName = jsonObject.get("discountName")==null?"":jsonObject.get("discountName").toString();
			discountValue = jsonObject.get("discountValue")==null?"":jsonObject.get("discountValue").toString();

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
			
//			this.baseDaoSupport.insert("es_attr_discount_info", mapValue);

		}
	}
	
	/**
	 * 保存优惠信息到业务表
	 * @param order_id
	 * @param inst_id
	 */
	public void saveDiscountInfo(String order_id, String inst_id, String discountInfos){
		String activity_type = "";
		String discountType = "";
		String discountID = "";
		String discountName = "";
		String discountDesc = "";
		String discountValue = "";
		String disacount_range = "";
		String disacount_unit = "02";
		if(StringUtils.isNotEmpty(discountInfos)){
			JSONArray jsonArray = JSONArray.fromObject(discountInfos);
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				discountID = jsonObject.get("activity_code")==null?"":jsonObject.get("activity_code").toString();
				discountName = jsonObject.get("activity_name")==null?"":jsonObject.get("activity_name").toString();
				discountDesc = jsonObject.get("activity_desc")==null?"":jsonObject.get("activity_desc").toString();
				discountType = jsonObject.get("activity_type")==null?"":jsonObject.get("activity_type").toString();
				discountValue = jsonObject.get("discount_amount")==null?"":jsonObject.get("discount_amount").toString();

				Map mapValue = new HashMap();
				mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
				mapValue.put("order_id", order_id);
				mapValue.put("inst_id", inst_id);
				mapValue.put("activity_code", discountID);
				mapValue.put("activity_name", discountName);
				mapValue.put("activity_desc", discountDesc);
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
				
//				this.baseDaoSupport.insert("es_attr_discount_info", mapValue);

			}
		}
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

}
