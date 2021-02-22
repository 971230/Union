package params.adminuser.req;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.form.util.StringUtil;

/**
 * 修改用户信息参数实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class AdminUserUpdateReq extends ZteRequest<ZteResponse>{

	/**
	 * 第一种方案
	 */
	private String oldPassword;
	private String newpassword;
	private String userid;
	
	/**
	 * 第二种方案
	 */
//	private Map<String, String> bossPwdMap;
	
	
//	private int status;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
	@Override
	public void check() throws ApiRuleException {
		if(StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newpassword) || StringUtil.isEmpty(userid)){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "adminUserServ.updatePassword";
	}
}
