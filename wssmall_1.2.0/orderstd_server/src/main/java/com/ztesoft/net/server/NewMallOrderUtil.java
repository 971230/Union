package com.ztesoft.net.server;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
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
import com.ztesoft.net.server.jsonserver.wcfpojo.MallActivity_List;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallCoupons_List;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallGift_List;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallOffer_Param;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallOrder;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallOrder_List;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallSKU_Param;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallUtils;
import com.ztesoft.net.server.jsonserver.wcfpojo.MallWCFPackage;
import com.ztesoft.net.server.jsonserver.wcfpojo.ZhwqInfoList;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsNiceInfo;
import com.ztesoft.net.server.jsonserver.zbpojo.CenterMallGoodsPhoneInfo;
import com.ztesoft.net.service.IOrderServiceLocal;
import com.ztesoft.orderstd.service.IOrderStandingManager;
import com.ztesoft.util.DevelopCheckInfoTableUtil;
import com.ztesoft.util.DevelopDeployExtUtil;

import consts.ConstsCore;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import params.ZteRequest;
import params.ZteResponse;
import params.order.req.OrderIdReq;
import params.order.resp.OrderIdResp;
import params.req.NewMallOrderStandardReq;
import params.resp.NewMallOrderStandardResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.ChannelConvertQrySubReq;
import zte.net.ecsord.params.ecaop.resp.ChannelConvertQrySubResp;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IOrderstdService;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.params.goods.req.GoodsGetReq;
import zte.params.goods.resp.GoodsGetResp;

public class NewMallOrderUtil {

