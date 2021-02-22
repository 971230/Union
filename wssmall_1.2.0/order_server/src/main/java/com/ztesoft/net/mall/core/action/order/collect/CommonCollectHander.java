package com.ztesoft.net.mall.core.action.order.collect;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.AbstractHander;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.*;
import com.ztesoft.net.mall.core.model.support.GoodsTypeDTO;
import commons.CommonTools;

import params.goodstype.req.GoodsTypeReq;
import services.GoodsTypeInf;

import java.util.List;


public  class CommonCollectHander  extends AbstractHander implements ICollectHander {

	private GoodsTypeInf goodsTypeServ;
	/**
	 * 订单采集
	 *shop/admin/collect!addOrder
	 * 1、商品申请入口，调用地址为
	 */
	@Override
	public void collect() {
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		addOrder();
	}
	
	public void addOrder(){
		GoodsApply goodsApply = getOrderRequst().getGoodsApply();
		OrderOuter orderOuter = getOrderRequst().getOrderOuter();
		
		CardInfRequest cardInfRequest = getOrderRequst().getCardInfRequest();
		
		//产品信息
		Goods  goods = goodsServ.getGoods(goodsApply.getGoods_id()+ "");
		Product product = this.proudctServ.getByGoodsId(goodsApply.getGoods_id()+ "");
		
		//购物车信息
		
		
		Cart cart = new Cart();
		
		cart.setNum(goodsApply.getGoods_num());
		cart.setName(goods.getName());
		cart.setSession_id(CommonTools.getUserSessionId());
		cart.setProduct_id(product.getProduct_id());
		cart.setItemtype(OrderStatus.ITEM_TYPE_0); //
		cart.setWeight(product.getWeight());
		cart.setPrice(goodsApply.getSales_price());
		cart.setName(product.getName());
		//ThreadContextHolder.getOrderParams().setCart(cart);
		cartManager.add(cart);
		
		Order order =new Order();//getOrderResult().getOrder();
		order.setOrder_id("");
		order.setTransaction_id("");
		
		//收货人地址信息
		order.setShip_addr(goodsApply.getShip_addr());
		order.setShip_day(goodsApply.getShip_day());
		order.setShip_name(goodsApply.getShip_name());
		order.setShip_tel(goodsApply.getShip_tel());
		order.setShip_mobile(goodsApply.getShip_mobile()); // 收货人手机
		order.setShipping_id(goodsApply.getShip_id()); //缺省配置的配送物流地址
		order.setShip_zip(goodsApply.getZip());
		order.setUserid(goodsApply.getUserid()); //
		order.setSource_from(goodsApply.getSource_from()); //
		order.setOrder_type(goodsApply.getOrder_type());
		order.setGoods_num(goodsApply.getGoods_num());
		order.setGoods_id(goodsApply.getGoods_id()); //信息冗余，方便统计处理
		order.setPayment_id(new Integer(goodsApply.getPayment_id()).intValue()); //默认为货到付款
		
		if (!StringUtil.isEmpty(goodsApply.getLan_id())) {
			order.setLan_id(goodsApply.getLan_id());  // 页面下拉框选择本地网
		}

		if(!StringUtil.isEmpty(goodsApply.getType_code()))
			order.setType_code(goodsApply.getType_code());
		else{
			GoodsTypeReq goodsTypeReq = new GoodsTypeReq();
			goodsTypeReq.setGoods_id(goodsApply.getGoods_id());
			GoodsTypeDTO goodsType = goodsTypeServ.getGoodsType(goodsTypeReq).getGoodsTypeDTO();
			order.setType_code(goodsType.getType_code());
		}
		String order_id  = this.baseDaoSupport.getSequences("s_es_order", "3", 18);
		if(!StringUtil.isEmpty(orderOuter.getOrder_id()))
			order_id  = orderOuter.getOrder_id();
		
		order.setOrder_id(order_id);
		//充值订单记录订单流水号
		if(cardInfRequest !=null && !StringUtil.isEmpty(cardInfRequest.getOrdeSeq()))
			order.setTransaction_id(cardInfRequest.getOrdeSeq());
		
		//充值号码
		if(cardInfRequest != null && !StringUtil.isEmpty(cardInfRequest.getAccNbr())){
			order.setAcc_nbr(cardInfRequest.getAccNbr());
		}
		
		//对账标识
		if(!StringUtil.isEmpty(goodsApply.getBill_flag())){
			order.setBill_flag(goodsApply.getBill_flag());
		}
		
		//订单本地网处理
		if(StringUtil.isEmpty(order.getLan_id())){
			if(orderOuter !=null && !StringUtil.isEmpty(orderOuter.getLan_id())){
				order.setLan_id(orderOuter.getLan_id());
			}
			
			if(cardInfRequest !=null && !StringUtil.isEmpty(cardInfRequest.getLan_id())){
				order.setLan_id(cardInfRequest.getLan_id());
			}
			
			
		}
		
		
		orderManager.addOrder(order,goodsApply,orderOuter,cart.getSession_id());
		
		getOrderResult().setOrder(orderManager.get(order_id));;
		getOrderRequst().setOrderId(order_id); //设置订单id
		
	}
	
