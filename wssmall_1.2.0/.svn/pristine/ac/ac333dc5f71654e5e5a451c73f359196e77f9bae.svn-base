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
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 
 * 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys="item_prod_inst_id",depency_keys="item_id",table_name="es_order_items_prod",service_desc="订单货品信息")
public class OrderItemProdBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander{
	
	private static final long serialVersionUID = 1L;
	
	@RequestFieldAnnontion(dname="es_order_items_prod_ext",need_insert="no",desc="订单货品单扩展信息实体",service_name="OrderItemProdExtBusiRequest")
	OrderItemProdExtBusiRequest orderItemProdExtBusiRequest = new OrderItemProdExtBusiRequest();
	
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
    private String item_id;
	@RequestFieldAnnontion()
    private String item_prod_inst_id;
	
	@RequestFieldAnnontion()
    private String item_spec_goods_id;
	@RequestFieldAnnontion()
    private String prod_spec_goods_id;
	@RequestFieldAnnontion()
    private String create_time;
	@RequestFieldAnnontion()
    private String oper_id;
	@RequestFieldAnnontion()
    private String  prod_spec_type_code;
	@RequestFieldAnnontion()
    private String  status;
	@RequestFieldAnnontion()
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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


	@Override
	@NotDbField
	public <T>T store() {
		ZteInstUpdateRequest<OrderItemProdBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemProdBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<List<OrderItemProdBusiRequest>> resp = new QueryResponse<List<OrderItemProdBusiRequest>>();
		return RequestStoreExector.orderProdBusiLoadAssemble(instQuery,resp,new ArrayList<OrderItemProdBusiRequest>());
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_prod_inst_id() {
		return item_prod_inst_id;
	}

	public void setItem_prod_inst_id(String item_prod_inst_id) {
		this.item_prod_inst_id = item_prod_inst_id;
	}

	public String getItem_spec_goods_id() {
		return item_spec_goods_id;
	}

	public void setItem_spec_goods_id(String item_spec_goods_id) {
		this.item_spec_goods_id = item_spec_goods_id;
	}

	public String getProd_spec_goods_id() {
		return prod_spec_goods_id;
	}

	public void setProd_spec_goods_id(String prod_spec_goods_id) {
		this.prod_spec_goods_id = prod_spec_goods_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getProd_spec_type_code() {
		return prod_spec_type_code;
	}

	public void setProd_spec_type_code(String prod_spec_type_code) {
		this.prod_spec_type_code = prod_spec_type_code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public OrderItemProdExtBusiRequest getOrderItemProdExtBusiRequest() {
		return orderItemProdExtBusiRequest;
	}

	public void setOrderItemProdExtBusiRequest(
			OrderItemProdExtBusiRequest orderItemProdExtBusiRequest) {
		this.orderItemProdExtBusiRequest = orderItemProdExtBusiRequest;
	}
}
