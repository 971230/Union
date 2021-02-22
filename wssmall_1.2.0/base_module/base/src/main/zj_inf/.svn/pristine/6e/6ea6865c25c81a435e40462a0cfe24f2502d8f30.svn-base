package zte.net.ecsord.params.ecaop.req;

import params.ZteRequest;
import zte.net.ecsord.params.ecaop.resp.EmpIdEssIDResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EmpInfoByEssIDReq extends ZteRequest<EmpIdEssIDResp> {

	@ZteSoftCommentAnnotationParam(name = "工号", type = "String", isNecessary = "Y", desc = "operator_id:工号")
	private String operator_id;
	@ZteSoftCommentAnnotationParam(name = "订单编号", type = "String", isNecessary = "Y", desc = "order_id:订单编号")
	private String order_id;
	@ZteSoftCommentAnnotationParam(name = "operId类型", type = "String", isNecessary = "Y", desc = "order_id:operId类型，order 表示订单工号，cb表示ess/cb开户工号")
	private String operIdType;

	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	public String getOperIdType() {
		return operIdType;
	}

	public void setOperIdType(String operIdType) {
		this.operIdType = operIdType;
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.ecsord.params.ecaop.req.empoperinfoessidreq";
	}

}
