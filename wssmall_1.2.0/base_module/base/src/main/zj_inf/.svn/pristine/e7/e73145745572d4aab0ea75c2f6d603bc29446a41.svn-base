/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;
import zte.net.ecsord.params.ecaop.resp.vo.OpenDealApplyFeeInfoVo;

/**
 * @author ZX
 * @version 2015-05-07
 * @see 开户处理申请返回对象
 * 
 */
public class OpenDealApplyResponse extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;

	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;

	@ZteSoftCommentAnnotationParam(name = "收费信息", type = "String", isNecessary = "Y", desc = "feeInfo：订单树es_attr_fee_info 收费信息（ESS从BSS获取到的）")
	private OpenDealApplyFeeInfoVo feeInfo;

	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "para：保留字段")
	private List<ParamsVo> para;

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

	public OpenDealApplyFeeInfoVo getFeeInfo() {
		return feeInfo;
	}

	public void setFeeInfo(OpenDealApplyFeeInfoVo feeInfo) {
		this.feeInfo = feeInfo;
	}

    public List<ParamsVo> getPara() {
        return para;
    }

    public void setPara(List<ParamsVo> para) {
        this.para = para;
    }
}
