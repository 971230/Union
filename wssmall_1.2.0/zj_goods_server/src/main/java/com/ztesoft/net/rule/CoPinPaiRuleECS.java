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
public class CoPinPaiRuleECS extends CoQueueBaseRule {
	
	
	private Logger logger = Logger.getLogger(CoPinPaiRuleECS.class);

	@Resource
	IOrderServiceLocal orderServiceLocal;
	
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		logger.debug("品牌同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		try {
			boolean flag = brandSyn(coQueueRuleReq);
//			品牌是批量同步的所以不管是否全部成功都表示成功
			flag = true;
			if (flag) {
				coQueueRuleResp.setError_code("0");
				coQueueRuleResp.setError_msg("成功");
				coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
				coQueueRuleResp.setResp_msg("成功");
			}
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg(e.getMessage());
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg(e.getMessage());
		}
		return coQueueRuleResp;
	}
	
	/**
	 * 品牌同步
	 * @return
	 */
	public boolean brandSyn(CoQueueRuleReq coQueueRuleReq){
		boolean flag = true;
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SendInfoUtil siu = new SendInfoUtil();
		String seq = "";
		List<Map> brandIds = this.baseDaoSupport.queryForList("select * from es_brand", null);
		for (int i = 0; i < brandIds.size(); i++) {
			try {
				seq = this.baseDaoSupport.getSequences("seq_goods");
				Map map = brandIds.get(i);
				String time = format.format(new Date());
				StringBuffer json = new StringBuffer();
				
				json.append("{\"brand_req\":");
				json.append("{\"tip\":").append("\"").append("品牌信息同步").append("\",");
				json.append("\"serial_no\":").append("\"").append(seq + time).append("\",");
				json.append("\"time\":").append("\"").append(time).append("\",");
				json.append("\"source_system\":").append("\"").append("10011").append("\",");
				json.append("\"receive_system\":").append("\"").append("10008").append("\",");
				json.append("\"brand_code\":").append("\"").append(map.get("brand_code")).append("\",");
				json.append("\"action\":").append("\"").append(coQueueRuleReq.getAction_code()).append("\",");
				json.append("\"brand_name\":").append("\"").append(map.get("name")).append("\",");
				json.append("\"type_code\":").append("\"").append("").append("\",");
				json.append("\"brand_class\":").append("\"").append("").append("\",");
				json.append("\"brand_state\":").append("\"").append(map.get("disabled")).append("\",");
				json.append("\"logo_addr\":").append("\"").append(map.get("logo")).append("\"");
				json.append("}}");
				String serviceCode="CO_PINPAI";
				String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";//+"' and a.org_id_belong='"+idBelong[i]+"'";
				String url=orderServiceLocal.queryString(sqlStr);
				logger.info("品牌同步完整的报文："+json+"\nURL："+url);
				String  huoping_fanghui = SendInfoUtil.postHttpReq(json.toString(), url);
				logger.info("===============================品牌同步返回信息="+huoping_fanghui);
			}catch (Exception e) {
				flag = false;
				logger.info(e.getMessage(), e);
			}
		}
		return flag;
	}
	
}
