package com.ztesoft.net.mall.core.action.order.refund;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import params.req.DespostBillReq;
import params.req.DespostChargeReq;
import params.req.PartnerInfoOneReq;
import params.resp.PartnerInfoResp;


/**
 * 
 * @author wui
 * 退款,包括：申请、审核等处理环节
 *
 */
public class CommonRefundHander   extends AbstractHander implements IRefundHander {



	@Override
	public void display() {
		
		
	}

	@Override
	public void execute() {
		String userid = ManagerUtils.getUserId();
		OrderAuditRequest orderAuditRequest = getOrderRequst().getOrderAuditRequest();
		// 需要人员审核处理
		if (orderAuditManager.getLastAuditRecord(getOrder().getOrder_id(),orderAuditRequest.getAudit_type()) == null) {
			// 审核处理
			
			orderAuditRequest.setOrder_id(getOrder().getOrder_id());
			orderAuditRequest.setFrom_userid(ManagerUtils.getUserId());
			orderAuditRequest.setCreate_date(DBTUtil.current());
			orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_0);
			orderAuditRequest.setSequ(0);
			orderAuditManager.apply(orderAuditRequest);
			setCanNext(false);

		} else {
			
			String p_state = orderAuditRequest.getP_audit_state();
			String state = getLastAuditState();
			if (state.equals(OrderStatus.ORDER_AUDIT_STATE_4)) {
				
				PaymentLog payment = getOrderRequst().getPayment();
				payment.setOrder_id(getOrder().getOrder_id());
				payment.setStatus(OrderStatus.PAY_STATUS_1);
				payment.setMoney(getOrder().getPaymoney());
				
				
				//充值卡退费处理,设置退费金额
				if(OrderStatus.ORDER_TYPE_2.equals(getOrder().getOrder_type()) && OrderBuilder.CARD_KEY.equals(getOrder().getType_code())){
					payment.setMoney(-getOrder().getOrder_amount());
				}
				orderNFlowManager.refund(payment);
				
				//TODO 根据类型进行相应的退费处理，统一为预存金退费
				String userId = payment.getUserid();
				
				PartnerInfoOneReq partnerInfoOneRep = new PartnerInfoOneReq();
				partnerInfoOneRep.setUserid(userId);
				
				PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneRep);
				Partner partner = new Partner();
				if(partnerInfoResp != null){
					partner = partnerInfoResp.getPartner();
				}
				String partnerId = partner.getPartner_id();
				Integer amount = getOrder().getOrder_amount().intValue();
				if(OrderBuilder.CONTRACT_KEY.equals(getOrder().getType_code())) //合约机退费处理,扣除预存金、一级分销商预存金此处不处理
				{
					if(isSecondPartnerOrder()){
						DespostChargeReq despostChargeRep = new DespostChargeReq();
						despostChargeRep.setPartnerId(partnerId);
						despostChargeRep.setAmount(amount);
						despostChargeRep.setFlag("Y");
						despostChargeRep.setTableName(Consts.ORDER_ES_ORDER);
						despostChargeRep.setOrderId(getOrder().getOrder_id());
						//只退二级分销商
						partnerServ.charge(despostChargeRep);
					}else if(isFirstPartnerOrder()){
						//不需要
					}
				}else if(OrderBuilder.CARD_KEY.equals(getOrder().getType_code())){  //充值卡退费处理、需要扣除以及、二级分销商的预存金
					cardServ.returnedCards(getOrder().getOrder_id());
					if(isFirstPartnerOrder())
					{
						DespostBillReq despostBillRep = new DespostBillReq();
						despostBillRep.setPartnerId(partnerId);
						despostBillRep.setAmount(amount);
						despostBillRep.setFlag("Y");
						
						//一级退费
						partnerServ.chargeBill(despostBillRep);
						
					}else if(isSecondPartnerOrder()){
						//退一级、二级分销商
						DespostChargeReq despostChargeRep = new DespostChargeReq();
						despostChargeRep.setPartnerId(partnerId);
						despostChargeRep.setAmount(amount);
						despostChargeRep.setFlag("Y");
						despostChargeRep.setTableName(Consts.ORDER_ES_ORDER);
						despostChargeRep.setOrderId(getOrder().getOrder_id());
						partnerServ.charge(despostChargeRep);
						
						
						DespostBillReq despostBillRep = new DespostBillReq();
						despostBillRep.setPartnerId(partnerId);
						despostBillRep.setAmount(amount);
						despostBillRep.setFlag("Y");
						partnerServ.chargeBill(despostBillRep);
					}
				}
				
				
				
				//更新关系状态
				OrderRel orderRel = new OrderRel();
				orderRel.setA_order_id(getOrder().getOrder_id());
				orderRel.setRel_type(OrderStatus.ORDER_TYPE_2);
				orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
				orderRel.setState_date(DBTUtil.current());
				orderNFlowManager.updateOrderRel(orderRel);
				
				
				
			} else {
				// 审核处理
				if (p_state.equals(OrderStatus.AUDIT_STATE_YES)) // 审核通过
				{
					if (OrderStatus.ORDER_AUDIT_STATE_0.equals(state)) // 一级分销商审核通过
					{
						orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_2);
						orderAuditRequest.setTo_userid(userid);
						orderAuditRequest.setDeal_message(orderAuditRequest.getP_audit_message());
						
					} else if (OrderStatus.ORDER_AUDIT_STATE_2.equals(state)) {// 电信员工审核通过
						
						orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_4);
						orderAuditRequest.setTo_mgr_userid(userid);
						orderAuditRequest.setMgr_deal_message(orderAuditRequest.getP_audit_message());
						
					}
				} else if (p_state.equals(OrderStatus.AUDIT_STATE_NO)) // 审核不通过
				{
					if (OrderStatus.ORDER_AUDIT_STATE_0.equals(state)) {
						
						orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_1);// 一级分销商审核不通过
						orderAuditRequest.setTo_userid(userid);
						orderAuditRequest.setDeal_message(orderAuditRequest.getP_audit_message());
						
					} else if (OrderStatus.ORDER_AUDIT_STATE_2.equals(state)) {
						
						orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_3); // 电信员工审核不通过
						orderAuditRequest.setTo_mgr_userid(userid);
						orderAuditRequest.setMgr_deal_message(orderAuditRequest.getP_audit_message());
						
					}
					
					//设置不允许退费
					dealOuterOrderApply();
					
				}
				orderAuditRequest.setState_date(DBTUtil.current());
				orderAuditRequest.setOrder_id(getOrder().getOrder_id());
				orderAuditManager.audit(orderAuditRequest);
			}
			setCanNext(false);
		}
	
		
		
	}

	private void dealOuterOrderApply() {
		this.addLog("订单"+getOrder().getOrder_id()+"不允许退费");
		String z_order_id = orderNFlowManager.getZOrderId(getOrder().getOrder_id(), OrderStatus.ORDER_TYPE_2);
		if(!StringUtil.isEmpty(z_order_id)){
			OrderOuter orderOuter = new OrderOuter();
			orderOuter.setOrder_id(z_order_id);
			orderOuter.setComments("分销平台不允许退费");
			orderNFlowManager.updateOrderOuter(orderOuter); //更新外系统订单
			
			OrderRel orderRel = new OrderRel();
			orderRel.setZ_order_id(z_order_id);
			orderRel.setRel_type(OrderStatus.ORDER_TYPE_2);
			orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
			orderNFlowManager.updateOrderRel(orderRel);
			
		}
	}
	@Override
	public void accept() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}

}

