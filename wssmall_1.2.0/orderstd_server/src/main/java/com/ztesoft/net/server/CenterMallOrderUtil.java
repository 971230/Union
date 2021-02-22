package com.ztesoft.net.server;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.req.CenterMallOrderStandardReq;
import params.resp.CenterMallOrderStandardResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.zb.vo.orderattr.SubPackage;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.params.goods.req.ActivityListGetReq;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.ActivityListGetResp;
import zte.params.goods.resp.GoodsGetResp;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.Outer;
import com.ztesoft.net.model.OuterItem;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.LocalOrderAttr;
import com.ztesoft.net.outter.inf.model.OuterError;
import com.ztesoft.net.outter.inf.service.SDBUtils;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallActivityInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallAttrPackageSubProd;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallDiscountInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallFeeInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallFlowInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGiftInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsAttInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsFuKaInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsNiceInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsPhoneInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallLeagueInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallOperatorInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallOrder;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallOrderSubProduct;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallPackage;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallPayInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallResourcesInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallSubProdInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallUseCustInfo;
import com.ztesoft.net.service.IOrderServiceLocal;

import consts.ConstsCore;

public class CenterMallOrderUtil {

	private static Logger logger = Logger.getLogger(CenterMallOrderUtil.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static IOrderServiceLocal goodServicesLocal;
	private static IOrderstdService orderstdService;
	private static SDBUtils dbUtils = null;
	private static ICacheUtil cacheUtil;
	public static void initBean(){
		goodServicesLocal = SpringContextHolder.getBean("orderStdServiceLocal");
		orderstdService = SpringContextHolder.getBean("orderstdService");
		outterECSTmplManager = SpringContextHolder.getBean("outterStdTmplManager");
		dbUtils = SpringContextHolder.getBean("sdbUtils");
		cacheUtil = SpringContextHolder.getBean("cacheUtil");
	}
	public static CenterMallOrderStandardResp orderStandardValidate(CenterMallOrderStandardReq req) throws Exception{
		initBean();
		CenterMallOrderStandardResp resp = new CenterMallOrderStandardResp();
		Map extMap = new HashMap<String, String>();
		Map orderMap = new HashMap();
		String outJson = "";
		String checkResult = "";
		boolean errorFlag = false;
		//记录错单需要
		String source_system = "ZB";
		String out_tid = "";
		CenterMallOrder centerMallOrder = null;
		
		String inJson = getReqContent(req.getBase_co_id(), req.isIs_exception());
		req.setReq_content(inJson);
		if(StringUtils.isEmpty(inJson)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未获取到请求报文");
			return resp;
		}
		//1.json转换成对象
	    centerMallOrder = MallUtils.jsonToCenterMallOrder(inJson);
		out_tid = centerMallOrder.getOrderId();
		source_system = centerMallOrder.getOrderAccType();
		//检查参数格式是否正确
		//注意所有金额都在checkCenterMallOrder方法里由厘转换为元
		checkResult = checkCenterMallOrder(centerMallOrder);
		
		if(!StringUtils.isEmpty(checkResult)){
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		//2.把json参数set对Outer中
		List<Outer> out_order_List = new ArrayList<Outer>();
		Outer out_order = new Outer();
		out_order_List.add(out_order);
		if(centerMallOrder.getExt_params()!=null){
			extMap = centerMallOrder.getExt_params();//报文节点中ext_params里面的数据存入扩展数据
		}
		String setValResult = setCenterMallOrderValue(centerMallOrder,out_order , extMap);
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
	private static String setCenterMallOrderValue(CenterMallOrder centerMallOrder , Outer outer , Map extMap){
		String checkResult = "";
		/**
		 * 补充信息,总部没传但订单系统必传
		 */
		//下单时间对应物理库的create_time
		outer.setGet_time(centerMallOrder.getOrderTime());
		//渠道类型  channel_mark  Reserve2
		outer.setReserve2("13");
		//接收方系统标识  receive_system  Reserve0
		outer.setReserve0("10011");
		//订单类型  order_type
		/* 
		 * 预售标识(0:预售) ZX update 2015-12-23 start 
		 * select a.* from es_dc_public_dict_relation a where a.stype='order_type';
		 * type对应外系统5，订单系统07（预售单）
		 * type对应外系统1，订单系统03（订购，正常单）
		 */ 
		if ("0".equals(centerMallOrder.getPreSale())) {
			outer.setType("5");
		} else {
			outer.setType(StringUtils.isNotBlank(centerMallOrder.getPreSale())?centerMallOrder.getPreSale():"");
		}
		/* 预售标识(0:预售) ZX udpate 2015-12-23 end */
		//订单状态  status
		outer.setStatus("1");
		//是否已办理完成  is_deal
		extMap.put("is_deal", "0");
		//买家标识  CustomerId
		extMap.put("uid", centerMallOrder.getCustomerId());
		//实收运费  RealPostFee
		extMap.put("n_shipping_amount", centerMallOrder.getRealPostFee());
		//deliTimeMode  配送时间类型
		extMap.put("shipping_time", centerMallOrder.getDeliTimeMode());
		//ConsiEmail  收件人EMAIL	
		extMap.put("ship_email", centerMallOrder.getConsiEmail());
		//OrderOperType  是否闪电送
		extMap.put("shipping_quick", centerMallOrder.getOrderOperType());
		//仓库名称  inventory_name
		extMap.put("inventory_name", "-1");
		//所属用户  ssyh
		outer.setReserve7("");
		//是否变更套餐
		extMap.put("is_change", "0");
		//订单来源
		extMap.put("source_type", centerMallOrder.getOrderSource());
		//订单接入类别
		extMap.put("regist_type", centerMallOrder.getOrderAccType());
		//退单标识
		outer.setReturn_flag(centerMallOrder.getReturnFlag());
		//ActiveNo  访问流水
		outer.setPayplatformorderid(centerMallOrder.getActiveNo());
		//CustomerId  买家标识/买家ID
		outer.setBuyer_name(centerMallOrder.getCustomerId());
		//RegisterName  买家昵称
		outer.setBuyer_id(centerMallOrder.getRegisterName());
		//ClientIP  买家登录IP
		//ConsiName  收件人姓名
		outer.setReceiver_name(centerMallOrder.getConsiName());;
		//ConsiPhone  收件人联系电话
		outer.setReceiver_mobile(centerMallOrder.getConsiPhone());
		//ConsiTelPhone  收件人联系电话2支持填写固话
		outer.setPhone(centerMallOrder.getConsiTelPhone());
		//BuyerMessage  买家留言
		outer.setBuyer_message(centerMallOrder.getBuyerMessage());
		//ConsiGoodsProv  收货省
		outer.setProvinc_code(centerMallOrder.getConsiGoodsProv());
		String prov_name = goodServicesLocal.queryString("SELECT  a.local_name from es_regions a WHERE a.region_id = '" + centerMallOrder.getConsiGoodsProv() + "' AND ROWNUM < 2");
		outer.setProvince(prov_name);
		//ConsiGoodsCity  收货市
		outer.setCity_code(centerMallOrder.getConsiGoodsCity());
		String city_name = goodServicesLocal.queryString("SELECT  a.local_name from es_regions a WHERE a.region_id = '" + centerMallOrder.getConsiGoodsCity() + "' AND ROWNUM < 2");
		outer.setCity(city_name);
		//ConsiGoodsDist  收货区/县（行政区域）
		outer.setArea_code(centerMallOrder.getConsiGoodsDist());
		String dist_name = null;
		if (MallUtils.isNotEmpty(centerMallOrder.getConsiGoodsDist())) {
			dist_name = goodServicesLocal.queryString("SELECT  a.local_name from es_regions a WHERE a.region_id = '" + centerMallOrder.getConsiGoodsDist() + "' AND ROWNUM < 2");
			outer.setDistrict(dist_name);
		}
		//ConsiPostCode  邮政编码
		outer.setPost(centerMallOrder.getConsiPostCode());
		//ConsiPostAddress  邮寄地址
		outer.setAddress(centerMallOrder.getConsiPostAddress());
		//outer.setAddress(prov_name + " " + city_name + " " + dist_name + " " + centerMallOrder.getConsiPostAddress());
		//ConsiPostRemark  邮寄备注
		outer.setService_remarks(centerMallOrder.getConsiPostRemark());
		//deliMode  配送方式
		outer.setSending_type(centerMallOrder.getDeliMode());
		//OrderId  订单编号
		outer.setOut_tid(centerMallOrder.getOrderId());
		//OrderId  订单ID
		outer.setOrder_num(centerMallOrder.getOrderNum());
		//最终来源商城订单编号
		outer.setSrc_out_tid(centerMallOrder.getOutOrderId());
		//OrderTime  下单时间，YYYY-MM-DD HH24:MI:SS
		outer.setTid_time(centerMallOrder.getOrderTime());
		//OrderProvince  订单省份
		outer.setOrder_provinc_code(centerMallOrder.getOrderProvince());
		//OrderCity  订单地市
		outer.setOrder_city_code(centerMallOrder.getOrderCity());
		//OrderSource  订单来源
		outer.setOrder_from(centerMallOrder.getOrderAccType());
		//OrderAccType  订单接入类别
		outer.setPlat_type(centerMallOrder.getOrderAccType());
		outer.setOrderacctype(centerMallOrder.getOrderAccType());
		outer.setOrder_channel(centerMallOrder.getOrderAccType());
		//OrderAccDesc  订单接入说明
		//OutOrderId  外部订单编号，即父订单编码
		//OrderOrigFee  订单应收总价
		outer.setOrder_totalfee(centerMallOrder.getOrderOrigFee());
		outer.setOrder_origfee(centerMallOrder.getOrderOrigFee());
		//OrderRealFee  订单实收总价
		outer.setOrder_realfee(centerMallOrder.getOrderRealFee());
		//OrderReliefFee  订单减免金额
		outer.setDiscountValue(centerMallOrder.getOrderReliefFee());
		//OrderReliefRes  订单减免原因
		extMap.put("OrderReliefRes",centerMallOrder.getOrderReliefRes());
		//OrigPostFee  应收邮寄费用
		outer.setPost_fee(centerMallOrder.getOrigPostFee());

		//InvoiceNo  发票号码(默认空)
		//InvoiceTitle  发票抬头
		outer.setReserve8(centerMallOrder.getInvoiceTitle());
		//InvoiceType  发票类型
		//总部发票类型都是增值发票(2)
		outer.setInvoice_type(2);
		
		if ("MONTH".equals(centerMallOrder.getInvoiceType())) {
			//发票打印方式  invoice_print_type
			outer.setInvoice_print_type("2");
		}else if ("NOW".equals(centerMallOrder.getInvoiceType())) {
			//发票打印方式  invoice_print_type
			outer.setInvoice_print_type("1");
		}else{
			outer.setInvoice_print_type("3");
		}
		//InvoiceDesc  发票内容
		extMap.put("invoice_group_content", centerMallOrder.getInvoiceDesc());
		
		
		//PayInfo  支付信息
		CenterMallPayInfo payInfo = centerMallOrder.getPayInfo();
		if(null != payInfo){
			//PayType  支付类型
			outer.setPaytype(payInfo.getPayType());
			//PayWay  支付方式
			outer.setPay_method(payInfo.getPayWay());
			//PayFinTime  支付完成时间
			outer.setPay_time(payInfo.getPayFinTime());
			//PayPlatFormOrderId  统一支付订单号
			outer.setPayplatformorderid(payInfo.getPayPlatFormOrderId());
			//PayProviderId  支付机构编码
			outer.setPayproviderid(payInfo.getPayProviderId());
			//PayProviderName  支付机构名称
			outer.setPayprovidername(payInfo.getPayProviderName());
			//PayChannelId  支付渠道编码
			outer.setPaychannelid(payInfo.getPayChannelId());
			//PayChannelName  支付渠道名称
			outer.setPaychannelname(payInfo.getPayChannelName());		
			//支付状态
			if ("HDFK".equalsIgnoreCase(payInfo.getPayType())) {
				outer.setPay_status("0");
			}else {
				outer.setPay_status("1");
			}
		}
		
		//DiscountInfo  优惠信息
		List<CenterMallDiscountInfo> discountInfos = centerMallOrder.getDiscountInfo();
		if(null != discountInfos && discountInfos.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(discountInfos);
			extMap.put("discountInfos", jsonArray.toString());
			//总部商城订单  代金券锁定状态默认为0
			extMap.put(AttrConsts.COUPONS_LOCKED, EcsOrderConsts.COUPONS_STATUS_0);
		}
		
		//GiftInfo  赠品信息
		List<CenterMallGiftInfo> giftInfos = centerMallOrder.getGiftInfo();
		if(null != giftInfos && giftInfos.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(giftInfos);
			extMap.put("giftInfos", jsonArray.toString());
		}
		
		//GoodsInfo  商品信息
		checkResult = setCenterMallGoodsInfo(centerMallOrder,outer,extMap);
		if (!"".equals(checkResult)) {
			return checkResult;
		}
		
		//FlowInfo  流程信息
		CenterMallFlowInfo flowInfo = centerMallOrder.getFlowInfo();
		if(null != flowInfo){
			//暂不处理
		}
		
		//OperatorInfo  操作人信息
		CenterMallOperatorInfo operatorInfo = centerMallOrder.getOperatorInfo();
		if(null != operatorInfo){
			//暂不处理
		}
		
		//LeagueInfo  联盟信息
		CenterMallLeagueInfo leagueInfo = centerMallOrder.getLeagueInfo();
		if(null != leagueInfo){
			//整个节点入库
			JSONArray jsonArray = JSONArray.fromObject(leagueInfo);
			extMap.put("LeagueInfo", jsonArray.toString());
		}
		
		/**
		 * ZX add 2015-12-29 start 责任人使用人信息(集客开户传)
		 */
		CenterMallUseCustInfo useCustInfo = centerMallOrder.getUseCustInfo();
		if (useCustInfo != null) {
			String usecustname = useCustInfo.getUsecustname(); // 使用人责任人姓名
			String usecustpspttype = useCustInfo.getUsecustpspttype(); // 使用人或责任人客户证件类型
			String usecustpsptcode = useCustInfo.getUsecustpsptcode(); // 使用人或责任人客户证件号码
			String usecustpsptaddress = useCustInfo.getUsecustpsptaddress(); // 使用人或责任人证件地址
			String itmprdgrouptype = useCustInfo.getItmprdgrouptype(); // B2B、B2C类集客产品标识(责任人时传'B2B'、'B2C'、'B2B2C'等)
			String itmprdrespobsible = useCustInfo.getItmprdrespobsible(); // 责任人标识(责任人时传1)
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
	private static String setCenterMallGoodsInfo(CenterMallOrder centerMallOrder , Outer outer , Map extMap){
		String checkResult = "";
		List<OuterItem> itemList = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();
		List<CenterMallGoodsInfo> goodsInfos = centerMallOrder.getGoodsInfo();
		for (CenterMallGoodsInfo goodsInfo : goodsInfos) {
			OuterItem item = new OuterItem();
			//GoodsNum  订单中商品的总数量
			item.setPro_num(Integer.parseInt(centerMallOrder.getGoodsNum()));
			//GoodsOrigFee  商品应收
			item.setSell_price(Double.parseDouble(goodsInfo.getGoodsOrigFee()));
			//GoodsRealFee  商品实收
			item.setPro_origfee(Double.parseDouble(goodsInfo.getGoodsRealFee()));
			
			//FeeInfo费用明细
			List<CenterMallFeeInfo> feeInfos = goodsInfo.getFeeInfo();
			if(null != feeInfos && feeInfos.size() > 0){
				JSONArray jsonArray = JSONArray.fromObject(feeInfos);
				extMap.put("feeinfo", jsonArray.toString());
			}
			
			String pagkage_id = "";
			String outer_sku_id = "";
			//查看商品是否存在
			//商品类型
			/*
			 * ZX add 2016-01-06 start 注释
			String goodsType = goodsInfo.getGoodsType();
			* ZX add 2016-01-06 end 注释
			*/
			
			List<CenterMallGoodsAttInfo> goodsAttInfos = goodsInfo.getGoodsAttInfo();
			if(null == goodsAttInfos || goodsAttInfos.size() >= 0){
//				CenterMallGoodsAttInfo goodsAttInfo = goodsAttInfos.get(0); // ZX注释2015-12-23
				/* ZX add 2015-12-23 start*/
				CenterMallGoodsAttInfo goodsAttInfo = null;
				List<CenterMallGoodsFuKaInfo> goodsAttFuKaInfoList = new ArrayList<CenterMallGoodsFuKaInfo>();
				for (CenterMallGoodsAttInfo centerMallGoodsAttInfo : goodsAttInfos) {
					String goodsType = centerMallGoodsAttInfo.getGoodsType();
					if (StringUtils.isBlank(centerMallGoodsAttInfo.getNumType())
							|| centerMallGoodsAttInfo.getNumType().equals(EcsOrderConsts.ZB_CARD_TYPE_ZHU)) {
						goodsAttInfo = centerMallGoodsAttInfo;

				//减免预存标记  ReliefPresFlag
				/**
				 * ZX add 2016年4月5日 18:51:40 start
				 */
				//ifSendPhotos  证件上传状态
				if(!StringUtils.isEmpty(centerMallOrder.getIfSendPhotos())){
					extMap.put(AttrConsts.CERT_UPLOAD_STATUS, centerMallOrder.getIfSendPhotos());
				}
				if (StringUtils.isNotBlank(goodsAttInfo.getRealnameType())) {
					if (goodsAttInfo.getRealnameType().equals("2")) {
						extMap.put("is_examine_card", "0"); // 不需要审核，已实名认证
						extMap.put(AttrConsts.CERT_UPLOAD_STATUS, EcsOrderConsts.CERT_STATUS_NONE);//已经实名认证的，无需上传	
					} else if (goodsAttInfo.getRealnameType().equals("1")) {
						extMap.put("is_examine_card", "1"); // 需要审核，未实名认证
					}
				}
				/**
				 * ZX add 2016年4月5日 18:51:54 end
				 */
				item.setReliefpres_flag(goodsAttInfo.getReliefPresFlag());
				CenterMallActivityInfo activityInfo = null;
				List<CenterMallActivityInfo> activityInfos = goodsAttInfo.getActivityInfo();
				if ( null != activityInfos && activityInfos.size() >= 0 ) {
					activityInfo = activityInfos.get(0);
				}
//				终端机型编码
				CenterMallResourcesInfo resourcesInfo = goodsAttInfo.getResourcesInfo();
				//机型编码  resourcestypeid
				if (null != resourcesInfo) {					
					item.setModel_code(resourcesInfo.getResourcesTypeId());
				}
				//可选包
				List<CenterMallPackage> centerPackages = goodsAttInfo.getPackage();
				if (null != centerPackages && centerPackages.size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(centerPackages);
					extMap.put("zbpackages", jsonArray.toString());
				}
				//号码信息
				CenterMallGoodsPhoneInfo phoneInfo = goodsAttInfo.getPhoneInfo();
				if(null != phoneInfo){
					JSONArray jsonArray = JSONArray.fromObject(phoneInfo);
					extMap.put("phoneInfo", jsonArray.toString());
				}

				/*
				 * ZHKL	总部号卡类	p_code(goodscode) + sn(productcode)
					ZHYL	总部号卡合约类	p_code(ActivityCode) + sn(productcode)
					ZZDL	总部终端合约类	p_code(ActivityCode) + sn(ResourcesTypeId)
					ZSWK	总部上网卡商品	p_code(goodscode) + sn(productcode)
					ZLZD	总部裸终端商品	p_code(取空) + sn(ResourcesTypeId)
					ZPJL	总部配件商品	  p_code(取空) + sn(ResourcesTypeId)
					ZYWL	总部业务变更类	无此业务
				 */
				//总部号卡类/总部号卡合约类
				if("ZHKL".equalsIgnoreCase(goodsType) ){
					pagkage_id = activityInfo == null ? "" : activityInfo.getActivityCode();
					if(pagkage_id == null || "".equalsIgnoreCase(pagkage_id)){
						pagkage_id = goodsInfo.getGoodsCode();
					}
					outer_sku_id = goodsAttInfo.getProductCode();
				}else if ( "ZHYL".equalsIgnoreCase(goodsType)) {
					pagkage_id = activityInfo == null ? "" : activityInfo.getActivityCode();
					outer_sku_id = goodsAttInfo.getProductCode();
				}else if ("ZZDL".equalsIgnoreCase(goodsType)) {
					//总部终端合约类
					pagkage_id = activityInfo == null ? "" : activityInfo.getActivityCode();
					outer_sku_id = resourcesInfo == null ? "" : resourcesInfo.getResourcesTypeId();
				}else if ("ZSWK".equalsIgnoreCase(goodsType)) {
					//总部上网卡商品
					pagkage_id = goodsInfo.getGoodsCode();
					outer_sku_id = goodsAttInfo.getProductCode();
				}else if ("ZPJL".equalsIgnoreCase(goodsType)) {
					//总部配件商品
					pagkage_id = "";
					outer_sku_id = resourcesInfo == null ? "" : resourcesInfo.getResourcesTypeId();
					if (null == resourcesInfo) {
						outer_sku_id = goodsAttInfo.getResourcesTypeId();
					}
				}else if ("ZLZD".equalsIgnoreCase(goodsType)) {
					//总部裸终端商品
					pagkage_id = "";
					outer_sku_id = resourcesInfo == null ? "" : resourcesInfo.getResourcesTypeId();
				}else {
					logger.info("订单:" + centerMallOrder.getOrderId() +",商品模板[" + goodsType + "]在商品系统未配置");
					checkResult = "商品模板[" + goodsType + "]在商品系统未配置.";
					continue;
				}
				//对于未传参数的不处理
				if (MallUtils.isEmpty(pagkage_id) && MallUtils.isEmpty(outer_sku_id)) {
					checkResult = "商品报文异常.";
					continue;
				}
				//是否老用户
				if (MallUtils.isNotEmpty(goodsAttInfo.getUserType()) 
						&& "OLD".equalsIgnoreCase(goodsAttInfo.getUserType())) {
					orderAttr.setIs_old("1");
					if ( MallUtils.isEmpty(goodsAttInfo.getCertType()) 
							|| MallUtils.isEmpty(goodsAttInfo.getCertNum()) ) {
						goodsAttInfo.setCertType("WXZ");
					}
				}else{
					orderAttr.setIs_old("0");
				}
				//FirstMonBillMode  首月资费方式
				item.setFirst_payment(goodsAttInfo.getFirstMonBillMode());
				//PhoneNum  订购号码
				item.setPhone_num(null==goodsAttInfo.getPhoneInfo() ? "" : goodsAttInfo.getPhoneInfo().getPhoneNum());
				//是否2G、3G升4G  is_to4g  Reserve1
//				outer.setOld_net_type(null==goodsAttInfo.getPhoneInfo() ? "" : goodsAttInfo.getPhoneInfo().getNumNet());
//				String mobileNet = null==goodsAttInfo.getPhoneInfo() ? "" :goodsAttInfo.getPhoneInfo().getNumNet();
//				String productNet = goodsAttInfo.getProductNet();
//				String reserve1 = "0";
//				if(!StringUtils.isEmpty(mobileNet)){
//					if(StringUtils.equals(productNet, EcsOrderConsts.NET_TYPE_4G) 
//							&& !StringUtils.equals(mobileNet, EcsOrderConsts.NET_TYPE_4G)){
//						reserve1 = EcsOrderConsts.IS_DEFAULT_VALUE;	
//					}
//				}	
//				outer.setReserve1(reserve1);		
				//CertType  证件类型
				item.setCert_type(goodsAttInfo.getCertType());
				//CertNum  证件号码
				item.setCert_card_num(goodsAttInfo.getCertNum());
				//CertAddr  证件地址
				item.setCert_address(goodsAttInfo.getCertAddr());
				outer.setCert_address(goodsAttInfo.getCertAddr());
				//CustomerName  号卡客户姓名
				item.setPhone_owner_name(goodsAttInfo.getCustomerName());
				//CertLoseTime  证件失效时间
				item.setCert_failure_time(goodsAttInfo.getCertLoseTime());
				//DevelopCode   发展人编码
				outer.setDevelopment_code(goodsAttInfo.getDevelopCode());
				//DevelopName  发展人名称
				outer.setDevelopment_name(goodsAttInfo.getDevelopName());
				//RefereeNum  推荐人号码 
				//outer.setRecommended_phone(goodsAttInfo.getRefereeNum());
				//推荐人手机  reference_phone
				if (MallUtils.isNotEmpty(goodsAttInfo.getRefereeNum())) {
					extMap.put("reference_phone", goodsAttInfo.getRefereeNum());
				}
				//RefereeName  推荐人名称
				outer.setRecommended_name(goodsAttInfo.getRefereeName());
				//ProductNet  产品网别
				item.setProduct_net(goodsAttInfo.getProductNet());
				//ProductCode  产品编码
				item.setProduct_id(goodsAttInfo.getProductCode());
				//ProductName  产品名称
				item.setPro_name(goodsAttInfo.getProductName());
				extMap.put("ProductName", goodsAttInfo.getProductName());
				//ProductBrand  产品品牌
				orderAttr.setPro_brand(goodsAttInfo.getProductBrand());
				//CardType  卡类型
				item.setWhite_cart_type(goodsAttInfo.getCardType());
				orderAttr.setBill_type("10");
				orderAttr.setGuarantor("无");
				String package_sale = null;
				if(StringUtils.isEmpty(goodsAttInfo.getSaleMode())||StringUtils.equals("FCPB", goodsAttInfo.getSaleMode())){
					package_sale = "0";
				}else if(StringUtils.equals("CPB", goodsAttInfo.getSaleMode())){
					package_sale = "1";
				}
				orderAttr.setPackage_sale(package_sale);
				//客户类型  CustomerType
				extMap.put("CustomerType", goodsAttInfo.getCustomerType());
				//ProPacCode  产品包编码
				extMap.put("ProPacCode", goodsAttInfo.getProPacCode());
				//ProPacDesc  产品包说明
				extMap.put("ProPacDesc", goodsAttInfo.getProPacDesc());
				//GoodsName 商品名称
				extMap.put("GoodsName", goodsInfo.getGoodsName());
				//package_sale  套包销售标记
				extMap.put("package_sale", package_sale);

						/*ZX add 2015-12-25 集团ID start*/
						// groupId 集团编码对应属性处理器的group_code
						// is_old 对应报文中的 入网类型 user_type
						if (EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equalsIgnoreCase(goodsType)
								||EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equalsIgnoreCase(goodsType)
								||EcsOrderConsts.ZB_GOODS_TYPE_ZZDL.equalsIgnoreCase(goodsType)
								||EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equalsIgnoreCase(goodsType)) {
							extMap.put("app_type", goodsAttInfo.getAppType());
							extMap.put("sub_app_type", goodsAttInfo.getSubAppType());
							extMap.put("group_code", goodsAttInfo.getGroupId());
						}
						/*ZX add 2015-12-25 集团ID end*/
						
						/*
						 * ZX add 2016-01-07 start 入网类型
						 */
						if (EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equalsIgnoreCase(goodsType)
								||EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equalsIgnoreCase(goodsType)
								||EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equalsIgnoreCase(goodsType)) {
							String userType = goodsAttInfo.getUserType();
							if (StringUtils.isNotBlank(userType)) {
								userType = CommonDataFactory.getInstance().getDictCodeValue("user_type", userType);
//								if (userType.equals(EcsOrderConsts.ZB_USER_TYPE_NEW)) {
//									userType = EcsOrderConsts.IS_OLD_0;
//								} else if (userType.equals(EcsOrderConsts.ZB_USER_TYPE_OLD)) {
//									userType = EcsOrderConsts.IS_OLD_1;
//								}
							}
							extMap.put("is_old", userType);
						}
						/*
						 * ZX add 2016-01-07 end 入网类型
						 */
						/*
						 * ZX add 2016-01-08 start 附加产品信息
						 */
						List<CenterMallOrderSubProduct> centerMallOrderSubProducts = new ArrayList<CenterMallOrderSubProduct>();
						List<CenterMallSubProdInfo> subProdInfos = goodsAttInfo.getSubProdInfo();
						if (subProdInfos != null && subProdInfos.size() > 0) {
							for (CenterMallSubProdInfo subProdInfo : subProdInfos) {
								CenterMallOrderSubProduct centerMallOrderSubProduct = new CenterMallOrderSubProduct();
								centerMallOrderSubProduct.setSub_pro_code(subProdInfo.getSubProCode());
								centerMallOrderSubProduct.setSub_pro_desc(subProdInfo.getSubProDesc());
								centerMallOrderSubProduct.setSub_pro_name(subProdInfo.getSubProName());
								centerMallOrderSubProduct.setSub_prod_type(EcsOrderConsts.ZB_CARD_TYPE_ZHU);
								List<SubPackage> subPackages = subProdInfo.getSubPackage();
								if (subPackages != null && subPackages.size() > 0) {
									List<CenterMallAttrPackageSubProd> centerMallAttrPackageSubProds = new ArrayList<CenterMallAttrPackageSubProd>();
									for (SubPackage subPackage : subPackages) {
										CenterMallAttrPackageSubProd centerMallAttrPackageSubProd = new CenterMallAttrPackageSubProd();
										centerMallAttrPackageSubProd.setPackage_code(subPackage.getSubPackageCode());
										centerMallAttrPackageSubProd.setPackage_name(subPackage.getSubPackageName());
										centerMallAttrPackageSubProd.setElement_code(subPackage.getSubElementCode());
										centerMallAttrPackageSubProd.setElement_type(subPackage.getSubElementType());
										centerMallAttrPackageSubProd.setElement_name(subPackage.getSubElementName());
										centerMallAttrPackageSubProds.add(centerMallAttrPackageSubProd);
									}
									centerMallOrderSubProduct.setSubPackage(centerMallAttrPackageSubProds);
								}
								centerMallOrderSubProducts.add(centerMallOrderSubProduct);
							}
							JSONArray json_subprodinfos = JSONArray.fromObject(centerMallOrderSubProducts);
							extMap.put("zb_sub_prod", json_subprodinfos.toString());
						}
						/*
						 * ZX add 2016-01-08 end 附加产品信息
						 */

						/*
						 * Rapon add 2016-04-08 start 合约信息
						 */
						if (activityInfos != null && activityInfos.size() > 0) {
							JSONArray json_activities = JSONArray.fromObject(activityInfos);
							extMap.put("zb_activity", json_activities.toString());
						}
						/*
						 * Rapon add 2016-04-08 end 合约信息
						 */
						//仓库编码  inventory_code
						item.setOut_house_id("-1");
					} else {
						if (EcsOrderConsts.ZB_GOODS_TYPE_HKFK.equalsIgnoreCase(goodsType) 
								||EcsOrderConsts.ZB_GOODS_TYPE_WKFK.equalsIgnoreCase(goodsType)) {
							goodsAttInfo = centerMallGoodsAttInfo;
							CenterMallGoodsFuKaInfo fuka = new CenterMallGoodsFuKaInfo();
							fuka.setOrder_from(centerMallOrder.getOrderAccType()); //订单来源
							fuka.setPhone_num(goodsAttInfo.getPhoneInfo()!=null?goodsAttInfo.getPhoneInfo().getPhoneNum():""); //手机号码
							fuka.setUser_type(CommonDataFactory.getInstance().getDictCodeValue("user_type", goodsAttInfo.getUserType())); //新老用户标记
							fuka.setCard_type(goodsAttInfo.getCardType()!=null?goodsAttInfo.getCardType():""); //卡类型
							fuka.setNet_type(CommonDataFactory.getInstance().getDictCodeValue("user_type", goodsAttInfo.getUserType())); //入网类型
							fuka.setNum_type(goodsAttInfo.getNumType()!=null?goodsAttInfo.getNumType():""); //主副卡标记
							fuka.setProd_id(goodsAttInfo.getProductCode()!=null?goodsAttInfo.getProductCode():""); //产品编码
							fuka.setProd_name(goodsAttInfo.getProductName()!=null?goodsAttInfo.getProductName():""); //产品名称
							fuka.setProd_type(goodsAttInfo.getProductType()!=null?goodsAttInfo.getProductType():""); //产品类型
							fuka.setProd_net_type(goodsAttInfo.getProductNet()!=null?goodsAttInfo.getProductNet():""); //产品网别
							fuka.setProd_bran_code(goodsAttInfo.getProductBrand()!=null?goodsAttInfo.getProductBrand():""); //产品品牌编码
							fuka.setFirst_fee_type(goodsAttInfo.getFirstMonBillMode()!=null?goodsAttInfo.getFirstMonBillMode():""); //首月资费类型
							if (goodsAttInfo.getActivityInfo() != null && goodsAttInfo.getActivityInfo().size() > 0) {
								// 默认取第一条
								fuka.setActivityType(goodsAttInfo.getActivityInfo().get(0)!=null?goodsAttInfo.getActivityInfo().get(0).getActivityType():""); //合约类型
								fuka.setActivityCode(goodsAttInfo.getActivityInfo().get(0)!=null?goodsAttInfo.getActivityInfo().get(0).getActivityCode():""); //合约编码
								fuka.setActivityName(goodsAttInfo.getActivityInfo().get(0)!=null?goodsAttInfo.getActivityInfo().get(0).getActivityName():""); //合约名称
								fuka.setActProtPer(goodsAttInfo.getActivityInfo().get(0)!=null?goodsAttInfo.getActivityInfo().get(0).getActProtPer():""); //合约期限
							}
							fuka.setProkey(goodsAttInfo.getPhoneInfo()!=null?goodsAttInfo.getPhoneInfo().getProKey():""); //预占关键字
							fuka.setOccupied_flag(goodsAttInfo.getPhoneInfo()!=null?goodsAttInfo.getPhoneInfo().getOperatorState():""); //操作类型
							fuka.setResult_state(goodsAttInfo.getPhoneInfo()!=null?goodsAttInfo.getPhoneInfo().getOperatorState():""); //操作结果状态
							fuka.setResult_msg("成功！"); //操作结果描述
							fuka.setBss_occupied_flag(goodsAttInfo.getPhoneInfo()!=null?goodsAttInfo.getPhoneInfo().getOperatorState():""); //BSS操作类型
							fuka.setOper_id(""); //预占工号
							fuka.setCert_type(goodsAttInfo.getCertType()!=null?goodsAttInfo.getCertType():""); // 副卡证件类型
							fuka.setCert_name(goodsAttInfo.getCustomerName()!=null?goodsAttInfo.getCustomerName():""); // 副卡名称
							fuka.setCert_num(goodsAttInfo.getCertNum()!=null?goodsAttInfo.getCertNum():""); // 副卡证件号码
							
							CenterMallGoodsPhoneInfo phoneInfo = goodsAttInfo.getPhoneInfo();
							if (phoneInfo != null) {
								CenterMallGoodsNiceInfo niceInfo = phoneInfo.getNiceInfo();
								if (niceInfo != null) {
									fuka.setAdvancepay(niceInfo.getAdvancePay()!=null?niceInfo.getAdvancePay():""); // 预存话费金额
									fuka.setAdvancecom(niceInfo.getAdvanceCom()!=null?niceInfo.getAdvanceCom():""); // 普通预存
									fuka.setAdvancespe(niceInfo.getAdvanceSpe()!=null?niceInfo.getAdvanceSpe():""); // 专项预存
									fuka.setNumthawtim(niceInfo.getNumThawTim()!=null?niceInfo.getNumThawTim():""); // 返还时长
									fuka.setNumthawpro(niceInfo.getNumThawPro()!=null?niceInfo.getNumThawPro():""); // 分月返还金额
									fuka.setClassid(niceInfo.getClassId()!=null?niceInfo.getClassId():""); // 号码等级
									fuka.setLowcostche(niceInfo.getLowCostChe()!=null?niceInfo.getLowCostChe():""); // 考核期最低消费
									fuka.setTimedurche(niceInfo.getTimeDurChe()!=null?niceInfo.getTimeDurChe():""); // 考核时长
									fuka.setChangetagche(niceInfo.getChangeTagChe()!=null?niceInfo.getChangeTagChe():""); // 考核期是否过户
									fuka.setCanceltagche(niceInfo.getCancelTagChe()!=null?niceInfo.getCancelTagChe():""); // 考核期是否销户
									fuka.setBremonche(niceInfo.getBremonChe()!=null?niceInfo.getBremonChe():""); // 考核期违约金月份
									fuka.setLowcostpro(niceInfo.getLowCostPro()!=null?niceInfo.getLowCostPro():""); // 协议期最低消费
									fuka.setTimedurpro(niceInfo.getTimeDurPro()!=null?niceInfo.getTimeDurPro():""); // 协议时长
									fuka.setChangetagpro(niceInfo.getChangeTagPro()!=null?niceInfo.getChangeTagPro():""); // 协议期是否过户
									fuka.setCanceltagpro(niceInfo.getCancelTagPro()!=null?niceInfo.getCancelTagPro():""); // 协议期是否销户
									fuka.setBromonpro(niceInfo.getBroMonPro()!=null?niceInfo.getBroMonPro():""); // 协议期违约金月份
								}
								fuka.setProkeymode(phoneInfo.getProKeyMode()!=null?phoneInfo.getProKeyMode():""); // 号码资源预占关键字类型 0：随机码 1：客户标识（或登录名） 2：订单标识 3：淘宝序列号
								fuka.setOccupiedtime(phoneInfo.getOccupiedTime()!=null?phoneInfo.getOccupiedTime():""); // 操作时间
							}
							fuka.setCertlosetime(goodsAttInfo.getCertLoseTime()); // 证件失效时间YYYY-MM-DD HH24:MI:SS
							fuka.setCertaddr(goodsAttInfo.getCertAddr()); // 证件地址
							fuka.setSex(goodsAttInfo.getSex()); // 性别，固定长度1位，M：男， F：女
							fuka.setReliefpresflag(goodsAttInfo.getReliefPresFlag()); // 减免预存标记
							fuka.setSalemode(goodsAttInfo.getSaleMode()); // 销售方式
							fuka.setSimid(goodsAttInfo.getSimId()); // SIM卡号
							fuka.setGoodstype(goodsAttInfo.getGoodsType()); // 商品类型
							fuka.setSertype(goodsAttInfo.getSerType()); // 付费类型
							fuka.setChecktype(goodsAttInfo.getCheckType()); // 认证类型：cbss系统订单必传01：本地认证02：公安认证03：二代证读卡器
							fuka.setRealnametype(goodsAttInfo.getRealnameType()); // 实名认证类型：1：未实名认证2：已实名认证
							fuka.setPackages(goodsAttInfo.getPackage());
							
							/*
							 * ZX add 2016-01-08 start 附加产品信息
							 */
							List<CenterMallOrderSubProduct> centerMallOrderSubProducts = new ArrayList<CenterMallOrderSubProduct>();
							List<CenterMallSubProdInfo> subProdInfos = goodsAttInfo.getSubProdInfo();
							if (subProdInfos != null && subProdInfos.size() > 0) {
								for (CenterMallSubProdInfo subProdInfo : subProdInfos) {
									CenterMallOrderSubProduct centerMallOrderSubProduct = new CenterMallOrderSubProduct();
									centerMallOrderSubProduct.setSub_pro_code(subProdInfo.getSubProCode());
									centerMallOrderSubProduct.setSub_pro_desc(subProdInfo.getSubProDesc());
									centerMallOrderSubProduct.setSub_pro_name(subProdInfo.getSubProName());
									centerMallOrderSubProduct.setSub_prod_type(EcsOrderConsts.ZB_CARD_TYPE_FU);
									List<SubPackage> subPackages = subProdInfo.getSubPackage();
									if (subPackages != null && subPackages.size() > 0) {
										List<CenterMallAttrPackageSubProd> centerMallAttrPackageSubProds = new ArrayList<CenterMallAttrPackageSubProd>();
										for (SubPackage subPackage : subPackages) {
											CenterMallAttrPackageSubProd centerMallAttrPackageSubProd = new CenterMallAttrPackageSubProd();
											centerMallAttrPackageSubProd.setPackage_code(subPackage.getSubPackageCode());
											centerMallAttrPackageSubProd.setPackage_name(subPackage.getSubPackageName());
											centerMallAttrPackageSubProd.setElement_code(subPackage.getSubElementCode());
											centerMallAttrPackageSubProd.setElement_type(subPackage.getSubElementType());
											centerMallAttrPackageSubProd.setElement_name(subPackage.getSubElementName());
											centerMallAttrPackageSubProds.add(centerMallAttrPackageSubProd);
										}
										centerMallOrderSubProduct.setSubPackage(centerMallAttrPackageSubProds);
									}
									centerMallOrderSubProducts.add(centerMallOrderSubProduct);
								}
							}
							/*
							 * ZX add 2016-01-08 end 附加产品信息
							 */
							fuka.setSubProdInfo(centerMallOrderSubProducts);

							/*
							 * Rapon add 2016-04-08 start 合约信息
							 */
							List<CenterMallActivityInfo> activityInfos = goodsAttInfo.getActivityInfo();
							fuka.setActivityInfo(activityInfos);
							/*
							 * Rapon add 2016-04-08 end 合约信息
							 */
							goodsAttFuKaInfoList.add(fuka);
						}
					}
				}
				JSONArray jsonArray = JSONArray.fromObject(goodsAttFuKaInfoList);
				extMap.put("zb_fuka_info", jsonArray.toString());
				/* ZX add 2015-12-23 end*/
				//是否后激活订单
				extMap.put(AttrConsts.LATER_ACT_FLAG, goodsAttInfo.getLaterActFlag());
			}
			
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			Map<String, String> param = new HashMap<String, String>();
			GoodsGetReq req = new GoodsGetReq();
			req.setPackage_id(pagkage_id);
			req.setSn(outer_sku_id);
			req.setSourceFrom(ManagerUtils.getSourceFrom());
			GoodsGetResp resp = null;
			try {
				ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
				resp = client.execute(req, GoodsGetResp.class);
				
				//add by zou.qh 20160930 匹配不到商品，给一个默认的商品，订单匹配独立生产模式 ，一期功能
				String defaultGoods = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_DEFAULT");
				if(Consts.ERROR_FAIL.equals(resp.getError_code()) && ConstsCore.CONSTS_YES.equals(defaultGoods)){
					String default_sn = "";//sn
					String default_pcode = "";//p_code
					//获取商品类型
					String goodsType = "";
					if(goodsAttInfos != null && goodsAttInfos.size() > 0){
						for(CenterMallGoodsAttInfo centerMallGoodsAttInfo : goodsAttInfos){
							goodsType = centerMallGoodsAttInfo.getGoodsType();
						}
					}
					//根据总部商品类型设置默认商品
					/*
			         * ZHKL 总部号卡类 p_code(goodscode) + sn(productcode)
			          ZHYL  总部号卡合约类 p_code(ActivityCode) + sn(productcode)
			          ZZDL  总部终端合约类 p_code(ActivityCode) + sn(ResourcesTypeId)
			          ZSWK  总部上网卡商品 p_code(goodscode) + sn(productcode)
			          ZLZD  总部裸终端商品 p_code(取空) + sn(ResourcesTypeId)
			          ZPJL  总部配件商品    p_code(取空) + sn(ResourcesTypeId)
			          ZYWL  总部业务变更类 无此业务
			         */
					if("ZHKL".equals(goodsType)){
						default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_ZHKL");
						default_pcode = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_PCODE_ZHKL");
					}else if("ZHYL".equals(goodsType)){
						default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_ZHYL");
						default_pcode = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_PCODE_ZHYL");
					}else if("ZZDL".equals(goodsType)){
						default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_ZZDL");
						default_pcode = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_PCODE_ZZDL");
					}else if("ZSWK".equals(goodsType)){
						default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_ZSWK");
						default_pcode = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_PCODE_ZSWK");
					}else if("ZLZD".equals(goodsType)){
						default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_ZLZD");
					}else if("ZPJL".equals(goodsType)){
						default_sn = cacheUtil.getConfigInfo("ORDER_STD_WITH_GOODS_SN_ZPJL");
					}else if("ZYWL".equals(goodsType)){
						
					}
					if(StringUtils.isEmpty(default_sn)){
						throw new Exception("根据商品编码未匹配到商品，且没有配置默认商品");
					}
					req = new GoodsGetReq();
					req.setSn(default_sn);
					req.setPackage_id(default_pcode);
					resp = client.execute(req, GoodsGetResp.class);
				}
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
				/*String sql = "select c.goods_id, c.pmt_id, d.pmta_id, d.pmt_type, d.pmt_solution, " +
						" e.name,e.pmt_code, e.begin_time, e.end_time, e.status_date, f.org_id from es_promotion d," +
						" es_promotion_activity e, es_pmt_goods c, es_activity_co f where " +
						" c.goods_id = ? and c.pmt_id = d.pmt_id" +
						" and d.pmta_id = e.id and e.id = f.activity_id and f.org_id = '10003' and f.status='1' " +
						" and (e.region = '"+cacheUtil.getConfigInfo("PROVINCE_REGION_CODE")+"' or instr(e.region,?)>0) and " +
						" to_date(?,'yyyy-mm-dd hh24:mi:ss') between " +
						" e.begin_time and e.end_time and f.source_from = 'ECS' and f.source_from = d.source_from " +
						" and f.source_from = e.source_from and f.source_from = c.source_from " +
						" and d.pmt_type in  ('006','011') and e.enable=1 and e.user_type in (1,?) order by e.status_date";
				logger.info(sql);
				IDaoSupport support = SpringContextHolder.getBean("baseDaoSupport");
				List activityList = support.queryForList(sql,resp.getData().get("goods_id").toString(),centerMallOrder.getOrderCity(),centerMallOrder.getOrderTime(),user_type);*/
				if(resp.getData()!=null && !StringUtils.isEmpty(resp.getData().get("goods_id"))){
					ActivityListGetReq actListGetReq = new ActivityListGetReq();
					actListGetReq.setGoodsId(resp.getData().get("goods_id"));
					actListGetReq.setOrderCity(centerMallOrder.getOrderCity());
					actListGetReq.setOrderTime(centerMallOrder.getOrderTime());
					actListGetReq.setUserType(user_type);
					
					ActivityListGetResp actListGetResp = client.execute(actListGetReq, ActivityListGetResp.class);
					List<Map> activityList = actListGetResp.getActList();
					if(null != activityList && activityList.size() > 0){
						StringBuffer strBuffer = new StringBuffer();
						for (int i = 0; i < activityList.size(); i++) {
							Map m = activityList.get(i);
							strBuffer.append(m.get("pmt_code").toString()).append(",");
						}					
						String str = strBuffer.substring(0, strBuffer.lastIndexOf(","));
						outer.setReserve9(str);
						
						logger.info("总部订单:"+outer.getOut_tid()+"匹配到的活动有:"+str);					
						
//						com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(outer.getOut_tid(), str);
						extMap.put("proactivity", str);
						
						//屬於給es_outer_accept的reserve9做的限制,因爲該表的reserve9字段爲varchar2(128)
						/*if(str.length() > 128){
							outer.setReserve9(str.substring(0,128));
						}else{
							outer.setReserve9(str);
						}*/
					}
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
				OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, null, centerMallOrder.getOrderAccType(), centerMallOrder.getOrderId(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + centerMallOrder.getOrderId() +",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
				continue;
			}
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				setPackageGoodsParam(item,param);
				itemList.add(item);
				
			}else {
				OuterError ng = new OuterError(pagkage_id, outer_sku_id, outer_sku_id, null, centerMallOrder.getOrderAccType(), centerMallOrder.getOrderId(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + centerMallOrder.getOrderId() +",获取商品相关参数失败======================");
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
	 * 获取总部商城请求的json数据
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
	 * 检查总部订单数据是否正常
	 * @param centerMallOrder
	 * @return
	 */
	private static String checkCenterMallOrder(CenterMallOrder centerMallOrder){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strTmp = null;
		//访问流水
		if (MallUtils.isEmpty(centerMallOrder.getActiveNo())) {
			return "缺少必填字段:ActiveNo.";
		}
		//买家标识/买家ID
/*		if (MallUtils.isEmpty(centerMallOrder.getCustomerId())) {
			return "缺少必填字段:CustomerId.";
		}*/
		//买家昵称
/*		if (MallUtils.isEmpty(centerMallOrder.getRegisterName())) {
			return "缺少必填字段:RegisterName.";
		}*/
		//收件人姓名
		if (MallUtils.isEmpty(centerMallOrder.getConsiName())) {
			return "缺少必填字段:ConsiName.";
		}
		//收件人证件类型
		if (MallUtils.isNotEmpty(centerMallOrder.getConsiCertType())) {
			if (! ( dbUtils.checkFieldValue("ConsiCertType", centerMallOrder.getConsiCertType()) ) ) {
				return "ConsiCertType值不正确.";
			}
		}
		//收件人联系电话1
		if (MallUtils.isEmpty(centerMallOrder.getConsiPhone())) {
			return "缺少必填字段:ConsiPhone.";
		}else {
			if (!"CRAWLER".equals(centerMallOrder.getOrderAccType())&&centerMallOrder.getConsiPhone().length() != 11) {
				return "ConsiPhone值("+centerMallOrder.getConsiPhone()+")不正确.";
			}
		}
		if (centerMallOrder.getDeliMode().equals(EcsOrderConsts.SENDING_TYPE_NO)) { // ZX add 2016年3月22日 16:16:01 增加if判断（无需配送不校验）
		} else {
		//收货省
		if (MallUtils.isEmpty(centerMallOrder.getConsiGoodsProv())) {
			return "缺少必填字段:ConsiGoodsProv.";
		}else {
			//入库省份并校验
		}
		//收货市
		if (MallUtils.isEmpty(centerMallOrder.getConsiGoodsCity())) {
			return "缺少必填字段:ConsiGoodsCitfy.";
		}else {
			//入库地市并校验
		}
		//收货区/县（行政区域）
//		if (MallUtils.isEmpty(centerMallOrder.getConsiGoodsDist())) {
//			return "缺少必填字段:ConsiGoodsDist.";
//		}else {
//			//入库区并校验
//		}
		//邮寄地址
		if (MallUtils.isEmpty(centerMallOrder.getConsiPostAddress())) {
			return "缺少必填字段:ConsiPostAddress.";
		}
		}
		//是否闪电送
		if (MallUtils.isEmpty(centerMallOrder.getOrderOperType())) {
			return "缺少必填字段:OrderOperType.";
		}else {
			if (!( dbUtils.checkFieldValue("OrderOperType", centerMallOrder.getOrderOperType()) )) {
				return "OrderOperType值不正确.";
			}
		}
		//配送方式
		if (MallUtils.isEmpty(centerMallOrder.getDeliMode())) {
			return "缺少必填字段:deliMode.";
		}else {
			if (!( dbUtils.checkFieldValue("deliMode", centerMallOrder.getDeliMode()) )) {
				return "deliMode值不正确.";
			}
		}		
		//配送时间类型
		if (!"ZT".equalsIgnoreCase(centerMallOrder.getDeliMode())
				&& !EcsOrderConsts.SENDING_TYPE_NO.equalsIgnoreCase(centerMallOrder.getDeliMode())) { //ZX add 2016年3月22日 17:41:44 增加无需配送条件
			if (MallUtils.isEmpty(centerMallOrder.getDeliTimeMode())) {
				return "缺少必填字段:deliTimeMode.";
			}else {
				if (!( dbUtils.checkFieldValue("deliTimeMode", centerMallOrder.getDeliTimeMode()) )) {
					return "deliTimeMode值不正确.";
				}
			}
		}
		//发票类型
		if (MallUtils.isNotEmpty(centerMallOrder.getInvoiceType())) {
			if (!( dbUtils.checkFieldValue("InvoiceType", centerMallOrder.getInvoiceType()) )) {
				return "InvoiceType值不正确.";
			}else if("NOW".equals(centerMallOrder.getInvoiceType())){
				//即时发票时必填  不必传
				if (MallUtils.isEmpty(centerMallOrder.getInvoiceDesc())) {
					return "缺少必填字段:InvoiceDesc.";
				}
				if (MallUtils.isEmpty(centerMallOrder.getInvoiceTitle())) {
					return "缺少必填字段:InvoiceTitle.";
				}
			}
		}
		//发票内容
		if (MallUtils.isNotEmpty(centerMallOrder.getInvoiceDesc()) && !"MX".equalsIgnoreCase(centerMallOrder.getInvoiceDesc())) {
			return "InvoiceDesc值不正确.";
		}
		//订单编号
		if (MallUtils.isEmpty(centerMallOrder.getOrderId())) {
			return "缺少必填字段:OrderId.";
		}
		//下单时间
		if (MallUtils.isEmpty(centerMallOrder.getOrderTime())) {
			return "缺少必填字段:OrderTime.";
		}else {
			try {
				format.parse(centerMallOrder.getOrderTime());
			} catch (Exception e) {
				return "OrderTime值不正确.";
			}
		}
		//订单省份
		if (MallUtils.isEmpty(centerMallOrder.getOrderProvince())) {
			return "缺少必填字段:OrderProvince.";
		}else {
			//入库校验
		}
		//订单地市
		if (MallUtils.isEmpty(centerMallOrder.getOrderCity())) {
			return "缺少必填字段:OrderCity.";
		}else {
			//入库校验
		}
		//订单来源
		if (MallUtils.isEmpty(centerMallOrder.getOrderSource())) {
			return "缺少必填字段:OrderSource.";
		}else {
			if (!( dbUtils.checkFieldValue("OrderSource", centerMallOrder.getOrderSource()) )) {
				return "OrderSource值不正确.";
			}
		}
		//订单接入类别
		if (MallUtils.isEmpty(centerMallOrder.getOrderAccType())) {
			return "缺少必填字段:OrderAccType.";
		}else {
			if (!( dbUtils.checkFieldValue("OrderAccType", centerMallOrder.getOrderAccType()) )) {
				return "OrderAccType值不正确.";
			}
		}
		//订单应收总价
		if (MallUtils.isEmpty(centerMallOrder.getOrderOrigFee())) {
			return "缺少必填字段:OrderOrigFee.";
		}else {
			try {
				//单位转换为元
				Integer t = Integer.parseInt(centerMallOrder.getOrderOrigFee());
				centerMallOrder.setOrderOrigFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "OrderOrigFee值不正确.";
			}
		}
		//订单实收总价
		if (MallUtils.isEmpty(centerMallOrder.getOrderRealFee())) {
			return "缺少必填字段:OrderRealFee.";
		}else {
			try {
				//单位转换为元
				Integer t = Integer.parseInt(centerMallOrder.getOrderRealFee());
				centerMallOrder.setOrderRealFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "OrderRealFee值不正确.";
			}
		}
		//订单减免金额
		if (MallUtils.isEmpty(centerMallOrder.getOrderReliefFee())) {
			return "缺少必填字段:OrderReliefFee.";
		}else {
			try {
				//单位转换为元			
				Integer t = Integer.parseInt(centerMallOrder.getOrderReliefFee());
				centerMallOrder.setOrderReliefFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "OrderReliefFee值不正确.";
			}
		}
		//应收邮寄费用
		if (MallUtils.isEmpty(centerMallOrder.getOrigPostFee())) {
			return "缺少必填字段:OrigPostFee.";
		}else {
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getOrigPostFee());
				centerMallOrder.setOrigPostFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "OrigPostFee值不正确.";
			}
		}
		//实收邮寄费用
		if (MallUtils.isEmpty(centerMallOrder.getRealPostFee())) {
			return "缺少必填字段:RealPostFee.";
		}else {
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(centerMallOrder.getRealPostFee());
				centerMallOrder.setRealPostFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "RealPostFee值不正确.";
			}
		}
		
		//PayInfo 支付信息检查
		CenterMallPayInfo payInfo = centerMallOrder.getPayInfo();
		if (null == payInfo) {
			return "缺少必填字段:payInfo.";
		}else {
			strTmp = checkPayInfo(payInfo);
			if ( !("".equals(strTmp)) ) {
				return strTmp;
			}
		}
		
		//DiscountInfo 优惠信息
		List<CenterMallDiscountInfo> discountInfos = centerMallOrder.getDiscountInfo();
		if(null != discountInfos && discountInfos.size() > 0){
			for (CenterMallDiscountInfo centerMallDiscountInfo : discountInfos) {
				strTmp = checkDiscountInfo(centerMallDiscountInfo);
				if ( !"".equals(strTmp)) {
					return strTmp;
				}
			}
		}
		//订单中商品的总数量
		if (MallUtils.isEmpty(centerMallOrder.getGoodsNum())) {
			return "缺少必填字段:GoodsNum.";
		}else {
			try {
				Integer t = Integer.parseInt(centerMallOrder.getGoodsNum());
			} catch (Exception e) {
				return "GoodsNum值不正确.";
			}
		}
		
		//GoodsInfo  商品信息
		List<CenterMallGoodsInfo> goodsInfos = centerMallOrder.getGoodsInfo();
		if (null == goodsInfos || goodsInfos.size() == 0) {
			return "缺少必填字段:GoodsInfo.";
		}else {
			for (CenterMallGoodsInfo centerMallGoodsInfo : goodsInfos) {
				strTmp = checkGoodsInfo(centerMallGoodsInfo);
				if ( !("".equals(strTmp)) ) {
					return strTmp;
				}
			}
		}
		
		//GiftInfo  赠品信息
		List<CenterMallGiftInfo> giftInfos = centerMallOrder.getGiftInfo();
		if (null != giftInfos && giftInfos.size() > 0) {
			for (CenterMallGiftInfo centerMallGiftInfo : giftInfos) {
				strTmp = checkGiftInfo(centerMallGiftInfo);
				if ( !("".equals(strTmp)) ) {
					return strTmp;
				}
			}
		}
		
		//FlowInfo  流程信息
		CenterMallFlowInfo flowInfo = centerMallOrder.getFlowInfo();
		if (null != flowInfo) {
			if (MallUtils.isNotEmpty(flowInfo.getOperMode())) {
				if (!( "AUTO".equalsIgnoreCase(flowInfo.getOperMode()) || "MAN".equalsIgnoreCase(flowInfo.getOperMode()) ||
						"LIVE".equalsIgnoreCase(flowInfo.getOperMode()) || "EXP".equalsIgnoreCase(flowInfo.getOperMode()) )) {
					return "OperMode值不正确.";
				}
			}
			if (MallUtils.isNotEmpty(flowInfo.getOrderFlow())) {
				if (!( "P".equalsIgnoreCase(flowInfo.getOrderFlow()) || "O".equalsIgnoreCase(flowInfo.getOrderFlow()) ||
						"W".equalsIgnoreCase(flowInfo.getOrderFlow()) || "D".equalsIgnoreCase(flowInfo.getOrderFlow()) ||
						"C".equalsIgnoreCase(flowInfo.getOrderFlow()) || "P-O-W-D-C".equalsIgnoreCase(flowInfo.getOrderFlow()) ||
						"P-O-D-C".equalsIgnoreCase(flowInfo.getOrderFlow()) || "P-D-C".equalsIgnoreCase(flowInfo.getOrderFlow()) ||
						"O-C".equalsIgnoreCase(flowInfo.getOrderFlow()) )) {
					return "OrderFlow值不正确.";
				}
			}
		}
		
		//OperatorInfo  操作人信息   不需要校验

		return "";
	}
	
	/**
	 * 检查赠品信息
	 * @param centerMallGiftInfo
	 * @return
	 */
	private static String checkGiftInfo(CenterMallGiftInfo giftInfo){
		//赠品面值
		if (MallUtils.isNotEmpty(giftInfo.getGiftValue())) {
			//单位转换为元
			try {
				Integer t = Integer.parseInt(giftInfo.getGiftValue());
				giftInfo.setGiftValue(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "GiftValue值不正确.";
			}
		}
		//赠品数量
		if (MallUtils.isNotEmpty(giftInfo.getGiftNum())) {
			try {
				Integer t = Integer.parseInt(giftInfo.getGiftNum());
			} catch (Exception e) {
				return "GiftNum值不正确.";
			}
		}
		//赠品类型
		if (MallUtils.isNotEmpty(giftInfo.getGiftType())) {
			if ( !( dbUtils.checkFieldValue("GiftType", giftInfo.getGiftType()) ) ) {
				return "GiftType值不正确.";
			}
		}
		
		return "";
	}
	
	/**
	 * 检查商品信息
	 * @param centerMallGoodsInfo
	 * @return
	 */
	private static String checkGoodsInfo(CenterMallGoodsInfo centerMallGoodsInfo){
		String strTmp = null;
		//商品编码
		if (MallUtils.isEmpty(centerMallGoodsInfo.getGoodsCode())) {
			return "缺少必填字段:GoodsCode.";
		}else {
			try {
				Long t = Long.parseLong(centerMallGoodsInfo.getGoodsCode());
			} catch (Exception e) {
				return "GoodsCode值不正确.";
			}
		}
		//商品名称
		if (MallUtils.isEmpty(centerMallGoodsInfo.getGoodsName())) {
			return "缺少必填字段:GoodsName.";
		}
		//商品应收
		if (MallUtils.isEmpty(centerMallGoodsInfo.getGoodsOrigFee())) {
			return "缺少必填字段:GoodsOrigFee.";
		}else {
			try {
				//单位转换为元
				Integer t = Integer.parseInt(centerMallGoodsInfo.getGoodsOrigFee());
				centerMallGoodsInfo.setGoodsOrigFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "GoodsOrigFee值不正确.";
			}
		}
		//商品实收
		if (MallUtils.isEmpty(centerMallGoodsInfo.getGoodsRealFee())) {
			return "缺少必填字段:GoodsRealFee.";
		}else {
			try {
				//单位转换为元
				Integer t = Integer.parseInt(centerMallGoodsInfo.getGoodsRealFee());
				centerMallGoodsInfo.setGoodsRealFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "GoodsRealFee值不正确.";
			}
		}
		//商品类型
		/*
		*ZX add 2016-01-06 end 注释
		if (MallUtils.isEmpty(centerMallGoodsInfo.getGoodsType())) {
			return "缺少必填字段:GoodsType.";
		}else {
			if ( !(  dbUtils.checkFieldValue("GoodsType", centerMallGoodsInfo.getGoodsType())  )) {
				return "GoodsType值不正确.";
			}
		}
		
		//获取商品类型,根据类型对不同模板进行校验
		String goodsType = centerMallGoodsInfo.getGoodsType();
		*ZX add 2016-01-06 end 注释
		*/
		//GoodsAttInfo  商品附属信息
		List<CenterMallGoodsAttInfo> goodsAttInfos = centerMallGoodsInfo.getGoodsAttInfo();
		if (null == goodsAttInfos || goodsAttInfos.size() == 0) {
			return "缺少必填字段:GoodsAttInfo.";
		}else{
			for (CenterMallGoodsAttInfo centerMallGoodsAttInfo : goodsAttInfos) {
				/*ZX add 2015-12-23 update start*/
				if (StringUtils.isBlank(centerMallGoodsAttInfo.getNumType())
						|| centerMallGoodsAttInfo.getNumType().equals(EcsOrderConsts.ZB_CARD_TYPE_ZHU)) {
					strTmp = checkGoodsAttInfo(centerMallGoodsAttInfo,centerMallGoodsAttInfo.getGoodsType());
				} else {
					continue;
				}
				/*ZX add 2015-12-23 update end*/
				if ( !("".equals(strTmp)) ) {
					return strTmp;
				}
			}
		}
		
		//FeeInfo  费用明细
		List<CenterMallFeeInfo> feeInfos = centerMallGoodsInfo.getFeeInfo();
		if(null != feeInfos && feeInfos.size() >0){
			for (CenterMallFeeInfo centerMallFeeInfo : feeInfos) {
				strTmp = checkFeeInfo(centerMallFeeInfo);
				if ( !("".equals(strTmp)) ) {
					return strTmp;
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
	private static String checkFeeInfo(CenterMallFeeInfo feeInfo){
		//收费项编码
		if (MallUtils.isEmpty(feeInfo.getFeeID())) {
			return "缺少必填字段:FeeID.";
		}else {
			if ( !( dbUtils.checkFieldValue("FeeID", feeInfo.getFeeID()) )) {
				return "FeeID值不正确.";
			}
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
				Integer t = Integer.parseInt(feeInfo.getOrigFee());
				feeInfo.setOrigFee(MallUtils.parseMoney(t));
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
				Integer t = Integer.parseInt(feeInfo.getReliefFee());
				feeInfo.setReliefFee(MallUtils.parseMoney(t));
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
				Integer t = Integer.parseInt(feeInfo.getRealFee());
				feeInfo.setRealFee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "RealFee值不正确.";
			}
		}
		return "";
	}
	
	/**
	 * 号码信息检查
	 * @param phoneInfo
	 * @return
	 */
	public static String checkGoodsPhoneInfo(CenterMallGoodsPhoneInfo phoneInfo){
		String strTmp = null;
		//订购号码
		if(MallUtils.isEmpty(phoneInfo.getPhoneNum())){
			return "缺少必填字段:PhoneNum.";
		}else{
			if (phoneInfo.getPhoneNum().length() != 11) {
				return "PhoneNum("+phoneInfo.getPhoneNum()+")值不正确.";
			}
		}
		//号码网别
//		if(MallUtils.isEmpty(phoneInfo.getNumNet())){
//			return "缺少必填字段:NumNet.";
//		}
		//号码状态标识
//		if(MallUtils.isEmpty(phoneInfo.getOccupiedFlag())){
//			return "缺少必填字段:OccupiedFlag.";
//		}
//		CenterMallGoodsNiceInfo niceInfo = phoneInfo.getNiceInfo();
//		if(null != niceInfo){
//			strTmp = checkGoodsNiceInfo(niceInfo);
//		}
		return strTmp;
	}
	
	/**
	 * 检查靓号信息
	 * @param niceInfo
	 * @return
	 */
	private String checkGoodsNiceInfo(CenterMallGoodsNiceInfo niceInfo){
		//预存话费金额
		if(MallUtils.isEmpty(niceInfo.getAdvancePay())){
			return "缺少必填字段:AdvancePay.";
		}
		//号码等级
		if(MallUtils.isEmpty(niceInfo.getClassId())){
			return "缺少必填字段:ClassId.";
		}
		return "";
	}
	
	/**
	 * 检查商品附属信息
	 * @param goodsAttInfo
	 * @return
	 */
	private static String checkGoodsAttInfo(CenterMallGoodsAttInfo goodsAttInfo , String goodsType){
		String strTmp = null;
		/**
		 * 商品类型goodsType说明
		 * ZHKL	总部号卡类
		ZHYL	总部号卡合约类
		ZZDL	总部终端合约类
		ZSWK	总部上网卡商品
		ZLZD	总部裸终端商品
		ZPJL	总部配件商品
		ZYWL	总部业务变更类
		 */
		/*
		 * ZX add 2016-01-06 start 判断商品类型
		 */
		if (MallUtils.isEmpty(goodsType)) {
			return "缺少必填字段:GoodsType.";
		}else {
			if ( !(  dbUtils.checkFieldValue("GoodsType", goodsType)  )) {
				return "GoodsType值不正确.";
			}
		}
		/*
		 * ZX add 2016-01-06 end 判断商品类型
		 */
		
		//总部业务变更类
		if ("ZYWL".equals(goodsType)) {
			//手机号码
			if (MallUtils.isEmpty(goodsAttInfo.getSerialNumber())) {
				return "缺少必填字段:SerialNumber.";
			}else {
				if (goodsAttInfo.getSerialNumber().length() != 11) {
					return "SerialNumber("+goodsAttInfo.getSerialNumber()+")值不正确.";
				}
			}
			//产品编码
			if (MallUtils.isEmpty(goodsAttInfo.getProductCode())) {
				return "缺少必填字段:ProductCode.";
			}
			//产品名称
			if (MallUtils.isEmpty(goodsAttInfo.getProductName())) {
				return "缺少必填字段:ProductName.";
			}
			//产品操作标示
			if (MallUtils.isNotEmpty(goodsAttInfo.getOptType())) {
				if ( !( dbUtils.checkFieldValue("OptType", goodsAttInfo.getOptType()) ) ) {
					return "OptType值不正确.";
				}
			}
			//FirstMonBillMode 首月资费方式\SerType  付费类型
			strTmp = checkCommonField(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkSubProdInfo(goodsAttInfo.getSubProdInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkActivityInfo(goodsAttInfo.getActivityInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			CenterMallResourcesInfo resourcesInfo = goodsAttInfo.getResourcesInfo();
			if (null != resourcesInfo) {
				strTmp = checkResourcesInfo(resourcesInfo);
				if (!"".equals(strTmp)) {
					return strTmp;
				}
			}
			//是否为定制机
			if (MallUtils.isEmpty(goodsAttInfo.getIsCustomized())) {
				return "缺少必填字段:IsCustomized.";
			}else {
				if( !( dbUtils.checkFieldValue("IsCustomized", goodsAttInfo.getIsCustomized()) ) ){
					return "IsCustomized值不正确.";
				}
			}
			//卡类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getCardType())) {
				if( !( dbUtils.checkFieldValue("CardType", goodsAttInfo.getCardType()) ) ){
					return "CardType值不正确.";
				}
			}
		}else if ("ZPJL".equals(goodsType)) {
			//配件类型
			if (MallUtils.isEmpty(goodsAttInfo.getPartsType())) {
				return "缺少必填字段:PartsType.";
			}else {
				if ( !( dbUtils.checkFieldValue("PartsType", goodsAttInfo.getPartsType()) )) {
					return "PartsType值不正确.";
				}
			}
			CenterMallResourcesInfo resourcesInfo = goodsAttInfo.getResourcesInfo();
			if (null != resourcesInfo) {
				strTmp = checkResourcesInfo(resourcesInfo);
				if (!"".equals(strTmp)) {
					return strTmp;
				}
			}
		}else if ("ZLZD".equals(goodsType)) {
			//是否为定制机
			if (MallUtils.isEmpty(goodsAttInfo.getIsCustomized())) {
				return "缺少必填字段:IsCustomized.";
			}else {
				if( !( dbUtils.checkFieldValue("IsCustomized", goodsAttInfo.getIsCustomized()) ) ){
					return "IsCustomized值不正确.";
				}
			}
			CenterMallResourcesInfo resourcesInfo = goodsAttInfo.getResourcesInfo();
			if (null != resourcesInfo) {
				strTmp = checkResourcesInfo(resourcesInfo);
				if (!"".equals(strTmp)) {
					return strTmp;
				}
			}
		}else if ("ZSWK".equals(goodsType)) {
			strTmp = checkCommon2(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			//销售方式
			if (MallUtils.isNotEmpty(goodsAttInfo.getSaleMode())) {
				if( !( dbUtils.checkFieldValue("SaleMode", goodsAttInfo.getSaleMode()) ) ){
					return "SaleMode值不正确.";
				}
			}
			//卡类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getCardType())) {
				if( !( dbUtils.checkFieldValue("CardType", goodsAttInfo.getCardType()) ) ){
					return "CardType值不正确.";
				}
			}
			//付费类型
			if (MallUtils.isEmpty(goodsAttInfo.getSerType())) {
				return "缺少必填字段:SerType.";
			}else {
				if( !( dbUtils.checkFieldValue("SerType", goodsAttInfo.getSerType()) ) ){
					return "SerType值不正确.";
				}
			}
			strTmp = checkSubProdInfo(goodsAttInfo.getSubProdInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkActivityInfo(goodsAttInfo.getActivityInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			CenterMallResourcesInfo resourcesInfo = goodsAttInfo.getResourcesInfo();
			if (null != resourcesInfo) {
				strTmp = checkResourcesInfo(resourcesInfo);
				if (!"".equals(strTmp)) {
					return strTmp;
				}
			}
		}else if ("ZZDL".equals(goodsType)) {
			strTmp = checkCommon2(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			//入网类型
			if (MallUtils.isEmpty(goodsAttInfo.getUserType())) {
				return "缺少必填字段:UserType.";
			}else {
				if ( !(  dbUtils.checkFieldValue("UserType", goodsAttInfo.getUserType()) ) ) {
					return "UserType值不正确.";
				}
			}
			//号码信息检查
			CenterMallGoodsPhoneInfo phoneInfo = goodsAttInfo.getPhoneInfo();
			if(null != phoneInfo){
				strTmp = checkGoodsPhoneInfo(phoneInfo);
				if(MallUtils.isNotEmpty(strTmp)){
					return strTmp;
				}
			}
			//减免预存标记
			if (MallUtils.isEmpty(goodsAttInfo.getReliefPresFlag())) {
				return "缺少必填字段:ReliefPresFlag.";
			}else {
				if ( !( dbUtils.checkFieldValue("ReliefPresFlag", goodsAttInfo.getReliefPresFlag()) )) {
					return "ReliefPresFlag值不正确.";
				}
			}
			//销售方式
			if (MallUtils.isNotEmpty(goodsAttInfo.getSaleMode())) {
				if( !( dbUtils.checkFieldValue("SaleMode", goodsAttInfo.getSaleMode()) ) ){
					return "SaleMode值不正确.";
				}
			}
			//卡类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getCardType())) {
				if( !( dbUtils.checkFieldValue("CardType", goodsAttInfo.getCardType()) ) ){
					return "CardType值不正确.";
				}
			}
			//产品类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getProductType())) {
				if( !( dbUtils.checkFieldValue("ProductType", goodsAttInfo.getProductType()) ) ){
					return "ProductType值不正确.";
				}
			}
			strTmp = checkPackage(goodsAttInfo.getPackage());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkCommonField(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkSubProdInfo(goodsAttInfo.getSubProdInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkActivityInfo(goodsAttInfo.getActivityInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			CenterMallResourcesInfo resourcesInfo = goodsAttInfo.getResourcesInfo();
			if (null != resourcesInfo) {
				strTmp = checkResourcesInfo(resourcesInfo);
				if (!"".equals(strTmp)) {
					return strTmp;
				}
			}
			//是否为定制机
			if (MallUtils.isEmpty(goodsAttInfo.getIsCustomized())) {
				return "缺少必填字段:IsCustomized.";
			}else {
				if( !( dbUtils.checkFieldValue("IsCustomized", goodsAttInfo.getIsCustomized()) ) ){
					return "IsCustomized值不正确.";
				}
			}
		}else if ("ZHYL".equals(goodsType)) {
			strTmp = checkCommon2(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			//号码信息检查
			CenterMallGoodsPhoneInfo phoneInfo = goodsAttInfo.getPhoneInfo();
			if(null != phoneInfo){
				strTmp = checkGoodsPhoneInfo(phoneInfo);
				if(MallUtils.isNotEmpty(strTmp)){
					return strTmp;
				}
			}
			//减免预存标记
			if (MallUtils.isEmpty(goodsAttInfo.getReliefPresFlag())) {
				return "缺少必填字段:ReliefPresFlag.";
			}else {
				if ( !( dbUtils.checkFieldValue("ReliefPresFlag", goodsAttInfo.getReliefPresFlag()) )) {
					return "ReliefPresFlag值不正确.";
				}
			}
			strTmp = checkCommonField(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			//销售方式
			if (MallUtils.isNotEmpty(goodsAttInfo.getSaleMode())) {
				if( !( dbUtils.checkFieldValue("SaleMode", goodsAttInfo.getSaleMode()) ) ){
					return "SaleMode值不正确.";
				}
			}
			//卡类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getCardType())) {
				if( !( dbUtils.checkFieldValue("CardType", goodsAttInfo.getCardType()) ) ){
					return "CardType值不正确.";
				}
			}
			//产品类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getProductType())) {
				if( !( dbUtils.checkFieldValue("ProductType", goodsAttInfo.getProductType()) ) ){
					return "ProductType值不正确.";
				}
			}
			strTmp = checkPackage(goodsAttInfo.getPackage());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkSubProdInfo(goodsAttInfo.getSubProdInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkActivityInfo(goodsAttInfo.getActivityInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			
		}else if ("ZHKL".equals(goodsType)) {
			strTmp = checkCommon2(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			//号码信息检查
			CenterMallGoodsPhoneInfo phoneInfo = goodsAttInfo.getPhoneInfo();
			if(null != phoneInfo){
				strTmp = checkGoodsPhoneInfo(phoneInfo);
				if(MallUtils.isNotEmpty(strTmp)){
					return strTmp;
				}
			}
			//减免预存标记
			if (MallUtils.isEmpty(goodsAttInfo.getReliefPresFlag())) {
				return "缺少必填字段:ReliefPresFlag.";
			}else {
				if ( !( dbUtils.checkFieldValue("ReliefPresFlag", goodsAttInfo.getReliefPresFlag()) )) {
					return "ReliefPresFlag值不正确.";
				}
			}
			//销售方式
			if (MallUtils.isNotEmpty(goodsAttInfo.getSaleMode())) {
				if( !( dbUtils.checkFieldValue("SaleMode", goodsAttInfo.getSaleMode()) ) ){
					return "SaleMode值不正确.";
				}
			}
			//卡类型
			if (MallUtils.isNotEmpty(goodsAttInfo.getCardType())) {
				if( !( dbUtils.checkFieldValue("CardType", goodsAttInfo.getCardType()) ) ){
					return "CardType值不正确.";
				}
			}
			//产品编码
			if (MallUtils.isEmpty(goodsAttInfo.getProductCode())) {
				return "缺少必填字段:ProductCode.";
			}
			strTmp = checkCommonField(goodsAttInfo);
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkSubProdInfo(goodsAttInfo.getSubProdInfo());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
			strTmp = checkPackage(goodsAttInfo.getPackage());
			if (!"".equals(strTmp)) {
				return strTmp;
			}
		}else {
			return "GoodsType值不正确.";
		}
		return "";
	}
	
	/**
	 * 检查可选包信息
	 * @param packages
	 * @return
	 */
	private static String checkPackage(List<CenterMallPackage> packages){
		if (null != packages) {
			for (CenterMallPackage centerMallPackage : packages) {
				//可选包编码
				if (MallUtils.isEmpty(centerMallPackage.getPackageCode())) {
					return "缺少必填字段:PackageCode.";
				}
				//可选包名称
				if (MallUtils.isEmpty(centerMallPackage.getPackageName())) {
					return "缺少必填字段:PackageName.";
				}
				//元素编码
				if (MallUtils.isEmpty(centerMallPackage.getElementCode())) {
					return "缺少必填字段:ElementCode.";
				}
				//元素类型
				if (MallUtils.isEmpty(centerMallPackage.getElementType())) {
					return "缺少必填字段:ElementType.";
				}else {
					if( !( dbUtils.checkFieldValue("ElementType", centerMallPackage.getElementType()) ) ){
						return "ElementType值不正确.";
					}
				}
				//元素名称
				if (MallUtils.isEmpty(centerMallPackage.getElementName())) {
					return "缺少必填字段:ElementName.";
				}
			}
		}
		return "";
	}
	
	/**
	 * 检查ZHKL	总部号卡类
		ZHYL	总部号卡合约类
		ZZDL	总部终端合约类
		ZSWK	总部上网卡商品中公共部分
	 * @param goodsAttInfo
	 * @return
	 */
	private static String checkCommon2(CenterMallGoodsAttInfo goodsAttInfo){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//号卡客户姓名
		if (MallUtils.isEmpty(goodsAttInfo.getCustomerName())) {
			return "缺少必填字段:CustomerName.";
		}
		//客户类型
		if (MallUtils.isEmpty(goodsAttInfo.getCustomerType())) {
			return "缺少必填字段:CustomerType.";
		}else {
			if ( !( dbUtils.checkFieldValue("CustomerType", goodsAttInfo.getCustomerType()) ) ) {
				return "CustomerType值不正确.";
			}
		}
		//证件类型,老用户非必填
		if ( MallUtils.isEmpty(goodsAttInfo.getCertType()) 
				&& !"OLD".equalsIgnoreCase(goodsAttInfo.getUserType()) ) {
			return "缺少必填字段:CertType.";
		}else {
			if ( !"OLD".equalsIgnoreCase(goodsAttInfo.getUserType()) 
					&& !( dbUtils.checkFieldValue("CertType", goodsAttInfo.getCertType()) ) ) {
				return "CertType值不正确.";
			}
		}
		//证件号码,老用户非必填
		if (MallUtils.isEmpty(goodsAttInfo.getCertNum()) 
				&& !"OLD".equalsIgnoreCase(goodsAttInfo.getUserType())) {
			return "缺少必填字段:CertNum.";
		}
		//证件失效时间,老用户非必填
		if (MallUtils.isEmpty(goodsAttInfo.getCertLoseTime()) 
				&& !"OLD".equalsIgnoreCase(goodsAttInfo.getUserType())) {
			return "缺少必填字段:CertLoseTime.";
		}else {
			try {
				format.parse(goodsAttInfo.getCertLoseTime());
			} catch (Exception e) {
				return "CertLoseTime值不正确.";
			}
		}
		//证件地址,老用户非必填
//		if (MallUtils.isEmpty(goodsAttInfo.getCertAddr()) 
//				&& !"OLD".equalsIgnoreCase(goodsAttInfo.getUserType())) {
//			return "缺少必填字段:CertAddr.";
//		}
		//产品编码
		if (MallUtils.isEmpty(goodsAttInfo.getProductCode())) {
			return "缺少必填字段:ProductCode.";
		}
		//产品名称
		if (MallUtils.isEmpty(goodsAttInfo.getProductName())) {
			return "缺少必填字段:ProductName.";
		}
		//产品网别
		if (MallUtils.isEmpty(goodsAttInfo.getProductNet())) {
			return "缺少必填字段:ProductNet.";
		}else {
			if ( !( dbUtils.checkFieldValue("ProductNet", goodsAttInfo.getProductNet()) ) ) {
				return "ProductNet值不正确.";
			}
		}
		//产品品牌
		if (MallUtils.isEmpty(goodsAttInfo.getProductBrand())) {
			return "缺少必填字段:ProductBrand.";
		}else {
			if ( !( dbUtils.checkFieldValue("ProductBrand", goodsAttInfo.getProductBrand()) ) ) {
				return "ProductBrand值不正确.";
			}
		}
		return "";
	}
	
	/**
	 * 检查终端信息
	 * @param resourcesInfos
	 * @return
	 */
	private static String checkResourcesInfo(CenterMallResourcesInfo resourcesInfos){
		//终端品牌编码
		if (MallUtils.isEmpty(resourcesInfos.getResourcesBrand())) {
			return "缺少必填字段:ResourcesBrand."; 
		}
		//终端型号编码
		if (MallUtils.isEmpty(resourcesInfos.getResourcesModel())) {
			return "缺少必填字段:ResourcesModel."; 
		}
		//终端机型编码
		if (MallUtils.isEmpty(resourcesInfos.getResourcesTypeId())) {
			return "缺少必填字段:ResourcesTypeId."; 
		}
		//终端颜色编码
		if (MallUtils.isEmpty(resourcesInfos.getResourcesColor())) {
			return "缺少必填字段:ResourcesColor."; 
		}
		return "";
	}
	
	/**
	 * 检查各模板公共的属性
	 * @return
	 */
	private static String checkCommonField(CenterMallGoodsAttInfo goodsAttInfo){
		//首月资费方式
		if (MallUtils.isNotEmpty(goodsAttInfo.getFirstMonBillMode())) {
			if ( !( dbUtils.checkFieldValue("FirstMonBillMode", goodsAttInfo.getFirstMonBillMode()) )) {
				return "FirstMonBillMode值不正确.";
			}
		}
		//付费类型
		if (MallUtils.isEmpty(goodsAttInfo.getSerType())) {
			return "缺少必填字段:SerType.";
		}else {
			if ( !( dbUtils.checkFieldValue("SerType", goodsAttInfo.getSerType()) )) {
				return "SerType值不正确.";
			}
		}
		return "";
	}
	
	/**
	 * 检查SubProdInfo
	 * @param subProdInfo
	 * @return
	 */
	private static String checkSubProdInfo(List<CenterMallSubProdInfo> subProdInfos){
		if (null != subProdInfos) {
			for (CenterMallSubProdInfo centerMallSubProdInfo : subProdInfos) {
				//附加产品编码
				if (MallUtils.isEmpty(centerMallSubProdInfo.getSubProCode())) {
					return "缺少必填字段:SubProCode.";
				}
				//附加产品名称
				if (MallUtils.isEmpty(centerMallSubProdInfo.getSubProName())) {
					return "缺少必填字段:SubProName.";
				}
				//附加产品说明
				if (MallUtils.isEmpty(centerMallSubProdInfo.getSubProDesc())) {
					return "缺少必填字段:SubProDesc.";
				}
			}
		}
		return "";
	}
	
	/**
	 * 检查合约信息
	 * @param activityInfos
	 * @return
	 */
	private static String checkActivityInfo(List<CenterMallActivityInfo> activityInfos){
		if (null != activityInfos) {
			for (CenterMallActivityInfo activityInfo : activityInfos) {
				//合约类型
//				if (MallUtils.isEmpty(activityInfo.getActivityType())) {
//					return "缺少必填字段:ActivityType.";
//				}else {
//					if ( !( dbUtils.checkFieldValue("ActivityType", activityInfo.getActivityType()) )) {
//						return "ActivityType值不正确.";
//					}
//				}
				//合约编码
				if (MallUtils.isEmpty(activityInfo.getActivityCode())) {
					return "缺少必填字段:ActivityCode.";
				}
				//合约名称
				if (MallUtils.isEmpty(activityInfo.getActivityName())) {
					activityInfo.setActivityName("总部合约活动");
				}
				//合约期限
//				if (MallUtils.isEmpty(activityInfo.getActProtPer())) {
//					return "缺少必填字段:ActProtPer.";
//				}

			}
		}
		return "";
	}
	
	/**
	 * 检查优惠信息
	 * @param discountInfo
	 * @return
	 */
	private static String checkDiscountInfo(CenterMallDiscountInfo discountInfo){
		//优惠活动类型
		if (MallUtils.isEmpty(discountInfo.getDiscountType())) {
			return "缺少必填字段:DiscountType.";
		}else {
			if ( !( dbUtils.checkFieldValue("DiscountType", discountInfo.getDiscountType()) ) ) {
				return "DiscountType值不正确.";
			}
		}
		//优惠活动编码
		if (MallUtils.isEmpty(discountInfo.getDiscountID())) {
			return "缺少必填字段:DiscountID.";
		}
		//优惠活动名称
		if (MallUtils.isEmpty(discountInfo.getDiscountName())) {
			return "缺少必填字段:DiscountName.";
		}
		//优惠金额
		if (MallUtils.isEmpty(discountInfo.getDiscountValue())) {
			return "缺少必填字段:DiscountValue.";
		}else {
			try {
				//单位转换为元
				Integer t = Integer.parseInt(discountInfo.getDiscountValue());
				discountInfo.setDiscountValue(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "DiscountValue值不正确.";
			}
		}
		return "";
	}
	
	/**
	 * 检查支付信息
	 * @param PayInfo
	 * @return
	 */
	private static String checkPayInfo(CenterMallPayInfo payInfo){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//支付类型
		if (MallUtils.isEmpty(payInfo.getPayType())) {
			return "缺少必填字段:PayType.";
		}else {
			if ( !( dbUtils.checkFieldValue("PayType", payInfo.getPayType()) ) ) {
				return "PayType值不正确.";
			}
		}
		//支付方式
		if (MallUtils.isEmpty(payInfo.getPayWay())) {
			return "缺少必填字段:PayWay.";
		}else {
			if ( !( dbUtils.checkFieldValue("PayWay", payInfo.getPayWay()) ) ) {
				return "PayWay值不正确.";
			}
		}
		//支付完成时间
		if (MallUtils.isNotEmpty(payInfo.getPayFinTime())) {
			try {
				format.parse(payInfo.getPayFinTime());
			} catch (ParseException e) {
				return "PayFinTime值不正确.";
			}
		}
		//支付机构编码
		if (MallUtils.isNotEmpty(payInfo.getPayProviderId())) {
			if ( !( dbUtils.checkFieldValue("PayProviderId", payInfo.getPayProviderId()) ) ) {
				return "PayProviderId值不正确.";
			}
		}
		//支付机构名称
		if (MallUtils.isNotEmpty(payInfo.getPayProviderName())) {
			if ( !( dbUtils.checkFieldValue("PayProviderName", payInfo.getPayProviderName()) ) ) {
				return "PayProviderName值不正确.";
			}
		}
		//支付渠道编码
		if (MallUtils.isNotEmpty(payInfo.getPayChannelId())) {
			if( ! ( dbUtils.checkFieldValue("PayChannelId", payInfo.getPayChannelId()) ) ){
				return "PayChannelId未按协议取值.";
			}
		}
		//支付渠道名称
		if (MallUtils.isNotEmpty(payInfo.getPayChannelName())) {
			if( ! ( dbUtils.checkFieldValue("PayChannelName", payInfo.getPayChannelName()) ) ){
				return "PayChannelName未按协议取值.";
			}
		}

		return "";
	}
}
