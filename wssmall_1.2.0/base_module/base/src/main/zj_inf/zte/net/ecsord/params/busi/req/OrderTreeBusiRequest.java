package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.HashMap;
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
 * @author wu.i 订单树对象，读取所有节点数据
 * 
 *         声明：建立的业务对象需要以BusiRequest作为后缀
 * 
 *         60G的内存可缓存15728640条订单树数据
 * 
 */
@RequestBeanAnnontion(primary_keys = "order_id", table_name = "es_order_trees", table_archive = "yes", cache_store = "yes", service_desc = "订单树对象，数据库存储完整的json数据值")
@Service
public class OrderTreeBusiRequest extends ZteBusiRequest implements IZteBusiRequestHander {

	private static final long serialVersionUID = 1L;

	@RequestFieldAnnontion(dname = "order_id", desc = "订单编号", need_query = "no")
	String order_id;

	@RequestFieldAnnontion(dname = "es_order", desc = "订单基本信息实体", need_query = "no")
	OrderBusiRequest orderBusiRequest = new OrderBusiRequest();

	@RequestFieldAnnontion(dname = "es_order_ext", desc = "订单扩展信息实体", need_query = "no")
	OrderExtBusiRequest orderExtBusiRequest = new OrderExtBusiRequest();

	@RequestFieldAnnontion(dname = "es_order_items", desc = "订单商品单基本信息实体", need_query = "no", service_name = "OrderItemBusiRequest")
	List<OrderItemBusiRequest> orderItemBusiRequests = new ArrayList<OrderItemBusiRequest>();

	@RequestFieldAnnontion(dname = "es_delivery", desc = "订单物流配送列表信息", need_query = "no", service_name = "OrderDeliveryBusiRequest")
	List<OrderDeliveryBusiRequest> orderDeliveryBusiRequests = new ArrayList<OrderDeliveryBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_route_log", desc = "订单物流路由明细", need_insert = "no", service_name = "OrderRouteLogBusiRequest")
	List<OrderRouteLogBusiRequest> orderRouteLogRequests = new ArrayList<OrderRouteLogBusiRequest>();

	@RequestFieldAnnontion(dname = "es_payment_logs", desc = "订单支付列表信息", need_query = "no", service_name = "OrderPayBusiRequest")
	List<OrderPayBusiRequest> orderPayBusiRequests = new ArrayList<OrderPayBusiRequest>();

	@RequestFieldAnnontion(dname = "es_attr_inst", desc = "订单属性列表信息", need_query = "no", service_name = "AttrInstBusiRequest")
	List<AttrInstBusiRequest> attrInstBusiRequests = new ArrayList<AttrInstBusiRequest>();

	@RequestFieldAnnontion(dname = "es_attr_package", desc = "可选包列表信息", need_query = "no", service_name = "AttrPackageBusiRequest")
	List<AttrPackageBusiRequest> packageBusiRequests = new ArrayList<AttrPackageBusiRequest>();

	@RequestFieldAnnontion(dname = "es_attr_fee_info", desc = "费用明细列表信息", need_query = "no", service_name = "AttrFeeInfoBusiRequest")
	List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = new ArrayList<AttrFeeInfoBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_phone_info", desc = "号码信息", need_query = "no", service_name = "OrderPhoneInfoBusiRequest")
	OrderPhoneInfoBusiRequest phoneInfoBusiRequest = new OrderPhoneInfoBusiRequest();

	@RequestFieldAnnontion(dname = "ES_ORDER_SUB_PRODCUT", desc = "附加产品列表信息", need_query = "no", service_name = "OrderSubProductBusiRequest")
	List<OrderSubProductBusiRequest> orderSubProductBusiRequest = new ArrayList<OrderSubProductBusiRequest>();

	@RequestFieldAnnontion(dname = "ES_ATTR_PACKAGE_SubProd", desc = "附加产品可选包列表信息", need_query = "no", service_name = "AttrPackageSubProdBusiRequest")
	List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest = new ArrayList<AttrPackageSubProdBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_phone_info_fuka", desc = "副卡号码信息", need_query = "no", service_name = "OrderPhoneInfoFukaBusiRequest")
	List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests = new ArrayList<OrderPhoneInfoFukaBusiRequest>();

