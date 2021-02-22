package zte.net.ecsord.params.base.po;

import java.io.Serializable;

public class ATTR_OPTIONS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8960738914887244126L;

	private String stype;
	
	private String value;
	
	private String value_desc;

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue_desc() {
		return value_desc;
	}

	public void setValue_desc(String value_desc) {
		this.value_desc = value_desc;
	}
}
