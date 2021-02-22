package params.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

public class ZbAuditStatusUpdateReq extends ZteRequest {

	private String out_tid;
	
	private String zb_id;
	
	private String audit_status;
	
	private String audit_type;
	
	private String audit_result;
	
	private String crawler_status;
	private String  distribute_status;
	
	
	@Override
	public void check() throws ApiRuleException {

	}

	@Override
	public String getApiMethodName() {
		return "zte.net.ecsord.updateZbAuditStatus";
	}

	public String getOut_tid() {
		return out_tid;
	}

	public void setOut_tid(String out_tid) {
		this.out_tid = out_tid;
	}

	public String getZb_id() {
		return zb_id;
	}

	public void setZb_id(String zb_id) {
		this.zb_id = zb_id;
	}

	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}

	public String getAudit_type() {
		return audit_type;
	}

	public void setAudit_type(String audit_type) {
		this.audit_type = audit_type;
	}

	public String getAudit_result() {
		return audit_result;
	}

	public void setAudit_result(String audit_result) {
		this.audit_result = audit_result;
	}

	public String getCrawler_status() {
		return crawler_status;
	}

	public void setCrawler_status(String crawler_status) {
		this.crawler_status = crawler_status;
	}

	public String getDistribute_status() {
		return distribute_status;
	}

	public void setDistribute_status(String distribute_status) {
		this.distribute_status = distribute_status;
	}

}
