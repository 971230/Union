package com.ztesoft.net.server;

import org.apache.log4j.Logger;

import com.ztesoft.net.outter.inf.util.PeripheryHttpClient;

import net.sf.json.JSONObject;

public class sendInfoUtil {
	private static Logger logger = Logger.getLogger(sendInfoUtil.class);
	 public static void main(String[] args) {  
//{"ActiveNo":"EM4652647058240036","ClientIP":"223.104.20.186","ConsiCertNum":"522401197606168419","ConsiCertType":"SFZ18","ConsiGoodsCity":"441900","ConsiGoodsDist":"441901","ConsiGoodsProv":"440000","ConsiName":"王龙洲","ConsiPhone":"13018619421","ConsiPostAddress":"世博广场天源电脑城D1A62","CustomerId":"4115080563181007","DiscountInfo":[{"DiscountID":"41461","DiscountName":"【6折抢购 直降120元 】广东联通3G半年卡 ","DiscountType":"3","DiscountValue":120000}],"GoodsInfo":[{"FeeInfo":[{"FeeDes":"终端费用","FeeID":"1002","OrigFee":300000,"RealFee":300000,"ReliefFee":0}],"GoodsAttInfo":[{"CardType":"","CertAddr":"贵州省毕节市阿市乡木拉村湾子组29号","CertLoseTime":"2050-01-01 00:00:00","CertNum":"522401197606168419","CertType":"SFZ18","CustomerName":"王龙洲","CustomerType":"GRKH","DevelopCode":"","DevelopName":"","PhoneInfo":{"NiceInfo":{"AdvancePay":"0","CancelTagChe":0,"CancelTagPro":0,"ChangeTagChe":0,"ChangeTagPro":0,"ClassId":9},"OccupiedTime":"","OperatorState":"2","PhoneNum":"","ProKey":"4115080563181007","ProKeyMode":""},"ProPacCode":"2301","ProPacDesc":"10G流量","ProductBrand":"3GWK","ProductCode":"99003994","ProductName":"无线上网卡3GB半年套餐","ProductNet":"3G","RefereeName":"","RefereeNum":"","SaleMode":"CPB","SerType":"PRE"}],"GoodsCode":511307298095,"GoodsName":"【半年卡】3G上网卡 流量卡 半年包10G流量   续费充200即送100！","GoodsOrigFee":180000,"GoodsRealFee":180000,"GoodsReliefRes":"","GoodsType":"ZSWK"}],"GoodsNum":1,"InvoiceDesc":"MX","InvoiceTitle":"王龙洲","InvoiceType":"NOW","LeagueInfo":{},"OrderAccCode":"SELF","OrderAccType":"SELF","OrderCity":"441900","OrderId":"1404469719","OrderOperType":"01","OrderOrigFee":180000,"OrderProvince":"440000","OrderRealFee":180000,"OrderReliefFee":0,"OrderSource":"EMALL","OrderTime":"2015-09-28 22:14:26","OrigPostFee":0,"PayInfo":{"PayFinTime":"","PayType":"HDFK","PayWay":"XJZF"},"RealPostFee":0,"RegisterName":"13168958536","deliMode":"KD","deliTimeMode":"NOLIMIT"}
	//1404469719		
			   String json ="{\"ActiveNo\":\"EM4652647058240036\",\"ClientIP\":\"223.104.20.186\",\"ConsiCertNum\":\"522401197606168419\",\"ConsiCertType\":\"SFZ18\",\"ConsiGoodsCity\":\"441900\",\"ConsiGoodsDist\":\"441901\",\"ConsiGoodsProv\":\"440000\",\"ConsiName\":\"王龙洲\",\"ConsiPhone\":\"13018619421\",\"ConsiPostAddress\":\"世博广场天源电脑城D1A62\",\"CustomerId\":\"4115080563181007\",\"DiscountInfo\":[{\"DiscountID\":\"41461\",\"DiscountName\":\"【6折抢购 直降120元 】广东联通3G半年卡 \",\"DiscountType\":\"3\",\"DiscountValue\":120000}],\"GoodsInfo\":[{\"FeeInfo\":[{\"FeeDes\":\"终端费用\",\"FeeID\":\"1002\",\"OrigFee\":300000,\"RealFee\":300000,\"ReliefFee\":0}],\"GoodsAttInfo\":[{\"CardType\":\"\",\"CertAddr\":\"贵州省毕节市阿市乡木拉村湾子组29号\",\"CertLoseTime\":\"2050-01-01 00:00:00\",\"CertNum\":\"522401197606168419\",\"CertType\":\"SFZ18\",\"CustomerName\":\"王龙洲\",\"CustomerType\":\"GRKH\",\"DevelopCode\":\"\",\"DevelopName\":\"\",\"PhoneInfo\":{\"NiceInfo\":{\"AdvancePay\":\"0\",\"CancelTagChe\":0,\"CancelTagPro\":0,\"ChangeTagChe\":0,\"ChangeTagPro\":0,\"ClassId\":9},\"OccupiedTime\":\"\",\"OperatorState\":\"2\",\"PhoneNum\":\"\",\"ProKey\":\"4115080563181007\",\"ProKeyMode\":\"\"},\"ProPacCode\":\"2301\",\"ProPacDesc\":\"10G流量\",\"ProductBrand\":\"3GWK\",\"ProductCode\":\"99003994\",\"ProductName\":\"无线上网卡3GB半年套餐\",\"ProductNet\":\"3G\",\"RefereeName\":\"\",\"RefereeNum\":\"\",\"SaleMode\":\"CPB\",\"SerType\":\"PRE\"}],\"GoodsCode\":511307298095,\"GoodsName\":\"【半年卡】3G上网卡 流量卡 半年包10G流量   续费充200即送100！\",\"GoodsOrigFee\":180000,\"GoodsRealFee\":180000,\"GoodsReliefRes\":\"\",\"GoodsType\":\"ZSWK\"}],\"GoodsNum\":1,\"InvoiceDesc\":\"MX\",\"InvoiceTitle\":\"王龙洲\",\"InvoiceType\":\"NOW\",\"LeagueInfo\":{},\"OrderAccCode\":\"SELF\",\"OrderAccType\":\"SELF\",\"OrderCity\":\"441900\",\"OrderId\":\"1404469719\",\"OrderOperType\":\"01\",\"OrderOrigFee\":180000,\"OrderProvince\":\"440000\",\"OrderRealFee\":180000,\"OrderReliefFee\":0,\"OrderSource\":\"EMALL\",\"OrderTime\":\"2015-09-28 22:14:26\",\"OrigPostFee\":0,\"PayInfo\":{\"PayFinTime\":\"\",\"PayType\":\"HDFK\",\"PayWay\":\"XJZF\"},\"RealPostFee\":0,\"RegisterName\":\"13168958536\",\"deliMode\":\"KD\",\"deliTimeMode\":\"NOLIMIT\"}";

			   String url = "http://localhost:8080/servlet/centerMallOrderStanding";

			     
			   JSONObject jsonObject =  JSONObject.fromObject(json);
			   logger.info(jsonObject.toString());
		       String outPackage = null;  
		       try {
		    	   	outPackage = new PeripheryHttpClient().doPost(url, jsonObject.toString());  //总部
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       logger.info("--订单接口-模拟客服端报文-获得返回信息---------outPackage="+outPackage);  
			   //logger.info(send("6906020000026"));
		   }  
}
