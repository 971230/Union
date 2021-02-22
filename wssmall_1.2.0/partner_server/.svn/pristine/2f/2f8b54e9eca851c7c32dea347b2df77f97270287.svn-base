package com.ztesoft.net.mall.core.action.desposit;

import java.util.List;

import com.ztesoft.net.app.base.core.model.Partner;
import com.ztesoft.net.app.base.core.model.PartnerAccount;
import com.ztesoft.net.app.base.core.model.PartnerFreezeLog;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.PartnerDespost;

public interface IDespostHander  {
	
	public String frostAccount(String partnerId, PartnerFreezeLog freezeLog);	//冻结解冻分销商预存金
	public boolean openAccount(String partnerId);	   	//创建分销商预存金账户信息
	public List queryfreezeLogList(String partnerId);  	//获取分销商预存金冻结记录
	public Partner queryPartnerByID(String partnerId);	//根据ID获取分销商信息
	public PartnerAccount getAccountById(String partnerId); //根据id获取预存金账户信息
	public Page qyFreezeLogList(String partnerId, String beginTime,String endTime,String opType, int page,int pageSize);
	public List queryDespostLogList(String partnerId);
	public boolean depositChange(PartnerDespost despost);	//预存金充值、提现变更
	public boolean addDespostLog(int oldAmount,int newAmount,int amount, String partnerId,String opType, String comments, String tableName, String orderId);
	public boolean addFreezeLog(int oldAmount,int newAmount,int amount, String partnerId,String opType, String comments);
	public boolean updatePartner(Partner partner);
	public boolean updatePartnerAccount(PartnerAccount partnerAccount);
	public boolean updatePartnerDespost(String depostId, String State);
	public PartnerDespost getDespostById(String depostId);
	public boolean charge(String partnerId, Integer amount, String flag, String tableName, String orderId);
	public boolean debit(String partnerId, Integer amount, String flag, String tableName, String orderId);
	public boolean canPay(String partnerId, int amount, String flag);
	public boolean chargeBill(String partnerId, Integer amount, String flag);
	
	public   void afterPay(String ordeSeq, Integer orderAmount, String  retnCode, String transDate);
	public void addTempLog(String sql, Object... args);
}
