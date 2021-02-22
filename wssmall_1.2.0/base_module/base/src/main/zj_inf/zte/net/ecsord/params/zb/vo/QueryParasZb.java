package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueryParasZb implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name = "选号条件", type = "String", isNecessary = "Y", desc = "queryType：选号条件")
	private String queryType;
	
	@ZteSoftCommentAnnotationParam(name = "选号参数", type = "String", isNecessary = "N", desc = "queryPara：选号参数")
	private String queryPara;

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryPara() {
		return queryPara;
	}

	public void setQueryPara(String queryPara) {
		this.queryPara = queryPara;
	}

	
}
