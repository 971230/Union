package zte.net.service.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import params.ZteResponse;
import params.req.ZbOrderAuditDetailReq;
import params.req.ZbOrderAuditStatusReq;
import params.req.ZbOrderStateQueryReq;
import params.resp.ZbOrderAuditDetailResp;
import zte.net.ecsord.common.LocalCrawlerUtil;
import zte.net.service.IOrderArtificialSectionService;

import com.ztesoft.autoprocess.base.ClientPool;
import com.ztesoft.autoprocess.base.ZBSystemClient;
import com.ztesoft.net.mall.consts.ZBOrderUrlConsts;
import com.ztesoft.net.mall.utils.CrawlerSetting;

import consts.ConstsCore;
/**
 * 订单激活环节
 * @author ricky
 *
 */
public class OrderArtificialSectionService implements IOrderArtificialSectionService {
	private static Logger logger = Logger.getLogger(OrderArtificialSectionService.class);
	private static final String ftpPath="";
	private ZBSystemClient init(){
		ZBSystemClient client=null;
		try {
			String username = "";
			if(ZBSystemClient.clientLogin!=null){
				if(StringUtils.isNotBlank(ZBSystemClient.clientLogin.getLoginParam().getUsername())){
					username = ZBSystemClient.clientLogin.getLoginParam().getUsername();
					//logger.info("=================分配获取登录的用户名："+username);
				}else{
					username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
				}
			}else{
				username = LocalCrawlerUtil.ZB_DEFAULT_ACCOUNT_CODE;//默认用户
			}
			 client = ClientPool.get(username);//获取总商登录客户端对象
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("【总部系统-登陆系统异常】");
		}
		return client;
	}
	@Override
	public ZteResponse orderAuditStatusModify(ZbOrderAuditStatusReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		ZBSystemClient client = init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		try{
			//步骤一，进入激活详情界面
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("orderId", req.getOrderId()));
			String result = client.post(param, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL_DETAIL)+req.getOrderId()) ;
			if(result.indexOf(req.getOrderNo())<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("总商未查询到该订单，请确认当前订单是否处于激活环节");
				return resp;
			}
			//解析总商错误提醒
			Document doc = Jsoup.parse(result);
			Elements elemetns =doc.select("#goodsList-idfy-updated-tip span");
			if(null!=elemetns&&elemetns.size()>0){
				for(Element element :elemetns){
					String tips=element.text();
					if(StringUtils.isNotBlank(tips)&&StringUtils.indexOf(tips, "未上传")>=0){//未上传证件照
						resp.setError_code(ConstsCore.ERROR_FAIL);
						resp.setError_msg(tips);
						return resp;
					}
				}
			}
			//反馈校验是否通过
			Map<String,Object> map = new HashMap<String,Object>();
			param = new ArrayList<NameValuePair>();
			if("0".equals(req.getIsAuditPass())){
				param.add(new BasicNameValuePair("rstCode", "6"));
				param.add(new BasicNameValuePair("flag", "1"));
			}else{
				param.add(new BasicNameValuePair("rstCode", "8"));//先默认为8
				param.add(new BasicNameValuePair("contractPhone", req.getContactsPhoneNo()));
				param.add(new BasicNameValuePair("orderNo", req.getOrderNo()));
				param.add(new BasicNameValuePair("sendMsg", req.getIsSendMessage()));
				if("1".equals(req.getIsOutboundCall())){
					param.add(new BasicNameValuePair("detailDesc", "信息不规范（通用短信）"));
					param.add(new BasicNameValuePair("reason", ""));
				}else{
					param.add(new BasicNameValuePair("detailDesc", ""));
					param.add(new BasicNameValuePair("reason", req.getSendMessage()));
				}
				param.add(new BasicNameValuePair("flag", "0"));
			}
			param.add(new BasicNameValuePair("orderId", req.getOrderId()));
			param.add(new BasicNameValuePair("ptype", req.getCertType()));
			param.add(new BasicNameValuePair("isGroupCust","0" ));//先默认为0，总商注解：入网信息取group表 1--是的 0--不是
			result =client.post(param, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL_AUDIT)+req.getOrderId());
			logger.info("提交订单"+req.getOrderNo()+"激活环节审核结果时返回"+result);
			JSONObject obj = JSONObject.fromObject(result);
			if(obj.containsKey("success") && obj.getBoolean("success")){
				//激活
				result =client.post(new ArrayList(), CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL)+req.getOrderId());
				logger.info("订单"+req.getOrderNo()+"激活结果"+result); 
				obj = JSONObject.fromObject(result);
				String respCode = null;
				if(obj.containsKey("RespCode")){
					respCode = obj.getString("RespCode");
				}
				if ("0000".equals(respCode)) {
					resp.setError_code(ConstsCore.ERROR_SUCC);
					resp.setError_msg("激活成功");
			    }else if ("9999".equals(respCode)) {
			    	resp.setError_code(ConstsCore.ERROR_FAIL);
					resp.setError_msg("调用激活接口失败,请重试");
			    } else {
			    	if(obj.containsKey("result")){
			    		String respResult = obj.getString("result");
			         	if ("1111" == respResult) {
			            	resp.setError_code(ConstsCore.ERROR_FAIL);
							resp.setError_msg("照片未审核通过,不能激活");
			         	}
			    	}
			    }
			}else{
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("订单激活审核失败,请稍后再试");
				return resp;
			}				

		}catch(Exception e){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("总部激活环节审核异常");
			return  resp;
		}
		return resp;
	}
	@Override
	public ZbOrderAuditDetailResp orderAuditDetail(ZbOrderAuditDetailReq req) {
		// TODO Auto-generated method stub
		ZbOrderAuditDetailResp resp = new ZbOrderAuditDetailResp();
		ZBSystemClient client = init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("orderId", req.getOrderId()));
		List list = new ArrayList();
		try {
			//步骤一，进入激活详情界面
			String result = client.post(param, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_ARTIFICIAL_DETAIL)+req.getOrderId()) ;
			if(result.indexOf(req.getOrderNo())<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("总商未查询到该订单，请确认当前订单是否处于激活环节");
				return resp;
			}
			Document doc = Jsoup.parse(result);
			//步骤二解析并下载图片
			Elements photoElements = doc.select("#showAreaStep .menudiv img[src]"); 
			for(int i=0;i<photoElements.size();i++){
				Element photo = photoElements.get(i);
				String src = photo.attr("src");
				//client.getImage(ZBOrderUrlConsts.ZB_DOMAIN+src, fileName);
				//文件名等于订单号+编号
				String fileName = "image_"+req.getOrderNo()+"_"+i;
				boolean flag =uploadFile(CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ZB_DOMAIN)+src, fileName);
				if(flag){
					list.add(ftpPath+fileName);
				}
			}
			//步骤三解析视频并下载
			Elements videoElements = doc.select("#showAreaStep .menudiv video"); 
			for(int i=0;i<videoElements.size();i++){
				Element video = videoElements.get(i);
				String src = video.attr("src");
				//client.getImage(ZBOrderUrlConsts.ZB_DOMAIN+src, fileName);
				//文件名等于订单号+编号
				String fileName = "video_"+req.getOrderNo()+"_"+i;
				boolean flag =uploadFile(CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ZB_DOMAIN)+src, fileName);
				if(flag){
					list.add(ftpPath+fileName);
				}
			}
			resp.setFilePath(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return resp;
	}
	
	/**
	 * add by duan.jingliang
	 * 订单状态查询
	 * */
	@Override
	public ZteResponse orderStateQuery(ZbOrderStateQueryReq req) {
		// TODO Auto-generated method stub
		ZteResponse resp = new ZteResponse();
		ZBSystemClient client = init();
		if(null==client){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未登陆总商系统，请先登录!");
			return  resp;
		}
		
		try {
			//步骤一，发请求, 查询订单状态;  客服订单管理>>客服订单查询
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderNo",req.getOrderNo()));
			params.add(new BasicNameValuePair("page.webPager.action","refresh"));
			params.add(new BasicNameValuePair("page.webPager.currentPage","1"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.totalSize","1"));
			params.add(new BasicNameValuePair("page.webPager.pageInfo.pageSize","5"));
			params.add(new BasicNameValuePair("pageSize","5"));
			String result = client.post(params, CrawlerSetting.OPERATION_URL_MAP.get(ZBOrderUrlConsts.ORDER_STATE_QUERY));
			
			//logger.info("总商订单状态查询返回: " + result);
			if(result.indexOf(req.getOrderNo())<=0){
				resp.setError_code(ConstsCore.ERROR_FAIL);
				resp.setError_msg("总商未查询到该订单，请确认订单编号是否正确");
				return resp;
			}
			//步骤二解析状态
			String orderState = "";
			Document doc = Jsoup.parse(result);
			Elements stateElements = doc.select("#pageTable .tableBody .processFourth p"); 
			if (null != stateElements && stateElements.size() > 0) {
				Element stateRecord = stateElements.get(stateElements.size() - 1);
				orderState = stateRecord.text();
			}
			logger.info("==========orderState:" + orderState);
			resp.setBody(orderState);
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("查询状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("查询状态异常!" + e.getMessage());
		} 
		return resp;
	}
	
	public  synchronized boolean uploadFile(String aurl,String fileName){
		URL url;  
        BufferedInputStream  input=null;
        boolean flag = false; 
        int port=21;
        String ip="";
        String userName="";
        String password="";
        try {
			url = new URL(aurl);
			HttpURLConnection  con = (HttpURLConnection )url.openConnection();  
		    con.setReadTimeout(30000);
		    con.setConnectTimeout(30000);
		    con.setRequestMethod("GET");
		    if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
           	 input = new BufferedInputStream(con.getInputStream());  
           	 try { 
                    FTPClient ftpClient = new FTPClient(); 
                    ftpClient.setControlEncoding("GBK"); 
                    ftpClient.setDefaultPort(port); 
                   // ftpClient.configure(getFtpConfig()); 
                    ftpClient.connect(ip); 
                    ftpClient.login(userName, password); 
                    ftpClient.setDefaultPort(port); 
                    int reply = ftpClient.getReplyCode(); 
                    ftpClient.setDataTimeout(30000); 
                    if (!FTPReply.isPositiveCompletion(reply)) { 
                            ftpClient.disconnect(); 
                            System.err.println("FTP server refused connection."); 
                    }
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                    ftpClient.enterLocalPassiveMode(); 
                    ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); 
                    ftpClient.changeWorkingDirectory(ftpPath); 

                    flag = ftpClient.storeFile(fileName, input); 
                    if (flag) { 
                            logger.info("上传文件成功！"); 
                    } else { 
                            logger.info("上传文件失败！"); 
                    } 
            } catch (Exception e) { 
                 e.printStackTrace(); 
            }finally{
            	if(input!=null){
            		try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
       }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
             // 打开URL连接  
        return  flag;
	}
}
