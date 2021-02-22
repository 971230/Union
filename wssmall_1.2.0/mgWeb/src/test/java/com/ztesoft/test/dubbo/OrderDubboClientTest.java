package com.ztesoft.test.dubbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import params.ZteResponse;
import params.cart.req.CartAddReq;
import params.cart.req.CartListReq;
import params.cart.resp.CartAddResp;
import params.cart.resp.CartListResp;
import params.coqueue.req.CoQueueAddReq;
import params.coqueue.resp.CoQueueAddResp;
import params.member.req.OrderListReq;
import params.member.resp.OrderListResp;
import params.order.req.OrderTotalReq;
import params.order.resp.OrderTotalResp;
import zte.params.cart.req.CartDeleteReq;
import zte.params.cart.resp.CartDeleteResp;
import zte.params.order.req.CartBarPriceTotalReq;
import zte.params.order.req.CartUpdateReq;
import zte.params.order.req.DeliveryFlowListReq;
import zte.params.order.req.DlyTypeAddressListReq;
import zte.params.order.req.DlyTypeListReq;
import zte.params.order.req.DlyTypePriceListReq;
import zte.params.order.req.GnotifyAddReq;
import zte.params.order.req.GnotifyDeleteReq;
import zte.params.order.req.GnotifyPageListReq;
import zte.params.order.req.GoodsCartItemListReq;
import zte.params.order.req.MemberReturnedOrderListReq;
import zte.params.order.req.OrderAddReq;
import zte.params.order.req.OrderApplyAddReq;
import zte.params.order.req.OrderApplyPageListReq;
import zte.params.order.req.OrderConfirmReq;
import zte.params.order.req.OrderDeliveryListReq;
import zte.params.order.req.OrderExceptionReq;
import zte.params.order.req.OrderGiftListReq;
import zte.params.order.req.OrderItemGetReq;
import zte.params.order.req.OrderMetaListReq;
import zte.params.order.req.OrderPageListReq;
import zte.params.order.req.OrderPriceEditReq;
import zte.params.order.req.OrderShipReq;
import zte.params.order.req.OrderStatusEditReq;
import zte.params.order.req.OrderStockingReq;
import zte.params.order.resp.CartBarPriceTotalResp;
import zte.params.order.resp.CartUpdateResp;
import zte.params.order.resp.DeliveryFlowListResp;
import zte.params.order.resp.DlyTypeAddressListResp;
import zte.params.order.resp.DlyTypeListResp;
import zte.params.order.resp.DlyTypePriceListResp;
import zte.params.order.resp.GnotifyAddResp;
import zte.params.order.resp.GnotifyDeleteResp;
import zte.params.order.resp.GnotifyPageListResp;
import zte.params.order.resp.GoodsCartItemListResp;
import zte.params.order.resp.MemberReturnedOrderListResp;
import zte.params.order.resp.OrderAddResp;
import zte.params.order.resp.OrderApplyAddResp;
import zte.params.order.resp.OrderApplyPageListResp;
import zte.params.order.resp.OrderConfirmResp;
import zte.params.order.resp.OrderDeliveryListResp;
import zte.params.order.resp.OrderExceptionResp;
import zte.params.order.resp.OrderGiftListResp;
import zte.params.order.resp.OrderItemGetResp;
import zte.params.order.resp.OrderMetaListResp;
import zte.params.order.resp.OrderPageListResp;
import zte.params.order.resp.OrderShipResp;
import zte.params.order.resp.OrderStatusEditResp;
import zte.params.order.resp.OrderStockingResp;

import com.ztesoft.api.ZteClient;
import com.ztesoft.net.mall.core.model.OrderApply;
import com.ztesoft.net.mall.core.model.OrderApplyItem;
import com.ztesoft.net.mall.core.model.OrderExcepCollect;
import com.ztesoft.net.mall.core.model.OrderQueryParam;

