package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderAdslBusiRequest;

public class OrderZhwqAdslHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值

		// orderAttrValues 包含值
		String rhBroadinfo = (String) orderAttrValues.get(AttrConsts.RHBROAD_INFO);
		String broadinfo = (String) orderAttrValues.get(AttrConsts.BROAD_INFO);

		if (!StringUtils.isEmpty(rhBroadinfo) && !"null".equals(rhBroadinfo)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(rhBroadinfo)) {
			JSONArray jsonArray = JSONArray.fromObject(rhBroadinfo);
			for (int i = 0; i < jsonArray.size(); i++) {
				OrderAdslBusiRequest orderAdslBusiRequest = new OrderAdslBusiRequest();
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				String product_code = jsonObject.get("product_code") == null ? ""
						: jsonObject.getString("product_code");//
				String service_type = jsonObject.get("service_type") == null ? ""
						: jsonObject.getString("service_type");//
				String std_address = jsonObject.get("std_address") == null ? "" : jsonObject.getString("std_address");//
				String adsl_addr_desc = jsonObject.get("adsl_addr_desc") == null ? ""
						: jsonObject.getString("adsl_addr_desc");//
				String user_address = jsonObject.get("user_address") == null ? ""
						: jsonObject.getString("user_address");//
				String exch_code = jsonObject.get("exch_code") == null ? "" : jsonObject.getString("exch_code");//
				String area_exch_id = jsonObject.get("area_exch_id")==null?"":jsonObject.getString("area_exch_id");//
				String point_exch_id = jsonObject.get("point_exch_id")==null?"":jsonObject.getString("point_exch_id");//
				String module_exch_id = jsonObject.get("module_exch_id")==null?"":jsonObject.getString("module_exch_id");//
				String grid = "";
				if (jsonObject.containsKey("grid")) {
					grid = jsonObject.get("grid") == null ? "" : jsonObject.getString("grid");// 固网网格
				}
				String adsl_speed = "";
                if (jsonObject.containsKey("kd_low_rate")) {
                    adsl_speed = jsonObject.get("kd_low_rate") == null ? "" : jsonObject.getString("kd_low_rate");// 宽带速率
                }
                String sale_mode = "";
                if (jsonObject.containsKey("sale_mode")) {
                    sale_mode = jsonObject.get("sale_mode") == null ? "" : jsonObject.getString("sale_mode");
                }
                String devic_gear = "";
                if (jsonObject.containsKey("devic_gear")) {
                    devic_gear = jsonObject.get("devic_gear") == null ? "" : jsonObject.getString("devic_gear");
                }
                String is_done_active = "";
                if (jsonObject.containsKey("is_done_active")) {
                    is_done_active = jsonObject.get("is_done_active") == null ? "" : jsonObject.getString("is_done_active");
                }
				String is_judge_address = jsonObject.get("is_judge_address") == null ? ""
						: jsonObject.getString("is_judge_address");
				String user_kind = jsonObject.get("user_kind") == null ? "" : jsonObject.getString("user_kind");//
				String deal_operator = jsonObject.get("deal_operator") == null ? ""
						: jsonObject.getString("deal_operator");
				String deal_office_id = jsonObject.get("deal_office_id") == null ? ""
						: jsonObject.getString("deal_office_id");
				String bb_account = jsonObject.get("bb_account") == null ? "" : jsonObject.getString("bb_account");//
				String bb_num = jsonObject.get("bb_num") == null ? "" : jsonObject.getString("bb_num");//

				String adsl_password = jsonObject.get("bb_password") == null ? "" : jsonObject.getString("bb_password");// 密码

				String appt_date = jsonObject.get("appt_date") == null ? "" : jsonObject.getString("appt_date");
				String appo_date = jsonObject.get("appo_date") == null ? "" : jsonObject.getString("appo_date");
				if (StringUtils.isNotEmpty(appo_date)) {
					appt_date = appo_date;
				}
				String county_id = jsonObject.get("county_id") == null ? "" : jsonObject.getString("county_id");
				String develop_point_code = jsonObject.get("develop_point_code") == null ? ""
						: jsonObject.getString("develop_point_code");
				String develop_point_name = jsonObject.get("develop_point_name") == null ? ""
						: jsonObject.getString("develop_point_name");
				String moderm_id = jsonObject.get("moderm_id") == null ? "" : jsonObject.getString("moderm_id");
				String moderm_name = jsonObject.get("moderm_name") == null ? "" : jsonObject.getString("moderm_name");
				String req_swift_num = jsonObject.get("req_swift_num") == null ? ""
						: jsonObject.getString("req_swift_num");
				String access_type = jsonObject.get("access_type") == null ? "" : jsonObject.getString("access_type");
				String object_status = jsonObject.get("object_status") == null ? ""
						: jsonObject.getString("object_status");
				String moderm_status = jsonObject.get("moderm_status") == null ? ""
						: jsonObject.getString("moderm_status");
				if (StringUtils.isNotEmpty(moderm_status)) {
					object_status = moderm_status;
				}
				String develop_code = jsonObject.get("develop_code") == null ? "" : jsonObject.getString("develop_code");
	            String develop_name = jsonObject.get("develop_name") == null ? "" : jsonObject.getString("develop_name");
				String business_county = jsonObject.get("business_county") == null ? ""
						: jsonObject.getString("business_county");
				String product_type = jsonObject.get("product_type") == null ? ""
						: jsonObject.getString("product_type");
				String wotv_num = jsonObject.get("wotv_num") == null ? "" : jsonObject.getString("wotv_num");
				String old_service_type = jsonObject.get("old_service_type") == null ? ""
						: jsonObject.getString("old_service_type");

				String account_number = jsonObject.get("account_number")==null?"":jsonObject.getString("account_number");
				
				//宽带新增字段old_obj_esn  old_obj_owner
				String old_obj_esn = jsonObject.get("old_obj_esn") == null ? "" 
						: jsonObject.getString("old_obj_esn");
				
				String old_obj_owner = jsonObject.get("old_obj_owner") == null ? "" 
						: jsonObject.getString("old_obj_owner");
				
				Map mapValue = new HashMap();
				mapValue.put("adsl_inst_id", this.baseDaoSupport.getSequences("seq_adsl_info"));
				mapValue.put("inst_id", inst_id);
				mapValue.put("order_id", order_id);
				mapValue.put("product_code", product_code);// 产品编码
				mapValue.put("adsl_account", bb_account);// 宽带账号
				mapValue.put("adsl_number", bb_num);// 宽带号码
				mapValue.put("adsl_password", adsl_password);// 密码
				mapValue.put("adsl_addr", std_address);// 标准地址
				mapValue.put("adsl_addr_desc", adsl_addr_desc);// 标准地址
				mapValue.put("adsl_speed", adsl_speed);// 速率
				mapValue.put("adsl_grid", grid);// 固网网格
				mapValue.put("user_kind", user_kind);// 用户种类
				mapValue.put("service_type", service_type);// 业务类型
				mapValue.put("exch_code", exch_code);// 局向编码
				mapValue.put("area_exch_id", area_exch_id);//区局
				mapValue.put("point_exch_id", point_exch_id);//端局
				mapValue.put("module_exch_id", module_exch_id);//模块局
				mapValue.put("cust_building_id", "");// 备注
				mapValue.put("total_fee", "");// 宽带总费用
				mapValue.put("user_address", user_address);// 用户地址
				mapValue.put("appt_date", appt_date);// 预约装机时间
				mapValue.put("county_id", county_id);// BSS县份
				mapValue.put("develop_point_code", develop_point_code);// 发展点编码
				mapValue.put("develop_point_name", develop_point_name);// 发展点名称
			    mapValue.put("develop_code", develop_code);// 发展人编码
	            mapValue.put("develop_name", develop_name);// 发展人名称
				mapValue.put("moderm_id", moderm_id);// 光猫ID
				mapValue.put("moderm_name", moderm_name);// 光猫ID中文名称
				mapValue.put("req_swift_num", req_swift_num);// 拍照流水
				mapValue.put("access_type", access_type);// 拍照流水
				mapValue.put("object_status", object_status);// 光猫物品状态
				mapValue.put("business_county", business_county);// 营业县分
				mapValue.put("product_type", product_type);// 产品分类
				mapValue.put("wotv_num", wotv_num);// 沃tv号码
				mapValue.put("old_service_type", old_service_type);// 老业务类型
				mapValue.put("is_judge_address", is_judge_address);
				mapValue.put("is_done_active", is_done_active);
                mapValue.put("sale_mode",sale_mode);
                mapValue.put("devic_gear",devic_gear);
                mapValue.put("old_obj_owner", old_obj_owner);
    			mapValue.put("old_obj_esn", old_obj_esn);
    			mapValue.put("account_number", account_number);
				mapValue.put("old_obj_owner", old_obj_owner);
				mapValue.put("old_obj_esn", old_obj_esn);
				try {
					BeanUtils.copyProperties(orderAdslBusiRequest, mapValue);
					orderAdslBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					orderAdslBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					CommonData.getData().getOrderTreeBusiRequest().getOrderAdslBusiRequest().add(orderAdslBusiRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (!StringUtils.isEmpty(broadinfo) && !"null".equals(broadinfo)
				&& !EcsOrderConsts.ATTR_DEFAULT_VALUE.equals(broadinfo)) {
			OrderAdslBusiRequest orderAdslBusiRequest = new OrderAdslBusiRequest();
			JSONObject jsonObject = JSONObject.fromObject(broadinfo);
			String product_code = jsonObject.get("product_code") == null ? "" : jsonObject.getString("product_code");//
			String service_type = jsonObject.get("service_type") == null ? "" : jsonObject.getString("service_type");//
			String std_address = jsonObject.get("std_address") == null ? "" : jsonObject.getString("std_address");//
			String adsl_addr_desc = jsonObject.get("adsl_addr_desc") == null ? ""
					: jsonObject.getString("adsl_addr_desc");//
			String user_address = jsonObject.get("user_address") == null ? "" : jsonObject.getString("user_address");//
			String exch_code = jsonObject.get("exch_code") == null ? "" : jsonObject.getString("exch_code");//
			String area_exch_id = jsonObject.get("area_exch_id")==null?"":jsonObject.getString("area_exch_id");//
			String point_exch_id = jsonObject.get("point_exch_id")==null?"":jsonObject.getString("point_exch_id");//
			String module_exch_id = jsonObject.get("module_exch_id")==null?"":jsonObject.getString("module_exch_id");//
			String grid = "";// null
			if (jsonObject.containsKey("grid")) {
				grid = jsonObject.get("grid") == null ? "" : jsonObject.getString("grid");// 固网网格
			}
			String adsl_speed = "";
            if (jsonObject.containsKey("kd_low_rate")) {
                adsl_speed = jsonObject.get("kd_low_rate") == null ? "" : jsonObject.getString("kd_low_rate");// 宽带速率
            }
            String sale_mode = "";
            if (jsonObject.containsKey("sale_mode")) {
                sale_mode = jsonObject.get("sale_mode") == null ? "" : jsonObject.getString("sale_mode");
            }
            String devic_gear = "";
            if (jsonObject.containsKey("devic_gear")) {
                devic_gear = jsonObject.get("devic_gear") == null ? "" : jsonObject.getString("devic_gear");
            }
            String is_done_active = "";
            if (jsonObject.containsKey("is_done_active")) {
                is_done_active = jsonObject.get("is_done_active") == null ? "" : jsonObject.getString("is_done_active");
            }
			String is_judge_address = jsonObject.get("is_judge_address") == null ? ""
					: jsonObject.getString("is_judge_address");
			String user_kind = jsonObject.get("user_kind") == null ? "" : jsonObject.getString("user_kind");//
			String deal_operator = jsonObject.get("deal_operator") == null ? "" : jsonObject.getString("deal_operator");
			String deal_office_id = jsonObject.get("deal_office_id") == null ? ""
					: jsonObject.getString("deal_office_id");
			String bb_account = jsonObject.get("bb_account") == null ? "" : jsonObject.getString("bb_account");//
			String bb_num = jsonObject.get("bb_num") == null ? "" : jsonObject.getString("bb_num");// null

			String adsl_password = jsonObject.get("bb_password") == null ? "" : jsonObject.getString("bb_password");// 密码

			String appt_date = jsonObject.get("appt_date") == null ? "" : jsonObject.getString("appt_date");
			String appo_date = jsonObject.get("appo_date") == null ? "" : jsonObject.getString("appo_date");
			if (StringUtils.isNotEmpty(appo_date) && !StringUtils.equals(appo_date, "null")) {
				appt_date = appo_date;
			}
			String county_id = jsonObject.get("county_id") == null ? "" : jsonObject.getString("county_id");

			String develop_point_code = jsonObject.get("develop_point_code") == null ? ""
					: jsonObject.getString("develop_point_code");
			String develop_point_name = jsonObject.get("develop_point_name") == null ? ""
					: jsonObject.getString("develop_point_name");
			String develop_code = jsonObject.get("develop_code") == null ? "" : jsonObject.getString("develop_code");
			String develop_name = jsonObject.get("develop_name") == null ? "" : jsonObject.getString("develop_name");
			String moderm_id = jsonObject.get("moderm_id") == null ? "" : jsonObject.getString("moderm_id");
			String moderm_name = jsonObject.get("moderm_name") == null ? "" : jsonObject.getString("moderm_name");
			String req_swift_num = jsonObject.get("req_swift_num") == null ? "" : jsonObject.getString("req_swift_num");
			String access_type = jsonObject.get("access_type") == null ? "" : jsonObject.getString("access_type");
			String object_status = jsonObject.get("object_status") == null ? "" : jsonObject.getString("object_status");
			String moderm_status = jsonObject.get("moderm_status") == null ? "" : jsonObject.getString("moderm_status");
			if (StringUtils.isNotEmpty(moderm_status) && !StringUtils.equals("null", moderm_status)) {
				object_status = moderm_status;
			}
			String xcounty_id = jsonObject.get("xcounty_id") == null ? "" : jsonObject.getString("xcounty_id");
			String product_type = jsonObject.get("product_type") == null ? "" : jsonObject.getString("product_type");
			String wotv_num = jsonObject.get("wotv_num") == null ? "" : jsonObject.getString("wotv_num");
			String old_service_type = jsonObject.get("old_service_type") == null ? ""
					: jsonObject.getString("old_service_type");

			String old_obj_esn = jsonObject.get("old_obj_esn") == null ? "" 
					: jsonObject.getString("old_obj_esn");
			
			String old_obj_owner = jsonObject.get("old_obj_owner") == null ? "" 
					: jsonObject.getString("old_obj_owner");
			
			Map mapValue = new HashMap();
			mapValue.put("adsl_inst_id", this.baseDaoSupport.getSequences("seq_adsl_info"));
			mapValue.put("inst_id", inst_id);
			mapValue.put("order_id", order_id);
			mapValue.put("product_code", product_code);// 产品编码
			mapValue.put("adsl_account", bb_account);// 宽带账号
			mapValue.put("adsl_number", bb_num);// 宽带号码
			mapValue.put("adsl_password", adsl_password);// 密码
			mapValue.put("adsl_addr", std_address);// 标准地址
			mapValue.put("adsl_addr_desc", adsl_addr_desc);// 标准地址
			mapValue.put("adsl_speed", adsl_speed);// 速率
			mapValue.put("adsl_grid", grid);// 固网网格
			mapValue.put("user_kind", user_kind);// 用户种类
			mapValue.put("service_type", service_type);// 业务类型
			mapValue.put("exch_code", exch_code);// 局向编码
			mapValue.put("area_exch_id", area_exch_id);//区局
			mapValue.put("point_exch_id", point_exch_id);//端局
			mapValue.put("module_exch_id", module_exch_id);//模块局
			mapValue.put("cust_building_id", "");// 备注
			mapValue.put("total_fee", "");// 宽带总费用
			mapValue.put("user_address", user_address);// 用户地址
			mapValue.put("appt_date", appt_date);// 预约装机时间
			mapValue.put("county_id", county_id);// BSS县份
			mapValue.put("develop_point_code", develop_point_code);// 发展点编码
			mapValue.put("develop_point_name", develop_point_name);// 发展点名称
			mapValue.put("develop_code", develop_code);// 发展人编码
			mapValue.put("develop_name", develop_name);// 发展人名称
			mapValue.put("moderm_id", moderm_id);// 光猫ID
			mapValue.put("moderm_name", moderm_name);// 光猫ID中文名称
			mapValue.put("req_swift_num", req_swift_num);// 拍照流水
			mapValue.put("access_type", access_type);// 拍照流水
			mapValue.put("object_status", object_status);// 光猫物品状态
			mapValue.put("business_county", xcounty_id);// 营业县分
			mapValue.put("product_type", product_type);// 产品分类
			mapValue.put("wotv_num", wotv_num);// 沃tv号码
			mapValue.put("old_service_type", old_service_type);// 老业务类型
			mapValue.put("is_judge_address", is_judge_address);// 是否预判
			mapValue.put("is_done_active", is_done_active);
            mapValue.put("sale_mode",sale_mode);
            mapValue.put("devic_gear",devic_gear);
            mapValue.put("old_obj_owner", old_obj_owner);
			mapValue.put("old_obj_esn", old_obj_esn);
			try {
				BeanUtils.copyProperties(orderAdslBusiRequest, mapValue);
				orderAdslBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
				orderAdslBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				CommonData.getData().getOrderTreeBusiRequest().getOrderAdslBusiRequest().add(orderAdslBusiRequest);
				// orderAdslBusiRequest.store();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageLoadSetting(AttrInstLoadReq req) {
		return null;
	}

	@Override
	public AttrInstLoadResp attrInitForPageUpdateSetting(AttrInstLoadReq req) {
		return null;
	}
}
