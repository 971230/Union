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
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;
/**
 * 商城优惠信息
 * @author zou.qh
 *
 */
public class ActivityListHandler extends BaseSupport implements IAttrHandler {

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
		
		String activity_list = (String) orderAttrValues.get(AttrConsts.ACTIVITY_LIST);
		if(!StringUtils.isEmpty(activity_list) && !"-1".equals(activity_list)){
			saveActivityList(order_id,inst_id,activity_list);
		}
		
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
		return resp;
	}
	
	/**
	 * 保存商城优惠信息到业务表，优惠信息从活动表获取
	 * @param order_id
	 * @param inst_id
	 */
	public void saveActivityList(String order_id, String inst_id, String activity_list){
		JSONArray jsonArray = JSONArray.fromObject(activity_list);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
			//取优惠活动编码
			String activity_code = jsonObject.get("activity_code")==null?"":jsonObject.get("activity_code").toString();

			ActivitySpecReq req = new ActivitySpecReq();
			req.setActivity_code(activity_code);
			ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
			ActivitySpecResp response = client.execute(req, ActivitySpecResp.class);
			Map specs = response.getSpecs();
			
			Map mapValue = new HashMap();
			mapValue.put("discount_inst_id", this.baseDaoSupport.getSequences("SEQ_ATTR_DISCOUNT_INFO"));
			mapValue.put("order_id", order_id);
			mapValue.put("inst_id", inst_id);
			mapValue.put("activity_code", Const.getStrValue(specs, "activity_code"));
			mapValue.put("activity_name", Const.getStrValue(specs, "activity_name"));
			mapValue.put("activity_desc", Const.getStrValue(specs, "activity_desc"));
			mapValue.put("activity_type", "3");
			mapValue.put("discount_range", "");
			String pmt_solution = Const.getStrValue(specs, "disacount_num");
			if(StringUtils.isEmpty(pmt_solution))
				pmt_solution = "0";
			mapValue.put("discount_num", pmt_solution);
			mapValue.put("discount_unit", "02");
			mapValue.put("source_from", ManagerUtils.getSourceFrom());
//			this.baseDaoSupport.insert("es_attr_discount_info", mapValue);
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
