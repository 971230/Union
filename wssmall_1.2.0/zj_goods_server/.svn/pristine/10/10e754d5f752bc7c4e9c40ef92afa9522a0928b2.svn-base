package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;

import com.ztesoft.net.outter.inf.iservice.IGoodsXmlParseManager;

public class GoodsXmlParseTimer {
	
	@Resource
	private IGoodsXmlParseManager goodsXmlParseManager;
	
	public void run(){
			
			//定时任务IP地址限制
			if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
			    return ;
			}
			 
			goodsXmlParseManager.parse();

		}
}
