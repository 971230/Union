/**
 * 
 */
package zte.net.ecsord.params.logs.req;

import params.ZteRequest;
import zte.net.ecsord.params.logs.resp.QryMatchDictLogsResp;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * @author ZX
 * @version 2015-08-27
 * @see 查询匹配字典的日志
 *
 */
public class QryMatchDictLogsReq extends ZteRequest<QryMatchDictLogsResp> {
	
	@ZteSoftCommentAnnotationParam(name="字典类型",type="String",isNecessary="Y",desc="字典类型")
	private String dict_type;
	@ZteSoftCommentAnnotationParam(name="字典内容",type="String",isNecessary="Y",desc="字典内容")
	private String dict_desc;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="Y",desc="开始时间")
	private String begin_time;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="Y",desc="结束时间")
	private String end_time;
	@ZteSoftCommentAnnotationParam(name="业务ID(联通特指订单编号)",type="String",isNecessary="Y",desc="业务ID(联通特指订单编号)")
	private String obj_id;
	@ZteSoftCommentAnnotationParam(name="页码",type="String",isNecessary="Y",desc="低级页")
	private int pageNo;
	@ZteSoftCommentAnnotationParam(name="页数",type="String",isNecessary="Y",desc="煤业显示数")
	private int pageSize;
	
	public String getDict_type() {
		return dict_type;
	}
	public void setDict_type(String dict_type) {
		this.dict_type = dict_type;
	}
	public String getDict_desc() {
		return dict_desc;
	}
	public void setDict_desc(String dict_desc) {
		this.dict_desc = dict_desc;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getObj_id() {
		return obj_id;
	}
	public void setObj_id(String obj_id) {
		this.obj_id = obj_id;
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
		return "zte.net.ecsord.iorderservices.qrydatapraserinst.req";
	}
}
