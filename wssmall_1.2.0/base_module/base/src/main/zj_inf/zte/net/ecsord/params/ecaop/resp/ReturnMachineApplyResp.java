package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.FeeInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class ReturnMachineApplyResp extends ZteResponse{
	@ZteSoftCommentAnnotationParam(name="落地系统订单号",type="String",isNecessary="N",desc="provOrdersId：落地系统订单号")
	private String provOrdersId;
	@ZteSoftCommentAnnotationParam(name="收费信息*（ESS从BSS获取到的）",type="FeeInfoVo",isNecessary="N",desc="feeInfo：收费信息*（ESS从BSS获取到的）")
	private List<FeeInfoRspVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name="收费信息*（ESS从BSS获取到的）",type="String",isNecessary="Y",desc="totalFee：总费用正整数单位：分")
	private String totalFee;
	
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="paras：保留字段")
	private List<ParaVo> paras;
	@ZteSoftCommentAnnotationParam(name="返回编码",type="String",isNecessary="N",desc="code：返回编码")
	private String code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="N",desc="detail：返回描述")
	private String detail;
	public String getProvOrdersId() {
		return provOrdersId;
	}
	public void setProvOrdersId(String provOrdersId) {
		this.provOrdersId = provOrdersId;
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
	public List<ParaVo> getParas() {
		return paras;
	}
	public void setParas(List<ParaVo> paras) {
		this.paras = paras;
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
