package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;
import zte.net.ecsord.params.ecaop.resp.vo.CertifyInfoVo;
import zte.net.ecsord.params.ecaop.resp.vo.ExistedCustomerVo;
import zte.net.ecsord.params.ecaop.resp.vo.MainProductInfoVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BSSCustomerInfoResponse  extends ZteResponse{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8583914390035781359L;

	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name = "应答码", type = "String", isNecessary = "N", desc = "RespCode")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "应答信息", type = "String", isNecessary = "N", desc = "RespDesc")
	private String RespDesc ;
	@ZteSoftCommentAnnotationParam(name="客户信息",type="List",isNecessary="N",desc="客户信息")
	private List<ExistedCustomerVo> ExistedCustomer;
	@ZteSoftCommentAnnotationParam(name="责任人/使用人信息",type="CertifyInfoVo",isNecessary="N",desc="责任人/使用人信息")
	private CertifyInfoVo CertifyInfo;
	@ZteSoftCommentAnnotationParam(name="主产品信息",type="ParamsVo",isNecessary="N",desc="主产品信息")
	private List<MainProductInfoVo> MainProductInfo;
	@ZteSoftCommentAnnotationParam(name = "扩展字段", type = "List", isNecessary = "N", desc = "扩展字段")
	private List<BSSParaVo> Para;
	
	public List<BSSParaVo> getPara() {
		return Para;
	}
	public void setPara(List<BSSParaVo> para) {
		Para = para;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespDesc() {
		return RespDesc;
	}
	public void setRespDesc(String respDesc) {
		RespDesc = respDesc;
	}
	public List<ExistedCustomerVo> getExistedCustomer() {
		return ExistedCustomer;
	}
	public void setExistedCustomer(List<ExistedCustomerVo> existedCustomer) {
		ExistedCustomer = existedCustomer;
	}
	public CertifyInfoVo getCertifyInfo() {
		return CertifyInfo;
	}
	public void setCertifyInfo(CertifyInfoVo certifyInfo) {
		CertifyInfo = certifyInfo;
	}
	public List<MainProductInfoVo> getMainProductInfo() {
		return MainProductInfo;
	}
	public void setMainProductInfo(List<MainProductInfoVo> mainProductInfo) {
		MainProductInfo = mainProductInfo;
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
