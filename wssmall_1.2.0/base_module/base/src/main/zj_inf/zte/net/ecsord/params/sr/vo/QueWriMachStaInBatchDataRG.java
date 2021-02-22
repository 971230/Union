package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class QueWriMachStaInBatchDataRG implements Serializable {

	@ZteSoftCommentAnnotationParam(name="body",type="String",isNecessary="Y",desc="body：")
	private  QueWriMachStaInBatchDataRGBodyReq body;
	
	@ZteSoftCommentAnnotationParam(name="head",type="String",isNecessary="Y",desc="head：")
	private  QueWriMachStaInBatchDataRGHeadReq head;


	public QueWriMachStaInBatchDataRGBodyReq getBody() {
		return body;
	}

	public void setBody(QueWriMachStaInBatchDataRGBodyReq body) {
		this.body = body;
	}

	public QueWriMachStaInBatchDataRGHeadReq getHead() {
		return head;
	}

	public void setHead(QueWriMachStaInBatchDataRGHeadReq head) {
		this.head = head;
	}
	
	
}
