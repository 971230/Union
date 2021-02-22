
package com.ztesoft.net.mall.core.service.impl;

import java.io.File;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.goods.sales.req.GoodsSalesReq;
import params.order.req.OrderExceptionCollectReq;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.order.req.OutcallLogsReq;
import params.paycfg.req.PaymentCfgReq;
import services.GoodsInf;
import services.PaymentCfgInf;
import services.PromotionInf;
import util.OrderThreadLocalHolder;
import utils.GlobalThreadLocalHolder;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.iservice.IGoodsService;
import zte.params.goods.req.GroupBuyEditReq;
import zte.params.goods.req.GroupBuyReq;
import zte.params.goods.req.LimitBuyReq;
import zte.params.goods.req.LimitBuyUpdateReq;
import zte.params.goods.resp.GroupBuyResp;
import zte.params.goods.resp.LimitBuyResp;
import zte.params.order.req.OrderExceptionLogsListReq;
import zte.params.order.resp.OrderAttrHanderResp;
import zte.params.store.req.InventoryReduceReq;
import zte.params.store.resp.InventoryReduceResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.cache.CacheList;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.eop.sdk.user.IUserService;
import com.ztesoft.net.eop.sdk.user.UserServiceFactory;
import com.ztesoft.net.eop.sdk.utils.DateUtil;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.DoubleMapper;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.framework.util.ExcelUtil;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.framework.util.ReflectionUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CardInfRequest;
import com.ztesoft.net.mall.core.model.Comments;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.model.ExtAttr;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.GroupBuy;
import com.ztesoft.net.mall.core.model.ItemCard;
import com.ztesoft.net.mall.core.model.ItemCardDef;
import com.ztesoft.net.mall.core.model.LimitBuy;
import com.ztesoft.net.mall.core.model.LimitBuyGoods;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderChange;
import com.ztesoft.net.mall.core.model.OrderExcepCollect;
import com.ztesoft.net.mall.core.model.OrderException;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.OrderLog;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.ReturnsOrder;
import com.ztesoft.net.mall.core.model.TestRule;
import com.ztesoft.net.mall.core.model.support.CartItem;
import com.ztesoft.net.mall.core.model.support.OrderPrice;
import com.ztesoft.net.mall.core.plugin.order.OrderPluginBundle;
import com.ztesoft.net.mall.core.service.ICartManager;
import com.ztesoft.net.mall.core.service.IDlyTypeManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.ITplInstManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicSQLInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.AttrTable;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.remote.inf.IWarehouseService;
import com.ztesoft.util.CacheUtils;

import commons.CommonTools;

/**
 * 订单管理
 * @author wui
 */

public class OrderManager extends   BaseSupport implements IOrderManager {
    @Resource
    private PromotionInf promotionServ;

	private ICartManager cartManager; 
	private IDlyTypeManager dlyTypeManager;
	//private IPaymentManager paymentManager;
	private PaymentCfgInf paymentCfgServ;
	private OrderPluginBundle orderPluginBundle;
	private ICacheUtil cacheUtil;
	private GoodsInf goodsServ;
	@Resource
	private IGoodsService goodServices;
	private IWarehouseService warehouseService;

	/**
	 * 按供货商提交订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-15 
	 * @param order
	 * @param sessionid
	 * @param staff_no
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public Order add(OrderOuter cp,Member member,Order order,String sessionid,String staff_no) {
//		logger.info(cp.getService_code()+"================servicecode==================");
		if(order==null) throw new RuntimeException("error: order is null");
		/**************************用户信息****************************/
		order.setQuery_user_id(staff_no);
		//非匿名购买
		if(member!=null){
			order.setMember_id(member.getMember_id());
		}
		/**************************计算价格、重量、积分****************************/
		boolean  isProtected = order.getIs_protect().compareTo(1)==0;
		OrderPrice orderPrice  =cartManager.countPrice(cp,member,sessionid, order.getShipping_id(),  ""+order.getRegionid(), isProtected, true,staff_no);
		
		order.setGoods_amount( orderPrice.getGoodsPrice());
		order.setWeight(orderPrice.getWeight());		
		order.setDiscount(orderPrice.getDiscountPrice());
		//设置原价钱
		if(!StringUtil.isEmpty(cp.getP_order_amount())){
			order.setOrder_amount(Double.valueOf(cp.getP_order_amount()));
		}else{
			order.setOrder_amount(orderPrice.getOrderPrice());
		}
		order.setProtect_price(orderPrice.getProtectPrice());
		Double ship_amount = null;
		if(!StringUtil.isEmpty(cp.getShip_amount())){
			ship_amount = new Double(cp.getShip_amount());
			order.setShipping_amount(ship_amount);
			if(!StringUtil.isEmpty(cp.getP_order_amount())){
				order.setOrder_amount(order.getOrder_amount()+ship_amount);//-orderPrice.getShippingPrice()
			}else{
				order.setOrder_amount(order.getOrder_amount()-orderPrice.getShippingPrice()+ship_amount);//
			}
		}else{
			//没传邮费
			if(!StringUtil.isEmpty(cp.getP_order_amount())){
				order.setOrder_amount(order.getOrder_amount()+orderPrice.getShippingPrice());
			}
			order.setShipping_amount(orderPrice.getShippingPrice());
		}
		order.setGainedpoint(orderPrice.getPoint());
		//配送方式名称
		if(order.getShipping_id()!=null && !"".equals(order.getShipping_id())){
			DlyType dlyType = dlyTypeManager.getDlyTypeById(order.getShipping_id());
			if(dlyType==null)  throw new RuntimeException("shipping not found count error");
			order.setShipping_type(dlyType.getName() );
		}
		
		/************支付方式价格及名称************************/
		PaymentCfgReq reqid = new PaymentCfgReq();
		reqid.setPayment_cfg_id(cp.getPayment_id()+"");
		PayCfg payCfg =paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
		//PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
		order.setPaymoney(0d);
		//PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
		//order.setPaymoney(this.paymentManager.countPayPrice(order.getOrder_id()));
		order.setPayment_name( payCfg.getName());
		order.setPayment_type(payCfg.getType());
		
		/************创建订单************************/
		order.setCreate_time(DBTUtil.current());
		order.setSn(this.createSn());
		order.setStatus(OrderStatus.ORDER_NOT_PAY);
		order.setDisabled(0);
		order.setPay_status(OrderStatus.PAY_NO);
		order.setShip_status(OrderStatus.SHIP_NO);	
		
		/************写入订单货物列表************************/
		List<CartItem> itemList  = this.cartManager.listGoods(member,sessionid,staff_no,cp.getCoupon());
		List<CartItem> checkedItemList = new ArrayList<CartItem>();
		for(CartItem item : itemList){
			if("0".equals(item.getIs_checked())){
				continue;
			}
			checkedItemList.add(item);
		}
		itemList = checkedItemList;
		boolean flag = false;
		boolean hasStore = false;
		String goods = "[";
		for(CartItem c:itemList){
			Pattern pl = Pattern.compile(c.getMember_lv_id()+",*|,*"+c.getMember_lv_id(), Pattern.CASE_INSENSITIVE);
			if(c.getMember_lv_id()!=null && !"".equals(c.getMember_lv_id()) && !pl.matcher(member.getLv_ids()).find()){
				goods += c.getName()+",";
				if(!flag)flag = true;
			}
			if(Const.MEMBER_LV_CHINA_TELECOM_DEP.equals(c.getMember_lv_id())){
				GoodsSalesReq goodsSalesReq = new GoodsSalesReq();
				goodsSalesReq.setGoods_id(c.getGoods_id());
				goodsSalesReq.setLan_code(order.getLan_id());
				int store = goodsServ.getGoodsStore(goodsSalesReq).getStores();
				if(store!=-1 && store<c.getNum()){
					goods += c.getName()+",";
					if(!hasStore)hasStore=true;
				}
			}
		}
		if(goods.length()>1){
			goods = goods.substring(0,goods.length()-1);
		}
		goods += "]";
		if(flag)throw new RuntimeException("购物车有非当前登录用户会员价购买的商品"+goods);
		if(hasStore)throw new RuntimeException("商品库存不足"+goods);
		
		if(!Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(cp.getService_code())){
			List pgkList  = this.cartManager.listPgk(member,sessionid,staff_no);
			
			/***************赠品货物列表**************************/
			List<CartItem> giftList  = cartManager.listGift(member,sessionid,staff_no);
			
			/***************团购购物车列表**************************/
			List<CartItem> groupList  = cartManager.listGroupBuy(member,sessionid, staff_no); //add by wui,开放团购
			
			/***************秒杀购物车列表**************************/
			List<CartItem> limitList  = cartManager.listLimitBuy(member,sessionid, staff_no); //add by wui,开放秒杀
			//捆绑
			itemList.addAll(pgkList);
			
			orderNumVali(groupList,limitList);
			
			//团购
			itemList.addAll(groupList);
			
			//秒杀
			itemList.addAll(limitList);
		}
		
		
		
		if(itemList.isEmpty()) throw new RuntimeException("创建订单失败，购物车为空");
		
		int gnum = 0;
		for(CartItem c:itemList){
			gnum+=c.getNum();
		}
		order.setGoods_num(gnum);
		if("true".equals(order.getCreaterOrder())){
			if("HB".equals(order.getSource_from())){
				Map orderMap = ReflectionUtil.po2Map(order);
				orderMap.put("col1", cp.getCol1());
				orderMap.put("col2", cp.getCol2());
				orderMap.put("col3", cp.getO_source_from());
				this.baseDaoSupport.insert("order", orderMap);
			}else{
				this.baseDaoSupport.insert("order", order);
			}
			
		}
		String orderId = order.getOrder_id();
		
		this.saveGoodsItem(itemList,null, orderId,order,new GoodsApply());
		
