/**
 * 
 */
package com.ztesoft.net.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import util.ChannelidProductcompRefUtil;
import util.EssOperatorInfoUtil;
import util.RefrenshDataUtil;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.iservice.IOrderServices;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.order.req.RefreshAttrDefReq;
import zte.params.order.req.RefreshOrderTreeAttrReq;

import com.alibaba.rocketmq.common.ThreadFactoryImpl;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.support.GoodsRefreshDTO;
import com.ztesoft.net.mall.core.service.IConfigInfoManager;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IGoodsManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.service.IRefreshPlanRuleManager;

import consts.ConstsCore;

/**
 * @author ZX
 * 刷新订单树属性
 * RefreshOrderTreeAttrAction.java
 * 2014-11-5
 */
public class OrderRefreshTreeAttrAction extends WWAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IOrderServices orderServices;
	private IConfigInfoManager configInfoManager;
	private IDcPublicInfoManager dcPublicInfoManager;
	private IRefreshPlanRuleManager refreshPlanRuleManager;
	private IGoodsManager goodsManager;
	private String order_ids;
	private String plan_rule_ids;
	private String refresh_type;
	private GoodsRefreshDTO goodsRefreshDTO;
	private String orderOperId;
	private String order_id;
	private String goods_id;
	private String order_source_from;//系统来源 
	private String is_remove_outer_data;//是否一并删除es_order_outer数据标识
	private String rule_id;//规则id或规则code
	
	/**
	 * 根据条件[GoodsRefreshDTO]增量刷新商品缓存
	 */
	public String refreshGoodsInfoByCondition(){
		try{
			//页面参数转换为符合sql参数
			this.cleanGoodsRefreshDTO(this.goodsRefreshDTO);
			List<Goods> googsList = this.goodsManager.initGoodsCacheByCondition(goodsRefreshDTO);
			StringBuffer msg = new StringBuffer();
			if(null == googsList || googsList.isEmpty()){
				msg.append("执行成功,但未查询到商品数据!");
			}else{
				msg.append("执行成功,已刷新商品Id:");
				for(Goods goods :googsList){
					msg.append(goods.getGoods_id()).append(" ");
				}
			}
			json = "{result:0,msg:'"+msg.toString()+"'}";
		} catch (Exception e) {
			e.printStackTrace();
			json = "{result:1,msg:'执行失败'}";
		}
		
		return this.JSON_MESSAGE;
	}
	
	/**
	 * 页面参数转换
	 */
	private void cleanGoodsRefreshDTO(GoodsRefreshDTO goodsRefreshDTO){
		if(StringUtils.isNotBlank(goodsRefreshDTO.getGoods_ids())){
			String[] strs = goodsRefreshDTO.getGoods_ids().split(",");
			StringBuffer sqlIn = new StringBuffer();
			for (int i = 0; i < strs.length; i++) {
				if (i == strs.length - 1) {
					sqlIn.append("'").append(strs[i]).append("'");
				} else {
					sqlIn.append("'").append(strs[i]).append("',");
				}
			}
			goodsRefreshDTO.setGoods_ids(sqlIn.toString());
		}
		
		if(StringUtils.isNotBlank(goodsRefreshDTO.getCreate_start())){
			goodsRefreshDTO.setCreate_start(goodsRefreshDTO.getCreate_start()+" 00:00:00");
		}
		if(StringUtils.isNotBlank(goodsRefreshDTO.getCreate_end())){
			goodsRefreshDTO.setCreate_end(goodsRefreshDTO.getCreate_end()+" 23:59:59");
		}
	}
	
	/**
	 * @作者 ZX
	 * 刷新订单树属性
	 * @return
	 */
	public String refreshOrderTreeAttr () {
		
		try {
			RefreshAttrDefReq defReq = new RefreshAttrDefReq();
			RefreshOrderTreeAttrReq attrReq = new RefreshOrderTreeAttrReq();
			orderServices.refreshOrderTreeAttr(attrReq); // 刷es_attr_def表
			orderServices.refreshAttrDef(defReq); // 刷新订单树属性
			DcPublicInfoCacheProxy dcPublicInfoCacheProxy = new DcPublicInfoCacheProxy(dcPublicInfoManager);
			dcPublicInfoCacheProxy.refreshCache();
			json = "{result:0,msg:'执行成功'}";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			json = "{result:1,msg:'执行失败'}";
		}		
		return JSON_MESSAGE;
	}
	
	private static ExecutorService refreshOrderThreadPools = null;
	private void startup() {
		if (this.refreshOrderThreadPools == null) {
			synchronized(OrderRefreshTreeAttrAction.class) {
				this.refreshOrderThreadPools = Executors.newCachedThreadPool(new ThreadFactoryImpl("刷新订单树线程池"));
			}
		}
	}
	
	/**
	 * 根据订单ID刷新订单树
	 * @return
	 */
	public String refreshOrderTree() {
		if (StringUtils.isNotBlank(order_ids)) {
			String[] ids = order_ids.split(",");
			startup();
			try {
				if (ids != null && ids.length > 0) {
					List<FutureTask<Boolean>> list = new ArrayList<FutureTask<Boolean>>();
					for (int i = 0; i < ids.length; i++) {
						ServiceRunnable runnable = new ServiceRunnable(ids[i]);
						FutureTask<Boolean> ft = new FutureTask<Boolean>(runnable);
						list.add(ft);
						refreshOrderThreadPools.submit(ft);
					}
					boolean result = false;
					for(FutureTask<Boolean> ft:list) {
						if (!ft.get()) {
							result = false;
							break;
						} else {
							result = true;
						}
					}
					if (!result) {
						json = "{result:1, msg:'刷新订单树失败！'}";
					} else {
						json = "{result:0, msg:'刷新订单树成功！'}";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JSON_MESSAGE;
		}
		return "refresh_order_tree";
	}
	private class ServiceRunnable implements Callable {
		private String order_id = "";
		private ServiceRunnable(String order_id) {
		    this.order_id = order_id;
		}
		@Override
		public Object call() throws Exception {
			try{
				long begin = System.currentTimeMillis();
				CommonDataFactory.getInstance().updateOrderTree(order_id);
				long end = System.currentTimeMillis();
				logger.info("请求接入消耗时间总时间时间===============>"+(end-begin));
				return true;
			}catch (Throwable e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	/**
	 * @作者 ZX
	 * @时间 2015-09-25
	 * 查看订单树刷新结果
	 * @return
	 */
	public String qryOrderRefreshResult() {
		if (StringUtils.isNotBlank(order_id)) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			if (orderTree != null) {
				JSONObject object = JSONObject.fromObject(orderTree);
				json = object.toString();
			}
			return JSON_MESSAGE;
		}
		return "qry_ordertree_refresh_result";
	}
	
	public String qryGoodsRefreshResult() {
		
		if (StringUtils.isNotBlank(goods_id)) {
			Goods goods = CommonDataFactory.getInstance().getGoodsBySku(goods_id);
			if (goods != null) {
				JSONObject object = JSONObject.fromObject(goods);
				json = object.toString();
			}
			return JSON_MESSAGE;
		}
		return "qry_ordertree_refresh_result";
	}
	
	/**
	 * @作者 ZX
	 * @时间 2014-11-14
	 * 根据方案ID刷新方案
	 * @return
	 */
	public String refreshPlanRule() {
		if (StringUtils.isNotBlank(plan_rule_ids)) {
			try {		
				if (refresh_type.equals("plan")){
					refreshPlanRuleManager.refreshPlan(plan_rule_ids);
					refreshPlanRuleManager.refreshRule(plan_rule_ids);
				}
				if (refresh_type.equals("rule"))
					refreshPlanRuleManager.refreshRule(plan_rule_ids);
				json = "{result:0, msg:'刷新方案成功！'}";
			} catch (Exception e) {
				e.printStackTrace();
				json = "{result:1, msg:'刷新方案失败！'}";
			}
			return JSON_MESSAGE;
		}
		return "refresh_plan_rule";
	}
	
	/**
	 * @作者 XMJ 刷新订单操作员ID
	 * @return
	 */
	public String refreshorderOperIdInfoCache() {
		if (StringUtils.isNotBlank(orderOperId)) {
			try {
				String[] ids = orderOperId.split(",");
				for (int i = 0; i < ids.length; i++) {
					EssOperatorInfoUtil.refreshorderOperIdInfoCache(ids[i]);
				}
				json = "{result:0, msg:'刷新订单操作员ID绑定ESS工号成功！'}";
			} catch (Exception e) {
				e.printStackTrace();
				json = "{result:1, msg:'刷新订单操作员ID绑定ESS工号失败！'}";
			}
			return JSON_MESSAGE;
		}
		return "refresh_order_tree";
	}
	
	/**
	 * @刷新渠道货主编码映射关系
	 * @return
	 */
	public String refreshChannelIdInfoCache() {
		if (StringUtils.isNotBlank(orderOperId)) {
			try {
				String[] ids = orderOperId.split(",");
				for (int i = 0; i < ids.length; i++) {
					ChannelidProductcompRefUtil.setCompCode(ids[i]);
				}
				json = "{result:0, msg:'刷新渠道ID货主映射关系成功！'}";
			} catch (Exception e) {
				e.printStackTrace();
				json = "{result:1, msg:'刷新渠道ID货主映射关系失败！'}";
			}
			return JSON_MESSAGE;
		}
		return "refresh_order_tree";
	}
	
	public String refreshRepeatOrder(){
		if (StringUtils.isNotBlank(order_ids)) {
			try {
				RefrenshDataUtil.refrenshRepearOrder(order_ids, is_remove_outer_data, order_source_from);
				json = "{result:0, msg:'刷新重复订单成功！'}";
			} catch (Exception e) {
				e.printStackTrace();
				json = "{result:1, msg:'刷新重复订单失败！'}";
			}
			return this.JSON_MESSAGE;
		}
		return "refresh_repeat_order";
	}
	
	/**
	 * 订单规则执行
	 * @author liu.quan
	 * @date 2017年4月19日
	 * @return
	 */
	public String orderRuleExec() {
		if (StringUtils.isNotBlank(order_ids)) {
			String[] ids = order_ids.split(",");
			startup();
			try {
				if (ids != null && ids.length > 0) {
					StringBuffer failOrderIdsSb = new StringBuffer();
					//将输入的规则id当成规则code去查询规则id，如果能查询到结果，说明输入的是规则code，否则输入的是规则id
					String ruleId = refreshPlanRuleManager.queryRuleId(rule_id);
					rule_id = StringUtils.isEmpty(ruleId)? rule_id:ruleId;
					for (int i = 0; i < ids.length; i++) {
						String order_id = ids[i];
						RuleTreeExeReq req = new RuleTreeExeReq();
						TacheFact fact = new TacheFact();
						fact.setOrder_id(order_id);
						req.setFact(fact);
						req.setRule_id(rule_id);
						req.setCheckAllRelyOnRule(true);
						req.setCheckCurrRelyOnRule(true);
						ZteClient client = ClientFactory.getZteDubboClient(AppKeyEnum.APP_KEY_WSSMALL_ESC);
						RuleTreeExeResp ruleResp = client.execute(req, RuleTreeExeResp.class);
						if (!ConstsCore.ERROR_SUCC.equals(ruleResp.getError_code())) {
							failOrderIdsSb.append(",").append(order_id);
						}
					}
					if (StringUtils.isEmpty(failOrderIdsSb.toString())) {
						json = "{result:1, msg:'订单规则全部执行成功！'}";
					} else {
						String failOrderIds = failOrderIdsSb.substring(1);
						json = "{result:0, msg:'订单规则部分执行失败！', failOrderIds:'"+failOrderIds+"'}";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return JSON_MESSAGE;
		}
		return "refresh_order_tree";
	}
	
	public IConfigInfoManager getConfigInfoManager() {
		return configInfoManager;
	}
	
	public void setConfigInfoManager(IConfigInfoManager configInfoManager) {
		this.configInfoManager = configInfoManager;
	}
	
	public IOrderServices getOrderServices() {
		return orderServices;
	}
	
	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}
	
	public String getOrder_ids() {
		return order_ids;
	}
	
	public void setOrder_ids(String order_ids) {
		this.order_ids = order_ids;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public String getPlan_rule_ids() {
		return plan_rule_ids;
	}

	public void setPlan_rule_ids(String plan_rule_ids) {
		this.plan_rule_ids = plan_rule_ids;
	}

	public String getRefresh_type() {
		return refresh_type;
	}

	public void setRefresh_type(String refresh_type) {
		this.refresh_type = refresh_type;
	}

	public IRefreshPlanRuleManager getRefreshPlanRuleManager() {
		return refreshPlanRuleManager;
	}

	public void setRefreshPlanRuleManager(
			IRefreshPlanRuleManager refreshPlanRuleManager) {
		this.refreshPlanRuleManager = refreshPlanRuleManager;
	}


	public GoodsRefreshDTO getGoodsRefreshDTO() {
		return goodsRefreshDTO;
	}


	public void setGoodsRefreshDTO(GoodsRefreshDTO goodsRefreshDTO) {
		this.goodsRefreshDTO = goodsRefreshDTO;
	}

	public IGoodsManager getGoodsManager() {
		return goodsManager;
	}


	public void setGoodsManager(IGoodsManager goodsManager) {
		this.goodsManager = goodsManager;
	}

	public String getOrderOperId() {
		return orderOperId;
	}

	public void setOrderOperId(String orderOperId) {
		this.orderOperId = orderOperId;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	public String getOrder_source_from() {
		return order_source_from;
	}

	public void setOrder_source_from(String order_source_from) {
		this.order_source_from = order_source_from;
	}

	public String getIs_remove_outer_data() {
		return is_remove_outer_data;
	}

	public void setIs_remove_outer_data(String is_remove_outer_data) {
		this.is_remove_outer_data = is_remove_outer_data;
	}

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

}
