package com.ztesoft.net.mall.core.service.impl;

import com.ztesoft.ibss.common.util.ListUtil;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.service.IBankManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

import java.util.List;

/**
 * 银行业务逻辑处理类
 * 
 * @author hzcl_sky gong.zhiwei
 * 
 */
public class BankManager extends BaseSupport<Bank> implements IBankManager {

	/**
	 * 获取银行列表 add by gong.zhiwei
	 * 
	 * @return
	 */
	@Override
	public List<Bank> bankList() {
		// add by gong.zhiwei
		if (StringUtil.isMobile()) {
			return null;
		}
		String sql = "select * from es_bank where show_flag='Y'";
		return this.baseDaoSupport.queryForList(sql, Bank.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Bank getBankByBankId(String bank_id) {
		String sql = "select * from es_bank where show_flag='Y' and bank_id=?";
		List  orderBanks = this.baseDaoSupport.queryForList(sql, Bank.class, bank_id);
		if(ListUtil.isEmpty(orderBanks))
			return null;
		return (Bank)orderBanks.get(0);
		
	}
	
	@Override
	public List getBankList(){
		
		String sql = "select bank_id, bank_name, img_url, show_flag, " +
				"bank_code, bank_type from es_bank where show_flag='Y' ";
		
		return this.baseDaoSupport.queryForList(sql);
	}
	
	@Override
	public Bank getBankByCode(String bankCode){
		
		String sql = "select bank_id, bank_name, img_url, show_flag, " +
		"bank_code, bank_type from es_bank where show_flag='Y' and bank_code = ?";
		
		return this.baseDaoSupport.queryForObject(sql, Bank.class, bankCode);
	}

	@Override
	public List<Bank> qryBankByPaymentCfgId(String config_id) {
		String sql = "select b.* from es_bank b,es_payment_cfg_bank_rel cb where b.bank_id=cb.bank_id and b.show_flag='Y' and cb.cfg_id=? and b.source_from=? and b.source_from=cb.source_from";
		return this.baseDaoSupport.queryForList(sql, Bank.class, config_id,ManagerUtils.getSourceFrom());
	}
}
