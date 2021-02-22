package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NotifyStatusFromWMSGoodInfoVo implements Serializable {

	@ZteSoftCommentAnnotationParam(name="货品ID",type="String",isNecessary="Y",desc="货品ID")
	private String orderProductId;
	@ZteSoftCommentAnnotationParam(name="商品ID",type="String",isNecessary="Y",desc="商品ID")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name="唯一串号",type="String",isNecessary="Y",desc="唯一串号")
	private String imei;
	@ZteSoftCommentAnnotationParam(name="顺序号 ",type="String",isNecessary="N",desc="顺序号 ")
	private String indexNum;
	@ZteSoftCommentAnnotationParam(name="预留字段3 ",type="String",isNecessary="N",desc="预留字段3 ")
	private String UserDef3;
	@ZteSoftCommentAnnotationParam(name="预留字段4 ",type="String",isNecessary="N",desc="预留字段4 ")
	private String UserDef4;

	public String getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}

	public String getUserDef3() {
		return UserDef3;
	}

	public void setUserDef3(String userDef3) {
		UserDef3 = userDef3;
	}

	public String getUserDef4() {
		return UserDef4;
	}

	public void setUserDef4(String userDef4) {
		UserDef4 = userDef4;
	}
	
	
}
