package zte.net.iservice.params.user.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import zte.net.iservice.params.user.resp.AuthPwdResp;
import com.ztesoft.remote.utils.SysConst;

import params.ZteRequest;

public class AuthPwdReq extends ZteRequest<AuthPwdResp> {
	@ZteSoftCommentAnnotationParam(name="产品号码",type="String",isNecessary="Y",desc="产品号码")
	private String acc_nbr;
	
	@ZteSoftCommentAnnotationParam(name="产品类型",type="String",isNecessary="Y",desc="产品类型")
	private String account_type;
	
	@ZteSoftCommentAnnotationParam(name="密码",type="String",isNecessary="Y",desc="密码")
	private String pass_word;
	
	@ZteSoftCommentAnnotationParam(name="密码类型",type="String",isNecessary="Y",desc="密码类型")
	private String pass_type;
	
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(用户登录)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getPass_word() {
		return pass_word;
	}

	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

	public String getPass_type() {
		return pass_type;
	}

	public void setPass_type(String pass_type) {
		this.pass_type = pass_type;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
        return SysConst.API_PREFIX + "authPwd";
	}

}
