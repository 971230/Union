package params.req;

import params.ZteRequest;
import params.resp.OrderExpWriteResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderExpWriteForInfReq  extends ZteRequest<OrderExpWriteResp>{
	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name="订单ID",type="String",isNecessary="Y",desc="订单ID")
	private String obj_id;
    @ZteSoftCommentAnnotationParam(name="订单类型",type="String",isNecessary="Y",desc="订单类型[order,order_queue]")
	private String obj_type;
    @ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="外部订单号")
    public String out_tid;
    @ZteSoftCommentAnnotationParam(name="报文日志id",type="String",isNecessary="N",desc="报文日志id")
	private String log_id;
    @ZteSoftCommentAnnotationParam(name="搜索id",type="String",isNecessary="Y",desc="搜索id")
   	private String search_id;
    @ZteSoftCommentAnnotationParam(name="搜索编码",type="String",isNecessary="Y",desc="搜索编码")
  	private String search_code;
    @ZteSoftCommentAnnotationParam(name="报文出参",type="String",isNecessary="Y",desc="报文出参")
  	private String out_param;
    @ZteSoftCommentAnnotationParam(name="报文入参",type="String",isNecessary="Y",desc="报文入参")
  	private String in_param;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.orderexp.orderexpWriteForInf";
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

	public String getLog_id() {
		return log_id;
	}

	public void setLog_id(String log_id) {
		this.log_id = log_id;
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

	public String getOut_param() {
		return out_param;
	}

	public void setOut_param(String out_param) {
		this.out_param = out_param;
	}

	public String getIn_param() {
		return in_param;
	}

	public void setIn_param(String in_param) {
		this.in_param = in_param;
	}	
}