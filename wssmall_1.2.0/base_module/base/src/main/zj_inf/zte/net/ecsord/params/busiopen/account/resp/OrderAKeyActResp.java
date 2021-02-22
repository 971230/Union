package zte.net.ecsord.params.busiopen.account.resp;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import params.OrderResp;
import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 订单一键开户
 * 
 * @作者 wui
 * @创建日期 2014-11-04
 * @版本 V 1.0
 */
@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" }) 
public class OrderAKeyActResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000：成功/其他编码：失败")
	private String resp_code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "结果描述信息")
	private String resp_msg;

	public String getResp_code() {
		return resp_code;
	}

	public void setResp_code(String resp_code) {
		this.resp_code = resp_code;
	}

	public String getResp_msg() {
		return resp_msg;
	}

	public void setResp_msg(String resp_msg) {
		this.resp_msg = resp_msg;
	}

	public static void main(String[] args){
		OrderAKeyActResp resp = new OrderAKeyActResp();
		OrderResp orderResp = new OrderResp();
		resp.setResp(orderResp);
		orderResp.setDeal_msg("deal_msg");
		resp.setError_code("-1");
		resp.setResp_code("0000");
		resp.setResp_msg("success");
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
