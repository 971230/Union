package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 *
 */
public class StaffList implements Serializable {

	private static final long serialVersionUID = 1L;

	@ZteSoftCommentAnnotationParam(name = "staffCode", type = "String", isNecessary = "N", desc = "操作员工号，员工绑定的bss工号，可能为空")
	private String staffCode;

	@ZteSoftCommentAnnotationParam(name = "staffName", type = "String", isNecessary = "Y", desc = "姓名，员工一体化显示的姓名")
	private String staffName;

	@ZteSoftCommentAnnotationParam(name = "phoneNum", type = "String", isNecessary = "Y", desc = "手机号码，员工手机号码")
	private String phoneNum;

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