import zte.net.iservice.params.user.req.UserLoginReq;
import zte.net.iservice.params.user.resp.UserLoginResp;

import com.ztesoft.test.dubbo.base.DubboClientTest;

import commons.CommonTools;

public class OrderDubboClientTest extends DubboClientTest{
	private static Logger logger = Logger.getLogger(OrderDubboClientTest.class);
	/*
	 * 查询配送方式 ------------成功---------
	 */
	@Test(enabled=false)
	public void queryOrder(){
		logger.info("查询配送方式");
		DlyTypeAddressListReq dlyTypeAddressListReq=new DlyTypeAddressListReq();
		dlyTypeAddressListReq.setRegion_id("650201");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(dlyTypeAddressListReq, DlyTypeAddressListResp.class);		
		logger.info("result:"+response.isResult());		
		if("0".equals(response.getError_code())){
			DlyTypeAddressListResp dlyTypeAddressListResp=(DlyTypeAddressListResp)response;
			logger.info(dlyTypeAddressListResp.getDlyTypeAddressList().size());
		}
		//预期值的判定（"实际值","预期值"）
		Assert.assertEquals(response.getError_code(), "0");
	}
	
	/*
	 * 修改订单金额   -------------成功------------
	 */
	@SuppressWarnings("unchecked")
	@Test(enabled=false)
	public void editOrder(){
		OrderPriceEditReq orderPriceEditReq=new OrderPriceEditReq();
		orderPriceEditReq.setOrder_id("201403216423131673");
		orderPriceEditReq.setOrder_price(-1);
		orderPriceEditReq.setShip_price(0);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderPriceEditReq, OrderStatusEditResp.class);
		logger.info("result====="+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 订单收货确认  -------------成功------------
	 */
	@SuppressWarnings("unchecked")
	@Test(enabled=false)
	public void confirmOrder(){
		OrderConfirmReq orderConfirmReq=new OrderConfirmReq();
		orderConfirmReq.setConfirm_userid("1");
		orderConfirmReq.setConfirm_username("admin");
		orderConfirmReq.setOrder_id("201403286834150427");
		ZteClient client =getDubboZteClient();
		ZteResponse response=client.execute(orderConfirmReq, OrderConfirmResp.class);
		logger.info("订单收货确认结果----》"+response.isResult());
		Assert.assertEquals(response.getError_code(),"0");
	}
		/* 成功*/ 
	
	@Test(enabled=false)
	public void addOrder(){
		OrderAddReq orderAddReq = new OrderAddReq();
		List<Map> paramsl =new ArrayList<Map>();
		Map param = new HashMap();
		param.put("product_id", "201402246578001832");
		param.put("goods_num", "1");
		param.put("name", "吴辉");
		param.put("app_code", "test");
		param.put("acc_nbr", "13318717285");
		paramsl.add(param);
		orderAddReq.setService_code("3");
		orderAddReq.setParamsl(paramsl);
		ZteClient client=getDubboZteClient();
        ZteResponse resp = client.execute(orderAddReq, OrderAddResp.class);
       
        logger.info("mesg:"+resp.getError_msg());
        logger.info("此次调用结果:"+resp.isResult());
        Assert.assertEquals(resp.getError_code(), "0");
	
	}
	/*
	 * 修改订单状态 --------------成功------------
	 */
	@Test(enabled=false)
	@SuppressWarnings("unchecked")
	public void editOrderStatus(){
		OrderStatusEditReq orderStatusEditReq=new OrderStatusEditReq();
		orderStatusEditReq.setOrder_id("0000XX");
		orderStatusEditReq.setOrder_status("1");
		orderStatusEditReq.setPay_status("1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderStatusEditReq, OrderStatusEditResp.class);
		logger.info("修改订单状态----"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 分页查询订单   -----------有问题----------
	 */
	@Test(enabled=false)
	public void queryOrderForPage(){
		OrderPageListReq orderPageListReq=new OrderPageListReq();
		OrderQueryParam orderQueryParam=new  OrderQueryParam();
		orderQueryParam.setUserid("1");
		//orderPageListReq.setPageNo(1);
		//orderPageListReq.setPageSize(10);
		orderPageListReq.setOrderQueryParam(orderQueryParam);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderPageListReq, OrderPageListResp.class);
		logger.info("分页查询--"+response.isResult());
		if("0".equals(response.getError_code())){
			OrderPageListResp orderPageListResp=(OrderPageListResp)response;
			logger.info(orderPageListResp.getWebPage().getResult());
		}
		// Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 用户登录，得到sessionid
	 */
	@Test(enabled=false)
	public void userLogin(){
		UserLoginReq userLoginReq=new UserLoginReq();
		userLoginReq.setUserName("admin");
		userLoginReq.setUerPwd("123");
		userLoginReq.setProduct_id("201403024579001887");
		userLoginReq.setService_code("3");
		userLoginReq.setUserSessionId(CommonTools.getUserSessionId());
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(userLoginReq, UserLoginResp.class);
		logger.info("异常订单返回---"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 订单发货-----------成功----------
	 */
	@Test(enabled=true)
	public void shipOrder(){
		OrderShipReq orderShipReq=new OrderShipReq();
		orderShipReq.setOrder_id("201404021184152860");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderShipReq, OrderShipResp.class);
		logger.info("订单发货--"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 订单备货--------------成功---------------
	 */
	@Test(enabled=false)
	public void stokingOrder(){
		OrderStockingReq orderStockingReq=new OrderStockingReq();
		orderStockingReq.setOrder_id("201403286834150427");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderStockingReq, OrderStockingResp.class);
		logger.info("订单备货--"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 添加购物测 ----成功------------------
	 */
	@Test(enabled=false)
	public void addCat(){
		CartAddReq cartAddReq=new CartAddReq();
		cartAddReq.setProduct_id("201403024579001887");
		cartAddReq.setNum("4");
		cartAddReq.setMember_id("lv01212");
		cartAddReq.setMember_lv_id("1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(cartAddReq, CartAddResp.class);
		logger.info("加入购物车--"+response.isResult());
		logger.info("mesg:"+response.getError_msg());
		if("0".equals(response.getError_code())){
			CartAddResp cartAddResp=(CartAddResp)response;
			logger.info(cartAddResp.getCart().getCart_id());
		}
		Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 查询购物车---------------成功--------------------
	 */
	@Test(enabled=false)
	public void listCart(){
		CartListReq cartListReq=new CartListReq();
		cartListReq.setMember_id("201309261559000087");
		cartListReq.setStaff_no("090909");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(cartListReq, CartListResp.class);
		if("0".equals(response.getError_code())){
			CartListResp cartListResp=(CartListResp)response;
			logger.info("查询购物车=="+cartListResp.getGiftItemList().size());
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 删除购物车 ---------------成功----------------
	 */
	@Test(enabled=false)
	public void deleteCart(){
		CartDeleteReq cartDeleteReq=new CartDeleteReq();
		cartDeleteReq.setClean(true);
		cartDeleteReq.setCart_ids(new String[]{"201403259643054319,201403251554054318"});
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(cartDeleteReq, CartDeleteResp.class);
		logger.info("dlete--"+response.isResult());
		logger.info("mesg:"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
//	 * ----------------------暂缓---得不到table_name---------------------
//	 */
//	@Test(enabled=false)
//	public void saveTplInst(){
//		AcceptRuleReq acceptRuleReq=new AcceptRuleReq();
//		List<AcceptVo> acceptVos =new ArrayList<AcceptVo>();
//		Order order=new Order();
//		order.setOrder_id("201403259264133656");
//		order.setSn("20140326052138");
//		order.setMember_id("201309261559000003");
//		order.setStatus(2);
//		acceptRuleReq.setAcceptVos(acceptVos);
//		acceptRuleReq.setOrder(order);
//		ZteClient client=getDubboZteClient();
//		ZteResponse response=client.execute(acceptRuleReq, AcceptRuleResp.class);
//		logger.info("异常订单返回---"+response.getError_msg());
//		 Assert.assertEquals(response.getError_code(), "0");
//	}
//	
	
	//-----------------成功-------------
	@Test(enabled=false)
	public void saveOrderFail(){
		OrderExceptionReq orderExceptionReq=new OrderExceptionReq();
		OrderExcepCollect orderExcepCollect=new OrderExcepCollect();
		orderExcepCollect.setOrder_id("201403215710131679");
		orderExcepCollect.setException_id("201403052284000101");
		orderExceptionReq.setOrderExcepCollect(orderExcepCollect);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderExceptionReq, OrderExceptionResp.class);
		logger.info("异常订单返回---"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
//	/*
//	 * 执行调用规则  ----------找不到服务，不用对外开放------------
//	 */
//	@Test(enabled=false)
//	public void processrule(){
//		RuleParams ruleParams=new RuleParams();
//		ruleParams.setGoods_id("2759");
//		ruleParams.setMethod_name("name");
//		ruleParams.setRemote_type("2");
//		ruleParams.setRule_type("3");
//		ZteClient client=getDubboZteClient();
//		ZteResponse response=client.execute(ruleParams, ZteResponse.class);
//		logger.info("执行调用规则。。。。"+response.isResult());
//		 Assert.assertEquals(response.getError_code(), "0");
//		
//	}
	/*
	 * 查询配送类型------------成功--------------
	 */
	@Test(enabled=false)
	public void listDlyType(){
		DlyTypeListReq dlyTypeListReq=new DlyTypeListReq();
		dlyTypeListReq.setRegion_id("650201");//区、县ID
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(dlyTypeListReq, DlyTypeListResp.class);
		logger.info("result==="+response.isResult());
		if("0".equals(response.getError_code())){
			DlyTypeListResp dlyTypeListResp=(DlyTypeListResp)response;
			logger.info(dlyTypeListResp.getDlyTypeList().size());
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 查询订单礼品 ---------------------成功-----------------
	 */
	@Test(enabled=false)
	public void listOrderGift(){
		OrderGiftListReq orderGiftListReq=new OrderGiftListReq();
		orderGiftListReq.setMember_lv_id("0");//会员等级Id
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderGiftListReq, OrderGiftListResp.class);
		logger.info("查询订单赠品=="+response.isResult());
		if("0".equals(response.getError_code())){
			OrderGiftListResp orderGiftListResp=(OrderGiftListResp)response;
			logger.info(orderGiftListResp.getGiftList().size());
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//----------------成功-----------------------
	@Test(enabled=false)
	public void listGoodsCartItem(){
		GoodsCartItemListReq goodsCartItemListReq=new GoodsCartItemListReq();
		goodsCartItemListReq.setUserSessionId("");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(goodsCartItemListReq, GoodsCartItemListResp.class);
		if("0".equals(response.getError_code())){
			GoodsCartItemListResp goodsCartItemListResp=(GoodsCartItemListResp)response;
			logger.info(goodsCartItemListResp.getItemList().size());
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//---------------------成功--------计算购物车里面订单总价格-------
	@Test(enabled=false)
	public void cartOrderTotalPrice(){
		OrderTotalReq orderTotalReq=new OrderTotalReq();
		orderTotalReq.setMember_id("201309261559000086");
		orderTotalReq.setRegion_id("440106");
		orderTotalReq.setDlyType_id("1");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderTotalReq, OrderTotalResp.class);
		logger.info("result-====-"+response.isResult());
		if("0".equals(response.getError_code())){
			OrderTotalResp orderTotalResp=(OrderTotalResp)response;
			logger.info(orderTotalResp.getOrderPrice());
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//-----------------------------成功------------修改购物车数量------------
	@Test(enabled=false)
	public void updateCartNum(){
		CartUpdateReq cartUpdateReq=new CartUpdateReq();
		cartUpdateReq.setCart_id("201403207542054027");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(cartUpdateReq, CartUpdateResp.class);
		logger.info("修改购物车数量"+response.isResult());
		logger.info("msg--"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 得到购物车总价格------------------------成功-----------------
	 */
	@Test(enabled=false)
	public void getCartBarTotalPrice(){
		CartBarPriceTotalReq cartBarPriceTotalReq=new CartBarPriceTotalReq();
		cartBarPriceTotalReq.setCountChecked(true);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(cartBarPriceTotalReq, CartBarPriceTotalResp.class);
		logger.info("总价格==="+response.isResult());
		if("0".equals(response.getError_code())){
			CartBarPriceTotalResp cartBarPriceTotalResp=(CartBarPriceTotalResp)response;
			logger.info(cartBarPriceTotalResp.getPgkTotal());
		}
		logger.info("msg--"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	  //-----------------------成功--------------------
	@Test(enabled=false)
	public void listOrderMeta(){
		OrderMetaListReq orderMetaListReq=new OrderMetaListReq();
		orderMetaListReq.setOrder_id("201403215065131676");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderMetaListReq, OrderMetaListResp.class);
		logger.info("jieguo ==="+response.isResult());
		if("0".equals(response.getError_code())){
			OrderMetaListResp orderMetaListResp=(OrderMetaListResp)response;
			logger.info(orderMetaListResp.getMetaList().size());
		}
		logger.info("msg--"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//-----------------------成功--------------------
	@Test(enabled=false)
	public void listDlyTypePrice(){
		DlyTypePriceListReq dlyTypePriceListReq=new DlyTypePriceListReq();
		dlyTypePriceListReq.setGoodsprice(90);
		dlyTypePriceListReq.setRegionid("650201");
		dlyTypePriceListReq.setWeight(900);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(dlyTypePriceListReq, DlyTypePriceListResp.class);
		logger.info("---------"+response.isResult());
		if("0".equals(response.isResult())){
			DlyTypePriceListResp dlyTypePriceListResp=(DlyTypePriceListResp)response;
			logger.info(dlyTypePriceListResp.getDlyTypePriceList().size());
		}
		logger.info("---"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 查询物流列表-----------------------成功--------------------
	 */
	@Test(enabled=false)
	public void listDeliveryList(){
		DeliveryFlowListReq deliveryFlowListReq=new DeliveryFlowListReq();
		deliveryFlowListReq.setDelivery_id("201310181822000649");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(deliveryFlowListReq, DeliveryFlowListResp.class);
		logger.info("==="+response.isResult());
		if("0".equals(response.getError_code())){
			DeliveryFlowListResp orderMetaListResp=(DeliveryFlowListResp)response;
			logger.info(orderMetaListResp.getDeliveryFlow().size());
		}
		logger.info("---"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 订单物流列表------------成功--------------------
	 */
	@Test(enabled=false)
	public void listOrderDelivery(){
		OrderDeliveryListReq orderDeliveryListReq=new OrderDeliveryListReq();
		orderDeliveryListReq.setOrder_id("201310184656004548");
		orderDeliveryListReq.setType(1);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderDeliveryListReq, OrderDeliveryListResp.class);
		logger.info("吗----"+response.isResult());
		if("0".equals(response.getError_code())){
			OrderDeliveryListResp orderMetaListResp=(OrderDeliveryListResp)response;
			logger.info(orderMetaListResp.getDeliveryList().size());
		}
		logger.info("---"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * -----------成功-------------------
	 */
	@Test(enabled=false)
	public void listMemberReturnedOrders(){
		MemberReturnedOrderListReq memberReturnedOrderListReq=new MemberReturnedOrderListReq();
		memberReturnedOrderListReq.setMenmber_id("201403215569000421");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(memberReturnedOrderListReq, MemberReturnedOrderListResp.class);
		logger.info("===----"+response.isResult());
		if("0".equals(response.getError_code())){
			MemberReturnedOrderListResp MemberReturnedOrderListResp=(MemberReturnedOrderListResp)response;
			logger.info(MemberReturnedOrderListResp.getReturnedOrders().size());
		}
		logger.info("---"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 查询缺货---------------------成功---------和下面的login一块执行-----------------
	 */
	
	@Test(enabled=false)
	public void queryGnotifyForPage(){
		GnotifyPageListReq gnotifyPageListReq=new GnotifyPageListReq();
		gnotifyPageListReq.setUserSessionId("46260be2af964b0aa2e46128792f98af");
		gnotifyPageListReq.setPageNo(1);
		gnotifyPageListReq.setPageSize(50);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(gnotifyPageListReq, GnotifyPageListResp.class);
		logger.info("---"+response.getError_msg());
		if("0".equals(response.getError_code())){
			GnotifyPageListResp MemberReturnedOrderListResp=(GnotifyPageListResp)response;
			logger.info(MemberReturnedOrderListResp.getPage());
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
//	@Test(enabled=false)
//	public void MemberLogin(){
//		MemberLoginReq memberLoginReq=new MemberLoginReq();
//		memberLoginReq.setPwd("123");
//		memberLoginReq.setUserName("mk");
//		memberLoginReq.setUserSessionId(CommonTools.getUserSessionId());
//		ZteClient client=getDubboZteClient();
//		ZteResponse response=client.execute(memberLoginReq, MemberLoginResp.class);
//		logger.info("msgss---"+response.isResult());
//		logger.info("sessoion"+response.getUserSessionId());
//		 Assert.assertEquals(response.getError_code(), "0");
//	}
	/*
	 * 删除-------------------------成功-------------
	 */
	@Test(enabled=false)
	public void deleteGontify(){
		GnotifyDeleteReq gnotifyDeleteReq=new GnotifyDeleteReq();
		gnotifyDeleteReq.setGnotify_id("1111111111111");//缺货id
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(gnotifyDeleteReq, GnotifyDeleteResp.class);
		logger.info("缺货吗----"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * 添加------------成功-----------
	 */
	@Test(enabled=false)
	public void addGnotify(){
		GnotifyAddReq gnotifyAddReq=new GnotifyAddReq();
		gnotifyAddReq.setProduct_id("00oi");//产品ID
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(gnotifyAddReq, GnotifyAddResp.class);
		logger.info("0000"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//----------------成功---------------------
	@Test(enabled=false)
	public void queryOrderApplyForPage(){
		OrderApplyPageListReq orderApplyPageListReq=new OrderApplyPageListReq();
		orderApplyPageListReq.setService_type("3");
		orderApplyPageListReq.setPageNo(2);
		orderApplyPageListReq.setPageSize(50);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderApplyPageListReq, OrderApplyPageListResp.class);
		logger.info("0000"+response.isResult());
		if("0".equals(response.getError_code())){
			OrderApplyPageListResp orderApplyPageListResp=(OrderApplyPageListResp)response;
			logger.info(orderApplyPageListResp.getPage());
					
		}
		 Assert.assertEquals(response.getError_code(), "0");
	}
	/*
	 * ---------------------成功---------
	 */
	@Test(enabled=false)
	public void addOrderApply(){
		OrderApplyAddReq orderApplyAddReq=new OrderApplyAddReq();
		OrderApply orderApply =new OrderApply();
		List<OrderApplyItem> orderApplyItem=new ArrayList<OrderApplyItem>();
		orderApply.setOrder_apply_id("201402282131000125");
		orderApply.setMember_id("201308067087000085");
		orderApply.setService_type("2");
		orderApplyAddReq.setOrderApply(orderApply);
		orderApplyAddReq.setOrder_id("201403247436131847");
		orderApplyAddReq.setApplyItems(orderApplyItem);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderApplyAddReq, OrderApplyAddResp.class);
		logger.info("添加订单申请000"+response.isResult());
		logger.info("结果--"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//-------------成功-----------
	@Test(enabled=false)
	public void getOrderItem(){
		OrderItemGetReq OrderItemGetReq=new OrderItemGetReq();
		OrderItemGetReq.setItem_id("201403259789053298");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(OrderItemGetReq, OrderItemGetResp.class);
		logger.info("msg---"+response.isResult());
		if("0".equals(response.getError_code())){
			OrderItemGetResp derItemGetResp=(OrderItemGetResp)response;
			logger.info(derItemGetResp.getItem().size());
		}
		logger.info("结果--"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//------------成功-----------------------------
	@Test(enabled=false)
	public void add(){
		CoQueueAddReq coQueueAddReq=new CoQueueAddReq();
		coQueueAddReq.setCo_name("1121");
		coQueueAddReq.setAction_code("12");
		coQueueAddReq.setBatch_id("12");
		coQueueAddReq.setObject_id("23");
		coQueueAddReq.setObject_type("23");
		coQueueAddReq.setOper_id("1");
		coQueueAddReq.setContents("12第三方");
		coQueueAddReq.setService_code("CO_PINPAI");
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(coQueueAddReq, CoQueueAddResp.class);
		logger.info("msgss---"+response.isResult());
		logger.info("结果--"+response.getError_msg());
		 Assert.assertEquals(response.getError_code(), "0");
	}
	//--------------------不用了--不测-------------
//	@Test(enabled=false)
//	public void syncOrderOuter(){
//		OrderOuterSyncReq orderOuterSyncReq =new OrderOuterSyncReq();
//		orderOuterSyncReq.setBegin_time("2014-01-02");
//		orderOuterSyncReq.setEnd_time("2015-01-02");
//		orderOuterSyncReq.setPage_no(1);
//		orderOuterSyncReq.setPage_size(50);
//		
//		ZteClient client=getDubboZteClient();
//		ZteResponse response=client.execute(orderOuterSyncReq, OrderOuterSyncResp.class);
//		logger.info("msgss---"+response.isResult());
//		if("0".equals(response.getError_code())){
//			OrderOuterSyncResp derItemGetResp=(OrderOuterSyncResp)response;
//			logger.info(derItemGetResp.getOrderSyncList().size());
//		}
//		 Assert.assertEquals(response.getError_code(), "0");
//		
//	}
	
	/*
//	 *---------------不开放不用测--------------
//	 */
//	@Test(enabled=false)
//	public void addHttpData(){
//		HttpApiDateReq httpApiDateReq=new HttpApiDateReq();
//		HttpReqData httpReqData=new HttpReqData();
//		httpApiDateReq.setHttpReqData(httpReqData);
//		ZteClient client=getDubboZteClient();
//		ZteResponse response=client.execute(httpApiDateReq, OrderAddResp.class);
//		logger.info("msgss---"+response.isResult());
//		if("0".equals(response.getError_code())){
//			OrderAddResp orderAddResp=(OrderAddResp)response;
//			logger.info(orderAddResp.getOrderList().size());
//		}
//		 Assert.assertEquals(response.getError_code(), "0");
//	}
	//----------------------成功-----------------
	@Test(enabled=false)
	public void pageOrders(){
		OrderListReq orderListReq=new OrderListReq();//没有加注释
		orderListReq.setMember_id("201309261559000089");
		orderListReq.setPageNo(1);
		orderListReq.setPageSize(50);
		ZteClient client=getDubboZteClient();
		ZteResponse response=client.execute(orderListReq, OrderListResp.class);
		logger.info("msgss--f-"+response.isResult());
		 Assert.assertEquals(response.getError_code(), "0");
	}
}
