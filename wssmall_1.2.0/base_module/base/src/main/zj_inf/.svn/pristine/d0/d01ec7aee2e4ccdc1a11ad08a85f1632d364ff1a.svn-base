/**
 * 
 */
package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParamsVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * 
 * @author chen.yi
 * @date 2016-1-5 下午4:07:33
 * @see 4g存费送费正式提交
 */
public class CunFeeSongFee4GSubmitResp extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="para：保留字段")
	private List<ParamsVo> para;
	@ZteSoftCommentAnnotationParam(name="BSS订单ID",type="String",isNecessary="N",desc="bssOrderId：BSS订单ID")
	private String bssOrderId;
	@ZteSoftCommentAnnotationParam(name="ESS总部订单ID",type="String",isNecessary="N",desc="essOrderId：ESS总部订单ID")
	private String essOrderId;
	@ZteSoftCommentAnnotationParam(name="税控码",type="String",isNecessary="N",desc="taxNo：税控码")
	private String taxNo;
	@ZteSoftCommentAnnotationParam(name="受理单模板编码",type="String",isNecessary="N",desc="acceptanceTp：受理单模板编码")
	private String acceptanceTp;
	@ZteSoftCommentAnnotationParam(name="0：套打；1：白打",type="String",isNecessary="N",desc="acceptanceMode：0：套打；1：白打")
	private String acceptanceMode;
	@ZteSoftCommentAnnotationParam(name="ESS订单交易流水",type="String",isNecessary="Y",desc="provOrderId：ESS订单交易流水  为正式提交时使用")
	private String provOrderId;
	@ZteSoftCommentAnnotationParam(name="受理单打印内容",type="String",isNecessary="N",desc="acceptanceForm：受理单打印内容")
	private String acceptanceForm;
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
	
	public List<ParamsVo> getPara() {
        return para;
    }
    public void setPara(List<ParamsVo> para) {
        this.para = para;
    }
    public String getBssOrderId() {
		return bssOrderId;
	}
	public void setBssOrderId(String bssOrderId) {
		this.bssOrderId = bssOrderId;
	}
	public String getEssOrderId() {
		return essOrderId;
	}
	public void setEssOrderId(String essOrderId) {
		this.essOrderId = essOrderId;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getAcceptanceTp() {
		return acceptanceTp;
	}
	public void setAcceptanceTp(String acceptanceTp) {
		this.acceptanceTp = acceptanceTp;
	}
	public String getAcceptanceMode() {
		return acceptanceMode;
	}
	public void setAcceptanceMode(String acceptanceMode) {
		this.acceptanceMode = acceptanceMode;
	}
	public String getProvOrderId() {
		return provOrderId;
	}
	public void setProvOrderId(String provOrderId) {
		this.provOrderId = provOrderId;
	}
	public String getAcceptanceForm() {
		return acceptanceForm;
	}
	public void setAcceptanceForm(String acceptanceForm) {
		this.acceptanceForm = acceptanceForm;
	}
}
