package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;

public class CrawlerCodeReq extends ZteRequest{
	
    @ZteSoftCommentAnnotationParam(name="用户名",type="String",isNecessary="Y",desc="用户名")
	private String userName;
    @ZteSoftCommentAnnotationParam(name="密码",type="String",isNecessary="Y",desc="密码")
	private String passWord;
    @ZteSoftCommentAnnotationParam(name="验证码",type="String",isNecessary="Y",desc="验证码")
	private String validateCode;
    @ZteSoftCommentAnnotationParam(name="验证码发送方式",type="String",isNecessary="Y",desc="验证码发送方式")
    private String sendType;
	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.iservice.impl.ZteCrawlerOpenService.doSendValidate";
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

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
}
