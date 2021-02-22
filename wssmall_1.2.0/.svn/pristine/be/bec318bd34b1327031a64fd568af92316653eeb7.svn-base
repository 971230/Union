package zte.net.iservice.params.user.req;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import params.ZteError;
import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import zte.net.iservice.params.user.resp.UserLoginResp;
import com.ztesoft.remote.utils.SysConst;
import commons.CommonTools;

import consts.ConstsCore;

/**
 * 用户登录入参
 * @author xuefeng
 */
public class UserLoginReq extends ZteRequest<UserLoginResp> {
    
	@ZteSoftCommentAnnotationParam(name="名称",type="String",isNecessary="Y",desc="用户登录名称")
    private String userName;
	
	@ZteSoftCommentAnnotationParam(name="密码",type="String",isNecessary="Y",desc="用户登录密码")
    private String uerPwd;
	
	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(用户登录)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
    private List<Map> paramsl = new ArrayList<Map>(); //product_id,uname,name,

    public List<Map> getParamsl() {
		return paramsl;
	}

	public void setParamsl(List<Map> paramsl) {
		this.paramsl = paramsl;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUerPwd() {
		return uerPwd;
	}

	public void setUerPwd(String uerPwd) {
		this.uerPwd = uerPwd;
	}

	@Override
    public void check() throws ApiRuleException {
		if(userName==null || "".equals(userName))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户登录名称不允许为空"));
		if(uerPwd==null || "".equals(uerPwd))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "用户登录密码不允许为空"));
		if(product_id==null || "".equals(product_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "产品id不允许为空"));
		if(service_code==null || "".equals(service_code))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "规则编码不允许为空"));
    }

    @Override
    public String getApiMethodName() {
        return SysConst.API_PREFIX + "userLogin";
    }
}
