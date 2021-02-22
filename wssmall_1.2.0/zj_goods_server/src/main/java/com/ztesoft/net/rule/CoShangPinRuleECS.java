package com.ztesoft.net.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IGoodsService;
import zte.params.goods.req.CoModifyStatusReq;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.framework.dao.SqlExeNew;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;


/**
 * 商品同步规则
 * @author suns
 *
 */
public class CoShangPinRuleECS extends CoQueueBaseRule {
	
	private Logger logger = Logger.getLogger(CoShangPinRuleECS.class);
	
	@Resource
	IGoodsService goodsService;
	
	@Resource
	IOrderServiceLocal orderServiceLocal;
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		
		logger.debug("商品同步");
		String string = "";
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
		/**
		 * 1、调接口同步数据
		 * 2、更新同步状态表为已同步
		 */
		String goods_id = coQueueRuleReq.getCoQueue().getObject_id().toString();
		String rtnStr = searchGoodsEcs(goods_id,coQueueRuleReq.getCoQueue().getAction_code(),coQueueRuleReq.getCoQueue().getOrg_id_str(),coQueueRuleReq.getCoQueue().getOrg_id_belong());
		
		
		if (StringUtils.isEmpty(rtnStr)) {
			//失败后同事更新es_goods_co状态---zengxianlian
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg("商品同步失败");
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg("商品同步失败");
		}
		SendInfoUtil siu = new SendInfoUtil();
		
        String serviceCode="CO_SHANGPIN";
        
        String huoping_fanghui=null;
		String[] org_id_strs =coQueueRuleReq.getCoQueue().getOrg_id_str().split(",");
		boolean flag=false;
		for(int i=0;i<org_id_strs.length;i++){
			 //集客农行的商品同步地址
//	        if ("10006".equals(coQueueRuleReq.getCoQueue().getOrg_id_str())) {
//	        	serviceCode="CO_SHANGPIN_10006";
//			}
	        //商城接入改成可配置
	        if(!StringUtils.isEmpty(coQueueRuleReq.getCoQueue().getOrg_id_str())
	        		&&existOrgCode(coQueueRuleReq.getCoQueue().getOrg_id_str())
	        		&&!coQueueRuleReq.getCoQueue().getOrg_id_belong().equals("10008")){
	        	serviceCode="CO_SHANGPIN_"+coQueueRuleReq.getCoQueue().getOrg_id_str();
	        }
	        
			if(flag){
				//因为新商城的单子公用一个接口地址，只传一遍
				continue;
			}
			if(coQueueRuleReq.getCoQueue().getOrg_id_belong().equals("10008")||coQueueRuleReq.getCoQueue().getOrg_id_belong().equals("100082")){
				flag=true;
			}
			
			String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";//+"' and a.org_id_belong='"+idBelong[i]+"'";
			String url=orderServiceLocal.queryString(sqlStr);
			logger.info("完整的报文："+rtnStr+"\nURL："+url);
			//String  huoping_fanghui = siu.postHttpReq(rtnStr, url);
			huoping_fanghui = MallUtils.sendPostHttpRequest(rtnStr, url);
			logger.info("===============================商品同步返回信息="+huoping_fanghui);
		}
    
		try {
			String ret_val =  JSONObject.fromObject(huoping_fanghui).getJSONObject("prod_offer_resp").get("resp_code").toString();
			if ("0".equalsIgnoreCase(ret_val)) {
				coQueueRuleResp.setError_code("0");
				coQueueRuleResp.setError_msg("成功");
				coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
				coQueueRuleResp.setResp_msg("成功");
			}
			else{
				coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
				coQueueRuleResp.setResp_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("prod_offer_resp").get("resp_msg").toString());
				coQueueRuleResp.setError_code("1");
				coQueueRuleResp.setError_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("prod_offer_resp").get("resp_msg").toString());
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			//失败后同事更新es_goods_co状态---zengxianlian
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg(huoping_fanghui);
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg(huoping_fanghui);
		}
		if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && Consts.RESP_CODE_0.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
		//if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
