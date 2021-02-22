package zte.net.ecsord.params.busi.req;

import java.util.Map;

import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.framework.database.NotDbField;

/**
 * 
 * @author wu.i
 * 
 * 订单对象插入
 * 
 * 60G的内存可缓存15728640条订单树数据
 * 
 */
public class ZteCrudBusiRequest extends ZteRequest {
	private String insertSql;
	private Map params;
	private Map keyMap;
	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public Map getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(Map keyMap) {
		this.keyMap = keyMap;
	}
	
}
