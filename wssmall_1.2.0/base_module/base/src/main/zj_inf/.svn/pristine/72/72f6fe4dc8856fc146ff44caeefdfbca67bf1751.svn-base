package zte.net.ecsord.params.hs.resp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 *  SAPB2C省分退货、拦截指令传输仓储商系统接口
 * @作者 Rapon
 * @创建日期 2016-07-22
 * @版本 V 1.0
 */
@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" }) 
public class OrderCancelReceiveResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000：成功/其他编码：失败")
	private String TYPE;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "结果描述信息")
	private String MESSAGE;
	
	@Override
	public String getBody(){
		return "O_RETURN";
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
}
