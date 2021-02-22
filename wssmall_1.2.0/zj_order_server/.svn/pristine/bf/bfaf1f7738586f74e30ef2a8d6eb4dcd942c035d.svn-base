package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import params.ZteRequest;
import params.ZteResponse;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.OrderExpWriteForBusReq;
import params.resp.OrderExpWriteResp;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrPackageSubProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSpProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderSubProductBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.iservice.IOrderQueueBaseManager;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.EcsOrderMapConsts;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.OrderReleaseRecord;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.ICommonManager;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IWorkPoolManager;

import consts.ConstsCore;
public class Utils{
	private static Logger logger = Logger.getLogger(Utils.class);
	/**
	 * 标记异常
	 * @param order_id
	 * @param is_notice_sr_dk 是否需要通知森瑞退卡
	 */
	public static void markException(String order_id,Boolean is_notice_sr_dk) {
		Map params = new HashMap();
		BusiCompRequest bcr = new BusiCompRequest();
		bcr.setEnginePath("zteCommonTraceRule.markedException");
		bcr.setOrder_id(order_id);
		params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
		if(is_notice_sr_dk){//通知森瑞退卡
			params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
		}
		bcr.setQueryParams(params);
		CommonDataFactory.getInstance().execBusiComp(bcr);
	}

	/**
	 * 标记异常
	 * @param order_id
	 * @param is_notice_sr_dk 是否需要通知森瑞退卡
	 * @param exceptionType   异常类型 EcsOrderConsts.ABNORMAL_TYPE_OPEN等等
	 * @param exceptionRemark 异常描述
	 */
	public static void markException(String order_id,Boolean is_notice_sr_dk,String exceptionType,String exceptionRemark) {
		Map params = new HashMap();
		BusiCompRequest bcr = new BusiCompRequest();
		bcr.setEnginePath("zteCommonTraceRule.markedException");
		bcr.setOrder_id(order_id);
		params.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
		params.put(EcsOrderConsts.EXCEPTION_TYPE, exceptionType);
		params.put(EcsOrderConsts.EXCEPTION_REMARK, exceptionRemark);
		if(is_notice_sr_dk){//通知森瑞退卡
			params.put(EcsOrderConsts.NOTIFY_SR_DK, EcsOrderConsts.NOTIFY_SR_DK_VAL);
		}
		bcr.setQueryParams(params);
		CommonDataFactory.getInstance().execBusiComp(bcr);
	}

	/**
	 * 保存终端串号/号码释放记录
	 */
	public static String saveReleaseRecord(OrderReleaseRecord record){

		ICommonManager commonManager = SpringContextHolder.getBean("commonManager");
		return commonManager.saveReleaseRecord(record);
	}
	
