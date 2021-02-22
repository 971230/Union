package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i
 * 查询归档历史数据
 * 60G的内存可缓存15728640条订单树数据
 * 
 */
@RequestBeanAnnontion(primary_keys="order_id",table_name="es_order_trees_his",table_archive="yes",cache_store="yes",service_desc="订单树对象，数据库存储完整的json数据值")
@Service
public class OrderTreeHisBusiRequest extends ZteBusiRequest  implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;
	
	@RequestFieldAnnontion(dname="order_id",desc="订单编号",need_query="no")
	String order_id;
	
	@RequestFieldAnnontion(dname="es_order_his",desc="订单基本信息实体",need_query="no")
	OrderBusiRequest orderBusiRequest = new OrderBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_ext_his",desc="订单扩展信息实体",need_query="no")
	OrderExtBusiRequest orderExtBusiRequest = new OrderExtBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_items_his",desc="订单商品单基本信息实体",need_query="no",service_name="OrderItemBusiRequest")
	List<OrderItemBusiRequest> orderItemBusiRequests = new ArrayList<OrderItemBusiRequest>();
	

	@RequestFieldAnnontion(dname="es_delivery_his",desc="订单物流配送列表信息",need_query="no",service_name="OrderDeliveryBusiRequest")
	List<OrderDeliveryBusiRequest> orderDeliveryBusiRequests = new ArrayList<OrderDeliveryBusiRequest>();
	
	
	@RequestFieldAnnontion(dname="es_payment_logs_his",desc="订单支付列表信息",need_query="no",service_name="OrderPayBusiRequest")
	List<OrderPayBusiRequest> orderPayBusiRequests = new ArrayList<OrderPayBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_attr_inst_his",desc="订单属性列表信息",need_query="no",service_name="AttrInstBusiRequest")
	List<AttrInstBusiRequest> attrInstBusiRequests = new ArrayList<AttrInstBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_attr_package_his",desc="可选包列表信息",need_query="no",service_name="AttrPackageBusiRequest")
	List<AttrPackageBusiRequest> packageBusiRequests = new ArrayList<AttrPackageBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_attr_fee_info_his",desc="费用明细列表信息",need_query="no",service_name="AttrFeeInfoBusiRequest")
	List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = new ArrayList<AttrFeeInfoBusiRequest>();

	@RequestFieldAnnontion(dname="es_order_phone_info",desc="号码信息",need_query="no",service_name="OrderPhoneInfoBusiRequest")
	OrderPhoneInfoBusiRequest phoneInfoBusiRequest = new OrderPhoneInfoBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_phone_info_fuka",desc="副卡号码信息",need_query="no",service_name="OrderPhoneInfoFukaBusiRequest")
	List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests = new ArrayList<OrderPhoneInfoFukaBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_attr_package_fuka",desc="副卡可选包列表信息",need_query="no",service_name="AttrPackageFukaBusiRequest")
	List<AttrPackageFukaBusiRequest> attrPackageFukaBusiRequests = new ArrayList<AttrPackageFukaBusiRequest>();	

	@RequestFieldAnnontion(dname="ES_ORDER_SUB_PRODCUT",desc="附加产品列表信息",need_query="no",service_name="OrderSubProductBusiRequest")
	List<OrderSubProductBusiRequest> orderSubProductBusiRequest = new ArrayList<OrderSubProductBusiRequest>();
	
	@RequestFieldAnnontion(dname="ES_ATTR_PACKAGE_SubProd",desc="附加产品可选包列表信息",need_query="no",service_name="AttrPackageSubProdBusiRequest")
	List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest = new ArrayList<AttrPackageSubProdBusiRequest>();	
	
	@RequestFieldAnnontion(dname="es_order_items_extvtl",desc="订单多商品实体",need_query="no",service_name="OrderItemExtvtlBusiRequest")
	List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests = new ArrayList<OrderItemExtvtlBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_order_lock",desc="订单锁定表",need_query="no",service_name="OrderLockBusiRequest")
	List<OrderLockBusiRequest> orderLockBusiRequests = new ArrayList<OrderLockBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_order_realname_info",desc="订单实名制信息表",need_query="no")
	OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest = new OrderRealNameInfoBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_wms_key_info",desc="自动化关键信息状态记录表",need_query="no")
	OrderWMSKeyInfoBusiRequest orderWMSKeyInfoBusiRequest = new OrderWMSKeyInfoBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_wms_key_info",desc="自动化关键信息状态记录表",need_query="no")
	OrderAdslBusiRequest orderAdslBusiRequest = new OrderAdslBusiRequest();
	
	@RequestFieldAnnontion(dname="create_time",desc="创建时间",need_query="no")
	String create_time;
	
	@RequestFieldAnnontion(dname="update_time",desc="更新时间",need_query="no")
	String update_time;
	
	
	public OrderBusiRequest getOrderBusiRequest() {
		return orderBusiRequest;
	}

	public void setOrderBusiRequest(OrderBusiRequest orderBusiRequest) {
		this.orderBusiRequest = orderBusiRequest;
	}


	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderTreeHisBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderTreeHisBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<OrderTreeHisBusiRequest> resp = new QueryResponse<OrderTreeHisBusiRequest>();
		T t =  RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this); //为空，走历史归档表
		return t;
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}

	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@NotDbField
	public String getApiMethodName() {
		return null;
	}

	public OrderExtBusiRequest getOrderExtBusiRequest() {
		return orderExtBusiRequest;
	}

	public void setOrderExtBusiRequest(OrderExtBusiRequest orderExtBusiRequest) {
		this.orderExtBusiRequest = orderExtBusiRequest;
	}

	public List<OrderItemBusiRequest> getOrderItemBusiRequests() {
		return orderItemBusiRequests;
	}

	public void setOrderItemBusiRequests(
			List<OrderItemBusiRequest> orderItemBusiRequests) {
		this.orderItemBusiRequests = orderItemBusiRequests;
	}

	

	public List<OrderDeliveryBusiRequest> getOrderDeliveryBusiRequests() {
		return orderDeliveryBusiRequests;
	}

	public void setOrderDeliveryBusiRequests(
			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequests) {
		this.orderDeliveryBusiRequests = orderDeliveryBusiRequests;
	}

	public List<OrderPayBusiRequest> getOrderPayBusiRequests() {
		return orderPayBusiRequests;
	}

	public void setOrderPayBusiRequests(
			List<OrderPayBusiRequest> orderPayBusiRequests) {
		this.orderPayBusiRequests = orderPayBusiRequests;
	}

	public List<AttrInstBusiRequest> getAttrInstBusiRequests() {
		return attrInstBusiRequests;
	}

	public void setAttrInstBusiRequests(
			List<AttrInstBusiRequest> attrInstBusiRequests) {
		this.attrInstBusiRequests = attrInstBusiRequests;
	}

	public List<AttrPackageBusiRequest> getPackageBusiRequests() {
		return packageBusiRequests;
	}

	public void setPackageBusiRequests(
			List<AttrPackageBusiRequest> packageBusiRequests) {
		this.packageBusiRequests = packageBusiRequests;
	}

	public List<AttrFeeInfoBusiRequest> getFeeInfoBusiRequests() {
		return feeInfoBusiRequests;
	}

	public void setFeeInfoBusiRequests(
			List<AttrFeeInfoBusiRequest> feeInfoBusiRequests) {
		this.feeInfoBusiRequests = feeInfoBusiRequests;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public OrderPhoneInfoBusiRequest getPhoneInfoBusiRequest() {
		return phoneInfoBusiRequest;
	}

	public void setPhoneInfoBusiRequest(
			OrderPhoneInfoBusiRequest phoneInfoBusiRequest) {
		this.phoneInfoBusiRequest = phoneInfoBusiRequest;
	}

	

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFukaBusiRequests() {
		return orderPhoneInfoFukaBusiRequests;
	}

	public void setOrderPhoneInfoFukaBusiRequests(
			List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests) {
		this.orderPhoneInfoFukaBusiRequests = orderPhoneInfoFukaBusiRequests;
	}

	public List<AttrPackageFukaBusiRequest> getAttrPackageFukaBusiRequests() {
		return attrPackageFukaBusiRequests;
	}

	public void setAttrPackageFukaBusiRequests(
			List<AttrPackageFukaBusiRequest> attrPackageFukaBusiRequests) {
		this.attrPackageFukaBusiRequests = attrPackageFukaBusiRequests;
	}

	public List<OrderSubProductBusiRequest> getOrderSubProductBusiRequest() {
		return orderSubProductBusiRequest;
	}

	public void setOrderSubProductBusiRequest(
			List<OrderSubProductBusiRequest> orderSubProductBusiRequest) {
		this.orderSubProductBusiRequest = orderSubProductBusiRequest;
	}

	public List<AttrPackageSubProdBusiRequest> getAttrPackageSubProdBusiRequest() {
		return attrPackageSubProdBusiRequest;
	}

	public void setAttrPackageSubProdBusiRequest(
			List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest) {
		this.attrPackageSubProdBusiRequest = attrPackageSubProdBusiRequest;
	}

	public List<OrderItemExtvtlBusiRequest> getOrderItemExtvtlBusiRequests() {
		return orderItemExtvtlBusiRequests;
	}

	public void setOrderItemExtvtlBusiRequests(
			List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests) {
		this.orderItemExtvtlBusiRequests = orderItemExtvtlBusiRequests;
	}
	public List<OrderLockBusiRequest> getOrderLockBusiRequests() {
		return orderLockBusiRequests;
	}

	public void setOrderLockBusiRequests(
			List<OrderLockBusiRequest> orderLockBusiRequests) {
		this.orderLockBusiRequests = orderLockBusiRequests;
	}

	public OrderRealNameInfoBusiRequest getOrderRealNameInfoBusiRequest() {
		return orderRealNameInfoBusiRequest;
	}

	public void setOrderRealNameInfoBusiRequest(
			OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest) {
		this.orderRealNameInfoBusiRequest = orderRealNameInfoBusiRequest;
	}

	public OrderWMSKeyInfoBusiRequest getOrderWMSKeyInfoBusiRequest() {
		return orderWMSKeyInfoBusiRequest;
	}

	public void setOrderWMSKeyInfoBusiRequest(
			OrderWMSKeyInfoBusiRequest orderWMSKeyInfoBusiRequest) {
		this.orderWMSKeyInfoBusiRequest = orderWMSKeyInfoBusiRequest;
	}

	public OrderAdslBusiRequest getOrderAdslBusiRequest() {
		return orderAdslBusiRequest;
	}

	public void setOrderAdslBusiRequest(OrderAdslBusiRequest orderAdslBusiRequest) {
		this.orderAdslBusiRequest = orderAdslBusiRequest;
	}
	
}