	public static final String SOURCE_FROM_ZHWQ = "10067";// 浙江智慧沃企
	private static final String LOCAL_CHARSET = "utf-8";
	private static IOrderServiceLocal localGoodServices;
	private static Logger logger = Logger.getLogger(NewMallOrderUtil.class);
	private static IOutterECSTmplManager outterECSTmplManager;
	private static List<String> skuParamCodes = new ArrayList<String>(); // 必填的货品参数param_code
	private static List<String> skuParamNames = new ArrayList<String>(); // 必填的货品参数param_name
	private static SDBUtils dbUtils = null;
	private static IOrderstdService orderstdService;
	private static String newOrderPhoneNum;// 作废订单时记录新单的号码---zengxianlian
	private static IOrderStandingManager orderStandingManager;

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
	}

	public static NewMallOrderStandardResp orderStandardValidate(NewMallOrderStandardReq req) throws Exception {
		initBean();
		NewMallOrderStandardResp resp = new NewMallOrderStandardResp();
		MallOrder mallOrder = new MallOrder();// json转map的实体对象
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
		mallOrder = MallUtils.jsonToMallOrder(req_content);

		if (mallOrder.getExt_params() != null) {// 自动扩展表配置数据入库数据封装
			extMap = mallOrder.getExt_params();
		}
		// 检查必填项
		// 2.注意所有金额都在checkMallOrder方法里由厘转换为元
		String checkResult = checkMallOrder(mallOrder);
		CacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String kg_make_up = cacheUtil.getConfigInfo("channel_convert_kg_makeup");
		//收单发展点发展人值判断
        if("1".equals(kg_make_up) && StringUtil.isEmpty(checkResult)){
            IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
            String sql2 ="select distinct cat_id from es_goods t where t.sku='"+mallOrder.getOrder_list().get(0).getProd_offer_code()+"' and t.source_from= '"+ManagerUtils.getSourceFrom()+"'";
            String cat_id= baseDaoSupport.queryForString(sql2);
            String cbssGoodsCat = cacheUtil.getConfigInfo("CBSS_GOODS_CAT_"+mallOrder.getSource_from());
            if(!StringUtil.isEmpty(cat_id)&&!StringUtil.isEmpty(cbssGoodsCat) && cbssGoodsCat.contains(cat_id)){
                checkResult = checkDevelopNameAndCodeCbss(mallOrder);
            }
            String bssGoodsCat = cacheUtil.getConfigInfo("BSS_GOODS_CAT_"+mallOrder.getSource_from());
            if(!StringUtil.isEmpty(cat_id)&&!StringUtil.isEmpty(bssGoodsCat) && bssGoodsCat.contains(cat_id)){
                checkResult= checkDevelopNameAndCodeBSS(mallOrder);
            }
        }
		if (!StringUtil.isEmpty(checkResult)) {
			resp.setError_msg(checkResult);
			resp.setError_code(ConstsCore.ERROR_FAIL);
			return resp;
		}
		
		// 3.作废单请求---zengxianlian
		String cancelMsg = cancelOrdHandel(mallOrder, out_order_List, out_order, extMap);
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

	private static String checkDevelopNameAndCodeCbss(MallOrder mallOrder) {//针对于CB业务需求  是否可以按照字段进行判断
	
        StringBuffer stringBuffer = new StringBuffer();
        if(MallUtils.isEmpty(mallOrder.getDevelop_departId()) && MallUtils.isEmpty(mallOrder.getDevelop_code())){ //第四种  不进行处理
            return null;
        }
        if(!MallUtils.isEmpty(mallOrder.getDevelop_departId())){//cb 发展点  第一种可能
            if(mallOrder.getExtMap() == null){
                mallOrder.setExtMap(new HashMap<String, Object>());
            }
            if(MallUtils.isEmpty(mallOrder.getDevelop_code())){//cb发展人  有发展点无发展人拒绝收单
                stringBuffer.append("CBSS发展点和发展人信息不符合业务要求");
                return stringBuffer.toString();
            }else{//转换值  有发展点 有发展人
                ChannelConvertQrySubResp resp =  getConvertQry(mallOrder.getDevelop_code());//若有则进行传发展人
                Boolean flag = false;
                if("00000".equals(resp.getCode())){
                    flag = true;
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                } 
                 if(flag && MallUtils.isEmpty(resp.getRespJson().getZb_developer())){
                    stringBuffer.append("CBSS发展人传值错误");
                    return stringBuffer.toString();
                }if(flag && !MallUtils.isEmpty(resp.getRespJson().getZb_developer())){//如果接口返回发展人编码和收单不一致  
                    if(!mallOrder.getDevelop_departId().equals(resp.getRespJson().getSf_channel_id()) && !mallOrder.getDevelop_departId().equals(resp.getRespJson().getZb_channel_id())){
                        stringBuffer.append("CBSS发展人和CBSS发展点关系不一致");
                        return stringBuffer.toString();
                    }else{
                        mallOrder.setDevelop_departId(resp.getRespJson().getZb_channel_id());
                        mallOrder.getExtMap().put("development_point_name", resp.getRespJson().getSf_channel_name());//发展点名称
                        mallOrder.setDevelop_code(resp.getRespJson().getZb_developer());
                        mallOrder.setDevelop_name(resp.getRespJson().getSf_developer_name());//cb发展人名称
                        return null;
                    }
                }
            }
        }else{
            if(!MallUtils.isEmpty(mallOrder.getDevelop_code())){//转换按照规则3 无发展点  有发展人信息
                //DOTO 请求转换进行判断
                ChannelConvertQrySubResp resp =  getConvertQry(mallOrder.getDevelop_code());//若有则进行传发展人
                Boolean flag = false;
                if("00000".equals(resp.getCode())){
                    flag = true;
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
                if(flag){
                    if(MallUtils.isEmpty(resp.getRespJson().getZb_developer())){
                        stringBuffer.append("CBSS发展人传值错误");
                        return stringBuffer.toString();
                    }else{
                        mallOrder.setDevelop_departId(resp.getRespJson().getZb_channel_id());
                        mallOrder.getExtMap().put("development_point_name", resp.getRespJson().getSf_channel_name());
                        mallOrder.setDevelop_code(resp.getRespJson().getZb_developer());
                        mallOrder.setDevelop_name(resp.getRespJson().getSf_developer_name());
                        return null;
                     }
                }
            }
        }
        return null;
    }
	
	private static String checkDevelopNameAndCodeBSS(MallOrder mallOrder) {//针对于BSS业务需求  是否可以按照字段进行判断
        // TODO Auto-generated method stub
        StringBuffer stringBuffer = new StringBuffer();
        //develop_code  发展点  develop_name   发展人     development_point_name 落发展点名称
        if(mallOrder.getExtMap() == null){
            mallOrder.setExtMap(new HashMap<String, Object>());
        }
        if(MallUtils.isEmpty(mallOrder.getDevelop_code()) && MallUtils.isEmpty(mallOrder.getDevelop_name())){ //第四种  不进行处理
            return null;
        }
        if(!MallUtils.isEmpty(mallOrder.getDevelop_code())){//b 发展点  第一种可能
        	 if(mallOrder.getExtMap() == null){
                 mallOrder.setExtMap(new HashMap<String, Object>());
             }
            if(MallUtils.isEmpty(mallOrder.getDevelop_name())){//b  只有发展点 没有发展人
                ChannelConvertQrySubResp resp =  getConvertQry(mallOrder.getDevelop_code());//若有则进行传发展人
                if("00000".equals(resp.getCode())){
                	if (!MallUtils.isEmpty(resp.getRespJson().getSf_channel_id())) {
                		 mallOrder.setDevelop_code(resp.getRespJson().getSf_channel_id());
                         mallOrder.getExtMap().put("development_point_name", resp.getRespJson().getSf_channel_name());
                        return null;
					} else {
						stringBuffer.append("根据收单发展点查不到省分发展点");
	                    return stringBuffer.toString();
					}
                	
                }else if("00502".equals(resp.getCode())){
                	stringBuffer.append("无发展点编码信息");
                    return stringBuffer.toString();
                }else{
                	 stringBuffer.append(resp.getMsg());
                     return stringBuffer.toString();
                }
            }else{//转换值  有发展点 有发展人 规则2
                ChannelConvertQrySubResp resp =  getConvertQry(mallOrder.getDevelop_name());//若有则进行传发展人
                if("00000".equals(resp.getCode())){
                    if(MallUtils.isEmpty(resp.getRespJson().getSf_developer())){//如果发展人为空拒绝收单
                        stringBuffer.append("bss发展点传值错误");
                        return stringBuffer.toString();
                    }else{
                        if(!mallOrder.getDevelop_code().equals(resp.getRespJson().getSf_channel_id()) && !mallOrder.getDevelop_code().equals(resp.getRespJson().getZb_channel_id())){
                            stringBuffer.append("bss发展点和bss发展人关系不一致");
                            return stringBuffer.toString();
                        }else{
                            mallOrder.setDevelop_code(resp.getRespJson().getSf_channel_id());
                            mallOrder.getExtMap().put("development_point_code", resp.getRespJson().getSf_channel_id());
                            mallOrder.getExtMap().put("development_point_name", resp.getRespJson().getSf_channel_name());
                            mallOrder.setDevelop_name(resp.getRespJson().getSf_developer());
                            mallOrder.getExtMap().put("develop_name_new", resp.getRespJson().getSf_developer_name());//存放省份发展人名称
                            return null;
                        }
                    }
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
            }
        }else{
            if(!MallUtils.isEmpty(mallOrder.getDevelop_name())){
                ChannelConvertQrySubResp resp =  getConvertQry(mallOrder.getDevelop_name());//若有则进行传发展人
                if("00000".equals(resp.getCode())){
                    if(!MallUtils.isEmpty(resp.getRespJson().getSf_developer())){
                        mallOrder.setDevelop_name(resp.getRespJson().getSf_developer());//发展人编码
                        mallOrder.getExtMap().put("development_point_code", resp.getRespJson().getSf_channel_id());
                        mallOrder.getExtMap().put("development_point_name", resp.getRespJson().getSf_channel_name());//发展点名称
                        mallOrder.setDevelop_code(resp.getRespJson().getSf_channel_id());//发展点编码
                        mallOrder.getExtMap().put("develop_name_new", resp.getRespJson().getSf_developer_name());//存放省份名称
                        return null;
                    }else{
                        stringBuffer.append("bss发展人传值错误");
                        return stringBuffer.toString();
                    }
                }else if("00502".equals(resp.getCode())){
                    stringBuffer.append("无发展人编码信息");
                    return stringBuffer.toString();
                }else{
                    stringBuffer.append(resp.getMsg());
                    return stringBuffer.toString();
                }
            }
        }
        return null;
    }
	private static ChannelConvertQrySubResp getConvertQry (String channelInfo){
        try{
            ChannelConvertQrySubResp resp = new ChannelConvertQrySubResp();
            ChannelConvertQrySubReq matchReq = new ChannelConvertQrySubReq();
            matchReq.setOp_type("21");
            matchReq.setOp_value(channelInfo);
            ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
            resp = client.execute(matchReq, ChannelConvertQrySubResp.class);
            return resp;
        }catch(Exception e){
            ChannelConvertQrySubResp resp = new ChannelConvertQrySubResp();
            return resp;
        }
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

	public static String cancelOrdHandel(MallOrder mallOrder, List<Outer> out_order_List, Outer out_order, Map extMap) throws Exception {
		String result_msg = "";
		String orderId = "";
		if (null == mallOrder.getIf_cancel()) {
			mallOrder.setIf_cancel("0");
		}
		boolean cancelFlag = "1".equals(mallOrder.getIf_cancel()) ? true : false;// 作废单标识
		boolean flag = true;
		if (cancelFlag) {// 作废单请求---zengxianlian\
			OrderIdReq orderIdReq = new OrderIdReq();
			orderIdReq.setOrder_out_id(mallOrder.getOrder_id());
			OrderIdResp orderIdResp = orderstdService.orderId(orderIdReq);
			if (!StringUtil.isEmpty(orderIdResp.getOrder_id())) {
				orderId = orderIdResp.getOrder_id();
			}
			int temp = getSaveOrderFlag(orderId);
			if (0 != temp) {// 不符合作废单规则
				flag = false;
				result_msg = getReturnValueByTempNew(temp, mallOrder.getOrder_id());
			}
		}
		if (flag) {
			// json参数set到Outer对象中,再次校验号卡、号卡合约、合约机、上网卡必填字段
			result_msg = setOrderValue(out_order, mallOrder, extMap);
			if (cancelFlag) {// 作废单请求---zengxianlian
				// 对旧单做处理,并复制数据至新单
				handelOrder(orderId, getNewOrderPhoneNum(), extMap);
				// 复制数据后,需要把新单的if_cancel字段置为0
				out_order.setOrder_if_cancel("0");
			}
		}
		return result_msg;
	}

	private static String getReturnValueByTempNew(int temp, String outId) {
		String returnValue = "";
		switch (temp) {
		case 1:
			returnValue = "作废订单[" + outId + "]失败,作废单不存在旧单";
			break;
		case 2:
			returnValue = "作废订单[" + outId + "]失败,作废订单不是AOP/BSS+现场交付的订单";
			break;
		case 3:
			returnValue = "作废订单[" + outId + "]失败,作废订单不在特定处理环节";
			break;
		default:
			break;
		}
		return returnValue;
	}

	/**
	 * 把json参数值存入outer对象中
	 * 
	 * @param outer
	 * @param mallOrder
	 */
	private static String setOrderValue(Outer outer, MallOrder mallOrder, Map extMap) {
		// 序列号 serial_no
		outer.setPayplatformorderid(MallUtils.getValues(mallOrder.getSerial_no()));
		// 时间 time
		outer.setGet_time(MallUtils.strFormatDate(MallUtils.strToDate(mallOrder.getTime())));
		// 发起方系统标识 source_system
		outer.setOrder_channel(mallOrder.getSource_system());
		// 订单编号 order_id
		outer.setOut_tid(MallUtils.getValues(mallOrder.getOrder_id()));
		// 订单类型 order_type
		outer.setType(MallUtils.getValues(mallOrder.getOrder_type()));
		if ("4".equals(mallOrder.getOrder_type())) {// 预约单设置值---zengxianlian
			outer.setWm_isreservation_from("1");
		} else {
			outer.setWm_isreservation_from("0");
		}
		// 订单状态 status
		outer.setStatus(MallUtils.getValues(mallOrder.getStatus()));
		// 订单来源系统 source_from_system
		outer.setPlat_type(MallUtils.getValues(mallOrder.getSource_from_system()));
		// 订单来源 source_from
		outer.setOrder_from(mallOrder.getSource_from());
		// outer.setC_order_from(MallUtils.getValues(mallOrder.getSourceFromName(mallOrder.getSource_system())));
		// //中文订单来源
		// 归属地市 order_city
		outer.setOrder_city_code(MallUtils.getValues(mallOrder.getOrder_city()));
		// 发展人编码 development_code
		// outer.setDevelopment_code(MallUtils.getValues(mallOrder.getDevelop_code()));
		// 推荐人手机 reference_phone
		// outer.setRecommended_phone(MallUtils.getValues(mallOrder.getReference_phone()));
		// 推荐人名称 reference_name
		outer.setRecommended_name(MallUtils.getValues(mallOrder.getReference_name()));
		// 下单时间 create_time
		if (MallUtils.isNotEmpty(mallOrder.getCreate_time())) {
			outer.setTid_time(MallUtils.strFormatDate(MallUtils.strToDate(mallOrder.getCreate_time())));
		}
		// 支付时间 pay_time
		outer.setPay_time(MallUtils.getValues(mallOrder.getPay_time()));
		// 支付类型 pay_type
		outer.setPaytype(MallUtils.getValues(mallOrder.getPay_type()));

		// 支付状态
		/*
		 * if ("HDFK".equalsIgnoreCase(mallOrder.getPay_type())) {
		 * outer.setPay_status("0"); }else { outer.setPay_status("1"); }
		 */
		if (StringUtil.isEmpty(mallOrder.getIs_pay()) || StringUtil.equals("0", mallOrder.getIs_pay())) {
			outer.setPay_status("0");
		} else {
			outer.setPay_status("1");
		}
		// 支付方式 payment_type
		outer.setPay_method(MallUtils.getValues(mallOrder.getPayment_type()));
		// 支付流水号 payment_serial_no
		outer.setPayplatformorderid(MallUtils.getValues(mallOrder.getPayment_serial_no()));
		// 支付机构编码 payment_code
		outer.setPayproviderid(MallUtils.getValues(mallOrder.getPayment_code()));
		// 支付机构名称 payment_name
		outer.setPayprovidername(MallUtils.getValues(mallOrder.getPayment_name()));
		// 支付渠道编码 payment_channel_code
		outer.setPaychannelid(MallUtils.getValues(mallOrder.getPayment_channel_code()));
		// 支付渠道名称 payment_channel_name
		outer.setPaychannelname(MallUtils.getValues(mallOrder.getPayment_channel_name()));

		// 外部支付账号 alipay_id
		outer.setAlipay_id(MallUtils.getValues(mallOrder.getAlipay_id()));
		// 买家名称 name
		outer.setBuyer_name(MallUtils.getValues(mallOrder.getOld_cust_id()));
		// 买家昵称 uname
		outer.setBuyer_id(MallUtils.getValues(mallOrder.getUname()));
		// 发货状态 delivery_status
		outer.setDelivery_status(MallUtils.getValues(mallOrder.getDelivery_status()));
		// 异常状态 abnormal_status
		outer.setAbnormal_status(MallUtils.getValues(mallOrder.getAbnormal_status()));
		// 外部平台状态 platform_status
		outer.setPlatform_status(MallUtils.getValues(mallOrder.getPlatform_status()));
		// 订单总价 order_amount
		outer.setOrder_totalfee(MallUtils.getValues(mallOrder.getOrder_amount()));
		// 优惠金额 order_disacount
		outer.setDiscountValue(MallUtils.getValues(mallOrder.getOrder_disacount()));
		// 实收金额 pay_money
		outer.setOrder_realfee(MallUtils.getValues(mallOrder.getPay_money()));
		// 应收运费 shipping_amount
		outer.setPost_fee(MallUtils.getValues(mallOrder.getShipping_amount()));
		// 接收是否作废单标识,用来区分能否重复入库---zengxianlian
		outer.setOrder_if_cancel(mallOrder.getIf_cancel());
		// 配送方式 shipping_type
		outer.setSending_type(mallOrder.getShipping_type());
		// 收货人姓名 ship_name
		outer.setReceiver_name(MallUtils.getValues(mallOrder.getShip_name()));
		// 地址地市 ship_city
		outer.setCity_code(MallUtils.getValues(mallOrder.getShip_city()));
		// 地址区县 ship_country
		outer.setArea_code(MallUtils.getValues(mallOrder.getShip_country()));
		
		
		outer.setDistrict(localGoodServices.getCountryName(mallOrder.getShip_country())); //收件人县分名称中文
		outer.setCity(localGoodServices.getCityname(mallOrder.getShip_city())); // 收件人地市中文 
		outer.setProvinc_code(localGoodServices.getProviceId(mallOrder.getShip_city())); // 收件人省份编码
		outer.setProvince(localGoodServices.getProvicename(mallOrder.getShip_city())); // 收件人省份中文
		
		
		
		// 订单归属省份编码 order_provinc_code
		outer.setOrder_provinc_code(MallUtils.getValues(mallOrder.getOrder_provinc_code()));
		// 订单归属地市编码 order_city_code
		outer.setOrder_city_code(MallUtils.getValues(mallOrder.getOrder_city_code()));
		// 地址 ship_addr
		if (dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type())) {
			String shipAddress = MallUtils.getValues(mallOrder.getShip_addr());
			String temp = null;
			Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
			Matcher m = p.matcher(shipAddress);
			int count = 0;
			while (m.find()) {
				temp = m.group(0);
				count = count + temp.length();
			}
			if (count < 8) {
				shipAddress = shipAddress + "(无需配送身份证件地址为空)";
			}
			outer.setAddress(shipAddress);
		} else {
			outer.setAddress(MallUtils.getValues(mallOrder.getShip_addr()));
		}
		// 邮件编码 postcode
		outer.setPost(MallUtils.getValues(mallOrder.getPostcode()));
		// 固定电话 ship_tel
		outer.setPhone(MallUtils.getValues(mallOrder.getShip_tel()));
		// 手机号码 ship_phone
		outer.setReceiver_mobile(MallUtils.getValues(mallOrder.getShip_phone()));
		// 百度账号 baidu_account
		outer.setBaidu_id(MallUtils.getValues(mallOrder.getBaidu_account()));
		// 百度冻结流水号 baidu_no
		outer.setFreeze_tran_no(MallUtils.getValues(mallOrder.getBaidu_no()));
		// 百度冻结款 baidu_money
		outer.setFreeze_free(MallUtils.getValues(mallOrder.getBaidu_money()));
		// 买家留言 buyer_message
		outer.setBuyer_message(MallUtils.getValues(mallOrder.getBuyer_message()));
		// 订单备注 order_comment
		outer.setService_remarks(MallUtils.getValues(mallOrder.getOrder_comment()));
		
		outer.setOrder_deal_method(MallUtils.getValues(mallOrder.getOrder_deal_method()));
		outer.setFlow_code(MallUtils.getValues(mallOrder.getFlow_code()));

		// 库中无对应字段存储的数据
		// 接收方系统标识 receive_system Reserve0
		outer.setReserve0(mallOrder.getReceive_system());
		// 是否2G、3G升4G is_to4g Reserve1
		outer.setReserve1(MallUtils.getValues(mallOrder.getIs_to4g()));
		// 渠道类型 channel_mark Reserve2
		outer.setReserve2(MallUtils.getValues(mallOrder.getChannel_mark()));
		// 渠道标识 channel_id Reserve3
		outer.setReserve3(MallUtils.getValues(mallOrder.getChannel_id()));
		// 渠道名称 chanel_name Reserve4
		outer.setReserve4(MallUtils.getValues(mallOrder.getChanel_name()));
		// 百度冻结开始时间 baidu_begin_time Reserve5
		if (MallUtils.isNotEmpty(mallOrder.getBaidu_begin_time())) {
			outer.setReserve5(MallUtils.strFormatDate(MallUtils.strToDate(mallOrder.getBaidu_begin_time())));
		}
		// 百度冻结结束时间 baidu_end_time Reserve6
		if (MallUtils.isNotEmpty(mallOrder.getBaidu_end_time())) {
			outer.setReserve6(MallUtils.strFormatDate(MallUtils.strToDate(mallOrder.getBaidu_end_time())));
		}
		//靓号信息
		outer.setNice_info(mallOrder.getNice_info());
		// 基金类型
		if (MallUtils.isNotEmpty(mallOrder.getFund_type())) {
			extMap.put("fund_type", mallOrder.getFund_type());
		}
		//
		if (MallUtils.isNotEmpty(mallOrder.getIntent_order_id())) {
			extMap.put("intent_order_id", mallOrder.getIntent_order_id());
		}
		
		//add by sgf
        if (MallUtils.isNotEmpty(mallOrder.getIs_blankcard())) {
            extMap.put("is_blankcard", mallOrder.getIs_blankcard());
        }
        if (MallUtils.isNotEmpty(mallOrder.getIs_new())) {
            extMap.put("is_new", mallOrder.getIs_new());
        }
		// 所属用户 ssyh
		outer.setReserve7(MallUtils.getValues(mallOrder.getSsyh()));
		
		
		extMap.put("evdo_num", mallOrder.getEvdo_num());
		extMap.put("user_kind", mallOrder.getUser_kind());
		
		// 设置扩展信息

		// 是否已办理完成 is_deal
		if (MallUtils.isNotEmpty(mallOrder.getIs_deal())) {
			extMap.put("is_deal", mallOrder.getIs_deal());
		}
		// 买家标识 uid
		if (MallUtils.isNotEmpty(mallOrder.getUid())) {
			extMap.put("uid", mallOrder.getUid());
		}
		if (MallUtils.isNotEmpty(mallOrder.getIccid())) {
			extMap.put("ICCID", mallOrder.getIccid());
		}
		// 订单重量（kg） order_heavy
		if (MallUtils.isNotEmpty(mallOrder.getOrder_heavy())) {
			extMap.put("order_heavy", mallOrder.getOrder_heavy());
		}
		// 实收运费 n_shipping_amount
		if (MallUtils.isNotEmpty(mallOrder.getN_shipping_amount())) {
			extMap.put("n_shipping_amount", mallOrder.getN_shipping_amount());
		}
		// 物流公司编码 shipping_company
		if (MallUtils.isNotEmpty(mallOrder.getShipping_company())) {
			extMap.put("shipping_company", mallOrder.getShipping_company());
		}
		// 配送时间 shipping_time
		if (MallUtils.isNotEmpty(mallOrder.getShipping_time())) {
			extMap.put("shipping_time", mallOrder.getShipping_time());
		}
		// 推荐人手机 reference_phone
		if (MallUtils.isNotEmpty(mallOrder.getReference_phone())) {
			extMap.put("reference_phone", mallOrder.getReference_phone());
		}
		// 地址商圈 ship_area
		if (MallUtils.isNotEmpty(mallOrder.getShip_area())) {
			extMap.put("ship_area", mallOrder.getShip_area());
		}
		// 电子邮件 ship_email
		if (MallUtils.isNotEmpty(mallOrder.getShip_email())) {
			extMap.put("ship_email", mallOrder.getShip_email());
		}
		// 卖家留言 seller_message
		if (MallUtils.isNotEmpty(mallOrder.getSeller_message())) {
			extMap.put("seller_message", mallOrder.getSeller_message());
		}
		// 是否闪电送 shipping_quick
		if (MallUtils.isNotEmpty(mallOrder.getShipping_quick())) {
			extMap.put("shipping_quick", mallOrder.getShipping_quick());
		}
		// 集团编码
		if (MallUtils.isNotEmpty(mallOrder.getGroup_code())) {
			extMap.put("group_code", mallOrder.getGroup_code());
		}
		// 集团名称
		if (MallUtils.isNotEmpty(mallOrder.getGroup_name())) {
			extMap.put("group_name", mallOrder.getGroup_name());
		}
		// 服务类型
		if (MallUtils.isNotEmpty(mallOrder.getService_type())) {
			extMap.put("service_type", mallOrder.getService_type());
		}
		// 行业来源
		if (MallUtils.isNotEmpty(mallOrder.getIndustry_type())) {
			extMap.put("industry_source", mallOrder.getIndustry_source());
		}
		// 行业应用类别
		if (MallUtils.isNotEmpty(mallOrder.getIndustry_type())) {
			extMap.put("industry_type", mallOrder.getIndustry_type());
		}
		// 应用子类别
		if (MallUtils.isNotEmpty(mallOrder.getIndustry_sub_type())) {
			extMap.put("industry_sub_type", mallOrder.getIndustry_sub_type());
		}
		// 订单兑换积分（分）
		if (MallUtils.isNotEmpty(mallOrder.getOrder_points())) {
			extMap.put("order_points", mallOrder.getOrder_points());
		}
		// 积分兑换用户
		if (MallUtils.isNotEmpty(mallOrder.getPoints_user())) {
			extMap.put("points_user", mallOrder.getPoints_user());
		}
		// 推广渠道
		if (MallUtils.isNotEmpty(mallOrder.getSpread_channel())) {
			extMap.put("spread_channel", mallOrder.getSpread_channel());
		}
		// BSS工号
		if (MallUtils.isNotEmpty(mallOrder.getBss_operator())) {
			extMap.put("bss_operator", mallOrder.getBss_operator());
		}
		// BSS工号名称
		if (MallUtils.isNotEmpty(mallOrder.getBss_operator_name())) {
			extMap.put("bss_operator_name", mallOrder.getBss_operator_name());
		}
		// 订单支撑系统工号
		if (MallUtils.isNotEmpty(mallOrder.getOss_operator())) {
			extMap.put("oss_operator", mallOrder.getOss_operator());
			extMap.put("lock_user_id", mallOrder.getOss_operator());
			extMap.put("lock_status", EcsOrderConsts.LOCK_STATUS);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			extMap.put("lock_time", dateFormat.format(new Date()));
		}
		/**
		 * TODO sgf  本次上线不进行覆盖提交代码
		 *  收单报文 develop_code  发展点编码 ---入库develop_point_code_new
		 *                       发展点名称 -- 入库development_point_name
		 *         develop_name  发展人编码 ---入库 develop_code_new 
		 *                       发展人名称--- 入库develop_name_new    
		 *         数据迁移             develop_name_new --迁移至---develop_code_new
		 *                       develop_code_new --迁移至---develop_point_code_new
		 *         现在无法区分出develop_name的信息值
		 *         现有生产数据均为号卡类业务信息
		 */
        IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
       /* String sql2 ="select distinct cat_id from es_goods t where t.sku='"+mallOrder.getOrder_list().get(0).getProd_offer_code()+"' and t.source_from= '"+ManagerUtils.getSourceFrom()+"'";
        String cat_id = baseDaoSupport.queryForString(sql2);*/
        String sql3 ="select distinct cat_id,type_id,goods_id from es_goods t where t.sku='"+mallOrder.getOrder_list().get(0).getProd_offer_code()+"' and t.source_from= '"+ManagerUtils.getSourceFrom()+"'";
        List<Map<String, String>> listInfo = baseDaoSupport.queryForList(sql3);
        String cat_id="";
        String type_id="";
        String goods_id="";
        if(listInfo.size()>0){
          cat_id   =   listInfo.get(0).get("cat_id");
          type_id  =   listInfo.get(0).get("type_id");
          goods_id =   listInfo.get(0).get("goods_id");
        }
        Map<String, String> develop_infoMap = new HashMap<String, String>();
        develop_infoMap.put("type_id",type_id);//大类
        develop_infoMap.put("cat_id",cat_id);//小类
        develop_infoMap.put("goods_id",goods_id);//商品
        develop_infoMap.put("order_city_code",  MallUtils.getValues(mallOrder.getOrder_city()));//地市
        develop_infoMap.put("source_system_type",mallOrder.getSource_from() );//来源
        if("10071".equals(mallOrder.getSource_from())){
            develop_infoMap.put("source_system","AB");//来源大类
        }else{
            develop_infoMap.put("source_system",mallOrder.getSource_system());//来源大类
        }
        develop_infoMap.put("order_county_code", "ZJ"+localGoodServices.getCityname(mallOrder.getShip_country()));//县分
        List<DevelopDeployExtUtil> tList = new ArrayList<DevelopDeployExtUtil>();
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String makeup_develop_deploy = cacheUtil.getConfigInfo("makeup_develop_deploy");
        String cbssGoodsCat = cacheUtil.getConfigInfo("CBSS_GOODS_CAT"); //分b和cb的
        if(!StringUtil.isEmpty(makeup_develop_deploy) && "1".equals(makeup_develop_deploy)){
            tList = DevelopCheckInfoTableUtil.developFindExt(develop_infoMap);
        }
        if(tList!=null&&!tList.isEmpty() && tList.size()>0){
            DevelopDeployExtUtil deployExt = tList.get(0);
            if(!MallUtils.isEmpty(cat_id)&& cbssGoodsCat.contains(cat_id)){
                mallOrder.setDevelop_code(deployExt.getDeveop_code());
                mallOrder.setDevelop_departId(deployExt.getDevelop_point_code());
            }else{
                mallOrder.setDevelop_code(deployExt.getDevelop_point_code());
                mallOrder.setDevelop_name(deployExt.getDeveop_code());
            }
        }
        if(!MallUtils.isEmpty(cat_id)&& cbssGoodsCat.contains(cat_id)){//cbss商品
            if (MallUtils.isNotEmpty(mallOrder.getDevelop_code())) {
                extMap.put("development_code", mallOrder.getDevelop_code());//cbss 取这套
                outer.setDevelopment_code(mallOrder.getDevelop_code());     
                extMap.put("develop_code_new", mallOrder.getDevelop_code()); //cbss 需要落到这个点
            }
            if (MallUtils.isNotEmpty(mallOrder.getDevelop_name())) {
                extMap.put("development_name", mallOrder.getDevelop_name()); //cbss取这套
                outer.setDevelopment_name(mallOrder.getDevelop_name());
                extMap.put("develop_name_new", mallOrder.getDevelop_name());//cbss 需要落到这个值
            }
            extMap.put("development_point_code", mallOrder.getDevelop_departId());
            extMap.put("develop_point_code_new", mallOrder.getDevelop_departId());
        }else{
            if (MallUtils.isNotEmpty(mallOrder.getDevelop_code())) {
                extMap.put("development_code", mallOrder.getDevelop_code());//cbss 取这套
                outer.setDevelopment_code(mallOrder.getDevelop_code());     
                extMap.put("develop_point_code_new", mallOrder.getDevelop_code());//bss 取这个套
                extMap.put("development_point_code", mallOrder.getDevelop_code());
            }
            if (MallUtils.isNotEmpty(mallOrder.getDevelop_name())) {//bss这处是发展人编码   cbss是发展人编码
                extMap.put("development_name", mallOrder.getDevelop_name()); //  旧一套编码也是落值
                outer.setDevelopment_name(mallOrder.getDevelop_name());
                extMap.put("develop_code_new", mallOrder.getDevelop_name());//bss需要落到这个点
            }
        }
		Map sub_other_info = new HashMap();

		// 收入归集集团15位编号
		if (MallUtils.isNotEmpty(mallOrder.getGroup_code())) {
			// orderSubProdInfoBusiRequest.setGroup_code(mallOrder.getGroup_code());
			sub_other_info.put("group_code", mallOrder.getGroup_code());
		}

		// 集团客户编号,可空
		if (MallUtils.isNotEmpty(mallOrder.getCust_id())) {
			sub_other_info.put("cust_id", mallOrder.getCust_id());
			extMap.put("cust_id", mallOrder.getCust_id());//CB副卡开户必传对应主卡号码的老客户ID
		}

		// 责任人/使用人标识
		if (MallUtils.isNotEmpty(mallOrder.getCertify_flag())) {
			sub_other_info.put("certify_flag", mallOrder.getCertify_flag());
		}

		// 责任人/使用人证件类型
		if (MallUtils.isNotEmpty(mallOrder.getCertify_cert_type())) {
			sub_other_info.put("certify_cert_type", mallOrder.getCertify_cert_type());
		}

		// 责任人/使用人证件号码
		if (MallUtils.isNotEmpty(mallOrder.getCertify_cert_num())) {
			sub_other_info.put("certify_cert_num", mallOrder.getCertify_cert_num());
		}

		// 责任人/使用人客户姓名
		if (MallUtils.isNotEmpty(mallOrder.getCertify_cust_name())) {
			sub_other_info.put("certify_cust_name", mallOrder.getCertify_cust_name());
		}

		// 责任人/使用人客户证件地址
		if (MallUtils.isNotEmpty(mallOrder.getCertify_cert_addr())) {
			sub_other_info.put("certify_cert_addr", mallOrder.getCertify_cert_addr());
		}

		JSONObject jsonObj = JSONObject.fromObject(sub_other_info);
		extMap.put("subotherinfo", jsonObj.toString());
		
		// 智慧沃企新增操作员，操作点
		if (MallUtils.isNotEmpty(mallOrder.getOut_office())) {
			extMap.put("out_office", mallOrder.getOut_office());
		}
		if(MallUtils.isNotEmpty(mallOrder.getOut_operator())){
		    extMap.put("out_operator", mallOrder.getOut_operator());
		    
		}
		// 代理商编码
		if (MallUtils.isNotEmpty(mallOrder.getAgent_code())) {
			extMap.put("agent_code", mallOrder.getAgent_code());
		}
		// 代理商名字
		if (MallUtils.isNotEmpty(mallOrder.getAgent_name())) {
			extMap.put("agent_name", mallOrder.getAgent_name());
		}
		// 代理商地市
		if (MallUtils.isNotEmpty(mallOrder.getAgent_city())) {
			extMap.put("agent_city", mallOrder.getAgent_city());
		}
		// 代理商归属县
		if (MallUtils.isNotEmpty(mallOrder.getAgent_area())) {
			extMap.put("agent_district", mallOrder.getAgent_area());
		}
		// 渠道类型
		if (MallUtils.isNotEmpty(mallOrder.getChannel_type())) {
			extMap.put("channel_type", mallOrder.getChannel_type());
		}
		if (MallUtils.isNotEmpty(mallOrder.getChannel_id())) {
			extMap.put("channelId", mallOrder.getChannel_id());
		}
		if (MallUtils.isNotEmpty(mallOrder.getSale_mod_type())) {
			extMap.put("sale_mod_type", mallOrder.getSale_mod_type());
		}
		if (MallUtils.isNotEmpty(mallOrder.getMarking_tag())) {
			extMap.put("marking_tag", mallOrder.getMarking_tag());
		}
		if (MallUtils.isNotEmpty(mallOrder.getDistrict_code())) {
			extMap.put("district_code", mallOrder.getDistrict_code());
		}
		if (MallUtils.isNotEmpty(mallOrder.getBssOrderId())) {
			extMap.put("bssOrderId", mallOrder.getBssOrderId());
		}
		if (MallUtils.isNotEmpty(mallOrder.getIs_aop())) {
			extMap.put("is_aop", mallOrder.getIs_aop());
		}

		if (MallUtils.isNotEmpty(mallOrder.getOld_cust_id())) {
			extMap.put("cust_id", mallOrder.getOld_cust_id());
		}

		// 是否需要审核证件照
		if (MallUtils.isNotEmpty(mallOrder.getIs_examine_card())) {
			extMap.put("is_examine_card", mallOrder.getIs_examine_card());
		} else if (MallUtils.isNotEmpty(mallOrder.getOrder_list().get(0).getRealname_flag())) {
			String realname = mallOrder.getOrder_list().get(0).getRealname_flag();
			if ("1".equals(realname)) {
				extMap.put("is_examine_card", "0");
			} else {
				extMap.put("is_examine_card", "1");
			}
		}
		// 销售商品名称
		if (MallUtils.isNotEmpty(mallOrder.getSales_name())) {
			extMap.put("sales_name", mallOrder.getSales_name());
		}
		extMap.put("is_pay", mallOrder.getIs_pay());
		extMap.put("couponbatchid", mallOrder.getCouponbatchid());
		extMap.put("is_realname", mallOrder.getIs_realname());

		if (mallOrder.getExtMap() instanceof Map && null != mallOrder.getExtMap() && !mallOrder.getExtMap().isEmpty()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(mallOrder.getExtMap());
			for (String name : map.keySet()) {
				if (null != map.get(name) && !StringUtils.isEmpty(map.get(name) + "")) {
					extMap.put(name, map.get(name));
				}
			}
		}

		String IS_SHOW_UPLOAD_PHOTOS_BTN = cacheUtil.getConfigInfo("UPLOAD_PHOTOS_BTN_" + mallOrder.getSource_from()+"_CAT_ID_"+cat_id);
		if(!StringUtil.isEmpty(IS_SHOW_UPLOAD_PHOTOS_BTN)&&"yes".equals(IS_SHOW_UPLOAD_PHOTOS_BTN)){
			extMap.put("if_Send_Photos", "0");
		}
		
		outer.setExtMap(extMap);

		// order_list参数值
		return setOrderListValue(outer, mallOrder, extMap);

	}

	/**
	 * 把order_list参数值set到OuterItem中
	 * 
	 * @param outer
	 * @param mallOrder
	 */
	private static String setOrderListValue(Outer outer, MallOrder mallOrder, Map extMap) {

		StringBuffer stringBuffer = new StringBuffer();
		List<MallOrder_List> orderLists = mallOrder.getOrder_list();
		List<OuterItem> itemList = new ArrayList<OuterItem>();

		// 存放产品参数
		Map<String, String> param = new HashMap<String, String>();
		JSONArray array = new JSONArray();

		int num = 0;
		for (MallOrder_List ordList : orderLists) {
			OuterItem item = new OuterItem();
			String outer_sku_id = "";
			String pagkage_id = "";

			// 客户类型
			if (StringUtils.isEmpty((String) extMap.get("CustomerType")) || "JTKH".equals(extMap.get("CustomerType"))) {// 只要有"集团客户",这个订单客户类型即为"集团客户"
				extMap.put("CustomerType", ordList.getCust_type());
			}
			// 判断沃财富(Resourcestypeid、Activitycode)/新商城(Prod_offer_code)的字段是否不为空
			boolean flag = false;

			if (dbUtils.checkFieldValue("iswcf", mallOrder.getSource_from())) {
				// 沃财富
				outer_sku_id = MallUtils.getValues(ordList.getResourcestypeid());
				pagkage_id = MallUtils.getValues(ordList.getActivitycode());
				if (MallUtils.isNotEmpty(outer_sku_id) && MallUtils.isNotEmpty(pagkage_id)) {
					flag = true;
				}
			} else {
				// 新商城
				outer_sku_id = MallUtils.getValues(ordList.getProd_offer_code());
				if (MallUtils.isNotEmpty(outer_sku_id)) {
					flag = true;
				}
			}
			if (flag) {
				GoodsGetReq req = new GoodsGetReq();
				GoodsGetResp resp = null;
				try {
					if (dbUtils.checkFieldValue("iswcf", mallOrder.getSource_from())) {
						req.setPackage_id(pagkage_id);
						req.setSn(outer_sku_id);
						// 与总部商城类似
						ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
						resp = client.execute(req, GoodsGetResp.class);
					} else {
						req.setPackage_id(outer_sku_id);
						resp = localGoodServices.getGoods(req);
					}
				} catch (Exception e) {
					logger.info(e.getMessage(), e);
					OuterError ng = new OuterError(null, outer_sku_id, outer_sku_id, null, mallOrder.getSource_from(), mallOrder.getOrder_id(), "sysdate", "goodserror");// noparam
																																											// nogoods
					ng.setDeal_flag("-1");
					outterECSTmplManager.insertOuterError(ng);
					logger.info("订单：" + mallOrder.getOrder_id() + "获取商品相关参数失败======================");
					continue;
				}

				if ("0".equals(resp.getError_code())) {
					param = resp.getData();

					// 获取大类类型type_id
					String type_id = param.get("type_id").toString();
					logger.info("=============type_id:" + type_id);
					// 商品小类
					String cat_id = param.get("product_type").toString();

					// 是否老用户标识
					String is_old = "0";
					// 本地表对象,存放货品参数
					LocalOrderAttr orderAttr = new LocalOrderAttr();
					String package_sale = null;
					if (StringUtils.isEmpty(ordList.getPackage_sale()) || StringUtils.equals("NO", ordList.getPackage_sale())) {
						package_sale = "0";
					} else if (StringUtils.equals("YES", ordList.getPackage_sale())) {
						package_sale = "1";
					}
					orderAttr.setPackage_sale(package_sale);
					// 货品参数校验
					List<MallSKU_Param> skuParams = ordList.getSku_param();
					// 号码信息
					CenterMallGoodsPhoneInfo phoneInfo = new CenterMallGoodsPhoneInfo();
					CenterMallGoodsNiceInfo niceInfo = new CenterMallGoodsNiceInfo();
					phoneInfo.setNiceInfo(niceInfo);
					if (null != skuParams && skuParams.size() != 0) {
						for (MallSKU_Param mallSKU_Param : skuParams) {
							if ("acc_nbr".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								// 在1.16中对商品大类为“礼品”的订单模板增加“号码”货品（可选）
								// 对于“附加产品”类的礼品，号码节点必传，表示实际办理的号码（兑换用户本人号码或转赠号码）
								if ("20008".equals(type_id) && "69110100".equals(cat_id) && MallUtils.isEmpty(mallSKU_Param.getParam_code())) {
									stringBuffer.append("缺少必填字段:acc_nbr,附加产品类礼品acc_nbr必填.");
								}
								item.setPhone_num(mallSKU_Param.getParam_value()); // 用户号码
								phoneInfo.setPhoneNum(mallSKU_Param.getParam_value());
								setNewOrderPhoneNum(mallSKU_Param.getParam_value());
							} else if ("is_goodno".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setIs_liang(mallSKU_Param.getParam_value()); // 靓号
							} else if ("good_no_fee".equalsIgnoreCase(mallSKU_Param.getParam_code()) && MallUtils.isNotEmpty(mallSKU_Param.getParam_value())) {
								orderAttr.setGood_no_fee(mallSKU_Param.getParam_value());
								item.setLiang_price(Double.parseDouble(mallSKU_Param.getParam_value())); // 靓号预存
							} else if ("if_love_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setIs_loves_phone(mallSKU_Param.getParam_value()); // 是否情侣号
							} else if ("love_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setLoves_phone_num(mallSKU_Param.getParam_value()); // 情侣号
							} else if ("family_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setFamliy_num(mallSKU_Param.getParam_value()); // 亲情号码
							} else if ("parents_bank_code".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setSuperiors_bankcode(mallSKU_Param.getParam_value()); // 上级银行编码
								orderAttr.setParents_bank_code(mallSKU_Param.getParam_value());
							} else if ("bank_code".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setBank_code(mallSKU_Param.getParam_value()); // 银行编码
								orderAttr.setBank_code(mallSKU_Param.getParam_value());
							} else if ("bank_cust_code".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setBank_account(mallSKU_Param.getParam_value()); // 银行账号
								orderAttr.setBank_cust_code(mallSKU_Param.getParam_value());
							} else if ("bank_cust_name".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setBank_user(mallSKU_Param.getParam_value()); // 银行开户名
								orderAttr.setBank_cust_name(mallSKU_Param.getParam_value());
							} else if ("card_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setWhite_cart_type(mallSKU_Param.getParam_value()); // 卡类型
							} else if ("is_old".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setIs_old(mallSKU_Param.getParam_value()); // 是否老用户
								is_old = mallSKU_Param.getParam_value();
							} else if ("password".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setPassword(mallSKU_Param.getParam_value()); // 老用户密码
							} else if ("net_region".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setNet_region(mallSKU_Param.getParam_value()); // 入网地址
							} else if ("vicecard_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setVicecard_no(mallSKU_Param.getParam_value()); // 副卡号码
							} else if ("net_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								item.setProduct_net(mallSKU_Param.getParam_value()); // 网别
							} else if ("good_no_low".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setGood_no_low(mallSKU_Param.getParam_value()); // 靓号低消
							} else if ("sex".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								String sex = mallSKU_Param.getParam_value();
								// String is_examine_card =
								// mallOrder.getIs_examine_card();
								// if("0".equals(is_examine_card)&&MallUtils.isEmpty(sex)){
								// stringBuffer.append("需要校验证件照时缺少必填字段:sex.");
								// }else{
								//
								// }
								orderAttr.setSex(sex); // 性别
								ordList.setCerti_sex(sex);
							} else if ("birthday".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBirthday(mallSKU_Param.getParam_value()); // 出生日期
							} else if ("contact_addr".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setContact_addr(mallSKU_Param.getParam_value()); // 通讯地址
							} else if ("bill_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBill_type(mallSKU_Param.getParam_value()); // 账户付费方式
							} else if ("bank_name".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBank_name(mallSKU_Param.getParam_value()); // 银行名称
							} else if ("sim_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {// 商城协议是否成品卡为0-白卡、1-成卡、2-半成卡;订单系统内部编码为0-成卡、1-白卡、2-半成卡;因此这里做下特殊处理
								if (EcsOrderConsts.SIM_TYPE_CK.equals(mallSKU_Param.getParam_value())) {
									orderAttr.setSim_type(EcsOrderConsts.SIM_TYPE_BK);
								} else if (EcsOrderConsts.SIM_TYPE_BK.equals(mallSKU_Param.getParam_value())) {
									orderAttr.setSim_type(EcsOrderConsts.SIM_TYPE_CK);
								} else {
									orderAttr.setSim_type(mallSKU_Param.getParam_value()); // 成卡/白卡
								}
							} else if ("credit_class".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setCredit_class(mallSKU_Param.getParam_value()); // 信用等级
							} else if ("credit_adjust".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setCredit_adjust(mallSKU_Param.getParam_value()); // 信用度调整
							} else if ("guarantor".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setGuarantor(mallSKU_Param.getParam_value()); // 担保人
							} else if ("guarantor_info".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setGuarantor_info(mallSKU_Param.getParam_value()); // 担保信息参数
							} else if ("guarantor_certi_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setGuarantor_certi_type(mallSKU_Param.getParam_value()); // 被担保人证件类型
							} else if ("guarantor_certi_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setGuarantor_certi_no(mallSKU_Param.getParam_value()); // 被担保人证件号码
							} else if ("bill_mail_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBill_mail_type(mallSKU_Param.getParam_value()); // 账单寄送方式
							} else if ("bill_mail_content".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBill_mail_content(mallSKU_Param.getParam_value()); // 账单寄送内容
							} else if ("bill_mail_rec".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBill_mail_rec(mallSKU_Param.getParam_value()); // 账单收件人
							} else if ("bill_mail_addr".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBill_mail_addr(mallSKU_Param.getParam_value()); // 账单寄送地址
							} else if ("bill_mail_post_code".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setBill_mail_post_code(mallSKU_Param.getParam_value()); // 账单寄送邮编
							} else if ("sub_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								orderAttr.setSub_no(mallSKU_Param.getParam_value()); // 共享子号
							} else if ("advance_com".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setAdvanceCom(mallSKU_Param.getParam_value()); // 普通预存
							} else if ("advance_spe".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setAdvanceSpe(mallSKU_Param.getParam_value()); // 专项预存
							} else if ("num_thaw_tim".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setNumThawTim(mallSKU_Param.getParam_value()); // 返还时长
							} else if ("num_thaw_pro".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setNumThawPro(mallSKU_Param.getParam_value()); // 分月返还金额
							} else if ("class_id".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setClassId(mallSKU_Param.getParam_value()); // 号码等级
							} else if ("LowCostChe".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setLowCostChe(mallSKU_Param.getParam_value()); // 考核期最低消费
							} else if ("time_dur_che".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setTimeDurChe(mallSKU_Param.getParam_value()); // 考核时长
							} else if ("change_tag_che".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setChangeTagChe(mallSKU_Param.getParam_value()); // 考核期是否过户
							} else if ("cancel_tag_che".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setCancelTagChe(mallSKU_Param.getParam_value()); // 考核期是否销户
							} else if ("bremon_che".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setBremonChe(mallSKU_Param.getParam_value()); // 考核期违约金月份
							} else if ("low_cost_pro".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setLowCostPro(mallSKU_Param.getParam_value()); // 协议期最低消费
							} else if ("time_dur_pro".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setTimeDurPro(mallSKU_Param.getParam_value()); // 协议时长
							} else if ("change_tag_pro".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setChangeTagPro(mallSKU_Param.getParam_value()); // 协议期是否过户
							} else if ("cancel_tag_pro".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setCancelTagPro(mallSKU_Param.getParam_value()); // 协议期是否销户
							} else if ("bro_mon_pro".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								niceInfo.setBroMonPro(mallSKU_Param.getParam_value()); // 协议期违约金月份
							} else if ("operator_state".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								phoneInfo.setOperatorState(mallSKU_Param.getParam_value()); // 操作状态
							} else if ("pro_key".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								phoneInfo.setProKey(mallSKU_Param.getParam_value()); // 资源预占关键字
							} else if ("OccupiedFlag".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								phoneInfo.setOccupiedFlag(mallSKU_Param.getParam_value()); // 号码状态标识
							} else if ("occupied_time".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								phoneInfo.setOccupiedTime(mallSKU_Param.getParam_value()); // 号码状态标识时间
							} else if ("pro_key_mode".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								phoneInfo.setProKeyMode(mallSKU_Param.getParam_value()); // 号码资源预占关键字类
							}
						}
					}
					// 老用户填WXZ
					if ("1".equals(is_old)) {
						if (MallUtils.isEmpty(ordList.getCerti_type()) || MallUtils.isEmpty(ordList.getCerti_num())) {
							ordList.setCerti_type("WXZ");
						}
					}
					if (SOURCE_FROM_ZHWQ.equals(mallOrder.getSource_from())) {// 如果是智慧沃企订单来源默认新用户
																				// add
																				// by
																				// mo.chencheng
																				// 2016-11-16
						if (MallUtils.isEmpty(orderAttr.getIs_old())) {
							orderAttr.setIs_old(is_old);
						}
					}
					// 号卡、号卡合约、合约机、上网卡必填字段
					if ("20000".equals(type_id) || "20002".equals(type_id) || "20001".equals(type_id) || "20021".equals(type_id)) {
						if (MallUtils.isEmpty(ordList.getCust_name())) {
							stringBuffer.append("缺少必填字段:cust_name.");
						}
						// 号卡、号卡合约、合约机必填
						if ("20000".equals(type_id) || "20002".equals(type_id) || "20021".equals(type_id)) {
							if (MallUtils.isEmpty(ordList.getCerti_type()) && !"1".equals(is_old)) {
								stringBuffer.append("缺少必填字段:certi_type.");
							}
							if (MallUtils.isEmpty(ordList.getCerti_num()) && !"1".equals(is_old)) {
								stringBuffer.append("缺少必填字段:certi_num.");
							}
							if (!"20021".equals(type_id)) {
								// 首月资费方式
								if (MallUtils.isEmpty(ordList.getOffer_eff_type())) {
									stringBuffer.append("缺少必填字段:offer_eff_type.");
								}
							}
						}
					}
					boolean isLove = false; // 是否情侣号
					String guarantor = ""; // 担保人信息
					String guarantor_info = ""; // 担保信息
					String guarantor_certi_type = ""; // 被担保人证件类型
					String guarantor_certi_no = ""; // 被担保人证件号码
					String loveNo = ""; // 情侣号
					if (null != skuParams && skuParams.size() > 0) {
						for (MallSKU_Param mallSKU_Param : skuParams) {
							if ("20000".equals(type_id) || "20002".equals(type_id)) {
								if ("acc_nbr".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
									if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
										// 号卡、号卡合约、合约机未传acc_nbr字段校验
										stringBuffer.append("sku_param缺少必填参数:acc_nbr.");
									} else if (mallSKU_Param.getParam_value().length() != 11) {
										stringBuffer.append("sku_param参数:acc_nbr的值不正确.");
									}
								}
								if ("card_type".equalsIgnoreCase(mallSKU_Param.getParam_code()) && MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:card_type.");
								}
							}

							if ("is_old".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:is_old.");
								}
							} else if ("if_love_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:if_love_no.");
								} else if ("1".equals(mallSKU_Param.getParam_value())) {
									isLove = true;
								}
							} else if ("net_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:net_type.");
								}
							} else if ("is_goodno".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:is_goodno.");
								}
							} else if ("bill_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:bill_type.");
								}
							} else if ("card_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isNotEmpty(mallSKU_Param.getParam_value())) {
									if (!(dbUtils.checkFieldValue("card_type", mallSKU_Param.getParam_value()))) {
										stringBuffer.append("card_type未按协议取值.");
									}
								}
							} else if ("guarantor".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:guarantor.");
								} else {
									guarantor = mallSKU_Param.getParam_value().equals("无") ? "" : mallSKU_Param.getParam_value();
								}
							} else if ("bill_mail_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								if (MallUtils.isEmpty(mallSKU_Param.getParam_value())) {
									stringBuffer.append("sku_param缺少必填参数值:bill_mail_type.");
								}
							} else if ("love_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								loveNo = mallSKU_Param.getParam_value();
							} else if ("guarantor_info".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								guarantor_info = mallSKU_Param.getParam_value();
							} else if ("guarantor_certi_type".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								guarantor_certi_type = mallSKU_Param.getParam_value();
							} else if ("guarantor_certi_no".equalsIgnoreCase(mallSKU_Param.getParam_code())) {
								guarantor_certi_no = mallSKU_Param.getParam_value();
							}

						}
					}
					// 是否情侣号时必须传情侣号
					if (isLove && MallUtils.isEmpty(loveNo)) {
						stringBuffer.append("sku_param缺少必填参数值:love_no.");
					}
					// 有担保人时担保人相关信息必填
					if (MallUtils.isNotEmpty(guarantor)) {
						if (MallUtils.isEmpty(guarantor_info)) {
							stringBuffer.append("sku_param缺少必填参数值:guarantor_info.");
						}
						if (MallUtils.isEmpty(guarantor_certi_type)) {
							stringBuffer.append("sku_param缺少必填参数值:guarantor_certi_type.");
						} else {
							if (!(dbUtils.checkFieldValue("certi_type", guarantor_certi_type))) {
								stringBuffer.append("guarantor_certi_type未按协议取值.");
							}
						}
						if (MallUtils.isEmpty(guarantor_certi_no)) {
							stringBuffer.append("sku_param缺少必填参数值:guarantor_certi_no.");
						}
					}
					if (MallUtils.isNotEmpty(stringBuffer.toString())) {
						return stringBuffer.toString();
					}
					if (MallUtils.isEmpty(ordList.getInventory_code())) {
						ordList.setInventory_code("-1");
					}
					if (MallUtils.isEmpty(ordList.getInventory_name())) {
						ordList.setInventory_name("-1");
					}
					// 商品价格 offer_price
					item.setSell_price(Double.parseDouble(ordList.getOffer_price()));
					// 优惠价格（厘） offer_disacount_price
					// 实收价格（厘） offer_coupon_price
					item.setPro_origfee(Double.parseDouble(ordList.getOffer_coupon_price()));
					// 商品数量 prod_offer_num
					item.setPro_num(Integer.parseInt(ordList.getProd_offer_num()));
					// 发票类型 invoice_type
					if (MallUtils.isNotEmpty(ordList.getInvoice_type())) {
						outer.setInvoice_type(Integer.parseInt(ordList.getInvoice_type()));
					}
					// 发票打印方式 invoice_print_type
					outer.setInvoice_print_type(ordList.getInvoice_print_type());
					// 发票抬头 invoice_title
					outer.setReserve8(ordList.getInvoice_title());
					// 发票明细 invoice_content
					outer.setInvoice_title_desc(ordList.getInvoice_content());
					// 仓库编码 inventory_code
					item.setOut_house_id(ordList.getInventory_code());
					// 商品备注 offer_comment
					item.setPlan_title(ordList.getOffer_comment());
					// 证件类型 certi_type
					item.setCert_type(ordList.getCerti_type());
					// 证件号码 certi_num
					item.setCert_card_num(MallUtils.getValues(ordList.getCerti_num()));
					// 民族
					item.setCert_nation(MallUtils.getValues(ordList.getCerti_nation()));
					//出入境号
					item.setCert_num2(MallUtils.getValues(ordList.getCert_num2()));
					// 证件生效时间
					item.setCert_eff_time(ordList.getCerti_eff_date());
					// 证件机关
					item.setCert_issuer(ordList.getCerti_issuer());
					// 证件失效时间
					item.setCert_failure_time(ordList.getCerti_valid_date());
					// 证件地址 certi_address
					item.setCert_address(MallUtils.getValues(ordList.getCerti_address()));
					// 证件性别
					item.setCerti_sex(MallUtils.getValues(ordList.getCerti_sex()));
					outer.setCert_address(MallUtils.getValues(ordList.getCerti_address()));
					// 客户名称 cust_name
					item.setPhone_owner_name(ordList.getCust_name());
					extMap.put("birthday", ordList.getCust_birthday());
					extMap.put("is_old", is_old);
					if (!StringUtils.isEmpty(ordList.getCert_addr())) {
						outer.setCert_address(MallUtils.getValues(ordList.getCert_addr()));
						item.setCert_address(MallUtils.getValues(ordList.getCert_addr()));
					}
					if (!StringUtils.isEmpty(ordList.getCert_effected_date())) {
						item.setCert_eff_time(ordList.getCert_effected_date());
					}
					if (!StringUtils.isEmpty(ordList.getCert_num())) {
						item.setCert_card_num(MallUtils.getValues(ordList.getCert_num()));
					}
					if (!StringUtils.isEmpty(ordList.getCert_expire_date())) {
						item.setCert_failure_time(ordList.getCert_expire_date());
					}
					if (!StringUtils.isEmpty(ordList.getCert_nation())) {
						item.setCert_nation(MallUtils.getValues(ordList.getCert_nation()));
					}
					if (!StringUtils.isEmpty(ordList.getCert_sex())) {
						extMap.put("sex", ordList.getCert_sex());
					}
					if (!StringUtils.isEmpty(ordList.getCustomer_name())) {
						item.setPhone_owner_name(ordList.getCustomer_name());
					}
					if (!StringUtils.isEmpty(ordList.getCert_sex())) {
						extMap.put("certi_issuer", ordList.getCerti_issuer());
					}
					// 首月资费方式 offer_eff_type
					item.setFirst_payment(ordList.getOffer_eff_type());
					// 合约编码 activitycode
					item.setOut_package_id(ordList.getActivitycode());
					// 机型编码 resourcestypeid
					item.setModel_code(ordList.getResourcestypeid());
					// 减免预存标记
					item.setReliefpres_flag("NO");

					StringBuffer pmt_code = new StringBuffer();
					// 优惠信息
					List<MallActivity_List> activity_Lists = ordList.getActivity_list();
					if (null != activity_Lists && activity_Lists.size() != 0) {
						JSONArray jsonArray = JSONArray.fromObject(activity_Lists);
						extMap.put("activity_list", jsonArray.toString());

						for (int i = 0; i < jsonArray.size(); ++i) {
							pmt_code.append(jsonArray.getJSONObject(i).get("activity_code")).append(",");
						}
						pmt_code = pmt_code.deleteCharAt(pmt_code.lastIndexOf(","));
						logger.debug("新商城订单" + outer.getOut_tid() + "匹配到的活动编码有:" + pmt_code.toString());
						logger.info("新商城订单" + outer.getOut_tid() + "匹配到的活动编码有:" + pmt_code.toString());
					}
					List<ZhwqInfoList> zhwqInfoList = ordList.getZhwq_info();
					if (null != zhwqInfoList && zhwqInfoList.size() != 0) {
						JSONArray jsonArray = JSONArray.fromObject(zhwqInfoList);
						extMap.put("zhwq_info", jsonArray.toString());
					}

					// 商品参数
					List<MallOffer_Param> offerParams = ordList.getOffer_param();
					if (null != offerParams && offerParams.size() != 0) {
						for (MallOffer_Param mallOffer_Param : offerParams) {
							// 商品参数处理逻辑
							;
						}
					}

					// 可选包
					List<MallWCFPackage> wcfPackages = ordList.getPackage();
					if (null != wcfPackages && wcfPackages.size() != 0) {
						JSONArray jsonArray = JSONArray.fromObject(wcfPackages);
						extMap.put("wcfPackages", jsonArray.toString());
					}

					// 设置保存货品参数
					outer.setLocalObject(orderAttr);

					/**
					 * ZX add 2016-01-05 start 副卡，共享子号
					 */
					JSONObject jsonObject = JSONObject.fromObject(orderAttr);
					extMap.put("zb_fuka_info", jsonObject.toString());
					/**
					 * ZX add 2016-01-05 end 副卡，共享子号
					 */

					// 产品参数
					setPackageGoodsParam(item, param);
					if ("7".equals(mallOrder.getOrder_type())) {
						// B2B专供产品参数
						JSONObject object = new JSONObject();
						object.put("virtualGoodsId", param.get("goods_id"));
						object.put("virtualGoodsNum", ordList.getProd_offer_num());
						object.put("virtualGoodsPrice", ordList.getOffer_price());
						array.add(object);
					}

					// 扩展信息
					// 发票内容 invoice_group_content
					if (MallUtils.isNotEmpty(ordList.getInvoice_group_content())) {
						extMap.put("invoice_group_content", ordList.getInvoice_group_content());
					}
					// 仓库名称 inventory_name
					if (MallUtils.isNotEmpty(ordList.getInventory_name())) {
						extMap.put("inventory_name", ordList.getInventory_name());
					}
					// 可选活动 choose_active
					if (MallUtils.isNotEmpty(ordList.getChoose_active())) {
						extMap.put("choose_active", ordList.getChoose_active());
					}
					// 终端串号
					if (MallUtils.isNotEmpty(ordList.getTerminal_num())) {
						extMap.put("terminal_num", ordList.getTerminal_num());
					}
					// 号码信息
					if (null != phoneInfo) {
						JSONArray jsonArray = JSONArray.fromObject(phoneInfo);
						extMap.put("phoneInfo", jsonArray.toString());
					}
					// 是否变更套餐
					String is_change = MallUtils.isEmpty(ordList.getIs_change()) ? "0" : ordList.getIs_change();
					extMap.put("is_change", is_change);

					// package_sale 套包销售标记
					extMap.put("package_sale", package_sale);
					// choose_active 可选活动
					extMap.put("choose_active", ordList.getChoose_active());
					// 商品兑换积分
					if (MallUtils.isNotEmpty(ordList.getOffer_point())) {
						extMap.put("offer_point", ordList.getOffer_point());
					}

					if (num == 0) {
						itemList.add(item);
						num++;
					}

					// 获取商品参与活动的活动编码
					// 应收
					double orderAmount = Double.parseDouble(mallOrder.getOrder_amount());
					// 实收
					double payMoney = Double.parseDouble(mallOrder.getPay_money());
					// 直降金额
					String relieffee = MallUtils.parseMoneyToLi(orderAmount - payMoney);

					// ordList.getActivity_list();
					// com.ztesoft.net.outter.inf.util.ZhuanDuiBaoUtil.save(outer.getOut_tid(),pmt_code.toString());
					extMap.put("proactivity", pmt_code.toString());
					extMap.put("order_realfee", payMoney+"");
					// 优惠券列表
					List<MallCoupons_List> coupons_list = ordList.getCoupons_list();
					if (null != coupons_list && coupons_list.size() > 0) {
						JSONArray jsonArray = JSONArray.fromObject(coupons_list);
						extMap.put(AttrConsts.COUPONS_LIST, jsonArray.toString());
						// 代金券的锁定状态
						extMap.put(AttrConsts.COUPONS_LOCKED, coupons_list.get(0).getCoupons_locked());
					}
				} else {
					OuterError ng = new OuterError(null, outer_sku_id, outer_sku_id, null, mallOrder.getSource_from_system(), mallOrder.getOrder_id(), "sysdate", "goodserror");// noparam
																																												// nogoods
					ng.setDeal_flag("-1");
					outterECSTmplManager.insertOuterError(ng);
					logger.info("订单：" + mallOrder.getOrder_id() + "电商没有配置商品=====");
					return "电商没有配置商品";
				}
			}
		}
		if ("7".equals(mallOrder.getOrder_type())) {
			extMap.put("virtual_pro_num", array.toString());
		}

		outer.setItemList(itemList);
		return "";
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
		// 商品大类
		if (MallUtils.isNotEmpty(param.get("type_id"))) {
			item.setType_id(param.get("type_id"));
		}

	}

	/**
	 * 商城json参数中必填项校验
	 * 
	 * @param mallOrder
	 * @return
	 */
	private static String checkMallOrder(MallOrder mallOrder) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer stringBuffer = new StringBuffer();

		// 序列号
		// if(!SOURCE_FROM_ZHWQ.equals(mallOrder.getSource_from())){//如果是智慧沃企订单来源则先不校验
		// add by mo.chencheng 2016-11-17
		// if (MallUtils.isEmpty(mallOrder.getSerial_no())) {
		// stringBuffer.append("缺少必填字段:serial_no.");
		// }
		// }

		// 时间
		if (MallUtils.isEmpty(mallOrder.getTime())) {
			stringBuffer.append("缺少必填字段:time.");
		} else {
			try {
				format.parse(mallOrder.getTime());
			} catch (Exception e) {
				stringBuffer.append("time值不正确.");
			}
		}
		// 发起方系统标识
		if (MallUtils.isEmpty(mallOrder.getSource_system())) {
			stringBuffer.append("缺少必填字段:source_system.");
		} else {
			if (!(dbUtils.checkFieldValue("source_system", mallOrder.getSource_system()))) {
				stringBuffer.append("source_system未按协议取值.");
			}
		}
		// 接收方系统标识
		if (MallUtils.isEmpty(mallOrder.getReceive_system())) {
			stringBuffer.append("缺少必填字段:receive_system.");
		} else {
			if (!"10011".equals(mallOrder.getReceive_system())) {
				stringBuffer.append("receive_system未按协议取值.");
			}
		}
		// 订单编码
		if (MallUtils.isEmpty(mallOrder.getOrder_id())) {
			stringBuffer.append("缺少必填字段:order_id.");
		}
		// 订单类型
		if (MallUtils.isEmpty(mallOrder.getOrder_type())) {
			stringBuffer.append("缺少必填字段:order_type.");
		} else {
			if (!(dbUtils.checkFieldValue("order_type", mallOrder.getOrder_type()))) {
				stringBuffer.append("order_type未按协议取值.");
			}
		}
		// 订单状态
		if (MallUtils.isEmpty(mallOrder.getStatus())) {
			stringBuffer.append("缺少必填字段:status.");
		}
		// 是否2G、3G升4G
		if (MallUtils.isEmpty(mallOrder.getIs_to4g())) {
			stringBuffer.append("缺少必填字段:is_to4g.");
		} else {
			if (!(dbUtils.checkFieldValue("is_to4g", mallOrder.getIs_to4g()))) {
				stringBuffer.append("is_to4g未按协议取值.");
			}
		}
		// 订单来源系统
		if (MallUtils.isEmpty(mallOrder.getSource_from_system())) {
			stringBuffer.append("缺少必填字段:source_from_system.");
		} else {
			if (!(dbUtils.checkFieldValue("source_from_system", mallOrder.getSource_from_system()))) {
				stringBuffer.append("source_from_system未按协议取值.");
			}
		}
		// 订单来源
		if (MallUtils.isEmpty(mallOrder.getSource_from())) {
			stringBuffer.append("缺少必填字段:source_from.");
		} else {
			if (!(dbUtils.checkFieldValue("source_from", mallOrder.getSource_from()))) {
				stringBuffer.append("source_from未按协议取值.");
			}
		}
		// 归属地市
		// modify by liu.quan 注释原因：童欣和客户确认，将此字段改为非必填，取消校验
		// if (MallUtils.isEmpty(mallOrder.getOrder_city())) {
		// stringBuffer.append("缺少必填字段:order_city.");
		// }else{
		// if( !( dbUtils.checkFieldValue("order_city",
		// mallOrder.getOrder_city()) ) ){
		// stringBuffer.append("order_city未按协议取值.");
		// }
		// }
		// 渠道标记
		if (MallUtils.isEmpty(mallOrder.getChannel_mark())) {
			stringBuffer.append("缺少必填字段:channel_mark.");
		} else {
			if (!(dbUtils.checkFieldValue("channel_mark", mallOrder.getChannel_mark()))) {
				stringBuffer.append("channel_mark值不正确.");
			}
		}
		// BSS工号
		if (dbUtils.checkFieldValue("isxj", mallOrder.getShipping_type()) && MallUtils.isEmpty(mallOrder.getBss_operator())) {
			stringBuffer.append("缺少必填字段:bss_operator.");
		}
		// 订单支撑系统工号
		if (dbUtils.checkFieldValue("isxj", mallOrder.getShipping_type()) && MallUtils.isEmpty(mallOrder.getOss_operator())) {
			stringBuffer.append("缺少必填字段:oss_operator.");
		}
		// 下单时间
		if (MallUtils.isEmpty(mallOrder.getCreate_time())) {
			stringBuffer.append("缺少必填字段:create_time.");
		} else {
			try {
				format.parse(mallOrder.getCreate_time());
			} catch (Exception e) {
				stringBuffer.append("create_time值不正确.");
			}
		}
		// 支付时间
		if (MallUtils.isNotEmpty(mallOrder.getPay_time())) {
			try {
				Date d = format.parse(mallOrder.getPay_time());
				// pay_time由yyyymmddhh24miss转换为yyyy-mm-dd hh24:mi:ss
				mallOrder.setPay_time(MallUtils.strFormatDate(d));
			} catch (Exception e) {
				stringBuffer.append("pay_time值不正确.");
			}
		}
		// 支付类型
		if (MallUtils.isEmpty(mallOrder.getPay_type())) {
			stringBuffer.append("缺少必填字段:pay_type.");
		} else {
			if (!(dbUtils.checkFieldValue("pay_type", mallOrder.getPay_type()))) {
				stringBuffer.append("pay_type未按协议取值.");
			}
		}
		// 支付类型为“积分支付（JFZF）”时，订单层“兑换积分”必填
		if ("JFZF".equals(mallOrder.getPay_type()) && MallUtils.isEmpty(mallOrder.getOrder_points())) {
			stringBuffer.append("缺少必填字段:order_points.");
		}
		if ("JFZF".equals(mallOrder.getPay_type()) && MallUtils.isEmpty(mallOrder.getPoints_user())) {
			stringBuffer.append("缺少必填字段:points_user.");
		}
		// 支付类型为在线支付时，支付时间必填
		if ("ZXZF".equals(mallOrder.getPay_type()) && MallUtils.isEmpty(mallOrder.getPay_time())) {
			stringBuffer.append("在线支付缺少必填字段:pay_time.");
		}
		// 支付方式
		if (MallUtils.isEmpty(mallOrder.getPayment_type())) {
			stringBuffer.append("缺少必填字段:payment_type.");
		} else {
			if (!(dbUtils.checkFieldValue("payment_type", mallOrder.getPayment_type()))) {
				stringBuffer.append("payment_type未按协议取值.");
			}
		}
		// 支付机构编码 payment_code
		if (MallUtils.isNotEmpty(mallOrder.getPayment_code())) {
			if (!(dbUtils.checkFieldValue("payment_code", mallOrder.getPayment_code()))) {
				stringBuffer.append("payment_code未按协议取值.");
			}
		}
		// 支付机构名称 payment_name
		if (MallUtils.isNotEmpty(mallOrder.getPayment_name())) {
			if (!(dbUtils.checkFieldValue("payment_name", mallOrder.getPayment_name()))) {
				stringBuffer.append("payment_name未按协议取值.");
			}
		}
		// 支付渠道编码 payment_channel_code
		if (MallUtils.isNotEmpty(mallOrder.getPayment_channel_code())) {
			if (!(dbUtils.checkFieldValue("payment_channel_code", mallOrder.getPayment_channel_code()))) {
				stringBuffer.append("payment_channel_code未按协议取值.");
			}
		}
		// 支付渠道名称 payment_channel_name
		if (MallUtils.isNotEmpty(mallOrder.getPayment_channel_name())) {
			if (!(dbUtils.checkFieldValue("payment_channel_name", mallOrder.getPayment_channel_name()))) {
				stringBuffer.append("payment_channel_name未按协议取值.");
			}
		}

		// 买家名称
		if (MallUtils.isEmpty(mallOrder.getName())) {
			stringBuffer.append("缺少必填字段:name.");
		}
		// 买家标识
		if (MallUtils.isEmpty(mallOrder.getUid())) {
			stringBuffer.append("缺少必填字段:uid.");
		}
		// 买家昵称
		if (MallUtils.isEmpty(mallOrder.getUname())) {
			stringBuffer.append("缺少必填字段:uname.");
		}
		// 发货状态
		if (MallUtils.isEmpty(mallOrder.getDelivery_status())) {
			stringBuffer.append("缺少必填字段:delivery_status.");
		} else {
			if (!(dbUtils.checkFieldValue("delivery_status", mallOrder.getDelivery_status()))) {
				stringBuffer.append("delivery_status未按协议取值.");
			}
		}
		// 异常状态
		if (MallUtils.isEmpty(mallOrder.getAbnormal_status())) {
			stringBuffer.append("缺少必填字段:abnormal_status.");
		}
		// 外部平台状态
		if (MallUtils.isEmpty(mallOrder.getPlatform_status())) {
			stringBuffer.append("缺少必填字段:platform_status.");
		}
		// 订单总价
		if (MallUtils.isEmpty(mallOrder.getOrder_amount())) {
			stringBuffer.append("缺少必填字段:order_amount.");
		} /*
			 * else { try { //单位转换为元 Integer i =
			 * Integer.parseInt(mallOrder.getOrder_amount());
			 * mallOrder.setOrder_amount(MallUtils.parseMoney(i)); } catch
			 * (Exception e) { stringBuffer.append("order_amount值不正确."); } }
			 */
		// 订单重量(KG)
		if (MallUtils.isEmpty(mallOrder.getOrder_heavy())) {
			stringBuffer.append("缺少必填字段:order_heavy.");
		} else {
			try {
				Double i = Double.parseDouble(mallOrder.getOrder_heavy());
			} catch (Exception e) {
				stringBuffer.append("order_heavy值不正确.");
			}
		}
		// 优惠金额
		if (MallUtils.isEmpty(mallOrder.getOrder_disacount())) {
			stringBuffer.append("缺少必填字段:order_disacount.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrder.getOrder_disacount());
				mallOrder.setOrder_disacount(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("order_disacount值不正确.");
			}
		}
		// 实收金额
		if (MallUtils.isEmpty(mallOrder.getPay_money())) {
			stringBuffer.append("缺少必填字段:pay_money.");
		} /*
			 * else { try { //单位转换为元 Integer i =
			 * Integer.parseInt(mallOrder.getPay_money());
			 * mallOrder.setPay_money(MallUtils.parseMoney(i)); } catch
			 * (Exception e) { stringBuffer.append("pay_money值不正确."); } }
			 */
		// 应收运费
		if (MallUtils.isEmpty(mallOrder.getShipping_amount())) {
			stringBuffer.append("缺少必填字段:shipping_amount.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrder.getShipping_amount());
				mallOrder.setShipping_amount(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("shipping_amount值不正确.");
			}
		}
		// 实收运费
		if (MallUtils.isEmpty(mallOrder.getN_shipping_amount())) {
			stringBuffer.append("缺少必填字段:n_shipping_amount.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrder.getN_shipping_amount());
				mallOrder.setN_shipping_amount(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("n_shipping_amount值不正确.");
			}
		}
		// 订单兑换积分
		if (MallUtils.isNotEmpty(mallOrder.getOrder_points())) {
			try {
				Long.parseLong(mallOrder.getOrder_points());
			} catch (Exception e) {
				stringBuffer.append("order_points值不正确.");
			}
		}
		// 物流公司编码
		if (MallUtils.isNotEmpty(mallOrder.getShipping_company())) {
			if (!(dbUtils.checkFieldValue("shipping_company", mallOrder.getShipping_company()))) {
				stringBuffer.append("shipping_company未按协议取值.");
			}
		}

		// 是否闪电送
		if (MallUtils.isEmpty(mallOrder.getShipping_quick())) {
			stringBuffer.append("缺少必填字段:shipping_quick.");
		} else {
			if (!(dbUtils.checkFieldValue("shipping_quick", mallOrder.getShipping_quick()))) {
				stringBuffer.append("shipping_quick未按协议取值.");
			}
		}
		// 配送方式
		if (MallUtils.isEmpty(mallOrder.getShipping_type())) {
			stringBuffer.append("缺少必填字段:shipping_type.");
		} else {
			if (!(dbUtils.checkFieldValue("shipping_type", mallOrder.getShipping_type()))) {
				stringBuffer.append("shipping_type未按协议取值.");
			}
		}

		// 渠道标记为社会渠道，支付方式为资金归集,配送方式为现场交付时的必填字段
		if (MallUtils.isNotEmpty(mallOrder.getShipping_type()) && MallUtils.isNotEmpty(mallOrder.getChannel_mark()) && MallUtils.isNotEmpty(mallOrder.getPayment_type())) {
			if (mallOrder.getChannel_mark().equals("8") && mallOrder.getPayment_type().equals("ZJGJ") && mallOrder.getShipping_type().equals("XJ")) {
				if (MallUtils.isEmpty(mallOrder.getAgent_code())) {
					stringBuffer.append("资金归集时缺少必填字段:agent_code.");
				}
				if (MallUtils.isEmpty(mallOrder.getAgent_city())) {
					stringBuffer.append("资金归集时缺少必填字段:agent_city.");
				}
				if (MallUtils.isEmpty(mallOrder.getAgent_area())) {
					stringBuffer.append("资金归集时缺少必填字段:agent_area.");
				}
			}
		}

		// 配送时间
		if (MallUtils.isEmpty(mallOrder.getShipping_time()) && !dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type())) {
			stringBuffer.append("缺少必填字段:shipping_time.");
		} else {
			if (!dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type()) && !(dbUtils.checkFieldValue("shipping_time", mallOrder.getShipping_time()))) {
				stringBuffer.append("shipping_time未按协议取值.");
			}
		}
		// 收货人姓名
		if (MallUtils.isEmpty(mallOrder.getShip_name()) && !dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type())) {
			stringBuffer.append("缺少必填字段:ship_name.");
		}
		
		// 订单归属省份编码
				if (MallUtils.isEmpty(mallOrder.getOrder_provinc_code())) {
					stringBuffer.append("缺少必填字段:order_provinc_code.");
				} else {
					if (!(dbUtils.checkFieldValue("order_provinc_code", mallOrder.getOrder_provinc_code()))) {
						// 支持全国省份
						if(!dbUtils.checkFieldValueExists("order_provinc_code", mallOrder.getOrder_provinc_code(), mallOrder.getShip_city(), mallOrder.getShip_country())) {
							stringBuffer.append("order_provinc_code未按协议取值.");
						}
					}
				}
		
		//非必填
		// 地址地市
		if (MallUtils.isEmpty(mallOrder.getShip_city()) && !dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type())) {
			stringBuffer.append("缺少必填字段:ship_city.");
		} else {
			if (!dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type()) && !(dbUtils.checkFieldValue("ship_city", mallOrder.getShip_city()))) {
				
				// 支持全国地址
				if(!dbUtils.checkFieldValueExists("ship_city", mallOrder.getOrder_provinc_code(), mallOrder.getShip_city(), mallOrder.getShip_country())) {
					stringBuffer.append("ship_city未按协议取值.");
				}
			}
		}
		// 地址区县
		if (MallUtils.isEmpty(mallOrder.getShip_country()) && !dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type())) {
			stringBuffer.append("缺少必填字段:ship_country.");
		} else {
			if (!dbUtils.checkFieldValue("isWX", mallOrder.getShipping_type()) && !(dbUtils.checkFieldValue("ship_country", mallOrder.getShip_country()))) {
				
				// 支持全国县分
				if(!dbUtils.checkFieldValueExists("ship_country", mallOrder.getOrder_provinc_code(), mallOrder.getShip_city(), mallOrder.getShip_country())) {
					stringBuffer.append("ship_country未按协议取值.");
				}
			}
		}
		
		// 订单归属地市编码
		// modify by liu.quan 注释原因：童欣和客户确认，将此字段改为非必填，取消校验
		// if (MallUtils.isEmpty(mallOrder.getOrder_city_code())) {
		// stringBuffer.append("缺少必填字段:order_city_code.");
		// }else {
		// if ( !( dbUtils.checkFieldValue("order_city_code",
		// mallOrder.getOrder_city_code()) ) ) {
		// stringBuffer.append("order_city_code未按协议取值.");
		// }
		// }
		// 手机号码
		if (MallUtils.isEmpty(mallOrder.getShip_phone())) {
			stringBuffer.append("缺少必填字段:ship_phone.");
		}
		// 是否已办理完成
		if (MallUtils.isEmpty(mallOrder.getIs_deal())) {
			stringBuffer.append("缺少必填字段:is_deal.");
		} else {
			if (!("0".equals(mallOrder.getIs_deal()) || "1".equals(mallOrder.getIs_deal()))) {
				stringBuffer.append("is_deal未按协议取值.");
			}
		}
		// 百度冻结款
		if (MallUtils.isNotEmpty(mallOrder.getBaidu_money())) {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(mallOrder.getBaidu_money());
				mallOrder.setBaidu_money(MallUtils.parseMoney(i));
			} catch (Exception e) {
				stringBuffer.append("baidu_money值不正确.");
			}
		}
		// 百度冻结开始时间
		if (MallUtils.isNotEmpty(mallOrder.getBaidu_begin_time())) {
			try {
				format.parse(mallOrder.getBaidu_begin_time());
			} catch (Exception e) {
				stringBuffer.append("baidu_begin_time值不正确.");
			}
		}
		// 百度冻结结束时间
		if (MallUtils.isNotEmpty(mallOrder.getBaidu_end_time())) {
			try {
				format.parse(mallOrder.getBaidu_end_time());
			} catch (Exception e) {
				stringBuffer.append("baidu_end_time值不正确.");
			}
		}
		// 基金类型
		if (MallUtils.isNotEmpty(mallOrder.getFund_type())) {
			if (!(dbUtils.checkFieldValue("isfund_type", mallOrder.getFund_type()))) {
				stringBuffer.append("fund_type值不正确.");
			}
		}
		// //集团编码
		// if (dbUtils.checkFieldValue("isJiKe", mallOrder.getSource_from())
		// && MallUtils.isEmpty(mallOrder.getGroup_code())) {
		// stringBuffer.append("缺少必填字段:group_code.");
		// }
		// //集团名称
		// if (dbUtils.checkFieldValue("isJiKe", mallOrder.getSource_from()) &&
		// MallUtils.isEmpty(mallOrder.getGroup_name())) {
		// stringBuffer.append("缺少必填字段:group_name.");
		// }
		// 订单清单
		List<MallOrder_List> orderLists = mallOrder.getOrder_list();
		if (null == orderLists || orderLists.size() == 0) {
			stringBuffer.append("缺少必填字段:order_list.");
		} else {
			for (MallOrder_List mallOrder_List : orderLists) {
				// 检查order_list列表中必填项
				stringBuffer.append(checkOrderList(mallOrder, mallOrder_List));
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 检查order_list列表中必填项
	 * 
	 * @param orderList
	 * @return
	 */
	private static String checkOrderList(MallOrder mallOrder, MallOrder_List orderList) {
		StringBuffer strBuf = new StringBuffer();

		if ("GRKH".equals(orderList.getCust_type()) || "JTKH".equals(orderList.getCust_type())) {// 客户类型只有"个人客户"、"集团客户"两种
			if ("JTKH".equals(orderList.getCust_type())) {// 集团客户
				if (StringUtils.isEmpty(mallOrder.getGroup_code())) {
					strBuf.append("缺少必填字段:group_code.");
				}
//				if (StringUtils.isEmpty(mallOrder.getGroup_name())) {
//					strBuf.append("缺少必填字段:group_name.");
//				}
			}
		} else {
			strBuf.append("必填字段:order_list.cust_type值不正确.");
		}
		// 沃财富是通过合约编码和机型编码关联商品的(与总部接口类似),商品编码可以为空
		if (dbUtils.checkFieldValue("iswcf", mallOrder.getSource_from())) {
			// 合约编码 沃财富
			if (MallUtils.isEmpty(orderList.getActivitycode())) {
				strBuf.append("缺少必填字段:activitycode.");
			}
			// 机型编码
			if (MallUtils.isEmpty(orderList.getResourcestypeid())) {
				strBuf.append("缺少必填字段:resourcestypeid.");
			}
		} else {
			// 商品编码 新商城
			if (MallUtils.isEmpty(orderList.getProd_offer_code())) {
				strBuf.append("缺少必填字段:prod_offer_code.");
			}
		}
		// 商品价格(厘)
		if (MallUtils.isEmpty(orderList.getOffer_price())) {
			strBuf.append("缺少必填字段:offer_price.");
		} /*
			 * else { try { //单位转换为元 Integer i =
			 * Integer.parseInt(orderList.getOffer_price());
			 * orderList.setOffer_price(MallUtils.parseMoney(i)); } catch
			 * (Exception e) { strBuf.append("offer_price值不正确."); } }
			 */
		// 优惠价格(厘)
		if (MallUtils.isEmpty(orderList.getOffer_disacount_price())) {
			strBuf.append("缺少必填字段:offer_disacount_price.");
		} else {
			try {
				// 单位转换为元
				Integer i = Integer.parseInt(orderList.getOffer_disacount_price());
				orderList.setOffer_disacount_price(MallUtils.parseMoney(i));
			} catch (Exception e) {
				strBuf.append("offer_disacount_price值不正确.");
			}
		}
		// 实收价格(厘)
		if (MallUtils.isEmpty(orderList.getOffer_coupon_price())) {
			strBuf.append("缺少必填字段:offer_coupon_price.");
		} /*
			 * else { try { //单位转换为元 Integer i =
			 * Integer.parseInt(orderList.getOffer_coupon_price());
			 * orderList.setOffer_coupon_price(MallUtils.parseMoney(i)); } catch
			 * (Exception e) { strBuf.append("offer_coupon_price值不正确."); } }
			 */
		// 商品兑换积分
		if (MallUtils.isNotEmpty(orderList.getOffer_point())) {
			try {
				Long.parseLong(orderList.getOffer_point());
			} catch (Exception e) {
				strBuf.append("offer_point值不正确.");
			}
		}
		// 商品数量
		if (MallUtils.isEmpty(orderList.getProd_offer_num())) {
			strBuf.append("缺少必填字段:prod_offer_num.");
		} else {
			try {
				Integer i = Integer.parseInt(orderList.getProd_offer_num());
			} catch (Exception e) {
				strBuf.append("prod_offer_num值不正确.");
			}
		}
		// 发票类型
		if (MallUtils.isNotEmpty(orderList.getInvoice_type())) {
			if (!("1".equals(orderList.getInvoice_type()) || "2".equals(orderList.getInvoice_type()))) {
				strBuf.append("invoice_type未按协议取值.");
			}
		}
		// 发票打印方式
		if (MallUtils.isEmpty(orderList.getInvoice_print_type())) {
			strBuf.append("缺少必填字段:invoice_print_type.");
		} else {
			if (!(dbUtils.checkFieldValue("invoice_print_type", orderList.getInvoice_print_type()))) {
				strBuf.append("invoice_print_type未按协议取值.");
			}
		}
		// 发票抬头
		if ("1".equals(orderList.getInvoice_print_type())) {
			if (MallUtils.isEmpty(orderList.getInvoice_title())) {
				strBuf.append("缺少必填字段:invoice_title.");
			}
			if (MallUtils.isEmpty(orderList.getInvoice_group_content())) {
				strBuf.append("缺少必填字段:invoice_group_content.");
			} else if (!"MX".equalsIgnoreCase(orderList.getInvoice_group_content())) {
				strBuf.append("invoice_group_content未按协议取值.");
			}
		}
		// 证件类型
		if (MallUtils.isNotEmpty(orderList.getCerti_type())) {
			if (!(dbUtils.checkFieldValue("certi_type", orderList.getCerti_type()))) {
				strBuf.append("certi_type未按协议取值.");
			}
		}
		// 是否变更套餐 is_change
		if (MallUtils.isEmpty(orderList.getIs_change())) {
			strBuf.append("缺少必填字段:is_change.");
		} else {
			if (!(dbUtils.checkFieldValue("is_change", orderList.getIs_change()))) {
				strBuf.append("is_change未按协议取值.");
			}
		}
		if (MallUtils.isNotEmpty(orderList.getOffer_eff_type())) {
			if (!(dbUtils.checkFieldValue("offer_eff_type", orderList.getOffer_eff_type()))) {
				strBuf.append("offer_eff_type未按协议取值.");
			}
		}
		// 证件失效时间
		if (MallUtils.isNotEmpty(orderList.getCerti_valid_date())) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				String certi_valid_date = dateFormat.format(simpleFormat.parse(orderList.getCerti_valid_date()));
				orderList.setCerti_valid_date(certi_valid_date);
			} catch (Exception e) {
				strBuf.append("certi_valid_date值不正确.");
			}
		}

		// 优惠信息
		List<MallActivity_List> activity_Lists = orderList.getActivity_list();
		if (null != activity_Lists && activity_Lists.size() > 0) {
			strBuf.append(checkActivityList(activity_Lists));
		}

		// 优惠券列表
		List<MallCoupons_List> coupons_Lists = orderList.getCoupons_list();
		if (null != coupons_Lists && coupons_Lists.size() > 0) {
			strBuf.append(checkCouponsList(coupons_Lists));
		}

		List<MallSKU_Param> skuParams = orderList.getSku_param();
		if (null == skuParams || skuParams.size() == 0) {
			if (!SOURCE_FROM_ZHWQ.equals(mallOrder.getSource_from()) && !"10061".equals(mallOrder.getSource_from())) {// 如果是智慧沃企订单来源则先不校验
																														// add
																														// by
																														// mo.chencheng
																														// 2016-11-16
				strBuf.append("缺少必填字段:sku_param.");
			}
		} else {
			// 检查sku_param中的必填项
			List<String> codesList = new ArrayList<String>();
			List<String> nameList = new ArrayList<String>();
			for (MallSKU_Param mallSKU_Param : skuParams) {
				strBuf.append(checkSkuParam(mallSKU_Param));
				// 参数编码
				codesList.add(mallSKU_Param.getParam_code());
				// 参数名称
				nameList.add(mallSKU_Param.getParam_name());
			}
			// 检查所有必填param_code是否都有
			if (!SOURCE_FROM_ZHWQ.equals(mallOrder.getSource_from()) && !"10061".equals(mallOrder.getSource_from())) {// 如果是智慧沃企订单来源则先不校验
																														// add
																														// by
																														// mo.chencheng
																														// 2016-11-16
				if (!codesList.containsAll(skuParamCodes)) {
					strBuf.append("sku_param中缺少必填字段:" + MallUtils.notContainsElement(codesList, skuParamCodes));
				}
			}
			// 检查所有必填param_name是否都有
			if (!SOURCE_FROM_ZHWQ.equals(mallOrder.getSource_from()) && !"10061".equals(mallOrder.getSource_from())) {// 如果是智慧沃企订单来源则先不校验
																														// add
																														// by
																														// mo.chencheng
																														// 2016-11-16
				if (!nameList.containsAll(skuParamNames)) {
					strBuf.append("sku_param中缺少必填字段:" + MallUtils.notContainsElement(nameList, skuParamNames));
				}
			}
		}
		return strBuf.toString();
	}

	/**
	 * 检查优惠信息中的必填项
	 * 
	 * @param activity_Lists
	 * @return
	 */
	private static String checkActivityList(List<MallActivity_List> activity_Lists) {
		StringBuffer strBuf = new StringBuffer();
		for (MallActivity_List activity : activity_Lists) {
			// 优惠编码
			if (MallUtils.isEmpty(activity.getActivity_code())) {
				strBuf.append("缺少必填字段：activity_code");
			}
			// 优惠标识
			if (MallUtils.isEmpty(activity.getActivity_id())) {
				strBuf.append("缺少必填字段：activity_id");
			}

			// 检查赠品信息
			List<MallGift_List> gifts = activity.getGift_list();
			if (null != gifts && gifts.size() > 0) {
				for (MallGift_List mallGift_List : gifts) {
					strBuf.append(checkGiftInfo(mallGift_List));
				}
			}

		}
		return strBuf.toString();
	}

	/**
	 * 检查赠品信息必填项
	 * 
	 * @param gift
	 * @return
	 */
	private static String checkGiftInfo(MallGift_List gift) {
		StringBuffer strBuf = new StringBuffer();
		// gift_id 赠品编码
		if (MallUtils.isEmpty(gift.getGift_id())) {
			strBuf.append("缺少必填字段:gift_id.");
		}
		// gift_num 赠品数量
		if (MallUtils.isEmpty(gift.getGift_num())) {
			strBuf.append("缺少必填字段:gift_num.");
		} else {
			try {
				Integer i = Integer.parseInt(gift.getGift_num());
			} catch (Exception e) {
				strBuf.append("gift_num值不正确.");
			}
		}
		// is_process 是否需要加工
		if (MallUtils.isEmpty(gift.getIs_process())) {
			strBuf.append("缺少必填字段:is_process.");
		} else {
			if (!("0".equals(gift.getIs_process()) || "1".equals(gift.getIs_process()))) {
				strBuf.append("is_process未按协议取值.");
			}
		}
		// process_type 加工类型
		if ("1".equals(gift.getIs_process()) && MallUtils.isEmpty(gift.getProcess_type())) {
			strBuf.append("缺少必填字段:process_type.");
		}
		if (MallUtils.isNotEmpty(gift.getProcess_type())) {
			if (!("01".equals(gift.getProcess_type()) || "99".equals(gift.getProcess_type()))) {
				strBuf.append("process_type未按协议取值.");
			}
		}
		return strBuf.toString();
	}

	/**
	 * 检查sku_param中的必填项
	 * 
	 * @param skuParam
	 * @return
	 */
	private static String checkSkuParam(MallSKU_Param skuParam) {
		StringBuffer strSku = new StringBuffer();
		if ("card_type".equalsIgnoreCase(skuParam.getParam_code()) && MallUtils.isNotEmpty(skuParam.getParam_value())) {
			if (!("NM".equalsIgnoreCase(skuParam.getParam_value()) || "MC".equalsIgnoreCase(skuParam.getParam_value()) || "NN".equalsIgnoreCase(skuParam.getParam_value())
					|| "none".equalsIgnoreCase(skuParam.getParam_value()))) {
				strSku.append("card_type未按协议取值.");
			}
		}
		return strSku.toString();
	}

	/**
	 * 入库标识
	 * 
	 * @param 内部订单号
	 * @author zengxianlian
	 */
	private static int getSaveOrderFlag(String orderId) {
		int flag = 0;
		if (null == orderId || "".equals(orderId)) {// 没找到旧单
			flag = 1;
		} else {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
			OrderBusiRequest order = orderTree.getOrderBusiRequest();
			OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
			if (!((EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_AOP.equals(orderExt.getIs_aop()) || EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(orderExt.getIs_aop()))
					&& "XJ".equals(order.getShipping_type()))) {// 不是AOP+现场交付不能作废
				flag = 2;
			}
			if (!("B".equals(orderExt.getFlow_trace_id()) || "C".equals(orderExt.getFlow_trace_id()) || "D".equals(orderExt.getFlow_trace_id()) || "X".equals(orderExt.getFlow_trace_id()))) {// 在特定环节才能作废
				flag = 3;
			}
		}
		return flag;
	}

	/**
	 * 作废旧单时对旧单和新单做处理
	 * 
	 * @param 内部订单号
	 * @author zengxianlian
	 */
	private static void handelOrder(String orderId, String newNum, Map<String, String> extMap) throws IllegalAccessException, InvocationTargetException {

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(orderId);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String oldNum = orderTree.getPhoneInfoBusiRequest().getPhone_num();
		String old_terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.TERMINAL_NUM);// 旧串号

		if (null != old_terminal_num && !"".equals(old_terminal_num)) {
			if (!extMap.containsKey("terminal_num")) {
				extMap.put("terminal_num", old_terminal_num);// 复制旧单串号到新单串号字段(如果新报文含有新的串号则覆盖这里的值,如果没有则使用旧单串号)
			}
			extMap.put("old_terminal_num", old_terminal_num);// 复制旧单串号到新单旧单串号字段
		}

		// 复制旧单的es_attr_Terminal_ext表数据到新单里面
		List<AttrTmResourceInfoBusiRequest> resourceInfoBusiRequests = orderTree.getTmResourceInfoBusiRequests();
		if (null != resourceInfoBusiRequests && resourceInfoBusiRequests.size() > 0) {
			JSONObject jsonObj = JSONObject.fromObject(resourceInfoBusiRequests.get(0));
			extMap.put("terminalInfo", jsonObj.toString());
		}

		// 修改旧订单状态(es_order_ext和es_order_outter表中的out_id)
		String outId = orderExtBusiRequest.getOut_tid();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String time = sdf.format(new Date());
		String updateOutId = outId + "_zf" + time;
		orderExtBusiRequest.setOut_tid(updateOutId);
		orderExtBusiRequest.setOrder_if_cancel("1");
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		CommonDataFactory.getInstance().updateAttrFieldValue(orderId, new String[] { AttrConsts.OUT_TID, AttrConsts.ORDER_IF_CANCEL }, new String[] { updateOutId, "1" });
		outterECSTmplManager.updateOutterOrderId(updateOutId, outId);
		// 新单更换了号码,需要释放旧单号码资源
		if (null != newNum && !"".equals(newNum) && null != oldNum && !"".equals(oldNum) && !newNum.equals(oldNum)) {
			releaseOldNum(orderId);
		}

	}

	/**
	 * 新单更换了号码,需要释放旧单号码资源
	 * 
	 * @param 内部订单号
	 * @author zengxianlian
	 */
	private static void releaseOldNum(String orderId) {
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderId);
		PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
		req.setFact(fact);

		// 开启异步线程执行后续规则
		try {
			TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req) {
				@Override
				public ZteResponse execute(ZteRequest zteRequest) {
					PlanRuleTreeExeReq exereq = (PlanRuleTreeExeReq) zteRequest;
					String order_id = ((TacheFact) exereq.getFact()).getOrder_id();
					String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);
					String occupancySysOld = CommonDataFactory.getInstance().sectionNoOccupancySysPlan(order_id, phone_num);
					BusiCompResponse busiResp = null;
					PlanRuleTreeExeResp planResp = null;
					// AOP释放
					if (StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1) || StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_2)) {
						exereq.setPlan_id(EcsOrderConsts.NUMBER_CHANGE_AOP);
						exereq.setDeleteLogs(true);
						planResp = CommonDataFactory.getInstance().exePlanRuleTree(exereq);
						busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
						if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
							logger.info("AOP号码资源释放成功[" + order_id + "]");
						} else {
							logger.info("AOP号码资源释放失败[" + order_id + "]:" + busiResp.getError_msg());
						}
					}
					// BSS释放
					if (StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_0) || StringUtils.equals(occupancySysOld, EcsOrderConsts.NUMBER_OCCUPANCY_TYPE_1)) {
						exereq.setPlan_id(EcsOrderConsts.NUMBER_RELEASE_BSS);
						planResp = CommonDataFactory.getInstance().exePlanRuleTree(exereq);
						busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
						if (!StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
							logger.info("BSS号码资源释放成功[" + order_id + "]");
						} else {
							logger.info("BSS号码资源释放失败[" + order_id + "]:" + busiResp.getError_msg());
						}
					}

					return busiResp;
				}
			});
			ThreadPoolFactory.getInfServerThreadPoolExector().execute(taskThreadPool);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getNewOrderPhoneNum() {
		return newOrderPhoneNum;
	}

	public static void setNewOrderPhoneNum(String newOrderPhoneNum) {
		NewMallOrderUtil.newOrderPhoneNum = newOrderPhoneNum;
	}

	private static String checkCouponsList(List<MallCoupons_List> coupons_Lists) {
		StringBuffer strBuf = new StringBuffer();
		for (MallCoupons_List coupons : coupons_Lists) {
			// 优惠劵金额
			if (MallUtils.isEmpty(coupons.getCoupons_fee())) {
				strBuf.append("缺少必填字段：coupons_fee");
			} else {
				try {
					// 单位转换为元
					Integer t = Integer.parseInt(coupons.getCoupons_fee());
					coupons.setCoupons_fee(MallUtils.parseMoney(t));
				} catch (Exception e) {
					strBuf.append("coupons_fee值不正确.");
				}
			}
		}
		return strBuf.toString();
	}

}
