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

import params.req.GroupOrderStandardReq;
import params.resp.GroupOrderStandardResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
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
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrder;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderBroadInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderCustInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderFeeInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderGoodsInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderPartners;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderPayInfo;
import com.ztesoft.net.server.jsonserver.rhywpojo.GroupOrderPhoneInfo;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.resp.DevelopOrderResp;
import com.ztesoft.util.DevelopChannelUtil;

import consts.ConstsCore;

public class GroupOrderUtil {

	private static Logger logger = Logger.getLogger(GroupOrderUtil.class);
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
	

	public static GroupOrderStandardResp orderStandardValidate(GroupOrderStandardReq req) throws Exception{
		initBean();
		GroupOrderStandardResp resp = new GroupOrderStandardResp();
		Map extMap = new HashMap<String, String>();
		Map orderMap = new HashMap();
		String outJson = "";
		String checkResult = "";
		boolean errorFlag = false;
		//记录错单需要
		String source_system = "ZB";
		String out_tid = "";
		GroupOrder groupOrder = null;
		
		String inJson = getReqContent(req.getBase_co_id(), req.isIs_exception());
		req.setReq_content(inJson);
		if(StringUtils.isEmpty(inJson)){
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("未获取到请求报文");
			return resp;
		}
		//1.json转换成对象
		groupOrder = MallUtils.jsonToGroupOrder(inJson);
		out_tid = groupOrder.getMall_order_id();
		source_system = groupOrder.getSource_system();
		//检查参数格式是否正确
		//注意所有金额都在checkgroupOrder方法里由厘转换为元
		checkResult = checkGroupOrder(groupOrder);
		
		if(!StringUtils.isEmpty(checkResult)){
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kgDeposit = cacheUtil.getConfigInfo("channel_convert_kg_group");
		DevelopOrderResp developOrderResp = null;
		//收单发展点发展人值判断
        if("1".equals(kgDeposit) && StringUtil.isEmpty(checkResult)){
        	DevelopChannelUtil developChannelUtil = new DevelopChannelUtil();
        	//渠道转换 1.goodId  2.来源   3.发展点  4.发展人
			developOrderResp = developChannelUtil.getDevelop(groupOrder.getGoods_info().get(0).getProd_offer_code(),
					groupOrder.getSource_system(),
					groupOrder.getDevelop_point_code_new(),
					groupOrder.getDevelop_code());
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
		
		String setValResult = setGroupOrderValue(groupOrder,out_order , extMap, developOrderResp);
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
	 * @param groupOrder
	 * @param outer
	 * @return
	 */
	private static String setGroupOrderValue(GroupOrder groupOrder , Outer outer , Map extMap, DevelopOrderResp developOrderResp){
		String checkResult = "";
		/**
		 * 补充信息
		 */
		//下单时间对应物理库的create_time
		outer.setGet_time(groupOrder.getCreate_time());
		//渠道类型  channel_mark  Reserve2
		outer.setReserve2(groupOrder.getChannel_mark());
		//接收方系统标识  receive_system  Reserve0
		outer.setReserve0(groupOrder.getReceive_system());
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
		
		//买家标识  CustomerId
		extMap.put("uid", "");
		//实收运费  RealPostFee
		extMap.put("n_shipping_amount", groupOrder.getReal_post_fee());
		//deliTimeMode  配送时间类型
		extMap.put("shipping_time", groupOrder.getDeli_time_mode());
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
		extMap.put("source_type", groupOrder.getSource_system());
		//订单接入类别
		extMap.put("regist_type", groupOrder.getSource_system());
		extMap.put("intent_order_id", groupOrder.getRel_order_id());
		//取到客户信息节点
		GroupOrderCustInfo custInfo = groupOrder.getCust_info();
		
		//ActiveNo  访问流水
		outer.setPayplatformorderid(groupOrder.getSerial_no());
		//CustomerId  买家标识/买家ID
		outer.setBuyer_name(custInfo.getCustomer_name());
		//RegisterName  买家昵称
		outer.setBuyer_id("");
		//ClientIP  买家登录IP
		//ConsiName  收件人姓名
		outer.setReceiver_name(groupOrder.getShip_name());;
		//ConsiPhone  收件人联系电话
		outer.setReceiver_mobile(groupOrder.getShip_tel());
		//ConsiTelPhone  收件人联系电话2支持填写固话
		outer.setPhone("");
		//BuyerMessage  买家留言
		outer.setBuyer_message(groupOrder.getBuyer_message());
		//ConsiGoodsProv  收货省
		outer.setProvinc_code(groupOrder.getShip_province());
		String prov_name = goodServicesLocal.queryString("SELECT  a.province_name from es_zbmall_area a WHERE a.province_code = '" + groupOrder.getShip_province() + "' AND ROWNUM < 2");
		outer.setProvince(prov_name);
		//ConsiGoodsCity  收货市
		outer.setCity_code(groupOrder.getShip_city());
		String city_name = goodServicesLocal.queryString("SELECT  a.city_name from es_zbmall_area a WHERE a.city_code = '" + groupOrder.getShip_city() + "' AND ROWNUM < 2");
		outer.setCity(city_name);
		//ConsiGoodsDist  收货区/县（行政区域）
		outer.setArea_code(groupOrder.getShip_district());
		String dist_name = null;
		if (MallUtils.isNotEmpty(groupOrder.getShip_district())) {
			dist_name = goodServicesLocal.queryString("SELECT  a.district_name from es_zbmall_area a WHERE a.district_code = '" + groupOrder.getShip_district() + "' AND ROWNUM < 2");
			outer.setDistrict(dist_name);
		}
		//ConsiPostCode  邮政编码
		outer.setPost("");
		//ConsiPostAddress  邮寄地址
		outer.setAddress(groupOrder.getShip_addr());
		//ConsiPostRemark  邮寄备注
		outer.setService_remarks("");
		//deliMode  配送方式
		outer.setSending_type(groupOrder.getDeli_mode());
		//OrderId  订单编号
		outer.setOut_tid(groupOrder.getMall_order_id());
		//OrderTime  下单时间，YYYY-MM-DD HH24:MI:SS
		outer.setTid_time(groupOrder.getCreate_time());
		//OrderProvince  订单省份
		outer.setOrder_provinc_code(groupOrder.getOrder_province_code());
		//OrderCity  订单地市
		outer.setOrder_city_code(groupOrder.getOrder_city_code());
		//OrderSource  订单来源
		outer.setOrder_from(groupOrder.getSource_system());
		//OrderAccType  订单接入类别
		outer.setPlat_type(groupOrder.getSource_system());
		outer.setOrderacctype(groupOrder.getSource_system());
		outer.setOrder_channel(groupOrder.getSource_system());
		//OrderAccDesc  订单接入说明
		//OutOrderId  外部订单编号，即父订单编码
		//OrderOrigFee  订单应收总价
		outer.setOrder_totalfee(String.valueOf(groupOrder.getOrder_amount()));
		outer.setOrder_origfee(String.valueOf(groupOrder.getOrder_amount()));
		//OrderRealFee  订单实收总价
		outer.setOrder_realfee(String.valueOf(groupOrder.getPay_amount()));
		//OrderReliefFee  订单减免金额
		outer.setDiscountValue(String.valueOf(groupOrder.getDiscount_amount()));
		//OrderReliefRes  订单减免原因
		extMap.put("OrderReliefRes",groupOrder.getDiscount_reason());
		//OrigPostFee  应收邮寄费用
		outer.setPost_fee(String.valueOf(groupOrder.getOrig_post_fee()));
		extMap.put("user_to_account", groupOrder.getUser_to_account());
		extMap.put("service_type", groupOrder.getService_type());
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
		GroupOrderPayInfo payInfo = groupOrder.getPay_info();
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
			if(StringUtil.isEmpty(groupOrder.getIs_pay())||StringUtil.equals("0", groupOrder.getIs_pay())){
				outer.setPay_status("0");
			}else{
				outer.setPay_status("1");
			}
		}
		
		//GoodsInfo  商品信息
		checkResult = setGroupOrderGoodsInfo(groupOrder,outer,extMap,developOrderResp);
		if (!"".equals(checkResult)) {
			return checkResult;
		}
		
		if(!StringUtil.isEmpty(groupOrder.getIs_pay())){
			extMap.put("is_pay", groupOrder.getIs_pay());
		}
		GroupOrderPartners partners = groupOrder.getPartners_info();
		if(partners != null){
		    String market_user_id = partners.getMarket_user_id();
		    String seed_user_id = partners.getSeed_user_id();
		    String share_svc_num = partners.getShare_svc_num();
		    String top_share_userid = partners.getTop_share_userid();
		    String top_share_num  = partners.getTop_share_num();
		    String activity_name = partners.getActivity_name();
		    if(StringUtils.isNotEmpty(market_user_id)){
		        extMap.put("market_user_id", market_user_id);
		    }
		    if(StringUtils.isNotEmpty(seed_user_id)){
                extMap.put("seed_user_id", seed_user_id);
            }
		    if(StringUtils.isNotEmpty(share_svc_num)){
                extMap.put("share_svc_num", share_svc_num);
            }
		    if(StringUtils.isNotEmpty(top_share_userid)){
                extMap.put("top_share_userid", top_share_userid);
            }
		    if(StringUtils.isNotEmpty(top_share_num)){
                extMap.put("top_share_num", top_share_num);
            }
		    if(StringUtils.isNotEmpty(activity_name)){
                extMap.put("activity_name", activity_name);
            }
		}
		if (custInfo != null) {
			String usecustname = custInfo.getCustomer_name(); // 使用人责任人姓名
			String usecustpspttype = custInfo.getCert_type(); // 使用人或责任人客户证件类型
			String usecustpsptcode = custInfo.getCert_num(); // 使用人或责任人客户证件号码
			String usecustpsptaddress = custInfo.getCert_addr(); // 使用人或责任人证件地址
			String itmprdgrouptype = "";//custInfo.getItmprdgrouptype(); // B2B、B2C类集客产品标识(责任人时传'B2B'、'B2C'、'B2B2C'等)
			String itmprdrespobsible = "";//custInfo.getItmprdrespobsible(); // 责任人标识(责任人时传1)
			String cust_id = custInfo.getCust_id();//客户标识--冰激凌宽带开户，王卡宽带开户必传
			String subs_id = custInfo.getSubs_id();
			String cert_sex = custInfo.getCert_sex();
		    String cust_birthday = custInfo.getCust_birthday();
		    if(StringUtils.isNotEmpty(cert_sex)){
		        extMap.put("cert_sex", cert_sex);
		    }
		    if(StringUtils.isNotEmpty(cust_birthday)){
                extMap.put("cust_birthday", cust_birthday);
            }
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
			if(StringUtils.isNotBlank(subs_id)) {
				extMap.put("subs_id", custInfo.getSubs_id());
			}
			//add by zhaochen 绑定类型--冰激凌宽带开户，王卡宽带开户必传
			if (StringUtils.isNotBlank(cust_id)) {
				extMap.put("cust_id", cust_id);
			}
			//end
		}
		
		List<GroupOrderBroadInfo> broadInfos = groupOrder.getBroad_info();
		if(null != broadInfos){
			JSONArray jsonArray = JSONArray.fromObject(broadInfos);
			extMap.put("RHbroadinfo", jsonArray.toString());
		}
		
		//add by zhaochen 绑定类型--冰激凌宽带开户，王卡宽带开户必传
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String broad_info_internetgroup = cacheUtil.getConfigInfo("broad_info_internetgroup");
		if(broadInfos != null && broadInfos.size()>0){
			String bindType = "";
			
			for(GroupOrderBroadInfo info : broadInfos){
				if("KD".equals(info.getProduct_type())){
					bindType = info.getBind_rela_type();
				}
				if ("1".equals(broad_info_internetgroup)) {
					extMap.put("development_point_code", info.getDevelop_point_code());
					extMap.put("develop_point_code_new", info.getDevelop_point_code());
				}
			}
			
			if (StringUtils.isNotBlank(bindType)) {
				extMap.put("bind_type", bindType);
			}
		}
		//end
		
		List<GroupOrderFeeInfo> feeInfosAll = new ArrayList<GroupOrderFeeInfo>();
		
		//融合移网节点入库
		List<GroupOrderPhoneInfo> phoneInfos = groupOrder.getPhone_info();
		if(null != phoneInfos) {
			JSONArray jsonArray = JSONArray.fromObject(phoneInfos);
			extMap.put("internetPhoneinfo", jsonArray.toString());
		}
		if (null != developOrderResp) {
			// 发展点编码
			extMap.put("development_point_code", developOrderResp.getDevelopment_point_code());
			extMap.put("develop_point_code_new", developOrderResp.getDevelopment_point_code());// 之前收单问题新增沉淀字段，方便以后移解
			extMap.put("development_point_name", developOrderResp.getDevelopment_point_name());
			extMap.put("develop_point_name_new", developOrderResp.getDevelopment_point_name());
			extMap.put("develop_code_new", developOrderResp.getDevelop_code());// 之前收单问题新增沉淀字段，方便以后移解
			extMap.put("develop_name_new", developOrderResp.getDevelop_name());// 发展人名称
		} else {
			if (!StringUtil.isEmpty(groupOrder.getDevelop_point_code_new())) {
				extMap.put("develop_point_code_new", groupOrder.getDevelop_point_code_new());
				extMap.put("development_point_code", groupOrder.getDevelop_point_code_new());
			}
			extMap.put("develop_code_new", groupOrder.getDevelop_code());
			extMap.put("develop_name_new", groupOrder.getDevelop_name());
			extMap.put("develop_point_name_new", groupOrder.getDevelop_point_name_new());
			
			extMap.put("development_point_name", groupOrder.getDevelop_point_name_new());
		}
		
		extMap.put("out_office", groupOrder.getDeal_office_id());
		extMap.put("out_operator", groupOrder.getDeal_operator());
		if(null != phoneInfos) {
			for (GroupOrderPhoneInfo groupOrderPhoneInfo : phoneInfos) {
				String serviceNum = groupOrderPhoneInfo.getService_num();
                String is_new = groupOrderPhoneInfo.getIs_new();
                String is_blankcard = groupOrderPhoneInfo.getIs_blankcard();
                String sim = groupOrderPhoneInfo.getSim();
				if(StringUtils.isNotBlank(serviceNum)) {
					extMap.put("phone_num",serviceNum);
				}

				if(StringUtils.isNotBlank(is_new)&& "1".equals(is_new) && "0".equals(is_blankcard)){//融合新开户  is_blankcard 0 代表成卡 规则匹配需要该字段
				    extMap.put("is_blankcard",groupOrderPhoneInfo.getIs_blankcard());
                    extMap.put("is_new",is_new);
				}
				if(StringUtils.isNotEmpty(sim)){//融合成卡不需要获取卡信息 收单传值需要进行入到iccid
				    extMap.put("ICCID", sim);
				}
				List<GroupOrderFeeInfo> feeInfos = groupOrderPhoneInfo.getFee_list();
				if(feeInfos != null && feeInfos.size() > 0) {
					for (GroupOrderFeeInfo feeInfo : feeInfos) {
						feeInfo.setIs_aop_fee(groupOrderPhoneInfo.getIs_new());
						feeInfosAll.add(feeInfo);
					}
				}
			}
		}

		
		if(null != broadInfos) {
			for (GroupOrderBroadInfo broadInfo : broadInfos) {
				List<GroupOrderFeeInfo> feeInfos = broadInfo.getFee_list();
				if(feeInfos!=null&&feeInfos.size()>0){
					for (GroupOrderFeeInfo feeInfo : feeInfos) {
					     feeInfo.setIs_aop_fee(broadInfo.getProduct_type());
					}
					//FeeInfo费用明细
					feeInfosAll.addAll(feeInfos) ;
				}
				extMap.put("service_num", custInfo.getService_num());
				if(!StringUtil.isEmpty(broadInfo.getProduct_type())&&StringUtil.equals(broadInfo.getProduct_type(), "KD")){
					extMap.put("phone_num", broadInfo.getBb_num());
				}
				
				
				//保存外部操作人信息
				extMap.put("out_office", broadInfo.getDeal_office_id());
				extMap.put("out_operator", broadInfo.getDeal_operator());
			}
		}
		
		if(null != feeInfosAll && feeInfosAll.size() > 0){
			JSONArray jsonArray = JSONArray.fromObject(feeInfosAll);
			extMap.put("feeinfo", jsonArray.toString());
		}
		
		  //add by sgf 无线宽带线上取默认配置直接入库
		String goods_id="";
        List<GroupOrderGoodsInfo> goodsInfos = groupOrder.getGoods_info();
        if(goodsInfos.size()>0){
            goods_id = goodsInfos.get(0).getProd_offer_code();
        }
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        String sql3 ="select distinct cat_id from es_goods t where t.goods_id='"+goods_id+"' and t.source_from= '"+ManagerUtils.getSourceFrom()+"'";
        String cat_id = baseDaoSupport.queryForString(sql3);
        String groupFlowKg = cacheUtil.getConfigInfo("GROUP_KD_WFGROUPUPINTENT");
        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg)&&"10093".equals(groupOrder.getSource_system()) && "221668199563784192".equals(cat_id)){
            IDaoSupport<EmpOperInfoVo> support = SpringContextHolder.getBean("baseDaoSupport");
            String sql = "select * from Es_OPERATOR_REL_EXT a where a.ORDER_GONGHAO ='WFGROUPUPINTENT' and a.CITY='"+groupOrder.getOrder_city_code()+"' and a.source_from ='" + ManagerUtils.getSourceFrom() + "'";
            EmpOperInfoVo empIdEssIDRequest = support.queryForObject(sql,EmpOperInfoVo.class);
            if(empIdEssIDRequest != null){
                extMap.put("out_office", empIdEssIDRequest.getDep_id());//操作点
                extMap.put("out_operator", empIdEssIDRequest.getEss_emp_id());//操作员
            }
        }
		
        
		//add by zhaochen AOP 20180607 接口新增AOP业务的渠道编号，县区，渠道类型字段
		if(!StringUtil.isEmpty(groupOrder.getDistrict()))
			extMap.put("district_code", groupOrder.getDistrict());
		
		if(!StringUtil.isEmpty(groupOrder.getChannelId()))
			extMap.put("channelId", groupOrder.getChannelId());
		
		if(!StringUtil.isEmpty(groupOrder.getChannelType()))
			extMap.put("channel_type", groupOrder.getChannelType());
		//end
		extMap.put("syn_mode", groupOrder.getSyn_mode());
		extMap.put("kingcard_status", groupOrder.getKingcard_status());
		outer.setExtMap(extMap);
		return checkResult;
	}
	
