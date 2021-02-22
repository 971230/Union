package zte.net.ecsord.params.busi.req;

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
 * @author wu.i 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys = "item_id", depency_keys="item_id", table_name = "es_order_items_agent_money", service_desc = "代理商支付金额表",source_from="ECS")
public class OrderItemAgentMoneyBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String col2;
	
	@RequestFieldAnnontion()
	private String col3;
	
	@RequestFieldAnnontion()
	private String col4;
	
	@RequestFieldAnnontion()
	private String col5;
	
	@RequestFieldAnnontion()
	private String col6;
	
	
	@RequestFieldAnnontion()
	private String col7;
	
	@RequestFieldAnnontion()
	private String col8;
	
	@RequestFieldAnnontion()
	private String col9;
	
	
	@RequestFieldAnnontion()
	private String col10;
	
	@RequestFieldAnnontion()
	private String col11;
	
	@RequestFieldAnnontion()
	private String item_id;
	
	
	@RequestFieldAnnontion()
	private String order_id;
	
	@RequestFieldAnnontion()
	private String goods_id;
	
	@RequestFieldAnnontion()
	private String c_order_id;
	
	
	@RequestFieldAnnontion()
	private String o_order_id;
	
	
	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderItemAgentMoneyBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemAgentMoneyBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		// 构造参数
		QueryResponse<OrderItemAgentMoneyBusiRequest> resp = new QueryResponse<OrderItemAgentMoneyBusiRequest>();
		return RequestStoreExector.orderProdBusiLoadAssemble(instQuery, resp,new OrderItemAgentMoneyBusiRequest());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
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

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getC_order_id() {
		return c_order_id;
	}

	public void setC_order_id(String c_order_id) {
		this.c_order_id = c_order_id;
	}

	public String getO_order_id() {
		return o_order_id;
	}

	public void setO_order_id(String o_order_id) {
		this.o_order_id = o_order_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
