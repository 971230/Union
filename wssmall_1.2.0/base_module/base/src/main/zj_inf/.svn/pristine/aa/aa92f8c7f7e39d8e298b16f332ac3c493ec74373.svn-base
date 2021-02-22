package zte.net.ecsord.params.ecaop.resp.vo;

import java.util.List;

import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.FeeInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class UserJoinMainViceCardVO {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "N", desc = "code")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "detail")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "订单ID", type = "String", isNecessary = "Y", desc = "")
	private String orderId;
	@ZteSoftCommentAnnotationParam(name = "收费信息*（ESS从BSS获取到的）", type = "FeeInfoRspVo", isNecessary = "Y", desc = "feeInfo")
	private List<FeeInfoRspVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "总费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "totalFee")
	private String totalFee;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParaVo", isNecessary = "Y", desc = "para")
	private List<ParaVo> para;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<FeeInfoRspVo> getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoRspVo> feeInfo) {
		this.feeInfo = feeInfo;
	}

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

}
