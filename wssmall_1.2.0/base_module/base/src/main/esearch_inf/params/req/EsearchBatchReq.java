package params.req;

import java.io.Serializable;
import java.util.List;

import params.ZteRequest;
import params.ZteResponse;

import com.ztesoft.api.ApiRuleException;

public class EsearchBatchReq extends ZteRequest<ZteResponse> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2116957135311608589L;

	private int pageNum;
	
	private int pageSize;
	
	private String sql;
	
	private List logList;
	
	
	public List getLogList() {
		return logList;
	}

	public void setLogList(List logList) {
		this.logList = logList;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	@Override
	public String getApiMethodName() {
		return "";
	}
	
}
