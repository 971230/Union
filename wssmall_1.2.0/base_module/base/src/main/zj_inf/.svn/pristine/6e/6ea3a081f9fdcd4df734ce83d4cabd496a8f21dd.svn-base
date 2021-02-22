/**
 * 
 */
package zte.net.ecsord.params.bss.resp;

import zte.net.ecsord.params.base.resp.ZteBusiResponse;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

/**
 * @author zengxianlian
 * @version 2016-03-15
 * @see 开户处理提交
 */
public class BSSOrderSubResp extends ZteBusiResponse {
	@ZteSoftCommentAnnotationParam(name = "返回代码", type = "String", isNecessary = "Y", desc = "code：返回代码")
	private String RespCode;
	@ZteSoftCommentAnnotationParam(name = "返回描述", type = "String", isNecessary = "Y", desc = "detail：返回描述")
	private String RespDesc;
	@ZteSoftCommentAnnotationParam(name="税控码",type="String",isNecessary="N",desc="taxNo：税控码")
	private String TaxInvoiceNo;
	@ZteSoftCommentAnnotationParam(name="税务登记号",type="String",isNecessary="N",desc="税务登记号")
	private String TaxRegistNum;
	@ZteSoftCommentAnnotationParam(name="机器编码",type="String",isNecessary="N",desc="机器编码")
	private String MachineCode;
	@ZteSoftCommentAnnotationParam(name="保留字段",type="ParamsVo",isNecessary="N",desc="para：保留字段")
	private BSSParaVo Para;
	public String getCode() {
		return RespCode;
	}
	public void setCode(String code) {
		this.RespCode = code;
	}
	public String getDetail() {
		return RespDesc;
	}
	public void setDetail(String detail) {
		this.RespDesc = detail;
	}
	public BSSParaVo getPara() {
		return Para;
	}
	public void setPara(BSSParaVo para) {
		this.Para = para;
	}
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
	public String getTaxInvoiceNo() {
		return TaxInvoiceNo;
	}
	public void setTaxInvoiceNo(String taxInvoiceNo) {
		this.TaxInvoiceNo = taxInvoiceNo;
	}
	public String getTaxRegistNum() {
		return TaxRegistNum;
	}
	public void setTaxRegistNum(String taxRegistNum) {
		this.TaxRegistNum = taxRegistNum;
	}
	public String getMachineCode() {
		return MachineCode;
	}
	public void setMachineCode(String machineCode) {
		this.MachineCode = machineCode;
	}
}
