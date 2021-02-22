package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Account;
import com.ztesoft.net.mall.core.model.BankVO;
import com.ztesoft.net.mall.core.model.CreditVO;
import com.ztesoft.net.mall.core.model.PayVO;
import com.ztesoft.net.mall.core.model.PaymentCfgBank;
import com.ztesoft.net.mall.core.service.ICreditManager;
import com.ztesoft.net.sqls.SF;

public class CreditManager extends BaseSupport<CreditVO> implements ICreditManager{

	@Override
	public void delete(String id) {
		String sql="delete from es_payment_cfg where id=? ";
		this.baseDaoSupport.execute(sql, id);
	}


	@Override
	public Page creditList(int page, int pageSize) {
		String sql="select id, name,type,config,biref from es_payment_cfg where 1=1";
		return this.baseDaoSupport.queryForPage(sql, page, pageSize);
	}

	

	@Override
	public List showBankList(String cfg_id) {
		String sql= SF.paymentSql("SHOW_BANK_LIST");
		return this.baseDaoSupport.queryForList(sql,cfg_id);
	}
	@Override
	public void addCredit(CreditVO credit) {
		credit.setCreate_time(DBTUtil.current());
		this.baseDaoSupport.insert("es_payment_cfg_bank_rel", credit);
	}
	
	@Override
	public void del(String cfg_id,String bank_id) {
		String sql="delete from es_payment_cfg_bank_rel where cfg_id=? and bank_id=?";
		this.baseDaoSupport.execute(sql, bank_id);
	}


	@Override
	public void addPayment(PayVO pay) {
		pay.setId(this.baseDaoSupport.getSequences("S_ES_PAYMENT_CFG"));
		this.baseDaoSupport.insert("ES_PAYMENT_CFG", pay);
		
	}


	@Override
	public CreditVO findPay(String id) {
		CreditVO pay=null;
		String sql="select * from es_payment_cfg where id=?";
		pay=this.baseDaoSupport.queryForObject(sql, CreditVO.class, id);
		return pay;
	}


	@Override
	public void updatePay(CreditVO pay) {
		this.baseDaoSupport.update("es_payment_cfg", pay, "id='"+pay.getId()+"'");
		
	}


	@Override
	public List accountList(String cfg_id) {
		String sql= SF.paymentSql("GET_ACCOUNT_LIST");
		return this.baseDaoSupport.queryForList(sql, cfg_id);
	}


	@Override
	public void delAcc(String accounted_code,String cfg_id) {
		String sql="delete from es_payment_account where accounted_code=? and cfg_id=?";
		this.baseDaoSupport.execute(sql, accounted_code,cfg_id);
	}


	@Override
	public void addAcc(Account acc) {
		acc.setCreate_time(DBTUtil.current());
		this.baseDaoSupport.insert("es_payment_account", acc);
		
	}


	@Override
	public List bankList(String id) {
		String sql="select b.* from es_payment_cfg c ,es_bank b where c.id=b.bank_id and id=?";
		return this.baseDaoSupport.queryForList(sql, id);
	}


	@Override
	public Page banksList(int page, int pageSize) {
		String sql="select * from es_bank where 1=1";
		return this.baseDaoSupport.queryForPage(sql, page, pageSize);
	}


	@Override
	public void delBank(String bank_id,String cfg_id) {
		//String sql="delete from es_bank where bank_id=?";
		String sql = "delete from es_payment_cfg_bank_rel where bank_id=? and cfg_id=?";
		this.baseDaoSupport.execute(sql, bank_id,cfg_id);
		
	}


	@Override
	public void addBanks(BankVO bank) {
		this.baseDaoSupport.insert("es_bank", bank);
		
	}

	@Override
	public Page addBanked(int page,int pageSize,String cfg_id) {
		String sql="select * from es_bank t where not exists(select * from es_payment_cfg_bank_rel cr where cr.bank_id=t.bank_id and cr.cfg_id=?)";
		return this.baseDaoSupport.queryForPage(sql, page, pageSize, cfg_id);
	}


	@Override
	public void addBankRel(CreditVO credit) {
		PaymentCfgBank pb = new PaymentCfgBank();
		pb.setBank_id(credit.getBank_id());
		pb.setCfg_id(credit.getCfg_id());
		pb.setCreate_time(DBTUtil.current());
		this.baseDaoSupport.insert("es_payment_cfg_bank_rel", pb);
		
	}


	@Override
	public int countID(String owner_userid) {
		int count=0;
		String sql="select count(*) from es_payment_account where owner_userid="+'?'+"";
		count=this.baseDaoSupport.queryForInt(sql, owner_userid);
		return count; 
	}
}
