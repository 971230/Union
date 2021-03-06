package com.ztesoft.net.mall.core.timer;

import java.util.Date;

import org.apache.log4j.Logger;

import params.req.CrawlerProcCondReq;
import params.resp.CrawlerProcCondResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.mall.utils.CrawlerSetting;
import com.ztesoft.net.mall.utils.CrawlerUtils;


/**
 * 订单抓取标准化新用户标准化定时器
 * @作者 lzg
 * @创建日期 2016-12-28 
 * @版本 V 1.0
 */
public class AuditOrderZBTask {
	
	private static Logger log = Logger.getLogger(AuditOrderZBTask.class);
	public static String queryType;
	/**
	 * 订单抓取标准化定时器
	 * @throws Exception
	 */
	public void runAuditTask() throws Exception {
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
        		log.info("开始[总部系统-订单抓取标准化]任务:" + CrawlerUtils.strFormatDate(new Date()));
                log.info("开始[总部系统-订单抓取标准化]任务:" + CrawlerUtils.strFormatDate(new Date()));
                auditProcess();
                log.info("结束[总部系统-订单抓取标准化]任务:"+ CrawlerUtils.strFormatDate(new Date()));
                log.info("结束[总部系统-订单抓取标准化]任务:"+ CrawlerUtils.strFormatDate(new Date()));
        	}else{
        		log.info("未执行[总部系统-订单抓取标准化]任务：未初始化爬虫配置信息");
        	}
            
        } catch (Exception e) {
            e.printStackTrace();
            log.info("[总部系统-订单抓取标准化]任务出错", e);
        }
    }
	
	/**
	 * main入口方法
	 * @param args
	 */
    public static void main(String[] args) {
        try {
//        	log.info("开始[总部系统-订单抓取标准化]任务:" + CrawlerUtils.strFormatDate(new Date()));
//            log.info("开始[总部系统-订单抓取标准化]任务:" + CrawlerUtils.strFormatDate(new Date()));
        	while(true){
        		auditProcess();
    			Thread.sleep(6*60*1000);//5分钟一次
        	}
            
//            log.info("结束[总部系统-订单抓取标准化]任务:"+ CrawlerUtils.strFormatDate(new Date()));
//            log.info("结束[总部系统-订单抓取标准化]任务:"+ CrawlerUtils.strFormatDate(new Date()));
        } catch (Exception e) {
            log.info("[总部系统-根据上次登陆cookie自动登陆]任务出错", e);
        }
    }
	
	/**
	 * 批量订单抓取标准化
	 * @throws Exception
	 */
	public static void auditProcess() throws Exception {
		try {
				CrawlerSetting.runThread();
		} catch (Exception e) {
			log.info("[总部系统-批量订单抓取标准化]任务出错", e);
			e.printStackTrace();
		}
    }
	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		AuditOrderZBTask.queryType = queryType;
	}
}
