package com.ztesoft.net.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.busi.req.HuashengOrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.sf.resp.NotifyOrderInfoSFResponse;
import zte.net.ecsord.utils.AttrUtils;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.model.DeliveryPrints;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.service.IOrdPostPrintManager;
import com.ztesoft.net.service.IOrdVisitTacheManager;
import com.ztesoft.net.service.IOrderSDSModelManager;

import consts.ConstsCore;

/**
 * 物流单打印
 * @作者 zhangJun
 * @创建日期 2014-11-3
 * @版本 V 1.0
 */
public class OrderPostPrintAction extends WWAction {

	private static final long serialVersionUID = 1L;
	@Resource
	private IOrdPostPrintManager ordPostPrintManager;
	
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IOrdVisitTacheManager ordVisitTacheManager;
	@Resource
	private IOrderSDSModelManager orderSDSModelManager;
	
	private DeliveryPrints prints;//订单补寄和重发用

	private String order_id;//订单id
	private String orderIds;//多个订单id(包含有order_id) 
	private String logi_company;//物流公司id
	private String logi_no;//  物流单ID 
	private String sending_type; // 配送方式  -carryMode
	private String n_shipping_amount;//运费 -carryFee
 
	private String post_type;//物流类型   0正常发货、1补寄、2重发
	private String delvery_print_id;//本次打印日志ID
	private String itmesIds;//勾选的赠品ID
	private String delivery_id;//重发、补寄的delivery_id
	private String order_is_his;//1-是历史单（重发、补寄时用到）
	
	
	private String reissue_info; // 赠品名称
	private String gift_inst_id; // 补寄物品ID
	private String reissue_num; // 赠品数量
	
	
	private Map<String,String > postModeMap;
	private List<Map> postPayModeList;
	private List<Map> isInsurList;
	private List<Map> isCodList;
	private List<Map> isReceiptList;
	
	private String result_msg;
	
	private String print_type = "";
	

	
	public String doGetPiritModelNew() {
		try {
			//1、获取页面特殊的参数
			if(EcsOrderConsts.LOGIS_NORMAL.equals(post_type)){//正常发货-接收参数
//				HttpServletRequest request = ThreadContextHolder.getHttpRequest();
//				logi_company=request.getParameter("shipping_company");
//				if(logi_company==null){	logi_company="";	}
//				logi_no=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.LOGI_NO);
//				sending_type=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.SENDING_TYPE);
//				n_shipping_amount=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.N_SHIPPING_AMOUNT);
				OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
				List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = ordertree.getOrderDeliveryBusiRequests();
				OrderDeliveryBusiRequest delivery = null;
				for(OrderDeliveryBusiRequest deli:orderDeliveryBusiRequest){
					if(EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())){
						delivery = deli;//取正常发货记录，前面业务逻辑保证有此记录
						break;
					}
				}
				delivery_id = delivery.getDelivery_id();
				logi_company = delivery.getShipping_company();
				logi_no = delivery.getLogi_no();
				sending_type =ordertree.getOrderBusiRequest().getShipping_type();
				n_shipping_amount = delivery.getN_shipping_amount();
			}
			
			
			if(orderIds==null){orderIds=order_id;};
			
			//2、查询订单货品等信息
			prints=ordPostPrintManager.queryPostDefaultInfo(order_id,orderIds,sending_type,n_shipping_amount,post_type,delivery_id,order_is_his,logi_company);//暂时用orderId代替orderIds
			//prints.setIs_cod("0");
			//prints.setIs_insur("0");
			
			//3、查询物流公司发货人信息
			postModeMap=ordPostPrintManager.queryPostModel(logi_company, order_id);
			
			
			//4、查询下拉框静态数据
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		    this.postPayModeList = dcPublicCache.getdcPublicExtList(StypeConsts.DIC_POST_PAY_MODE);
		    this.isInsurList = dcPublicCache.getdcPublicExtList(StypeConsts.IS_INSUR);
		    this.isCodList = dcPublicCache.getdcPublicExtList(StypeConsts.IS_COD);
		    
		   
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "quality_psot_print_add";
	}

