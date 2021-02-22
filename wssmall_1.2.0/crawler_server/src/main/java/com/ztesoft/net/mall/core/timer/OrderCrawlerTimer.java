package com.ztesoft.net.mall.core.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.net.mall.utils.CrawlerUtils;


/**
 * 抓取订单分配页面老用户页面老用户订单定时任务
 * @作者 lzg
 * @创建日期 2016-12-19 
 * @版本 V 1.0
 */
public class OrderCrawlerTimer {
	
	private static Logger log = Logger.getLogger(OrderCrawlerTimer.class);
	
	/**
	 * 订单分配页面老用户页面老用户入库定时器
	 */
	public void runCrawler(){
		try {
//			if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"runCrawler")){
//				return ;
//			}
			log.info("开始[总部系统-订单分配页面老用户]任务:" + CrawlerUtils.strFormatDate(new Date()));
            log.info("开始[总部系统-订单分配页面老用户]任务:" + CrawlerUtils.strFormatDate(new Date()));
            auditProcess();
            log.info("结束[总部系统-订单分配页面老用户]任务:"+ CrawlerUtils.strFormatDate(new Date()));
            log.info("结束[总部系统-订单分配页面老用户]任务:"+ CrawlerUtils.strFormatDate(new Date()));
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
	}
	
	/**
	 * main入口方法
	 * @param args
	 */
    public static void main(String[] args) {
        try {
//        	log.info("开始[总部系统-订单审核]任务:" + CrawlerUtils.strFormatDate(new Date()));
//            log.info("开始[总部系统-订单审核]任务:" + CrawlerUtils.strFormatDate(new Date()));
        	while(true){
        		auditProcess();
    			Thread.sleep(6*60*1000);//5分钟一次
        	}
            
//            log.info("结束[总部系统-订单审核]任务:"+ CrawlerUtils.strFormatDate(new Date()));
//            log.info("结束[总部系统-订单审核]任务:"+ CrawlerUtils.strFormatDate(new Date()));
        } catch (Exception e) {
            log.info("[总部系统-订单分配页面老用户]任务出错", e);
        }
    }
	
	private String getCrawlerStr(){
		String str="{\"ActiveNo\":\"EM0595948920614937\",\"ClientIP\":\"10.142.194.167\",\"ConsiCertNum\":\"37152219850914053X\",\"ConsiCertType\":\"SFZ18\",\"ConsiGoodsCity\":\"330100\",\"ConsiGoodsDist\":\"330108\",\"ConsiGoodsProv\":\"330000\",\"ConsiName\":\"任全康\",\"ConsiPhone\":\"15658119769\",\"ConsiPostAddress\":\"杭州市滨江区滨安路1336号\",\"CustomerId\":\"3616112411877389\",\"GoodsInfo\":[{\"FeeInfo\":[{\"FeeDes\":\"号码预存费用\",\"FeeID\":\"2001\",\"OrigFee\":0,\"RealFee\":0,\"ReliefFee\":0}],\"GoodsAttInfo\":[{\"AppType\":\"\",\"CardType\":\"\",\"CertAddr\":\"杭州市滨江区滨安路1336号\",\"CertLoseTime\":\"2050-01-01 00:00:00\",\"CertNum\":\"37152219850914053X\",\"CertType\":\"SFZ18\",\"CheckType\":\"01\",\"CustomerName\":\"任全康\",\"CustomerType\":\"GRKH\",\"DevelopCode\":\"\",\"DevelopDepId\":\"\",\"DevelopName\":\"\",\"FirstMonBillMode\":\"\",\"GoodsType\":\"ZHKL\",\"GroupId\":\"\",\"LaterActFlag\":\"1\",\"NumType\":\"1\",\"Package\":[],\"PhoneInfo\":{\"NiceInfo\":{\"AdvancePay\":\"0\",\"CancelTagChe\":0,\"CancelTagPro\":0,\"ChangeTagChe\":0,\"ChangeTagPro\":0,\"ClassId\":9},\"NumNet\":\"未知\",\"OccupiedTime\":\"20591231235959\",\"OperatorState\":\"2\",\"PhoneNum\":\"17602067803\",\"ProKey\":\"999992554188749\",\"ProKeyMode\":\"1\"},\"ProductBrand\":\"4GPH\",\"ProductCode\":\"90063345\",\"ProductName\":\"大王卡\",\"ProductNet\":\"4G\",\"ProductType\":\"\",\"RealnameType\":\"1\",\"RefereeName\":\"\",\"RefereeNum\":\"\",\"ReliefPresFlag\":\"YES\",\"SaleMode\":\"\",\"SerType\":\"BAK\",\"Sex\":\"M\",\"SimId\":\"\",\"SubAppType\":\"\",\"UserType\":\"NEW\"}],\"GoodsCode\":511610241533,\"GoodsName\":\"视频大王2选1\",\"GoodsOrigFee\":0,\"GoodsRealFee\":0,\"GoodsReliefRes\":\"\"}],\"GoodsNum\":1,\"IfSendPhotos\":\"0\",\"LeagueInfo\":{},\"OrderAccCode\":\"SELF\",\"OrderAccType\":\"SELF\",\"OrderCity\":\"330100\",\"OrderId\":\"4567123489\",\"OrderOperType\":\"01\",\"OrderOrigFee\":0,\"OrderProvince\":\"330000\",\"OrderRealFee\":0,\"OrderReliefFee\":0,\"OrderSource\":\"MOBILE\",\"OrderTime\":\"2016-11-11 15:20:51\",\"OrigPostFee\":0,\"PayInfo\":{\"PayFinTime\":\"2016-11-11 15:20:51\",\"PayType\":\"ZXZF\",\"PayWay\":\"QEZF\"},\"PreSale\":\"1\",\"RealPostFee\":0,\"RegisterName\":\"3616112411877389\",\"deliMode\":\"KD\",\"deliTimeMode\":\"NOLIMIT\",\"useCustInfo\":{}}";
		return str;
	}
	
	/**
	 *  
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static void auditProcess() throws Exception {
		try {
			String username = ZBSystemClient.getLoginZbName();
			ZBSystemClient client = ClientPool.get(username);//获取总商登录客户端对象
			if (client != null) {//对象不为空则进行分配订单爬虫操作
				List<String> businessTypeList = new ArrayList<String>();
				businessTypeList.add("07");//蚂蚁宝卡
				businessTypeList.add("08");//腾讯王卡
				businessTypeList.add("09");//滴滴王卡
			
	            //调用批量分配逻辑
//				if(businessTypeList!=null && businessTypeList.size()>0){
//					for(int i=0;i<businessTypeList.size();i++){
//						client.getOrderAllocationInfo(client,businessTypeList.get(i));
//					}
//				}
				ZBSystemClient.getOrderAllocationInfoOrderCtn(client,"");
				log.info("批量订单分配页面老用户获取到客户端对象不为空！");
	        } else {
	            log.info("批量订单分配页面老用户获取不到客户端对象，请检查自动登录的cookie是否已失效!");
	            log.info("批量订单分配页面老用户获取不到客户端对象，请检查自动登录的cookie是否已失效!");
	        }
		} catch (Exception e) {
			log.info("[总部系统-批量订单分配页面老用户]任务出错", e);
		}
    }
}
