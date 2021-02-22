/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @see 开户时选择的产品信息
 * 
 */
public class BSSProductVo implements Serializable {
	
	private String ProductID; // Y 产品标识
	private String Producttype; // Y 产品模式 1：主产品 2：附加产品
	private List<BSSProCompInfoVo> ProCompInfo; // * 产品下附加包及包元素（资费，服务）

	public String getProductID() {
		if (StringUtils.isBlank(ProductID)) return null;
		return ProductID;
	}
	public void setProductID(String productId) {
		this.ProductID = productId;
	}
	public String getProducttype() {
		if (StringUtils.isBlank(Producttype)) return null;
		return Producttype;
	}
	public void setProducttype(String producttype) {
		this.Producttype = producttype;
	}
	public List<BSSProCompInfoVo> getProCompInfo() {
		if (ProCompInfo==null || ProCompInfo.size() <= 0) return null;	
		return ProCompInfo;
	}
	public void setProCompInfo(List<BSSProCompInfoVo> proCompInfo) {
		this.ProCompInfo = proCompInfo;
	}
}
