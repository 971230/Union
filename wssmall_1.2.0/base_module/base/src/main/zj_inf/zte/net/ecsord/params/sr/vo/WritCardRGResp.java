package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class WritCardRGResp implements Serializable {
	
	public WritCardRGResp(String code, String message){
		this.head = new RespHeadRG(code, message);
	};
	
	@ZteSoftCommentAnnotationParam(name="body",type="WriteCardBody",isNecessary="Y",desc="body:返回主体信息")
	private WriteCardBodyRG body;
	
	@ZteSoftCommentAnnotationParam(name="head",type="RespHead",isNecessary="Y",desc="head：返回描述信息")
	private RespHeadRG head;

	public WriteCardBodyRG getBody() {
		return body;
	}

	public void setBody(WriteCardBodyRG body) {
		this.body = body;
	}

	public RespHeadRG getHead() {
		return head;
	}

	public void setHead(RespHeadRG head) {
		this.head = head;
	}
}
