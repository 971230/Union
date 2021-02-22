package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 业务完成状态通知
 * 商品信息对象
 */
public class NotifyAccountGoodInfoVo implements Serializable{
	
	private static final long serialVersionUID = -7872313688801797366L;
	
	@ZteSoftCommentAnnotationParam(name="货品Id",type="String",isNecessary="Y",desc="货品Id，序列生成，具有唯一性")
	private String orderProductId;
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="Y",desc="商品Id，序列生成，具有唯一性")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name="业务类型",type="String",isNecessary="N",desc="业务类型：01(ESS业务办理)，02(BSS业务办理)；目前暂时只有01")
	private String businessType;
	@ZteSoftCommentAnnotationParam(name="状态",type="String",isNecessary="Y",desc="状态")
	private String status;
	@ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="Y",desc="描述")
	private String descr;
	@ZteSoftCommentAnnotationParam(name="预留字段1",type="String",isNecessary="N",desc="预留字段1")
	private String userDef1;
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="N",desc="预留字段2")
	private String userDef2;
	
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
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getUserDef1() {
		return userDef1;
	}
	public void setUserDef1(String userDef1) {
		this.userDef1 = userDef1;
	}
	public String getUserDef2() {
		return userDef2;
	}
	public void setUserDef2(String userDef2) {
		this.userDef2 = userDef2;
	}
}
