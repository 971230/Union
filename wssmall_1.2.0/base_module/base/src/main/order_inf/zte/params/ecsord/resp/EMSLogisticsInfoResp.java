package zte.params.ecsord.resp;

import java.util.List;

import params.ZteResponse;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class EMSLogisticsInfoResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name="物流信息",type="List",isNecessary="Y",desc="物流信息")
	private List traces;

	private String res_code;//响应编码
	
	private String res_message;//响应信息描述
	
	public List getTraces() {
		return traces;
	}
	
	public void setTraces(List traces) {
		this.traces = traces;
	}

	public String getRes_code() {
		return res_code;
	}

	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}

	public String getRes_message() {
		return res_message;
	}

	public void setRes_message(String res_message) {
		this.res_message = res_message;
	}

	

}
