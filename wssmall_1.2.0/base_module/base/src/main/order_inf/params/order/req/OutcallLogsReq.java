package params.order.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class OutcallLogsReq extends ZteRequest {
	
	private String deal_time;//处理时间
	private String deal_name;//处理人
	private String oper_time;//申请时间
	private String oper_name;//申请人
	private String is_finish;//是否完成1完成0未完成
	private String oper_remark;//外呼原因
	private String outcall_type;//外呼类型
	
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeal_time() {
		return deal_time;
	}

	public void setDeal_time(String deal_time) {
		this.deal_time = deal_time;
	}

	public String getDeal_name() {
		return deal_name;
	}

	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	public String getOper_time() {
		return oper_time;
	}

	public void setOper_time(String oper_time) {
		this.oper_time = oper_time;
	}

	public String getOper_name() {
		return oper_name;
	}

	public void setOper_name(String oper_name) {
		this.oper_name = oper_name;
	}

	public String getIs_finish() {
		return is_finish;
	}

	public void setIs_finish(String is_finish) {
		this.is_finish = is_finish;
	}

	public String getOper_remark() {
		return oper_remark;
	}

	public void setOper_remark(String oper_remark) {
		this.oper_remark = oper_remark;
	}

	public String getOutcall_type() {
		return outcall_type;
	}

	public void setOutcall_type(String outcall_type) {
		this.outcall_type = outcall_type;
	}

	
}
