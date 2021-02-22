/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @version 2016-03-18
 * @see 产品下附加包及包元素（资费，服务）
 * 
 */
public class BSSProCompInfoVo implements Serializable {
	
	private String ProComponentID;// Y 包编号
	private List<BSSProComItemInfoVo> ProComItemInfo;

	public String getProComponentID() {
		if (StringUtils.isBlank(ProComponentID)) return null;
		return ProComponentID;
	}

	public void setProComponentID(String proComponentId) {
		this.ProComponentID = proComponentId;
	}

	public List<BSSProComItemInfoVo> getProComItemInfo() {
		if (ProComItemInfo==null || ProComItemInfo.size() <= 0) return null;
		return ProComItemInfo;
	}

	public void setProComItemInfo(List<BSSProComItemInfoVo> proComItemInfo) {
		this.ProComItemInfo = proComItemInfo;
	}

}
