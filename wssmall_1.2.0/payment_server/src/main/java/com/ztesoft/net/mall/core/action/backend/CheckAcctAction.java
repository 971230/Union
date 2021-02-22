package com.ztesoft.net.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.cmp.ICheckAcctManager;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.CheckAcctConfig;



/**
 * 对账设置操作类
 * @author hu.yi
 * @date 2013.12.04
 */
public class CheckAcctAction extends WWAction{

	
	private ICheckAcctManager checkAcctManager;
	private CheckAcctConfig checkAcctConfig;

	
	public String listAcct(){
		
		if(checkAcctConfig == null){
			checkAcctConfig = new CheckAcctConfig();
		}
		
		this.webpage = this.checkAcctManager.listAcct(this.getPage(), this
				.getPageSize(), checkAcctConfig);
		
		return "check_acct_list";
	}
	
	
	public String addAcct(){
		
		if(checkAcctConfig != null){
			this.checkAcctConfig = this.checkAcctManager.qyrAcctById(checkAcctConfig.getSystem_id());
			if(checkAcctConfig != null){
				checkAcctConfig.setA_file_name(checkAcctConfig.getA_shop_no() +
						"." + checkAcctConfig.getA_file_tail());
			}
		}
		
		return "check_acct_add";
	}
	
	public String saveAcct(){
		String msg = "";
		
		try {
			if(!StringUtil.isEmpty(checkAcctConfig.getSystem_id())){
				this.checkAcctManager.updateAcct(checkAcctConfig);
			}else{
				this.checkAcctManager.saveAcct(checkAcctConfig);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		if(StringUtil.isEmpty(msg)){
			this.json = "{result:0}";
		}else{
			this.json = "{result:1,message:\"保存失败："+msg+"\"}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	public String delAcct(){
		
		String msg = "";
		
		try {
			checkAcctConfig.setState(Consts.CHECK_ACCT_CONFIG_ENANLE);
			this.checkAcctManager.updateAcct(checkAcctConfig);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		
		if(StringUtil.isEmpty(msg)){
			this.json = "{result:0}";
		}else{
			this.json = "{result:1,message:\"删除失败："+msg+"\"}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	public ICheckAcctManager getCheckAcctManager() {
		return checkAcctManager;
	}

	public void setCheckAcctManager(ICheckAcctManager checkAcctManager) {
		this.checkAcctManager = checkAcctManager;
	}

	public CheckAcctConfig getCheckAcctConfig() {
		return checkAcctConfig;
	}

	public void setCheckAcctConfig(CheckAcctConfig checkAcctConfig) {
		this.checkAcctConfig = checkAcctConfig;
	}
}
