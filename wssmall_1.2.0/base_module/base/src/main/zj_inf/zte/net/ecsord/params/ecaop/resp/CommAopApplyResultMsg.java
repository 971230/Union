package zte.net.ecsord.params.ecaop.resp;

import java.io.Serializable;
import java.util.List;

import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.resp.vo.FeeInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class CommAopApplyResultMsg implements Serializable {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "ESS订单交易流水", type = "String", isNecessary = "Y", desc = "provOrderId订单交易流水 为正式提交时使用")
	private String provOrderId; // Y String(30) ESS订单交易流水 为正式提交时使用
	@ZteSoftCommentAnnotationParam(name = "BSS订单ID", type = "String", isNecessary = "Y", desc = "bssOrderId订单ID")
	private String bssOrderId; // Y String(30) BSS订单ID
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "String", isNecessary = "Y", desc = "feeInfo：订单树es_attr_fee_info 收费信息（ESS从BSS获取到的）")
	private List<FeeInfoRspVo> feeInfo; // 订单树es_attr_fee_info 收费信息（ESS从BSS获取到的）
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "para：保留字段")
	private List<ParamsVo> para;// 不管 * 保留字段
	@ZteSoftCommentAnnotationParam(name = "应收费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "para：应收费用正整数单位：厘")
	private String totalFee;
	
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
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

	public String getBssOrderId() {
		return bssOrderId;
	}

	public void setBssOrderId(String bssOrderId) {
		this.bssOrderId = bssOrderId;
	}
	
	public List<FeeInfoRspVo> getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(List<FeeInfoRspVo> feeInfo) {
		this.feeInfo = feeInfo;
	}

    public List<ParamsVo> getPara() {
        return para;
    }

    public void setPara(List<ParamsVo> para) {
        this.para = para;
    }
}
