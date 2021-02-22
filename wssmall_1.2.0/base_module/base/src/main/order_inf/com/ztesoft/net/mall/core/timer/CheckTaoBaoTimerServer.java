package com.ztesoft.net.mall.core.timer;

import java.util.Map;

import org.apache.log4j.Logger;

import zte.net.iservice.IJobTaskService;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class CheckTaoBaoTimerServer {
	private static Logger logger = Logger.getLogger(CheckTaoBaoTimerServer.class);
	public static boolean isJobRunInIp(){
		IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
		Map resultMap = jobTaskService.checkedTaobaoJob("","");
		boolean isrun = Boolean.parseBoolean(resultMap.get("iscanrun").toString()) ;
		return isrun;
	}
	public static boolean isTaobaoCityCode(String order_city_code,String ip,String port){
		IJobTaskService jobTaskService = SpringContextHolder.getBean("jobTaskService");
		Map map = jobTaskService.checkedTaobaoJob(ip,port);
		boolean isneedcheck = Boolean.parseBoolean(map.get("isneedcheck").toString()) ;
		boolean iscanrun = Boolean.parseBoolean(map.get("iscanrun").toString()) ;
		if(!isneedcheck){
			return true;
		}else if(isneedcheck&&!iscanrun){
			return false;
		}
		logger.info("-------"+order_city_code+"---------");
		String ckeck_rule = map.get("city_ckeck_rule").toString();
		boolean flag = false;
		if("1".equals(ckeck_rule)){
			if(map.get("order_city_code").toString().indexOf(order_city_code)>-1){
				flag = true;
			}
		}else if("0".equals(ckeck_rule)){
			if(map.get("order_city_code").toString().indexOf(order_city_code)==-1){
				flag = true;
			}
		}
		return flag;
	}
}