//			CoModifyStatusReq req = new CoModifyStatusReq();
//			req.setId(coQueueRuleReq.getBatch_id());  //根据同步批次号更新
//			req.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN);
//			req.setStatus_new(Consts.PUBLISH_1.toString());
//			this.goodsService.modifyStatus(req);
			//成功后同事更新es_goods_co状态---zengxianlian
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_1.toString());
		}else if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && !Consts.RESP_CODE_0.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
		//else if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && !Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
			//失败后同事更新es_goods_co状态---zengxianlian
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
		}
		return coQueueRuleResp;
	}
	
	private void modifyStatus(String batch_id,String status){
		CoModifyStatusReq req = new CoModifyStatusReq();
		req.setId(batch_id);  //根据同步批次号更新
		req.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN);
		req.setStatus_new(status);
		this.goodsService.modifyStatus(req);
	}
	
	private boolean existOrgCode(String org_id_str) {
		// 判断商城的ID是不是在同步地址里面有配置
		String sqlOrgCode="select count(*) from es_inf_addr where service_code='CO_SHANGPIN_"+org_id_str+"'";
		int count = this.baseDaoSupport.queryForInt(sqlOrgCode);
		if(count>0){
			return true;
		}
		return false;
	}

	/**
	 * 商品信息同步
	 * @param object_id
	 * @param action_code
	 * @param orgId
	 * @return
	 */
	public String searchGoodsEcs(String object_id,String action_code,String orgId,String orgIdBelong) {
		StringBuffer json = new StringBuffer();
		String goods_id = object_id;
		try {
			Map map = baseDaoSupport.queryForMap("select * from es_gdlt_v_goods a  where  a.goods_id = '"+ goods_id + "'");
			String seq = baseDaoSupport.queryForString("select seq_goods.nextVal from dual");
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = format.format(new Date());
			json.append("{\"prod_offer_req\":");
			json.append("{\"tip\":").append("\"").append("商品信息同步").append("\",");
			json.append("\"serial_no\":").append("\"").append(seq + time).append("\",");	//序列号
			json.append("\"time\":").append("\"").append(time).append("\",");	//时间
			json.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
			json.append("\"receive_system\":").append("\"").append(orgIdBelong).append("\",");	//接收方系统标识
			String channel = getChannelCode(orgId);			
			json.append("\"channel\":").append("\"").append(channel).append("\",");	//商城编码
			json.append("\"prod_offer_code\":").append("\"").append(map.get("prod_offer_code")).append("\",");	//商品编码
			json.append("\"action\":").append("\"").append(action_code).append("\",");	//动作
			json.append("\"prod_offer_name\":").append("\"").append(map.get("prod_offer_name")).append("\",");	//商品名称
			json.append("\"prod_offer_type\":").append("\"").append(map.get("cat_ida")).append("\",");	//商品分类
			json.append("\"prod_offer_category\":").append("\"").append(map.get("type_aid")).append("\",");
			json.append("\"prod_offer_brand\":").append("\"").append(map.get("prod_offer_brand")).append("\",");	//商品品牌
			json.append("\"terminal_model\":").append("\"").append(map.get("terminal_model")).append("\",");	//机型
			Object prod_offer_state = map.get("prod_offer_state");
			if (null == prod_offer_state || "".equals(prod_offer_state.toString())) {
				prod_offer_state = "0";
			}
			json.append("\"prod_offer_state\":").append("\"").append(prod_offer_state.toString()).append("\",");	//商品状态
			json.append("\"prod_offer_price\":").append("\"").append(map.get("prod_offer_price")).append("\",");	//商品价格
			json.append("\"prod_offer_heavy\":").append("\"").append(map.get("prod_offer_heavy")).append("\",");	//商品重量（kg）
			json.append("\"prod_offer_package\":").append("\"").append(map.get("prod_offer_heavy")).append("\",");	//归属商品包
			
			//获取p_code
			String p_code = CommonDataFactory.getInstance().getPCode(goods_id);
			json.append("\"P_code\":").append("\"").append(p_code).append("\",");	//归属商品包
			
			//商品构成
			json.append("\"prod_offer_ele\":[");
			try {
				String sqlStr= "SELECT a.sku,sum(1) record_num FROM ES_GDLT_V_GOODS_REL a where a.goods_id = "+goods_id+ " group by a.sku";
				List list_sku = baseDaoSupport.queryForList(sqlStr);
				if(list_sku.toString() != "[]" ){
					for(int i = 0; i < list_sku.size();i++){  
						Map m = (Map) list_sku.get(i);
						json.append("{\"sku\":").append("\"").append(m.get("sku")).append("\",");	//货品sku
						Object goods_num = map.get("goods_num");
						if (null == goods_num) {
							goods_num = "1";
						}else {
							goods_num = m.get("record_num");
						}
						json.append("\"goods_num\":").append("\"").append(goods_num).append("\"");	//货品数量
						if (i < list_sku.size() - 1){
							json.append("},");
						}else {
							json.append("}");
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}			
			json.append("],");
			
			//商品参数
			json.append("\"prod_offer_param\":[");
			String mon_return = "0";
			String mobile_price = "0";
			String order_return = "0";
			String deposit_fee = "0";
			String mon_give = "0";
			String all_give = "0";
			try {
				if(null != map.get("params") && !"[]".equalsIgnoreCase(map.get("params").toString())){
					String strpar2 = map.get("params").toString();
					strpar2 = strpar2.substring(1, strpar2.lastIndexOf("]"));
					paramsL p2=JsonUtil.fromJson(strpar2, paramsL.class);
					if (p2.getParamList().size() > 0) {
						for (int i = 0; i < p2.getParamList().size(); i++) {
							paramsenum tmp=p2.getParamList().get(i);
							String ename=tmp.getEname();
							if("mon_return".equalsIgnoreCase(ename)){
								mon_return = MallUtils.isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("mobile_price".equalsIgnoreCase(ename)){
								mobile_price = MallUtils.isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("order_return".equalsIgnoreCase(ename)){
								order_return = MallUtils.isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("deposit_fee".equalsIgnoreCase(ename)){
								deposit_fee = MallUtils.isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("mon_give".equalsIgnoreCase(ename)){
								mon_give = MallUtils.isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}else if("all_give".equalsIgnoreCase(ename)){
								all_give = MallUtils.isEmpty(tmp.getValue()) ? "0" : tmp.getValue();
							}
							json.append("{\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
							json.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
							json.append("\"param_value_code\":").append("\"").append("").append("\",");
							json.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\"");
							if (i < p2.getParamList().size() - 1){
								json.append("},");
							}else {
								json.append("}");
							}
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			//mon_give不一定有值,无值时取mon_return
			if (null == mon_give || "".equals(mon_give) || "0".equals(mon_give) && null != mon_return) {
				mon_give = mon_return;
			}
			json.append("],");
			json.append("\"prod_offer_attr\":");	//合约属性
			json.append("{");
			json.append("\"prod_offer_sku\":").append("\"").append("0").append("\",");	//套餐sku
			json.append("\"mon_give\":").append("\"").append(mon_give).append("\",");	//月送费金额
			json.append("\"all_give\":").append("\"").append(all_give).append("\",");	//协议期总送费金额
			json.append("\"mobile_price\":").append("\"").append(mobile_price).append("\",");	//手机款
			json.append("\"order_return\":").append("\"").append(order_return).append("\",");	//首月返还
			json.append("\"deposit_fee\":").append("\"").append(deposit_fee).append("\",");	//预存款
			json.append("\"mon_return\":").append("\"").append(mon_return).append("\"");	//分月返还
			json.append("}}}");
			
		} catch (Exception e) {
			json = new StringBuffer();
			logger.info("商品["+goods_id+"]同步失败.");
			logger.info(e.getMessage(), e);
		}
		return json.toString().replaceAll("\"null\"", "\"\"");
	}
	
	/**
	 * 获取商城的编码
	 * @param coQueueRuleReq
	 * @return
	 */
	public String getChannelCode(String orgId){
		StringBuffer jsonBuffer = new StringBuffer();
		String []arr = orgId.split(",");
		try {
			String sql = null;
			String orgCode = null;
			for (int i = 0; i < arr.length; i++) {
				SqlExeNew sqlexe = new SqlExeNew();
				sql = "select a.org_code from es_goods_org a where a.party_id = " + arr[i];
//				orgCode = sqlexe.queryForString(sql);本机测试走不通，暂时保留
				orgCode = this.baseDaoSupport.queryForString(sql);
				if (null == jsonBuffer || "".equals(jsonBuffer.toString())) {
					jsonBuffer.append(orgCode);
				}else {
					jsonBuffer.append(",").append(orgCode);
				}
			}
		} catch (Exception e) {
			jsonBuffer = new StringBuffer();
			logger.info(e.getMessage(), e);
		}
		return jsonBuffer.toString().replaceAll("\"null\"", "\"\"");
	}

}
