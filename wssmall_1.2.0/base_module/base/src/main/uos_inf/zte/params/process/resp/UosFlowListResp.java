package zte.params.process.resp;

import java.util.ArrayList;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-9-16
 */
public class UosFlowListResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "流程列表", type = "Object", isNecessary = "N", desc = "")
	private ArrayList<FlowDefine> flowList = new ArrayList<FlowDefine>();

	@ZteSoftCommentAnnotationParam(name = "总记录数", type = "int", isNecessary = "N", desc = "")
	private int totalCount;
	@ZteSoftCommentAnnotationParam(name = "当前页索引", type = "int", isNecessary = "N", desc = "")
	private int pageIndex;

	@ZteSoftCommentAnnotationParam(name = "总页数", type = "int", isNecessary = "N", desc = "")
	private int pageCount;

	@ZteSoftCommentAnnotationParam(name = "每页的记录行数", type = "int", isNecessary = "N", desc = "")
	private int pageSize;

	public ArrayList<FlowDefine> getFlowList() {
		return flowList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setFlowList(ArrayList<FlowDefine> flowList) {
		this.flowList = flowList;
	}

}
