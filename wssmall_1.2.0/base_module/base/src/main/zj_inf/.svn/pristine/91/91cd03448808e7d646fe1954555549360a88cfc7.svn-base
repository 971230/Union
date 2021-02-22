package zte.net.ecsord.params.bss.req;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class NumberResourceQueryZjBssRequest extends ZteRequest {

	private static final long serialVersionUID = 1L;
	@ZteSoftCommentAnnotationParam(name = "订单号", type = "String", isNecessary = "Y", desc = "订单系统内部订单")
    private String notNeedReqStrOrderId;
	
	@ZteSoftCommentAnnotationParam(name = "号码地市", type = "String", isNecessary = "Y", desc = "号码地市")
	private String region_id;

	@ZteSoftCommentAnnotationParam(name = "号码关键字", type = "String", isNecessary = "Y", desc = "01：移网随机 02：移网关键字")
	private String query_key;

	@ZteSoftCommentAnnotationParam(name = "查询条件", type = "String", isNecessary = "Y", desc = "当query_key=01时 随机查询，01：普通随机；02：靓号随机；03：普通、靓号随机 当query_key=02时：参考营业前台AABB ABC（当关键字为11位时为指定移网号码查询")
	private String query_value;

	@ZteSoftCommentAnnotationParam(name = "营业点", type = "String", isNecessary = "N", desc = "营业点")
	private String office_id;

	@ZteSoftCommentAnnotationParam(name = "营业员", type = "String", isNecessary = "N", desc = "营业员")
	private String operator_id;

	@ZteSoftCommentAnnotationParam(name = "返回号码数", type = "String", isNecessary = "N", desc = "01:100个,02:500个[可空,空时系统默认为01]")
	private String number;

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getQuery_key() {
		return query_key;
	}

	public void setQuery_key(String query_key) {
		this.query_key = query_key;
	}

	public String getQuery_value() {
		return query_value;
	}

	public void setQuery_value(String query_value) {
		this.query_value = query_value;
	}

	public String getOffice_id() {
		return office_id;
	}

	public void setOffice_id(String office_id) {
		this.office_id = office_id;
	}

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {

		return "com.zte.unicomService.bss.numberResourceQueryZjBss";
	}

    public String getNotNeedReqStrOrderId() {
        return notNeedReqStrOrderId;
    }

    public void setNotNeedReqStrOrderId(String notNeedReqStrOrderId) {
        this.notNeedReqStrOrderId = notNeedReqStrOrderId;
    }

}
