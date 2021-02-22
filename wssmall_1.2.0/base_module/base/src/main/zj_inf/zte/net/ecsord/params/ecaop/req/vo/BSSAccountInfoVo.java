package zte.net.ecsord.params.ecaop.req.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zengxianlian
 * @version BSS
 *
 */
public class BSSAccountInfoVo implements Serializable{
	
	private String AccountName;//Y账户名称
	private String AccountPasswd;//Y账户密码
	private String AccountPayType;//Y账户付费方式10：现金 11：现金支票 12：转账支票 13：信用卡 14：缴费卡 15：托收 16：代收17：银行代扣 
	private String AcctType;//Y账户类型0-免付  1-准预付(营账内实现的预付)  2-后付  3-预付费(智能网)
	private String AccountAddr;//Y账户地址      
	private String AccountZip;//Y账户邮编      
	private String AccountPhone;//N电话          
	private String AccountBankCode;//N银行编码      
	private String AccountBankBranch;//N银行分行      
	private String AccountBankClName;//N托收户名      
	private String AccountBankCardID;//N银行账号      
	private String ContactPerson;//N联系人        
	private String ContactPhone;//N联系电话      
	private String ContactZip;//N联系人邮编    
	private String ContactAddr;//N联系人地址    
	private String PaymentAddr;//N付款地址      
	private String BillRecvAddr;//N账单接收地址  
	private String BillRecvName;//N账单接收名称  
	private String BillRecvEmail;//N账单接收EMAIL 
	private String AcctManager;//N账户管理员名称
	private String BillSendCont;//Y账单递送内容01-账单，02-账单和详单，03-详单，04-发票，05-其他
	private String SendBillFlag;//Y账单寄送标志1-寄送，2-不寄送
	private List<BSSAccountRemarkVo> AccountRemark; // N 账户备注
	
	public String getAccountName() {
		if (StringUtils.isBlank(AccountName)) return null;
		return AccountName;
	}
	public void setAccountName(String accountName) {
		this.AccountName = accountName;
	}
	public String getAccountPasswd() {
		if (StringUtils.isBlank(AccountPasswd)) return null;
		return AccountPasswd;
	}
	public void setAccountPasswd(String accountPasswd) {
		this.AccountPasswd = accountPasswd;
	}
	public String getAccountPayType() {
		if (StringUtils.isBlank(AccountPayType)) return null;
		return AccountPayType;
	}
	public void setAccountPayType(String accountPayType) {
		this.AccountPayType = accountPayType;
	}
	public String getAcctType() {
		if (StringUtils.isBlank(AcctType)) return null;
		return AcctType;
	}
	public void setAcctType(String acctType) {
		this.AcctType = acctType;
	}
	public String getAccountAddr() {
		if (StringUtils.isBlank(AccountAddr)) return null;
		return AccountAddr;
	}
	public void setAccountAddr(String accountAddr) {
		this.AccountAddr = accountAddr;
	}
	public String getAccountZip() {
		if (StringUtils.isBlank(AccountZip)) return null;
		return AccountZip;
	}
	public void setAccountZip(String accountZip) {
		this.AccountZip = accountZip;
	}
	public String getAccountPhone() {
		if (StringUtils.isBlank(AccountPhone)) return null;
		return AccountPhone;
	}
	public void setAccountPhone(String accountPhone) {
		this.AccountPhone = accountPhone;
	}
	public String getAccountBankCode() {
		if (StringUtils.isBlank(AccountBankCode)) return null;
		return AccountBankCode;
	}
	public void setAccountBankCode(String accountBankCode) {
		this.AccountBankCode = accountBankCode;
	}
	public String getAccountBankBranch() {
		if (StringUtils.isBlank(AccountBankBranch)) return null;
		return AccountBankBranch;
	}
	public void setAccountBankBranch(String accountBankBranch) {
		this.AccountBankBranch = accountBankBranch;
	}
	public String getAccountBankClName() {
		if (StringUtils.isBlank(AccountBankClName)) return null;
		return AccountBankClName;
	}
	public void setAccountBankClName(String accountBankClName) {
		this.AccountBankClName = accountBankClName;
	}
	public String getAccountBankCardID() {
		if (StringUtils.isBlank(AccountBankCardID)) return null;
		return AccountBankCardID;
	}
	public void setAccountBankCardID(String accountBankCardID) {
		this.AccountBankCardID = accountBankCardID;
	}
	public String getContactPerson() {
		if (StringUtils.isBlank(ContactPerson)) return null;
		return ContactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.ContactPerson = contactPerson;
	}
	public String getContactPhone() {
		if (StringUtils.isBlank(ContactPhone)) return null;
		return ContactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.ContactPhone = contactPhone;
	}
	public String getContactZip() {
		if (StringUtils.isBlank(ContactZip)) return null;
		return ContactZip;
	}
	public void setContactZip(String contactZip) {
		this.ContactZip = contactZip;
	}
	public String getContactAddr() {
		if (StringUtils.isBlank(ContactAddr)) return null;
		return ContactAddr;
	}
	public void setContactAddr(String contactAddr) {
		this.ContactAddr = contactAddr;
	}
	public String getPaymentAddr() {
		if (StringUtils.isBlank(PaymentAddr)) return null;
		return PaymentAddr;
	}
	public void setPaymentAddr(String paymentAddr) {
		this.PaymentAddr = paymentAddr;
	}
	public String getBillRecvAddr() {
		if (StringUtils.isBlank(BillRecvAddr)) return null;
		return BillRecvAddr;
	}
	public void setBillRecvAddr(String billRecvAddr) {
		this.BillRecvAddr = billRecvAddr;
	}
	public String getBillRecvName() {
		if (StringUtils.isBlank(BillRecvName)) return null;
		return BillRecvName;
	}
	public void setBillRecvName(String billRecvName) {
		this.BillRecvName = billRecvName;
	}
	public String getBillRecvEmail() {
		if (StringUtils.isBlank(BillRecvEmail)) return null;
		return BillRecvEmail;
	}
	public void setBillRecvEmail(String billRecvEmail) {
		this.BillRecvEmail = billRecvEmail;
	}
	public String getAcctManager() {
		if (StringUtils.isBlank(AcctManager)) return null;
		return AcctManager;
	}
	public void setAcctManager(String acctManager) {
		this.AcctManager = acctManager;
	}
	public String getBillSendCont() {
		if (StringUtils.isBlank(BillSendCont)) return null;
		return BillSendCont;
	}
	public void setBillSendCont(String billSendCont) {
		this.BillSendCont = billSendCont;
	}
	public String getSendBillFlag() {
		if (StringUtils.isBlank(SendBillFlag)) return null;
		return SendBillFlag;
	}
	public void setSendBillFlag(String sendBillFlag) {
		this.SendBillFlag = sendBillFlag;
	}
	public List<BSSAccountRemarkVo> getAccountRemarkVo() {
		if (AccountRemark==null || AccountRemark.size() <= 0) return null;
		return AccountRemark;
	}
	public void setAccountRemarkVo(List<BSSAccountRemarkVo> accountRemarkVo) {
		this.AccountRemark = accountRemarkVo;
	}

}
