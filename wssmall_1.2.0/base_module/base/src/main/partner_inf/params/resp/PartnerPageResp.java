package params.resp;

import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

/**
 * 查询分销商分页对象出参
 * @author hu.yi
 * @date 2013.12.26
 */
public class PartnerPageResp extends ZteResponse{

	private Page webPage;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}
}
