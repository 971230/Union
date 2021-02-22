package zte.net.ecsord.params.sf.resp;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import params.ZteResponse;
import zte.net.ecsord.params.sf.vo.OrderInfo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" }) 
public class NotifyOrderInfoSFResponse  extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="errorCode：OK-成功   ERR-失败")
	private String errorCode;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="errorMessage： 失败的时候才有值")
	private String errorMessage;
	
	
	@ZteSoftCommentAnnotationParam(name="响应码",type="String",isNecessary="Y",desc="orderInfo：订单信息，成功的时候才有值")
	private List<OrderInfo> orderInfo;

	@ZteSoftCommentAnnotationParam(name="不用备注",type="String",isNecessary="Y",desc="remark：1-收方超范围，2-派方超范围，3-其他原因")
	private String remark;
	
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
	
	
	
public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



public List<OrderInfo> getOrderInfo() {
		return orderInfo;
	}



	public void setOrderInfo(List<OrderInfo> orderInfo) {
		this.orderInfo = orderInfo;
	}



public static void main(String[] args){
		
		
		
		 
		
	NotifyOrderInfoSFResponse resp = new NotifyOrderInfoSFResponse();
	OrderInfo info=new OrderInfo();
//	resp.setOrderInfo(info);
	resp.setError_msg("");
	resp.setError_msg("");
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(resp);
			//logger.info("json:"+json);//json:{"resp_code":"0000","resp_msg":"success"}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {  
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
