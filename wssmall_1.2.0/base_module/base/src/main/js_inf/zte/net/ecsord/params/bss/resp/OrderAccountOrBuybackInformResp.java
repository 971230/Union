package zte.net.ecsord.params.bss.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

/**
 *  商城收费、退款成功触发接口
 *	返回对象
 */
public class OrderAccountOrBuybackInformResp extends ZteBusiResponse {
	
	private static final long serialVersionUID = -669162485347929414L;
	
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="验证结果[以0000为结束,成功]")
	private String xCheckInfo;

	public String getxCheckInfo() {
		return xCheckInfo;
	}

	public void setxCheckInfo(String xCheckInfo) {
		this.xCheckInfo = xCheckInfo;
	}
	

}
