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
 * @author wu.i 订单数对象
 * 
 */
@RequestBeanAnnontion(primary_keys = "lock_id", depency_keys = "order_id", table_name = "es_order_lock", service_desc = "订单锁定表")
public class OrderLockBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
//depency_keys="order_id"
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String lock_id;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String tache_code;
	@RequestFieldAnnontion()
	private String lock_user_id;
	@RequestFieldAnnontion()
	private String lock_user_name;
	@RequestFieldAnnontion()
	private String lock_status;
	@RequestFieldAnnontion()
	private String lock_time;
	@RequestFieldAnnontion()
	private String remark;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String lock_end_time;
	@RequestFieldAnnontion()
	private String pool_id;
	
	@RequestFieldAnnontion()
	private String dealer_type;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderLockBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderLockBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		// 构造参数
		QueryResponse<List<OrderLockBusiRequest>> resp = new QueryResponse<List<OrderLockBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, new ArrayList<OrderLockBusiRequest>());
	}

	public String getLock_id() {
		return lock_id;
	}

	public void setLock_id(String lock_id) {
		this.lock_id = lock_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getLock_user_id() {
		return lock_user_id;
	}

	public void setLock_user_id(String lock_user_id) {
		this.lock_user_id = lock_user_id;
	}

	public String getLock_user_name() {
		return lock_user_name;
	}

	public void setLock_user_name(String lock_user_name) {
		this.lock_user_name = lock_user_name;
	}

	public String getLock_status() {
		return lock_status;
	}

	public void setLock_status(String lock_status) {
		this.lock_status = lock_status;
	}

	public String getLock_time() {
		return lock_time;
	}

	public void setLock_time(String lock_time) {
		this.lock_time = lock_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getLock_end_time() {
		return lock_end_time;
	}

	public void setLock_end_time(String lock_end_time) {
		this.lock_end_time = lock_end_time;
	}

	public String getPool_id() {
		return pool_id;
	}

	public void setPool_id(String pool_id) {
		this.pool_id = pool_id;
	}

	public String getDealer_type() {
		return dealer_type;
	}

	public void setDealer_type(String dealer_type) {
		this.dealer_type = dealer_type;
	}
}
