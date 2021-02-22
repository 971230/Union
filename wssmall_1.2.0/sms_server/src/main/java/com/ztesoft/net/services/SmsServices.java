package com.ztesoft.net.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.timer.CheckTimerServer;
import com.ztesoft.net.model.SendSms;
import com.ztesoft.net.model.SmsUser;
import com.ztesoft.net.sms.service.ISendSms;
import com.ztesoft.net.sms.service.ISmsManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-10-09 22:28
 * To change this template use File | Settings | File Templates.
 * <p/>
 * 短信发送类
 */
public class SmsServices {
    private Logger logger = Logger.getLogger(SmsServices.class);

    private static ApplicationContext context = null;

    private SmsUser smsUser = null;

    public static void main(String args[]) throws Exception {
        String configLocations[] = new String[]{
                "classpath:spring/dataAccessContext-jdbc.xml",
                "classpath:spring/smsContext.xml",
                "classpath:spring/mblServiceContext.xml"
        };
        context = new ClassPathXmlApplicationContext(configLocations);

        SmsServices services = new SmsServices();
        services.sendSms();

   /*
        ISmsManager smsManager = (ISmsManager) context.getBean("smsManager");
        SmsUser user=smsManager.getSmsUser();
        utils.SystemUtils.printLog(user.getUserName());*/

       /* List<SendSms> list= smsManager.getSendList();
        SendSms sms=smsManager.querySmsById("20130930103229000127");
        utils.SystemUtils.printLog(sms.getSend_no());
        utils.SystemUtils.printLog(smsManager.delete("20130930103229000127"));

        SendSms sms=smsManager.querySmsCodeById("1");
        utils.SystemUtils.printLog(sms);
        utils.SystemUtils.printLog(smsManager.updateHisState("20130930103229000127"));

        utils.SystemUtils.printLog(smsManager.updateSendCount("20130925105344000119"));
        SendSms sms=new SendSms();
        sms.setRecv_num("111111");
        sms.setSend_content("测试");
        utils.SystemUtils.printLog(smsManager.save(sms))*/

    }

    private int mainSleep(int arg) {
        try {
            Thread.sleep(1000 * arg);
            ++arg;
            if (arg > 4) {
                arg = 0;
            }
            logger.info("主线程休眠" + arg + "秒");
        } catch (Exception ex) {
            logger.info("主线程休眠出错！！");
        }
        return arg;
    }

    public void sendSms() throws Exception {
	    try {
	    	//logger.info("发短信定时任务");
	    	if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"sendSms"))
	  			return ;//添加限制
	    	//logger.info("发短信定时任务执行"+DateUtil.getTime2());
	    	// Thread.sleep(1000 * 10);
	    	String bean = "sendSmsImpl";
	    	ISendSms sendSms = null;
	    	if(null == context){
	    		sendSms = (ISendSms)SpringContextHolder.getBean(bean);
	    	}else{
	    		sendSms = (ISendSms)context.getBean(bean);
	    	}
	        if(null == sendSms){
	        	return;
	        }
	       // int count = 0;
	        List<SendSms> list = null;
	      //  while (true) {
	        	//logger.info("循环");
	          //  count = mainSleep(count);
	            ISmsManager smsManager = getSmsManager();
	            if (smsManager != null) {
	              /*  if (null == smsUser) {
	                    smsUser = smsManager.getSmsUser();
	                }
	                if (null == smsUser) return;*/
	
	                list = smsManager.getSendList();
	                if (null != list && list.size() > 0) {
	                    for (SendSms sms : list) {
	                        if (null == sms) continue;
	
	
	                        if (sendSms.sendSms(sms)) {
	                            smsManager.delete(sms.getSend_no());
	                        } else {
	                            if (null != sms.getSend_count() && sms.getSend_count() >= 3) {
	                                smsManager.updateState(sms.getSend_no());
	                            } else {
	                                smsManager.updateSendCount(sms.getSend_no());
	                            }
	                        }
	                    }
	                }
	            } 
    	} catch (Exception e) {
		e.printStackTrace();
	}
    }

    private ISmsManager getSmsManager() {
    	ISmsManager smsManager = null;
    	String bean = "smsManager";
    	if(null == context){
    		smsManager = (ISmsManager)SpringContextHolder.getBean(bean);
    	}else{
    		smsManager = (ISmsManager)context.getBean(bean);
    	}
        return smsManager;
    }

}
