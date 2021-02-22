package params.adminuser.req;

import params.ZteRequest;
import params.adminuser.resp.AdminUserIdResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.eop.resource.model.AdminUser;



/**
 * 新增管理员实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminUserAddReq extends ZteRequest<AdminUserIdResp>{

	private String userid;
	private String siteid;
	private AdminUser adminUser;
	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSiteid() {
		return siteid;
	}
	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}
	public AdminUser getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}
	@Override
	public void check() throws ApiRuleException {
		if(null == adminUser){
			throw new ApiRuleException("-1","adminuser不能为空");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.addUser";
	}
}
