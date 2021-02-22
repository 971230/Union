/**
 * 
 */
package com.ztesoft.net.mall.core.model;

/**
 * @author ZX
 * @version 2015-09-06
 *
 */
public class LogDictDetailContent {

	private String log_id; // 接口日志ID
	private String local_log_id; // 本地接口日志ID
	private String deal_opinion; // 处理建议
	private String result_desc; // 接口日志描述
	private String dict_name; // 错误名称
	private String op_code; // 组建编码
	
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getLocal_log_id() {
		return local_log_id;
	}
	public void setLocal_log_id(String local_log_id) {
		this.local_log_id = local_log_id;
	}
	public String getDeal_opinion() {
		return deal_opinion;
	}
	public void setDeal_opinion(String deal_opinion) {
		this.deal_opinion = deal_opinion;
	}
	public String getResult_desc() {
		return result_desc;
	}
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
	public String getDict_name() {
		return dict_name;
	}
	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}
	public String getOp_code() {
		return op_code;
	}
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	
}
