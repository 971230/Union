package com.ztesoft.net.util;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.common.util.MD5Util;
import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.ecsord.params.ecaop.vo.BSSGonghaoInfoVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.consts.ZjEcsOrderConsts;
import com.ztesoft.net.mall.core.service.impl.RequestStoreManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.service.IEcsOrdManager;
import com.ztesoft.net.service.IWorkPoolManager;

import consts.ConstsCore;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.busi.req.AttrFeeInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderLockBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.utils.AttrUtils;

@SuppressWarnings("rawtypes")
public class ZjCommonUtils {

	private static INetCache cache;
	private static IEcsOrdManager ecsOrdManager;

	private static IDaoSupport baseDaoSupport;

	static {
		cache = CacheFactory.getCacheByType("");
		ecsOrdManager = SpringContextHolder.getBean("ecsOrdManager");
		baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
	}

	public static boolean lockOrder(String order_id) {
		boolean result = false;
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String trace_id = orderExt.getFlow_trace_id();
		String key = new StringBuffer().append("order_lock_").append(trace_id).append("_").append(order_id).toString();
		try {
			key = MD5Util.MD5(key);
			String def = String.valueOf(cache.get(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key));
			if (StringUtils.isEmpty(def)) {
				cache.set(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key, key,
						ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE_TIMEOUT);
				OrderLockBusiRequest orderLockBusiRequest = CommonDataFactory.getInstance()
						.getOrderLockBusiRequest(orderTree, trace_id);
				IWorkPoolManager workPoolManager = SpringContextHolder.getBean("workPoolManager");
				if (orderLockBusiRequest == null) {
					orderLockBusiRequest = new OrderLockBusiRequest();
					orderLockBusiRequest.setLock_id(ecsOrdManager.getSequences("o_outcall_log"));
					orderLockBusiRequest.setLock_user_id(ManagerUtils.getAdminUser().getUserid());
					orderLockBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
					orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
					orderLockBusiRequest.setTache_code(trace_id);
					orderLockBusiRequest.setOrder_id(order_id);
					orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
					// 锁单信息增加工号池和锁单结束时间
					orderLockBusiRequest.setPool_id(
							workPoolManager.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getPool_id());
					orderLockBusiRequest.setLock_end_time(workPoolManager
							.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getLock_end_time());
					orderLockBusiRequest.store();
				} else {
					orderLockBusiRequest.setLock_user_id(ManagerUtils.getAdminUser().getUserid());
					orderLockBusiRequest.setLock_user_name(ManagerUtils.getAdminUser().getUsername());
					orderLockBusiRequest.setLock_status(EcsOrderConsts.LOCK_STATUS);
					orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					// 锁单信息增加工号池和锁单结束时间
					orderLockBusiRequest.setPool_id(
							workPoolManager.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getPool_id());
					orderLockBusiRequest.setLock_end_time(workPoolManager
							.getLockTimeByUserId(ManagerUtils.getAdminUser().getUserid()).getLock_end_time());
					orderLockBusiRequest.store();
				}
				result = true;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void unlockOrder(String order_id) {
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
		String trace_id = orderExt.getFlow_trace_id();
		String key = new StringBuffer().append("order_lock_").append(trace_id).append("_").append(order_id).toString();
		try {
			key = MD5Util.MD5(key);
			String def = String.valueOf(cache.get(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key));
			if (!StringUtils.isEmpty(def) && key.equals(def)) {
				cache.delete(ZjEcsOrderConsts.ORDER_LOCK_NAMESPACE, key);
				List<OrderLockBusiRequest> orderLockBusiRequestList = orderTree.getOrderLockBusiRequests();
				for (int i = 0; i < orderLockBusiRequestList.size(); i++) {
					OrderLockBusiRequest orderLockBusiRequest = orderLockBusiRequestList.get(i);
					// String aa = orderLockBusiRequest.getTache_code();
					if (trace_id.equals(orderLockBusiRequest.getTache_code())) {
						orderLockBusiRequest.setLock_user_id(ConstsCore.NULL_VAL);
						orderLockBusiRequest.setLock_user_name(ConstsCore.NULL_VAL);
						orderLockBusiRequest.setLock_status(EcsOrderConsts.UNLOCK_STATUS);
						orderLockBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
						orderLockBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
						// 解锁订单释放工号池和锁单结束时间
						orderLockBusiRequest.setPool_id(ConstsCore.NULL_VAL);
						orderLockBusiRequest.setLock_end_time(ConstsCore.NULL_VAL);
						orderLockBusiRequest.store();
					}
				}
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 检查是否当前锁定用户
	 * 
	 * @作者 MoChunrun
	 * @创建日期 2014-11-11
	 * @return
	 */
	public static String checkLockUser(String order_id, String query_type) {
		String msg = "";
		AdminUser user = ManagerUtils.getAdminUser();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		List<OrderLockBusiRequest> orderLockRequest = orderTree.getOrderLockBusiRequests();// 某些单子会出现锁定状态为null的情况
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
		if (orderLockBusiRequest == null
				|| EcsOrderConsts.UNLOCK_STATUS.equals(orderLockBusiRequest.getLock_status())) {
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

	/**
	 * 根据订单号获取工号信息
	 *
	 * @param order_id
	 * @return
	 * @throws Exception
	 *
	 * @author liu.quan68@ztesoft.com
	 *
	 * @date 2017年2月28日
	 */
	public static BSSGonghaoInfoVO getGonghaoInfoByOrderId(String order_id) {
		String cityCode = AttrUtils.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE, "", "");
		String orderFrom = AttrUtils.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM, "", "");
		BSSGonghaoInfoVO gonghaoInfo = new BSSGonghaoInfoVO();
		gonghaoInfo.setCity_id(cityCode);
		gonghaoInfo.setOrder_from(orderFrom);
		
		return gonghaoInfo;
	}

	public static void setOrderVisable(String order_id) throws ApiBusiException {
		OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
		String order_visible = getOrderVisible(order_id);
		orderExt.setVisible_status(order_visible);
		orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExt.store();
	}

	/**
	 * 订单可见性逻辑处理
	 * 
	 * @param order_id
	 * @return
	 * @throws ApiBusiException
	 */
	public static String getOrderVisible(String order_id) throws ApiBusiException {
		OrderTreeBusiRequest tree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExt = tree.getOrderExtBusiRequest();
		// 订单处理类型
		String refundDealType = orderExt.getRefund_deal_type();

		// 退单状态
		String refundStatus = orderExt.getRefund_status();

		String orderMode = orderExt.getOrder_model();

		String flowTraceId = orderExt.getFlow_trace_id();

		// ZX ADD 2015-10-21 判断网别是否3G
		String net_type = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10002,
				SpecConsts.NET_TYPE);
		// ZX ADD 2015-10-21 判断合约计划是否存费送费
		String cat_id = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10001,
				SpecConsts.CAT_ID);

		// 质检环节订单都设为可见
		if (EcsOrderConsts.DIC_ORDER_NODE_F.equals(flowTraceId)) {
			return EcsOrderConsts.VISIBLE_STATUS_0;
		}

		if (EcsOrderConsts.OPER_MODE_DL.equals(orderMode)) {
			return EcsOrderConsts.VISIBLE_STATUS_0;
		}

		// 退单驳回
		if (EcsOrderConsts.REFUND_STATUS_00.equals(refundStatus))
			return EcsOrderConsts.VISIBLE_STATUS_0;

		// 退单类型，非正常订单
		if (EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(refundDealType)) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}
		// 回访，且与总部交互
		if (EcsOrderConsts.DIC_ORDER_NODE_J.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 发货，且与WMS交互
		if (EcsOrderConsts.DIC_ORDER_NODE_H.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.OPER_MODE_ZD.equals(orderExt.getOrder_model())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 写卡，且与WMS交互
		if (EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.OPER_MODE_ZD.equals(orderExt.getOrder_model())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 开户，订单与总户交互、且订单异常类型（ABNORMAL_TYPE）为“非伪自动化异常”，则设置订单不可见。
		if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.SEND_ZB_1.equals(orderExt.getSend_zb())
				&& !EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExt.getAbnormal_type())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 开户，订单走AOP开户、且订单异常类型（ABNORMAL_TYPE）为“非伪自动化异常”，则设置订单不可见。
		if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.IS_DEFAULT_VALUE.equals(orderExt.getIs_aop())
				&& !EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExt.getAbnormal_type())) {
			// return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 预拣货：订单与WMS交互，则设置订单不可见
		if (EcsOrderConsts.DIC_ORDER_NODE_C.equals(orderExt.getFlow_trace_id())
				&& EcsOrderConsts.OPER_MODE_ZD.equals(orderExt.getOrder_model())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 自动化订单，在预拣货、开户、写卡都不可见 (正常单)
		if (EcsOrderConsts.OPER_MODE_ZD.equals(orderMode)
				&& EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(orderExt.getException_type())
				&& (EcsOrderConsts.DIC_ORDER_NODE_C.equals(flowTraceId)
						|| EcsOrderConsts.DIC_ORDER_NODE_D.equals(flowTraceId)
						|| EcsOrderConsts.DIC_ORDER_NODE_X.equals(flowTraceId))) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 爬虫自动写卡订单，在订单审核、开户、写卡都不可见 (正常单)
		/*
		 * if(EcsOrderConsts.OPER_MODE_PCZD.equals(orderMode) &&
		 * EcsOrderConsts.ORDER_ABNORMAL_TYPE_0.equals(orderExt.
		 * getException_type()) &&
		 * (EcsOrderConsts.DIC_ORDER_NODE_B.equals(flowTraceId) ||
		 * EcsOrderConsts.DIC_ORDER_NODE_D.equals(flowTraceId) ||
		 * EcsOrderConsts.DIC_ORDER_NODE_X.equals(flowTraceId))){ return
		 * EcsOrderConsts.VISIBLE_STATUS_1; }
		 */

		/**
		 * ZX ADD 2015-10-21 3g存费送费、走AOP、业务办理环节、非伪自动化异常，设置为隐藏
		 */
		if (!StringUtils.isEmpty(net_type) && !StringUtils.isEmpty(cat_id)
				&& !EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(orderExt.getAbnormal_type())
				&& EcsOrderConsts.NET_TYPE_3G.equals(net_type) && EcsOrderConsts.GOODS_CAT_FEE_GIVE_FEE.equals(cat_id)
				&& EcsOrderConsts.IS_DEFAULT_VALUE.equals(orderExt.getIs_aop())
				&& EcsOrderConsts.DIC_ORDER_NODE_Y.equals(orderExt.getFlow_trace_id())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 华盛B2B订单在拣货环节隐藏
		if (EcsOrderConsts.ORDER_FROM_10062.equals(orderExt.getOrder_from())
				&& EcsOrderConsts.DIC_ORDER_NODE_C.equals(orderExt.getFlow_trace_id())) {
			return EcsOrderConsts.VISIBLE_STATUS_1;
		}

		// 集中写卡模式订单在开户、写卡、业务办理环节不可见
		if (EcsOrderConsts.OPER_MODE_XK.equals(orderExt.getOrder_model())) {
			if (EcsOrderConsts.DIC_ORDER_NODE_D.equals(orderExt.getFlow_trace_id())
					|| EcsOrderConsts.DIC_ORDER_NODE_X.equals(orderExt.getFlow_trace_id())
					|| EcsOrderConsts.DIC_ORDER_NODE_Y.equals(orderExt.getFlow_trace_id())) {
				return EcsOrderConsts.VISIBLE_STATUS_1;
			}
		}
		return EcsOrderConsts.VISIBLE_STATUS_0;
	}

	@SuppressWarnings("unchecked")
	public static Map getPrice(String order_id) {
		Map priceMap = new HashMap();
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		List<AttrFeeInfoBusiRequest> feeInfoList = orderTree.getFeeInfoBusiRequests();
		for (AttrFeeInfoBusiRequest feevo : feeInfoList) {
			if (StringUtils.equals(feevo.getFee_item_code(), ZjEcsOrderConsts.DJYCK_FEE_ITEM_ID)) {
				priceMap.put("deposit_price", feevo.getN_fee_num() + "");
			}
			if (StringUtils.equals(feevo.getFee_item_code(), ZjEcsOrderConsts.OPEN_ACC_FEE_ITEM_ID)) {
				priceMap.put("openAcc_price", feevo.getN_fee_num() + "");
			}
		}
		if (orderTree.getPhoneInfoBusiRequest() != null
				&& !StringUtils.equals(ZjEcsOrderConsts.PHONE_CLASS_ID_9,
						orderTree.getPhoneInfoBusiRequest().getClassId() + "")
				&& !StringUtils.isEmpty(orderTree.getPhoneInfoBusiRequest().getAdvancePay())) {
			String num_price = (Double.parseDouble(orderTree.getPhoneInfoBusiRequest().getAdvancePay()) / 1000) + "";
			priceMap.put("num_price", num_price);
		} else {
			priceMap.put("num_price", "0");
		}
		String good_price_system = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.PRICE);
		priceMap.put("good_price_system", good_price_system);
		return priceMap;
	}

	/**
	 * 更新缓存和数据库字段
	 * 
	 * @param set_sql
	 * @param table_name
	 * @param order_id
	 * @param orderTree
	 */
	public static void updateOrderTree(String set_sql, String table_name, String order_id, OrderTreeBusiRequest orderTree) {
		String sql = "update " + table_name + " set " + set_sql + " where order_id=?";
		baseDaoSupport.execute(sql, order_id);
		// 更新订单树缓存
		cache.set(RequestStoreManager.NAMESPACE, RequestStoreManager.DC_TREE_CACHE_NAME_KEY + "order_id" + order_id,
				orderTree, RequestStoreManager.time);
	}
}
