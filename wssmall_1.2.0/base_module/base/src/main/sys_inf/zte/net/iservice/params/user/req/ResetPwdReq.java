package zte.net.iservice.params.user.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import zte.net.iservice.params.user.resp.ResetPwdResp;
import com.ztesoft.remote.utils.SysConst;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class ResetPwdReq extends ZteRequest<ResetPwdResp>{
	@ZteSoftCommentAnnotationParam(name="产品号码",type="String",isNecessary="Y",desc="产品号码")
	private String acc_nbr;
	
	@ZteSoftCommentAnnotationParam(name="产品类型",type="String",isNecessary="Y",desc="产品类型")
	private String account_type;
	
	@ZteSoftCommentAnnotationParam(name="原密码",type="String",isNecessary="Y",desc="原密码")
	private String old_password;
	
	@ZteSoftCommentAnnotationParam(name="新密码",type="String",isNecessary="Y",desc="新密码")
	private String new_password;
	
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

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
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

	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(用户登录)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(acc_nbr==null || "".equals(acc_nbr))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "推送号码不允许为空"));
		if(account_type==null || "".equals(account_type))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "产品类型不允许为空"));
		if(old_password==null || "".equals(old_password))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "原密码不允许为空"));
		if(new_password==null || "".equals(new_password))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "新密码不允许为空"));
		if(product_id==null || "".equals(product_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "产品id不允许为空"));
		if(service_code==null || "".equals(service_code))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "规则编码不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return SysConst.API_PREFIX + "resetPwd";
	}

}
