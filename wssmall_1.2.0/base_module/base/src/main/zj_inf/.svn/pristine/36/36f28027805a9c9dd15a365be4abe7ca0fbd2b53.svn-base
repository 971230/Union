/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 号码资料(无线上网卡不一定存在)
 * 
 */
public class OpenDealApplyNumIdVo implements Serializable {

	private String serialNumber; // Y 号码
	private String proKey; // Y 资源预占关键字
	private OpenDealApplyNiceInfoVo niceInfo; // 靓号信息
	
	public String getSerialNumber() {
		if(StringUtils.isBlank(serialNumber))return null;
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProKey() {
		if(StringUtils.isBlank(serialNumber))return null;
		return proKey;
	}

	public void setProKey(String proKey) {
		this.proKey = proKey;
	}

	public OpenDealApplyNiceInfoVo getNiceInfo() {
		return niceInfo;
	}

	public void setNiceInfo(OpenDealApplyNiceInfoVo niceInfo) {
		this.niceInfo = niceInfo;
	}

}
