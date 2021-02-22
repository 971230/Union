package zte.net.common.params.req;

import java.util.HashMap;
import java.util.Map;

import params.ZteBusiRequest;
import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;


/**
 * 
 * @author wu.i 
 * 实体对象查询，入参对象
 * @param <T>
 * 
 */
public class ZteInstQueryRequest<T extends ZteBusiRequest> extends ZteBusiRequest {
	
	@ZteSoftCommentAnnotationParam(name="请求参数",type="String",isNecessary="N",desc="请求参数")
	private Map queryParmas =new HashMap() ; //请求参数
	
	private T queryObject; //请求实体
	

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}

	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.orderService.zteinst.query";
	}


	public void setQueryParmas(Map<String, String> queryParmas) {
		this.queryParmas = queryParmas;
	}

	public void setQueryObject(T queryObject) {
		this.queryObject = queryObject;
	}

	public Map getQueryParmas() {
		return queryParmas;
	}

	public T getQueryObject() {
		return queryObject;
	}

	


}
