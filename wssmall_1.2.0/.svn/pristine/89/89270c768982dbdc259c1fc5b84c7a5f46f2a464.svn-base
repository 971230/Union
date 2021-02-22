package com.ztesoft.net.server.jsonserver.wcfpojo;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JavaIdentifierTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import params.req.HBBroadbandOrderReq;

import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrder;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderBroadInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderContactInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderCustInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderDeveloperInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderPayInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderSubProdInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderSubsOtherInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderBroadInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderContactInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderCustInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderDeveloperInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderPayInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderPhoneInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderSubProdInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupBusiOrderSubsOtherInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InterGroupFeeInfo;
import com.ztesoft.net.server.jsonserver.hlwrhywpojo.InternetGroupBusiOrder;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiContactInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiCustInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiDeveloperInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiNiceInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiOrder;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiPayInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiPhoneInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiSubProdInfo;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrder;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrderContactInfo;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrderCustInfo;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrderDeveloperInfo;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrderPayInfo;
import com.ztesoft.net.server.jsonserver.mobilebusipojo.MobileBusiOrderSeedUserInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallCustInfoa;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallDeliverInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallDiscountInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallFeeInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallGoodsInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallOrder;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallPayInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceBusiOrder;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceContactInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceCustInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceFeeInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceGoodsInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceObjectInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplacePayInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrder;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderBroadInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderFeeInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderPartners;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderPhoneInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrder;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderBroadInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderCustInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderDeliveryInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderDeveloperInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderPayInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderSubProdInfo;
import com.ztesoft.net.server.jsonserver.wdgwywpojo.FixBusiOrderSubsOtherInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallActivityInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallDiscountInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallFeeInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallFlowInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGiftInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsAttInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsNiceInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsPhoneInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallLeagueInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallOperatorInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallOrder;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallPackage;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallPayInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallResourcesInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallSubProdInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallUseCustInfo;
import com.ztesoft.net.server.jsonserver.zjpojo.Item;
import com.ztesoft.net.server.jsonserver.zjpojo.ZJLocalMallOrder;

public class MallUtils {
	
	private static Logger logger = Logger.getLogger(MallUtils.class);

