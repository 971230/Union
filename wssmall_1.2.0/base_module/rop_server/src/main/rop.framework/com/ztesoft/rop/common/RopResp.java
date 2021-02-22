/**
 *
 * 日    期：12-2-10
 */
package com.ztesoft.rop.common;

import java.io.Serializable;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * <pre>
 *    ROP服务的返回对象，返回方法的入参必须是继承于该类。
 * </pre>
 * 
 * @author wui
 * @version 1.0
 */
public abstract class RopResp implements Serializable {

	public String error_code ="-1";
	public String error_msg;
	public String error_stack_msg;//错误堆栈信息
	String is_write_exception;//是否写异常单  1 写入  0  不写入
	
	@NotDbField
	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	@NotDbField
	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	@NotDbField
	public String getError_stack_msg() {
    	if(StringUtil.isEmpty(error_stack_msg)){
    		error_stack_msg = this.getError_msg();
    	}
		return error_stack_msg;
	}

	public void setError_stack_msg(String error_stack_msg) {
		this.error_stack_msg = error_stack_msg;
	}

	public String getIs_write_exception() {
		return is_write_exception;
	}

	public void setIs_write_exception(String is_write_exception) {
		this.is_write_exception = is_write_exception;
	}
	
}
