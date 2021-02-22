package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.OrgInfoResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.util.StringUtil;


/**
 * 组织信息实体
 * @author hu.yi
 * @date 2013.12.23 
 */
public class OrgInfoReq extends ZteRequest<OrgInfoResp>{

	private String org_id;
	private String state;
	private String realname;
	private String username;
	private String pageNo;
	private String pageSize;
	
	
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
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
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(org_id) || StringUtil.isEmpty(pageNo) || StringUtil.isEmpty(pageSize)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.orgUserList";
	}
}
