package zte.net.common.params.req;

import java.util.List;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i 实体对象查询，入参对象
 * @param <T>
 * 
 */
public class QueryRequest <T extends Object> extends ZteRequest {

	private String querySql; // 查询sql

	private List queryParams;

	private T queryObject; //返回对象类型

	

	public void setQueryObject(T zteRequest) {
		this.queryObject = zteRequest;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.query.basesupportquery";
	} // 查询参数

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public List getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(List queryParams) {
		this.queryParams = queryParams;
	}

	public T getQueryObject() {
		return queryObject;
	}

	
}