		try{
			//修改商品逻辑库存
			if(!StringUtils.isEmpty(cp.getHouse_id())){
				for(CartItem ci:itemList){
					InventoryReduceReq iReq = new InventoryReduceReq();
					//iReq.setOrder_id(cp.getOrg_id());
					iReq.setOrg_id(cp.getOrg_id());
					iReq.setGoods_id(ci.getGoods_id());
					iReq.setNum(ci.getNum());
					iReq.setHouse_id(cp.getHouse_id());
					InventoryReduceResp resp = warehouseService.inventoryReduce(iReq);
					//写消息队列
					/*CoQueueAddReq coreq = new CoQueueAddReq();
				    coreq.setCo_name("库存同步");
				    coreq.setBatch_id(order.getOrder_id());
				    coreq.setService_code(Consts.SERVICE_CODE_CO_SHANGPIN_KUCUN);
				    coreq.setAction_code("M");
				    coreq.setObject_type("SHANGPIN_KUCUN");
				    coreq.setObject_id(order.getOrder_id());
				    coreq.setOrg_id_str("");
				    coreq.setOper_id("-1");
					for(int j=0;j<3;j++){
						try{
							ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
							CoQueueAddResp coresp = client.execute(coreq, CoQueueAddResp.class);
							//CoQueueAddResp coresp = coQueueService.add(coreq);
							break ;
						}catch(Exception ex){
							ex.printStackTrace();
							continue ;
						}
					}*/
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		//if(giftList!=null && !giftList.isEmpty())
		//this.saveGiftItem(giftList, orderId);
		
		//if(!Const.ORDER_STAND_AUTO_SERVICE_CODE.equals(cp.getService_code())){
			/**
			 * 应用订单优惠，送出优惠卷及赠品，并记录订单优惠方案
			 */
			if(member!=null){
				this.promotionServ.applyOrderPmt(orderId, orderPrice.getOrderPrice(), member.getLv_id(),cp.getCoupon(),staff_no);
				List<Promotion> pmtList =  promotionServ.list(orderPrice.getOrderPrice(), member.getLv_id(),cp.getCoupon());
				for(Promotion pmt :pmtList){
					String sql = SF.orderSql("SERVICE_ORDER_PMT_INSERT");
					this.baseDaoSupport.execute(sql, pmt.getPmt_id(),orderId,pmt.getPmt_describe());
				}
				if(pmtList==null || pmtList.size()==0){
					CartItem ci = (itemList!=null) ? itemList.get(0):null;
					if(ci!=null){
						pmtList = promotionServ.list(ci.getGoods_id(), member.getLv_id(), cp.getCoupon(), staff_no);
						for(Promotion pmt :pmtList){
							String sql = SF.orderSql("SERVICE_ORDER_PMT_INSERT");
							this.baseDaoSupport.execute(sql, pmt.getPmt_id(),orderId,pmt.getPmt_describe());
						}
					}
				}
				
			}
		//}
		
		/************写入订单日志************************/
		OrderLog log = new OrderLog();
		log.setMessage("订单创建");
		log.setOp_name(member.getUname());
		log.setOrder_id(orderId);
		this.addLog(log);
		order.setOrder_id(orderId);
		/**
		 * 清空已结算的购物车
		 */
		if(itemList!=null && itemList.size()>0){
			String productids = "";
			for(CartItem c:itemList){
				productids += c.getProduct_id()+",";
			}
			if(productids.length()>1)
				productids = productids.substring(0,productids.length()-1);
			cartManager.clean(sessionid, true,productids);
		}
		
		cartManager.updateCheckedFlag(sessionid, 0l, 1);
		order.setOrderprice(orderPrice);
		this.orderPluginBundle.onCreate(order, sessionid);
		return order;
	}
	
	public void orderNumVali(List<CartItem> groupList,List<CartItem> limitList){
		//团购数量判断
		if(!groupList.isEmpty()){
			for (CartItem groupItem:groupList) {
					String spec_id =groupItem.getSpec_id();
					GroupBuyReq req = new GroupBuyReq();
					req.setGroupid(spec_id);
					req.setGoodsid(groupItem.getGoods_id());
					GroupBuyResp resp = goodServices.queryGroupBuy(req);
					GroupBuy groupBuy = resp.getGroupBuy();
					if(groupBuy !=null){
						
						int cart_num =groupItem.getNum(); //获取团购限制的数量
						int buy_num = groupBuy.getBuy_count(); //获取购物车数量
						int buyed_num = groupBuy.getBuyed_count(); //获取已购买数量
						if((cart_num+buyed_num)>buy_num){
							CommonTools.addFailError("已达团购的最大数量限制，团购失败！");
						}else{ //小于，则做更新动作
							GroupBuyEditReq editReq = new GroupBuyEditReq();
							groupBuy.setBuyed_count(cart_num+buyed_num);
							editReq.setGroupBuy(groupBuy);
							goodServices.updateGroupByCount(editReq);
						}
						
					}
			}
		}
		//秒杀数量判断
		if(!limitList.isEmpty()){
			for (CartItem cartItem:limitList) {
				String spec_id =cartItem.getSpec_id();
				LimitBuyReq req = new LimitBuyReq();
				req.setLimitbuyid(spec_id);
				LimitBuyResp resp = goodServices.queryLimitBuy(req);
				LimitBuy limitBuy = resp.getLimitBuy();
				List<LimitBuyGoods> limitBuyGoods = limitBuy.getLimitBuyGoodsList();
				if(!ListUtil.isEmpty(limitBuyGoods)){
					for (LimitBuyGoods buyGoods :limitBuyGoods) {
						if(buyGoods.getGoodsid().equals(cartItem.getGoods_id()))
						{
							int num =buyGoods.getNum(); //获取团购限制的数量
							int cart_num = cartItem.getNum(); //获取购物车数量
							int buyedNum = buyGoods.getBuy_num(); //获取已购买数量
							if((cart_num+buyedNum)>num){
								CommonTools.addFailError("已达秒杀的最大数量限制，秒杀失败！");
							}else{ //小于，则做更新动作
								LimitBuyUpdateReq limitBuyUpdateReq = new LimitBuyUpdateReq();
								buyGoods.setBuy_num(cart_num+buyedNum);
								limitBuyUpdateReq.setLimitBuyGoods(buyGoods);
								goodServices.modLimitBuy(limitBuyUpdateReq);
							}
						}
					}
				}
			}
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void savePrice(double price,String orderid){
		Order order  = this.get(orderid);
		double  amount  =order.getOrder_amount();
//		double discount=  amount-price;
		double discount=  CurrencyUtil.sub(amount,price);
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_AMOUNT_UPDATE"), price,orderid);
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_DISCOUNT_UPDATE"), discount,orderid);
		
		OrderChange orderChange = new OrderChange();
		orderChange.setOrder_id(order.getOrder_id());
		orderChange.setItem_id(order.getOrder_id());
		orderChange.setNew_value(String.valueOf(price));
		orderChange.setNew_value_desc(String.valueOf(price));
		orderChange.setOld_value(String.valueOf(order.getOrder_amount()));
		orderChange.setOld_value_desc(String.valueOf(order.getOrder_amount()));
		orderChange.setCreate_date(DBTUtil.current());
		orderChange.setField_name("order_amount");
		orderChange.setTable_name("ES_ORDER");
		orderChange.setSequ(0);
		this.saveChange(orderChange);
		
		this.log(orderid, "修改订单价格为"+ price, null, "管理员");
	}
	/**
	 * 作废
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void cancel(String orderId) {
		if(orderId== null ) throw new  IllegalArgumentException("param orderId is NULL");
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"), OrderStatus.ORDER_CONFIRM_CANCEL,orderId);
		//logger.info("cancel");
	}
	/**
	 * 保存变动日志表
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-13 
	 * @param orderChange
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveChange(OrderChange orderChange) {
		this.baseDaoSupport.insert("order_change", orderChange);
	}
	
	/** 
	 * 保存售后服务订单
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveAfterServiceOrder(Goods goods,Map<String,String> params){
		String order_id = this.baseDaoSupport.getSequences("s_es_order");
		OrderItem item = new OrderItem();
		item.setOrder_id(order_id);
		item.setGoods_id(goods.getGoods_id());
		item.setNum(1);
		item.setName(goods.getName());
		item.setItem_type(0);
		item.setPrice(goods.getMktprice());
		item.setStatus("0");
		item.setSource_from("FJ");
		item.setItem_id(this.baseDaoSupport.getSequences("s_es_order_items"));
		this.baseDaoSupport.insert("es_order_items", item);
		
		Order order = new Order();
		order.setOrder_id(order_id);
		order.setOrder_type("15");//售后类型
		order.setSn(goods.getSn());
		order.setStatus(21);//售后服务正常状态订单
		order.setPay_status(0);
		order.setShipping_area(params.get("addrArea"));
		order.setCreate_time(DBTUtil.getDBCurrentTime());
		order.setShip_name(params.get("cname"));
		order.setShip_addr(params.get("caddr"));
		order.setShip_mobile(params.get("cmobile"));
		order.setShip_time(params.get("time"));
		order.setGoods_num(1);
		order.setSource_from("FJ");
		order.setGoods_id(goods.getGoods_id());
		order.setCreate_type(4);
		this.baseDaoSupport.insert("es_order", order);
	}
	
	/**
	 * 记录订单操作日志
	 * @param order_id
	 * @param message
	 * @param op_id
	 * @param op_name
	 */
	@Override
	public void log(String order_id,String message,String op_id,String op_name){
		OrderLog orderLog = new OrderLog();
		orderLog.setMessage(message);
		orderLog.setOp_id(op_id);
		orderLog.setOp_name(op_name);
		orderLog.setOp_time(DBTUtil.current());
		orderLog.setOrder_id(order_id);
		this.baseDaoSupport.insert("order_log", orderLog);
	}	
	
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order add(Order order,String sessionid) {
		
		if(order==null) throw new RuntimeException("error: order is null");
		
		/**************************用户信息****************************/
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		//非匿名购买
		if(member!=null){
			order.setMember_id(member.getMember_id());
		}
		
		/**************************计算价格、重量、积分****************************/
		boolean  isProtected = order.getIs_protect().compareTo(1)==0;
		OrderPrice orderPrice  =cartManager.countPrice(member,sessionid, order.getShipping_id(),  ""+order.getRegionid(), isProtected, true,null);
		
		order.setGoods_amount( orderPrice.getGoodsPrice());
		order.setWeight(orderPrice.getWeight());		
		order.setDiscount(orderPrice.getDiscountPrice());
		order.setOrder_amount(orderPrice.getOrderPrice());
		order.setProtect_price(orderPrice.getProtectPrice());
		order.setShipping_amount(orderPrice.getShippingPrice());
		order.setGainedpoint(orderPrice.getPoint());
		
		//配送方式名称
		DlyType dlyType = dlyTypeManager.getDlyTypeById(order.getShipping_id());
		if(dlyType==null)  throw new RuntimeException("shipping not found count error");
		order.setShipping_type(dlyType.getName() );
		
		
		/************支付方式价格及名称************************/
		PaymentCfgReq reqid = new PaymentCfgReq();
		reqid.setPayment_cfg_id(order.getPayment_id()+"");
		PayCfg payCfg =paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
		//PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
		order.setPaymoney(0d);
		order.setPayment_name( payCfg.getName());
		order.setPayment_type(payCfg.getType());
		
		/************创建订单************************/
		order.setCreate_time(DBTUtil.current());
		order.setSn(this.createSn());
		order.setStatus(0);
		order.setDisabled(0);
		order.setPay_status(OrderStatus.PAY_NO);
		order.setShip_status(OrderStatus.SHIP_NO);	 		
		this.baseDaoSupport.insert("order", order);
		
		
		
		/************写入订单货物列表************************/
		List<CartItem> itemList  = this.cartManager.listGoods(member,sessionid,null);
		List<CartItem> checkedItemList = new ArrayList<CartItem>();
		for(CartItem item : itemList){
			if("0".equals(item.getIs_checked())){
				continue;
			}
			checkedItemList.add(item);
		}
		itemList = checkedItemList;
		List pgkList  = this.cartManager.listPgk(member,sessionid);
		
		/***************赠品货物列表**************************/
		List<CartItem> giftList  = cartManager.listGift(member,sessionid);
		
		if(itemList.isEmpty() && pgkList.isEmpty() && giftList.isEmpty()) throw new RuntimeException("创建订单失败，购物车为空");
		
 
		
		String orderId = order.getOrder_id();
		
		itemList.addAll(pgkList );
		this.saveGoodsItem(itemList,null, orderId,order,new GoodsApply());
		
		
		if(giftList!=null && !giftList.isEmpty())
		this.saveGiftItem(giftList, orderId);
		
		/**
		 * 应用订单优惠，送出优惠卷及赠品，并记录订单优惠方案
		 */
		if(member!=null){
			this.promotionServ.applyOrderPmt(orderId, orderPrice.getOrderPrice(), member.getLv_id(),null);
			List<Promotion> pmtList =  promotionServ.list(orderPrice.getOrderPrice(), member.getLv_id(),null);
			for(Promotion pmt :pmtList){
				String sql =SF.orderSql("SERVICE_ORDER_PMT_INSERT");
				this.baseDaoSupport.execute(sql, pmt.getPmt_id(),orderId,pmt.getPmt_describe());
			}
			
		}
		
		
		/************写入订单日志************************/
		OrderLog log = new OrderLog();
		log.setMessage("订单创建");
		log.setOp_name("顾客");
		log.setOrder_id(orderId);
		this.addLog(log);
		order.setOrder_id(orderId);
		
		cartManager.clean(sessionid, true);
		cartManager.updateCheckedFlag(sessionid, 0l, 1);
		ThreadContextHolder.getSessionContext().removeAttribute("coupon");
		order.setOrderprice(orderPrice);
		this.orderPluginBundle.onCreate(order, sessionid);
		return order;
	}
	
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order addOrder(Order order,GoodsApply goodsApply,OrderOuter orderOuter,String sessionid) {
		
		if(order==null) throw new RuntimeException("error: order is null");
		
		/**************************用户信息****************************/
		IUserService userService = UserServiceFactory.getUserService();
		Member member = userService.getCurrentMember();
		//非匿名购买
		if(member!=null){
			order.setMember_id(member.getMember_id());
		}
		
		/**************************计算价格、重量、积分****************************/
		boolean  isProtected = order.getIs_protect().compareTo(1)==0;
		OrderPrice orderPrice  = cartManager.countPrice(member,sessionid, order.getShipping_id(),  ""+order.getRegionid(), isProtected, true,orderOuter==null?null:orderOuter.getCoupon());
		
		
		
//		Cart cart = ThreadContextHolder.getOrderParams().getCart();
//		
//		order.setGoods_amount(cart.getPrice()*cart.getNum());
//		order.setWeight(cart.getWeight());		
//		order.setDiscount(0.0);
//		order.setOrder_amount(cart.getPrice()*cart.getNum());
//		order.setProtect_price(0.0);
//		order.setShipping_amount(0.0);
//		order.setGainedpoint(0);
//		
		order.setGoods_amount( orderPrice.getGoodsPrice());
		order.setWeight(orderPrice.getWeight());		
		order.setDiscount(orderPrice.getDiscountPrice());
		order.setOrder_amount(orderPrice.getOrderPrice());
		order.setProtect_price(orderPrice.getProtectPrice());
		order.setShipping_amount(orderPrice.getShippingPrice());
		order.setGainedpoint(orderPrice.getPoint());
		
		//配送方式名称
		DlyType dlyType = dlyTypeManager.getDlyTypeById(order.getShipping_id());
		if(dlyType==null)  throw new RuntimeException("shipping not found count error");
		order.setShipping_type(dlyType.getName() );
		
		
		/************支付方式价格及名称************************/
		PaymentCfgReq reqid = new PaymentCfgReq();
		reqid.setPayment_cfg_id(order.getPayment_id()+"");
		PayCfg payCfg =paymentCfgServ.queryPaymentCfgById(reqid).getPaymentCfg();
		//PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
		order.setPaymoney(0d);
		//PayCfg payCfg = this.paymentManager.get(order.getPayment_id());
		//order.setPaymoney(this.paymentManager.countPayPrice(order.getOrder_id()));
		order.setPayment_name( payCfg.getName());
		order.setPayment_type(payCfg.getType());
		
		/************创建订单************************/
		order.setCreate_time(DBTUtil.current());
		order.setSn(this.createSn());
		order.setStatus(OrderStatus.ORDER_COLLECT); //采集订单
		order.setDisabled(0);
		order.setPay_status(OrderStatus.PAY_NO);
		order.setShip_status(OrderStatus.SHIP_NO);
		
		
		this.baseDaoSupport.insert("order", order);
		
		
		
		/************写入订单货物列表************************/
		List<CartItem> itemList  = this.cartManager.listGoods(member,sessionid,orderOuter==null?null:orderOuter.getCoupon());
		List<CartItem> checkedItemList = new ArrayList<CartItem>();
		for(CartItem item : itemList){
			if("0".equals(item.getIs_checked())){
				continue;
			}
			checkedItemList.add(item);
		}
		itemList = checkedItemList;
		List pgkList  = this.cartManager.listPgk(member,sessionid);
		
		/***************赠品货物列表**************************/
		List<CartItem> giftList  = cartManager.listGift(member,sessionid);
		
		if(itemList.isEmpty() && pgkList.isEmpty() && giftList.isEmpty()) throw new RuntimeException("创建订单失败，购物车为空");
		
		String orderId = order.getOrder_id();
		
		
		itemList.addAll(pgkList );
		this.saveGoodsItem(itemList,orderOuter, orderId,order,goodsApply);
		
		
		if(giftList!=null && !giftList.isEmpty())
		this.saveGiftItem(giftList, orderId);
		
		/**
		 * 应用订单优惠，送出优惠卷及赠品，并记录订单优惠方案
		 */
		if(member!=null){
			this.promotionServ.applyOrderPmt(orderId, orderPrice.getOrderPrice(), member.getLv_id(),orderOuter.getCoupon());
			List<Promotion> pmtList =  promotionServ.list(orderPrice.getOrderPrice(), member.getLv_id(),orderOuter.getCoupon());
			for(Promotion pmt :pmtList){
				String sql =SF.orderSql("SERVICE_ORDER_PMT_INSERT");
				this.baseDaoSupport.execute(sql, pmt.getPmt_id(),orderId,pmt.getPmt_describe());
			}
			
		}
		
		
		order.setOrder_id(orderId);
		
		cartManager.clean(sessionid, true);
		cartManager.updateCheckedFlag(sessionid, 0l, 1);
		ThreadContextHolder.getSessionContext().removeAttribute("coupon");
		order.setOrderprice(orderPrice);
		return order;
	}
	
	
	
	/**
	 * 添加订单日志
	 * @param log
	 */
	@Override
	public void addLog(OrderLog log){
		log.setOp_time(DBTUtil.current());
//		this.baseDaoSupport.insert("order_log", log);
	}
	
	@Override
	public Order getOrderByTransId(String transactionId){
		String sql = SF.orderSql("SERVICE_ORDER_SQL_SELECT")+" and a.transaction_id = ?";
		return (Order)this.baseDaoSupport.queryForObject(sql, Order.class, transactionId);
	}
	
	/**
	 * 将一个json字串转为list
	 * @param props
	 * @return
	 */
	public List<ExtAttr> converAttrFormString(String props){
		if (props == null || props.equals(""))
			return new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(props);
		List<ExtAttr> extAtts = new ArrayList<ExtAttr>();
		List<ExtAttr> lists = (List) JSONArray.toCollection(jsonArray,ExtAttr.class);
		
//		for (Map extAttr:lists) {
//			ExtAttr p_extAttr = new ExtAttr();
//			extAtts.add(BeanUtils.copyProperties(, extAttr));
//		}
		return lists;
	}
 
	@Override
	public void addOrderItem(OrderItem item){
		String itemid = this.baseDaoSupport.getSequences("s_es_order_items");
		item.setItem_id(itemid);
		this.baseDaoSupport.insert("es_order_items", item);
	}
	
	@Override
	public void delOrderItem(String item_id){
		String sql = SF.orderSql("DelOrderItem");
		this.baseDaoSupport.execute(sql, item_id);
	}
	
	@Override
	public void updateOrderItemShipNum(String item_id,int ship_num){
		String sql = SF.orderSql("UpdateOrderItemShipNum");
		this.baseDaoSupport.execute(sql, ship_num,item_id);
	}
	
	@Override
	public void updateShipStatus(String order_id,int status){
		String sql = SF.orderSql("UpdateShipStatus");
		this.baseDaoSupport.execute(sql, status,order_id);
	}
	
	
	/**
	 * 保存商品订单项
	 * @param itemList
	 * @param order_id
	 */
	private void saveGoodsItem(List itemList,OrderOuter orderOuter,String order_id,Order order,GoodsApply goodsApply){
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		order.setOrderItemList(orderItemList);
		for(int i=0;i<itemList.size();i++){
			
			CardInfRequest cardInfRequest =goodsApply.getCardInfRequest();
			
			OrderItem  orderItem = new OrderItem();
			String itemid = this.baseDaoSupport.getSequences("s_es_order_items");
			orderItem.setItem_id(itemid);
			orderItemList.add(orderItem);
			CartItem cartItem =(CartItem) itemList.get(i);
			orderItem.setPrice(cartItem.getCoupPrice() ) ;
			orderItem.setName( cartItem.getName());
			orderItem.setNum(cartItem.getNum());

			orderItem.setGoods_id(cartItem.getGoods_id());
			orderItem.setShip_num(0);
			orderItem.setSpec_id(StringUtil.isEmpty(cartItem.getSpec_id())?cartItem.getProduct_id():cartItem.getSpec_id());
			orderItem.setProduct_id(cartItem.getProduct_id());
			orderItem.setOrder_id(order_id);
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setGainedpoint(cartItem.getPoint());
			orderItem.setAddon( cartItem.getAddon() );
			
			//orderItem.setLan_id(order.getLan_id());
			orderItem.setLv_id(cartItem.getMember_lv_id());
			orderItem.setItem_type(cartItem.getItemtype());
			if(cartItem.getCoupPrice()!=null){
				orderItem.setCoupon_price(cartItem.getCoupPrice());
			}else{
				orderItem.setCoupon_price(0d);
			}
			//订单入网信息,外系统订单
			if(orderOuter !=null)
			{
				if("true".equals(order.getCreaterOrder())){
					this.baseDaoSupport.insert("order_items", orderItem);
				}
			}else{
				if("true".equals(order.getCreaterOrder())){
					this.baseDaoSupport.insert("order_items", orderItem);
				}
			}
			
		}
		order.setOrderItemList(orderItemList);
	}

	@Override
	public void insertCardItem(Order order, OrderItem orderItem,List<ExtAttr> jsonLists,String mapping_type) {
		for (ExtAttr extAttr:jsonLists) {
				String field_name = extAttr.getField_name();
				String field_value = extAttr.getField_value();
				String field_value_desc = extAttr.getField_value_desc();
				insertItemCard(order.getType_code(), field_name, field_value, field_value_desc,mapping_type,orderItem);
		}
	}
	
	
	public static void main(String[] args) {
			String props = "[{field_name:'test1',field_value:'11',field_value_desc:'22'}]";
			//List<ExtAttr> jsonLists = WorkFlowUtils.converAttrFormString(props);
	}
	

	//根据字段名获取实例数据
	@Override
	public ItemCard getItemCardByFieldName(String order_id,String field_name){
		List<ItemCard> itemCards=getItemCardByOrderId(order_id);
		for (ItemCard itemCard:itemCards) {
				if(field_name.equals(itemCard.getField_ename()))
						return itemCard;
		}
		return null;
	}
	
	
	//根据字段名获取实例数据
	@Override
	public ItemCard getItemCardByFieldName(String order_id,String field_name,List<ItemCard> itemCards){
		for (ItemCard itemCard:itemCards) {
				if(field_name.equals(itemCard.getField_ename()))
						return itemCard;
		}
		return null;
	}
	

	//根据字段名获取实例数据
	@Override
	public List<ItemCard> getItemCardsByFieldName(String field_name,List<ItemCard> itemCards){
		List<ItemCard> p_itemCards = new ArrayList<ItemCard>();
		for (ItemCard itemCard:itemCards) {
				if(field_name.equals(itemCard.getField_ename()))
					p_itemCards.add(itemCard);
					
		}
		return p_itemCards;
	}
	
	
	@Override
	public List<ItemCard> getItemCardByOrderId(String order_id){
		return this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_CARD_SELECT_BY_ORDER"), ItemCard.class, order_id);
	}
	
	
	//根据字段名获取配置规格数据
	public ItemCardDef getItemCardDefByFieldName(String type_code,String field_name,String mapping_type){
//		List<ItemCardDef> cardDefs = cacheUtil.getItemCardDefByType(type_code);
//		for (ItemCardDef itemCardDef:cardDefs) {
//				if(field_name.equals(itemCardDef.getField_name())&& Consts.FIELD_MAPPING_0.equals(mapping_type))
//					return itemCardDef;
//				else if(field_name.equals(itemCardDef.getSec_field_name())&& Consts.FIELD_MAPPING_1.equals(mapping_type))
//					return itemCardDef;
//		}
		return null;
	}
	
	//订单项卡片插入规格数据
	@Transactional(propagation = Propagation.REQUIRED)
	public void insertItemCard(String type_code,String field_name,String sec_field_value,String sec_field_value_desc,String mapping_type,OrderItem orderItem){
		ItemCard itemCard = new ItemCard();
		logger.info("22");
		ItemCardDef itemCardDef = getItemCardDefByFieldName(type_code, field_name, mapping_type);
		if(itemCardDef ==null)
			return;
		itemCard.setBa_card_type(itemCardDef.getCard_type());
		itemCard.setField_cname(itemCardDef.getField_desc());
		itemCard.setField_ename(itemCardDef.getField_name());
		itemCard.setField_value_type(itemCardDef.getValue_type());
		itemCard.setItem_id(orderItem.getItem_id());
		itemCard.setOrder_id(orderItem.getOrder_id());
		itemCard.setSequ(itemCardDef.getSequ());
		itemCard.setSec_field_value(sec_field_value);
		itemCard.setSec_field_value_desc(sec_field_value_desc);
		itemCard.setCreate_date(DBTUtil.current());
		setItemCardValues(sec_field_value, sec_field_value_desc, itemCard,itemCardDef);
		this.baseDaoSupport.insert("ES_CARD", itemCard);
	}

	@SuppressWarnings("unchecked")
	private void setItemCardValues(String sec_field_value,String sec_field_value_desc, ItemCard itemCard,ItemCardDef itemCardDef) {
		if(!StringUtil.isEmpty(itemCardDef.getAttr_code())){
			List<Map> dcList  = cacheUtil.doDcPublicQuery(itemCardDef.getAttr_code());
			for (Map dcMap :dcList) {
				if(sec_field_value.equals(dcMap.get("codea"))){ //codea,codeb,codec,coded,定义淘宝与分销平台值转换
					itemCard.setField_value((String)dcMap.get("codec"));
					itemCard.setField_value_desc((String)dcMap.get("coded"));
				}
			}
			
		}else{
			if(!StringUtil.isEmpty(itemCardDef.getDefault_value())){
				itemCard.setField_value(itemCardDef.getDefault_value());
				itemCard.setField_value_desc(itemCardDef.getDefault_value_desc());
			}else{
				itemCard.setField_value(sec_field_value);
				itemCard.setField_value_desc(sec_field_value_desc);
			}
		}
	}
	
	
	/**
	 * 保存赠品项
	 * @param itemList
	 * @param orderid
	 * @throws IllegalStateException 会员尚未登录,不能兑换赠品!
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void saveGiftItem(List<CartItem> itemList ,String orderid){
		Member member = UserServiceFactory.getUserService().getCurrentMember();
		if(member==null){
			throw new IllegalStateException("会员尚未登录,不能兑换赠品!"); 
		}
		
		
		int point =0;
		for(CartItem cartItem : itemList){
			point  += cartItem.getSubtotal().intValue();
			//this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_GIFT_INSERT"), 
			//		orderid,item.getProduct_id(),item.getName(),item.getPoint(),item.getNum(),0,"exchange");
			/**
			 * 修改为把赠品添加到order_items表中=========start===========
			 */
			OrderItem  orderItem = new OrderItem();
			orderItem.setPrice(0d) ;
			orderItem.setName( cartItem.getName());
			orderItem.setNum(cartItem.getNum());

			orderItem.setGoods_id(cartItem.getGoods_id());
			orderItem.setShip_num(0);
			orderItem.setSpec_id(StringUtil.isEmpty(cartItem.getSpec_id())?cartItem.getProduct_id():cartItem.getSpec_id());
			orderItem.setOrder_id(orderid);
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setGainedpoint(cartItem.getPoint());
			//orderItem.setAddon( cartItem.getAddon() );
			//orderItem.setLan_id(order.getLan_id());
			orderItem.setLv_id(cartItem.getMember_lv_id());
			orderItem.setItem_type(2);
			orderItem.setCoupon_price(0d);
			this.baseDaoSupport.insert("order_items", orderItem);
			/**
			 * 修改为把赠品添加到order_items表中=========end===========
			 */
		}
		if(member.getPoint().intValue() < point){
			throw new IllegalStateException("会员积分不足,不能兑换赠品!"); 
		}
		member.setPoint( member.getPoint()-point ); //更新session中的会员积分
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_MEM_POINT_UPDATE"), member.getPoint(),member.getMember_id());
		
	}
	
	
	@Override
	public Page list(int pageNO, int pageSize,int disabled, String searchkey,
			String searchValue, String order) {
		StringBuffer sql = new StringBuffer(SF.orderSql("SERVICE_ORDER_SQL_SELECT")).append(" and disabled=? ");
		if(!StringUtil.isEmpty(searchkey) && !StringUtil.isEmpty(searchValue) ){
			sql.append(" and ");
			sql.append(searchkey);
			sql.append("=?"); 
		}
		if(!ManagerUtils.isAdminUser()){
			String staffNo = ManagerUtils.getAdminUser().getUserid();
			sql.append(" and exists(select b.staff_no from " + this.getTableName("order_items") + " a, " 
					+ this.getTableName("goods") + " b " +
					"where a.goods_id=b.goods_id and b.staff_no=" + staffNo + " and a.order_id=t.order_id) ");
		}
		order = StringUtil.isEmpty(order)?"order_id desc":order;
		sql.append(" order by "+ order);
		Page page =null;
		
		if(!StringUtil.isEmpty(searchkey) && !StringUtil.isEmpty(searchValue) ){
			//page  = this.saasDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, searchValue);
			page = this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class,disabled, searchValue);
		}else{
			page  = this.baseDaoSupport.queryForPage(sql.toString(), pageNO, pageSize, Order.class,disabled);
		}
		Object result = page.getResult();
		return page;
	}
	
	public StringBuffer sqlReset(){
		String sql = SF.orderSql("SERVICE_ORDER_SQL_COUNT");
		StringBuffer sqls = new StringBuffer(sql);
		return sqls;
	}
	
	
	public Map queryOrderCountByActionName(String action_name,OrderQueryParam ordParam,Map countMap){
		List params = new ArrayList();
		StringBuffer sqls = sqlReset();
		ordParam.setAction(action_name);
		params.clear();
		sqls  = sqlReset();
		assembleOrdSql(ordParam, params, sqls);
		
		List countL = this.baseDaoSupport.queryForList(sqls.toString(),params.toArray());
		if(ListUtil.isEmpty(countL))
			countMap.put(action_name, 0);
		else {
			countMap.put(action_name, ((Map)countL.get(0)).get("all_count")+"");
		}
		return countMap;
		
	}
	//新查询列表
	@Override
	@SuppressWarnings("unchecked")
	public Map listc(OrderQueryParam ordParam) {
		Map countMap = new HashMap();
		
		//待审核订单
		queryOrderCountByActionName("wait_audit",ordParam,countMap);
		
		//待支付订单
		queryOrderCountByActionName("wait_pay",ordParam,countMap);
		
		//待受理订单
		queryOrderCountByActionName("wait_accept",ordParam,countMap);
		
		//待发货订单
		queryOrderCountByActionName("wait_ship",ordParam,countMap);
		
		//待确认收货
		queryOrderCountByActionName("wait_confirm_ship",ordParam,countMap);
	
		//待退费订单
		queryOrderCountByActionName("refund",ordParam,countMap);
		
		//待换货订单
		queryOrderCountByActionName("wait_change_ship",ordParam,countMap);
	
		//待退货订单
		queryOrderCountByActionName("wait_refund_ship",ordParam,countMap);
		
		return countMap;
		
	}
	
	@Override
	public Page listAfterService(int pageNO, int pageSize,String order){
		String serviceSql = "select a.order_id,a.sn,a.status,a.pay_status,a.shipping_area,a.create_time,a.ship_name,a.ship_addr,a.ship_mobile,a.goods_num,a.order_type,b.name col6,a.goods_id,a.ship_time from es_order a,es_goods b  where a.goods_id=b.goods_id and a.order_type='15' and a.source_from='FJ' ";
		Page page = this.baseDaoSupport.queryForPage(serviceSql, pageNO, pageSize);
		return page;
	}
	
	//新查询列表
	@Override
	@SuppressWarnings("unchecked")
	public Page listn(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order) {
		
		List params = new ArrayList();
		StringBuffer sqls = new StringBuffer("select t.*,(select name from es_goods g where g.goods_id = t.goods_id) goods_name,(select lan_name from es_lan lan where lan.lan_id = t.lan_id ) lan_name, (select state from es_order_audit where order_id = t.order_id and audit_type = '"+OrderStatus.AUDIT_TYPE_00A+"' "+ DBTUtil.andEqRownum("1")+" ) audit_state,(select realname from es_adminuser aduser where aduser.userid = t.userid and aduser.source_from ='"+ManagerUtils.getSourceFrom()+"') user_name  from " + this.getTableName("order") + " t where 1=1 ");
		assembleOrdSql(ordParam, params, sqls);
		order = StringUtil.isEmpty(order)?"create_time desc":order;
		sqls.append(" order by "+ order);
		
		
		List paramsC = new ArrayList();
		StringBuffer countSqls = new StringBuffer("select count(*) from " + this.getTableName("order") + " t where 1=1 ");
		assembleOrdSql(ordParam, paramsC, countSqls);
		
		Page page  =this.baseDaoSupport.queryForCPage(sqls.toString(), pageNO, pageSize,  Order.class, countSqls.toString(),params.toArray());
		
		//Page page   = this.baseDaoSupport.queryForPage(sqls.toString(), pageNO, pageSize, Order.class,disabled);
		return page;
	}
	
	
	/**
	 * 大众版查询列表
	 * @作者 MoChunrun 
	 * @创建日期 2013-8-12 
	 * @param pageNO
	 * @param pageSize
	 * @param disabled
	 * @param ordParam
	 * @param order
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Page listc(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order) {
		List params = new ArrayList();
		String sql = SF.orderSql("SERVICE_USER_ORDER_STATE_SQL_SELECT").replaceAll("#rowlimit", DBTUtil.andEqRownum("1"));
		StringBuffer sqls = new StringBuffer(sql);
		assembleOrdSqlc(ordParam, params, sqls);
		if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())){
			order = StringUtil.isEmpty(order)?"create_time desc":order;
		}else{
			order = StringUtil.isEmpty(order)?"create_time asc":order;
		}
		sqls.append(" order by "+ order);
		List paramsC = new ArrayList();
		StringBuffer countSqls = new StringBuffer(SF.orderSql("SERVICE_ORDER_SQL_COUNT"));
		assembleOrdSqlc(ordParam, paramsC, countSqls);
		Page page  =this.baseDaoSupport.queryForCPage(sqls.toString(), pageNO, pageSize,  Order.class, countSqls.toString(),params.toArray());
		return page;
	}
	
	private void assembleOrdSqlc(OrderQueryParam ordParam, List params, StringBuffer sqls) {
		AdminUser au = ManagerUtils.getAdminUser();
		/**
		 * 供货商管理员ID
		 */
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getParuserid();
		boolean isSupplier = ((Consts.SUPPLIER_FOUNDER==au.getFounder())||(5==au.getFounder()));
		if(isSupplier){
			sqls.append(" and userid = ? ");
			params.add(userid);
		}
		if("wait_audit".equals(ordParam.getAction())) //待审核
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_COLLECT);
		}else if("wait_pay".equals(ordParam.getAction())) //待支付
		{
			assembleNewOrdSql(ordParam, params, sqls);
			//查询待支付的订单
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
		}else if("wait_accept".equals(ordParam.getAction())) //待备货
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? and pay_status=?");
			params.add(OrderStatus.ORDER_PAY);
			params.add(OrderStatus.PAY_YES);
		}else if("wait_ship".equals(ordParam.getAction())) //待发货 
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and (status = ? or (status = ? and ship_status=?))");
			params.add(OrderStatus.ORDER_ACCEPT);
			params.add(OrderStatus.ORDER_SHIP);
			params.add(OrderStatus.SHIP_PARTIAL_SHIPED);
		}else if("wait_confir_ship".equals(ordParam.getAction())) //待确认收货 
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? and ship_status=?");
			params.add(OrderStatus.ORDER_SHIP);
			//params.add(OrderStatus.ORDER_CONFIRM_SHIP);
			params.add(OrderStatus.SHIP_YES);
		}else if("wait_finish".equals(ordParam.getAction())) //待确认完成
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? and ship_status=?");
			//params.add(OrderStatus.ORDER_SHIP);
			params.add(OrderStatus.ORDER_CONFIRM_SHIP);
			params.add(OrderStatus.SHIP_YES);
		}else if("cancel".equals(ordParam.getAction())) //订单取消 
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_COLLECT);
		}else if("widhdraw".equals(ordParam.getAction())) //订单撤单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
		}else if("finish".equals(ordParam.getAction())) //订单完成
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_COMPLETE);
		}else if("refund".equals(ordParam.getAction())) //待退费订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and pay_status in(1,3,4) ");
			sqls.append(" and exists (select 1 from es_order_rel eor,es_order_apply eop where eor.z_order_id=eop.order_apply_id and eop.a_order_item_id=t.order_id and eop.apply_state=? and eop.SERVICE_TYPE=? and upper(eor.Z_TABLE_NAME)='ES_ORDER_APPLY') ");
			params.add(OrderStatus.ORDER_APPLY_STATUS_1);
			params.add(OrderStatus.ORDER_TYPE_2);
		}else if("wait_change_ship".equals(ordParam.getAction())) //待换货订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			
			sqls.append(" and exists (select 1 from es_order_rel eor,es_order_apply eop where eor.z_order_id=eop.order_apply_id and eop.a_order_item_id=t.order_id and eop.apply_state=? and eop.SERVICE_TYPE=? and upper(eor.Z_TABLE_NAME)='ES_ORDER_APPLY') ");
			params.add(OrderStatus.ORDER_APPLY_STATUS_1);
			params.add(OrderStatus.ORDER_TYPE_3);
		}else if("wait_refund_ship".equals(ordParam.getAction())) //待退货订单
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			/*sqls.append(" and (status = ? or status = ?) ");
			params.add(OrderStatus.ORDER_COMPLETE);
			params.add(OrderStatus.ORDER_CONFIRM_SHIP);*/
			sqls.append(" and exists (select 1 from es_order_rel eor,es_order_apply eop where eor.z_order_id=eop.order_apply_id and eop.a_order_item_id=t.order_id and eop.apply_state=? and eop.SERVICE_TYPE=? and upper(eor.Z_TABLE_NAME)='ES_ORDER_APPLY') ");
			params.add(OrderStatus.ORDER_APPLY_STATUS_1);
			params.add(OrderStatus.ORDER_TYPE_4);
			
		}else if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())) //全部订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
		}
	}
	

	private void assembleOrdSql(OrderQueryParam ordParam, List params, StringBuffer sqls) {
		
		
		
		if("wait_audit".equals(ordParam.getAction())) //待审核，【提供电信、一级分销商】
		{
			assembleNewOrdSql(ordParam, params, sqls);
			
			//查询待审核订单
			if(ManagerUtils.isNetStaff() || ManagerUtils.isFirstPartner()){
				params.add(OrderStatus.ORDER_COLLECT);
				if(ManagerUtils.isNetStaff()){
					//查询待审核一级分销商订单
					sqls.append(" and status = ? and source_from = ? ");
					params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				}else if(ManagerUtils.isFirstPartner()){ 
					
					//查询待审核归属二级分销商订单
					sqls.append(" and status = ? and userid in (select userid from es_adminuser b where b.state='1' and b.paruserid = ? ) and source_from = ? ");
					
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
					
				}
			}else{
				sqls.append(" and order_id ='1'");
			}
		}else if("wait_pay".equals(ordParam.getAction())) //待支付，【提供二级、一级分销商】
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			//查询待支付的订单
			sqls.append(" and status = ? and userid  = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
			params.add(ManagerUtils.getUserId());
			
			
		}else if("wait_accept".equals(ordParam.getAction())) //待受理，【提供二级、一级、电信】
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isNetStaff()){ //电信

				//查询待调拨的云卡、流量卡、号码 
				sqls.append("and ( type_code in(?,?,?) and source_from = ? and status = ? )");
				params.add(OrderBuilder.CLOUD_KEY);
				params.add(OrderBuilder.CARD_KEY);
				params.add(OrderBuilder.CONTRACT_KEY);
				params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				params.add(OrderStatus.ORDER_PAY);
				
				sqls.append(" or ");
				
				//查询待审核处理订单
				sqls.append(" (  "); //source_from  = ?
				sqls.append("  exists (select 1 from es_order_audit aud where aud.order_id = t.order_id   and aud.state =? )  )");
				//params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				//params.add(OrderStatus.AUDIT_TYPE_00A);
				params.add(OrderStatus.ORDER_AUDIT_STATE_2);
			
				
			}else if(ManagerUtils.isFirstPartner()){ // 查询待调拨二级分销商订单、待审核二级分销商订单、待业务受理淘宝订单
				
				//查询待调拨给二级分销商的云卡、充值卡 ,号码
				sqls.append("and ( type_code in(?,?,?) and source_from = ? and status = ? and  userid in (select userid from es_adminuser b where b.state='1' and b.paruserid = ?) ) ");
				params.add(OrderBuilder.CLOUD_KEY);
				params.add(OrderBuilder.CARD_KEY);
				params.add(OrderBuilder.CONTRACT_KEY);
				params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
				params.add(OrderStatus.ORDER_PAY);
				params.add(ManagerUtils.getUserId());
				
				sqls.append(" or ");
				
				
				//查询待审核合约机 
				sqls.append(" ( source_from  = ? ").
				append(" and exists (select 1 from es_order_audit aud where aud.order_id = t.order_id and aud.audit_type = ?  and state =? ) and userid in (select userid from es_adminuser u where u.paruserid  = ? ) ) ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderStatus.AUDIT_TYPE_00A);
				params.add(OrderStatus.ORDER_AUDIT_STATE_0);
				params.add(ManagerUtils.getUserId());
				
				sqls.append(" or ");
				
				
				//查询代申请的合约机
				sqls.append(" (    userid = ? and source_from  = ?  and status = ? and type_code = ?  and not exists (select 1 from es_order_audit aud where aud.order_id = t.order_id and audit_type = ?  ) ) ");
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderStatus.ORDER_PAY);
				params.add(OrderBuilder.CONTRACT_KEY);
				params.add(OrderStatus.AUDIT_TYPE_00A);
				
				sqls.append(" or ");
				
				
				//查询待受理的订单(资料返档、合约机受理)[来源淘宝订单]
				sqls.append(" (  source_from = ?  and userid = ? and status = ? and (type_code = ?  or (type_code  =? and exists ( select 1 from es_order_audit aud where aud.order_id = t.order_id and state =? and audit_type = ?  )))) ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.ORDER_PAY);
				params.add(OrderBuilder.CLOUD_KEY);
				params.add(OrderBuilder.CONTRACT_KEY);
				params.add(OrderStatus.ORDER_AUDIT_STATE_4);
				params.add(OrderStatus.AUDIT_TYPE_00A);
				
				
			}else if(ManagerUtils.isSecondPartner()){//查询待申请二级分销商，待业务受理二级分销商订单
				
				
				//查询待申请的合约机
				sqls.append("and ( source_from  = ? and exists( select 1 from es_order_rel rel where a_order_id = t.order_id and rel_type = ? and rel.state= ?  )").
				append(" and not exists (select 1 from es_order_audit aud where aud.order_id = t.order_id and aud.audit_type = ? ) and userid = ? ) ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderStatus.ORDER_TYPE_2);
				params.add(OrderStatus.ORDER_REL_STATE_0);
				params.add(OrderStatus.AUDIT_TYPE_00A);
				params.add(ManagerUtils.getUserId());
				
				sqls.append(" or ");
				
				
				//查询待受理的订单(资料返档、合约机受理) [来源淘宝订单]
				sqls.append(" (  source_from = ?  and userid = ? and status = ? and (type_code = ?  or (type_code  =? and exists(select 1 from es_order_audit aud where aud.order_id = t.order_id and state =? and audit_type = ?  )))  ) ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.ORDER_PAY);
				params.add(OrderBuilder.CLOUD_KEY);
				params.add(OrderBuilder.CONTRACT_KEY);
				params.add(OrderStatus.ORDER_AUDIT_STATE_4);
				params.add(OrderStatus.AUDIT_TYPE_00A);
				
			}
		}else if("wait_ship".equals(ordParam.getAction())) //待发货 (电信，一级，二级分销商)
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			params.add(OrderStatus.ORDER_ACCEPT);
			if(ManagerUtils.isNetStaff()){
				
				sqls.append(" and status = ? and userid in (select userid from es_adminuser b where b.state='1' and b.founder =?) and source_from = ?  ");
				params.add(Consts.CURR_FOUNDER_3);
				params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				
			}else if((ManagerUtils.isFirstPartner())){
				
				sqls.append(" and status = ? and (  (userid = ?  and   source_from  = ? ) or ( userid in (select userid from es_adminuser b where b.state='1' and b.paruserid = ? )  and   source_from  = ?))");
				
				//一级分销商淘宝订单
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				
				//或者归属二级分销商订单
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
				
			}else if((ManagerUtils.isSecondPartner())){
				
				//二级分销商淘宝订单
				sqls.append(" and status = ? and ((userid = ?  and   source_from  = ? ))");
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
	
			}
		}else if("wait_confirm_ship".equals(ordParam.getAction())) //待确认收货 【一级、二级分销商确认收货】
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner()){
				params.add(OrderStatus.ORDER_SHIP);
				if(ManagerUtils.isFirstPartner()){
					
					//电信人员发货的订单、淘宝订单 
					sqls.append(" and status = ? and userid = ?");
					params.add(ManagerUtils.getUserId());
					
				}else if(ManagerUtils.isSecondPartner()){
					
					//二级分销商发货的订单、淘宝订单
					sqls.append(" and status = ? and userid = ?");
					params.add(ManagerUtils.getUserId());
					
				}
			}else{
				sqls.append("and  order_id ='1'");
			}
		}else if("cancel".equals(ordParam.getAction())) //订单取消 【一级、二级分销商取消分销平台订单】
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner()){
				if(ManagerUtils.isFirstPartner()){
					
					//一级分销商取消订单
					sqls.append(" and status = ? and userid = ? and source_from = ? ");
					params.add(OrderStatus.ORDER_COLLECT);
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				}else if(ManagerUtils.isSecondPartner()){
					
					//二级分销商取消订单
					sqls.append(" and status = ? and userid = ? and source_from = ? ");
					params.add(OrderStatus.ORDER_COLLECT);
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
					
				}
			}else{
				sqls.append("and  order_id ='1'");
			} //
		}else if("widhdraw".equals(ordParam.getAction())) //订单撤单
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner()){
				if(ManagerUtils.isFirstPartner()){
					//一级分销商撤单
					
					sqls.append(" and status = ? and userid = ? and source_from = ? ");
					params.add(OrderStatus.ORDER_NOT_PAY);
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				}else if(ManagerUtils.isSecondPartner()){
					
					//二级分销商撤单订单
					sqls.append(" and status = ? and userid = ? and source_from = ? ");
					params.add(OrderStatus.ORDER_NOT_PAY);
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
					
				}
			}else{
				sqls.append("and order_id='1'");
			}
		}else if("finish".equals(ordParam.getAction())) //订单完成
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner()){
				if(ManagerUtils.isFirstPartner()){
					//一级分销商撤单
					sqls.append(" and status = ? and userid = ? and source_from = ? ");
					params.add(OrderStatus.ORDER_COMPLETE);
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				}else if(ManagerUtils.isSecondPartner()){
					
					//二级分销商撤单订单
					sqls.append(" and status = ? and userid = ? and source_from = ? ");
					params.add(OrderStatus.ORDER_COMPLETE);
					params.add(ManagerUtils.getUserId());
					params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
					
				}
			}else{
				sqls.append("and order_id='1'");
			}
		}else if("refund".equals(ordParam.getAction())) //待退费订单
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isNetStaff()){
				
				//查询一级分销商审核通过、待审核的退费单
				sqls.append("and source_from  = ? and exists( select 1 from es_order_rel rel where a_order_id = t.order_id and rel_type = ? and rel.state= ?  )").
				append(" and exists (select 1 from es_order_audit aud where aud.order_id = t.order_id and aud.audit_type = ?  and state in(?,?) ) ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderStatus.ORDER_TYPE_2);
				params.add(OrderStatus.ORDER_REL_STATE_0);
				params.add(OrderStatus.AUDIT_TYPE_00B);
				params.add(OrderStatus.ORDER_AUDIT_STATE_2);
				params.add(OrderStatus.ORDER_AUDIT_STATE_4);
			}else if(ManagerUtils.isFirstPartner()){
				
				//查询一级分销商归属二级分销商申请的退费单
				sqls.append("and source_from  = ? and exists( select 1 from es_order_rel rel where a_order_id = t.order_id and rel_type = ? and rel.state= ?  )").
				append(" and exists (select 1 from es_order_audit aud where aud.order_id = t.order_id and aud.audit_type = ?  and state =? ) and userid in (select userid from es_adminuser u where u.paruserid  = ? ) ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderStatus.ORDER_TYPE_2);
				params.add(OrderStatus.ORDER_REL_STATE_0);
				params.add(OrderStatus.AUDIT_TYPE_00B);
				params.add(OrderStatus.ORDER_AUDIT_STATE_0);
				params.add(ManagerUtils.getUserId());
				
			}else if(ManagerUtils.isSecondPartner()){
				
				//查询可申请的退费订单
				sqls.append("and source_from  = ? and exists ( select 1 from es_order_rel rel where a_order_id = t.order_id and rel_type = ? and rel.state= ?  )").
				append(" and not  exists (select 1 from es_order_audit aud where aud.order_id = t.order_id ) and userid  = ? ");
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderStatus.ORDER_TYPE_2);
				params.add(OrderStatus.ORDER_REL_STATE_0);
				params.add(ManagerUtils.getUserId());
				
				
			}
		}else if("wait_change_ship".equals(ordParam.getAction())) //待换货订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner())
			{
				sqls.append(" and source_from  = ? and exists( select 1 from es_order_rel rel where a_order_id = t.order_id and rel_type = ? and rel.state= ?  ) and userid = ? ");
				if(ManagerUtils.isFirstPartner()){
					//查询 一级分销商淘宝退货单，且状态为未处理
					params.add(OrderStatus.SOURCE_FROM_TAOBAO);
					
					params.add(OrderStatus.ORDER_TYPE_3);
					params.add(OrderStatus.ORDER_REL_STATE_0);
					params.add(ManagerUtils.getUserId());
				}else if(ManagerUtils.isSecondPartner()){
					//查询 二级分销商淘宝退货单，且状态为未处理
					params.add(OrderStatus.SOURCE_FROM_TAOBAO);
					params.add(OrderStatus.ORDER_TYPE_3);
					params.add(OrderStatus.ORDER_REL_STATE_0);
					params.add(ManagerUtils.getUserId());
				}
			}else{
				sqls.append("and order_id='1'");
			}
		}else if("wait_refund_ship".equals(ordParam.getAction())) //待退货订单
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isFirstPartner() || ManagerUtils.isSecondPartner())
			{
				sqls.append(" and source_from  = ? and exists( select 1 from es_order_rel rel where a_order_id = t.order_id and rel_type = ? and rel.state= ?  ) and userid = ? ");
				if(ManagerUtils.isFirstPartner()){
					//查询 一级分销商淘宝退货单，且状态为未处理
					params.add(OrderStatus.SOURCE_FROM_TAOBAO);
					params.add(OrderStatus.ORDER_TYPE_4);
					params.add(OrderStatus.ORDER_REL_STATE_0);
					params.add(ManagerUtils.getUserId());
				}else if(ManagerUtils.isSecondPartner()){
					//查询 二级分销商淘宝退货单，且状态为未处理
					params.add(OrderStatus.SOURCE_FROM_TAOBAO);
					params.add(OrderStatus.ORDER_TYPE_4);
					params.add(OrderStatus.ORDER_REL_STATE_0);
					params.add(ManagerUtils.getUserId());
				}
			}else{
				sqls.append("and order_id='1'");
			}
		}else if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())) //全部订单
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			
			if(ManagerUtils.isNetStaff()){ 
				
				//查询一级分销商订单, 或淘宝待审核的订单(合约机、退订单 )
				sqls.append(" and (source_from = ? or (  exists ( select 1 from es_order_audit aud where aud.order_id = t.order_id and (aud.state  in (?,?) )) )) ");
				
				params.add(OrderStatus.SOURCE_FROM_AGENT_ONE);
				params.add(OrderStatus.ORDER_AUDIT_STATE_2);
				params.add(OrderStatus.ORDER_AUDIT_STATE_4);
				//params.add(OrderStatus.SOURCE_FROM_TAOBAO); and source_from = ? 
				
				
				
				
			}else if(ManagerUtils.isFirstPartner()){
				
				//查询一级分销商，和归属二级分销商订单
				sqls.append(" and (userid = ? or (  userid in (select userid from es_adminuser u where u.paruserid  = ? and (source_from = ? or (source_from = ? and type_code = ? and   exists (select 1 from es_order_audit  aud where aud.order_id = t.order_id and state='"+OrderStatus.ORDER_AUDIT_STATE_0+"' ) )   )) ))");
				
				params.add(ManagerUtils.getUserId());
				params.add(ManagerUtils.getUserId());
				params.add(OrderStatus.SOURCE_FROM_AGENT_TOW);
				
				//或者淘宝合约机订单、(待审核)
				params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				params.add(OrderBuilder.CONTRACT_KEY);
				
				
			}else if(ManagerUtils.isSecondPartner()){
				
				//查询二级分销商订单
				sqls.append(" and userid = ? ");
				params.add(ManagerUtils.getUserId());
				
			}
		}
		
	}
	
	/**
	 * 新加入的查询条件，因为要在or条件前先执行and，所以独立出来
	 * @param ordParam
	 * @param params
	 * 
	 * @param sqls
	 */
	private void assembleNewOrdSql(OrderQueryParam ordParam, List params, StringBuffer sqls) {
		
		if(ordParam != null ){
			if(ordParam.getStartTime() != null && !"".equals(ordParam.getStartTime())){
				sqls.append(" and t.create_time >= "+DBTUtil.to_date(2));
				params.add(ordParam.getStartTime()+" 00:00:00");
			}
			if(ordParam.getEndTime() != null && !"".equals(ordParam.getEndTime())){
				sqls.append(" and t.create_time <= "+DBTUtil.to_date(2));
				params.add(ordParam.getEndTime()+" 23:59:59");
			}
			
			if(ordParam.getSourceFrom() != null && !"".equals(ordParam.getSourceFrom())){
				if(OrderStatus.SOURCE_FROM_TAOBAO.equals(ordParam.getSourceFrom())){
					sqls.append(" and t.source_from = ? ");
					params.add(ordParam.getSourceFrom());
				}else{
					sqls.append(" and t.source_from <> ? ");
					params.add(OrderStatus.SOURCE_FROM_TAOBAO);
				}
			}
			if(ordParam.getOrderName() != null && !"".equals(ordParam.getOrderName())){
				sqls.append(" and t.goods_id in (select g.goods_id from es_goods g  where upper(g.name) like '%"+ ordParam.getOrderName().trim().toUpperCase() +"%')");
//				params.add(ordParam.getOrderName());
			}
			
			if(ordParam.getOrderId() != null && !"".equals(ordParam.getOrderId())){
				sqls.append(" and t.order_id like '%"+ ordParam.getOrderId() +"%'");
			}
			
			/**
			 * 修改为前端用户
			 */
			/*if(ordParam.getUserid() != null && !"".equals(ordParam.getUserid())){
				sqls.append(" and t.userid = '"+ ordParam.getUserid() +"'");
			}*/
			if(ordParam.getUserName()!=null && !"".equals(ordParam.getUserName())){
				sqls.append(" and upper(t.ship_name) like '%"+ordParam.getUserName().trim().toUpperCase()+"%'");
			}
			
			if(!StringUtils.isEmpty(ordParam.getOrderstatus()) && !"-1".equals(ordParam.getOrderstatus())){
				sqls.append(" and t.status="+ordParam.getOrderstatus());
				//已处理，对应订单已完成status==7、审核不通过状态status==1
				/*if("0".equals(ordParam.getOrderstatus())){
					sqls.append(" and t.status in(1,7) ");
				}
				//已撤销，对应订单已取消tatus==-10、已撤单状态status==-9
				else if("1".equals(ordParam.getOrderstatus())){
					sqls.append(" and t.status in(-10,-9) ");
				}else if("-1".equals(ordParam.getOrderstatus())){
					//logger.info("==all===");
				}
				//待处理，对应其他状态
				else{
					sqls.append(" and t.status not in(-10,-9,1,7) ");
				}*/
			}
			
			if(!StringUtils.isEmpty(ordParam.getPhoneNo())){
				sqls.append(" and t.ship_mobile='"+ordParam.getPhoneNo()+"'");
			}
			if(!StringUtils.isEmpty(ordParam.getDeliveryNo())){
				// TODO Auto-generated method stub
				//物流单号
			}
			if(!StringUtils.isEmpty(ordParam.getNetPhoneNo())){
				// TODO Auto-generated method stub
				//入网号
			}
			if(!StringUtils.isEmpty(ordParam.getTerminalNo())){
				// TODO Auto-generated method stub
				//终端串码
			}
			if(!StringUtils.isEmpty(ordParam.getDlyTypeId()) && !"-1".equals(ordParam.getDlyTypeId())){
				sqls.append(" and t.shipping_id='"+ordParam.getDlyTypeId()+"'");
			}
			if(!StringUtils.isEmpty(ordParam.getPayment_id()) && !"-1".equals(ordParam.getPayment_id())){
				sqls.append(" and t.payment_id='"+ordParam.getPayment_id()+"'");
			}
		}
	}
	
	
	
	@Override
	public Order get(String orderId) {
		String sql  = SF.orderSql("SERVICE_ORDER_SELECT_BY_ID")+" and source_from is not null ";
		//String source_from = ManagerUtils.getSourceFrom();
		//List<Order> list = this.baseDaoSupport.queryForList(sql,Order.class, orderId.trim(),source_from.trim());
		List<Order> list = this.baseDaoSupport.queryForList(sql,Order.class, orderId.trim());
		/*Order order = new Order();
		try {
			if(list!=null&&list.size()>0)
			{
			  BeanUtilsBean.getInstance().copyProperties(order, list.get(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return list.size()>0?list.get(0):null;
	}
	
	//根据外部订单号获取内部订单号---zengxianlian
	@Override
	public String getOrderIdByOrderOutId(String outId){
		String sql = "select t.order_id from es_order_ext t where t.out_tid=? and t.source_from=?";
		ArrayList para = new ArrayList();
		para.add(outId);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}

	
	@Override
	public Order getSn(String ordersn) {
		String sql  = SF.orderSql("SERVICE_ORDER_SELECT_BY_SN");
		List list = this.baseDaoSupport.queryForList(sql, ordersn);
		Order order = new Order();
		try {
			if(list!=null&&list.size()>0)
			{
			BeanUtilsBean.getInstance().copyProperties(order, list.get(0));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Order order  = (Order)this.baseDaoSupport.queryForObject(sql, Order.class, ordersn.trim());
		return order;
		 
	}
	
	
	@Override
	public List<Map> listGoodsItems(String orderId) {

		String sql = SF.orderSql("SERVICE_GOODS_ITEMS_SELECT");
		String order_from = ManagerUtils.getSourceFrom();
		List<Map> itemList = this.daoSupport.queryForList(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("item_id", rs.getString("item_id"));
				map.put("order_id", rs.getString("order_id"));
				map.put("goods_id", rs.getString("goods_id"));
				map.put("spec_id", rs.getString("spec_id"));
				map.put("num", rs.getInt("num"));
				int ship_nun = rs.getInt("ship_num");
				map.put("ship_num", ship_nun);
				map.put("name", rs.getString("name"));
				map.put("price", rs.getObject("price"));
				map.put("gainedpoint", rs.getInt("gainedpoint"));
				map.put("addon", rs.getString("addon"));	
				map.put("coupon_price", rs.getDouble("coupon_price"));
				String returndnum = rs.getString("returndnum");
				map.put("returndnum", returndnum);
				String image_default = rs.getString("image");
				if(image_default!=null){
					image_default  =UploadUtil.replacePath(image_default);
				}
				map.put("image", image_default);
				map.put("sn", rs.getString("sn"));
				int can_return = ship_nun;
				if(!StringUtils.isEmpty(returndnum)){
					can_return = can_return - Integer.parseInt(returndnum);
				}
				map.put("can_return", can_return);
				return map;
			}
			
		}, orderId,order_from);
		this.orderPluginBundle.onFilter(orderId, itemList);
		return itemList;
	}
	@Override
	public List<Map> listGoodsItemsHis(String orderId) {

		String sql = SF.orderSql("SERVICE_GOODS_ITEMS_HIS_SELECT");
		String order_from = ManagerUtils.getSourceFrom();
		List<Map> itemList = this.daoSupport.queryForList(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map map = new HashMap();
				map.put("item_id", rs.getString("item_id"));
				map.put("order_id", rs.getString("order_id"));
				map.put("goods_id", rs.getString("goods_id"));
				map.put("spec_id", rs.getString("spec_id"));
				map.put("num", rs.getInt("num"));
				int ship_nun = rs.getInt("ship_num");
				map.put("ship_num", ship_nun);
				map.put("name", rs.getString("name"));
				map.put("price", rs.getObject("price"));
				map.put("gainedpoint", rs.getInt("gainedpoint"));
				map.put("addon", rs.getString("addon"));	
				map.put("coupon_price", rs.getDouble("coupon_price"));
				String returndnum = rs.getString("returndnum");
				map.put("returndnum", returndnum);
				String image_default = rs.getString("image");
				if(image_default!=null){
					image_default  =UploadUtil.replacePath(image_default);
				}
				map.put("image", image_default);
				map.put("sn", rs.getString("sn"));
				int can_return = ship_nun;
				if(!StringUtils.isEmpty(returndnum)){
					can_return = can_return - Integer.parseInt(returndnum);
				}
				map.put("can_return", can_return);
				return map;
			}
			
		}, orderId,order_from);
		this.orderPluginBundle.onFilter(orderId, itemList);
		return itemList;
	}
	
	@Override
	public List<Comments> qryCommentByOrderId(String orderId, String object_id){
		String sql = SF.orderSql("SERVICE_COMMENTS_SELECT_BY_ORDER");
        List<Comments> commnetList=null;
//        String sqlCount="select count(1) from es_comments where order_id=? and object_id=? ";
        try{
//        	int count=this.baseDaoSupport.queryForInt(sqlCount, new String[]{orderId,object_id});
//        	if (count == 0){
//        		return null;
//        	}
//            Object  obj = this.baseDaoSupport.queryForObject(sql, Comments.class, orderId,object_id);
        	commnetList=this.baseDaoSupport.queryForList(sql, Comments.class, new String[]{orderId,object_id});
//            if(null!=obj){
//                commnet=(Comments)obj;
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
		return commnetList;
	}

	
	@Override
	public List listGiftItems(String orderId) {
		String sql = SF.orderSql("SERVICE_GIFT_ITEMS_SELECT");
		return this.baseDaoSupport.queryForList(sql, orderId);
	}
	
	
	/**
	 * 读取订单日志 
	 */
	
	@Override
	public List listLogs(String orderId) {
		String sql = SF.orderSql("SERVICE_ORDER_LOGS_SELECT");
		return this.baseDaoSupport.queryForList(sql, orderId);
	}
	
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void clean(Integer[] orderId) {
		String ids = StringUtil.arrayToString(orderId, ",");
		
		String sql = SF.orderSql("SERVICE_ORDER_DELETE")+" and order_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
		
		sql = SF.orderSql("SERVICE_ORDER_ITEM_DELETE") + " and order_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
		
		sql = SF.orderSql("SERVICE_ORDER_LOG_DELETE") + " and order_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
		
		sql = SF.orderSql("SERVICE_PAYMENT_LOG_DELETE") + " and order_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
		
 
		sql = SF.orderSql("SERVICE_DELIVERY_ITEM_DELETE")+ " and delivery_id in (select delivery_id from es_delivery where order_id in (" + ids + "))";
		this.daoSupport.execute(sql);
		
		sql = SF.orderSql("SERVICE_DELIVERY_DELETE") + " and order_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql);
		
		/**
		 * -------------------
		 * 激发订单的删除事件
		 * -------------------
		 */
		this.orderPluginBundle.onDelete(orderId);
		
	}
	
	private void exec(Integer[] orderId, int disabled){
		String ids = StringUtil.arrayToString(orderId, ",");
		String sql = SF.orderSql("SERVICE_ORDER_UPDATE") + " and order_id in (" + ids + ")";
		this.baseDaoSupport.execute(sql, disabled);
	}

	
	@Override
	public void delete(Integer[] orderId) {
		exec(orderId, 1);
		
	}

	
	@Override
	public void revert(Integer[] orderId) {
		exec(orderId, 0);
		
	}	
	
	private String createSn(){
		Date now = new Date();
		String sn = this.baseDaoSupport.getSequences("seq_sn");
		return sn;
	}

	public ICartManager getCartManager() {
		return cartManager;
	}

	public void setCartManager(ICartManager cartManager) {
		this.cartManager = cartManager;
	}

	public IDlyTypeManager getDlyTypeManager() {
		return dlyTypeManager;
	}

	public void setDlyTypeManager(IDlyTypeManager dlyTypeManager) {
		this.dlyTypeManager = dlyTypeManager;
	}

	/*public IPaymentManager getPaymentManager() {
		return paymentManager;
	}

	public void setPaymentManager(IPaymentManager paymentManager) {
		this.paymentManager = paymentManager;
	}*/

	
	@Override
	public List listOrderByMemberId(String member_id) {
		String sql = SF.orderSql("SERVICE_ORDER_SELECT_BY_MEM_ID");
		List list = this.baseDaoSupport.queryForList(sql, Order.class, member_id);
		return list;
	}
	
	
	@Override
	public Map mapOrderByMemberId(String memberId) {
		String sql = SF.orderSql("SERVICE_ORDER_COUNT_BY_MEM_ID");
		Integer buyTimes = this.baseDaoSupport.queryForInt(sql, memberId);
		Double buyAmount = (Double)this.baseDaoSupport.queryForObject(SF.orderSql("SERVICE_MEM_PAYMONEY_SUM"),new DoubleMapper(), memberId);
		Map map = new HashMap();
		map.put("buyTimes", buyTimes);
		map.put("buyAmount", buyAmount);
		return map;
	}

	
	@Override
	public void edit(Order order) {
		this.baseDaoSupport.update("order", order, "order_id = " + order.getOrder_id());
		
	}

	
	@Override
	public List<Map> listAdjItem(String orderId) {
		//length(addon)  dbms_lob.getlength(addon)
		String sql  =SF.orderSql("SERVICE_ORDER_ITEM_BY_ORDER_ID");
		return this.baseDaoSupport.queryForList(sql, orderId);
	}

	//已废弃，使用CartManager.countPrice
/*	public OrderPrice countPrice(String sessionid,Integer shippingid,String regionid,boolean isProtected ) {
		
		OrderPrice orderPrice = new OrderPrice();
		
		//计算商品重量
		Double weight  = cartManager.countGoodsWeight(sessionid);
		//计算商品原始价格
		Double originalPrice = cartManager.countGoodsTotal(sessionid); 
		//计算捆绑商品的总价，并加入订单总价中
		Double pgkTotal = this.cartManager.countPgkTotal(sessionid);
		//计算团购总价
		Double groupBuyTotal = this.cartManager.countGroupBuyTotal(sessionid);
		
		originalPrice = CurrencyUtil.add(originalPrice,pgkTotal);
		originalPrice = CurrencyUtil.add(originalPrice,groupBuyTotal);
		
		Integer point  = this.cartManager.countPoint(sessionid);	
		
		//计算原始配置送费用
		Double[] priceArray = this.dlyTypeManager.countPrice(shippingid, weight, originalPrice, regionid, isProtected);
		Double dlyPrice = priceArray[0];//费送费用
		
		Member member  = UserServiceFactory.getUserService().getCurrentMember();
		Double orderTotal =null;
		if(member!=null){
			Double coupPrice =cartManager.countGoodsDiscountTotal(sessionid); //应用了商品优惠规则后的商品价格
			
			//对订单价格和积分执行优惠
			DiscountPrice discountPrice  = this.promotionManager.applyOrderPmt(coupPrice, dlyPrice,point, member.getLv_id()); 
			
			coupPrice=discountPrice.getOrderPrice() ; //优惠会后订单金额
			dlyPrice = discountPrice.getShipFee();
			point = discountPrice.getPoint();
//			orderTotal = coupPrice+ dlyPrice; //订单总金额	
			orderTotal = CurrencyUtil.add(coupPrice, dlyPrice); //订单总金额
//			Double reducePrice = originalPrice - coupPrice;
			Double reducePrice = CurrencyUtil.sub(originalPrice , coupPrice);
			
			*//**设置订单价格对象**//*
			orderPrice.setGoodsPrice(coupPrice); //商品金额，优惠后的
			orderPrice.setDiscountPrice(reducePrice); //优惠的金额
			orderPrice.setShippingPrice(dlyPrice);
			orderPrice.setPoint(point);
						
		}else{
//			orderTotal = originalPrice+ dlyPrice; //如果是非会员购买，订单总金额=商品原始金额+配送用
			orderTotal = CurrencyUtil.add(originalPrice, dlyPrice);
			orderPrice.setPoint(point);
			orderPrice.setShippingPrice(dlyPrice);
		}
		
	
		
		//计算保价费用
		if(isProtected){
			Double protectPrice = priceArray[1];
//			orderTotal+=protectPrice;
			orderTotal=CurrencyUtil.add(orderTotal, protectPrice);
			
			*//**设置保价费用**//*
			orderPrice.setProtectPrice(protectPrice);
		}
		
		
		
		*//**设置订单价格对象**//*
		orderPrice.setOriginalPrice(originalPrice);
		orderPrice.setOrderPrice(orderTotal);
		orderPrice.setWeight(weight);
		
		return orderPrice;
	}*/

	
	/**
	 * 统计订单状态 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map censusState() {
		
		//构造一个返回值Map，并将其初始化：各种订单状态的值皆为0
		Map<String,Integer> stateMap = new HashMap<String,Integer>(7);
		String[] states= {"cancel_ship","cancel_pay","not_pay","pay","ship","complete","cancellation"};
		for(String s:states){
			stateMap.put(s, 0);
		}
		
		//分组查询、统计订单状态 
		String founderSql = "";
		if(!ManagerUtils.isAdminUser()){
			String staffNo = ManagerUtils.getAdminUser().getUserid();
			founderSql = " and exists(select b.staff_no from " + this.getTableName("order_items") + " a, " 
					+ this.getTableName("goods") + " b " +
					"where a.goods_id=b.goods_id and b.staff_no=" + staffNo + " and a.order_id=t.order_id) ";
		}
		String sql =SF.orderSql("SERVICE_ORDER_STATUE_COUNT")
				+ founderSql +  " group by status";
		List<Map<String,Integer> > list  = this.daoSupport.queryForList(sql,new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String,Integer> map = new HashMap<String, Integer>();
				map.put("status", rs.getInt("status"));
				map.put("num", rs.getInt("num"));
				return map;
			}
			
		});
		
		//将list转为map
		for(Map<String,Integer> state:list){
			stateMap.put( this.getStateString( state.get("status")), state.get("num"));
		}
		
		return stateMap;
	}
	
	/**
	 * 根据订单状态值获取状态字串，如果状态值不在范围内反回null。
	 * @param state
	 * @return
	 */
	private String getStateString(Integer state){
		String str=null;
		switch (state.intValue()) {
		case -2:
			str= "cancel_ship";
			break;
		case -1:
			str= "cancel_pay";
			break;
		case 0:
			str= "not_pay";
			break;
		case 1:
			str= "pay";
			break;	
		case 2:
			str= "ship";
			break;
		case 3:
			str= "complete";
			break;
		case 4:
			str= "cancellation";
			break;
		default:
			str=null;
			break;
		}
		return str;
	}

	public OrderPluginBundle getOrderPluginBundle() {
		return orderPluginBundle;
	}

	public void setOrderPluginBundle(OrderPluginBundle orderPluginBundle) {
		this.orderPluginBundle = orderPluginBundle;
	}

	@Override
	public String export(Date start, Date end) {
		String sql  =SF.orderSql("SERVICE_ORDER_EXPORT_SELECT");
		if(start!=null){
			sql+=" and create_time>"+ start.getTime();
		}
		
		if(end!=null){
			sql+="  and create_timecreate_time<"+ end.getTime();
		}
		
		List<Order> orderList  = this.baseDaoSupport.queryForList(sql,Order.class);
		
		
		//使用excel导出流量报表
		ExcelUtil excelUtil = new ExcelUtil(); 
		
		//流量报表excel模板在类包中，转为流给excelutil
		InputStream in =FileBaseUtil.getResourceAsStream("com/enation/mall/core/service/impl/order.xls");
		
		excelUtil.openModal( in );
		int i=1;
		for(Order order :orderList){
			
			excelUtil.writeStringToCell(i, 0,order.getSn()); //订单号
			excelUtil.writeStringToCell(i, 1,DateUtil.toString(new Date(order.getCreate_time()), "yyyy-MM-dd HH:mm:ss")  ); //下单时间
			excelUtil.writeStringToCell(i, 2,order.getOrderStatus() ); //订单状态
			excelUtil.writeStringToCell(i, 3,""+order.getOrder_amount() ); //订单总额
			excelUtil.writeStringToCell(i, 4,order.getShip_name() ); //收货人
			excelUtil.writeStringToCell(i, 5,order.getPayStatus()); //付款状态
			excelUtil.writeStringToCell(i, 6,order.getShipStatus()); //发货状态
			excelUtil.writeStringToCell(i, 7,order.getShipping_type()); //配送方式
			excelUtil.writeStringToCell(i, 8,order.getPayment_name()); //支付方式
			i++;
		}
		//String target= EopSetting.IMG_SERVER_PATH;
		//saas 版导出目录用户上下文目录access文件夹
		String filename= "";
		if("2".equals( EopSetting.RUNMODE )){
			EopSite site = EopContext.getContext().getCurrentSite();
			filename ="/user/"+site.getUserid()+"/"+site.getId()+"/order";
		}else{
			filename ="/order";
		}
		File file  = new File(EopSetting.IMG_SERVER_PATH + filename);
		if(!file.exists())file.mkdirs();
		
		filename =filename+ "/order"+com.ztesoft.net.framework.util.DateUtil.getDatelineLong()+".xls";
		excelUtil.writeToFile(EopSetting.IMG_SERVER_PATH+filename);
		
		return EopSetting.IMG_SERVER_DOMAIN +filename ;
	}

	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}

	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	@Override
	public List<Map<String, Object>> listOrderOption(){
		String sql = SF.orderSql("SERVICE_OPTION_SQL_SELECT");
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> listOrderSource(){
		
		return this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_SOURCE_SQL_SELECT"));
	}


	/**
	 * 查询购物车的所有商家ID
	 * @author mochunrun
	 */
	@Override
	public List<Map<String,String>> qryStaffNoBySessionID(String sessionID) {
		String sql = SF.orderSql("SERVICE_STAFFNO_BY_SESSION_ID");
		return this.baseDaoSupport.queryForList(sql, sessionID);
	}


	/**
	 * 查询购物车的所有商家ID
	 * @author mochunrun
	 */
	@Override
	public List<Map<String,String>> qryGoodsByStaffNo(String staff_no) {
		String sql = SF.orderSql("SERVICE_GOODS_SELECT_BY_STAFFNO");
		return this.baseDaoSupport.queryForList(sql, staff_no,GlobalThreadLocalHolder.getInstance().getUUID());
		
	}
	
	
	@Override
	public List<Order> getByBatchID(String batchID) {
		String sql  =SF.orderSql("SERVICE_ORDER_SELECT_BY_BATCH_ID");
		List<Order> list = this.baseDaoSupport.queryForList(sql,Order.class,batchID);
		return list;
	}


	@Override
	public int countYestodayOrders() {
		AdminUser au = ManagerUtils.getAdminUser();
		/**
		 * 供货商管理员ID
		 */
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getParuserid();
		boolean isSupplier = ((Consts.SUPPLIER_FOUNDER==au.getFounder())||(5==au.getFounder()));
		//String sql = "select count(*) as totalAmount from es_order t where trunc(t.create_time)=trunc(sysdate-1) ";
		String sql = SF.orderSql("SERVICE_ORDER_COUNT") + " and "+DBTUtil.to_char("t.create_time", 1)+"="+DBTUtil.to_char(DBTUtil.currentDate(-1),1);
		if(isSupplier){
			sql += " and t.userid='"+userid+"'";
		}
		int totalAmount = this.baseDaoSupport.queryForInt(sql);
		return totalAmount;
	}


	@Override
	public int countTodayOrders() {
		AdminUser au = ManagerUtils.getAdminUser();
		/**
		 * 供货商管理员ID
		 */
		String userid = au.getUserid();
		if(Const.ADMIN_RELTYPE_SUPPER_STAFF.equalsIgnoreCase(au.getReltype()))
			userid = au.getParuserid();
		boolean isSupplier = ((Consts.SUPPLIER_FOUNDER==au.getFounder())||(5==au.getFounder()));
		//String sql = "select count(*) as totalAmount from es_order t where trunc(t.create_time)=trunc(sysdate) ";
		String sql = SF.orderSql("SERVICE_ORDER_COUNT") + " and "+DBTUtil.to_char("t.create_time", 1)+"="+DBTUtil.to_char(DBTUtil.currentDate(0),1);
		//"+DBTUtil.to_char("t.create_time", 1)+"="+DBTUtil.to_char(DBTUtil.currentDate(-1),1)
		if(isSupplier){
			sql += " and t.userid='"+userid+"'";
		}
		int totalAmount = this.baseDaoSupport.queryForInt(sql);
		return totalAmount;
	}
	
	@Override
	public OrderItem getOrderItemByItemId(String itemId){
		OrderItem result=null;
		result=(OrderItem)this.baseDaoSupport.queryForObject(SF.orderSql("SERVICE_ORDER_ITEM_BY_ITEM_ID"), OrderItem.class, itemId);
		return result;
	}


	@Override
	public int countOrders(OrderQueryParam ordParam) {
		List paramsC = new ArrayList();
		StringBuffer countSqls = new StringBuffer(SF.orderSql("SERVICE_ORDER_COUNT"));
		assembleOrdSqlc(ordParam, paramsC, countSqls);
		int count = this.baseDaoSupport.queryForInt(countSqls.toString(), paramsC.toArray());
		return count;
	}


	@Override
	public void exchangeDeliveryPrice(String order_id, double price) {
		String sql = SF.orderSql("SERVICE_DELIVERY_PRICE_UPDATE");
		this.baseDaoSupport.execute(sql, price,price,order_id);
	}


	@Override
	public int goodsSalseCount(String goods_id, String land_code) {
		String sql = SF.orderSql("SERVICE_GOODS_SALES_COUNT");
		if(land_code!=null && !"".equals(land_code)){
			sql += " and t.lan_id=?";
			return this.baseDaoSupport.queryForInt(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP,land_code);
		}else{
			return this.baseDaoSupport.queryForInt(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP);
		}
	}


	@Override
	public double goodsSalseMoney(String goods_id, String land_code) {
		String sql = SF.orderSql("SERVICE_SALES_MONEY_SUM");
		List<Map> list = null;
		if(land_code!=null && !"".equals(land_code)){
			sql += " and t.lan_id=?";
			list = this.baseDaoSupport.queryForList(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP,land_code);
		}else{
			list = this.baseDaoSupport.queryForList(sql, goods_id,Const.MEMBER_LV_CHINA_TELECOM_DEP);
		}
		if(list!=null&&list.size()>0){
			Map map = list.get(0);
			Object p = map.get("price");
			if(p!=null)
				return Double.parseDouble(p.toString());
		}
		return 0;
	}

	@Override
	public List<Order> qryTimeOutOrders(int beforeDays) {
		String sql = SF.orderSql("SERVICE_ORDER_SQL_SELECT") + " and t.create_time<"+DBTUtil.to_char(DBTUtil.currentDate(-beforeDays),1)+" and t.status=? and t.pay_status=?";
		return this.baseDaoSupport.queryForList(sql, Order.class, beforeDays,OrderStatus.ORDER_NOT_PAY,OrderStatus.PAY_NO);
	}


	@Override
	public void updateConfirmStaus(String order_id, String confirm_status) {
		String sql = SF.orderSql("SERVICE_CONFIRM_STATUE_UPDAATE");
		this.baseDaoSupport.execute(sql, confirm_status,order_id);
	}

	@Override
	public long countOrders(int disabled,OrderQueryParam ordParam,int event){
		
		List params = new ArrayList();
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		StringBuffer sql = new StringBuffer(SF.orderSql("QueryFlowOrder"));
		//params.add(userid);
		params.add(ManagerUtils.getSourceFrom());
		if(event==0){
			String s = " and ((l.flow_user_id = ? and l.flow_user_id is not null) or "+
					" (l.flow_user_id is null and exists (select 1 from es_order_group_role_rel t"+
					"  where t.group_id = l.flow_group_id and t.source_from = l.source_from"+
					"  and (t.userid = ? or exists(select 1 from es_user_role ur "+
					"  where ur.roleid = t.role_id and ur.userid = ? and ur.source_from = t.source_from))))) ";
			sql.append(s);
			params.add(userid);
			params.add(userid);
			params.add(userid);
			sql.append(" and l.flow_status=0 ");
		}else if(event==1){
			String s = " and ((l.flow_user_id = ? and l.flow_user_id is not null) or "+
					" (l.flow_user_id is null and exists (select 1 from es_order_group_role_rel t"+
					"  where t.group_id = l.flow_group_id and t.source_from = l.source_from"+
					"  and (t.userid = ? or exists(select 1 from es_user_role ur "+
					"  where ur.roleid = t.role_id and ur.userid = ? and ur.source_from = t.source_from))))) ";
			sql.append(s);
			params.add(userid);
			params.add(userid);
			params.add(userid);
			//sql.append(" and (l.flow_status=0 or l.flow_type='"+OrderFlowConst.FLOW_DEF_TYPE_FINISH+"') ");
			//sql.append(" and (l.flow_status = 0 or (l.flow_type = 'finish' and l.flow_status in(0,1))) ");
		}
		if("exception".equals(ordParam.getAction())){
			//异常单
			sql.append(" and t.order_record_status = "+OrderStatus.ORDER_EXCEPTION+" ");
		}else{
			//非异常单
			sql.append(" and (t.order_record_status <> "+OrderStatus.ORDER_EXCEPTION+" or t.order_record_status is null) ");
		}
		if(StringUtils.isEmpty(ordParam.getAction()) || "all".equals(ordParam.getAction()) || "day".equals(ordParam.getAction()) || "exception".equals(ordParam.getAction())){
			if("day".equals(ordParam.getAction())){
				//今天所有订单
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String now = df.format(new Date());
				sql.append(" and t.create_time >= "+DBTUtil.to_date(2));
				params.add(now+" 00:00:00");
				sql.append(" and t.create_time <= "+DBTUtil.to_date(2));
				params.add(now+" 23:59:59");
			}
		}else{
			sql.append(" and l.flow_type=? ");
			params.add(ordParam.getAction());
		}
		assembleNewOrdSql(ordParam, params, sql);
		String countSql = "select count(*) from ("+sql.toString()+") c";
		return this.baseDaoSupport.queryForLong(countSql, params.toArray());
	}
	
	@Override
	public Page queryFlowOrder(int pageNO, int pageSize,int disabled, OrderQueryParam ordParam, String order,int event){
		List params = new ArrayList();
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		StringBuffer sql = new StringBuffer(SF.orderSql("QueryFlowOrder"));
		//params.add(userid);
		params.add(ManagerUtils.getSourceFrom());
		if(event==0){
			String s = " and ((l.flow_user_id = ? and l.flow_user_id is not null) or "+
					" (l.flow_user_id is null and exists (select 1 from es_order_group_role_rel t"+
					"  where t.group_id = l.flow_group_id and t.source_from = l.source_from"+
					"  and (t.userid = ? or exists(select 1 from es_user_role ur "+
					"  where ur.roleid = t.role_id and ur.userid = ? and ur.source_from = t.source_from))))) ";
			sql.append(s);
			params.add(userid);
			params.add(userid);
			params.add(userid);
			sql.append(" and l.flow_status=0 ");
		}else if(event==1){
			String s = " and ((l.flow_user_id = ? and l.flow_user_id is not null) or "+
					" (l.flow_user_id is null and exists (select 1 from es_order_group_role_rel t"+
					"  where t.group_id = l.flow_group_id and t.source_from = l.source_from"+
					"  and (t.userid = ? or exists(select 1 from es_user_role ur "+
					"  where ur.roleid = t.role_id and ur.userid = ? and ur.source_from = t.source_from))))) ";
			sql.append(s);
			params.add(userid);
			params.add(userid);
			params.add(userid);
			//sql.append(" and (l.flow_status=0 or l.flow_type='"+OrderFlowConst.FLOW_DEF_TYPE_FINISH+"') ");
			//sql.append(" and (l.flow_status = 0 or (l.flow_type = 'finish' and l.flow_status in(0,1))) ");
		}
		if("exception".equals(ordParam.getAction())){
			//异常单
			sql.append(" and t.order_record_status = "+OrderStatus.ORDER_EXCEPTION+"");
		}else{
			//非异常单
			sql.append(" and (t.order_record_status <> "+OrderStatus.ORDER_EXCEPTION+" or t.order_record_status is null) ");
		}
		//params.add(ordParam.getAction());
		if(StringUtils.isEmpty(ordParam.getAction()) || "all".equals(ordParam.getAction()) || "day".equals(ordParam.getAction()) || "exception".equals(ordParam.getAction())){
			if("day".equals(ordParam.getAction())){
				//今天所有订单
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String now = df.format(new Date());
				sql.append(" and t.create_time >= "+DBTUtil.to_date(2));
				params.add(now+" 00:00:00");
				sql.append(" and t.create_time <= "+DBTUtil.to_date(2));
				params.add(now+" 23:59:59");
			}
		}else if("rv".equals(ordParam.getAction())){
			//已回访的订单
			sql.append(" and exists(select 1 from es_order_return_visit orv where orv.order_id=t.order_id and orv.source_from=t.source_from) ");
		}else{
			sql.append(" and l.flow_type=? ");
			params.add(ordParam.getAction());
		}
		
		assembleNewOrdSql(ordParam, params, sql);
		String countSql = "select count(*) from ("+sql.toString()+") c";
		if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())){
			order = StringUtil.isEmpty(order)?" t.create_time desc":order;
		}else{
			order = StringUtil.isEmpty(order)?" t.create_time desc":order;
		}
		sql.append(" order by ").append(order);
		Page page  =this.baseDaoSupport.queryForCPage(sql.toString(), pageNO, pageSize,  Order.class, countSql,params.toArray());
		return page;
	}
	
	
	@Override
	public Page listGroupOrder(int pageNO, int pageSize, int disabled,
			OrderQueryParam ordParam, String order) {
		List params = new ArrayList();
		AdminUser au = ManagerUtils.getAdminUser();
		String userid = au.getUserid();
		StringBuffer sql1 = new StringBuffer(SF.orderSql("SERVICE_ORDER_SQL_SELECT_BY_USER"));
		params.add(userid);
		assembleOrdSqlGroup(ordParam, params, sql1);
		StringBuffer sql2 = new StringBuffer(SF.orderSql("SERVICE_GROUP_ORDER_SQL_SELECT"));
		params.add(userid);
		params.add(userid);
		assembleOrdSqlGroup(ordParam, params, sql2);
		if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())){
			order = StringUtil.isEmpty(order)?" create_time desc":order;
		}else{
			order = StringUtil.isEmpty(order)?" create_time desc":order;
		}
		//StringBuffer sqls = new StringBuffer(SF.orderSql("SERVICE_ORDER_MEMBER_SQL_SELECT"));
		//StringBuffer countSqls = new StringBuffer(SF.orderSql("SERVICE_GROUP_ORDER_SQL_COUNT"));
		
		StringBuffer sqls = new StringBuffer("select ou.*,um.uname from (");
		sqls.append(sql1).append(" union all ").append(sql2).append(" ) ou left join es_member um on(ou.member_id=um.member_id and um.source_from = '" + ManagerUtils.getSourceFrom() + "')");
		
		StringBuffer countSqls = new StringBuffer("select count(*) from (");
		countSqls.append(sql1).append(" union all ").append(sql2).append(" ) od ");
		
		if(!"wp_type_5".equals(ordParam.getAction()) && !"wp_type_6".equals(ordParam.getAction())){
			/*sqls.append(" where create_type<>'6'");
			countSqls.append(" where create_type<>'6'");*/
            sqls.append(" where create_type!='6'");
            countSqls.append(" where create_type!='6'");
		}
		sqls.append(" order by "+ order);
		
		Page page  =this.baseDaoSupport.queryForCPage(sqls.toString(), pageNO, pageSize,  Order.class, countSqls.toString(),params.toArray());
		return page;
	}
	
	private void assembleOrdSqlGroup(OrderQueryParam ordParam, List params, StringBuffer sqls) {
		if("wait_audit".equals(ordParam.getAction())) //待审核
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_COLLECT);
		}else if("wait_pay".equals(ordParam.getAction())) //待支付
		{
			assembleNewOrdSql(ordParam, params, sqls);
			//查询待支付的订单
			sqls.append(" and exists(select 1 from es_payment_logs dy where dy.order_id=t.order_id and dy.status=-1)");
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
		}else if("wp_type_5".equals(ordParam.getAction())){
			sqls.append(" and create_type='5' ");
			assembleNewOrdSql(ordParam, params, sqls);
			//查询待支付的订单
			sqls.append(" and exists(select 1 from es_payment_logs dy where dy.order_id=t.order_id and dy.status=-1)");
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
		}else if("wp_type_6".equals(ordParam.getAction())){
			sqls.append(" and create_type='6'" );
			//查询待支付的订单
			sqls.append(" and exists(select 1 from es_payment_logs dy where dy.order_id=t.order_id and dy.status=-1)");
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
		}else if("wait_accept".equals(ordParam.getAction())) //待备货
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? and pay_status=?");
			params.add(OrderStatus.ORDER_PAY);
			params.add(OrderStatus.PAY_YES);
		}else if("wait_ship".equals(ordParam.getAction())) //待发货 
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and exists(select 1 from es_delivery dy where dy.order_id=t.order_id and dy.ship_status=-1)");
			sqls.append(" and (status = ? or (status = ? and ship_status=?))");
			params.add(OrderStatus.ORDER_ACCEPT);
			params.add(OrderStatus.ORDER_SHIP);
			params.add(OrderStatus.SHIP_PARTIAL_SHIPED);
		}else if("wait_confir_ship".equals(ordParam.getAction())) //待确认收货 
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? and ship_status=?");
			params.add(OrderStatus.ORDER_SHIP);
			params.add(OrderStatus.SHIP_YES);
		}else if("wait_finish".equals(ordParam.getAction())) //待确认完成
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? and ship_status=?");
			//params.add(OrderStatus.ORDER_SHIP);
			params.add(OrderStatus.ORDER_CONFIRM_SHIP);
			params.add(OrderStatus.SHIP_YES);
		}else if("cancel".equals(ordParam.getAction())) //订单取消 
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_COLLECT);
		}else if("widhdraw".equals(ordParam.getAction())) //订单撤单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_NOT_PAY);
		}else if("finish".equals(ordParam.getAction())) //订单完成
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and status = ? ");
			params.add(OrderStatus.ORDER_COMPLETE);
		}else if("refund".equals(ordParam.getAction())) //待退费订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and pay_status in(1,3,4) ");
			sqls.append(" and exists (select 1 from es_order_rel eor,es_order_apply eop where eor.z_order_id=eop.order_apply_id and eop.a_order_item_id=t.order_id and eop.apply_state=? and eop.SERVICE_TYPE=? and upper(eor.Z_TABLE_NAME)='ES_ORDER_APPLY') ");
			params.add(OrderStatus.ORDER_APPLY_STATUS_1);
			params.add(OrderStatus.ORDER_TYPE_2);
		}else if("wait_change_ship".equals(ordParam.getAction())) //待换货订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
			
			sqls.append(" and exists (select 1 from es_order_rel eor,es_order_apply eop where eor.z_order_id=eop.order_apply_id and eop.a_order_item_id=t.order_id and eop.apply_state=? and eop.SERVICE_TYPE=? and upper(eor.Z_TABLE_NAME)='ES_ORDER_APPLY') ");
			params.add(OrderStatus.ORDER_APPLY_STATUS_1);
			params.add(OrderStatus.ORDER_TYPE_3);
		}else if("wait_refund_ship".equals(ordParam.getAction())) //待退货订单
		{
			
			assembleNewOrdSql(ordParam, params, sqls);
			/*sqls.append(" and (status = ? or status = ?) ");
			params.add(OrderStatus.ORDER_COMPLETE);
			params.add(OrderStatus.ORDER_CONFIRM_SHIP);*/
			sqls.append(" and exists (select 1 from es_order_rel eor,es_order_apply eop where eor.z_order_id=eop.order_apply_id and eop.a_order_item_id=t.order_id and eop.apply_state=? and eop.SERVICE_TYPE=? and upper(eor.Z_TABLE_NAME)='ES_ORDER_APPLY') ");
			params.add(OrderStatus.ORDER_APPLY_STATUS_1);
			params.add(OrderStatus.ORDER_TYPE_4);
			
		}else if("can_returned".equals(ordParam.getAction())){
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and (t.status in(7,6) or t.ship_status in(3,5)) ");
		}else if("all".equals(ordParam.getAction()) || StringUtils.isEmpty(ordParam.getAction())) //全部订单
		{
			assembleNewOrdSql(ordParam, params, sqls);
		}else if("exception".equals(ordParam.getAction())){
			assembleNewOrdSql(ordParam, params, sqls);
			sqls.append(" and t.status = "+OrderStatus.ORDER_EXCEPTION+"");
		}else if("day".equals(ordParam.getAction())){
			//今天所有订单
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String now = df.format(new Date());
			sqls.append(" and t.create_time >= "+DBTUtil.to_date(2));
			params.add(now+" 00:00:00");
			sqls.append(" and t.create_time <= "+DBTUtil.to_date(2));
			params.add(now+" 23:59:59");
		}
	}


	@Override
	public List<PaymentLog> listOrderPayMentLog(String order_id) {
		List<PaymentLog> payments = this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_PAYMENT_LOGS_SELECT") + " and l.source_from is not null ", PaymentLog.class, order_id);
		return payments;
	}

	@Override
	public PaymentLog qryNotPayPaymentLog(String payType,String objectId){
		String sql = SF.orderSql("SERVICE_NOT_PAYMENT_LOGS_SELECT");
		if("0".equals(payType)){
			sql += " and o.order_id=? ";
		}else{
			sql += " and o.batch_id=?";
		}
		List<PaymentLog> list = this.baseDaoSupport.queryForList(sql, PaymentLog.class, objectId);
		return list.size()>0?list.get(0):null;
	}
	
	@Override
	public PaymentLog qryPaymentLogByTransactionId(String transaction_id){
		String sql = SF.orderSql("SERVICE_PAYMENT_LOGS_BY_TRANS_ID");
		List<PaymentLog> list = this.baseDaoSupport.queryForList(sql, PaymentLog.class, transaction_id);
		return list.size()>0?list.get(0):null;
	}
	
	
	@Override
	public List listException(){
		
		String sql = SF.orderSql("LIST_ORDER_EXCEPTION");
		
		return this.baseDaoSupport.queryForList(sql);
	}
	
	
	
	
	@Override
	public Page listOrderException(OrderException orderException,int page,int pageSize){
		
		StringBuffer sql = new StringBuffer(SF.orderSql("LIST_ORDER_EXCEPTION"));
		StringBuffer cSql = new StringBuffer(SF.orderSql("LIST_ORDER_EXCEPTION_COUNT"));
		StringBuffer where = new StringBuffer();
		
		if(StringUtils.isNotEmpty(orderException.getException_name())){
			where.append(" AND exception_name LIKE '%"+orderException.getException_name()+"%'");
		}
		if(StringUtils.isNotEmpty(orderException.getException_code())){
			where.append(" AND exception_code LIKE '%"+orderException.getException_code()+"%'");
		}
		
		where.append(" ORDER BY create_date DESC");
		
		return this.baseDaoSupport.queryForCPage(sql.append(where).toString(), page, pageSize, 
				OrderException.class, cSql.append(where).toString());
	}
	
	@Override
	public OrderException qryExceptionById(String exception_id){
		String sql = SF.orderSql("QRY_ORDER_EXCEPTION");
		return (OrderException) this.baseDaoSupport.queryForObject(sql, OrderException.class, exception_id);
	}
	
	@Override
	public void saveOrderException(OrderException orderException){
		if(StringUtils.isNotEmpty(orderException.getException_id())){
			this.baseDaoSupport.update("es_order_exception", orderException, " exception_id = '"+orderException.getException_id()+"'");
		}else{
			orderException.setException_id(this.baseDaoSupport.getSequences("SEQ_ES_ORDER_EXCEPTION"));
			orderException.setCreate_date(DBTUtil.current());
			orderException.setSource_from(ManagerUtils.getSourceFrom());
			this.baseDaoSupport.insert("es_order_exception", orderException);
		}
	}
	
	@Override
	public List<TestRule> qryYuyueList(){
		String sql= "select t.log_id,t.BUSS_NAME, t.BUSS_ADDRESS, t.COMPANY_POST, t.COMPANY_TYPE, t.COMPANY_TELE, t.UNAME, t.UMOBILE, t.UPHONE, t.FAX, t.EMAIL, t.UADDRESS, t.UPOST,g.name item_id from es_test_rule t left join es_goods g on g.goods_id = t.log_id where 1=1 and t.log_id is not null and t.app_code=? and t.source_from=? and rownum<2 order by t.create_time desc";
		List<TestRule> list= this.baseDaoSupport.queryForList(sql,TestRule.class,"wssmall_ydzq",ManagerUtils.getSourceFrom());
		return list;
	}
	
	@Override
	public void delOrderException(String exception_id){
		String sql = SF.orderSql("DELETE_ORDER_EXCEPTION");
		
		this.baseDaoSupport.execute(sql, exception_id);
	}
	
	
	@Override
	public int qryExceptionCount(OrderException orderException){
		
		StringBuffer sql = new StringBuffer(SF.orderSql("LIST_ORDER_EXCEPTION_COUNT"));
		sql.append(" AND exception_code = '"+orderException.getException_code()+"'");
		if(StringUtils.isNotEmpty(orderException.getException_id())){
			sql.append(" AND exception_id <> '"+orderException.getException_id()+"'");
		}
		return this.baseDaoSupport.queryForInt(sql.toString());
	}
	
	
	
	
	
	
	
	
	public GoodsInf getGoodsServ() {
		return goodsServ;
	}


	public void setGoodsServ(GoodsInf goodsServ) {
		this.goodsServ = goodsServ;
	}


	@Override
	public List qryGroupByOrder(String user_id, String group_id) {
		String sql = SF.orderSql("SERVICE_GROUP_SELECT_BY_ORDER");
		return this.baseDaoSupport.queryForList(sql, user_id,group_id,user_id,group_id);
	}


	@Override
	public void setOrderSecurity(Order order) {
		AdminUser user = ManagerUtils.getAdminUser();
		if(order!=null){
			if(user.getUserid().equals(order.getPay_user_id()) || this.qryGroupByOrder(user.getUserid(), order.getPay_group_id()).size()>0){
				order.setCanPay("yes");
			}else{
				order.setCanPay("no");
			}
			if(user.getUserid().equals(order.getShip_user_id()) || this.qryGroupByOrder(user.getUserid(), order.getShip_group_id()).size()>0){
				order.setCanShip("yes");
			}else{
				order.setCanShip("no");
			}
		}
	}
	
	@Override
	public void updatePaymentMoney(String order_id,String transactionId,double pay_money,String payment_id){
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_PAYMENT_MONEY_UPDATE"), transactionId,pay_money,payment_id);
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_PAYMONEY_UPDATE"), transactionId,pay_money,order_id);
	}


	public PromotionInf getPromotionServ() {
		return promotionServ;
	}


	public void setPromotionServ(PromotionInf promotionServ) {
		this.promotionServ = promotionServ;
	}


	public PaymentCfgInf getPaymentCfgServ() {
		return paymentCfgServ;
	}


	public void setPaymentCfgServ(PaymentCfgInf paymentCfgServ) {
		this.paymentCfgServ = paymentCfgServ;
	}


	@Override
	public boolean isCreateOrder(String service_code, String goods_ids) {
		//add by wui广东联通订单系统强制需要创建订单，广东联通商品数据太多，很消耗性能
		if("ECS".equals(ManagerUtils.getSourceFrom()) || "ECSORD".equals(ManagerUtils.getSourceFrom()))
			return true;
		String sql = SF.orderSql("IS_CREATE_ORDER").replace(":instr", goods_ids);
		return this.baseDaoSupport.queryForInt(sql, service_code)>0;
	}
	
	private ITplInstManager tplInstManager; 

	@Override
	public List<AttrInst> getOuterAttrInst(OrderOuter orderOuter) {
		if(OrderThreadLocalHolder.getInstance().getOrderCommonData().isOuterOrder(orderOuter)){
			return tplInstManager.getOuterAttrInst(orderOuter.getOrder_id());
		}else{
			return orderOuter.getOuterAttrInsts();
		}
	}
	
	@Override
	public void saveOrderOuter(OrderOuter orderOuter) {
		if(OrderThreadLocalHolder.getInstance().getOrderCommonData().isOuterOrder(orderOuter)){
			this.baseDaoSupport.insert("ORDER_OUTER", orderOuter);
		}
	}

	@Override
	public void saveOuterAttrInst(OrderOuter orderOuter) {
		if(OrderThreadLocalHolder.getInstance().getOrderCommonData().isOuterOrder(orderOuter)){
			for(AttrInst a:orderOuter.getOuterAttrInsts()){
				this.baseDaoSupport.insert("es_outer_attr_inst", a);
			}
		}
	}
	
	@Override
	public void saveOrderFail(OrderExcepCollect orderExcepCollect){
		orderExcepCollect.setSource_from(ManagerUtils.getSourceFrom());
		orderExcepCollect.setCreate_date(DBTUtil.current());
		this.baseDaoSupport.insert("es_order_exception_collect", orderExcepCollect);
	}


	public ITplInstManager getTplInstManager() {
		return tplInstManager;
	}

	public void setTplInstManager(ITplInstManager tplInstManager) {
		this.tplInstManager = tplInstManager;
	}


	@Override
	public List<ReturnsOrder> memberReturnedOrders(String member_id) {
		return this.baseDaoSupport.queryForList(SF.orderSql("SERVICE_RETURN_ORDER_MEM"), ReturnsOrder.class, member_id);
	}


	@Override
	public Page pageMemberOrders(String member_id,String pageNo, String pageSize, String status) {
		// TODO Auto-generated method stub
		String sql = null;
		Page rpage = null;
		List<Map> list = null;
		if(StringUtil.isEmpty(status)){
			sql = SF.memberSql("MEMBER_PAGE_ORDERS"); //and status<>'-10'
			rpage = this.baseDaoSupport.queryForPage(sql, Integer.parseInt(pageNo), Integer.parseInt(pageSize), member_id);
			list = (rpage.getResult());
		}else{
			sql = SF.memberSql("MEMBER_PAGE_ORDERS_STATUS");
			rpage = this.baseDaoSupport.queryForPage(sql, Integer.parseInt(pageNo), Integer.parseInt(pageSize), member_id,status);
			list = (rpage.getResult());
		}
		return rpage;
	}
	
	@Override
	public OrderOuter getOrderOuterByGoodsId(String product_id ,List<OrderOuter> orderOuters){
		for (OrderOuter orderOuter:orderOuters) {
			if(product_id.equals(orderOuter.getProduct_id()))
				return orderOuter;
		}
		return null;
	}
	
	
	@Override
	public OrderOuter qryOrderOuter(String order_id){
		String sql = SF.orderSql("ORDER_OUTER_BY_ORDER_ID");
		List<OrderOuter> list = this.baseDaoSupport.queryForList(sql, order_id);
		OrderOuter orderOuter = new OrderOuter();
		if(!ListUtil.isEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				orderOuter = list.get(0);
			}
		}
		return orderOuter;
	}
	
	@Override
	public void updateOrderStatus(int status,String order_id){
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_STATUS_UPDATE"),status,order_id);
	}
	
	@Override
	public void updateOrderAcceptStatus(int accept_status,String order_id){
		this.baseDaoSupport.execute(SF.orderSql("SERVICE_ORDER_ACCEPT_STATUS_UPDATE"),accept_status,order_id);
	}
	
	@Override
	public int countHasShipOrderItems(String order_id){
		String sql = SF.orderSql("CountHasShipOrderItems");
		return this.baseDaoSupport.queryForInt(sql, order_id);
	}
	
	@Override
	public String getGoodsTypeCode(String goods_id){
		String sql = SF.orderSql("QUERYGOODSTYPECODE");
		List<Map> list = this.baseDaoSupport.queryForList(sql, goods_id,ManagerUtils.getSourceFrom());
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			Object obj = map.get("type_code");
			if(obj!=null){
				return String.valueOf(obj);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	@Override
	public void updateOrderDisabledStatus(String order_id,int disabled){
		String sql = SF.orderSql("UpdateOrderDisabledStatus");
		this.baseDaoSupport.execute(sql, disabled,order_id);
	}
	
	@Override
	public List<OrderHandleLogsReq> queryOrderHandlerLogs(String order_id,String handler_type,String is_his_order){
		//历史单查询
		if(StringUtils.isNotEmpty(is_his_order) && "1".equals(is_his_order)){
			String sql = " select * from (select t.order_id,t.flow_id,t.flow_trace_id,t.comments,t.create_time,t.op_id, c.realname op_name,t.type_code,t.handler_type, t.exception_id,d.pname type_name,n.pname trace_name "+
					"   from es_order_handle_logs_his t left join es_dc_public_ext d on (t.source_from = d.source_from and d.pkey = t.type_code and d.stype = 'DIC_HANDLE_TYPE_' || t.flow_trace_id)                      "+
					"   left join es_dc_public_ext n on (t.source_from = n.source_from and t.flow_trace_id = n.pkey and n.stype = 'DIC_ORDER_NODE')                                                                   "+
					"   left join es_adminuser c on t.op_id = c.userid where t.order_id = ? and t.source_from = '"+ManagerUtils.getSourceFrom()+"' order by t.create_time desc) ";
			return this.baseDaoSupport.queryForList(sql, OrderHandleLogsReq.class, order_id);
		}else if(!"DIC_HANDLE_TYPE".equalsIgnoreCase(handler_type)){
			String sql = SF.orderSql("QUERYORDERHANDLERLOGS");
			List params = new ArrayList();
			params.add(order_id);
			if(!StringUtil.isEmpty(handler_type)){
				sql += " and t.handler_type=?";
				params.add(handler_type);
			}
			sql += " order by t.create_time desc";
			return this.baseDaoSupport.queryForList(sql, OrderHandleLogsReq.class, params.toArray());
		}else{
			String sql = " select * from (select t.order_id,t.flow_id,t.flow_trace_id,t.comments,t.create_time,t.op_id, c.realname op_name,t.type_code,t.handler_type, t.exception_id,d.pname type_name,n.pname trace_name "+
					"   from es_order_handle_logs t left join es_dc_public_ext d on (t.source_from = d.source_from and d.pkey = t.type_code and d.stype = 'DIC_HANDLE_TYPE_' || t.flow_trace_id)                      "+
					"   left join es_dc_public_ext n on (t.source_from = n.source_from and t.flow_trace_id = n.pkey and n.stype = 'DIC_ORDER_NODE')                                                                   "+
					"   left join es_adminuser c on t.op_id = c.userid where t.order_id = ? and t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.handler_type = 'DIC_HANDLE_TYPE'                                     "+
					" union all                                                                                                                                                                                       "+
					" select t.order_id,t.flow_id,t.flow_trace_id,t.comments,t.create_time,t.op_id, c.realname op_name,t.type_code,t.handler_type, t.exception_id,decode(d.pname,'正常单','取消退单',d.pname) type_name,'退单处理' trace_name             "+
					"   from es_order_handle_logs t left join es_dc_public_ext d on (t.source_from = d.source_from and d.pkey = t.type_code and d.stype = 'order_refund_status')                                      "+
					"   left join es_adminuser c on t.op_id = c.userid where t.order_id = ? and t.source_from = '"+ManagerUtils.getSourceFrom()+"' and t.handler_type in('returned','cancelreturned')) logs order by logs.create_time desc       ";
			return this.baseDaoSupport.queryForList(sql, OrderHandleLogsReq.class, order_id,order_id);
		}
	}
	
	@Override
	public List<OrderExceptionCollectReq> queryOrderExceptionLogs(OrderExceptionLogsListReq req) {
		// TODO Auto-generated method stub
		
		List<OrderExceptionCollectReq> list = null;
		StringBuilder sql = new StringBuilder("select t.*, d.pname exception_name, n.pname trace_name");
		sql.append(" from es_order_exception_collect t");
		sql.append(" left join es_dc_public_ext d on(d.pkey=t.EXCEPTION_ID and d.stype='DIC_ORDER_EXCEPTION_TYPE')");
		sql.append(" left join es_dc_public_ext n on(t.flow_trace_id=n.pkey and n.stype='DIC_ORDER_NODE')");
		sql.append(" where 1=1");
		if (req != null) {
			sql.append(" and t.order_id = '"+req.getOrder_id()+"'");
			sql.append(" and t.flow_trace_id = '"+req.getFlow_trace_id()+"'");
			list = baseDaoSupport.queryForList(sql.toString(), OrderExceptionCollectReq.class);
		}
		return list;
	}
	
	@Override
	public List<OutcallLogsReq> queryOutcallLogs(String order_id) {
		List<OutcallLogsReq> list = null;
		StringBuilder sql = new StringBuilder("select t.DEAL_TIME deal_time, (select e.REALNAME from es_adminuser e where e.USERNAME=t.DEAL_OPER_ID) deal_name, t.OPER_TIME oper_time, (select e.REALNAME from es_adminuser e where e.USERID=t.OPER_ID) oper_name");
		sql.append(" , t.IS_FINISH is_finish, t.OPER_REMARK oper_remark, t.DEAL_REMARK outcall_type");
		sql.append(" from es_order_outcall_log t");
		/*sql.append(" where t.IS_FINISH=1");*/
		if(order_id != null){
			sql.append(" where t.ORDER_ID = '"+order_id+"'");
			sql.append(" order by is_finish");
			logger.info(sql);
			list = baseDaoSupport.queryForList(sql.toString(), OutcallLogsReq.class);
		}
		return list;
	}
	
	/**
	 * 订单独立退单,状态变更
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void chargebackStackChg(String flag, String zb_order_id) {
		if("notify_zb".equals(flag)){
			this.baseDaoSupport.execute(SF.orderSql("CHARGEBACK_APPLY_STATUS_UPDATE"), zb_order_id);
		}
		if("zb_return".equals(flag)){
			this.baseDaoSupport.execute(SF.orderSql("CHARGEBACK_RET_STATUS_UPDATE"), zb_order_id);
		}
		if("zb_exception".equals(flag)){
			this.baseDaoSupport.execute(SF.orderSql("CHARGEBACK_APPLY_EXCEPTION_STATUS_UPDATE"), zb_order_id);
		}
		
	}
	
	@Override
	public String getOrderSysCode(String order_id){
		String sql = SF.orderSql("QUERYORDERSYSCODE");
		List<Map> list = this.baseDaoSupport.queryForList(sql, order_id,ManagerUtils.getSourceFrom(),order_id,ManagerUtils.getSourceFrom());
		if(list!=null && list.size()>0){
			Map map = list.get(0);
			String sys_code = String.valueOf(map.get("sys_code"));
			return sys_code;
		}
		return null;
	}
	
	@Override
	public void updateExceptionStatus(String order_id,int exception_status){
		String sql = SF.orderSql("UpdateExceptionStatus");
		this.baseDaoSupport.execute(sql, exception_status,order_id);
	}

	public IWarehouseService getWarehouseService() {
		return warehouseService;
	}

	public void setWarehouseService(IWarehouseService warehouseService) {
		this.warehouseService = warehouseService;
	}
	
	

	public Map getOrderAttrs(String order_attrs){
		Map result = new HashMap();
		if(order_attrs != null && !"".equals(order_attrs) && !"{}".equals(order_attrs) && !"null".equals(order_attrs)){
			JSONObject jsonObject =  JSONObject.fromObject(order_attrs);
			Iterator it = jsonObject.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				result.put(key, jsonObject.get(key));
			}
		}
		return result;
	}
	
	public Map getGoodsAttrs(String goods_attrs){
		Map result = new HashMap();
		if(goods_attrs != null && !"".equals(goods_attrs) && !"[]".equals(goods_attrs) && !"null".equals(goods_attrs)){
			JSONArray jsonArray = JSONArray.fromObject(goods_attrs);
			JSONObject jsonObject = (JSONObject)jsonArray.get(0);
			Iterator it = jsonObject.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				result.put(key, jsonObject.get(key));
			}
		}
		return result;
	}

	
	public List<Map> getConsts(String key){
		DcPublicSQLInfoCacheProxy dcPublicSqlCache = new DcPublicSQLInfoCacheProxy();
        List<Map> list = dcPublicSqlCache.getDropdownData(key);
        return list;
	}
	
	
	/**
	 * 按表名分组属性数据并插入attr_inst表
	 * @作者 MoChunrun
	 * @创建日期 2014-9-22 
	 * @param attrs
	 * @return
	 */
	@Override
	public OrderAttrHanderResp groupAndInsertAttrByTable(int index,List<AttrDef> attrs,Map values,String order_id,String inst_id,String goods_id,String pro_goods_id,String order_from,List<AttrTable> tbList,String goods_pro_id,String product_pro_id){
		OrderAttrHanderResp handlerResp = new OrderAttrHanderResp();
		Map<String,List<AttrDef>> map = new HashMap<String,List<AttrDef>>();
		Map<String,List<AttrTable>> atbMap = groupAttrTable(tbList);
		
		List<AttrInstBusiRequest> attrInstList = new ArrayList<AttrInstBusiRequest>();
		if(attrs!=null && attrs.size()>0){
			//long start0 = System.currentTimeMillis();
			for(AttrDef ad:attrs){
				long start = System.currentTimeMillis();
				String key = ad.getField_name();
				Object obj = values.get(key);
				String value = obj==null?null:String.valueOf(obj);
				String i_value = null;
				String value_desc = null;
				if(index==0){
					if(!StringUtil.isEmpty(ad.getHandler_class())){
						
						if("commonAttrHander".equals(ad.getHandler_class()))
							i_value=value;
						else{
							try{
								AttrSwitchParams params = new AttrSwitchParams();
								params.setHander_class(ad.getHandler_class());
								params.setOrder_id(order_id);
								params.setGoods_id(goods_id);
								params.setInst_id(inst_id);
								params.setOrder_from(order_from);
								params.setOrderAttrValues(values);
								params.setPro_goods_id(pro_goods_id);
								params.setValue(value);
								params.setAttrDef(ad);
								
								IAttrHandler handler = SpringContextHolder.getBean(ad.getHandler_class());
								AttrInstLoadResp resp = handler.handler(params);
								long end = System.currentTimeMillis();
	//							logger.info("订单属性转换["+ad.getHandler_class()+"]扩展属性==========>："+(end-start));
								if(resp ==null)
									continue;
								i_value = resp.getField_value();
								value_desc = resp.getField_value_desc();
								values.put(ad.getField_name(), i_value);
							}catch(Exception ex){ //业务异常
								ex.printStackTrace();
								i_value=value;
							}
						}
					}else{
						i_value=value;
					}
				}else{
					i_value=value;
				}
				if(StringUtil.isEmpty(value_desc)){
					String attr_code = ad.getAttr_code();
					if(!StringUtil.isEmpty(i_value) && !StringUtil.isEmpty(attr_code)){
						List<Map> list = getConsts(attr_code);
						if(list!=null && list.size()>0){
							for(Map am:list){
								String a_value = (String) am.get("value");
								if(i_value.equals(a_value)){
									value_desc = (String) am.get("value_desc");
									break ;
								}
							}
						}
					}
				}
				
				if(StringUtil.isEmpty(i_value) && (StringUtil.isEmpty(ad.getHandler_class()) || "commonHander".equals(ad.getHandler_class())))
					continue;
				String attr_inst_from_redis ="yes";//cacheUtil.getConfigInfo(EcsOrderConsts.ATTR_INST_FROM_REDIS);
				//add by wuiredis执行
				//为空的数据也插到数据实例表
				if(index==0 && "-1".equals(ad.getSub_attr_spec_type())){
					//如果是货品，侧只插第一个公共的属性 inst_id改为order_id
					attrInstList.add(this.packageAttrInst(ad, i_value, value, order_id, order_id,value_desc));
				}else if(!"-1".equals(ad.getSub_attr_spec_type())){
					attrInstList.add(this.packageAttrInst(ad, i_value, value, order_id, inst_id,value_desc));
				}
				if(!StringUtil.isEmpty(i_value))values.put(key, i_value);
				
				if(atbMap==null)continue ;
				List<AttrTable> list = atbMap.get(ad.getField_attr_id());
				if(list==null || list.size()==0)continue ;
				if(StringUtil.isEmpty(i_value))continue;//如果值为空不加到扩展表
				
				for(AttrTable atb:list){
					String tableName = atb.getTable_name();
					if(!StringUtil.isEmpty(tableName)){
						List<AttrDef> alist = map.get(tableName);
						if(alist==null){
							alist = new ArrayList<AttrDef>();
							map.put(tableName, alist);
						}
						ad.setTable_field_name(atb.getField_name());
						alist.add(ad);
					}
				}
				
			}
			//批量插入
			handlerResp.setAttInstList(attrInstList);
			
		}
		handlerResp.setAttrDefTable(map);
		//return map;
		return handlerResp;
	}

	/**
	 * 插入订单属性实例表
	 * @作者 MoChunrun
	 * @创建日期 2014-9-28 
	 * @param attrDef
	 * @param i_value
	 * @param o_value
	 * @param order_id
	 * @param inst_id
	 */
	static AtomicInteger icInt = new AtomicInteger();
	private AttrInstBusiRequest packageAttrInst(AttrDef attrDef,String i_value,String o_value,String order_id,String inst_id,String value_desc){
		int curValue =icInt.incrementAndGet();
		AttrInstBusiRequest attrInst = new AttrInstBusiRequest();
		attrInst.setAttr_inst_id(attrDef.getField_attr_id());
		attrInst.setOrder_id(order_id);
		attrInst.setInst_id(inst_id);
		attrInst.setAttr_spec_id(attrDef.getAttr_spec_id());
		attrInst.setField_attr_id(attrDef.getField_attr_id());
		attrInst.setField_name(attrDef.getField_name());
		attrInst.setFiled_desc(attrDef.getField_desc());
		attrInst.setField_value(i_value);
		attrInst.setSec_field_value(o_value);
		attrInst.setField_value_desc(value_desc);
		attrInst.setCreate_date(DBTUtil.current());
		//add by wui首次规格数据填充
		attrInst.setSpec_is_null(attrDef.getIs_null());
		attrInst.setSpec_is_edit(attrDef.getIs_edit());
		return attrInst;
	}
	
	private Map<String,List<AttrTable>> groupAttrTable(List<AttrTable> list){
		if(list!=null && list.size()>0){
			Map<String,List<AttrTable>> map = new HashMap<String,List<AttrTable>>();
			for(AttrTable at:list){
				List<AttrTable> ats = map.get(at.getAttr_def_id());
				if(ats==null){
					ats = new ArrayList<AttrTable>();
					map.put(at.getAttr_def_id(), ats);
				}
				ats.add(at);
			}
			return map;	
		}else{
			return null;
		}
	}
	
	public static boolean _CACHE_FLAG = true;
	public static final ThreadLocal<Boolean> CACHE_LOCAL = new ThreadLocal<Boolean>();
	private INetCache cache = CacheFactory.getCacheByType("");
	public static int space = Const.CACHE_SPACE;
	
	@Override
	public List<AttrDef> queryAttrDef(String space_type,String sub_space_type, String attr_space_id) {
		List<AttrDef> list = null;
		String CACHE_KEY = CacheUtils.ATTR_SPACE_TYPE+space_type+"_"+attr_space_id;
		if(!StringUtil.isEmpty(sub_space_type))CACHE_KEY += "_"+sub_space_type;
		Boolean tFlag = CACHE_LOCAL.get();
		if(tFlag==null)tFlag = false;
		if(_CACHE_FLAG && !tFlag){
			list =(List<AttrDef>)cache.get(space, CACHE_KEY);
			if(list!=null)return list;
		}
		String sql = SF.orderSql("QUERY_ATTE_DEF_COM");
		//公共属性与自定议属性
		if(!StringUtil.isEmpty(sub_space_type))sql += " and (t.sub_attr_spec_type='"+sub_space_type+"' or t.sub_attr_spec_type='"+Const.COMM_SUB_SPACE_ID+"')";
		sql += " order by t.sort asc";
		list = this.baseDaoSupport.queryForList(sql, AttrDef.class, space_type,attr_space_id,ManagerUtils.getSourceFrom());
		if(list!=null && _CACHE_FLAG){
			CacheList<AttrDef> clist = new CacheList<AttrDef>();
			clist.addAll(list);
			cache.set(space, CACHE_KEY,clist);
		}
		return list;
	}
	
	@Override
	public List<AttrTable> queryAttrTable(String space_type,String sub_space_type,String attr_space_id){
		List<AttrTable> list = null;
		String CACHE_KEY = CacheUtils.ATTR_TABLE_SPACE_TYPE+space_type+"_"+attr_space_id;
		if(!StringUtil.isEmpty(sub_space_type))CACHE_KEY += "_"+sub_space_type;
		Boolean tFlag = CACHE_LOCAL.get();
		if(tFlag==null)tFlag = false;
		if(_CACHE_FLAG && !tFlag){
			list =(List<AttrTable>)cache.get(space, CACHE_KEY);
			if(list!=null)return list;
		}
		String sql = SF.orderSql("QUERY_ATTR_TABLE");
		//公共属性与自定议属性
		if(!StringUtil.isEmpty(sub_space_type))sql += " and (d.sub_attr_spec_type='"+sub_space_type+"' or d.sub_attr_spec_type='"+Const.COMM_SUB_SPACE_ID+"')";
		list = this.baseDaoSupport.queryForList(sql, AttrTable.class, space_type,attr_space_id,ManagerUtils.getSourceFrom());
		if(list!=null && _CACHE_FLAG){
			CacheList<AttrTable> clist = new CacheList<AttrTable>();
			clist.addAll(list);
			cache.set(space, CACHE_KEY,clist);
		}
		return list;
	}

	@Override
	public List<OrderExceptionLogsReq> queryOrderExcLogs(Map queryParams) {
		String order_id = (String) queryParams.get("order_id");
		String is_his = (String) queryParams.get("is_his");
		String sql = "";
		if(!"1".equals(is_his)){
			sql = SF.orderSql("EXC_LOGS");
		}else{
			sql = SF.orderSql("EXC_LOGS_HIS");
		}
		// TODO Auto-generated method stub
		return this.baseDaoSupport.queryForList(sql, OrderExceptionLogsReq.class, order_id);
	}

	@Override
	public String getOrderIdByLogiNo(String logi_no) {
		String sql = "select t.order_id from es_delivery t where t.logi_no=? and t.source_from=? and rownum < 2";
		ArrayList para = new ArrayList();
		para.add(logi_no);
		para.add(CommonTools.getSourceForm());
		String order_id = this.baseDaoSupport.queryForString(sql, para.toArray());
		return order_id;
	}


}
