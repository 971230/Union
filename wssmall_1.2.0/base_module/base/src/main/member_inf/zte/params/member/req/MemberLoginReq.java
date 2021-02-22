package zte.params.member.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import params.ZteRequest;
import zte.params.member.resp.MemberLoginResp;

public class MemberLoginReq extends ZteRequest<MemberLoginResp> {

	@ZteSoftCommentAnnotationParam(name="使用会员账号登录",type="String",isNecessary="Y",desc="使用会员账号登录(如果账号为空就使用手机号码登录)")
	private String userName;
	@ZteSoftCommentAnnotationParam(name="登录密码",type="String",isNecessary="Y",desc="登录密码")
	private String pwd;
	@ZteSoftCommentAnnotationParam(name="使用手机号码登录",type="String",isNecessary="Y",desc="使用手机号码登录")
	private String mobile;
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		/*if(StringUtil.isEmpty(this.userSessionId))
			CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户session不能为空，请传入http request session值"));*/
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.memberService.member.login";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}