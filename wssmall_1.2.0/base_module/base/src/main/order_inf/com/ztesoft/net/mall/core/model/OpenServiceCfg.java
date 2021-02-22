package com.ztesoft.net.mall.core.model;

/**
 * 
 * @Package com.ztesoft.net.mall.core.model
 * @Description: 开放服务配置实体类ES_OPEN_SERVICE_CFG
 * @author zhouqiangang
 * @date 2015年11月19日 上午9:04:16
 */
public class OpenServiceCfg implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private String service_id;

	/**
	 * service_code(ApiMethodName)
	 */
	private String service_code;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * 是否写队列
	 */
	private String write_queue_flag;

	/**
	 * 是否写订单
	 */
	private String write_order_flag;

	/**
	 * 是否写订单日志
	 */
	private String write_logs_flag;

	/**
	 * 消费类型 Dubbo or Mq
	 */
	private String rpc_type;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * source_from
	 */
	private String source_from;

	/**
	 * 操作类型，M更新/D删除/A新增
	 */
	private String action_code;

	/**
	 * 业务对象执行路径
	 */
	private String execute_path;

	/**
	 * order_id 路径
	 */
	private String order_id_path;

	/**
	 * 外部服务编码
	 */
	private String out_service_code;

	/**
	 * 执行方案ID
	 */
	private String plan_id;

	/**
	 * mq 主题
	 */
	private String mq_topic;

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWrite_queue_flag() {
		return write_queue_flag;
	}

	public void setWrite_queue_flag(String write_queue_flag) {
		this.write_queue_flag = write_queue_flag;
	}

	public String getWrite_order_flag() {
		return write_order_flag;
	}

	public void setWrite_order_flag(String write_order_flag) {
		this.write_order_flag = write_order_flag;
	}

	public String getWrite_logs_flag() {
		return write_logs_flag;
	}

	public void setWrite_logs_flag(String write_logs_flag) {
		this.write_logs_flag = write_logs_flag;
	}

	public String getRpc_type() {
		return rpc_type;
	}

	public void setRpc_type(String rpc_type) {
		this.rpc_type = rpc_type;
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

	public String getAction_code() {
		return action_code;
	}

	public void setAction_code(String action_code) {
		this.action_code = action_code;
	}

	public String getExecute_path() {
		return execute_path;
	}

	public void setExecute_path(String execute_path) {
		this.execute_path = execute_path;
	}

	public String getOrder_id_path() {
		return order_id_path;
	}

	public void setOrder_id_path(String order_id_path) {
		this.order_id_path = order_id_path;
	}

	public String getOut_service_code() {
		return out_service_code;
	}

	public void setOut_service_code(String out_service_code) {
		this.out_service_code = out_service_code;
	}

	public String getPlan_id() {
		return plan_id;
	}

	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
	}

	public String getMq_topic() {
		return mq_topic;
	}

	public void setMq_topic(String mq_topic) {
		this.mq_topic = mq_topic;
	}

}
