package zte.net.ecsord.params.sf.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class RoutePushServiceResponse  extends ZteResponse {
	
	private static final long serialVersionUID = -4251352737618932518L;

	@ZteSoftCommentAnnotationParam(name="成功接收的路由编号",type="String",isNecessary="Y",desc="id：成功接收的路由编号，如果有多个路由编号，以逗号分隔，如“123,124,125”")
	private String id;
	
	@ZteSoftCommentAnnotationParam(name="未成功接收的路由编号",type="String",isNecessary="Y",desc="id_error：未成功接收的路由编号，如果有多个路由编号，以逗号分隔，如“123,124,125“，这部分路由编号，顺丰会定时重发")
	private String id_error;

	@ZteSoftCommentAnnotationParam(name="结果编码",type="String",isNecessary="Y",desc="结果编码")
	private String respOrderStatus; 
	
	@ZteSoftCommentAnnotationParam(name="请求结果描述",type="String",isNecessary="Y",desc="请求结果描述")
    private String respOrderDesc; 
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_error() {
		return id_error;
	}

	public void setId_error(String id_error) {
		this.id_error = id_error;
	}

	public String getRespOrderStatus() {
		return respOrderStatus;
	}

	public void setRespOrderStatus(String respOrderStatus) {
		this.respOrderStatus = respOrderStatus;
	}

	public String getRespOrderDesc() {
		return respOrderDesc;
	}

	public void setRespOrderDesc(String respOrderDesc) {
		this.respOrderDesc = respOrderDesc;
	}	
	
	
}
