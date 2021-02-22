package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.resp.vo.ProdAndActivityInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.CertInfoRspVo;
import zte.net.ecsord.params.ecaop.resp.vo.ErrorListRspVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
/**
 * @author XMJ
 * @date 2015-06-24
 *
 */
public class Check23to4Resp  extends ZteResponse{
	private static final long serialVersionUID = 1L;
	
	
	@ZteSoftCommentAnnotationParam(name = "返回编码", type = "String", isNecessary = "N", desc = "code:返回编码")
	private String code;
	
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "N", desc = "detail:返回描述")
	private String detail;
	
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "certType:证件类型")
	private String certType;
	
	@ZteSoftCommentAnnotationParam(name = "证件号码", type = "String", isNecessary = "N", desc = "certNum:证件号码")
	private String certNum;
	
	@ZteSoftCommentAnnotationParam(name = "客户名称", type = "String", isNecessary = "N", desc = "customerName:客户名称")
	private String customerName;
	
	@ZteSoftCommentAnnotationParam(name = "证件类型", type = "String", isNecessary = "N", desc = "certAdress:证件地址")
	private String certAdress;
	
	@ZteSoftCommentAnnotationParam(name = "最低消费", type = "String", isNecessary = "N", desc = "minConsume:最低消费")
	private String minConsume;

	
	@ZteSoftCommentAnnotationParam(name = "错误列表", type = "String", isNecessary = "N", desc = "responseInfo:省份23转4校验错误列表")
	private List<ErrorListRspVo> responseInfo;
	
	@ZteSoftCommentAnnotationParam(name = "产品合约信息", type = "String", isNecessary = "N", desc = "activityInfo:产品合约信息")
	private List<ProdAndActivityInfoRspVo> activityInfo;
	
	@ZteSoftCommentAnnotationParam(name = "客户资料信息", type = "String", isNecessary = "N", desc = "certInfo:客户资料信息")
	private List<CertInfoRspVo> certInfo;
	
	

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

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCertAdress() {
		return certAdress;
	}

	public void setCertAdress(String certAdress) {
		this.certAdress = certAdress;
	}

	public String getMinConsume() {
		return minConsume;
	}

	public void setMinConsume(String minConsume) {
		this.minConsume = minConsume;
	}

	

	public List<ProdAndActivityInfoRspVo> getActivityInfo() {
		return activityInfo;
	}

	public void setActivityInfo(List<ProdAndActivityInfoRspVo> activityInfo) {
		this.activityInfo = activityInfo;
	}

	

	public List<ErrorListRspVo> getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(List<ErrorListRspVo> responseInfo) {
		this.responseInfo = responseInfo;
	}

	public List<CertInfoRspVo> getCertInfo() {
		return certInfo;
	}

	public void setCertInfo(List<CertInfoRspVo> certInfo) {
		this.certInfo = certInfo;
	}

	

	
	
}