	/**
	 * 把goodsinfo数据set对java对象中
	 * @param groupOrder
	 * @param outer
	 * @return
	 */
	private static String setGroupOrderGoodsInfo(GroupOrder groupOrder , Outer outer , Map extMap, DevelopOrderResp developOrderResp){
		String checkResult = "";
		List<OuterItem> itemList  = new ArrayList<OuterItem>();
		LocalOrderAttr orderAttr = new LocalOrderAttr();
		
		List<GroupOrderGoodsInfo> goodsInfos = groupOrder.getGoods_info();
		for(GroupOrderGoodsInfo goodsInfo : goodsInfos) {
			OuterItem item = new OuterItem();
			//GoodsNum  订单中商品的总数量
			item.setPro_num(goodsInfo .getGoods_num());
			//GoodsOrigFee  商品应收
			item.setSell_price(Double.parseDouble(goodsInfo.getOffer_price()));
			//GoodsRealFee  商品实收
			item.setPro_origfee(Double.parseDouble(goodsInfo.getReal_offer_price()));
			
			if(null != goodsInfo){
				JSONObject jsonObj = JSONObject.fromObject(goodsInfo);
				extMap.put("goodsInfo", jsonObj);
			}
			
			String outer_sku_id = goodsInfo.getProd_offer_code();
			
			String goods_id = goodsInfo.getProd_offer_code();
			//查看商品是否存在
			//商品类型
				/**
				 * ZX add 2016年4月5日 18:51:40 start
				 */
				//ifSendPhotos  证件上传状态
				
				if (StringUtils.isNotBlank(groupOrder.getRealname_type())) {
					if (groupOrder.getRealname_type().equals("2")) {
						extMap.put("is_examine_card", "0"); // 不需要审核，已实名认证
						extMap.put(AttrConsts.CERT_UPLOAD_STATUS, EcsOrderConsts.CERT_STATUS_NONE);//已经实名认证的，无需上传						
					} else if (groupOrder.getRealname_type().equals("1")) {
						extMap.put("is_examine_card", "1"); // 需要审核，未实名认证
					}
				}

				GroupOrderCustInfo custInfo = groupOrder.getCust_info();
				//是否老用户
				if (MallUtils.isNotEmpty(custInfo.getUser_type()) 
						&& "OLD".equalsIgnoreCase(custInfo.getUser_type())) {
					orderAttr.setIs_old("1");
					extMap.put("is_old", "1");
					if ( MallUtils.isEmpty(custInfo.getUser_type()) 
							|| MallUtils.isEmpty(custInfo.getCert_num()) ) {
						//goodsAttInfo.setCertType("WXZ");
					}
				}else{
					orderAttr.setIs_old("0");
					extMap.put("is_old", "0");
				}
				//FirstMonBillMode  首月资费方式
				item.setFirst_payment("ALLM");//首月月费接口没有
				
//				outer.setReserve1(reserve1);
				//CertType  证件类型
				item.setCert_type(custInfo.getCert_type());
				//CertNum  证件号码
				item.setCert_card_num(custInfo.getCert_num());
				//CertNum  证件号码
                item.setCert_num2(custInfo.getCert_num2());
				//CertAddr  证件地址
				item.setCert_address(custInfo.getCert_addr());
				outer.setCert_address(custInfo.getCert_addr());
				//CustomerName  号卡客户姓名
				item.setPhone_owner_name(custInfo.getCustomer_name());
				
				//CertLoseTime  证件失效时间
				item.setCert_failure_time(custInfo.getCert_expire_date());
				item.setCert_eff_time(custInfo.getCert_effected_date());
				item.setCerti_sex(custInfo.getCert_sex());
				item.setCert_nation(custInfo.getCert_nation());
				extMap.put("birthday", custInfo.getCust_birthday());
				//extMap.put("cert_eff_time", custInfo.getCert_effected_date());
				extMap.put("sex", custInfo.getCert_sex());
				extMap.put("cert_issuer", custInfo.getCert_issuedat());
				
				if (null != developOrderResp) {
					// 发展人编码
					outer.setDevelopment_code(developOrderResp.getDevelop_code());
					//DevelopName  发展人名称
					outer.setDevelopment_name(developOrderResp.getDevelop_name());
				} else {
					//DevelopCode   发展人编码
					outer.setDevelopment_code(groupOrder.getDevelop_code());
					//DevelopName  发展人名称
					outer.setDevelopment_name(groupOrder.getDevelop_name());
				}
				
				//RefereeNum  推荐人号码 
				//outer.setRecommended_phone(goodsAttInfo.getRefereeNum());
				//推荐人手机  reference_phone
				if (MallUtils.isNotEmpty(groupOrder.getReferee_num())) {
					extMap.put("reference_phone", groupOrder.getReferee_num());
				}
				//RefereeName  推荐人名称
				outer.setRecommended_name(groupOrder.getReferee_name());
				
				//ProductNet  产品网别
				//item.setProduct_net("4G");
				//ProductCode  产品编码
				item.setProduct_id(goodsInfo.getProd_offer_code());
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
				extMap.put("ProPacCode", "");
				//ProPacDesc  产品包说明
				extMap.put("ProPacDesc", "");
				//GoodsName 商品名称
				extMap.put("GoodsName", goodsInfo.getProd_offer_name());
				//package_sale  套包销售标记
				extMap.put("package_sale", package_sale);
				
						/*
						 * ZX add 2016-01-07 start 入网类型
						 */
						if (EcsOrderConsts.ZB_GOODS_TYPE_ZHKL.equalsIgnoreCase(groupOrder.getBusi_type())
								||EcsOrderConsts.ZB_GOODS_TYPE_ZHYL.equalsIgnoreCase(groupOrder.getBusi_type())
								||EcsOrderConsts.ZB_GOODS_TYPE_ZSWK.equalsIgnoreCase(groupOrder.getBusi_type())) {
							String userType = custInfo.getUser_type();
							if (StringUtils.isNotBlank(userType)) {
								userType = CommonDataFactory.getInstance().getDictCodeValue("user_type", custInfo.getUser_type());
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
						
						/*
						 * ZX add 2016-01-08 end 附加产品信息
						 */

						/*
						 * Rapon add 2016-04-08 start 合约信息
						 */
						
						extMap.put("zb_activity", "");
						/*
						 * Rapon add 2016-04-08 end 合约信息
						 */
						
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
				List activityList = support.queryForList(sql,resp.getData().get("goods_id").toString(),groupOrder.getOrder_city_code(),groupOrder.getCreate_time(),user_type);
				
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
				OuterError ng = new OuterError(goods_id, outer_sku_id, outer_sku_id, null, groupOrder.getSource_system(), groupOrder.getMall_order_id(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + groupOrder.getMall_order_id() +",获取商品相关参数失败======================");
				checkResult = "电商没有配置商品.";
				continue;
			}
			if ("0".equals(resp.getError_code())) {
				param = resp.getData();
				setPackageGoodsParam(item,param);
				itemList.add(item);
				
			}else {
				OuterError ng = new OuterError(goods_id, outer_sku_id, outer_sku_id, null, groupOrder.getSource_system(), groupOrder.getMall_order_id(), "sysdate", "goodserror");//noparam  nogoods
				ng.setDeal_flag("-1");
				outterECSTmplManager.insertOuterError(ng);
				logger.info("订单:" + groupOrder.getMall_order_id() +",获取商品相关参数失败======================");
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
	 * 检查融合业务订单数据是否正常
	 * @param groupOrder
	 * @return
	 */
	private static String checkGroupOrder(GroupOrder groupOrder){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strTmp = null;
		//序列号
		if (MallUtils.isEmpty(groupOrder.getSerial_no())) {
			return "缺少必填字段:serial_no.";
		}
		//时间
		if (MallUtils.isEmpty(groupOrder.getCreate_time())) {
			return "缺少必填字段:create_time.";
		}else {
			try {
				format.parse(groupOrder.getCreate_time());
			} catch (Exception e) {
				return "create_time值不正确.";
			}
		}
		
		//发起方系统标识
		if (MallUtils.isEmpty(groupOrder.getSource_system())) {
			return "缺少必填字段:source_system.";
		}else {
			if (!( dbUtils.checkFieldValue("source_system", groupOrder.getSource_system()) )) {
				return "source_system值不正确.";
			}
		}
		//接收方系统标识
		if (MallUtils.isEmpty(groupOrder.getReceive_system())) {
			return "缺少必填字段:receive_system.";
		}
		
		//业务类型
		if (MallUtils.isEmpty(groupOrder.getBusi_type())) {
			return "缺少必填字段:busi_type.";
		}
		
		//渠道标记
		if (MallUtils.isEmpty(groupOrder.getChannel_mark())) {
			return "缺少必填字段:channel_mark.";
		}
		
		//受理类型
		if (MallUtils.isEmpty(groupOrder.getService_type())) {
			return "缺少必填字段:service_type.";
		}
		
		//商城系统单号
		if (MallUtils.isEmpty(groupOrder.getMall_order_id())) {
			return "缺少必填字段:mall_order_id.";
		}
		
		//订单归属省份
		if (MallUtils.isEmpty(groupOrder.getOrder_province_code())) {
			return "缺少必填字段:order_province_code.";
		}
		
		//订单归属城市
		if (MallUtils.isEmpty(groupOrder.getOrder_city_code())) {
			return "缺少必填字段:OrderCityCode.";
		}
		
		//订单应收金额
		if (MallUtils.isEmpty(groupOrder.getOrder_amount())) {
			return "缺少必填字段:order_amount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(groupOrder.getOrder_amount());
				groupOrder.setOrder_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "order_amount值不正确.";
			}
		}
		
		//订单实收金额
		if (MallUtils.isEmpty(groupOrder.getPay_amount())) {
			return "缺少必填字段:pay_amount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(groupOrder.getPay_amount());
				groupOrder.setPay_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "pay_amount值不正确.";
			}
		}
		
		//订单减免金额
		if (MallUtils.isEmpty(groupOrder.getDiscount_amount())) {
			return "缺少必填字段:discount_amount.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(groupOrder.getDiscount_amount());
				groupOrder.setDiscount_amount(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "discount_amount值不正确.";
			}
		}
		
		//应收邮寄费用
		if (MallUtils.isEmpty(groupOrder.getOrig_post_fee())) {
			return "缺少必填字段:orig_post_fee.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(groupOrder.getOrig_post_fee());
				groupOrder.setOrig_post_fee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "orig_post_fee值不正确.";
			}
		}
		
		//实收邮寄费用
		if (MallUtils.isEmpty(groupOrder.getReal_post_fee())) {
			return "缺少必填字段:real_post_fee.";
		}else{
			try {
				//单位转换为厘
				Integer t = Integer.parseInt(groupOrder.getReal_post_fee());
				groupOrder.setReal_post_fee(MallUtils.parseMoney(t));
			} catch (Exception e) {
				return "real_post_fee值不正确.";
			}
		}
		
		//配送方式
		if (MallUtils.isEmpty(groupOrder.getDeli_mode())) {
			return "缺少必填字段:deli_mode.";
		}	
		
		//配送时间类型
		if (MallUtils.isEmpty(groupOrder.getDeli_time_mode())) {
			return "缺少必填字段:deli_time_mode.";
		} else {
			if(!( dbUtils.checkFieldValue("deliTimeMode", groupOrder.getDeli_time_mode()))){
				return "deli_time_mode值不正确.";
			}
		}
		
		//收货人姓名
		if (MallUtils.isEmpty(groupOrder.getShip_name())) {
			return "缺少必填字段:ship_name.";
		}
		
		//收货省
		if (MallUtils.isEmpty(groupOrder.getShip_province())) {
			return "缺少必填字段:ship_province.";
		}
		
		//收货地市
		if (MallUtils.isEmpty(groupOrder.getShip_city())) {
			return "缺少必填字段:ship_city.";
		}
		
		//收货地址
		if (MallUtils.isEmpty(groupOrder.getShip_addr())) {
			return "缺少必填字段:ship_addr.";
		}
		
		//联系电话
		if (MallUtils.isEmpty(groupOrder.getShip_tel())) {
			return "缺少必填字段:ship_tel.";
		}
		
		//实名认证类型
		if (MallUtils.isEmpty(groupOrder.getRealname_type())) {
			return "缺少必填字段:realname_type.";
		} 
		
		//是否合账
		if (MallUtils.isEmpty(groupOrder.getUser_to_account())) {
			return "缺少必填字段:user_to_account.";
		}
		
		List<GroupOrderGoodsInfo> goodsInfos = groupOrder.getGoods_info();
			//商品信息节点校验
			if(null == goodsInfos || goodsInfos.size() == 0){
				return "缺少必填节点:goods_info";
			}
			for (GroupOrderGoodsInfo goodsInfo : goodsInfos) {
				//商品编码
				if (MallUtils.isEmpty(goodsInfo.getProd_offer_code())) {
					return "缺少必填字段:prod_offer_code.";
				} 
				
				//商品名称
				if (MallUtils.isEmpty(goodsInfo.getProd_offer_name())) {
					return "缺少必填字段:prod_offer_name.";
				} 
				
				//商品数量
				if (MallUtils.isEmpty(String.valueOf(goodsInfo.getGoods_num()))) {
					return "缺少必填字段:goods_num.";
				} 
				
				//商品应收
				if (MallUtils.isEmpty(goodsInfo.getOffer_price())) {
					return "缺少必填字段:offer_price.";
				}else{
					try {
						//单位转换为厘
						Integer t = Integer.parseInt(goodsInfo.getOffer_price());
						goodsInfo.setOffer_price(MallUtils.parseMoney(t));
					} catch (Exception e) {
						return "offer_price值不正确.";
					}
				}
				
				//商品实收
				if (MallUtils.isEmpty(goodsInfo.getReal_offer_price())) {
					return "缺少必填字段:real_offer_price.";
				} else{
					try {
						//单位转换为厘
						Integer t = Integer.parseInt(goodsInfo.getReal_offer_price());
						goodsInfo.setReal_offer_price(MallUtils.parseMoney(t));
					} catch (Exception e) {
						return "real_offer_price值不正确.";
					}
				}
				
			}
		
		List<GroupOrderBroadInfo> broadInfos = groupOrder.getBroad_info();
		//固网信息节点校验
		if(null != broadInfos && broadInfos.size() > 0){
			for (GroupOrderBroadInfo broadInfo : broadInfos) {
				//产品分类
				if (MallUtils.isEmpty(broadInfo.getProduct_type())) {
					return "缺少必填字段:product_type.";
				} 
				
				//标准地址
				if (MallUtils.isEmpty(broadInfo.getStd_address())) {
					return "缺少必填字段:std_address.";
				}
				
				//宽带用户地址
				if (MallUtils.isEmpty(broadInfo.getUser_address())) {
					return "缺少必填字段:user_address.";
				}
				
				//县分编码
				if (MallUtils.isEmpty(broadInfo.getCounty_id())) {
					return "缺少必填字段:county_id.";
				}
				
				/*//局向编码
				if (MallUtils.isEmpty(broadInfo.getExch_code())) {
					return "缺少必填字段:exch_code.";
				}*/
				//区局
				/*
				if (MallUtils.isEmpty(broadInfo.getArea_exch_id())) {
					return "缺少必填字段:area_exch_id.";
				}
				//端局
				if (MallUtils.isEmpty(broadInfo.getPoint_exch_id())) {
					return "缺少必填字段:point_exch_id.";
				}
				
				//模块局
				if (MallUtils.isEmpty(broadInfo.getModule_exch_id())) {
					return "缺少必填字段:module_exch_id.";
				}
				*/
				/*//用户种类
				if (MallUtils.isEmpty(broadInfo.getUser_kind())) {
					return "缺少必填字段:user_kind.";
				}*/
				
				//宽带账号
			/*	if (MallUtils.isEmpty(broadInfo.getBb_account())) {
					return "缺少必填字段:bb_account.";
				}
				
				//宽带号码
				if (MallUtils.isEmpty(broadInfo.getBb_num())) {
					return "缺少必填字段:bb_num.";
				}*/
				
				/*//接入方式
				if (MallUtils.isEmpty(broadInfo.getAccess_type())) {
					return "缺少必填字段:access_type.";
				}*/
				
				//FeeInfo  费用明细
				List<GroupOrderFeeInfo> feeInfos = broadInfo.getFee_list();
				if(null != feeInfos && feeInfos.size() >0){
					for (GroupOrderFeeInfo feeInfo : feeInfos) {
						strTmp = checkFeeInfo(feeInfo);
						if ( !("".equals(strTmp)) ) {
							return strTmp;
						}
					}
				}
			}
		}

		
		GroupOrderCustInfo custInfo = groupOrder.getCust_info();
		//客户信息节点校验
		if (null == custInfo) {
			return "缺少必填节点:cust_info.";
		} 
		
		if(MallUtils.isEmpty(custInfo.getCert_expire_date())){
			//return "缺少必填字段:CertExpireDate";
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
			//return "缺少必填字段:CertEffectedDate";
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
		
		//客户姓名
		if(MallUtils.isEmpty(custInfo.getCustomer_name())){
			return "缺少必填字段:customer_name";
		}
		
		//用户类型
		if(MallUtils.isEmpty(custInfo.getUser_type())){
			return "缺少必填字段:user_type";
		}
		
		//客户类型
		if(MallUtils.isEmpty(custInfo.getCustomer_type())){
			return "缺少必填字段:customer_type";
		}
		
		//证件类型
		if(MallUtils.isEmpty(custInfo.getCert_type())){
			return "缺少必填字段:cert_type";
		}
		
		//证件号码
		if(MallUtils.isEmpty(custInfo.getCert_num())){
			return "缺少必填字段:cert_num";
		}
		
		//证件地址
		/*if(MallUtils.isEmpty(custInfo.getCert_addr())){
			return "缺少必填字段:cert_addr";
		}*/
		
		
		GroupOrderPayInfo payInfo = groupOrder.getPay_info();
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
		
		if(StringUtils.isEmpty(groupOrder.getIs_pay())){
			return "缺少必填字段:is_pay";
			
		}
		
		
		return "";
	}
	
	/**
	 * 检查费用明细
	 * @param feeInfo
	 * @return
	 */
	private static String checkFeeInfo(GroupOrderFeeInfo feeInfo){
		//账本ID
		/*if (MallUtils.isEmpty(feeInfo.getFeeID())) {
			return "缺少必填字段:feeID.";
		}
		//费用名称
		if (MallUtils.isEmpty(feeInfo.getFeeDes())) {
			return "缺少必填字段:feeDes.";
		}
		//应收金额
		if (MallUtils.isEmpty(feeInfo.getOrigFee())) {
			return "缺少必填字段:origFee.";
		}
		//减免金额
		if (MallUtils.isEmpty(feeInfo.getReliefFee())) {
			return "缺少必填字段:reliefFee.";
		}
		//实收金额
		if (MallUtils.isEmpty(feeInfo.getRealFee())) {
			return "缺少必填字段:realFee.";
		}*/
		return "";
	}
	
	
}
