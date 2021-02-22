/**
 * 
 */
package zte.net.ecsord.params.logs.req;

import java.util.Map;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.logs.resp.MatchInfLogStateResp;

/**
 * @author ZX
 * @version 2015-10-29
 * @see 修改日志接口状态
 *
 */
public class MatchInfLogStateReq extends ZteRequest<MatchInfLogStateResp> {
	
	@ZteSoftCommentAnnotationParam(name="组件编码",type="String",isNecessary="Y",desc="组件编码")
	private String op_code;
	@ZteSoftCommentAnnotationParam(name="接口返回报文",type="String",isNecessary="Y",desc="接口返回报文")
	private String rspXml;
	@ZteSoftCommentAnnotationParam(name="接口返回错误信息",type="String",isNecessary="Y",desc="接口返回错误信息")
	private Map logColMap;
	@ZteSoftCommentAnnotationParam(name="接口返回对象",type="String",isNecessary="Y",desc="接口返回对象")
	private Map retObj;	
	
	public Map getRetObj() {
		return retObj;
	}
	public void setRetObj(Map retObj) {
		this.retObj = retObj;
	}
	public Map getLogColMap() {
		return logColMap;
	}
	public void setLogColMap(Map logColMap) {
		this.logColMap = logColMap;
	}
	public String getOp_code() {
		return op_code;
	}
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	public String getRspXml() {
		return rspXml;
	}
	public void setRspXml(String rspXml) {
		this.rspXml = rspXml;
	}
	
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.updateinflogstate.req";
	}
	
}
