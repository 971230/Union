package com.ztesoft.net.mall.core.timer;

import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.cache.common.SysNetCacheWrite;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 商品缓存刷新
 * @author wui
 *
 */
public class SysCacheTimer {
	
	public void run() {
//		if(true)
//		{
//			logger.info(this.getClass().getName()+"111111111111");
//			return;
//		}
		//ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run"))
  			return ;
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String planDBSourceFroms = cacheUtil.getConfigInfo("PLAN_SOURCE_FROM");
		//配置的不匹配直接选用文件配置的,add by wui
		if(!(planDBSourceFroms.indexOf(EopSetting.SOURCE_FROM)>-1))
			planDBSourceFroms = EopSetting.SOURCE_FROM;
		if(StringUtil.isEmpty(planDBSourceFroms))
			planDBSourceFroms = ManagerUtils.getSourceFrom();
		SysNetCacheWrite write = SpringContextHolder.getBean("sysNetCacheWrite");
		try{
			for (String plan_dbsourcefrom :planDBSourceFroms.split(",")) {
				ManagerUtils.CACHE_REFRESH_SOURCE_FROM = plan_dbsourcefrom;
				write.loadAllGoodsOrg();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			ManagerUtils.CACHE_REFRESH_SOURCE_FROM ="";
		}
		
	}
	
	public static void main(String[] args) {
		SysCacheTimer sysCacheTimer = new SysCacheTimer();
		sysCacheTimer.run();
	}
}
