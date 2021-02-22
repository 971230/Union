package com.ztesoft.net.action;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.buffalo.service.spring.SpringUtil;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;

import params.ZteResponse;
import params.order.req.OrderHandleLogsReq;
import util.Utils;
import zte.net.ecsord.common.AttrBusiInstTools;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.StypeConsts;
import zte.net.ecsord.params.attr.resp.AttrInstLoadResp;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrGiftInfoBusiRequest;
import zte.net.ecsord.params.busi.req.AttrInstBusiRequest;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtvtlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPhoneInfoFukaBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.pub.req.OrderSubPackageList;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.ILogsServices;
import zte.net.iservice.IOrderServices;
import zte.net.model.CrawlerConfig;
import zte.net.model.CrawlerProc;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;

import com.alibaba.fastjson.JSONArray;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.context.webcontext.ThreadContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.OrdReceipt;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IOrdCollectManagerManager;
import com.ztesoft.net.service.IOrdOpenAccountTacheManager;
import com.ztesoft.net.service.IOrderCrawlerManager;
import com.ztesoft.net.service.IOrderExtManager;
import com.ztesoft.net.service.IOrderFlowManager;
import com.ztesoft.net.service.IOrderSupplyManager;
import com.ztesoft.net.service.impl.EcsOrdManager;
import com.ztesoft.net.util.ZjCommonUtils;

import consts.ConstsCore;


public class OrderCrawlerAction extends WWAction {
	@Resource
	private IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	private IOrderCrawlerManager orderCrawlerManager;
	@Resource
	private IEcsOrdManager ecsOrdManager;
	@Resource
	private IOrderExtManager orderExtManager;
	@Resource
	private IOrderSupplyManager iOrderSupplyManager;
	@Resource
	private ILogsServices logsServices;
	@Resource
	private IOrderServices orderServices;
	@Resource
	private IOrderFlowManager ordFlowManager;// 流程处理类
	@Resource
	private IOrdCollectManagerManager ordCollectManager;
	@Resource
	private IOrdOpenAccountTacheManager ordOpenAccountTacheManager;
	
	private String order_id;
	private String orders;
	private String query_type;
	private String q_check; // WMS质检稽核特殊处理（不需要做环节界面验证）
	private String btnType;
	private String shipping_company;
	private String oldCertNum;// 旧的证件号码 供预校验比较
	private String abnormal_type;// 异常类型
	private String tel;
	private String address;
	private String mobile;
	private String resourceCodeStr;// 多商品串号拼接字符串
	private String resourceItemsStr;// 多商品串号Item_id拼接字符串
	private String protect_free; // 保费
	private String logi_receiver;
	private String logi_receiver_phone;
	private String delivery_id;
	private String need_receipt;
	private String prod_audit_status; // 质检稽核状态
	private String is_order_exp;
	private String dealType;// 处理类型
	private String dealDesc;// 处理内容
	private String ICCID;
	private String feiyongmingxi_s;
	private String active_fail_type;
	private String active_status;
	private String params;
	private String cond_id;//进程条件id
	private String cuc_id;//爬虫操作配置id
	private String proc_id;//进程id
	private String flag;
	private String d_type = "apply";// cfm确认 apply申请 ycl 预处理
	private String changeMode;
	private String good_price_system;// 商品原价
	private String num_price;// 号码预存
	private String deposit_price;// 多交预存
	private String openAcc_price;// 开户费用
	
	private Long provinc_code;
	private Long city_code;
	private Long district_id;
	private double order_amount;// 订单总价
	
	private static Map mutexLocks = Collections.synchronizedMap(new HashMap());
	private List list;
	private List validateMsgList;
	private List activeDescList;
	private OrderTreeBusiRequest orderTree;
	private List<AttrFeeInfoBusiRequest> feeInfoList;
	private OrderDeliveryBusiRequest delivery;
	private List<AttrGiftInfoBusiRequest> giftInfoList;// 礼品信息列表
	private List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList;// 副卡列表
	private List<OrderSubPackageList> subProductList;//附加产品信息
	
	private OrdReceipt ordReceipt;// 回单对象
	private CrawlerConfig crawlerConfig;//爬虫配置对象
	private CrawlerProc crawlerProc;//爬虫进程对象
	private Page webPage;
	private	Map <String,String> condCodeInfo;
	private List crawlerCondType;
	private List<Map<String, String>>  condCodeList;
	private String condType;
	private String condValueCode;
	private List<Map<String, String>>  condValueList;
	
