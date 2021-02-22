package params.suppler.resp;

import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;

/**
 * 查询供货商列表返回
 * @author hu.yi
 * @date 2013.12.31
 */
public class SuppliersListResp extends ZteResponse{

	private Page webPage;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}
}
