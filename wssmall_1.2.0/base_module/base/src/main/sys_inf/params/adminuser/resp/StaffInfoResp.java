package params.adminuser.resp;

import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;



/**
 * 客户信息返回实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class StaffInfoResp extends ZteResponse{

	private Page webPage;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}
}
