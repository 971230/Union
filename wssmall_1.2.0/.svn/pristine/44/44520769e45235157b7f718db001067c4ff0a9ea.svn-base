package params.adminuser.req;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class UserWdLoginReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="Y",desc="用户登录名称")
    private String username;
	@ZteSoftCommentAnnotationParam(name="密码",type="String",isNecessary="Y",desc="用户登录密码")
    private String password;
    
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(username))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户登录名称不允许为空"));
		if(StringUtils.isEmpty(password))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户登录密码不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.service.sysService.getUserWd";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
