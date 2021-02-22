/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version
 * @see 开户时选择的产品信息
 * 
 */
public class OpenDealApplyProductVo implements Serializable {
	
	private String productId; // Y 产品标识
	private String productMode; // Y 产品模式 1：主产品 2：附加产品
	private List<OpenDealApplyPackageElementVo> packageElement; // * 产品下附加包及包元素（资费，服务）
	private String productCode; // N 套包编码，北六无线上网卡必传		
	
	public String getProductCode() {
		if (StringUtils.isBlank(productCode)) return null;
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductId() {
		if (StringUtils.isBlank(productId)) return null;
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductMode() {
		if (StringUtils.isBlank(productMode)) return null;
		return productMode;
	}
	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}
	public List<OpenDealApplyPackageElementVo> getPackageElement() {
		if (packageElement==null || packageElement.size() <= 0) return null;
		return packageElement;
	}
	public void setPackageElement(List<OpenDealApplyPackageElementVo> packageElement) {
		this.packageElement = packageElement;
	}
	
}
