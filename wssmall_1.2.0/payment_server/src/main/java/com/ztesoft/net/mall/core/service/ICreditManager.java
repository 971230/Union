package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.Account;
import com.ztesoft.net.mall.core.model.BankVO;
import com.ztesoft.net.mall.core.model.CreditVO;
import com.ztesoft.net.mall.core.model.PayVO;


public interface ICreditManager{
	public void delete(String id);//删除
	public Page creditList(int page,int pageSize);
	public List showBankList(String cfg_id);
	public void del(String cfg_id,String bank_id);
	public void addCredit(CreditVO credit);
	public void addPayment(PayVO pay);
	public void addAcc(Account acc);
	public void addBanks(BankVO bank);
	public CreditVO findPay(String id);
	public void updatePay(CreditVO pay);
	public List accountList(String cfg_id);
	public List bankList (String id);
	public void delAcc(String accounted_code,String cfg_id);
	public Page banksList(int page,int pageSize);
	public void delBank(String bank_id,String cfg_id);
	public void addBankRel(CreditVO credit);
	public Page addBanked(int page,int pageSize,String cfg_id);
	public int countID(String owner_userid);
}
