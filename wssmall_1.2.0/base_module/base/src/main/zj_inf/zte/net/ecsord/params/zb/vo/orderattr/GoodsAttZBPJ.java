package zte.net.ecsord.params.zb.vo.orderattr;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * 商品附属信息:总部配件模版
 * @author sguo
 *
 */
public class GoodsAttZBPJ extends GoodsAtt implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "号卡客户姓名", type = "String", isNecessary = "Y", desc = "CustomerName：号卡客户姓名")
	private String PartsType;

	@ZteSoftCommentAnnotationParam(name = "商品类型", type = "String", isNecessary = "Y", desc = "goodsType：商品类型")
	private String goodsType;
	
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "CertType：老用户非必填")
	private Resources ResourcesInfo;

	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "OrderId：订单编号")
	private String notNeedReqStrOrderId;
	
	public String getPartsType() {
		return PartsType;
	}

	public void setPartsType(String partsType) {
		PartsType = partsType;
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

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

}
