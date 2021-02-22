package zte.net.ecsord.params.busi.req;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.api.ApiRuleException;

import params.ZteBusiRequest;
import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

@RequestBeanAnnontion(primary_keys="log_id",table_name="es_order_items_vali_log",depency_keys="item_id",service_desc="订单开户人证件照图片校验日志")
public class OrderItemsValiLogBusiRequest extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {

	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String item_id;
	@RequestFieldAnnontion()
	private String log_id;
	@RequestFieldAnnontion()
	private String account_vali;
	@RequestFieldAnnontion()
	private String vali_time;
	@RequestFieldAnnontion()
	private String vali_user;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String vali_desc;
	
	public String getOrder_id(){
		return order_id;
	}
	public void setOrder_id(String order_id){
		this.order_id = order_id;
	}
	public String getLog_id(){
		return log_id;
	}
	public void setLog_id(String log_id){
		this.log_id = log_id;
	}
	public String getAccount_vali(){
		return account_vali;
	}
	public void setAccount_vali(String account_vali){
		this.account_vali = account_vali;
	}
	public String getVali_time(){
		return vali_time;
	}
	public void setVali_time(String vali_time){
		this.vali_time = vali_time;
	}
	public String getVali_user(){
		return vali_user;
	}
	public void setVali_user(String vali_user){
		this.vali_user = vali_user;
	}
	public String getVali_desc(){
		return vali_desc;
	}
	public void setVali_desc(String vali_desc){
		this.vali_desc = vali_desc;
	}
	public String getSource_from(){
		return source_from;
	}
	public void setSource_from(String source_from){
		this.source_from = source_from;
	}
	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderItemsValiLogBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderItemsValiLogBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderItemsValiLogBusiRequest>> resp = new QueryResponse<List<OrderItemsValiLogBusiRequest>>();
		return RequestStoreExector.orderProdBusiLoadAssemble(instQuery,resp,new ArrayList<OrderItemsValiLogBusiRequest>());
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
