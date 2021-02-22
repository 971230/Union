package zte.net.ecsord.params.sr.vo;

import java.io.Serializable;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class RecycleWriteCardBody implements Serializable {

	@ZteSoftCommentAnnotationParam(name="detail",type="String",isNecessary="Y",desc="detail：描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name="result",type="String", isNecessary="Y",
			desc="result:0:退卡成功,1:写卡机当前写卡槽中无卡可退,-1:写卡机返回失败，需要检查写卡机状态" + 
				 "result:0:退卡成功,1:写卡机当前写卡通道中无卡可回收,-1:写卡机返回失败，需要检查")
	private String result;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
