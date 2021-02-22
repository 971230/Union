/**
 * 
 */
package com.zte.cbss.autoprocess.model.data;

/**
 * @author ZX
 * SPDataSearch.java
 * 2015-1-17
 */
public class SPDataSearch implements Cloneable {

	private String touchId;
	private String serialNumber;
	private String netTypeCode;
	private String rightCode;
	private String globalPageName;
	
	private String referer = "https://gd.cbss.10010.com/custserv";
		
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getTouchId() {
		return touchId;
	}
	public void setTouchId(String touchId) {
		this.touchId = touchId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getNetTypeCode() {
		return netTypeCode;
	}
	public void setNetTypeCode(String netTypeCode) {
		this.netTypeCode = netTypeCode;
	}
	public String getRightCode() {
		return rightCode;
	}
	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
	public String getGlobalPageName() {
		return globalPageName;
	}
	public void setGlobalPageName(String globalPageName) {
		this.globalPageName = globalPageName;
	}
	
}
