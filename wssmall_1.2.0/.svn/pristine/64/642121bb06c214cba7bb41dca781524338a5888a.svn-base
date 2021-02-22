package zte.net.ecsord.params.nd.resp;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.nd.req.DispatchingNumReceivingNDRequset;
@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" }) 
public class DispatchingNumReceivingNDResponse  extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name="访问流水",type="String",isNecessary="Y",desc="ActiveNo：访问流水")
	private String activeNo;
	
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="RespCode：0000：成功   0001：失败")
	private String errorCode;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="RespDesc：错误描述")
	private String errorMessage;

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
public static void main(String[] args){
		
		
		
		 
		
	DispatchingNumReceivingNDResponse resp = new DispatchingNumReceivingNDResponse();
		resp.setActiveNo("1111111");
	
	
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(resp);
			logger.info("json:"+json);//json:{"resp_code":"0000","resp_msg":"success"}
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
