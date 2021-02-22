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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.req.CodePurchaseMallOrderStandardReq;
import params.resp.CodePurchaseMallOrderStandardResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

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
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallBroadInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallCustInfoa;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallDeliverInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallDiscountInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallGoodsInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallOrder;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallPayInfo;
import com.ztesoft.net.server.jsonserver.msgpojo.CodePurchaseMallFeeInfo;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.resp.DevelopOrderResp;
import com.ztesoft.util.DevelopChannelUtil;

import consts.ConstsCore;

public class CodePurchaseMallOrderUtil {

	private static Logger logger = Logger.getLogger(CodePurchaseMallOrderUtil.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static IOrderServiceLocal goodServicesLocal;
	private static IOrderstdService orderstdService;
	private static IGoodsService goodServices;
	private static SDBUtils dbUtils = null;
	private static ICacheUtil cacheUtil;
	public static void initBean(){
		goodServicesLocal = SpringContextHolder.getBean("orderStdServiceLocal");
		orderstdService = SpringContextHolder.getBean("orderstdService");
		outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
		goodServices = SpringContextHolder.getBean("goodServices");
		dbUtils = SpringContextHolder.getBean("sdbUtils");
		cacheUtil = SpringContextHolder.getBean("cacheUtil");
	}
	

	public static CodePurchaseMallOrderStandardResp orderStandardValidate(CodePurchaseMallOrderStandardReq req) throws Exception{
		initBean();
		CodePurchaseMallOrderStandardResp resp = new CodePurchaseMallOrderStandardResp();
		Map extMap = new HashMap<String, String>();
		Map orderMap = new HashMap();
		String outJson = "";
		String checkResult = "";
		boolean errorFlag = false;
		//记录错单需要
		String source_system = "ZB";
		String out_tid = "";
		CodePurchaseMallOrder codePurchaseMallOrder = null;
		
		String inJson = getReqContent(req.getBase_co_id(), req.isIs_exception());
		req.setReq_content(inJson);
		if(StringUtils.isEmpty(inJson)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未获取到请求报文");
			return resp;
		}
		//1.json转换成对象
		codePurchaseMallOrder = MallUtils.jsonToCodePurchaseMallOrder(inJson);
		out_tid = codePurchaseMallOrder.getMall_order_id();
		source_system = codePurchaseMallOrder.getSource_system();
		//检查参数格式是否正确
		//注意所有金额都在checkCenterMallOrder方法里由厘转换为元
		checkResult = checkCodePurchaseMallOrder(codePurchaseMallOrder);
		
		if(!StringUtils.isEmpty(checkResult)){
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		// 2.注意所有金额都在checkMallOrder方法里由厘转换为元
		DevelopOrderResp developOrderResp = null;
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kgMsgMall = cacheUtil.getConfigInfo("channel_convert_kg_msg_mall");
		//收单发展点发展人值判断
		 if("1".equals(kgMsgMall) && StringUtil.isEmpty(checkResult)){
	        	DevelopChannelUtil developChannelUtil = new DevelopChannelUtil();
	        	//渠道转换 1.goodId  2.来源   3.发展点  4.发展人
				developOrderResp = developChannelUtil.getDevelop(codePurchaseMallOrder.getGoods_info().get(0).getProd_offer_code(),
						codePurchaseMallOrder.getSource_system(),
						codePurchaseMallOrder.getGoods_info().get(0).getBroad_info().getDevelop_point_code(),
						codePurchaseMallOrder.getGoods_info().get(0).getDevelop_code());
				if (null != developOrderResp){
					if (!StringUtil.isEmpty(developOrderResp.getMsg())) {
						resp.setError_msg(developOrderResp.getMsg());
						resp.setError_code(ConstsCore.ERROR_FAIL);
						return resp;
					}
				}
	        }
		
		//2.把json参数set对Outer中
		List<Outer> out_order_List = new ArrayList<Outer>();
		Outer out_order = new Outer();
		out_order_List.add(out_order);
		
		String setValResult = setCodePurchaseMallOrderValue(codePurchaseMallOrder,out_order , extMap, developOrderResp);
		if(!StringUtils.isEmpty(setValResult)){
			resp.setError_msg(setValResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("订单收单本地化校验成功");
		resp.setOut_order_List(out_order_List);
		return resp;
	}
	
	//获取归集系统请求报文
	private static  String getReqContent(String co_id,boolean is_exception){
			String content = "";
			OrderQueueBusiRequest orderQueue = new OrderQueueBusiRequest();
			if(is_exception){
				orderQueue = CommonDataFactory.getInstance().getOrderQueueFor(null, co_id);
			}else{
				orderQueue = CommonDataFactory.getInstance().getOrderQueue(null, co_id);
			}
			if(orderQueue!=null){
				content = orderQueue.getContents();
			}
			return content;
	}
	
	/**
	 * json参数set到java对象中
	 * @param centerMallOrder
	 * @param outer
	 * @return
	 */
	private static String setCodePurchaseMallOrderValue(CodePurchaseMallOrder codePurchaseMallOrder , Outer outer , Map extMap, DevelopOrderResp developOrderResp){
		String checkResult = "";
		/**
		 * 补充信息,码上购没传但订单系统必传
		 */
		//下单时间对应物理库的create_time
		outer.setGet_time(codePurchaseMallOrder.getCreate_time());
		//渠道类型  channel_mark  Reserve2
		outer.setReserve2(MallUtils.isEmpty(codePurchaseMallOrder.getChannel_mark())?"13":codePurchaseMallOrder.getChannel_mark());
		//接收方系统标识  receive_system  Reserve0
		outer.setReserve0(codePurchaseMallOrder.getReceive_system());
		//是否2G、3G升4G  is_to4g  Reserve1
		outer.setReserve1("0");
		//订单类型  order_type
		/* 
		 * 预售标识(0:预售) ZX update 2015-12-23 start 
		 * select a.* from es_dc_public_dict_relation a where a.stype='order_type';
		 * type对应外系统5，订单系统07（预售单）
		 * type对应外系统1，订单系统03（订购，正常单）
		 */ 
		outer.setType("");//码上购没传
		
		/* 预售标识(0:预售) ZX udpate 2015-12-23 end */ 
		//订单状态  status
		outer.setStatus("1");
		//是否已办理完成  is_deal
		extMap.put("is_deal", "0");
		extMap.put("intent_order_id", codePurchaseMallOrder.getRel_order_id());
		CodePurchaseMallDeliverInfo deliverInfo = codePurchaseMallOrder.getDelivery_info();
		
		//买家标识  CustomerId
		extMap.put("uid", "");
		//实收运费  RealPostFee
		extMap.put("n_shipping_amount", deliverInfo.getReal_post_fee());
		//deliTimeMode  配送时间类型
		extMap.put("shipping_time", deliverInfo.getDeli_time_mode());
		//ConsiEmail  收件人EMAIL	
		extMap.put("ship_email", "");
		//OrderOperType  是否闪电送
		extMap.put("shipping_quick", "0");
		//仓库名称  inventory_name
		extMap.put("inventory_name", "-1");
		//所属用户  ssyh
		outer.setReserve7("");
		//是否变更套餐
		extMap.put("is_change", "0");
		//订单来源
		extMap.put("source_type", codePurchaseMallOrder.getSource_system());
		//订单接入类别
		extMap.put("regist_type", codePurchaseMallOrder.getSource_system());
		
		//取到客户信息节点
		CodePurchaseMallCustInfoa custInfo = codePurchaseMallOrder.getCust_info();
		
		//ActiveNo  访问流水
		outer.setPayplatformorderid(codePurchaseMallOrder.getSerial_no());
		//CustomerId  买家标识/买家ID
		outer.setBuyer_name(custInfo.getCustomer_name());
		//RegisterName  买家昵称
		outer.setBuyer_id("");
		//ClientIP  买家登录IP
		//ConsiName  收件人姓名
		outer.setReceiver_name(deliverInfo.getShip_name());;
		//ConsiPhone  收件人联系电话
		outer.setReceiver_mobile(deliverInfo.getShip_tel());
		//ConsiTelPhone  收件人联系电话2支持填写固话
		outer.setPhone("");
		//BuyerMessage  买家留言
		outer.setBuyer_message(codePurchaseMallOrder.getOrder_ramark());
		//ConsiGoodsProv  收货省
		outer.setProvinc_code(deliverInfo.getShip_province());
		String prov_name = goodServicesLocal.queryString("SELECT  a.province_name from es_zbmall_area a WHERE a.province_code = '" + deliverInfo.getShip_province() + "' AND ROWNUM < 2");
		outer.setProvince(prov_name);
		//ConsiGoodsCity  收货市
		outer.setCity_code(deliverInfo.getShip_city());
		String city_name = goodServicesLocal.queryString("SELECT  a.city_name from es_zbmall_area a WHERE a.city_code = '" + deliverInfo.getShip_city() + "' AND ROWNUM < 2");
		outer.setCity(city_name);
		//ConsiGoodsDist  收货区/县（行政区域）
		outer.setArea_code(deliverInfo.getShip_district());
		String dist_name = null;
		if (MallUtils.isNotEmpty(deliverInfo.getShip_district())) {
			dist_name = goodServicesLocal.queryString("SELECT  a.district_name from es_zbmall_area a WHERE a.district_code = '" + deliverInfo.getShip_district() + "' AND ROWNUM < 2");
			outer.setDistrict(dist_name);
		}
		//ConsiPostCode  邮政编码
		outer.setPost("");
		//ConsiPostAddress  邮寄地址
		outer.setAddress(deliverInfo.getShip_addr());
		//ConsiPostRemark  邮寄备注
		outer.setService_remarks("");
		//deliMode  配送方式
		outer.setSending_type(deliverInfo.getDeli_mode());
		//OrderId  订单编号
		outer.setOut_tid(codePurchaseMallOrder.getMall_order_id());
		//OrderTime  下单时间，YYYY-MM-DD HH24:MI:SS
		outer.setTid_time(codePurchaseMallOrder.getCreate_time());
		//OrderProvince  订单省份
		outer.setOrder_provinc_code(codePurchaseMallOrder.getOrder_prov_code());
		//OrderCity  订单地市
		outer.setOrder_city_code(codePurchaseMallOrder.getOrder_city_code());
		//OrderSource  订单来源
		outer.setOrder_from(codePurchaseMallOrder.getSource_system());
		//OrderAccType  订单接入类别
		outer.setPlat_type(codePurchaseMallOrder.getSource_system());
		outer.setOrderacctype(codePurchaseMallOrder.getSource_system());
		outer.setOrder_channel(codePurchaseMallOrder.getSource_system());
		//OrderAccDesc  订单接入说明
		//OutOrderId  外部订单编号，即父订单编码
		//OrderOrigFee  订单应收总价
		outer.setOrder_totalfee(String.valueOf(codePurchaseMallOrder.getOrder_amount()));
		outer.setOrder_origfee(String.valueOf(codePurchaseMallOrder.getOrder_amount()));
		//OrderRealFee  订单实收总价
		outer.setOrder_realfee(String.valueOf(codePurchaseMallOrder.getPay_amount()));
		//OrderReliefFee  订单减免金额
		outer.setDiscountValue(String.valueOf(codePurchaseMallOrder.getDiscount_amount()));
		//OrderReliefRes  订单减免原因
		extMap.put("OrderReliefRes",codePurchaseMallOrder.getDiscount_reason());
		//OrigPostFee  应收邮寄费用
		outer.setPost_fee(String.valueOf(deliverInfo.getOrig_post_fee()));

		//InvoiceNo  发票号码(默认空)
		//InvoiceTitle  发票抬头
		outer.setReserve8("");
		//InvoiceType  发票类型
		//总部发票类型都是增值发票(2)
		outer.setInvoice_type(2);
		
		outer.setInvoice_print_type("3");
		
		//InvoiceDesc  发票内容
		extMap.put("invoice_group_content", "");
		
		//PayInfo  支付信息
		CodePurchaseMallPayInfo payInfo = codePurchaseMallOrder.getPay_info();
		if(null != payInfo){
			//PayType  支付类型
			outer.setPaytype(payInfo.getPay_type());
			//PayWay  支付方式
			outer.setPay_method(payInfo.getPay_method());
			//PayFinTime  支付完成时间
			outer.setPay_time("");
			//PayPlatFormOrderId  统一支付订单号
			outer.setPayplatformorderid("");
			//PayProviderId  支付机构编码
			outer.setPayproviderid("");
			//PayProviderName  支付机构名称
			outer.setPayprovidername("");
			//PayChannelId  支付渠道编码
			outer.setPaychannelid("");
			//PayChannelName  支付渠道名称
			outer.setPaychannelname("");		
			//支付状态
			/*if ("HDFK".equalsIgnoreCase(payInfo.getPay_type())) {
				outer.setPay_status("0");
			}else {
				outer.setPay_status("1");
			}*/
			if(StringUtil.isEmpty(codePurchaseMallOrder.getIs_pay())||StringUtil.equals("0", codePurchaseMallOrder.getIs_pay())){
				outer.setPay_status("0");
			}else{
				outer.setPay_status("1");
			}
		}
		
		//DiscountInfo  优惠信息
		List<CodePurchaseMallDiscountInfo> discountInfos = codePurchaseMallOrder.getGoods_info().get(0).getDiscount_info();
		if(null != discountInfos && discountInfos.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(discountInfos);
			extMap.put("discountInfos", jsonArray.toString());
			//总部商城订单  代金券锁定状态默认为0
			extMap.put(AttrConsts.COUPONS_LOCKED, EcsOrderConsts.COUPONS_STATUS_0);
		}
		
		//GoodsInfo  商品信息
		checkResult = setCodePurchaseMallGoodsInfo(codePurchaseMallOrder,outer,extMap, developOrderResp);
		if (!"".equals(checkResult)) {
			return checkResult;
		}
		
		if(!StringUtil.isEmpty(codePurchaseMallOrder.getIs_pay())){
			extMap.put("is_pay", codePurchaseMallOrder.getIs_pay());
		}
		/**
		 * ZX add 2015-12-29 start 责任人使用人信息(集客开户传)
		 */
		
		if (custInfo != null) {
			String usecustname = custInfo.getCustomer_name(); // 使用人责任人姓名
			String usecustpspttype = custInfo.getCert_type(); // 使用人或责任人客户证件类型
			String usecustpsptcode = custInfo.getCert_num(); // 使用人或责任人客户证件号码
			String usecustpsptaddress = custInfo.getCert_addr(); // 使用人或责任人证件地址
			String itmprdgrouptype = "";//custInfo.getItmprdgrouptype(); // B2B、B2C类集客产品标识(责任人时传'B2B'、'B2C'、'B2B2C'等)
			String itmprdrespobsible = "";//custInfo.getItmprdrespobsible(); // 责任人标识(责任人时传1)
			if (StringUtils.isNotBlank(usecustname)) {
				extMap.put("useCustName", usecustname);
			}
			if (StringUtils.isNotBlank(usecustpspttype)) {
				extMap.put("useCustPsptType", usecustpspttype);
			}
			if (StringUtils.isNotBlank(usecustpsptcode)) {
				extMap.put("useCustPsptCode", usecustpsptcode);
			}
			if (StringUtils.isNotBlank(usecustpsptaddress)) {
				extMap.put("useCustPsptAddress", usecustpsptaddress);
			}
			if (StringUtils.isNotBlank(itmprdgrouptype)) {
				extMap.put("itmPrdGroupType", itmprdgrouptype);
			}
			if (StringUtils.isNotBlank(itmprdrespobsible)) {
				extMap.put("itmPrdRespobsible", itmprdrespobsible);
			}
		}
		/**
		 * ZX add 2015-12-29 end 责任人使用人信息(集客开户传)
		 */

		outer.setExtMap(extMap);
		return checkResult;
	}
	
	/**
	 * 把goodsinfo数据set对java对象中
	 * @param centerMallOrder
	 * @param outer
	 * @return
	 */
	private static String setCodePurchaseMallGoodsInfo(CodePurchaseMallOrder codePurchaseMallOrder , Outer outer , Map extMap, DevelopOrderResp developOrderResp){
		String checkResult = "";
		List<OuterItem> itemList  = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();
		
		List<CodePurchaseMallGoodsInfo> goodsInfos = codePurchaseMallOrder.getGoods_info();
		for(CodePurchaseMallGoodsInfo goodsInfo : goodsInfos) {
			OuterItem item = new OuterItem();
			//GoodsNum  订单中商品的总数量
			item.setPro_num(goodsInfo .getGoods_num());
			//GoodsOrigFee  商品应收
			item.setSell_price(Double.parseDouble(goodsInfo.getOffer_price()));
			//GoodsRealFee  商品实收
			item.setPro_origfee(Double.parseDouble(goodsInfo.getReal_offer_price()));
			
			CodePurchaseMallBroadInfo broadInfo = goodsInfo.getBroad_info();
			if(null != broadInfo){
				JSONObject jsonObj = JSONObject.fromObject(broadInfo);
				extMap.put("broadinfo", jsonObj.toString());
				extMap.put("phone_num", broadInfo.getBb_num());
				/*extMap.put("develop_point_code_new", broadInfo.getDevelop_point_code());
				extMap.put("development_point_code", broadInfo.getDevelop_point_code()); 不确定是否加入到extvtl  sgf*/
			}
			
			//FeeInfo费用明细
			List<CodePurchaseMallFeeInfo> feeInfos = codePurchaseMallOrder.getFee_list();
			if(null != feeInfos && feeInfos.size() > 0){
				JSONArray jsonArray = JSONArray.fromObject(feeInfos);
				extMap.put("feeinfo", jsonArray.toString());
			}
			
			//保存外部操作人信息
			extMap.put("out_office", broadInfo.getDeal_office_id());
			extMap.put("out_operator", broadInfo.getDeal_operator());

			if(null != goodsInfo){
				JSONObject jsonObj = JSONObject.fromObject(goodsInfo);
				extMap.put("goodsInfo", jsonObj);
			}
			
			String goods_id = goodsInfo.getProd_offer_code();
			String outer_sku_id = broadInfo.getProduct_code();
			//查看商品是否存在
			//商品类型
				/**
				 * ZX add 2016年4月5日 18:51:40 start
				 */
				//ifSendPhotos  证件上传状态
				
				if (StringUtils.isNotBlank(goodsInfo.getRealname_type())) {
					if (goodsInfo.getRealname_type().equals("2")) {
						extMap.put("is_examine_card", "0"); // 不需要审核，已实名认证
						extMap.put(AttrConsts.CERT_UPLOAD_STATUS, EcsOrderConsts.CERT_STATUS_NONE);//已经实名认证的，无需上传						
					} else if (goodsInfo.getRealname_type().equals("1")) {
						extMap.put("is_examine_card", "1"); // 需要审核，未实名认证
					}
				}

				CodePurchaseMallCustInfoa custInfo = codePurchaseMallOrder.getCust_info();
				//是否老用户
				if (MallUtils.isNotEmpty(custInfo.getUser_type()) 
						&& "OLD".equalsIgnoreCase(custInfo.getUser_type())) {
					orderAttr.setIs_old("1");
					if ( MallUtils.isEmpty(custInfo.getUser_type()) 
							|| MallUtils.isEmpty(custInfo.getCert_num()) ) {
						//goodsAttInfo.setCertType("WXZ");
					}
				}else{
					orderAttr.setIs_old("0");
				}
				//FirstMonBillMode  首月资费方式
				item.setFirst_payment("ALLM");//首月月费接口没有
				
//				outer.setReserve1(reserve1);
				//CertType  证件类型
				item.setCert_type(custInfo.getCert_type());
				//CertNum  证件号码
				item.setCert_card_num(custInfo.getCert_num());
				//出入境号
				item.setCert_num2(custInfo.getCert_num2());
				//CertAddr  证件地址
				item.setCert_address(custInfo.getCert_addr());
				outer.setCert_address(custInfo.getCert_addr());
				//CustomerName  号卡客户姓名
				item.setPhone_owner_name(custInfo.getCustomer_name());
				item.setCert_eff_time(custInfo.getCert_effected_date());
				item.setCerti_sex(custInfo.getCert_sex());
				item.setCert_nation(custInfo.getCert_nation());
				extMap.put("birthday", custInfo.getCust_birthday());
				//extMap.put("cert_eff_time", custInfo.getCert_effected_date());
				extMap.put("sex", custInfo.getCert_sex());
				extMap.put("cert_issuer", custInfo.getCert_issuedat());
				//CertLoseTime  证件失效时间
				item.setCert_failure_time(custInfo.getCert_expire_date());
				if (null != developOrderResp) {
					outer.setDevelopment_code(developOrderResp.getDevelop_code());
					outer.setDevelopment_name(developOrderResp.getDevelop_name());
					extMap.put("develop_code_new", developOrderResp.getDevelop_code());
					extMap.put("develop_name_new", developOrderResp.getDevelop_name());
				} else {
					//DevelopCode   发展人编码
					outer.setDevelopment_code(goodsInfo.getDevelop_code());
					//DevelopName  发展人名称
					outer.setDevelopment_name(goodsInfo.getDevelop_name());
					extMap.put("develop_code_new", goodsInfo.getDevelop_code());
					extMap.put("develop_name_new", goodsInfo.getDevelop_name());
				}
				
				//RefereeNum  推荐人号码 
				//outer.setRecommended_phone(goodsAttInfo.getRefereeNum());
				//推荐人手机  reference_phone
				if (MallUtils.isNotEmpty(goodsInfo.getReferee_num())) {
					extMap.put("reference_phone", goodsInfo.getReferee_num());
				}
				//RefereeName  推荐人名称
				outer.setRecommended_name(goodsInfo.getReferee_name());
				
				//ProductNet  产品网别
				//item.setProduct_net("4G");
				//ProductCode  产品编码
				item.setProduct_id(broadInfo.getProduct_code());
				//ProductName  产品名称
				item.setPro_name("");
				//ProductBrand  产品品牌
				orderAttr.setPro_brand("");
				//CardType  卡类型
				//item.setWhite_cart_type(goodsAttInfo.getCardType());
				
				orderAttr.setBill_type("10");
				orderAttr.setGuarantor("无");
				String package_sale = "0";
			 
				orderAttr.setPackage_sale(package_sale);//套包销售标记：package_sale  默认是0 －非套包销售
				//客户类型  CustomerType
				extMap.put("CustomerType", custInfo.getCustomer_type());
				//ProPacCode  产品包编码
				extMap.put("ProPacCode", goodsInfo.getBroad_info().getProduct_code());
				//ProPacDesc  产品包说明
				extMap.put("ProPacDesc", "");
				//GoodsName 商品名称
				extMap.put("GoodsName", goodsInfo.getProd_offer_name());
				//package_sale  套包销售标记
				extMap.put("package_sale", package_sale);
				
						/*
						 * ZX add 2016-01-07 start 入网类型
						 */
						if (EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equalsIgnoreCase(codePurchaseMallOrder.getBusi_type())
								||EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equalsIgnoreCase(codePurchaseMallOrder.getBusi_type())
								||EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equalsIgnoreCase(codePurchaseMallOrder.getBusi_type())) {
							String userType = custInfo.getUser_type();
							if (StringUtils.isNotBlank(userType)) {
								userType = CommonDataFactory.getInstance().getDictCodeValue("user_type", custInfo.getUser_type());
							}
							extMap.put("is_old", userType);
						}
						List<CodePurchaseMallDiscountInfo> discountInfo = goodsInfo.getDiscount_info();
						if (discountInfo != null && discountInfo.size() > 0) {
							JSONArray json_activities = JSONArray.fromObject(discountInfo);
							extMap.put("zb_activity", json_activities.toString());
						}
						
						//仓库编码  inventory_code
						item.setOut_house_id("-1");
					//} 
				
	
				/* ZX add 2015-12-23 end*/
		
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
				List activityList = support.queryForList(sql,resp.getData().get("goods_id").toString(),codePurchaseMallOrder.getOrder_city_code(),codePurchaseMallOrder.getCreate_time(),user_type);
				
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
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				OuterError ng = new OuterError(goods_id, outer_sku_id, outer_sku_id, null, codePurchaseMallOrder.getSource_system(), codePurchaseMallOrder.getMall_order_id(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + codePurchaseMallOrder.getMall_order_id() +",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
				continue;
			}
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				setPackageGoodsParam(item,param);
				itemList.add(item);
				
			}else {
				OuterError ng = new OuterError(goods_id, outer_sku_id, outer_sku_id, null, codePurchaseMallOrder.getSource_system(), codePurchaseMallOrder.getMall_order_id(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + codePurchaseMallOrder.getMall_order_id() +",获取商品相关参数失败======================");
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
	private static void setPackageGoodsParam(OuterItem item , Map<String, String> param){
		
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
	
	/**
	 * 获取码上商城请求的json数据
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String getRequestJson(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
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
	 * @param centerMallOrder
	 * @return
	 */
	private static String checkCodePurchaseMallOrder(CodePurchaseMallOrder centerMallOrder){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strTmp = null;
		//序列号
		if (MallUtils.isEmpty(centerMallOrder.getSerial_no())) {
			return "缺少必填字段:SerialNo.";
		}
		//时间
		if (MallUtils.isEmpty(centerMallOrder.getCreate_time())) {
			return "缺少必填字段:CreateTime.";
		}else {
			try {
				format.parse(centerMallOrder.getCreate_time());
			} catch (Exception e) {
				return "CreateTime值不正确.";
			}
		}
		
		//发起方系统标识
		if (MallUtils.isEmpty(centerMallOrder.getSource_system())) {
			return "缺少必填字段:SourceSystem.";
		}else {
			if (!( dbUtils.checkFieldValue("source_system", centerMallOrder.getSource_system()) )) {
				return "SourceSystem值不正确.";
			}
		}
		//接收方系统标识
		if (MallUtils.isEmpty(centerMallOrder.getReceive_system())) {
			return "缺少必填字段:ReceiveSystem.";
		}
		
		//业务类型
		if (MallUtils.isEmpty(centerMallOrder.getBusi_type())) {
			return "缺少必填字段:busiType.";
		}
		
		//渠道标记
		if (MallUtils.isEmpty(centerMallOrder.getChannel_mark())) {
			return "缺少必填字段:ChannelMark.";
		}
		
		//商城系统单号
		if (MallUtils.isEmpty(centerMallOrder.getMall_order_id())) {
			return "缺少必填字段:MallOrderId.";
		}
		
		//订单归属省份
		if (MallUtils.isEmpty(centerMallOrder.getOrder_prov_code())) {
			return "缺少必填字段:OrderProvCode.";
		}
		
		//订单归属城市
		if (MallUtils.isEmpty(centerMallOrder.getOrder_city_code())) {
			return "缺少必填字段:OrderCityCode.";
		}
		
		//订单应收金额
		if (MallUtils.isEmpty(centerMallOrder.getOrder_amount())) {
			return "缺少必填字段:OrderAmount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getOrder_amount());
				centerMallOrder.setOrder_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "OrderAmount值不正确.";
			}
		}
		
		//订单实收金额
		if (MallUtils.isEmpty(centerMallOrder.getPay_amount())) {
			return "缺少必填字段:PayAmount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getPay_amount());
				centerMallOrder.setPay_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "PayAmount值不正确.";
			}
		}
		
		//订单减免金额
		if (MallUtils.isEmpty(centerMallOrder.getDiscount_amount())) {
			return "缺少必填字段:DiscountAmount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getDiscount_amount());
				centerMallOrder.setDiscount_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "DiscountAmount值不正确.";
			}
		}
		
				
		CodePurchaseMallDeliverInfo deliverInfo = centerMallOrder.getDelivery_info();
		if(null == deliverInfo){
			return "缺少必填节点:deliverInfo";
		}
		
		//应收邮寄费用
		if (MallUtils.isEmpty(deliverInfo.getOrig_post_fee())) {
			return "缺少必填字段:OrigPostFee.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(deliverInfo.getOrig_post_fee());
				deliverInfo.setOrig_post_fee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "OrigPostFee值不正确.";
			}
		}
		
		//实收邮寄费用
		if (!MallUtils.isEmpty(deliverInfo.getReal_post_fee())) {
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(deliverInfo.getReal_post_fee());
				deliverInfo.setReal_post_fee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "RealPostFee值不正确.";
			}
		}
		
		//配送方式
		if (MallUtils.isEmpty(deliverInfo.getDeli_mode())) {
			return "缺少必填字段:DeliMode.";
		}	
		
		//配送时间类型
		if (MallUtils.isEmpty(deliverInfo.getDeli_time_mode())) {
			return "缺少必填字段:DeliTimeMode.";
		} else {
			if(!( dbUtils.checkFieldValue("deliTimeMode", deliverInfo.getDeli_time_mode()))){
				return "deliTimeMode值不正确.";
			}
		}
		
		//收货人姓名
		if (MallUtils.isEmpty(deliverInfo.getShip_name())) {
			return "缺少必填字段:ShipName.";
		}
		
		//收货省
		if (MallUtils.isEmpty(deliverInfo.getShip_province())) {
			return "缺少必填字段:ShipProvince.";
		}
		
		//收货地市
		if (MallUtils.isEmpty(deliverInfo.getShip_city())) {
			return "缺少必填字段:ShipCity.";
		}
		
		/*收货区县(码上购不传入，改为非必填)
		if (MallUtils.isEmpty(deliverInfo.getShip_district())) {
			return "缺少必填字段:ShipDistrict.";
		}*/
		
		//收货地址
		if (MallUtils.isEmpty(deliverInfo.getShip_addr())) {
			return "缺少必填字段:ShipAddr.";
		}
		
		//联系电话
		if (MallUtils.isEmpty(deliverInfo.getShip_tel())) {
			return "缺少必填字段:ShipTel.";
		}
		
		List<CodePurchaseMallGoodsInfo> goodsInfos = centerMallOrder.getGoods_info();
		//商品信息节点校验
		if(null == goodsInfos && goodsInfos.size() == 0){
			return "缺少必填节点:GoodsInfo";
		}
		for (CodePurchaseMallGoodsInfo goodsInfo : goodsInfos) {
			//商品编码
			if (MallUtils.isEmpty(goodsInfo.getProd_offer_code())) {
				return "缺少必填字段:ProdOfferCode.";
			} 
			
			//商品名称
			if (MallUtils.isEmpty(goodsInfo.getProd_offer_name())) {
				return "缺少必填字段:ProdOfferName.";
			} 
			
			//商品数量
			if (MallUtils.isEmpty(String.valueOf(goodsInfo.getGoods_num()))) {
				return "缺少必填字段:GoodsNum.";
			} 
			
			//商品应收
			if (MallUtils.isEmpty(goodsInfo.getOffer_price())) {
				return "缺少必填字段:OfferPrice.";
			}else{
				try {
					//单位转换为厘
					Integer t = Integer.parseInt(goodsInfo.getOffer_price());
					goodsInfo.setOffer_price(MallUtils.parseMoney(t));
				} catch (Exception e) {
					return "OfferPrice值不正确.";
				}
			}
			
			//商品实收
			if (MallUtils.isEmpty(goodsInfo.getReal_offer_price())) {
				return "缺少必填字段:RealOfferPrice.";
			} else{
				try {
					//单位转换为厘
					Integer t = Integer.parseInt(goodsInfo.getReal_offer_price());
					goodsInfo.setReal_offer_price(MallUtils.parseMoney(t));
				} catch (Exception e) {
					return "RealOfferPrice值不正确.";
				}
			}
			
			//实名认证类型
			if (MallUtils.isEmpty(goodsInfo.getRealname_type())) {
				return "缺少必填字段:RealnameType.";
			} 
			
			//激活类型
			if (MallUtils.isEmpty(goodsInfo.getAct_flag())) {
				return "缺少必填字段:ActFlag.";
			} 
			
			//付费类型
			if (MallUtils.isEmpty(goodsInfo.getSer_type())) {
				return "缺少必填字段:SerType.";
			} 
			
			CodePurchaseMallBroadInfo broadInfo = goodsInfo.getBroad_info();
			//校验宽带信息
			if (null == broadInfo) {
				return "缺少必填节点:BroadInfo.";
			} 
			
			//产品编码
			/*if (MallUtils.isEmpty(broadInfo.getProduct_code())) {
				return "缺少必填字段:ProductCode.";
			} */
			
			//标准地址
			if (MallUtils.isEmpty(broadInfo.getStd_address())) {
				return "缺少必填字段:StdAddres.";
			} 
			
			//宽带用户地址
			if (MallUtils.isEmpty(broadInfo.getUser_address())) {
				return "缺少必填字段:UserAddress.";
			} 
			
			//局向编码
			if (MallUtils.isEmpty(broadInfo.getExch_code())) {
				return "缺少必填字段:ExchCode.";
			} 
			
			//固网网格(非必填)
//			if (MallUtils.isEmpty(broadInfo.getGrid())) {
//				return "缺少必填字段:Grid.";
//			} 
			
			//用户种类
			if (MallUtils.isEmpty(broadInfo.getUser_kind())) {
				return "缺少必填字段:UserKind.";
			} 
			
			//宽带账号
			if (MallUtils.isEmpty(broadInfo.getBb_account())) {
				return "缺少必填字段:BbAccount.";
			} 
			
			//宽带号码
			if (MallUtils.isEmpty(broadInfo.getBb_num())) {
				return "缺少必填字段:BbNum.";
			} 
			//受理类型
			if (MallUtils.isEmpty(broadInfo.getService_type())) {
				return "缺少必填字段:ServiceType.";
			}
			
			if(!MallUtils.isEmpty(broadInfo.getAppt_date())){
				try {
					format.parse(broadInfo.getAppt_date());
				} catch (Exception e) {
					return "ApptDate值不正确.";
				}
			}
			if (MallUtils.isEmpty(broadInfo.getCounty_id())) {
				return "缺少必填字段:county_id.";
			}
			
			if("10071".equals(centerMallOrder.getSource_system())){
				
				if (MallUtils.isEmpty(broadInfo.getAccess_type())) {
					return "缺少必填字段:access_type.";
				}
			}
			String service_type1 = CommonDataFactory.getInstance().getGoodSpec(null, centerMallOrder.getGoods_info().get(0).getProd_offer_code(), "service_type");
			if (MallUtils.isEmpty(broadInfo.getModerm_id())&&service_type1.equals("6115")
					&&!StringUtils.isEmpty(service_type1)) {
				return "缺少必填字段:moderm_id().";
			}
			//校验优惠信息
			List<CodePurchaseMallDiscountInfo> discountInfos = goodsInfo.getDiscount_info();
			for (CodePurchaseMallDiscountInfo discountInfo : discountInfos) {
				
				//活动编码
				if (MallUtils.isEmpty(discountInfo.getActivity_code())) {
					return "缺少必填字段:ActivityCode.";
				} 
				
				//活动名称
				if (MallUtils.isEmpty(discountInfo.getActivity_name())) {
					return "缺少必填字段:ActivityName.";
				} 
				
				//活动类型
				if (MallUtils.isEmpty(discountInfo.getActivity_type())) {
					return "缺少必填字段:ActivityType.";
				} 
				
				//优惠金额 
				if (MallUtils.isEmpty(discountInfo.getDiscount_amount())) {
					return "缺少必填字段:DiscountAmount.";
				} else{
					try {
						//单位转换为厘
						Integer t = Integer.parseInt(discountInfo.getDiscount_amount());
						discountInfo.setDiscount_amount(MallUtils.parseMoney(t));
					} catch (Exception e) {
						return "DiscountAmount值不正确.";
					}
				}
			}
			
		}
		
		CodePurchaseMallCustInfoa custInfo = centerMallOrder.getCust_info();
		//客户信息节点校验
		if (null == custInfo) {
			return "缺少必填节点:CustInfo.";
		} 
		
		//客户姓名
		if(MallUtils.isEmpty(custInfo.getCustomer_name())){
			return "缺少必填字段:CustomerName";
		}
		
		//用户类型
		if(MallUtils.isEmpty(custInfo.getUser_type())){
			return "缺少必填字段:UserType";
		}
		
		//客户类型
		if(MallUtils.isEmpty(custInfo.getCustomer_type())){
			return "缺少必填字段:CustomerType";
		}
		
		//证件类型
		if(MallUtils.isEmpty(custInfo.getCert_type())){
			return "缺少必填字段:CertType";
		}
		
		//证件号码
		if(MallUtils.isEmpty(custInfo.getCert_num())){
			return "缺少必填字段:CertNum";
		}
		
		/*证件地址(码上购不传改为非必填)
		if(MallUtils.isEmpty(custInfo.getCert_addr())){
			return "缺少必填字段:CertAddr";
		}
		
		//客户生日
		if(MallUtils.isEmpty(custInfo.getCust_birthday())){
			return "缺少必填字段:CustBirthday";
		}*/
		
		//证件失效时间
		if(MallUtils.isEmpty(custInfo.getCert_expire_date())){
			return "缺少必填字段:CertExpireDate";
		}else{
			try {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(custInfo.getCert_expire_date()+"000000", new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				custInfo.setCert_expire_date(format.format(tDate));
			} catch (Exception e) {
				return "Cert_expire_date值不正确.";
			}
		}
		
		//证件生效时间
		if(MallUtils.isEmpty(custInfo.getCert_effected_date())){
			return "缺少必填字段:CertEffectedDate";
		}else{
			try {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(custInfo.getCert_effected_date()+"000000", new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				custInfo.setCert_effected_date(format.format(tDate));
			} catch (Exception e) {
				return "Cert_effected_date值不正确.";
			}
		}
		
		CodePurchaseMallPayInfo payInfo = centerMallOrder.getPay_info();
		//支付节点校验
		if(null == payInfo){
			return "缺少必填节点:PayInfo";
		}
		
		if(!"10071".equals(centerMallOrder.getSource_system())){
			//支付类型
			if(MallUtils.isEmpty(payInfo.getPay_type())){
				return "缺少必填字段:PayType";
			}
			
			//支付方式
			if(MallUtils.isEmpty(payInfo.getPay_method())){
				return "缺少必填字段:PayMethod";
			}
		}
		if(!StringUtil.isEmpty(centerMallOrder.getSource_system())&&"10071".equals(centerMallOrder.getSource_system())){
			if(StringUtils.isEmpty(centerMallOrder.getIs_pay())){
				return "缺少必填字段:is_pay";
				
			}
		}
		
		//FeeInfo  费用明细
		if(!StringUtil.isEmpty(centerMallOrder.getSource_system())&&"10071".equals(centerMallOrder.getSource_system())){
			List<CodePurchaseMallFeeInfo> feeInfos = centerMallOrder.getFee_list();
			if(null != feeInfos && feeInfos.size() >0){
				for (CodePurchaseMallFeeInfo CodePurchaseMallFeeInfo : feeInfos) {
					strTmp = checkFeeInfo(CodePurchaseMallFeeInfo);
					if ( !("".equals(strTmp)) ) {
						return strTmp;
					}
				}
			}
		}
		
		
		return "";
	}
	
	/**
	 * 检查费用明细
	 * @param feeInfo
	 * @return
	 */
	private static String checkFeeInfo(CodePurchaseMallFeeInfo feeInfo){
		//收费项编码
		if (MallUtils.isEmpty(feeInfo.getFeeID())) {
			return "缺少必填字段:FeeID.";
		}
		//收费项描述
		if (MallUtils.isEmpty(feeInfo.getFeeDes())) {
			return "缺少必填字段:FeeDes.";
		}
		//应收金额
		if (MallUtils.isEmpty(feeInfo.getOrigFee())) {
			return "缺少必填字段:OrigFee.";
		}else {
			try {
				//单位转换为元
				/*Integer t = Integer.parseInt(feeInfo.getOrigFee());
				feeInfo.setOrigFee(MallUtils.parseMoney(t));*/
			} catch (Exception e) {
				return "OrigFee值不正确.";
			}
		}
		//减免金额
		if (MallUtils.isEmpty(feeInfo.getReliefFee())) {
			return "缺少必填字段:ReliefFee.";
		}else {
			try {
				//单位转换为元
				/*Integer t = Integer.parseInt(feeInfo.getReliefFee());
				feeInfo.setReliefFee(MallUtils.parseMoney(t));*/
			} catch (Exception e) {
				return "ReliefFee值不正确.";
			}
		}
		//实收金额
		if (MallUtils.isEmpty(feeInfo.getRealFee())) {
			return "缺少必填字段:RealFee.";
		}else {
			try {
				//单位转换为元
				/*Integer t = Integer.parseInt(feeInfo.getRealFee());
				feeInfo.setRealFee(MallUtils.parseMoney(t));*/
			} catch (Exception e) {
				return "RealFee值不正确.";
			}
		}
		return "";
	}
	
	
}
