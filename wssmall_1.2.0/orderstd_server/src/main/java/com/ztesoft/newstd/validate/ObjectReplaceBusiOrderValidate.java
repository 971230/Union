package com.ztesoft.newstd.validate;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.cache.CacheList;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.conf.CacheValues;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceBusiOrder;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceContactInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceCustInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceFeeInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceGoodsInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplaceObjectInfo;
import com.ztesoft.net.server.jsonserver.objreplacepojo.ObjectReplacePayInfo;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.newstd.common.CommonData;
import com.ztesoft.newstd.util.JsonUtil;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.req.ObjectReplaceStandardReq;
import params.resp.ObjectReplaceStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IGoodsService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

public class ObjectReplaceBusiOrderValidate {

	private static Logger logger = Logger.getLogger(ObjectReplaceBusiOrderValidate.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static IGoodsService goodServices;
	private static SDBUtils dbUtils = null;
//	private static INetCache cache = CacheFactory.getCacheByType("");
	private final static int space = Const.CACHE_SPACE_ORDERSTD;
	private final static int EXPIRE_TIME = Const.CACHE_TIME_ORDERSTD;
	
	public static void initBean() {
		outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
		goodServices = SpringContextHolder.getBean("goodServices");
		dbUtils = SpringContextHolder.getBean("sdbUtils");
	}

	public static ObjectReplaceStandardResp orderStandardValidate(ObjectReplaceStandardReq req) throws Exception {
		initBean();
		ObjectReplaceStandardResp resp = new ObjectReplaceStandardResp();
		Map extMap = new HashMap<String, String>();
		Map orderMap = new HashMap();
//		String outJson = "";
		String checkResult = "";
//		boolean errorFlag = false;
		// 记录错单需要
//		String source_system = "ZB";
//		String out_tid = "";
		ObjectReplaceBusiOrder replaceBusiOrder = null;
		String inJson="";
		if(StringUtils.isEmpty(req.getReq_content())){//判断是否需要从订单队列中取数据
			 inJson = getReqContent(req.getBase_co_id(), req.isIs_exception());
			 req.setReq_content(inJson);
		}else{
			inJson=req.getReq_content();
		}
		
		if (StringUtils.isEmpty(inJson)) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未获取到请求报文");
			return resp;
		}
		// 1.json转换成对象
		replaceBusiOrder = MallUtils.jsonToReplaceBusiOrder(inJson);
//		out_tid = replaceBusiOrder.getMall_order_id();
//		source_system = replaceBusiOrder.getSource_system();
		// 检查参数格式是否正确
		// 注意所有金额都在checkCenterMallOrder方法里由厘转换为元
		checkResult = checkMobileBusiOrder(replaceBusiOrder);

		if (!StringUtils.isEmpty(checkResult)) {
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.把json参数set对Outer中
		List<Outer> out_order_List = new ArrayList<Outer>();
		Outer out_order = new Outer();
		out_order_List.add(out_order);

		String setValResult = setMobileBusiOrderValue(replaceBusiOrder, out_order, extMap);
		if (!StringUtils.isEmpty(setValResult)) {
			resp.setError_msg(setValResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("订单收单本地化校验成功");
		resp.setOut_order_List(out_order_List);
		return resp;
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

	/**
	 * json参数set到java对象中
	 * 
	 * @param centerMallOrder
	 * @param outer
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String setMobileBusiOrderValue(ObjectReplaceBusiOrder objectBusiMallOrder, Outer outer, Map extMap) {
		String checkResult = "";
		if (!StringUtil.isEmpty(objectBusiMallOrder.getService_num())) {// 业务号码
			extMap.put("phone_num", objectBusiMallOrder.getService_num());
		}
		extMap.put("is_pay", objectBusiMallOrder.getIs_pay());
		/**
		 * 补充信息,没传但订单系统必传
		 */
		// outer.setOrder_if_cancel("0");
		// 下单时间对应物理库的create_time
		outer.setGet_time(objectBusiMallOrder.getCreate_time());
		// 渠道类型 channel_mark Reserve2
		outer.setReserve2("13");
		// 接收方系统标识 receive_system Reserve0
		outer.setReserve0("10011");
		// 是否2G、3G升4G is_to4g Reserve1
		outer.setReserve1("0");
		// 订单类型 order_type
		/*
		 * 预售标识(0:预售) ZX update 2015-12-23 start select a.* from
		 * es_dc_public_dict_relation a where a.stype='order_type';
		 * type对应外系统5，订单系统07（预售单） type对应外系统1，订单系统03（订购，正常单）
		 */
		outer.setType("");// 码上购没传

		/* 预售标识(0:预售) ZX udpate 2015-12-23 end */
		// 订单状态 status
		outer.setStatus("1");
		// 是否已办理完成 is_deal
		extMap.put("is_deal", "0");

		ObjectReplaceContactInfo contactInfo = objectBusiMallOrder.getContact_info();
		// 买家标识 CustomerId
		extMap.put("uid", "");
		// ConsiEmail 收件人EMAIL
		extMap.put("ship_email", "");
		// OrderOperType 是否闪电送
		extMap.put("shipping_quick", "0");
		// 仓库名称 inventory_name
		extMap.put("inventory_name", "-1");
		// 所属用户 ssyh
		outer.setReserve7("");
		// 是否变更套餐
		extMap.put("is_change", "0");
		// 订单来源
		extMap.put("source_type", objectBusiMallOrder.getSource_system_type());
		// 订单接入类别
		extMap.put("regist_type", objectBusiMallOrder.getSource_system());
		
		ObjectReplaceObjectInfo object_info = objectBusiMallOrder.getObject_info();
		
		String operator_id = object_info.getDeal_operator();
		String office_id = object_info.getDeal_office_id();
		
		extMap.put("out_operator",operator_id);
		extMap.put("out_office", office_id);
		//费用列表
		List<ObjectReplaceFeeInfo> feeInfos = objectBusiMallOrder.getObject_info().getFee_list();
		
		if (null != feeInfos && feeInfos.size() > 0) {
			JSONArray jsonArray = JSONArray.fromObject(feeInfos);
			extMap.put("feeinfo", jsonArray.toString());
		} else {
			extMap.put("feeinfo", "");
		}
		

		if(null != object_info){
			extMap.put("broadinfo", JsonUtil.beanToJson(object_info));
		}
		
		
		// 取到客户信息节点
		ObjectReplaceCustInfo custInfo = objectBusiMallOrder.getCust_info();
		if (null != custInfo) {
			if (!StringUtil.isEmpty(custInfo.getCheck_type())) {// 认证类型
				extMap.put("checkType", custInfo.getCheck_type());
			}
			if (!StringUtil.isEmpty(custInfo.getIs_real_name())) {// 是否实名
				extMap.put("is_real_name", custInfo.getIs_real_name());
			}
			if (!StringUtil.isEmpty(custInfo.getCust_tel())) {// 号码
				// extMap.put("phone_num", custInfo.getCust_tel());
			}
			if (!StringUtil.isEmpty(custInfo.getCust_id())) {// 客户编码
				extMap.put("cust_id", custInfo.getCust_id());
			}
			if (!StringUtil.isEmpty(custInfo.getGroup_code())) {// 集团15位编码
				extMap.put("group_code", custInfo.getGroup_code());
			}
			// CustomerId 买家标识/买家ID
			outer.setBuyer_name(custInfo.getCustomer_name());
			// RegisterName 买家昵称
			outer.setBuyer_id(custInfo.getCustomer_name());
		}
		// if(!broad_map.isEmpty()){//宽带节点
		// }

		// ActiveNo 访问流水
		outer.setPayplatformorderid(objectBusiMallOrder.getSerial_no());
		
		CommonData.getData().getOrderTreeBusiRequest().getOrderBusiRequest().setSn(objectBusiMallOrder.getSerial_no());
		
		outer.setService_remarks(objectBusiMallOrder.getOrder_remark());// 客服备注
		// extMap.put("service_remarks", mobileBusiMallOrder.getOrder_remark());
		// ClientIP 买家登录IP
		// ConsiName 收件人姓名
		outer.setReceiver_name(contactInfo.getShip_name());

		// ConsiPhone 收件人联系电话
		outer.setReceiver_mobile(contactInfo.getShip_tel());
		// outer.setPhone(contactInfo.getShip_tel2());
		// BuyerMessage 买家留言
		outer.setBuyer_message(objectBusiMallOrder.getBuyer_message());
		// ConsiPostCode 邮政编码
		outer.setPost("");
		// ConsiPostAddress 邮寄地址
		outer.setAddress(contactInfo.getShip_addr());
		// 备注
		outer.setService_remarks(objectBusiMallOrder.getOrder_remark());
		// deliMode 配送方式
		// outer.setSending_type(deliverInfo.getDeli_mode());
		extMap.put("sending_type", "NO");
		extMap.put("shipping_time", "NOLIMIT");
		outer.setOut_tid(objectBusiMallOrder.getMall_order_id());// OrderId 订单编号
		outer.setTid_time(objectBusiMallOrder.getCreate_time());// OrderTime
																// 下单时间，YYYY-MM-DD
																// HH24:MI:SS
		outer.setOrder_provinc_code(objectBusiMallOrder.getOrder_prov_code());// OrderProvince
																				// 订单省份
		outer.setOrder_city_code(objectBusiMallOrder.getOrder_city_code());// OrderCity
																			// 订单地市
		extMap.put("district_code", objectBusiMallOrder.getOrder_county_code());// Todo:订单归属县分
		outer.setOrder_from(objectBusiMallOrder.getSource_system_type());// OrderSource
																			// 订单来源
		// OrderAccType 订单接入类别
		outer.setPlat_type(objectBusiMallOrder.getSource_system());
		outer.setOrderacctype(objectBusiMallOrder.getSource_system());
		outer.setOrder_channel(objectBusiMallOrder.getSource_system());
		// OrderAccDesc 订单接入说明
		// OutOrderId 外部订单编号，即父订单编码
		// OrderOrigFee 订单应收总价
		outer.setOrder_totalfee(String.valueOf(objectBusiMallOrder.getOrder_amount()));
		outer.setOrder_origfee(String.valueOf(objectBusiMallOrder.getOrder_amount()));
		// OrderRealFee 订单实收总价
		outer.setOrder_realfee(String.valueOf(objectBusiMallOrder.getPay_amount()));
		// OrderReliefFee 订单减免金额
		outer.setDiscountValue(String.valueOf(objectBusiMallOrder.getDiscount_amount()));
		// OrderReliefRes 订单减免原因
		extMap.put("OrderReliefRes", objectBusiMallOrder.getDiscount_reason());
		// OrigPostFee 应收邮寄费用
		// outer.setPost_fee(String.valueOf(deliverInfo.getOrig_post_fee()));

		// InvoiceNo 发票号码(默认空)
		// InvoiceTitle 发票抬头
		outer.setReserve8("");
		// InvoiceType 发票类型
		// 总部发票类型都是增值发票(2)
		outer.setInvoice_type(2);
		outer.setProvinc_code(objectBusiMallOrder.getOrder_prov_code());
		outer.setCity_code(objectBusiMallOrder.getOrder_city_code());
		outer.setArea_code(objectBusiMallOrder.getOrder_county_code());
		outer.setPost_fee("0");

		outer.setInvoice_print_type("3");

		// InvoiceDesc 发票内容
		extMap.put("invoice_group_content", "");

		// PayInfo 支付信息
		ObjectReplacePayInfo payInfo = objectBusiMallOrder.getPay_info();
		if (null != payInfo) {
			// PayType 支付类型
			outer.setPaytype(payInfo.getPay_type());
			if(StringUtil.isEmpty(objectBusiMallOrder.getIs_pay())||StringUtil.equals("0", objectBusiMallOrder.getIs_pay())){
				outer.setPay_status("0");
			}else{
				outer.setPay_status("1");
			}
			
			// PayWay 支付方式
			if (StringUtils.isEmpty(payInfo.getPay_method())) {
				if (StringUtils.equals(payInfo.getPay_type(), "SMSF")) {
					payInfo.setPay_method("ZERO");
				} else {
					payInfo.setPay_method(EcsOrderConsts.PAY_METHOD_ZFB);
				}
			}
			outer.setPay_method(payInfo.getPay_method());
			// PayFinTime 支付完成时间
			SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
			java.util.Date tDate = dateFormator.parse(com.ztesoft.net.framework.util.StringUtil.getTransDate(), new ParsePosition(0));
			dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
			outer.setPay_time(dateFormator.format(tDate));
			// PayStatus 支付状态
			outer.setPay_status(payInfo.getPay_status());
			// PayPlatFormOrderId 支付发起流水
			if (StringUtils.isNotEmpty(payInfo.getPay_sequ())) {
				String pay_id = payInfo.getPay_sequ() + "|" + payInfo.getPay_back_sequ();
				outer.setPayplatformorderid(pay_id);
			} else {
				outer.setPayplatformorderid("");
			}
			// PayProviderId 支付机构编码
			outer.setPayproviderid("");
			// PayProviderName 支付机构名称
			outer.setPayprovidername("");
			// PayChannelId 支付渠道编码
			outer.setPaychannelid("");
			// PayChannelName 支付渠道名称
			outer.setPaychannelname("");
			// 支付流水
		}

		// GoodsInfo 商品信息
		checkResult = setMobileBusiMallGoodsInfo(objectBusiMallOrder, outer, extMap);
		if (!"".equals(checkResult)) {
			return checkResult;
		}

		outer.setExtMap(extMap);
		return checkResult;
	}

	/**
	 * 把goodsinfo数据set对java对象中
	 * 
	 * @param centerMallOrder
	 * @param outer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static String setMobileBusiMallGoodsInfo(ObjectReplaceBusiOrder objectBusiMallOrder, Outer outer, Map extMap) {
		String checkResult = "";
		List<OuterItem> itemList = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();
		INetCache cache = CacheFactory.getCacheByType(CacheValues.CACHE_TYPE_EHCACHE);
		List<ObjectReplaceGoodsInfo> goodsInfos = objectBusiMallOrder.getGoods_info();
		for (ObjectReplaceGoodsInfo goodsInfo : goodsInfos) {
			OuterItem item = new OuterItem();
			if (!StringUtil.isEmpty(objectBusiMallOrder.getService_num())) {// 业务号码
				item.setPhone_num(objectBusiMallOrder.getService_num());
			}
			// GoodsNum 订单中商品的总数量
			item.setPro_num(1);

			if (null != goodsInfo) {
				JSONObject jsonObj = JSONObject.fromObject(goodsInfo);
				extMap.put("goodsInfo", jsonObj);
			}

			String goods_id = goodsInfo.getProd_code();

			ObjectReplaceCustInfo custInfo = objectBusiMallOrder.getCust_info();
			if (null != custInfo) {
				// CertType 证件类型
				item.setCert_type(custInfo.getCert_type());
				// CertNum 证件号码
				item.setCert_card_num(custInfo.getCert_num());
				// cert_nation 证件民资
				item.setCert_nation(custInfo.getCert_nation());
				// certi_sex 证件性别
				item.setCerti_sex(custInfo.getCert_sex());
				// CertAddr 证件地址
				item.setCert_address(custInfo.getCert_addr());
				outer.setCert_address(custInfo.getCert_addr());
				// CustomerName 号卡客户姓名
				item.setPhone_owner_name(custInfo.getCustomer_name());
				// CertLoseTime 证件失效时间
				/*
				 * SimpleDateFormat dateFormator = new
				 * SimpleDateFormat(DateUtil.DATE_FORMAT_5); java.util.Date
				 * tDate = dateFormator.parse(centerMallOrder.getCreate_time(),
				 * new ParsePosition(0)); dateFormator = new
				 * SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				 * centerMallOrder.setCreate_time(dateFormator.format(tDate));
				 */

				item.setCert_failure_time(custInfo.getCert_expire_date());
				// 证件失效时间
				item.setCert_eff_time(custInfo.getCert_effected_date());
				// 签发机关
				item.setCert_issuer(custInfo.getCert_issuedat());
				// 客户类型 CustomerType100：个人客户(联通)，不传时默认值 50：个人企业客户（小微）
				String CustomerType = custInfo.getCustomer_type() == null || "100".equals(custInfo.getCustomer_type()) ? "GRKH" : custInfo.getCustomer_type();
				// if (!StringUtil.isEmpty(custInfo.getCustomer_type()) &&
				// !"100".equals(custInfo.getCustomer_type())) {
				// CustomerType = "GRQYKH";
				// }
				extMap.put("CustomerType", CustomerType);
				// extMap.put("CustomerType", custInfo.getCustomer_type() ==
				// null ?
				// "100" : custInfo.getCustomer_type());
				// 客户生日
				extMap.put("birthday", custInfo.getCust_birthday());
			}
			// FirstMonBillMode 首月资费方式
			item.setFirst_payment("ALLM");// 首月月费接口没有

			// outer.setReserve1(reserve1);
			// ProductBrand 产品品牌
			orderAttr.setPro_brand("");
			orderAttr.setBill_type("10");
			orderAttr.setGuarantor("无");
			String package_sale = "0";

			orderAttr.setPackage_sale(package_sale);// 套包销售标记：package_sale 默认是0
													// －非套包销售
			// ProPacCode 产品包编码
			// extMap.put("ProPacCode",
			// goodsInfo.getBroad_info().getProduct_code());
			// ProPacDesc 产品包说明
			extMap.put("ProPacDesc", "");
			// GoodsName 商品名称
			extMap.put("GoodsName", goodsInfo.getProd_name());
			// package_sale 套包销售标记
			extMap.put("package_sale", package_sale);


			// 仓库编码 inventory_code
			item.setOut_house_id("-1");
			// }

			/* ZX add 2015-12-23 end */

			Map<String, String> param = new HashMap<String, String>();
			GoodsGetReq req = new GoodsGetReq();
			req.setGoods_id(goods_id);// 商品ID
			GoodsGetResp resp = null;
			try {
                String package_id = req.getPackage_id();
                String sn = req.getSn();
                String GOODSGETRESP_CACHE_KEY = "GOODSGETRESP_"+goods_id;
                resp = (GoodsGetResp) cache.get(space, GOODSGETRESP_CACHE_KEY);
                if (resp == null) {
                	resp = goodServices.getGoods(req);
                    cache.set(space, GOODSGETRESP_CACHE_KEY, resp ,EXPIRE_TIME);
                }
				// 获取商品对应的活动编码
				String s_user_type = orderAttr.getIs_old();
				int user_type = -1;
				if (null != s_user_type && "1".equalsIgnoreCase(s_user_type))
					user_type = 3;// old user,referenced EcsOrderConsts.java
				else
					user_type = 2;// new user
				// 匹配原則:按照商城(總部商城10003),下單的時間是否在活動有效期範圍內(具體到時分秒),
				// 活動類型(直降,溢價),活動是啓用的
				// 用戶類型(必須有的是新/老用戶,以及訂單中的用戶類型)
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String province_region = cacheUtil.getConfigInfo("PROVINCE_REGION_CODE");
			    String PROMOTIONLIST_CACHE_KEY = "PROMOTIONLIST_"+resp.getData().get("goods_id").toString();
//			    String BUSSINESS_TYPE_CACHE_KEY = "PROMOTIONLIST_"+resp.getData().get("goods_id").toString() + province_region + sourceFrom;
				List<Map> promotionList = (List<Map>) cache.get(space, PROMOTIONLIST_CACHE_KEY);
//				List<Map> promotionList = new ArrayList<Map>();
				if(promotionList == null) {
				String sql = "select e.pmt_code, e.begin_time, e.end_time, e.region,e.user_type from es_promotion d,"
						+ " es_promotion_activity e, es_pmt_goods c, es_activity_co f where " + " c.goods_id = ? and c.pmt_id = d.pmt_id"
						+ " and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10003' and f.status='1' "
						+ " and f.source_from = '" + ManagerUtils.getSourceFrom() + "' and f.source_from = d.source_from "
						+ " and f.source_from = e.source_from and f.source_from = c.source_from " + " and d.pmt_type in  ('006','011') and e.enable=1 order by e.status_date";
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				promotionList = support.queryForList(sql, resp.getData().get("goods_id").toString());
//				promotionList = support.queryForList(sql, resp.getData().get("goods_id").toString(), mobileBusiOrder.getOrder_city_code(), user_type);
				CacheList<Map> cacheList = new CacheList<Map>();
				cacheList.addAll(promotionList);
				cache.set(space, PROMOTIONLIST_CACHE_KEY, cacheList ,EXPIRE_TIME);
				
				}
				
				List<Map> activityList = new ArrayList<Map>();
				//判断是否在有效期内
                if (null != promotionList && promotionList.size() > 0) {
                    for (int i = 0; i < promotionList.size(); i++) {
                        Map m = promotionList.get(i);
                        String create_time = objectBusiMallOrder.getCreate_time();
                        Long create_time_stamp = DateFormat.getDateTimeInstance().parse(create_time).getTime();
                        Long begin_time_stamp = DateFormat.getDateTimeInstance().parse(m.get("begin_time").toString()).getTime();
                        Long end_time_stamp = DateFormat.getDateTimeInstance().parse(m.get("end_time").toString()).getTime();
                        if (create_time_stamp > begin_time_stamp && create_time_stamp < end_time_stamp) {
                        	//活动在参与省份城市
                        	if(String.valueOf(m.get("region")).indexOf(province_region)>0||String.valueOf(m.get("region")).indexOf(objectBusiMallOrder.getOrder_city_code())>0) {
                        		if(1==Integer.parseInt((String)m.get("user_type"))||user_type==Integer.parseInt((String)m.get("user_type")))
                        			activityList.add(m);
                        	}	
                            
                        }
                    }
                }
				
				if (null != activityList && activityList.size() > 0) {
					StringBuffer strBuffer = new StringBuffer();
					for (int i = 0; i < activityList.size(); i++) {
						Map m = activityList.get(i);
						strBuffer.append(m.get("pmt_code").toString()).append(",");
					}
					String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
					outer.setReserve9(str);

					logger.info("总部订单:" + outer.getOut_tid() + "匹配到的活动有:" + str);

					// com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(outer.getOut_tid(),
					// str);
					extMap.put("proactivity", str);

				}
			} catch (Exception ex) {
				ex.printStackTrace();
				OuterError ng = new OuterError(goods_id, objectBusiMallOrder.getGoods_info().get(0).getProd_code(), objectBusiMallOrder.getGoods_info().get(0).getProd_code(), null,
						objectBusiMallOrder.getSource_system(), objectBusiMallOrder.getMall_order_id(), "sysdate", "goodserror");// noparam
																															// nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + objectBusiMallOrder.getMall_order_id() + ",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
				continue;
			}
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				setPackageGoodsParam(item, param);
				itemList.add(item);

			} else {
				OuterError ng = new OuterError(goods_id, objectBusiMallOrder.getGoods_info().get(0).getProd_code(), objectBusiMallOrder.getGoods_info().get(0).getProd_code(), null,
						objectBusiMallOrder.getSource_system(), objectBusiMallOrder.getMall_order_id(), "sysdate", "goodserror");// noparam
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + objectBusiMallOrder.getMall_order_id() + ",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
			}

		}

