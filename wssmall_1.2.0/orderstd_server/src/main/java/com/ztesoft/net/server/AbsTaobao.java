package com.ztesoft.net.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import zte.params.region.req.RegionsGetReq;
import zte.params.region.resp.RegionsGetResp;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradeWtverticalGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeWtverticalGetResponse;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.iservice.OuterInf;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.util.TaoBaoHttpClientUtils;

public abstract class AbsTaobao implements OuterInf{
	private static Logger logger = Logger.getLogger(AbsTaobao.class);
		//@Resource
		protected IOutterECSTmplManager outterStdTmplManager;
		//protected INumberService numberService;
		
		public static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
	    //正式环境：http://gw.api.taobao.com/router/rest
		//沙箱环境：http://gw.api.tbsandbox.com/router/rest
		//public static  String url = "http://gw.api.taobao.com/router/rest";
		public static final String WT_URL = "http://gw.api.taobao.com/router/rest";
		//public static  String appkey = "12580564";
		//public static  String secret = "efbab255db138519e303e73e078a0a19";
		//public static  String sessionKey = "6102b1205bb47a99e0b01f436544aa57047c6d17f37e542747143122";
		
		public static final String INC_ORDER_EDITTIME_FIELDS = "seller_nick,buyer_nick,title,type,created,tid,seller_rate,seller_can_rate,buyer_rate,can_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,alipay_id,alipay_no,is_lgtype,is_force_wlb,is_brand_sale,has_buyer_message,credit_card_fee,step_trade_status,step_paid_fee,mark_desc,send_time,,has_yfx,yfx_fee,yfx_id,yfx_type,trade_source,trade_from,seller_flag,is_daixiao,is_part_consign,orders,service_orders,is_wt";
		
		/**
		 * 返回的字段 全量查询
		 */
		public static final String ORDER_LIST_FIELDS = "seller_nick,buyer_nick,title,type,created,tid,seller_rate,seller_can_rate,buyer_rate,can_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,seller_flag,alipay_id,alipay_no,is_lgtype,is_force_wlb,is_brand_sale,buyer_area,has_buyer_message,credit_card_fee,lg_aging_type,lg_aging,step_trade_status,step_paid_fee,mark_desc,has_yfx,yfx_fee,yfx_id,yfx_type,trade_source,trade_from,send_time,is_daixiao,is_wt,is_part_consign,orders.title,orders.pic_path,orders.price,orders.num,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.refund_id,orders.seller_type,orders.end_time,orders.order_from,orders.consign_time,orders.shipping_type,orders.logistics_company,orders.invoice_no,orders.is_daixiao";
		//全部 seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,buyer_message,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,is_daixiao,consign_interval,arrive_interval,arrive_cut_time,orders.title,orders.pic_path,orders.price,orders.num,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.outer_sku_id,orders.outer_iid,orders.buyer_rate,orders.seller_rate,orders.is_daixiao
		//trade  seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,buyer_message,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,is_daixiao,consign_interval,arrive_interval,arrive_cut_time
		public static final String TRADE_FIELD = "seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_rate,status,payment,discount_fee,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,buyer_message,pic_path,num_iid,num,price,cod_fee,cod_status,shipping_type,is_daixiao,consign_interval,arrive_interval,arrive_cut_time,orders.title,orders.pic_path,orders.price,orders.num,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.outer_sku_id,orders.outer_iid,orders.buyer_rate,orders.seller_rate,orders.is_daixiao";
		
		/**
		 * 交易状态，默认查询所有交易状态的数据，除了默认值外每次只能查询一种状态。 
		 * 可选值： TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款) 
		 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) SELLER_CONSIGNED_PART（卖家部分发货）
		 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) TRADE_BUYER_SIGNED(买家已签收,货到付款专用) 
		 * TRADE_FINISHED(交易成功) TRADE_CLOSED(交易关闭) TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭) 
		 * ALL_WAIT_PAY(包含：WAIT_BUYER_PAY、TRADE_NO_CREATE_PAY) ALL_CLOSED(包含：TRADE_CLOSED、TRADE_CLOSED_BY_TAOBAO)
		 */
		public static final String STATUS = "TRADE_FINISHED";
		
