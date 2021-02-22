package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;


import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * 商品附属信息:总部裸终端模版
 * @author sguo
 *
 */
public class GoodsAttZBLJZD extends GoodsAtt implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "号卡客户姓名", type = "String", isNecessary = "Y", desc = "CustomerName：号卡客户姓名")
	private String IsCustomized;

	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "CertType：老用户非必填")
	private Resources ResourcesInfo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	public String getIsCustomized() {
		return IsCustomized;
	}

	public void setIsCustomized(String isCustomized) {
		IsCustomized = isCustomized;
	}

	public Resources getResourcesInfo() {
		return ResourcesInfo;
	}

	public void setResourcesInfo(Resources resourcesInfo) {
		ResourcesInfo = resourcesInfo;
	}

	public String getNotNeedReqStrOrderId() {
		return notNeedReqStrOrderId;
	}

	public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
		this.notNeedReqStrOrderId = notNeedReqStrOrderId;
	}
	
}
