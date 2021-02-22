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
 * @Description 订单队列历史数据业务对象
 * @author  zhangJun
 * @date    2015-3-10
 * @version 1.0
 */
@RequestBeanAnnontion(primary_keys="co_id",table_name="es_order_queue_his",service_desc="订单队列历史数据")
public class OrderQueueHisBusiRequest extends ZtePofBusiRequest<ZteResponse>  implements IZteBusiRequestHander {
	
	private static final long serialVersionUID = 1L;
	@RequestFieldAnnontion(desc = "队列编码")
	private String co_id;
	
	@RequestFieldAnnontion(desc = "业务名称")
	private String co_name;
	
	@RequestFieldAnnontion(desc = "批次号")
	private String batch_id;
	
	@RequestFieldAnnontion(desc = "服务编码")
	private String service_code;
	
	@RequestFieldAnnontion(desc = "操作类型，M更新/D删除/A新增")
	private String action_code;
	
	@RequestFieldAnnontion(desc = "外部订单类型")
	private String object_type;
	
	@RequestFieldAnnontion(desc = "外部订单编码")
	private String object_id;
	
	@RequestFieldAnnontion(desc = "报文内容，只记录缓存Key值")
	private String contents;
	
	@RequestFieldAnnontion(desc = "报文状态，WFS未发送/FSZ发送中/XYCG/ERR发送失败")
	private String status;
	
	@RequestFieldAnnontion(desc = "报文状态变更时间")
	private String status_date;
	
	@RequestFieldAnnontion(desc = "操作人")
	private String oper_id;
	
	@RequestFieldAnnontion(desc = "创建时间")
	private String created_date;
	
	@RequestFieldAnnontion(desc = "操作次数")
	private Integer deal_num = 0;
	
	@RequestFieldAnnontion(desc = "发送时间")
	private String send_date;
	
	@RequestFieldAnnontion(desc = "反馈时间")
	private String resp_date;
	
	@RequestFieldAnnontion(desc = "订单来源")
	private String source_from;
	
	@RequestFieldAnnontion(desc = "服务版本号")
	private String service_version;
	
	@RequestFieldAnnontion(desc = "订单id")
	private String order_id;
	
	@RequestFieldAnnontion(desc = "报文类型")
	private String type;
	
	@RequestFieldAnnontion(desc = "更新时间")
	private String update_date;
	
	@RequestFieldAnnontion(desc = "处理状态")
	private String work_state;
	
	@RequestFieldAnnontion(desc = "报文头部表id")
	private String inf_head_id;
	
	@RequestFieldAnnontion(desc = "处理结果")
	private String deal_desc;
	
	@RequestFieldAnnontion(desc = "模板编码")
	private String template_code;
	
	@RequestFieldAnnontion(desc = "模板版本号")
	private String template_version;
	
	@RequestFieldAnnontion(desc = "请求来源")
	private String request_source;
	
	@RequestFieldAnnontion(desc = "业务类型(ADD下单添加 SPLIT已拆单)")
	private String busi_type;
	
	@RequestFieldAnnontion(desc = "归集成功后后续流程对应的规则方案ID")
	private String plan_id;
	
	@RequestFieldAnnontion(desc = "处理系统")
	private String handle_sys;
	
	@RequestFieldAnnontion(desc = "标准化处理标识: 成功:Succeed  失败:Fail   相当于成功(淘宝订单) EqualSucceed")
	private String deal_code;
	
	@RequestFieldAnnontion(desc = "处理内容,订单处理辅助信息.现用:淘宝订单获取字符串")
	private String deal_contents;
	
	@RequestFieldAnnontion(desc = "队列类型, ctn:收单队列, exp:异常系统队列")
	private String queue_type;

	@Override
	@NotDbField
	public <T> T store() {
		ZteInstUpdateRequest<OrderQueueHisBusiRequest> updateRequest = new ZteInstUpdateRequest<OrderQueueHisBusiRequest>(); 
		return RequestStoreExector.orderBusiStoreAssemble(updateRequest, this);
	}

	@Override
	@NotDbField
	public <T> T load(ZteInstQueryRequest instQuery) {
		//构造参数
		QueryResponse<OrderQueueHisBusiRequest> resp = new QueryResponse<OrderQueueHisBusiRequest>();
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

	public String getQueue_type() {
		return queue_type;
	}

	public void setQueue_type(String queue_type) {
		this.queue_type = queue_type;
	}

	public String getCo_id() {
		return co_id;
	}

	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}

	public String getCo_name() {
		return co_name;
	}

	public String getDeal_contents() {
		return deal_contents;
	}

	public void setDeal_contents(String deal_contents) {
		this.deal_contents = deal_contents;
	}

	public void setCo_name(String co_name) {
		this.co_name = co_name;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public String getObject_id() {
		return object_id;
	}

	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_date() {
		return status_date;
	}

	public void setStatus_date(String status_date) {
		this.status_date = status_date;
	}

	public String getOper_id() {
		return oper_id;
	}

	public void setOper_id(String oper_id) {
		this.oper_id = oper_id;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}


	public String getSend_date() {
		return send_date;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getResp_date() {
		return resp_date;
	}

	public void setResp_date(String resp_date) {
		this.resp_date = resp_date;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}


	public Integer getDeal_num() {
		return deal_num;
	}

	public void setDeal_num(Integer deal_num) {
		this.deal_num = deal_num;
	}

	public String getService_version() {
		return service_version;
	}

	public void setService_version(String service_version) {
		this.service_version = service_version;
	}

	public String getWork_state() {
		return work_state;
	}

	public void setWork_state(String work_state) {
		this.work_state = work_state;
	}

	public String getInf_head_id() {
		return inf_head_id;
	}

	public void setInf_head_id(String inf_head_id) {
		this.inf_head_id = inf_head_id;
	}

	public String getTemplate_code() {
		return template_code;
	}

	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
	}

	public String getTemplate_version() {
		return template_version;
	}

	public void setTemplate_version(String template_version) {
		this.template_version = template_version;
	}

	public String getRequest_source() {
		return request_source;
	}

	public void setRequest_source(String request_source) {
		this.request_source = request_source;
	}

	public String getBusi_type() {
		return busi_type;
	}

	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getHandle_sys() {
		return handle_sys;
	}

	public void setHandle_sys(String handle_sys) {
		this.handle_sys = handle_sys;
	}

	public String getDeal_code() {
		return deal_code;
	}

	public void setDeal_code(String deal_code) {
		this.deal_code = deal_code;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeal_desc() {
		return deal_desc;
	}

	public void setDeal_desc(String deal_desc) {
		this.deal_desc = deal_desc;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	
	
}
