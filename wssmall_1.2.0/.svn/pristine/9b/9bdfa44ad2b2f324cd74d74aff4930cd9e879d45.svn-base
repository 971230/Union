package com.ztesoft.net.mall.core.service.impl;


import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.mall.core.service.AbsLoginCheck;
import com.ztesoft.net.mall.core.service.ISmsCode;
import commons.PlatService;

public class SmsCode extends AbsLoginCheck{

	@Override
	public boolean checkUser(String username, String password) {
		try {
			ISmsCode smsCode = PlatService.getPlatServInstance(SmsCode.class);
			return smsCode.checkUser(username,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isMultiOnline() {
		return false;
	}

	@Override
	public boolean partenerIsLock(AdminUser admin) {
		try {
			ISmsCode smsCode = PlatService.getPlatServInstance(SmsCode.class);
			return smsCode.partenerIsLock(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean partenerIsLogAble(AdminUser admin) {
		try {
			ISmsCode smsCode = PlatService.getPlatServInstance(SmsCode.class);
			return smsCode.partenerIsLogAble(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
