package zte.params.process.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.params.process.resp.UosStartResp;

/**
 * @author Reason.Yea 
 * @version 创建时间：2014-9-16
 */
public class UosStartReq extends ZteRequest<UosStartResp> {
	@ZteSoftCommentAnnotationParam(name="用户ID",type="Int",isNecessary="Y",desc="用户ID")
	private int staffId;
	@ZteSoftCommentAnnotationParam(name="用户名称",type="String",isNecessary="N",desc="用户名称")
	private String staffName;
	@ZteSoftCommentAnnotationParam(name="流程ID",type="String",isNecessary="Y",desc="流程ID")
	private String processId;
	@ZteSoftCommentAnnotationParam(name="流程名称",type="String",isNecessary="N",desc="流程名称")
	private String processName;
	
	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.uosService.process.start";
	}

}
