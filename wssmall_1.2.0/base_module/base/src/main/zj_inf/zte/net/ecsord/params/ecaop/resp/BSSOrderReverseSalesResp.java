package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BSSOrderReverseSalesResp extends ZteResponse{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3981677932302450067L;	
	@ZteSoftCommentAnnotationParam(name="返回编码",type="String",isNecessary="Y",desc="code：返回编码")
	private String code;
	@ZteSoftCommentAnnotationParam(name="返回描述",type="String",isNecessary="Y",desc="detail：返回描述")
	private String detail;
	@ZteSoftCommentAnnotationParam(name = "应答码", type = "String", isNecessary = "N", desc = "RespCode")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "应答信息", type = "String", isNecessary = "N", desc = "RespDesc")
	private String RespDesc ;
	@ZteSoftCommentAnnotationParam(name="当前省分返销订单ID",type="String",isNecessary="Y",desc="ProvRollBackOrderID：当前省分返销订单ID")
	private String ProvRollBackOrderID;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="List",isNecessary="N",desc="paras：保留字段")
	private List<BSSParaVo> paras;
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
	public String getProvRollBackOrderID() {
		return ProvRollBackOrderID;
	}
	public void setProvRollBackOrderID(String provRollBackOrderID) {
		ProvRollBackOrderID = provRollBackOrderID;
	}
	public List<BSSParaVo> getParas() {
		return paras;
	}
	public void setParas(List<BSSParaVo> paras) {
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
