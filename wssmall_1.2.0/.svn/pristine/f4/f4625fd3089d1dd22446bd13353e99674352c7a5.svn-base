package com.zte.cbss.autoprocess.model.data;

import com.zte.cbss.autoprocess.model.ParameterData;

public class PageData implements Cloneable{

	private String listener = "init";
	private String needNotify = "true";
	private String staffId = "";
	private String departId = "";
	private String eparchyCode = "";
	private String provinceId = "";
	private String LOGIN_CHECK_CODE = "";
	private String LOGIN_RANDOM_CODE = "";
	
	private String referer = "https://gd.cbss.10010.com/essframe";
	
	public PageData(){}
	
	public PageData(ParameterData param){
		this.staffId = param.getStaffId();
		this.eparchyCode = param.getEparchyCode();
		this.departId = param.getDepartId();
		this.provinceId = param.getProvinceId();
		this.LOGIN_CHECK_CODE = param.getLOGIN_CHECK_CODE();
		this.LOGIN_RANDOM_CODE = param.getLOGIN_RANDOM_CODE();
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}

	public String getNeedNotify() {
		return needNotify;
	}

	public void setNeedNotify(String needNotify) {
		this.needNotify = needNotify;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getEparchyCode() {
		return eparchyCode;
	}

	public void setEparchyCode(String eparchyCode) {
		this.eparchyCode = eparchyCode;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getLOGIN_CHECK_CODE() {
		return LOGIN_CHECK_CODE;
	}

	public void setLOGIN_CHECK_CODE(String lOGIN_CHECK_CODE) {
		LOGIN_CHECK_CODE = lOGIN_CHECK_CODE;
	}

	public String getLOGIN_RANDOM_CODE() {
		return LOGIN_RANDOM_CODE;
	}

	public void setLOGIN_RANDOM_CODE(String lOGIN_RANDOM_CODE) {
		LOGIN_RANDOM_CODE = lOGIN_RANDOM_CODE;
	}
}
