package zte.net.ecsord.params.base.resp;

import params.ZteResponse;

import com.ztesoft.net.auto.rule.fact.AutoFact;

/**
 * 
 * @author wu.i
 * 
 * 业务组件返回对象
 * 
 */
public class BusiCompResponse extends ZteResponse {
	
	private static final long serialVersionUID = 1941946526675627213L;
	private ZteResponse response;
	private AutoFact fact;
	
	public ZteResponse getResponse() {
		return response;
	}

	public void setResponse(ZteResponse response) {
		this.response = response;
	}

	public AutoFact getFact() {
		return fact;
	}

	public void setFact(AutoFact fact) {
		this.fact = fact;
	}
}
