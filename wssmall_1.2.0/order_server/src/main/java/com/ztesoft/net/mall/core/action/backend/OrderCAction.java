package com.ztesoft.net.mall.core.action.backend;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionProvinceListResp;
import services.CardInf;
import services.FreeOfferInf;
import services.PromotionInf;
import services.RegionsInf;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.action.order.Button;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.action.order.OrderResult;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.GoodsApply;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderQueryParam;
import com.ztesoft.net.mall.core.model.ReturnsOrder;
import com.ztesoft.net.mall.core.service.ICloudManager;
import com.ztesoft.net.mall.core.service.IOrderAuditManager;
import com.ztesoft.net.mall.core.service.IOrderFlowManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderReportManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.IReturnsOrderManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;


public class OrderCAction extends WWAction {
    private PromotionInf promotionServ;
    private FreeOfferInf freeOfferServ;
    private RegionsInf regionsServ;
	private String orderId;
	
	private String orderIdCopy;
	private String searchKey;
	private String searchValue;
	private String order;
	private GoodsApply goodsApply;
	private IOrderManager orderManager;
	private IOrderFlowManager orderFlowManager;

	private IOrderReportManager orderReportManager;
	IOrderUtils orderUtils;
	private IReturnsOrderManager returnsOrderManager ;
	private IOrderAuditManager orderAuditManager;
	//private IBankManager bankManager;
	private Order ord;
	private Bank bank;
	private List itemList;
	private List payLogList;
	private List refundList;
	private List shipLogList;
	private List reshipLogList;
	private List chshipLogList;
	private List logList;
	private List auditLogList;
	private List cardLogList;
	private List cloudLogList;
	
	private List provinceList;
	private List orderGiftList;//赠品列表
	private List pmtList; // 订单的优惠方案列表
	private Integer[] id;
	private int giftCount;
	private double price; //修改订单价格所用
	private String remark;
	private String buttonHtmls;
	private Integer state; //根据订单状态过滤
	private String action;
	
	private Map ordermap;

	private Member member;
	private ReturnsOrder rorder;
	private String refuse_reson; // 拒绝原因
	private String start;
	private String end;
	private String goods_id;
	private OrderQueryParam ordParam;
	private IOrderDirector orderDirector;
	
	private ICloudManager cloudManager;
	private String render_type;

    @Resource
    private CardInf cardServ;
	
	/**
	 * 为订单增加查询条件所用
	 */
	private List orderStatusList;	//订单状态
	private List orderSourceList;	//订单分销商
	private String startTime;	//开始时间
	private String endTime;	//结束时间
	private String sourceFrom;	//经销商来源
	private String orderName;	//订单名称
	private String orderCode;		//订单编号
	private String userid;		//分销商id
	private String keyWord;
	private String realname;
	private String username ;
	private String orderstatus = "2";
	
	private AdminUser adminUser;
	
	/**
	 * 分页读取订单列表
	 * 根据订单状态 state 检索，如果未提供状态参数，则检索所有
	 * @return
	 * 
	 * 
	 *
	 * 展示菜单列表:
	 * 
	 * 全部订单
	 * 待支付
	 * 待审核
	 * 待受理
	 * 待发货
	 * 待确认发货
	 * 订单取消
	 * 订单撤单
	 * 
	 */
	public String list() {
		
		if(state!=null){
			this.searchKey = "status";
			this.searchValue =  ""+state;
		}
		this.webpage = this.orderManager.listn(this.getPage(), this
				.getPageSize(), 0, ordParam, order);
		
		
		return "list";
	}
	
