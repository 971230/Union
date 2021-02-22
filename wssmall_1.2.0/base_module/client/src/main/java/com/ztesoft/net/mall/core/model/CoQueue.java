package com.ztesoft.net.mall.core.model;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 消息队列对象
 * @author kingapex
 *2010-3-23下午03:34:04
 */
public class CoQueue extends ZteRequest { //add by wui,对象可直接开放
	
	private String co_id;  //消息队列标识
	private String co_name;  //消息队列名称
	private String batch_id;  //批次号
	private String service_code;  //业务编码
	private String service_name;  //业务名称
	private String action_code;  //动作编码，用于区分数据是从页面来，还是后台，如：页面点发布时，订单归集时等
	private String object_type;  //对象类型,如：手机号码、商品标识、货品标识、其他规格数据
	private String object_id;  //对象标识，如：18988889999（手机号码）、690010101（商品标识）
	private String contents; //同步的数据内容(json格式的字符串)
	private String status;  //同步状态：WFS 未发送；FSZ 发送中；FSCG	发送成功；FSSB	发送失败；XYCG	响应成功；XYSB	响应失败
	private String status_date;  //队列标识
	private String oper_id;  //操作员标识
	private String created_date;  //入队列时间
	private String req_class;  //请求参数对象的类路径
	private Integer deal_num;  //处理次数
	private String failure_desc;  //同步失败描述
	private String send_date;  //发送时间
	private String resp_date;  //响应时间
	private String source_from;  //数据来源
	private String org_id_str;  //需要同步的组织串，用逗号隔开
	private String org_id_belong;  //发往的平台组织标识
	private String url; //请求的接口地址
	private String sys_code;		//新老系统标识
	
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	
	public String getCo_name() {
		return co_name;
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
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
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
	public String getReq_class() {
		return req_class;
	}
	public void setReq_class(String req_class) {
		this.req_class = req_class;
	}
	public Integer getDeal_num() {
		return deal_num;
	}
	public void setDeal_num(Integer deal_num) {
		this.deal_num = deal_num;
	}
	public String getFailure_desc() {
		return failure_desc;
	}
	public void setFailure_desc(String failure_desc) {
		this.failure_desc = failure_desc;
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
	public String getOrg_id_str() {
		return org_id_str;
	}
	public void setOrg_id_str(String org_id_str) {
		this.org_id_str = org_id_str;
	}
	public String getOrg_id_belong() {
		return org_id_belong;
	}
	public void setOrg_id_belong(String org_id_belong) {
		this.org_id_belong = org_id_belong;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSys_code() {
		return sys_code;
	}
	public void setSys_code(String sys_code) {
		this.sys_code = sys_code;
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
}
