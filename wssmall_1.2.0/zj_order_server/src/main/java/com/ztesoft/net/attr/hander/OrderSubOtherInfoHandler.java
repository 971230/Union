package com.ztesoft.net.attr.hander;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderSubOtherInfoBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.model.AttrDef;

import consts.ConstsCore;

public class OrderSubOtherInfoHandler extends BaseSupport implements IAttrHandler{

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
		
		OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = new OrderSubOtherInfoBusiRequest();
		
		String subotherinfo = orderAttrValues.get(AttrConsts.SUBOTHER_INFO)==null?"": orderAttrValues.get(AttrConsts.SUBOTHER_INFO).toString();
		if (!StringUtils.isEmpty(subotherinfo) && !"null".equals(subotherinfo) && !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(subotherinfo)) {
				JSONObject jsonObject = JSONObject.fromObject(subotherinfo);
				String user_group = jsonObject.get("user_group")==null?"":jsonObject.getString("user_group");
				String lan_school = jsonObject.get("lan_school")==null?"":jsonObject.getString("lan_school");
				String complex_grid_id = jsonObject.get("complex_grid_id")==null?"":jsonObject.getString("complex_grid_id");
				String cust_building_id = jsonObject.get("cust_building_id")==null?"":jsonObject.getString("cust_building_id");
				String grp_grid_id = jsonObject.get("grp_grid_id")==null?"":jsonObject.getString("grp_grid_id");
				String grpuser_ope_model = jsonObject.get("grpuser_ope_model")==null?"":jsonObject.getString("grpuser_ope_model");
				String group_code = jsonObject.get("group_code")==null?"":jsonObject.getString("group_code");
				String cust_id = jsonObject.get("cust_id")==null?"":jsonObject.getString("cust_id");
				String certify_flag = jsonObject.get("certify_flag")==null?"":jsonObject.getString("certify_flag");
				String certify_cert_type = jsonObject.get("certify_cert_type")==null?"":jsonObject.getString("certify_cert_type");
				String certify_cert_num = jsonObject.get("certify_cert_num")==null?"":jsonObject.getString("certify_cert_num");
				String certify_cust_name = jsonObject.get("certify_cust_name")==null?"":jsonObject.getString("certify_cust_name");
				String certify_cert_addr = jsonObject.get("certify_cert_addr")==null?"":jsonObject.getString("certify_cert_addr");
				
				Map mapValue = new HashMap();
				mapValue.put("sub_other_info_inst_id",this.baseDaoSupport.getSequences("seq_sub_other_info"));
				mapValue.put("order_id", order_id);
				mapValue.put("user_group", user_group);
				mapValue.put("lan_school", lan_school);
				mapValue.put("complex_grid_id", complex_grid_id);
				mapValue.put("cust_building_id", cust_building_id);
				mapValue.put("grp_grid_id", grp_grid_id);
				mapValue.put("grpuser_ope_model", grpuser_ope_model);
				mapValue.put("group_code", group_code);
				mapValue.put("cust_id", cust_id);
				mapValue.put("certify_flag", certify_flag);
				mapValue.put("certify_cert_type", certify_cert_type);
				mapValue.put("certify_cert_num", certify_cert_num);
				mapValue.put("certify_cust_name", certify_cust_name);
				mapValue.put("certify_cert_addr", certify_cert_addr);
                
				try {
					BeanUtils.copyProperties(orderSubOtherInfoBusiRequest, mapValue);
					orderSubOtherInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					orderSubOtherInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderSubOtherInfoBusiRequest.store();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
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
}