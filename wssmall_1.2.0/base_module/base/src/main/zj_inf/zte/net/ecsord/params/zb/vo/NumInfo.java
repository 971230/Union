package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumInfo implements Serializable{
	@ZteSoftCommentAnnotationParam(name="号码",type="String",isNecessary="Y",desc="numId：号码")
	private String numId;
	
	@ZteSoftCommentAnnotationParam(name="号码说明",type="String",isNecessary="Y",desc="numMemo：号码说明")
	private String numMemo;
	
	@ZteSoftCommentAnnotationParam(name="预存话费金额",type="String",isNecessary="N",desc="advancePay：预存话费金额")
	private String advancePay;
	
	@ZteSoftCommentAnnotationParam(name="普通预存",type="String",isNecessary="N",desc="advanceCom：普通预存")
	private String advanceCom;
	
	@ZteSoftCommentAnnotationParam(name="专项预存",type="String",isNecessary="N",desc="advanceSpe：专项预存")
	private String advanceSpe;
	
	@ZteSoftCommentAnnotationParam(name="返回时长",type="String",isNecessary="N",desc="numThawTim：返回时长")
	private String numThawTim;
	
	@ZteSoftCommentAnnotationParam(name="分月返回金额",type="String",isNecessary="N",desc="numThawPro：分月返回金额")
	private String numThawPro;
	
	@ZteSoftCommentAnnotationParam(name="号码等级",type="String",isNecessary="Y",desc="numLevel：号码等级")
	private String numLevel;
	
	@ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="numPreFee：")
	private String numPreFee;
	
	@ZteSoftCommentAnnotationParam(name="",type="String",isNecessary="Y",desc="numTime：")
	private String numTime;
	
	@ZteSoftCommentAnnotationParam(name="号码等级",type="String",isNecessary="Y",desc="classId：号码等级")
	private String classId;
	
	@ZteSoftCommentAnnotationParam(name="考核期最低消费",type="String",isNecessary="N",desc="lowCostChe：考核期最低消费")
	private String lowCostChe;
	
	@ZteSoftCommentAnnotationParam(name="考核时长",type="String",isNecessary="N",desc="timeDurChe：考核时长")
	private String timeDurChe;
	
	@ZteSoftCommentAnnotationParam(name="考核期是否过户",type="String",isNecessary="N",desc="changeTagChe：考核期是否过户")
	private String changeTagChe;
	
	@ZteSoftCommentAnnotationParam(name="考核期是否销户",type="String",isNecessary="N",desc="cancelTagChe：考核期是否销户")
	private String cancelTagChe;
	
	@ZteSoftCommentAnnotationParam(name="考核期违约金月份",type="String",isNecessary="N",desc="bremonChe：考核期违约金月份")
	private String bremonChe;
	
	@ZteSoftCommentAnnotationParam(name="协议期最低消费",type="String",isNecessary="N",desc="lowCostPro：协议期最低消费")
	private String lowCostPro;
	
	@ZteSoftCommentAnnotationParam(name="协议时长",type="String",isNecessary="N",desc="timeDurPro：协议时长")
	private String timeDurPro;
	
	@ZteSoftCommentAnnotationParam(name="协议期是否过户",type="String",isNecessary="N",desc="changeTagPro：协议期是否过户")
	private String changeTagPro;
	
	@ZteSoftCommentAnnotationParam(name="协议期是否销户",type="String",isNecessary="N",desc="cancelTagPro：协议期是否销户")
	private String cancelTagPro;
	
	@ZteSoftCommentAnnotationParam(name="协议期违约金月份",type="String",isNecessary="N",desc="broMonPro：协议期违约金月份")
	private String broMonPro;
	
	@ZteSoftCommentAnnotationParam(name="号码约束产品",type="String",isNecessary="N",desc="numProduct：号码约束产品")
	private String numProduct;
	
	@ZteSoftCommentAnnotationParam(name="关联号码",type="String",isNecessary="N",desc="numRelNum：关联号码")
	private String numRelNum;
	
	@ZteSoftCommentAnnotationParam(name="关联卡号",type="String",isNecessary="N",desc="simId：关联卡号")
	private String simId;
	
	@ZteSoftCommentAnnotationParam(name="分组信息",type="GroupInfo",isNecessary="N",desc="groupInfo：分组信息")
	private GroupInfo groupInfo;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="List",isNecessary="N",desc="para：保留字段")
	private List<Para> para;

	public String getNumId() {
		return numId;
	}

	public void setNumId(String numId) {
		this.numId = numId;
	}

	public String getNumMemo() {
		return numMemo;
	}

	public void setNumMemo(String numMemo) {
		this.numMemo = numMemo;
	}

	public String getAdvancePay() {
		return advancePay;
	}

	public void setAdvancePay(String advancePay) {
		this.advancePay = advancePay;
	}

	public String getAdvanceCom() {
		return advanceCom;
	}

	public void setAdvanceCom(String advanceCom) {
		this.advanceCom = advanceCom;
	}

	public String getAdvanceSpe() {
		return advanceSpe;
	}

	public void setAdvanceSpe(String advanceSpe) {
		this.advanceSpe = advanceSpe;
	}

	public String getNumThawTim() {
		return numThawTim;
	}

	public void setNumThawTim(String numThawTim) {
		this.numThawTim = numThawTim;
	}

	public String getNumThawPro() {
		return numThawPro;
	}

	public void setNumThawPro(String numThawPro) {
		this.numThawPro = numThawPro;
	}

	public String getNumLevel() {
		return numLevel;
	}

	public void setNumLevel(String numLevel) {
		this.numLevel = numLevel;
	}

	public String getNumPreFee() {
		return numPreFee;
	}

	public void setNumPreFee(String numPreFee) {
		this.numPreFee = numPreFee;
	}

	public String getNumTime() {
		return numTime;
	}

	public void setNumTime(String numTime) {
		this.numTime = numTime;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLowCostChe() {
		return lowCostChe;
	}

	public void setLowCostChe(String lowCostChe) {
		this.lowCostChe = lowCostChe;
	}

	public String getTimeDurChe() {
		return timeDurChe;
	}

	public void setTimeDurChe(String timeDurChe) {
		this.timeDurChe = timeDurChe;
	}

	public String getChangeTagChe() {
		return changeTagChe;
	}

	public void setChangeTagChe(String changeTagChe) {
		this.changeTagChe = changeTagChe;
	}

	public String getCancelTagChe() {
		return cancelTagChe;
	}

	public void setCancelTagChe(String cancelTagChe) {
		this.cancelTagChe = cancelTagChe;
	}

	public String getBremonChe() {
		return bremonChe;
	}

	public void setBremonChe(String bremonChe) {
		this.bremonChe = bremonChe;
	}

	public String getLowCostPro() {
		return lowCostPro;
	}

	public void setLowCostPro(String lowCostPro) {
		this.lowCostPro = lowCostPro;
	}

	public String getTimeDurPro() {
		return timeDurPro;
	}

	public void setTimeDurPro(String timeDurPro) {
		this.timeDurPro = timeDurPro;
	}

	public String getChangeTagPro() {
		return changeTagPro;
	}

	public void setChangeTagPro(String changeTagPro) {
		this.changeTagPro = changeTagPro;
	}

	public String getCancelTagPro() {
		return cancelTagPro;
	}

	public void setCancelTagPro(String cancelTagPro) {
		this.cancelTagPro = cancelTagPro;
	}

	public String getBroMonPro() {
		return broMonPro;
	}

	public void setBroMonPro(String broMonPro) {
		this.broMonPro = broMonPro;
	}

	public String getNumProduct() {
		return numProduct;
	}

	public void setNumProduct(String numProduct) {
		this.numProduct = numProduct;
	}

	public String getNumRelNum() {
		return numRelNum;
	}

	public void setNumRelNum(String numRelNum) {
		this.numRelNum = numRelNum;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public GroupInfo getGroupInfo() {
		return groupInfo;
	}

	public void setGroupInfo(GroupInfo groupInfo) {
		this.groupInfo = groupInfo;
	}

	public List<Para> getPara() {
		return para;
	}

	public void setPara(List<Para> para) {
		this.para = para;
	}
	
	
}