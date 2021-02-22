package params.req;

import params.ZteRequest;
import params.resp.OrderExpWriteResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderExpWriteForBusReq  extends ZteRequest<OrderExpWriteResp>{
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String obj_id;
    @ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="Y",desc="订单类型[order,order_queue]")
	private String obj_type;
    @ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
    public String out_tid;
    @ZteSoftCommentAnnotationParam(name="搜索id",type="String",isNecessary="Y",desc="搜索id")
	private String search_id;
    @ZteSoftCommentAnnotationParam(name="搜索编码",type="String",isNecessary="Y",desc="搜索编码")
   	private String search_code;
    @ZteSoftCommentAnnotationParam(name="错误业务描述",type="String",isNecessary="Y",desc="错误业务描述")
   	private String error_msg;
    @ZteSoftCommentAnnotationParam(name="错误堆栈信息",type="String",isNecessary="Y",desc="错误堆栈信息")
   	public String error_stack_msg;
    
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.orderexp.orderexpWriteForBus";
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

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}

	public String getSearch_id() {
		return search_id;
	}

	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}

	public String getSearch_code() {
		return search_code;
	}

	public void setSearch_code(String search_code) {
		this.search_code = search_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public String getError_stack_msg() {
		return error_stack_msg;
	}

	public void setError_stack_msg(String error_stack_msg) {
		this.error_stack_msg = error_stack_msg;
	}
	
}