		outer.setItemList(itemList);
		outer.setLocalObject(orderAttr);
		return checkResult;
	}

	/**
	 * 把产品参数添加在OuterItem对象中
	 * 
	 * @param item
	 * @param param
	 */
	private static void setPackageGoodsParam(OuterItem item, Map<String, String> param) {

		// 活动预存款 单位元
		if (MallUtils.isEmpty(param.get("deposit_fee"))) {
			item.setPhone_deposit("0");
		} else {
			item.setPhone_deposit(param.get("deposit_fee"));
		}
		// 产品ID
		item.setProduct_id(param.get("product_id"));
		item.setGoods_id(param.get("goods_id"));

		// 合约期限 12月、18月、24月、36月、48月
		if (MallUtils.isEmpty(param.get("package_limit"))) {
			item.setAct_protper("0");
		} else {
			item.setAct_protper(param.get("package_limit"));
		}

		// 字典：5购机送费、4存费送机、3存费送费 --合约类型
		if (MallUtils.isNotEmpty(param.get("ative_type"))) {
			item.setAtive_type(param.get("ative_type"));
		}

		// 品牌
		if (MallUtils.isNotEmpty(param.get("brand_name"))) {
			item.setBrand_name(param.get("brand_name"));
		}

		// 品牌编码
		if (MallUtils.isNotEmpty(param.get("brand_number"))) {
			item.setBrand_number(param.get("brand_number"));
		}
		// 颜色编码
		if (MallUtils.isNotEmpty(param.get("color_code"))) {
			item.setColor_code(param.get("color_code"));
		}
		if (MallUtils.isNotEmpty(param.get("color_name"))) {
			item.setColor_name(param.get("color_name"));
		}
		// 是否iphone套餐 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("is_iphone_plan"))) {
			item.setIs_iphone_plan(param.get("is_iphone_plan"));
		}
		// 是否靓号 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("is_liang"))) {
			item.setIs_liang(param.get("is_liang"));
		}
		// 是否情侣号码 字典：0否，1是 --默认0
		if (MallUtils.isNotEmpty(param.get("is_loves_phone"))) {
			item.setIs_loves_phone(param.get("is_loves_phone"));
		}
		// 是否库存机 字典：0否，1是 --终端属性
		if (MallUtils.isNotEmpty(param.get("is_stock_phone"))) {
			item.setIs_stock_phone(param.get("is_stock_phone"));
		}
		// 是否赠品 字典：0否，1是
		if (MallUtils.isNotEmpty(param.get("isgifts"))) {
			item.setIsgifts(param.get("isgifts"));
		}
		// 机型编码
		if (MallUtils.isNotEmpty(param.get("model_code"))) {
			item.setModel_code(param.get("model_code"));
		}
		// 机型名称
		if (MallUtils.isNotEmpty(param.get("model_name"))) {
			item.setModel_name(param.get("model_name"));
		}
		// BSS套餐编码 BSS套餐编码
		if (MallUtils.isNotEmpty(param.get("out_plan_id_bss"))) {
			item.setOut_plan_id_bss(param.get("out_plan_id_bss"));
		}
		// 总部套餐编码 总部套餐编码
		if (MallUtils.isNotEmpty(param.get("out_plan_id_ess"))) {
			item.setOut_plan_id_ess(param.get("out_plan_id_ess"));
		}
		// 套餐名称
		if (MallUtils.isNotEmpty(param.get("goods_name"))) {
			item.setPlan_title(param.get("goods_name"));
			item.setPro_name(param.get("goods_name"));
		}
		// 产品类型
		if (MallUtils.isNotEmpty(param.get("product_type"))) {
			item.setPro_type(param.get("product_type"));
		}
		// 产品网别
		if (MallUtils.isNotEmpty(param.get("product_net"))) {
			item.setProduct_net(param.get("product_net"));
		}
		// 型号名称
		if (MallUtils.isNotEmpty(param.get("specification_name"))) {
			item.setSpecificatio_nname(param.get("specification_name"));
		}
		// 型号编码
		if (MallUtils.isNotEmpty(param.get("specification_code"))) {
			item.setSpecification_code(param.get("specification_code"));
		}
		if (MallUtils.isNotEmpty(param.get("type_id"))) {
			item.setType_id(param.get("type_id"));
		}

	}

	/**
	 * 获取码上商城请求的json数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		StringBuffer jsonBuffer = new StringBuffer();
		Map map = request.getParameterMap();
		Set keys = map.keySet();
		Iterator<String> iterator = keys.iterator();
		String strKey = null;
		String[] strVal = null;
		while (iterator.hasNext()) {
			strKey = iterator.next();
			strVal = (String[]) map.get(strKey);
			if (!"".equals(strVal[0])) {
				jsonBuffer.append(strKey).append("=").append(strVal[0]);
			} else {
				jsonBuffer.append(strKey);
			}
		}
		return jsonBuffer.toString();
	}

	/**
	 * 检查订单数据是否正常
	 * 
	 * @param mobileBusiOrder
	 * @return
	 */
	private static String checkMobileBusiOrder(ObjectReplaceBusiOrder objectBusiOrder) {
		INetCache cache = CacheFactory.getCacheByType("");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 序列号
		if (MallUtils.isEmpty(objectBusiOrder.getSerial_no())) {
			return "缺少必填字段:serial_no.";
		}
		// 时间
		if (MallUtils.isEmpty(objectBusiOrder.getCreate_time())) {
			return "缺少必填字段:create_time.";
		} else {
			try {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(objectBusiOrder.getCreate_time(), new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				objectBusiOrder.setCreate_time(dateFormator.format(tDate));
			} catch (Exception e) {
				logger.info(objectBusiOrder.getCreate_time());
				return "create_time值不正确.";
			}
		}

		// 发起方系统标识
		if (MallUtils.isEmpty(objectBusiOrder.getSource_system())) {
			return "缺少必填字段:source_system.";
		} else {
			//改造dbUtils方法实现缓存 ding.xiaotao
            Boolean source_system_check = dbUtils.checkFieldValue("source_system", objectBusiOrder.getSource_system());
			if (!source_system_check) {
				return "source_system值不正确.";
			}
		}

		// 发起方系统标识分类
		if (MallUtils.isEmpty(objectBusiOrder.getSource_system_type())) {
			return "缺少必填字段:source_system_type.";
		} else {
			//改造dbUtils方法实现缓存 ding.xiaotao
            Boolean source_system_type_check = dbUtils.checkFieldValue("source_from_system", objectBusiOrder.getSource_system_type());
            if (!source_system_type_check) {
				return "source_system_type值不正确.";
			}
		}
		
		// 商城系统单号
		if (MallUtils.isEmpty(objectBusiOrder.getMall_order_id())) {
			return "缺少必填字段:mall_order_id.";
		}
		
		//
		if(MallUtils.isEmpty(objectBusiOrder.getIs_pay())
				&& StringUtils.equals(objectBusiOrder.getSource_system_type(), EcsOrderConsts.ORDER_FROM_10071)) {
			return "行销APPis_pay字段必填.";
		}
		// 订单归属省份
		if (MallUtils.isEmpty(objectBusiOrder.getOrder_prov_code())) {
			return "缺少必填字段:order_prov_code.";
		}

		// 订单归属城市
		if (MallUtils.isEmpty(objectBusiOrder.getOrder_city_code())) {
			return "缺少必填字段:order_city_code.";
		}

		// 订单应收金额
		if (MallUtils.isEmpty(objectBusiOrder.getOrder_amount())) {
			return "缺少必填字段:order_amount.";
		} else {
			try {
				// 单位转换为厘
				Integer t = Integer.parseInt(objectBusiOrder.getOrder_amount());
				objectBusiOrder.setOrder_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "order_amount值不正确.";
			}
		}

		// 订单实收金额
		if (MallUtils.isEmpty(objectBusiOrder.getPay_amount())) {
			return "缺少必填字段:pay_amount.";
		} else {
			try {
				// 单位转换为厘
				Integer t = Integer.parseInt(objectBusiOrder.getPay_amount());
				objectBusiOrder.setPay_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "pay_amount值不正确.";
			}
		}

		// 订单减免金额
		if (MallUtils.isEmpty(objectBusiOrder.getDiscount_amount())) {
			return "缺少必填字段:discount_amount.";
		} else {
			try {
				// 单位转换为厘
				Integer t = Integer.parseInt(objectBusiOrder.getDiscount_amount());
				objectBusiOrder.setDiscount_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "discount_amount值不正确.";
			}
		}

		ObjectReplaceContactInfo contact_info = objectBusiOrder.getContact_info();
		if (null == contact_info) {
			return "缺少必填节点:contact_info";
		}

		// 收货人姓名
		if (MallUtils.isEmpty(contact_info.getShip_name())) {
			return "缺少必填字段:ship_name.";
		}

		// 联系电话
		if (MallUtils.isEmpty(contact_info.getShip_tel())) {
			return "缺少必填字段:ship_tel.";
		}
		// 联系地址
		// if (MallUtils.isEmpty(contact_info.getShip_addr())) {
		// return "缺少必填字段:ship_addr.";
		// }

		List<ObjectReplaceGoodsInfo> goodsInfos = objectBusiOrder.getGoods_info();
		// 商品信息节点校验
		if (null == goodsInfos || goodsInfos.size() == 0) {
			return "缺少必填节点:goods_info";
		}
		for (ObjectReplaceGoodsInfo goodsInfo : goodsInfos) {
			// 商品编码
			if (MallUtils.isEmpty(goodsInfo.getProd_code())) {
				return "缺少必填字段:prod_code.";
			}

			// 商品名称
			if (MallUtils.isEmpty(goodsInfo.getProd_name())) {
				return "缺少必填字段:prod_name.";
			}

			// 货品编码
			if (MallUtils.isEmpty(goodsInfo.getProd_offer_code())) {
				return "缺少必填字段:prod_offer_code.";
			}

			// 货品名称
			if (MallUtils.isEmpty(goodsInfo.getProd_offer_name())) {
				return "缺少必填字段:prod_offer_name.";
			}
			
		}

		ObjectReplaceCustInfo custInfo = objectBusiOrder.getCust_info();
		// 客户信息节点校验
		// if (null == custInfo) {
		// return "缺少必填节点:cust_info.";
		// }

		if (null != custInfo) {
			// 是否实名
			if (MallUtils.isEmpty(custInfo.getIs_real_name())) {
				return "缺少必填字段:is_real_name";
			}
			// 客户姓名
			if (MallUtils.isEmpty(custInfo.getCustomer_name())) {
				return "缺少必填字段:customer_name";
			}

			// 证件类型
			if (MallUtils.isEmpty(custInfo.getCert_type())) {
				return "缺少必填字段:cert_type";
			}

			// 证件号码
			if (MallUtils.isEmpty(custInfo.getCert_num())) {
				return "缺少必填字段:cert_num";
			}
			if (!MallUtils.isEmpty(custInfo.getCert_sex())) {
				if (custInfo.getCert_sex().length() != 1) {
					return "Cert_sex值不正确.";
				}
			}

			if (!MallUtils.isEmpty(custInfo.getCert_expire_date())) {
				try {
					SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
					java.util.Date tDate = dateFormator.parse(custInfo.getCert_expire_date() + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
					custInfo.setCert_expire_date(format.format(tDate));
				} catch (Exception e) {
					return "Cert_expire_date值不正确.";
				}

			}

			if (!MallUtils.isEmpty(custInfo.getCert_effected_date())) {
				try {
					SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
					java.util.Date tDate = dateFormator.parse(custInfo.getCert_effected_date() + "000000", new ParsePosition(0));
					dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
					custInfo.setCert_effected_date(format.format(tDate));
				} catch (Exception e) {
					return "Cert_effected_date值不正确.";
				}
			}

		}

		ObjectReplacePayInfo payInfo = objectBusiOrder.getPay_info();
		// 支付节点校验
		if (null == payInfo) {
			return "缺少必填节点:pay_info";
		}

		// 支付类型
		if (MallUtils.isEmpty(payInfo.getPay_type())) {
			return "缺少必填字段:pay_type";
		}
		// 支付状态
		if (MallUtils.isEmpty(payInfo.getPay_status())) {
			return "缺少必填字段:pay_status";
		}
		
		ObjectReplaceObjectInfo objectInfo = objectBusiOrder.getObject_info() ; 
		if(null == objectInfo) {
			return "缺少必填节点:object_info";
		}
		
		if(MallUtils.isEmpty(objectInfo.getService_type())) {
			return "缺少必填字段:service_type";
		}
		
		if(MallUtils.isEmpty(objectInfo.getModerm_id())) {
			return "缺少必填字段:moderm_id";
		}
		if(MallUtils.isEmpty(objectInfo.getSale_mode())) {
			return "缺少必填字段:sale_mode";
		}
		if(MallUtils.isEmpty(objectInfo.getDeal_operator())) {
			return "缺少必填字段:deal_operator";
		}
		
		if(MallUtils.isEmpty(objectInfo.getDeal_office_id())) {
			return "缺少必填字段:deal_office_id";
		}

		return "";
	}

}
