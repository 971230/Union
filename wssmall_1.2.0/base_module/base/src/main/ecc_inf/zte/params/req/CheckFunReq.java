package zte.params.req;

import java.util.HashMap;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.consts.EccConsts;

import params.ZteRequest;

public class CheckFunReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="流程环节 ",type="String",isNecessary="N",desc="trace_code：流程环节 ")
	private String trace_code;
	
    @ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String obj_id;
    
    @ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="Y",desc="订单类型[order,order_queue]")
	private String obj_type;
	
	@ZteSoftCommentAnnotationParam(name="执行时间",type="String",isNecessary="N",desc="exe_time：执行时间 ，缺省环节后检验")
	private String exe_time = EccConsts.EXE_TIME_AFTER ;
	
	@ZteSoftCommentAnnotationParam(name="扩展参数",type="String",isNecessary="N",desc="ext_param：其他扩展参数")
	private HashMap ext_param;
	
	@ZteSoftCommentAnnotationParam(name="规则ID",type="String",isNecessary="N",desc="fun_id：规则ID")
	private String fun_id;
	

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.eccService.checkFun";
	}

	public String getTrace_code() {
		return trace_code;
	}

	public void setTrace_code(String trace_code) {
		this.trace_code = trace_code;
	}

	public String getObj_id() {
		return obj_id;
	}

	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}

	public String getObj_type() {
		return obj_type;
	}

	public void setObj_type(String obj_type) {
		this.obj_type = obj_type;
	}

	public String getExe_time() {
		return exe_time;
	}

	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}

	public HashMap getExt_param() {
		return ext_param;
	}

	public void setExt_param(HashMap ext_param) {
		this.ext_param = ext_param;
	}

	public String getFun_id() {
		return fun_id;
	}

	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
	}

}
