package com.ztesoft.net.param.inner;
 
import java.io.Serializable;

/**
 * 关键字校验、写入、更新入参
 * @author shen.qiyu
 *
 */
public class SpecvaluesCheckProcessedInner implements Serializable{
   
	private static final long serialVersionUID = 1L;
	
    //报文日志id
	private String log_id;
	//处理动作（新增、修改）
	private String action_type;
	//旧关键字
	private String old_key_word;
	//异常类型(接口异常、业务异常)
	private String exp_type;
	//报文
	private String param;
	//搜索编码
	private String search_code;
	//搜索id 
	private String search_id;
	//错误堆栈
	private String error_stack_msg;
	
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getOld_key_word() {
		return old_key_word;
	}
	public void setOld_key_word(String old_key_word) {
		this.old_key_word = old_key_word;
	}
	public String getExp_type() {
		return exp_type;
	}
	public void setExp_type(String exp_type) {
		this.exp_type = exp_type;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getSearch_code() {
		return search_code;
	}
	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}
	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}
	public String getError_stack_msg() {
		return error_stack_msg;
	}
	public void setError_stack_msg(String error_stack_msg) {
		this.error_stack_msg = error_stack_msg;
	}
	
	
}