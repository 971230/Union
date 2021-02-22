/**
 * 
 */
package zte.net.ecsord.params.jkzf.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.jkzf.resp.JKZFInfResp;

/**
 * @author ZX
 * @version 2015-10-08
 * @see 接口转发请求
 *
 */
public class JKZFInfReq extends ZteRequest<JKZFInfResp> {
	
	private Map paramsMap;
	private String jk_mark;
	
	public Map getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map paramsMap) {
		this.paramsMap = paramsMap;
	}

	public String getJk_mark() {
		return jk_mark;
	}

	public void setJk_mark(String jk_mark) {
		this.jk_mark = jk_mark;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return "zte.net.infservices.jkzfinf";
	}
	
}
