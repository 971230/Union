package com.ztesoft.net.server.jsonserver.internetpojo;

public class InternetBusiNiceInfo {

	private String lhscheme_id;//靓号活动ID
	
	private String pre_fee;//靓号预存金额 BSS靓号必传
	
	private String advancePay;//预存话费金额，单位：厘
	
	private String classId;//号码等级 1：1级靓号 2：2级靓号 以此类推 9：普通号码
	
	private String lowCostPro;//协议期最低消费 单位厘
	
	private String timeDurPro; //协议时长 当值为00000时表示无期限

	public String getLhscheme_id() {
		return lhscheme_id;
	}

	public void setLhscheme_id(String lhscheme_id) {
		this.lhscheme_id = lhscheme_id;
	}

	public String getPre_fee() {
		return pre_fee;
	}

	public void setPre_fee(String pre_fee) {
		this.pre_fee = pre_fee;
	}

	public String getAdvancePay() {
		return advancePay;
	}

	public void setAdvancePay(String advancePay) {
		this.advancePay = advancePay;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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
	
	

}
