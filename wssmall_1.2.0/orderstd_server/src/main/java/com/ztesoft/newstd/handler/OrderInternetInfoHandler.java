package com.ztesoft.newstd.handler;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.newstd.common.CommonData;

import consts.ConstsCore;

public class OrderInternetInfoHandler extends BaseSupport implements IAttrHandler {
	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo) throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
/*	    OrderInternetBusiRequest orderInternetBusiRequest = (OrderInternetBusiRequest) params.getBusiRequest();
*/		String order_id = params.getOrder_id();// 订单ID
		String inst_id = params.getInst_id(); // 数据实列ID（如订单扩展表ID、子订单扩展表ID、货品扩展表ID、物流ID、支付记录ID）
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String order_from = params.getOrder_from(); // 订单来源
		// orderAttrValues 包含值
		String rhPhoneInfo = (String) orderAttrValues.get("internetPhoneinfo");
		if (!StringUtils.isEmpty(rhPhoneInfo) && !"null".equals(rhPhoneInfo)) {
			JSONArray jsonArray = JSONArray.fromObject(rhPhoneInfo);
			for (int i = 0; i < jsonArray.size(); i++) {
				OrderInternetBusiRequest orderInternetBusiRequest = new OrderInternetBusiRequest();
				JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
				
				String lhscheme_id = jsonObject.getString("lhscheme_id") == null || StringUtils.equals("null", jsonObject.getString("lhscheme_id")) ? "":jsonObject.getString("lhscheme_id");
				
				String pre_fee = jsonObject.getString("pre_fee") == null || StringUtils.equals("null", jsonObject.getString("pre_fee"))? "":jsonObject.getString("pre_fee");
				
				String advancePay = jsonObject.getString("advancePay") == null || StringUtils.equals("null", jsonObject.getString("advancePay"))? "":jsonObject.getString("advancePay");
				
				String classId = jsonObject.getString("classId") == null || StringUtils.equals("null", jsonObject.getString("classId"))? "":jsonObject.getString("classId");
				
				String lowCostPro = jsonObject.getString("lowCostPro") == null || StringUtils.equals("null", jsonObject.getString("lowCostPro"))? "":jsonObject.getString("lowCostPro");
			
				String timeDurPro = jsonObject.getString("timeDurPro") == null || StringUtils.equals("null", jsonObject.getString("timeDurPro"))? "":jsonObject.getString("timeDurPro");
				
				String is_new = jsonObject.get("is_new") == null || StringUtils.equals("null", jsonObject.getString("is_new"))? ""
						: jsonObject.getString("is_new");//
				
				String subs_id = jsonObject.get("subs_id") == null || StringUtils.equals("null", jsonObject.getString("subs_id"))? ""
						: jsonObject.getString("subs_id");//
				
				String service_num = jsonObject.get("service_num") == null || StringUtils.equals("null", jsonObject.getString("service_num"))? "" : jsonObject.getString("service_num");//
				
				String evdo_num = jsonObject.get("evdo_num") == null || StringUtils.equals("null", jsonObject.getString("evdo_num"))? ""
						: jsonObject.getString("evdo_num");//
				
				String is_blankcard = jsonObject.get("is_blankcard") == null || StringUtils.equals("null", jsonObject.getString("is_blankcard"))? "" : jsonObject.getString("is_blankcard");//
				
				String is_main_number = "";
				if (jsonObject.containsKey("is_main_number")) {
					is_main_number = jsonObject.get("is_main_number") == null || StringUtils.equals("null", jsonObject.getString("is_main_number"))? "" : jsonObject.getString("is_main_number");// 固网网格
				}
				
				String number_level = jsonObject.get("number_level") == null || StringUtils.equals("null", jsonObject.getString("number_level"))? ""
						: jsonObject.getString("number_level");
				
				String scheme_id_l = jsonObject.get("scheme_id_l") == null || StringUtils.equals("null", jsonObject.getString("scheme_id_l"))? "" : jsonObject.getString("scheme_id_l");//
				
				String product_id = jsonObject.get("product_id") == null || StringUtils.equals("null", jsonObject.getString("product_id"))? ""
						: jsonObject.getString("product_id");
				
				String scheme_id = jsonObject.get("scheme_id") == null || StringUtils.equals("null", jsonObject.getString("scheme_id"))? ""
						: jsonObject.getString("scheme_id");
				
				String source_flag = jsonObject.get("source_flag") == null || StringUtils.equals("null", jsonObject.getString("source_flag"))? "" : jsonObject.getString("source_flag");//
				
				String url_key = jsonObject.get("url_key") == null || StringUtils.equals("null", jsonObject.getString("url_key"))? "" : jsonObject.getString("url_key");// 密码

				String http_request = jsonObject.get("http_request") == null || StringUtils.equals("null", jsonObject.getString("http_request"))? "" : jsonObject.getString("http_request");
				
				String res_number = jsonObject.get("res_number") == null || StringUtils.equals("null", jsonObject.getString("res_number"))? "" : jsonObject.getString("res_number");
				
				String nomalize_flag = jsonObject.get("nomalize_flag") == null || StringUtils.equals("null", jsonObject.getString("nomalize_flag"))? ""
						: jsonObject.getString("nomalize_flag");
				
				
				String http_request1 = jsonObject.get("http_request1") == null || StringUtils.equals("null", jsonObject.getString("http_request1"))? ""
						: jsonObject.getString("http_request1");
				
				String user_kind = jsonObject.get("user_kind") == null || StringUtils.equals("null", jsonObject.getString("user_kind"))? ""
						: jsonObject.getString("user_kind");
				String sale_mode = jsonObject.get("sale_mode") == null || StringUtils.equals("null", jsonObject.getString("sale_mode"))? ""
                        : jsonObject.getString("sale_mode");
				String object_esn = jsonObject.get("object_esn") == null || StringUtils.equals("null", jsonObject.getString("object_esn"))? ""
                        : jsonObject.getString("object_esn");
				String object_id = jsonObject.get("object_id") == null || StringUtils.equals("null", jsonObject.getString("object_id"))? ""
                        : jsonObject.getString("object_id");
							
				Map mapValue = new HashMap();
				mapValue.put("internet_phone_info_id", this.baseDaoSupport.getSequences("INTERNET_PHONE_INFO_ID"));
				mapValue.put("order_id",order_id);
				mapValue.put("order_from", order_from);
				mapValue.put("lhscheme_id", lhscheme_id);
				mapValue.put("pre_fee",pre_fee );
				mapValue.put("advancePay",advancePay );
				mapValue.put("classId",classId );
				mapValue.put("lowCostPro",lowCostPro );
				mapValue.put("timeDurPro",timeDurPro );
				mapValue.put("http_request1", http_request1);
				mapValue.put("order_id", order_id);
				mapValue.put("nomalize_flag", nomalize_flag);
				mapValue.put("res_number", res_number);
				mapValue.put("http_request", http_request);
				mapValue.put("url_key", url_key);
				mapValue.put("source_flag", source_flag);
				mapValue.put("scheme_id", scheme_id);
				mapValue.put("product_id", product_id);
				mapValue.put("scheme_id_l", scheme_id_l);
				mapValue.put("number_level", number_level);
				mapValue.put("is_main_number", is_main_number);
				mapValue.put("is_blankcard", is_blankcard);
				mapValue.put("evdo_num", evdo_num);
				mapValue.put("service_num", service_num);
				mapValue.put("subs_id", subs_id);
				mapValue.put("is_new", is_new);
				mapValue.put("user_kind", user_kind);
                mapValue.put("sale_mode", sale_mode);
                mapValue.put("object_esn", object_esn);
                mapValue.put("object_id", object_id);
				try {
					BeanUtils.copyProperties(orderInternetBusiRequest, mapValue);
					orderInternetBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					orderInternetBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					/*orderInternetBusiRequest.store();*/
	                CommonData.getData().getOrderTreeBusiRequest().getOrderInternetBusiRequest().add(orderInternetBusiRequest);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
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
