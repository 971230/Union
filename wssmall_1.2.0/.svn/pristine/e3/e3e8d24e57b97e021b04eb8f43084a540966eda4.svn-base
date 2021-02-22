package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.ecsord.params.busi.req.OrderItemAgentMoneyBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsAptPrintsBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemsValiLogBusiRequest;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="item_id",table_name="es_order_items",service_desc="订单扩展信息横表")
public class OrderItemBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{
	
	private static final long serialVersionUID = 1L;


	@RequestFieldAnnontion(dname="ES_ORDER_ITEMS_EXT",need_insert="no",desc="订单商品单扩展信息实体")
	OrderItemExtBusiRequest orderItemExtBusiRequest = new OrderItemExtBusiRequest();
	
//	@RequestFieldAnnontion(dname="ES_ORDER_ITEMS_AGENT_MONEY",need_insert="no",desc="订单商品代理商资金批扣信息")
//	OrderItemAgentMoneyBusiRequest orderItemAgentMoneyBusiRequest = new OrderItemAgentMoneyBusiRequest();
//	
//	
	//订单商品--产品信息，self入库即可
	@RequestFieldAnnontion(dname="ES_ORDER_ITEMS_PROD",need_insert="no",desc="订单产品基本信息列表",service_name="OrderItemProdBusiRequest")
	List<OrderItemProdBusiRequest> orderItemProdBusiRequests = new ArrayList<OrderItemProdBusiRequest>();
//	
//	@RequestFieldAnnontion(dname="ES_ORDER_ITEMS_APT_PRINTS",desc="订单商品单受理单打印信息",need_insert="no",service_name="OrderItemsAptPrintsBusiRequest")
//	List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequests = new ArrayList<OrderItemsAptPrintsBusiRequest>();
//	
//	@RequestFieldAnnontion(dname="ES_ORDER_ITEMS_VALI_LOG",desc="订单开户人证件照图片校验日志",need_insert="no",service_name="OrderItemsValiLogBusiRequest")
//	List<OrderItemsValiLogBusiRequest> orderItemsValiLogList = new ArrayList<OrderItemsValiLogBusiRequest>();
//	
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
    private String item_id;
	@RequestFieldAnnontion()
    private String goods_id;
	@RequestFieldAnnontion()
    private String spec_id;
	@RequestFieldAnnontion()
    private Integer num;
	@RequestFieldAnnontion()
    private Integer ship_num;
	@RequestFieldAnnontion()
    private String name;
	
   
	@RequestFieldAnnontion()
    private String addon;
	@RequestFieldAnnontion()
    private Double price;
	@RequestFieldAnnontion()
    private Long gainedpoint;
  
	@RequestFieldAnnontion()
    private String source_from;
	@RequestFieldAnnontion()
	private String lv_id;
	@RequestFieldAnnontion()
	private Double coupon_price;
	@RequestFieldAnnontion()
	private String unit;
	@RequestFieldAnnontion()
	private Integer item_type=0;// 0商品 1配件 2赠品
	@RequestFieldAnnontion()
	private String product_id ="";
	@RequestFieldAnnontion()
	private String col2;					//资金归集扣款
	@RequestFieldAnnontion()
	private String col3;					//资金归集扣款时间
	@RequestFieldAnnontion()
	private String col4;					//资金归集返销
	@RequestFieldAnnontion()
	private String col5;					//资金归集返销时间
	@RequestFieldAnnontion()
	private String col6;					//扣减金额
	@RequestFieldAnnontion()
	private String col7;					//返销金额
	
	
	private String sn;
	private String  order_outer_id;
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getSpec_id() {
		return spec_id;
	}

	public void setSpec_id(String spec_id) {
		this.spec_id = spec_id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getShip_num() {
		return ship_num;
	}

	public void setShip_num(Integer ship_num) {
		this.ship_num = ship_num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@NotDbField
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAddon() {
		return addon;
	}

	public void setAddon(String addon) {
		this.addon = addon;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	public Long getGainedpoint() {
		return gainedpoint;
	}

	public void setGainedpoint(Long gainedpoint) {
		this.gainedpoint = gainedpoint;
	}

	@NotDbField
	public String getOrder_outer_id() {
		return order_outer_id;
	}

	public void setOrder_outer_id(String order_outer_id) {
		this.order_outer_id = order_outer_id;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lv_id) {
		this.lv_id = lv_id;
	}

	public Double getCoupon_price() {
		return coupon_price;
	}

	public void setCoupon_price(Double coupon_price) {
		this.coupon_price = coupon_price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Integer getItem_type() {
		return item_type;
	}

	public void setItem_type(Integer item_type) {
		this.item_type = item_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<OrderItemBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}
	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<List<OrderItemBusiRequest>> resp = new QueryResponse<List<OrderItemBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderItemBusiRequest>());
	}
	
	@NotDbField
	public List<OrderItemProdBusiRequest> getOrderItemProdBusiRequests() {
		return orderItemProdBusiRequests;
	}
	@NotDbField
	public void setOrderItemProdBusiRequests(
			List<OrderItemProdBusiRequest> orderItemProdBusiRequests) {
		this.orderItemProdBusiRequests = orderItemProdBusiRequests;
	}
	
//	@NotDbField
//	public List<OrderItemsAptPrintsBusiRequest> getOrderItemsAptPrintsRequests() {
//		return orderItemsAptPrintsRequests;
//	}
//	@NotDbField
//	public void setOrderItemsAptPrintsRequests(
//			List<OrderItemsAptPrintsBusiRequest> orderItemsAptPrintsRequests) {
//		this.orderItemsAptPrintsRequests = orderItemsAptPrintsRequests;
//	}
	
	@NotDbField
	public OrderItemExtBusiRequest getOrderItemExtBusiRequest() {
		return orderItemExtBusiRequest;
	}
	@NotDbField
	public void setOrderItemExtBusiRequest(OrderItemExtBusiRequest orderItemExtBusiRequest) {
		this.orderItemExtBusiRequest = orderItemExtBusiRequest;
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

//	public OrderItemAgentMoneyBusiRequest getOrderItemAgentMoneyBusiRequest() {
//		return orderItemAgentMoneyBusiRequest;
//	}
//
//	public void setOrderItemAgentMoneyBusiRequest(
//			OrderItemAgentMoneyBusiRequest orderItemAgentMoneyBusiRequest) {
//		this.orderItemAgentMoneyBusiRequest = orderItemAgentMoneyBusiRequest;
//	}
//	
//	public List<OrderItemsValiLogBusiRequest> getOrderItemsValiLogList(){
//		return orderItemsValiLogList;
//	}
//	public void setOrderItemsValiLogList(List<OrderItemsValiLogBusiRequest> orderItemsValiLogList){
//		this.orderItemsValiLogList = orderItemsValiLogList;
//	}
}
