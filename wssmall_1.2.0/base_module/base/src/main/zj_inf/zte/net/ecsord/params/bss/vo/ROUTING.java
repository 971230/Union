package zte.net.ecsord.params.bss.vo;

import java.io.Serializable;
import java.util.Map;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ROUTING implements Serializable {
	@ZteSoftCommentAnnotationParam(name="路由类型",type="String",isNecessary="Y",desc="参见路由类型编码，如按手机号码路由等")
	private String ROUTE_TYPE;
	
	@ZteSoftCommentAnnotationParam(name="路由关键值",type="String",isNecessary="Y",desc="路由类型对应的关键值，参见路由类型说明。如路由类型为手机号码则此字段应填写手机号码；若路由类型为充值卡号码则此字段应填写充值卡号码")
	private String ROUTE_VALUE;

	public String getROUTE_TYPE() {
		return ROUTE_TYPE;
	}

	public void setROUTE_TYPE(String rOUTE_TYPE) {
		ROUTE_TYPE = rOUTE_TYPE;
	}

	public String getROUTE_VALUE() {
		return ROUTE_VALUE;
	}

	public void setROUTE_VALUE(String rOUTE_VALUE) {
		ROUTE_VALUE = rOUTE_VALUE;
	}

	
}
