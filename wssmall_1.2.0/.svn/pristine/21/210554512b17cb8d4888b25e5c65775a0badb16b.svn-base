package params.adminuser.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;

public class AdminUserReq extends ZteRequest<ZteResponse>{
	private AdminUser adminUser;
	private String user_id;
	private String user_name; //?
	private String refCode;
	
	
	
	public String getRefCode() {
		return refCode;
	}

	public void setRefCode(String refCode) {
		this.refCode = refCode;
	}

	public AdminUser getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUser adminUser) {
		this.adminUser = adminUser;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	@Override
	public void check() throws ApiRuleException {
		if(null == adminUser && StringUtil.isEmpty(user_id) && StringUtil.isEmpty(user_name)
				&& StringUtil.isEmpty(refCode)){
			throw new ApiRuleException("-1","缺失参数");
		}
		
	}

	@Override
	public String getApiMethodName() {
		if(!StringUtil.isEmpty(user_id) && (StringUtil.isEmpty(user_name) && StringUtil.isEmpty(refCode) 
				&& null == adminUser)){
			return "adminUserServ.getAdminUserById";
		}else if(!StringUtil.isEmpty(user_name) && (StringUtil.isEmpty(user_id) && StringUtil.isEmpty(refCode) 
				&& null == adminUser)){
			return "adminUserServ.getAdminUserByUserName";
		}else if(!StringUtil.isEmpty(refCode) && (StringUtil.isEmpty(user_name) && StringUtil.isEmpty(user_id) 
				&& null == adminUser)){
			return "adminUserServ.getAdminuserByRole";
		}else if(adminUser != null && (StringUtil.isEmpty(user_name) && StringUtil.isEmpty(refCode) 
				&& StringUtil.isEmpty(user_id))){
			return "adminUserServ.createAdminUser";
		}else{
			return null;
		}
	}
}
