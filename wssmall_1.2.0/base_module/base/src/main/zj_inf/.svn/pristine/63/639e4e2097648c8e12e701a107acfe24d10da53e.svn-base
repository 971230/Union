/**
 * 
 */
package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-05-04
 *
 */
public class QueryParasVo implements Serializable {
	
	@ZteSoftCommentAnnotationParam(name="选号条件",type="String",isNecessary="Y",desc="queryParas：选号条件 01：随机 02：号段 03：号码关键字 04：靓号等级: 05：预付费产品编码 06：查询号码范围")
	private String queryType;
	@ZteSoftCommentAnnotationParam(name="选号参数",type="String",isNecessary="N",desc="queryParas：选号参数，当选号条件为04时，选号参数为固定值 0：一类靓号；1：二类靓号；2：三类靓号；3：四类靓号；4：五类靓号；5：六类靓号，6：普号，一类靓号预存最高，普号最低。选号参数，当选号条件为06时，选号参数为固定值 0：仅从仓中取号 1：仅从池中选号 2：从仓和池中选号")
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
