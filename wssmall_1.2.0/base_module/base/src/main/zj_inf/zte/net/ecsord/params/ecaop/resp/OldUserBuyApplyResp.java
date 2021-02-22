/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.FeeInfoRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author ZX
 * @version 2015-06-23
 * @see 老用户优惠购机处理申请
 *
 */
public class OldUserBuyApplyResp extends ZteResponse {
	
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name = "ESS订单ID", type = "String", isNecessary = "Y", desc = "essSubscribeId：订单ID")
	private String essSubscribeId;
	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "OldUserBuyApplyFeeInfoVo", isNecessary = "Y", desc = "feeInfo：收费信息")
	private List<FeeInfoRspVo> feeInfo;
	@ZteSoftCommentAnnotationParam(name = "总费用正整数单位：厘", type = "String", isNecessary = "Y", desc = "totalFee：总费用正整数单位：厘")
	private String totalFee;
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "ParaVo", isNecessary = "Y", desc = "para：保留字段")
	private List<ParaVo> para;
	
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
	public String getEssSubscribeId() {
		return essSubscribeId;
	}
	public void setEssSubscribeId(String essSubscribeId) {
		this.essSubscribeId = essSubscribeId;
	}
	public List<FeeInfoRspVo> getFeeInfo() {
		return feeInfo;
	}
	public void setFeeInfo(List<FeeInfoRspVo> feeInfo) {
		this.feeInfo = feeInfo;
	}
	public List<ParaVo> getPara() {
		return para;
	}
	public void setPara(List<ParaVo> para) {
		this.para = para;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
}