	private static INetCache cache;
	public static int NAMESPACE = 308;
	public static String CURR_ORDER_CLICK_PAGE = "CURR_ORDER_CLICK_PAGE_";
	public static String LOCK_ORDER_FLOW_DEAL = "LOCK_ORDER_FLOW_DEAL_";
	static {
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	/**
	 * 环节处理通用方法
	 * @return
	 */
	public String flowDeal(){
		long start = System.currentTimeMillis();
		// add by wui对象锁处理,避免重复处理调用
		String lock_name = "O" + order_id;
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL+order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
			String currFlowTraceId = otreq.getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
			String orderModel = otreq.getOrderExtBusiRequest().getOrder_model();//生产模式
			//设置缓存锁
			String lockOrder = (String) cache.get(NAMESPACE, lock_flow_deal);
			//boolean lock_flag = cache.add(NAMESPACE, lock_flow_deal,order_id,300);
			if(!StringUtils.isEmpty(lockOrder)){
				//已有缓存锁，有进程正在处理料箱队列
				this.json = "{result:1,message:'订单正在处理,请等待!'}";
				return this.JSON_MESSAGE;
			}
			cache.set(NAMESPACE, lock_flow_deal,order_id,1);
			logger.info("订单：" + order_id + "进入锁定信息！");
			
			// add by wui二次抓取，同步调用时验证处理
			String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
			if (!StringUtil.isEmpty(page_trace_id) && !currFlowTraceId.equals(page_trace_id)
					&& (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_trace_id))) {
				this.json = "{result:1,message:'正在处理页面为" + page_trace_id + "，实际处理环节为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。'}";
				logger.info("订单" + order_id + "正在处理页面为" + page_trace_id + "，实际处理流程为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。");
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}

			String lockMsg = ZjCommonUtils.checkLockUser(order_id,query_type);
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// 每个环节流程保存  ---爬虫订单在审核环节不需要修改数据
			String msg = this.editSave();
			if (msg != null && msg.length() > 0) {
				String validateMsg = JSONArray.toJSONString(validateMsgList);
				this.json = "{result:1,message:'" + msg + "',validateMsgList:" + validateMsg + "}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			long end2 = System.currentTimeMillis();
			try {
				Map<String,String> map = new HashMap<String, String>();
				AdminUser user = ManagerUtils.getAdminUser();
				map.put("user_id", user.getUserid());
				map.put("user_name", user.getUsername());
				map.put("currFlowTraceId", currFlowTraceId);
				if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId) 
						|| EcsOrderConsts.DIC_ORDER_NODE_H.equals(currFlowTraceId) 
						|| EcsOrderConsts.DIC_ORDER_NODE_X.equals(currFlowTraceId)
						|| (EcsOrderConsts.DIC_ORDER_NODE_F.equals(currFlowTraceId)
								&& !EcsOrderConsts.ORDER_MODEL_01.equals(orderModel))) {
					ordCollectManager.setOrderHide(order_id,"4","异步隐藏");
					//调审核方案
					String dealMsg = this.flow_deal_detail(order_id, dealType, dealDesc, map);
					JSONObject jsonObj = JSONObject.fromObject(dealMsg);
					String result = jsonObj.getString("result");
					currFlowTraceId = otreq.getOrderExtBusiRequest().getFlow_trace_id(); // 重新获取当前环节编码
					if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId) && !ConstsCore.ERROR_SUCC.equals(result)) {
						String comments = "审核失败："+jsonObj.get("message");
						IOrderFlowManager orderFlowManager = SpringContextHolder.getBean("ordFlowManager");
						orderFlowManager.addOrderHandlerLog(order_id, comments, ZjEcsOrderConsts.BATCH_PREHANDLE_EXP);
					}
					String action_url = this.getActionUrl(currFlowTraceId,otreq.getOrder_id());
					if (ConstsCore.ERROR_SUCC.equals(result)) {
						//在订单审核环节审核成功，执行PC写卡匹配队列方案，执行成功后订单转为爬虫自动写卡生产模式
						if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId) && ConstsCore.CONSTS_YES.equals(this.changeMode) && EcsOrderConsts.ORDER_MODEL_07.equals(orderModel)){
							//执行PC批量写卡匹配队列方案
							PlanRuleTreeExeReq req = new PlanRuleTreeExeReq();
							TacheFact fact = new TacheFact();
							fact.setOrder_id(order_id);
							req.setPlan_id(EcsOrderConsts.PC_BATCH_WRITE_CARD_QUEUE);
							req.setFact(fact);
							req.setDeleteLogs(true);
							PlanRuleTreeExeResp planResp = CommonDataFactory.getInstance().exePlanRuleTree(req);
							BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(planResp);
							if (StringUtils.equals(busiResp.getError_code(), ConstsCore.ERROR_SUCC)) {
								//可见性设置
								ZjCommonUtils.setOrderVisable(order_id);
								//修改生产模式
								CommonDataFactory.getInstance().updateAttrFieldValue(order_id, 
										new String[]{AttrConsts.ORDER_MODEL}, new String[]{ZjEcsOrderConsts.ORDER_MODEL_08});
								//添加日志
								IOrderFlowManager orderFlowManager = SpringContextHolder.getBean("ordFlowManager");
								orderFlowManager.addOrderHandlerLog(order_id, "审核成功，并转为爬虫自动写卡生产模式", EcsOrderConsts.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
								this.json = "{result:0,message:'操作成功，生产模式转为爬虫自动写卡',action_url:'"+action_url+"'}";
							}
							else{
								this.json = "{result:0,message:'操作成功',action_url:'"+action_url+"'}";
							}
						}
					}
					else{
						if(dealMsg.indexOf("在分配界面未查询到订单")!=-1){//总商爬虫订单分配特殊处理
							this.json = "{result:0,message:'操作成功',action_url:'"+action_url+"'}";
						}else{
							this.json = "{result:1,message:'"+jsonObj.getString("message")+"',action_url:'"+action_url+"'}";
						}
						
					}
				} else {
					//调审核方案
					flow_deal_detail(order_id, dealType, dealDesc, map);
					cache.delete(NAMESPACE, lock_flow_deal);
				}
			} catch (Exception e) {
				this.json = "{result:1,message:'操作失败,系统异常:"+e.getMessage()+"'}";
				e.printStackTrace();
			} finally {
				cache.delete(NAMESPACE, lock_flow_deal);
				ordCollectManager.cancelOrderHide(order_id);
				removeMutexLock(lock_name);
			}
		}
		return this.JSON_MESSAGE;
	}
	
	public String flow_deal_detail(String order_id,String p_dealType,String p_dealDesc,Map<String,String> map) {
		String msg = "";
		String json = "";
		try {
			
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String curr_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String currFlowTraceId = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id(); //获取正在处理的环节id
			
			String trace_id = null;
			// 预处理现场交付的订单不需要校验身份证 设置校验 状态 不真实调接口校验
			String send_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.SENDING_TYPE);
			if (EcsOrderConsts.SHIPPING_TYPE_XJ.equals(send_type)&& EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId)) {
				// 成功设置预校验成功状态
				String idCardStatus = EcsOrderConsts.ACCOUNT_VALI_1;// 身份证校验成功状态
				CommonDataFactory.getInstance().updateAttrFieldValue(this.order_id,new String[] { AttrConsts.ACCOUNT_VALI },new String[] { idCardStatus });
			}
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put("order_id", this.order_id);
			busi.setEnginePath("enterTraceRule.exec");
			busi.setOrder_id(this.order_id);
			queryParams.put("deal_from", EcsOrderConsts.DEAL_FROM_PAGE);
			queryParams.put("deal_type", dealType);
			queryParams.put("deal_desc", getDealDesc());
			queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_ALL);
			busi.setQueryParams(queryParams);

			/**
			 * 1、处理思路：调用+同步锁方法，控制按串行流程执行
			 * 2、对质检环节特殊处理，当前为质检环节、且数据库也为治检环节，则退出处理
			 */
			ZteResponse response = orderServices.execBusiComp(busi);

			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			String action_url = getActionUrl(trace_id,orderTree.getOrder_id());
			if (!ConstsCore.ERROR_SUCC.equals(response.getError_code())) {
				msg = response.getError_msg();
				json = "{result:1,message:'" + msg + "'}";
			} else {
				json = "{result:0,message:'操作成功',action_url:'"+ action_url + "'}";
				// 记录最后一次页面操作预处理、预捡货的工号信息，供开户使用
				if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(currFlowTraceId)|| EcsOrderConsts.DIC_ORDER_NODE_C.equals(currFlowTraceId)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
							new String[] { AttrConsts.PRE_LOCK_USER_ID },
							new String[] { ManagerUtils.getUserId() });
				}
				Utils.commonUnlock(order_id, curr_trace_id, trace_id); // 解锁公共方法
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String error_msg = e.getMessage();
			json = "{result:1,message:'"+error_msg+"'}";
		}
		return json;
	}
	
	// 开户环节流转--新开户
	public String flowDealOpenAcct() {
		
		// add by wui对象锁处理,避免重复处理调用
		String lock_name = "O" + order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			logger.info("订单：" + order_id + "进入锁定信息！");
			long start = System.currentTimeMillis();
			// add by wui二次抓取，同步调用时验证处理
			String page_trace_id = (String) cache.get(NAMESPACE,CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			String currFlowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
			if (!StringUtil.isEmpty(page_trace_id)
					&& (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_trace_id))
					&& !currFlowTraceId.equals(page_trace_id)) {
				this.json = "{result:1,message:'正在处理页面为" + page_trace_id + "，实际处理环节为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。',order_id:'" + order_id + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}

			String lockMsg = ZjCommonUtils.checkLockUser(order_id,query_type);
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "',order_id:'" + order_id + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(orderTree.getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!',order_id:'" + order_id + "'}";
				removeMutexLock(lock_name);
				return this.JSON_MESSAGE;
			}
			long end = System.currentTimeMillis();
			logger.info("flowDealOpenAcct==1===" + order_id + "=====" + (end - start));
			// 每个缓解流程保存
			String msg = "";
			try {
				String curr_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				String trace_id = null;
				RuleTreeExeResp ruleTreeExeResp = null;
				// 调用业务组件
				try {
					start = System.currentTimeMillis();
					TacheFact fact = new TacheFact();
					fact.setOrder_id(order_id);
					String plan_id = EcsOrderConsts.OPEN_ACCOUNT_PLAN_ID;
					String rule_id = ZjEcsOrderConsts.ORDER_MODEL_07_ZB_OPEN_ACCOUNT_DETAIL;//爬虫生产模式，进入总商开户详情页面规则ID
					ruleTreeExeResp = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);

					/**
					 * 1、处理思路：调用+同步锁方法，控制按串行流程执行
					 * 2、对质检环节特殊处理，当前为质检环节、且数据库也为治检环节，则退出处理
					 */
					//response = orderServices.execBusiComp(busi);
					end = System.currentTimeMillis();
					logger.info("flowDealOpenAcct==2===" + order_id + "=====" + (end - start));

				} finally {
					start = System.currentTimeMillis();
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
					end = System.currentTimeMillis();
					logger.info("flowDealOpenAcct==3===" + order_id + "=====" + (end - start));
				}

				start = System.currentTimeMillis();
				if (!ConstsCore.ERROR_SUCC.equals(ruleTreeExeResp.getError_code())) {
					msg = ruleTreeExeResp.getError_msg();
					this.json = "{result:1,message:'" + msg + "',order_id:'" + order_id + "'}";
				} else {
					this.json = "{result:0,message:'操作成功',order_id:'" + order_id + "'}";
					Utils.commonUnlock(order_id, curr_trace_id, trace_id); // 解锁公共方法
				}
				end = System.currentTimeMillis();
				logger.info("flowDealOpenAcct==4===" + order_id + "=====" + (end - start));
			} catch (Exception e) {
				e.printStackTrace();
				this.json = "{result:1,message:'" + e.getMessage() + "',order_id:'" + order_id + "'}";
			} finally {
				removeMutexLock(lock_name);
			}
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * 判断是否有费用明细，存在费用明细则弹出费用明细修改窗口
	 * @return
	 */
	public String hasFeeInfoCheck(){
		feeInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		if(feeInfoList!=null && feeInfoList.size()>0){
			this.json = "{result:0,message:'订单存在费用明细'}";
		}
		else{
			this.json = "{result:1,message:'订单不存在费用明细'}";
		}
		return this.JSON_MESSAGE;
	}
	
	/**
	 * ZX add 2017年1月18日 09:50:45 start
	 * @return
	 */
	public String flowDealOpenAcct_pc() {
		feeInfoList = CommonDataFactory.getInstance().getOrderTree(order_id).getFeeInfoBusiRequests();
		sort(feeInfoList, "fee_category", false);
		return "fee_info_list";
	}
	
	@SuppressWarnings("unchecked")
    public static <T> void sort(List<T> list, String fieldName, boolean asc) {
        Comparator<?> mycmp = ComparableComparator.getInstance();
        mycmp = ComparatorUtils.nullLowComparator(mycmp); // 允许null
        if (!asc) {
            mycmp = ComparatorUtils.reversedComparator(mycmp); // 逆序
        }
        Collections.sort(list, new BeanComparator(fieldName, mycmp));
    }
	
	public String flowDealOpenAcct_pc_save() {
		try{
			logger.info("修改费用信息："+feiyongmingxi_s);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(feiyongmingxi_s);
			List<Map<String, String>> list = (List) net.sf.json.JSONArray.toCollection(jsonArray, Map.class);
			
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			
			List<AttrFeeInfoBusiRequest> feeInfoBusiRequests = orderTree.getFeeInfoBusiRequests();
			
			if (feeInfoBusiRequests != null && feeInfoBusiRequests.size() > 0) {
				for (Map<String, String> map : list) {
					for(int i=0;i<feeInfoBusiRequests.size();i++){
						AttrFeeInfoBusiRequest feeInfoBusiRequest = feeInfoBusiRequests.get(i);
						if(feeInfoBusiRequest.getFee_inst_id().equals(map.get("fee_inst_id"))){
							if(!StringUtils.isEmpty(map.get("o_fee_num"))){
								feeInfoBusiRequest.setO_fee_num(Double.parseDouble(map.get("o_fee_num")));
							}
							if(!StringUtils.isEmpty(map.get("discount_fee"))){
								feeInfoBusiRequest.setDiscount_fee(Double.parseDouble(map.get("discount_fee")));
							}
							if("undefined".equals(map.get("discount_reason")) || StringUtils.isEmpty(map.get("discount_reason"))){
								feeInfoBusiRequest.setDiscount_reason(ConstsCore.NULL_VAL);
							}
							else{
								feeInfoBusiRequest.setDiscount_reason(URLDecoder.decode(map.get("discount_reason"),"UTF-8"));
							}
							if(!StringUtils.isEmpty(map.get("n_fee_num"))){
								feeInfoBusiRequest.setN_fee_num(Double.parseDouble(map.get("n_fee_num")));
							}
							feeInfoBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);// 操作
							feeInfoBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);// 脏数据处理
							feeInfoBusiRequest.store();
						}
					}
				}
			}
			this.json = "{result:0,message:'费用信息修改成功'}";
		}catch(Exception e){
			this.json = "{result:1,message:'费用信息修改失败'}";
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * ZX add 2017年1月18日 09:50:45 end
	 */
	
	public String editSave() {
		Map old_map = ecsOrdManager.getUpdate(order_id);
		long start_s = System.currentTimeMillis();
		// 校验前，先将校验值清空处理
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		ThreadContextHolder.setHttpRequest(getRequest());

		// 属性验证方法校验
		String msg = "";
		if (q_check == null || q_check.equals("")) { // WMS质检稽核环节q_check='n',不做界面环节验证
			msg = this.validte();
		} else if ("n".equals(q_check)) {// WMS质检稽核环节q_check='n',物流单号还是要校验的
			msg = this.validteLogino();
		}
		if (!StringUtil.isEmpty(msg)) {
			return msg;
		}
		msg = validteTerminalNum();
		if (!StringUtil.isEmpty(msg)) {
			return msg;
		}

		if (msg.length() == 0) {

			List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);

			int col1Count = 0;
			for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
				// 是否闪电送和物流公司字段变动时 重新匹配模式
				if (AttrConsts.SHIPPING_QUICK.equals(pageAttrInstBusiRequest.getField_name())
						|| AttrConsts.SHIPPING_COMPANY.equals(pageAttrInstBusiRequest.getField_name())) {
					col1Count++;
				}
				pageAttrInstBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				pageAttrInstBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				long start2 = System.currentTimeMillis();
				pageAttrInstBusiRequest.store(); // 属性数据入库
				long end2 = System.currentTimeMillis();
				logger.info("store:==" + (end2 - start2));
			}

			String flow_trace = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getFlow_trace_id();

			everyDealSave(flow_trace);// 不同环节的特殊处理
			// 在预处理环节 是否闪电送和物流公司字段变动时 重新匹配模式
			if (col1Count > 0 && EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace)) {
				OrderTreeBusiRequest orderTree1 = new OrderTreeBusiRequest();
				orderTree1.setOrder_id(orderTree.getOrder_id());
				orderTree1.setCol1(EcsOrderConsts.REP_EXEC_YES);
				orderTree1.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				orderTree1.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				orderTree1.store(); // 属性数据入库
			}
		}
		Map new_map = ecsOrdManager.getUpdate(order_id);
		ecsOrdManager.saveChange(old_map, new_map, order_id);
		long end_s = System.currentTimeMillis();
		logger.info("订单信息保存总耗时============================================================>"+ (end_s - start_s));
		
