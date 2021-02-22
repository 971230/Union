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
 * @Description 订单工作表业务对象
 * @author  zhangJun
 * @date    2015-3-10
 * @version 1.0
 */
@RequestBeanAnnontion(primary_keys="order_id",table_name="es_order_work",service_desc="订单工作表")
public class OrderWorkBusiRequest extends ZtePofBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;

	@RequestFieldAnnontion(desc="创建时间")
	String create_time;
	@RequestFieldAnnontion(desc="订单描述")
	String order_desc;
	@RequestFieldAnnontion(desc="修改时间")
	String update_time;
	@RequestFieldAnnontion(desc="订单id")
	String order_id;
	@RequestFieldAnnontion(desc="订单流程id")
	String flow_id;
	@RequestFieldAnnontion(desc="流程实例Id")
	String flow_inst_id;
	@RequestFieldAnnontion(desc="订单流程名称")
	String flow_name;
	
	
	
	

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderWorkBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderWorkBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<OrderWorkBusiRequest> resp = new QueryResponse<OrderWorkBusiRequest>();
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(String flow_id) {
		this.flow_id = flow_id;
	}

	public String getFlow_inst_id() {
		return flow_inst_id;
	}

	public void setFlow_inst_id(String flow_inst_id) {
		this.flow_inst_id = flow_inst_id;
	}

	public String getFlow_name() {
		return flow_name;
	}

	public void setFlow_name(String flow_name) {
		this.flow_name = flow_name;
	}

}
