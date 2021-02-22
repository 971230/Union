package com.ztesoft.remote.params.notice.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;
/**
 * 
 * @author wui
 * 广告请求实体
 *
 */
@SuppressWarnings("unchecked")
public class NoticeResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="分页器",type="Page",isNecessary="N",desc="分页器：webPage.getResult()获取文章数据")
	Page webPage ;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}

}
