package com.ztesoft.net.rule;

import java.util.Map;

import javax.annotation.Resource;

import org.drools.core.util.StringUtils;

import params.member.req.RegisterReq;
import params.order.req.OrderSyReq;
import params.order.resp.OrderSyResp;
import services.GoodsInf;
import services.MemberCenterInf;
import services.OrderInf;
import services.OrderRuleInf;
import services.ProductInf;
import services.ServiceBase;
import util.OrderThreadLocalHolder;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Cart;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderRel;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderOuterManager;
import commons.CommonTools;

public abstract class AbsSyOrderRule extends ServiceBase {
    @Resource
    protected ProductInf proudctServ;

    @Resource
    protected GoodsInf goodsServ;


	public IOrderOuterManager orderOuterManager;
	public MemberCenterInf memberCenterServ;

	public ICartManager cartManager;
	public OrderInf orderServ;
	public IOrderNFlowManager orderNFlowManager;
	
	@Resource
    protected  OrderRuleInf orderRuleServ;
	
	public Cart assembleCart(OrderOuter orderOuter,Member member){
		
		// 加入购物车
		//Goods goods = goodsServ.getGoods(orderOuter.getGoods_id());
		Product product = proudctServ.get(orderOuter.getProduct_id());
		Cart cart = new Cart();
		cart.setItemtype(orderOuter.getItem_type());
		cart.setNum(Integer.valueOf(orderOuter.getGoods_num()));
		String name = null;
		if(product!=null)
			name = product.getName();
		if(!StringUtils.isEmpty(orderOuter.getGoods_name()))
			name = orderOuter.getGoods_name();
		cart.setName(name);
		cart.setSession_id(CommonTools.getUserSessionId());
		//cart.setSpec_id(orderOuter.getSpec_id());
		cart.setProduct_id(product.getProduct_id());
		//cart.setItemtype(OrderStatus.ITEM_TYPE_0); //
		cart.setWeight(product.getWeight());
		cart.setMember_lv_id(member.getLv_id());
		//如果传进来的价格为空侧以product的价格为准  mochunrun ========2015-01-03========================
		if(StringUtil.isEmpty(orderOuter.getOrder_amount())){
			cart.setPrice(product.getPrice());
		}else{
			cart.setPrice(Double.valueOf(orderOuter.getOrder_amount())/new Integer(orderOuter.getGoods_num()).intValue());
		}
		cart.setAddon(orderOuter.getAddon());
		cart.setSpec_id(orderOuter.getSpec_id()); //设置活动id
		//cart.setPrice(product.getPrice());
		//cart.setName(product.getName());
		if(!StringUtils.isEmpty(orderOuter.getMember_lv_id()))
			cart.setMember_lv_id(orderOuter.getMember_lv_id());
		return cart;
	}
	
