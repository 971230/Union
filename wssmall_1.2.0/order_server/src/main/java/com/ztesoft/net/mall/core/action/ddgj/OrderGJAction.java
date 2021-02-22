package com.ztesoft.net.mall.core.action.ddgj;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import params.member.req.MemberByIdReq;
import params.member.resp.MemberByIdResp;
import services.MemberInf;
import util.ConfigUtil;
import zte.net.iservice.IDlyTypeAddressService;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IPaymentConfigService;
import zte.params.cfg.req.PaymentConfigListReq;
import zte.params.cfg.resp.PaymentConfigListResp;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;
import zte.params.order.req.DlyTypeListReq;
import zte.params.order.req.OrderGetReq;
import zte.params.order.req.OrderItemListReq;
import zte.params.order.req.OrderOuterQryReq;
import zte.params.order.resp.DlyTypeListResp;
import zte.params.order.resp.OrderGetResp;
import zte.params.order.resp.OrderItemListResp;
import zte.params.order.resp.OrderOuterQryResp;

import com.ztesoft.common.util.StringUtils;
import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.DeliveryFlow;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.DlyType;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.PayCfg;
import com.ztesoft.net.mall.core.service.IDeliveryFlow;
import com.ztesoft.net.mall.core.service.IDeliveryManager;
import com.ztesoft.net.mall.core.service.IOrderApplycMamager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderReportManager;
import com.ztesoft.net.mall.core.service.IPromotionManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.service.IOrderFlowBussManager;
import com.ztesoft.net.model.OrderPromotion;
import com.ztesoft.net.model.OrderToDoList;

/**
 * 订单归集
 * @作者 MoChunrun
 * @创建日期 2014-5-22 
 * @版本 V 1.0
 */
public class OrderGJAction extends WWAction{

	private OrderQueryParam ordParam;
    private String startTime;    //开始时间
    private String endTime;    //结束时间
    private String sourceFrom;    //经销商来源
    private String orderName;    //订单名称
    private String orderCode;        //订单编号
    private String userid;        //分销商id
    private String keyWord;
    private String realname;
    private String username;
    private Integer state; //根据订单状态过滤
    private String action = "all";
    private String orderstatus = "-1";
    private String order;
    private String page_from;
    
    private String phoneNo;//联系电话
    private String deliveryNo;//物流单号
    private String netPhoneNo;//入网号
    private String terminalNo;//终端串码
    private String dlyTypeId;//配送方式ID
    private String payment_id;//支付方式
    
    private IOrderServices orderServices;
    private IDlyTypeAddressService dlyTypeAddressService;
    private IPaymentConfigService paymentConfigService;
    private IGoodsService goodServices;
    private MemberInf memberServ;
    private IOrderApplycMamager orderApplycMamager;
    private IPromotionManager promotionManager;
    
    //条件
    private List<DlyType> dlyTypeList;
    private List<PayCfg> paymentList;
    
    //订单基本信息
    private String order_id;
    private Order orderInfo;
    private OrderOuter orderOuter;
    private PayCfg payCfg;
    private Member member;
    
    //子订单列表
    private String goods_id;
    private List<Map> orderItems;
    //private Goods goods;
    
    private List<OrderPromotion> orderPromotionList;
    
    private List<OrderToDoList> toDoList;
    private IOrderFlowBussManager orderFlowBussManager;
    
    public String orderFlowLog(){
    	toDoList = orderFlowBussManager.listOrderToDoListByOrderId(order_id);
    	if("right".equals(action)){
    		return "right_order_flow_log";
    	}
    	return "order_flow_log";
    }
    
    public String listOrderPromotion(){
    	//enoughPriceGiveGiftPlugin 满就送  enoughPriceReducePrice满就减 enoughPriceFreeDeliveryPlugin免运费
    	orderPromotionList = promotionManager.listOrderPromotion(order_id);
    	if(orderPromotionList!=null && orderPromotionList.size()>0){
    		for(OrderPromotion op:orderPromotionList){
    			op.setMemberLvList(promotionManager.listPromotionMenberLv(op.getPmt_id()));
    			if("enoughPriceGiveGiftPlugin".equals(op.getPmts_id()))op.setPromotionGoodsList(promotionManager.listPromotionGoods(op.getPmt_id()));
    		}
    	}
    	return "order_promotion";
    }
    private String local_order_url;
    
