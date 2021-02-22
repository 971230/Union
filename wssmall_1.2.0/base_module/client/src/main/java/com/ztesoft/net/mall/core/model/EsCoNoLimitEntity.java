package com.ztesoft.net.mall.core.model;

import com.ztesoft.net.framework.database.NotDbField;

public class EsCoNoLimitEntity {
	private String org_id;  //销售组织标识
	private String org_name;//组织名
	private String region_id;//地市编码
	private String max_ordinary;//普通号上限
	private String min_ordinary;//普通号下限
	private String max_lucky;//靓号上限
	private String min_lucky;//靓号下限
	private String warning_ordinary;//普通号预警阀值
	private String warning_phone;//预警号码
    private Float replenish_factor;//补货系数
    private String source_from;//数据来源
    private String warning_lucky;
    
    private String local_name;
  
	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

	public String getMax_ordinary() {
		return max_ordinary;
	}

	public void setMax_ordinary(String max_ordinary) {
		this.max_ordinary = max_ordinary;
	}

	public String getMin_ordinary() {
		return min_ordinary;
	}

	public void setMin_ordinary(String min_ordinary) {
		this.min_ordinary = min_ordinary;
	}

	public String getMax_lucky() {
		return max_lucky;
	}

	public void setMax_lucky(String max_lucky) {
		this.max_lucky = max_lucky;
	}

	public String getMin_lucky() {
		return min_lucky;
	}

	public void setMin_lucky(String min_lucky) {
		this.min_lucky = min_lucky;
	}

	public String getWarning_ordinary() {
		return warning_ordinary;
	}

	public void setWarning_ordinary(String warning_ordinary) {
		this.warning_ordinary = warning_ordinary;
	}

	public String getWarning_phone() {
		return warning_phone;
	}

	public void setWarning_phone(String warning_phone) {
		this.warning_phone = warning_phone;
	}

	public Float getReplenish_factor() {
		return replenish_factor;
	}

	public void setReplenish_factor(Float replenish_factor) {
		this.replenish_factor = replenish_factor;
	}

	public String getWarning_lucky() {
		return warning_lucky;
	}

	public void setWarning_lucky(String warning_lucky) {
		this.warning_lucky = warning_lucky;
	}

	@NotDbField
	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	@NotDbField
	public String getLocal_name() {
		return local_name;
	}

	public void setLocal_name(String local_name) {
		this.local_name = local_name;
	}

	
    
}
