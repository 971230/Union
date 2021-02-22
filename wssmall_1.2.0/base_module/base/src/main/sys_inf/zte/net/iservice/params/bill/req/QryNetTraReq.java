package zte.net.iservice.params.bill.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;
import zte.net.iservice.params.bill.resp.QryNetTraResp;
import com.ztesoft.remote.utils.SysConst;

import params.ZteRequest;

public class QryNetTraReq extends ZteRequest<QryNetTraResp>{
	@ZteSoftCommentAnnotationParam(name="手机号码",type="String",isNecessary="Y",desc="手机号码")
	private String acc_nbr;

	@ZteSoftCommentAnnotationParam(name="账期",type="String",isNecessary="Y",desc="账期")
	private String billing_cycle;
	
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

	public String getBilling_cycle() {
		return billing_cycle;
	}

	public void setBilling_cycle(String billing_cycle) {
		this.billing_cycle = billing_cycle;
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
		return SysConst.API_PREFIX + "netTra";
	}

}
