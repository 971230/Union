package com.ztesoft.net.mall.core.action.order;

import javax.annotation.Resource;

import params.req.DespostCanPayReq;
import params.req.DespostDebitReq;
import params.req.PartnerInfoOneReq;
import params.resp.DespostBusResp;
import params.resp.PartnerInfoResp;
import services.CardInf;
import services.GoodsInf;
import services.PartnerInf;
import services.ProductInf;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.webcontext.ThreadOrderHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderAudit;
import com.ztesoft.net.mall.core.model.OrderLog;
import com.ztesoft.net.mall.core.service.IAccNbrManager;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.mall.core.service.ICloudManager;
import com.ztesoft.net.mall.core.service.IContractManager;
import com.ztesoft.net.mall.core.service.ICustReturnedManager;
import com.ztesoft.net.mall.core.service.IOrderAuditManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.IRateManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public abstract class AbstractHander   extends BaseSupport  {
    @Resource
    public ProductInf proudctServ;

    @Resource
    protected GoodsInf goodsServ;

	public ICartManager cartManager;

	public IOrderManager orderManager;

	public IOrderNFlowManager orderNFlowManager;



	public IOrderAuditManager orderAuditManager; //订单审核对象


	public IContractManager contractManager; //合约机受理处理类
	public ICustReturnedManager custReturnedManager;//资料返档案处理类
	public ICloudManager cloudManager; //云卡调拨处理类
	public IRateManager rateManager;//流量卡调拨处理类

    @Resource
    protected CardInf cardServ; //充值卡处理类

	public ICacheUtil cacheUtil;

	public IOrderUtils orderUtils;

	//public IPaymentManager paymentManager;


	public IAccNbrManager accNbrManager;

	protected PartnerInf partnerServ;


	public abstract boolean isCanExecute();

	public OrderRequst getOrderRequst() {
		//return orderRequst;
		return ThreadOrderHolder.getOrderParams().getOrderRequst();
	}

//	public void setOrderRequst(OrderRequst orderRequst) {
//		this.orderRequst = orderRequst;
//	}

	public OrderResult getOrderResult() {
		//return orderResult;
		return ThreadOrderHolder.getOrderParams().getOrderResult();
	}

//	public void setOrderResult(OrderResult orderResult) {
//		this.orderResult = orderResult;
//	}

	public void afterPay(String ordeSeq, Integer orderAmount, String  retnCode, String tranDate){

	}

	public void perform() {
		if (!StringUtil.isEmpty(getOrderRequst().getOrderId())
				&& (getOrderResult().getOrder() == null || StringUtil.isEmpty(getOrderResult().getOrder().getOrder_id())))
			getOrderResult().setOrder(orderManager.get(getOrderRequst().getOrderId()));


		//判断分销商是否允许办理业务,收集订单
		if(this.getClass().getSimpleName().indexOf("CollectHander")>-1){

			//判断商品是否已经下架

			Goods goods = orderUtils.getGoodsId(getOrderRequst().getGoodsApply().getGoods_id());
			if(goods.getDisabled().equals("1"))
			{
				throw new RuntimeException("申请商品"+goods.getName()+"已经下架，不允许办理此业务");
			}

			String state = orderUtils.getGoodsUsageState(getOrderRequst().getGoodsApply().getGoods_id(),getOrderRequst().getGoodsApply().getUserid() );
			if(Consts.GOODS_USAGE_STATE_1.equals(state)){
				throw new RuntimeException("分销商业务已冻结、不允许办理此业务");
			}

			GoodsApply goodsApply = getOrderRequst().getGoodsApply();


			if(goodsApply !=null)
			{
				if(OrderStatus.SOURCE_FROM_TAOBAO.equals(goodsApply.getSource_from()))
				{
					if(StringUtil.isEmpty(state))
						throw new RuntimeException("分销商未申请受理业务，不允许办理此业务");
				}

			}

			//判断分销商用户是否已经被冻结、注销
			String userId = getUserId();
			if(!StringUtil.isEmpty(userId)){
				PartnerInfoOneReq partnerInfoRep = new PartnerInfoOneReq();
				partnerInfoRep.setUserid(userId);
				PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoRep);

				Partner partner = new Partner();
				if(partnerInfoResp != null){
					partner = partnerInfoResp.getPartner();
				}
				if(partner ==null || Consts.PARTNER_STATE_CONGELATION.equals(partner.getState()) || Consts.PARTNER_STATE_LOGOUT.equals(partner.getState()))
				{
					throw new RuntimeException("分销商已冻结、或注销，不允许再办理此业务");
				}
			}

			String p_userid = ManagerUtils.getAdminUser().getParuserid();
			state = orderUtils.getGoodsUsageState(getOrderRequst().getGoodsApply().getGoods_id(),p_userid );
			if(Consts.GOODS_USAGE_STATE_1.equals(state)){
				throw new RuntimeException("尊敬的分销商、您的上级分销商业务已冻结、不允许办理此业务");
			}
			PartnerInfoOneReq partnerInfoOneRep = new PartnerInfoOneReq();
			partnerInfoOneRep.setUserid(userId);

			PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneRep);
			Partner partner = new Partner();
			if(partnerInfoResp != null){
				partner = partnerInfoResp.getPartner();
			}
			if(partner ==null || Consts.PARTNER_STATE_CONGELATION.equals(partner.getState()) || Consts.PARTNER_STATE_LOGOUT.equals(partner.getState()))
			{
				throw new RuntimeException("尊敬的分销商、您的上级分销商已冻结、或注销，不允许再办理此业务");
			}


			String user_state = orderUtils.getAdminUserByUserId(ManagerUtils.getAdminUser().getUserid());
			if(StringUtil.isEmpty(user_state))
				throw new RuntimeException("分销商工号已被删除，不允许在办理此业务");
			else if(!StringUtil.isEmpty(user_state) && "invalid".equals(user_state)){
				throw new RuntimeException("分销商工号已被禁用，不允许在办理此业务");
			}

		}
		this.execute();
		getOrderResult().setOrder(getOrder());

	}

	private String getUserId() {
		String userId  ="";
		if(getOrderRequst().getGoodsApply() !=null)
			userId =getOrderRequst().getGoodsApply().getUserid();
		if(StringUtil.isEmpty(userId))
			userId =getOrder().getUserid();
		return userId;
	}

	public void render() {
		if (!StringUtil.isEmpty(getOrderRequst().getOrderId() + "")
				&& (getOrderResult().getOrder() == null || StringUtil.isEmpty(getOrderResult().getOrder().getOrder_id())))
			getOrderResult().setOrder(orderManager.get(getOrderRequst().getOrderId()));
		this.display();

		getOrderResult().setOrder(getOrder());

	}

	public Order getOrder() {
		if (!StringUtil.isEmpty(getOrderRequst().getOrderId() + "")
				&& (getOrderResult().getOrder() == null || StringUtil.isEmpty(getOrderResult().getOrder().getOrder_id())))
			getOrderResult().setOrder(orderManager.get(getOrderRequst().getOrderId()));
		return getOrderResult().getOrder();
	}


	public void addLog(String message){
		Order order = getOrderResult().getOrder();
		OrderLog log = new OrderLog();
		log.setMessage(message);
		log.setOp_name(ManagerUtils.getAdminUser().getUsername());
		log.setOrder_id(order.getOrder_id());
		orderManager.addLog(log);

	}



	public void canPay(int amount, String userId) {

		PartnerInfoOneReq partnerInfoOneRep = new PartnerInfoOneReq();
		partnerInfoOneRep.setUserid(userId);

		PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneRep);
		Partner partner = new Partner();
		if(partnerInfoResp != null){
			partner = partnerInfoResp.getPartner();
		}
		if(partner ==null)
			throw new RuntimeException("订单所属分销商用户不存在，请检查！");

		String partnerId = partner.getPartner_id();


		DespostCanPayReq despostCanPayRep = new DespostCanPayReq();
		despostCanPayRep.setPartnerId(partnerId);
		despostCanPayRep.setAmount(amount);
		despostCanPayRep.setFlag("Y");

		DespostBusResp despostCanPayResp = this.partnerServ.canPay(despostCanPayRep);

		boolean flag = true;
		if(despostCanPayResp != null){
			flag = despostCanPayResp.getFlag();
		}

		if(!flag){

			throw new RuntimeException("账户余额不足");
		}

	}

	public void debitDepost(int amount, String userId) {
		PartnerInfoOneReq partnerInfoOneRep = new PartnerInfoOneReq();
		partnerInfoOneRep.setUserid(userId);

		PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneRep);
		Partner partner = new Partner();
		if(partnerInfoResp != null){
			partner = partnerInfoResp.getPartner();
		}
		String partnerId = partner.getPartner_id();

		DespostDebitReq despostDebitRep = new DespostDebitReq();
		despostDebitRep.setPartnerId(partnerId);
		despostDebitRep.setAmount(amount);
		despostDebitRep.setFlag("Y");
		despostDebitRep.setTableName(Consts.ORDER_ES_ORDER);
		despostDebitRep.setOrderId(getOrder().getOrder_id());

		this.partnerServ.debit(despostDebitRep);
	}


	public OrderAudit getLastAuditRecord(){
		OrderAudit orderAudit  = new OrderAudit();
		try{
			orderAudit= orderAuditManager.getLastAuditRecord(getOrder().getOrder_id(),getOrderRequst().getOrderAuditRequest().getAudit_type());
		}catch (Exception e) {
			// TODO: handle exception
		}
		return orderAudit;

	}

	public String getLastAuditState(){
		 OrderAudit orderAudit = getLastAuditRecord();
		 if(orderAudit == null)
			 return "";
		return orderAudit.getState();

	}

	public abstract void execute();

	public abstract void display();

	public ICartManager getCartManager() {
		return cartManager;
	}

	public void setCartManager(ICartManager cartManager) {
		this.cartManager = cartManager;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}

	public void setCanNext(boolean state) {
		this.getOrderRequst().setCan_execute(state);
	}


	public boolean isSecondAgent() {
		if (OrderStatus.SOURCE_FROM_AGENT_TOW.equals(getOrder()
				.getSource_from()))
			return true;
		return false;
	}

	public boolean isFristAgent() {
		if (OrderStatus.SOURCE_FROM_AGENT_ONE.equals(getOrder()
				.getSource_from()))
			return true;
		return false;
	}

	public boolean isTaobaoAgent() {
		if (OrderStatus.SOURCE_FROM_TAOBAO.equals(getOrder().getSource_from()))
			return true;
		return false;
	}

	//是否为一级分销商订单
	public boolean isFirstPartnerOrder() {
		String userid = getOrder().getUserid();
		AdminUser adminUser = orderUtils.getAdminUserById(userid);
		if (Consts.CURR_FOUNDER_3.equals(adminUser.getFounder()+""))
			return true;
		return false;
	}

	//是否为一级分销商订单
	public boolean isSecondPartnerOrder() {
		String userid = getOrder().getUserid();
		AdminUser adminUser = orderUtils.getAdminUserById(userid);
		if (Consts.CURR_FOUNDER_2.equals(adminUser.getFounder()+""))
			return true;
		return false;
	}

	public boolean isFirstPartner() {
		if (ManagerUtils.isFirstPartner())
			return true;
		return false;
	}

	public boolean isSecondPartner() {
		if (ManagerUtils.isSecondPartner())
			return true;
		return false;
	}

	public boolean isNetStaff() {
		if (ManagerUtils.isNetStaff())
			return true;
		return false;
	}

	//归属同一个客户订单
	public boolean isSameOwnerUserId() {
		return ManagerUtils.getUserId().equals(getOrder().getUserid());
	}

	//归属同一个客户订单
	public boolean isNotSameOwnerUserId() {
		return !ManagerUtils.getUserId().equals(getOrder().getUserid());
	}


	public IContractManager getContractManager() {
		return contractManager;
	}

	public void setContractManager(IContractManager contractManager) {
		this.contractManager = contractManager;
	}

	public ICustReturnedManager getCustReturnedManager() {
		return custReturnedManager;
	}

	public void setCustReturnedManager(ICustReturnedManager custReturnedManager) {
		this.custReturnedManager = custReturnedManager;
	}

	public ICloudManager getCloudManager() {
		return cloudManager;
	}

	public void setCloudManager(ICloudManager cloudManager) {
		this.cloudManager = cloudManager;
	}

	public IOrderAuditManager getOrderAuditManager() {
		return orderAuditManager;
	}

	public void setOrderAuditManager(IOrderAuditManager orderAuditManager) {
		this.orderAuditManager = orderAuditManager;
	}

	public IRateManager getRateManager() {
		return rateManager;
	}

	public void setRateManager(IRateManager rateManager) {
		this.rateManager = rateManager;
	}

	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}

	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}



	/*public IPaymentManager getPaymentManager() {
		return paymentManager;
	}

	public void setPaymentManager(IPaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}*/


	public IAccNbrManager getAccNbrManager() {
		return accNbrManager;
	}

	public void setAccNbrManager(IAccNbrManager accNbrManager) {
		this.accNbrManager = accNbrManager;
	}

	public PartnerInf getPartnerServ() {
		return partnerServ;
	}

	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	public ProductInf getProudctServ() {
		return proudctServ;
	}

	public void setProudctServ(ProductInf proudctServ) {
		this.proudctServ = proudctServ;
	}

	public GoodsInf getGoodsServ() {
		return goodsServ;
	}

	public void setGoodsServ(GoodsInf goodsServ) {
		this.goodsServ = goodsServ;
	}

	public CardInf getCardServ() {
		return cardServ;
	}

	public void setCardServ(CardInf cardServ) {
		this.cardServ = cardServ;
	}



}
