package services;

import commons.CommonContext;

/**
 * 接口过期登录用户清空定时任务
* @作者 MoChunrun 
* @创建日期 2013-9-26 
* @版本 V 1.0
 */
public class LoginUserClearTimer {

	public void clear(){
		CommonContext.getInstance().clearTimeUser();
	}
	
}
