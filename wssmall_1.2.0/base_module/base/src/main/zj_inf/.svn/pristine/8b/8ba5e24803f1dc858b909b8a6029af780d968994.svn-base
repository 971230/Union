/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @version 2016-03-15
 * @see 开户时活动信息
 * 
 */
public class BSSActivityInfoVo implements Serializable {

	private String ActPlanID; // Y 活动ID（总部活动ID）
	private String ActionId;//N转兑包ID
	private String ActProtPer;//N活动协议期限单位：月
	private String ResourcesType;// N 资源类型 03：移动电话 04：上网卡 05：上网本
	private String ResourcesCode;// N 终端资源编码，一般是IMEI码
	private String ResourcesFee;// N 资源费用
	private String ResourcesRele;//Y资源归属类型01 总部管理资源02 省分管理资源03：全部资源04 社会渠道资源
	private String ResourcesBrand;//Y资源品牌    
	private String ResourcesModel;//Y资源型号    
	private String StoredFee;//Y预存金额    
	private String PresentedFee;//Y赠款金额    
	private String ThawPro;//Y按月返还比例
	private String TotolSaleFee;//Y累计消费单位：厘        
	private String MonthLowestFee;//Y月保底消费单位：厘    
	private String DepID;//Y押金科目ID    
	private String DepFee;//Y押金金额   
	private List<BSSActParaVo> ActPara; // * 活动扩展字段
	private String IsVirtual;	//是否虚拟终端ID
	private String MachineTypeCode;	//机型编码
	private String IsOccupied;	//是否已经预占—默认传1
	private String IdType;	//类型01:活动ID 02：转兑包ID 03：附加产品ID—默认

	private String ProductIdA;	//活动附加产品ID

	public String getActPlanID() {
		if (StringUtils.isBlank(ActPlanID)) return null;
		return ActPlanID;
	}

	public void setActPlanID(String actPlanId) {
		this.ActPlanID = actPlanId;
	}

	public String getActionId() {
		if (StringUtils.isBlank(ActionId)) return null;
		return ActionId;
	}

	public void setActionId(String actionId) {
		this.ActionId = actionId;
	}

	public String getActProtPer() {
		if (StringUtils.isBlank(ActProtPer)) return null;
		return ActProtPer;
	}

	public void setActProtPer(String actProtPer) {
		this.ActProtPer = actProtPer;
	}

	public String getResourcesType() {
		if (StringUtils.isBlank(ResourcesType)) return null;
		return ResourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.ResourcesType = resourcesType;
	}

	public String getResourcesCode() {
		if (StringUtils.isBlank(ResourcesCode)) return null;
		return ResourcesCode;
	}

	public void setResourcesCode(String resourcesCode) {
		this.ResourcesCode = resourcesCode;
	}

	public String getResourcesFee() {
		if (StringUtils.isBlank(ResourcesFee)) return null;
		return ResourcesFee;
	}

	public void setResourcesFee(String resourcesFee) {
		this.ResourcesFee = resourcesFee;
	}

	public String getResourcesRele() {
		if (StringUtils.isBlank(ResourcesRele)) return null;
		return ResourcesRele;
	}

	public void setResourcesRele(String resourcesRele) {
		this.ResourcesRele = resourcesRele;
	}

	public String getResourcesBrand() {
		if (StringUtils.isBlank(ResourcesBrand)) return null;
		return ResourcesBrand;
	}

	public void setResourcesBrand(String resourcesBrand) {
		this.ResourcesBrand = resourcesBrand;
	}

	public String getResourcesModel() {
		if (StringUtils.isBlank(ResourcesModel)) return null;
		return ResourcesModel;
	}

	public void setResourcesModel(String resourcesModel) {
		this.ResourcesModel = resourcesModel;
	}

	public String getStoredFee() {
		if (StringUtils.isBlank(StoredFee)) return null;
		return StoredFee;
	}

	public void setStoredFee(String storedFee) {
		this.StoredFee = storedFee;
	}

	public String getPresentedFee() {
		if (StringUtils.isBlank(PresentedFee)) return null;
		return PresentedFee;
	}

	public void setPresentedFee(String presentedFee) {
		this.PresentedFee = presentedFee;
	}

	public String getThawPro() {
		if (StringUtils.isBlank(ThawPro)) return null;
		return ThawPro;
	}

	public void setThawPro(String thawPro) {
		this.ThawPro = thawPro;
	}

	public String getTotolSaleFee() {
		if (StringUtils.isBlank(TotolSaleFee)) return null;
		return TotolSaleFee;
	}

	public void setTotolSaleFee(String totolSaleFee) {
		this.TotolSaleFee = totolSaleFee;
	}

	public String getMonthLowestFee() {
		if (StringUtils.isBlank(MonthLowestFee)) return null;
		return MonthLowestFee;
	}

	public void setMonthLowestFee(String monthLowestFee) {
		this.MonthLowestFee = monthLowestFee;
	}

	public String getDepID() {
		if (StringUtils.isBlank(DepID)) return null;
		return DepID;
	}

	public void setDepID(String depID) {
		this.DepID = depID;
	}

	public String getDepFee() {
		if (StringUtils.isBlank(DepFee)) return null;
		return DepFee;
	}

	public void setDepFee(String depFee) {
		this.DepFee = depFee;
	}

	public List<BSSActParaVo> getActPara() {
		if (ActPara==null || ActPara.size() <= 0) return null;
		return ActPara;
	}

	public void setActPara(List<BSSActParaVo> actPara) {
		this.ActPara = actPara;
	}

	public String getIsVirtual() {
		return IsVirtual;
	}

	public void setIsVirtual(String isVirtual) {
		IsVirtual = isVirtual;
	}

	public String getMachineTypeCode() {
		return MachineTypeCode;
	}

	public void setMachineTypeCode(String machineTypeCode) {
		MachineTypeCode = machineTypeCode;
	}

	public String getIsOccupied() {
		return IsOccupied;
	}

	public void setIsOccupied(String isOccupied) {
		IsOccupied = isOccupied;
	}

	public String getIdType() {
		return IdType;
	}

	public void setIdType(String idType) {
		IdType = idType;
	}

	public String getProductIdA() {
		return ProductIdA;
	}

	public void setProductIdA(String productIdA) {
		ProductIdA = productIdA;
	}
	
}