	@RequestFieldAnnontion(dname = "es_attr_package_fuka", desc = "副卡可选包列表信息", need_query = "no", service_name = "AttrPackageFukaBusiRequest")
	List<AttrPackageFukaBusiRequest> attrPackageFukaBusiRequests = new ArrayList<AttrPackageFukaBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_items_extvtl", desc = "订单多商品实体", need_query = "no", service_name = "OrderItemExtvtlBusiRequest")
	List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests = new ArrayList<OrderItemExtvtlBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_lock", desc = "订单锁定表", need_query = "no", service_name = "OrderLockBusiRequest")
	List<OrderLockBusiRequest> orderLockBusiRequests = new ArrayList<OrderLockBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_realname_info", desc = "订单实名制信息表", need_query = "no")
	OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest = new OrderRealNameInfoBusiRequest();

	@RequestFieldAnnontion(dname = "es_order_wms_key_info", desc = "自动化关键信息状态记录表", need_query = "no")
	OrderWMSKeyInfoBusiRequest orderWMSKeyInfoBusiRequest = new OrderWMSKeyInfoBusiRequest();

	@RequestFieldAnnontion(dname = "es_order_zhwq_adsl", desc = "宽带信息表", need_query = "no", service_name = "OrderAdslBusiRequest")
	List<OrderAdslBusiRequest> orderAdslBusiRequest = new ArrayList<OrderAdslBusiRequest>();

	@RequestFieldAnnontion(dname = "es_attr_terminal_ext", desc = "终端信息表", need_query = "no", service_name = "AttrTmResourceInfoBusiRequest")
	List<AttrTmResourceInfoBusiRequest> tmResourceInfoBusiRequests = new ArrayList<AttrTmResourceInfoBusiRequest>();
	// @RequestFieldAnnontion(dname = "es_work_order", desc = "工单信息表",
	// need_query = "no", service_name = "OrderWorksBusiRequest")
	// List<OrderWorksBusiRequest> orderWorksBusiRequest = new
	// ArrayList<OrderWorksBusiRequest>();

	public List<OrderInternetBusiRequest> getOrderInternetBusiRequest() {
		return OrderInternetBusiRequest;
	}

	public void setOrderInternetBusiRequest(List<OrderInternetBusiRequest> orderInternetBusiRequest) {
		OrderInternetBusiRequest = orderInternetBusiRequest;
	}

	@RequestFieldAnnontion(dname = "es_order_internet_info", desc = "互联网号码信息表", need_query = "no", service_name = "OrderInternetBusiRequest")
	List<OrderInternetBusiRequest> OrderInternetBusiRequest = new ArrayList<OrderInternetBusiRequest>();

	@RequestFieldAnnontion(dname = "es_order_file", desc = "订单图片文件信息表", need_query = "no", service_name = "OrderFileBusiRequest")
	List<OrderFileBusiRequest> orderFileBusiRequests = new ArrayList<OrderFileBusiRequest>();

	@RequestFieldAnnontion(dname = "ES_ORDER_SUB_PROD_INFO", desc = "温大子产品表", need_query = "no", service_name = "OrderSubProdInfoBusiRequest")
	List<OrderSubProdInfoBusiRequest> orderSubProdInfoBusiRequest = new ArrayList<OrderSubProdInfoBusiRequest>();

	@RequestFieldAnnontion(dname = "ES_ORDER_SUB_OTHER_INFO", desc = "集客业务专属信息节点表", need_query = "no", service_name = "OrderSubOtherInfoBusiRequest")
	OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest = new OrderSubOtherInfoBusiRequest();

	@RequestFieldAnnontion(dname = "create_time", desc = "创建时间", need_query = "no")
	String create_time;

	@RequestFieldAnnontion(dname = "update_time", desc = "更新时间", need_query = "no")
	String update_time;

