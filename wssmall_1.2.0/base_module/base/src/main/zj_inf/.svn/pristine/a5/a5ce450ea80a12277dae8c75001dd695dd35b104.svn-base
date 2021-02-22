package zte.net.ecsord.params.ecaop.req.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @version BSS
 *
 */
public class BSSSpclSvcInfoVo {

	private String SpclSvcID;//Y特服编码
	private String SpclSvcValue;//Y特服值
	private List<BSSProComItemAttrVo> ProComItemAttr;//特服附加属性
	
	public String getSpclSvcId() {
		if (StringUtils.isBlank(SpclSvcID)) return null;
		return SpclSvcID;
	}
	public void setSpclSvcId(String spclSvcId) {
		this.SpclSvcID = spclSvcId;
	}
	public String getSpclSvcValue() {
		if (StringUtils.isBlank(SpclSvcValue)) return null;
		return SpclSvcValue;
	}
	public void setSpclSvcValue(String spclSvcValue) {
		this.SpclSvcValue = spclSvcValue;
	}
	public List<BSSProComItemAttrVo> getProComItemAttr() {
		if (ProComItemAttr==null || ProComItemAttr.size() <= 0) return null;
		return ProComItemAttr;
	}
	public void setProComItemAttr(List<BSSProComItemAttrVo> proComItemAttr) {
		this.ProComItemAttr = proComItemAttr;
	}

	
}