    private int monitor = 0;
    
	public String listOrder(){
		if("请输入商品名称".equals(orderName))orderName=null;
		if("请输入订单编号".equals(orderCode))orderCode=null;
        if (ordParam == null)
            ordParam = new OrderQueryParam();
        ordParam.setStartTime(startTime);
        ordParam.setEndTime(endTime);
        ordParam.setSourceFrom(sourceFrom);
        ordParam.setState(state);
        ordParam.setAction(action);
        try{
        	ordParam.setOrderName(URLDecoder.decode(orderName==null?"":orderName, "UTF-8"));
        	ordParam.setUserName(URLDecoder.decode(username==null?"":username, "UTF-8"));
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        ordParam.setOrderId(orderCode);
        ordParam.setUserid(userid);
        ordParam.setOrderstatus(orderstatus);
        ordParam.setPhoneNo(phoneNo);
        ordParam.setDeliveryNo(deliveryNo);
        ordParam.setNetPhoneNo(netPhoneNo);
        ordParam.setTerminalNo(terminalNo);
        ordParam.setDlyTypeId(dlyTypeId);
        ordParam.setPayment_id(payment_id);
        /*if(StringUtils.isEmpty(ordParam.getAction()) || "all".equals(ordParam.getAction()) || "day".equals(ordParam.getAction()) || "exception".equals(ordParam.getAction())){
        	OrderPageListReq req = new OrderPageListReq();
            req.setOrderQueryParam(ordParam);
            req.setSecurity(true);
            req.setPageNo(this.getPage());
            req.setPageSize(15);
            OrderPageListResp resp = orderServices.queryOrderForPage(req);
            webpage = resp.getWebPage();
        }else{*/
        webpage = orderManager.queryFlowOrder(this.getPage(), this.getPageSize(), 0, ordParam, order,monitor);
        if(webpage!=null){
        	List list = webpage.getResult();
        	if(list!=null){
        		for(int i=0;i<list.size();i++){
        			Order o = (Order) list.get(i);
        			if(o.getStatus()==OrderStatus.ORDER_COMPLETE){
        				List<com.ztesoft.net.mall.core.action.order.orderc.OrderApply> al = orderApplycMamager.queryApplyByOrderId(o.getOrder_id());
        				if(al!=null && al.size()>0){
        					com.ztesoft.net.mall.core.action.order.orderc.OrderApply oa = al.get(0);
        					if(OrderStatus.ORDER_TYPE_4.equals(oa.getService_type())){
        						//退货
        						if(OrderStatus.ORDER_APPLY_STATUS_0.equals(oa.getApply_state())){
        							//退货申请
        							o.setStatus(OrderStatus.RETURNED_APPLY);
        						}if(OrderStatus.ORDER_APPLY_STATUS_1.equals(oa.getApply_state())){
        							//审核通过
        							o.setStatus(OrderStatus.RETURNED_APPLY_PASS);
        						}
        					}else if(OrderStatus.ORDER_TYPE_3.equals(oa.getService_type())){
        						//换货
        						if(OrderStatus.ORDER_APPLY_STATUS_0.equals(oa.getApply_state())){
        							//退货申请
        							o.setStatus(OrderStatus.EXCHANGE_APPLY);
        						}if(OrderStatus.ORDER_APPLY_STATUS_1.equals(oa.getApply_state())){
        							//审核通过
        							o.setStatus(OrderStatus.EXCHANGE_APPLY_PASS);
        						}
        					}
        				}
        			}
        		}
        	}
        }
        //}
        

		return "list";
	}
	
	/**
	 * 订单中心
	 * @作者 MoChunrun
	 * @创建日期 2014-5-20 
	 * @return
	 */
	public String orderCenter(){
		try{
			ConfigUtil cu = new ConfigUtil("/resource/order.properties");
			local_order_url = cu.getString("order.right.url");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		DlyTypeListReq req = new DlyTypeListReq();
		DlyTypeListResp resp = dlyTypeAddressService.listDlyType(req);
		dlyTypeList = resp.getDlyTypeList();
		PaymentConfigListReq preq = new PaymentConfigListReq();
		PaymentConfigListResp presp = paymentConfigService.listPaymentCfgList(preq);
		paymentList = presp.getPaymentCfgList();
		listOrder();
		return "order_center";
	}
	
	private boolean returned = false;
    private boolean refund = false;
    private boolean exchange = false;
    private OrderToDoList toDo;
	
	/**
	 * 订单基本信息
	 * @作者 MoChunrun
	 * @创建日期 2014-5-21 
	 * @return
	 */
	public String orderBase(){
		//获取订单基本信息
		OrderGetReq req = new OrderGetReq();
		req.setOrder_id(order_id);
		OrderGetResp resp = orderServices.getOrder(req);
		orderInfo = resp.getOrder();
		orderManager.setOrderSecurity(orderInfo);
		AdminUser user = ManagerUtils.getAdminUser();
		toDo = orderFlowBussManager.queryUserOrderToDoList(order_id, user.getUserid(),0);
		returned = orderApplycMamager.countAmountByServiceType(OrderStatus.ORDER_TYPE_4,order_id)>0;
		refund = orderApplycMamager.countAmountByServiceType(OrderStatus.ORDER_TYPE_2,order_id)>0;
		exchange = orderApplycMamager.countAmountByServiceType(OrderStatus.ORDER_TYPE_3,order_id)>0;
		
		//获取外系统订单信息
		OrderOuterQryReq orderOuterQryReq = new OrderOuterQryReq();
		orderOuterQryReq.setOrder_id(order_id);
		OrderOuterQryResp orderOuterQryResp = orderServices.qryOrderOuter(orderOuterQryReq);
		if(orderOuterQryResp != null){
			this.orderOuter = orderOuterQryResp.getOrderOuter();
		}
		
		//获取支付方式
		if(this.orderInfo != null){
			PaymentConfigListReq paymentConfigListReq = new PaymentConfigListReq();
			paymentConfigListReq.setPayment_id(String.valueOf(this.orderInfo.getPayment_id()));
			PaymentConfigListResp paymentConfigListResp = this.paymentConfigService.listPaymentCfgList(paymentConfigListReq);
			this.paymentList = paymentConfigListResp.getPaymentCfgList();
		}
		if(!ListUtil.isEmpty(paymentList)){
			for (int i = 0; i < paymentList.size(); i++) {
				this.payCfg = paymentList.get(0);
			}
		}
		
		
		//获取会员信息
		if(this.orderInfo != null){
			MemberByIdReq memberByIdReq = new MemberByIdReq();
			memberByIdReq.setMember_id(this.orderInfo.getMember_id());
			MemberByIdResp memberByIdResp = this.memberServ.getMemberById(memberByIdReq);
			this.member = memberByIdResp.getMember();
		}
		
		if(StringUtils.isNotEmpty(page_from) && "supply".equals(page_from)){
			return "order_supply";	//跳转订单补录
		}else{
			return "order_base";	//跳转订单基本信息
		}
	}
	
	/**
	 * 子订单列表
	 * @作者 MoChunrun
	 * @创建日期 2014-5-21 
	 * @return
	 */
	public String orderItems(){
		OrderItemListReq req = new OrderItemListReq();
		req.setOrder_id(order_id);
		OrderItemListResp resp = orderServices.listOrderItems(req);
		orderItems = resp.getOrderItems();
		if(orderItems!=null && orderItems.size()>0){
			Object obj = orderItems.get(0).get("goods_id");
			if(obj!=null){
				goods_id = obj.toString();
				try{
					goodsInfo();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return "order_items";
	}
	
	private Map<String,String> goods;
	
	/**
	 * 商品信息
	 * @作者 MoChunrun
	 * @创建日期 2014-5-21 
	 * @return
	 */
	public String goodsInfo(){
		/*GoodsBaseQueryReq req = new GoodsBaseQueryReq();
		req.setGoods_id(goods_id);
		try {
			GoodsBaseQueryResp resp = goodServices.queryGoodsBaseInfo(req);
			goods = resp.getGoods();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try{
			GoodsGetReq req = new GoodsGetReq();
			req.setGoods_id(goods_id);
			GoodsGetResp resp = goodServices.getGoods(req);
			goods =resp.getData();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "goods_info";
	}
	
	private List payLogList;
    private List refundList;
    private IOrderReportManager orderReportManager;

	/**
	 * 收退款记录
	 * @作者 MoChunrun
	 * @创建日期 2014-5-22 
	 * @return
	 */
	public String paymentLog() {
        payLogList = this.orderReportManager.listPayLogs(order_id.trim(), 1);
        refundList = this.orderReportManager.listPayLogs(order_id.trim(), 2);
        return "payment_log";
    }
	
	private List<Map> shipLogList;
    private List<Map> reshipLogList;
    private List<Map> chshipLogList;
    private IDeliveryFlow deliveryFlowManager;
    private IDeliveryManager deliveryManager;
	/**
	 * 发退货记录
	 * @作者 MoChunrun
	 * @创建日期 2014-5-22 
	 * @return
	 */
    public String shippingLog() {
        shipLogList = orderReportManager.listDelivery(order_id.trim(), 1);
        reshipLogList = orderReportManager.listDelivery(order_id.trim(), 2);
        chshipLogList = orderReportManager.listDelivery(order_id.trim(), 3);
        return "shipping_log";
    }
    
    private String delivery_id;
    private String logi_id;
    private String logi_no;
    private List<DeliveryFlow> flowList;
    private List<DeliveryItem> deliveryItemList;
    private int goods_size=2;
    /**
     * 查看物流详细
     * @作者 MoChunrun
     * @创建日期 2014-5-22 
     * @return
     */
    public String deliveryInfo(){
    	if("0".equals(logi_id)){
    		flowList = deliveryFlowManager.qryDeliveryFlowByDeliveryID(delivery_id);
		}else{
			//有物流单号的  logi_no
			flowList = deliveryFlowManager.qryFlowInfo(logi_no, logi_id);
		}
    	deliveryItemList = deliveryManager.qryDeliveryItems(delivery_id);
    	goods_size = deliveryItemList.size()+1;
    	return "delivery_info";
    }
    
    public String showDeliveryInfoAdd(){
    	return "delivery_info_add";
    }
    
    private List logList;
    private IOrderManager orderManager;
    /**
     * 订单日志
     * @作者 MoChunrun
     * @创建日期 2014-5-23 
     * @return
     */
    public String orderLog(){
    	logList = this.orderManager.listLogs(order_id);
    	return "order_log";
    }
    
	
	public OrderQueryParam getOrdParam() {
		return ordParam;
	}

	public void setOrdParam(OrderQueryParam ordParam) {
		this.ordParam = ordParam;
	}


	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public IOrderServices getOrderServices() {
		return orderServices;
	}

	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getNetPhoneNo() {
		return netPhoneNo;
	}

	public void setNetPhoneNo(String netPhoneNo) {
		this.netPhoneNo = netPhoneNo;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public IDlyTypeAddressService getDlyTypeAddressService() {
		return dlyTypeAddressService;
	}

	public void setDlyTypeAddressService(
			IDlyTypeAddressService dlyTypeAddressService) {
		this.dlyTypeAddressService = dlyTypeAddressService;
	}

	public List<DlyType> getDlyTypeList() {
		return dlyTypeList;
	}

	public void setDlyTypeList(List<DlyType> dlyTypeList) {
		this.dlyTypeList = dlyTypeList;
	}

	public String getDlyTypeId() {
		return dlyTypeId;
	}

	public void setDlyTypeId(String dlyTypeId) {
		this.dlyTypeId = dlyTypeId;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public IPaymentConfigService getPaymentConfigService() {
		return paymentConfigService;
	}

	public void setPaymentConfigService(IPaymentConfigService paymentConfigService) {
		this.paymentConfigService = paymentConfigService;
	}

	public List<PayCfg> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PayCfg> paymentList) {
		this.paymentList = paymentList;
	}

	public Order getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(Order orderInfo) {
		this.orderInfo = orderInfo;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public List<Map> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<Map> orderItems) {
		this.orderItems = orderItems;
	}

	/*public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}*/

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public IGoodsService getGoodServices() {
		return goodServices;
	}

	public void setGoodServices(IGoodsService goodServices) {
		this.goodServices = goodServices;
	}

	public String getPage_from() {
		return page_from;
	}

	public void setPage_from(String page_from) {
		this.page_from = page_from;
	}

	public List getPayLogList() {
		return payLogList;
	}

	public void setPayLogList(List payLogList) {
		this.payLogList = payLogList;
	}

	public List getRefundList() {
		return refundList;
	}

	public void setRefundList(List refundList) {
		this.refundList = refundList;
	}

	public IOrderReportManager getOrderReportManager() {
		return orderReportManager;
	}

	public void setOrderReportManager(IOrderReportManager orderReportManager) {
		this.orderReportManager = orderReportManager;
	}

	public List<Map> getShipLogList() {
		return shipLogList;
	}

	public void setShipLogList(List<Map> shipLogList) {
		this.shipLogList = shipLogList;
	}

	public List<Map> getReshipLogList() {
		return reshipLogList;
	}

	public void setReshipLogList(List<Map> reshipLogList) {
		this.reshipLogList = reshipLogList;
	}

	public List<Map> getChshipLogList() {
		return chshipLogList;
	}

	public void setChshipLogList(List<Map> chshipLogList) {
		this.chshipLogList = chshipLogList;
	}

	public IDeliveryFlow getDeliveryFlowManager() {
		return deliveryFlowManager;
	}

	public void setDeliveryFlowManager(IDeliveryFlow deliveryFlowManager) {
		this.deliveryFlowManager = deliveryFlowManager;
	}

	public IDeliveryManager getDeliveryManager() {
		return deliveryManager;
	}

	public void setDeliveryManager(IDeliveryManager deliveryManager) {
		this.deliveryManager = deliveryManager;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getLogi_id() {
		return logi_id;
	}

	public void setLogi_id(String logi_id) {
		this.logi_id = logi_id;
	}

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public List<DeliveryFlow> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<DeliveryFlow> flowList) {
		this.flowList = flowList;
	}

	public List<DeliveryItem> getDeliveryItemList() {
		return deliveryItemList;
	}

	public void setDeliveryItemList(List<DeliveryItem> deliveryItemList) {
		this.deliveryItemList = deliveryItemList;
	}

	public int getGoods_size() {
		return goods_size;
	}

	public void setGoods_size(int goods_size) {
		this.goods_size = goods_size;
	}

	public Map<String, String> getGoods() {
		return goods;
	}

	public void setGoods(Map<String, String> goods) {
		this.goods = goods;
	}

	public List getLogList() {
		return logList;
	}

	public void setLogList(List logList) {
		this.logList = logList;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}

	public OrderOuter getOrderOuter() {
		return orderOuter;
	}

	public void setOrderOuter(OrderOuter orderOuter) {
		this.orderOuter = orderOuter;
	}

	public PayCfg getPayCfg() {
		return payCfg;
	}

	public void setPayCfg(PayCfg payCfg) {
		this.payCfg = payCfg;
	}

	public MemberInf getMemberServ() {
		return memberServ;
	}

	public void setMemberServ(MemberInf memberServ) {
		this.memberServ = memberServ;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public IOrderApplycMamager getOrderApplycMamager() {
		return orderApplycMamager;
	}

	public void setOrderApplycMamager(IOrderApplycMamager orderApplycMamager) {
		this.orderApplycMamager = orderApplycMamager;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public boolean isRefund() {
		return refund;
	}

	public void setRefund(boolean refund) {
		this.refund = refund;
	}

	public boolean isExchange() {
		return exchange;
	}

	public void setExchange(boolean exchange) {
		this.exchange = exchange;
	}

	public IPromotionManager getPromotionManager() {
		return promotionManager;
	}

	public void setPromotionManager(IPromotionManager promotionManager) {
		this.promotionManager = promotionManager;
	}

	public List<OrderPromotion> getOrderPromotionList() {
		return orderPromotionList;
	}

	public void setOrderPromotionList(List<OrderPromotion> orderPromotionList) {
		this.orderPromotionList = orderPromotionList;
	}

	public IOrderFlowBussManager getOrderFlowBussManager() {
		return orderFlowBussManager;
	}

	public void setOrderFlowBussManager(IOrderFlowBussManager orderFlowBussManager) {
		this.orderFlowBussManager = orderFlowBussManager;
	}

	public OrderToDoList getToDo() {
		return toDo;
	}

	public void setToDo(OrderToDoList toDo) {
		this.toDo = toDo;
	}

	public List<OrderToDoList> getToDoList() {
		return toDoList;
	}

	public void setToDoList(List<OrderToDoList> toDoList) {
		this.toDoList = toDoList;
	}

	public String getLocal_order_url() {
		return local_order_url;
	}

	public void setLocal_order_url(String local_order_url) {
		this.local_order_url = local_order_url;
	}

	public int getMonitor() {
		return monitor;
	}

	public void setMonitor(int monitor) {
		this.monitor = monitor;
	}
	
}
