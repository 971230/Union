package com.ztesoft.net.rule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;

import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;



/**
 * 品牌同步规则
 * @author suns
 *
 */
public class CoXingHaoRuleECS extends CoQueueBaseRule {
	
	
	private Logger logger = Logger.getLogger(CoXingHaoRuleECS.class);

	@Resource
	IOrderServiceLocal orderServiceLocal;
	
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		logger.debug("型号同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		try {
//			boolean flag = modeSyn(coQueueRuleReq);
			boolean flag = modeBatchSyn(coQueueRuleReq);
//			型号是批量同步的所以不管是否全部成功都表示成功
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
	 * 型号同步
	 * @return
	 */
	public boolean modeSyn(CoQueueRuleReq coQueueRuleReq){
		boolean flag = true;
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SendInfoUtil siu = new SendInfoUtil();
		String seq = "";
		List<Map> brandIds = this.baseDaoSupport.queryForList("select ee.model_code class_code,ee.model_name class_name,ee.brand_code from es_brand_model ee", null);
		for (int i = 0; i < brandIds.size(); i++) {
			try {
				seq = this.baseDaoSupport.getSequences("seq_goods");
				Map m = brandIds.get(i);
				StringBuffer jsonBuffer = new StringBuffer();
				jsonBuffer.append("{\"class_req\":{");
				jsonBuffer.append("\"serial_no\":\"").append(seq).append("\",");
				jsonBuffer.append("\"time\":\"").append(format.format(new Date())).append("\",");
				jsonBuffer.append("\"source_system\":\"").append("10011").append("\",");
				jsonBuffer.append("\"receive_system\":\"").append("10008").append("\",");
				jsonBuffer.append("\"class_code\":\"").append(m.get("class_code")).append("\",");
				jsonBuffer.append("\"action\":\"").append(coQueueRuleReq.getAction_code()).append("\",");
				jsonBuffer.append("\"class_name\":\"").append(m.get("class_name")).append("\",");
				jsonBuffer.append("\"brand_code\":\"").append(m.get("brand_code")).append("\",");
				jsonBuffer.append("\"class_state\":\"").append("0").append("\"");
				jsonBuffer.append("}}");
				
				String serviceCode="CO_XINGHAO";
				String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";//+"' and a.org_id_belong='"+idBelong[i]+"'";
				String url=orderServiceLocal.queryString(sqlStr);
				logger.info("型号同步完整的报文："+jsonBuffer.toString()+"\nURL："+url);
				String  huoping_fanghui = SendInfoUtil.postHttpReq(jsonBuffer.toString(), url);
				logger.info("===============================型号同步返回信息="+huoping_fanghui);
			}catch (Exception e) {
				flag = false;
				logger.info(e.getMessage(), e);
			}
		}
		return flag;
	}
	
	/**
	 * 型号同步
	 * @return
	 */
	public boolean modeBatchSyn(CoQueueRuleReq coQueueRuleReq){
		boolean flag = true;
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SendInfoUtil siu = new SendInfoUtil();
		String seq = "";
		List<Map> brandIds = this.baseDaoSupport.queryForList("select ee.model_code class_code,ee.model_name class_name,ee.brand_code from es_brand_model ee where ee.brand_model_id=?", coQueueRuleReq.getObject_id());
		for (int i = 0; i < brandIds.size(); i++) {
			try {
				seq = this.baseDaoSupport.getSequences("seq_goods");
				Map m = brandIds.get(i);
				StringBuffer jsonBuffer = new StringBuffer();
				jsonBuffer.append("{\"class_req\":{");
				jsonBuffer.append("\"serial_no\":\"").append(seq).append("\",");
				jsonBuffer.append("\"time\":\"").append(format.format(new Date())).append("\",");
				jsonBuffer.append("\"source_system\":\"").append("10011").append("\",");
				jsonBuffer.append("\"receive_system\":\"").append(coQueueRuleReq.getCoQueue().getOrg_id_belong()).append("\",");
				jsonBuffer.append("\"class_code\":\"").append(m.get("class_code")).append("\",");
				jsonBuffer.append("\"action\":\"").append(coQueueRuleReq.getAction_code()).append("\",");
				jsonBuffer.append("\"class_name\":\"").append(m.get("class_name")).append("\",");
				jsonBuffer.append("\"brand_code\":\"").append(m.get("brand_code")).append("\",");
				jsonBuffer.append("\"class_state\":\"").append("0").append("\"");
				jsonBuffer.append("}}");
				
				String serviceCode="CO_XINGHAO_"+coQueueRuleReq.getCoQueue().getOrg_id_belong();
				String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";//+"' and a.org_id_belong='"+idBelong[i]+"'";
				String url=orderServiceLocal.queryString(sqlStr);
				logger.info("型号同步完整的报文："+jsonBuffer.toString()+"\nURL："+url);
				String  huoping_fanghui = SendInfoUtil.postHttpReq(jsonBuffer.toString(), url);
				logger.info("===============================型号同步返回信息="+huoping_fanghui);
			}catch (Exception e) {
				flag = false;
				logger.info(e.getMessage(), e);
			}
		}
		return flag;
	}
	
}