	public static void main(String[] args) throws ParseException {

		String order_id = "WCS14122811420496932780";
		String seqNo = "20131117020200128146";
		String acc_nbr = "18617206384";
//		String json = "{\"order_req\": {\"source_system\": \"10008\",\"receive_system\": \"10011\",\"serial_no\": \"704e12d3-5400-41f8-b832-8e5a6c5c6df6\",\"time\": \"20141228114250\",\"order_id\": \""+order_id+"\",\"order_city\": \"441900\",\"development_code\": \"5100009527\",\"order_type\": \"1\",\"status\": \"WAIT_SELLER_SEND_GOODS\",\"platform_status\": \"WAIT_SELLER_SEND_GOODS\",\"create_time\": \"20141228114204\",\"order_amount\": 460000,\"order_heavy\": \"0\",\"order_disacount\": 0,\"pay_money\": 460000,\"source_from_system\": \"10008\",\"source_from\": \"10031\",\"ship_area\": \"\",\"order_provinc_code\": \"440000\",\"order_city_code\": \"441900\",\"alipay_id\": \"无账号\",\"uid\": \"510000\",\"is_deal\": \"0\",\"pay_time\": \"20141228114019\",\"payment_serial_no\": \""+seqNo+"\",\"payment_code\": \"102\",\"payment_name\": \"联通支付有限公司\",\"pay_type\": \"ZXZF\",\"payment_type\": \"QEZF\",\"channel_id\": \"newmall001\",\"chanel_name\": \"自建自营厅(传统)\",\"channel_mark\": \"1\",\"ssyh\": \"newmall001\",\"bss_operator\": \"HLWDG679\",\"oss_operator\": \"GD001838\",\"order_comment\": \"6元/月 来电显示,10元包200条,5元/月 手机炫铃,32元包200分钟,8元包100MB;；渠道标记：传统营业厅\",\"shipping_type\": \"XJ\",\"shipping_time\": \"NOLIMIT\",\"ship_city\": \"\",\"ship_country\": \"\",\"ship_name\": \"测试\",\"ship_phone\": \"18602031952\",\"name\": \"测试\",\"uname\": \"测试\",\"abnormal_status\": \"正常\",\"delivery_status\": \"未发货\",\"shipping_amount\": 0,\"n_shipping_amount\": 0,\"shipping_quick\": \"01\",\"is_to4g\": \"0\",\"order_list\": [{\"Package\": [{\"PackageCode\": \"5869070000120140620000019\"},{\"PackageCode\": \"5869070000320140620000011\"},{\"PackageCode\": \"5869070000420140620000017\"},{\"PackageCode\": \"5869070000520141209002079\"},{\"PackageCode\": \"5869070000620140620000050\"}],\"prod_offer_code\": \"596901020620141001016719\",\"prod_offer_name\": \"4G组合套餐自由选择随意组合！4G组合套餐2.0版震撼来袭！！全国版\",\"prod_offer_type\": \"20000\",\"offer_disacount_price\": 0,\"offer_coupon_price\": 100000,\"prod_offer_num\": 1,\"prod_offer_heavy\": \"0\",\"card_type\": \"NM\",\"certi_type\": \"SFZ18\",\"cust_name\": \"测试\",\"certi_num\": \"411302198607091854\",\"invoice_print_type\": \"3\",\"offer_price\": 100000,\"good_no_deposit\": 360,\"offer_eff_type\": \"ALLM\",\"package_sale\": \"YES\",\"is_change\": \"0\",\"sku_param\": [{\"param_code\": \"acc_nbr\",\"param_name\": \"用户号码\",\"param_value_code\": \"\",\"param_value\": \""+acc_nbr+"\"},{\"param_code\": \"is_old\",\"param_name\": \"是否老用户\",\"param_value_code\": \"\",\"param_value\": \"0\"},{\"param_code\": \"family_no\",\"param_name\": \"亲情号码\",\"param_value_code\": \"\",\"param_value\": \"\"},{\"param_code\": \"if_love_no\",\"param_name\": \"是否情侣号\",\"param_value_code\": \"\",\"param_value\": \"0\"},{\"param_code\": \"net_type\",\"param_name\": \"网别\",\"param_value_code\": \"\",\"param_value\": \"3G\"},{\"param_code\": \"is_goodno\",\"param_name\": \"靓号\",\"param_value_code\": \"\",\"param_value\": \"1\"},{\"param_code\": \"good_no_fee\",\"param_name\": \"靓号预存\",\"param_value_code\": \"\",\"param_value\": 360},{\"param_code\": \"good_no_low\",\"param_name\": \"靓号低消\",\"param_value_code\": \"\",\"param_value\": 96},{\"param_code\": \"bill_type\",\"param_name\": \"账户付费方式\",\"param_value_code\": \"\",\"param_value\": \"10\"},{\"param_code\": \"card_type\",\"param_name\": \"卡类型\",\"param_value_code\": \"\",\"param_value\": \"NM\"},{\"param_code\": \"guarantor\",\"param_name\": \"担保人\",\"param_value_code\": \"\",\"param_value\": \"无\"},{\"param_code\": \"bill_mail_type\",\"param_name\": \"账单寄送方式\",\"param_value_code\": \"\",\"param_value\": \"00\"}]}]}}";
		String json = "{ \"order_req\": { \"source_system\": \"10008\", \"receive_system\": \"10011\", \"serial_no\": \"704e12d3-5400-41f8-b832-8e5a6c5c6df6\", \"time\": \"20141228114250\", \"order_id\": \""+order_id+"\", \"order_city\": \"441900\", \"development_code\": \"5100009527\", \"order_type\": \"1\", \"status\": \"WAIT_SELLER_SEND_GOODS\", \"platform_status\": \"WAIT_SELLER_SEND_GOODS\", \"create_time\": \"20141228114204\", \"order_amount\": 460000, \"order_heavy\": \"0\", \"order_disacount\": 0, \"pay_money\": 460000, \"source_from_system\": \"10008\", \"source_from\": \"10031\", \"ship_area\": \"\", \"order_provinc_code\": \"440000\", \"order_city_code\": \"441900\", \"alipay_id\": \"无账号\", \"uid\": \"510000\", \"is_deal\": \"0\", \"pay_time\": \"20141228114019\", \"payment_serial_no\": \""+seqNo+"\", \"payment_code\": \"102\", \"payment_name\": \"联通支付有限公司\", \"pay_type\": \"ZXZF\", \"payment_type\": \"QEZF\", \"channel_id\": \"newmall001\", \"chanel_name\": \"自建自营厅(传统)\", \"channel_mark\": \"1\", \"ssyh\": \"newmall001\", \"bss_operator\": \"HLWDG679\", \"oss_operator\": \"GD001838\", \"order_comment\": \"6元/月 来电显示,10元包200条,5元/月 手机炫铃,32元包200分钟,8元包100MB;；渠道标记：传统营业厅\", \"shipping_type\": \"XJ\", \"shipping_time\": \"NOLIMIT\", \"ship_city\": \"\", \"ship_country\": \"\", \"ship_name\": \"测试\", \"ship_phone\": \"18602031952\", \"name\": \"测试\", \"uname\": \"测试\", \"abnormal_status\": \"正常\", \"delivery_status\": \"未发货\", \"shipping_amount\": 0, \"n_shipping_amount\": 0, \"shipping_quick\": \"01\", \"is_to4g\": \"0\", \"order_list\": [ { \"Package\": [ { \"PackageCode\": \"5869070000120140620000019\" }, { \"PackageCode\": \"5869070000320140620000011\" }, { \"PackageCode\": \"5869070000420140620000017\" }, { \"PackageCode\": \"5869070000520141209002079\" }, { \"PackageCode\": \"5869070000620140620000050\" } ], \"prod_offer_code\": \"596901020620141001016719\", \"prod_offer_name\": \"4G组合套餐 自由选择随意组合！4G组合套餐2.0版震撼来袭！！全国版\", \"prod_offer_type\": \"20000\", \"offer_disacount_price\": 0, \"offer_coupon_price\": 100000, \"prod_offer_num\": 1, \"prod_offer_heavy\": \"0\", \"card_type\": \"NM\", \"certi_type\": \"SFZ18\", \"cust_name\": \"测试\", \"certi_num\": \"411302198607091854\", \"invoice_print_type\": \"3\", \"offer_price\": 100000, \"good_no_deposit\": 360, \"offer_eff_type\": \"ALLM\", \"package_sale\": \"YES\", \"is_change\": \"0\", \"sku_param\": [ { \"param_code\": \"acc_nbr\", \"param_name\": \"用户号码\", \"param_value_code\": \"\", \"param_value\": \""+acc_nbr+"\" }, { \"param_code\": \"is_old\", \"param_name\": \"是否老用户\", \"param_value_code\": \"\", \"param_value\": \"0\" }, { \"param_code\": \"family_no\", \"param_name\": \"亲情号码\", \"param_value_code\": \"\", \"param_value\": \"\" }, { \"param_code\": \"if_love_no\", \"param_name\": \"是否情侣号\", \"param_value_code\": \"\", \"param_value\": \"0\" }, { \"param_code\": \"net_type\", \"param_name\": \"网别\", \"param_value_code\": \"\", \"param_value\": \"3G\" }, { \"param_code\": \"is_goodno\", \"param_name\": \"靓号\", \"param_value_code\": \"\", \"param_value\": \"1\" }, { \"param_code\": \"good_no_fee\", \"param_name\": \"靓号预存\", \"param_value_code\": \"\", \"param_value\": 360 }, { \"param_code\": \"good_no_low\", \"param_name\": \"靓号低消\", \"param_value_code\": \"\", \"param_value\": 96 }, { \"param_code\": \"bill_type\", \"param_name\": \"账户付费方式\", \"param_value_code\": \"\", \"param_value\": \"10\" }, { \"param_code\": \"card_type\", \"param_name\": \"卡类型\", \"param_value_code\": \"\", \"param_value\": \"NM\" }, { \"param_code\": \"guarantor\", \"param_name\": \"担保人\", \"param_value_code\": \"\", \"param_value\": \"无\" }, { \"param_code\": \"bill_mail_type\", \"param_name\": \"账单寄送方式\", \"param_value_code\": \"\", \"param_value\": \"00\" } ] } ] } } ";
		json = "{ \"order_req\": { \"source_system\": \"10008\", \"receive_system\": \"10011\", \"serial_no\": \"704e12d3-5400-41f8-b832-8e5a6c5c6df6\", \"time\": \"20141228114250\", \"order_id\": \""+order_id+"\", \"order_city\": \"441900\", \"development_code\": \"5100009527\", \"order_type\": \"1\", \"status\": \"WAIT_SELLER_SEND_GOODS\", \"platform_status\": \"WAIT_SELLER_SEND_GOODS\", \"create_time\": \"20141228114204\", \"order_amount\": 460000, \"order_heavy\": \"0\", \"order_disacount\": 0, \"pay_money\": 460000, \"source_from_system\": \"10008\", \"source_from\": \"10031\", \"ship_area\": \"\", \"order_provinc_code\": \"440000\", \"order_city_code\": \"441900\", \"alipay_id\": \"无账号\", \"uid\": \"510000\", \"is_deal\": \"0\", \"pay_time\": \"20141228114019\", \"payment_serial_no\": \""+seqNo+"\", \"payment_code\": \"102\", \"payment_name\": \"联通支付有限公司\", \"pay_type\": \"ZXZF\", \"payment_type\": \"QEZF\", \"channel_id\": \"newmall001\", \"chanel_name\": \"自建自营厅(传统)\", \"channel_mark\": \"1\", \"ssyh\": \"newmall001\", \"bss_operator\": \"HLWDG679\", \"oss_operator\": \"GD001838\", \"order_comment\": \"6元/月 来电显示,10元包200条,5元/月 手机炫铃,32元包200分钟,8元包100MB;；渠道标记：传统营业厅\", \"shipping_type\": \"XJ\", \"shipping_time\": \"NOLIMIT\", \"ship_city\": \"\", \"ship_country\": \"\", \"ship_name\": \"测试\", \"ship_phone\": \"18602031952\", \"name\": \"测试\", \"uname\": \"测试\", \"abnormal_status\": \"正常\", \"delivery_status\": \"未发货\", \"shipping_amount\": 0, \"n_shipping_amount\": 0, \"shipping_quick\": \"01\", \"is_to4g\": \"0\", \"order_list\": [ { \"Package\": [ { \"PackageCode\": \"5869070000120140620000019\" },{ \"PackageCode\": \"5869070000320140620000011\" }, 					  { \"PackageCode\": \"5869070000420140620000017\" }, { \"PackageCode\": \"5869070000520140620000048\" }, { \"PackageCode\": \"5869070000620140620000050\" } ], \"prod_offer_code\": \"596901020620141001016719\", \"prod_offer_name\": \"4G组合套餐 自由选择随意组合！4G组合套餐2.0版震撼来袭！！全国版\", \"prod_offer_type\": \"20000\", \"offer_disacount_price\": 0, \"offer_coupon_price\": 100000, \"prod_offer_num\": 1, \"prod_offer_heavy\": \"0\", \"card_type\": \"NM\", \"certi_type\": \"SFZ18\", \"cust_name\": \"测试\", \"certi_num\": \"411302198607091854\", \"invoice_print_type\": \"3\", \"offer_price\": 100000, \"good_no_deposit\": 360, \"offer_eff_type\": \"ALLM\", \"package_sale\": \"YES\", \"is_change\": \"0\", \"sku_param\": [ { \"param_code\": \"acc_nbr\", \"param_name\": \"用户号码\", \"param_value_code\": \"\", \"param_value\": \""+acc_nbr+"\" }, { \"param_code\": \"is_old\", \"param_name\": \"是否老用户\", \"param_value_code\": \"\", \"param_value\": \"0\" }, { \"param_code\": \"family_no\", \"param_name\": \"亲情号码\", \"param_value_code\": \"\", \"param_value\": \"\" }, { \"param_code\": \"if_love_no\", \"param_name\": \"是否情侣号\", \"param_value_code\": \"\", \"param_value\": \"0\" }, { \"param_code\": \"net_type\", \"param_name\": \"网别\", \"param_value_code\": \"\", \"param_value\": \"3G\" }, { \"param_code\": \"is_goodno\", \"param_name\": \"靓号\", \"param_value_code\": \"\", \"param_value\": \"1\" }, { \"param_code\": \"good_no_fee\", \"param_name\": \"靓号预存\", \"param_value_code\": \"\", \"param_value\": 360 }, { \"param_code\": \"good_no_low\", \"param_name\": \"靓号低消\", \"param_value_code\": \"\", \"param_value\": 96 }, { \"param_code\": \"bill_type\", \"param_name\": \"账户付费方式\", \"param_value_code\": \"\", \"param_value\": \"10\" }, { \"param_code\": \"card_type\", \"param_name\": \"卡类型\", \"param_value_code\": \"\", \"param_value\": \"NM\" }, { \"param_code\": \"guarantor\", \"param_name\": \"担保人\", \"param_value_code\": \"\", \"param_value\": \"无\" }, { \"param_code\": \"bill_mail_type\", \"param_name\": \"账单寄送方式\", \"param_value_code\": \"\", \"param_value\": \"00\" } ] } ] } } ";
		order_id = "WCS14122811420496932114";
		json="{\"order_req\":{\"source_system\":\"10008\",\"receive_system\":\"10011\",\"serial_no\":\"b26789b4-c98b-43c2-95ff-5ab490103fbc\",\"time\":\"20150929080630\",\"order_id\":\""+order_id+"\",\"order_city\":\"440300\",\"development_code\":\"5100000999\",\"order_type\":\"1\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"platform_status\":\"WAIT_SELLER_SEND_GOODS\",\"reference_name\":\"dssz\",\"create_time\":\"20150929080557\",\"order_amount\":120000,\"order_heavy\":\"0\",\"order_disacount\":0,\"pay_money\":120000,\"source_from_system\":\"10008\",\"source_from\":\"10034\",\"ship_area\":\"\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440300\",\"alipay_id\":\"无账号\",\"uid\":\"510000\",\"is_deal\":\"0\",\"pay_time\":\"20150929080625\",\"payment_serial_no\":\"2015092921001004640055092912\",\"payment_code\":\"03\",\"payment_name\":\"支付宝\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"channel_id\":\"vip\",\"chanel_name\":\"老用户\",\"channel_mark\":\"13\",\"ssyh\":\"vip\",\"points_user\":\"18566663067\",\"order_comment\":\"赠品：总部-赠1G省内闲时流量-6个月-限76套餐;；渠道标记：自营渠道\",\"is_examine_card\":\"0\",\"shipping_type\":\"WX\",\"shipping_time\":\"NOLIMIT\",\"ship_city\":\"440100\",\"ship_country\":\"440101\",\"ship_phone\":\"-1\",\"name\":\"广东买家\",\"ship_name\":\"广东买家\",\"uname\":\"广东买家\",\"abnormal_status\":\"正常\",\"delivery_status\":\"未发货\",\"shipping_amount\":0,\"n_shipping_amount\":0,\"shipping_quick\":\"01\",\"is_to4g\":\"0\",\"order_list\":[{\"activity_list\":[{\"activity_code\":\"201501081914002656\",\"activity_id\":13870,\"gift_list\":[{\"gift_id\":\"2014032710416\",\"gift_num\":1,\"is_process\":\"0\"}]}],\"prod_offer_code\":\"596901020520141202023432\",\"prod_offer_name\":\"76元[vip商城]【沃4G】预存120元送240元话费12月\",\"prod_offer_type\":\"20000\",\"offer_disacount_price\":0,\"offer_coupon_price\":120000,\"prod_offer_num\":1,\"prod_offer_heavy\":\"0\",\"certi_type\":\"SFZ18\",\"cust_type\":\"GRKH\",\"cust_name\":\"宋良友\",\"certi_num\":\"511081198008224515\",\"certi_address\":\"四川省资阳市雁江区伍隍镇红庙村9组9号\",\"invoice_print_type\":\"3\",\"offer_price\":120000,\"good_no_deposit\":\"0\",\"offer_eff_type\":\"COMM\",\"package_sale\":\"NO\",\"is_change\":\"1\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18566663067\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"1\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"sex\",\"param_name\":\"性别\",\"param_value_code\":\"\",\"param_value\":\"M\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\",\"param_value_code\":\"\",\"param_value\":\"四川省资阳市雁江区伍隍镇红庙村9组9号\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"none\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}";
		
