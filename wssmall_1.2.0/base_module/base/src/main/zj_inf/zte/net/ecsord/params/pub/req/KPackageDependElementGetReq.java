package zte.net.ecsord.params.pub.req;

import java.util.List;

import params.ZteRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class KPackageDependElementGetReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="可选包信息",type="List",isNecessary="Y",desc="packageBusiRequests：可选包信息")
	List<AttrPackageBusiRequest> packageBusiRequests;
	
	@ZteSoftCommentAnnotationParam(name="首月资费方式",type="String",isNecessary="Y",desc="first_payment：首月资费方式")
	private String first_payment;
	
	@ZteSoftCommentAnnotationParam(name="产品编码",type="String",isNecessary="Y",desc="product_id：产品编码")
	private String product_id;

	public List<AttrPackageBusiRequest> getPackageBusiRequests() {
		return packageBusiRequests;
	}

	public void setPackageBusiRequests(
			List<AttrPackageBusiRequest> packageBusiRequests) {
		this.packageBusiRequests = packageBusiRequests;
	}

	public String getFirst_payment() {
		return first_payment;
	}

	public void setFirst_payment(String first_payment) {
		this.first_payment = first_payment;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	@Override
	public void check() throws ApiRuleException {
	}

	@Override
	public String getApiMethodName() {
		return "com.dcPublicService.kPackageDependElement.list";
	}

}
