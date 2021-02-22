/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;

/**
 * @author ZX
 * @version 2015-06-25
 *
 */
public class ZfInfoRspVo implements Serializable {
	
	private String serialNumber;
	private String codeTag;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCodeTag() {
		return codeTag;
	}
	public void setCodeTag(String codeTag) {
		this.codeTag = codeTag;
	}
	
}
