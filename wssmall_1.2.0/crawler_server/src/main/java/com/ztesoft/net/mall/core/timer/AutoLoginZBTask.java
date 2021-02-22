package com.ztesoft.net.mall.core.timer;

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import params.req.CrawlerProcCondReq;
import params.resp.CrawlerProcCondResp;
import zte.net.ecsord.common.LocalCrawlerUtil;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.ConfigUtil;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;
import com.ztesoft.remote.params.req.SmsTempleteRequest;
import com.ztesoft.remote.params.resp.SmsTempleteResponse;

import consts.ConstsCore;


/**
 * 登录总商定时器
 * @作者 lzg
 * @创建日期 2016-12-28 
 * @版本 V 1.0
 */
public class AutoLoginZBTask {
	
	private static Logger log = Logger.getLogger(AutoLoginZBTask.class);
	
	private static int count=0;
	/**
	 * 自动登录总商定时器
	 * @throws Exception
	 */
	public void runTask() throws Exception {
        try {
        	String initStatus = "0";
        	if(CrawlerSetting.OPERATION_URL_MAP == null || CrawlerSetting.OPERATION_URL_MAP.size() <= 0){//执行job之前先判断是不是初始化了参数，先用操作链接作为判断标准，如果没有操作链接后面的所有操作都无法执行
        		String server_ip = StringUtil.getLocalIpAddress();
        		String server_port = StringUtil.getContextPort();
        		
        		log.info(server_ip+":"+server_port);
        		CrawlerProcCondReq req = new CrawlerProcCondReq();
        		req.setIp(server_ip);
        		req.setPort(server_port);
        		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        		CrawlerProcCondResp resp = client.execute(req, CrawlerProcCondResp.class);
        		if("0".equals(resp.getError_code())){
        			CrawlerSetting.init(resp);	
        		}else{
        			initStatus = "1";
        			log.info("==========爬虫查询初始化配置出错："+resp.getError_msg());
        		}
        	}
        	if("0".equals(initStatus)){
	            log.info("开始[总部系统-根据上次登陆cookie自动登陆]任务:" + CrawlerUtils.strFormatDate(new Date()));
	            log.info("开始[总部系统-根据上次登陆cookie自动登陆]任务:" + CrawlerUtils.strFormatDate(new Date()));
	            autoProcess();
	            log.info("结束[总部系统-根据上次登陆cookie自动登陆]任务:"+ CrawlerUtils.strFormatDate(new Date()));
	            log.info("结束[总部系统-根据上次登陆cookie自动登陆]任务:"+ CrawlerUtils.strFormatDate(new Date()));
        	}else{
        		log.info("未执行[总部系统-根据上次登陆cookie自动登陆]任务：未初始化爬虫配置信息");
        	}
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[总部系统-根据上次登陆cookie自动登陆]任务出错", e);
        }
    }
	
	/**
	 * main入口方法
	 * @param args
	 */
    public static void main(String[] args) {
        try {
//            log.info("开始[总部系统-根据上次登陆cookie自动登陆]任务:" + CrawlerUtils.strFormatDate(new Date()));
//            log.info("开始[总部系统-根据上次登陆cookie自动登陆]任务:" + CrawlerUtils.strFormatDate(new Date()));
            while(true){
            	autoProcess();
    			Thread.sleep(5*60*1000);//5分钟一次
        	}
//            log.info("结束[总部系统-根据上次登陆cookie自动登陆]任务:"+ CrawlerUtils.strFormatDate(new Date()));
//            log.info("结束[总部系统-根据上次登陆cookie自动登陆]任务:"+ CrawlerUtils.strFormatDate(new Date()));
        } catch (Exception e) {
            log.info("[总部系统-根据上次登陆cookie自动登陆]任务出错", e);
        }
    }
	
