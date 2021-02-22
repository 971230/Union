package zte.net.iservice.params.user.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import zte.net.iservice.params.user.resp.QryCustInfoResp;
import com.ztesoft.remote.utils.SysConst;
import commons.CommonTools;

import consts.ConstsCore;

import params.ZteError;
import params.ZteRequest;

public class QryCustInfoReq extends ZteRequest<QryCustInfoResp> {
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="Y",desc="手机号码")
	private String acc_nbr;
	
	@ZteSoftCommentAnnotationParam(name="客户类别",type="String",isNecessary="Y",desc="1：代理商信息；2：客户信息")
	private String cust_type;

	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(用户登录)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
	
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
	
	public String getAcc_nbr() {
		return acc_nbr;
	}

	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}
	
    @Override
	@NotDbField
	public void check() throws ApiRuleException {
		if(acc_nbr==null || "".equals(acc_nbr))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "推送号码不允许为空"));
		if(cust_type==null || "".equals(cust_type))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "客户类别不允许为空"));
		if(product_id==null || "".equals(product_id))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "产品id不允许为空"));
		if(service_code==null || "".equals(service_code))CommonTools.addError(new ZteError(ConstsCore.ERROR_FAIL, "规则编码不允许为空"));
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return SysConst.API_PREFIX + "qryCustInfo";
	}


}