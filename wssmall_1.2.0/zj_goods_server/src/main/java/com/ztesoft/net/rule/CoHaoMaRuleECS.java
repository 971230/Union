package com.ztesoft.net.rule;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.INumberService;
import zte.params.goods.req.CoModifyStatusReq;
import zte.params.number.req.NoCoDeleteReq;
import zte.params.number.req.NoInfoByNoReq;
import zte.params.number.req.NumberSynInfoReq;
import zte.params.number.resp.NoInfoByNoResp;
import zte.params.number.resp.NumberSynInfoResp;

import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.infclient.InfEntirty;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;


/**
 * 号码同步规则
 * @author suns
 *
 */
public class CoHaoMaRuleECS extends CoQueueBaseRule {
	
	private Logger logger = Logger.getLogger(CoHaoMaRuleECS.class);
	
	@Resource
	IGoodsService goodsService;
	
	INumberService numberService;
	
	@Resource
	IOrderServiceLocal orderServiceLocal;
	
	private void init(){
		if(null == numberService) numberService = ApiContextHolder.getBean("numberService");
	}
	
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		//初始化bean
		init();
		
		logger.debug("号码同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		String dn_no = coQueueRuleReq.getObject_id();
		
		//获取号码信息
		NumberSynInfoResp resp;
		
		//号码回收
		if (!Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code())) {
			NoInfoByNoReq noReq = new NoInfoByNoReq();
			noReq.setNo(dn_no);
			NoInfoByNoResp noResp = this.numberService.queryNoInfoByNo(noReq);
			resp = new NumberSynInfoResp();
			resp.setNumberSynInfoList(noResp.getNumberSynInfoList());
		} else {
			
			NumberSynInfoReq nsi = new NumberSynInfoReq();
			nsi.setBatch_id(coQueueRuleReq.getCoQueue().getBatch_id());
			resp = numberService.queryNumSynInfo(nsi);
		}
		
		/**
		 * 1、调接口同步数据
		 */
		try {
			InfEntirty entity = new InfEntirty();
			//String rtnStr = entity.numSyn(resp, coQueueRuleReq);
			String rtnStr = InfEntirty.numSync(resp, coQueueRuleReq);
			if (StringUtils.isEmpty(rtnStr)) {
//				CommonTools.addError("-1", "号码信息接口同步失败");
//				coQueueRuleResp.setResp_code("-1");
				coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
				coQueueRuleResp.setResp_msg("号码信息接口同步失败");
				coQueueRuleResp.setError_code("1");
				coQueueRuleResp.setError_msg("号码信息接口同步失败");
				return coQueueRuleResp;
			}
			  
			SendInfoUtil siu = new SendInfoUtil();
			String serviceCode="CO_HAOMA";
			String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";
			String url=orderServiceLocal.queryString(sqlStr);
			logger.info("完整的报文："+rtnStr+"\nURL："+url);
			String  huoping_fanghui = SendInfoUtil.postHttpReq(rtnStr, url);
			logger.info("===============================号码同步返回信息="+huoping_fanghui);
			try {
				String ret_val =  JSONObject.fromObject(huoping_fanghui).getJSONObject("phone_no_resp").get("resp_code").toString();
				if ("0".equalsIgnoreCase(ret_val)) {
					coQueueRuleResp.setError_code("0");
					coQueueRuleResp.setError_msg("成功");
					coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
					coQueueRuleResp.setResp_msg("成功");
				}
				else{
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
					coQueueRuleResp.setResp_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("phone_no_resp").get("resp_msg").toString());
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("phone_no_resp").get("resp_msg").toString());
				}
			} catch (Exception e) {
				// TODO: handle exception
				coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
				coQueueRuleResp.setResp_msg(huoping_fanghui);
				coQueueRuleResp.setError_code("1");
				coQueueRuleResp.setError_msg(huoping_fanghui);
			}
			
			//2、更新同步状态表【es_no_co】为已同步
			if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code())  && Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
				CoModifyStatusReq req = new CoModifyStatusReq();
				req.setId(coQueueRuleReq.getBatch_id());  //根据同步批次号更新
				req.setService_code(Consts.SERVICE_CODE_CO_HAOMA);
				req.setStatus_new(Consts.PUBLISH_1.toString());
				this.goodsService.modifyStatus(req);
			}
			
			//如果是回收则把es_no_co记录删除（无备份）
			if (Consts.ACTION_CODE_D.equals(coQueueRuleReq.getAction_code())  
					&& Consts.RESP_CODE_000.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
				NoCoDeleteReq req = new NoCoDeleteReq();
				req.setDn_no(dn_no);
				this.numberService.deleteNoCo(req);
			}
			
		} catch (Exception e) {
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			coQueueRuleResp.setResp_msg(e.getMessage());
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg(e.getMessage());
			// TODO: handle exception
		}
		
		
		return coQueueRuleResp;
	}
}
