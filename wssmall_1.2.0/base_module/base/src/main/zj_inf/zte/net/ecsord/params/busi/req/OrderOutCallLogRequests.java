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
@RequestBeanAnnontion(primary_keys="log_id",table_name="es_order_outcall_log",depency_keys="order_id",service_desc="订单外呼日志信息")
public class OrderOutCallLogRequests extends ZteBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String log_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String tache_code;
	@RequestFieldAnnontion()
	private String oper_type;
	@RequestFieldAnnontion()
	private String oper_time;
	@RequestFieldAnnontion()
	private String oper_id;
	@RequestFieldAnnontion()
	private String oper_remark;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private Integer order_status;
	@RequestFieldAnnontion()
	private Integer is_finish;
	@RequestFieldAnnontion()
	private String deal_remark;
	@RequestFieldAnnontion()
	private String deal_oper_id;
	@RequestFieldAnnontion()
	private String deal_time;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderOutCallLogRequests> updateRequest = new ZteInstUpdateRequest<OrderOutCallLogRequests>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderOutCallLogRequests>> resp = new QueryResponse<List<OrderOutCallLogRequests>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderOutCallLogRequests>());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
	}
	@Override
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

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getOper_type() {
		return oper_type;
	}

	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}

	public String getOper_time() {
		return oper_time;
	}

	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}

	

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	

	public String getOper_remark() {
		return oper_remark;
	}

	public void setOper_remark(String oper_remark) {
		this.oper_remark = oper_remark;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public Integer getIs_finish() {
		return is_finish;
	}

	public void setIs_finish(Integer is_finish) {
		this.is_finish = is_finish;
	}

	public String getDeal_remark() {
		return deal_remark;
	}

	public void setDeal_remark(String deal_remark) {
		this.deal_remark = deal_remark;
	}

	public String getDeal_oper_id() {
		return deal_oper_id;
	}

	public void setDeal_oper_id(String deal_oper_id) {
		this.deal_oper_id = deal_oper_id;
	}

	public String getDeal_time() {
		return deal_time;
	}

	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}

	
	
}
