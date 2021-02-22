package zte.net.ecsord.params.hs.resp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import params.ZteResponse;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 *  退货入库传输SAP
 * @作者 Rapon
 * @创建日期 2016-07-23
 * @版本 V 1.0
 */
@JsonIgnoreProperties(value = { "error_code","error_msg",
		"userSessionId","error_cause","body","resp","exec_path","rule_id","rule_name","item","result" }) 
public class ReturnWarehousingResp extends ZteResponse {
	@ZteSoftCommentAnnotationParam(name = "退货申请单号", type = "String", isNecessary = "N", desc = "退货申请单号")
	private String ZTHNUM;
	@ZteSoftCommentAnnotationParam(name = "项目号", type = "String", isNecessary = "N", desc = "项目号")
	private String POSNR;
	@ZteSoftCommentAnnotationParam(name = "串码", type = "String", isNecessary = "Y", desc = "串码")
	private String CUANM;
	@ZteSoftCommentAnnotationParam(name = "‘S’成功；’E’失败", type = "String", isNecessary = "N", desc = "‘S’成功；’E’失败")
	private String TYPE;
	@ZteSoftCommentAnnotationParam(name = "返回信息描述", type = "String", isNecessary = "N", desc = "返回信息描述")
	private String MESSAGE;
	public String getZTHNUM() {
		return ZTHNUM;
	}
	public void setZTHNUM(String zTHNUM) {
		ZTHNUM = zTHNUM;
	}
	public String getPOSNR() {
		return POSNR;
	}
	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}
	public String getCUANM() {
		return CUANM;
	}
	public void setCUANM(String cUANM) {
		CUANM = cUANM;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
}
