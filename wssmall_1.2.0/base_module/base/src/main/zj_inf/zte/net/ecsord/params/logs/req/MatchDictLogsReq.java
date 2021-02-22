/**
 * 
 */
package zte.net.ecsord.params.logs.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

import params.ZteRequest;
import zte.net.ecsord.params.logs.resp.MatchDictLogsResp;

/**
 * @author ZX
 * @version 2015-08-26
 * @see 匹配错误字典
 *
 */
public class MatchDictLogsReq extends ZteRequest<MatchDictLogsResp> {
	
	@ZteSoftCommentAnnotationParam(name="业务ID",type="String",isNecessary="Y",desc="业务ID")
	private String obj_id;
	@ZteSoftCommentAnnotationParam(name="组件编码",type="String",isNecessary="Y",desc="组件编码")
	private String op_code;
	@ZteSoftCommentAnnotationParam(name="本地接口日志ID",type="String",isNecessary="Y",desc="本地接口日志ID")
	private String local_log_id;
	@ZteSoftCommentAnnotationParam(name="报文内容",type="String",isNecessary="Y",desc="报文内容")
	private String rsp_xml;
	
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
	}
	public String getOp_code() {
		return op_code;
	}
	public void setOp_code(String op_code) {
		this.op_code = op_code;
	}
	public String getLocal_log_id() {
		return local_log_id;
	}
	public void setLocal_log_id(String local_log_id) {
		this.local_log_id = local_log_id;
	}
	public String getRsp_xml() {
		return rsp_xml;
	}
	public void setRsp_xml(String rsp_xml) {
		this.rsp_xml = rsp_xml;
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
		return "zte.net.ecsord.iorderservices.matchdictlogs.req";
	}
	
}