	/**
	 * 外系统订单同步到分销平台
	 */
	public void syOrder(){
		
		OrderOuter orderOuter = getOrderRequst().getOrderOuter();
		String a_order_id ="";
		String z_order_id = "";
		
		//旧订单编号不为空，且是换货、退货、退费申请单,生成订单关系
		
		if(!StringUtil.isEmpty(orderOuter.getOld_sec_order_id())
				&& (OrderStatus.ORDER_TYPE_2.equals(orderOuter.getOrder_type()) 
						||OrderStatus.ORDER_TYPE_3.equals(orderOuter.getOrder_type()) 
						|| OrderStatus.ORDER_TYPE_4.equals(orderOuter.getOrder_type()))){
			
				//获取分销平台的订单id
				String order_id = orderNFlowManager.getOrderOuterOrderIdByOldOrderId(orderOuter.getOld_sec_order_id()); //获取原单
				String source_order_id = orderNFlowManager.getAOrderId(order_id, OrderStatus.ORDER_TYPE_1);
				a_order_id = source_order_id;
				z_order_id = orderOuter.getOrder_id();
				setCanNext(false);//不允许下一步
				
		}else{
			
			//订单入库
			addOrder();
			
			//插入日志
			super.addLog("外系统订单同步");
			
			//外系统订单，更新号码为预占用
			if(!StringUtil.isEmpty(orderOuter.getAcc_nbr())){
				AccNbr accNbr = accNbrManager.getAccNbr(orderOuter.getAcc_nbr());
				if(accNbr !=null){
					accNbr.setState(Consts.ACC_NBR_STATE_1);
					accNbr.setState_date(DBTUtil.current());
					accNbr.setSec_order_id(getOrderRequst().getOrderId());
					accNbrManager.updateAccNbr(accNbr);
				}
			}
			a_order_id =getOrder().getOrder_id();
			z_order_id = orderOuter.getOrder_id();
			
			
		}
	
		
		//插入订单关系
		OrderRel orderRel  = new OrderRel();
		orderRel.setA_order_id(a_order_id);
		orderRel.setZ_order_id(z_order_id);
		orderRel.setCreate_date(DBTUtil.current());
		orderRel.setZ_table_name("ES_ORDER_OUTER");
		orderRel.setRel_type(orderOuter.getOrder_type());
		orderRel.setState(OrderStatus.ORDER_REL_STATE_0);
		orderRel.setComments("订单同步"); //
		orderNFlowManager.saveOrderRel(orderRel);
		
		//合约机默认发起申请处理
		if(OrderBuilder.CONTRACT_KEY.equals(getOrder().getType_code()) && OrderStatus.ORDER_TYPE_1.equals(getOrder().getOrder_type())){
			addAutoAuditApply("合约机订购申请",OrderStatus.AUDIT_TYPE_00A);
		}
	}

	
	//自动发起合约机申请
	public void addAutoAuditApply(String message,String audit_type) {
		
		OrderAuditRequest orderAuditRequest =new OrderAuditRequest();
		orderAuditRequest.setOrder_id(getOrder().getOrder_id());
		orderAuditRequest.setFrom_userid(getOrder().getUserid());
		orderAuditRequest.setCreate_date(DBTUtil.current());
		if(isFirstPartner())//一级分销商申请，直接修改状态为电信人员审核
			orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_2); 
		else
			orderAuditRequest.setState(OrderStatus.ORDER_AUDIT_STATE_0);
		orderAuditRequest.setSequ(0);
		orderAuditRequest.setApply_message(message);
		orderAuditRequest.setAudit_type(audit_type);
		orderAuditManager.apply(orderAuditRequest);
	}
	
	//淘宝充值订单
	/**
	 * 淘宝调用流量卡充值接口
	 */
	public void taobaoRechargeOrder(){
		
		//订单入库
		addOrder();
		
		//插入日志
		super.addLog("淘宝流量卡充值订单");
//		//插入订单关系
//		OrderRel orderRel  = new OrderRel();
//		orderRel.setA_order_id(getOrder().getOrder_id());
//		orderRel.setZ_order_id(getOrderRequst().getRateInfRequest().getOrdeSeq());
//		orderRel.setCreate_date(Consts.SYSDATE);
//		orderRel.setZ_table_name("ES_ORDER_OUTER");
//		orderRel.setRel_type(OrderStatus.ORDER_TYPE_1);
//		orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
//		orderRel.setComments("淘宝流量卡订单"); //
//		orderNFlowManager.saveOrderRel(orderRel);
		
	}

	
	/**
	 * 淘宝调用充值卡充值接口
	 */
	@SuppressWarnings("unchecked")
	public void taobaoCardOrder(){
		CardInfRequest cardInfRequest = getOrderRequst().getCardInfRequest();
		
		
		//判断是否已经插入订单记录,已经插入不处理
		if(StringUtil.isEmpty(cardInfRequest.getOrdeSeq()))
			throw new RuntimeException("请求流水号不能为空！");
		List ords = orderUtils.queryOrderByTransaction_id(cardInfRequest.getOrdeSeq());
		
		if(!ListUtil.isEmpty(ords))
		{
			
			//通知门户库存不足，短息提醒
			getOrderResult().setCode(Consts.CODE_FAIL);
			getOrderResult().setMessage("正在充值，请稍后再试");
			
			setCanNext(false);
			return;
		}
		
		//订单入库
		addOrder();
		
		
//		//插入日志
//		super.addLog("淘宝充值订单");
//		//插入订单关系
//		OrderRel orderRel  = new OrderRel();
//		orderRel.setA_order_id(getOrder().getOrder_id());
//		orderRel.setZ_order_id(getOrderRequst().getCardInfRequest().getOrdeSeq());
//		orderRel.setCreate_date(Consts.SYSDATE);
//		orderRel.setZ_table_name("ES_ORDER_OUTER");
//		orderRel.setRel_type(OrderStatus.ORDER_TYPE_1);
//		orderRel.setState(OrderStatus.ORDER_REL_STATE_1);
//		orderRel.setComments("淘宝充值卡订单"); //
//		orderNFlowManager.saveOrderRel(orderRel);
//		
		
	}
	

	@Override
	public boolean isCanExecute() {
		return this.getOrderRequst().isCan_execute();
	}

	public GoodsTypeInf getGoodsTypeServ() {
		return goodsTypeServ;
	}

	public void setGoodsTypeServ(GoodsTypeInf goodsTypeServ) {
		this.goodsTypeServ = goodsTypeServ;
	}
	

}