		/**
		 * 交易类型列表，同时查询多种交易类型可用逗号分隔。默认同时查询guarantee_trade, auto_delivery, ec, cod,step
		 * 的5种交易类型的数据；查询所有交易类型的数据，需要设置下面全部可选值。 可选值： fixed(一口价) auction(拍卖) 
		 * guarantee_trade(一口价、拍卖) step(分阶段付款，万人团，阶梯团订单） independent_simple_trade
		 * (旺店入门版交易) independent_shop_trade(旺店标准版交易) auto_delivery(自动发货) ec(直冲) 
		 * cod(货到付款) game_equipment(游戏装备) shopex_trade(ShopEX交易) netcn_trade(万网交易) 
		 * external_trade(统一外部交易) instant_trade (即时到账) b2c_cod(大商家货到付款) hotel_trade(酒店类型交易) 
		 * super_market_trade(商超交易) super_market_cod_trade(商超货到付款交易) taohua(淘花网交易类型） 
		 * waimai(外卖交易类型） nopaid（即时到帐/趣味猜交易类型） step (万人团) eticket(电子凭证) tmall_i18n
		 * （天猫国际）;nopaid （无付款交易）insurance_plus（保险）finance（基金） 注：guarantee_trade
		 * 是一个组合查询条件，并不是一种交易类型，获取批量或单个订单中不会返回此种类型的订单。
		 * 
		 * 可传多个使用","号分开
		 */
		public static final String ORDER_TYPE = "fixed";
		
		public static final String order_channel = "A";//A	他建互联网电商(已经改为了订为源 10001或10012)
		
		public static final String plat_type = "淘宝";
		public static Map<String,String> PAY_METHOD = new HashMap<String,String>();
		static{
			//支付方式：ALIPAY_SURETY（支付宝担保交易）、ALIPAY_CHAIN（分账交易）、
			// TRANSFER（线下转账）、PREPAY（预存款）、IMMEDIATELY（即时到账）、CASHGOODS（先款后货）
			//淘宝分销的支付方式和淘宝是一样的，建议目前都对应到支付宝方式，只要不影响客户付款和我们收款就可以了，如果以后有需要再改。
			PAY_METHOD.put("ALIPAY_SURETY", "ZFB");
			PAY_METHOD.put("ALIPAY_CHAIN", "ZFB");
			PAY_METHOD.put("TRANSFER", "ZFB");
			PAY_METHOD.put("PREPAY", "ZFB");
			PAY_METHOD.put("IMMEDIATELY", "ZFB");
			PAY_METHOD.put("CASHGOODS", "ZFB");
		}
		public static Map<String,String> CARD_TYPE = new HashMap<String,String>();
		static{
			//NM	普卡 MC	微卡 NN	纳诺卡
			CARD_TYPE.put("大卡","NM");
			CARD_TYPE.put("小卡","MC");
			CARD_TYPE.put("NANO卡","NN");
			CARD_TYPE.put("nano卡","NN");
			CARD_TYPE.put("微型卡","MC");
			CARD_TYPE.put("普通卡","NM");
			
		}
		public static Map<String,String> CERT_TYPE = new HashMap<String,String>();
		static{
			CERT_TYPE.put("身份证","SFZ");
			CERT_TYPE.put("护照","HZB");
			CERT_TYPE.put("户口簿","HKB");
			CERT_TYPE.put("军官证","JUZ");
			CERT_TYPE.put("警官证","JGZ");
			CERT_TYPE.put("港澳居民来往内地通行证","GOT");
			CERT_TYPE.put("台湾军民来往大陆通行证","TWT");
		}
		
