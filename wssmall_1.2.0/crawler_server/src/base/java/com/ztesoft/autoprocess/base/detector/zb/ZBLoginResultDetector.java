package com.ztesoft.autoprocess.base.detector.zb;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.autoprocess.base.detector.impl.LoginResultDetector;

/**
 * 登录结果验证接口
 * @author tanghaoyang
 *
 */
public class ZBLoginResultDetector implements LoginResultDetector{
	
	/**对象单例*/
	private static final ZBLoginResultDetector ordersLoginResultDetector=new ZBLoginResultDetector();
	
	/**
	 * 获取单例对象
	 * @return
	 */
	public static ZBLoginResultDetector getInstance(){
		return ordersLoginResultDetector;
	}

	private ZBLoginResultDetector(){}
	
	/**
	 * 根据返回报文判断是否登录成功
	 * @param response 登录请求返回的报文
	 * @return boolean 是否登录成功
	 */
	@Override
	public boolean isLogin(String response){
		if(StringUtils.isNotBlank(response)){
			//登录成功
			if(response.indexOf("{\"ErrorCode\":\"0\"}")!=-1){
				return true;
			}
		}

		//登录失败
		return false;
	}
}
