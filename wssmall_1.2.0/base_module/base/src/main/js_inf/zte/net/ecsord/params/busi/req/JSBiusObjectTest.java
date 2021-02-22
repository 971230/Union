package zte.net.ecsord.params.busi.req;

import org.springframework.stereotype.Service;

import rule.IZteBusiRequestHander;
import zte.net.common.annontion.context.request.RequestBeanAnnontion;
import zte.net.common.annontion.context.request.RequestFieldAnnontion;
import zte.net.common.params.ZtePofBusiRequest;
import zte.net.common.params.req.ZteInstQueryRequest;
import zte.net.common.params.req.ZteInstUpdateRequest;
import zte.net.common.params.req.ZtePofInstUpdateRequest;
import zte.net.common.params.resp.QueryResponse;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

@RequestBeanAnnontion(primary_keys = "JSBiusObjectTest", cache_keys = "", table_name = "es_order_trees", table_archive = "yes", cache_store = "yes", service_desc = "订单树对象，数据库存储完整的json数据值")
@Service
public class JSBiusObjectTest extends ZtePofBusiRequest implements IZteBusiRequestHander {

	private static final long serialVersionUID = 2755922263606492133L;

	@RequestFieldAnnontion(dname = "orderId", desc = "orderId", field_Order = 0, need_query = "no")
	private String orderId;

	@RequestFieldAnnontion(dname = "a", desc = "aa", field_Order = 1, need_query = "no")
	private String a;

	@RequestFieldAnnontion(dname = "b", desc = "bb", field_Order = 2, need_query = "no")
	private String b;

	@RequestFieldAnnontion(dname = "c", desc = "cc", field_Order = 3, need_query = "no")
	private String c;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	@Override
	@NotDbField
	public <T> T store() {
		ZtePofInstUpdateRequest<JSBiusObjectTest> updateRequest = new ZtePofInstUpdateRequest<JSBiusObjectTest>();
		return RequestStoreExector.orderPofBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<OrderTreeHisBusiRequest> resp = new QueryResponse<OrderTreeHisBusiRequest>();
		T t = RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, this); // 为空，走历史归档表
		return null;
	}

//	@Override
//	@NotDbField
//	public void delete() {
//		ZteInstUpdateRequest<JSBiusObjectTest> updateRequest = new ZteInstUpdateRequest<JSBiusObjectTest>();
//		RequestStoreExector.orderDeleteStoreAssemble(updateRequest, this);
//		
//	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return null;
	}

}
