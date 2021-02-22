package com.ztesoft.net.mall.core.action.backend;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DateUtil;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.action.order.IOrderDirector;
import com.ztesoft.net.mall.core.action.order.OrderBuilder;
import com.ztesoft.net.mall.core.action.order.OrderRequst;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CustReturned;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderItem;
import com.ztesoft.net.mall.core.service.ICustReturnedManager;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.IOrderNFlowManager;
import com.ztesoft.net.mall.core.service.IOrderUtils;
import com.ztesoft.net.mall.core.service.impl.cache.IGoodsCacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 客户资料返档
 * 
 * @author wui
 * 
 */
public class CustReturnedAction extends WWAction {
	private CustReturned custReturned;
	private String orderId;
	private String batchId;
	private Order order;
	private IOrderManager orderManager;
	private IOrderUtils orderUtils;
	private IOrderNFlowManager orderNFlowManager;
	private IOrderDirector orderDirector;
	private ICustReturnedManager custReturnedManager;
	private ICacheUtil cacheUtil;
	private IGoodsCacheUtil goodsCacheUtil;
	public IGoodsCacheUtil getGoodsUtil() {
		return goodsCacheUtil;
	}


	public void setGoodsUtil(IGoodsCacheUtil goodsCacheUtil) {
		this.goodsCacheUtil = goodsCacheUtil;
	}


	/**
	 * 显示商品申请审核界面
	 * @return
	 */
	public String showCustReturnedDialog() {
		order = orderManager.get(orderId);
		if(custReturned ==null)
			custReturned = new CustReturned();
		
		List<OrderItem>  orderItems= orderNFlowManager.listNotAcceptGoodsItem(orderId);
		Order order = orderManager.get(orderId);
		Goods goods = goodsCacheUtil.getGoodsById(order.getGoods_id());
		OrderItem orderItem =orderItems.get(0);
		
		/*custReturned.setAcc_nbr(orderItem.getAcc_nbr());
		custReturned.setCust_name(orderItem.getCust_name());
		custReturned.setCerti_type(orderItem.getCerti_type());
		custReturned.setCerti_number(orderItem.getCerti_number());
		custReturned.setTerminal_code(orderItem.getTerminal_code());
		custReturned.setTerminal_name(orderItem.getTerminal_name());*/
		custReturned.setCust_pwd(ManagerUtils.genRandowNum(6)); //获取6位随机密码
		custReturned.setCust_addr(order.getShip_addr());
		custReturned.setLan_id(order.getLan_id());
		custReturned.setOffer_name(goods.getName()); //资料返档
		custReturned.setCerti_type(Consts.DEF_CERTI_TYPE_A);
		
		return "cust_returned_add";
	}
	
	
	/**
	 * 资料返档
	 * @return
	 */
	public String custReturnedAdd() {
		order = orderManager.get(orderId);
		return "cust_returned_add";
	}
	
	
	
	//资料返档
	
	public String custReturned(){
		try {
			
			returned();
			this.json = "{result:1,message:'订单[" +orderId+ "]资料返档成功'}";
		} catch (RuntimeException e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			e.printStackTrace();
			this.json = "{result:0,message:\"客户资料返档失败：" + e.getMessage() + "\"}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	/**
	 * 
	 * 订单处理流程 触发条件：前台商品审核时触发
	 */
	public void returned() {
		
		Order order = this.orderManager.get(orderId);
		OrderRequst orderRequst = new OrderRequst();
		orderRequst.setService_name(order.getType_code());
		orderRequst.setFlow_name(OrderBuilder.ACCEPT);
		orderRequst.setAccept_action(OrderStatus.ACCEPT_ACTION_ORDER);
		orderRequst.setOrderId(orderId);
		orderRequst.setCustReturned(custReturned);
		
		custReturned.setLan_id(order.getLan_id());
		orderDirector.perform(orderRequst);

	}
	
	public String batchList(){
		
		if(custReturned == null){
			custReturned = new CustReturned();
		}
		if(!StringUtils.isEmpty(batchId)){
			custReturned.setBatch_id(batchId);
		}
		
		this.webpage = custReturnedManager.getCustReturnByBatchId(custReturned, this.getPage(), this.getPageSize());
		if(excel != null && !"".equals(excel)){
			HttpServletRequest request = ServletActionContext.getRequest();
			String[] title = {"受理单号","客户名称","证件类型", "证件号码", "销售品名称", "业务号码", "终端串码", "处理结果", "返档时间", "备注"};
			String[] content = {"accept_id","cust_name","certi_type_name", "certi_number", "offer_name", "acc_nbr"
					, "terminal_code", "state_name", "status_date", "comments"};
			String fileTitle = "资料返档" + DateUtil.toString(new Date(), "yyyyMMdd");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			request.setAttribute("fileTitle", fileTitle);
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}
		return "batch_list";
	}
	
	
	
	public String list(){
		this.webpage = custReturnedManager.getCustReturnByBatchId(custReturned, this.getPage(), this.getPageSize());
		
		List<CustReturned> list =  this.webpage.getResult();
		if(list != null && !list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				CustReturned custReturned = list.get(i);
				if(custReturned.getComments().length() > 10){
					custReturned.setS_comments(custReturned.getComments().substring(0,10) + "...");
				}else{
					custReturned.setS_comments(custReturned.getComments());
				}
				
;			}
		}
		
		if(excel != null && !"".equals(excel)){
			HttpServletRequest request = ServletActionContext.getRequest();
			String[] title = {"受理单号","客户名称","证件类型", "证件号码", "销售品名称", "业务号码", "终端串码", "处理结果", "返档时间", "备注"};
			String[] content = {"accept_id","cust_name","certi_type_name", "certi_number", "offer_name", "acc_nbr"
					, "terminal_code", "state_name", "status_date", "comments"};
			String fileTitle = "资料返档" + DateUtil.toString(new Date(), "yyyyMMdd");
			request.setAttribute("title", title);
			request.setAttribute("content", content);
			request.setAttribute("fileTitle", fileTitle);
			/**
			 * 修改退出方式 mochunrun 20130917
			 */
			DownLoadUtil.export(webpage, fileTitle, title, content, ServletActionContext.getResponse());
			return null;
			//return "export_excel_list";
		}
		return "list";
	}

	public CustReturned getCustReturned() {
		return custReturned;
	}

	public void setCustReturned(CustReturned custReturned) {
		this.custReturned = custReturned;
	}

	

	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public IOrderManager getOrderManager() {
		return orderManager;
	}

	public void setOrderManager(IOrderManager orderManager) {
		this.orderManager = orderManager;
	}


	public IOrderUtils getOrderUtils() {
		return orderUtils;
	}


	public void setOrderUtils(IOrderUtils orderUtils) {
		this.orderUtils = orderUtils;
	}


	public IOrderNFlowManager getOrderNFlowManager() {
		return orderNFlowManager;
	}


	public void setOrderNFlowManager(IOrderNFlowManager orderNFlowManager) {
		this.orderNFlowManager = orderNFlowManager;
	}


	public IOrderDirector getOrderDirector() {
		return orderDirector;
	}


	public void setOrderDirector(IOrderDirector orderDirector) {
		this.orderDirector = orderDirector;
	}


	public String getBatchId() {
		return batchId;
	}


	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}


	public ICustReturnedManager getCustReturnedManager() {
		return custReturnedManager;
	}


	public void setCustReturnedManager(ICustReturnedManager custReturnedManager) {
		this.custReturnedManager = custReturnedManager;
	}


	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}


	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	
	

}
