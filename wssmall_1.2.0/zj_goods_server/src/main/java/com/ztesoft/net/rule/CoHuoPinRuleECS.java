package com.ztesoft.net.rule;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.iservice.IGoodsService;
import zte.params.goods.req.CoModifyStatusReq;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;
import com.ztesoft.net.util.SendWMSUtil;


/**
 * 货品同步规则
 * @author suns
 *
 */
public class CoHuoPinRuleECS extends CoQueueBaseRule {
	
	private Logger logger = Logger.getLogger(CoHuoPinRuleECS.class);
	
	@Resource
	IGoodsService goodsService;
	
	@Resource
	IOrderServiceLocal orderServiceLocal;
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		
		logger.debug("货品同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		
		coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
		
		if(EcsOrderConsts.GOODS_WMS_20001.equalsIgnoreCase(coQueueRuleReq.getCoQueue().getOrg_id_belong()))
		{//同步WMS
			synchronizedToWMS(coQueueRuleReq,coQueueRuleResp);
			return coQueueRuleResp;
		}
		
		/**
		 * 1、调接口同步数据
		 * 2、更新同步状态表为已同步
		 */
		String product_id = coQueueRuleReq.getCoQueue().getObject_id().toString();
		String rtnStr = searchProductEcs(product_id,coQueueRuleReq.getCoQueue().getAction_code(),coQueueRuleReq.getCoQueue().getOrg_id_str());
		
		if (StringUtils.isEmpty(rtnStr)) {
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg("货品接口同步失败");
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg("货品接口同步失败");
			return coQueueRuleResp;
		}
		SendInfoUtil siu = new SendInfoUtil();
		String serviceCode="CO_HUOPIN";
		String huoping_fanghui=null;
		String[] org_id_strs =coQueueRuleReq.getCoQueue().getOrg_id_str().split(",");
		boolean flag=false;
		for(int i=0;i<org_id_strs.length;i++){
			//集客农行的货品同步地址
//			if ("10006".equals(coQueueRuleReq.getCoQueue().getOrg_id_str())) {
//				serviceCode="CO_HUOPIN_10006";
//			}			
	        //商城接入改成可配置
	        if(!StringUtils.isEmpty(coQueueRuleReq.getCoQueue().getOrg_id_str())
	        		&&existOrgCode(coQueueRuleReq.getCoQueue().getOrg_id_str())
	        		&&!coQueueRuleReq.getCoQueue().getOrg_id_belong().equals("10008")){
	        	serviceCode="CO_HUOPIN_"+coQueueRuleReq.getCoQueue().getOrg_id_str();
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
//			String url="http://test.zhuwenda.com/infosync/servlet/GoodsInfoCallback";
			logger.info("货品["+coQueueRuleReq.getCoQueue().getObject_id()+"]完整的报文："+rtnStr+"\nURL："+url);
			//String huoping_fanghui = siu.postHttpReq(rtnStr, url);
			huoping_fanghui = MallUtils.sendPostHttpRequest(rtnStr, url);
			logger.info("===============================货品["+coQueueRuleReq.getCoQueue().getObject_id()+"]同步返回信息="+huoping_fanghui);
		}			
			
		try {
			String ret_val =  JSONObject.fromObject(huoping_fanghui).getJSONObject("goods_info_resp").get("resp_code").toString();
			if ("0".equalsIgnoreCase(ret_val)) {
				coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
				coQueueRuleResp.setResp_msg("成功");
				coQueueRuleResp.setError_code("0");
				coQueueRuleResp.setError_msg("成功");
			}
			else{
				coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
				coQueueRuleResp.setResp_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("goods_info_resp").get("resp_msg").toString());
				coQueueRuleResp.setError_code("1");
				coQueueRuleResp.setError_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("goods_info_resp").get("resp_msg").toString());
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg(huoping_fanghui);
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg(huoping_fanghui);
		}
		//2、更新同步状态表[es_product_co]为已同步
		if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
        // 商城修改掉原来返回的000作为成功编码,改成了0---zengxianlian
//		if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
//			CoModifyStatusReq req = new CoModifyStatusReq();
//			req.setId(coQueueRuleReq.getBatch_id());  //根据同步批次号更新
//			req.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
//			req.setStatus_new(Consts.PUBLISH_1.toString());
//			this.goodsService.modifyStatus(req);
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_1.toString());
		}else if(Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && !Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())){
		//else if(Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && !Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())){
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
		}
		return coQueueRuleResp;
	}
	
	private void modifyStatus(String batch_id,String status){
		CoModifyStatusReq req = new CoModifyStatusReq();
		req.setId(batch_id);  //根据同步批次号更新
		req.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
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
	 * 货品同步
	 * @param object_id
	 * @param action_code
	 * @param orgId
	 * @return
	 */
	public String searchProductEcs(String object_id,String action_code,String orgId){
		
		String goods_id = object_id;
		Map tmpMap = null;
		logger.info("============================进入货品同步=goods_id=" + goods_id);
		String sql = "SELECT * from v_product ep where  ep.product_id ='"+goods_id+"'";
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer jsonStr=new StringBuffer();
		String seq = "";
		String dc_sql = "";
		String dc_sql_value = "";
		
		try {
			List<Map> list = baseDaoSupport.queryForList(sql);
			if (null != list && list.size() > 0) {
				tmpMap = list.get(0);
			}else {
				logger.info("没有找到货品[" + goods_id + "]");
			}
			//序列号
			seq = baseDaoSupport.getSequences("seq_goods");

			jsonStr.append("{\"goods_info_req\":");
			jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");	//序列号
			jsonStr.append("\"time\":").append("\"").append(df.format(new Date())).append("\",");	//时间
			jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");	//货品编码
			jsonStr.append("\"source_system\":").append("\"").append("10011").append("\",");	//发起方系统标识
			jsonStr.append("\"receive_system\":").append("\"").append("10008").append("\",");	//接收方系统标识
			jsonStr.append("\"action\":").append("\"").append(action_code).append("\",");	//动作
			jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");	//货品名称
			jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");	//货品品牌
			jsonStr.append("\"goods_category\":").append("\"").append(tmpMap.get("type_id")).append("\",");	//货品分类
			jsonStr.append("\"goods_type\":").append("\"").append(tmpMap.get("cat_name")).append("\",");	//货品类型
			String tmpa=tmpMap.get("model_code")==null?"":tmpMap.get("model_code").toString();
			jsonStr.append("\"goods_class\":").append("\"").append(tmpa).append("\",");	//货品型号
			//货品规格
			if (null == tmpMap.get("sn")) {
				jsonStr.append("\"goods_spec\":").append("\"").append("").append("\",");
			}else {
				jsonStr.append("\"goods_spec\":").append("\"").append(tmpMap.get("sn")).append("\",");
			}
			//货品状态
			if (null == tmpMap.get("state")) {
				jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
			}else {
				jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
			}
			//货品属性
			jsonStr.append("\"goods_attr\":[");
			
			String strpar = tmpMap.get("params").toString();
			//strpar=strpar.substring(1,strpar.lastIndexOf("]"));	
			JSONArray jsonArr = JSONArray.fromObject(strpar);		
			paramsL pl = null;
			try {
				pl = JsonUtil.fromJson(jsonArr.getString(0), paramsL.class);
				
				if (null != pl && pl.getParamList().size() > 0) {
					for (int i = 0; i < pl.getParamList().size(); i++) {
						paramsenum tmp=pl.getParamList().get(i);
						jsonStr.append("{");
						jsonStr.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
						jsonStr.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
						
						if (MallUtils.isNotEmpty(tmp.getAttrcode())) {
							dc_sql = querySqlResult("select DC_SQL from es_dc_sql  a where  a.dc_name = '"+tmp.getAttrcode() +"'");
							dc_sql_value = querySqlResult("select value_desc from (select 'ECS' source_from, e.* from ("+dc_sql+") e)T where T.VALUE ='"+tmp.getValue().toString()+"' and rownum < 2");
							jsonStr.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
							jsonStr.append("\"param_value\":").append("\"").append(dc_sql_value).append("\",");
						}else {
							jsonStr.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
							jsonStr.append("\"param_value_code\":").append("\"").append("").append("\",");
						}
						jsonStr.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\"");
						jsonStr.append("}");
						if(i < pl.getParamList().size() - 1){
							jsonStr.append(",");
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			
			jsonStr.append("]");
			jsonStr.append("}}");
			
		} catch (Exception e) {
			logger.info("error json:==========" + jsonStr.toString());
			jsonStr = new StringBuffer();
			logger.info(e.getMessage(), e);
		}
		return jsonStr.toString();
					
	}
	
	/**
	 * 同步货品到WMS
	 * @param object_id
	 * @param action_code
	 * @param orgId
	 * @return
	 */
	public String searchProductWMS(String object_id,String action_code,String orgId){
		StringBuffer jsonStr = new StringBuffer();
		try {
			String goods_id = object_id;
			String str = "SELECT * from v_product ep where  ep.product_id ='"+goods_id+"'";
			Map tmpMap = baseDaoSupport.queryForMap(str);
			
			//ur:197933
			//请在同步货品给WMS的接口中过滤掉虚拟货品，即不同步虚拟货品
			String type_id = tmpMap.get("type_id").toString();	//货品大类
			String cat_id = tmpMap.get("cat_name").toString();	//货品小类
			//判断货品大类是否为虚拟货品
			String sql = "select count(*) nums from es_mall_config c where c.field_name = 'is_virtual' and c.field_value = '"+type_id+"'";
			String rs_type = baseDaoSupport.queryForString(sql);
			//判断货品小类是否为虚拟货品
			sql = "select count(*) nums from es_mall_config c where c.field_name = 'isvirtualgift' and c.field_value = '"+cat_id+"'";
			String rs_cat = baseDaoSupport.queryForString(sql);
			//虚拟货品不同步WMS
			if (!"0".equals(rs_type) || !"0".equals(rs_cat)) {
				logger.warn("货品[" + goods_id + "]是虚拟货品.大类["+type_id+"],小类["+cat_id+"].");
				return "";
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
			String dtime = df.format(new Date());
			String seq = baseDaoSupport.getSequences("seq_goods");
			jsonStr.append("{\"goods_info_req\":");
			jsonStr.append("{\"serial_no\":").append("\"").append(seq).append("\",");
			jsonStr.append("\"time\":").append("\"").append(dtime).append("\",");
			jsonStr.append("\"sku\":").append("\"").append((String)tmpMap.get("skua")).append("\",");
			
			StringBuffer attrBuf = new StringBuffer();
			attrBuf.append("\"goods_attr\":[");
			String strpar = tmpMap.get("params").toString();
			//strpar=strpar.substring(1,strpar.lastIndexOf("]"));
			JSONArray jsonArr = JSONArray.fromObject(strpar);
			paramsL pl = null;
			String matnr = "";
			String mchk = "";
			String mtart = "";
			try {
				pl = JsonUtil.fromJson(jsonArr.getString(0), paramsL.class);
				if(pl.getParamList().size()>0){
					for(int i=0;i<pl.getParamList().size();i++){
						paramsenum tmp=pl.getParamList().get(i);
						attrBuf.append("{");
						attrBuf.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
						attrBuf.append("\"param_name\":").append("\"").append(tmp.getName()).append("\",");
						Map tmpMapdc=null;
						Map tmpMap_yanshe=null;
						//华盛货品编码
						if("MATNR".equals(tmp.getEname())){
							matnr = tmp.getValue();
						}
//						else if("MCHK".equals(tmp.getEname())){
//							//串码管理
//							mchk = tmp.getValue();
//						}else if("MTART".equals(tmp.getEname())){
//							//华盛商品类型
//							mtart = tmp.getValue();
//						}
						if(null != tmp.getAttrcode() && !"".equalsIgnoreCase(tmp.getAttrcode())){
							String strdc="select DC_SQL from es_dc_sql  a where  a.dc_name = '"+tmp.getAttrcode() +"'";
							try {
								tmpMapdc = baseDaoSupport.queryForMap(strdc);
								String dc_sql =  tmpMapdc.get("dc_sql").toString();
								String str_yanshe="select T2.value_desc from (select value_desc,VALUE,'ECS' source_from  from ("+dc_sql+") T ) T2 where T2.VALUE = '"+tmp.getValue().toString() + "'";
								tmpMap_yanshe = baseDaoSupport.queryForMap(str_yanshe);
								attrBuf.append("\"param_value_code\":").append("\"").append(tmp.getValue()).append("\",");
								attrBuf.append("\"param_value\":").append("\"").append(tmpMap_yanshe.get("value_desc")).append("\",");
							} catch (Exception e) {
								
							}
						}else {
							attrBuf.append("\"param_value\":").append("\"").append(tmp.getValue()).append("\",");
							attrBuf.append("\"param_value_code\":").append("\"").append("").append("\",");
						}
						attrBuf.append("\"sku_attr\":").append("\"").append(tmp.getAttrvaltype()).append("\"");
						attrBuf.append("}");
						if(i<pl.getParamList().size()-1){
							attrBuf.append(",");
						}
					}
				}
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			attrBuf.append("]");
			//根据物料号获取“串码管理”、“华盛商品类型”
			if(StringUtils.isNotEmpty(matnr)){
				String querySql = "select c.mtart,c.mchk from ES_GOODS_HUASHENG c where c.matnr = ? and c.source_from = ?";
				Map hsGoods = this.baseDaoSupport.queryForMap(querySql, matnr,ManagerUtils.getSourceFrom());
				if(null != hsGoods && hsGoods.size() > 0){
					if(hsGoods.containsKey("mchk")){
						mchk = hsGoods.get("mchk").toString();
					}
					if(hsGoods.containsKey("mtart")){
						mtart = hsGoods.get("mtart").toString();
					}
				}
			}
			//华盛货品编码  物料号
			jsonStr.append("\"sku_hs\":").append("\"").append(matnr).append("\",");
			//华盛商品串码管理
			jsonStr.append("\"MCHK\":").append("\"").append(mchk).append("\",");
			jsonStr.append("\"MTART\":").append("\"").append(mtart).append("\",");
			jsonStr.append("\"action\":").append("\"").append(action_code).append("\",");
			jsonStr.append("\"goods_name\":").append("\"").append(tmpMap.get("name")).append("\",");
			jsonStr.append("\"goods_brand\":").append("\"").append(tmpMap.get("brand_name")).append("\",");
			jsonStr.append("\"goods_category\":").append("\"").append(type_id).append("\",");
			jsonStr.append("\"goods_type\":").append("\"").append(cat_id).append("\",");
			String tmpa=tmpMap.get("model_code")==null?"":tmpMap.get("model_code").toString();
			jsonStr.append("\"goods_class\":").append("\"").append(tmpa).append("\",");//tmpMap.get("model_code")
			if (null == tmpMap.get("state")) {
				jsonStr.append("\"goods_state\":").append("\"").append("0").append("\",");
			}else {
				jsonStr.append("\"goods_state\":").append("\"").append(tmpMap.get("state")).append("\",");
			}
			jsonStr.append(attrBuf.toString());
			jsonStr.append("}}");
		} catch (Exception e) {
			jsonStr = new StringBuffer();
			logger.info("同步货品["+object_id+"]失败.");
			logger.info(e.getMessage(), e);
		}
		return jsonStr.toString().replaceAll("\"null\"", "\"\"");
	}
	
	/**
	 * Added on 2015-07-06
	 * @param coQueueRuleReq
	 * @param coQueueRuleResp
	 */
	private void synchronizedToWMS(CoQueueRuleReq coQueueRuleReq, CoQueueRuleResp coQueueRuleResp)
	{
		if(null==coQueueRuleReq || null==coQueueRuleResp)
			return;
				
		String result="";
		String serviceCode=coQueueRuleReq.getCoQueue().getService_code();
		String sqlWMSStr = "SELECT e.addr FROM ES_INF_ADDR_WMS e where e.service_code= '"+ serviceCode + "'";
		try
		{
			String urlWMS = baseDaoSupport.queryForString(sqlWMSStr);
			if (null != urlWMS && !"".equals(urlWMS) && !"null".equals(urlWMS))
			{
				SendWMSUtil sWMS = new SendWMSUtil();
				String msg = searchProductWMS(coQueueRuleReq.getCoQueue().getObject_id(), 
						coQueueRuleReq.getCoQueue().getAction_code(),
						coQueueRuleReq.getCoQueue().getOrg_id_str());
				// 虚拟货品不同步WMS
				if (null != msg && !"".equals(msg))
				{
					result = SendWMSUtil.getWSDLCall(urlWMS, msg);
					logger.info("货品["+ coQueueRuleReq.getCoQueue().getObject_id()+ "]同步WMS报文：" + msg);
					logger.info("货品["+ coQueueRuleReq.getCoQueue().getObject_id()+ "],同步WMS系统返回结果:" + result);
				}
				else
				{ 
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
					coQueueRuleResp.setResp_msg("虚拟货品不同步WMS");
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg("虚拟货品不同步WMS");
					return;
				}
				
				if(null!=result && !"".equalsIgnoreCase(result))
				{//同步有返回
					String ret_val =  JSONObject.fromObject(result).getJSONObject("goods_info_resp").get("resp_code").toString();
					if ("0".equalsIgnoreCase(ret_val)) {
						coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
						coQueueRuleResp.setResp_msg("成功");
						coQueueRuleResp.setError_code("0");
						coQueueRuleResp.setError_msg("成功");
					}
					else
					{
						coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
						coQueueRuleResp.setResp_msg(JSONObject.fromObject(result).getJSONObject("goods_info_resp").get("resp_msg").toString());
						coQueueRuleResp.setError_code("1");
						coQueueRuleResp.setError_msg(JSONObject.fromObject(result).getJSONObject("goods_info_resp").get("resp_msg").toString());
					}
				}
			}
		}
		catch (Exception ex)
		{
			logger.info(ex.getMessage(), ex);
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg("应用抛出异常啦");
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg("应用抛出异常啦");
		}		

		if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code())
				&& Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code()))
		{
			CoModifyStatusReq req = new CoModifyStatusReq();
			req.setId(coQueueRuleReq.getBatch_id()); // 根据同步批次号更新
			req.setService_code(Consts.SERVICE_CODE_CO_HUOPIN);
			req.setStatus_new(Consts.PUBLISH_1.toString());
			this.goodsService.modifyStatus(req);
		}
	}
	
	// CLON转换String


	
	public static String ClobToString(Clob cb) {
		try {
			// 以 java.io.Reader 对象形式（或字符流形式）
			// 检索此 Cb 对象指定的 CLOB 值 --Clob的转换
			Reader inStreamDoc = cb.getCharacterStream();
			// 取得cb的长度
			char[] tempDoc = new char[(int) cb.length()];
			inStreamDoc.read(tempDoc);
			inStreamDoc.close();
			return new String(tempDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException es) {
			es.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行查询SQL，查单列，单行
	 * @param sql
	 * @return
	 */
	public String querySqlResult(String sql){
		String result = "";
		try {
			result = baseDaoSupport.queryForString(sql);
		} catch (Exception e) {
			logger.info("执行SQL[" + sql + "]失败.");
			logger.info(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 判断是否存在
	 */
	public boolean isExists(String sql){
		boolean flag = false;
		try {
			int nums = this.baseDaoSupport.queryForInt(sql);
			flag = nums > 0;
		} catch (Exception e) {
			logger.info("执行SQL[" + sql + "]失败.");
			logger.info(e.getMessage(), e);
		}
		return flag;
	}
}
