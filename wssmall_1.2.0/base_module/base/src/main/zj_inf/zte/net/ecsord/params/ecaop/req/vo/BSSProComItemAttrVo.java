package zte.net.ecsord.params.ecaop.req.vo;

import org.apache.commons.lang3.StringUtils;

public class BSSProComItemAttrVo {

	private String ProComItemAttrCode;//Y特服附加属性编码
	private String ProComItemAttrValue;//	Y特服附加属性值
	
	public String getProComItemAttrCode() {
		if (StringUtils.isBlank(ProComItemAttrCode)) return null;
		return ProComItemAttrCode;
	}
	public void setProComItemAttrCode(String proComItemAttrCode) {
		this.ProComItemAttrCode = proComItemAttrCode;
	}
	public String getProComItemAttrValue() {
		if (StringUtils.isBlank(ProComItemAttrValue)) return null;
		return ProComItemAttrValue;
	}
	public void setProComItemAttrValue(String proComItemAttrValue) {
		this.ProComItemAttrValue = proComItemAttrValue;
	}
	
}
