package com.ztesoft.net.outter.inf.taobao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import params.ZteResponse;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.remote.params.activity.req.PromotionMapByIdReq;
import com.ztesoft.remote.params.activity.resp.PromotionMapByIdResp;

public class Test {

	//正式环境：http://gw.api.taobao.com/router/rest
	//沙箱环境：http://gw.api.tbsandbox.com/router/rest
	public static  String url = "http://gw.api.taobao.com/router/rest";
	public static  String appkey = "12469285";
	public static  String secret = "d7f3540761ae620397baaa27afc1c035";
	public static String sessionKey = "6100a00011dbbb3e5cb6fe62abb830bbad19b4277093977747143122";
	
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("111111");
		//String configs[]=new String[]{"classpath*:spring/*.xml","classpath*:dubbo/reference/*.xml"};
		//ApplicationContext context = new ClassPathXmlApplicationContext(configs);
		
		PromotionMapByIdReq req =  new PromotionMapByIdReq();
		req.setActivity_id("201403268759000140");
		ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
        ZteResponse response = client.execute(req, PromotionMapByIdResp.class);
        System.out.println("姝ゆ璋冪敤缁撴灉:"+response.isResult()+",msg:"+response.getError_msg());

		
		/*String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println(ip);
		String port = System.getProperty(Consts.PORT_SYS_PROP);
		System.out.println(port);*/
		//test1();
		//test2();
		//System.out.println(java.net.URLEncoder.encode("http://localhost:8080/taobao/t.jsp", "utf-8"));
		
