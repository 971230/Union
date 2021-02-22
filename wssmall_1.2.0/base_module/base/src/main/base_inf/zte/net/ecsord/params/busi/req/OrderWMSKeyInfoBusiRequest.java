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
 * 自动化关键信息状态记录表
 * @author Rapon
 *
 */
@RequestBeanAnnontion(primary_keys = "order_id", table_name = "es_order_wms_key_info", service_desc = "自动化关键信息状态记录表")
public class OrderWMSKeyInfoBusiRequest extends ZteBusiRequest<ZteResponse> implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 6066350075098993583L;
	@RequestFieldAnnontion()
	private String order_id;
	@RequestFieldAnnontion()
	private String is_wms_pick_notify;
	@RequestFieldAnnontion()
	private String wms_pick_time;
	@RequestFieldAnnontion()
	private String write_machine_code;
	@RequestFieldAnnontion()
	private String is_upload_code;
	@RequestFieldAnnontion()
	private String upload_time;
	@RequestFieldAnnontion()
	private String allocate_time;
	@RequestFieldAnnontion()
	private String was_arrived;
	@RequestFieldAnnontion()
	private String arrived_time;
	@RequestFieldAnnontion()
	private String is_revert;
	@RequestFieldAnnontion()
	private String revert_time;
	@RequestFieldAnnontion()
	private String notify_time;
	@RequestFieldAnnontion()
	private String is_excp;
	@RequestFieldAnnontion()
	private String source_from;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderWMSKeyInfoBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderWMSKeyInfoBusiRequest>();
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		// 构造参数
		QueryResponse<OrderWMSKeyInfoBusiRequest> resp = new QueryResponse<OrderWMSKeyInfoBusiRequest>();
		return RequestStoreExector.orderBusiLoadAssemble(instQuery, resp, this);
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

	public String getIs_wms_pick_notify() {
		return is_wms_pick_notify;
	}

	public void setIs_wms_pick_notify(String is_wms_pick_notify) {
		this.is_wms_pick_notify = is_wms_pick_notify;
	}

	public String getWms_pick_time() {
		return wms_pick_time;
	}

	public void setWms_pick_time(String wms_pick_time) {
		this.wms_pick_time = wms_pick_time;
	}

	public String getWrite_machine_code() {
		return write_machine_code;
	}

	public void setWrite_machine_code(String write_machine_code) {
		this.write_machine_code = write_machine_code;
	}

	public String getIs_upload_code() {
		return is_upload_code;
	}

	public void setIs_upload_code(String is_upload_code) {
		this.is_upload_code = is_upload_code;
	}

	public String getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}

	public String getAllocate_time() {
		return allocate_time;
	}

	public void setAllocate_time(String allocate_time) {
		this.allocate_time = allocate_time;
	}

	public String getWas_arrived() {
		return was_arrived;
	}

	public void setWas_arrived(String was_arrived) {
		this.was_arrived = was_arrived;
	}

	public String getArrived_time() {
		return arrived_time;
	}

	public void setArrived_time(String arrived_time) {
		this.arrived_time = arrived_time;
	}

	public String getIs_revert() {
		return is_revert;
	}

	public void setIs_revert(String is_revert) {
		this.is_revert = is_revert;
	}

	public String getRevert_time() {
		return revert_time;
	}

	public void setRevert_time(String revert_time) {
		this.revert_time = revert_time;
	}

	public String getNotify_time() {
		return notify_time;
	}

	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}

	public String getIs_excp() {
		return is_excp;
	}

	public void setIs_excp(String is_excp) {
		this.is_excp = is_excp;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}
