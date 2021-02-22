package com.ztesoft.net.ecsord.params.ecaop.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class PreCommitVO implements Serializable{
	@ZteSoftCommentAnnotationParam(name="号卡预提交返回ID",type="String",isNecessary="Y",desc="serial_num：号卡预提交返回ID")
	private String serial_num;

	public String getSerial_num() {
		return serial_num;
	}

	public void setSerial_num(String serial_num) {
		this.serial_num = serial_num;
	}
	
}