		//OrderCollect c = new OrderCollect();
	//	String str = "{\"@type\":\"com.ztesoft.net.outter.inf.model.OrderCollect\",\"acceptList\":[{\"@type\":\"com.ztesoft.net.outter.inf.taobao.TaobaoAccept\",\"act_protper\":\"12\",\"adjustment_credit\":\"\",\"ative_type\":\"ggg\",\"bank_account\":\"\",\"bank_code\":\"\",\"bank_user\":\"\",\"brand_name\":\"gaaa\",\"brand_number\":\"123\",\"cert_address\":\"\",\"cert_failure_time\":\"2050-12-31 23:59:59\",\"certi_number\":\"123\",\"certi_type\":\"1\",\"collection\":\"0\",\"color_code\":\"red\",\"color_name\":\"red\",\"disfee_select\":\"ffff\",\"famliy_num\":\"\",\"first_payment\":\"13\",\"goods_num\":10,\"is_iphone_plan\":\"0\",\"is_liang\":\"0\",\"is_loves_phone\":\"0\",\"is_stock_phone\":\"0\",\"isgifts\":\"0\",\"liang_code\":\"12312312\",\"liang_price\":1000D,\"loves_phone_num\":\"\",\"model_code\":\"123123123\",\"model_name\":\"ggfgfgfg\",\"order_id\":\"201403187172129544\",\"out_package_id\":\"123123\",\"out_plan_id_bss\":\"4234324\",\"out_plan_id_ess\":\"9324i024\",\"phone_deposit\":0,\"phone_num\":\"\",\"phone_owner_name\":\"\",\"plan_title\":\"ksfjlksjf\",\"pro_detail_code\":\"1\",\"pro_name\":\"mk-测试商品\",\"pro_origfee\":123123D,\"pro_type\":\"123\",\"product_id\":\"1\",\"product_net\":\"2G\",\"reliefpres_flag\":\"\",\"sell_price\":1111D,\"simid\":\"\",\"society_name\":\"\",\"society_price\":0,\"specificatio_nname\":\"oeoeo\",\"specification_code\":\"\",\"superiors_bankcode\":\"\",\"terminal_num\":\"\",\"white_cart_type\":\"\"}],\"goodsAttrList\":[{\"act_protper\":\"12\",\"adjustment_credit\":\"\",\"ative_type\":\"ggg\",\"bank_account\":\"\",\"bank_code\":\"\",\"bank_user\":\"\",\"brand_name\":\"gaaa\",\"brand_number\":\"123\",\"collection\":\"0\",\"color_code\":\"red\",\"color_name\":\"red\",\"disfee_select\":\"ffff\",\"famliy_num\":\"\",\"first_payment\":\"13\",\"goods_num\":\"10\",\"is_iphone_plan\":\"0\",\"is_liang\":\"0\",\"is_loves_phone\":\"0\",\"is_stock_phone\":\"0\",\"isgifts\":\"0\",\"liang_code\":\"12312312\",\"liang_price\":\"1000.0\",\"model_code\":\"123123123\",\"model_name\":\"ggfgfgfg\",\"out_package_id\":\"123123\",\"out_plan_id_bss\":\"4234324\",\"out_plan_id_ess\":\"9324i024\",\"phone_deposit\":\"\",\"phone_num\":\"\",\"phone_owner_name\":\"\",\"plan_title\":\"ksfjlksjf\",\"pro_detail_code\":\"1\",\"pro_name\":\"mk-测试商品\",\"pro_origfee\":\"123123.0\",\"pro_type\":\"123\",\"product_net\":\"2G\",\"reliefpres_flag\":\"\",\"simid\":\"\",\"society_name\":\"\",\"society_price\":\"\",\"specificatio_nname\":\"oeoeo\",\"specification_code\":\"\",\"superiors_bankcode\":\"\",\"terminal_num\":\"\",\"white_cart_type\":\"\"}],\"infOrder\":{\"@type\":\"com.ztesoft.net.outter.inf.taobao.TaobaoOrder\",\"abnormal_status\":\"qqqqqqqqqqqqq\",\"address\":\"ddddddd\",\"alipay_id\":\"111111111111\",\"area_code\":\"2222222\",\"bss_order_type\":\"\",\"buyer_id\":\"vvvvvvv\",\"buyer_message\":\"\",\"buyer_name\":\"mkmkm\",\"cert_address\":\"\",\"cert_failure_time\":\"\",\"city\":\"广州\",\"city_code\":\"gz\",\"collection_free\":\"\",\"couponbatchid\":\"\",\"couponname\":\"\",\"delivery_status\":\"成功\",\"development_code\":\"\",\"development_name\":\"\",\"discountValue\":\"\",\"discountid\":\"\",\"discountname\":\"\",\"discountrange\":\"\",\"disfee_type\":\"\",\"district\":\"ddddddd\",\"file_return_type\":\"\",\"get_time\":\"2014-10-10 11:11:11\",\"goods_num\":1,\"invoice_print_type\":\"\",\"invoice_title\":\"\",\"is_adv_sale\":\"0\",\"is_bill\":\"\",\"itemList\":[{\"@type\":\"com.ztesoft.net.outter.inf.taobao.TaobaoOrderItem\",\"act_protper\":\"12\",\"adjustment_credit\":\"\",\"ative_type\":\"ggg\",\"bank_account\":\"\",\"bank_code\":\"\",\"bank_user\":\"\",\"brand_name\":\"gaaa\",\"brand_number\":\"123\",\"cert_card_num\":\"123\",\"cert_type\":\"1\",\"collection\":\"0\",\"color_code\":\"red\",\"color_name\":\"red\",\"disfee_select\":\"ffff\",\"famliy_num\":\"\",\"first_payment\":\"13\",\"is_iphone_plan\":\"0\",\"is_liang\":\"0\",\"is_loves_phone\":\"0\",\"is_stock_phone\":\"0\",\"isgifts\":\"0\",\"liang_code\":\"12312312\",\"liang_price\":1000D,\"loves_phone_num\":\"\",\"model_code\":\"123123123\",\"model_name\":\"ggfgfgfg\",\"out_package_id\":\"123123\",\"out_plan_id_bss\":\"4234324\",\"out_plan_id_ess\":\"9324i024\",\"phone_deposit\":0,\"phone_num\":\"\",\"phone_owner_name\":\"\",\"plan_title\":\"ksfjlksjf\",\"pro_detail_code\":\"1\",\"pro_name\":\"mk-测试商品\",\"pro_num\":10,\"pro_origfee\":123123D,\"pro_type\":\"123\",\"product_net\":\"2G\",\"reliefpres_flag\":\"\",\"sell_price\":1111D,\"simid\":\"\",\"society_name\":\"\",\"society_price\":0,\"specificatio_nname\":\"oeoeo\",\"specification_code\":\"\",\"superiors_bankcode\":\"\",\"terminal_num\":\"\",\"white_cart_type\":\"\"}],\"merge_status\":\"合并\",\"one_agents_id\":\"\",\"order_channel\":\"taobao\",\"order_city_code\":\"杭州市\",\"order_disfee\":\"200\",\"order_from\":\"\",\"order_origfee\":\"\",\"order_provinc_code\":\"gz\",\"order_realfee\":\"1000d\",\"order_totalfee\":\"200\",\"orderacccode\":\"\",\"orderacctype\":\"\",\"out_tid\":\"23232313\",\"pay_mothed\":\"\",\"pay_status\":\"成功\",\"pay_time\":\"2014-03-05 11:11:11\",\"paychannelid\":\"\",\"paychannelname\":\"\",\"payfintime\":\"\",\"payplatformorderid\":\"\",\"payproviderid\":\"\",\"payprovidername\":\"\",\"paytype\":\"\",\"phone\":\"1590000000000\",\"plat_distributor_code\":\"\",\"plat_type\":\"taobao\",\"platform_status\":\"1\",\"post\":\"12313\",\"pro_totalfee\":\"2000.1\",\"promotion_area\":\"\",\"provinc_code\":\"gd\",\"province\":\"gd\",\"receiver_mobile\":\"1590000000\",\"receiver_name\":\"kkkk\",\"recommended_code\":\"\",\"recommended_name\":\"\",\"recommended_phone\":\"\",\"sending_type\":\"GSM\",\"service_remarks\":\"\",\"status\":\"1\",\"tid\":\"\",\"tid_time\":\"2014-10-10 10:10:10\",\"two_agents_id\":\"\",\"type\":\"正常订单\",\"vouchers_code\":\"\",\"vouchers_money\":\"\",\"wm_isreservation_from\":\"\",\"wm_order_from\":\"\",\"wt_paipai_order_id\":\"\"},\"orderAttr\":{\"abnormal_status\":\"qqqqqqqqqqqqq\",\"alipay_id\":\"111111111111\",\"area_code\":\"2222222\",\"bss_order_type\":\"\",\"buyer_id\":\"vvvvvvv\",\"buyer_name\":\"mkmkm\",\"cert_address\":\"\",\"cert_failure_time\":\"\",\"city\":\"广州\",\"city_code\":\"gz\",\"collection_free\":\"\",\"couponbatchid\":\"\",\"couponname\":\"\",\"delivery_status\":\"成功\",\"discountValue\":\"\",\"discountid\":\"\",\"discountname\":\"\",\"discountrange\":\"\",\"disfee_type\":\"\",\"district\":\"ddddddd\",\"file_return_type\":\"\",\"invoice_print_type\":\"\",\"invoice_title\":\"\",\"is_bill\":\"\",\"one_agents_id\":\"\",\"order_city_code\":\"杭州市\",\"order_disfee\":\"200\",\"order_origfee\":\"\",\"order_provinc_code\":\"gz\",\"order_realfee\":\"1000d\",\"orderacccode\":\"\",\"orderacctype\":\"\",\"pay_mothed\":\"\",\"pay_time\":\"2014-03-05 11:11:11\",\"paychannelid\":\"\",\"paychannelname\":\"\",\"payfintime\":\"\",\"payplatformorderid\":\"\",\"payproviderid\":\"\",\"payprovidername\":\"\",\"paytype\":\"\",\"phone\":\"1590000000000\",\"plat_distributor_code\":\"\",\"platform_status\":\"1\",\"post\":\"12313\",\"promotion_area\":\"\",\"provinc_code\":\"gd\",\"province\":\"gd\",\"sending_type\":\"GSM\",\"service_remarks\":\"\",\"two_agents_id\":\"\",\"vouchers_code\":\"\",\"vouchers_money\":\"\",\"wm_order_from\":\"\",\"wt_paipai_order_id\":\"\"},\"orderOuter\":{\"acc_nbr\":\"\",\"accept_time\":\"\",\"addon\":\"\",\"batch_id\":\"201403189798129543\",\"certi_number\":\"123\",\"certi_type\":\"1\",\"col1\":\"\",\"col2\":\"\",\"comments\":\"\",\"create_time\":\"2014-10-10 11:11:11\",\"cust_name\":\"\",\"development_code\":\"\",\"development_name\":\"\",\"ext_cols\":\"\",\"goods_attrs\":\"\",\"goods_id\":\"\",\"goods_name\":\"\",\"goods_num\":\"1\",\"iccid\":\"\",\"import_state\":\"\",\"import_userid\":\"\",\"is_adv_sale\":\"0\",\"item_type\":0,\"lan_id\":\"\",\"member_lv_id\":\"\",\"merge_status\":\"合并\",\"name\":\"\",\"o_source_from\":\"\",\"offer_name\":\"\",\"old_sec_order_id\":\"23232313\",\"order_amount\":\"200\",\"order_attrs\":\"\",\"order_channel\":\"taobao\",\"order_from\":\"taobao\",\"order_id\":\"201403187172129544\",\"order_type\":\"正常订单\",\"payment_id\":\"\",\"plat_type\":\"taobao\",\"price\":\"\",\"pro_totalfee\":\"2000.1\",\"product_id\":\"1\",\"remark\":\"\",\"sec_order_id\":\"\",\"sex\":0,\"ship_addr\":\"ddddddd\",\"ship_day\":\"\",\"ship_mobile\":\"1590000000\",\"ship_name\":\"kkkk\",\"shipping_id\":\"\",\"source_from\":\"\",\"spread_code\":\"\",\"spread_name\":\"\",\"spread_phone\":\"\",\"status\":\"1\",\"terminal_code\":\"\",\"terminal_name\":\"\",\"tid_time\":\"2014-10-10 10:10:10\",\"uname\":\"\",\"userid\":\"\",\"wm_isreservation_from\":\"\"}}";//CommonTools.beanToJson(c);
		//System.out.println(str);
		//OrderCollect c = CommonTools.jsonToBean(str, OrderCollect.class);
		//System.out.println(c);
		//OrderStandardPushReq req = new OrderStandardPushReq();
		//req.setOuterList(null);
		//ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		//OrderStandardPushResp resp = client.execute(req, OrderStandardPushResp.class);
		