	/**
	 * 自动登录
	 * @throws Exception
	 */
	public static void autoProcess() throws Exception {
		try{
			String username = "";
			String password = "";
			if(ZBSystemClient.clientLogin!=null){
				if(StringUtils.isNotBlank(ZBSystemClient.clientLogin.getLoginParam().getUsername())){
					username = ZBSystemClient.clientLogin.getLoginParam().getUsername();
					//log.info("=================获取登录的用户名："+username);
				}else{
					username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
				}
				if(StringUtils.isNotBlank(ZBSystemClient.clientLogin.getLoginParam().getPassword())){
					password = ZBSystemClient.clientLogin.getLoginParam().getPassword();
					//log.info("=================获取登录的用户名："+password);
				}else{
					password = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_PWD;//默认密码
				}
			}else{
				username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
				password = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_PWD;//默认密码
			}
			String cookieValue = ZBSystemClient.cookieValue1;
			if(StringUtils.isNotBlank(cookieValue)){
				ZBSystemClient client = ZBSystemClient.getInstanceByCookie(username,password,"",cookieValue);
		        if (client != null) {
		            ClientPool.add(username, client);
		            log.info("用户["+LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE+"]的自动登陆成功!");
		            count = 0;//重新登录重置为0
		        } else {
		        	//关闭连接
		        	if(ClientPool.activeClientMap.size()>0){
		        		Iterator iterator=ClientPool.activeClientMap.entrySet().iterator();
			        	while(iterator.hasNext()){
			        		Entry<String, ZBSystemClient> entry = (Entry<String, ZBSystemClient>) iterator.next();
			        		ClientPool.close(entry);
			        	}
		        	}
		            log.info("用户["+LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE+"]的自动登陆失败,cookie已经失效!");
		            ZBSystemClient.cookieValue1 = ZBSystemClient.cookieValue;
		            count++;
		            if(count<3){//cookie失效少于3次则调用自动登录，大于3次不调用，防止死循环
		            	 AutoLoginZBTask.autoProcess();//初始化自动登录
		            }
		        }
			}else{
				 log.info("自动登陆失败,获取的cookie值为空!");
		         log.info("自动登陆失败,获取的cookie值为空!");
		         count = 0;//重新登录重置为0
			}
			if(count>=3 && count%3==0 ){
				//重新登录把cookie都设置为空(这里不重置就会隔一段时间发送一次短信提醒，重置了就只会cookie失效3次后提醒一次)
				ZBSystemClient.cookieValue1 = "";
				ZBSystemClient.cookieValue = "";
            	//大于3次还是提示cookie已经失效则发送短信提醒
				sendSms();
            }
		}catch (Exception e) {
			log.info("[总部系统-自动登陆]任务出错", e);
		}
    }
	
	
	/**
	 * 自动登录断开发送短信提醒相关人员
	 */
	private static void sendSms(){
//		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
//    	String receive_phonenums=cacheUtil.getConfigInfo("SMS_RECEIVE_PHONENUMS");//接收号码，配置在数据库表es_config_info
		ConfigUtil cu = new ConfigUtil("/com/ztesoft/net/mall/resource/receiveSms.properties");
		String receive_phonenums = ConfigUtil.getString("receive_phonenums");//从配置文件中获取号码
		if(!StringUtils.isEmpty(receive_phonenums)){
			String[] receive_phonenums_array=receive_phonenums.split(",");
			for (String receive_phonenum : receive_phonenums_array) {
				SmsTempleteRequest smsTemp = new SmsTempleteRequest();
				smsTemp.setCode("LOGIN_OUT_ZS");//短信模板，LOGIN_OUT_ZS  ,配置在表es_sms_templete
				smsTemp.setAccNbr(receive_phonenum);//接收号码\
				ZteClient zteclient = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				SmsTempleteResponse smsTempResp = zteclient.execute(smsTemp, SmsTempleteResponse.class);
				if(smsTempResp!=null&&ConstsCore.ERROR_SUCC.equals(smsTempResp.getError_code())){
					 log.info("爬虫自动登陆失败,发送短信验证码到指定手机用户成功!");
			         log.info("爬虫自动登陆失败,发送短信验证码到指定手机用户成功!");
				}else{
					 log.info("爬虫自动登陆失败,发送短信验证码到指定手机用户失败!");
			         log.info("爬虫自动登陆失败,发送短信验证码到指定手机用户失败!");
				}
			}
		}
	}
}
