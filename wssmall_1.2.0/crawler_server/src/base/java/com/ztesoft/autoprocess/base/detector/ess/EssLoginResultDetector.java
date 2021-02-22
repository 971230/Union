/**
 * 
 */
package com.ztesoft.autoprocess.base.detector.ess;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.autoprocess.base.detector.impl.LoginResultDetector;
import com.ztesoft.autoprocess.base.exception.SystemException;

/**
 * ESS登录结果判断
 * @author tangml
 * 2013-10-17
 */
public class EssLoginResultDetector implements LoginResultDetector {

	//实例化对象
	private static final EssLoginResultDetector loginResultDetector=new EssLoginResultDetector();
	
	public static EssLoginResultDetector getInstance(){
		return loginResultDetector;
	}
	
	public EssLoginResultDetector(){}
	
	@Override
	public boolean isLogin(String response) throws Exception{
		int len = response.indexOf("preloginComplete(")+17;
		String params = response.substring(len, response.length()-2).trim();
		String[] paramsArry=params.split(",");
		//是否为初始化密码
		String initalPwd = paramsArry[0];
		//登录校验类型
		String loginCheckType=paramsArry[1].trim().replace("'", "");
		//密码过期
		String isLatePwd = paramsArry[5];

		if("true".equals(initalPwd) || "true".equals(isLatePwd)){
			if("true".equals(initalPwd)){
				throw new SystemException("登录失败,您当前使用的是系统初始化密码,请更新您的密码!");
			}
			if("true".equals(isLatePwd)){
				throw new SystemException("登录失败,您当前使用的密码已过期，请更新您的密码!");
			}
		}
		if("0".equals(loginCheckType) || "3".equals(loginCheckType)){
			return true;
		}else{
			String msg = StringUtils.isBlank(loginCheckType)?"未知错误:登录失败,请重新登录!":"登录用户验证类型不对!";
			throw new SystemException(msg);
		}
		
	}
}
