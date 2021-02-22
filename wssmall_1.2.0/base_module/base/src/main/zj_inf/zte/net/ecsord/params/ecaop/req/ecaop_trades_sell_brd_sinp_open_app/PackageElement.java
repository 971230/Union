package zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app;

import java.util.*;
import java.io.Serializable;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.ecsord.params.ecaop.req.ecaop_trades_sell_brd_sinp_open_app.ServiceAttr;

public class PackageElement implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "取消默认标示（此字段用于取消产下下某个默认的资费或者包，不下发）", type = "String", isNecessary = "N", desc = "取消默认标示（此字段用于取消产下下某个默认的资费或者包，不下发）")
	private String optType;

	@ZteSoftCommentAnnotationParam(name = "元素类型", type = "String", isNecessary = "Y", desc = "元素类型")
	private String elementType;

	@ZteSoftCommentAnnotationParam(name = "包编号", type = "String", isNecessary = "Y", desc = "包编号")
	private String packageId;

	@ZteSoftCommentAnnotationParam(name = "元素编号", type = "String", isNecessary = "Y", desc = "元素编号")
	private String elementId;

	@ZteSoftCommentAnnotationParam(name = "服务属性", type = "ServiceAttr", isNecessary = "N", desc = "服务属性")
	private List<ServiceAttr> serviceAttr;

	public String getOptType() {
		return this.optType;
	}

	public void setOptType(String v) {
		this.optType = v;
	}

	public String getElementType() {
		return this.elementType;
	}

	public void setElementType(String v) {
		this.elementType = v;
	}

	public String getPackageId() {
		return this.packageId;
	}

	public void setPackageId(String v) {
		this.packageId = v;
	}

	public String getElementId() {
		return this.elementId;
	}

	public void setElementId(String v) {
		this.elementId = v;
	}

	public List<ServiceAttr> getServiceAttr() {
		return this.serviceAttr;
	}

	public void setServiceAttr(List<ServiceAttr> v) {
		this.serviceAttr = v;
	}

}
