package zte.net.ecsord.params.ecaop.resp.vo;

import java.util.List;

import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class MainViceOpenVO {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "code")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "detail")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "cBSS订单交易流水  为正式提交时使用", type = "String", isNecessary = "Y", desc = "bssOrderId")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name = "收费信息*（ESS从BSS获取到的）", type = "MainViceOpenFeeInfoVo", isNecessary = "Y", desc = "feeInfo")
	private List<FeeInfoRspVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "总费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "totalFee")
	private String totalFee;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParaVo", isNecessary = "Y", desc = "para")
	private List<ParaVo> para;

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public List<ParaVo> getPara() {
		return para;
	}

	public void setPara(List<ParaVo> para) {
		this.para = para;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getProvOrderId() {
		return provOrderId;
	}

	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}

	public List<FeeInfoRspVo> getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoRspVo> feeInfo) {
		this.feeInfo = feeInfo;
	}


}
