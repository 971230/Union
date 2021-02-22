package com.ztesoft.net.mall.core.model;


public class Terminal  implements java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3241763730157797861L;

	private String source_from;
	
	private String sn;
	
	private String terminal_no;

	public String getSn() {
		return sn;
	}
	
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getTerminal_no() {
		return terminal_no;
	}
	
	public void setTerminal_no(String terminal_no) {
		this.terminal_no = terminal_no;
	}
	
	public String getSource_from() {
		return source_from;
	}
	
	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}
}