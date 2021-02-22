package zte.net.ecsord.params.zb.vo;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.zb.vo.orderattr.Fee;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class AccountRsp implements Serializable{
	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="RespCode：应答编码")
	private String RespCode;
	
	@ZteSoftCommentAnnotationParam(name="错误描述",type="String",isNecessary="Y",desc="RespDesc：错误描述")
	private String RespDesc;
	
	@ZteSoftCommentAnnotationParam(name="总费用",type="String",isNecessary="Y",desc="TotalFee:总费用")
	private String TotalFee;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="Para:保留字段 ")
	private List<Fee> FeeInfo;

	public String getRespCode() {
		return RespCode;
	}

	public void setRespCode(String respCode) {
		RespCode = respCode;
	}

	public String getRespDesc() {
		return RespDesc;
	}

	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}

	public String getTotalFee() {
		return TotalFee;
	}

	public void setTotalFee(String totalFee) {
		TotalFee = totalFee;
	}

	public List<Fee> getFeeInfo() {
		return FeeInfo;
	}

	public void setFeeInfo(List<Fee> feeInfo) {
		FeeInfo = feeInfo;
	}
	
}
