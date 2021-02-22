package com.ztesoft.net.mall.core.action.backend;

import java.util.List;

import net.sf.json.JSONArray;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Bank;
import com.ztesoft.net.mall.core.service.IBankManager;

/**
 * 银行信息
 * @author hzcl_sky gong.zhiwei
 *
 */
public class BankAction extends WWAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IBankManager bankManager;

	@SuppressWarnings("unchecked")
	public String bankList() {
		List<Bank> list = this.bankManager.bankList();
		JSONArray json = JSONArray.fromObject(list);
		this.json = json.toString();
		return JSON_MESSAGE;
	}

	public IBankManager getBankManager() {
		return bankManager;
	}

	public void setBankManager(IBankManager bankManager) {
		this.bankManager = bankManager;
	}
}