		String url = "http://10.45.54.59:8080/servlet/newMallOrderStanding";
//		String url = "http://10.123.99.69:10004/servlet/InformationServerServlet";
		
		
//		json = "{\"standard_order_req\":{\"serial_no\":\"201501099203117933\",\"time\":\"20150109045702\",\"source_system\":\"10011\",\"receive_system\":\"10009\",\"order_id\":\"WCS14123015302320273678\",\"source_from_system\":\"10008\",\"source_from\":\"10015\",\"source_type\":\"GDMALL\",\"regist_type\":\"SELF\",\"order_city\":\"440100\",\"channel_mark\":\"13\",\"spread_channel\":\"\",\"channel_id\":\"caicaijiao\",\"chanel_name\":\"蔡彩娇\",\"bss_operator\":\"\",\"bss_operator_name\":\"\",\"oss_operator\":\"\",\"development_code\":\"5101158748\",\"development_name\":\"孙文娟\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20141230153023\",\"pay_time\":\"\",\"pay_type\":\"HDFK\",\"payment_type\":\"XJZF\",\"payment_status\":\"未支付\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"order_amount\":\"300000\",\"order_disacount\":\"101000\",\"pay_money\":\"199000\",\"o_shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"order_points\":\"\",\"points_user\":\"\",\"shipping_company\":\"\",\"shipping_company_name\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"张健\",\"ship_province\":\"440000\",\"ship_city\":\"440100\",\"ship_country\":\"440105\",\"ship_area\":\"\",\"ship_addr\":\"工业大道中253号珠江医院\",\"postcode\":\"\",\"ship_tel\":\"13925091863\",\"ship_phone\":\"13925091863\",\"ship_email\":\"\",\"cust_phone_no\":\"13925091863\",\"cust_mobile_no\":\"13925091863\",\"cust_name\":\"张健\",\"cust_addr\":\"工业大道中253号珠江医院\",\"uid\":\"510000\",\"uname\":\"张健\",\"buyer_message\":\"特急！用户要求元旦前送到，谢谢！来源：客联，挂量：周秀，真实发展人：蔡彩娇                                 \",\"seller_message\":\"\",\"order_comment\":\"渠道标记：自营渠道\",\"is_manual_operation\":\"\",\"group_code\":\"\",\"group_name\":\"\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"fund_type\":\"\",\"retrieve_type\":\"\",\"retrieve_content\":\"\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"张健\",\"invoice_content\":\"1\",\"invoice_group_content\":\"MX\",\"is_upload\":\"\",\"order_type\":\"1\",\"tid_category\":\"Z7\",\"is_to4g\":\"0\",\"is_deal\":\"0\",\"ssyh\":\"caicaijiao\",\"mall_order_id\":\"TEYX201412308502207998\",\"league_info\":{\"league_id\":\"\",\"league_name\":\"\",\"higher_league_id\":\"\",\"higher_league_name\":\"\"},\"order_list\":[{\"prod_offer_code\":\"100013302\",\"prod_offer_name\":\"3G极速半年卡\",\"prod_offer_type\":\"20001\",\"prod_brand\":\"3GWK\",\"is_group_contract\":\"1\",\"offer_price\":\"300000\",\"offer_disacount_price\":\"101000\",\"offer_coupon_price\":\"199000\",\"offer_point\":\"\",\"prod_offer_num\":\"1\",\"inventory_code\":\"-1\",\"inventory_name\":\"-1\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"440105196609288013\",\"certi_address\":\"工业大道中253号珠江医院\",\"cust_name\":\"张健\",\"cust_type\":\"GRKH\",\"certi_valid_date\":\"20501231235959\",\"reduce_flag\":\"NO\",\"offer_param\":[{\"param_code\":\"CARD_TIME\",\"param_name\":\"上网卡时间\",\"has_value_code\":\"1\",\"param_value_code\":\"2\",\"param_value\":\"半年卡\"},{\"param_code\":\"is_group\",\"param_name\":\"是否全国卡\",\"has_value_code\":\"1\",\"param_value_code\":\"0\",\"param_value\":\"0\"},{\"param_code\":\"IS_SET\",\"param_name\":\"是否成品卡\",\"has_value_code\":\"1\",\"param_value_code\":\"0\",\"param_value\":\"0\"},{\"param_code\":\"NETCARD_TYPE\",\"param_name\":\"上网卡类型\",\"has_value_code\":\"1\",\"param_value_code\":\"5869040100020140918001440\",\"param_value\":\"10G上网半年卡\"}],\"package_sale\":\"YES\",\"choose_active\":\"\",\"is_self\":\"0\",\"Package\":[],\"activity_list\":[{\"activity_code\":\"201412067448002050\",\"activity_name\":\"201412067448002050\",\"activity_desc\":\"半年卡 1.6-1.14\",\"activity_type\":\"3\",\"disacount_range\":\"\",\"disacount_num\":\"101000\",\"disacount_unit\":\"02\"}],\"ad_service\":[],\"djyck\":\"0\",\"online_time\":\"02\",\"AccountInfo\":{\"AcceptanceForm\":{\"mainContentOne\":\"1、  套餐名称：3G极速半年卡，详见业务协议\",\"AcceptanceMode\":\"0\",\"contactAddr\":\"工业大道中253号珠江医院\",\"mainContentTwo\":\"基本通信服务及附加业务名称及描述：\r可选业务包名称及描述：可选活动名\r号码信息：您选择的号码，具体规则详见业务协议\",\"agentPaperType\":\"\",\"acctAddr\":\"工业大道中253号珠江医院\",\"userType\":\"\",\"paperExpr\":\"\",\"acctName\":\"张健\",\"agentPhone\":\"\",\"custType\":\"GRKH\",\"payMethod\":\"现金支付\",\"paperAddr\":\"\",\"paperType\":\"SFZ18\",\"staffInfo\":\"\",\"contactPhone\":\"13925091863\",\"bankAcctName\":\"\",\"bankName\":\"\",\"bankAcct\":\"\",\"paperNo\":\"440105196609288013\",\"bankCode\":\"\",\"agentPaperNo\":\"\",\"agentName\":\"\",\"userNo\":\"\"},\"AcceptanceMode\":\"0\",\"AcceptanceTp\":\"presub_t\",\"Para\":{\"ParaID\":\"\",\"ParaValue\":\"\"}},\"sku_list\":[{\"sku_id\":\"16163013\",\"goods_name\":\"3G极速半年卡\",\"goods_type\":\"10002\",\"goods_category\":\"690103000\",\"goods_desc\":\"3G极速半年卡\",\"is_virtual\":\"1\",\"is_gift\":\"0\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"offer_name\",\"param_name\":\"套餐名称\",\"has_value_code\":\"1\",\"param_value_code\":\"3G极速半年卡\",\"param_value\":\"3G极速半年卡\"},{\"param_code\":\"offer_eff_type\",\"param_name\":\"生效方式\",\"has_value_code\":\"1\",\"param_value_code\":\"ALLM\",\"param_value\":\"ALLM\"},{\"param_code\":\"wo_offer_eff_type\",\"param_name\":\"微信沃包生效方式\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"ALLM\"},{\"param_code\":\"offer_change\",\"param_name\":\"套餐是否变更\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"is_iphone\",\"param_name\":\"是否iphone套餐\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS套餐编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"16163013\"},{\"param_code\":\"ess_code\",\"param_name\":\"ESS套餐编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"99005970\"},{\"param_code\":\"month_fee\",\"param_name\":\"套餐档次\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"pay_type\",\"param_name\":\"付费类型\",\"has_value_code\":\"1\",\"param_value_code\":\"PRE\",\"param_value\":\"预付费\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"has_value_code\":\"1\",\"param_value_code\":\"3G\",\"param_value\":\"3G\"},{\"param_code\":\"sms_send_num\",\"param_name\":\"国内短信发送条数\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"answering_free\",\"param_name\":\"接听免费\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"call_times\",\"param_name\":\"国内语音拨打分钟数\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"flow\",\"param_name\":\"国内流量\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"visual_phone\",\"param_name\":\"可视电话\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"other_business\",\"param_name\":\"赠送增值业务\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"out_call_times\",\"param_name\":\"套餐外通话费用（元/分钟）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"out_flow\",\"param_name\":\"套餐外流量费用（元/KB）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"out_visual_phone\",\"param_name\":\"套餐外可视电话（元/分钟）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"plan_type\",\"param_name\":\"WCDMA(3G)基本套餐类型（A/B/C）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"}],\"sku_fee\":[]},{\"sku_id\":\"5869040100020140918001440\",\"goods_name\":\"号码\",\"goods_type\":\"10011\",\"goods_category\":\"10011\",\"goods_desc\":\"号码\",\"is_virtual\":\"1\",\"is_gift\":\"0\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"acc_nbr_bz\",\"param_name\":\"号码校验失败备注\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"号码不存在\"},{\"param_code\":\"acc_nbr\",\"param_name\":\"号码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"none\"},{\"param_code\":\"nbr_net_type\",\"param_name\":\"网别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"net_region\",\"param_name\":\"入网地区\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_low\",\"param_name\":\"靓号低消\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"none\"},{\"param_code\":\"sim_type\",\"param_name\":\"成卡白卡\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"白卡\"},{\"param_code\":\"password\",\"param_name\":\"老用户密码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"vicecard_no\",\"param_name\":\"副卡号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"love_no\",\"param_name\":\"情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"sex\",\"param_name\":\"性别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"birthday\",\"param_name\":\"出生日期\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"parents_bank_code\",\"param_name\":\"上级银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_code\",\"param_name\":\"银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_name\",\"param_name\":\"银行名称\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_cust_code\",\"param_name\":\"银行账号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_cust_name\",\"param_name\":\"银行账户名\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"credit_class\",\"param_name\":\"信用等级\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"credit_adjust\",\"param_name\":\"信用度调整\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"guarantor_info\",\"param_name\":\"担保信息参数\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor_certi_type\",\"param_name\":\"被担保人证件类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor_certi_no\",\"param_name\":\"被担保人证件号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"00\"},{\"param_code\":\"bill_mail_content\",\"param_name\":\"账单寄送内容\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_rec\",\"param_name\":\"账单收件人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_addr\",\"param_name\":\"账单寄送地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_post_code\",\"param_name\":\"账单寄送邮编\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"sub_no\",\"param_name\":\"共享子号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"}],\"sku_fee\":[]},{\"sku_id\":\"5869100000020140815001262\",\"goods_name\":\"通用转兑包\",\"goods_type\":\"10010\",\"goods_category\":\"691000000\",\"goods_desc\":\"通用转兑包\",\"is_virtual\":\"1\",\"is_gift\":\"1\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"package_type\",\"param_name\":\"转兑包类型\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"10002\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"111111111\"},{\"param_code\":\"PACKAGE_NET\",\"param_name\":\"转兑包网别\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"4G\"},{\"param_code\":\"ADJUST_NUM\",\"param_name\":\"调价额度\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"}],\"sku_fee\":[]}],\"propack_code\":\"\",\"propack_desc\":\"\"}]}}";
//		json = "{\"standard_order_req\":{\"serial_no\":\"201501092665117936\",\"time\":\"20150109050300\",\"source_system\":\"10011\",\"receive_system\":\"10009\",\"order_id\":\"WCS14123020264266559718\",\"source_from_system\":\"10008\",\"source_from\":\"10015\",\"source_type\":\"GDMALL\",\"regist_type\":\"SELF\",\"order_city\":\"445100\",\"channel_mark\":\"13\",\"spread_channel\":\"\",\"channel_id\":\"caicaijiao\",\"chanel_name\":\"蔡彩娇\",\"bss_operator\":\"\",\"bss_operator_name\":\"\",\"oss_operator\":\"\",\"development_code\":\"5102465364\",\"development_name\":\"www.10010.com\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20141230202642\",\"pay_time\":\"\",\"pay_type\":\"HDFK\",\"payment_type\":\"XJZF\",\"payment_status\":\"未支付\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"order_amount\":\"100000\",\"order_disacount\":\"0\",\"pay_money\":\"100000\",\"o_shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"order_points\":\"\",\"points_user\":\"\",\"shipping_company\":\"\",\"shipping_company_name\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"关友方\",\"ship_province\":\"440000\",\"ship_city\":\"445100\",\"ship_country\":\"445122\",\"ship_area\":\"\",\"ship_addr\":\"饶平县城白竹乐圆\",\"postcode\":\"\",\"ship_tel\":\"13172560061\",\"ship_phone\":\"13172560061\",\"ship_email\":\"\",\"cust_phone_no\":\"13172560061\",\"cust_mobile_no\":\"13172560061\",\"cust_name\":\"关友方\",\"cust_addr\":\"饶平县城白竹乐圆\",\"uid\":\"510000\",\"uname\":\"关友方\",\"buyer_message\":\"加急！发票！红米的微卡，发展人：蔡彩娇                                         \",\"seller_message\":\"\",\"order_comment\":\";56元包500分钟 8元包100MB 6元/月 来电显示;;；渠道标记：自营渠道\",\"is_manual_operation\":\"\",\"group_code\":\"\",\"group_name\":\"\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"fund_type\":\"\",\"retrieve_type\":\"\",\"retrieve_content\":\"\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"关友方\",\"invoice_content\":\"1\",\"invoice_group_content\":\"MX\",\"is_upload\":\"\",\"order_type\":\"1\",\"tid_category\":\"Z6\",\"is_to4g\":\"0\",\"is_deal\":\"0\",\"ssyh\":\"caicaijiao\",\"mall_order_id\":\"TEYX201412303499208535\",\"league_info\":{\"league_id\":\"\",\"league_name\":\"\",\"higher_league_id\":\"\",\"higher_league_name\":\"\"},\"order_list\":[{\"prod_offer_code\":\"511405139289\",\"prod_offer_name\":\"4G全国组合套餐\",\"prod_offer_type\":\"20000\",\"prod_brand\":\"3GPH\",\"is_group_contract\":\"1\",\"offer_price\":\"100000\",\"offer_disacount_price\":\"0\",\"offer_coupon_price\":\"100000\",\"offer_point\":\"\",\"prod_offer_num\":\"1\",\"inventory_code\":\"-1\",\"inventory_name\":\"-1\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"512534197006206849\",\"certi_address\":\"饶平县城白竹乐圆\",\"cust_name\":\"关友方\",\"cust_type\":\"GRKH\",\"certi_valid_date\":\"20501231235959\",\"reduce_flag\":\"NO\",\"offer_param\":[{\"param_code\":\"deposit_fee\",\"param_name\":\"预存金额\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"50000\"},{\"param_code\":\"order_return\",\"param_name\":\"首月返还\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"mon_return\",\"param_name\":\"分月返还\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"is_set\",\"param_name\":\"是否成品卡\",\"has_value_code\":\"1\",\"param_value_code\":\"0\",\"param_value\":\"0\"},{\"param_code\":\"is_attached\",\"param_name\":\"是否副卡\",\"has_value_code\":\"1\",\"param_value_code\":\"0\",\"param_value\":\"0\"},{\"param_code\":\"all_give\",\"param_name\":\"协议期总送费金额\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"mon_give\",\"param_name\":\"月送费金额\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"}],\"package_sale\":\"YES\",\"choose_active\":\"\",\"is_self\":\"0\",\"Package\":[{\"PackageCode\":\"51002531\",\"PackageName\":\"6元/月 来电显示\",\"ProductCode\":\"51002531\",\"ElementCode\":\"5557000\",\"ElementType\":\"D\",\"ElementName\":\"6元/月 来电显示\",\"OperType\":\"E\",\"ChageType\":\"O\",\"BizType\":\"FWYH\"},{\"PackageCode\":\"51002515\",\"PackageName\":\"56元包500分钟\",\"ProductCode\":\"51002515\",\"ElementCode\":\"5533000\",\"ElementType\":\"D\",\"ElementName\":\"56元包500分钟\",\"OperType\":\"E\",\"ChageType\":\"O\",\"BizType\":\"FWYH\"},{\"PackageCode\":\"51002514\",\"PackageName\":\"8元包100MB\",\"ProductCode\":\"51002514\",\"ElementCode\":\"5501000\",\"ElementType\":\"D\",\"ElementName\":\"8元包100MB\",\"OperType\":\"E\",\"ChageType\":\"O\",\"BizType\":\"FWYH\"}],\"activity_list\":[],\"ad_service\":[],\"djyck\":\"0\",\"online_time\":\"\",\"AccountInfo\":{\"AcceptanceForm\":{\"mainContentOne\":\"1、  套餐名称：4G全国组合套餐，详见业务协议\",\"AcceptanceMode\":\"0\",\"contactAddr\":\"饶平县城白竹乐圆\",\"mainContentTwo\":\"基本通信服务及附加业务名称及描述：国内通话\r可选业务包名称及描述：可选活动名\r号码信息：您选择的号码18676850398，具体规则详见业务协议\",\"agentPaperType\":\"\",\"acctAddr\":\"饶平县城白竹乐圆\",\"userType\":\"\",\"paperExpr\":\"\",\"acctName\":\"关友方\",\"agentPhone\":\"\",\"custType\":\"GRKH\",\"payMethod\":\"现金支付\",\"paperAddr\":\"\",\"paperType\":\"SFZ18\",\"staffInfo\":\"\",\"contactPhone\":\"13172560061\",\"bankAcctName\":\"\",\"bankName\":\"\",\"bankAcct\":\"\",\"paperNo\":\"512534197006206849\",\"bankCode\":\"\",\"agentPaperNo\":\"\",\"agentName\":\"\",\"userNo\":\"18676850398\"},\"AcceptanceMode\":\"0\",\"AcceptanceTp\":\"presub_t\",\"Para\":{\"ParaID\":\"\",\"ParaValue\":\"\"}},\"sku_list\":[{\"sku_id\":\"5869010700020140722000038\",\"goods_name\":\"4G全国组合套餐\",\"goods_type\":\"10002\",\"goods_category\":\"690107000\",\"goods_desc\":\"4G全国组合套餐\",\"is_virtual\":\"1\",\"is_gift\":\"0\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"offer_name\",\"param_name\":\"套餐名称\",\"has_value_code\":\"1\",\"param_value_code\":\"4G全国组合套餐\",\"param_value\":\"4G全国组合套餐\"},{\"param_code\":\"offer_eff_type\",\"param_name\":\"生效方式\",\"has_value_code\":\"1\",\"param_value_code\":\"COMM\",\"param_value\":\"COMM\"},{\"param_code\":\"wo_offer_eff_type\",\"param_name\":\"微信沃包生效方式\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"ALLM\"},{\"param_code\":\"offer_change\",\"param_name\":\"套餐是否变更\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"4g_type\",\"param_name\":\"4G套餐类型\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"DIY\"},{\"param_code\":\"is_iphone\",\"param_name\":\"是否iphone套餐\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS套餐编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"89002148\"},{\"param_code\":\"ess_code\",\"param_name\":\"ESS套餐编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"89002148\"},{\"param_code\":\"month_fee\",\"param_name\":\"套餐档次\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"pay_type\",\"param_name\":\"付费类型\",\"has_value_code\":\"1\",\"param_value_code\":\"BAK\",\"param_value\":\"后付费\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"has_value_code\":\"1\",\"param_value_code\":\"4G\",\"param_value\":\"4G\"},{\"param_code\":\"sms_send_num\",\"param_name\":\"国内短信发送条数\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"answering_free\",\"param_name\":\"接听免费\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"call_times\",\"param_name\":\"国内语音拨打分钟数\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"flow\",\"param_name\":\"国内流量\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"visual_phone\",\"param_name\":\"可视电话\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"other_business\",\"param_name\":\"赠送增值业务\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"out_call_times\",\"param_name\":\"套餐外通话费用（元/分钟）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"out_flow\",\"param_name\":\"套餐外流量费用（元/KB）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"out_visual_phone\",\"param_name\":\"套餐外可视电话（元/分钟）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"plan_type\",\"param_name\":\"WCDMA(3G)基本套餐类型（A/B/C）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"}],\"sku_fee\":[]},{\"sku_id\":\"5869080000020140918001435\",\"goods_name\":\"号码\",\"goods_type\":\"10011\",\"goods_category\":\"10011\",\"goods_desc\":\"号码\",\"is_virtual\":\"1\",\"is_gift\":\"0\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"acc_nbr_bz\",\"param_name\":\"号码校验失败备注\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"号码校验成功\"},{\"param_code\":\"acc_nbr\",\"param_name\":\"号码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"18676850398\"},{\"param_code\":\"nbr_net_type\",\"param_name\":\"网别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"net_region\",\"param_name\":\"入网地区\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"445100\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_low\",\"param_name\":\"靓号低消\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"MC\"},{\"param_code\":\"sim_type\",\"param_name\":\"成卡白卡\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"白卡\"},{\"param_code\":\"password\",\"param_name\":\"老用户密码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"vicecard_no\",\"param_name\":\"副卡号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"love_no\",\"param_name\":\"情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"sex\",\"param_name\":\"性别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"birthday\",\"param_name\":\"出生日期\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"parents_bank_code\",\"param_name\":\"上级银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_code\",\"param_name\":\"银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_name\",\"param_name\":\"银行名称\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_cust_code\",\"param_name\":\"银行账号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_cust_name\",\"param_name\":\"银行账户名\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"credit_class\",\"param_name\":\"信用等级\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"credit_adjust\",\"param_name\":\"信用度调整\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"guarantor_info\",\"param_name\":\"担保信息参数\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor_certi_type\",\"param_name\":\"被担保人证件类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor_certi_no\",\"param_name\":\"被担保人证件号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"00\"},{\"param_code\":\"bill_mail_content\",\"param_name\":\"账单寄送内容\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_rec\",\"param_name\":\"账单收件人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_addr\",\"param_name\":\"账单寄送地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_post_code\",\"param_name\":\"账单寄送邮编\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"sub_no\",\"param_name\":\"共享子号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"}],\"sku_fee\":[]}],\"propack_code\":\"\",\"propack_desc\":\"\"}]}}";
//		json = "{\"standard_order_req\":{\"serial_no\":\"201501099520117938\",\"time\":\"20150109050300\",\"source_system\":\"10011\",\"receive_system\":\"10009\",\"order_id\":\"WCS14123015350588192224\",\"source_from_system\":\"10008\",\"source_from\":\"10015\",\"source_type\":\"GDMALL\",\"regist_type\":\"SELF\",\"order_city\":\"440100\",\"channel_mark\":\"13\",\"spread_channel\":\"\",\"channel_id\":\"caicaijiao\",\"chanel_name\":\"蔡彩娇\",\"bss_operator\":\"\",\"bss_operator_name\":\"\",\"oss_operator\":\"\",\"development_code\":\"5101158748\",\"development_name\":\"孙文娟\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20141230153505\",\"pay_time\":\"\",\"pay_type\":\"HDFK\",\"payment_type\":\"XJZF\",\"payment_status\":\"未支付\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"order_amount\":\"300000\",\"order_disacount\":\"101000\",\"pay_money\":\"199000\",\"o_shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"order_points\":\"\",\"points_user\":\"\",\"shipping_company\":\"\",\"shipping_company_name\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"张健\",\"ship_province\":\"440000\",\"ship_city\":\"440100\",\"ship_country\":\"440105\",\"ship_area\":\"\",\"ship_addr\":\"海珠区工业大道中253号珠江医院\",\"postcode\":\"\",\"ship_tel\":\"13925091863\",\"ship_phone\":\"13925091863\",\"ship_email\":\"\",\"cust_phone_no\":\"13925091863\",\"cust_mobile_no\":\"13925091863\",\"cust_name\":\"张健\",\"cust_addr\":\"海珠区工业大道中253号珠江医院\",\"uid\":\"510000\",\"uname\":\"张健\",\"buyer_message\":\"特急！用户要求元旦前送到，谢谢！来源：客联，挂量：周秀，真实发展人：蔡彩娇                                 \",\"seller_message\":\"\",\"order_comment\":\";;；渠道标记：自营渠道\",\"is_manual_operation\":\"\",\"group_code\":\"\",\"group_name\":\"\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"fund_type\":\"\",\"retrieve_type\":\"\",\"retrieve_content\":\"\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"张健\",\"invoice_content\":\"1\",\"invoice_group_content\":\"MX\",\"is_upload\":\"\",\"order_type\":\"1\",\"tid_category\":\"Z7\",\"is_to4g\":\"0\",\"is_deal\":\"0\",\"ssyh\":\"caicaijiao\",\"mall_order_id\":\"TEYX201412306555207999\",\"league_info\":{\"league_id\":\"\",\"league_name\":\"\",\"higher_league_id\":\"\",\"higher_league_name\":\"\"},\"order_list\":[{\"prod_offer_code\":\"100013302\",\"prod_offer_name\":\"3G极速半年卡\",\"prod_offer_type\":\"20001\",\"prod_brand\":\"3GWK\",\"is_group_contract\":\"1\",\"offer_price\":\"300000\",\"offer_disacount_price\":\"101000\",\"offer_coupon_price\":\"199000\",\"offer_point\":\"\",\"prod_offer_num\":\"1\",\"inventory_code\":\"-1\",\"inventory_name\":\"-1\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"440105196609288013\",\"certi_address\":\"海珠区工业大道中253号珠江医院\",\"cust_name\":\"张健\",\"cust_type\":\"GRKH\",\"certi_valid_date\":\"20501231235959\",\"reduce_flag\":\"NO\",\"offer_param\":[{\"param_code\":\"CARD_TIME\",\"param_name\":\"上网卡时间\",\"has_value_code\":\"1\",\"param_value_code\":\"2\",\"param_value\":\"半年卡\"},{\"param_code\":\"is_group\",\"param_name\":\"是否全国卡\",\"has_value_code\":\"1\",\"param_value_code\":\"0\",\"param_value\":\"0\"},{\"param_code\":\"IS_SET\",\"param_name\":\"是否成品卡\",\"has_value_code\":\"1\",\"param_value_code\":\"0\",\"param_value\":\"0\"},{\"param_code\":\"NETCARD_TYPE\",\"param_name\":\"上网卡类型\",\"has_value_code\":\"1\",\"param_value_code\":\"5869040100020140918001440\",\"param_value\":\"10G上网半年卡\"}],\"package_sale\":\"YES\",\"choose_active\":\"\",\"is_self\":\"0\",\"Package\":[],\"activity_list\":[{\"activity_code\":\"201412067448002050\",\"activity_name\":\"201412067448002050\",\"activity_desc\":\"半年卡 1.6-1.14\",\"activity_type\":\"3\",\"disacount_range\":\"\",\"disacount_num\":\"101000\",\"disacount_unit\":\"02\"}],\"ad_service\":[],\"djyck\":\"0\",\"online_time\":\"02\",\"AccountInfo\":{\"AcceptanceForm\":{\"mainContentOne\":\"1、  套餐名称：3G极速半年卡，详见业务协议\",\"AcceptanceMode\":\"0\",\"contactAddr\":\"海珠区工业大道中253号珠江医院\",\"mainContentTwo\":\"基本通信服务及附加业务名称及描述：\r可选业务包名称及描述：可选活动名\r号码信息：您选择的号码，具体规则详见业务协议\",\"agentPaperType\":\"\",\"acctAddr\":\"海珠区工业大道中253号珠江医院\",\"userType\":\"\",\"paperExpr\":\"\",\"acctName\":\"张健\",\"agentPhone\":\"\",\"custType\":\"GRKH\",\"payMethod\":\"现金支付\",\"paperAddr\":\"\",\"paperType\":\"SFZ18\",\"staffInfo\":\"\",\"contactPhone\":\"13925091863\",\"bankAcctName\":\"\",\"bankName\":\"\",\"bankAcct\":\"\",\"paperNo\":\"440105196609288013\",\"bankCode\":\"\",\"agentPaperNo\":\"\",\"agentName\":\"\",\"userNo\":\"\"},\"AcceptanceMode\":\"0\",\"AcceptanceTp\":\"presub_t\",\"Para\":{\"ParaID\":\"\",\"ParaValue\":\"\"}},\"sku_list\":[{\"sku_id\":\"16163013\",\"goods_name\":\"3G极速半年卡\",\"goods_type\":\"10002\",\"goods_category\":\"690103000\",\"goods_desc\":\"3G极速半年卡\",\"is_virtual\":\"1\",\"is_gift\":\"0\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"offer_name\",\"param_name\":\"套餐名称\",\"has_value_code\":\"1\",\"param_value_code\":\"3G极速半年卡\",\"param_value\":\"3G极速半年卡\"},{\"param_code\":\"offer_eff_type\",\"param_name\":\"生效方式\",\"has_value_code\":\"1\",\"param_value_code\":\"ALLM\",\"param_value\":\"ALLM\"},{\"param_code\":\"wo_offer_eff_type\",\"param_name\":\"微信沃包生效方式\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"ALLM\"},{\"param_code\":\"offer_change\",\"param_name\":\"套餐是否变更\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"is_iphone\",\"param_name\":\"是否iphone套餐\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS套餐编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"16163013\"},{\"param_code\":\"ess_code\",\"param_name\":\"ESS套餐编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"99005970\"},{\"param_code\":\"month_fee\",\"param_name\":\"套餐档次\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"pay_type\",\"param_name\":\"付费类型\",\"has_value_code\":\"1\",\"param_value_code\":\"PRE\",\"param_value\":\"预付费\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"has_value_code\":\"1\",\"param_value_code\":\"3G\",\"param_value\":\"3G\"},{\"param_code\":\"sms_send_num\",\"param_name\":\"国内短信发送条数\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"answering_free\",\"param_name\":\"接听免费\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"call_times\",\"param_name\":\"国内语音拨打分钟数\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"flow\",\"param_name\":\"国内流量\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"visual_phone\",\"param_name\":\"可视电话\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"other_business\",\"param_name\":\"赠送增值业务\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"out_call_times\",\"param_name\":\"套餐外通话费用（元/分钟）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"out_flow\",\"param_name\":\"套餐外流量费用（元/KB）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"out_visual_phone\",\"param_name\":\"套餐外可视电话（元/分钟）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"plan_type\",\"param_name\":\"WCDMA(3G)基本套餐类型（A/B/C）\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"}],\"sku_fee\":[]},{\"sku_id\":\"5869040100020140918001440\",\"goods_name\":\"号码\",\"goods_type\":\"10011\",\"goods_category\":\"10011\",\"goods_desc\":\"号码\",\"is_virtual\":\"1\",\"is_gift\":\"0\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"acc_nbr_bz\",\"param_name\":\"号码校验失败备注\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"号码不存在\"},{\"param_code\":\"acc_nbr\",\"param_name\":\"号码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"none\"},{\"param_code\":\"nbr_net_type\",\"param_name\":\"网别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"net_region\",\"param_name\":\"入网地区\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_low\",\"param_name\":\"靓号低消\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"none\"},{\"param_code\":\"sim_type\",\"param_name\":\"成卡白卡\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"白卡\"},{\"param_code\":\"password\",\"param_name\":\"老用户密码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"vicecard_no\",\"param_name\":\"副卡号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"love_no\",\"param_name\":\"情侣号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"sex\",\"param_name\":\"性别\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"birthday\",\"param_name\":\"出生日期\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"contact_addr\",\"param_name\":\"通讯地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"parents_bank_code\",\"param_name\":\"上级银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_code\",\"param_name\":\"银行编码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_name\",\"param_name\":\"银行名称\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_cust_code\",\"param_name\":\"银行账号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bank_cust_name\",\"param_name\":\"银行账户名\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"credit_class\",\"param_name\":\"信用等级\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"credit_adjust\",\"param_name\":\"信用度调整\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"guarantor_info\",\"param_name\":\"担保信息参数\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor_certi_type\",\"param_name\":\"被担保人证件类型\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"guarantor_certi_no\",\"param_name\":\"被担保人证件号码\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"00\"},{\"param_code\":\"bill_mail_content\",\"param_name\":\"账单寄送内容\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_rec\",\"param_name\":\"账单收件人\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_addr\",\"param_name\":\"账单寄送地址\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_mail_post_code\",\"param_name\":\"账单寄送邮编\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"sub_no\",\"param_name\":\"共享子号\", \"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"\"}],\"sku_fee\":[]},{\"sku_id\":\"5869100000020140815001262\",\"goods_name\":\"通用转兑包\",\"goods_type\":\"10010\",\"goods_category\":\"691000000\",\"goods_desc\":\"通用转兑包\",\"is_virtual\":\"1\",\"is_gift\":\"1\",\"sku_num\":\"1\",\"sku_param\":[{\"param_code\":\"package_type\",\"param_name\":\"转兑包类型\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"10002\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS编码\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"111111111\"},{\"param_code\":\"PACKAGE_NET\",\"param_name\":\"转兑包网别\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"4G\"},{\"param_code\":\"ADJUST_NUM\",\"param_name\":\"调价额度\",\"has_value_code\":\"0\",\"param_value_code\":\"\",\"param_value\":\"0\"}],\"sku_fee\":[]}],\"propack_code\":\"\",\"propack_desc\":\"\"}]}}";
//		url = "http://10.123.178.47/web/commonInterface.srv?reqType=pust_order&reqId=new-edb-system&md5=1234";
//		logger.info(json);
		String result = sendPostHttpRequest(json, url);
		logger.info(result);
//		String json = "{\"order_req\":{\"source_system\":\"10008\",\"receive_system\":\"10011\",\"serial_no\":\"aeed72e0-0b8e-4b9d-b1d5-92e4c78bdf95\",\"time\":\"20141228091240\",\"order_id\":\"WCS14122809071192382326\",\"order_city\":\"441900\",\"development_code\":\"5100009527\",\"order_type\":\"1\",\"status\":\"WAIT_SELLER_SEND_GOODS\",\"platform_status\":\"WAIT_SELLER_SEND_GOODS\",\"create_time\":\"20141228090711\",\"order_amount\":460000,\"order_heavy\":\"0\",\"order_disacount\":0,\"pay_money\":460000,\"source_from_system\":\"10008\",\"source_from\":\"10031\",\"ship_area\":\"\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"441900\",\"alipay_id\":\"无账号\",\"uid\":\"510000\",\"is_deal\":\"0\",\"pay_time\":\"20141228091014\",\"payment_serial_no\":\"20131017020200118128\",\"payment_code\":\"102\",\"payment_name\":\"联通支付有限公司\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"channel_id\":\"newmall001\",\"chanel_name\":\"自建自营厅(传统)\",\"channel_mark\":\"1\",\"ssyh\":\"newmall001\",\"bss_operator\":\"HLWDG679\",\"ship_addr\":\"test\",\"oss_operator\":\"GD001838\",\"order_comment\":\"来电显示免费,10元包200条,5元/月 手机炫铃,32元包200分钟,8元包100MB;；渠道标记：传统营业厅\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_city\":\"440100\",\"ship_country\":\"440106\",\"ship_name\":\"测试\",\"ship_phone\":\"18602031952\",\"name\":\"测试\",\"uname\":\"测试\",\"abnormal_status\":\"正常\",\"delivery_status\":\"未发货\",\"shipping_amount\":0,\"n_shipping_amount\":0,\"shipping_quick\":\"01\",\"is_to4g\":\"0\",\"order_list\":[{\"Package\":[{\"PackageCode\":\"5869070000120140620000019\"},{\"PackageCode\":\"5869070000320140620000011\"},{\"PackageCode\":\"5869070000420140620000017\"},{\"PackageCode\":\"5869070000520141209002079\"},{\"PackageCode\":\"5869070000620140620000050\"}],\"prod_offer_code\":\"596901020620141001016719\",\"prod_offer_name\":\"4G组合套餐  自由选择随意组合！4G组合套餐2.0版震撼来袭！！全国版\",\"prod_offer_type\":\"20000\",\"offer_disacount_price\":0,\"offer_coupon_price\":100000,\"prod_offer_num\":1,\"prod_offer_heavy\":\"0\",\"card_type\":\"NM\",\"certi_type\":\"SFZ18\",\"cust_name\":\"测试\",\"certi_num\":\"411302198607091854\",\"invoice_print_type\":\"3\",\"offer_price\":100000,\"good_no_deposit\":360,\"offer_eff_type\":\"ALLM\",\"package_sale\":\"YES\",\"is_change\":\"0\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18664025174\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"family_no\",\"param_name\":\"亲情号码\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"1\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":360},{\"param_code\":\"good_no_low\",\"param_name\":\"靓号低消\",\"param_value_code\":\"\",\"param_value\":96},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}";
//		logger.info(json);
	}
	
	private static void demo(){
		String []arrs = {
				
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w1\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w2\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w3\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w4\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w5\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w6\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w7\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w8\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w9\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w10\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w11\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w12\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w13\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w14\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w15\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w16\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w17\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w18\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w19\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w20\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w21\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}",
				"{\"order_req\":{\"serial_no\":\"201409221718000035\",\"time\":\"20140922171800\",\"source_system\":\"10006\",\"receive_system\":\"10011\",\"order_id\":\"testmo0922142406267w22\",\"order_type\":\"1\",\"status\":\"已支付\",\"is_to4g\":\"0\",\"source_from_system\":\"10006\",\"source_from\":\"10006\",\"order_city\":\"440200\",\"channel_mark\":\"5\",\"chanel_id\":\"电子渠道\",\"chanel_name\":\"电子渠道\",\"development_code\":\"5104213962\",\"reference_phone\":\"\",\"reference_name\":\"\",\"create_time\":\"20140922142406\",\"pay_time\":\"20140922154459\",\"pay_type\":\"ZXZF\",\"payment_type\":\"QEZF\",\"payment_serial_no\":\"\",\"payment_code\":\"\",\"payment_name\":\"\",\"payment_channel_code\":\"\",\"payment_channel_name\":\"\",\"alipay_id\":\"\",\"name\":\"邹逸骁\",\"uid\":\"-1\",\"uname\":\"邹逸骁\",\"delivery_status\":\"未发货\",\"abnormal_status\":\"正常\",\"platform_status\":\"已付款\",\"order_amount\":\"2999000\",\"order_heavy\":\"0\",\"order_disacount\":\"00\",\"pay_money\":\"2999000\",\"shipping_amount\":\"0\",\"n_shipping_amount\":\"0\",\"shipping_company\":\"\",\"shipping_quick\":\"01\",\"shipping_type\":\"KD\",\"shipping_time\":\"NOLIMIT\",\"ship_name\":\"邹逸骁\",\"ship_city\":\"440200\",\"ship_country\":\"440204\",\"order_provinc_code\":\"440000\",\"order_city_code\":\"440200\",\"ship_area\":\"\",\"ship_addr\":\"客服将根据您留下的手机联系您，确认收货地址！\",\"postcode\":\"510000\",\"ship_tel\":\"\",\"ship_phone\":\"10000000000\",\"ship_email\":\"\",\"baidu_account\":\"\",\"baidu_no\":\"\",\"baidu_money\":\"\",\"baidu_begin_time\":\"\",\"baidu_end_time\":\"\",\"buyer_message\":\"\",\"seller_message\":\"\",\"ssyh\":\"\",\"is_deal\":\"0\",\"order_comment\":\"\",\"group_code\":\"12122455950116\",\"group_name\":\"中国农业银行广东省分行\",\"industry_type\":\"\",\"industry_sub_type\":\"\",\"order_list\":[{\"prod_offer_code\":\"596901020120140915006135\",\"offer_price\":\"2999000\",\"offer_disacount_price\":\"00\",\"offer_coupon_price\":\"2999000\",\"prod_offer_num\":\"1\",\"invoice_type\":\"1\",\"invoice_print_type\":\"1\",\"invoice_title\":\"邹逸骁\",\"invoice_group_content\":\"MX\",\"invoice_content\":\"\",\"inventory_code\":\"\",\"inventory_name\":\"\",\"offer_comment\":\"\",\"certi_type\":\"SFZ18\",\"certi_num\":\"44012219711106032X\",\"certi_address\":\"广州省广州市\",\"cust_name\":\"邹逸骁\",\"offer_eff_type\":\"COMM\",\"activitycode\":\"\",\"resourcestypeid\":\"\",\"is_change\":\"0\",\"package_sale\":\"YES\",\"choose_active\":\"\",\"sku_param\":[{\"param_code\":\"acc_nbr\",\"param_name\":\"用户号码\",\"param_value_code\":\"\",\"param_value\":\"18520293902\"},{\"param_code\":\"is_old\",\"param_name\":\"是否老用户\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"if_love_no\",\"param_name\":\"是否情侣号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"net_type\",\"param_name\":\"网别\",\"param_value_code\":\"\",\"param_value\":\"3G\"},{\"param_code\":\"is_goodno\",\"param_name\":\"靓号\",\"param_value_code\":\"\",\"param_value\":\"0\"},{\"param_code\":\"good_no_fee\",\"param_name\":\"靓号预存\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bill_type\",\"param_name\":\"账户付费方式\",\"param_value_code\":\"\",\"param_value\":\"10\"},{\"param_code\":\"card_type\",\"param_name\":\"卡类型\",\"param_value_code\":\"\",\"param_value\":\"NM\"},{\"param_code\":\"guarantor\",\"param_name\":\"担保人\",\"param_value_code\":\"\",\"param_value\":\"无\"},{\"param_code\":\"bill_mail_type\",\"param_name\":\"账单寄送方式\",\"param_value_code\":\"\",\"param_value\":\"00\"}]}]}}"
				
		};
		
