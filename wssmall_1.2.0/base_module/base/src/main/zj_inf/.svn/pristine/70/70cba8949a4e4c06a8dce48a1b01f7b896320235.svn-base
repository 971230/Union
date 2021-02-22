/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version AOP二期
 *
 */
public class ActivityInfoReqVo implements Serializable  {	
	@ZteSoftCommentAnnotationParam(name = "活动方案ID（客户选定）", type = "String", isNecessary = "Y", desc = "活动方案ID（客户选定）")
	private String actPlanId;
	@ZteSoftCommentAnnotationParam(name = "活动协议期限，单位：月", type = "String", isNecessary = "N", desc = "活动协议期限，单位：月")
	private String actProtPer;
	@ZteSoftCommentAnnotationParam(name = "终端型号（购机必传），来自终端查询？", type = "String", isNecessary = "N", desc = "终端型号（购机必传），来自终端查询？")
	private String resourcesModel;
	@ZteSoftCommentAnnotationParam(name = "终端品牌（购机必传），来自终端查询？", type = "String", isNecessary = "N", desc = "终端品牌（购机必传），来自终端查询？")
	private String resourcesBrand;
	@ZteSoftCommentAnnotationParam(name = "续约标记：0：不续约 1：续约-- 取值逻辑是?", type = "String", isNecessary = "N", desc = "续约标记：0：不续约 1：续约-- 取值逻辑是?")
	private String extensionTag;
	@ZteSoftCommentAnnotationParam(name = "活动扩展字段", type = "String", isNecessary = "N", desc = "活动扩展字段")
	private List<ActParaVo> actPara;
	
	private String resourcesType;// N 资源类型 03：移动电话 04：上网卡 05：上网本
	private String resProcId;// N 预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联
	private String resourcesCode;// N 终端资源编码，一般是IMEI码
	private String isTest; // N 是否测试终端 0：测试 1：正式
	private String resourcesFee;// N 资源费用
	private String activityFee; // N 活动预存费用
	private List<ProdPackElementReqVo> packageElement; // 活动下自选包
	
	public String getResourcesType() {
		return resourcesType;
	}
	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}
	public String getResProcId() {
		return resProcId;
	}
	public void setResProcId(String resProcId) {
		this.resProcId = resProcId;
	}
	public String getResourcesCode() {
		return resourcesCode;
	}
	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}
	public String getIsTest() {
		return isTest;
	}
	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
	public String getResourcesFee() {
		return resourcesFee;
	}
	public void setResourcesFee(String resourcesFee) {
		this.resourcesFee = resourcesFee;
	}
	public String getActivityFee() {
		return activityFee;
	}
	public void setActivityFee(String activityFee) {
		this.activityFee = activityFee;
	}
	public List<ProdPackElementReqVo> getPackageElement() {
		return packageElement;
	}
	public void setPackageElement(List<ProdPackElementReqVo> packageElement) {
		this.packageElement = packageElement;
	}
	public String getActPlanId() {
		return actPlanId;
	}
	public void setActPlanId(String actPlanId) {
		this.actPlanId = actPlanId;
	}
	public String getActProtPer() {
		return actProtPer;
	}
	public void setActProtPer(String actProtPer) {
		this.actProtPer = actProtPer;
	}
	public String getResourcesModel() {
		return resourcesModel;
	}
	public void setResourcesModel(String resourcesModel) {
		this.resourcesModel = resourcesModel;
	}
	public String getResourcesBrand() {
		return resourcesBrand;
	}
	public void setResourcesBrand(String resourcesBrand) {
		this.resourcesBrand = resourcesBrand;
	}
	public String getExtensionTag() {
		return extensionTag;
	}
	public void setExtensionTag(String extensionTag) {
		this.extensionTag = extensionTag;
	}
	public List<ActParaVo> getActPara() {
		return actPara;
	}
	public void setActPara(List<ActParaVo> actPara) {
		this.actPara = actPara;
	}
	
}
