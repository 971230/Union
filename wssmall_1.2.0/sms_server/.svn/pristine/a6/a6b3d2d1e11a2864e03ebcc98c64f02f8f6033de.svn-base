package com.ztesoft.net.mall.core.action.sms;

import java.util.Random;

import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;

import services.AdminUserInf;

import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.SendSms;
import com.ztesoft.net.sms.service.ISmsManager;


public class SmsAction extends WWAction {
	private ISmsManager smsManager;
	private AdminUserInf adminUserServ;
	private String username;
	private boolean result;

	public String addSms(){
		AdminUserReq adminUserReq = new AdminUserReq();
		adminUserReq.setUser_name(username);
		
		AdminUserResp adminUserResp = adminUserServ.getAdminUserByUserName(adminUserReq);
		AdminUser adminUser = new AdminUser();
		if(adminUserResp != null){
			adminUser = adminUserResp.getAdminUser();
		}
		if(adminUser!=null){
			SendSms sendSms=new SendSms();
			String sRand=""; 
			// 生成随机类 
			Random random = new Random(); 
			for(int i=0;i<6;i++){
				String rand=String.valueOf(random.nextInt(10));
				sRand+=rand;
			}
			String smsBaseContent="欢迎分销商用户，您本次登录的随机密码是：";
			String content="["+smsBaseContent+sRand+"]。（本信息免费）";
			sendSms.setSend_no(smsManager.getSmsNo());
			sendSms.setSend_num("10001");
			sendSms.setRecv_num(adminUser.getPhone_num());
			sendSms.setSend_content(content);
			sendSms.setSend_count(0);
	    	sendSms.setState("1");
	    	smsManager.save(sendSms);
	    	result=true;
		}
		return "addSms";
	}


	public ISmsManager getSmsManager() {
		return smsManager;
	}


	public void setSmsManager(ISmsManager smsManager) {
		this.smsManager = smsManager;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}


	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}


	public boolean isResult() {
		return result;
	}


	public void setResult(boolean result) {
		this.result = result;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

}
