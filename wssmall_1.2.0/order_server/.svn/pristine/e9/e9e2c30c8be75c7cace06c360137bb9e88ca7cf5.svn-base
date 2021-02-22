package com.ztesoft.net.mall.core.action.order.service.contract;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.action.order.accept.CommonAcceptHander;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Contract;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAuditRequest;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class ContractAcceptHander extends CommonAcceptHander {

	@Override
	public void display() {
		

	}

	/**
	 * 1.将分销平台的号码调拨到二级分销商,写受理成功日志 2.调用接口将crm 调拨号码同步到分销平台 3.调用crm接口资料返档，成功后更新订单状态
	 * 4.参考退费处理环节
	 */
	@Override
	public void execute ()  {
		
		Order order = getOrder();
		if(order == null) //订单为空，直接订购处理
		{
			contractManager.order(getOrderRequst(), getOrderResult()); // 调用合约机受理接口
			setCanNext(false);
			return;
		}
		
		String userid = ManagerUtils.getUserId();
		if (order.getSource_from().equals(OrderStatus.SOURCE_FROM_AGENT_TOW) || order.getSource_from().equals(OrderStatus.SOURCE_FROM_AGENT_ONE) ) { // 分销商 申请单处理

			orderNFlowManager.accept(order.getOrder_id());
			accNbrManager.transfer_phone_no_for_accept(getOrderRequst(), getOrderResult()); //new 合约机号码调拨
			accNbrManager.resetUnUsedAccNbrOrderId(order.getOrder_id());  //将预占用的设置为可用
			
		} else if (order.getSource_from().equals(OrderStatus.SOURCE_FROM_TAOBAO)) { // 淘宝订单

			// 需要人员审核处理
			if (orderAuditManager.getLastAuditRecord(getOrder().getOrder_id(),OrderStatus.AUDIT_TYPE_00A) == null) {
				// 审核处理
				OrderAuditRequest orderAuditRequest = getOrderRequst().getOrderAuditRequest();
				orderAuditRequest.setOrder_id(getOrder().getOrder_id());
				orderAuditRequest.setFrom_userid(ManagerUtils.getUserId());
				orderAuditRequest.setCreate_date(DBTUtil.current());
				if(isFirstPartner())//一级分销商申请，直接修改状态为电信人员审核
					orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_2); 
				else
					orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_0);
				orderAuditRequest.setSequ(0);
				orderAuditManager.apply(orderAuditRequest);
				setCanNext(false);

			} else {
				OrderAuditRequest orderAuditRequest = getOrderRequst().getOrderAuditRequest();
				String p_state = orderAuditRequest.getP_audit_state();
				String state = getLastAuditState();
				if (state.equals(OrderStatus.ORDER_AUDIT_STATE_4)) {
					
					
					//预存金验证
//					int amount = getOrder().getOrder_amount().intValue();
//					String userId = getOrder().getUserid();
//					super.canPay(amount, userId);
					//合约机处理
					contractManager.order(getOrderRequst(), getOrderResult()); // 调用合约机受理接口
					
					//根据接口判断成功失败标识
					if(Consts.CODE_SUCC.equals(getOrderResult().getCode())){
						orderNFlowManager.accept(order.getOrder_id());
						
						//更新订单项信息 TODO 
					}else if(Consts.CODE_FAIL.equals(getOrderResult().getCode())){
						orderNFlowManager.acceptFail(order.getOrder_id(), getOrderResult().getMessage());
						setCanNext(false);
						return;
					}
					
					
					//更新子订单项订串码 
					OrderItem orderItem = new OrderItem();
					orderItem.setOrder_id(getOrder().getOrder_id());
					Contract contract = getOrderRequst().getContract();
					//orderItem.setTerminal_code(contract.getTerminal_code());
					
					orderNFlowManager.updateOrderItem(orderItem);
					
					
					
					//二级分销商扣除预存金，一级的费用在crm已经扣除
//					if(isSecondAgent()){
//						super.debitDepost(amount, userId);
//					}
					setCanNext(false);
					
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
					}
					setCanNext(false);
					orderAuditRequest.setState_date(DBTUtil.current());
					orderAuditRequest.setOrder_id(getOrder().getOrder_id());
					orderAuditManager.audit(orderAuditRequest);
				}
				
			}
		}
		
	}

	@Override
	public void accept() {

	}

}
