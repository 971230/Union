package zte.net.ecsord.params.nd.resp;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.nd.vo.DealOrderInfo;
import zte.net.ecsord.params.nd.vo.StatuRespOrderInfo;
@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" })
public class NotifyOrderStatuNDResponse  extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String activeNo;
	
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="errorCode：应答编码 0000：成功 0001：失败")
	private String errorCode;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="errorMessage：错误描述")
	private String errorMessage;

	@ZteSoftCommentAnnotationParam(name="订单信息",type="String",isNecessary="Y",desc="orderInfo：订单信息")
	private List<StatuRespOrderInfo> orderInfo;


	public String getActiveNo() {
		return activeNo;
	}

	public void setActiveNo(String activeNo) {
		this.activeNo = activeNo;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<StatuRespOrderInfo> getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(List<StatuRespOrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}

	
}
