package zte.net.ecsord.params.zb.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;

import zte.net.ecsord.params.zb.vo.NumInfoZb;

public class NumberResourceQueryZbResponse extends ZteBusiResponse  {
	
	@ZteSoftCommentAnnotationParam(name = "返回编码", type = "String", isNecessary = "Y", desc = "code：返回编码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String deatil;
	

	@ZteSoftCommentAnnotationParam(name = "号码信息", type = "List", isNecessary = "Y", desc = "numInfo：号码信息")
	private List<NumInfoZb> numInfo;


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDeatil() {
		return deatil;
	}


	public void setDeatil(String deatil) {
		this.deatil = deatil;
	}


	public List<NumInfoZb> getNumInfo() {
		return numInfo;
	}


	public void setNumInfo(List<NumInfoZb> numInfo) {
		this.numInfo = numInfo;
	}

}
