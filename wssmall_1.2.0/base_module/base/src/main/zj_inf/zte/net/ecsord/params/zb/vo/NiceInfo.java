package zte.net.ecsord.params.zb.vo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NiceInfo {
	@ZteSoftCommentAnnotationParam(name="预存话费金额",type="String",isNecessary="Y",desc="advancePay：号码")
	private String advancePay; 
	@ZteSoftCommentAnnotationParam(name="普通预存",type="String",isNecessary="N",desc="advancePay：普通预存")
	private String advanceCom;
	@ZteSoftCommentAnnotationParam(name="专项预存",type="String",isNecessary="N",desc="advanceSpe：专项预存")
	private String advanceSpe;
	@ZteSoftCommentAnnotationParam(name="返还时长",type="String",isNecessary="N",desc="numThawTim：返还时长")
	private String numThawTim;
	@ZteSoftCommentAnnotationParam(name="分月返还金额",type="String",isNecessary="N",desc="numThawPro：分月返还金额")
	private String numThawPro;
	@ZteSoftCommentAnnotationParam(name="号码等级",type="String",isNecessary="Y",desc="classId：号码等级：1：一级靓号2：二级靓号3：三级靓号4：四级靓号5：五级靓号6：六级靓号9：普通号码")
	private Integer classId;
	@ZteSoftCommentAnnotationParam(name="考核期最低消费",type="String",isNecessary="N",desc="lowCostChe：考核期最低消费")
	private String lowCostChe;
	@ZteSoftCommentAnnotationParam(name="考核时长",type="String",isNecessary="N",desc="timeDurChe：考核时长")
	private String timeDurChe;
	@ZteSoftCommentAnnotationParam(name="考核期是否过户",type="String",isNecessary="N",desc="changeTagChe： 0:允许1：不允许")
	private Integer changeTagChe;
	@ZteSoftCommentAnnotationParam(name="考核期是否销户",type="String",isNecessary="N",desc="cancelTagChe：0:允许1：不允许")
	private Integer cancelTagChe;
	@ZteSoftCommentAnnotationParam(name="考核期违约金月份",type="String",isNecessary="N",desc="bremonChe：考核期违约金月份")
	private String bremonChe;
	@ZteSoftCommentAnnotationParam(name="协议期最低消费",type="String",isNecessary="N",desc="lowCostPro：协议期最低消费")
	private String lowCostPro;
	@ZteSoftCommentAnnotationParam(name="协议时长",type="String",isNecessary="N",desc="timeDurPro：协议时长 当值为00000时表示无期限")
	private String timeDurPro;
	@ZteSoftCommentAnnotationParam(name="协议期是否过户",type="String",isNecessary="N",desc="changeTagPro：协议期是否过户0:允许 1：不允许")
	private Integer changeTagPro;
	@ZteSoftCommentAnnotationParam(name="协议期是否销户",type="String",isNecessary="N",desc="cancelTagPro：协议期是否销户0:允许 1：不允许")
	private Integer cancelTagPro;
	@ZteSoftCommentAnnotationParam(name="协议期违约金月份",type="String",isNecessary="N",desc="broMonPro：协议期违约金月份")
	private String broMonPro;
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
	public String getBroMonPro() {
		return broMonPro;
	}
	public void setBroMonPro(String broMonPro) {
		this.broMonPro = broMonPro;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public Integer getChangeTagChe() {
		return changeTagChe;
	}
	public void setChangeTagChe(Integer changeTagChe) {
		this.changeTagChe = changeTagChe;
	}
	public Integer getCancelTagChe() {
		return cancelTagChe;
	}
	public void setCancelTagChe(Integer cancelTagChe) {
		this.cancelTagChe = cancelTagChe;
	}
	public Integer getChangeTagPro() {
		return changeTagPro;
	}
	public void setChangeTagPro(Integer changeTagPro) {
		this.changeTagPro = changeTagPro;
	}
	public Integer getCancelTagPro() {
		return cancelTagPro;
	}
	public void setCancelTagPro(Integer cancelTagPro) {
		this.cancelTagPro = cancelTagPro;
	}
	
	
}
