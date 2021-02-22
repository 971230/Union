package com.ztesoft.net.rule;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import rule.impl.CoQueueBaseRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.iservice.IGoodsService;
import zte.params.store.req.InventoryApplyLogReq;
import zte.params.store.resp.InventoryApplyLogResp;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.inf.infclient.InfEntirty;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.net.util.SendInfoUtil;
import com.ztesoft.remote.inf.IWarehouseService;
import commons.CommonTools;


/**
 * 商品库存同步规则
 * @author suns
 *
 */
public class CoShangPinKuCunRuleECS extends CoQueueBaseRule {
	
	private Logger logger = Logger.getLogger(CoShangPinKuCunRuleECS.class);
	
	@Resource
	IGoodsService goodsService;
	
	@Resource
	IWarehouseService warehouseService;
	
	@Resource
	IOrderServiceLocal orderServiceLocal;
	
	@Override
	public CoQueueRuleResp coQueue(CoQueueRuleReq coQueueRuleReq) {
		
		logger.debug("商品库存同步");
		CoQueueRuleResp coQueueRuleResp = new CoQueueRuleResp();
		
		
        coQueueRuleResp.setResp_code(Consts.RESP_CODE_000);
		
		//获取货品库存日志信息
		InventoryApplyLogReq req = new InventoryApplyLogReq();
		req.setLog_id(coQueueRuleReq.getObject_id());
		InventoryApplyLogResp resp = warehouseService.queryApplyLogById(req);
		
		/**
		 * 1、调接口同步数据
		 */
		InfEntirty entity = new InfEntirty();
		String rtnStr = InfEntirty.goodInventorySyn(resp, coQueueRuleReq);
		if (StringUtils.isEmpty(rtnStr)) {
			CommonTools.addError("-1", "商品库存同步接口同步失败");
			coQueueRuleResp.setResp_code("-1");
		}
		
		//http://192.168.17.115:8777/integrationweb/integration/goodsInventory.sync
		SendInfoUtil siu = new SendInfoUtil();
		
        String serviceCode="CO_SHANGPIN_KUCUN";
        if(StringUtils.equals("100312",coQueueRuleReq.getCoQueue().getOrg_id_str())){
        	serviceCode="CO_SHANGPIN_KUCUN_100312";
        }
		String sqlStr="SELECT e.addr FROM inf_addr e where e.service_code='"+serviceCode +"'";//+"' and a.org_id_belong='"+idBelong[i]+"'";
		String url=orderServiceLocal.queryString(sqlStr);
		logger.info("完整的报文："+rtnStr+"\nURL："+url);
		String  huoping_fanghui = SendInfoUtil.postHttpReq(rtnStr, url);
		logger.info("===============================商品库存同步返回信息="+huoping_fanghui);
		
		return coQueueRuleResp;
		
	}
}
