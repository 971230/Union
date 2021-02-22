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

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
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
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiContactInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiCustInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiNiceInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiOrder;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiPayInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiPhoneInfo;
import com.ztesoft.net.server.jsonserver.internetpojo.InternetBusiSubProdInfo;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.resp.DevelopOrderResp;
import com.ztesoft.util.DevelopChannelUtil;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.req.InternetOrderStandardReq;
import params.resp.InternetOrderStandardResp;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.iservice.IGoodsService;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

public class InternetBusiOrderUtil {

	private static Logger logger = Logger.getLogger(InternetBusiOrderUtil.class);
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
	

	public static InternetOrderStandardResp orderStandardValidate(InternetOrderStandardReq req) throws Exception{
		initBean();
		InternetOrderStandardResp resp = new InternetOrderStandardResp();
		Map extMap = new HashMap<String, String>();
		Map orderMap = new HashMap();
		String outJson = "";
		String checkResult = "";
		boolean errorFlag = false;
		//记录错单需要
		String source_system = "ZB";
		String out_tid = "";
		InternetBusiOrder internetBusiOrder = null;
		
		String inJson = getReqContent(req.getBase_co_id(), req.isIs_exception());
		req.setReq_content(inJson);
		if(StringUtils.isEmpty(inJson)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未获取到请求报文");
			return resp;
		}
		//1.json转换成对象
		internetBusiOrder = MallUtils.jsonToInternetBusiOrder(inJson);
		out_tid = internetBusiOrder.getMall_order_id();
		source_system = internetBusiOrder.getSource_system();
		//检查参数格式是否正确
		//注意所有金额都在checkCenterMallOrder方法里由厘转换为元
		checkResult = checkFixBusiOrder(internetBusiOrder);
		
		if(!StringUtils.isEmpty(checkResult)){
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kgInternetBusi = cacheUtil.getConfigInfo("channel_convert_kg_internet_busi");
		DevelopOrderResp developOrderResp = null;
		//收单发展点发展人值判断
        if("1".equals(kgInternetBusi) && StringUtil.isEmpty(checkResult)){
        	DevelopChannelUtil developChannelUtil = new DevelopChannelUtil();
        	//渠道转换 1.goodId  2.来源   3.发展点  4.发展人
			developOrderResp = developChannelUtil.getDevelop(internetBusiOrder.getPhone_info().getProd_offer_code(),
					internetBusiOrder.getSource_system_type(),
					internetBusiOrder.getDeveloper_info().getDevelop_point_code(),
					internetBusiOrder.getDeveloper_info().getDevelop_code());
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
		
		String setValResult = setInternetBusiOrderValue(internetBusiOrder,out_order , extMap, developOrderResp);
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
	private static String setInternetBusiOrderValue(InternetBusiOrder internetBusiOrder , Outer outer , Map extMap, DevelopOrderResp developOrderResp){
		String checkResult = "";
	
		//下单时间对应物理库的create_time
		outer.setGet_time(internetBusiOrder.getSerial_time());
		//渠道类型  channel_mark  Reserve2
		outer.setReserve2("13");
		//接收方系统标识  receive_system  Reserve0
		outer.setReserve0("10011");
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
		
//		FixBusiOrderDeliveryInfo deliverInfo = FixBusiMallOrder.getDelivery_info();
		
		//买家标识  CustomerId
		extMap.put("uid", "");
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
		extMap.put("source_type", internetBusiOrder.getSource_system());
		//订单接入类别
		extMap.put("regist_type", internetBusiOrder.getSource_system());
		
		//取到客户信息节点
		InternetBusiCustInfo custInfo = internetBusiOrder.getCust_info();
		
		//ActiveNo  访问流水
		outer.setPayplatformorderid(internetBusiOrder.getSerial_no());
		//CustomerId  买家标识/买家ID
		outer.setBuyer_name(custInfo.getCust_name());
		//RegisterName  买家昵称
		outer.setBuyer_id("");
		//ClientIP  买家登录IP
		InternetBusiContactInfo contact_info = internetBusiOrder.getContact_info();
		if(contact_info == null) {
			outer.setReceiver_name(internetBusiOrder.getShip_name());
			outer.setReceiver_mobile(internetBusiOrder.getShip_phone());
			outer.setAddress(internetBusiOrder.getShip_addr());
		}else {
			outer.setReceiver_name(contact_info.getShip_name());
			outer.setReceiver_mobile(contact_info.getShip_phone());
			outer.setAddress(contact_info.getShip_addr());
		}
		
		//ConsiTelPhone  收件人联系电话2支持填写固话
		outer.setPhone("");
		//BuyerMessage  买家留言
		outer.setBuyer_message(internetBusiOrder.getBuyer_message());
		//ConsiPostCode  邮政编码
		outer.setPost("");
		//ConsiPostAddress  邮寄地址
		//outer.setAddress(deliverInfo.getShip_addr());
		//ConsiPostRemark  邮寄备注
		outer.setService_remarks("");
		//deliMode  配送方式
		//outer.setSending_type(deliverInfo.getDeli_mode());
		//OrderId  订单编号
		outer.setOut_tid(internetBusiOrder.getMall_order_id());
		//OrderTime  下单时间，YYYY-MM-DD HH24:MI:SS
		outer.setTid_time(internetBusiOrder.getSerial_time());
		//OrderProvince  订单省份
		outer.setOrder_provinc_code(internetBusiOrder.getOrder_prov_code());
		//OrderCity  订单地市
		outer.setOrder_city_code(internetBusiOrder.getOrder_city_code());
		//OrderSource  订单来源
		outer.setOrder_from(internetBusiOrder.getSource_system_type() == null ? internetBusiOrder.getSource_system():internetBusiOrder.getSource_system_type());
		//OrderAccType  订单接入类别
		outer.setPlat_type(internetBusiOrder.getSource_system());
		outer.setOrderacctype(internetBusiOrder.getSource_system());
		outer.setOrder_channel(internetBusiOrder.getSource_system());
		//OrderAccDesc  订单接入说明
		//OutOrderId  外部订单编号，即父订单编码
		//OrderOrigFee  订单应收总价
		outer.setOrder_totalfee(String.valueOf(internetBusiOrder.getOrder_amount()));
		outer.setOrder_origfee(String.valueOf(internetBusiOrder.getOrder_amount()));
		//OrderRealFee  订单实收总价
		outer.setOrder_realfee(String.valueOf(internetBusiOrder.getPay_amount()));
		//OrderReliefFee  订单减免金额
		outer.setDiscountValue(String.valueOf(internetBusiOrder.getDiscount_amount()));
		//OrderReliefRes  订单减免原因
		extMap.put("OrderReliefRes",internetBusiOrder.getDiscount_reason());
		//OrigPostFee  应收邮寄费用
		//outer.setPost_fee(String.valueOf(deliverInfo.getOrig_post_fee()));

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
		InternetBusiPayInfo payInfo = internetBusiOrder.getPay_info();
		if(null != payInfo){
			//PayType  支付类型
			outer.setPaytype(payInfo.getPay_type());
			//PayWay  支付方式
			outer.setPay_method(payInfo.getPay_method());
			//PayFinTime  支付完成时间
			outer.setPay_time(internetBusiOrder.getSerial_time());
			//PayPlatFormOrderId  统一支付订单号
			outer.setPayplatformorderid(payInfo.getPay_sequ());
			//PayProviderId  支付机构编码
			outer.setPayproviderid("");
			//PayProviderName  支付机构名称
			outer.setPayprovidername("");
			//PayChannelId  支付渠道编码
			outer.setPaychannelid("");
			//PayChannelName  支付渠道名称
			outer.setPaychannelname("");		
			//支付状态
			
		}
		
		
		//GoodsInfo  商品信息
		checkResult = setInternetBusiMallGoodsInfo(internetBusiOrder,outer,extMap, developOrderResp);
		if (!"".equals(checkResult)) {
			return checkResult;
		}
		
		/*if(!StringUtil.isEmpty(FixBusiMallOrder.getIs_pay())){
			extMap.put("is_pay", FixBusiMallOrder.getIs_pay());
		}*/
		/**
		 * ZX add 2015-12-29 start 责任人使用人信息(集客开户传)
		 */
		
//		if (custInfo != null) {
//			String usecustname = custInfo.getCustomer_name(); // 使用人责任人姓名
//			String usecustpspttype = custInfo.getCert_type(); // 使用人或责任人客户证件类型
//			String usecustpsptcode = custInfo.getCert_num(); // 使用人或责任人客户证件号码
//			String usecustpsptaddress = custInfo.getCert_addr(); // 使用人或责任人证件地址
//			String itmprdgrouptype = "";//custInfo.getItmprdgrouptype(); // B2B、B2C类集客产品标识(责任人时传'B2B'、'B2C'、'B2B2C'等)
//			String itmprdrespobsible = "";//custInfo.getItmprdrespobsible(); // 责任人标识(责任人时传1)
//			String cust_id = custInfo.getCust_id();
//			String group_code = custInfo.getGroup_code();
//			if (StringUtils.isNotBlank(usecustname)) {
//				extMap.put("useCustName", usecustname);
//			}
//			if (StringUtils.isNotBlank(usecustpspttype)) {
//				extMap.put("useCustPsptType", usecustpspttype);
//			}
//			if (StringUtils.isNotBlank(usecustpsptcode)) {
//				extMap.put("useCustPsptCode", usecustpsptcode);
//			}
//			if (StringUtils.isNotBlank(usecustpsptaddress)) {
//				extMap.put("useCustPsptAddress", usecustpsptaddress);
//			}
//			if (StringUtils.isNotBlank(itmprdgrouptype)) {
//				extMap.put("itmPrdGroupType", itmprdgrouptype);
//			}
//			if (StringUtils.isNotBlank(itmprdrespobsible)) {
//				extMap.put("itmPrdRespobsible", itmprdrespobsible);
//			}
//			if(!StringUtil.isEmpty(cust_id)){
//				extMap.put("cust_id", cust_id);
//			}
//			if(!StringUtil.isEmpty(group_code)){
//				extMap.put("group_code", group_code);
//			}
//		}
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
	private static String setInternetBusiMallGoodsInfo(InternetBusiOrder InternetBusiOrder , Outer outer , Map extMap, DevelopOrderResp developOrderResp){
		String checkResult = "";
		List<OuterItem> itemList  = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();
		
		InternetBusiPhoneInfo phoneInfo = InternetBusiOrder.getPhone_info();

		OuterItem item = new OuterItem();
		//GoodsNum  订单中商品的总数量
		item.setPro_num(1);

		if(null != phoneInfo){


			extMap.put("phone_num", phoneInfo.getAcc_nbr()); //业务号码
			extMap.put("contract_month", phoneInfo.getContract_month());//合约期
			extMap.put("GoodsName",phoneInfo.getProd_offer_name()); //商品名称
			extMap.put("ICCID", phoneInfo.getiCCID()); //iccid
			extMap.put("bssOrderId", phoneInfo.getBss_order_id()); //bss单号
			
		
		}
		InternetBusiNiceInfo nice_info = InternetBusiOrder.getNice_info();
		if(null != nice_info) {
			Map nice_map = new HashMap();
			try {
				BeanUtils.bean2Map(nice_map, nice_info);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JSONObject jsonObj = JSONObject.fromObject(nice_map);
			extMap.put("internetPhoneinfo", jsonObj.toString());
		}
		
		InternetBusiSubProdInfo sub_prod_info = InternetBusiOrder.getSub_prod_info();
		if(sub_prod_info!=null){
			JSONArray jsonArray = JSONArray.fromObject(sub_prod_info);
			extMap.put("subprodinfo", jsonArray);
		}

		//保存外部操作人信息4
		if (null != developOrderResp) {
			//发展人部门编码
			extMap.put("development_code", developOrderResp.getDevelop_code());
			//发展人名字
			extMap.put("development_name", developOrderResp.getDevelop_name());
		  
			extMap.put("develop_code_new", developOrderResp.getDevelop_code());
	        extMap.put("develop_name_new", developOrderResp.getDevelop_name());
			
	        //存储发展点
			extMap.put("development_point_code", developOrderResp.getDevelopment_point_code());
		    extMap.put("develop_point_code_new", developOrderResp.getDevelopment_point_code());
			//发展点名称 develop_point_name
			extMap.put("development_point_name", developOrderResp.getDevelopment_point_name());
			//DevelopCode   发展人编码
			outer.setDevelopment_code(developOrderResp.getDevelop_code());
			//DevelopName  发展人名称 
			outer.setDevelopment_name(developOrderResp.getDevelop_name());
		} else {
			//发展人部门编码
			extMap.put("development_code", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDevelop_code());
			//发展人名字
			extMap.put("development_name", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDevelop_name());
		  
			extMap.put("develop_code_new", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDevelop_code());
	        extMap.put("develop_name_new", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDevelop_name());
			
	        //存储发展点
			extMap.put("development_point_code", InternetBusiOrder.getDeveloper_info() == null?"":InternetBusiOrder.getDeveloper_info().getDevelop_point_code());
		    extMap.put("develop_point_code_new", InternetBusiOrder.getDeveloper_info() == null?"":InternetBusiOrder.getDeveloper_info().getDevelop_point_code());
			//发展点名称 develop_point_name
			extMap.put("development_point_name", InternetBusiOrder.getDeveloper_info() == null?"":InternetBusiOrder.getDeveloper_info().getDevelop_point_name());
			//DevelopCode   发展人编码
			outer.setDevelopment_code(InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDevelop_code());
			//DevelopName  发展人名称 
			outer.setDevelopment_name(InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDevelop_name());
		}
	
		extMap.put("out_office", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDeal_office_id());
		extMap.put("out_operator", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getDeal_operator());
		//县份
		extMap.put("district_code", InternetBusiOrder.getDeveloper_info()== null?"":InternetBusiOrder.getDeveloper_info().getCounty_id());
		
		
		//存储地市
		InternetBusiContactInfo contact_info = InternetBusiOrder.getContact_info();
		if(contact_info != null) {
			extMap.put("provinc_code", contact_info.getShip_province());
			extMap.put("city_code", contact_info.getShip_city() );
			extMap.put("area_code", contact_info.getShip_county());
		}
		

		    InternetBusiCustInfo custInfo = InternetBusiOrder.getCust_info();
			//FirstMonBillMode  首月资费方式
			item.setFirst_payment("ALLM");//首月月费接口没有
			
//			outer.setReserve1(reserve1);
			//CertType  证件类型
			item.setCert_type(custInfo.getCerti_type());
			//CertNum  证件号码
			item.setCert_card_num(custInfo.getCerti_num());
			//出入境号
            item.setCert_num2(custInfo.getCert_num2());
			//CertAddr  证件地址

			//CustomerName  号卡客户姓名
			item.setPhone_owner_name( custInfo.getCust_name());
			//CertLoseTime  证件失效时间
//			item.setCert_failure_time(custInfo.getCert_expire_date());
			
			//RefereeNum  推荐人号码 
			//outer.setRecommended_phone(goodsAttInfo.getRefereeNum());
			//推荐人手机  reference_phone
			if(InternetBusiOrder.getDeveloper_info() != null) {
				if (MallUtils.isNotEmpty(InternetBusiOrder.getDeveloper_info().getReferee_num())) {
					extMap.put("reference_phone", InternetBusiOrder.getDeveloper_info().getReferee_num());
				}
				//RefereeName  推荐人名称
				if(MallUtils.isNotEmpty(InternetBusiOrder.getDeveloper_info().getReferee_name())){
					outer.setRecommended_name(InternetBusiOrder.getDeveloper_info().getReferee_name());
				}
			}
			
			
			
			//ProductNet  产品网别
			//item.setProduct_net("4G");
			
			
			if(InternetBusiOrder.getSub_prod_info() != null) {
				//ProductCode  产品编码
				item.setProduct_id(InternetBusiOrder.getSub_prod_info().getSub_prod_code());
				//ProductName  产品名称
				item.setPro_name(InternetBusiOrder.getSub_prod_info().getSub_prod_name());
			}
			
			//ProductBrand  产品品牌
			orderAttr.setPro_brand("");
			//CardType  卡类型
			//item.setWhite_cart_type(goodsAttInfo.getCardType());
			
			orderAttr.setBill_type("10");
			orderAttr.setGuarantor("无");
			String package_sale = "0";
		 
			orderAttr.setPackage_sale(package_sale);//套包销售标记：package_sale  默认是0 －非套包销售
			//客户类型  CustomerType
//			extMap.put("CustomerType", custInfo.getCustomer_type());
			//ProPacCode  产品包编码
			//extMap.put("ProPacCode", goodsInfo.getBroad_info().getProduct_code());
			//ProPacDesc  产品包说明
			extMap.put("ProPacDesc", "");
			//GoodsName 商品名称
			extMap.put("GoodsName", phoneInfo.getProd_offer_name());
			//package_sale  套包销售标记
			extMap.put("package_sale", package_sale);
					//仓库编码  inventory_code
					item.setOut_house_id("-1");
				//} 
			

			/* ZX add 2015-12-23 end*/
	
		Map<String, String> param = new HashMap<String, String>();
		GoodsGetReq req = new GoodsGetReq();
		String goods_id = phoneInfo.getProd_offer_code();
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
			List activityList = support.queryForList(sql,resp.getData().get("goods_id").toString(),InternetBusiOrder.getOrder_city_code(),InternetBusiOrder.getSerial_time(),user_type);
			
			if(null != activityList && activityList.size() > 0){
				StringBuffer strBuffer = new StringBuffer();
				for (int i = 0; i < activityList.size(); i++) {
					Map m = (Map)activityList.get(i);
					strBuffer.append(m.get("pmt_code").toString()).append(",");
				}					
				String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
				outer.setReserve9(str);
				
				logger.info("总部订单:"+outer.getOut_tid()+"匹配到的活动有:"+str);					
				
//				com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(outer.getOut_tid(), str);
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
			OuterError ng = new OuterError(goods_id, InternetBusiOrder.getSub_prod_info().getSub_prod_code(), InternetBusiOrder.getSub_prod_info().getSub_prod_code(), null, InternetBusiOrder.getSource_system(), InternetBusiOrder.getMall_order_id(), "sysdate", "goodserror");//noparam  nogoods
			ng.setDeal_flag("-1");
			outterECSTmplManager.insertOuterError(ng);
			logger.info("订单:" + InternetBusiOrder.getMall_order_id() +",获取商品相关参数失败======================");
			checkResult = "电商没有配置商品.";
//			continue;
		}
		if ("0".equals(resp.getError_code())) {
			param = resp.getData();
			setPackageGoodsParam(item,param);
			itemList.add(item);
			
		}else {
			OuterError ng = new OuterError(goods_id, InternetBusiOrder.getSub_prod_info().getSub_prod_code(), InternetBusiOrder.getSub_prod_info().getSub_prod_code(), null, InternetBusiOrder.getSource_system(), InternetBusiOrder.getMall_order_id(), "sysdate", "goodserror");//noparam  nogoods
			ng.setDeal_flag("-1");
			outterECSTmplManager.insertOuterError(ng);
			logger.info("订单:" + InternetBusiOrder.getMall_order_id() +",获取商品相关参数失败======================");
			checkResult = "电商没有配置商品.";
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
	private static String checkFixBusiOrder(InternetBusiOrder centerMallOrder){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strTmp = null;
		//序列号
		if (MallUtils.isEmpty(centerMallOrder.getSerial_no())) {
			return "缺少必填字段:serial_no.";
		}
		//时间
		if (MallUtils.isEmpty(centerMallOrder.getSerial_time())) {
			return "缺少必填字段:serial_time.";
		}else {
			try {
				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
				java.util.Date tDate = dateFormator.parse(centerMallOrder.getSerial_time(), new ParsePosition(0));
				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
				centerMallOrder.setSerial_time(dateFormator.format(tDate));
			} catch (Exception e) {
				return "serial_time值不正确.";
			}
		}
		
		//发起方系统标识
		if (MallUtils.isEmpty(centerMallOrder.getSource_system())) {
			return "缺少必填字段:source_system.";
		}else {
			if (!( dbUtils.checkFieldValue("source_system", centerMallOrder.getSource_system()) )) {
				return "source_system值不正确.";
			}
		}
		
		
		//商城系统单号
		if (MallUtils.isEmpty(centerMallOrder.getMall_order_id())) {
			return "缺少必填字段:mall_order_id.";
		}
		
		//订单归属省份
		if (MallUtils.isEmpty(centerMallOrder.getOrder_prov_code())) {
			return "缺少必填字段:order_prov_code.";
		}
		
		//订单归属城市
		if (MallUtils.isEmpty(centerMallOrder.getOrder_city_code())) {
			return "缺少必填字段:order_city_code.";
		}
		
		//订单应收金额
		if (MallUtils.isEmpty(centerMallOrder.getOrder_amount())) {
			return "缺少必填字段:order_amount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getOrder_amount());
				centerMallOrder.setOrder_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "order_amount值不正确.";
			}
		}
		
		//订单实收金额
		if (MallUtils.isEmpty(centerMallOrder.getPay_amount())) {
			return "缺少必填字段:pay_amount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getPay_amount());
				centerMallOrder.setPay_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "pay_amount值不正确.";
			}
		}
		
		//订单减免金额
		if (MallUtils.isEmpty(centerMallOrder.getDiscount_amount())) {
			return "缺少必填字段:discount_amount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getDiscount_amount());
				centerMallOrder.setDiscount_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "discount_amount值不正确.";
			}
		}
			
		InternetBusiPhoneInfo phone_info = centerMallOrder.getPhone_info();
		//号码信息节点校验
		if(phone_info == null) {
			return "缺少必填节点:phone_info.";
		}
		
		if(MallUtils.isEmpty(phone_info.getAcc_nbr())) {
			return "缺少必填字段:acc_nbr";
		}
		
		if(MallUtils.isEmpty(phone_info.getProd_offer_code())) {
			return "缺少必填字段:prod_offer_code";
		}
		
		if(MallUtils.isEmpty(phone_info.getProd_offer_name())) {
			return "缺少必填字段:prod_offer_name";
		}
	
		InternetBusiCustInfo custInfo = centerMallOrder.getCust_info();
		//客户信息节点校验
		if (null == custInfo) {
			return "缺少必填节点:cust_info.";
		} 
		
		//客户姓名
		if(MallUtils.isEmpty(custInfo.getCust_name())){
			return "缺少必填字段:cust_name";
		}
		
		if(MallUtils.isEmpty(custInfo.getCerti_num())){
			return "缺少必填字段:certi_num";
		}
		
		if(MallUtils.isEmpty(custInfo.getCerti_type())){
			return "缺少必填字段:certi_name";
		}
		
		
		
		InternetBusiPayInfo payInfo = centerMallOrder.getPay_info();
		//支付节点校验
		if(null == payInfo){
			return "缺少必填节点:pay_info";
		}
		
			//支付类型
			if(MallUtils.isEmpty(payInfo.getPay_type())){
				return "缺少必填字段:pay_type";
			}
			
			//支付方式
			if(MallUtils.isEmpty(payInfo.getPay_method())){
				return "缺少必填字段:pay_method";
			}
			
//			FixBusiOrderBroadInfo  broad_info = centerMallOrder.getBroad_info();
//		if(broad_info==null){
//			return "缺少必填字段:broad_info";
//		}else{
//			if(MallUtils.isEmpty(broad_info.getAppo_date())){
//				SimpleDateFormat dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_5);
//				java.util.Date tDate = dateFormator.parse(broad_info.getAppo_date()+"000000", new ParsePosition(0));
//				dateFormator = new SimpleDateFormat(DateUtil.DATE_FORMAT_2);
//				broad_info.setAppo_date(format.format(dateFormator));
//			}
//		}
		
		
		
		return "";
	}
	
	
	
}
