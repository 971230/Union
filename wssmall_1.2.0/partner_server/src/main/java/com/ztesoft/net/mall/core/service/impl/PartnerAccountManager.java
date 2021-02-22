package com.ztesoft.net.mall.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import params.org.req.OrgReq;
import params.req.PartnerInfoOneReq;
import params.resp.PartnerInfoResp;
import services.AdminUserInf;
import services.PartnerInf;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.action.desposit.model.AgentDepositRespDto;
import com.ztesoft.net.mall.core.service.IPartnerAccountManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class PartnerAccountManager extends BaseSupport<PartnerAccount> implements IPartnerAccountManager {

	
	private PartnerInf partnerServ;
	
	private AdminUserInf adminUserServ;
	
	@Override
	public boolean add(PartnerAccount partnerAccount){
		
		partnerAccount.setAccount_id(System.currentTimeMillis()+"");
		this.baseDaoSupport.insert("partner_account", partnerAccount);
		return true;
	}
	
	
	@Override
	public int delete(String account_id){
		String userid=ManagerUtils.getUserId();
		PartnerInfoOneReq partnerInfoOneReq = new PartnerInfoOneReq();
		partnerInfoOneReq.setUserid(userid);
		PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneReq);
		Partner partner = new Partner();
		if(partnerInfoResp != null){
			partner = partnerInfoResp.getPartner();
		}
		String sql="delete from ES_PARTNER_ACCOUNT  where  partner_id = ?  and  ACCOUNT_ID = ? ";
		this.baseDaoSupport.execute(sql, partner.getPartner_id() ,account_id);
		
		return 1;
	}
	
	
	//获取分销商列表
	@Override
	public Page list(PartnerAccount pAccount, int page, int pageSize) {
		String userid=ManagerUtils.getUserId();
		
		PartnerInfoOneReq partnerInfoOneReq = new PartnerInfoOneReq();
		partnerInfoOneReq.setUserid(userid);
		PartnerInfoResp partnerInfoResp = partnerServ.getPartnerByUserId(partnerInfoOneReq);
		Partner partner = new Partner();
		if(partnerInfoResp != null){
			partner = partnerInfoResp.getPartner();
		}
		String sql="select * from es_partner_account a";
        String whereSql= " where partner_id = ? and account_type <>'00A'  ";
       
		sql+=whereSql;
		sql+=" order by a.create_date desc ";
		String countSql = "select count(*) from es_partner_account a "+whereSql;
	    Page webpage = this.daoSupport.queryForCPage(sql, page, pageSize,PartnerAccount.class,countSql,partner.getPartner_id());
	    
	  return webpage;
	}
	
	
	@Override
	public PartnerAccount getAccountById(String partnerId){
		String sql = "select a.partner_id,a.account_type,a.account_amount/100 account_amount,a.account_code,a.account_name," +
				"a.create_date,a.frost_deposit/100 frost_deposit from es_partner_account a where a.account_type = '00A'";
		PartnerAccount account = null;
		if(null != partnerId && !"".equals(partnerId)){
			sql += " and a.partner_id = ?";
		}else {
			return null;
		}
		List result = this.baseDaoSupport.queryForList(sql, PartnerAccount.class, partnerId);
		if(null != result && !result.isEmpty()){
			account = (PartnerAccount)result.get(0);
		}
		return account;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PartnerAccount getAccountByCurrentUserId(){
		
		String sql = "select b.partner_id,b.account_type,nvl(b.account_amount,0)/100 account_amount," +
				"b.account_code,b.account_name,a.create_date,nvl(a.frost_deposit,0)/100 frost_deposit from es_partner a, " +
				"es_partner_account b where a.partner_id = b.partner_id and a.userid = ? and a.sequ = 0";
		
		PartnerAccount partnerAccount = this.baseDaoSupport.queryForObject(sql, PartnerAccount.class, ManagerUtils.getUserId());
		if(null == partnerAccount){
			partnerAccount = new PartnerAccount();
			partnerAccount.setAccount_amount(0);
			partnerAccount.setFrost_deposit(0);
		}
		
		if(ManagerUtils.isFirstPartner()){
			try {
				
				Map agent = new HashMap();
				agent.put("requestTime", StringUtil.getTransDate());
				agent.put("requestId", StringUtil.getTransDate() + (int)(Math.random()*10000));
				OrgReq orgReq = new OrgReq();
				orgReq.setStaff_code(ManagerUtils.getAdminUser().getRelcode());
				Map agenInfo = adminUserServ.getAgentOrgInfo(orgReq).getOrgInfo();
				agent.put("unionOrgCode",agenInfo.get("union_org_code").toString());
				//agent.put("unionOrgCode","7310118222");
				AgentDepositRespDto retObj =null;
				
				String sqlMoney = "select sum(a.deposit)/100 deposit from es_partner a where a.parent_userid = ?";
				int childMoney = this.baseDaoSupport.queryForInt(sqlMoney, ManagerUtils.getUserId());
				
				if(null != retObj && retObj.getResult().equals("0")){
					int banlence = Integer.parseInt(retObj.getBalance());
					partnerAccount.setAccount_amount(banlence - childMoney);
				}else {
					
					partnerAccount.setAccount_amount(0);
				}
				partnerAccount.setFrost_deposit(0);											//一级分销商的冻结金额默认都是0
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}else {
			
			//调用接口异常，预存金全部默认0
			partnerAccount.setAccount_amount(0);
			partnerAccount.setFrost_deposit(0);
		}
		return partnerAccount;
	}
	
	@Override
	public boolean edit(PartnerAccount partnerAccount){
			
		this.baseDaoSupport.update("partner_account", partnerAccount, " partner_id ='" + 
				partnerAccount.getPartner_id() + "'");

		return true;
	}




	public PartnerInf getPartnerServ() {
		return partnerServ;
	}


	public void setPartnerServ(PartnerInf partnerServ) {
		this.partnerServ = partnerServ;
	}


	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}


	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}


	@Override
	public PartnerAccount getprimaryAccount(String partnerId) {
		String sql = "select b.* from es_partner_account b where b.partner_id=? and b.account_code='p'";
		PartnerAccount partnerAccount = this.baseDaoSupport.queryForObject(sql, PartnerAccount.class, partnerId);
		return partnerAccount;
	}


	@Override
	public PartnerAccount getSecondaryAccount(String partnerId) {
		String sql = "select b.* from es_partner_account b where b.partner_id=? and b.account_code='s'";
		PartnerAccount partnerAccount = this.baseDaoSupport.queryForObject(sql, PartnerAccount.class, partnerId);
		return partnerAccount;
	}
	
	
}
