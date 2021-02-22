package zte.params.order.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;
import params.ZteResponse;

public class OrderPageListResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="分页器",type="String",isNecessary="N",desc="分页器：webPage.getResult()获取订单数据")
	private Page webPage;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}
	
}
