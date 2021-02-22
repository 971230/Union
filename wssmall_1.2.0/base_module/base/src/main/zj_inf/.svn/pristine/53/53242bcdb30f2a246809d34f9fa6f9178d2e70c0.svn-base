package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.ParaVo;

/**
 * 老用户续约提交
 * 2016-05-03
 * @author duan.shaochu
 *
 */
public class OldUserRenActivitySubmitResp extends ZteResponse {

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	private String taxNo; // 税控码
	private String acceptanceTp; // 受理单模板编码
	private String acceptanceMode; // 0：套打；1：白打
	private String acceptanceForm; // 受理单打印内容
	private List<ParaVo> para; // 保留内容
	
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
	
	public String getAcceptanceForm() {
		return acceptanceForm;
	}
	
	public void setAcceptanceForm(String acceptanceForm) {
		this.acceptanceForm = acceptanceForm;
	}
	
	public List<ParaVo> getPara() {
		return para;
	}
	
	public void setPara(List<ParaVo> para) {
		this.para = para;
	}
	
	

}
