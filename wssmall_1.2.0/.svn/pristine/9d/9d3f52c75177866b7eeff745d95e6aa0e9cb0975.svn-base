package params.req;

import params.ZteRequest;
import params.resp.OrderExpMarkProcessedResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class OrderExpMarkProcessedReq  extends ZteRequest<OrderExpMarkProcessedResp>{
	
	private static final long serialVersionUID = 1L;
	
	@ZteSoftCommentAnnotationParam(name="关联业务ID",type="String",isNecessary="Y",desc="关联业务ID")
	private String rel_obj_id;
    @ZteSoftCommentAnnotationParam(name="关联业务类型",type="String",isNecessary="Y",desc="关联业务类型 order 订单ID order_queue 订单队列  ")
	private String rel_obj_type;
    @ZteSoftCommentAnnotationParam(name="异常实例ID",type="String",isNecessary="Y",desc="该字段不为空时，rel_obj_id、rel_obj_type无效")
	private String exp_inst_id;
    @ZteSoftCommentAnnotationParam(name="处理结果",type="String",isNecessary="Y",desc="处理结果")
	private String deal_result;
    @ZteSoftCommentAnnotationParam(name="工号",type="String",isNecessary="Y",desc="工号")
   	private String deal_staff_no;
    @ZteSoftCommentAnnotationParam(name="请求时间",type="String",isNecessary="Y",desc="请求时间 yyyy-MM-dd HH24:mi:ss")
   	private String request_time;
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.orderexp.orderExpMarkProcessed";
	}

	public String getRel_obj_id() {
		return rel_obj_id;
	}

	public void setRel_obj_id(String rel_obj_id) {
		this.rel_obj_id = rel_obj_id;
	}

	public String getRel_obj_type() {
		return rel_obj_type;
	}

	public void setRel_obj_type(String rel_obj_type) {
		this.rel_obj_type = rel_obj_type;
	}

	public String getExp_inst_id() {
		return exp_inst_id;
	}

	public void setExp_inst_id(String exp_inst_id) {
		this.exp_inst_id = exp_inst_id;
	}

	public String getDeal_result() {
		return deal_result;
	}

	public void setDeal_result(String deal_result) {
		this.deal_result = deal_result;
	}

	public String getDeal_staff_no() {
		return deal_staff_no;
	}

	public void setDeal_staff_no(String deal_staff_no) {
		this.deal_staff_no = deal_staff_no;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}
}
