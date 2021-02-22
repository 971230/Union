package zte.params.orderctn.resp;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class HSOrderCtnResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "0000：成功/其他编码：失败")
	private String TYPE;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "结果描述信息")
	private String MESSAGE;
	
	
	
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



	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return "O_RETURN";
	}
	
}
