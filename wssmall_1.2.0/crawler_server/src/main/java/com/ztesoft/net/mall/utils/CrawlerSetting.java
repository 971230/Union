package com.ztesoft.net.mall.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import params.req.CrawlerProcCondSettingReq;
import params.req.UpdateCrawlerSettingReq;
import params.resp.CrawlerProcCondResp;
import zte.net.model.CrawlerUrlConfig;

import com.ztesoft.autoprocess.base.CrawlerThread;
import com.ztesoft.net.framework.util.StringUtil;

/**
 * 爬虫配置
 * @author qin.yingxiong
 *
 */
public class CrawlerSetting {
	private static Logger logger = Logger.getLogger(CrawlerSetting.class);
	/**
	 * 是否走代理   默认   N
	 */
	public static String IS_PROXY = "N";
	/**
	 * 代理地址
	 */
	public static String PROXY_HOST = "";
	/**
	 * 代理端口
	 */
	public static int PROXY_PORT = -1;
	/**
	 * 是否开启单号匹配校验
	 */
	public static String IS_OUT_ID_CHECK = "N";

	/**
	 * 線程處理條數。update_status 0為未寫入數據庫，1為寫入數據庫
	 */
	public static Map<String,Map<String,String>> HANDLE_NUM_MAP = new HashMap<String, Map<String,String>>();
	
	//查询条件
	public static Map<String, Map<String,String>> ORDER_REVIEW_MAP = new HashMap<String, Map<String,String>>();
	public static Map<String, Map<String,String>> ORDER_ALLOCATION_MAP = new HashMap<String, Map<String,String>>();
	
	/**
	 * 目标地址配置
	 */
	public static String CRAWLER_ADDRESS = "";
	public static Map<String, String> OPERATION_URL_MAP = new HashMap<String,String>();
	
	//private static Map<String,Map<String, String>> threadSetting = new HashMap<String, Map<String,String>>();
	private static Map<String, String> threadNames = new HashMap<String, String>();
	
    public static void init(CrawlerProcCondResp req){
		IS_PROXY = req.getCrawlerProxyType();//是否走代理
		IS_OUT_ID_CHECK = req.getCrawlerIsOutIdCheck();//是否走订单号匹配
		CRAWLER_ADDRESS = req.getCrawlerAddress();
		if(StringUtils.isEmpty(CRAWLER_ADDRESS)){
			CRAWLER_ADDRESS = "";
		}
		if(StringUtils.isEmpty(IS_PROXY) || "N".equals(IS_PROXY)) {
    		IS_PROXY = "N";
    	}else{
    		PROXY_HOST = req.getCrawlerProxyAddress();//代理地址
    		String port = req.getCrawlerProxyPort();
    		if(StringUtils.isEmpty(port) && isNumeric(port)){
    			PROXY_PORT = Integer.parseInt(port);//代理端口
    		}
    	}
    	if(IS_OUT_ID_CHECK == null){
    		IS_OUT_ID_CHECK = "N";
    	}
    	
    	List<CrawlerUrlConfig> urlconfigList = req.getUrlList();
    	if(urlconfigList != null){
    		for (CrawlerUrlConfig crawlerUrlConfig : urlconfigList) {
    			String key = crawlerUrlConfig.getOperation_code();
    			String operationUrl = crawlerUrlConfig.getOperation_url();
    			if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(operationUrl)){
        	    	OPERATION_URL_MAP.put(key, operationUrl);
        	    	logger.info("=====["+key+":"+operationUrl+"]");
    			}
			}
    	}
    	
