package params.pay.resp;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import params.ZteResponse;

/**
 * 去支付返回
 * @作者 MoChunrun
 * @创建日期 2013-10-21 
 * @版本 V 1.0
 */
public class GoToPayResp extends ZteResponse {
	private static Logger logger = Logger.getLogger(GoToPayResp.class);
	private String bankURL;
	private String MERCHANTID;
	private String SUBMERCHANTID;
	private String ORDERSEQ;
	private String ORDERREQTRANSEQ;
	private String ORDERDATE;
	private String ORDERAMOUNT;
	private String PRODUCTAMOUNT;
	private String ATTACHAMOUNT;
	private String CURTYPE;
	private String ENCODETYPE;
	private String MERCHANTURL;
	private String BACKMERCHANTURL;
	private String BANKID;
	private String ATTACH;
	private String BUSICODE;
	private String TMNUM;
	private String CUSTOMERID;
	private String PRODUCTID;
	private String PRODUCTDESC;
	private String MAC;
	private String CLIENTIP;
	public String getBankURL() {
		return bankURL;
	}
	public void setBankURL(String bankURL) {
		this.bankURL = bankURL;
	}
	public String getMERCHANTID() {
		return MERCHANTID;
	}
	public void setMERCHANTID(String mERCHANTID) {
		MERCHANTID = mERCHANTID;
	}
	public String getSUBMERCHANTID() {
		return SUBMERCHANTID;
	}
	public void setSUBMERCHANTID(String sUBMERCHANTID) {
		SUBMERCHANTID = sUBMERCHANTID;
	}
	public String getORDERSEQ() {
		return ORDERSEQ;
	}
	public void setORDERSEQ(String oRDERSEQ) {
		ORDERSEQ = oRDERSEQ;
	}
	public String getORDERREQTRANSEQ() {
		return ORDERREQTRANSEQ;
	}
	public void setORDERREQTRANSEQ(String oRDERREQTRANSEQ) {
		ORDERREQTRANSEQ = oRDERREQTRANSEQ;
	}
	public String getORDERDATE() {
		return ORDERDATE;
	}
	public void setORDERDATE(String oRDERDATE) {
		ORDERDATE = oRDERDATE;
	}
	public String getORDERAMOUNT() {
		return ORDERAMOUNT;
	}
	public void setORDERAMOUNT(String oRDERAMOUNT) {
		ORDERAMOUNT = oRDERAMOUNT;
	}
	public String getPRODUCTAMOUNT() {
		return PRODUCTAMOUNT;
	}
	public void setPRODUCTAMOUNT(String pRODUCTAMOUNT) {
		PRODUCTAMOUNT = pRODUCTAMOUNT;
	}
	public String getATTACHAMOUNT() {
		return ATTACHAMOUNT;
	}
	public void setATTACHAMOUNT(String aTTACHAMOUNT) {
		ATTACHAMOUNT = aTTACHAMOUNT;
	}
	public String getCURTYPE() {
		return CURTYPE;
	}
	public void setCURTYPE(String cURTYPE) {
		CURTYPE = cURTYPE;
	}
	public String getENCODETYPE() {
		return ENCODETYPE;
	}
	public void setENCODETYPE(String eNCODETYPE) {
		ENCODETYPE = eNCODETYPE;
	}
	public String getMERCHANTURL() {
		return MERCHANTURL;
	}
	public void setMERCHANTURL(String mERCHANTURL) {
		MERCHANTURL = mERCHANTURL;
	}
	public String getBACKMERCHANTURL() {
		return BACKMERCHANTURL;
	}
	public void setBACKMERCHANTURL(String bACKMERCHANTURL) {
		BACKMERCHANTURL = bACKMERCHANTURL;
	}
	public String getBANKID() {
		return BANKID;
	}
	public void setBANKID(String bANKID) {
		BANKID = bANKID;
	}
	public String getATTACH() {
		return ATTACH;
	}
	public void setATTACH(String aTTACH) {
		ATTACH = aTTACH;
	}
	public String getBUSICODE() {
		return BUSICODE;
	}
	public void setBUSICODE(String bUSICODE) {
		BUSICODE = bUSICODE;
	}
	public String getTMNUM() {
		return TMNUM;
	}
	public void setTMNUM(String tMNUM) {
		TMNUM = tMNUM;
	}
	public String getCUSTOMERID() {
		return CUSTOMERID;
	}
	public void setCUSTOMERID(String cUSTOMERID) {
		CUSTOMERID = cUSTOMERID;
	}
	public String getPRODUCTID() {
		return PRODUCTID;
	}
	public void setPRODUCTID(String pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}
	public String getPRODUCTDESC() {
		return PRODUCTDESC;
	}
	public void setPRODUCTDESC(String pRODUCTDESC) {
		PRODUCTDESC = pRODUCTDESC;
	}
	public String getMAC() {
		return MAC;
	}
	public void setMAC(String mAC) {
		MAC = mAC;
	}
	public String getCLIENTIP() {
		return CLIENTIP;
	}
	public void setCLIENTIP(String cLIENTIP) {
		CLIENTIP = cLIENTIP;
	}
	@Override
	public String toString() {
		Field filds[] = this.getClass().getDeclaredFields();
		for(Field f:filds){
			try {
				logger.info(f.getName()+":="+f.get(this));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return super.toString();
	}
}
