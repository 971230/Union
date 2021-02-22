package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @version BSS
 *
 */
public class BSSNumberInfoVo implements Serializable {

	private String NumGrade;//Y靓号类别等级：01：一级靓号02：二级靓号03：三级靓号04：四级靓号05：五级靓号06：六级靓号99：普通号码
	private String OccupiedId;//号码预占流水
	private String TailNumType;//N尾号类型（靓号规则）例如：AAAAA或正则表达式
	private String NumProtPer;//N在网时长单位：月
	private String NumStoredFee;//N预存金额；单位：厘
	private String NumThawPro;//N按月解冻金额；单位：厘
	private String NumMonthLowFee;//N月保底消费 单位：厘
	private String FirstMonthAmount;//N首月到帐规则（单位：厘）
	private String EndMonthAmount;//N末月到帐规则（单位：厘）
	private String GroupCustCount;//N携带集团客户数量
	
	public String getNumGrade() {
		if (StringUtils.isBlank(NumGrade)) return null;
		return NumGrade;
	}
	public void setNumGrade(String numGrade) {
		this.NumGrade = numGrade;
	}
	public String getOccupiedId() {
		if (StringUtils.isBlank(OccupiedId)) return null;
		return OccupiedId;
	}
	public void setOccupiedId(String occupiedId) {
		OccupiedId = occupiedId;
	}
	public String getTailNumTNpe() {
		if (StringUtils.isBlank(TailNumType)) return null;
		return TailNumType;
	}
	public void setTailNumTNpe(String tailNumTNpe) {
		this.TailNumType = tailNumTNpe;
	}
	public String getNumProtPer() {
		if (StringUtils.isBlank(NumProtPer)) return null;
		return NumProtPer;
	}
	public void setNumProtPer(String numProtPer) {
		this.NumProtPer = numProtPer;
	}
	public String getNumStoredFee() {
		if (StringUtils.isBlank(NumStoredFee)) return null;
		return NumStoredFee;
	}
	public void setNumStoredFee(String numStoredFee) {
		this.NumStoredFee = numStoredFee;
	}
	public String getNumThawPro() {
		if (StringUtils.isBlank(NumThawPro)) return null;
		return NumThawPro;
	}
	public void setNumThawPro(String numThawPro) {
		this.NumThawPro = numThawPro;
	}
	public String getNumMonthLowFee() {
		if (StringUtils.isBlank(NumMonthLowFee)) return null;
		return NumMonthLowFee;
	}
	public void setNumMonthLowFee(String numMonthLowFee) {
		this.NumMonthLowFee = numMonthLowFee;
	}
	public String getFirstMonthAmount() {
		if (StringUtils.isBlank(FirstMonthAmount)) return null;
		return FirstMonthAmount;
	}
	public void setFirstMonthAmount(String firstMonthAmount) {
		this.FirstMonthAmount = firstMonthAmount;
	}
	public String getEndMonthAmount() {
		if (StringUtils.isBlank(EndMonthAmount)) return null;
		return EndMonthAmount;
	}
	public void setEndMonthAmount(String endMonthAmount) {
		this.EndMonthAmount = endMonthAmount;
	}
	public String getGroupCustCount() {
		if (StringUtils.isBlank(GroupCustCount)) return null;
		return GroupCustCount;
	}
	public void setGroupCustCount(String groupCustCount) {
		this.GroupCustCount = groupCustCount;
	}
	
}
