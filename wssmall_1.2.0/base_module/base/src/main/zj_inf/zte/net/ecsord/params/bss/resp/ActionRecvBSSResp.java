package zte.net.ecsord.params.bss.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;


/**
 * 页面功能调用反调确认接口(BSS社会机TAC码、商品折扣包录入)
 * 返回对象
 */
public class ActionRecvBSSResp extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name="验证结果",type="String",isNecessary="Y",desc="验证结果[0:验证失败、1:验证成功]")
	private String RSP_CODE;

	@ZteSoftCommentAnnotationParam(name="验证结果描述",type="String",isNecessary="Y",desc="验证结果描述")
	private String RSP_DESC;

	public String getRSP_CODE() {
		return RSP_CODE;
	}

	public void setRSP_CODE(String rSP_CODE) {
		RSP_CODE = rSP_CODE;
	}

	public String getRSP_DESC() {
		return RSP_DESC;
	}

	public void setRSP_DESC(String rSP_DESC) {
		RSP_DESC = rSP_DESC;
	}
	
	

}
