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

@RequestBeanAnnontion(primary_keys = "sp_inst_id",  depency_keys="order_id", table_name = "es_order_sp_product", service_desc = "SP服务产品表")
public class OrderSpProductBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String sp_id;
	@RequestFieldAnnontion()
	private String sp_code;
	@RequestFieldAnnontion()
	private String sp_provider;
	@RequestFieldAnnontion()
	private String sp_name;
	@RequestFieldAnnontion()
	private String accept_platform;
	@RequestFieldAnnontion()
	private String status;
	@RequestFieldAnnontion()
	private String source_from;
	@RequestFieldAnnontion()
	private String sp_inst_id;
	@RequestFieldAnnontion()
	private String sku;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderSpProductBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderSpProductBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderSpProductBusiRequest>> resp = new QueryResponse<List<OrderSpProductBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp,
				new ArrayList<OrderSpProductBusiRequest>());
	}
	/**
	 * 只插入数据库，不更新缓存，在标准化入库时执行
	 */
	@NotDbField
	public ZteResponse  insertNoCache(){
		return RequestStoreExector.getInstance().insertZteRequestInst(this);
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

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSp_id() {
		return sp_id;
	}

	public void setSp_id(String sp_id) {
		this.sp_id = sp_id;
	}

	public String getSp_code() {
		return sp_code;
	}

	public void setSp_code(String sp_code) {
		this.sp_code = sp_code;
	}

	public String getSp_provider() {
		return sp_provider;
	}

	public void setSp_provider(String sp_provider) {
		this.sp_provider = sp_provider;
	}

	public String getSp_name() {
		return sp_name;
	}

	public void setSp_name(String sp_name) {
		this.sp_name = sp_name;
	}

	public String getAccept_platform() {
		return accept_platform;
	}

	public void setAccept_platform(String accept_platform) {
		this.accept_platform = accept_platform;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getSp_inst_id() {
		return sp_inst_id;
	}

	public void setSp_inst_id(String sp_inst_id) {
		this.sp_inst_id = sp_inst_id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

}
