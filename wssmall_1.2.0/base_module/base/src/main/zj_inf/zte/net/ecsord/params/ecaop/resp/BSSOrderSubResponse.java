package zte.net.ecsord.params.ecaop.resp;

import java.util.List;

import params.ZteResponse;
import zte.net.ecsord.params.bss.resp.BSSAccountResp;
import zte.net.ecsord.params.bss.resp.BSSOrderSubResp;
import zte.net.ecsord.params.ecaop.req.vo.BSSParaVo;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

public class BSSOrderSubResponse  extends ZteResponse{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8583914390035781359L;

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
	@ZteSoftCommentAnnotationParam(name = "保留字段", type = "String", isNecessary = "Y", desc = "para：保留字段")
	private List<BSSParaVo> Para;// 不管 * 保留字段
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
	public List<BSSParaVo> getPara() {
		return Para;
	}
	public void setPara(List<BSSParaVo> para) {
		Para = para;
	}

}
