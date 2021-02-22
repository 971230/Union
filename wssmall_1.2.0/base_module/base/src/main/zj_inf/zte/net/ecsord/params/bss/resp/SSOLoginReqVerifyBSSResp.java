package zte.net.ecsord.params.bss.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 
 * BSS页面嵌入登陆反调确认接口
 * 返回对象
 */
public class SSOLoginReqVerifyBSSResp extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name="验证结果",type="String",isNecessary="Y",desc="验证结果")
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