//		for (int i = 0; i < arrs.length; i++) {
//			
//			MallThread thread = new MallThread(arrs[i], "http://10.123.99.69:10003/servlet/InformationServerServlet");
//			thread.start();
//			
//		}
	}
	
	private static void test(String dd){
		//商品同步
		String json = "{\"prod_offer_req\":{\"tip\":\"商品信息同步\",\"serial_no\":\"20140923111838\",\"time\":\"20140923111838\",\"source_system\":\"10011\",\"receive_system\":\"10008\",\"channel\":\"10031\",\"prod_offer_code\":\"596903010120140724005593\",\"action\":\"A\",\"prod_offer_name\":\"三星I9082C白24月存费送机4G/3G一体化套餐106元\",\"prod_offer_type\":\"69030101\",\"prod_offer_category\":\"20002\",\"prod_offer_brand\":\"SX\",\"terminal_model\":\"I9082C\",\"prod_offer_state\":\"0\",\"prod_offer_price\":\"1899\",\"prod_offer_heavy\":\"0\",\"prod_offer_package\":\"0\",\"prod_offer_ele\":[{\"sku\":\"99999829\",\"goods_num\":\"1\"},{\"sku\":\"7400208277\",\"goods_num\":\"1\"},{\"sku\":\"5869030200020140619000004\",\"goods_num\":\"1\"}],\"prod_offer_param\":[{\"param_code\":\"mobile_price\",\"param_name\":\"购机金额\",\"param_value_code\":\"\",\"param_value\":\"579.0\"},{\"param_code\":\"deposit_fee\",\"param_name\":\"预存金额\",\"param_value_code\":\"\",\"param_value\":\"1320.0\"},{\"param_code\":\"order_return\",\"param_name\":\"首月返还\",\"param_value_code\":\"\",\"param_value\":\"132.0\"},{\"param_code\":\"mon_return\",\"param_name\":\"分月返还\",\"param_value_code\":\"\",\"param_value\":\"49.0\"},{\"param_code\":\"all_give\",\"param_name\":\"总送费金额\",\"param_value_code\":\"\",\"param_value\":\"\"},{\"param_code\":\"bss_code\",\"param_name\":\"BSS编码\",\"param_value_code\":\"\",\"param_value\":\"0\"}],\"prod_offer_attr\":{\"prod_offer_sku\":\"0\",\"mon_give\":\"49.0\",\"all_give\":\"0\",\"mobile_price\":\"579.0\",\"order_return\":\"132.0\",\"deposit_fee\":\"1320.0\",\"mon_return\":\"49.0\"}}}";
		//佛山
		String url = "http://www.fs186.cn/store/province/receive_product/";
		//深圳
		url = "http://u.75510010.com:8088/infosync/servlet/ProductInfoCallback";
		String rString = sendPostHttpRequest(json, url);
		logger.info(rString);
	}
	
	/**
	 * 判断字符串是否为空，不为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if(null == str || "".equals(str)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 判断字符串是否为空，为空返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(null == str || "".equals(str)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 返回字符串值，null返回空值""
	 * @param str
	 * @return
	 */
	public static String getValues(String str){
		if(null == str ||"".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 替换空格、回车、换行、制表位为空字符
	 * @param str
	 * @return
	 */
	public static String strReplaceBlank(String str){
		Pattern pattern = Pattern.compile("\r|\t|\n");
		Matcher m = pattern.matcher(str);
		return m.replaceAll("");
	}
	
	/**
	 * 沃财富/新商城输入json转换为java实体类
	 * @param json
	 * @return
	 */
	public static MallOrder jsonToMallOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收

//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("order_list", MallOrder_List.class);
		classMap.put("activity_list", MallActivity_List.class);
		classMap.put("offer_param", MallOffer_Param.class);
		classMap.put("Package", MallWCFPackage.class);
		classMap.put("sku_param", MallSKU_Param.class);
		classMap.put("gift_list", MallGift_List.class);
		classMap.put("coupons_list", MallCoupons_List.class);
		
//		配置需要过滤的属性,java bean中没有的属性,转换时会报错,需要过滤,过滤时属性区分大小写
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String []ingoreFields = new String[]{"sku_fee"};
		config.setExcludes(ingoreFields);
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
//				return str.toLowerCase();
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(MallOrder.class);
		
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json,config);
		
//		转换日期需要指定日期格式
//		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss"}));
		
//		把json对象转换为java bean
		jsonObject=jsonObject.getJSONObject("order_req");
		MallOrder mallOrder = (MallOrder)JSONObject.toBean(jsonObject, config);
		if (StringUtils.isBlank(mallOrder.getOrder_comment()) && jsonObject.containsKey("service_remarks")) {
			mallOrder.setOrder_comment((String)jsonObject.get("service_remarks"));
		}
		return mallOrder;
	}
	
	/*
	 * 押金业务json转java对象  add wjq
	 */
	public static MallOrderDeposit jsonMallOrderDeposit(String inJson){
//		替换空格、回车、换行、制表位为空字符
		inJson = strReplaceBlank(inJson);
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setRootClass(MallOrderDeposit.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(inJson);
		MallOrderDeposit mallOrderDeposit = (MallOrderDeposit)JSONObject.toBean(jsonObject.getJSONObject("order_req"), config);
		return mallOrderDeposit;
	}
	/**
	 * 浙江本地商城json转为java bean
	 * @param inJson
	 * @return
	 */
	public static ZJLocalMallOrder jsonToZJLocalMallOrder(String inJson){
//		替换空格、回车、换行、制表位为空字符
		inJson = strReplaceBlank(inJson);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("orderItem", Item.class);
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(ZJLocalMallOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(inJson);
		ZJLocalMallOrder zjLocalMallOrder = (ZJLocalMallOrder)JSONObject.toBean(jsonObject, config);
		return zjLocalMallOrder;
	}
	
	/**
	 * 河北本地商城json转为java bean
	 * @param inJson
	 * @return
	 */
	public static HBBroadbandOrderReq jsonToHBBroadbandOrder(String inJson){
//		替换空格、回车、换行、制表位为空字符
		inJson = strReplaceBlank(inJson);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		//classMap.put("orderItem", Item.class);
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				//char[] chars = str.toCharArray();
		        //chars[0] = Character.toLowerCase(chars[0]);
		        return new String(str.toLowerCase());
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(HBBroadbandOrderReq.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(inJson);
		 
		HBBroadbandOrderReq hbBroadbandOrder = (HBBroadbandOrderReq)JSONObject.toBean(jsonObject, config);
		return hbBroadbandOrder;
	}
	
	/**
	 * 总部商城json转换为java对象
	 * @param json
	 * @return
	 */
	public static CenterMallOrder jsonToCenterMallOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("PayInfo", CenterMallPayInfo.class);
		classMap.put("DiscountInfo", CenterMallDiscountInfo.class);
		classMap.put("GoodsInfo", CenterMallGoodsInfo.class);
		classMap.put("GiftInfo", CenterMallGiftInfo.class);
		classMap.put("GoodsAttInfo", CenterMallGoodsAttInfo.class);
		classMap.put("FeeInfo", CenterMallFeeInfo.class);
		classMap.put("FlowInfo", CenterMallFlowInfo.class);
		classMap.put("OperatorInfo", CenterMallOperatorInfo.class);
		classMap.put("Package", CenterMallPackage.class);
		classMap.put("SubProdInfo", CenterMallSubProdInfo.class);
		classMap.put("ActivityInfo", CenterMallActivityInfo.class);
		classMap.put("ResourcesInfo", CenterMallResourcesInfo.class);
		classMap.put("LeagueInfo", CenterMallLeagueInfo.class);
		classMap.put("PhoneInfo", CenterMallGoodsPhoneInfo.class);
		classMap.put("NiceInfo", CenterMallGoodsNiceInfo.class);
		
		/*
		 * 责任人使用人信息(集客开户传) 
		 * ZX add 2015-12-23 start
		 */
		classMap.put("useCustInfo", CenterMallUseCustInfo.class);
		/*
		 * 责任人使用人信息(集客开户传) 
		 * ZX add 2015-12-23 end
		 */
		
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(CenterMallOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		CenterMallOrder centerMallOrder = (CenterMallOrder)JSONObject.toBean(jsonObject, config);
		return centerMallOrder;
	}
	
	/**
	 * 码上购json转换为java对象
	 * @param json
	 * @return
	 */
	public static CodePurchaseMallOrder jsonToCodePurchaseMallOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", CodePurchaseMallGoodsInfo.class);
		classMap.put("cust_info", CodePurchaseMallCustInfoa.class);
		classMap.put("pay_info", CodePurchaseMallPayInfo.class);
		classMap.put("delivery_info", CodePurchaseMallDeliverInfo.class);
		classMap.put("discount_info", CodePurchaseMallDiscountInfo.class);
		classMap.put("fee_list", CodePurchaseMallFeeInfo.class);

		
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(CodePurchaseMallOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		CodePurchaseMallOrder centerMallOrder = (CodePurchaseMallOrder)JSONObject.toBean(jsonObject, config);
		return centerMallOrder;
	}
	
	/**
	 * 融合业务json转换为java对象
	 * @param json
	 * @return
	 */
	public static GroupOrder jsonToGroupOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", GroupOrderGoodsInfo.class);
		classMap.put("cust_info", CodePurchaseMallCustInfoa.class);
		classMap.put("pay_info", CodePurchaseMallPayInfo.class);
		classMap.put("fee_list", GroupOrderFeeInfo.class);
		classMap.put("broad_info", GroupOrderBroadInfo.class);
		classMap.put("phone_info", GroupOrderPhoneInfo.class);	
		classMap.put("partners_info", GroupOrderPartners.class);    
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(GroupOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		GroupOrder groupOrder = (GroupOrder)JSONObject.toBean(jsonObject, config);
		return groupOrder;
	}
	
	/**
	 * 温大固网业务json转换为java对象
	 * @param json
	 * @return
	 */
	public static FixBusiOrder jsonToFixBusiOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("delivery_info", FixBusiOrderDeliveryInfo.class);
		classMap.put("goods_info", FixBusiOrderGoodsInfo.class);
		classMap.put("developer_info", FixBusiOrderDeveloperInfo.class);
		classMap.put("broad_info", FixBusiOrderBroadInfo.class);
		classMap.put("cust_info", FixBusiOrderCustInfo.class);
		classMap.put("pay_info", FixBusiOrderPayInfo.class);
		classMap.put("sub_prod_info", FixBusiOrderSubProdInfo.class);
		classMap.put("subs_other_info", FixBusiOrderSubsOtherInfo.class);
		
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(FixBusiOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		FixBusiOrder fixBusiOrder = (FixBusiOrder)JSONObject.toBean(jsonObject, config);
		return fixBusiOrder;
	}
	
	/**
	 * 互联网固网业务json转换为java对象
	 * @param json
	 * @return
	 */
	public static InterFixBusiOrder jsonToInterFixBusiOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", InterFixBusiOrderGoodsInfo.class);
		classMap.put("developer_info", InterFixBusiOrderDeveloperInfo.class);
		classMap.put("broad_info", InterFixBusiOrderBroadInfo.class);
		classMap.put("cust_info", InterFixBusiOrderCustInfo.class);
		classMap.put("pay_info", InterFixBusiOrderPayInfo.class);
		classMap.put("sub_prod_info", InterFixBusiOrderSubProdInfo.class);
		classMap.put("subs_other_info", InterFixBusiOrderSubsOtherInfo.class);
		classMap.put("contact_info",InterFixBusiOrderContactInfo.class);
		
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(InterFixBusiOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		InterFixBusiOrder interFixBusiOrder = (InterFixBusiOrder)JSONObject.toBean(jsonObject, config);
		return interFixBusiOrder;
	}
	
	/**
	 * 移网产品活动业务json转换为java对象
	 * @param json
	 * @return
	 */
	public static MobileBusiOrder jsonToMobileBusiOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", MobileBusiOrderGoodsInfo.class);
		classMap.put("developer_info", MobileBusiOrderDeveloperInfo.class);
		classMap.put("cust_info", MobileBusiOrderCustInfo.class);
		classMap.put("pay_info", MobileBusiOrderPayInfo.class);
		classMap.put("contact_info",MobileBusiOrderContactInfo.class);
		classMap.put("seedUser_info",MobileBusiOrderSeedUserInfo.class);
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(MobileBusiOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		MobileBusiOrder mobileBusiOrder = (MobileBusiOrder)JSONObject.toBean(jsonObject, config);
		return mobileBusiOrder;
	}
	
	/**
	 * 固网终端更换业务json转java对象
	 * @param json
	 * @return
	 */
//	public static ObjectReplaceBusiOrder jsonToReplaceBusiOrder(String json) throws Exception {
//		json = strReplaceBlank(json);
//		
//		Map<String, Class> classMap = new HashMap<String, Class>();
//		classMap.put("goods_info", ObjectReplaceGoodsInfo.class);
//		classMap.put("object_info", ObjectReplaceObjectInfo.class);
//		classMap.put("cust_info", ObjectReplaceCustInfo.class);
//		classMap.put("pay_info", ObjectReplacePayInfo.class);
//		classMap.put("contact_info", ObjectReplaceContactInfo.class);
//		classMap.put("fee_list", ObjectReplaceFeeInfo.class);
//		
//		JsonConfig config = new JsonConfig();
//		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
//			@Override
//			public String transformToJavaIdentifier(String str) {
//				char[] chars = str.toCharArray();
//				chars[0] = Character.toLowerCase(chars[0]);
//				return new String(chars);
//			}
//		});
//		config.setClassMap(classMap);
//		config.setRootClass(ObjectReplaceBusiOrder.class);
//		
//		JSONObject jsonObject = JSONObject.fromObject(json);
//		ObjectReplaceBusiOrder busiOrder = (ObjectReplaceBusiOrder)JSONObject.toBean(jsonObject,config);
//		
//		return busiOrder;
//	}
	
	/**
	 * 互联网融合业务json转换为java对象
	 * @param json
	 * @return
	 */
	public static InternetGroupBusiOrder jsonToGroupInterFixBusiOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", InterGroupBusiOrderGoodsInfo.class);
		classMap.put("developer_info", InterGroupBusiOrderDeveloperInfo.class);
		classMap.put("broad_info", InterGroupBusiOrderBroadInfo.class);
		classMap.put("fee_list", InterGroupFeeInfo.class);
		classMap.put("cust_info", InterGroupBusiOrderCustInfo.class);
		classMap.put("pay_info", InterGroupBusiOrderPayInfo.class);
		classMap.put("sub_prod_info", InterGroupBusiOrderSubProdInfo.class);
		classMap.put("subs_other_info", InterGroupBusiOrderSubsOtherInfo.class);
		classMap.put("contact_info",InterGroupBusiOrderContactInfo.class);
		classMap.put("phone_info",InterGroupBusiOrderPhoneInfo.class);
		
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(InternetGroupBusiOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
		InternetGroupBusiOrder interGroupBusiOrder = (InternetGroupBusiOrder)JSONObject.toBean(jsonObject, config);
		return interGroupBusiOrder;
	}
	
	/**
	 * 互联网业务json转换为java对象
	 * @param json
	 * @return
	 */
	public static InternetBusiOrder jsonToInternetBusiOrder(String json) throws Exception{
//		替换空格、回车、换行、制表位为空字符
		json = strReplaceBlank(json);
//		json中[]表示一个List集合，需要定义集合对象来接收
//		因为MallOrder类里面还有集合类型，所以加上classMap让JSONLib深度转换
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("phone_info", InternetBusiPhoneInfo.class);
		classMap.put("developer_info", InternetBusiDeveloperInfo.class);
		classMap.put("nice_info", InternetBusiNiceInfo.class);
		classMap.put("cust_info", InternetBusiCustInfo.class);
		classMap.put("pay_info", InternetBusiPayInfo.class);
		classMap.put("sub_prod_info", InternetBusiSubProdInfo.class);
		classMap.put("contact_info", InternetBusiContactInfo.class);
		
		JsonConfig config = new JsonConfig();
//		忽略json属性字段的首字母大小写
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
		        chars[0] = Character.toLowerCase(chars[0]);
		        return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(InternetBusiOrder.class);
//		生成一个json对象
		JSONObject jsonObject = JSONObject.fromObject(json);
	
		InternetBusiOrder internetBusiOrder = (InternetBusiOrder)JSONObject.toBean(jsonObject, config);
		return internetBusiOrder;
	}
	
	/**
	 * 日期数据转换为字符型日期
	 * 输入:Date
	 * 输出:yyyy-mm-dd hh24:mi:ss
	 * @return
	 */
	public static String strFormatDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	/**
	 * 日期转换为字符串
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	/**
	 * 字符转换为日期
	 * 输入：yyyymmddhh24miss
	 * 输出:Date
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 字符转换为日期
	 * 输入：yyyymmddhh24miss
	 * 输出:Date
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate, String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			return format.parse(strDate);
		} catch (ParseException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 获取字符型的当前时间
	 * yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	
	/**
	 * 以Post方式往服务端发送请求数据
	 * @param json
	 * @param urlAddr
	 * @return
	 */
	public static String sendPostHttpRequest(String json , String urlAddr){

		StringBuffer responseStr = new StringBuffer();
		try {
//			指定服务器
			URL url = new URL(urlAddr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			指定通信方式
			conn.setRequestMethod("POST");
//			指定超时时长
			conn.setConnectTimeout(30 * 1000);
//			不使用缓存
			conn.setUseCaches(false);
//			允许输入/输出
			conn.setDoInput(true);
			conn.setDoOutput(true);
//			设置content-type
			conn.setRequestProperty("Content-Type", "text/html;charset=utf-8");
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//			使用字节流往服务端写数据
			BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
			logger.info("URL：" + urlAddr);
			logger.info("同步json内容：" + json);
//			设置字符集
			out.write(json.getBytes("UTF-8"));
//			清空缓存、关闭连接
			out.flush();
			out.close();
//			接收服务端的返回消息
			InputStream inputStream = conn.getInputStream();
			if(null == inputStream){
				return "";
			}else{
//				设置读取输入流的字符集
				BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				String line;
				while ((line = rd.readLine()) != null) {
		        	responseStr.append(line);
		        }
				rd.close();
				inputStream.close();
			}
		} catch (IOException e) {
			responseStr.append(e.getMessage());
			logger.info(e.getMessage(), e);
		}
		logger.info("对端返回消息：" + responseStr.toString());
		return responseStr.toString();
	}
	
	/**
	 * 根据订单来源系统中文名获得编码
	 * @param orderFromName
	 * @return
	 */
	public static String getOrderFromId(String orderFromName){
		if("淘宝".equalsIgnoreCase(orderFromName)){
			return "10001";
		}else if( "联通商城".equalsIgnoreCase(orderFromName) ){
			return "10002";
		}else if( "总部商城".equalsIgnoreCase(orderFromName) ){
			return "10003";
		}else if( "网盟店铺".equalsIgnoreCase(orderFromName) ){
			return "10004";
		}else if("拍拍".equalsIgnoreCase(orderFromName) ){
			return "10005";
		}else if("农行商城".equalsIgnoreCase(orderFromName) ){
			return "10006";
		}else if("360商城".equalsIgnoreCase(orderFromName) ){
			return "10007";
		}else if("沃云购".equalsIgnoreCase(orderFromName)){
			return "10008";
		}else if("订单系统".equalsIgnoreCase(orderFromName)){
			return "10009";
		}else if("WMS".equalsIgnoreCase(orderFromName)){
			return "10010";
		}else if("商品管理系统".equalsIgnoreCase(orderFromName)){
			return "10011";
		}else if("淘宝分销".equalsIgnoreCase(orderFromName)){
			return "10012";
		}else if("沃商城".equalsIgnoreCase(orderFromName)){
			return "10036";
		}else if("CPS".equalsIgnoreCase(orderFromName)){
			return "10037";
		}else if("异业联盟".equalsIgnoreCase(orderFromName)){
			return "10038";
		}else if("电话商城".equalsIgnoreCase(orderFromName)){
			return "10015";
		}else if("微商城".equalsIgnoreCase(orderFromName)){
			return "10030";
		}else if("沃云购".equalsIgnoreCase(orderFromName)){
			return "10031";
		}else if("沃货架".equalsIgnoreCase(orderFromName)){
			return "10031";
		}else if("营业厅U惠站".equalsIgnoreCase(orderFromName)){
			return "10032";
		}else if("销售联盟".equalsIgnoreCase(orderFromName)){
			return "10033";
		}else if("vip商城".equalsIgnoreCase(orderFromName)){
			return "10034";
		}else if("电子沃店".equalsIgnoreCase(orderFromName)){
			return "10035";
		}else if("百度担保".equalsIgnoreCase(orderFromName)){
			return "10039";
		}else if("广州天猫".equalsIgnoreCase(orderFromName)){
			return "10057";
		}else{
			return orderFromName;
		}
	}
	
	/**
	 * 金额由厘转换为元
	 * @param money
	 * @return
	 */
	public static String parseMoney(Integer money){
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setGroupingUsed(false);
		String s = format.format( (double) money / 1000 );
		return s;
	}
	
	/**
	 * 金额由元转为厘
	 * @param money
	 * @return
	 */
	public static String parseMoneyToLi(double money){
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(3);
		format.setGroupingUsed(false);
		String s = format.format(money * 1000);
		return s;
	}
	
	/**
	 * 获取所有集合2(element)在集合1(all)中不存在的元素
	 * @param all
	 * @param element
	 * @return
	 */
	public static String notContainsElement(List<String> all , List<String> element){
		StringBuffer stringBuffer = new StringBuffer();
		for (String name : element) {
			if( ! all.contains(name) ){
				stringBuffer.append(name + ".");
			}
		}
		return stringBuffer.toString();
	}
	
	public static  String xml2JSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			xml = xml.replace("&", "&amp;");
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			obj.put(root.getName(), iterateElement(root));
			return obj.getString(root.getName());
		} catch (Exception e) {
			return null;
		}
	}
	private static Map  iterateElement(Element element) {
		List jiedian = element.getChildren();
		Element et = null;
		Map obj = new HashMap();
		List list = null;
		for (int i = 0; i < jiedian.size(); i++) {
			list = new LinkedList();
			et = (Element) jiedian.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (obj.containsKey(et.getName())) {
					list = (List) obj.get(et.getName());
				}
				list.add(iterateElement(et));
				obj.put(et.getName(), list);
			} else {
//				if (obj.containsKey(et.getName())) {
//					list = (List) obj.get(et.getName());
//				}
//				list.add(et.getTextTrim());
				obj.put(et.getName(), et.getTextTrim());
			}
		}
		return obj;
	}
	
	public static String getSeqNo(){
	 	DateFormat tempDF = new SimpleDateFormat("yyyyMMddHHmmss");   
		String str=tempDF.format(new Date());
		String model = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";  
        char[] m = model.toCharArray(); 
		for(int j=0;j<6;j++){
			char c = m[(int)(Math.random()*62)];  
			str = str + c;  
		}
		return str;
	}
	
	/**
	 * 获取json字符串中某一个key的value
	 * @param json 
	 * @param key 键
	 * @return
	 */
	public static String searchValue(String json, String key) {
		json = strReplaceBlank(json);
		String regex = "\"" +key + "\":(.*?),";
		Matcher matcher=Pattern.compile(regex).matcher(json);
		String value = "";
		
		while(matcher.find())
		{
			value = matcher.group(1);
		}
		// 去掉双引号("")
		value = value.trim().replaceAll("\"(\\w+)\"", "$1"); 
		return value;
	}
	/**
	 * 固网终端更换业务json转java对象
	 * @param json
	 * @return
	 */
	public static ObjectReplaceBusiOrder jsonToReplaceBusiOrder(String json) throws Exception {
		json = strReplaceBlank(json);
		
		Map<String, Class> classMap = new HashMap<String, Class>();
		classMap.put("goods_info", ObjectReplaceGoodsInfo.class);
		classMap.put("object_info", ObjectReplaceObjectInfo.class);
		classMap.put("cust_info", ObjectReplaceCustInfo.class);
		classMap.put("pay_info", ObjectReplacePayInfo.class);
		classMap.put("contact_info", ObjectReplaceContactInfo.class);
		classMap.put("fee_list", ObjectReplaceFeeInfo.class);
		
		JsonConfig config = new JsonConfig();
		config.setJavaIdentifierTransformer(new JavaIdentifierTransformer(){
			@Override
			public String transformToJavaIdentifier(String str) {
				char[] chars = str.toCharArray();
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		});
		config.setClassMap(classMap);
		config.setRootClass(ObjectReplaceBusiOrder.class);
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		ObjectReplaceBusiOrder busiOrder = (ObjectReplaceBusiOrder)JSONObject.toBean(jsonObject,config);
		
		return busiOrder;
	}
}
