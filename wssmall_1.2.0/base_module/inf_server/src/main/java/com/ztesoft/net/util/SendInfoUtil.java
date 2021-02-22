package com.ztesoft.net.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;
import zte.params.goods.req.ProductInfoGetReq;
import zte.params.goods.resp.ProductInfoGetResp;

import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.PeripheryHttpClient;
import com.ztesoft.net.mall.core.model.Goods;

public class SendInfoUtil {
	private static Logger logger = Logger.getLogger(SendInfoUtil.class);
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url
					+ "?"
					+ param.replace("\"", "%22").replace("{", "%7b")
							.replace("}", "%7d");
			;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				logger.info(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.info("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

    /** 
    * 发送post请求,客户端采用二进制流发送,服务端采用二进制流介绍 
    * @param json  入参的json格式的报文 
    * @param url    http服务器的地址 
    * @return  返回响应信息 
    */  
   public static String postHttpReq(String json,String url) {  
       HttpClient httpClient = new HttpClient();  
         
       EntityEnclosingMethod postMethod = new PostMethod(); 
	try {
		byte b[] = json.getBytes("utf-8");
		RequestEntity requestEntity = new ByteArrayRequestEntity(b);
		postMethod.setRequestEntity(requestEntity);// 设置数据  
	} catch (UnsupportedEncodingException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}//把字符串转换为二进制数据  
         
 
        
       
       postMethod.setPath(url);// 设置服务的url  
       postMethod.setRequestHeader("Content-Type", "text/html;charset=utf-8");// 设置请求头编码  
 
       // 设置连接超时  
       httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(  
               5 * 1000);  
       // 设置读取超时  
       httpClient.getHttpConnectionManager().getParams().setSoTimeout(200 * 1000);  
 
       String responseMsg = "";  
       int statusCode = 0;  
       try {  
           statusCode = httpClient.executeMethod(postMethod);// 发送请求  
           responseMsg = postMethod.getResponseBodyAsString();// 获取返回值 
           logger.info("--------------------------------");    
       } catch (HttpException e) {  
           e.printStackTrace();  
       } catch (IOException e) {  
           e.printStackTrace();  
       } finally {  
           postMethod.releaseConnection();// 释放连接  
       }  
 
       if (statusCode != HttpStatus.SC_OK) {  
           logger.info("CCCCCCCCCCCCCCCCCCCC================================HTTP服务异常" + statusCode);  
       }  
       return responseMsg;  
   }  
     
   //POST方式发送HTTP请求  
   public static void main(String[] args) {  
	//String url = "http://192.168.17.112:8777/wcsweb/integration/basBrand.sync";  
	  	   //	   String json = "{                                                                             " +
//			   "    \"order_req\": {                                                          " +
//			   "        \"source_system\": \"10008\",                                         " +
//			   "        \"receive_system\": \"10011\",                                        " +
//			   "        \"serial_no\": \"51fe72ce-960b-471f-927e-7fe3a6a47020\",              " +
//			   "        \"time\": \"20140618050700\",                                         " +
//			   "        \"order_id\": \"WCS14061816584642817483\",                            " +
//			   "        \"order_city\": \"440100\",                                           " +
//			   "        \"development_code\": \"5122222220\",                                 " +
//			   "        \"development_name\": \"5122222220\",                                 " +
//			   "        \"order_type\": \"1\",                                                " +
//			   "        \"status\": \"WAIT_BUYER_PAY\",                                       " +
//			   "        \"platform_status\": \"WAIT_BUYER_PAY\",                              " +
//			   "        \"create_time\": \"20140618045846\",                                  " +
//			   "        \"order_amount\": 1,                                                  " +
//			   "        \"order_heavy\": \"0\",                                               " +
//			   "        \"order_disacount\": \"0\",                                           " +
//			   "        \"pay_money\": 1,                                                     " +
//			   "        \"invoice_content\": \"1\",                                           " +
//			   "        \"source_from_system\": \"10008\",                                    " +
//			   "        \"source_from\": \"10031\",                                           " +
//			   "        \"ship_area\": \"\",                                                  " +
//			   "        \"order_provinc_code\": \"440000\",                                   " +
//			   "        \"order_city_code\": \"440100\",                                      " +
//			   "        \"alipay_id\": \"无账号\",                                            " +
//			   "        \"pay_type\": \"HDFK\",                                               " +
//			   "        \"payment_type\": \"HDFK\",                                           " +
//			   "        \"channel_type\": 77,                                                 " +
//			   "        \"channel_id\": 16,                                                   " +
//			   "        \"chanel_name\": \"广州四海通网络通讯系统有限公司\",                  " +
//			   "        \"shipping_type\": \"KD\",                                            " +
//			   "        \"ship_name\": \"James\",                                             " +
//			   "        \"ship_city\": \"440100\",                                            " +
//			   "        \"ship_country\": \"440105\",                                         " +
//			   "        \"ship_addr\": \"368大厦\",                                           " +
//			   "        \"ship_phone\": \"13560408475\",                                      " +
//			   "        \"name\": \"James\",                                                  " +
//			   "        \"uname\": \"James\",                                                 " +
//			   "        \"abnormal_status\": \"正常\",                                        " +
//			   "        \"delivery_status\": \"未发货\",                                      " +
//			   "        \"order_list\": [                                                     " +
//			   "            {                                                                 " +
//			   "                \"activity_list\": [],                                        " +
//			   "                \"prod_offer_code\": \"6905020000096\",                       " +
//			   "                \"prod_offer_name\": \"MIUI/小米 红米手机 联通3G版\",         " +
//			   "                \"prod_offer_type\": \"20003\",                               " +
//			   "                \"offer_disacount_price\": \"0\",                             " +
//			   "                \"offer_coupon_price\": 1,                                    " +
//			   "                \"prod_offer_num\": 1,                                        " +
//			   "                \"prod_offer_heavy\": \"0\",                                  " +
//			   "                \"inventory_code\": \"\",                                     " +
//			   "                \"inventory_name\": \"\",                                     " +
//			   "                \"offer_price\": 1,                                           " +
//			   "                \"good_no_deposit\": \"0\",                                   " +
//			   "                \"package_sale\": \"0\",                                      " +
//			   "                \"sku_param\": [                                              " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"acc_nbr\",                          " +
//			   "                        \"param_name\": \"用户号码\",                         " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"family_no\",                        " +
//			   "                        \"param_name\": \"亲情号码\",                         " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"love_no\",                          " +
//			   "                        \"param_name\": \"情侣号\",                           " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"net_type\",                         " +
//			   "                        \"param_name\": \"网别\",                             " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"is_goodno\",                        " +
//			   "                        \"param_name\": \"靓号\",                             " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"certi_type\",                       " +
//			   "                        \"param_name\": \"证件类型\",                         " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"certi_num\",                        " +
//			   "                        \"param_name\": \"证件号码\",                         " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"certi_address\",                    " +
//			   "                        \"param_name\": \"证件地址\",                         " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"cust_name\",                        " +
//			   "                        \"param_name\": \"客户名称\",                         " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    },                                                        " +
//			   "                    {                                                         " +
//			   "                        \"param_code\": \"certi_valid_date\",                 " +
//			   "                        \"param_name\": \"证件有效期\",                       " +
//			   "                        \"param_value_code\": \"\"                            " +
//			   "                    }                                                         " +
//			   "                ],                                                            " +
//			   "                \"sku_fee\": []                                               " +
//			   "            }                                                                 " +
//			   "        ]                                                                     " +
//			   "    }                                                                         " +
//			   "}                                                                             " ;
	   //String url = "http://127.0.0.1:8080/mgWeb/ECSSERV/informationServerServlet"; 
//		String json = "{                                                                                        " + 
//				"    \"order_req\": {                                                                     " +
//				"        \"source_system\": \"10008\",                                                    " +
//				"        \"receive_system\": \"10011\",                                                   " +
//				"        \"serial_no\": \"f975d7e1-be4e-46c9-bc06-2c6f0ed73de1\",                         " +
//				"        \"time\": \"20140609040600\",                                                    " +
//				"        \"order_id\": \"WCS14060916044536318189\",                                       " +
//				"        \"order_type\": \"1\",                                                           " +
//				"        \"status\": \"WAIT_SELLER_SEND_GOODS\",                                          " +
//				"        \"platform_status\": \"WAIT_SELLER_SEND_GOODS\",                                 " +
//				"        \"create_time\": \"20140609040445\",                                             " +
//				"        \"order_amount\": \"19700101080000\",                                            " +
//				"        \"order_heavy\": \"0\",                                                          " +
//				"        \"order_disacount\": \"0\",                                                      " +
//				"        \"pay_money\": 1,                                                                " +
//				"        \"invoice_type\": \"2\",                                                         " +
//				"        \"invoice_print_type\": \"3\",                                                   " +
//				"        \"invoice_content\": \"1\",                                                      " +
//				"        \"order_comment\": \"沃百富用户赠送 优志魔术棒移动电源 1个\",                    " +
//				"        \"invoice_title\": \"王昭\",                                                     " +
//				"        \"source_from_system\": \"10008\",                                               " +
//				"        \"source_from\": \"10039\",                                                      " +
//				"        \"ship_area\": \"\",                                                             " +
//				"        \"order_provinc_code\": \"440000\",                                              " +
//				"        \"order_city_code\": \"440100\",                                                 " +
//				"        \"alipay_id\": \"无账号\",                                                       " +
//				"        \"baidu_no\": \"20140609030844\",                                                " +
//				"        \"baidu_money\": \"1\",                                                          " +
//				"        \"baidu_account\": \"539411b6f9dcda25d8eafcb7\",                                 " +
//				"        \"baidu_begin_time\": \"2014-06-09 16:04:45\",                                   " +
//				"        \"baidu_end_time\": \"2016-07-31 23:59:59\",                                     " +
//				"        \"pay_time\": \"20140609040554\",                                                " +
//				"        \"payment_type\": \"BDLC\",                                                      " +
//				"        \"channel_type\": 198,                                                           " +
//				"        \"channel_id\": 18070,                                                           " +
//				"        \"chanel_name\": \"yancan\",                                                     " +
//				"        \"shipping_type\": \"KD\",                                                       " +
//				"        \"ship_name\": \"王昭\",                                                         " +
//				"        \"ship_city\": \"441900\",                                                       " +
//				"        \"ship_country\": \"441902\",                                                    " +
//				"        \"ship_addr\": \"莞太路6号\",                                                    " +
//				"        \"ship_phone\": \"13215331533\",                                                 " +
//				"        \"ship_email\": \"348871183@QQ.COM\",                                            " +
//				"        \"bill_mail_post_code\": \"523000\",                                             " +
//				"        \"name\": \"王昭\",                                                              " +
//				"        \"uname\": \"王昭\",                                                             " +
//				"        \"abnormal_status\": \"正常\",                                                   " +
//				"        \"delivery_status\": \"未发货\",                                                 " +
//				"        \"order_list\": [                                                                " +
//				"            {                                                                            " +
//				"                \"activity_list\": [],                                                   " +
//				"                \"development_name\": \"5112345678\",                                    " +
//				"                \"reference_name\": \"王昭\",                                            " +
//				"                \"prod_offer_code\": \"201405042365002543\",                             " +
//				"                \"offer_disacount_price\": \"0\",                                        " +
//				"                \"offer_coupon_price\": 1,                                               " +
//				"                \"prod_offer_num\": 1,                                                   " +
//				"                \"prod_offer_heavy\": \"0\",                                             " +
//				"                \"certi_type\": \"SFZ18\",                                               " +
//				"                \"cust_name\": \"王昭\",                                                 " +
//				"                \"certi_num\": \"430921198111050885\",                                   " +
//				"                \"certi_address\": \"湖南省南县茅草街镇\",                               " +
//				"                \"offer_price\": 1,                                                      " +
//				"                \"good_no_deposit\": 0,                                                  " +
//				"                \"new_acc_nbr\": \"18502072960\",                                        " +
//				"                \"offer_eff_type\": \"COMM\",                                            " +
//				"                \"development_code\": \"5112345678\"                                     " +
//				"            }                                                                            " +
//				"        ]                                                                                " +
//				"    }                                                                                    " +
//				"}                                                                                        " ;
  
	   
	   String json ="{\"ActiveNo\":\"EM9926272763295602\",\"ClientIP\":\"61.144.224.161\",\"ConsiCertNum\":\"440921198005070032\",\"ConsiCertType\":\"SFZ18\",\"ConsiGoodsCity\":\"440300\",\"ConsiGoodsDist\":\"440307\",\"ConsiGoodsProv\":\"330000\",\"ConsiName\":\"李灿\",\"ConsiPhone\":\"13502810454\",\"ConsiPostAddress\":\"清林中路环保与水务局大楼902\",\"CustomerId\":\"2715012043019874\",\"DiscountInfo\":[{\"DiscountID\":\"23840\",\"DiscountName\":\" 【上网卡】季卡 300元 \",\"DiscountType\":\"3\",\"DiscountValue\":101000}],\"GoodsInfo\":[{\"FeeInfo\":[{\"FeeDes\":\"终端费用\",\"FeeID\":\"1002\",\"OrigFee\":300000,\"RealFee\":300000,\"ReliefFee\":0}],\"GoodsAttInfo\":[{\"CardType\":\"\",\"CertAddr\":\"广东省深圳市龙岗区兴华路19号西湖苑金豪阁803\",\"CertLoseTime\":\"2050-01-01 00:00:00\",\"CertNum\":\"440921198005070032\",\"CertType\":\"SFZ18\",\"CustomerName\":\"李灿\",\"CustomerType\":\"GRKH\",\"DevelopCode\":\"\",\"DevelopName\":\"\",\"PhoneNum\":\"\",\"ProPacCode\":\"2372\",\"ProPacDesc\":\"9GB极速卡季卡\",\"ProductBrand\":\"3GWK\",\"ProductCode\":\"99005969\",\"ProductName\":\"省分预付费无线上网卡产品包1\",\"ProductNet\":\"3G\",\"RefereeName\":\"\",\"RefereeNum\":\"\",\"SaleMode\":\"CPB\",\"SerType\":\"PRE\"}],\"GoodsCode\":511203263466,\"GoodsName\":\"【6.6折促销！】广东联通3G极速季卡（广州 揭阳 中山 江门 汕尾四地市无货）\",\"GoodsOrigFee\":199000,\"GoodsRealFee\":199000,\"GoodsReliefRes\":\"\",\"GoodsType\":\"ZSWK\"}],\"GoodsNum\":1,\"InvoiceDesc\":\"MX\",\"InvoiceTitle\":\"李灿\",\"InvoiceType\":\"NOW\",\"LeagueInfo\":{},\"OrderAccCode\":\"SELF\",\"OrderAccType\":\"SELF\",\"OrderCity\":\"440300\",\"OrderId\":\"3033504027\",\"OrderOperType\":\"01\",\"OrderOrigFee\":199000,\"OrderProvince\":\"330000\",\"OrderRealFee\":199000,\"OrderReliefFee\":0,\"OrderSource\":\"EMALL\",\"OrderTime\":\"2015-01-20 15:02:41\",\"OrigPostFee\":0,\"PayInfo\":{\"PayFinTime\":\"\",\"PayType\":\"HDFK\",\"PayWay\":\"XJZF\"},\"RealPostFee\":0,\"RegisterName\":\"1404062028@qq.com\",\"deliMode\":\"KD\",\"deliTimeMode\":\"WORKDAY\"}";
			   //	   String url = "http://10.123.180.104:10001/servlet/InformationServerServlet"; 
//	   String url = "http://112.96.28.191:10003/servlet/InformationServerServlet";
//	   String url = "http://112.96.28.191:10003/servlet/ZBMallOrderServlet";
//	   String url = "http://localhost/ServletTest/servlet/ServletTest";
//	   String url = "http://localhost/servlet/ZBMallOrderServlet";
//	   String url = "http://172.0.1.217:8080/servlet/ZBMallOrderServlet";
	   String url = "http://172.0.1.217:8080/servlet/InformationServerServlet";
//		    String time ="20140416103242";
//		    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
//	        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//	        String times = null;  
//	        try {  
//	            times = sdf2.format(sdf.parse(time));  
//	            logger.info("time:::::::::::::::::::::::::::=" + times);  
//	        } catch (ParseException e) {  
//	            // TODO Auto-generated catch block  
//	            e.printStackTrace();  
//	        }  
	     
	   JSONObject jsonObject =  JSONObject.fromObject(json);
	   logger.info(jsonObject.toString());
//	    String order_req = jsonObject.get("order_req").toString();
//	    JSONObject jsonObject_order_req = JSONObject.fromObject(order_req);
//	    logger.info("jsonObject_order_req=============报文order_req=："+jsonObject_order_req);
       String outPackage = null;  
       outPackage = postHttpReq(jsonObject.toString(), url);   //新商城
       PeripheryHttpClient pcClient = new PeripheryHttpClient();
       try {
//    	   	outPackage = new PeripheryHttpClient().doPost(url, jsonObject.toString());  //总部
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       logger.info("--订单接口-模拟客服端报文-获得返回信息---------outPackage="+outPackage);  
	   //logger.info(send("6906020000026"));
   }  


	public static Map send(String goods_id) {
		Map aa = new HashMap();

		ProductInfoGetReq req = new ProductInfoGetReq();
		req.setProduct_id(goods_id);

		ZteClient client = ClientFactory
				.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		ProductInfoGetResp response = client.execute(req,
				ProductInfoGetResp.class);
		Goods goods = response.getProduct();
		aa.put("name", goods.getName());
		return aa;
	}

	public static String searchGoodsEcs(String goods_id) {

		ProductInfoGetReq req = new ProductInfoGetReq();
		req.setProduct_id(goods_id);

		ZteClient client = ClientFactory
				.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
		ProductInfoGetResp response = client.execute(req,
				ProductInfoGetResp.class);
		Goods goods = response.getProduct();

		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("{\"goods_info_req\":");
		jsonStr.append("{\"serial_no\":").append("此处为序列号").append(",");
		jsonStr.append("\"time\":").append("此处为时间").append(",");

		jsonStr.append("\"sku\":").append(goods.getSku()).append(",");
		jsonStr.append("\" source_system \":").append("     ").append(",");
		jsonStr.append("\"receive_system\":").append("     ").append(",");
		jsonStr.append("\"action\":").append("A ").append(",");

		jsonStr.append("\"goods_name\":").append("\"").append(goods.getName())
				.append("\",");
		jsonStr.append("\"goods_brand\":").append("\"")
				.append(goods.getBrand_name()).append("\",");
		jsonStr.append("\"goods_category\":").append("\"")
				.append(goods.getCat_name()).append("\",");
		jsonStr.append("\"goods_type\":").append("\"")
				.append(goods.getGoods_type()).append("\",");
		jsonStr.append("\"goods_class\":").append("\"")
				.append(goods.getModel()).append("\",");
		jsonStr.append("\"goods_state\":").append("\"")
				.append(goods.getState()).append("\",");

		/*
		 * jsonStr.append("\"goods_attr\":["); String str=goods.getParams();
		 * str=str.substring(1,str.lastIndexOf("]")); paramsL
		 * pl=JsonUtil.fromJson(str, paramsL.class); for(int
		 * i=0;i<pl.getParamList().size();i++){
		 * 
		 * paramsenum tmp=pl.getParamList().get(i); jsonStr.append("{");
		 * jsonStr.append("\"param_code\":").append(tmp.getEname()).append(",");
		 * jsonStr.append("\"param_name\":").append(tmp.getName()).append(",");
		 * jsonStr
		 * .append("\"param_value_code\":").append(tmp.getAttrcode()).append
		 * (",");
		 * jsonStr.append("\"param_value\":").append(tmp.getValue()).append
		 * (","); jsonStr.append("\"sku_attr\":").append(tmp.attrvaltype);
		 * jsonStr.append("}"); if(1<pl.getParamList().size()-1)
		 * jsonStr.append(","); }
		 */

		jsonStr.append("]}}");

		return jsonStr.toString();
	}

}