	public String listn() {
		/**
		 * 搜索查询条件
		 */
		orderStatusList = this.orderManager.listOrderOption();
		orderSourceList = this.orderManager.listOrderSource();
		
		this.adminUser= ManagerUtils.getAdminUser();
		
		if(ordParam == null)
			ordParam = new OrderQueryParam();
		ordParam.setStartTime(startTime);
		ordParam.setEndTime(endTime);
		ordParam.setSourceFrom(sourceFrom);
		ordParam.setState(state);
		ordParam.setAction(action);
		ordParam.setOrderName(orderName);
		ordParam.setOrderId(orderCode);
		ordParam.setUserid(userid);
		ordParam.setOrderstatus(orderstatus);
		this.webpage = this.orderManager.listn(this.getPage(), this
				.getPageSize(), 0, ordParam, order);
		
		List<Order> orders = this.webpage.getResult();
		for (Order p_order:orders) {
			ord =p_order;
			render_type ="button";
			showbtns();
			p_order.setOper_btns(this.buttonHtmls);
		}
		
		
		/**
		 * 以下代码是为了导出excel功能所编写
		 * title:excel表头，传递需要打印的表头注释
		 * content:内容字段，输入实体对象所对应表头字段的属性
		 * set的时候勿更改属性名，否则将出现错误
		 * fileTitle为导出excel文件的标题，根据自己导出的内容设定
		 * 
		 * 若需要导出功能，请在jsp页面的grid属性里面添加 excel="yes"
		 */
		String[] title = {"商品名称","订单号","下单日期","订单类型",
				"订单状态","订单总额","申请人","付款状态","受理状态","发货状态","申请区域"};
		String[] content = {"goods_name","order_id","create_time","orderType","orderStatus"
				,"order_amount","user_name","payStatus","acceptStatus","shipStatus","lan_name"};
		String fileTitle = "订单数据导出";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("fileTitle", fileTitle);
		
		if(excel != null && !"".equals(excel)){
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}else{
			return "list";
		}
	}
	
	
	public String listc() {
		try {
			
			if(ordParam == null)
				ordParam = new OrderQueryParam();
			ordParam.setState(state);
			ordParam.setAction(action);
			Map countMap = this.orderManager.listc(ordParam);
			String jsonStr = ManagerUtils.toJsonString(countMap);
			
			this.json = "{result:1,"+jsonStr+"}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"统计订单数量失败";

		}
		return WWAction.JSON_MESSAGE;
		
	}
	
	
	/**
	 * 显示订单的详细内容 到订单详细页
	 * 
	 * @return
	 */
	public String detail() {
		this.ord = this.orderManager.get(orderId);
		RegionProvinceListReq req = new RegionProvinceListReq();
		RegionProvinceListResp resp = regionsServ.getProvinceList(req);
		if(resp != null){
			provinceList = resp.getProvince_list();
		}
		return "detail";
	}
	
	
	//订单取消
	public String cancel(){
		try {
			orderCancel();
			this.json = "{result:1,message:'订单[" + orderId+ "]取消成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"订单取消失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	/**
	 * 
	 * 订单取消
	 */
	private void orderCancel() {
		orderDirector.getOrderBuilder().buildCancelFlow();
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(OrderBuilder.COMMON_KEY);
		orderRequst.setFlow_name(OrderBuilder.CANCEL);
		orderRequst.setOrderId(orderId);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_CANCEL);
		orderDirector.perform(orderRequst);
	}
	
	
	
	//订单撤单
	public String withdraw(){
		try {
			orderWithdraw();
			this.json = "{result:1,message:'订单[" +orderId+ "]撤单成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"订单撤单失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	/**
	 * 
	 * 订单撤单
	 */
	private void orderWithdraw() {
	
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(OrderBuilder.COMMON_KEY);
		orderRequst.setFlow_name(OrderBuilder.WITHDRAW);
		orderRequst.setOrderId(orderId);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_WITHDRAWS);
		orderDirector.getOrderBuilder().buildWithDrawFlow();
		orderDirector.perform(orderRequst);
	}
	
	
	/**
	 * 作废订单
	 * 
	 * @return
	 */
	public String invalid() {
		try {
			this.orderFlowManager.cancel(orderId);
			Order order = this.orderManager.get(orderId);
			this.json = "{result:1,message:'订单[" + order.getSn()
					+ "]成功作废',orderStatus:" + order.getStatus() + "}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"订单作废失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	

	/**
	 * 完成订单
	 * 
	 * @return
	 */
	public String complete() {
		try {
			this.orderFlowManager.complete(orderId);
			Order order = this.orderManager.get(orderId);
			this.json = "{result:1,message:'订单[" + order.getSn()+ "]成功标记为完成状态',orderStatus:" + order.getStatus() + "}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"订单完成失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	
	/**
	 * 订单按钮
	 * 
	 * @return
	 */
	public String showbtns() {
		if(ord == null)
			ord = this.orderManager.get(orderId);
		orderDirector.getOrderBuilder().buildOrderFlow();
		OrderRequst orderRequst = new OrderRequst();
		String type_code = ord.getType_code();
		orderRequst.setService_name(type_code);
		orderRequst.setFlow_name(OrderBuilder.COLLECT);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
		orderRequst.setOrderId(orderId);
		
		OrderResult orderResult = orderDirector.display(orderRequst,ord);
		List<Button> buttons = orderResult.getButtons();
//		
////		
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CLOUD_ACCEPT,OrderStatus.BUTTON_CLOUD_ACCEPT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CUST_ACCEPT,OrderStatus.BUTTON_CUST_ACCEPT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CARD_ACCEPT,OrderStatus.BUTTON_CARD_ACCEPT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_APPLY,OrderStatus.BUTTON_CONTRACT_APPLY,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_AUDIT,OrderStatus.BUTTON_CONTRACT_AUDIT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_ACCEPT,OrderStatus.BUTTON_CONTRACT_ACCEPT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_PAY,OrderStatus.BUTTON_PAY,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CARD_PAY,OrderStatus.BUTTON_CARD_PAY,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND,OrderStatus.BUTTON_REFUND,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND_APPLY,OrderStatus.BUTTON_REFUND_APPLY,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_REFUND_AUDIT,OrderStatus.BUTTON_REFUND_AUDIT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_AUDIT,OrderStatus.BUTTON_AUDIT,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_SHIPPING,OrderStatus.BUTTON_SHIPPING,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_GET_SHIPPING,OrderStatus.BUTTON_GET_SHIPPING,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_RETURNED_SHIPPING,OrderStatus.BUTTON_RETURNED_SHIPPING,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_FINISHED,OrderStatus.BUTTON_FINISHED,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_INVALID,OrderStatus.BUTTON_INVALID,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CANCEL,OrderStatus.BUTTON_CANCEL,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_DRAWBACK,OrderStatus.BUTTON_DRAWBACK,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CHANGE_SHIPPING,OrderStatus.BUTTON_CHANGE_SHIPPING,false,""));
//		buttons.add(new Button(OrderStatus.BUTTON_NAME_CONTRACT_TANSFER,OrderStatus.BUTTON_CONTRACT_TANSFER,false,""));
//	
//		
		this.buttonHtmls =Button.toHtml(buttons,render_type); //展示按钮html
		return "showbtns";
	}


	
	public String savePrice(){
		
		try{
			this.orderManager.savePrice(price, this.orderId);
			this.json ="{result:1}";
		}catch(RuntimeException e){
			this.logger.info(e.getMessage(), e);
			this.json ="{result:0}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	

	/**
	 * 商品信息
	 * 
	 * @return
	 */
	public String items() {
		itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
		this.orderGiftList = this.freeOfferServ.getOrderGift(orderId);
		this.giftCount = orderGiftList.size();
		return "items";
	}

	/**
	 * 收退款记录
	 * 
	 * @return
	 */
	public String payLog() {
		payLogList = this.orderReportManager.listPayLogs(orderId, 1);
		refundList = this.orderReportManager.listPayLogs(orderId, 2);
		return "pay_log";
	}

	/**
	 * 发退货记录
	 * 
	 * @return
	 */
	public String shipLog() {
		shipLogList = orderReportManager.listDelivery(orderId, 1);
		reshipLogList = orderReportManager.listDelivery(orderId, 2);
		chshipLogList = orderReportManager.listDelivery(orderId, 3);
		return "ship_log";
	}

	/**
	 * 订单日志
	 * 
	 * @return
	 */
	public String log() {
		this.logList = this.orderManager.listLogs(orderId);
		return "log";
	}
	
	
	/**
	 * 订单审核日志（退费、合约机日志信息等）
	 * 
	 * @return
	 */
	public String auditlog() {
		this.auditLogList = this.orderAuditManager.listLogs(orderId);
		return "auditlog";
	}
	
	public String cloudlog() {
		this.cloudLogList = this.cloudManager.list(orderId);
		return "cloudlog";
	}
	
	
	public String cardlog() {
		this.cardLogList = this.cardServ.list(orderId);
		return "cardlog";
	}
	
	
	
	public String remark() {
		this.ord = this.orderManager.get(orderId); // 订单信息
		return "remark";
	}
	
	public String saveRemark(){
		this.ord = this.orderManager.get(orderId);
		this.ord.setRemark(remark);
		try{
			this.orderManager.edit(this.ord);
			this.json = "{result:0,message:'修改成功'}";
		}catch(RuntimeException e){
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:\"修改失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public String delete() {
		try {
			this.orderManager.delete(id);
			this.json = "{result:0,message:'订单删除成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:\"订单删除失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;

	}

	public String revert() {
		try {
			this.orderManager.revert(id);
			this.json = "{result:0,message:'订单还原成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:\"订单还原失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;

	}

	public String clean() {
		try {
			this.orderManager.clean(id);
			this.json = "{result:0,message:'订单清除成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:1,message:\"订单清除失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;

	}

	/**
	 * 优惠方案
	 * 
	 * @return
	 */
	public String pmt() {
		 pmtList  =this.promotionServ.listOrderPmt(this.orderId);
		return "pmt";
	}

	
	
	public String collect() {
		return "";
	}

	public String accept() {
		return "";
	}

	public String audit() {
		return "";
	}

	public String payment() {
		return "";
	}

	public String dilvery() {
		return "";
	}

	public String finish() {
		return "";
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getSearchKey() {
		return searchKey;
	}



	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}



	public String getSearchValue() {
		return searchValue;
	}



	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}



	public String getOrder() {
		return order;
	}



	public void setOrder(String order) {
		this.order = order;
	}



	public GoodsApply getGoodsApply() {
		return goodsApply;
	}



	public void setGoodsApply(GoodsApply goodsApply) {
		this.goodsApply = goodsApply;
	}



	public IOrderManager getOrderManager() {
		return orderManager;
	}



	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}



	public IOrderFlowManager getOrderFlowManager() {
		return orderFlowManager;
	}



	public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
		this.orderFlowManager = orderFlowManager;
	}

	public IOrderReportManager getOrderReportManager() {
		return orderReportManager;
	}



	public void setOrderReportManager(IOrderReportManager orderReportManager) {
		this.orderReportManager = orderReportManager;
	}

	public IReturnsOrderManager getReturnsOrderManager() {
		return returnsOrderManager;
	}



	public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
		this.returnsOrderManager = returnsOrderManager;
	}
/*


	public IBankManager getBankManager() {
		return bankManager;
	}



	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}
*/


	public Order getOrd() {
		return ord;
	}



	public void setOrd(Order ord) {
		this.ord = ord;
	}



	public Bank getBank() {
		return bank;
	}



	public void setBank(Bank bank) {
		this.bank = bank;
	}



	public List getItemList() {
		return itemList;
	}



	public void setItemList(List itemList) {
		this.itemList = itemList;
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



	public List getShipLogList() {
		return shipLogList;
	}



	public void setShipLogList(List shipLogList) {
		this.shipLogList = shipLogList;
	}



	public List getReshipLogList() {
		return reshipLogList;
	}



	public void setReshipLogList(List reshipLogList) {
		this.reshipLogList = reshipLogList;
	}



	public List getChshipLogList() {
		return chshipLogList;
	}



	public void setChshipLogList(List chshipLogList) {
		this.chshipLogList = chshipLogList;
	}



	public List getLogList() {
		return logList;
	}



	public void setLogList(List logList) {
		this.logList = logList;
	}



	public List getProvinceList() {
		return provinceList;
	}



	public void setProvinceList(List provinceList) {
		this.provinceList = provinceList;
	}



	public List getOrderGiftList() {
		return orderGiftList;
	}



	public void setOrderGiftList(List orderGiftList) {
		this.orderGiftList = orderGiftList;
	}



	public List getPmtList() {
		return pmtList;
	}



	public void setPmtList(List pmtList) {
		this.pmtList = pmtList;
	}



	public Integer[] getId() {
		return id;
	}



	public void setId(Integer[] id) {
		this.id = id;
	}



	public int getGiftCount() {
		return giftCount;
	}



	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getState() {
		return state;
	}



	public void setState(Integer state) {
		this.state = state;
	}



	public Map getOrdermap() {
		return ordermap;
	}



	public void setOrdermap(Map ordermap) {
		this.ordermap = ordermap;
	}



	public Member getMember() {
		return member;
	}



	public void setMember(Member member) {
		this.member = member;
	}



	public ReturnsOrder getRorder() {
		return rorder;
	}



	public void setRorder(ReturnsOrder rorder) {
		this.rorder = rorder;
	}



	public String getRefuse_reson() {
		return refuse_reson;
	}



	public void setRefuse_reson(String refuse_reson) {
		this.refuse_reson = refuse_reson;
	}



	public String getStart() {
		return start;
	}



	public void setStart(String start) {
		this.start = start;
	}



	public String getEnd() {
		return end;
	}



	public void setEnd(String end) {
		this.end = end;
	}


	public String getButtonHtmls() {
		return buttonHtmls;
	}


	public void setButtonHtmls(String buttonHtmls) {
		this.buttonHtmls = buttonHtmls;
	}


	public String getOrderIdCopy() {
		return orderIdCopy;
	}


	public void setOrderIdCopy(String orderIdCopy) {
		this.orderIdCopy = orderIdCopy;
	}


	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}


	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}


	public String getGoods_id() {
		return goods_id;
	}


	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public OrderQueryParam getOrdParam() {
		return ordParam;
	}

	public void setOrdParam(OrderQueryParam ordParam) {
		this.ordParam = ordParam;
	}

	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}

	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}

	public IOrderAuditManager getOrderAuditManager() {
		return orderAuditManager;
	}

	public void setOrderAuditManager(IOrderAuditManager orderAuditManager) {
		this.orderAuditManager = orderAuditManager;
	}

	public List getAuditLogList() {
		return auditLogList;
	}

	public void setAuditLogList(List auditLogList) {
		this.auditLogList = auditLogList;
	}

	public List getCardLogList() {
		return cardLogList;
	}

	public void setCardLogList(List cardLogList) {
		this.cardLogList = cardLogList;
	}

	public List getCloudLogList() {
		return cloudLogList;
	}

	public void setCloudLogList(List cloudLogList) {
		this.cloudLogList = cloudLogList;
	}

	public ICloudManager getCloudManager() {
		return cloudManager;
	}

	public void setCloudManager(ICloudManager cloudManager) {
		this.cloudManager = cloudManager;
	}

	public String getRender_type() {
		return render_type;
	}

	public void setRender_type(String render_type) {
		this.render_type = render_type;
	}

	public List getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public List getOrderSourceList() {
		return orderSourceList;
	}

	public void setOrderSourceList(List orderSourceList) {
		this.orderSourceList = orderSourceList;
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

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
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

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public PromotionInf getPromotionServ() {
		return promotionServ;
	}

	public void setPromotionServ(PromotionInf promotionServ) {
		this.promotionServ = promotionServ;
	}

	public FreeOfferInf getFreeOfferServ() {
		return freeOfferServ;
	}

	public void setFreeOfferServ(FreeOfferInf freeOfferServ) {
		this.freeOfferServ = freeOfferServ;
	}

	public RegionsInf getRegionsServ() {
		return regionsServ;
	}

	public void setRegionsServ(RegionsInf regionsServ) {
		this.regionsServ = regionsServ;
	}

	public CardInf getCardServ() {
		return cardServ;
	}

	public void setCardServ(CardInf cardServ) {
		this.cardServ = cardServ;
	}
	
	
	
}