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

@RequestBeanAnnontion(primary_keys = "file_id", depency_keys = "order_id", table_name = "es_order_file", service_desc = "订单图片文件信息")
public class OrderFileBusiRequest extends ZteBusiRequest<ZteResponse> implements
		IZteBusiRequestHander {

	/**
	 * 
	 */
	private static final long serialVersionUID = -311522280387966246L;

	@RequestFieldAnnontion()
	private String order_id;//内部订单号
	@RequestFieldAnnontion()
	private String file_id;//文件ID
	@RequestFieldAnnontion()
	private String file_type;//文件类型，png、jpg、doc、txt、xml、properties
	@RequestFieldAnnontion()
	private String file_path;//文件路径
	@RequestFieldAnnontion()
	private String file_name;//文件名称
	@RequestFieldAnnontion()
	private String create_time;//创建时间
	@RequestFieldAnnontion()
	private String operator_id;//操作人ID
	@RequestFieldAnnontion()
	private String status;//状态
	@RequestFieldAnnontion()
	private String tache_code;//环节编码
	@RequestFieldAnnontion()
	private String order_status;//订单状态
	@RequestFieldAnnontion()
	private String source_from;
	
	@Override
	public <T> T store() {
		ZteInstUpdateRequest<OrderFileBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderFileBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	public <T> T load(ZteInstQueryRequest instQuery) {
		QueryResponse<List<OrderFileBusiRequest>> resp = new QueryResponse<List<OrderFileBusiRequest>>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery,resp,new ArrayList<OrderFileBusiRequest>());
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

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTache_code() {
		return tache_code;
	}

	public void setTache_code(String tache_code) {
		this.tache_code = tache_code;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
