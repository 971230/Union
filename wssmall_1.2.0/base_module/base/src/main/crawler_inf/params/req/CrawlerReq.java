package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

public class CrawlerReq extends ZteRequest{
	
    @ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="Y",desc="用户名")
	private String userName;
    @ZteSoftCommentAnnotationParam(name="密码",type="String",isNecessary="Y",desc="密码")
	private String passWord;
    @ZteSoftCommentAnnotationParam(name="验证码",type="String",isNecessary="Y",desc="验证码")
	private String validateCode;
    @ZteSoftCommentAnnotationParam(name="cookie码",type="String",isNecessary="N",desc="cookie")
	private String cookie;
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.zbClientLogin";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}
