/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version
 * @see 开户时选择的产品信息
 * 
 */
public class ProductInfoReqVo implements Serializable {

	private static final long serialVersionUID = 5113929029447566819L;
	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "Y", desc = "productId")
	private String productId;
	@ZteSoftCommentAnnotationParam(name = "产品企业编码", type = "String", isNecessary = "Y", desc = "")
	private String companyId;
	@ZteSoftCommentAnnotationParam(name = "产品别名", type = "String", isNecessary = "Y", desc = "")
	private String productNameX;
	@ZteSoftCommentAnnotationParam(name = "0：可选产品；1：主产品", type = "String", isNecessary = "Y", desc = "productMode")
	private String productMode;
	@ZteSoftCommentAnnotationParam(name = "00：订购；01：退订；02：变更", type = "String", isNecessary = "N", desc = "optType")
	private String optType;
	@ZteSoftCommentAnnotationParam(name = "套包编码，北六无线上网卡必传", type = "String", isNecessary = "N", desc = "productCode")
	private String productCode;
	@ZteSoftCommentAnnotationParam(name = "产品下附加包及包元素（资费，服务）", type = "PackageChangePackageElementVo", isNecessary = "Y", desc = "packageElement")
	private List<ProdPackElementReqVo> packageElement;

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getProductCode() {
		if (StringUtils.isBlank(productCode))
			return null;
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductId() {
		if (StringUtils.isBlank(productId))
			return null;
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductMode() {
		if (StringUtils.isBlank(productMode))
			return null;
		return productMode;
	}

	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}

	public List<ProdPackElementReqVo> getPackageElement() {
		if (packageElement == null || packageElement.size() <= 0)
			return null;
		return packageElement;
	}

	public void setPackageElement(List<ProdPackElementReqVo> packageElement) {
		this.packageElement = packageElement;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getProductNameX() {
		return productNameX;
	}

	public void setProductNameX(String productNameX) {
		this.productNameX = productNameX;
	}

}
