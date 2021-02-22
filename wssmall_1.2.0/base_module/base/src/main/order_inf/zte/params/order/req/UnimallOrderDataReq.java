package zte.params.order.req;

import params.ZteRequest;
import zte.params.order.resp.WorkFlowCallBackResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

public class UnimallOrderDataReq extends ZteRequest<WorkFlowCallBackResp> {
	
	private Integer curr_thread =0;
	private Integer thread_acount =0;
	private String def_sql;
	private String def_code;
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

	public Integer getCurr_thread() {
		return curr_thread;
	}

	public void setCurr_thread(Integer curr_thread) {
		this.curr_thread = curr_thread;
	}

	public Integer getThread_acount() {
		return thread_acount;
	}

	public void setThread_acount(Integer thread_acount) {
		this.thread_acount = thread_acount;
	}

	public String getDef_sql() {
		return def_sql;
	}

	public void setDef_sql(String def_sql) {
		this.def_sql = def_sql;
	}

	public String getDef_code() {
		return def_code;
	}

	public void setDef_code(String def_code) {
		this.def_code = def_code;
	}


}
