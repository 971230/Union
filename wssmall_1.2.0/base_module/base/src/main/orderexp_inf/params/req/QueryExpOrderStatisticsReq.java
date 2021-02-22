package params.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

public class QueryExpOrderStatisticsReq extends ZteRequest {
	
	@ZteSoftCommentAnnotationParam(name="用户ID",type="String",isNecessary="Y",desc="用户ID")
  	private String user_id;
	@ZteSoftCommentAnnotationParam(name="用户等级",type="Integer",isNecessary="Y",desc="用户等级")
  	private Integer founder;
	@ZteSoftCommentAnnotationParam(name="开始时间yyyy-mm-dd hh24:mi:ss",type="String",isNecessary="Y",desc="开始时间yyyy-mm-dd hh24:mi:ss")
  	private String start_time;
	@ZteSoftCommentAnnotationParam(name="结束时间yyyy-mm-dd hh24:mi:ss",type="String",isNecessary="Y",desc="结束时间yyyy-mm-dd hh24:mi:ss")
  	private String end_time;
	@ZteSoftCommentAnnotationParam(name="当前页数",type="Integer",isNecessary="N",desc="当前页数")
	private Integer pageNo;
	@ZteSoftCommentAnnotationParam(name="每页显示记录数",type="Integer",isNecessary="N",desc="每页显示记录数")
	private Integer pageSize;

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		return "zte.net.orderexp.queryExpOrderStatistics";
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getFounder() {
		return founder;
	}

	public void setFounder(Integer founder) {
		this.founder = founder;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
