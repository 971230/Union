package zte.net.iservice.params.resp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;

public class OpenAccountResp extends ZteResponse{
	
	@ZteSoftCommentAnnotationParam(name="开户结果描述(样例)",type="String",isNecessary="N",desc="开户结果描述")
    private String result_desc;

	public String getResult_desc() {
		return result_desc;
	}

	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
}
