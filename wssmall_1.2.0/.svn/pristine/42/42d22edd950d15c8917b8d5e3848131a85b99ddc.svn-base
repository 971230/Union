package zte.net.ord.params.busi.req;

import params.ZteResponse;
import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.ZtePofBusiRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @Description 订单工作项表业务对象
 * @author  zhangJun
 * @date    2015-3-10
 * @version 1.0
 */
@RequestBeanAnnontion(primary_keys="work_item_id",table_name="es_order_work",service_desc="订单工作项表")
public class OrderWorkItemBusiRequest extends ZtePofBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;

	@RequestFieldAnnontion(desc="订单id")
	String order_id;
	@RequestFieldAnnontion(desc="创建时间")
	String create_time;
	@RequestFieldAnnontion(desc="处理状态(0待审核、1审核通过、2审核部通过)")
	String state;
	@RequestFieldAnnontion(desc="订单描述")
	String order_desc;
	@RequestFieldAnnontion(desc="修改时间")
	String update_time;
	@RequestFieldAnnontion(desc="处理人")
	String deal_user_id;
	@RequestFieldAnnontion(desc="处理时间")
	String deal_user_time;
	@RequestFieldAnnontion(desc="工作流环节名称")
	String tache_name;
	@RequestFieldAnnontion(desc="工作流环节id")
	String tache_id;
	@RequestFieldAnnontion(desc="处理描述")
	String deal_user_desc;
	@RequestFieldAnnontion(desc="工单项id")
	String work_item_id;
	@RequestFieldAnnontion(desc="工作流环节编码")
	String tache_code;
	@RequestFieldAnnontion(desc="处理人名称")
	String deal_user_name;
	
	
	
	

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderWorkItemBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderWorkItemBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<OrderWorkItemBusiRequest> resp = new QueryResponse<OrderWorkItemBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,this);
	}

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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOrder_desc() {
		return order_desc;
	}

	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getDeal_user_id() {
		return deal_user_id;
	}

	public void setDeal_user_id(String deal_user_id) {
		this.deal_user_id = deal_user_id;
	}

	public String getDeal_user_time() {
		return deal_user_time;
	}

	public void setDeal_user_time(String deal_user_time) {
		this.deal_user_time = deal_user_time;
	}

	public String getTache_name() {
		return tache_name;
	}

	public void setTache_name(String tache_name) {
		this.tache_name = tache_name;
	}

	public String getTache_id() {
		return tache_id;
	}

	public void setTache_id(String tache_id) {
		this.tache_id = tache_id;
	}

	public String getDeal_user_desc() {
		return deal_user_desc;
	}

	public void setDeal_user_desc(String deal_user_desc) {
		this.deal_user_desc = deal_user_desc;
	}

	public String getWork_item_id() {
		return work_item_id;
	}

	public void setWork_item_id(String work_item_id) {
		this.work_item_id = work_item_id;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getDeal_user_name() {
		return deal_user_name;
	}

	public void setDeal_user_name(String deal_user_name) {
		this.deal_user_name = deal_user_name;
	}

	

}