	/**
	 * 调用异常系统写入异常
	 * @param order_id
	 * @param exception_id
	 * @param exception_remark
	 */
	public static void writeExp(String order_id,String exception_type,String exception_desc,String write_exp_flag){
		try{
			//过滤接口组件的标记
			if(!StringUtils.equals(write_exp_flag, EcsOrderConsts.BASE_YES_FLAG_1)){
				return ;
			}
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);//是否启用异常系统
			if(!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0 ){//异常系统启动标识
				OrderExpWriteForBusReq ordExpWReq = new OrderExpWriteForBusReq();
				ordExpWReq.setObj_id(order_id); //对象id
				ordExpWReq.setObj_type("order"); //对象类型（order、order_queue）
				ordExpWReq.setSearch_id(EcsOrderConsts.EXP_ORD_FLOW_MARK_ID); //搜索id
				ordExpWReq.setSearch_code(EcsOrderConsts.EXP_ORD_FLOW_MARK_CODE);//搜索编码
				IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> exceptionList = dcPublicCache.getList("DIC_ORDER_EXCEPTION_TYPE");
				List<Map> explist = dcPublicCache.getList("EXCEPTION_TYPE");
				exceptionList.addAll(explist);
				for(int i=0;i<exceptionList.size();i++){
					Map map = exceptionList.get(i);
					if(StringUtils.equals((String) map.get("pkey"), exception_type)){
						String error_msg="人工标记异常（"+(String) map.get("pname")+"）";
						ordExpWReq.setError_msg(error_msg);//错误信息
					}
				}
				ordExpWReq.setError_stack_msg(exception_desc);
				String exe_type = MqEnvGroupConfigSetting.ORD_EXP_EXEC;//调用方式 m/d  m是走mq d是走dubbo
				if(ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)){
					writeExpByDubbo(ordExpWReq);
				}else{
					writeExpByMq(ordExpWReq);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过dubbo调用
	 * @param req
	 */
	private static void writeExpByDubbo(OrderExpWriteForBusReq req){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderExpWriteResp response = client.execute(req, OrderExpWriteResp.class);
	}
	
	/**
	 * 通过mq调用
	 * @param req
	 * @return
	 */
	private static ZteResponse writeExpByMq(OrderExpWriteForBusReq req){
		AsynExecuteMsgWriteMqReq mqReq = new AsynExecuteMsgWriteMqReq();
		mqReq.setService_code(req.getApiMethodName());
		mqReq.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		mqReq.setZteRequest((ZteRequest)req);
		mqReq.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		IOrderQueueBaseManager  orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
	    return orderQueueBaseManager.asynExecuteMsgWriteMq(mqReq);
	}
	
	/**
	 * 订单向下流转环节后，公共解锁方法
	 * @param prev 上一环节
	 * @param cur 当前环节
	 */
	public static void commonUnlock(String order_id, String prev, String cur) {
		//环节跳转标识，true跳转，false未跳转
		boolean flag = true;
		/*//上一环节编码和当前环节编码都为空
		if (StringUtil.isEmpty(prev) || StringUtil.isEmpty(cur)) {
			logger.info("Utils.commonUnlock:" + order_id + ", prev("+prev+") or cur("+cur+") is null.");
			return;
		}
		1、环节没有跳转
		  2、审核环节调到下一环节（配货或开户）
		if (prev.equals(EcsOrderConsts.DIC_ORDER_NODE_B) && cur.equals(EcsOrderConsts.DIC_ORDER_NODE_B)) {
			flag = false;
		} else if (prev.equals(EcsOrderConsts.DIC_ORDER_NODE_B) && !cur.equals(EcsOrderConsts.DIC_ORDER_NODE_B)) {
			flag = true;//
		}
		1、上一环节是 配货 或 开户 或 写卡 或 业务办理，当前环节是 配货 或 开户 或 写卡 或 业务办理，即环节没有流转
		  2、环节流转到下一环节
		if ((prev.equals(EcsOrderConsts.DIC_ORDER_NODE_C) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_D) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_X) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_Y))
				&& (cur.equals(EcsOrderConsts.DIC_ORDER_NODE_C) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_D) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_X) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_Y))) {
			flag = false;
		} else if ((prev.equals(EcsOrderConsts.DIC_ORDER_NODE_C) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_D) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_X) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_Y))
				&& !(cur.equals(EcsOrderConsts.DIC_ORDER_NODE_C) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_D) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_X) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_Y))) {
			flag = true;
		}
		1、上一环节是 质检稽核 或 发货，当前环节是 质检稽核 或 发货，即环节没有流转
		  2、环节流转到下一环节
		if ((prev.equals(EcsOrderConsts.DIC_ORDER_NODE_F) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_H))
				&& !(cur.equals(EcsOrderConsts.DIC_ORDER_NODE_F) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_H))) {
			flag = false;
		} else if ((prev.equals(EcsOrderConsts.DIC_ORDER_NODE_F) || prev.equals(EcsOrderConsts.DIC_ORDER_NODE_H))
				&& (cur.equals(EcsOrderConsts.DIC_ORDER_NODE_F) || cur.equals(EcsOrderConsts.DIC_ORDER_NODE_H))) {
			flag = true;
		}
		1、没有从订单归档跳到资料归档
		  2、订单归档跳到资料归档
		if (prev.equals(EcsOrderConsts.DIC_ORDER_NODE_L) && cur.equals(EcsOrderConsts.DIC_ORDER_NODE_L)) {
			flag = false;
		} else if (prev.equals(EcsOrderConsts.DIC_ORDER_NODE_L) && !cur.equals(EcsOrderConsts.DIC_ORDER_NODE_L)) {
			flag = true;
		}*/
		if(!StringUtils.isEmpty(prev) && !StringUtils.isEmpty(cur) && cur.equals(prev)){
			flag = false;
		}
		logger.info("Utils.commonUnlock:" + order_id + ", prev("+prev+") - cur("+cur+") ,flag(" + flag + ")");
		
		if (flag) {
			AdminUser user = ManagerUtils.getAdminUser();
			String tacheAuth = user.getTacheAuth();//环节权限
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			//上一环节解锁
			OrderLockBusiRequest preOrderLockBusiRequest = CommonDataFactory.getInstance().getOrderLockBusiRequest(orderTree, prev);
			if(preOrderLockBusiRequest!=null){
				preOrderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				preOrderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_DELETE);
				preOrderLockBusiRequest.store();
			}
            ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String order_froms = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getOrder_from();
    	    String goods_cat = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);
            String close_group_order_catid = cacheUtil.getConfigInfo("page_look_info");
            String kgs = cacheUtil.getConfigInfo("kg_lock_new");
        	//如果有当前环节权限则锁定订单，如果没有则解锁
			IWorkPoolManager workPoolManager = SpringContextHolder.getBean("workPoolManager");
			OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance().getOrderLockBusiRequest(orderTree, cur);
            boolean flagnew = true;
    	    if("1".equals(kgs)&&"10093".equals(order_froms) && !StringUtils.isEmpty(close_group_order_catid) && close_group_order_catid.contains(goods_cat)){
    	        flagnew = false;
    	    }
    	    if(!flagnew){
    	    	if(null != orderLockBusiRequest){
	    			orderLockBusiRequest.setOrder_id(order_id);
	    			orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
	    			orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
	    			orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
	    			orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
	    			orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
	    			//解锁订单释放工号池和锁单结束时间
	    			orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
	    			orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
	    			orderLockBusiRequest.store();
	    		}
    	    }else{
    	    	if(StringUtils.isEmpty(tacheAuth) || !tacheAuth.contains(cur)){//环节权限为空，或者没有当前环节权限，订单解锁
    	    		if(null != orderLockBusiRequest){
    	    			orderLockBusiRequest.setOrder_id(order_id);
    	    			orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
    	    			orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
    	    			orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
    	    			orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
    	    			orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
    	    			//解锁订单释放工号池和锁单结束时间
    	    			orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
    	    			orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
    	    			orderLockBusiRequest.store();
    	    		}
    	    	}
    	    	else if(!StringUtils.isEmpty(tacheAuth) && tacheAuth.contains(cur)){
    	    		if(null == orderLockBusiRequest){
    	    			orderLockBusiRequest = new OrderLockBusiRequest();
    	    			IEcsOrdManager ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
    	    			orderLockBusiRequest.setLock_id(ecsOrdManager.getSequences("o_outcall_log"));
    	    			orderLockBusiRequest.setOrder_id(order_id);
    	    			orderLockBusiRequest.setTache_code(cur);
    	    			orderLockBusiRequest.setLock_user_id(user.getUserid());
    	    			orderLockBusiRequest.setLock_user_name(user.getUsername());
    	    			orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
    	    			try {
    	    				orderLockBusiRequest.setLock_time(DateUtil.getTime2());
    	    			} catch (FrameException e) {
    	    				e.printStackTrace();
    	    			}
    	    			orderLockBusiRequest.setSource_from(ManagerUtils.getSourceFrom());
    	    			//锁单信息增加工号池和锁单结束时间
    	    			orderLockBusiRequest.setPool_id(workPoolManager.getLockTimeByUserId(user.getUserid()).getPool_id());
    	    			orderLockBusiRequest.setLock_end_time(workPoolManager.getLockTimeByUserId(user.getUserid()).getLock_end_time());
    	    			orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
    	    			orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
    	    			orderLockBusiRequest.store();
    	    		}
    	    	}
    	    }
		}
	}
	
