package zte.params.process.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;
import zte.params.process.resp.UosFlowListResp;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-9-16
 */
public class UosFlowListReq extends ZteRequest<UosFlowListResp> {
	@ZteSoftCommentAnnotationParam(name = "流程定义ID", type = "String", isNecessary = "N", desc = "")
	private String processId;
	@ZteSoftCommentAnnotationParam(name = "流程名称", type = "String", isNecessary = "N", desc = "")
	private String processName;
	@ZteSoftCommentAnnotationParam(name = "当前页", type = "String", isNecessary = "N", desc = "")
	private int pageIndex;
	@ZteSoftCommentAnnotationParam(name = "页面大小", type = "String", isNecessary = "N", desc = "")
	private int pageSize;

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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.uosService.process.list";
	}

}
