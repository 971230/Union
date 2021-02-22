/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-06-25
 *
 */
public class UserInfoRspVo implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name = "电信业务类别", type = "String", isNecessary = "Y", desc = "serviceClassCode")
	private String serviceClassCode;
	@ZteSoftCommentAnnotationParam(name = "用户编码", type = "String", isNecessary = "Y", desc = "userId")
	private String userId;
	@ZteSoftCommentAnnotationParam(name = "用户状态", type = "String", isNecessary = "Y", desc = "userState")
	private String userState;
	@ZteSoftCommentAnnotationParam(name = "品牌编码", type = "String", isNecessary = "Y", desc = "brandCode")
	private String brandCode;
	@ZteSoftCommentAnnotationParam(name = "品牌名称", type = "String", isNecessary = "N", desc = "brand")
	private String brand;
	@ZteSoftCommentAnnotationParam(name = "入网时间", type = "String", isNecessary = "N", desc = "openDate")
	private String openDate;
	@ZteSoftCommentAnnotationParam(name = "通话级别", type = "String", isNecessary = "N", desc = "landLevle")
	private String landLevle;
	@ZteSoftCommentAnnotationParam(name = "漫游状态", type = "String", isNecessary = "N", desc = "roamStat")
	private String roamStat;
	@ZteSoftCommentAnnotationParam(name = "当前使用产品标识", type = "String", isNecessary = "N", desc = "productId")
	private String productId;
	@ZteSoftCommentAnnotationParam(name = "当前使用产品名称", type = "String", isNecessary = "N", desc = "productName")
	private String productName;
	@ZteSoftCommentAnnotationParam(name = "下月使用产品标识", type = "String", isNecessary = "N", desc = "nextProductId")
	private String nextProductId;
	@ZteSoftCommentAnnotationParam(name = "下月使用产品名称", type = "String", isNecessary = "N", desc = "nextProductName")
	private String nextProductName;
	@ZteSoftCommentAnnotationParam(name = "最低消费(单位：分)", type = "String", isNecessary = "N", desc = "minConsume")
	private String minConsume;
	@ZteSoftCommentAnnotationParam(name = "0 后付费 1 预付费 2 OCS 3 其它", type = "String", isNecessary = "N", desc = "subscType")
	private String subscType;
	@ZteSoftCommentAnnotationParam(name = "是否3G：1是；2 否", type = "String", isNecessary = "N", desc = "flag3g")
	private String flag3G;
	@ZteSoftCommentAnnotationParam(name = "欠费信息", type = "ArrearageFeeInfoRspVo", isNecessary = "N", desc = "arrearageFeeInfo")
	private ArrearageFeeInfoRspVo arrearageFeeInfo;
	@ZteSoftCommentAnnotationParam(name = "主副卡列表", type = "ZfInfoRspVo", isNecessary = "N", desc = "sysType")
	private List<ZfInfoRspVo> zfInfo;
	@ZteSoftCommentAnnotationParam(name = "活动信息", type = "String", isNecessary = "N", desc = "activityInfo")
	private List<ActivityInfoRspVo> activityInfo;
	@ZteSoftCommentAnnotationParam(name = "资费服务信息", type = "String", isNecessary = "N", desc = "elementInfo")
	private List<ElementInfoRspVo> elementInfo;
	
	public String getServiceClassCode() {
		return serviceClassCode;
	}
	public void setServiceClassCode(String serviceClassCode) {
		this.serviceClassCode = serviceClassCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getLandLevle() {
		return landLevle;
	}
	public void setLandLevle(String landLevle) {
		this.landLevle = landLevle;
	}
	public String getRoamStat() {
		return roamStat;
	}
	public void setRoamStat(String roamStat) {
		this.roamStat = roamStat;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getNextProductId() {
		return nextProductId;
	}
	public void setNextProductId(String nextProductId) {
		this.nextProductId = nextProductId;
	}
	public String getNextProductName() {
		return nextProductName;
	}
	public void setNextProductName(String nextProductName) {
		this.nextProductName = nextProductName;
	}
	public String getMinConsume() {
		return minConsume;
	}
	public void setMinConsume(String minConsume) {
		this.minConsume = minConsume;
	}
	public String getSubscType() {
		return subscType;
	}
	public void setSubscType(String subscType) {
		this.subscType = subscType;
	}
	
	public String getFlag3G() {
		return flag3G;
	}
	public void setFlag3G(String flag3g) {
		flag3G = flag3g;
	}
	public ArrearageFeeInfoRspVo getArrearageFeeInfo() {
		return arrearageFeeInfo;
	}
	public void setArrearageFeeInfo(
			ArrearageFeeInfoRspVo arrearageFeeInfo) {
		this.arrearageFeeInfo = arrearageFeeInfo;
	}
	public List<ZfInfoRspVo> getZfInfo() {
		return zfInfo;
	}
	public void setZfInfo(List<ZfInfoRspVo> zfInfo) {
		this.zfInfo = zfInfo;
	}
	public List<ActivityInfoRspVo> getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(List<ActivityInfoRspVo> activityInfo) {
		this.activityInfo = activityInfo;
	}
	public List<ElementInfoRspVo> getElementInfo() {
		return elementInfo;
	}
	public void setElementInfo(List<ElementInfoRspVo> elementInfo) {
		this.elementInfo = elementInfo;
	}
	
}
