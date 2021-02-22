package params.adminuser.resp;

import com.ztesoft.net.framework.database.Page;

import params.ZteResponse;


/**
 * 角色列表返回实体
 * @author hu.yi
 * @date 2013.12.24
 */
public class RolePageResp extends ZteResponse{
	private Page webPage;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}

}
