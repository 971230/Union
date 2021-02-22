package zte.net.ecsord.params.wms.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 接收写卡机编号
 * 商品信息对象
 */
public class SynCardInfoGoodInfoVo implements Serializable{
	
	private static final long serialVersionUID = -7824672397606346906L;
	
	@ZteSoftCommentAnnotationParam(name="货品Id",type="String",isNecessary="Y",desc="货品Id")
	private String orderProductId;
	@ZteSoftCommentAnnotationParam(name="商品Id",type="String",isNecessary="Y",desc="商品Id")
	private String packageId;
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="N",desc="手机号码")
	private String mobileTel;
	@ZteSoftCommentAnnotationParam(name="写卡机编号",type="String",isNecessary="Y",desc="写卡机编号")
	private String readId;
	@ZteSoftCommentAnnotationParam(name="顺序号",type="String",isNecessary="Y",desc="顺序号")
	private Integer indexNum;
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
	public String getMobileTel() {
		return mobileTel;
	}
	public void setMobileTel(String mobileTel) {
		this.mobileTel = mobileTel;
	}
	public String getReadId() {
		return readId;
	}
	public void setReadId(String readId) {
		this.readId = readId;
	}
	public Integer getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
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
