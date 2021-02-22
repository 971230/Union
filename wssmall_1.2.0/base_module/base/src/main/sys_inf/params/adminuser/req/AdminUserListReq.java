package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminUserPageResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

public class AdminUserListReq extends ZteRequest<AdminUserPageResp>{

	private int usertype;
	private String realname;
	private String name;
	private String userid;
	private String state;
	public String pageNo;
	public String pageSize;
	
	private String lan_id;
	
	
	public int getUsertype() {
		return usertype;
	}
	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
		if(StringUtil.isEmpty(pageNo)){
			throw new ApiRuleException("-1","pageNo不能为空");
		}
		if(StringUtil.isEmpty(pageSize)){
			throw new ApiRuleException("-1","pageSize不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.list";
	}
	
	public String getLan_id() {
		return lan_id;
	}
	
	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}
}