	/**
	 * 初始化界面数据--非历史表
	 *  
	 * 
	 * 重发需要传的参数 post_type=2、logi_company、logi_no、sending_type、n_shipping_amount、order_id、orderIds
	 * 补发需要传的参数 post_type=1、logi_company、logi_no、sending_type、n_shipping_amount、order_id、orderIds、itmesIds
	 * orderIds至少包含一个，即至少要包含order_id  ，多个id用逗号分隔
	 * itmesIds 多个id用逗号分隔
	 * @return
	 */
	public String doGetPiritModel() {
		try {
			//1、获取页面特殊的参数
			if(EcsOrderConsts.LOGIS_NORMAL.equals(post_type)){//正常发货-接收参数
				HttpServletRequest request = ThreadContextHolder.getHttpRequest();
				logi_company=request.getParameter("shipping_company");
				if(logi_company==null){	logi_company="";	}
				logi_no=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.LOGI_NO);
				sending_type=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.SENDING_TYPE);
				n_shipping_amount=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.N_SHIPPING_AMOUNT);
			}
			
			
			if(orderIds==null){orderIds=order_id;};
			
			//2、查询订单货品等信息
			prints=ordPostPrintManager.queryPostDefaultInfo(order_id,orderIds,sending_type,n_shipping_amount,post_type,delivery_id,order_is_his,logi_company);//暂时用orderId代替orderIds
			//prints.setIs_cod("0");
			//prints.setIs_insur("0");
			
			//3、查询物流公司发货人信息
			postModeMap=ordPostPrintManager.queryPostModel(logi_company, order_id);
			
			
			//4、查询下拉框静态数据
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		    this.postPayModeList = dcPublicCache.getdcPublicExtList(StypeConsts.DIC_POST_PAY_MODE);
		    this.isInsurList = dcPublicCache.getdcPublicExtList(StypeConsts.IS_INSUR);
		    this.isCodList = dcPublicCache.getdcPublicExtList(StypeConsts.IS_COD);
		    
		   
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "quality_psot_print_add";
	}
	
	
	public String doGetHotFree() {
		postModeMap = new HashMap<String,String>();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String sn = orderTree.getOrderBusiRequest().getSn();//条形码号
		String receive_num= orderTree.getOrderExtBusiRequest().getReceive_num();		//领取号
		String custName  = orderTree.getOrderBusiRequest().getShip_name();
		String mobile =  orderTree.getOrderBusiRequest().getShip_mobile();
		String planTitle = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PLAN_TITLE);// 套餐名称
		String cityCode = AttrUtils.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE,"","");
		String cityName = getCacheName("DC_MODE_REGION",cityCode);
		String terminalNum = CommonDataFactory.getInstance().getAttrFieldValue(order_id, "terminal_num"); //终端串码
		OrderBusiRequest ord = orderTree.getOrderBusiRequest();
        String iccid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String mode_print = cacheUtil.getConfigInfo("mode_print2");//新架构未存在sn 需要进行补充
        String sns="";
		if(!StringUtils.isEmpty(iccid)){
            sns = "0"+iccid.substring(iccid.length()-8,iccid.length()-1);//iccid取后6位
        }
		if("1".equals(mode_print) && !StringUtil.isEmpty(sns)){
		    sn=sns;
		    ord.setSn(sns);
		    ord.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		    ord.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		    ord.store();
		}
		postModeMap.put("order_id", order_id);
		postModeMap.put("sn", sn);
		postModeMap.put("receive_num", receive_num);
		postModeMap.put("custName", custName);
		postModeMap.put("mobile", mobile);
		postModeMap.put("terminal", planTitle);
		postModeMap.put("cityName", cityName);
		postModeMap.put("terminalNum", terminalNum);
		postModeMap.put("print_type", print_type);
		
		return "pick_hotfree_print";
	}
	

	public String getCacheName(String code,String key){
		String  cacheName = "";
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List list = dc.loadData(code);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				String value = (String) map.get("value");
				if(value.equals(key)){
					cacheName = (String) map.get("value_desc");
				}				
			}
		}		
		return cacheName;
	}
	
	public String doGetHotFreeModel() {
		try {
			OrderDeliveryBusiRequest delivery = null;

			//1、获取页面特殊的参数
//			if(EcsOrderConsts.LOGIS_NORMAL.equals(post_type)){//正常发货-接收参数
//				HttpServletRequest request = ThreadContextHolder.getHttpRequest();
//				logi_company=request.getParameter("shipping_company");
//				if(logi_company==null){	logi_company="";	}
//				logi_no=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.LOGI_NO);
//				sending_type=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.SENDING_TYPE);
//				n_shipping_amount=ordPostPrintManager.getParamByLikeName(order_id,AttrConsts.N_SHIPPING_AMOUNT);
				
				OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
				List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = ordertree.getOrderDeliveryBusiRequests();
				for(OrderDeliveryBusiRequest deli:orderDeliveryBusiRequest){
					if(EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())){
						delivery = deli;//取正常发货记录，前面业务逻辑保证有此记录
						break;
					}
				}
				delivery_id = delivery.getDelivery_id();
				logi_company = delivery.getShipping_company();
				logi_no = delivery.getLogi_no();
				sending_type =ordertree.getOrderBusiRequest().getShipping_type();
				n_shipping_amount = delivery.getN_shipping_amount();
//			}			

			if(orderIds==null){orderIds=order_id;};
//			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
//			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = ordertree.getOrderDeliveryBusiRequests();
//			OrderDeliveryBusiRequest delivery = null;
//			for(OrderDeliveryBusiRequest deli:orderDeliveryBusiRequest){
//				if(EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())){
//					delivery = deli;//取正常发货记录，前面业务逻辑保证有此记录
//					break;
//				}
//			}
			
			

			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.SHIPPING_COMPANY}, new String[]{logi_company});
			//2、查询订单货品等信息

			//prints.setIs_cod("0");
			//prints.setIs_insur("0");
			
			//3、查询物流公司发货人信息
			postModeMap = new HashMap<String,String>();
			postModeMap.putAll(ordPostPrintManager.queryPostModel(logi_company, order_id));

			//3.2 投递要求(备注)
		    String order_from = ordertree.getOrderExtBusiRequest().getOrder_from();
		    if(null!=ordertree.getOrderRealNameInfoBusiRequest()&&"1".equals(ordertree.getOrderRealNameInfoBusiRequest().getLater_active_flag())){//后激活订单
		    	postModeMap.put("hot_post_remarks", "");//可加一字段，使后激活、非后激活订单投递备注均可配置

		    }else if(!EcsOrderConsts.ORDER_FROM_10061.equals(order_from)){//非华盛B2C的订单
				String template = postModeMap.get("hot_post_remarks");
				if(!StringUtil.isEmpty(template)){
			        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
			        Matcher matcher = pattern.matcher(template);
			        String word = "";
			        while(matcher.find()){
			        	String key = matcher.group();
			        	word = CommonDataFactory.getInstance().getAttrFieldValue(order_id, key);
			        	template = template.replace("{"+key+"}", word);
			        }  
				}
		    	postModeMap.put("hot_post_remarks", template);
		    }
			
			//4、查询下拉框静态数据
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		    this.postPayModeList = dcPublicCache.getdcPublicExtList(StypeConsts.DIC_POST_PAY_MODE);
		    this.isInsurList = dcPublicCache.getdcPublicExtList(StypeConsts.IS_INSUR);
		    this.isCodList = dcPublicCache.getdcPublicExtList(StypeConsts.IS_COD);
		    this.isReceiptList = dcPublicCache.getdcPublicExtList(AttrConsts.NEED_RECEIPT);
		   /* OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
		    OrderDeliveryBusiRequest delivery = ordertree.getOrderDeliveryBusiRequests().get(0);*/
			prints=ordPostPrintManager.queryPostDefaultInfo(order_id,orderIds,sending_type,n_shipping_amount,post_type,delivery_id,order_is_his,logi_company);//暂时用orderId代替orderIds
