package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.OrderResp;
import params.ZteResponse;
import zte.net.ecsord.params.bss.vo.BlankCardData;
import zte.net.ecsord.params.bss.vo.NumInfoZjBss;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * 获取白卡数据响应结果
 *
 */
public class SynCardDataRsp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：调用结果00000表示成功")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：错误描述")
	private String msg;
	
	
	
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



	
	
}
