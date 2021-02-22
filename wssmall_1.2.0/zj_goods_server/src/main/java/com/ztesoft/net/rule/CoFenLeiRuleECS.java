package com.ztesoft.net.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;

import com.ztesoft.common.util.JsonUtil;
import com.ztesoft.inf.infclient.paramsL;
import com.ztesoft.inf.infclient.paramsenum;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;



/**
 * 分类同步规则
 * @author suns
 *
 */
public class CoFenLeiRuleECS extends CoQueueBaseRule {
	
	
	private Logger logger = Logger.getLogger(CoFenLeiRuleECS.class);

	@Resource
	IOrderServiceLocal orderServiceLocal;
	
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		logger.debug("分类同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		try {
			boolean flag = classifySyn(coQueueRuleReq);
			//分类是批量同步的所以不管是否全部成功都表示成功
			flag = true;
			if (flag) {
				coQueueRuleResp.setError_code("0");
				coQueueRuleResp.setError_msg("成功");
				coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
				coQueueRuleResp.setResp_msg("成功");
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
		}
		return coQueueRuleResp;
	}
	
	/**
	 * 分类同步
	 * @return
	 */
	public boolean classifySyn(CoQueueRuleReq coQueueRuleReq){
		boolean flag = true;
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SendInfoUtil siu = new SendInfoUtil();
		String seq = "";
		List<Map> classList = this.baseDaoSupport.queryForList("SELECT a.NAME cat_name,a.cat_id cat_code,a.type_id,a.parent_id parents_code,'0' type_state," +
				"A.TYPE type_i from es_goods_cat a LEFT JOIN es_goods_type b ON b.type_id = a.cat_id", null);
		for (int i = 0; i < classList.size(); i++) {
			try {
				seq = this.baseDaoSupport.getSequences("seq_goods");
				Map m = classList.get(i);
				String time = format.format(new Date());
				StringBuffer jsonBuffer = new StringBuffer();

				String type = m.get("type_i").toString();
				if("product".equals(type)){
					type = "1";
				}else{
					type = "0";
				}
				jsonBuffer.append("{\"type_req\":");
				jsonBuffer.append("{\"tip\":").append("\"").append("分类信息同步").append("\",");
				jsonBuffer.append("\"serial_no\":").append("\"").append(seq).append("\",");
				jsonBuffer.append("\"time\":").append("\"").append(time).append("\",");
				jsonBuffer.append("\"source_system\":").append("\"").append("10011").append("\",");
				jsonBuffer.append("\"receive_system\":").append("\"").append("10008").append("\",");
				jsonBuffer.append("\"type_code\":").append("\"").append("").append(m.get("cat_code")).append("\",");
				jsonBuffer.append("\"action\":").append("\"").append(coQueueRuleReq.getAction_code()).append("\",");
				jsonBuffer.append("\"type_name\":").append("\"").append(m.get("cat_name")).append("\",");
				jsonBuffer.append("\"type_class\":").append("\"").append(type).append("\",");
				jsonBuffer.append("\"type_parents_code\":").append("\"").append(m.get("parents_code")).append("\",");
				jsonBuffer.append("\"type_state\":").append("\"").append("0").append("\",");
				jsonBuffer.append("\"brand_list\":").append("[");
				
				try {
					List l_brand = this.baseDaoSupport.queryForList("select b.brand_code,b.NAME from es_type_brand a JOIN es_brand b " +
							"ON b.brand_id = a.brand_id AND b.disabled = 0 WHERE a.source_from = '"+ManagerUtils.getSourceFrom()+"' and b.source_from = '"+ManagerUtils.getSourceFrom()+"' and a.type_id ="+ m.get("cat_code").toString());
					if (null != l_brand && l_brand.size() > 0) {
						for (int j = 0; j < l_brand.size(); j++) {
							Map lm = (Map) l_brand.get(j);
							jsonBuffer.append("{");
							jsonBuffer.append("\"brand_code\":").append("\"" + lm.get("brand_code") + "\",");
							jsonBuffer.append("\"brand_name\":").append("\"" + lm.get("name") + "\"");
							if (j < l_brand.size() - 1) {
								jsonBuffer.append("},");
							} else {
								jsonBuffer.append("}");
							}
						}
					}
				} catch (Exception e) {
					logger.info(e.getMessage(),e);
				}
				jsonBuffer.append("],");
				jsonBuffer.append("\"param_list\":").append("[");
				//获取订单的params参数值
				try {
					String params = baseDaoSupport.queryForString("select params from es_goods_type c where type_id = '"+m.get("cat_code").toString()+"'");
					
					if (null != params && !"".equals(params) && !"[]".equalsIgnoreCase(params) && !"<CLOB>".equals(params)) {
						String strpar = params.toString();
						//strpar = strpar.substring(1, strpar.lastIndexOf("]"));
						JSONArray jsonArr = JSONArray.fromObject(strpar);
						paramsL pl = JsonUtil.fromJson(jsonArr.getString(0), paramsL.class);
						if (pl.getParamList().size() > 0) {
							for (int k = 0; k < pl.getParamList().size(); k++) {
								paramsenum tmp = pl.getParamList().get(k);
								jsonBuffer.append("{");
								jsonBuffer.append("\"param_code\":").append("\"").append(tmp.getEname()).append("\",");
								jsonBuffer.append("\"param_name\":").append("\"").append(tmp.getName()).append("\"");
								jsonBuffer.append("}");
								if (k < pl.getParamList().size() - 1){
									jsonBuffer.append(",");
								}

							}
						}
					}
				} catch (Exception e) {
					logger.info(e.getMessage(),e);
				}
				jsonBuffer.append("]");
				jsonBuffer.append("}}");
				
				String serviceCode="CO_FENLEI";
				String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";//+"' and a.org_id_belong='"+idBelong[i]+"'";
				String url=orderServiceLocal.queryString(sqlStr);
				logger.info("分类["+m.get("cat_code")+"]同步完整的报文："+jsonBuffer.toString()+"\nURL："+url);
				String  huoping_fanghui = SendInfoUtil.postHttpReq(jsonBuffer.toString().replaceAll("\"null\"", "\"\""), url);
				logger.info("===============================分类["+m.get("cat_code")+"]同步返回信息="+huoping_fanghui);
			}catch (Exception e) {
				flag = false;
				logger.info(e.getMessage(), e);
			}
		}		
		return flag;
	}
	
}
