/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ZX
 * @version 2015-05-05
 * @see 开户时活动信息
 * 
 */
public class OpenDealApplyActivityInfoVo implements Serializable {

	private String actPlanId; // Y 活动ID（总部活动ID）
	private String resourcesType;// N 资源类型 03：移动电话 04：上网卡 05：上网本
	private String resProcId;// N 预占流水，保持前后多次资源操作的内容一致，保证多次操作是同一订单的前后关联
	private String resourcesCode;// N 终端资源编码，一般是IMEI码
	private String isTest; // N 是否测试终端 0：测试 1：正式
	private String resourcesFee;// N 资源费用
	private String activityFee; // N 活动预存费用
	private List<OpenDealApplyPackageElementVo> packageElement; // 活动下自选包
	private List<OpenDealApplyActParaVo> actPara; // * 活动扩展字段

	public String getResourcesType() {
		if (StringUtils.isBlank(resourcesType)) return null;
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	public String getResProcId() {
		if (StringUtils.isBlank(resProcId)) return null;
		return resProcId;
	}

	public void setResProcId(String resProcId) {
		this.resProcId = resProcId;
	}

	public String getResourcesCode() {
		if (StringUtils.isBlank(resourcesCode)) return null;
		return resourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		this.resourcesCode = resourcesCode;
	}

	public String getIsTest() {
		if (StringUtils.isBlank(isTest)) return null;
		return isTest;
	}

	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}

	public String getResourcesFee() {
		if (StringUtils.isBlank(resourcesFee)) return null;
		return resourcesFee;
	}

	public void setResourcesFee(String resourcesFee) {
		this.resourcesFee = resourcesFee;
	}

	public String getActivityFee() {
		if (StringUtils.isBlank(activityFee)) return null;
		return activityFee;
	}

	public void setActivityFee(String activityFee) {
		this.activityFee = activityFee;
	}

	public String getActPlanId() {
		if (StringUtils.isBlank(actPlanId)) return null;
		return actPlanId;
	}

	public void setActPlanId(String actPlanId) {
		this.actPlanId = actPlanId;
	}

	public List<OpenDealApplyActParaVo> getActPara() {
		if (actPara==null || actPara.size() <= 0) return null;
		return actPara;
	}

	public void setActPara(List<OpenDealApplyActParaVo> actPara) {
		this.actPara = actPara;
	}

	public List<OpenDealApplyPackageElementVo> getPackageElement() {
		if (packageElement==null || packageElement.size() <= 0) return null;
		return packageElement;
	}

	public void setPackageElement(List<OpenDealApplyPackageElementVo> packageElement) {
		this.packageElement = packageElement;
	}

}
