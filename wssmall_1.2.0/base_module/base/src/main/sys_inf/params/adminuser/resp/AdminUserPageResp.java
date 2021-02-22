package params.adminuser.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.Page;

/**
 * 分页查询返回用户对象
 * @author hu.yi
 * @date 2013.12.24
 */
public class AdminUserPageResp extends ZteResponse{

	@ZteSoftCommentAnnotationParam(name="分页器",type="String",isNecessary="Y",desc="webPage：分页器， 不能为空。")
	private Page webPage;

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}
}
