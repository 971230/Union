package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RecycleCardResp implements Serializable {
	
	public RecycleCardResp(String code, String message){
		this.head = new RespHead(code, message);
	}
	
	@ZteSoftCommentAnnotationParam(name="body",type="RecycleWriteCardBody",isNecessary="Y",desc="body:返回主体信息")
	private RecycleWriteCardBody body;
	
	@ZteSoftCommentAnnotationParam(name="head",type="RespHead",isNecessary="Y",desc="head：返回描述信息")
	private RespHead head;

	public RecycleWriteCardBody getBody() {
		return body;
	}

	public void setBody(RecycleWriteCardBody body) {
		this.body = body;
	}

	public RespHead getHead() {
		return head;
	}

	public void setHead(RespHead head) {
		this.head = head;
	}
}
