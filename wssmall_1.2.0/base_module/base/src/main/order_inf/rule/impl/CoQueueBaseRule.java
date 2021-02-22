package rule.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;

import rule.AbstractRuleHander;
import rule.ICoQueueRule;
import rule.params.coqueue.req.CoQueueRuleReq;
import rule.params.coqueue.resp.CoQueueRuleResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.ICoQueueService;

import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CoQueue;

import commons.CommonTools;

public abstract class CoQueueBaseRule extends AbstractRuleHander implements ICoQueueRule {
	
	@Resource
	private ICoQueueService coQueueService;
	
	/**
	 * 消息队列规则
	 * 1、调本地规则
	 * 2、调完本地规则，更新消息队列表信息
	 * @param coQueueRuleReq
	 * @return
	 */
	public CoQueueRuleResp coQueuePerform(CoQueueRuleReq coQueueRuleReq) {
		
		CoQueue coQueue = coQueueRuleReq.getCoQueue();
		//add by wui
		if(coQueue ==null){
			coQueue = coQueueService.get(coQueueRuleReq.getCo_id());
			coQueueRuleReq.setCoQueue(coQueue);
		}
		
		//1、调本地规则
		@SuppressWarnings("unused")
		CoQueueRuleResp coQueueRuleResp = this.coQueue(coQueueRuleReq);
		
		//add by wui 2.0订单不写队列表，当处理失败后才真正写入队列历史表即可，优化性能  失败写入队列失败表移到OrderServices.java 923行,数据同步老系统//TODO需要继续优化，临时先不优化
		String shipping_type = "";
		if( coQueueRuleResp.getOrderAddResp() !=null){
			String order_id = coQueueRuleResp.getOrderAddResp().getOrderList().get(0).getOrder_id();
			shipping_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SENDING_TYPE);
		}
		
		//2.0非现场交付处理
		if(!"XJ".equals(shipping_type)){
			//2、更新消息队列表信息——如果接口处理失败，则更新消息队列状态为XYSB
			if (!Consts.RESP_CODE_000.equals(coQueueRuleResp.getResp_code())) {
				
				String msg =coQueueRuleResp.getResp_msg();
				int msgLength = 3500;
				if(StringUtils.isEmpty(msg)){
					msg = CommonTools.getErrorStr();
					if(!StringUtils.isEmpty(msg) && msg.length()>msgLength)
						msg = msg.substring(0,msgLength);
				}
				
				//如果是订单挂起，就把队列状态改成订单挂起DDGQ
				if (Consts.CO_QUEUE_STATUS_DDGQ.equals(coQueueRuleResp.getResp_code())) {
					coQueueService.modifyStatus(coQueue.getCo_id(), 
							coQueueRuleResp.getResp_code(), "", msg);
				} else {
					coQueueService.modifyStatus(coQueue.getCo_id(), 
							coQueueRuleResp.getResp_code(), msg);
				}
				
			} else {
				//处理成功则备份到消息队列表es_co_queue_bak
				coQueueService.delete(coQueue.getCo_id());
			}
		}
	    return coQueueRuleResp;
	}

	public ICoQueueService getCoQueueService() {
		return coQueueService;
	}

	public void setCoQueueService(ICoQueueService coQueueService) {
		this.coQueueService = coQueueService;
	}
	
}