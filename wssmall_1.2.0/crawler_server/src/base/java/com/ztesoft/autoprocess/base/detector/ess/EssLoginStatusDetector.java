package com.ztesoft.autoprocess.base.detector.ess;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.autoprocess.base.detector.impl.LoginStatusDetector;

public class EssLoginStatusDetector implements LoginStatusDetector {

	private static final EssLoginStatusDetector loginStatusDetector=new EssLoginStatusDetector();
	
	public static EssLoginStatusDetector getInstance(){
		return loginStatusDetector;
	}
	
	public EssLoginStatusDetector(){}
	
	@Override
	public boolean isOnline(String response) {
		if(StringUtils.isNotBlank(response)){
			if(response.indexOf("/pages/sys/frame/frameTop.jsf") > 0){
				return true;
			}
		}
		return false;
	}

}