	@RequestFieldAnnontion(dname = "col1", desc = "扩展字段1--是否重新匹配模式，yes重新匹配", need_query = "no")
	String col1;
	@RequestFieldAnnontion(dname = "col2", desc = "扩展字段1--是否重新匹配流程 ，yes重新匹配", need_query = "no")
	String col2;
	@RequestFieldAnnontion(dname = "col3", desc = "扩展字段1--区分是接口[EcsOrderConsts.TRACE_TRIGGER_INF]请求业务组件，还是页面[EcsOrderConsts.TRACE_TRIGGER_PAGE]请求", need_query = "no")
	String col3;
	@RequestFieldAnnontion(dname = "col4", desc = "扩展字段1", need_query = "no")
	String col4;
	@RequestFieldAnnontion(dname = "col5", desc = "扩展字段1", need_query = "no")
	String col5;
	@RequestFieldAnnontion(dname = "col6", desc = "扩展字段1", need_query = "no")
	String col6;
	@RequestFieldAnnontion(dname = "col7", desc = "扩展字段1", need_query = "no")
	String col7;
	@RequestFieldAnnontion(dname = "col8", desc = "扩展字段1", need_query = "no")
	String col8;
	@RequestFieldAnnontion(dname = "col9", desc = "扩展字段1-判读属性数据是否写入redis,yes是，no否", need_query = "no")
	String col9;
	@RequestFieldAnnontion(dname = "col10", desc = "扩展字段1--是否历史订单查询，yes是", need_query = "no")
	String col10;

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
		ZteInstUpdateRequest<OrderTreeBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderTreeBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {

		QueryResponse<OrderTreeBusiRequest> resp = new QueryResponse<OrderTreeBusiRequest>();
		T t = RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, this); // 为空，走历史归档表
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

	public void setOrderItemBusiRequests(List<OrderItemBusiRequest> orderItemBusiRequests) {
		this.orderItemBusiRequests = orderItemBusiRequests;
	}

	public List<OrderDeliveryBusiRequest> getOrderDeliveryBusiRequests() {
		return orderDeliveryBusiRequests;
	}

	public void setOrderDeliveryBusiRequests(List<OrderDeliveryBusiRequest> orderDeliveryBusiRequests) {
		this.orderDeliveryBusiRequests = orderDeliveryBusiRequests;
	}

	public List<OrderPayBusiRequest> getOrderPayBusiRequests() {
		return orderPayBusiRequests;
	}

	public void setOrderPayBusiRequests(List<OrderPayBusiRequest> orderPayBusiRequests) {
		this.orderPayBusiRequests = orderPayBusiRequests;
	}

	/**
	 * 获取所有属性实例数据 add by wui 此方法废弃使用
	 * 
	 * @return
	 */
	@Deprecated
	public List<AttrInstBusiRequest> getAttrInstBusiRequests() {
		return attrInstBusiRequests;
	}

	/**
	 * 设置所有属性实例数据
	 * 
	 * @param attrInstBusiRequests
	 *            此方法废弃使用
	 */
	@Deprecated
	public void setAttrInstBusiRequests(List<AttrInstBusiRequest> attrInstBusiRequests) {
	}

	public List<AttrPackageBusiRequest> getPackageBusiRequests() {
		return packageBusiRequests;
	}

	public void setPackageBusiRequests(List<AttrPackageBusiRequest> packageBusiRequests) {
		this.packageBusiRequests = packageBusiRequests;
	}

	public List<AttrFeeInfoBusiRequest> getFeeInfoBusiRequests() {
		return feeInfoBusiRequests;
	}

	public void setFeeInfoBusiRequests(List<AttrFeeInfoBusiRequest> feeInfoBusiRequests) {
		this.feeInfoBusiRequests = feeInfoBusiRequests;
	}

	public List<AttrGiftInfoBusiRequest> getGiftInfoBusiRequests() {
		//删除无用代码  xiao.ruidan 20180518
		return new ArrayList<AttrGiftInfoBusiRequest>();
	}

	public void setGiftInfoBusiRequests(List<AttrGiftInfoBusiRequest> giftInfoBusiRequests) {
//		this.giftInfoBusiRequests = giftInfoBusiRequests;
	}

	public List<AttrDiscountInfoBusiRequest> getDiscountInfoBusiRequests() {
		return new ArrayList<AttrDiscountInfoBusiRequest>();
	}