		/**
		 * 默认地区
		 */
		//public static final String default_region = "440106|天河区|440106";
		//public static final String default_city = "440100|广州市|440100";
		//public static final String default_provin = "440000|广东省|440000";
		
		public static Map<String,String> TAOBAO_CHANNEL = new HashMap<String,String>();
		static{
			/**
			 * 交易内部来源。 WAP(手机);HITAO(嗨淘);TOP(TOP平台);TAOBAO(普通淘宝);JHS(聚划算) 一笔订单可能同时有以上多个标记，
			 * 则以逗号分隔
			 */
			TAOBAO_CHANNEL.put("WAP", "手机");
			TAOBAO_CHANNEL.put("HITAO", "嗨淘");
			TAOBAO_CHANNEL.put("TAOBAO", "普通淘宝");
			TAOBAO_CHANNEL.put("JHS", "聚划算");
		}
		//首月资料费方式
		public static Map<String,String> FIRST_PAYMENT_MAP = new HashMap<String,String>();
		static{
			//全月/半月/次月
			//ALLM	当月生效
			//HALF	半月生效
			//COMM	次月生效
			FIRST_PAYMENT_MAP.put("全月", "ALLM");
			FIRST_PAYMENT_MAP.put("半月", "HALF");
			FIRST_PAYMENT_MAP.put("次月", "COMM");
		}
		
		public static Map<String,String> PRODUCT_BRAND = new HashMap<String,String>();
		static{
			//商品大类    20000号卡  20001上网卡  20002合约机  20003裸机  69060000增值业务  69070000配件
			//网别 2G 3G 4G
			/**
			 *  网别2G，号卡、号卡合约、合约机大类的
				对应的产品品牌都是2G手机卡(2GPH)
				
				网别3G，号码、号卡合约、合约机大类的
				对应的产品品牌都是3G手机卡(3GPH)
				
				网别3G，上网卡大类的
				对应的产品品牌为3G上网卡(3GWK）
				
				网别4G，号码、号卡合约、合约机大类的
				对应的产品品牌为4G手机卡(4GPH)
			*/
			PRODUCT_BRAND.put("2G20000", "2GPH");
			PRODUCT_BRAND.put("2G20002", "2GPH");
			PRODUCT_BRAND.put("3G20000", "3GPH");
			PRODUCT_BRAND.put("3G20002", "3GPH");
			PRODUCT_BRAND.put("3G20001", "3GWK");
			PRODUCT_BRAND.put("4G20000", "4GPH");
			PRODUCT_BRAND.put("4G20002", "4GPH");
		}
		
		public static Map<String,String> PRO_TYPE = new HashMap<String,String>();
		static{
			/* 订单类别
			 * Z0:联通合约机
			   Z1:3G号卡
			   Z2:定制机裸机
			   Z3:社会机合约机
			   Z4:社会机裸机
			   Z5:2G号卡
			   Z7:上网卡【不含卡体】
			   Z8:上网卡【含卡体】
			   Z9:增值业务
			 */
			PRO_TYPE.put("69030101","Z0");
			PRO_TYPE.put("69030102","Z0");
			
			PRO_TYPE.put("69010203","Z1");
			PRO_TYPE.put("69010101","Z1");
			PRO_TYPE.put("69010103", "Z1");
			
			PRO_TYPE.put("69050100","Z2");
			
			PRO_TYPE.put("69030203","Z3");
			PRO_TYPE.put("69030202","Z3");
			
			PRO_TYPE.put("69050200","Z4");
			
			PRO_TYPE.put("69020100","Z7");
			PRO_TYPE.put("69020200","Z7");
			
			PRO_TYPE.put("69040000","Z8");  
			
		}
	
