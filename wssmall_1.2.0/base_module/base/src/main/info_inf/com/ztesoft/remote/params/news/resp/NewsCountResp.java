package com.ztesoft.remote.params.news.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class NewsCountResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="待审核的讯息数量",type="int",isNecessary="N",desc="待审核的讯息数量")
	private int wait_audit_flash;
	
	@ZteSoftCommentAnnotationParam(name="讯息总数量",type="int",isNecessary="N",desc="讯息总数量")
	private int flash_count;

	public int getWait_audit_flash() {
		return wait_audit_flash;
	}

	public void setWait_audit_flash(int wait_audit_flash) {
		this.wait_audit_flash = wait_audit_flash;
	}

	public int getFlash_count() {
		return flash_count;
	}

	public void setFlash_count(int flash_count) {
		this.flash_count = flash_count;
	}

}
