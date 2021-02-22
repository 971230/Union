package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSFeeInfoReqVo;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * BSS卡数据同步响应结果
 *
 */
public class WriteCardPreRsp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name="应答编码",type="String",isNecessary="Y",desc="respCode：应答编码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name="错误描述详细的描述错误原因",type="String",isNecessary="Y",desc="respDesc：错误描述详细的描述错误原因")
	private String RespDesc;
	@ZteSoftCommentAnnotationParam(name="收费信息",type="String",isNecessary="Y",desc="feeInfo：收费信息")
	private List<BSSFeeInfoReqVo> FeeInfo;
	@ZteSoftCommentAnnotationParam(name="总费用正整数",type="String",isNecessary="Y",desc="totalFee：总费用正整数")
	private String TotalFee;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="String",isNecessary="Y",desc="para：保留字段")
	private List<BSSParaVo> Para;
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
	public List<BSSFeeInfoReqVo> getFeeInfo() {
		return FeeInfo;
	}
	
	public void setFeeInfo(List<BSSFeeInfoReqVo> feeInfo) {
		FeeInfo = feeInfo;
	}
	public String getTotalFee() {
		return TotalFee;
	}
	public void setTotalFee(String totalFee) {
		this.TotalFee = totalFee;
	}
	public List<BSSParaVo> getPara() {
		return Para;
	}
	public void setPara(List<BSSParaVo> para) {
		this.Para = para;
	}
}
