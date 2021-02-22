package services;

import params.login.req.LoginReq;
import params.login.resp.LoginResp;

/**
 * 登录
* @作者 MoChunrun 
* @创建日期 2013-9-27 
* @版本 V 1.0
 */
public interface LoginInf {

	public LoginResp login(LoginReq req);
	
}