	/**
	 * 指定位置截取，支持空串，不足位字符
	 * @param str
	 * @param star
	 * @param end
	 */
	public static String substringByCon(String str, int star, int end) {
		String rstr = "";
		if(str==null)rstr = "";

		int strlen = str.length();
		if(strlen < end+1){
			rstr = str;
		}else{
			rstr = str.substring(star, end);
		}
		return rstr;
	}
	
	/**
	 * 获取受理单打印内容
	 * @param order_id
	 * @return
	 */
	public static Map getAcceptanceFormMap(String order_id){
		Map map = new HashMap();
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		
		String title = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//受理单标题	填充地市
		String orderCity = CommonDataFactory.getInstance().getLanCode(title);//6位编码转4位编码
		
		////////////////////客户基本信息
		String cust_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NAME);//客户名称
//		String cust_type = "个人客户";//客户类型,固定为“个人客户”
		String cert_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERTI_TYPE);//证件类型
		String cert_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_CARD_NUM);//证件号码
		String cert_addr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.CERT_ADDRESS);//证件地址
//		String cert_fail = "";//证件有效期,业务确认不需要
		String contact_name = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_NAME);//联系人
		String contact_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.REVEIVER_MOBILE);//联系电话
		String contact_addr = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SHIP_ADDR);//通信地址
