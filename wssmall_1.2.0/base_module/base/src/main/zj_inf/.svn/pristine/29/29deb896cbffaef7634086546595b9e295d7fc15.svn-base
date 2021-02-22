package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.BSSFeeInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BSSAccountResponse  extends ZteResponse{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8583914390035781359L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String RespDesc;
	@ZteSoftCommentAnnotationParam(name = "ESS订单交易流水", type = "String", isNecessary = "Y", desc = "provOrderId订单交易流水 为正式提交时使用")
	private String ProvOrderID; // Y String(30) ESS订单交易流水 为正式提交时使用
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "String", isNecessary = "Y", desc = "feeInfo：订单树es_attr_fee_info 收费信息（ESS从BSS获取到的）")
	private List<BSSFeeInfoRspVo> FeeInfo; // 订单树es_attr_fee_info 收费信息（ESS从BSS获取到的）
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "para：保留字段")
	private List<BSSParaVo> Para;// 不管 * 保留字段
	@ZteSoftCommentAnnotationParam(name = "应收费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "para：应收费用正整数单位：厘")
	private String TotalFee;
	
	public String getTotalFee() {
		return TotalFee;
	}

	public void setTotalFee(String totalFee) {
		this.TotalFee = totalFee;
	}


	public String getProvOrderId() {
		return ProvOrderID;
	}

	public void setProvOrderId(String provOrderId) {
		this.ProvOrderID = provOrderId;
	}

	public List<BSSFeeInfoRspVo> getFeeInfo() {
		return FeeInfo;
	}

	public void setFeeInfo(List<BSSFeeInfoRspVo> feeInfo) {
		this.FeeInfo = feeInfo;
	}

	public String getRespCode() {
		return RespCode;
	}

	public void setRespCode(String respCode) {
		this.RespCode = respCode;
	}

	public String getRespDesc() {
		return RespDesc;
	}

	public void setRespDesc(String respDesc) {
		this.RespDesc = respDesc;
	}

	public String getProvOrderID() {
		return ProvOrderID;
	}

	public void setProvOrderID(String provOrderID) {
		ProvOrderID = provOrderID;
	}

	public List<BSSParaVo> getPara() {
		return Para;
	}

	public void setPara(List<BSSParaVo> para) {
		Para = para;
	}

}