	public static void packageGoodsParam(OuterItem o,Map<String,String> param){
		
		o.setPhone_deposit(StringUtil.isEmpty(param.get("deposit_fee"))?"0":param.get("deposit_fee"));//活动预存款	单位元
		
		String product_id = param.get("product_id");//i.getOuterIid();//String.valueOf(i.getNumIid());
		//product_id = "1";
		logger.info("product_id:="+product_id);
		o.setProduct_id(product_id);//产品ID
		o.setGoods_id(param.get("goods_id"));
		/**
		 * 规格数所
		 */
		o.setAct_protper(StringUtil.isEmpty(param.get("package_limit"))?"0":param.get("package_limit")); //合约期限  12月、18月、24月、36月、48月
		/**
		 * 规格数所
		 */
		o.setAtive_type(StringUtil.isEmpty(param.get("ative_type"))?"":param.get("ative_type")); //字典：5购机送费、4存费送机、3存费送费 --合约类型
		/**
		 * 规格数所
		 */
		o.setBrand_name(param.get("brand_name")); //品牌
		/**
		 * 规格数所
		 */
		o.setBrand_number(param.get("brand_number")); //品牌编码
		/**
		 * 规格数所
		 */
		o.setColor_code(StringUtil.isEmpty(param.get("color_code"))?"":param.get("color_code"));//颜色编码
		o.setColor_name(StringUtil.isEmpty(param.get("color_name"))?"":param.get("color_name"));
		/**
		 * 规格数所
		 */
		o.setIs_iphone_plan(param.get("is_iphone_plan"));//是否iphone套餐	字典：0否，1是   没有
		/**
		 * 规格数所
		 */
		o.setIs_liang(param.get("is_liang"));//是否靓号	字典：0否，1是 --待确认
		/**
		 * 规格数所
		 */
		o.setIs_loves_phone(StringUtil.isEmpty(param.get("is_loves_phone"))?"0":param.get("is_loves_phone")); //是否情侣号码	字典：0否，1是 --默认0
		/**
		 * 规格数所
		 */
		o.setIs_stock_phone(StringUtil.isEmpty(param.get("is_stock_phone"))?"0":param.get("is_stock_phone"));//是否库存机	字典：0否，1是 --终端属性
		/**
		 * 规格数所
		 */
		o.setIsgifts(String.valueOf(param.get("isgifts")));//是否赠品	字典：0否，1是
		/**
		 * 规格数所
		 */
		o.setModel_code(param.get("model_code"));//机型编码	　---如果要送总部开户，就要对应总部的编码 如果不送总部，就为空。待局方确认
		/**
		 * 规格数所
		 */
		o.setModel_name(param.get("model_name"));//机型名称
		
		o.setOut_plan_id_bss(param.get("out_plan_id_bss"));//BSS套餐编码	BSS套餐编码 --待局方确认
		o.setOut_plan_id_ess(param.get("out_plan_id_ess"));//总部套餐编码	总部套餐编码 ---如果要送总部开户，就要对应总部的编码 如果不送总部，就为空。待局方确认
		
		/**
		 * 规格数所
		 */
		o.setPlan_title(param.get("goods_name"));//套餐名称wt.getPlan_title();	　商品名称  param.get("goods_name")
		
		o.setPro_name(param.get("goods_name"));//产品名称	　商品名称
		
		/**
		 * 规格数所
		 */
		o.setPro_type(param.get("product_type"));//产品类型
		o.setProduct_net(param.get("product_net"));//产品网别	　2G、3G --填空
		/**
		 * 规格数所
		 */
		o.setSpecificatio_nname(StringUtil.isEmpty(param.get("specification_name"))?"":param.get("specification_name"));//型号名称
		/**
		 * 规格数所
		 */
		o.setSpecification_code(StringUtil.isEmpty(param.get("specification_code"))?"":param.get("specification_code"));//型号编码	　---如果要送总部开户，就要对应总部的编码 如果不送总部，就为空。待局方确认
		o.setType_id(param.get("type_id"));
		if(!StringUtil.isEmpty(o.getPro_type()))
			o.setTid_category(PRO_TYPE.get(o.getPro_type()));
		
		/**
		 * 订单明细扩展属性
		 */
		Map extMap = new HashMap();
		o.setExtMap(extMap);
		
		//商品下面的可选包信息 --UR:185009
		o.getExtMap().put("customerization_offer_str", 
				StringUtil.isEmpty(param.get("customerization_offer_str")) ? ""
						: param.get("customerization_offer_str"));
		
	}
	
