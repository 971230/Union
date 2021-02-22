package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.PackageElement;

public class ProductInfo implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "产品ID", type = "String", isNecessary = "Y", desc = "产品ID")
	private String productId;

	@ZteSoftCommentAnnotationParam(name = "0- 可选产品", type = "String", isNecessary = "Y", desc = "0- 可选产品")
	private String productMode;

	@ZteSoftCommentAnnotationParam(name = "产品下非默认的包及包元素（资费，服务）", type = "PackageElement", isNecessary = "N", desc = "产品下非默认的包及包元素（资费，服务）")
	private List<PackageElement> packageElement;

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String v) {
		this.productId = v;
	}

	public String getProductMode() {
		return this.productMode;
	}

	public void setProductMode(String v) {
		this.productMode = v;
	}

	public List<PackageElement> getPackageElement() {
		return this.packageElement;
	}

	public void setPackageElement(List<PackageElement> v) {
		this.packageElement = v;
	}

}
