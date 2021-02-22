package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import services.CardInf;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.model.DealLog;
import com.ztesoft.net.mall.core.service.IDealCardFailManager;
import com.ztesoft.net.mall.core.service.IDealLogManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.sqls.SF;

public class DealCardFailManager extends BaseSupport implements IDealCardFailManager {
    @Resource
    private CardInf cardServ;  //充值卡处理类

	private IDealLogManager dealLogManager;
	private IOrderNFlowManager orderNFlowManager;
	private CacheUtil cacheUtil;

	@Override
	public void dealCardFail(){
		try {
			//修改=== mochunrun 2014-4-21
			/*IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
			JobTaskCheckedReq req = new JobTaskCheckedReq();
			req.setClassName(DealCardFailManager.class.getName());
			req.setMethod("dealCardFail");
			JobTaskCheckedResp resp = jobTaskService.checkedJobTask(req);
			if(!resp.isCanRun())
				return ;*/
			
			/*if (!CheckSchedulerServer.isMatchServer()) {
				return;
			}*/
			List<Map> dealList = getCardFail();
			if(dealList != null && !dealList.isEmpty()){
				for(Map order:dealList){
					int dealCount = Integer.parseInt(order.get("deal_count").toString());
					if(dealCount < Consts.MAX_DEAL_COUNT){
						Card card = cardServ.getCardByUserIdAndMoney(order.get("userid").toString(), order.get("order_amount").toString(),
																				order.get("type_code").toString(),order.get("goods_id").toString());
						if(null != card){
							CardInfRequest cardInfRequest = new CardInfRequest();
							cardInfRequest.setAccNbr(order.get("acc_nbr").toString());
							Map result = cardServ.uvcRecharge(card, cardInfRequest);
							if(result ==null || result.get("code") == null || Consts.CODE_FAIL.equals(result.get("code"))){
								card.setState(Consts.CARD_INFO_STATE_4);
                                cardServ.updateCard(card);
								String message ="";
								if(result !=null){
									message =(String)result.get("massage");
									if(!StringUtil.isEmpty(message) && message.length()>3000)
										message = message.substring(0,3000);
								}
								orderNFlowManager.acceptFail(order.get("order_id").toString(), "调用UVC充值失败:"+message);
							}else if(Consts.CODE_SUCC.equals(result.get("code"))){
								card.setSec_order_id(order.get("order_id").toString());
								card.setState(Consts.CARD_INFO_STATE_2);
                                cardServ.updateCard(card);
								orderNFlowManager.accept_ship_auto(order.get("order_id").toString());
							}
							
							//写日志
							DealLog dealLog = new DealLog();
							String code = "";
							String message = "";
							if(result !=null){
								code = result.get("code").toString();
								message = result.get("massage").toString();
							}
							if(dealCount > 0){
								dealLog = dealLogManager.getDealLogbyId(order.get("order_id").toString());
								dealLog.setDeal_count(dealCount + 1);
								if(code.equals(Consts.CODE_SUCC)){
									dealLog.setDeal_result("00S");
								}else {
									dealLog.setDeal_result("00F");
								}
								dealLog.setStatus_date(DBTUtil.current());
								dealLog.setComments(message);
								dealLogManager.updateLog(dealLog);
							}else {
								dealLog.setDeal_order_id(order.get("order_id").toString());
								dealLog.setCreate_date(DBTUtil.current());
								dealLog.setStatus_date(DBTUtil.current());
								dealLog.setDeal_count(1);
								if(code.equals(Consts.CODE_SUCC)){
									dealLog.setDeal_result("00S");
								}else {
									dealLog.setDeal_result("00F");
								}
								dealLog.setType_code(order.get("type_code").toString());
								dealLog.setComments(message);
								dealLogManager.addLog(dealLog);
							}
						}
						
					}else {
						//发短信通知
						String smsMsg = "订单：" + order.get("order_id").toString() + "处理" + Consts.MAX_DEAL_COUNT +"次后，" +
								"依然失败，请联系相关人员手工处理";
						String reciveNums = cacheUtil.getConfigInfo(Consts.CHARGE_FAIL_NBRS);
						String[] reviceNum = reciveNums.split(",");
						if(reviceNum != null && reviceNum.length > 0){
							for(int i = 0; i < reviceNum.length; i++){
								//smsManager.addShortMsg(smsMsg, reviceNum[i]);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private List<Map> getCardFail(){
		
		String sql = SF.orderSql("SERVICE_CARD_FAIL_SELECT");
		
		return this.baseDaoSupport.queryForList(sql);
	}

	public IDealLogManager getDealLogManager() {
		return dealLogManager;
	}

	public void setDealLogManager(IDealLogManager dealLogManager) {
		this.dealLogManager = dealLogManager;
	}
	
	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}

	public CacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(CacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
}
