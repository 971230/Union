package com.ztesoft.net.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.jsonserver.depositpojo.DepositFeeInfo;
import com.ztesoft.net.server.jsonserver.depositpojo.DepositGoodsInfo;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallOrderDeposit;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.resp.DevelopOrderResp;
import com.ztesoft.orderstd.service.IOrderStandingManager;
import com.ztesoft.util.DevelopChannelUtil;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.req.DepositOrderStandardReq;
import params.resp.DepositOrderStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

public class DepositOrderUtil {

	public static final String SOURCE_FROM_ZHWQ = "10067";// 浙江智慧沃企
	private static final String LOCAL_CHARSET = "utf-8";
	private static IOrderServiceLocal localGoodServices;
	private static Logger logger = Logger.getLogger(DepositOrderUtil.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static List<String> skuParamCodes = new ArrayList<String>(); // 必填的货品参数param_code
	private static List<String> skuParamNames = new ArrayList<String>(); // 必填的货品参数param_name
	private static SDBUtils dbUtils = null;
	private static IOrderstdService orderstdService;
	private static String newOrderPhoneNum;// 作废订单时记录新单的号码---zengxianlian
	private static IOrderStandingManager orderStandingManager;
	private static IGoodsService goodServices;

	// 初始化skuParamCodes,skuParamNames
	static {
		skuParamCodes.add("acc_nbr");
		skuParamNames.add("用户号码");
		skuParamCodes.add("is_old");
		skuParamNames.add("是否老用户");
		skuParamCodes.add("if_love_no");
		skuParamNames.add("是否情侣号");
		skuParamCodes.add("net_type");
		skuParamNames.add("网别");
		skuParamCodes.add("is_goodno");
		skuParamNames.add("靓号");
		skuParamCodes.add("bill_type");
		skuParamNames.add("账户付费方式");
		skuParamCodes.add("card_type");
		skuParamNames.add("卡类型");
		skuParamCodes.add("guarantor");
		skuParamNames.add("担保人");
		skuParamCodes.add("bill_mail_type");
		skuParamNames.add("账单寄送方式");
	}

	public static void initBean() {
		localGoodServices = SpringContextHolder.getBean("orderStdServiceLocal");
		outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
		orderstdService = SpringContextHolder.getBean("orderstdService");
		dbUtils = SpringContextHolder.getBean("sdbUtils");
		orderStandingManager = SpringContextHolder.getBean("orderStandingManager");
		goodServices = SpringContextHolder.getBean("goodServices");

	}


	
    // 获取归集系统请求报文
	private static String getReqContent(String co_id, boolean is_exception) {
		String content = "";
		OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
		if (is_exception) {
			orderQueue = CommonDataFactory.getInstance().getOrderQueueFor(null, co_id);
		} else {
			orderQueue = CommonDataFactory.getInstance().getOrderQueue(null, co_id);
		}
		if (orderQueue != null) {
			content = orderQueue.getContents();
		}
		return content;
	}




	public static String getNewOrderPhoneNum() {
		return newOrderPhoneNum;
	}

	public static void setNewOrderPhoneNum(String newOrderPhoneNum) {
		DepositOrderUtil.newOrderPhoneNum = newOrderPhoneNum;
	}


	public static DepositOrderStandardResp orderDepositStandardValidate(DepositOrderStandardReq req) throws Exception {
		initBean();
		DepositOrderStandardResp resp = new DepositOrderStandardResp();
		MallOrderDeposit mallOrderDeposit = new MallOrderDeposit();// json转map的实体对象
		DevelopOrderResp developOrderResp = null;
		Map extMap = new HashMap<String, String>();
		List<Outer> out_order_List = new ArrayList<Outer>();
		Outer out_order = new Outer();
		String returnValue = "";
		String req_content = getReqContent(req.getBase_co_id(), req.isIs_exception());
		if (StringUtil.isEmpty(req_content)) {
			resp.setError_msg("未获取到请求报文");
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 1.json转化为map对象
		mallOrderDeposit = MallUtils.jsonMallOrderDeposit(req_content);

		// 检查必填项
		// 2.注意所有金额都在checkMallOrder方法里由厘转换为元
		String checkResult = checkMallOrderDeposit(mallOrderDeposit);
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kgDeposit = cacheUtil.getConfigInfo("channel_convert_kg_deposit");
		//收单发展点发展人值判断
        if("1".equals(kgDeposit) && StringUtil.isEmpty(checkResult)){
        	DevelopChannelUtil developChannelUtil = new DevelopChannelUtil();
        	//渠道转换 1.goodId  2.来源   3.发展点  4.发展人
        	developOrderResp=developChannelUtil.getDevelop(mallOrderDeposit.getProd_offer_code(), mallOrderDeposit.getSource_system_type(), null, null);
        	if (null != developOrderResp){
				if (!StringUtil.isEmpty(developOrderResp.getMsg())) {
					resp.setError_msg(developOrderResp.getMsg());
					resp.setError_code(ConstsCore.ERROR_FAIL);
					return resp;
				}
			}
        }
		if (!StringUtil.isEmpty(checkResult)) {
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		//3.把json参数set对Outer中
		String cancelMsg = setdepositOrdHandel(mallOrderDeposit, out_order, extMap, developOrderResp);
		if (!StringUtil.isEmpty(cancelMsg)) {
			resp.setError_msg(cancelMsg);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		out_order_List.add(out_order);
		resp.setOut_order_List(out_order_List);
		resp.setError_code(ConstsCore.ERROR_SUCC);
		return resp;
	}
	
	/**
	 * 押金json参数中必填项校验
	 * 
	 * @param mallOrder
	 * @return
	 */
	private static String checkMallOrderDeposit(MallOrderDeposit mallOrderDeposit) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer stringBuffer = new StringBuffer();

		// 序列号
		if (MallUtils.isEmpty(mallOrderDeposit.getSerial_no())) {
			stringBuffer.append("缺少必填字段:serial_no.");
		}
		// 时间
		if (MallUtils.isNotEmpty(mallOrderDeposit.getCreate_time())) {
			try {
				Date d = format.parse(mallOrderDeposit.getCreate_time());
				// pay_time由yyyymmddhh24miss转换为yyyy-mm-dd hh24:mi:ss
				mallOrderDeposit.setCreate_time(MallUtils.strFormatDate(d));
			} catch (Exception e) {
				stringBuffer.append("create_time值不正确.");
			}
		} else {
			stringBuffer.append("缺少必填字段:create_time.");
		}
		// 发起方系统标识
		if (MallUtils.isEmpty(mallOrderDeposit.getSource_system())) {
			stringBuffer.append("缺少必填字段:source_system.");
		} else {
			if (!(dbUtils.checkFieldValue("source_system", mallOrderDeposit.getSource_system()))) {
				stringBuffer.append("source_system未按协议取值.");
			}
		}
		// 发起方系统标识分类
		if (MallUtils.isEmpty(mallOrderDeposit.getSource_system_type())) {
			stringBuffer.append("缺少必填字段:source_system_type.");
		} else {
			if (!(dbUtils.checkFieldValue("source_from", mallOrderDeposit.getSource_system_type()))) {
				stringBuffer.append("source_system_type未按协议取值.");
			}
		}
		// 外系统的订单号
		if (MallUtils.isEmpty(mallOrderDeposit.getOut_order_id())) {
			stringBuffer.append("缺少必填字段:out_order_id.");
		}
		// 订单归属省份
		if (MallUtils.isEmpty(mallOrderDeposit.getOrder_province_code())) {
			stringBuffer.append("缺少必填字段:order_province_code .");
		} 
		// 订单归属地市
		if (MallUtils.isEmpty(mallOrderDeposit.getOrder_city_code())) {
			stringBuffer.append("缺少必填字段:order_city_code");
		}
		// 业务号码
		if (MallUtils.isEmpty(mallOrderDeposit.getService_num())) {
			stringBuffer.append("缺少必填字段:service_num.");
		} 
		// 商品编码
		if (MallUtils.isEmpty(mallOrderDeposit.getProd_offer_code())) {
			stringBuffer.append("缺少必填字段:prod_offer_code.");
		}
		// 商品名称
		if (MallUtils.isEmpty(mallOrderDeposit.getProd_offer_name())) {
			stringBuffer.append("缺少必填字段:prod_offer_name.");
		} 
		// 是否收费
		if (MallUtils.isEmpty(mallOrderDeposit.getIs_pay())) {
			stringBuffer.append("缺少必填字段:is_pay.");
		} 
//		// 支付类型为“积分支付（JFZF）”时，订单层“兑换积分”必填
//		if ("JFZF".equals(mallOrder.getPay_type()) && MallUtils.isEmpty(mallOrder.getOrder_points())) {
//			stringBuffer.append("缺少必填字段:order_points.");
//		}
//		if ("JFZF".equals(mallOrder.getPay_type()) && MallUtils.isEmpty(mallOrder.getPoints_user())) {
//			stringBuffer.append("缺少必填字段:points_user.");
//		}
//		// 支付类型为在线支付时，支付时间必填
//		if ("ZXZF".equals(mallOrder.getPay_type()) && MallUtils.isEmpty(mallOrder.getPay_time())) {
//			stringBuffer.append("在线支付缺少必填字段:pay_time.");
//		}
//		// 支付方式
//		if (MallUtils.isEmpty(mallOrder.getPayment_type())) {
//			stringBuffer.append("缺少必填字段:payment_type.");
//		} else {
//			if (!(dbUtils.checkFieldValue("payment_type", mallOrder.getPayment_type()))) {
//				stringBuffer.append("payment_type未按协议取值.");
//			}
//		}
		// 押金项目
		if (MallUtils.isEmpty(mallOrderDeposit.getFee_type())) {
			stringBuffer.append("缺少必填字段:fee_type.");
		}
		// 应收费用
		if (MallUtils.isEmpty(mallOrderDeposit.getOrig_fee())) {
			stringBuffer.append("缺少必填字段:orig_fee.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrderDeposit.getOrig_fee());
				mallOrderDeposit.setOrig_fee(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("orig_fee值不正确.");
			}
		}
		// 减免费用
		if (MallUtils.isEmpty(mallOrderDeposit.getRelief_fee())) {
			stringBuffer.append("缺少必填字段:relief_fee.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrderDeposit.getRelief_fee());
				mallOrderDeposit.setRelief_fee(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("relief_fee值不正确.");
			}
		}
		// 实收费用
		if (MallUtils.isEmpty(mallOrderDeposit.getReal_fee())) {
			stringBuffer.append("缺少必填字段:real_fee.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrderDeposit.getReal_fee());
				mallOrderDeposit.setReal_fee(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("real_fee值不正确.");
			}
		}
		// 费用规则id
		if (MallUtils.isEmpty(mallOrderDeposit.getFee_rule_id())) {
			stringBuffer.append("缺少必填字段:fee_rule_id.");
		}
		// 办理操作员
		if (MallUtils.isEmpty(mallOrderDeposit.getDeal_operator())) {
			stringBuffer.append("缺少必填字段:deal_operator.");
		}
		// 办理操作点
		if (MallUtils.isEmpty(mallOrderDeposit.getDeal_office_id())) {
			stringBuffer.append("缺少必填字段:deal_office_id.");
		} 
		
		if (MallUtils.isNotEmpty(mallOrderDeposit.getCan_cancel_date())) {
			try {
				Date d = format.parse(mallOrderDeposit.getCan_cancel_date());
				// can_cancel_date由yyyymmddhh24miss转换为yyyy-mm-dd hh24:mi:ss
				mallOrderDeposit.setCan_cancel_date(MallUtils.strFormatDate(d));
			} catch (Exception e) {
				stringBuffer.append("can_cancel_date值不正确.");
			}
		}
		
		return stringBuffer.toString();
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String setdepositOrdHandel(MallOrderDeposit mallOrderDeposit, Outer outer, Map extMap, DevelopOrderResp developOrderResp) throws Exception {
		
		String result_msg="";
		// 序列号 serial_no
		outer.setPayplatformorderid(MallUtils.getValues(mallOrderDeposit.getSerial_no()));
		// 时间 time
		outer.setGet_time(mallOrderDeposit.getCreate_time());
		// 下单时间
		outer.setTid_time(mallOrderDeposit.getCreate_time());
		// 发起方系统标识 source_system
		outer.setOrder_channel(mallOrderDeposit.getSource_system());
		//发起方系统标识分类
		outer.setOrder_from(mallOrderDeposit.getSource_system_type());
		outer.setPlat_type(mallOrderDeposit.getSource_system_type());
		// 外系统的订单号
		outer.setOut_tid(MallUtils.getValues(mallOrderDeposit.getOut_order_id()));
		// 订单归属省份编码 order_provinc_code
		outer.setOrder_provinc_code(MallUtils.getValues(mallOrderDeposit.getOrder_province_code()));
		outer.setProvinc_code(MallUtils.getValues(mallOrderDeposit.getOrder_province_code()));
		outer.setProvince(MallUtils.getValues(mallOrderDeposit.getOrder_province_code()));
		// 订单归属地市编码 order_city_code
		outer.setOrder_city_code(MallUtils.getValues(mallOrderDeposit.getOrder_city_code()));
		outer.setCity_code(MallUtils.getValues(mallOrderDeposit.getOrder_city_code()));
		outer.setCity(MallUtils.getValues(mallOrderDeposit.getOrder_city_code()));
		// 订单归属县分
		if (MallUtils.isNotEmpty(mallOrderDeposit.getOrder_county_code())) {
			extMap.put("order_county_code", mallOrderDeposit.getOrder_county_code());
		}
		// 业务号码
		if (MallUtils.isNotEmpty(mallOrderDeposit.getService_num())) {
			extMap.put("service_num", mallOrderDeposit.getService_num());
			extMap.put("phone_num", mallOrderDeposit.getService_num());
		}
		// 商品编码
		if (MallUtils.isNotEmpty(mallOrderDeposit.getProd_offer_code())) {
			extMap.put("prod_offer_code", mallOrderDeposit.getProd_offer_code());
		}
		// 商品名称
		if (MallUtils.isNotEmpty(mallOrderDeposit.getProd_offer_name())) {
			extMap.put("prod_offer_name", mallOrderDeposit.getProd_offer_name());
		}
		// 是否收费
		if(StringUtil.isEmpty(mallOrderDeposit.getIs_pay())||StringUtil.equals("0", mallOrderDeposit.getIs_pay())){
			outer.setPay_status("0");
		}else{
			outer.setPay_status("1");
		}
		//配送方式
		outer.setSending_type("WX");
		//OrderOrigFee  订单应收总价
		outer.setOrder_totalfee(String.valueOf(mallOrderDeposit.getOrig_fee()));
		outer.setOrder_origfee(String.valueOf(mallOrderDeposit.getOrig_fee()));
		//OrderRealFee  订单实收总价
		outer.setOrder_realfee(String.valueOf(mallOrderDeposit.getReal_fee()));
		//OrderReliefFee  订单减免金额
		outer.setDiscountValue(String.valueOf(mallOrderDeposit.getRelief_fee()));
		// 支付类型 pay_type
		outer.setPaytype(MallUtils.getValues(mallOrderDeposit.getPay_type()));
		// 支付方式 payment_type
		outer.setPay_method(MallUtils.getValues(mallOrderDeposit.getPay_method()));
		if (MallUtils.isNotEmpty(mallOrderDeposit.getDeal_operator())) {
			extMap.put("out_operator", mallOrderDeposit.getDeal_operator());
		}
		if (MallUtils.isNotEmpty(mallOrderDeposit.getDeal_office_id())) {
			extMap.put("out_office", mallOrderDeposit.getDeal_office_id());
		}
		// 订单备注
//		outer.setService_remarks(MallUtils.getValues(mallOrderDeposit.getOrder_remark()));
		outer.setReserve2("13");
		outer.setBuyer_message(MallUtils.getValues(mallOrderDeposit.getOrder_remark()));
		List<DepositFeeInfo> defeeAll = new ArrayList<DepositFeeInfo>();
		DepositFeeInfo defee = new DepositFeeInfo();
		// 可退押金时间
		defee.setCan_cancel_date(mallOrderDeposit.getCan_cancel_date());
		// 费用规则id
		defee.setFee_rule_id(mallOrderDeposit.getFee_rule_id());
		//  订单应收总价
		defee.setOrigFee(mallOrderDeposit.getOrig_fee());
		// 订单实收总价
		defee.setRealFee(mallOrderDeposit.getReal_fee());
		// 订单减免金额
		defee.setReliefFee(mallOrderDeposit.getRelief_fee());
		// 押金项目
		defee.setFee_type(mallOrderDeposit.getFee_type());
		
		defeeAll.add(defee);
		
		if(null != defeeAll && defeeAll.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(defeeAll);
			extMap.put("feeinfo", jsonArray.toString());
		}
		
		result_msg = setDepositInfo(mallOrderDeposit,outer,extMap);
		if (!"".equals(result_msg)) {
			return result_msg;
		}
		
		outer.setExtMap(extMap);

		return result_msg;
	}
	
	/**
	 * 把goodsinfo数据set对java对象中
	 * @param groupOrder
	 * @param outer
	 * @return
	 */
	private static String setDepositInfo(MallOrderDeposit mallOrderDeposit , Outer outer , Map extMap){
		String checkResult = "";
		List<OuterItem> itemList  = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();
		
		List<DepositGoodsInfo> goodsInfos = new ArrayList<DepositGoodsInfo>();
		DepositGoodsInfo goodsInf = new DepositGoodsInfo();
		goodsInf.setProd_offer_code(mallOrderDeposit.getProd_offer_code());
		goodsInf.setProd_offer_name(mallOrderDeposit.getProd_offer_name());
		goodsInfos.add(goodsInf);
		for(DepositGoodsInfo goodsInfo : goodsInfos) {
			OuterItem item = new OuterItem();
			
			//GoodsNum  订单中商品的总数量
			item.setPro_num(0);
			//GoodsOrigFee  商品应收
//			item.setSell_price(Double.parseDouble(mallOrderDeposit.getOrig_fee()));
//			//GoodsRealFee  商品实收
//			item.setPro_origfee(Double.parseDouble(mallOrderDeposit.getReal_fee()));
			
			if(null != goodsInfo){
				JSONObject jsonObj = JSONObject.fromObject(goodsInfo);
				extMap.put("goodsInfo", jsonObj);
			}
			
			String outer_sku_id = goodsInfo.getProd_offer_code();
			
			String goods_id = goodsInfo.getProd_offer_code();
			//查看商品是否存在
		
			Map<String, String> param = new HashMap<String, String>();
			GoodsGetReq req = new GoodsGetReq();
			req.setGoods_id(goods_id);//商品ID
			GoodsGetResp resp = null;
			try {
				resp = goodServices.getGoods(req);
				
				//获取商品对应的活动编码
				String s_user_type=orderAttr.getIs_old();
				int user_type=-1;
				if(null != s_user_type && "1".equalsIgnoreCase(s_user_type))
					user_type=3;//old user,referenced EcsOrderConsts.java
				else 
					user_type=2;//new user
				//匹配原則:按照商城(總部商城10003),下單的時間是否在活動有效期範圍內(具體到時分秒),
				//活動類型(直降,溢價),活動是啓用的
				//用戶類型(必須有的是新/老用戶,以及訂單中的用戶類型)
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
				String sql = "select c.goods_id, c.pmt_id, d.pmta_id, d.pmt_type, d.pmt_solution, " +
						" e.name,e.pmt_code, e.begin_time, e.end_time, e.status_date, f.org_id from es_promotion d," +
						" es_promotion_activity e, es_pmt_goods c, es_activity_co f where " +
						" c.goods_id = ? and c.pmt_id = d.pmt_id" +
						" and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10003' and f.status='1' " +
						" and (e.region = '"+province_region+"' or instr(e.region,?)>0) and " +
						" to_date(?,'yyyy-mm-dd hh24:mi:ss') between " +
						" e.begin_time and e.end_time and f.source_from = '"+ManagerUtils.getSourceFrom()+"' and f.source_from = d.source_from " +
						" and f.source_from = e.source_from and f.source_from = c.source_from " +
						" and d.pmt_type in  ('006','011') and e.enable=1 and e.user_type in (1,?) order by e.status_date";
				logger.info(sql);
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				List activityList = support.queryForList(sql,resp.getData().get("goods_id").toString(),mallOrderDeposit.getOrder_city_code(),mallOrderDeposit.getCreate_time(),user_type);
				
				if(null != activityList && activityList.size() > 0){
					StringBuffer strBuffer = new StringBuffer();
					for (int i = 0; i < activityList.size(); i++) {
						Map m = (Map)activityList.get(i);
						strBuffer.append(m.get("pmt_code").toString()).append(",");
					}					
					String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
					outer.setReserve9(str);
					
					logger.info("总部订单:"+outer.getOut_tid()+"匹配到的活动有:"+str);					
					
//					com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(outer.getOut_tid(), str);
					extMap.put("proactivity", str);
					
					//屬於給es_outer_accept的reserve9做的限制,因爲該表的reserve9字段爲varchar2(128)
					/*if(str.length() > 128){
						outer.setReserve9(str.substring(0,128));
					}else{
						outer.setReserve9(str);
					}*/
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				OuterError ng = new OuterError(goods_id, outer_sku_id, outer_sku_id, null, mallOrderDeposit.getSource_system(), mallOrderDeposit.getOut_order_id(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + mallOrderDeposit.getOut_order_id() +",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
				continue;
			}
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				setPackageGoodsParam1(item,param);
				itemList.add(item);
				
			}else {
				OuterError ng = new OuterError(goods_id, outer_sku_id, outer_sku_id, null, mallOrderDeposit.getSource_system(), mallOrderDeposit.getOut_order_id(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + mallOrderDeposit.getOut_order_id() +",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
			}
			
		}
		outer.setItemList(itemList);
		outer.setLocalObject(orderAttr);
		return checkResult;
	}
	
	/**
	 * 把产品参数添加在OuterItem对象中
	 * @param item
	 * @param param
	 */
	private static void setPackageGoodsParam1(OuterItem item , Map<String, String> param){
		
//		活动预存款 单位元
		if (MallUtils.isEmpty(param.get("deposit_fee"))) {
			item.setPhone_deposit("0");
		} else {
			item.setPhone_deposit(param.get("deposit_fee"));
		}
//		产品ID
		item.setProduct_id(param.get("product_id"));
		item.setGoods_id(param.get("goods_id"));
		
//		合约期限 12月、18月、24月、36月、48月
		if (MallUtils.isEmpty(param.get("package_limit"))) {
			item.setAct_protper("0");
		} else {
			item.setAct_protper(param.get("package_limit"));
		}
		
//		字典：5购机送费、4存费送机、3存费送费 --合约类型
		if (MallUtils.isNotEmpty(param.get("ative_type"))) {
			item.setAtive_type(param.get("ative_type"));
		}
		
//		品牌
		if (MallUtils.isNotEmpty(param.get("brand_name"))) {
			item.setBrand_name(param.get("brand_name"));
		}
		
//		品牌编码
		if (MallUtils.isNotEmpty(param.get("brand_number"))) {
			item.setBrand_number(param.get("brand_number"));
		}
//		颜色编码
		if (MallUtils.isNotEmpty(param.get("color_code"))) {
			item.setColor_code(param.get("color_code"));
		}
		if (MallUtils.isNotEmpty(param.get("color_name"))) {
			item.setColor_name(param.get("color_name"));
		}
//		是否iphone套餐 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("is_iphone_plan"))) {
			item.setIs_iphone_plan(param.get("is_iphone_plan"));
		}
//		是否靓号 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("is_liang"))) {
			item.setIs_liang(param.get("is_liang"));
		}
//		是否情侣号码 字典：0否，1是 --默认0
		if (MallUtils.isNotEmpty(param.get("is_loves_phone"))) {
			item.setIs_loves_phone(param.get("is_loves_phone"));
		}
//		是否库存机 字典：0否，1是 --终端属性
		if (MallUtils.isNotEmpty(param.get("is_stock_phone"))) {
			item.setIs_stock_phone(param.get("is_stock_phone"));
		}
//		是否赠品 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("isgifts"))) {
			item.setIsgifts(param.get("isgifts"));
		}
//		机型编码
		if (MallUtils.isNotEmpty(param.get("model_code"))) {
			item.setModel_code(param.get("model_code"));
		}
//		机型名称
		if (MallUtils.isNotEmpty(param.get("model_name"))) {
			item.setModel_name(param.get("model_name"));
		}
//		BSS套餐编码 BSS套餐编码
		if (MallUtils.isNotEmpty(param.get("out_plan_id_bss"))) {
			item.setOut_plan_id_bss(param.get("out_plan_id_bss"));
		}
//		总部套餐编码 总部套餐编码
		if (MallUtils.isNotEmpty(param.get("out_plan_id_ess"))) {
			item.setOut_plan_id_ess(param.get("out_plan_id_ess"));
		}
//		套餐名称
		if (MallUtils.isNotEmpty(param.get("goods_name"))) {
			item.setPlan_title(param.get("goods_name"));
			item.setPro_name(param.get("goods_name"));
		}
//		产品类型
		if (MallUtils.isNotEmpty(param.get("product_type"))) {
			item.setPro_type(param.get("product_type"));
		}
//		产品网别
		if (MallUtils.isNotEmpty(param.get("product_net"))) {
			item.setProduct_net(param.get("product_net"));
		}
//		型号名称
		if (MallUtils.isNotEmpty(param.get("specification_name"))) {
			item.setSpecificatio_nname(param.get("specification_name"));
		}
//		型号编码
		if (MallUtils.isNotEmpty(param.get("specification_code"))) {
			item.setSpecification_code(param.get("specification_code"));
		}
		if (MallUtils.isNotEmpty(param.get("type_id"))) {
			item.setType_id(param.get("type_id"));
		}
		
	}
}