	public RegisterReq assembleMember(OrderSyReq syReq) {
		OrderOuter orderOuter = syReq.getOrderOuterResps().get(0).getOrderOuter();
		Map map = null;
		if(syReq.getParamsl()!=null && syReq.getParamsl().size()>0){
			map = syReq.getParamsl().get(0);
		}
		AdminUser user = OrderThreadLocalHolder.getInstance().getOrderCommonData().getAdminUser();
		if(orderOuter.getUname()==null && user!=null){
			orderOuter.setUname(user.getUsername());
		}
		if(orderOuter.getName()==null && user!=null){
			orderOuter.setName(user.getUsername());
		}
		//生成会员
		String member_name =orderOuter.getUname();
		if(StringUtil.isEmpty(member_name))
			member_name = orderOuter.getName();
		if(map!=null && StringUtil.isEmpty(member_name)){
			member_name = (String) map.get("buyer_id");
		}
		if(StringUtil.isEmpty(member_name))
			member_name ="defmember";
		RegisterReq req = new RegisterReq();
		Member member = new Member();
		member.setMember_id(orderOuter.getMember_id());
		if(StringUtil.isEmpty(orderOuter.getName()) && map!=null)orderOuter.setName((String)map.get("buyer_name"));
		member.setName(orderOuter.getName());
		member.setUname(member_name);
		if(StringUtil.isEmpty(orderOuter.getShip_tel()) && map!=null)orderOuter.setShip_tel((String)map.get("reference_phone"));
		member.setTel(orderOuter.getShip_tel());
		if(StringUtil.isEmpty(orderOuter.getShip_mobile()) && map!=null)orderOuter.setShip_mobile((String)map.get("phone_num"));
		member.setMobile(orderOuter.getShip_mobile());
		member.setZip(orderOuter.getShip_zip());
		if(StringUtil.isEmpty(orderOuter.getRegion()) && map!=null)orderOuter.setRegion((String)map.get("district"));
		member.setRegion(orderOuter.getRegion());
		if(StringUtil.isEmpty(orderOuter.getCity()) && map!=null)orderOuter.setCity((String)map.get("city"));
		member.setCity(orderOuter.getCity());
		if(StringUtil.isEmpty(orderOuter.getProvince()) && map!=null)orderOuter.setProvince((String)map.get("province"));
		member.setProvince(orderOuter.getProvince());
		member.setSex(orderOuter.getSex());
		member.setAddress(orderOuter.getShip_addr());
		member.setSource_from(orderOuter.getSource_from());
		
		if(map!=null){
			String rid = (String) map.get("area_code");
			if(!StringUtil.isEmpty(rid))member.setRegion_id(Integer.valueOf(rid));
			String cid = (String) map.get("city_code");
			if(!StringUtil.isEmpty(cid))member.setCity_id(Integer.valueOf(cid));
			String pid = (String) map.get("provinc_code");
			if(!StringUtil.isEmpty(pid))member.setProvince_id(Integer.valueOf(pid));
			
			member.setBuyer_uid((String)map.get("uid"));
			member.setShip_area((String)map.get("ship_area"));
			member.setCert_card_num((String)map.get("cert_card_num"));
			member.setCert_address((String)map.get("cert_address"));
			member.setCert_failure_time((String)map.get("cert_failure_time"));
			member.setCert_type((String)map.get("certi_type"));
			member.setCustomertype((String)map.get("CustomerType"));
			member.setPlat_type((String)map.get("plat_type"));
		}
		
		req.setMember(member);
		return req;
	}

	public OrderRel assembleOrderRel(OrderOuter orderOuter,Order order){
		//插入订单关系
		OrderRel orderRel  = new OrderRel();
		orderRel.setA_order_id(orderOuter.getOrder_id());
		orderRel.setZ_order_id(order.getOrder_id());
		orderRel.setCreate_date(DBTUtil.current());
		orderRel.setZ_table_name("ES_ORDER_OUTER");
		orderRel.setRel_type("1");
		orderRel.setState(OrderStatus.ORDER_REL_STATE_0);
		orderRel.setComments("订单同步");
		return orderRel;
	}
	

	public abstract  OrderSyResp perform(OrderSyReq syReq) throws ApiBusiException;
	
	public OrderSyResp syNOrder(OrderSyReq syReq)throws ApiBusiException {
		return perform(syReq);
	}


	public IOrderOuterManager getOrderOuterManager() {
		return orderOuterManager;
	}

	public void setOrderOuterManager(IOrderOuterManager orderOuterManager) {
		this.orderOuterManager = orderOuterManager;
	}

	public MemberCenterInf getMemberCenterServ() {
		return memberCenterServ;
	}

	public void setMemberCenterServ(MemberCenterInf memberCenterServ) {
		this.memberCenterServ = memberCenterServ;
	}

	public ICartManager getCartManager() {
		return cartManager;
	}

	public void setCartManager(ICartManager cartManager) {
		this.cartManager = cartManager;
	}

	public OrderInf getOrderServ() {
		return orderServ;
	}

	public void setOrderServ(OrderInf orderServ) {
		this.orderServ = orderServ;
	}

	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}

	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
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

	public OrderRuleInf getOrderRuleServ() {
		return orderRuleServ;
	}

	public void setOrderRuleServ(OrderRuleInf orderRuleServ) {
		this.orderRuleServ = orderRuleServ;
	}


}
