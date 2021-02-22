package params.adminuser.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class UserPasswordUpdateReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="Y",desc="用户登录名称")
    private String username;//用户名
    private String oldPassword;//旧密码
    private String newPassword;//新密码
    private boolean is_loser;//是否找回密码  true是找回密码  false是修改密码
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(StringUtils.isEmpty(username))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户名不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.service.sysService.updateUserPassword";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public boolean getIs_loser() {
		return is_loser;
	}

	public void setIs_loser(boolean is_loser) {
		this.is_loser = is_loser;
	}

}