//		String contact_mail = "";//电子邮箱,（沃云购下单没有要求用户填写）
		String first_payment = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.FIRST_PAYMENT);//首月资费方式
		
		////////////////////业务受理信息
		String active_no = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ACTIVE_NO);//订单编号,物流模式下除非生产人员填写，否则没有
		String phone_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.PHONE_NUM);//用户号码
		//商品、货品属性
		String deposit_fee = "";//【预存金额】
		String lump_sum_payment = "";//【一次性到账（新增属性）】
		String total_fee = "";//【协议期总送费金额】
		String contract_period = "";//【合约期】	备注：合约期通过合约计划货品获取
		String first_return = "";//【首月返还】
		String monthly_return = "";//【分月返还 】
		String last_return = "";//【尾月返还（新增属性）】
		String domestic_voice_min = "";//【国内语音拨打分钟数】
		String out_package_con_fee = "";//【套餐外通话费用 】
		String local_voice_min = "";//【本地拨打分钟】
		String out_package_local_fee = "";//【套餐外本地拨打费用（新增）】
		String domestic_roaming_charges = "";//【国内漫游通话费用（新增）】
		String domestic_traffic = "";//【国内流量】
		String out_package_tra_fee = "";//【套餐外流量费用 】
		String province_traffic = "";//【省内流量】
		
		String taocan_name = "";//套餐名称
		String heyue_name = "";//合约计划名称
		String zengsongyewu_name = "";//活动中的赠送业务

		String bss_operator = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR_NAME) + "	" + CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_OPERATOR);//受理人及工号
		String bss_account_time = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_ACCOUNT_TIME);//日期
		try{
			String time = DateUtil.convertDateFormat(bss_account_time, DateUtil.DATE_FORMAT_2, DateUtil.DATE_FORMAT_1);//转为yyyy-MM-dd格式
			if(!StringUtils.isEmpty(time)){
				bss_account_time = time;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		/**
		 * 公共属性
		 */
		title = EcsOrderMapConsts.ORDER_CITY.containsKey(orderCity) ? EcsOrderMapConsts.ORDER_CITY.get(orderCity)+"市" : orderCity;
		map.put("title1", title);
		map.put("title2", title);
		map.put("title3m", title);
		map.put("cust_name", cust_name);
		map.put("cert_type", EcsOrderMapConsts.BUYER_CARD_TYPE.containsKey(cert_type) ? EcsOrderMapConsts.BUYER_CARD_TYPE.get(cert_type) : cert_type);
		map.put("cert_num", cert_num);
		map.put("cert_addr", cert_addr);
		map.put("contact_name", contact_name);
		map.put("contact_num", contact_num);
		map.put("contact_addr", contact_addr);
		map.put("active_no", active_no);
		map.put("phone_num", phone_num);
		
		map.put("bss_operator", bss_operator);
		map.put("bss_account_time", bss_account_time);

		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		/**
		 * 独立属性
		 */
		if(EcsOrderConsts.GOODS_TYPE_NUM_CARD.equals(goods_type)){//号卡
			Map goodsSpec = CommonDataFactory.getInstance().getGoodsSpecMap(order_id,"");//商品规格
			Map heyueSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10001);//合约计划货品
			Map taocanSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10002);//套餐货品
			

			deposit_fee = (String)goodsSpec.get("deposit_fee");//【预存金额】
			lump_sum_payment = (String)goodsSpec.get("disposable_return");//【一次性到账（新增属性）】
			total_fee = (String)goodsSpec.get("all_give");//【协议期总送费金额】
			contract_period = (String)heyueSpec.get("package_limit");//【合约期】
			first_return = (String)goodsSpec.get("order_return");//【首月返还】
			monthly_return = (String)goodsSpec.get("mon_return");//【分月返还 】
			last_return = (String)goodsSpec.get("last_month_return");//【尾月返还（新增属性）】
			domestic_voice_min = (String)taocanSpec.get("call_times");//【国内语音拨打分钟数】
			out_package_con_fee = (String)taocanSpec.get("out_call_times");//【套餐外通话费用 】
			local_voice_min = (String)taocanSpec.get("local_call_times");//【本地拨打分钟】
			out_package_local_fee = (String)taocanSpec.get("out_local_call_fee");//【套餐外本地拨打费用（新增）】
			domestic_roaming_charges = (String)taocanSpec.get("roam_call_fee");//【国内漫游通话费用（新增）】
			domestic_traffic = (String)taocanSpec.get("flow");//【国内流量】
			out_package_tra_fee = (String)taocanSpec.get("out_flow");//【套餐外流量费用 】
			province_traffic = (String)taocanSpec.get("local_flow");//【省内流量】
			
			taocan_name = (String)taocanSpec.get("name");//套餐名称
			heyue_name = (String)heyueSpec.get("name");//合约计划名称
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);//此方法可获取到历史订单树
			if(null!=tree){
				List<String> skus = new ArrayList();
				StringBuffer buffer = new StringBuffer();
				//SP产品
				List<OrderSpProductBusiRequest> spProducts = tree.getSpProductBusiRequest();
				if(null!=spProducts&&spProducts.size()>0){
					for(OrderSpProductBusiRequest spProduct:spProducts){
						skus.add(spProduct.getSku());
					}
				}
				//附加产品
				List<OrderSubProductBusiRequest> subProducts = tree.getOrderSubProductBusiRequest();
				List<AttrPackageSubProdBusiRequest> subProdPackages = tree.getAttrPackageSubProdBusiRequest();
				if(null!=subProducts&&subProducts.size()>0&&null!=subProdPackages&&subProdPackages.size()>0){
					for(OrderSubProductBusiRequest subProduct:subProducts){
						if("0".equals(subProduct.getProd_inst_id())){//主号
							for(AttrPackageSubProdBusiRequest subProdPackage:subProdPackages){
								if(StringUtils.equals(subProduct.getSub_pro_inst_id(), subProdPackage.getSubProd_inst_id())){
									skus.add(subProdPackage.getSku());
								}
							}
						}
					}
				}
				//套餐业务包
				List<AttrPackageBusiRequest> attrPackages = tree.getPackageBusiRequests();
				if(null!=attrPackages&&attrPackages.size()>0){
					for(AttrPackageBusiRequest attrPackage:attrPackages){
						skus.add(attrPackage.getSku());
					}
				}
				if(skus.size()>0){
					for(String sku:skus){
						Goods goods = CommonDataFactory.getInstance().getGoodsBySku(sku);
						if(null!=goods){
							buffer.append(goods.getName()).append(';');
						}
					}
				}
				if(buffer.length()>0){
					buffer = new StringBuffer().append("[3]").append(buffer);
				}
				zengsongyewu_name = buffer.toString();//活动中的赠送业务
			}

			Map business_info = new HashMap();
			
			business_info.put("deposit_fee", deposit_fee);
			business_info.put("lump_sum_payment", lump_sum_payment);
			business_info.put("total_fee", total_fee);
			business_info.put("contract_period", contract_period);
			business_info.put("first_return", first_return);
			business_info.put("monthly_return", monthly_return);
			business_info.put("last_return", last_return);
			business_info.put("domestic_voice_min", domestic_voice_min);
			business_info.put("out_package_con_fee", out_package_con_fee);
			business_info.put("local_voice_min", local_voice_min);
			business_info.put("out_package_local_fee", out_package_local_fee);
			business_info.put("domestic_roaming_charges", domestic_roaming_charges);
			business_info.put("domestic_traffic", domestic_traffic);
			business_info.put("out_package_tra_fee", out_package_tra_fee);
			business_info.put("province_traffic", province_traffic);
			
			business_info.put("taocan_name", taocan_name);
			business_info.put("heyue_name", heyue_name);
			business_info.put("zengsongyewu_name", zengsongyewu_name);
			business_info.put("first_payment", EcsOrderMapConsts.FIRST_FEE_TYPE.containsKey(first_payment) ? EcsOrderMapConsts.FIRST_FEE_TYPE.get(first_payment) : first_payment);
			
			String template_p = cacheUtil.getConfigInfo("HK_PRODUCT");//号卡产品
			if(!StringUtil.isEmpty(template_p)){
		        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
		        Matcher matcher = pattern.matcher(template_p);
		        String word = "";
		        while(matcher.find()){
		        	String key = matcher.group();
		        	word = (String)business_info.get(key);
		        	if(null==word){
		        		word = "";
		        	}
		        	template_p = template_p.replace("{"+key+"}", word);
		        }  
			}
			map.put("template_p", template_p);
			
			String template_f = cacheUtil.getConfigInfo("HK_FEE");//号卡资费&优惠
			if(!StringUtil.isEmpty(template_f)){
		        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
		        Matcher matcher = pattern.matcher(template_f);
		        String word = "";
		        while(matcher.find()){
		        	String key = matcher.group();
		        	word = (String)business_info.get(key);
		        	if(null==word){
		        		word = "";
		        	}
		        	template_f = template_f.replace("{"+key+"}", word);
		        }  
			}
			map.put("template_f", template_f);
		}else if(EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)){//上网卡
			Map taocanSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10002);//套餐货品
			
			taocan_name = (String)taocanSpec.get("name");//套餐名称

			map.put("taocan_name", "[1]" + taocan_name);
		}else if(EcsOrderConsts.GOODS_TYPE_CONTRACT_MACHINE.equals(goods_type)){//合约机
			//合约机特有属性
			String terminal_name = "";//终端名称
			String terminal_num = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.TERMINAL_NUM);//终端串号
			String purchar_price = "";//购机款
			
			String zengsongyewu_p = "";
			String zengsongyewu_f = "";
			
			Map goodsSpec = CommonDataFactory.getInstance().getGoodsSpecMap(order_id,"");//商品规格
			Map heyueSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10001);//合约计划货品
			Map taocanSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10002);//套餐货品
			Map zhongduanSpec = CommonDataFactory.getInstance().getProductSpecMap(order_id, SpecConsts.TYPE_ID_10000);//手机终端货品
			

			purchar_price = (String)goodsSpec.get("mobile_price");//购机款
			deposit_fee = (String)goodsSpec.get("deposit_fee");//【预存金额】
			lump_sum_payment = (String)goodsSpec.get("disposable_return");//【一次性到账（新增属性）】
			total_fee = (String)goodsSpec.get("all_give");//【协议期总送费金额】
			contract_period = (String)heyueSpec.get("package_limit");//【合约期】
			first_return = (String)goodsSpec.get("order_return");//【首月返还】
			monthly_return = (String)goodsSpec.get("mon_return");//【分月返还 】
			last_return = (String)goodsSpec.get("last_month_return");//【尾月返还（新增属性）】
			domestic_voice_min = (String)taocanSpec.get("call_times");//【国内语音拨打分钟数】
			out_package_con_fee = (String)taocanSpec.get("out_call_times");//【套餐外通话费用 】
			local_voice_min = (String)taocanSpec.get("local_call_times");//【本地拨打分钟】
			out_package_local_fee = (String)taocanSpec.get("out_local_call_fee");//【套餐外本地拨打费用（新增）】
			domestic_roaming_charges = (String)taocanSpec.get("roam_call_fee");//【国内漫游通话费用（新增）】
			domestic_traffic = (String)taocanSpec.get("flow");//【国内流量】
			out_package_tra_fee = (String)taocanSpec.get("out_flow");//【套餐外流量费用 】
			province_traffic = (String)taocanSpec.get("local_flow");//【省内流量】
			
			taocan_name = (String)taocanSpec.get("name");//套餐名称
			heyue_name = (String)heyueSpec.get("name");//合约计划名称
			terminal_name = (String)zhongduanSpec.get("name");//终端名称
			OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);//此方法可获取到历史订单树
			if(null!=tree){
				List<String> skus = new ArrayList();
				StringBuffer buffer = new StringBuffer();
				//SP产品
				List<OrderSpProductBusiRequest> spProducts = tree.getSpProductBusiRequest();
				if(null!=spProducts&&spProducts.size()>0){
					for(OrderSpProductBusiRequest spProduct:spProducts){
						skus.add(spProduct.getSku());
					}
				}
				//附加产品
				List<OrderSubProductBusiRequest> subProducts = tree.getOrderSubProductBusiRequest();
				List<AttrPackageSubProdBusiRequest> subProdPackages = tree.getAttrPackageSubProdBusiRequest();
				if(null!=subProducts&&subProducts.size()>0&&null!=subProdPackages&&subProdPackages.size()>0){
					for(OrderSubProductBusiRequest subProduct:subProducts){
						if("0".equals(subProduct.getProd_inst_id())){//主号
							for(AttrPackageSubProdBusiRequest subProdPackage:subProdPackages){
								if(StringUtils.equals(subProduct.getSub_pro_inst_id(), subProdPackage.getSubProd_inst_id())){
									skus.add(subProdPackage.getSku());
								}
							}
						}
					}
				}
				//套餐业务包
				List<AttrPackageBusiRequest> attrPackages = tree.getPackageBusiRequests();
				if(null!=attrPackages&&attrPackages.size()>0){
					for(AttrPackageBusiRequest attrPackage:attrPackages){
						skus.add(attrPackage.getSku());
					}
				}
				if(skus.size()>0){
					for(String sku:skus){
						Goods goods = CommonDataFactory.getInstance().getGoodsBySku(sku);
						if(null!=goods){
							buffer.append(goods.getName()).append(';');
						}
					}
				}
				if(buffer.length()>0){
					zengsongyewu_p = new StringBuffer().append("[4]").append(buffer).toString();
					zengsongyewu_f = new StringBuffer().append("[3]").append(buffer).toString();
				}
				zengsongyewu_name = buffer.toString();//活动中的赠送业务
			}
			map.put("terminal_name", terminal_name);
			map.put("terminal_num", terminal_num);
			map.put("purchar_price", purchar_price);

			Map business_info = new HashMap();
			
			business_info.put("deposit_fee", deposit_fee);
			business_info.put("lump_sum_payment", lump_sum_payment);
			business_info.put("total_fee", total_fee);
			business_info.put("contract_period", contract_period);
			business_info.put("first_return", first_return);
			business_info.put("monthly_return", monthly_return);
			business_info.put("last_return", last_return);
			business_info.put("domestic_voice_min", domestic_voice_min);
			business_info.put("out_package_con_fee", out_package_con_fee);
			business_info.put("local_voice_min", local_voice_min);
			business_info.put("out_package_local_fee", out_package_local_fee);
			business_info.put("domestic_roaming_charges", domestic_roaming_charges);
			business_info.put("domestic_traffic", domestic_traffic);
			business_info.put("out_package_tra_fee", out_package_tra_fee);
			business_info.put("province_traffic", province_traffic);
			
			business_info.put("taocan_name", taocan_name);
			business_info.put("terminal_name", terminal_name);
			business_info.put("heyue_name", heyue_name);
			business_info.put("zengsongyewu_p", zengsongyewu_p);
			business_info.put("zengsongyewu_f", zengsongyewu_f);
			business_info.put("first_payment", EcsOrderMapConsts.FIRST_FEE_TYPE.containsKey(first_payment) ? EcsOrderMapConsts.FIRST_FEE_TYPE.get(first_payment) : first_payment);
			
			String template_p = cacheUtil.getConfigInfo("HYJ_PRODUCT");//号卡产品
			if(!StringUtil.isEmpty(template_p)){
		        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
		        Matcher matcher = pattern.matcher(template_p);
		        String word = "";
		        while(matcher.find()){
		        	String key = matcher.group();
		        	word = (String)business_info.get(key);
		        	if(null==word){
		        		word = "";
		        	}
		        	template_p = template_p.replace("{"+key+"}", word);
		        }  
			}
			map.put("template_p", template_p);
			
			String template_f = cacheUtil.getConfigInfo("HYJ_FEE");//号卡资费&优惠
			if(!StringUtil.isEmpty(template_f)){
		        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
		        Matcher matcher = pattern.matcher(template_f);
		        String word = "";
		        while(matcher.find()){
		        	String key = matcher.group();
		        	word = (String)business_info.get(key);
		        	if(null==word){
		        		word = "";
		        	}
		        	template_f = template_f.replace("{"+key+"}", word);
		        }  
			}
			map.put("template_f", template_f);
			
		}else{
			
		}
		return map;
	}
	
	public static String lpad(String value , int length , String replace){
		while(value.length() < length){
			value = replace + value;
		}
		return value;
	}
}