package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueWriMachStaInBatchRGResp implements Serializable {
	
	public QueWriMachStaInBatchRGResp(String code, String message){
		this.head = new RespHeadRG(code, message);
	}
	
	@ZteSoftCommentAnnotationParam(name="body",type="RecycleWriteCardBody",isNecessary="Y",desc="body:返回主体信息")
	private QueWriMachStaInBatchRGBody body;
	
	@ZteSoftCommentAnnotationParam(name="head",type="RespHead",isNecessary="Y",desc="head：返回描述信息")
	private RespHeadRG head;

	public QueWriMachStaInBatchRGBody getBody() {
		return body;
	}

	public void setBody(QueWriMachStaInBatchRGBody body) {
		this.body = body;
	}

	public RespHeadRG getHead() {
		return head;
	}

	public void setHead(RespHeadRG head) {
		this.head = head;
	}
}