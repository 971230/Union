package com.ztesoft.net.mall.core.action.desposit;

import java.util.Map;

import params.bank.req.BankReq;
import params.payment.req.EditPaymentListReq;
import services.AdminUserInf;
import services.BankInf;
import services.PaymentListInf;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.eop.sdk.utils.ValidCodeServlet;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.context.webcontext.WebSessionContext;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.model.PartnerDespost;
import com.ztesoft.net.mall.core.model.PayReponse;
import com.ztesoft.net.mall.core.model.PayRequest;
import com.ztesoft.net.mall.core.model.PaymentList;
import com.ztesoft.net.mall.core.service.IPartnerAccountManager;
import com.ztesoft.net.mall.core.service.IPartnerManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import commons.CommonTools;

public class DepositChargeAction extends WWAction{
		
	private String partnerId;
	private int amount;
	private String comments;
	private String bankCode;
	private String validCode;
	private String paySource;
	private String userFlag;
	private String lan_id;
	private String lan_name;
	

	
	
	protected String json ;
	
	
	private Partner partner ;
	private PartnerAccount partnerAccount;
	private Bank bank;
	
	protected IDespostHander despostHander;
	private IPartnerManager partnerManager;
	private IPartnerAccountManager accountManager;
	private AdminUserInf adminUserServ;
	private BankInf bankServ;
	protected PaymentListInf paymentListServ;
	
	/**
	 * 
	 * @function 预存金充值功能
	 */
	@SuppressWarnings("unchecked")
	public String depositCharge(){
		
		try {
			
			partner = partnerManager.getPartnerByCurrentUserId(CommonTools.getUserId(), partner);
			//partnerAccount = accountManager.getAccountByCurrentUserId();
			if(ManagerUtils.isFirstPartner()){				//判断是否为一级分销商
				userFlag = "T";
			}else {
				userFlag = "F";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "charge";
	}
	
	public String depositChargeCfm(){
		partner = this.despostHander.queryPartnerByID(partnerId);
		BankReq bankReq = new BankReq();
		bankReq.setBank_code(bankCode);
		bank = bankServ.getBankByCode(bankReq).getBank();
		lan_name = StringUtil.getLanName(lan_id);
		return "chargeConfirm";
	}
	
	public String depositChargeEnd(){
		WebSessionContext sessonContext = ThreadContextHolder.getSessionContext();
		Object realCode = sessonContext.getAttribute(ValidCodeServlet.SESSION_VALID_CODE+"pay");
		
		if(!validCode.equals(realCode)){
			
			this.json = "{result:0,message:'请输入正确的验证码'}";	
		}else {
			
			try{
				
				PayRequest payReq = new PayRequest(new Double(amount),bankCode,paySource,"despostHander",Consts.PAY_DEPOSIT); //wssmall/shop/deposi
				PayReponse payRsp =bankServ.bankPay(payReq);
				
				//写入充值订单
				PartnerDespost despost = new PartnerDespost();
				despost.setTransaction_id(payRsp.getTransaction_id());
				despost.setPartner_id(partnerId);
				despost.setSequ(0);
				despost.setOp_type("002");
				despost.setOp_time(Consts.SYSDATE);
				despost.setDespost_state("00A");
				despost.setPayed_amount(StringUtil.transMoney(amount));
				despost.setBank_code(bankCode);
				despost.setLan_id(lan_id);
				this.despostHander.depositChange(despost);
				
				
				PaymentList paymentList = new PaymentList();
				paymentList.setOrder_id(despost.getDepost_id());
				paymentList.setTransaction_id(payRsp.getTransaction_id());
				
				Map result = payRsp.getResult();
				result.put("ORDERREQTRANSEQ",despost.getDepost_id());
				EditPaymentListReq req = new EditPaymentListReq();
		        req.setTransactionId(paymentList.getTransaction_id());
		        req.setPaymentList(paymentList);
		        paymentListServ.editPaymentList(req);
				this.json = "{result:1,message:'充值'," +payRsp.toString() + "}";
				
				
			}catch (RuntimeException e) {
				e.printStackTrace();
				this.json = "{result:0,message:'数据校验不通过'}";	
			}
		}
		return WWAction.JSON_MESSAGE;
	}
	public String goBank(){
		partner = this.despostHander.queryPartnerByID(partnerId);
		BankReq bankReq = new BankReq();
		bankReq.setBank_code(bankCode);
		bank = bankServ.getBankByCode(bankReq).getBank();
		return "chargeEnd";
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public IDespostHander getDespostHander() {
		return despostHander;
	}

	public void setDespostHander(IDespostHander despostHander) {
		this.despostHander = despostHander;
	}


	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getValidCode() {
		return validCode;
	}

	public IPartnerManager getPartnerManager() {
		return partnerManager;
	}

	public void setPartnerManager(IPartnerManager partnerManager) {
		this.partnerManager = partnerManager;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	@Override
	public String getJson() {
		return json;
	}

	@Override
	public void setJson(String json) {
		this.json = json;
	}

	public String getPaySource() {
		return paySource;
	}

	public void setPaySource(String paySource) {
		this.paySource = paySource;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}



	public IPartnerAccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(IPartnerAccountManager accountManager) {
		this.accountManager = accountManager;
	}

	public String getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getLan_id() {
		return lan_id;
	}

	public void setLan_id(String lan_id) {
		this.lan_id = lan_id;
	}

	public String getLan_name() {
		return lan_name;
	}

	public void setLan_name(String lan_name) {
		this.lan_name = lan_name;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	public BankInf getBankServ() {
		return bankServ;
	}

	public void setBankServ(BankInf bankServ) {
		this.bankServ = bankServ;
	}

	public PaymentListInf getPaymentListServ() {
		return paymentListServ;
	}

	public void setPaymentListServ(PaymentListInf paymentListServ) {
		this.paymentListServ = paymentListServ;
	}
	
}