    	Map<String , List<CrawlerProcCondSettingReq>> threadSettingMap = req.getRespMap();
    	if(threadSettingMap != null){
    		Set<Entry<String, List<CrawlerProcCondSettingReq>>> threadName = threadSettingMap.entrySet();//线程名称
        	for (Entry<String, List<CrawlerProcCondSettingReq>> entry : threadName) {//遍历线程名称
    			String key = entry.getKey();//线程名称
    			Map<String, String> orderReviewThreadParams =  new HashMap<String, String>();//线程参数对象
    			Map<String, String> orderAllocationParams = new HashMap<String, String>();
    			Map<String, String> threanHandleNum = new HashMap<String, String>();
    			
    			List<CrawlerProcCondSettingReq> paramsList = threadSettingMap.get(key);
    			if(paramsList != null ){
    				for (CrawlerProcCondSettingReq settingReq : paramsList) {
    					String condCode = settingReq.getCond_code();
    					if(condCode != null && condCode.indexOf("ORDER_REVIEW.") != -1){
    						orderReviewThreadParams.put(condCode.substring(13),settingReq.getCond_value());
    	    			}else if(condCode != null && condCode.indexOf("ORDER_ALLOCATION.") != -1){
    	    				orderAllocationParams.put(condCode.substring(17),settingReq.getCond_value());
    	    			}else if("HANDLE_NUM".equals(condCode)){
    	    				String str = settingReq.getCond_value();
	    					threanHandleNum.put("UPDATE_STATUS", "0");
    	    				if(!StringUtil.isEmpty(str) && isNumeric(str)){
    	    					threanHandleNum.put("HANDLE_NUM", str);
    	    				}else{
    	    					threanHandleNum.put("HANDLE_NUM", "-1");
    	    				}
    	    				
    	    			}else {
    	    				logger.info("==========爬虫处理条件未知条件：("+condCode+"-"+settingReq.getCond_value()+"-"+settingReq.getCond_name());
    	    				return ;
    	    			}
    				}
    			}
    			ORDER_REVIEW_MAP.put(key, orderReviewThreadParams);
    			ORDER_ALLOCATION_MAP.put(key, orderAllocationParams);
    			HANDLE_NUM_MAP.put(key, threanHandleNum);
    			if(!threadNames.containsKey(key)){//如果该线程名不存在，则设置线程名
    				threadNames.put(key, key);
    			}
    		}
    	}
    }

    public static String updateCrawlerSetting(UpdateCrawlerSettingReq req){ 
    	IS_PROXY = req.getCrawlerProxyType();//是否走代理
		IS_OUT_ID_CHECK = req.getCrawlerIsOutIdCheck();//是否走订单号匹配
		PROXY_HOST = req.getCrawlerProxyAddress();//代理地址
    	if(StringUtils.isEmpty(IS_PROXY) || "N".equals(IS_PROXY)) {
    		IS_PROXY = "N";
    	}else{
    		PROXY_HOST = req.getCrawlerProxyAddress();//代理地址
    		String port = req.getCrawlerProxyPort();
    		if(StringUtils.isEmpty(port) && isNumeric(port)){
    			PROXY_PORT = Integer.parseInt(port);//代理端口
    		}
    	}
    	if(IS_OUT_ID_CHECK == null){
    		IS_OUT_ID_CHECK = "N";
    	}
    	List<CrawlerUrlConfig> urlconfigList = req.getUrlList();
    	if(urlconfigList != null){
    		for (CrawlerUrlConfig crawlerUrlConfig : urlconfigList) {
    			String key = crawlerUrlConfig.getOperation_code();
    			String operationUrl = crawlerUrlConfig.getOperation_url();
    			if(!StringUtils.isEmpty(key) && !StringUtils.isEmpty(operationUrl)){
        	    	OPERATION_URL_MAP.put(key, operationUrl);
        	    	logger.info("=====["+key+":"+operationUrl+"]");
    			}
			}
    	}
    	
    	String threadStatus = req.getThreadStatus();
    	String threadName = req.getThreadName();
    	if(!StringUtil.isEmpty(threadStatus) && "0".equals(threadStatus) && !StringUtil.isEmpty(threadName)){
    		threadNames.remove(threadName);
    		ORDER_REVIEW_MAP.remove(threadName);
    		ORDER_ALLOCATION_MAP.remove(threadName);
    		return "0";
    	} else if (!StringUtils.isEmpty(threadName)){
        		List<CrawlerProcCondSettingReq> threadSettingMap = req.getCrawlerProcCond();
        		if(threadSettingMap != null && threadSettingMap.size() > 0){//更新线程参数
        			//根据线程名查找对应的线程参数对象，如该线程没有参数对象则创建线程对象
        	    	Map<String, String> orderReviewThreadParams = new HashMap<String, String>();
        			Map<String, String> orderAllocationParams = new HashMap<String, String>();
        			Map<String, String> threanHandleNum = new HashMap<String, String>();
        			
        			for (CrawlerProcCondSettingReq settingReq : threadSettingMap) {//解析线程参数
        				String condCode = settingReq.getCond_code();
        				if(condCode != null && condCode.indexOf("ORDER_REVIEW.") != -1){
        					orderReviewThreadParams.put(condCode.substring(13),settingReq.getCond_value());
        				}else if(condCode != null && condCode.indexOf("ORDER_ALLOCATION.") != -1){
        					orderAllocationParams.put(condCode.substring(17),settingReq.getCond_value());
        				}else if("HANDLE_NUM".equals(condCode)){
    	    				String str = settingReq.getCond_value();
	    					threanHandleNum.put("UPDATE_STATUS", "0");
    	    				if(!StringUtil.isEmpty(str) && isNumeric(str)){
    	    					threanHandleNum.put("HANDLE_NUM", str);
    	    				}else{
    	    					threanHandleNum.put("HANDLE_NUM", "-1");
    	    				}
    	    				
    	    			}else {
        					logger.info("==========爬虫处理条件未知条件：("+condCode+"-"+settingReq.getCond_value()+"-"+settingReq.getCond_name());
        					return settingReq.getCond_name()+"条件设置错误"+condCode+"=="+settingReq.getCond_value();
        				}
        			}
        			
        			//更新线程参数
        			ORDER_REVIEW_MAP.put(threadName, orderReviewThreadParams);
        			ORDER_ALLOCATION_MAP.put(threadName, orderAllocationParams);
        			HANDLE_NUM_MAP.put(threadName, threanHandleNum);
        			
        			if(!threadNames.containsKey(threadName)){//如果该线程不存在,则设置线程名
            			threadNames.put(threadName, threadName);
            		}
        		}
    	}
    	
    	return "0";
    }
    
    public static void runThread(){
    	Set<String> threadName = threadNames.keySet();
    	if(threadName.size() > 0){
	    	for (String key : threadName) {
	    		Map<String, String> params = CrawlerSetting.HANDLE_NUM_MAP.get(threadName);
				String str = null;
				if(params != null){
					str = params.get("HANDLE_NUM");
				}
				int handleNum = StringUtil.isEmpty(str)?-1:Integer.valueOf(str);
				if(handleNum > 0 || handleNum == -1){//设置了处理条数，如果处理条数等于0则不做收单
					new CrawlerThread(key).start();
				}else{
					logger.info("=====线程["+key+"]已达最大处理数量");
				}
			}
    	}else{
    		logger.info("==========爬虫当前无线程可用");
    	}
    }
    
    public static Map<String,String> queryRunThreadStatus(){
    	return threadNames;
    }
    
    public final static boolean isNumeric(String s) {  
        if (s != null && !"".equals(s.trim()))  
            return s.matches("^[0-9]*$");  
        else  
            return false;  
    }

    public static void main(String[] args) {
    	InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			String ip=addr.getHostAddress().toString();//获得本机IP
			String host=InetAddress.getLocalHost().toString();
			logger.info(ip+":"+host);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	/*File file = new File("D:\\ZTESoft\\project\\config\\config_hn\\crawler.properties");
		if(file.exists()){
			try {
				FileInputStream fileIS1  = new FileInputStream(file);
				InputStream stream = fileIS1;
	            Properties props = new Properties();
	            props.load(stream);
				OutputStream  fileIS  = new FileOutputStream(file);
				props.setProperty("order_allocation.count", "100");   
				props.store(fileIS, "Update ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
		}else{
			logger.info("==============文件不存在============");
		}*/
	}

    
}
