package com.ztesoft.net.mall.core.action.desposit;

import params.req.PartnerInfoOneReq;
import params.resp.PartnerInfoResp;
import services.PartnerInf;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.service.IPartnerAccountManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class PartnerAccountAction extends WWAction {
	
	private String partnerid ;
	
	protected String json;
	private String account_id;
	
	private IPartnerAccountManager accountManager ;
	private PartnerAccount partnerAccount ;
	private PartnerInf partnerServ;
	
	public String add()throws Exception{
		partnerAccount.setFrost_deposit(0);
		partnerAccount.setCreate_date(Consts.SYSDATE);
		partnerAccount.setAccount_amount(0);
		partnerAccount.setAccount_type("00A");
		accountManager.add(partnerAccount);
		return "list_acct";
	}
	
	
	public String add_act()throws Exception{
		return "act_input";
	}
	
	/**
	 * 前台门户保存银行信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String save_add()throws Exception{
		try {
			
			
			String userid=ManagerUtils.getUserId();
			PartnerInfoOneReq partnerInfoOneReq = new PartnerInfoOneReq();
			partnerInfoOneReq.setUserid(userid);
			PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneReq);
			Partner partner = new Partner();
			if(partnerInfoResp != null){
				partner = partnerInfoResp.getPartner();
			}
			partnerAccount.setPartner_id(partner.getPartner_id());
			
			
			partnerAccount.setFrost_deposit(0);
			partnerAccount.setAccount_id(System.currentTimeMillis()+"");
			partnerAccount.setCreate_date(Consts.SYSDATE);
			partnerAccount.setAccount_amount(0);
			accountManager.add(partnerAccount);
			
			this.json = "{'result':0,'message':'添加成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'添加失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String list_act(){
	   this.webpage = this.accountManager.list(partnerAccount,this.getPage(),this.getPageSize());
		return "list_acct";
	}
	
	
	public String delete(){
		try {
			accountManager.delete(account_id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (RuntimeException e) {
			this.json = "{'result':1,'message':'删除失败'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	public IPartnerAccountManager getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(IPartnerAccountManager accountManager) {
		this.accountManager = accountManager;
	}
	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}
	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}
	public String getPartnerId() {
		return partnerid;
	}
	public void setPartnerId(String partnerId) {
		this.partnerid = partnerId;
	}
	@Override
	public String getJson() {
		return json;
	}
	@Override
	public void setJson(String json) {
		this.json = json;
	}


	public String getPartnerid() {
		return partnerid;
	}


	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}


	public String getAccount_id() {
		return account_id;
	}


	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}


	public PartnerInf getPartnerServ() {
		return partnerServ;
	}


	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}


	
}