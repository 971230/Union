package com.ztesoft.net.attr.hander;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import zte.net.ecsord.params.attr.req.AttrInstLoadReq;
import zte.net.ecsord.params.attr.req.AttrSyLoadReq;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.attr.resp.AttrSyLoadResp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class ZhwqInfoHandler extends BaseSupport implements IAttrHandler {

	@Override
	public AttrSyLoadResp attrSyingVali(AttrSyLoadReq oo)
			throws ApiBusiException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AttrInstLoadResp handler(AttrSwitchParams params) {
		Map orderAttrValues = params.getOrderAttrValues();// 订单所有属性值
		String order_id = params.getOrder_id();// 订单ID
		
		String zhwqInfo = (String) orderAttrValues.get("zhwq_info");
		if(!StringUtils.isEmpty(zhwqInfo)){
			JSONArray jsonArr = JSON.parseArray(zhwqInfo);
			if(jsonArr!=null && jsonArr.size()>0){
				for(int i=0;i<jsonArr.size();i++){
					JSONObject zhwqObj = jsonArr.getJSONObject(i);
					if(zhwqObj!=null){
						JSONArray adslArr = zhwqObj.getJSONArray("adsl_list");
						saveAdslInfo(adslArr,order_id);
						
						JSONArray ywArr = zhwqObj.getJSONArray("yw_list");
						saveYwInfo(ywArr,order_id);
						
						JSONArray gwArr = zhwqObj.getJSONArray("gw_list");
						saveGwInfo(gwArr,order_id);
						
						JSONArray yxtArr = zhwqObj.getJSONArray("yxt_list");
						saveYxtInfo(yxtArr,order_id);
					}
				}
			}
		}
		AttrInstLoadResp resp = new AttrInstLoadResp();
		resp.setField_value(null);
		return resp;
	}
	
	@SuppressWarnings({ "unchecked" })
	private void saveAdslInfo(JSONArray adslArr,String order_id){
		if(adslArr!=null && adslArr.size()>0){
			for(int i=0;i<adslArr.size();i++){
				JSONObject adslObj = adslArr.getJSONObject(i);
				
				String adsl_password = adslObj.get("bb_password")==null?"":adslObj.getString("bb_password");//密码
				
				Map adslMap = new HashMap();
				String id = this.baseDaoSupport.getSequences("S_ZHWQ_ADSL_ID");
				adslMap.put("inst_id", id);
				adslMap.put("order_id", order_id);
				adslMap.put("product_code", adslObj.getString("product_id"));
				adslMap.put("adsl_account", adslObj.getString("adsl_account"));
				adslMap.put("adsl_number", adslObj.getString("adsl_number"));
				adslMap.put("adsl_password", adsl_password);//密码
				adslMap.put("adsl_addr", adslObj.getString("adsl_addr"));
				adslMap.put("adsl_speed", adslObj.getString("adsl_speed"));
				adslMap.put("adsl_grid", adslObj.getString("adsl_grid"));
				adslMap.put("user_kind", adslObj.getString("adsl_usertype"));
				adslMap.put("service_type", adslObj.getString("service_type"));
				adslMap.put("exch_code", adslObj.getString("exch_code"));
				adslMap.put("cust_building_id", adslObj.getString("cust_building_id"));
				String totalFee = "0";
				try {
					//单位转换为元
					Integer fee = Integer.parseInt(adslObj.getString("total_fee"));
					totalFee = this.parseMoney(fee);
				} catch (Exception e) {
					e.printStackTrace();
				}
				adslMap.put("total_fee", totalFee);
				adslMap.put("source_from", ManagerUtils.getSourceFrom());
				
				this.baseDaoSupport.insert("ES_ORDER_ZHWQ_ADSL", adslMap);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private void saveYwInfo(JSONArray ywArr,String order_id){
		if(ywArr!=null && ywArr.size()>0){
			for(int i=0;i<ywArr.size();i++){
				JSONObject ywObj = ywArr.getJSONObject(i);
				Map adslMap = new HashMap();
				String id = this.baseDaoSupport.getSequences("S_ZHWQ_YW_ID");
				adslMap.put("id", id);
				adslMap.put("order_id", order_id);
				adslMap.put("product_id", ywObj.getString("product_id"));
				adslMap.put("simid", ywObj.getString("simid"));
				adslMap.put("sim_type", ywObj.getString("sim_type"));
				adslMap.put("is_main", ywObj.getString("is_main"));
				adslMap.put("service_num", ywObj.getString("service_num"));
				adslMap.put("source_from", ManagerUtils.getSourceFrom());
				
				this.baseDaoSupport.insert("ES_ORDER_ZHWQ_YW", adslMap);
			}
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void saveGwInfo(JSONArray gwArr,String order_id){
		if(gwArr!=null && gwArr.size()>0){
			for(int i=0;i<gwArr.size();i++){
				JSONObject gwObj = gwArr.getJSONObject(i);
				Map adslMap = new HashMap();
				String id = this.baseDaoSupport.getSequences("S_ZHWQ_YW_ID");
				adslMap.put("id", id);
				adslMap.put("order_id", order_id);
				adslMap.put("product_id", gwObj.getString("product_id"));
				adslMap.put("service_num", gwObj.getString("service_num"));
				adslMap.put("is_main", gwObj.getString("is_main"));
				adslMap.put("service_type", gwObj.getString("service_type"));
				String totalFee = "0";
				try {
					//单位转换为元
					Integer fee = Integer.parseInt(gwObj.getString("total_fee"));
					totalFee = this.parseMoney(fee);
				} catch (Exception e) {
					e.printStackTrace();
				}
				adslMap.put("total_fee", gwObj.getString("total_fee"));
				adslMap.put("source_from", ManagerUtils.getSourceFrom());
				
				this.baseDaoSupport.insert("ES_ORDER_ZHWQ_GW", adslMap);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private void saveYxtInfo(JSONArray yxtArr,String order_id){
		if(yxtArr!=null && yxtArr.size()>0){
			for(int i=0;i<yxtArr.size();i++){
				JSONObject yxtObj = yxtArr.getJSONObject(i);
				Map adslMap = new HashMap();
				String id = this.baseDaoSupport.getSequences("S_ZHWQ_YW_ID");
				adslMap.put("id", id);
				adslMap.put("order_id", order_id);
				adslMap.put("product_id", yxtObj.getString("product_id"));
				adslMap.put("service_num", yxtObj.getString("service_num"));
				adslMap.put("service_type", yxtObj.getString("service_type"));
				adslMap.put("simid", yxtObj.getString("simid"));
				adslMap.put("sim_type", yxtObj.getString("sim_type"));
				adslMap.put("source_from", ManagerUtils.getSourceFrom());
				
				this.baseDaoSupport.insert("ES_ORDER_ZHWQ_YXT", adslMap);
			}
		}
	}
	
	/**
	 * 金额由厘转换为元
	 * @param money
	 * @return
	 */
	private String parseMoney(Integer money){
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setGroupingUsed(false);
		String s = format.format( (double) money / 1000 );
		return s;
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
