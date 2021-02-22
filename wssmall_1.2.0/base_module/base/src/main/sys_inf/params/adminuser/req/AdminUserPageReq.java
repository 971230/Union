package params.adminuser.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import params.adminuser.resp.AdminUserPageResp;

public class AdminUserPageReq extends ZteRequest<AdminUserPageResp>{
	@ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="Y",desc="username：用户名， 不能为空。")
	private String username;
	@ZteSoftCommentAnnotationParam(name="页数",type="String",isNecessary="Y",desc="pageNo：页数， 不能为空。")
	private String pageNo;
	@ZteSoftCommentAnnotationParam(name="分页大小",type="String",isNecessary="Y",desc="pageSize：分页大小， 不能为空。")
	private String pageZise;
	
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getPageZise() {
		return pageZise;
	}

	public void setPageZise(String pageZise) {
		this.pageZise = pageZise;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(pageNo) || StringUtils.isEmpty(pageZise)){
			throw new ApiRuleException("-1","页面信息不能为空");
		}
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.sysService.admin.qryUser";
	}

}