//			//3.1 月结账号
			try{
				String monthAccount = orderSDSModelManager.findSfMonthAccount(order_id);
				postModeMap.put("monthly_payment", monthAccount);
				prints.setMonthly_payment(monthAccount);
			}catch(Exception e){
				
			}
			
			
			/*
			 * 是否保价及保价金额在弹出页面后人工设定，此时还不能向顺丰下单
			if (StringUtil.isEmpty(logi_no)) {
				BusiDealResult result = ordVisitTacheManager.synOrderInfoSF(
						order_id);
				NotifyOrderInfoSFResponse response = (NotifyOrderInfoSFResponse) result.getResponse();
				prints.setDestcode(response.getOrderInfo().get(0).getDestcode());//目的地代码取顺丰返回值
				prints.setOrigin(response.getOrderInfo().get(0).getOrigincode());//原寄地代码取顺丰返回值
				
			}else{//这里是为了传送顺丰返回的目的地代码及原寄地代码,并且在页面显示
				NotifyOrderInfoSFResponse result = ordVisitTacheManager.querySfOrder(order_id);
				if (!EcsOrderConsts.SF_INF_SUCC_CODE.equals(result.getErrorCode())){
					json = "{'result':1,'message':'向顺丰下单失败！'}";
					return JSON_MESSAGE;
				}else{
					prints.setDestcode(result.getOrderInfo().get(0).getDestcode());
					prints.setOrigin(result.getOrderInfo().get(0).getOrigincode());
				}
				
			}
			 * */
			String entitys = ordPostPrintManager.queryEntityProdInfos(order_id);
			prints.setPost_items(entitys);
			prints.setPostage_mode(EcsOrderConsts.POSTAGE_MODE_JZZF);//集中总付(月结)
		    logi_no = delivery.getLogi_no();
		    prints.setIs_insur(EcsOrderConsts.IS_INSUR_YES);//保价
		    if(StringUtil.isEmpty(logi_no)){//未有物流单号,保价费默认为空
		    	prints.setInsur_value("");
		    }else{
				Map insur_info = ordPostPrintManager.queryInsureValue(order_id);
				if(null!=insur_info){
					String insur_value = (String)insur_info.get("insur_value");
					String is_insur = (String)insur_info.get("is_insur");
					prints.setInsur_value(StringUtils.isEmpty(insur_value)?"":insur_value);//保价费取之前打印的值，下单时传送的值
					prints.setIs_insur(StringUtils.isEmpty(is_insur)?EcsOrderConsts.IS_INSUR_YES:is_insur);//是否保价取之前打印的值，下单时传送的值
				}
		    }
			if(StringUtil.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE), EcsOrderConsts.PAY_TYPE_HDFK)){	//这里不考虑历史单，属性从现表里拿			
				prints.setIs_cod(EcsOrderConsts.IS_COD_YES);// 代收
			}else{
				prints.setIs_cod(EcsOrderConsts.IS_COD_NO);// 非代收
			}
			//是否是D卡
			String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
			if(!StringUtils.isEmpty(goods_cat_id)&&(EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(goods_cat_id)||
				EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(goods_cat_id)||EcsOrderConsts.GOODS_CAT_ID_HKSN.equals(goods_cat_id))){
				prints.setIs_signback("0");//是否需要回单	需确保2个字段编码一致
			}else{
				prints.setIs_signback(delivery.getNeed_receipt());//是否需要回单	需确保2个字段编码一致
			}
			//所有订单都不保价 奥叔与生产口确认 2016-01-13
			prints.setIs_insur(delivery.getIs_protect().toString());
			prints.setInsur_value(delivery.getProtect_price()==null?"":delivery.getProtect_price().toString());
			prints.setPickup_user(postModeMap.get("pickup_user")==null?"":postModeMap.get("pickup_user"));
			prints.setPost_comp(postModeMap.get("post_comp")==null?"":postModeMap.get("post_comp"));
			prints.setPost_code(postModeMap.get("post_code")==null?"":postModeMap.get("post_code"));
			prints.setPost_link_man(postModeMap.get("post_linkman")==null?"":postModeMap.get("post_linkman"));
			prints.setPost_tel(postModeMap.get("post_tel")==null?"":postModeMap.get("post_tel"));
			prints.setPost_address(postModeMap.get("post_address")==null?"":postModeMap.get("post_address"));
			prints.setprint_type("1");
			prints.setExpress_type(postModeMap.get("express_type")==null?"":postModeMap.get("express_type"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "quality_hotfree_print";
	}

	/**
	 * 保存数据-delveryPrint
	 * @return
	 */
	public String doAddOne() {
		try {
			 String	order_orginid="";
			if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
				order_orginid=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.OUT_TID);
			}else{
				order_orginid=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
			}			
		    String update_time = DateUtil.getTime2();
			prints.setOrder_id(order_id);
			prints.setOrder_orginid(order_orginid);
			prints.setUpdate_time(update_time);
			delvery_print_id=ordPostPrintManager.doAddOne(prints);
			json = "{'result':0,'message':'保持成功！','delveryPrintId':'"+delvery_print_id+"'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	public String getOrderModel(){
		try {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String order_model = orderTree.getOrderExtBusiRequest().getOrder_model();
			if(StringUtil.equals(order_model, "06")){
				json = "{'result':0,'message':'自动写卡'}";
			}else{
				json = "{'result':1,'message':'非自动写卡'}";
			}
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	
	/**
	 * 保存热免单数据-delveryPrint
	 * @return
	 */
	public String doAddHotFree() {
		try {
			 String	order_orginid="";
			if(EcsOrderConsts.IS_ORDER_HIS_YES.equals(order_is_his)){//历史单
				order_orginid=CommonDataFactory.getInstance().getAttrFieldValueHis(order_id, AttrConsts.OUT_TID);
			}else{
				order_orginid=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.OUT_TID);
			}
		    String update_time = DateUtil.getTime2();
		    if(prints==null){
		    	post_type = "0";
		    	doGetHotFreeModel();
		    }
			prints.setOrder_id(order_id);
			prints.setOrder_orginid(order_orginid);
			prints.setUpdate_time(update_time);
			
			//代收货款情况下保存月结账号
			if(StringUtil.equals(CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PAY_TYPE), EcsOrderConsts.PAY_TYPE_HDFK)) {
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String monthlyPayment = cacheUtil.getConfigInfo("MONTHLY_PAYMENT");
				prints.setMonthly_payment(monthlyPayment);
			}
			
			
			OrderTreeBusiRequest ordertree = CommonDataFactory.getInstance().getOrderTree(order_id);
			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = ordertree.getOrderDeliveryBusiRequests();
			OrderDeliveryBusiRequest delivery = null;
			for(OrderDeliveryBusiRequest deli:orderDeliveryBusiRequest){
				if(EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())){
					delivery = deli;//取正常发货记录，前面业务逻辑保证有此记录
					break;
				}
			}
		    String logi_no = delivery.getLogi_no();
		    String shipping_company =delivery.getShipping_company();
		    String receipt_no = delivery.getReceipt_no();
		    String order_from = ordertree.getOrderExtBusiRequest().getOrder_from();
			String order_city_code=CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);


