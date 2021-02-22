package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WritCardResp implements Serializable {
	
	public WritCardResp(String code, String message){
		this.head = new RespHead(code, message);
	};
	
	@ZteSoftCommentAnnotationParam(name="body",type="WriteCardBody",isNecessary="Y",desc="body:返回主体信息")
	private WriteCardBody body;
	
	@ZteSoftCommentAnnotationParam(name="head",type="RespHead",isNecessary="Y",desc="head：返回描述信息")
	private RespHead head;

	public WriteCardBody getBody() {
		return body;
	}

	public void setBody(WriteCardBody body) {
		this.body = body;
	}

	public RespHead getHead() {
		return head;
	}

	public void setHead(RespHead head) {
		this.head = head;
	}
}
