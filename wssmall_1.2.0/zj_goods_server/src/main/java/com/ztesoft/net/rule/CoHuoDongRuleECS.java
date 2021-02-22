package com.ztesoft.net.rule;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.iservice.IGoodsService;
import zte.params.goods.req.CoModifyStatusReq;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.infclient.InfEntirty;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;
import com.ztesoft.remote.inf.IActivityService;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;


/**
 * 活动同步规则
 * @author suns
 *
 */
public class CoHuoDongRuleECS extends CoQueueBaseRule {
	
	private Logger logger = Logger.getLogger(CoHuoDongRuleECS.class);
	
	@Resource
	IGoodsService goodsService;
	@Resource
	IActivityService activityService;
	@Resource
	IOrderServiceLocal orderServiceLocal;
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		
		logger.debug("活动同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
		//获取活动同步信息
		 PromotionMapByIdReq request = new PromotionMapByIdReq();
		 request.setActivity_id(coQueueRuleReq.getObject_id());
		 PromotionMapByIdResp resp =activityService.getPromotionMap(request);
		/**
		 * 1、调接口同步数据
		 */
		 try {
				InfEntirty entity = new InfEntirty();
				String rtnStr = InfEntirty.activitySyn(resp, coQueueRuleReq);
				if (StringUtils.isEmpty(rtnStr)) {
//					CommonTools.addError("-1", "活动接口同步失败");
//					coQueueRuleResp.setResp_code("-1");
					modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
					coQueueRuleResp.setResp_msg("活动接口同步失败");
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg("活动接口同步失败");
					return coQueueRuleResp;
				}
				//http://192.168.17.115:8777/integrationweb/integration/goodsInventory.sync
				//http://10.123.100.230:8002/integrationweb/integration/actExt.sync
				SendInfoUtil siu = new SendInfoUtil();
				String  huoping_fanghui=null;
				String[] org_id_strs =coQueueRuleReq.getCoQueue().getOrg_id_str().split(",");
				boolean flag=false;
				for(int i=0;i<org_id_strs.length;i++){
					String serviceCode="CO_HUODONG";
					   if(!StringUtils.isEmpty(coQueueRuleReq.getCoQueue().getOrg_id_str())
				        		&&existOrgCode(coQueueRuleReq.getCoQueue().getOrg_id_str())
				        		&&!coQueueRuleReq.getCoQueue().getOrg_id_belong().equals("10008")){
				        	serviceCode="CO_HUODONG_"+coQueueRuleReq.getCoQueue().getOrg_id_str();
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
					huoping_fanghui = SendInfoUtil.postHttpReq(rtnStr, url);
					logger.info("===============================[" + coQueueRuleReq.getObject_id() + "]活动信息同步返回信息="+huoping_fanghui);
				}
				
				try {
					String ret_val =  JSONObject.fromObject(huoping_fanghui).getJSONObject("activity_resp").get("resp_code").toString();
					if ("0".equalsIgnoreCase(ret_val)) {
//						coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
						coQueueRuleResp.setError_code("0");
						coQueueRuleResp.setError_msg("成功");
						coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
						coQueueRuleResp.setResp_msg("成功");
					}
					else{
//						CommonTools.addError("-1", JSONObject.fromObject(huoping_fanghui).getJSONObject("prod_offer_resp").get("resp_msg").toString());
//						coQueueRuleResp.setResp_code("-1");
						coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
						coQueueRuleResp.setResp_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("activity_resp").get("resp_msg").toString());
						coQueueRuleResp.setError_code("1");
						coQueueRuleResp.setError_msg(JSONObject.fromObject(huoping_fanghui).getJSONObject("activity_resp").get("resp_msg").toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
//					CommonTools.addError("-1", "订单系统返回失败:"+huoping_fanghui);
//					coQueueRuleResp.setResp_code("-1");
					modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
					coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
					coQueueRuleResp.setResp_msg(huoping_fanghui);
					coQueueRuleResp.setError_code("1");
					coQueueRuleResp.setError_msg(huoping_fanghui);
				}
				
				//2、更新同步状态表[es_activity_co]为已同步
				//坑爹商城改左接口...原来0000改成0.....
				if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && Consts.RESP_CODE_0.equalsIgnoreCase(coQueueRuleResp.getResp_code())) {
//					CoModifyStatusReq req = new CoModifyStatusReq();
//					req.setId(coQueueRuleReq.getBatch_id());  //根据同步批次号更新
//					req.setService_code(Consts.SERVICE_CODE_CO_HUODONG);
//					req.setStatus_new(Consts.PUBLISH_1.toString());
//					this.goodsService.modifyStatus(req);
					modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_1.toString());
				}else if (Consts.ACTION_CODE_A.equals(coQueueRuleReq.getAction_code()) && !Consts.RESP_CODE_0.equalsIgnoreCase(coQueueRuleResp.getResp_code())){
					modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
				}
		} catch (Exception e) {
			coQueueRuleResp.setResp_code(Consts.CO_QUEUE_STATUS_XYSB);
			modifyStatus(coQueueRuleReq.getBatch_id(),Consts.PUBLISH_3.toString());
			coQueueRuleResp.setResp_msg(e.getMessage());
			coQueueRuleResp.setError_code("1");
			coQueueRuleResp.setError_msg(e.getMessage());
			// TODO: handle exception
		}

		
		return coQueueRuleResp;
	}
	
	private void modifyStatus(String batch_id,String status){
		CoModifyStatusReq req = new CoModifyStatusReq();
		req.setId(batch_id);  //根据同步批次号更新
		req.setService_code(Consts.SERVICE_CODE_CO_HUODONG);
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
}
