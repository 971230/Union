package com.ztesoft.net.mall.core.model;

public class NumeroUmbral {

	/**
	 * 销售组织标识
	 */
	private String sale_org_id;

	/**
	 * 地市编码
	 */
	private String area_code;

	/**
	 * 号码上限
	 */
	private String max_no;

	/**
	 * 号码下限
	 */
	private String min_no;

	/**
	 * 普通号上限
	 */
	private String max_ordinary;

	/**
	 * 普通号下限
	 */
	private String min_ordinary;

	/**
	 * 靓号上限
	 */
	private String max_lucky;

	/**
	 * 靓号下限
	 */
	private String min_lucky;

	/**
	 * 普通号可销数
	 */
	private String usable_ordinary;

	/**
	 * 靓号可销数
	 */
	private String usable_lucky;

	/**
	 * 补货系数
	 */
	private String replenish_factor;

	/**
	 * 普通号预警阀值
	 */
	private String warning_ordinary;

	/**
	 * 靓号预警阀值
	 */
	private String warning_lucky;

	/**
	 * 预警号码
	 */
	private String warning_phone;

	/**
	 * 数据来源
	 */
	private String source_from;

	public String getSale_org_id() {
		return sale_org_id;
	}

	public void setSale_org_id(String sale_org_id) {
		this.sale_org_id = sale_org_id;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getMax_no() {
		return max_no;
	}

	public void setMax_no(String max_no) {
		this.max_no = max_no;
	}

	public String getMin_no() {
		return min_no;
	}

	public void setMin_no(String min_no) {
		this.min_no = min_no;
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

	public String getUsable_ordinary() {
		return usable_ordinary;
	}

	public void setUsable_ordinary(String usable_ordinary) {
		this.usable_ordinary = usable_ordinary;
	}

	public String getUsable_lucky() {
		return usable_lucky;
	}

	public void setUsable_lucky(String usable_lucky) {
		this.usable_lucky = usable_lucky;
	}

	public String getReplenish_factor() {
		return replenish_factor;
	}

	public void setReplenish_factor(String replenish_factor) {
		this.replenish_factor = replenish_factor;
	}

	public String getWarning_ordinary() {
		return warning_ordinary;
	}

	public void setWarning_ordinary(String warning_ordinary) {
		this.warning_ordinary = warning_ordinary;
	}

	public String getWarning_lucky() {
		return warning_lucky;
	}

	public void setWarning_lucky(String warning_lucky) {
		this.warning_lucky = warning_lucky;
	}

	public String getWarning_phone() {
		return warning_phone;
	}

	public void setWarning_phone(String warning_phone) {
		this.warning_phone = warning_phone;
	}

	public String getSource_from() {
		return source_from;
	}

	public void setSource_from(String source_from) {
		this.source_from = source_from;
	}

}
