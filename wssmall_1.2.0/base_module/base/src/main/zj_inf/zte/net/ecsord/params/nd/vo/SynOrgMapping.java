package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;

public class SynOrgMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8713286958859275809L;

	private String party_id;
	
	private String org_name;
	
	private String hq_dept_id;
	
	private String dept_name;

	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getHq_dept_id() {
		return hq_dept_id;
	}

	public void setHq_dept_id(String hq_dept_id) {
		this.hq_dept_id = hq_dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
}
