package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;

/**
 * 商品申请
 * 
 * @author wui
 * 
 */
public class GoodsApplyAction extends WWAction {

//	private String goodsid;
//	private String addressNum;
//	private GoodsApply goodsApply;
//	private GoodsAudit goodsAudit;
//	private GoodsTypeDTO goodsType;
//	private IGoodsManager goodsManager;
//	private IProductManager productManager;
//	private IGoodsTypeManager goodsTypeManager;
//	private PartnerInf partnerServ;
//	private ICardManager cardManager;
//	
//	private Product product;
//	private PartnerAddress partnerAddress;
//	private Goods goods;
//	private IOrderDirector orderDirector;
//	private String cards_ids;
//	private String returned_ids;
//	
//	
//	/**
//	 * 显示商品申请界面
//	 * 
//	 * @return
//	 */
//	public String showGoodsApplyDialog() {
//		product = productManager.getByGoodsId(goodsid);
//		goods = goodsManager.getGoods(goodsid);
//		goodsType = goodsTypeManager.get(goods.getType_id());
//		return "goods_apply_dialog";
//	}
//	/**
//	 * 显示退货商品申请界面
//	 */
//	public String showGoodsReturnedApplyDialog() {
//		return "goods_returned_apply_dialog";
//	}
//
//	// 商品申请
//	public String apply() {
//		try {
//			goodsApply();
//			this.json = "{result:1,message:'商品["
//					+ goodsManager.getGoods(goodsApply.getGoods_id()).getName()
//					+ "]申请成功'}";
//		} catch (RuntimeException e) {
//			if (logger.isDebugEnabled()) {
//				logger.debug(e);
//			}
//			this.json = "{result:0,message:\"商品申请失败：" + e.getMessage() + "\"}";
//		}
//		return this.JSON_MESSAGE;
//	}
//	
//	
//	/**
//	 * 充值卡申请退货
//	 */
//	public String chargeReturnedApply() {
//		try {
//			checkChargeReturnedApply();
//			returnedApply();
//			json = "{result:1,message:'退货申请已提交'}";
//			
//		} catch (RuntimeException e) {
//			logger.debug(e);
//			json = "{result:0,message:\"充值卡申请退货失败：" + e.getMessage() + "\"}";
//		}
//		
//		return JSON_MESSAGE;
//	}
//
//	
//	/**
//	 * 
//	 * 商品申请流程 触发条件：前台商品申请时触发
//	 */
//	private void goodsApply() {
//		
//		if(ManagerUtils.isNetStaff())
//			 	throw new RuntimeException("电信员工不允许申请商品");
//		
//		//统一设置销售价格
//		Goods goods  = goodsManager.getGoods(goodsApply.getGoods_id());
//		if(addressNum ==null)
//			throw new RuntimeException("收货地址不能为空，请先新增配送地址");
//		
//		
//		Double price = goods.getPrice();
//		goodsApply.setSales_price(price);
//		
//		if (addressNum.trim().length() > 0) {
//			PartnerAddrReq partnerAddrReq = new PartnerAddrReq();
//			partnerAddrReq.setAddressNum(addressNum);
//			
//			PartnerAddrResp partnerAddrResp = partnerServ.getPartnerAddressByAddr_id(partnerAddrReq);
//			
//			partnerAddress = new PartnerAddress();
//			if(partnerAddrResp != null){
//				partnerAddress = partnerAddrResp.getPartnerAddress();
//			}
//		}
//		if(partnerAddress!=null){
//		 goodsApply.setShip_addr(partnerAddress.getAddr());
//		 goodsApply.setShip_tel(partnerAddress.getTel());
//		 goodsApply.setShip_mobile(partnerAddress.getMobile()); //add mobile
//		 goodsApply.setZip(partnerAddress.getZip());
//  		 goodsApply.setShip_name(partnerAddress.getName());
//		}
//		 goodsApply.setUserid(ManagerUtils.getUserId());
//		 goodsApply.setGoods_id(goodsid);
//		 
//		 if(ManagerUtils.isFirstPartner()){
//			 goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_ONE);
//			 goodsApply.setSource_from(OrderStatus.SOURCE_FROM_AGENT_ONE);
//		 }
//		 if(ManagerUtils.isSecondPartner()){
//			 goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_TWO);
//			 goodsApply.setSource_from(OrderStatus.SOURCE_FROM_AGENT_TOW);
//		 }
//		 goodsApply.setOrder_type(OrderStatus.ORDER_TYPE_1);
//				
//		 
//		 if(goodsApply.getGoods_num()<0)
//			 throw new RuntimeException("申请数量不能小于0");
//		 
//		 goods = goodsManager.getGoods(goodsid);
//		 goodsType = goodsTypeManager.get(goods.getType_id());
//				
//				
//		 orderDirector.getOrderBuilder().buildOrderFlow();
//		 OrderRequst orderRequst = new OrderRequst();
//		 orderRequst.setService_name(goodsManager.getGoodsTypeByGoodsId(goodsid).getType_code());
//		 orderRequst.setFlow_name(OrderBuilder.COLLECT);
//		 orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
//		 orderRequst.setGoodsApply(goodsApply);
//		 orderDirector.perform(orderRequst);
//
//		 
//	}
//	
//	
//	private void checkChargeReturnedApply() {
//		List<Card> list = cardManager.checkUnavailableCard(getReturned_ids());
//		if (list != null && list.size() > 0 ) {
//			String message = "以下卡号不可退货:" + StringUtil.listToString(list, ", ");
//			throw new RuntimeException(message);
//		}
//	}
//	
//	
//	
//	/**
//	 * 
//	 * 充值卡、流量卡退货处理
//	 */
//	private void returnedApply() {
//		PartnerAddrReq partnerAddrReq = new PartnerAddrReq();
//		if (addressNum !=null && addressNum.trim().length() > 0)
//			partnerAddrReq.setAddressNum(addressNum);
//			PartnerAddrResp partnerAddrResp = partnerServ.getPartnerAddressByAddr_id(partnerAddrReq);
//			partnerAddress = new PartnerAddress();
//			if(partnerAddrResp != null){
//				partnerAddress = partnerAddrResp.getPartnerAddress();
//			}
//		if(partnerAddress == null)
//			partnerAddress = new PartnerAddress();
//		
//		if(goodsApply == null)
//			goodsApply = new GoodsApply();
//		
//		 goodsApply.setShip_addr(partnerAddress.getAddr());
//		 goodsApply.setShip_tel(partnerAddress.getMobile());
//		 
//		 goodsApply.setZip(partnerAddress.getZip());
//		 
//		if(partnerAddress!=null){
//			goodsApply.setShip_name(partnerAddress.getName());
//		}
//		
//		
//		 //==========================获取商品信息
//		 goodsid =cardManager.getGoodsIdByCardId(returned_ids.split(",")[0]);
//		  
//		 goodsApply.setUserid(ManagerUtils.getUserId());
//		 goodsApply.setGoods_id(goodsid);
//		 
//	
//		 goodsApply.setReturned_ids(returned_ids);
//				
//		 if(ManagerUtils.isFirstPartner()){
//			 goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_ONE);
//			 goodsApply.setSource_from(OrderStatus.SOURCE_FROM_AGENT_ONE);
//		 }
//		 if(ManagerUtils.isSecondPartner()){
//			 goodsApply.setShip_id(Consts.SHIP_ID_PARTNER_TWO);
//			 goodsApply.setSource_from(OrderStatus.SOURCE_FROM_AGENT_TOW);
//		 }
//		 
//		 
//		 goodsApply.setOrder_type(OrderStatus.ORDER_TYPE_2);
//				
//		 if(ManagerUtils.isNetStaff())
//			 	throw new RuntimeException("电信员工不允许申请商品");
//			 
//		 goods = goodsManager.getGoods(goodsid);
//		 goodsType = goodsTypeManager.get(goods.getType_id());
//				
//		 orderDirector.getOrderBuilder().buildOrderFlow();
//		 OrderRequst orderRequst = new OrderRequst();
//		 orderRequst.setService_name(goodsManager.getGoodsTypeByGoodsId(goodsid).getType_code());
//		 orderRequst.setFlow_name(OrderBuilder.COLLECT);
//		 orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
//		 orderRequst.setGoodsApply(goodsApply);
//		 orderDirector.perform(orderRequst);
//
//	}
//	
//
//	public String getGoodsid() {
//		return goodsid;
//	}
//
//	public void setGoodsid(String goodsid) {
//		this.goodsid = goodsid;
//	}
//
//	public GoodsApply getGoodsApply() {
//		return goodsApply;
//	}
//
//	public void setGoodsApply(GoodsApply goodsApply) {
//		this.goodsApply = goodsApply;
//	}
//
//	public IGoodsManager getGoodsManager() {
//		return goodsManager;
//	}
//
//	public void setGoodsManager(IGoodsManager goodsManager) {
//		this.goodsManager = goodsManager;
//	}
//
//	public IProductManager getProductManager() {
//		return productManager;
//	}
//
//	public void setProductManager(IProductManager productManager) {
//		this.productManager = productManager;
//	}
//
//	public IGoodsTypeManager getGoodsTypeManager() {
//		return goodsTypeManager;
//	}
//
//	public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager) {
//		this.goodsTypeManager = goodsTypeManager;
//	}
//
//	public GoodsAudit getGoodsAudit() {
//		return goodsAudit;
//	}
//
//	public void setGoodsAudit(GoodsAudit goodsAudit) {
//		this.goodsAudit = goodsAudit;
//	}
//
//	public GoodsTypeDTO getGoodsType() {
//		return goodsType;
//	}
//
//	public void setGoodsType(GoodsTypeDTO goodsType) {
//		this.goodsType = goodsType;
//	}
//
//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//
//	public Goods getGoods() {
//		return goods;
//	}
//
//	public void setGoods(Goods goods) {
//		this.goods = goods;
//	}
//
//	public String getAddressNum() {
//		return addressNum;
//	}
//
//	public void setAddressNum(String addressNum) {
//		this.addressNum = addressNum;
//	}
//
//	public IOrderDirector getOrderDirector() {
//		return orderDirector;
//	}
//
//	public void setOrderDirector(IOrderDirector orderDirector) {
//		this.orderDirector = orderDirector;
//	}
//
//
//	public PartnerInf getPartnerServ() {
//		return partnerServ;
//	}
//	public void setPartnerServ(PartnerInf partnerServ) {
//		this.partnerServ = partnerServ;
//	}
//	public PartnerAddress getPartnerAddress() {
//		return partnerAddress;
//	}
//
//	public void setPartnerAddress(PartnerAddress partnerAddress) {
//		this.partnerAddress = partnerAddress;
//	}
//
//	public String getReturned_ids() {
//		return returned_ids;
//	}
//
//	public void setReturned_ids(String returned_ids) {
//		this.returned_ids = returned_ids;
//	}
//
//	public ICardManager getCardManager() {
//		return cardManager;
//	}
//
//	public void setCardManager(ICardManager cardManager) {
//		this.cardManager = cardManager;
//	}
//
//	public String getCards_ids() {
//		return cards_ids;
//	}
//
//	public void setCards_ids(String cards_ids) {
//		this.cards_ids = cards_ids;
//	}

	
}
