package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.req.ZteBusiWraperRequest;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 写卡完成通知WMS
 * 商品信息对象
 */
public class NotifyWriteCardResultGoodInfoVo implements Serializable{
	
	private static final long serialVersionUID = 3778568637588460526L;
	
	@ZteSoftCommentAnnotationParam(name="货品Id",type="String",isNecessary="Y",desc="货品Id")
	private String orderProductId;
	
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="Y",desc="商品Id")
	private String packageId;
	
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="N",desc="手机号码")
	private String mobileTel;
	
	@ZteSoftCommentAnnotationParam(name="状态",type="String",isNecessary="Y",desc="状态")
	private String status;
	
	@ZteSoftCommentAnnotationParam(name="描述",type="String",isNecessary="N",desc="描述")
	private String descr;
	
	@ZteSoftCommentAnnotationParam(name="预留字段1",type="String",isNecessary="N",desc="预留字段1")
	private String userDef1;
	
	@ZteSoftCommentAnnotationParam(name="预留字段2",type="String",isNecessary="N",desc="预留字段2")
	private String userDef2;
	
	@ZteSoftCommentAnnotationParam(name="商城订单编号",type="String",isNecessary="Y",desc="OrderId：商城订单编号")
	private String OrderId;
	
	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
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
	public String getMobileTel() {
		return mobileTel;
	}
	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
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
