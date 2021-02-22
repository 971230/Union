package zte.net.iservice.params.req;

import params.ZteRequest;
import zte.net.iservice.params.resp.OpenAccountResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;



/**
 * 新增管理员实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class OpenAccountReq extends ZteRequest<OpenAccountResp>{

	@ZteSoftCommentAnnotationParam(name="产品id",type="String",isNecessary="Y",desc="产品id(用户登录)")
	private String product_id;
    
    @ZteSoftCommentAnnotationParam(name="规则编码",type="String",isNecessary="Y",desc="规则编码")
	private String service_code;
    
    @ZteSoftCommentAnnotationParam(name="客户订单", type="String",isNecessary="Y",desc="返回客户订单流水号")
    private String order_id;
    
    @ZteSoftCommentAnnotationParam(name="错误编码",type="String",isNecessary="Y",desc="返回")
    private String err_code;
    @ZteSoftCommentAnnotationParam(name="错误信息",type="String",isNecessary="Y",desc="返回")
    private String err_msg;
    
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
	
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	
	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	
	public String getErr_msg() {
		return err_msg;
	}

	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		
		return "zte.net.iservice.openAccount";
	}
}
