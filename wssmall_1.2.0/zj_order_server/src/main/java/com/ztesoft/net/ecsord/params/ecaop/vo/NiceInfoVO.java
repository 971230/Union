package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NiceInfoVO implements Serializable{ 
	@ZteSoftCommentAnnotationParam(name="靓号活动ID",type="String",isNecessary="Y",desc="lhscheme_id：BSS省内靓号")
	private String lhscheme_id;
	@ZteSoftCommentAnnotationParam(name="靓号预存金额",type="String",isNecessary="Y",desc="pre_fee：BSS省内靓号")
	private String pre_fee;
	@ZteSoftCommentAnnotationParam(name="预存话费金额",type="String",isNecessary="Y",desc="advancePay：CB总部靓号")
	private String advancePay;
	@ZteSoftCommentAnnotationParam(name="号码等级",type="String",isNecessary="Y",desc="classId：CB总部靓号")
	private String classId;
	@ZteSoftCommentAnnotationParam(name="协议期最低消费",type="String",isNecessary="Y",desc="lowCostPro：CB总部靓号")
	private String lowCostPro;
	@ZteSoftCommentAnnotationParam(name="协议时长",type="String",isNecessary="Y",desc="timeDurPro：CB总部靓号")
	private String timeDurPro;
	
	public NiceInfoVO() {
		
	}
	
	public NiceInfoVO(Builder builder) {
		this.lhscheme_id = builder.lhscheme_id;
		this.pre_fee = builder.pre_fee;
		this.advancePay = builder.advancePay;
		this.classId = builder.classId;
		this.lowCostPro = builder.lowCostPro;
		this.timeDurPro = builder.timeDurPro;
	}
	
	public static class Builder {
		private String lhscheme_id;
		private String pre_fee;
		private String advancePay;
		private String classId;
		private String lowCostPro;
		private String timeDurPro;
		
		public Builder preFee(String pre_fee) {
			this.pre_fee = pre_fee;
			return this ;
		}
		public Builder advancePay(String advancePay) {
			this.advancePay = advancePay;
			return this ;
		}
		public Builder classId(String classId) {
			this.classId = classId;
			return this ;
		}
		public Builder lowCostPro(String lowCostPro) {
			this.lowCostPro = lowCostPro;
			return this ;
		}
		public Builder timeDurPro(String timeDurPro) {
			this.timeDurPro = timeDurPro;
			return this ;
		}
		public Builder lhschemeId(String lhscheme_id) {
			this.lhscheme_id = lhscheme_id;
			return this ;
		}
		
		public NiceInfoVO build() {
			return new NiceInfoVO(this);
		}
	}
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
	/**
	* get the value from Map
	*/
	public void fromMap(Map map) {
		setLhscheme_id((map.get("lhscheme_id") == null?"":(map.get("lhscheme_id").toString())));
		setPre_fee((map.get("pre_fee") == null?"":(map.get("pre_fee").toString())));
		setAdvancePay((map.get("advancePay") == null?"":(map.get("advancePay").toString())));
		setClassId((map.get("classId") == null?"":(map.get("classId").toString())));
		setLowCostPro((map.get("lowCostPro") == null?"":(map.get("lowCostPro").toString())));
		setTimeDurPro((map.get("timeDurPro") == null?"":(map.get("timeDurPro").toString())));
	}
	/**
	* set the value from Map
	*/
	public Map toMap() {
		Map map = new HashMap();
		map.put("lhscheme_id",getLhscheme_id());
		map.put("pre_fee",getPre_fee());
		map.put("advancePay",getAdvancePay());
		map.put("classId",getClassId());
		map.put("lowCostPro",getLowCostPro());
		map.put("timeDurPro",getTimeDurPro());
		return map;
	}
}
