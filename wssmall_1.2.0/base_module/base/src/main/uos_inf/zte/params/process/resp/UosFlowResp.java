package zte.params.process.resp;

import java.util.ArrayList;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author Reason.Yea
 * @version 创建时间：2014-9-26
 */
public class UosFlowResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "节点列表", type = "Object", isNecessary = "N", desc = "")
	private ArrayList<UosNode> nodes = new ArrayList<UosNode>();

	@ZteSoftCommentAnnotationParam(name = "线条列表", type = "Object", isNecessary = "N", desc = "")
	private ArrayList<UosLine> lines = new ArrayList<UosLine>();

	public ArrayList<UosNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<UosNode> nodes) {
		this.nodes = nodes;
	}

	public ArrayList<UosLine> getLines() {
		return lines;
	}

	public void setLines(ArrayList<UosLine> lines) {
		this.lines = lines;
	}

}