	public void setDiscountInfoBusiRequests(List<AttrDiscountInfoBusiRequest> discountInfoBusiRequests) {
//		this.discountInfoBusiRequests = discountInfoBusiRequests;
	}

	public List<AttrLeagueInfoBusiRequest> getLeagueInfoBusiRequests() {
		return new ArrayList<AttrLeagueInfoBusiRequest>();
	}

	public void setLeagueInfoBusiRequests(List<AttrLeagueInfoBusiRequest> leagueInfoBusiRequests) {
//		this.leagueInfoBusiRequests = leagueInfoBusiRequests;
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

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OrderPhoneInfoBusiRequest getPhoneInfoBusiRequest() {
		return phoneInfoBusiRequest;
	}

	public void setPhoneInfoBusiRequest(OrderPhoneInfoBusiRequest phoneInfoBusiRequest) {
		this.phoneInfoBusiRequest = phoneInfoBusiRequest;
	}

	public List<OrderItemExtvtlBusiRequest> getOrderItemExtvtlBusiRequests() {
		return orderItemExtvtlBusiRequests;
	}

	public void setOrderItemExtvtlBusiRequests(List<OrderItemExtvtlBusiRequest> orderItemExtvtlBusiRequests) {
		this.orderItemExtvtlBusiRequests = orderItemExtvtlBusiRequests;
	}

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFukaBusiRequests() {
		return orderPhoneInfoFukaBusiRequests;
	}

	public void setOrderPhoneInfoFukaBusiRequests(List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaBusiRequests) {
		this.orderPhoneInfoFukaBusiRequests = orderPhoneInfoFukaBusiRequests;
	}

	public List<AttrPackageFukaBusiRequest> getAttrPackageFukaBusiRequests() {
		return attrPackageFukaBusiRequests;
	}

	public void setAttrPackageFukaBusiRequests(List<AttrPackageFukaBusiRequest> attrPackageFukaBusiRequests) {
		this.attrPackageFukaBusiRequests = attrPackageFukaBusiRequests;
	}

	public List<OrderSubProductBusiRequest> getOrderSubProductBusiRequest() {
		return orderSubProductBusiRequest;
	}

	public void setOrderSubProductBusiRequest(List<OrderSubProductBusiRequest> orderSubProductBusiRequest) {
		this.orderSubProductBusiRequest = orderSubProductBusiRequest;
	}

	public List<AttrPackageSubProdBusiRequest> getAttrPackageSubProdBusiRequest() {
		return attrPackageSubProdBusiRequest;
	}

	public void setAttrPackageSubProdBusiRequest(List<AttrPackageSubProdBusiRequest> attrPackageSubProdBusiRequest) {
		this.attrPackageSubProdBusiRequest = attrPackageSubProdBusiRequest;
	}

	public List<OrderActivityBusiRequest> getOrderActivityBusiRequest() {
		return new ArrayList<OrderActivityBusiRequest>();
	}

	public void setOrderActivityBusiRequest(List<OrderActivityBusiRequest> orderActivityBusiRequest) {
//		this.orderActivityBusiRequest = orderActivityBusiRequest;
	}

	public List<AttrPackageActivityBusiRequest> getAttrPackageActivityBusiRequest() {
		return new ArrayList<AttrPackageActivityBusiRequest>();
	}

	public void setAttrPackageActivityBusiRequest(List<AttrPackageActivityBusiRequest> attrPackageActivityBusiRequest) {
//		this.attrPackageActivityBusiRequest = attrPackageActivityBusiRequest;
	}

	public List<AttrZhuanDuiBaoBusiRequest> getZhuanDuiBaoBusiRequest() {
		return new ArrayList<AttrZhuanDuiBaoBusiRequest>();
	}

	public void setZhuanDuiBaoBusiRequest(List<AttrZhuanDuiBaoBusiRequest> zhuanDuiBaoBusiRequest) {
//		this.zhuanDuiBaoBusiRequest = zhuanDuiBaoBusiRequest;
	}

	public List<OrderSpProductBusiRequest> getSpProductBusiRequest() {
		return new ArrayList<OrderSpProductBusiRequest>();
	}

	public void setSpProductBusiRequest(List<OrderSpProductBusiRequest> spProductBusiRequest) {
//		this.spProductBusiRequest = spProductBusiRequest;
	}

	public List<OrderRouteLogBusiRequest> getOrderRouteLogRequests() {
		return orderRouteLogRequests;
	}

	public void setOrderRouteLogRequests(List<OrderRouteLogBusiRequest> orderRouteLogRequests) {
		this.orderRouteLogRequests = orderRouteLogRequests;
	}

	public List<HuashengOrderBusiRequest> getHuashengOrderBusiRequest() {
		return new ArrayList<HuashengOrderBusiRequest>();
	}

	public void setHuashengOrderBusiRequest(List<HuashengOrderBusiRequest> huashengOrderBusiRequest) {
//		this.huashengOrderBusiRequest = huashengOrderBusiRequest;
	}

	public List<HuashengOrderItemBusiRequest> getHuashengOrderItemBusiRequest() {
		return new ArrayList<HuashengOrderItemBusiRequest>();
	}

	public void setHuashengOrderItemBusiRequest(List<HuashengOrderItemBusiRequest> huashengOrderItemBusiRequest) {
//		this.huashengOrderItemBusiRequest = huashengOrderItemBusiRequest;
	}

	public List<OrderLockBusiRequest> getOrderLockBusiRequests() {
		return orderLockBusiRequests;
	}

	public void setOrderLockBusiRequests(List<OrderLockBusiRequest> orderLockBusiRequests) {
		this.orderLockBusiRequests = orderLockBusiRequests;
	}

	public OrderRealNameInfoBusiRequest getOrderRealNameInfoBusiRequest() {
		return orderRealNameInfoBusiRequest;
	}

	public void setOrderRealNameInfoBusiRequest(OrderRealNameInfoBusiRequest orderRealNameInfoBusiRequest) {
		this.orderRealNameInfoBusiRequest = orderRealNameInfoBusiRequest;
	}

	public OrderWMSKeyInfoBusiRequest getOrderWMSKeyInfoBusiRequest() {
		return orderWMSKeyInfoBusiRequest;
	}

	public void setOrderWMSKeyInfoBusiRequest(OrderWMSKeyInfoBusiRequest orderWMSKeyInfoBusiRequest) {
		this.orderWMSKeyInfoBusiRequest = orderWMSKeyInfoBusiRequest;
	}

	public List<OrderAdslBusiRequest> getOrderAdslBusiRequest() {
		return orderAdslBusiRequest;
	}

	public void setOrderAdslBusiRequest(List<OrderAdslBusiRequest> orderAdslBusiRequest) {
		this.orderAdslBusiRequest = orderAdslBusiRequest;
	}

	public List<OrderFileBusiRequest> getOrderFileBusiRequests() {
		return orderFileBusiRequests;
	}

	public void setOrderFileBusiRequests(List<OrderFileBusiRequest> orderFileBusiRequests) {
		this.orderFileBusiRequests = orderFileBusiRequests;
	}

	public List<OrderSubProdInfoBusiRequest> getOrderSubProdInfoBusiRequest() {
		return orderSubProdInfoBusiRequest;
	}

	public void setOrderSubProdInfoBusiRequest(List<OrderSubProdInfoBusiRequest> orderSubProdInfoBusiRequest) {
		this.orderSubProdInfoBusiRequest = orderSubProdInfoBusiRequest;
	}

	public OrderSubOtherInfoBusiRequest getOrderSubOtherInfoBusiRequest() {
		return orderSubOtherInfoBusiRequest;
	}

	public void setOrderSubOtherInfoBusiRequest(OrderSubOtherInfoBusiRequest orderSubOtherInfoBusiRequest) {
		this.orderSubOtherInfoBusiRequest = orderSubOtherInfoBusiRequest;
	}

	public List<AttrTmResourceInfoBusiRequest> getTmResourceInfoBusiRequests() {
		return tmResourceInfoBusiRequests;
	}

	public void setTmResourceInfoBusiRequests(List<AttrTmResourceInfoBusiRequest> tmResourceInfoBusiRequests) {
		this.tmResourceInfoBusiRequests = tmResourceInfoBusiRequests;
	}


	

}
