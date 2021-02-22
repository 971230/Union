package params;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author wui
 * 
 * 规则参数
 */
@SuppressWarnings("serial")
public class RuleParams extends ZteRequest<ZteResponse> {

	private ZteRequest zteRequest;
	
	private String goods_id; //商品id
	
	private String service_code;
	
	private String rule_type;

	private String method_name;
	
	private String api_method;
	
	private String exec_rule; //导入数据处理bean
	
	
	
	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	
	public ZteRequest getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteRequest zteRequest) {
		this.zteRequest = zteRequest;
		this.zteRequest.setSourceFrom(this.getSourceFrom()); //重新设置进去，避免dubbo调用处理问题
		this.zteRequest.setAppKey(this.getSourceFrom());
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public String getRule_type() {
		return rule_type;
	}

	public void setRule_type(String rule_type) {
		this.rule_type = rule_type;
	}

	public String getMethod_name() {
		return method_name;
	}

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	public String getExec_rule() {
		return exec_rule;
	}

	public void setExec_rule(String exec_rule) {
		this.exec_rule = exec_rule;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return this.api_method == null?"zte.service.wssmall.process.ruleparams":this.api_method;
	}

	public String setApiMethodName(String api_method) {
		return this.api_method = api_method;
	}

	
}
