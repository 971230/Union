package zte.net.ecsord.params.bss.resp;

import java.util.List;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.bss.vo.NumInfoZjBss;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * 
 * 浙江省份选号返回参数
 * @author wayne
 * 2016-9-30
 *
 */
public class NumberResourceQueryZjBssResponse extends ZteBusiResponse {
	
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String msg;
	
	@ZteSoftCommentAnnotationParam(name = "号码信息", type = "List", isNecessary = "Y", desc = "resp：号码信息")
	private List<NumInfoZjBss> respJson;
	
	
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public List<NumInfoZjBss> getRespJson() {
		return respJson;
	}


	public void setRespo(List<NumInfoZjBss> respJson) {
		this.respJson = respJson;
	}










}