//		String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
//		if(ZjEcsOrderConsts.ORDER_FROM_100032.equals(orderTree.getOrderExtBusiRequest().getOrder_from()) && ZjEcsOrderConsts.DIC_ORDER_NODE_B.equals(trace_id)){//总商爬虫，订单审核环节商品修改特殊处理
//			msg = modifyZbOrderInfo(order_id);
//		}
		return msg;
	}
	
//	private String  modifyZbOrderInfo(String orderId){
//		//总商爬虫，订单审核环节商品修改特殊处理
//		String error_msg="";
//		OrderTreeBusiRequest newOrderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
//		List<OrderDeliveryBusiRequest> deliveryList = newOrderTree.getOrderDeliveryBusiRequests();
//		if(null!=deliveryList){
//			OrderDeliveryBusiRequest delivery = deliveryList.get(0);
//			
//			CrawlerUpdatePostInfoReq postInfoReq = new CrawlerUpdatePostInfoReq();
//			postInfoReq.setOrderNo(newOrderTree.getOrderExtBusiRequest().getOut_tid());
//			postInfoReq.setReceiverName(delivery.getShip_name());
//			postInfoReq.setProviceCode(delivery.getProvince_id()==null?"":delivery.getProvince_id().toString());
//			postInfoReq.setCityCode(delivery.getCity_id()==null?"":delivery.getCity_id().toString());
//			postInfoReq.setDistrictCode(delivery.getRegion_id()==null?"":delivery.getRegion_id().toString());
//			postInfoReq.setPostAddr(delivery.getShip_addr());
//			postInfoReq.setWishLgtsCode(delivery.getShipping_company().equals(EcsOrderConsts.LOGI_COMPANY_SFFYZQYF)?"1002":"1001");
//			String deliveryType =delivery.getShipping_time();
//			if(ZjEcsOrderConsts.SHIPPING_TIME_NOLIMIT.equals(deliveryType)){
//				postInfoReq.setDeliverDateType("01");//配送类型  04：延时配送，01： 不限时配送，02：只工作日送货，03只有双休日、节假日送货
//			}else if(ZjEcsOrderConsts.SHIPPING_TIME_HOLIDAY.equals(deliveryType)){
//				postInfoReq.setDeliverDateType("03");
//			}else if(ZjEcsOrderConsts.SHIPPING_TIME_WORKDAY.equals(deliveryType)){
//				postInfoReq.setDeliverDateType("02");
//			}
//			postInfoReq.setMobilePhone(delivery.getShip_tel());
//			String shipType =newOrderTree.getOrderBusiRequest().getShipping_type();
//			if(ZjEcsOrderConsts.SHIP_TYPE_KD.equals(shipType)){
//				postInfoReq.setDeliverTypeCode("01");  // 01:第三方物流 04：现场提货 05：自行配送
//				postInfoReq.setDispatchName("快递");//配送方式名称
//			}else if(ZjEcsOrderConsts.SHIP_TYPE_XCTH.equals(shipType)) {
//				postInfoReq.setDeliverTypeCode("04"); 
//			}else if(ZjEcsOrderConsts.SHIP_TYPE_ZYPS.equals(shipType)){
//				postInfoReq.setDeliverTypeCode("05"); 
//			}
//			
//			ZteClient client =ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
//			ZteResponse  resp= client.execute(postInfoReq, ZteResponse.class);
//			if(!ConstsCore.ERROR_SUCC.equals(resp.getError_code())){
//				logger.info("=====OrderCrawlerAction modifyZbOrderInfo 修改总商配送信息出错【orderId："+newOrderTree.getOrderExtBusiRequest().getOrder_num()+",errorMsg:"+resp.getError_msg()+"】");
//				return resp.getError_msg();
//			}
//			
//			
//			
//			CrawlerUpdateGoodsInfoReq goodsInfoReq = new CrawlerUpdateGoodsInfoReq();
//			goodsInfoReq.setOrderNo(newOrderTree.getOrderExtBusiRequest().getOut_tid());
//			goodsInfoReq.setCustName(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.PHONE_OWNER_NAME));
//			goodsInfoReq.setCertAddr(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_ADDRESS));
//			goodsInfoReq.setPsptNo(CommonDataFactory.getInstance().getAttrFieldValue(orderId, AttrConsts.CERT_CARD_NUM));
//			List<OrderItemProdBusiRequest> itemProdList = newOrderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
//			if(null!=itemProdList){
//				String certType =itemProdList.get(0).getOrderItemProdExtBusiRequest().getCerti_type();
//				String firstMonthFree = itemProdList.get(0).getOrderItemProdExtBusiRequest().getFirst_payment();
//				if(ZjEcsOrderConsts.CERTI_TYPE_SFZ15.equals(certType)){
//					goodsInfoReq.setPsptType("01");
//				}else if(ZjEcsOrderConsts.CERTI_TYPE_SFZ18.equals(certType)){
//					goodsInfoReq.setPsptType("02");
//				}
//				
//				if(ZjEcsOrderConsts.FIRST_FEE_TYPE_ALLM.equals(firstMonthFree)){
//					goodsInfoReq.setFirstMonthFee("A000011V000001");
//					goodsInfoReq.setFirstMonthFeeName("全月");
//				}else if(ZjEcsOrderConsts.FIRST_FEE_TYPE_HALF.equals(firstMonthFree)){
//					goodsInfoReq.setFirstMonthFee("A000011V000002");
//					goodsInfoReq.setFirstMonthFeeName("半月");
//				}else if(ZjEcsOrderConsts.FIRST_FEE_TYPE_OTHER.equals(firstMonthFree)){
//					goodsInfoReq.setFirstMonthFee("A000011V000003");
//					goodsInfoReq.setFirstMonthFeeName("套餐包外");
//				}
//				
//			}
//			ZteResponse  goodsInfoResp= client.execute(goodsInfoReq, ZteResponse.class);
//			if(!ConstsCore.ERROR_SUCC.equals(goodsInfoResp.getError_code())){
//
//				logger.info("=====OrderCrawlerAction modifyZbOrderInfo 修改总商商品清单出错【orderId："+newOrderTree.getOrderExtBusiRequest().getOrder_num()+",errorMsg:"+resp.getError_msg()+"】");
//				return goodsInfoResp.getError_msg();
//			}
//		}
//		
//		return error_msg;
//	}
	public String validte() {
		String msg = "";
		ThreadContextHolder.setHttpRequest(getRequest());// 线程变量
		// pageLoad属性验证方法校验
		validateMsgList = new ArrayList();
		long start = System.currentTimeMillis();
		List<AttrInstLoadResp> attrInstLoadResps = AttrBusiInstTools.validateOrderAttrInstsForPage(order_id,ConstsCore.ATTR_ACTION_LOAD_AND_UPDATE);
		long end = System.currentTimeMillis();
		// logger.info("=============>属性解析耗时:"+(end-start));
		if (attrInstLoadResps.size() > 0) {
			msg += "校验失败,请更新页面高亮部分。";

			for (AttrInstLoadResp attrInstLoadResp : attrInstLoadResps) {
				Map map = new HashMap();
				map.put("field_name", attrInstLoadResp.getField_name());
				map.put("check_msg", attrInstLoadResp.getCheck_message());
				this.validateMsgList.add(map);
				msg += attrInstLoadResp.getCheck_message();
			}
		}
		String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(this.order_id).getOrderExtBusiRequest().getFlow_trace_id();

		if ("ordList".equals(this.btnType)) {// 如果是点订单列表上的按钮直接处理 就需要从订单树上取

			OrderDeliveryBusiRequest deliverBusiReq = CommonDataFactory
					.getInstance().getOrderTree(this.order_id).getOrderDeliveryBusiRequests().get(0);
			if (deliverBusiReq != null) {
				this.provinc_code = deliverBusiReq.getProvince_id();
				this.city_code = deliverBusiReq.getCity_id();
				this.district_id = deliverBusiReq.getRegion_id();
			}
			this.shipping_company = CommonDataFactory.getInstance()
					.getAttrFieldValue(order_id, AttrConsts.SHIPPING_COMPANY);
		}
		if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)) {
			String ss = AttrUtils.isShowShipVisiableBySendType(CommonDataFactory.getInstance().getAttrFieldValue(order_id,AttrConsts.SENDING_TYPE));
			if ((EcsOrderConsts.IS_DEFAULT_VALUE).equals(ss)) {

				if (provinc_code == null) {
					Map map = new HashMap();
					map.put("field_name", "provinc_code");
					map.put("check_msg", "收货省份不能为空。");
					this.validateMsgList.add(map);
					msg += "收货省份不能为空。";
				}
				if (this.city_code == null) {
					Map map = new HashMap();
					map.put("field_name", "city_code");
					map.put("check_msg", "收货地市不能为空。");
					this.validateMsgList.add(map);
					msg += "收货地市不能为空。";
				}
				if (this.district_id == null) {
					Map map = new HashMap();
					map.put("field_name", "district_id");
					map.put("check_msg", "收货区县不能为空。");
					this.validateMsgList.add(map);
					msg += "收货区县不能为空。";
				}
			}
			String order_model = CommonDataFactory.getInstance()
					.getAttrFieldValue(this.order_id, AttrConsts.ORDER_MODEL);
			if ((StringUtil.isEmpty(this.shipping_company) || ConstsCore.NULL_VAL.equals(this.shipping_company))
					&& (EcsOrderConsts.DIC_ORDER_NODE_F.equals(flow_trace_id) || (EcsOrderConsts.ORDER_MODEL_01
							.equals(order_model) && EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)))) {
				Map map = new HashMap();
				map.put("field_name", "shipping_company");
				map.put("check_msg", "物流公司名称不能为空。");
				this.validateMsgList.add(map);
				msg += "物流公司名称不能为空。";
			}

		}
		return msg;
	}

	public String validteTerminalNum() {// 终端串号校验
		String msg = "";
		String flow_trace_id = CommonDataFactory.getInstance()
				.getOrderTree(this.order_id).getOrderExtBusiRequest()
				.getFlow_trace_id();
		if (EcsOrderConsts.DIC_ORDER_NODE_C.equals(flow_trace_id)) {
			String terminal_num = "";
			boolean is_terminal_num_dirty = false;
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
					.getOrderTree(order_id);// 获取脏数据
			List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools
					.getPageDirtyOrderAttrInst(order_id, orderTree);
			for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
				if (AttrConsts.TERMINAL_NUM.equals(pageAttrInstBusiRequest
						.getField_name())) {
					terminal_num = pageAttrInstBusiRequest.getField_value();// 获取页面新的物流单号(最理想是直接获取页面传过来的值)
					is_terminal_num_dirty = true;
					break;
				}
			}
			// if(!is_terminal_num_dirty){//若终端串号未修改，认为单号没问题
			// terminal_num =
			// CommonDataFactory.getInstance().getAttrFieldValue(this.order_id,
			// AttrConsts.TERMINAL_NUM);
			// }
			Map map = new HashMap();
			validateMsgList = new ArrayList();
			/*
			 * if(org.apache.commons.lang3.StringUtils.isBlank(logi_no)){//物流单号不能为空
			 * 。这一点在页面已经校验过，但页面没有过滤纯空格的字符串。纯空格不是脏数据，这里会导致页面提示操作成功，但实际不会保存 // msg
			 * += "校验失败,请更新页面高亮部分。"; map.put("field_name",AttrConsts.LOGI_NO);
			 * map.put("check_msg", "物流单号不能为空。"); this.validateMsgList.add(map);
			 * msg += "物流单号不能为空。"; return msg; }
			 */
			if (!StringUtil.isEmpty(terminal_num)) {
				boolean flag = ecsOrdManager.terminalNumCheck(order_id,
						terminal_num);
				if (!flag) {// 页面修改的物流单号已经被其他订单使用过
					// msg += "校验失败,请更新页面高亮部分。";
					map.put("field_name", AttrConsts.TERMINAL_NUM);
					map.put("check_msg", "校验串号失败");
					this.validateMsgList.add(map);
					msg += "校验串号失败" + terminal_num + "。";
					return msg;
				}
			}
		}
		return msg;
	}

	public String validteLogino() {// 实物稽核页面保存校验物流单号
		String msg = "";
		String logi_no = "";
		boolean is_logiNo_dirty = false;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);// 获取脏数据
		List<AttrInstBusiRequest> pageAttrInstBusiRequests = AttrBusiInstTools.getPageDirtyOrderAttrInst(order_id, orderTree);
		for (AttrInstBusiRequest pageAttrInstBusiRequest : pageAttrInstBusiRequests) {
			if (AttrConsts.LOGI_NO.equals(pageAttrInstBusiRequest.getField_name())) {
				logi_no = pageAttrInstBusiRequest.getField_value();// 获取页面新的物流单号(最理想是直接获取页面传过来的值)
				is_logiNo_dirty = true;
				break;
			}
		}
		if (!is_logiNo_dirty) {// 若logi_no物流单号未修改，认为单号没问题
			logi_no = CommonDataFactory.getInstance().getAttrFieldValue(this.order_id, AttrConsts.LOGI_NO);
		}
		Map map = new HashMap();
		validateMsgList = new ArrayList();
		if (org.apache.commons.lang3.StringUtils.isBlank(logi_no)) {// 物流单号不能为空。这一点在页面已经校验过，但页面没有过滤纯空格的字符串。纯空格不是脏数据，这里会导致页面提示操作成功，但实际不会保存
		// msg += "校验失败,请更新页面高亮部分。";
			map.put("field_name", AttrConsts.LOGI_NO);
			map.put("check_msg", "物流单号不能为空。");
			this.validateMsgList.add(map);
			msg += "物流单号不能为空。";
			return msg;
		}
		String count = orderExtManager.getOrderCountByLogino(logi_no, order_id);
		if (!StringUtils.equals("0", count)) {// 页面修改的物流单号已经被其他订单使用过
		// msg += "校验失败,请更新页面高亮部分。";
			map.put("field_name", AttrConsts.LOGI_NO);
			map.put("check_msg", "物流单号" + logi_no + "已被使用过。");
			this.validateMsgList.add(map);
			msg += "物流单号" + logi_no + "已被使用过。";
			return msg;
		}
		return msg;
	}
	
	/**
	 * 不同环节的保存方法
	 * @param flow_trace 环节编码
	 * @return
	 */
	public String everyDealSave(String flow_trace) {
		if (ConstsCore.TRACE_B.equals(flow_trace) || EcsOrdManager.QUERY_TYPE_YCL.equals(this.query_type)) {
			this.preDealSave();
		} else if (ConstsCore.TRACE_C.equals(flow_trace)) {
			String order_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_TYPE);
			String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
			// 当订单类型为多商品类型时，当来源为华盛B2C时
			if (EcsOrderConsts.ORDER_TYPE_09.equals(order_type) || order_from.equals(EcsOrderConsts.ORDER_FROM_10061)) { 
				this.savePrePickMore();
			}
		} else if (ConstsCore.TRACE_H.equals(flow_trace)) {
			//shipping();
		} else if (ConstsCore.TRACE_F.equals(flow_trace)) {
			quality_audit();
		} else if (ConstsCore.TRACE_J.equals(flow_trace)) {
			ord_receipt_new();
		} else if (ConstsCore.TRACE_L.equals(flow_trace)) {
			//orderArchive();
		}
		return "";
	}
	
	public String getActionUrl(String trace_id, String order_id) {
		String action_url = RuleFlowUtil.getOrderUrl(order_id, trace_id);
		if (action_url != null && action_url.indexOf("?") != -1) {
			action_url += "&order_id=" + order_id;
		} else {
			action_url += "?order_id=" + order_id;
		}
		if (!StringUtil.isEmpty(is_order_exp) && "1".equals(is_order_exp)) {
			action_url += "&is_order_exp=" + is_order_exp;
		}
		if (!StringUtil.isEmpty(query_type) ) {
			action_url += "&query_type=" + query_type;
		}
		return action_url;
	}
	
	public String preDealSave() {// 预处理保存

		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		// 收货信息|物流信息
		if (orderTree.getOrderDeliveryBusiRequests().size() > 0) {
			OrderDeliveryBusiRequest deliverBusiReq = null;
			List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree
					.getOrderDeliveryBusiRequests();
			for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
				if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
					deliverBusiReq = deli;
					break;
				}
			}
			if (null == deliverBusiReq) {// 没有正常发货的记录(整个业务过程应该控制到有且仅有一条正常发货记录)
				deliverBusiReq = new OrderDeliveryBusiRequest();
				try {
					BeanUtils.copyProperties(deliverBusiReq,
							orderDeliveryBusiRequest.get(0));// 从其他发货记录copy
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deliverBusiReq.setDelivery_id(iOrderSupplyManager
						.getSequences("S_ES_DELIVERY"));
				deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
				deliverBusiReq
						.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
				deliverBusiReq.setLogi_no("");// 物流单号
				deliverBusiReq.setReceipt_no("");// 回单号
				deliverBusiReq.setSign_status("");// 签收状态
				deliverBusiReq.setReceipt_status("");// 回单签收状态
				deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
				deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				deliverBusiReq.store();
			}
			deliverBusiReq.setProvince_id(this.provinc_code);
			deliverBusiReq.setCity_id(this.city_code);
			deliverBusiReq.setRegion_id(this.district_id);
			if (null != this.district_id) {
				deliverBusiReq.setRegion(CommonDataFactory.getInstance()
						.getLocalName(this.district_id.toString()));
			}
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
			deliverBusiReq.store(); // 属性数据入库
		} else {
			OrderDeliveryBusiRequest deliverBusiReq = new OrderDeliveryBusiRequest();
			deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
			deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
			deliverBusiReq.setProvince_id(this.provinc_code);
			deliverBusiReq.setCity_id(this.city_code);
			deliverBusiReq.setRegion_id(this.district_id);
			if (null != this.district_id) {
				deliverBusiReq.setRegion(CommonDataFactory.getInstance()
						.getLocalName(this.district_id.toString()));
			}
			deliverBusiReq.setOrder_id(order_id);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.store();
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			orderTree.getOrderDeliveryBusiRequests().add(deliverBusiReq);
			try {
				logsServices.cacheOrderTree(orderTree);
			} catch (ApiBusiException e) {
				e.printStackTrace();
			}
		}

		// 保存订单总价

		orderTree.getOrderBusiRequest().setOrder_amount(this.order_amount);
		orderTree.getOrderBusiRequest().setDb_action(
				ConstsCore.DB_ACTION_UPDATE);
		orderTree.getOrderBusiRequest().setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTree.getOrderBusiRequest().store();
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				new String[] { "order_amount", "shipping_company" },
				new String[] { order_amount + "", this.shipping_company });

		// 保存异常类型

		// if(EcsOrderConsts.)
		String quick_ship_company_code = "";
		// 顺丰
		if (EcsOrderConsts.LOGI_COMPANY_SF0001.equals(shipping_company)
				|| EcsOrderConsts.LOGI_COMPANY_SF0002.equals(shipping_company)
				|| EcsOrderConsts.LOGI_COMPANY_SF0003.equals(shipping_company)) {
			quick_ship_company_code = EcsOrderConsts.SDS_COMP_SF;
		}
		// 南都
		if (EcsOrderConsts.LOGI_COMPANY_ND0001.equals(shipping_company)
				|| EcsOrderConsts.LOGI_COMPANY_ND0002.equals(shipping_company)) {
			quick_ship_company_code = EcsOrderConsts.SDS_COMP_ND;
		}
		orderTree.getOrderExtBusiRequest().setQuick_ship_company_code(
				quick_ship_company_code);
		orderTree.getOrderExtBusiRequest().setAbnormal_type(abnormal_type);
		orderTree.getOrderExtBusiRequest().setDb_action(
				ConstsCore.DB_ACTION_UPDATE);
		orderTree.getOrderExtBusiRequest().setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderTree.getOrderExtBusiRequest().store();

		String member_id = orderTree.getOrderBusiRequest().getMember_id();
		if (member_id != null && !"".equals(member_id)) {
			Map map = new HashMap();
			map.put("address", address);
			map.put("tel", tel);
			map.put("mobile", mobile);
			map.put("member_id", member_id);
			this.ordFlowManager.updateBuyInfoByMemberId(new String[] { mobile,
					tel, address, member_id });
		}
		return "";
	}
	
	/* 多商品预拣货保存 */
	public String savePrePickMore() {
		String msg = "";
		try {
			if (!StringUtil.isEmpty(resourceCodeStr)) {
				Map map = new HashMap();
				String[] resourceCodeArr = this.resourceCodeStr.split(",");
				String[] resourceItemsArr = this.resourceItemsStr.split(",");
				for (int j = 0; j < resourceCodeArr.length; j++) {
					String resourceCode = resourceCodeArr[j];
					if (!StringUtil.isEmpty(resourceCode)) {
						String items_id = resourceItemsArr[j];
						map.put(items_id, resourceCode);
					}
				}
				List<OrderItemExtvtlBusiRequest> ordItemExtList = CommonDataFactory
						.getInstance().getOrderTree(order_id)
						.getOrderItemExtvtlBusiRequests();
				for (int i = 0; i < ordItemExtList.size(); i++) {
					OrderItemExtvtlBusiRequest orderItemExt = ordItemExtList
							.get(i);
					String items_id = orderItemExt.getItems_id();
					String new_resource_code = (String) map.get(items_id);
					if (!StringUtil.isEmpty(new_resource_code)) {
						orderItemExt.setResources_code(new_resource_code);
						orderItemExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						orderItemExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderItemExt.store();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		return msg;
	}
	
	/**
	 * 质检稽核界面保存
	 */
	public void quality_audit() {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
				.getOrderTree(order_id);

		// 收货信息|物流信息
		OrderDeliveryBusiRequest deliverBusiReq = null;
		List<OrderDeliveryBusiRequest> orderDeliveryBusiRequest = orderTree
				.getOrderDeliveryBusiRequests();
		if (orderDeliveryBusiRequest != null
				&& orderDeliveryBusiRequest.size() > 0) {
			for (OrderDeliveryBusiRequest deli : orderDeliveryBusiRequest) {
				if (EcsOrderConsts.LOGIS_NORMAL.equals(deli.getDelivery_type())) {
					deliverBusiReq = deli;
					break;
				}
			}
			if (null == deliverBusiReq) {// 没有正常发货的记录(整个业务过程应该控制到有且仅有一条正常发货记录)
				deliverBusiReq = new OrderDeliveryBusiRequest();
				try {
					BeanUtils.copyProperties(deliverBusiReq,
							orderDeliveryBusiRequest.get(0));// 从其他发货记录copy
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deliverBusiReq.setDelivery_id(iOrderSupplyManager
						.getSequences("S_ES_DELIVERY"));
				deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
				deliverBusiReq
						.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
				deliverBusiReq.setLogi_no("");// 物流单号
				deliverBusiReq.setReceipt_no("");// 回单号
				deliverBusiReq.setSign_status("");// 签收状态
				deliverBusiReq.setReceipt_status("");// 回单签收状态
				deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
				deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				deliverBusiReq.store();
			}
		} else {
			deliverBusiReq = new OrderDeliveryBusiRequest();
			deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL);// 创建一条正常发货的记录
			deliverBusiReq.setShip_status(OrderStatus.DELIVERY_SHIP_SATUS_NOT);// 未发货、未打印
			deliverBusiReq.setOrder_id(order_id);
			deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_INSERT);
			deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
			deliverBusiReq.store();
			orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			orderTree.getOrderDeliveryBusiRequests().add(deliverBusiReq);
			try {
				logsServices.cacheOrderTree(orderTree);
			} catch (ApiBusiException e) {
				e.printStackTrace();
			}
		}
		deliverBusiReq.setProvince_id(provinc_code);
		deliverBusiReq.setCity_id(city_code);
		deliverBusiReq.setRegion_id(district_id);
		deliverBusiReq.setProtect_price(protect_free != null ? Double
				.parseDouble(protect_free) : 0.0);
		deliverBusiReq.setLogi_receiver_phone(logi_receiver_phone);
		deliverBusiReq.setLogi_receiver(logi_receiver);
		if ("SF-FYZQYF".equals(shipping_company)
				|| "SF0001".equals(shipping_company)
				|| "SF0002".equals(shipping_company)
				|| "SF0003".equals(shipping_company)) {
			deliverBusiReq.setNeed_receipt(need_receipt);
		}
		deliverBusiReq.setDelivery_type(EcsOrderConsts.LOGIS_NORMAL); // 正常单（ZX
																		// add
																		// 2014-12-26
																		// 12:12:12）
		deliverBusiReq.setLogi_no(CommonDataFactory.getInstance()
				.getAttrFieldValue(order_id, AttrConsts.LOGI_NO));
		// orderTree.getOrderDeliveryBusiRequests().set(0, deliverBus、iReq);
		deliverBusiReq.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		deliverBusiReq.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		deliverBusiReq.store(); // 属性数据入库
		String shipping_company_name = this.ordFlowManager
				.getCompanyNameByCode(this.shipping_company);
		String[] field_name = new String[] { "prod_audit_status",
				"shipping_company", "carry_person", "carry_person_mobile",
				"shipping_company_name" };
		String[] field_value = new String[] { prod_audit_status,
				shipping_company, logi_receiver, logi_receiver_phone,
				shipping_company_name };
		CommonDataFactory.getInstance().updateAttrFieldValue(order_id,
				field_name, field_value);

	}
	
	/**
	 * 回单（浙江联通资料归档环节保存）
	 */
	public void ord_receipt_new() {
		// 1、保存资料信息到数据库
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance()
				.getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree
				.getOrderExtBusiRequest();

		orderExtBusiRequest.setArchive_num(ordReceipt.getArchive_num());
		orderExtBusiRequest.setNet_certi(Integer.valueOf(ordReceipt
				.getNet_certi()));
		orderExtBusiRequest.setAccept_agree(Integer.valueOf(ordReceipt
				.getAccept_agree()));
		orderExtBusiRequest.setLiang_agree(Integer.valueOf(ordReceipt
				.getLiang_agree()));

		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.store();

		OrderBusiRequest orderBusiRequest = orderTree.getOrderBusiRequest();
		orderBusiRequest.setStatus(ZjEcsOrderConsts.DIC_ORDER_STATUS_10);// 交易成功
		orderBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderBusiRequest.store();

	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public String saveICCID() {
		try {
			String iccid1 = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ICCID);
			String bssorderid = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSSORDERID);
			String is_aop = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.IS_AOP);
			if (!StringUtils.isEmpty(bssorderid) && "2".equals(is_aop)) {
				this.json = "{result:1,message:'该订单已提交成功，不允许换卡，原卡号" + iccid1 + "'}";
				return this.JSON_MESSAGE;
			}
			String flow_trace_id = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest()
					.getFlow_trace_id();
			if (!StringUtils.isEmpty(iccid1) && !iccid1.equals(ICCID) && "2".equals(is_aop)
					&& "D".equals(flow_trace_id)) {
				String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);
				ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
				String upiccid_order_from = cacheUtil.getConfigInfo("UPICCID_ORDER_FROM");
				if (upiccid_order_from.contains(order_from)) {
					Map<String, Object> fields = new HashMap<String, Object>();
					Map<String, Object> where = new HashMap<String, Object>();
					fields.put("exe_result", "1");
					where.put("rule_id", cacheUtil.getConfigInfo("UPICCID_" + order_from));
					where.put("obj_id", order_id);
					where.put("exe_result", "0");
					IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
					baseDaoSupport.update("es_rule_exe_log", fields, where);
				}
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { AttrConsts.ICCID },
					new String[] { ICCID });
			this.json = "{result:0,message:'ICCID保存成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:1,message:'ICCID保存失败'}";
		}
		return this.JSON_MESSAGE;
	}
	
	public String activeStatusPass(){
		String lock_name = "O" + order_id;
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL+order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			//设置缓存锁
			String lockOrder = (String) cache.get(NAMESPACE, lock_flow_deal);
			//boolean lock_flag = cache.add(NAMESPACE, lock_flow_deal,order_id,300);
			if(!StringUtils.isEmpty(lockOrder)){
				//已有缓存锁，有进程正在处理料箱队列
				this.json = "{result:1,message:'订单正在处理,请等待!'}";
				return this.JSON_MESSAGE;
			}
			cache.set(NAMESPACE, lock_flow_deal,order_id,1);
			logger.info("订单：" + order_id + "进入锁定信息！");
			// add by wui二次抓取，同步调用时验证处理
			String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
			String currFlowTraceId = otreq.getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
			if (!StringUtil.isEmpty(page_trace_id)
					&& (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_trace_id))
					&& !currFlowTraceId.equals(page_trace_id)) {
				this.json = "{result:1,message:'正在处理页面为" + page_trace_id + "，实际处理环节为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。'}";
				logger.info("订单" + order_id + "正在处理页面为" + page_trace_id + "，实际处理流程为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。");
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}

			String lockMsg = ZjCommonUtils.checkLockUser(order_id,query_type);
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ACTIVE_FLAG}, new String[]{EcsOrderConsts.LATER_ACTIVE_STATUS_3});
			TacheFact fact = new TacheFact();
			fact.setOrder_id(order_id);
			String plan_id = EcsOrderConsts.ORDER_ARCHIVE_PLAN;
			String rule_id = EcsOrderConsts.CRAWLER_ORDER_ARCHIVE_RULE;
			RuleTreeExeResp ruleTreeExeResp = RuleFlowUtil.executeRuleTree(plan_id, rule_id, fact,true,true,EcsOrderConsts.DEAL_FROM_PAGE);
			if(ConstsCore.ERROR_SUCC.equals(ruleTreeExeResp.getError_code())){
				this.json = "{result:0,message:'操作成功'}";
			}
			else{
				this.json = "{result:1,message:'"+ruleTreeExeResp.getError_msg()+"'}";
			}
		}
		
		return this.JSON_MESSAGE;
	}
	
	public String getActiveDesc(){
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		activeDescList = dcPublicCache.getList("ACTIVE_NOT_PASS_DESC");
		return "active_not_pass";
	}
	
	public String activeStatusNotPass(){
		String lock_name = "O" + order_id;
		final String lock_flow_deal = LOCK_ORDER_FLOW_DEAL+order_id;
		Map lock = getMutexLock(lock_name);
		synchronized (lock) {
			//设置缓存锁
			String lockOrder = (String) cache.get(NAMESPACE, lock_flow_deal);
			//boolean lock_flag = cache.add(NAMESPACE, lock_flow_deal,order_id,300);
			if(!StringUtils.isEmpty(lockOrder)){
				//已有缓存锁，有进程正在处理料箱队列
				this.json = "{result:1,message:'订单正在处理,请等待!'}";
				return this.JSON_MESSAGE;
			}
			cache.set(NAMESPACE, lock_flow_deal,order_id,1);
			logger.info("订单：" + order_id + "进入锁定信息！");
			// add by wui二次抓取，同步调用时验证处理
			String page_trace_id = (String) cache.get(NAMESPACE, CURR_ORDER_CLICK_PAGE + order_id); // 获取进入页面时的环节id
			OrderTreeBusiRequest otreq = CommonDataFactory.getInstance().getOrderTree(order_id);
			String currFlowTraceId = otreq.getOrderExtBusiRequest().getFlow_trace_id(); // 获取正在处理的环节id
			if (!StringUtil.isEmpty(page_trace_id)
					&& (EcsOrderConsts.DIC_ORDER_NODE_F.equals(page_trace_id) || EcsOrderConsts.DIC_ORDER_NODE_B.equals(page_trace_id))
					&& !currFlowTraceId.equals(page_trace_id)) {
				this.json = "{result:1,message:'正在处理页面为" + page_trace_id + "，实际处理环节为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。'}";
				logger.info("订单" + order_id + "正在处理页面为" + page_trace_id + "，实际处理流程为" + currFlowTraceId + "与实际不一致，请确认是否重复执行。");
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}

			String lockMsg = ZjCommonUtils.checkLockUser(order_id,query_type);
			if (!StringUtils.isEmpty(lockMsg)) {
				this.json = "{result:1,message:'" + lockMsg + "'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			// 退单状态不允许流转20160516
			if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest().getRefund_deal_type())) {
				this.json = "{result:1,message:'该订单已退单或退单申请!'}";
				removeMutexLock(lock_name);
				cache.delete(NAMESPACE, lock_flow_deal);
				return this.JSON_MESSAGE;
			}
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ACTIVE_FLAG,AttrConsts.ACTIVE_FAIL_TYPE}, new String[]{EcsOrderConsts.LATER_ACTIVE_STATUS_1,this.active_fail_type});
			this.json = "{result:0,message:'操作成功'}";
		}
		return this.JSON_MESSAGE;
	}
	/**
	 * 检查是否当前锁定用户
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-11
	 * @return
	 */
	public String checkLockUser() {
		String msg = "";
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest myOrderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = myOrderTree.getOrderExtBusiRequest();
		List<OrderLockBusiRequest> orderLockRequest = myOrderTree.getOrderLockBusiRequests();// 某些单子会出现锁定状态为null的情况
		String currTacheCode = orderExtBusiRequest.getFlow_trace_id();
		OrderLockBusiRequest orderLockBusiRequest = null;

		if (orderLockRequest == null) {
			return msg = "订单锁定状态请求为null，异常";
		} else {
			for (int i = 0; i < orderLockRequest.size(); i++) {
				if (!"bss_parallel".equals(query_type)) {
					if (orderExtBusiRequest.getFlow_trace_id().equals(orderLockRequest.get(i).getTache_code())) {
						orderLockBusiRequest = orderLockRequest.get(i);
					}
				} else {
					// 并行业务办理环节权限处理
					if ("Y2".equals(orderLockRequest.get(i).getTache_code())) {
						orderLockBusiRequest = orderLockRequest.get(i);
						currTacheCode = "Y2";
					}
				}
			}
		}
		if (orderLockBusiRequest == null || ZjEcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
			msg = "请先锁定订单";
		} else if (!user.getUserid().equals(orderLockBusiRequest.getLock_user_id())) {
			msg = "订单已经被其他用户锁定,不能进行操作";
		} else if (ManagerUtils.getAdminUser().getFounder() != 1
				&& (StringUtils.isEmpty(ManagerUtils.getAdminUser().getTacheAuth()) 
						|| !ManagerUtils.getAdminUser().getTacheAuth().contains(currTacheCode))) {
			msg = "您没有订单当前环节的操作权限";
		}
		return msg;
	}
	
	public String flowSave() {
		String lockMsg = checkLockUser();
		if (!StringUtils.isEmpty(lockMsg)) {
			this.json = "{result:1,message:'" + lockMsg + "'}";
			return this.JSON_MESSAGE;
		}
		IOrderFlowManager orderFlowManager = SpringContextHolder.getBean("ordFlowManager");
		orderFlowManager.addOrderHandlerLog(order_id, this.dealDesc, EcsOrderConsts.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE);
		// WMS质检稽核，不做环节界面验证
		String msg = editSave();
		if (msg != null && msg.length() > 0) {// WMS质检稽核，不做环节界面验证
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:"+ validateMsg + ",order_id:'" + order_id + "'}";
			return this.JSON_MESSAGE;
		} else {
			String action_url = "";
			if (EcsOrdManager.QUERY_TYPE_YCL.equals(this.query_type)) {
				action_url = ZjEcsOrderConsts.REPLENISH_URL + "?order_id="+ order_id + "&d_type=" + d_type + "&query_type="+ query_type;
			} else {
				orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				String trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
				DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
				List<Map> n_list = dcPublicCache.getList(ZjEcsOrderConsts.DIC_ORDER_NODE);
				action_url = this.getActionUrl(trace_id, orderTree.getOrder_id());
			}
			this.json = "{result:0,message:'操作成功',action_url:'" + action_url+ "'}";
		}
		return this.JSON_MESSAGE;
	}	
	
	public String orderRefundDetail() {
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		if (orderTree == null) { // 如果确认退单操作后进入该页面,订单可能已经归档
			orderTree = CommonDataFactory.getInstance().getOrderTreeHis(order_id);
		}
		List<OrderDeliveryBusiRequest> list = orderTree.getOrderDeliveryBusiRequests();
		if (list != null && list.size() > 0) {
			delivery = list.get(0);
		}
		this.giftInfoList = orderTree.getGiftInfoBusiRequests();
		// 获取副卡信息
		this.setOrderPhoneInfoFukaList(orderTree.getOrderPhoneInfoFukaBusiRequests());
		// 获取价格信息
		Map priceMap = ZjCommonUtils.getPrice(order_id);
		deposit_price = Const.getStrValue(priceMap, "deposit_price");
		openAcc_price = Const.getStrValue(priceMap, "openAcc_price");
		num_price = Const.getStrValue(priceMap, "num_price");
		good_price_system = Const.getStrValue(priceMap, "good_price_system");
		// 附加产品信息
		this.setSubProductList(CommonDataFactory.getInstance().getSubProdPackList(orderTree));
		if ("cfm".equals(d_type)) {
			return "orderRefundAudit";
		} else {
			return "orderRefundApply";
		}
	}
	
	public String orderRefundApply() {
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		if (ZjEcsOrderConsts.REFUND_STATUS_04.equals(orderExtBusiRequest.getRefund_status())) {
			json="{status:1,msg:'此单已经退款。'}";
			return JSON_MESSAGE;
		}
		if(!ZjEcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())){
			json="{status:1,msg:'请先确认退单。'}";
			return JSON_MESSAGE;
		}

		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		
		//执行爬虫退款方案,执行之前，清除上一次的记录
		String plan_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ORDER_REFUND_PLAN, "CRAWLER_REFUND");
		PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(plan_id, 0, fact, true, ZjEcsOrderConsts.DEAL_FROM_PAGE, "", "");
		if(resp!=null && "0".equals(resp.getError_code())){
			this.json="{status:0,msg:'成功'}";
			
		}else{
			String msg = resp==null?"":resp.getError_msg();
			this.json="{status:1,msg:'失败["+msg+"]'}";
		}	
		return JSON_MESSAGE;
	}
	
	public String doOrderRefund(){
		OrderTreeBusiRequest orderTree= CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		if(!ZjEcsOrderConsts.REFUND_STATUS_01.equals(orderExtBusiRequest.getRefund_status())){
			json="{status:1,msg:'请先确认退单。'}";
			return JSON_MESSAGE;
		}
		String bssRefundStatus = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.BSS_REFUND_STATUS);
		if (ZjEcsOrderConsts.BSS_REFUND_STATUS_OFFLINE_SUCC.equals(bssRefundStatus)||ZjEcsOrderConsts.BSS_REFUND_STATUS_ONLINE_SUCC.equals(bssRefundStatus)) {
			json="{status:1,msg:'退款已完成。'}";
			return JSON_MESSAGE;
		}
		if (!ZjEcsOrderConsts.BSS_REFUND_STATUS_5.equals(bssRefundStatus)) {
			json="{status:1,msg:'请先申请退款。'}";
			return JSON_MESSAGE;
		}
		
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderTree.getOrder_id());
		
		//执行爬虫退款方案,执行之前，清除上一次的记录
		String plan_id = CommonDataFactory.getInstance().getOtherDictVodeValue(StypeConsts.ORDER_REFUND_PLAN, "CRAWLER_REFUND");
		PlanRuleTreeExeResp resp = RuleFlowUtil.executePlanRuleTree(plan_id, -1, fact, false, ZjEcsOrderConsts.DEAL_FROM_PAGE, "", "");
		if(resp!=null && "0".equals(resp.getError_code())){
			//写日志
			OrderHandleLogsReq req = new OrderHandleLogsReq();
			String flow_id = orderTree.getOrderExtBusiRequest().getFlow_id();
			String flowTraceId = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			req.setOrder_id(order_id);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			req.setComments(dealDesc);
			req.setHandler_type(Const.ORDER_HANDLER_TYPE_REFUND);
			req.setType_code(ZjEcsOrderConsts.REFUND_STATUS_04);
			req.setOp_id(ManagerUtils.getUserId());
			req.setOp_name(ManagerUtils.getAdminUser().getUsername());
			this.ordFlowManager.insertOrderHandLog(req);
			
			this.json="{status:0,msg:'退款成功'}";	
		}
		else{
			this.json="{status:1,msg:'退款失败["+resp.getError_msg()+"]'}";
		}
		return JSON_MESSAGE;
	}
	
	public String batchOrderRefund(){
		String[] orderIdArr = orders.split(",");
		int succCount = 0;
		String batchMsg = null;
		for(String _order_id:orderIdArr){
			this.order_id = _order_id;
			doOrderRefund();
			
			//添加退单人员工号和退单时间到es_order_extvtl表中,便于订单报表导出
			AdminUser user = ManagerUtils.getAdminUser();
			Calendar date = new GregorianCalendar();
			DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String refund_time=DF.format(date.getTime());
			String user_id = user.getUsername();
			if(!StringUtil.isEmpty(user_id)) {
					CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[] { "refund_userid" }, new String[] { user_id });
				}
			String update_county_extvtl_sql = "update es_order_extvtl set refund_time = to_date('"+refund_time+"','yyyy-MM-dd HH24:mi:ss') where order_id='" + order_id + "'";
			IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
			baseDaoSupport.execute(update_county_extvtl_sql);
			
			JSONObject obj = JSONObject.fromObject(this.json);
			String result = obj.getString("status");
			String msg = obj.getString("msg");
			if(ConstsCore.ERROR_SUCC.equals(result)){
				succCount++;
			}
		}
		this.json="{status:0,msg:'退款处理成功，退款成功"+succCount+"个订单'}";	
		return JSON_MESSAGE;
	}
	
	/*
	 * 保存爬虫配置信息
	 */
	public String saveCrawlerInfo(){
		if(crawlerConfig!=null){
			String result = "";
			try {
				result = orderCrawlerManager.saveCrawlerInfo(crawlerConfig);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(result.equals("0")){
				this.json = "{result:0,message:'操作成功'}";
			}else{
				this.json = "{result:1,message:'操作失败'}";
			}			
		}else{
			this.json = "{result:1,message:'操作失败'}";
		}

		return this.JSON_MESSAGE;
	}
	
	/*
	 * 查询进程信息
	 */
	public String queryCrawlerProc(){
/*		if(crawlerConfig == null){
			crawlerConfig = new CrawlerConfig();
		}*/
		crawlerConfig = orderCrawlerManager.queryCrawlerConfig();
		list = orderCrawlerManager.queryCrawlerProc();
		webPage = orderCrawlerManager.queryUrlInfo(getPage(),getPageSize());
		return "crawlerConfigList";
	}
	
	/*
	 * 启用/禁用进程
	 */
	public String startOrForbidProc(){
		String rsp_s[] = orderCrawlerManager.startOrForbidProc(proc_id,flag);
        if (rsp_s!=null && rsp_s.length>0) {
        	json = "{'result':1,'message':'"+rsp_s[1]+"'}";
  		} else {
  			json = "{'result':0, 'message':'操作成功!'}";
  		}
		return this.JSON_MESSAGE;
	}
	
	/*
	 * 添加进程
	 */
	public String addProc(){
		String result = orderCrawlerManager.addProc(crawlerProc,flag);
		if(result.equals("0")){
			this.json = "{result:0,message:'操作成功'}";
		}else{
			this.json = "{result:1,message:'操作失败'}";
		}
		return this.JSON_MESSAGE;
	}
	
	/*
	 * 查询进程配置的条件
	 */
	public String queryProcCond(){
//		condCodeInfo = orderCrawlerManager.getCondCodeInfo();
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		crawlerCondType = dcPublicCache.getList("DIC_CRAWLER_COND");//条件类型
		crawlerProc = orderCrawlerManager.getProcInfo(proc_id);
//		condCodeList = new ArrayList<Map<String,String>>();
		webPage = orderCrawlerManager.queryProcCond(proc_id,getPage(),getPageSize());
		return "crawlerProcCond";
	}
	
	/*
	 * 根据条件类型查询条件编码数据
	 */
	public String getCondCode(){
		condCodeList = orderCrawlerManager.getCondCode(condType);
		json = com.alibaba.fastjson.JSONArray.toJSONString(condCodeList);
		return this.JSON_MESSAGE;
	}
	
	//根据条件编码获取条件值
	public String getCondValueInfo(){
		condValueList = orderCrawlerManager.getCondValueInfo(condValueCode);
		json = com.alibaba.fastjson.JSONArray.toJSONString(condValueList);
		return this.JSON_MESSAGE; 
	}
	
	/*
	 * 查询进程
	 */
	public String queryProc(){
		crawlerProc = orderCrawlerManager.getProcInfo(proc_id);
		return "addOrEditCrawlerProc";
	}
	
	/*
	 * 添加进程条件
	 */
	public String addProcCond(){
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(params); 
        List<Map<String, String>> listParams = (List) net.sf.json.JSONArray.toCollection(jsonArray, Map.class);
        String rsp_s[] = orderCrawlerManager.addProcCond(listParams,proc_id);
        if (rsp_s!=null && rsp_s.length>0) {
        	json = "{'result':1,'message':'"+rsp_s[1]+"'}";
  		} else {
  			json = "{'result':0, 'message':'操作成功!'}";
  		}
		return this.JSON_MESSAGE;
	}
	
	/*
	 * 禁用进程条件
	 */
	public String forbidCond(){
		String[] rsp_s = orderCrawlerManager.forbidCond(cond_id);
        if (rsp_s!=null && rsp_s.length>0) {
        	json = "{'result':1,'message':'"+rsp_s[1]+"'}";
  		} else {
  			json = "{'result':0, 'message':'操作成功!'}";
  		}
		return this.JSON_MESSAGE; 
	}
	
	/*
	 * 新增爬虫操作配置信息
	 */
	public String addUrlInfo(){
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(params); 
        List<Map<String, String>> UrlList = (List) net.sf.json.JSONArray.toCollection(jsonArray, Map.class);
        String rsp_s[] = null;
		try {
			rsp_s = orderCrawlerManager.addUrlInfo(UrlList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (rsp_s!=null && rsp_s.length>0) {
        	json = "{'result':1,'message':'"+rsp_s[1]+"'}";
  		} else {
  			json = "{'result':0, 'message':'操作成功!'}";
  		}
		return this.JSON_MESSAGE;
	}
	
	/*
	 * 禁用操作配置信息
	 */
	public String delUrlConfig(){
		String[] rsp_s = orderCrawlerManager.delUrlConfig(cuc_id);
        if (rsp_s!=null && rsp_s.length>0) {
        	json = "{'result':1,'message':'"+rsp_s[1]+"'}";
  		} else {
  			json = "{'result':0, 'message':'操作成功!'}";
  		}
		return this.JSON_MESSAGE; 
	}
	
	//界面刷新将已开启进程的爬虫信息传到爬虫侧
	public String refreshInfo(){
		String[] rsp_s = orderCrawlerManager.refreshInfo();
        if (rsp_s!=null && rsp_s.length>0) {
        	json = "{'result':1,'message':'"+rsp_s[1]+"'}";
  		} else {
  			json = "{'result':0, 'message':'刷新成功!'}";
  		}
		return this.JSON_MESSAGE; 
	}
	
	public String transManualOpen(){
		String zbOpenType = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ZB_OPEN_TYPE);
		if("1".equals(zbOpenType)){
			json = "{'result':1,'message':'当前已经是手动开户，不需要转换'}";
		}else{
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ZB_OPEN_TYPE},new String[]{"1"});
			String action_url ="shop/admin/orderFlowAction!openAccount.do?openAccountType=offline&order_id="+order_id+"&query_type=order";
			json = "{'result':0, 'message':'转手动开户成功 !',action_url:'" + action_url+ "'}";
		}
		return this.JSON_MESSAGE; 
	}
	/**
	 * 订单审核环节修改商品信息
	 * @return
	 */
	public String updateGoodsInfo(){
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:"+ validateMsg + ",order_id:'" + order_id + "'}";
			return this.JSON_MESSAGE;
		} else {
			json = this.orderCrawlerManager.modifyZbOrderGoodsInfo(order_id);
		}
		return this.JSON_MESSAGE; 
	}
	/**
	 * 订单审核环节修改配送信息
	 * @return
	 */
	public String updateDeliverInfo(){
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:"+ validateMsg + ",order_id:'" + order_id + "'}";
			return this.JSON_MESSAGE;
		} else {
			json = this.orderCrawlerManager.modifyZbOrderPostInfo(order_id);
		}
		
		return this.JSON_MESSAGE; 
	}
	/**
	 * 开户环节修改商品信息
	 * @return
	 */
	public String modifyGoodsInfoToZb(){
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:"+ validateMsg + ",order_id:'" + order_id + "'}";
			return this.JSON_MESSAGE;
		} else {
			json = this.orderCrawlerManager.modifyGoodsInfoToZb(order_id);
		}
		return this.JSON_MESSAGE; 
	}
	/**
	 * 订单审核环节修改配送信息
	 * @return
	 */
	public String modifyDeliveryInfoToZb(){
		String msg = editSave();
		if (msg != null && msg.length() > 0) {
			String validateMsg = JSONArray.toJSONString(validateMsgList);
			this.json = "{result:1,message:'" + msg + "',validateMsgList:"+ validateMsg + ",order_id:'" + order_id + "'}";
			return this.JSON_MESSAGE;
		} else {
			json = this.orderCrawlerManager.modifyDeliveryInfoToZb(order_id);
		}
		
		return this.JSON_MESSAGE; 
	}
	
	public static void setMutexLock(String lockName, HashMap lock) {
		mutexLocks.put(lockName, lock);
	}

	public static Map getMutexLock(String lockName) {
		HashMap lock = (HashMap) mutexLocks.get(lockName);
		if (lock == null) {
			lock = new HashMap();
			mutexLocks.put(lockName, lock);
		}
		return lock;
	}

	public static void removeMutexLock(String lockName) {
		mutexLocks.remove(lockName);
	}

	public static Map getMutexLocks() {
		return mutexLocks;
	}

	public ILogsServices getLogsServices() {
		return logsServices;
	}

	public void setLogsServices(ILogsServices logsServices) {
		this.logsServices = logsServices;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getQuery_type() {
		return query_type;
	}

	public void setQuery_type(String query_type) {
		this.query_type = query_type;
	}

	public String getQ_check() {
		return q_check;
	}

	public void setQ_check(String q_check) {
		this.q_check = q_check;
	}

	public String getBtnType() {
		return btnType;
	}

	public void setBtnType(String btnType) {
		this.btnType = btnType;
	}

	public String getShipping_company() {
		return shipping_company;
	}

	public void setShipping_company(String shipping_company) {
		this.shipping_company = shipping_company;
	}

	public String getOldCertNum() {
		return oldCertNum;
	}

	public void setOldCertNum(String oldCertNum) {
		this.oldCertNum = oldCertNum;
	}

	public String getAbnormal_type() {
		return abnormal_type;
	}

	public void setAbnormal_type(String abnormal_type) {
		this.abnormal_type = abnormal_type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getResourceCodeStr() {
		return resourceCodeStr;
	}

	public void setResourceCodeStr(String resourceCodeStr) {
		this.resourceCodeStr = resourceCodeStr;
	}

	public String getResourceItemsStr() {
		return resourceItemsStr;
	}

	public void setResourceItemsStr(String resourceItemsStr) {
		this.resourceItemsStr = resourceItemsStr;
	}

	public String getProtect_free() {
		return protect_free;
	}

	public void setProtect_free(String protect_free) {
		this.protect_free = protect_free;
	}

	public String getLogi_receiver() {
		return logi_receiver;
	}

	public void setLogi_receiver(String logi_receiver) {
		this.logi_receiver = logi_receiver;
	}

	public String getLogi_receiver_phone() {
		return logi_receiver_phone;
	}

	public void setLogi_receiver_phone(String logi_receiver_phone) {
		this.logi_receiver_phone = logi_receiver_phone;
	}

	public String getDelivery_id() {
		return delivery_id;
	}

	public void setDelivery_id(String delivery_id) {
		this.delivery_id = delivery_id;
	}

	public String getNeed_receipt() {
		return need_receipt;
	}

	public void setNeed_receipt(String need_receipt) {
		this.need_receipt = need_receipt;
	}

	public String getProd_audit_status() {
		return prod_audit_status;
	}

	public void setProd_audit_status(String prod_audit_status) {
		this.prod_audit_status = prod_audit_status;
	}

	public String getIs_order_exp() {
		return is_order_exp;
	}
	public void setIs_order_exp(String is_order_exp) {
		this.is_order_exp = is_order_exp;
	}
	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public String getICCID() {
		return ICCID;
	}

	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}

	public String getFeiyongmingxi_s() {
		return feiyongmingxi_s;
	}

	public void setFeiyongmingxi_s(String feiyongmingxi_s) {
		this.feiyongmingxi_s = feiyongmingxi_s;
	}

	public String getActive_fail_type() {
		return active_fail_type;
	}

	public void setActive_fail_type(String active_fail_type) {
		this.active_fail_type = active_fail_type;
	}

	public String getActive_status() {
		return active_status;
	}

	public void setActive_status(String active_status) {
		this.active_status = active_status;
	}

	public Long getProvinc_code() {
		return provinc_code;
	}

	public void setProvinc_code(Long provinc_code) {
		this.provinc_code = provinc_code;
	}

	public Long getCity_code() {
		return city_code;
	}

	public void setCity_code(Long city_code) {
		this.city_code = city_code;
	}

	public Long getDistrict_id() {
		return district_id;
	}

	public void setDistrict_id(Long district_id) {
		this.district_id = district_id;
	}

	public double getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
	}

	public OrdReceipt getOrdReceipt() {
		return ordReceipt;
	}

	public void setOrdReceipt(OrdReceipt ordReceipt) {
		this.ordReceipt = ordReceipt;
	}

	public List getValidateMsgList() {
		return validateMsgList;
	}

	public void setValidateMsgList(List validateMsgList) {
		this.validateMsgList = validateMsgList;
	}

	public List getActiveDescList() {
		return activeDescList;
	}

	public void setActiveDescList(List activeDescList) {
		this.activeDescList = activeDescList;
	}

	public List<AttrFeeInfoBusiRequest> getFeeInfoList() {
		return feeInfoList;
	}

	public void setFeeInfoList(List<AttrFeeInfoBusiRequest> feeInfoList) {
		this.feeInfoList = feeInfoList;
	}

	public OrderDeliveryBusiRequest getDelivery() {
		return delivery;
	}

	public void setDelivery(OrderDeliveryBusiRequest delivery) {
		this.delivery = delivery;
	}

	public List<AttrGiftInfoBusiRequest> getGiftInfoList() {
		return giftInfoList;
	}

	public void setGiftInfoList(List<AttrGiftInfoBusiRequest> giftInfoList) {
		this.giftInfoList = giftInfoList;
	}

	public List<OrderSubPackageList> getSubProductList() {
		return subProductList;
	}

	public void setSubProductList(List<OrderSubPackageList> subProductList) {
		this.subProductList = subProductList;
	}

	public List<OrderPhoneInfoFukaBusiRequest> getOrderPhoneInfoFukaList() {
		return orderPhoneInfoFukaList;
	}

	public void setOrderPhoneInfoFukaList(
			List<OrderPhoneInfoFukaBusiRequest> orderPhoneInfoFukaList) {
		this.orderPhoneInfoFukaList = orderPhoneInfoFukaList;
	}

	public CrawlerConfig getCrawlerConfig() {
		return crawlerConfig;
	}

	public void setCrawlerConfig(CrawlerConfig crawlerConfig) {
		this.crawlerConfig = crawlerConfig;
	}

	public CrawlerProc getCrawlerProc() {
		return crawlerProc;
	}

	public void setCrawlerProc(CrawlerProc crawlerProc) {
		this.crawlerProc = crawlerProc;
	}

	public String getProc_id() {
		return proc_id;
	}

	public void setProc_id(String proc_id) {
		this.proc_id = proc_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Page getWebPage() {
		return webPage;
	}

	public void setWebPage(Page webPage) {
		this.webPage = webPage;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getCond_id() {
		return cond_id;
	}

	public void setCond_id(String cond_id) {
		this.cond_id = cond_id;
	}

	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}

	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}

	public String getD_type() {
		return d_type;
	}

	public void setD_type(String d_type) {
		this.d_type = d_type;
	}

	public String getChangeMode() {
		return changeMode;
	}

	public void setChangeMode(String changeMode) {
		this.changeMode = changeMode;
	}

	public String getCuc_id() {
		return cuc_id;
	}

	public void setCuc_id(String cuc_id) {
		this.cuc_id = cuc_id;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Map<String, String> getCondCodeInfo() {
		return condCodeInfo;
	}

	public void setCondCodeInfo(Map<String, String> condCodeInfo) {
		this.condCodeInfo = condCodeInfo;
	}

	public List getCrawlerCondType() {
		return crawlerCondType;
	}

	public void setCrawlerCondType(List crawlerCondType) {
		this.crawlerCondType = crawlerCondType;
	}


	public List<Map<String, String>> getCondCodeList() {
		return condCodeList;
	}

	public void setCondCodeList(List<Map<String, String>> condCodeList) {
		this.condCodeList = condCodeList;
	}

	public String getCondType() {
		return condType;
	}

	public void setCondType(String condType) {
		this.condType = condType;
	}

	public String getCondValueCode() {
		return condValueCode;
	}

	public void setCondValueCode(String condValueCode) {
		this.condValueCode = condValueCode;
	}

	public List<Map<String, String>> getCondValueList() {
		return condValueList;
	}

	public void setCondValueList(List<Map<String, String>> condValueList) {
		this.condValueList = condValueList;
	}

	
}
