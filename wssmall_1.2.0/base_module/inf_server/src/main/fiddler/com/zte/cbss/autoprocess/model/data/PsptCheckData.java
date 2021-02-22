package com.zte.cbss.autoprocess.model.data;

import com.zte.cbss.autoprocess.model.ParameterData;

/**
 * 身份证检查数据
 * @author 张浩
 * @version 1.0.0
 */
public class PsptCheckData {

	private String psptId;			//身份证ID
	private String customName;		//客户姓名
	private String globalPageName = "popupdialog.PersonCardReaderSX";
	
	private String random = "11452112453140";
	private String staffId = "";
	private String departId = "";
	private String subSysCode = "custserv";
	private String eparchyCode = "";
	
	private String checkType = "2";		//检查类型,需要用户选择，默认身份证检查
	
	private String referer = "https://gd.cbss.10010.com/essframe";
	
	public PsptCheckData(){}
	
	public PsptCheckData(ParameterData data){
		this.staffId = data.getStaffId();
		this.departId = data.getDepartId();
		this.eparchyCode = data.getEparchyCode();
	}
	
	public String getPsptId() {
		return psptId;
	}
	public void setPsptId(String psptId) {
		this.psptId = psptId;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getGlobalPageName() {
		return globalPageName;
	}
	public void setGlobalPageName(String globalPageName) {
		this.globalPageName = globalPageName;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
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

	public String getSubSysCode() {
		return subSysCode;
	}

	public void setSubSysCode(String subSysCode) {
		this.subSysCode = subSysCode;
	}

	public String getEparchyCode() {
		return eparchyCode;
	}

	public void setEparchyCode(String eparchyCode) {
		this.eparchyCode = eparchyCode;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
}
