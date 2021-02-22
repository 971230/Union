package com.ztesoft.net.mall.core.action.backend;

import java.util.Date;
import java.util.List;
import java.util.Map;

import params.bank.req.BankReq;
import params.regions.req.RegionProvinceListReq;
import params.regions.resp.RegionProvinceListResp;
import services.BankInf;
import services.CardInf;
import services.FreeOfferInf;
import services.PromotionInf;
import services.RegionsInf;

import com.ztesoft.net.app.base.core.model.Member;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.flow.AcceptPlugin;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.Card;
import com.ztesoft.net.mall.core.model.ItemCard;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.model.ReturnsOrder;
import com.ztesoft.net.mall.core.service.ICloudManager;
import com.ztesoft.net.mall.core.service.IOrderFlowManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderReportManager;
import com.ztesoft.net.mall.core.service.IReturnsOrderManager;
import com.ztesoft.net.mall.core.service.OrderUtils;
/**
 * 订单管理action
 * 
 * @author kingapex 2010-4-7下午01:51:48
 */
public class OrderAction extends WWAction {
  
    private PromotionInf promotionServ;
	private String orderId;
	private String searchKey;
	private String searchValue;
	private String order;
	private IOrderManager orderManager;
	private IOrderFlowManager orderFlowManager;
	private IOrderReportManager orderReportManager;
	private IReturnsOrderManager returnsOrderManager ;
	//private IBankManager bankManager;
	private BankInf bankServ;
	private Order ord;
	private Bank bank;
	private List itemList;
	private List payLogList;
	private List refundList;
	private List shipLogList;
	private List reshipLogList;
	private List chshipLogList;
	private List logList;
	private List provinceList;
	private List orderGiftList;//赠品列表
	private List pmtList; // 订单的优惠方案列表
	private Integer[] id;
	private int giftCount;
	private double price; //修改订单价格所用
	private String remark;
	private OrderUtils orderUtils;
	private Card card;
	private OrderItem orderItem;//
	private List<ItemCard> itemcards;
	private AcceptPlugin cloudFlowAcceptPlugin;
	private Integer state; //根据订单状态过滤
	private Map ordermap;
	private Member member;
	private ReturnsOrder rorder;
	private String refuse_reson; // 拒绝原因
	private String start;
	private String end;
	private ICloudManager cloudManager;
    private CardInf cardServ;
    private FreeOfferInf freeOfferServ;//赠品
    private RegionsInf regionsServ;
    
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
	 * 分页读取订单列表
	 * 根据订单状态 state 检索，如果未提供状态参数，则检索所有
	 * @return
	 */
	public String list() {
		if(state!=null){
			this.searchKey = "status";
			this.searchValue =  ""+state;
		}
		this.webpage = this.orderManager.list(this.getPage(), this
				.getPageSize(), 0, searchKey, searchValue, order);
		
		
		
		return "list";
	}
	
	
	public String trash_list() {
		this.webpage = this.orderManager.list(this.getPage(), this
				.getPageSize(), 1, searchKey, searchValue, order);

		return "trash_list";
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

	/**
	 * 显示基本信息
	 * 
	 * @return
	 */
	public String base() {

		itemList = this.orderManager.listGoodsItems(orderId); // 订单商品列表
		this.ord = this.orderManager.get(orderId); // 订单信息
		String bank_id=ord.getBank_id();
		if(!StringUtil.isEmpty(bank_id)){
			BankReq req = new BankReq();
			req.setBank_id(bank_id);
			this.bank = bankServ.getBankByCode(req).getBank();
			//this.bank=this.bankManager.getBankByBankId(bank_id);
		}
//		if (ord.getMember_id() != null)
//			this.member = this.memberManager.get(ord.getMember_id());
		if (!StringUtil.isEmpty(ord.getUserid()))
		{
			member=  new  Member();
			if(OrderStatus.SOURCE_FROM_TAOBAO.equals(ord.getSource_from()) || OrderBuilder.COMMONAGE.equals(ord.getType_code())) //淘宝外系统订单
			{
				
				member.setUname(ord.getShip_name());
				member.setName(ord.getShip_name());
				member.setTel(ord.getShip_tel());
				member.setAddress(ord.getShip_addr());
				member.setEmail(ord.getShip_email());
			}else {
				//分销商订单
				
				AdminUser adminUser = orderUtils.getAdminUserById(ord.getUserid());
				member.setUname(adminUser.getUsername());
				member.setName(adminUser.getRealname());
				member.setTel(adminUser.getPhone_num());
				member.setAddress(ord.getShip_addr());
				member.setEmail(ord.getShip_email());
			}
		}
		
		orderItem  = orderUtils.getOrderItemByOrderId(orderId);
		itemcards =null;// orderManager.getItemCardByOrderId(orderId);
		
		if(!StringUtil.isEmpty(ord.getAcc_nbr())){
			card = cardServ.getCardByOrderId(ord.getOrder_id());
		}
		
		return "base";
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

	/**
	 * 完成订单
	 * 
	 * @return
	 */
	public String complete() {
		try {
			this.orderFlowManager.complete(orderId);
			Order order = this.orderManager.get(orderId);
			this.json = "{result:1,message:'订单[" + order.getSn()
					+ "]成功标记为完成状态',orderStatus:" + order.getStatus() + "}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			this.json = "{result:0,message:\"订单完成失败：" + e.getMessage() + "\"}";

		}
		return WWAction.JSON_MESSAGE;
	}

	/**
	 * 作废订单
	 * 
	 * @return
	 */
	public String cancel() {
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
	 * 退货列表
	 * @return
	 */
	public String returnsList(){
		this.webpage = this.returnsOrderManager.listAll(this.getPage(), this.getPageSize());		
		return "return_list";
	}
	
	
	/**
	 * 退货详细
	 * @return
	 */
	public String returnDetail(){
		rorder = returnsOrderManager.get(orderId);
		return "return_detail";
	}
	
	
	/**
	 * 拒绝换货或退货 
	 * 注意此处的orderId为 退货订单的id
	 * @return
	 */
	public String refuse(){
		 
		try{
			this.returnsOrderManager.refuse(orderId, this.refuse_reson);
			this.json ="{result:1}";
		}catch(RuntimeException e){
			e.printStackTrace();
			this.logger.info(e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 同意退换货
	 * @return
	 */
	public String agree(){
		try{
			this.returnsOrderManager.updateState(orderId, 1);
			this.json ="{result:1}";
		}catch(RuntimeException e){
			e.printStackTrace();
			this.logger.info(e.fillInStackTrace());
			this.json ="{result:0,message:'"+e.getMessage()+"'}";
		}
		
		return WWAction.JSON_MESSAGE;
		
	}
	
	
	public String toExport(){
		
		return "export";
	}
	
	public String export(){
		
		try{
			Date startDate = null;
			Date endDate = null;
			
			if(!StringUtil.isEmpty(start))
				startDate = DateUtil.toDate(start, "yyyy-MM-dd");
			
			if(!StringUtil.isEmpty(end))
				endDate = DateUtil.toDate(end, "yyyy-MM-dd");
			
			String url = this.orderManager.export(startDate, endDate);
			this.json= "{result:1,url:'"+url+"'}";
		}catch(RuntimeException e){
			this.logger.info("导出订单出错", e);
			this.showErrorJson(e.getMessage());
		}
		return WWAction.JSON_MESSAGE;
	}
	

	
	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
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

	public Order getOrd() {
		return ord;
	}

	public void setOrd(Order ord) {
		this.ord = ord;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List getItemList() {
		return itemList;
	}

	public void setItemList(List itemList) {
		this.itemList = itemList;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
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

	public IOrderFlowManager getOrderFlowManager() {
		return orderFlowManager;
	}

	public void setOrderFlowManager(IOrderFlowManager orderFlowManager) {
		this.orderFlowManager = orderFlowManager;
	}

	public Integer[] getId() {
		return id;
	}

	public void setId(Integer[] id) {
		this.id = id;
	}

	public Map getOrdermap() {
		return ordermap;
	}

	public void setOrdermap(Map ordermap) {
		this.ordermap = ordermap;
	}

	public List getOrderGiftList() {
		return orderGiftList;
	}

	public void setOrderGiftList(List orderGiftList) {
		this.orderGiftList = orderGiftList;
	}

	public int getGiftCount() {
		return giftCount;
	}

	public void setGiftCount(int giftCount) {
		this.giftCount = giftCount;
	}

	public IOrderReportManager getOrderReportManager() {
		return orderReportManager;
	}

	public void setOrderReportManager(IOrderReportManager orderReportManager) {
		this.orderReportManager = orderReportManager;
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

	public List getPmtList() {
		return pmtList;
	}

	public void setPmtList(List pmtList) {
		this.pmtList = pmtList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public IReturnsOrderManager getReturnsOrderManager() {
		return returnsOrderManager;
	}

	public void setReturnsOrderManager(IReturnsOrderManager returnsOrderManager) {
		this.returnsOrderManager = returnsOrderManager;
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

	public void setRefuse_reson(String refuseReson) {
		refuse_reson = refuseReson;
	}

	public List getChshipLogList() {
		return chshipLogList;
	}

	public void setChshipLogList(List chshipLogList) {
		this.chshipLogList = chshipLogList;
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

	/*public IBankManager getBankManager() {
		return bankManager;
	}

	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}*/

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public AcceptPlugin getCloudFlowAcceptPlugin() {
		return cloudFlowAcceptPlugin;
	}

	public void setCloudFlowAcceptPlugin(AcceptPlugin cloudFlowAcceptPlugin) {
		this.cloudFlowAcceptPlugin = cloudFlowAcceptPlugin;
	}

	public OrderUtils getOrderUtils() {
		return orderUtils;
	}

	public void setOrderUtils(OrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public List<ItemCard> getItemcards() {
		return itemcards;
	}

	public void setItemcards(List<ItemCard> itemcards) {
		this.itemcards = itemcards;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public ICloudManager getCloudManager() {
		return cloudManager;
	}

	public void setCloudManager(ICloudManager cloudManager) {
		this.cloudManager = cloudManager;
	}

	public PromotionInf getPromotionServ() {
		return promotionServ;
	}

	public void setPromotionServ(PromotionInf promotionServ) {
		this.promotionServ = promotionServ;
	}

	public BankInf getBankServ() {
		return bankServ;
	}

	public void setBankServ(BankInf bankServ) {
		this.bankServ = bankServ;
	}

	public CardInf getCardServ() {
		return cardServ;
	}

	public void setCardServ(CardInf cardServ) {
		this.cardServ = cardServ;
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

}