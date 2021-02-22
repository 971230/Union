package com.ztesoft.net.mall.core.scheduler;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.ConfigInfo;
import com.ztesoft.net.mall.core.service.IConfigInfoManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CheckSchedulerServer {

	protected final static Logger logger = Logger.getLogger(CheckSchedulerServer.class);
	
	private static IConfigInfoManager configInfoManager;

	
	
	/**
	 * 根据内网ip判断是否是可以执行定时任务的服务器
	 * @returns
	 */
	public static boolean isMatchServer() {
		if (configInfoManager == null) {
			configInfoManager = SpringContextHolder.getBean("configInfoManager");
		}
        ConfigInfo config = configInfoManager.getCofigByKey(Consts.SCHEDULER_SERVER_IP_ADDR);
        if(null==config)return false;
        String value = config.getCf_value();
        if(StringUtils.isEmpty(value))return false;
        String [] ss = value.split(",");
        for(String s:ss){
            System.setProperty("timer_port",s.split(":")[1]);
        	if (!StringUtils.isEmpty(s) && s.equals(StringUtil.getLocalIpAddress() + ":" + StringUtil.getContextPort())) {
                logger.debug("local ip:port address " + StringUtil.getLocalIpAddress() + ":" + StringUtil.getContextPort() + " match Scheduler Server run it...");
                return true;
            }
        }
		return false;
	}
}