		/*String str = "广东广州";
		System.out.println(str.length());
		System.out.println(str.substring(0,2));
		System.out.println(str.substring(2,4));*/
		/*String str = "##地市=深圳,                                                                                                            "
				+"号码=18575540731,                                                                                                       "
				+"套餐=96C套餐-本地通话型-联通沃3G,                                                                                       "
				+"合约期=24个月,                                                                                                          "
				+"资费方式=费用按您实际使用情况收取，                                                                                     "
				+"发票=一次性发票,                                                                                                        "
				+"资料上传=是,                                                                                                            "
				+"资料类型=二代,                                                                                                          "
				+"开户名=沈良嵩,                                                                                                          "
				+"资料号码=530381199402102050,                                                                                            "
				+"卡类型=大卡,                                                                                                            "
				+"导购=蔡美春,                                                                                                            "
				+"合约类型=存费送机,                                                                                                      "
				+"产品颜色=银灰,                                                                                                          "
				+"处理时间=2014-4-2 9:58:07,                                                                                              "
				+"##已在线提供身份证照片:已发深圳群，信息已经在线核对：请知悉：客服：阮健丽：2014-4-2 18:14:24;未接4.3;多次未接;已上传资料";
		String [] ss = str.split(",");
		for(String s:ss){
			if(s!=null){
				String [] s2 = s.trim().split("=");
				if(s2!=null && s2.length==2){
					if("卡类型".equals(s2[0])){
						System.out.println(s2[1]);
						break ;
					}
				}
			}
		}*/
		
