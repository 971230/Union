package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;
import java.util.List;

public class Flow_Info_Node_Cfg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5902469017942523131L;

	private String node_code;
	
	private String node_name;
	
	private String ret_code;
	
	private List<Flow_Info_Rule_Cfg> rules;
	
	private String isDoing;

	public String getNode_code() {
		return node_code;
	}

	public void setNode_code(String node_code) {
		this.node_code = node_code;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public List<Flow_Info_Rule_Cfg> getRules() {
		return rules;
	}

	public void setRules(List<Flow_Info_Rule_Cfg> rules) {
		this.rules = rules;
	}

	public String getIsDoing() {
		return isDoing;
	}

	public void setIsDoing(String isDoing) {
		this.isDoing = isDoing;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}
}