//		    if(shipping_company.indexOf("EMS")>-1){
//    			prints.setPost_no(logi_no);
//    			prints.setReceipt_no(receipt_no);
//    			prints.setDestcode(delivery.getDest_code());//目的地代码
//    			prints.setOrigin(delivery.getOrigin_code());//原寄地代码   	
//		    }else{				
				prints.setPost_no(logi_no);
				prints.setReceipt_no(receipt_no);
				prints.setDestcode(delivery.getDest_code());//目的地代码
				prints.setOrigin(delivery.getOrigin_code());//原寄地代码   	
				
//			    if(EcsOrderConsts.ORDER_FROM_10061.equals(order_from)){//如果是华盛的B2C订单，不需要调用顺丰接口下单，所有需要从顺丰下单接口获取的数据，都应该由华盛SAP提供或者默认写死
//			    	List<HuashengOrderBusiRequest> hsOrder = ordertree.getHuashengOrderBusiRequest();
//			    	if(null!=hsOrder&&hsOrder.size()>0){
//			    		prints.setPost_no(logi_no);//由华盛SAP提供，放在既有的物流表
//			    		prints.setReceipt_no(delivery.getReceipt_no());//由华盛SAP提供，放在既有的物流表
//			    		prints.setDestcode(hsOrder.get(0).getSf_destcode());//目的地代码由华盛SAP提供，放在新建的华盛订单表
//			    		prints.setOrigin(hsOrder.get(0).getSf_origincode());//原寄地代码由华盛SAP提供，放在新建的华盛订单表(华盛未提供，订单入库时写死)
//			    	}
//			    }else{
//			    	//向顺丰下单
//			    	if (StringUtil.isEmpty(logi_no)) {
//						String goods_cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
//						if(!StringUtils.isEmpty(goods_cat_id)&&(EcsOrderConsts.GOODS_CAT_ID_DDWK.equals(goods_cat_id)||
//								EcsOrderConsts.GOODS_CAT_ID_TXWK.equals(goods_cat_id))){
//							delivery.setNeed_receipt("0");//是否需要回单	需确保2个字段编码一致
//						}else{
//							delivery.setNeed_receipt(prints.getIs_signback());//是否需要回单	需确保2个字段编码一致
//						}
//			    		delivery.setNeed_receipt(prints.getIs_signback());	//保存是否回单字段	需确保2个字段编码一致
//			    		delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//			    		delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//			    		delivery.store();
//			    		
//			    		BusiDealResult result = ordVisitTacheManager.synOrderInfoSF(
//			    				order_id,prints.getInsur_value());
//			    		if(!StringUtil.equals("0000", result.getError_code())){
//			    			json = "{'result':1,'message':'"+result.getError_msg()+"'}";
//			    			return JSON_MESSAGE;
//			    		}
//			    		NotifyOrderInfoSFResponse response = (NotifyOrderInfoSFResponse) result.getResponse();
//			    		prints.setDestcode(response.getOrderInfo().get(0).getDestcode());//目的地代码取顺丰返回值
//			    		prints.setOrigin(response.getOrderInfo().get(0).getOrigincode());//原寄地代码取顺丰返回值
//			    		prints.setPost_no(response.getOrderInfo().get(0).getMailno());
//			    		prints.setReceipt_no(response.getOrderInfo().get(0).getReturn_tracking_no());		    	
//			    	}else {//调用顺丰查询订单接口,主要为了获取目的地代码、原寄地代码
//			    		NotifyOrderInfoSFResponse result = ordVisitTacheManager.querySfOrder(order_id);
//			    		if (!EcsOrderConsts.SF_INF_SUCC_CODE.equals(result.getErrorCode())){
//			    			json = "{'result':1,'message':'"+result.getErrorMessage()+"'}";
//			    			return JSON_MESSAGE;
//			    		}else{
//			    			prints.setPost_no(result.getOrderInfo().get(0).getMailno());
//			    			prints.setReceipt_no(result.getOrderInfo().get(0).getReturn_tracking_no());
//			    			prints.setDestcode(result.getOrderInfo().get(0).getDestcode());//目的地代码取顺丰返回值
//			    			prints.setOrigin(result.getOrderInfo().get(0).getOrigincode());//原寄地代码取顺丰返回值
//			    			delivery.setLogi_no(result.getOrderInfo().get(0).getMailno());
//			    			delivery.setReceipt_no(result.getOrderInfo().get(0).getReturn_tracking_no());
//			    			delivery.setDb_action(ConstsCore.DB_ACTION_UPDATE);
//			    			delivery.setIs_dirty(ConstsCore.IS_DIRTY_YES);
//			    			delivery.store();
//			    		}
//			    	}
//			    	
//			    }
//		    }
			//prints.setDeliver_info(prints.getDeliver_info().replace("\n", "<br>"));
			prints.setPost_items(prints.getPost_items().replace("\n", "<br>"));
			delvery_print_id=ordPostPrintManager.doAddOne(prints);
			json = "{'result':0,'message':'保存成功！','delveryPrintId':'"+delvery_print_id+"','items':'"+itmesIds+"','is_signback':'"+prints.getIs_signback()+"','logi_company':'"+logi_company+"','delivery_id':'"+prints.getDelivery_id()+"','logi_no':'"+prints.getPost_no()+"'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}

	
	/**
	 * 质检稽核界面添加礼品到补寄品表
	 * @return
	 */
	public String addGifToItems() {
		try {
			OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest =
					new OrderDeliveryItemBusiRequest();
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
					.getOrderTree(order_id);

			OrderDeliveryBusiRequest orderDeliveryBusiRequest = new OrderDeliveryBusiRequest();
			List<OrderDeliveryBusiRequest> list=orderTree.getOrderDeliveryBusiRequests();
			
			for (OrderDeliveryBusiRequest de : list) {
				if(EcsOrderConsts.DELIVERY_TYPE_NORMAL.equals(de.getDelivery_type())){//肯定有一条记录是正常发货的
					orderDeliveryBusiRequest=de;
					break;
				}  
			}
			orderDeliveryItemBusiRequest.setDelivery_id(orderDeliveryBusiRequest!=null
					?orderDeliveryBusiRequest.getDelivery_id():"");
			orderDeliveryItemBusiRequest.setName(reissue_info);
			Integer num=null;
			if(reissue_num!=null&&!reissue_num.equals("")){
				num=Integer.parseInt(reissue_num);
			}		
			orderDeliveryItemBusiRequest.setNum(num);
			orderDeliveryItemBusiRequest.setOrder_id(order_id);
			orderDeliveryItemBusiRequest.setItemtype(OrderStatus.DELIVERY_ITEM_TYPE_3);
			orderDeliveryItemBusiRequest.setCol2(gift_inst_id);
			
			OrderDeliveryItemBusiRequest items=ordPostPrintManager.addDeliveryItem(orderDeliveryItemBusiRequest);
			json = "{'result':0,'message':'保持成功！','item_id':'"+items.getItem_id()+"'}";
		} catch (Exception e) {
			json = "{'result':1,'message':'操作失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	
	
	public IOrdPostPrintManager getOrdPostPrintManager() {
		return ordPostPrintManager;
	}

	public void setOrdPostPrintManager(IOrdPostPrintManager ordPostPrintManager) {
		this.ordPostPrintManager = ordPostPrintManager;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public DeliveryPrints getPrints() {
		return prints;
	}

	public void setPrints(DeliveryPrints prints) {
		this.prints = prints;
	}



	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getLogi_company() {
		return logi_company;
	}

	public void setLogi_company(String logi_company) {
		this.logi_company = logi_company;
	}

	

	public String getLogi_no() {
		return logi_no;
	}

	public void setLogi_no(String logi_no) {
		this.logi_no = logi_no;
	}

	public String getSending_type() {
		return sending_type;
	}

	public void setSending_type(String sending_type) {
		this.sending_type = sending_type;
	}

	public String getN_shipping_amount() {
		return n_shipping_amount;
	}

	public void setN_shipping_amount(String n_shipping_amount) {
		this.n_shipping_amount = n_shipping_amount;
	}



	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public List<Map> getPostPayModeList() {
		return postPayModeList;
	}

	public void setPostPayModeList(List<Map> postPayModeList) {
		this.postPayModeList = postPayModeList;
	}

	public List<Map> getIsInsurList() {
		return isInsurList;
	}

	public void setIsInsurList(List<Map> isInsurList) {
		this.isInsurList = isInsurList;
	}

	public List<Map> getIsCodList() {
		return isCodList;
	}

	public void setIsCodList(List<Map> isCodList) {
		this.isCodList = isCodList;
	}

	public Map<String, String> getPostModeMap() {
		return postModeMap;
	}

	public void setPostModeMap(Map<String, String> postModeMap) {
		this.postModeMap = postModeMap;
	}

	public String getPost_type() {
		return post_type;
	}

	public void setPost_type(String post_type) {
		this.post_type = post_type;
	}

	public String getDelvery_print_id() {
		return delvery_print_id;
	}

	public void setDelvery_print_id(String delvery_print_id) {
		this.delvery_print_id = delvery_print_id;
	}

	public String getItmesIds() {
		return itmesIds;
	}

	public void setItmesIds(String itmesIds) {
		this.itmesIds = itmesIds;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getOrder_is_his() {
		return order_is_his;
	}

	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}

	public String getReissue_info() {
		return reissue_info;
	}

	public void setReissue_info(String reissue_info) {
		this.reissue_info = reissue_info;
	}

	

	public String getGift_inst_id() {
		return gift_inst_id;
	}

	public void setGift_inst_id(String gift_inst_id) {
		this.gift_inst_id = gift_inst_id;
	}

	public String getReissue_num() {
		return reissue_num;
	}

	public void setReissue_num(String reissue_num) {
		this.reissue_num = reissue_num;
	}

	public String getResult_msg(){
		return result_msg;
	}
	public void setResult_msg(String result_msg){
		this.result_msg = result_msg;
	}

	public List<Map> getIsReceiptList() {
		return isReceiptList;
	}

	public void setIsReceiptList(List<Map> isReceiptList) {
		this.isReceiptList = isReceiptList;
	}

	public String getPrint_type() {
		return print_type;
	}

	public void setPrint_type(String print_type) {
		this.print_type = print_type;
	}
	
	
}
