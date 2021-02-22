package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import params.ZteBusiRequest;
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
 * 声明：建立的业务对象需要以BusiRequest作为后缀
 * 
 * 60G的内存可缓存15728640条订单树数据
 * 
 */
@RequestBeanAnnontion(primary_keys="order_id",table_name="es_order_trees",table_archive="yes",cache_store="yes",service_desc="订单树对象，数据库存储完整的json数据值")
@Service
public class OrderTreeBusiRequest extends ZteBusiRequest  implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;

	@RequestFieldAnnontion(dname="order_id",desc="订单编号",need_query="no")
	String order_id;
	
	@RequestFieldAnnontion(dname="es_order",desc="订单基本信息实体",need_query="no")
	OrderBusiRequest orderBusiRequest = new OrderBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_ext",desc="订单扩展信息实体",need_query="no")
	OrderExtBusiRequest orderExtBusiRequest = new OrderExtBusiRequest();
	
	@RequestFieldAnnontion(dname="es_order_items",desc="订单商品单基本信息实体",need_query="no",service_name="OrderItemBusiRequest")
	List<OrderItemBusiRequest> orderItemBusiRequests = new ArrayList<OrderItemBusiRequest>();
	

	@RequestFieldAnnontion(dname="es_delivery",desc="订单物流配送列表信息",need_query="no",service_name="OrderDeliveryBusiRequest")
	List<OrderDeliveryBusiRequest> orderDeliveryBusiRequests = new ArrayList<OrderDeliveryBusiRequest>();
	
	
	@RequestFieldAnnontion(dname="es_payment_logs",desc="订单支付列表信息",need_query="no",service_name="OrderPayBusiRequest")
	List<OrderPayBusiRequest> orderPayBusiRequests = new ArrayList<OrderPayBusiRequest>();
	
	@RequestFieldAnnontion(dname="es_attr_inst",desc="订单属性列表信息",need_query="no",service_name="AttrInstBusiRequest")
	List<AttrInstBusiRequest> attrInstBusiRequests = new ArrayList<AttrInstBusiRequest>();
	
	
	@RequestFieldAnnontion(dname="create_time",desc="创建时间",need_query="no")
	String create_time;
	
	@RequestFieldAnnontion(dname="update_time",desc="更新时间",need_query="no")
	String update_time;
	
	@RequestFieldAnnontion(dname="col1",desc="扩展字段1--是否重新匹配模式，yes重新匹配",need_query="no")
	String col1;
	@RequestFieldAnnontion(dname="col2",desc="扩展字段1--是否重新匹配流程 ，yes重新匹配",need_query="no")
	String col2;
	@RequestFieldAnnontion(dname="col3",desc="扩展字段1--区分是接口[EcsOrderConsts.TRACE_TRIGGER_INF]请求业务组件，还是页面[EcsOrderConsts.TRACE_TRIGGER_PAGE]请求",need_query="no")
	String col3;
	@RequestFieldAnnontion(dname="col4",desc="扩展字段1",need_query="no")
	String col4;
	@RequestFieldAnnontion(dname="col5",desc="扩展字段1",need_query="no")
	String col5;
	@RequestFieldAnnontion(dname="col6",desc="扩展字段1",need_query="no")
	String col6;
	@RequestFieldAnnontion(dname="col7",desc="扩展字段1",need_query="no")
	String col7;
	@RequestFieldAnnontion(dname="col8",desc="扩展字段1",need_query="no")
	String col8;
	@RequestFieldAnnontion(dname="col9",desc="扩展字段1-判读属性数据是否写入redis,yes是，no否",need_query="no")
	String col9;
	@RequestFieldAnnontion(dname="col10",desc="扩展字段1--是否历史订单查询，yes是",need_query="no")
	String col10;
	
	
	public OrderBusiRequest getOrderBusiRequest() {
		//order table
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
		T t =  RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this); //为空，走历史归档表
		return t;
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

	/**
	 * 获取所有属性实例数据
	 * add by wui 此方法废弃使用
	 * @return
	 */
	@Deprecated
	public List<AttrInstBusiRequest> getAttrInstBusiRequests() {
		return attrInstBusiRequests;
	}
	
	/**
	 * 设置所有属性实例数据
	 * @param attrInstBusiRequests
	 * 此方法废弃使用
	 */
	@Deprecated
	public void setAttrInstBusiRequests(List<AttrInstBusiRequest> attrInstBusiRequests) {
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
	
}
