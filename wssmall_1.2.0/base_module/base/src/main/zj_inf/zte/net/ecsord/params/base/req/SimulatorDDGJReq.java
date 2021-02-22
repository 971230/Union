package zte.net.ecsord.params.base.req;


import params.ZteRequest;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.framework.database.NotDbField;

public  class SimulatorDDGJReq extends ZteRequest  {
	
	@ZteSoftCommentAnnotationParam(name="归集类型",type="String",isNecessary="N",desc="ddgj：新商城；ddgj_zb：总部")
	String reqType;
	
	@ZteSoftCommentAnnotationParam(name="请求报文",type="String",isNecessary="N",desc="请求报文")
	String reqStr;
	
	@ZteSoftCommentAnnotationParam(name="请求的servlet路径",type="String",isNecessary="N",desc="请求的servlet路径")
	String sevletPath;

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getSevletPath() {
		return sevletPath;
	}

	public void setSevletPath(String sevletPath) {
		this.sevletPath = sevletPath;
	}

	@Override
	@NotDbField
	public void check() throws ApiRuleException {
		
	}
	
	@Override
	@NotDbField
	public String getApiMethodName() {
		return "zte.net.ecsord.base.simuDDGJ";
	}

	
}
