package com.ztesoft.net.server;

import java.io.UnsupportedEncodingException;
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

import com.alibaba.fastjson.JSON;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.CacheUtil;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrder;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderBroadInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderContactInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderCustInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderDeveloperInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderPayInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderSubProdInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixBusiOrderSubsOtherInfo;
import com.ztesoft.net.server.jsonserver.hlwgwsdpojo.InterFixFeeInfo;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.resp.DevelopOrderResp;
import com.ztesoft.util.DevelopChannelUtil;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.req.BroadBandCtnStandardReq;
import params.resp.BroadBandCtnStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

public class BroadBandCtnUtil {

	private static Logger logger = Logger.getLogger(BroadBandCtnUtil.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static IOrderServiceLocal goodServicesLocal;
	private static IOrderstdService orderstdService;
	private static IGoodsService goodServices;
	private static SDBUtils dbUtils = null;
	private static ICacheUtil cacheUtil;

	public static void initBean() {
		goodServicesLocal = SpringContextHolder.getBean("orderStdServiceLocal");
		orderstdService = SpringContextHolder.getBean("orderstdService");
		outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
		goodServices = SpringContextHolder.getBean("goodServices");
		dbUtils = SpringContextHolder.getBean("sdbUtils");
		cacheUtil = SpringContextHolder.getBean("cacheUtil");
	}

	public static BroadBandCtnStandardResp orderStandardValidate(BroadBandCtnStandardReq req) throws Exception {
		initBean();
		BroadBandCtnStandardResp resp = new BroadBandCtnStandardResp();
		Map extMap = new HashMap<String, String>();
		Map orderMap = new HashMap();
		String outJson = "";
		String checkResult = "";
		boolean errorFlag = false;
		// 记录错单需要
		String source_system = "ZB";
		String out_tid = "";
		InterFixBusiOrder interFixBusiOrder = null;

		String inJson = getReqContent(req.getBase_co_id(), req.isIs_exception());
		req.setReq_content(inJson);
		if (StringUtils.isEmpty(inJson)) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未获取到请求报文");
			return resp;
		}
		// 1.json转换成对象
        
		interFixBusiOrder = MallUtils.jsonToInterFixBusiOrder(inJson);
		out_tid = interFixBusiOrder.getMall_order_id();
		source_system = interFixBusiOrder.getSource_system();
		// 检查参数格式是否正确
		// 注意所有金额都在checkCenterMallOrder方法里由厘转换为元
		checkResult = checkInterFixBusiOrder(interFixBusiOrder);

		if (!StringUtils.isEmpty(checkResult)) {
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		

		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kgBroadBand = cacheUtil.getConfigInfo("channel_convert_kg_broad_band");
		DevelopOrderResp developOrderResp = null;
		//收单发展点发展人值判断
        if("1".equals(kgBroadBand) && StringUtil.isEmpty(checkResult)){
        	DevelopChannelUtil developChannelUtil = new DevelopChannelUtil();
        	//渠道转换 1.goodId  2.来源   3.发展点  4.发展人
			developOrderResp = developChannelUtil.getDevelop(interFixBusiOrder.getGoods_info().get(0).getProd_offer_code(),
					interFixBusiOrder.getSource_system_type(),
					interFixBusiOrder.getDeveloper_info().getDevelop_point_code(),
					interFixBusiOrder.getDeveloper_info().getDevelop_code());
			if (null != developOrderResp){
				if (!StringUtil.isEmpty(developOrderResp.getMsg())) {
					resp.setError_msg(developOrderResp.getMsg());
					resp.setError_code(ConstsCore.ERROR_FAIL);
					return resp;
				}
			}
        }
		
		// 2.把json参数set对Outer中
		List<Outer> out_order_List = new ArrayList<Outer>();
		Outer out_order = new Outer();
		out_order_List.add(out_order);

		String setValResult = setInterFixBusiOrderValue(interFixBusiOrder, out_order, extMap, developOrderResp);
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
	private static String setInterFixBusiOrderValue(InterFixBusiOrder InterFixBusiMallOrder, Outer outer, Map extMap, DevelopOrderResp developOrderResp) {
		String checkResult = "";
		
		if (StringUtils.isNotBlank(InterFixBusiMallOrder.getRel_order_id())) {
			extMap.put("intent_order_id", InterFixBusiMallOrder.getRel_order_id());
		}
		/**
		 * 补充信息,码上购没传但订单系统必传
		 */
		// 下单时间对应物理库的create_time
		outer.setGet_time(InterFixBusiMallOrder.getCreate_time());
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

		InterFixBusiOrderContactInfo contactInfo = InterFixBusiMallOrder.getContact_info();

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
		extMap.put("source_type", InterFixBusiMallOrder.getSource_system_type());
		// 订单接入类别
		extMap.put("regist_type", InterFixBusiMallOrder.getSource_system());

		// 取到客户信息节点
		InterFixBusiOrderCustInfo custInfo = InterFixBusiMallOrder.getCust_info();

		// ActiveNo 访问流水
		outer.setPayplatformorderid(InterFixBusiMallOrder.getSerial_no());
		// CustomerId 买家标识/买家ID
		outer.setBuyer_name(custInfo.getCustomer_name());
		// RegisterName 买家昵称
		outer.setBuyer_id("");
		// ClientIP 买家登录IP
		// ConsiName 收件人姓名
		outer.setReceiver_name(contactInfo.getShip_name());
		;
		// ConsiPhone 收件人联系电话
		outer.setReceiver_mobile(contactInfo.getShip_tel());
		// ConsiTelPhone 收件人联系电话2支持填写固话
		outer.setPhone("");
		// BuyerMessage 买家留言
		outer.setBuyer_message(InterFixBusiMallOrder.getOrder_remark());
		// ConsiPostCode 邮政编码
		outer.setPost("");
		// ConsiPostAddress 邮寄地址
		outer.setAddress(contactInfo.getShip_addr());
		// ConsiPostRemark 邮寄备注
		outer.setService_remarks("");
		// deliMode 配送方式
		// outer.setSending_type(deliverInfo.getDeli_mode());
		extMap.put("sending_type", "NO");
		extMap.put("shipping_time", "NOLIMIT");
		// OrderId 订单编号
		outer.setOut_tid(InterFixBusiMallOrder.getMall_order_id());
		// OrderTime 下单时间，YYYY-MM-DD HH24:MI:SS
		outer.setTid_time(InterFixBusiMallOrder.getCreate_time());
		// OrderProvince 订单省份
		outer.setOrder_provinc_code(InterFixBusiMallOrder.getOrder_prov_code());
		// OrderCity 订单地市
		outer.setOrder_city_code(InterFixBusiMallOrder.getOrder_city_code());
		// Todo:订单归属县分
		extMap.put("district_code", InterFixBusiMallOrder.getOrder_county_code());
		// OrderSource 订单来源
		outer.setOrder_from(InterFixBusiMallOrder.getSource_system_type());
		// OrderAccType 订单接入类别
		outer.setPlat_type(InterFixBusiMallOrder.getSource_system());
		outer.setOrderacctype(InterFixBusiMallOrder.getSource_system());
		outer.setOrder_channel(InterFixBusiMallOrder.getSource_system());
		// OrderAccDesc 订单接入说明
		// OutOrderId 外部订单编号，即父订单编码
		// OrderOrigFee 订单应收总价
		outer.setOrder_totalfee(String.valueOf(InterFixBusiMallOrder.getOrder_amount()));
		outer.setOrder_origfee(String.valueOf(InterFixBusiMallOrder.getOrder_amount()));
		// OrderRealFee 订单实收总价
		outer.setOrder_realfee(String.valueOf(InterFixBusiMallOrder.getPay_amount()));
		// OrderReliefFee 订单减免金额
		outer.setDiscountValue(String.valueOf(InterFixBusiMallOrder.getDiscount_amount()));
		// OrderReliefRes 订单减免原因
		extMap.put("OrderReliefRes", InterFixBusiMallOrder.getDiscount_reason());
		// OrigPostFee 应收邮寄费用
		// outer.setPost_fee(String.valueOf(deliverInfo.getOrig_post_fee()));

		// InvoiceNo 发票号码(默认空)
		// InvoiceTitle 发票抬头
		outer.setReserve8("");
		// InvoiceType 发票类型
		// 总部发票类型都是增值发票(2)
		outer.setInvoice_type(2);
		outer.setProvinc_code(InterFixBusiMallOrder.getOrder_prov_code());
		outer.setCity_code(InterFixBusiMallOrder.getOrder_city_code());
		outer.setArea_code(InterFixBusiMallOrder.getOrder_city_code());
		outer.setPost_fee("0");

		outer.setInvoice_print_type("3");

		// InvoiceDesc 发票内容
		extMap.put("invoice_group_content", "");

		// PayInfo 支付信息
		InterFixBusiOrderPayInfo payInfo = InterFixBusiMallOrder.getPay_info();
		if (null != payInfo) {
			// PayType 支付类型
			outer.setPaytype(payInfo.getPay_type());

			if (payInfo.getPay_type().equals("SMSF")) {
				extMap.put("is_pay", "0");
			} else {
				extMap.put("is_pay", "1");
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
			java.util.Date tDate = dateFormator.parse(com.ztesoft.net.framework.util.StringUtil.getTransDate(),
					new ParsePosition(0));
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
		checkResult = setInterFixBusiMallGoodsInfo(InterFixBusiMallOrder, outer, extMap, developOrderResp);
		if (!"".equals(checkResult)) {
			return checkResult;
		}

		String cust_id = custInfo.getCust_id();// 客户编码
		String group_code = custInfo.getGroup_code();// 集团15位编码
		String is_real_name = custInfo.getIs_real_name();// 是否实名

		if (!StringUtil.isEmpty(cust_id)) {
			extMap.put("cust_id", cust_id);
		}
		if (!StringUtil.isEmpty(group_code)) {
			extMap.put("group_code", group_code);
		}
		if (!StringUtil.isEmpty(is_real_name)) {
			extMap.put("is_real_name", custInfo.getIs_real_name());
		}
		if (!StringUtil.isEmpty(InterFixBusiMallOrder.getResult_url())) {// 异步通知url
			extMap.put("result_url", InterFixBusiMallOrder.getResult_url());
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
	private static String setInterFixBusiMallGoodsInfo(InterFixBusiOrder InterFixBusiMallOrder, Outer outer,
			Map extMap, DevelopOrderResp developOrderResp) {
		String checkResult = "";
		List<OuterItem> itemList = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();

		List<InterFixBusiOrderGoodsInfo> goodsInfos = InterFixBusiMallOrder.getGoods_info();
		for (InterFixBusiOrderGoodsInfo goodsInfo : goodsInfos) {
			OuterItem item = new OuterItem();
			// GoodsNum 订单中商品的总数量
			item.setPro_num(1);
			// GoodsOrigFee 商品应收
			item.setSell_price(
					Double.parseDouble(goodsInfo.getOffer_price() == null ? "0" : goodsInfo.getOffer_price()));
			// GoodsRealFee 商品实收
			item.setPro_origfee(Double
					.parseDouble(goodsInfo.getReal_offer_price() == null ? "0" : goodsInfo.getReal_offer_price()));

			InterFixBusiOrderBroadInfo broadInfo = InterFixBusiMallOrder.getBroad_info();
			if (null != broadInfo) {

				Map broad_map = new HashMap();
				if (!StringUtils.isEmpty(InterFixBusiMallOrder.getContact_info().getShip_addr())) {
					broadInfo.setAdsl_addr_desc(InterFixBusiMallOrder.getContact_info().getShip_addr());
				}
				try {
					BeanUtils.bean2Map(broad_map, broadInfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				InterFixBusiOrderDeveloperInfo developerInfo = InterFixBusiMallOrder.getDeveloper_info();
				if (developerInfo != null) {
					if (null !=developOrderResp) {
						broad_map.put("develop_point_code", developOrderResp.getDevelopment_point_code());
						broad_map.put("develop_point_name", developOrderResp.getDevelopment_point_name());
						broad_map.put("develop_code", developOrderResp.getDevelop_code());
						broad_map.put("develop_name", developOrderResp.getDevelop_name());
						extMap.put("development_code",developOrderResp.getDevelop_code());
			            extMap.put("develop_code_new",developOrderResp.getDevelop_code());
			            extMap.put("development_name",developOrderResp.getDevelop_code());
			            extMap.put("develop_name_new",developOrderResp.getDevelop_name());
			            extMap.put("develop_point_code_new",developOrderResp.getDevelopment_point_code());
			            extMap.put("development_point_code",developOrderResp.getDevelopment_point_code());
			            extMap.put("development_point_name",developOrderResp.getDevelopment_point_name());
			            outer.setDevelopment_code(developOrderResp.getDevelop_code());
						// DevelopName 发展人名称
						outer.setDevelopment_name(developOrderResp.getDevelop_name());
					} else {
						broad_map.put("develop_point_code", developerInfo.getDevelop_point_code());
						broad_map.put("develop_point_name", developerInfo.getDevelop_point_name());
						broad_map.put("develop_code", developerInfo.getDevelop_code());
						broad_map.put("develop_name", developerInfo.getDevelop_name());
					    if (!StringUtils.isEmpty(developerInfo.getDevelop_code())) {
				            extMap.put("development_code",developerInfo.getDevelop_code());
				            extMap.put("develop_code_new",developerInfo.getDevelop_code());
				        }
				        if (!StringUtils.isEmpty(developerInfo.getDevelop_name())) {
				            extMap.put("development_name",developerInfo.getDevelop_code());
				            extMap.put("develop_name_new",developerInfo.getDevelop_name());
				        }
				        if (!StringUtils.isEmpty(developerInfo.getDevelop_point_code())) {
				            extMap.put("develop_point_code_new",developerInfo.getDevelop_point_code());
				            extMap.put("development_point_code",developerInfo.getDevelop_point_code());

				        }
				        if (!StringUtils.isEmpty(developerInfo.getDevelop_point_name())) {
				            extMap.put("development_point_name",developerInfo.getDevelop_point_name());
				        }
				        outer.setDevelopment_code(developerInfo.getDevelop_code());
						// DevelopName 发展人名称
						outer.setDevelopment_name(developerInfo.getDevelop_name());
					}
					
					if (!StringUtils.isEmpty(developerInfo.getCounty_id())) {
						broad_map.put("county_id", developerInfo.getCounty_id());
						extMap.put("county_id", developerInfo.getCounty_id());
					} else {
						if (!StringUtils.isEmpty(InterFixBusiMallOrder.getOrder_county_code())) {
							IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
							String sql1 = "select field_value,other_field_value from es_dc_public_dict_relation where stype ='county'";
							final List<Map<String, String>> list1 = baseDaoSupport.queryForList(sql1);
							// 行政县分转营业县分-如果developer_info下传county_id就覆盖
							for (Map<String, String> county : list1) {
								if (county.get("field_value").equals(InterFixBusiMallOrder.getOrder_county_code())) {
									broad_map.put("county_id", county.get("other_field_value"));
									extMap.put("county_id", county.get("other_field_value"));
									break;
								}
							}
						}
					}

					// 拍照流水
					if (null != InterFixBusiMallOrder.getCust_info().getReq_swift_num()) {
						broad_map.put("req_swift_num", InterFixBusiMallOrder.getCust_info().getReq_swift_num());
					}
					// 保存外部操作人信息
					extMap.put("out_office", developerInfo.getDeal_office_id());
					extMap.put("out_operator", developerInfo.getDeal_operator());
					// 渠道分类
					extMap.put("channel_type", developerInfo.getChannelType());
					// 渠道类型
					extMap.put("spread_channel", developerInfo.getDeal_office_type());
					extMap.put("moderm_name", broadInfo.getModerm_name());
					extMap.put("adsl_addr_desc", broadInfo.getUser_address());
					extMap.put("adsl_addr", broadInfo.getStd_address());
					extMap.put("moderm_id", broadInfo.getModerm_id());

					

					if (MallUtils.isNotEmpty(developerInfo.getReferee_num())) {
						extMap.put("reference_phone", developerInfo.getReferee_num());
					}
					// RefereeName 推荐人名称
					if (MallUtils.isNotEmpty(developerInfo.getReferee_name())) {
						outer.setRecommended_name(developerInfo.getReferee_name());
					}
				}

				com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(JSON.toJSONString(broad_map));
				extMap.put("broadinfo", jsonObj.toString());
				if (!StringUtils.isEmpty(broadInfo.getBb_account())) {
					extMap.put("phone_num", broadInfo.getBb_account());
				} else {
					IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
					String sql = "select type_id from es_goods where goods_id='"
							+ InterFixBusiMallOrder.getGoods_info().get(0).getProd_code() + "'";
					String type = support.queryForString(sql);
					ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
					String kdjj_type = cacheUtil.getConfigInfo("kdjj_type");
					if (kdjj_type.contains(type)) {
						extMap.put("phone_num", cacheUtil.getConfigInfo("acc_nbr"));// 13333333333
					}
				}

				// FeeInfo费用明细
				List<InterFixFeeInfo> feeInfos = InterFixBusiMallOrder.getBroad_info().getFee_list();
				if (null != feeInfos && feeInfos.size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(feeInfos);
					extMap.put("feeinfo", jsonArray.toString());
				} else {
					extMap.put("feeinfo", "");
				}

			}

			List<InterFixBusiOrderSubProdInfo> sub_prod_info = InterFixBusiMallOrder.getSub_prod_info();
			if (sub_prod_info != null) {
				JSONArray jsonArray = JSONArray.fromObject(sub_prod_info);
				extMap.put("subprodinfo", jsonArray);
			}
			InterFixBusiOrderSubsOtherInfo sub_other_info = InterFixBusiMallOrder.getSubs_other_info();
			if (sub_other_info != null) {
				JSONObject jsonObj = JSONObject.fromObject(sub_other_info);
				extMap.put("subotherinfo", jsonObj);
			} else {
				extMap.put("subotherinfo", "");
			}

			if (null != goodsInfo) {
				JSONObject jsonObj = JSONObject.fromObject(goodsInfo);
				extMap.put("goodsInfo", jsonObj);
			} else {
				extMap.put("goodsInfo", "");
			}

			String goods_id = goodsInfo.getProd_offer_code();
			// String outer_sku_id = broadInfo.getProduct_code();
			// 查看商品是否存在
			// 商品类型
			/**
			 * ZX add 2016年4月5日 18:51:40 start
			 */
			// ifSendPhotos 证件上传状态

			/*
			 * if (StringUtils.isNotBlank(goodsInfo.getRealname_type())) { if
			 * (goodsInfo.getRealname_type().equals("2")) {
			 * extMap.put("is_examine_card", "0"); // 不需要审核，已实名认证
			 * extMap.put(AttrConsts.CERT_UPLOAD_STATUS,
			 * EcsOrderConsts.CERT_STATUS_NONE);//已经实名认证的，无需上传 } else if
			 * (goodsInfo.getRealname_type().equals("1")) {
			 * extMap.put("is_examine_card", "1"); // 需要审核，未实名认证 } }
			 */

			InterFixBusiOrderCustInfo custInfo = InterFixBusiMallOrder.getCust_info();
			// FirstMonBillMode 首月资费方式
			item.setFirst_payment("ALLM");// 首月月费接口没有

			// outer.setReserve1(reserve1);
			// CertType 证件类型
			item.setCert_type(custInfo.getCert_type());
			// CertNum 证件号码
			item.setCert_card_num(custInfo.getCert_num());
			//  证件号码  出入境号
            item.setCert_num2(custInfo.getCert_num2());
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
			 * SimpleDateFormat(DateUtil.DATE_FORMAT_5); java.util.Date tDate =
			 * dateFormator.parse(centerMallOrder.getCreate_time(), new
			 * ParsePosition(0)); dateFormator = new
			 * SimpleDateFormat(DateUtil.DATE_FORMAT_2);
			 * centerMallOrder.setCreate_time(dateFormator.format(tDate));
			 */

			item.setCert_failure_time(custInfo.getCert_expire_date());
			// 证件失效时间
			item.setCert_eff_time(custInfo.getCert_effected_date());
			// 签发机关
			item.setCert_issuer(custInfo.getCert_issuedat());
			// ProductBrand 产品品牌
			orderAttr.setPro_brand("");
			orderAttr.setBill_type("10");
			orderAttr.setGuarantor("无");
			String package_sale = "0";

			orderAttr.setPackage_sale(package_sale);// 套包销售标记：package_sale 默认是0
													// －非套包销售
			// 客户类型 CustomerType
			extMap.put("CustomerType", custInfo.getCustomer_type() == null ? "100" : custInfo.getCustomer_type());
			// 客户生日
			extMap.put("birthday", custInfo.getCust_birthday());
			// ProPacCode 产品包编码
			// extMap.put("ProPacCode",
			// goodsInfo.getBroad_info().getProduct_code());
			// ProPacDesc 产品包说明
			extMap.put("ProPacDesc", "");
			// GoodsName 商品名称
			extMap.put("GoodsName", goodsInfo.getProd_offer_name());
			// package_sale 套包销售标记
			extMap.put("package_sale", package_sale);
			
			extMap.put("syn_mode", InterFixBusiMallOrder.getSyn_mode());
			extMap.put("kingcard_status", InterFixBusiMallOrder.getKingcard_status());

			/*
			 * ZX add 2016-01-07 start 入网类型
			 * 
			 * if (EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equalsIgnoreCase(
			 * FixBusiMallOrder.getBusi_type())
			 * ||EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equalsIgnoreCase(
			 * FixBusiMallOrder.getBusi_type())
			 * ||EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equalsIgnoreCase(
			 * FixBusiMallOrder.getBusi_type())) { String userType =
			 * custInfo.getUser_type(); if (StringUtils.isNotBlank(userType)) {
			 * userType =
			 * CommonDataFactory.getInstance().getDictCodeValue("user_type",
			 * custInfo.getUser_type()); // if
			 * (userType.equals(EcsOrderConsts.ZB_USER_TYPE_NEW)) { // userType
			 * = EcsOrderConsts.IS_OLD_0; // } else if
			 * (userType.equals(EcsOrderConsts.ZB_USER_TYPE_OLD)) { // userType
			 * = EcsOrderConsts.IS_OLD_1; // } } extMap.put("is_old", userType);
			 * }
			 */
			/*
			 * ZX add 2016-01-07 end 入网类型
			 */
			/*
			 * ZX add 2016-01-08 start 附加产品信息
			 */

			/*
			 * ZX add 2016-01-08 end 附加产品信息
			 */

			// 仓库编码 inventory_code
			item.setOut_house_id("-1");
			// }

			/* ZX add 2015-12-23 end */

			Map<String, String> param = new HashMap<String, String>();
			GoodsGetReq req = new GoodsGetReq();
			req.setGoods_id(goods_id);// 商品ID
			GoodsGetResp resp = null;
			try {
				resp = goodServices.getGoods(req);

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
				String sql = "select c.goods_id, c.pmt_id, d.pmta_id, d.pmt_type, d.pmt_solution, "
						+ " e.name,e.pmt_code, e.begin_time, e.end_time, e.status_date, f.org_id from es_promotion d,"
						+ " es_promotion_activity e, es_pmt_goods c, es_activity_co f where "
						+ " c.goods_id = ? and c.pmt_id = d.pmt_id"
						+ " and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10003' and f.status='1' "
						+ " and (e.region = '" + province_region + "' or instr(e.region,?)>0) and "
						+ " to_date(?,'yyyy-mm-dd hh24:mi:ss') between "
						+ " e.begin_time and e.end_time and f.source_from = '" + ManagerUtils.getSourceFrom()
						+ "' and f.source_from = d.source_from "
						+ " and f.source_from = e.source_from and f.source_from = c.source_from "
						+ " and d.pmt_type in  ('006','011') and e.enable=1 and e.user_type in (1,?) order by e.status_date";
				logger.info(sql);
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				List activityList = support.queryForList(sql, resp.getData().get("goods_id").toString(),
						InterFixBusiMallOrder.getOrder_city_code(), InterFixBusiMallOrder.getCreate_time(), user_type);

				if (null != activityList && activityList.size() > 0) {
					StringBuffer strBuffer = new StringBuffer();
					for (int i = 0; i < activityList.size(); i++) {
						Map m = (Map) activityList.get(i);
						strBuffer.append(m.get("pmt_code").toString()).append(",");
					}
					String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
					outer.setReserve9(str);

					logger.info("总部订单:" + outer.getOut_tid() + "匹配到的活动有:" + str);

					// com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(outer.getOut_tid(),
					// str);
					extMap.put("proactivity", str);

					// 屬於給es_outer_accept的reserve9做的限制,因爲該表的reserve9字段爲varchar2(128)
					/*
					 * if(str.length() > 128){
					 * outer.setReserve9(str.substring(0,128)); }else{
					 * outer.setReserve9(str); }
					 */
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				OuterError ng = new OuterError(goods_id, InterFixBusiMallOrder.getGoods_info().get(0).getProd_code(),
						InterFixBusiMallOrder.getGoods_info().get(0).getProd_code(), null,
						InterFixBusiMallOrder.getSource_system(), InterFixBusiMallOrder.getMall_order_id(), "sysdate",
						"goodserror");// noparam
										// nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + InterFixBusiMallOrder.getMall_order_id() + ",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
				continue;
			}
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				setPackageGoodsParam(item, param);
				itemList.add(item);

			} else {
				OuterError ng = new OuterError(goods_id, InterFixBusiMallOrder.getGoods_info().get(0).getProd_code(),
						InterFixBusiMallOrder.getGoods_info().get(0).getProd_code(), null,
						InterFixBusiMallOrder.getSource_system(), InterFixBusiMallOrder.getMall_order_id(), "sysdate",
						"goodserror");// noparam
										// nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + InterFixBusiMallOrder.getMall_order_id() + ",获取商品相关参数失败======================");
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
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
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
	 * 检查码上购订单数据是否正常
	 * 
	 * @param centerMallOrder
	 * @return
	 */
	private static String checkInterFixBusiOrder(InterFixBusiOrder centerMallOrder) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strTmp = null;
		// 序列号
		if (MallUtils.isEmpty(centerMallOrder.getSerial_no())) {
			return "缺少必填字段:serial_no.";
		}
		// 时间
		if (MallUtils.isEmpty(centerMallOrder.getCreate_time())) {
			return "缺少必填字段:create_time.";
		} else {
			try {
				logger.info(centerMallOrder.getCreate_time());
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(centerMallOrder.getCreate_time(), new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				centerMallOrder.setCreate_time(dateFormator.format(tDate));
			} catch (Exception e) {
				return "create_time值不正确.";
			}
		}

		// 发起方系统标识
		if (MallUtils.isEmpty(centerMallOrder.getSource_system())) {
			return "缺少必填字段:source_system.";
		} else {
			if (!(dbUtils.checkFieldValue("source_system", centerMallOrder.getSource_system()))) {
				return "source_system值不正确.";
			}
		}

		// 发起方系统标识分类
		if (MallUtils.isEmpty(centerMallOrder.getSource_system_type())) {
			return "缺少必填字段:source_system_type.";
		} else {
			if (!(dbUtils.checkFieldValue("source_from_system", centerMallOrder.getSource_system_type()))) {
				return "source_system_type值不正确.";
			}
		}

		// 商城系统单号
		if (MallUtils.isEmpty(centerMallOrder.getMall_order_id())) {
			return "缺少必填字段:mall_order_id.";
		}

		// 订单归属省份
		if (MallUtils.isEmpty(centerMallOrder.getOrder_prov_code())) {
			return "缺少必填字段:order_prov_code.";
		}

		// 订单归属城市
		if (MallUtils.isEmpty(centerMallOrder.getOrder_city_code())) {
			return "缺少必填字段:order_city_code.";
		}

		// 订单应收金额
		if (MallUtils.isEmpty(centerMallOrder.getOrder_amount())) {
			return "缺少必填字段:order_amount.";
		} else {
			try {
				// 单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getOrder_amount());
				centerMallOrder.setOrder_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "order_amount值不正确.";
			}
		}

		// 订单实收金额
		if (MallUtils.isEmpty(centerMallOrder.getPay_amount())) {
			return "缺少必填字段:pay_amount.";
		} else {
			try {
				// 单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getPay_amount());
				centerMallOrder.setPay_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "pay_amount值不正确.";
			}
		}

		// 订单减免金额
		if (MallUtils.isEmpty(centerMallOrder.getDiscount_amount())) {
			return "缺少必填字段:discount_amount.";
		} else {
			try {
				// 单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getDiscount_amount());
				centerMallOrder.setDiscount_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "discount_amount值不正确.";
			}
		}

		InterFixBusiOrderContactInfo contact_info = centerMallOrder.getContact_info();
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
		if (MallUtils.isEmpty(contact_info.getShip_addr())) {
			return "缺少必填字段:ship_addr.";
		}

		List<InterFixBusiOrderGoodsInfo> goodsInfos = centerMallOrder.getGoods_info();
		// 商品信息节点校验
		if (null == goodsInfos || goodsInfos.size() == 0) {
			return "缺少必填节点:goods_info";
		}
		for (InterFixBusiOrderGoodsInfo goodsInfo : goodsInfos) {
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
			// 商品应收
			if (!MallUtils.isEmpty(goodsInfo.getOffer_price())) {
				try {
					// 单位转换为厘
					Integer t = Integer.parseInt(goodsInfo.getOffer_price());
					goodsInfo.setOffer_price(MallUtils.parseMoney(t));
				} catch (Exception e) {
					return "offer_price值不正确.";
				}
			}
			// 商品实收
			if (!MallUtils.isEmpty(goodsInfo.getReal_offer_price())) {
				try {
					// 单位转换为厘
					Integer t = Integer.parseInt(goodsInfo.getReal_offer_price());
					goodsInfo.setReal_offer_price(MallUtils.parseMoney(t));
				} catch (Exception e) {
					return "real_offer_price值不正确.";
				}
			}
		}

		InterFixBusiOrderCustInfo custInfo = centerMallOrder.getCust_info();
		// 客户信息节点校验
		if (null == custInfo) {
			return "缺少必填节点:cust_info.";
		}

		// 是否实名
		if (MallUtils.isEmpty(custInfo.getIs_real_name())) {
			return "缺少必填字段:is_real_name";
		}
		// 客户姓名
		if (MallUtils.isEmpty(custInfo.getCustomer_name())) {
			return "缺少必填字段:customer_name";
		}

		/*
		 * //客户类型 if(MallUtils.isEmpty(custInfo.getCustomer_type())){ return
		 * "缺少必填字段:customer_type"; }
		 */

		// 证件类型
		if (MallUtils.isEmpty(custInfo.getCert_type())) {
			return "缺少必填字段:cert_type";
		}

		// 证件号码
		if (MallUtils.isEmpty(custInfo.getCert_num())) {
			return "缺少必填字段:cert_num";
		}

		// 证件地址
		/*
		 * if(MallUtils.isEmpty(custInfo.getCert_addr())){ return
		 * "缺少必填字段:cert_addr"; }
		 */

		if (!MallUtils.isEmpty(custInfo.getCert_expire_date())) {
			try {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(custInfo.getCert_expire_date(), new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				custInfo.setCert_expire_date(format.format(tDate));
			} catch (Exception e) {
				return "Cert_expire_date值不正确.";
			}

		}

		if (!MallUtils.isEmpty(custInfo.getCert_effected_date())) {
			try {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(custInfo.getCert_effected_date() + "000000",
						new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				custInfo.setCert_effected_date(format.format(tDate));
			} catch (Exception e) {
				return "Cert_effected_date值不正确.";
			}

		}

		InterFixBusiOrderPayInfo payInfo = centerMallOrder.getPay_info();
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

		InterFixBusiOrderBroadInfo broad_info = centerMallOrder.getBroad_info();
		if (broad_info == null) {
			return "缺少必填字段:broad_info";
		} else {
			if (!MallUtils.isEmpty(broad_info.getAppo_date())) {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(broad_info.getAppo_date(), new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				broad_info.setAppo_date(format.format(tDate));
			}

			if (MallUtils.isEmpty(broad_info.getStd_address())) {
				return "缺少必填字段:Std_address";
			}
			if (MallUtils.isEmpty(broad_info.getIs_judge_address())) {
				return "缺少必填字段:Is_judge_address";
			}
			if (MallUtils.isEmpty(broad_info.getProduct_type())) {
				return "缺少必填字段:product_type";
			}
			if (MallUtils.isEmpty(broad_info.getUser_address())) {
				return "缺少必填字段:user_address";
			}
		}
		return "";
	}
}