		//testOrder();
		
	}
	
	public static void testOrder() throws ApiException{
		String [] tids = {"611896416155060",
				"611767687109302",
				"611640741653363",
				"611819448003580",
				"611366200000786",
				"611944335556087",
				"611963448762680",
				"611442438130104",
				"611992961079069",
				"611941304998579",
				"612224010553333",
				"612289840305346",
				"611854566368403",
				"611383232918403",
				"612353844486793",
				"612307765609513",
				"611982674854764",
				"612569779939978",
				"612486009212631",
				"612001077012784",
				"612407382239044",
				"612850259500684",
				"612299712245425",
				"612381393485366",
				"612850181209309",
				"612865782281482",
				"612875383585304",
				"613154573112434",
				"612892662921890",
				"612576112796395",
				"613919620499104",
				"614294890037454",
				"613939861744525",
				"613959860804033",
				"613972749533657",
				"613983946989757",
				"614250885919876",
				"612915787853132",
				"612661715539192",
				"613499536933614",
				"612862111696954",
				"612869390332740",
				"613581218640019",
				"613122754899104",
				"613474905490019",
				"613469305452497",
				"613651842875540",
				"613714503075111",
				"613893368376744",
				"613748640810053",
				"613925770788232",
				"613864808982849",
				"613405951029029",};
		
		String [] exists_tids = {"612730275925555",
				"613551861950019",
				"613805614600019",
				"613229158610019",
				"613545863040019",
				"613511555211480",
				"613901686143503",
				"611297078769265",
				"609686596932098",
				"611805864772680",
				"612307054306654",
				"612512735761118",
				"612446483782345",
				"612152355318857",
				"611903768032680",
				"613585239434566",
				"614100403109984",
				"614042269564863",
				"614066109878279",
				"613159057476730"};
		
		
		String [] ids0423 = {"621138812572918",
				"620055866405841",
				"610983867612168",
				"552053375803183",
				"611736974526024",
				"611150190796024",
				"616842241607146",
				"610810733500284",
				"621372588816218",
				"618583707931288",
				"617084111821008",
				"621223792730339",
				"620844590875170",
				"599507606098165",
				"616868342507050",
				"592155605403685",
				"611672967395027",
				"620833122916180",
				"604524263482020",
				"610189208954502",
				"614632489930299",
				"611266275940347",
				"611572184614347",
				"611314491426024",
				"610304596526891",
				"611281531878696",
				"611162178776438",
				"616952745156290",
				"621426581012525",
				"588357767131440",
				"613747773185540",
				"606260081704823",
				"611312417082406",
				"563803460470610",
				"611718507746530",
				"611290184894789",
				"610972803035303",
				"619449863634592",
				"612057933390673",
				"612136416288114",
				"616980086913730",
				"611803399388525",
				"612235934353333",
				"605860814294165",
				"611882244884232",
				"568197551931745",
				"612437130051649",
				"612377044482616",
				"609440035115927",
				"610793073162102",
				"611755557946673",
				"611670038220780",
				"626059056380385",
				"611265360061203",
				"611331130968247",
				"611315524829912",
				"621153778198607",
				"621212653330347",
				"609726102695927",
				"619050028996513",
				"617740745276513",
				"616981218336513",
				"615672186956513",
				"611834663955350",
				"612642400694454",
				"611805864772680",
				"612385603976991",
				"611545922474755",
				"585670198021032",
				"612898666017853",
				"613641612727187",
				"619573138751280",
				"625884111086722"};
		
		String [] ids0424 = {"627208501163359",
				"627248022980944",
				"627556094432688",
				"626950031075787",
				"627595137258711",
				"627012270374225",
				"627087958535241",
				"627098836117516",
				"627423064459610",
				"627630323054688",
				"627105477595583",
				"627643444667516",
				"627431468477516",
				"627741458862223",
				"627754815031948",
				"627145635696956",
				"627152351667551",
				"627148438671692",
				"627149078053584",
				"627691368482595"};
		
		String [] ids0425 = {"629846118319525",
				"629915550876884",
				"630039795194398",
				"630420022393644",
				"630436827749939",
				"630477528154514",
				"630499204241161"};
		
		String [] ids0425_eno = {"629399871998356",
				"629673228786537",
				"628667471961817",
				"628649957998412",
				"629666256618629",
				"629351604767186"};
		
		String [] ids0427 = {"629915550876884",
				"630039795194398",
				"630420022393644",
				"630477528154514",
				"630499204241161"};
		
		String [] ids0504 = {"633595855403071",
				"633595855403071",
				"635353377818100",
				"635353377818100",
				"635353377818100",
				"635708833196975",
				"635708833196975",
				"635708833196975",
				"636470162387758",
				"633595855403071",
				"637562819012674",
				"637973700707304",
				"637973700707304",
				"638437697789340",
				"634107875091366",
				"637973700707304",
				"638437697789340",
				"639842491919202",
				"639842491919202"};
		
		String [] ids0504_i = {"635427690716194",
				"637067479115468",
				"637753846465291",
				"638271854381291",
				"638063280780832",
				"637602919517304",
				"638331844046085",
				"637546967960936",
				"637292666313376",
				"636996356786165",
				"637731210080200",
				"637757601288973",
				"637791040900819",
				"637221798179273",
				"637566985013882",
				"637272914676051",
				"637304674534891",
				"637671866697198",
				"638130169322298",
				"637888588071291",
				"637547793180169",
				"637984265269503",
				"638374255087334",
				"637702678049013",
				"636476960759023",
				"635406277200782",
				"636700328208431",
				"635462024129013",
				"637620820894407",
				"637355318194256",
				"637726093604069",
				"637558751040043",
				"635502184958519",
				"637804497700368",
				"637792886811002",
				"638357921667568",
				"637396108069976",
				"638048260902306",
				"636135143320666",
				"636996593881814",
				"637792564527704",
				"637979201070504",
				"638148005952022",
				"637412196575095",
				"638224566416031",
				"637733603387679",
				"637289632573421",
				"637991521766444",
				"637441630473461"};
		
		for(String s:ids0504){
			try{
				TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
				TradeGetRequest req=new TradeGetRequest();
				req.setFields("seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,buyer_message,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,is_daixiao,consign_interval,arrive_interval,arrive_cut_time,orders.title,orders.pic_path,orders.price,orders.num,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.outer_sku_id,orders.outer_iid,orders.buyer_rate,orders.seller_rate,orders.is_daixiao");
				req.setTid(Long.parseLong(s));
				TradeGetResponse response = client.execute(req , sessionKey);
				System.out.println(response.getTrade().getType()+"\t"+response.getTrade().getTid()+"\t"+response.getTrade().getStatus()+"\t"+df.format(response.getTrade().getPayTime())+"\t"+df.format(response.getTrade().getModified())+"\t"+response.getTrade().getIsWt()+"\t"+response.getTrade().getOrders().get(0).getOuterSkuId()+"\t"+response.getTrade().getOrders().get(0).getOuterIid());
//				TradeWtverticalGetRequest wtreq=new TradeWtverticalGetRequest();
//				wtreq.setTids(s);
//				TradeWtverticalGetResponse wt = client.execute(wtreq , sessionKey);
//				System.out.println(wt.getBody());
//				System.out.println(wt.getWtextResults().get(0).getOutPackageId());
			}catch(Exception ex){
				//ex.printStackTrace();
				System.out.println("error"+"\t"+s);
				continue ;
			}
		}
		
	}
	
	public static void test1() throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		ItemcatsGetRequest req=new ItemcatsGetRequest();
		req.setFields("cid,parent_cid,name,is_parent");
		req.setParentCid(0L);
		ItemcatsGetResponse response = client.execute(req);
		System.out.println(response.getMsg());
		System.out.println(response.getBody());
	}
	
	public static void test2() throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret,"xml");
		TradesSoldGetRequest req=new TradesSoldGetRequest();
		req.setFields("seller_nick,buyer_nick,title,type,created,sid,tid," +
				"seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee," +
				"post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee," +
				"point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid," +
				"num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state," +
				"receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone," +
				"orders.title,orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id," +
				"orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment," +
				"orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate," +
				"orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			req.setStartCreated(df.parse("2014-03-24 09:50:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		req.setEndCreated(new Date());
		req.setStatus("TRADE_FINISHED");
		TradesSoldGetResponse response = client.execute(req,sessionKey);
		System.out.println(response.getBody());
		/*System.out.println(response.getErrorCode()+"\t"+response.getMsg());
		for(int i=0;i<response.getTrades().size();i++){
			System.out.println("outerid:="+response.getTrades().get(i).getOrders().get(0).getOuterIid());
		}*/
		//System.out.println(response.getTrades());
	}
}
