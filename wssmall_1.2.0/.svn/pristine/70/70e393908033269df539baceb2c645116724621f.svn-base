package zte.params.process.resp;

import java.util.ArrayList;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-9-16
 */
public class UosDealResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "工作项", type = "Object", isNecessary = "N", desc = "工作项列表")
	private ArrayList<WorkItem> workItems = new ArrayList<WorkItem>();
	@ZteSoftCommentAnnotationParam(name = "流程是否结束", type = "boolean", isNecessary = "N", desc = "流程是否结束")
	private boolean end;

	@ZteSoftCommentAnnotationParam(name = "流程是否中止", type = "boolean", isNecessary = "N", desc = "流程是否中止")
	public boolean terminal;

	
	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public boolean isTerminal() {
		return terminal;
	}

	public void setTerminal(boolean terminal) {
		this.terminal = terminal;
	}


	public ArrayList<WorkItem> getWorkItems() {
		return workItems;
	}

	public void setWorkItems(ArrayList<WorkItem> workItems) {
		this.workItems = workItems;
	}

}
