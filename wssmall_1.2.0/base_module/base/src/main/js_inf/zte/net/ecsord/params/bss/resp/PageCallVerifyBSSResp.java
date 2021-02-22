package zte.net.ecsord.params.bss.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 页面功能调用反调确认接口
 * 返回对象
 */
public class PageCallVerifyBSSResp extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name="验证结果",type="String",isNecessary="Y",desc="验证结果[0:验证失败、1:验证成功]")
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
