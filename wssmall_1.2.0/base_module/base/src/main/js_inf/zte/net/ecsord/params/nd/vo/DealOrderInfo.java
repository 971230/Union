package zte.net.ecsord.params.nd.vo;

import java.io.Serializable;
import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 返回给南都订单信息实体
 * @author jun
 *
 */
public class DealOrderInfo implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7033506859007862833L;

	@ZteSoftCommentAnnotationParam(name="外部订单号",type="String",isNecessary="Y",desc="origOrderId：外部订单号")
	private String origOrderId;

	@ZteSoftCommentAnnotationParam(name="状态通知应用编码",type="String",isNecessary="Y",desc="respOrderStatus：状态通知应用编码  0000：成功 0001：失败 9999: 订单系统自己处理、不接受外部同步")
	private String respOrderStatus;	

	@ZteSoftCommentAnnotationParam(name="订单的错误描述",type="String",isNecessary="Y",desc="respOrderDesc:订单的错误描述")
	private String respOrderDesc;	
	


	public String getOrigOrderId() {
		return origOrderId;
	}


	public void setOrigOrderId(String origOrderId) {
		this.origOrderId = origOrderId;
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