	/**
	 * 部份查询，高性能
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param tid
	 * @return
	 * @throws ApiException
	 */
	public static TradeGetResponse getTrade(long tid,String json) throws Exception{
		TradeGetRequest req=new TradeGetRequest();
		req.setFields(TRADE_FIELD);
		req.setTid(tid);
		TradeGetResponse response = (TradeGetResponse) TaoBaoHttpClientUtils.execute(req, json);
		return response;
	}
	
	/**
	 * 网厅垂直信息查询接口   暂时没有权限
	 * @作者 MoChunrun
	 * @创建日期 2014-3-25 
	 * @param tids
	 * @return
	 * @throws ApiException 
	 */
	public static TradeWtverticalGetResponse getTradeWtvertical(String tids,String json) throws Exception{
		TradeWtverticalGetRequest req=new TradeWtverticalGetRequest();
		req.setTids(tids);
		TradeWtverticalGetResponse response = (TradeWtverticalGetResponse) TaoBaoHttpClientUtils.execute(req, json);
		return response;
	}
	
	/**
	 * 查询全部信息
	 * @作者 MoChunrun
	 * @创建日期 2014-4-14 
	 * @param tid
	 * @param appkey
	 * @param secret
	 * @param sessionKey
	 * @param url
	 * @throws ApiException
	 */
	public static TradeFullinfoGetResponse getFullTrade(long tid,String appkey,String secret,String sessionKey,String url) throws ApiException{
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
		req.setFields("seller_nick,buyer_nick,title,type,created,tid,seller_rate,buyer_flag,buyer_rate,status,payment,adjust_fee,post_fee,total_fee,pay_time,end_time,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,buyer_memo,seller_memo,alipay_no,alipay_id,buyer_message,pic_path,num_iid,num,price,buyer_alipay_no,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,seller_flag,seller_alipay_no,seller_mobile,seller_phone,seller_name,seller_email,available_confirm_fee,has_post_fee,timeout_action_time,snapshot_url,cod_fee,cod_status,shipping_type,trade_memo,is_3D,buyer_email,buyer_area,trade_from,is_lgtype,is_force_wlb,is_brand_sale,buyer_cod_fee,discount_fee,seller_cod_fee,express_agency_fee,invoice_name,service_orders,credit_cardfee,step_trade_status,step_paid_fee,mark_desc,has_yfx,yfx_fee,yfx_id,yfx_type,trade_source,eticket_ext,send_time,is_daixiao,is_part_consign,arrive_interval,arrive_cut_time,consign_interval,orders");
		req.setTid(tid);
		TradeFullinfoGetResponse response = client.execute(req , sessionKey);
		return response;
	}
	
	public RegionsGetResp getRegion(RegionsGetReq req) {	
		ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ECSORD);
		RegionsGetResp resp = client.execute(req, RegionsGetResp.class);
		return resp;
	}
	
	public void insertOuterError(OuterError ng){
		
	}

	public IOutterECSTmplManager getOutterStdTmplManager() {
		return outterStdTmplManager;
	}

	public void setOutterStdTmplManager(IOutterECSTmplManager outterStdTmplManager) {
		this.outterStdTmplManager = outterStdTmplManager;
	}

	/*public INumberService getNumberService() {
		return numberService;
	}

	public void setNumberService(INumberService numberService) {
		this.numberService = numberService;
	}*/

}
