package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Delivery;
import com.ztesoft.net.mall.core.model.DeliveryItem;
import com.ztesoft.net.mall.core.model.PaymentLog;
import com.ztesoft.net.mall.core.service.IOrderReportManager;

/**
 * @author lzf<br/>
 * 2010-4-12下午12:17:49<br/>
 * version 1.0
 */
public class OrderReportAction extends WWAction {
	
	private IOrderReportManager orderReportManager;
	private String order;
	private String id;
	private PaymentLog payment;
	private Delivery delivery;
	private List<DeliveryItem> listDeliveryItem;
	
	private String startDate;
	private String endDate;
	private String create_type;
	
	private String order_id;
	private String payment_id;
	private String member_name;
	private String delivery_id;
	
	//供应商订单报表 lqy
	public String orderReportList(){
		this.webpage = orderReportManager.orderCountList(this.getPage(), this.getPageSize(),startDate,endDate);
		return "orderReportList";
	}
	//供货商报表详情
	public String supplierOrderDetail(){
		this.webpage = orderReportManager.ListOrderWithStaffId(id,this.getPage(), this.getPageSize(),startDate,endDate);
		return "supplierOrderDetail";
	}
	
	public String paymentList(){
		this.webpage = orderReportManager.listPayment(this.getPage(), this.getPageSize(), order, payment_id, order_id, member_name);
		return "paymentList";
	}
	
	public String paymentDetail(){
		payment = orderReportManager.getPayment(id);
		return "paymentDetail";
	}
	
	public String refundList(){
		this.webpage = orderReportManager.listRefund(this.getPage(), this.getPageSize(), order, payment_id, order_id, member_name);
		return "refundList";
	}
	
	public String refundDetail(){
		payment = orderReportManager.getPayment(id);
		return "refundDetail";
	}
	
	public String shippingList(){
		this.webpage = orderReportManager.listShipping(this.getPage(), this.getPageSize(), order,create_type, order_id,  delivery_id,  member_name);
		return "shippingList";
	}
	
	public String shippingDetail(){
		delivery = orderReportManager.getDelivery(id);
		listDeliveryItem = orderReportManager.listDeliveryItem(id);
		return "shippingDetail";
	}
	
	public String returnedList(){
		this.webpage = orderReportManager.listReturned(this.getPage(), this.getPageSize(), order,create_type, order_id,  delivery_id,  member_name);
		return "returnedList";
	}
	
	public String returnedDetail(){
		delivery = orderReportManager.getDelivery(id);
		listDeliveryItem = orderReportManager.listDeliveryItem(id);
		return "returnedDetail";
	}
	
	////
	

	public IOrderReportManager getOrderReportManager() {
		return orderReportManager;
	}

	public void setOrderReportManager(IOrderReportManager orderReportManager) {
		this.orderReportManager = orderReportManager;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PaymentLog getPayment() {
		return payment;
	}

	public void setPayment(PaymentLog payment) {
		this.payment = payment;
	}

	public Delivery getDelivery() {
		return delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public List<DeliveryItem> getListDeliveryItem() {
		return listDeliveryItem;
	}

	public void setListDeliveryItem(List<DeliveryItem> listDeliveryItem) {
		this.listDeliveryItem = listDeliveryItem;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCreate_type() {
		return create_type;
	}
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}
	

}
