package params.pay.req;

import com.ztesoft.api.ApiRuleException;

import params.ZteRequest;

/**
 * 支付成功后银行重定向地址
 * @作者 MoChunrun
 * @创建日期 2013-10-21 
 * @版本 V 1.0
 */
public class BankRedirectReq extends ZteRequest {

	private String UPTRANSEQ;
	private String RETNCODE;
	private String RETNINFO;
	private String ORDERSEQ;
	private String ORDERAMOUNT;
	private String ENCODETYPE;
	private String TRANDATE;
	private String SIGN;
	public String getUPTRANSEQ() {
		return UPTRANSEQ;
	}
	public void setUPTRANSEQ(String uPTRANSEQ) {
		UPTRANSEQ = uPTRANSEQ;
	}
	public String getRETNCODE() {
		return RETNCODE;
	}
	public void setRETNCODE(String rETNCODE) {
		RETNCODE = rETNCODE;
	}
	public String getRETNINFO() {
		return RETNINFO;
	}
	public void setRETNINFO(String rETNINFO) {
		RETNINFO = rETNINFO;
	}
	public String getORDERSEQ() {
		return ORDERSEQ;
	}
	public void setORDERSEQ(String oRDERSEQ) {
		ORDERSEQ = oRDERSEQ;
	}
	public String getORDERAMOUNT() {
		return ORDERAMOUNT;
	}
	public void setORDERAMOUNT(String oRDERAMOUNT) {
		ORDERAMOUNT = oRDERAMOUNT;
	}
	public String getENCODETYPE() {
		return ENCODETYPE;
	}
	public void setENCODETYPE(String eNCODETYPE) {
		ENCODETYPE = eNCODETYPE;
	}
	public String getTRANDATE() {
		return TRANDATE;
	}
	public void setTRANDATE(String tRANDATE) {
		TRANDATE = tRANDATE;
	}
	public String getSIGN() {
		return SIGN;
	}
	public void setSIGN(String sIGN) {
		SIGN = sIGN;
	}
	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getApiMethodName() {
		// TODO Auto-generated method stub
		return null;
	}
}
