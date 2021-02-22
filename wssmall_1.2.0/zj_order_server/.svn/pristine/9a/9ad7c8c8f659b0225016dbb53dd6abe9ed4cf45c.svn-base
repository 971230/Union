package com.ztesoft.net.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.powerise.ibss.util.RuleUtil;
import com.ztesoft.api.ApiBusiException;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.StringUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.cache.common.SerializeList;
import com.ztesoft.net.ecsord.params.ecaop.req.ObjectQryReq;
import com.ztesoft.net.ecsord.params.ecaop.resp.ObjectQryResp;
import com.ztesoft.net.ecsord.params.ecaop.vo.ObjectQryVO;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.context.MqEnvGroupConfigSetting;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.BusiDealResult;
import com.ztesoft.net.rule.util.RuleFlowUtil;
import com.ztesoft.net.service.IOrderFlowManager;

import commons.CommonTools;
import consts.ConstsCore;
import params.ZteRequest;
import params.ZteResponse;
import params.order.req.Iphone6sReq;
import params.order.req.OrderExceptionLogsReq;
import params.order.req.OrderHandleLogsReq;
import params.orderqueue.req.AsynExecuteMsgWriteMqReq;
import params.req.OrderExpMarkProcessedReq;
import params.resp.OrderExpMarkProcessedResp;
import util.EssOperatorInfoUtil;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.common.SpecConsts;
import zte.net.ecsord.params.base.req.BusiCompRequest;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ecsord.params.ecaop.req.vo.EmpOperInfoVo;
import zte.net.ecsord.params.zb.req.NumberStateChangeZBRequest;
import zte.net.ecsord.params.zb.resp.NumberStateChangeZBResponse;
import zte.net.ecsord.params.zb.vo.ResourcesInfo;
import zte.net.ecsord.rule.tache.TacheFact;
import zte.net.ecsord.utils.AttrUtils;
import zte.net.iservice.IOrderQueueBaseManager;
import zte.net.iservice.IOrderServices;
import zte.net.iservice.IUosService;
import zte.net.params.resp.RuleTreeExeResp;
import zte.params.process.req.UosDealReq;
import zte.params.process.resp.UosDealResp;
import zte.params.process.resp.WorkItem;

public class OrderFlowManager extends BaseSupport implements IOrderFlowManager {
	
	private static final Logger logger = Logger.getLogger(OrderFlowManager.class);
	
	private IOrderServices  orderServices;
	
	@Resource
	protected IDcPublicInfoManager dcPublicInfoManager;
	@Resource
	protected IUosService uosService;

	static INetCache cache;
	static{
		cache = (INetCache) com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	public static int NAMESPACE = 300;
	static int time =60*24*60*5;//缺省缓存20天,memcache最大有效期是30天
	
	@Override
	public List getProvList() {
		String key = "CACHE_ES_REGIONS_ZB_ADD_LEVEL1";
		SerializeList serializeList = (SerializeList)cache.get(NAMESPACE, key);
		List list = new ArrayList();
		if(null != serializeList) {
			list = serializeList.getObj();
		} else {
			serializeList = new SerializeList();
			String sql = "select code,name from es_regions_zb where add_level = '1' ";
			list = this.baseDaoSupport.queryForList(sql);
			serializeList.setObj(list);
			cache.set(NAMESPACE, key, serializeList);
		}
		
		return list;
	}
	@Override
	public List getCityList(Long prov_id) {
		String key = "CACHE_ES_REGIONS_ZB_"+prov_id;
		SerializeList serializeList = (SerializeList)cache.get(NAMESPACE, key);
		List list = new ArrayList();
		if(null != serializeList) {
			list = serializeList.getObj();
		} else {
			serializeList = new SerializeList();
			String sql = "select code,name from es_regions_zb where add_level = '2' and pre_code=? ";
			list = this.baseDaoSupport.queryForList(sql,prov_id);
			serializeList.setObj(list);
			cache.set(NAMESPACE, key, serializeList);
		}
		
		return list;
	}

	@Override
	public List getDistrictList(Long city_id) {
		String key = "CACHE_ES_REGIONS_ZB_"+city_id;
		SerializeList serializeList = (SerializeList)cache.get(NAMESPACE, key);
		List list = new ArrayList();
		if(null != serializeList) {
			list = serializeList.getObj();
		} else {
			serializeList = new SerializeList();
			String sql = "select code,name from es_regions_zb where add_level = '3' and pre_code=? ";
			list = this.baseDaoSupport.queryForList(sql,city_id);
			serializeList.setObj(list);
			cache.set(NAMESPACE, key, serializeList);
		}
		return list;
	}
	@Override
	public Map getBuyInfoByMemberId(String member_id) {
	    String sql = "select mobile,tel,address from es_member  where member_id =?";
	    Map map = new HashMap();
	    map = this.baseDaoSupport.queryForMap(sql, member_id);
		return map;
	}
	@Override
	public void updateBuyInfoByMemberId(String[] str) {
		String sql = "update es_member set mobile=?,tel=?,address=? where member_id=?";
		
		this.baseDaoSupport.execute(sql, str);
	}
	@Override
	public void insertOrderHandLog(OrderHandleLogsReq req) {
		AdminUser adminUser = ManagerUtils.getAdminUser();
		if(adminUser!=null && StringUtils.isEmpty(req.getOp_id())){
			String  user_id  = adminUser.getUserid();
			String  user_name = adminUser.getUsername();
			req.setOp_id(user_id);
			req.setOp_name(user_name);
		}
		if(StringUtil.isEmpty(req.getOp_id())){
			req.setOp_id("1");
			req.setOp_name("系统管理员");
		}
		req.setCreate_time("sysdate");
	    this.baseDaoSupport.insert("es_order_handle_logs", req);
	}
	@Override
	public void insertOrderExceptionLog(OrderExceptionLogsReq req) {
		req.setMark_time("sysdate");
		req.setException_id(daoSupport.getSequences("ES_ORDER_EXCEPTION_LOGS_SEQ", "1", 18));
		req.setIs_deal("0");
	    this.baseDaoSupport.insert("es_order_exception_logs", req);
	}
	@Override
	public void updateOrderExceptionLog(String order_id) {
		AdminUser adminUser = ManagerUtils.getAdminUser();
		String user_id ="1";
		String user_name="系统管理员";
		if(adminUser!=null){
			user_id  = adminUser.getUserid();
			user_name = adminUser.getUsername();
		}
		String sql1 = "update es_order_exception_logs set deal_time=sysdate,deal_op_id=?,deal_op_name=?,is_deal=? where order_id=?";
		String[] str =new String[]{user_id,user_name,"1",order_id};
		this.baseDaoSupport.execute(sql1, str);
	}
	@Override
	public String getSpecErrorMsg(String order_id) {
		String msg  = "" ;
		String name = "" ;
		String goods_cat = CommonDataFactory.getInstance().getGoodSpec(order_id, null, SpecConsts.CAT_ID);
		//存费送机时 分月返还、手机款、首月返还、预存款 不能为空
		Map<String,String> spec_20002 = null;
		if(SpecConsts.CAT_ID_690302000.equals(goods_cat)){
			spec_20002 = CommonDataFactory.getInstance().getProductSpecs(order_id, SpecConsts.TYPE_ID_20002);
//			String mon_return = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_20002, null, SpecConsts.MON_RETURN);
//		    String mobile_price = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_20002, null, SpecConsts.MOBILE_PRICE);
//		    String order_return = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_20002, null, SpecConsts.ORDER_RETURN);
//		    String deposit_fee = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_20002, null, SpecConsts.DEPOSIT_FEE);
			String mon_return = spec_20002.get(SpecConsts.MON_RETURN);
		    String mobile_price = spec_20002.get(SpecConsts.MOBILE_PRICE);
		    String order_return = spec_20002.get(SpecConsts.ORDER_RETURN);
		    String deposit_fee = spec_20002.get(SpecConsts.DEPOSIT_FEE);
		    if(StringUtils.isEmpty(mon_return)){
		    	msg += "分月返还不能为空。";
		    	name += "mon_return,";
		    }
		    if(StringUtils.isEmpty(mobile_price)){
		    	msg +="手机款不能为空。";
		    	name += "mobile_price,";
		    }
		    if(StringUtils.isEmpty(order_return)){
		    	msg += "首月返还不能为空。";
		    	name += "order_return,";
		    }
		    if(StringUtils.isEmpty(deposit_fee)){
		    	msg +="预存款不能为空。";
		    	name += "deposit_fee,";
		    }
		}
		//购机送费  月送费金额、协议期总送费金额 不能为空
		if(SpecConsts.CAT_ID_690301000.equals(goods_cat)){ 
			String mon_give = "";
			String all_give = "";
			if(null != spec_20002) {
				
			}
			mon_give = spec_20002.get(SpecConsts.MON_GIVE);
		    all_give = spec_20002.get(SpecConsts.ALL_GIVE);
		    if(StringUtils.isEmpty(mon_give)){
		    	msg += "月送费金额不能为空。";
		    	name += "mon_give,";
		    }
		    if(StringUtils.isEmpty(all_give)){
		    	msg +="协议期总送费金额 不能为空。";
		    	name += "all_give,";
		    }
		}
		//类型为上网卡时 上网时长不能为空   颜色 品牌  型号 机型
		String goods_type = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_TYPE);
		if(EcsOrderConsts.GOODS_TYPE_WIFI_CARD.equals(goods_type)){
			String is_union = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_20001, null, SpecConsts.IS_GROUP);
			if(StringUtils.isEmpty(is_union)){
				msg +="是否全国卡不能为空。";
				name += "is_union,";
			}
		}
		//如果存在终端 那么终端信息是必填的
		String is_term = CommonDataFactory.getInstance().hasTerminal(order_id);
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		
		//终端存在时 颜色 容量 型号 机型编码 品牌不能为空
		if(EcsOrderConsts.DEFAULT_STR_ONE.equals(is_term)){
			Map<String,String> spec_10000 = CommonDataFactory.getInstance().getProductSpecs(order_id, SpecConsts.TYPE_ID_10000);
//			String color_name =  CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.COLOR_NAME);
//			String size = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.SIZE);
//			String model_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MODEL_NAME);
//			String machine_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.MACHINE_CODE);
//		    String brand_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10000, null, SpecConsts.BRAND_NAME);
		    String color_name = spec_10000.get(SpecConsts.COLOR_NAME);
			String size = spec_10000.get(SpecConsts.SIZE);
			String model_name = spec_10000.get(SpecConsts.MODEL_NAME);
			String machine_code = spec_10000.get(SpecConsts.MACHINE_CODE);
		    String brand_name = spec_10000.get(SpecConsts.BRAND_NAME);
		    
		    if(StringUtils.isEmpty(color_name)){
		    	msg +="终端颜色不能为空。";
		    	name += "term.color,";
		    }
		    if(StringUtils.isEmpty(size)){
		    	msg +="终端容量不能为空。";
		    	name += "term.size,";
		    }
		    if(StringUtils.isEmpty(model_name)){
		    	msg +="终端型号不能为空。";
		    	name += "term.model_name,";
		    }
		    if(StringUtils.isEmpty(machine_code)){
		    	msg +="终端机型不能为空。";
		    	name += "term.machine_code,";
		    }
		    if(StringUtils.isEmpty(brand_name)){
		    	msg +="终端品牌不能为空。";
		    	name += "term.brand_name,";
		    }
			
		}
		//是否存在上网卡硬件
		String has_TYPE_ID_10003 = CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10003);
		if(EcsOrderConsts.DEFAULT_STR_ONE.equals(has_TYPE_ID_10003)){
			Map<String,String> spec_10003 = CommonDataFactory.getInstance().getProductSpecs(order_id, SpecConsts.TYPE_ID_10003);
//			String color_name =  CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.COLOR_NAME);
//			String model_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.MODEL_NAME);
//			String machine_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.MACHINE_CODE);
//		    String brand_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10003, null, SpecConsts.BRAND_NAME);
		    
			String color_name = spec_10003.get(SpecConsts.COLOR_NAME);
			String model_name = spec_10003.get(SpecConsts.MODEL_NAME);
			String machine_code = spec_10003.get(SpecConsts.MACHINE_CODE);
		    String brand_name = spec_10003.get(SpecConsts.BRAND_NAME);
		    
		    if(StringUtils.isEmpty(color_name)){
		    	msg +="上网卡硬件颜色不能为空。";
		    	name += "netCard.color_name,";
		    }
		    if(StringUtils.isEmpty(model_name)){
		    	msg +="上网卡硬件型号不能为空。";
		    	name += "netCard.model_name,";
		    }
		    if(StringUtils.isEmpty(machine_code)){
		    	msg +="上网卡硬件机型不能为空。";
		    	name += "netCard.machine_code,";
		    }
		    if(StringUtils.isEmpty(brand_name)){
		    	msg +="上网卡硬件品牌不能为空。";
		    	name += "netCard.brand_name,";
		    }
		}
		String has_TYPE_ID_10006 = CommonDataFactory.getInstance().hasTypeOfProduct(order_id, SpecConsts.TYPE_ID_10006);
		if(EcsOrderConsts.DEFAULT_STR_ONE.equals(has_TYPE_ID_10006)){
			Map<String,String> spec_10006 = CommonDataFactory.getInstance().getProductSpecs(order_id, SpecConsts.TYPE_ID_10006);
//			String color_name =  CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.COLOR_NAME);
//			String model_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.MODEL_NAME);
//			String machine_code = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.MACHINE_CODE);
//		    String brand_name = CommonDataFactory.getInstance().getProductSpec(order_id, SpecConsts.TYPE_ID_10006, null, SpecConsts.BRAND_NAME);
			String color_name = spec_10006.get(SpecConsts.COLOR_NAME);
			String model_name = spec_10006.get(SpecConsts.MODEL_NAME);
			String machine_code = spec_10006.get(SpecConsts.MACHINE_CODE);
		    String brand_name = spec_10006.get(SpecConsts.BRAND_NAME);
		  
		    if(StringUtils.isEmpty(color_name)){
		    	msg +="配件颜色不能为空。";
		    	name += "accessories.color_name,";
		    }
		    if(StringUtils.isEmpty(model_name)){
		    	msg +="配件型号不能为空。";
		    	name += "accessories.model_name,";
		    }
		    if(StringUtils.isEmpty(machine_code)){
		    	msg +="配件机型不能为空。";
		    	name += "accessories.machine_code,";
		    }
		    if(StringUtils.isEmpty(brand_name)){
		    	msg +="配件品牌不能为空。";
		    	name += "accessories.brand_name,";
		    }
		}
		return msg+"|"+name;
	}
	@Override
	public List getDevelopmentName(String code,String order_from) {
		String sql  = "select development_name from es_development_code  where development_code =? and source_from_id=?";
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql, code,order_from);
		return list;
	}
	@Override
	public String getCompanyNameByCode(String code) {
		String sql ="select name from es_logi_company  where id ='"+code+"'";
        String name = this.baseDaoSupport.queryForString(sql);
		return name;
	}
	
	@Override
	public Boolean executeOrderReRule(String orderId,OrderHandleLogsReq req){
		TacheFact fact = new TacheFact();
		fact.setOrder_id(orderId);
		//获取方案信息
		Map m = AttrUtils.getInstance().getRefundPlanInfo(orderId);
		String plan_mode = m.get("plan_id").toString();
		String rule_mode = m.get("app_rule_id").toString();
		RuleTreeExeResp resp = RuleFlowUtil.executeRuleTree(plan_mode, rule_mode, fact,true,true,EcsOrderConsts.DEAL_FROM_INF);
		if(resp!=null && "0".equals(resp.getError_code())){
			//写日志
			String flow_id = resp.getFact().getFlow_id();
			String flowTraceId = resp.getFact().getTrace_flow_id();
			req.setOrder_id(orderId);
			req.setFlow_id(flow_id);
			req.setFlow_trace_id(flowTraceId);
			this.insertOrderHandLog(req);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public BusiDealResult offLineOpenAccount(String order_id){
		BusiDealResult result = new BusiDealResult();
		try{
			 OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			 OrderExtBusiRequest oe = orderTree.getOrderExtBusiRequest();
			 String mode_code = oe.getOrder_model();
			 String curr_flow_id = oe.getFlow_trace_id();
			 String is_aop = oe.getIs_aop();
			 //String syn_ord_wms = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.SYN_ORD_WMS);
			 //如果是自动化并是客户回访环节，转手工开户强制转换为人工集中模式
			 if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(curr_flow_id) && EcsOrderConsts.ORDER_MODEL_01.equals(mode_code)){
				 oe.setOrder_id(order_id);
				 oe.setOrder_model(EcsOrderConsts.ORDER_MODEL_02);
				 oe.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				 oe.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				 oe.store();
			 }
			 
			 orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
			 TacheFact fact = new TacheFact();
			 fact.setOrder_id(order_id);
			 String curr_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
			 
			 //总部标记异常
			 BusiCompRequest busi = new BusiCompRequest();
			 Map queryParams = new HashMap();
			 queryParams.put("exception_type", EcsOrderConsts.ABNORMAL_TYPE_STOCKOUT);
			 queryParams.put("exception_remark", "缺货异常");
			 queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			 queryParams.put("order_id", order_id);
			 busi.setEnginePath("zteCommonTraceRule.markedException");
			 busi.setOrder_id(order_id);
			 busi.setQueryParams(queryParams);
			 BusiCompResponse response = orderServices.execBusiComp(busi);
			 
			 if(!EcsOrderConsts.RULE_EXE_FLAG_SUCC.equals(response.getError_code())){
				 result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_FAIL);
				 result.setError_msg(response.getError_msg());
				 return result;
			 }
			 try{
				//如果当前环节是预校验 则强制转化为伪自动化异常
				 // 预校验加个按钮(转手工开户)
			     //   ---订单强制转伪自动化异常
			     //   ---流转到开户不节(跳过预拣货环节)
				 OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
				 if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(curr_trace_id)){
			 		 orderExtBusiRequest.setOrder_id(order_id);
			    	 orderExtBusiRequest.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
					 orderExtBusiRequest.setAbnormal_status(EcsOrderConsts.ORDER_ABNORMAL_STATUS_1);
					 orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					 orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					 orderExtBusiRequest.store();
					 //走预校验下一步流程====
					 String ruleId = "";
					 if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
						 ruleId = EcsOrderConsts.YJY_NEXT_RULE_ID;
					 }else if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)){
						 ruleId = EcsOrderConsts.YJY_NEXT_RULE_ID_BSSKL;
					 } else {
						 ruleId = EcsOrderConsts.YJY_NEXT_RULE_ID_AOP;
					 }
					 RuleTreeExeResp rresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.ORDER_PRE_DEAL_PLAN, ruleId, fact,false,false,EcsOrderConsts.DEAL_FROM_PAGE);
					 orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				     String nextFlorTraceID = orderTree.getOrderExtBusiRequest().getFlow_trace_id(); 
				     if(EcsOrderConsts.DIC_ORDER_NODE_C.equals(nextFlorTraceID)){
				    	 //如果下一个环节为预拣货环节侧跳过
				    	 String order_mode = orderTree.getOrderExtBusiRequest().getOrder_model();
						 String yjh_next = getYJHNextRuleId(order_mode,is_aop);
				    	 RuleTreeExeResp rjhresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.YJH_PLAN_ID, yjh_next, fact,false,false,EcsOrderConsts.DEAL_FROM_PAGE);
				     }
				 }else if(EcsOrderConsts.DIC_ORDER_NODE_C.equals(curr_trace_id)){
					 orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					 String order_mode = orderTree.getOrderExtBusiRequest().getOrder_model();
					 String yjh_next = getYJHNextRuleId(order_mode,is_aop);
					 //流转到开户不节
					 RuleTreeExeResp rjhresp = RuleFlowUtil.executeRuleTree(EcsOrderConsts.YJH_PLAN_ID, yjh_next, fact,false,false,EcsOrderConsts.DEAL_FROM_PAGE);
				 }
				 //记录为线下开户
				 OrderExtBusiRequest orderExt = CommonDataFactory.getInstance().getOrderTree(order_id).getOrderExtBusiRequest();
				 orderExt.setOpen_acc_mode(EcsOrderConsts.OPEN_ACC_OFFLINE);
				 orderExt.setIs_dirty(ConstsCore.IS_DIRTY_YES);
				 orderExt.setDb_action(ConstsCore.DB_ACTION_UPDATE);
				 orderExt.store();
			 }catch(Exception ex){
				 //如果失败但标志异常成功调异常恢复业务组件
				 orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
				 OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
				 BusiCompRequest rbusi = new BusiCompRequest();
				 Map rqueryParams = new HashMap();
				 rqueryParams.put("order_id", order_id);
				 rqueryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
				 rqueryParams.put(EcsOrderConsts.EXCEPTION_TYPE, orderExtBusiRequest.getException_type());
				 rqueryParams.put(EcsOrderConsts.EXCEPTION_REMARK, orderExtBusiRequest.getException_desc());
				 rbusi.setEnginePath("zteCommonTraceRule.restorationException");
				 rbusi.setOrder_id(order_id);
				 rbusi.setQueryParams(rqueryParams);
				 ZteResponse rresponse = null;
				 try {
					 rresponse = orderServices.execBusiComp(busi);
				 } catch (Exception e) {
				 	 CommonTools.addError("1", "恢复异常失败");
				 }
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
		}
		result.setError_code(EcsOrderConsts.BUSI_DEAL_RESULT_0000);
		result.setError_msg("转手工开户处理成功");
		return result;
	}
	
	private String getYJHNextRuleId(String order_mode, String is_aop){
		String yjh_next = "";
		if (EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_ZB.equals(is_aop)) {
			yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_01;
			if(EcsOrderConsts.ORDER_MODEL_02.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_02;
			}else if(EcsOrderConsts.ORDER_MODEL_03.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_03;
			}else if(EcsOrderConsts.ORDER_MODEL_04.equals(order_mode)){
				yjh_next=  EcsOrderConsts.YJH_NEXT_RULE_ID_04;
			}else if(EcsOrderConsts.ORDER_MODEL_05.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_05;
			}
		}else if(EcsOrderConsts.ACCOUNT_OPEN_CHANNEL_BSS.equals(is_aop)){
			yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_01_BSSKL;
			if(EcsOrderConsts.ORDER_MODEL_02.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_02_BSSKL;
			}else if(EcsOrderConsts.ORDER_MODEL_03.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_03_BSSKL;
			}else if(EcsOrderConsts.ORDER_MODEL_04.equals(order_mode)){
				yjh_next=  EcsOrderConsts.YJH_NEXT_RULE_ID_04_BSSKL;
			}else if(EcsOrderConsts.ORDER_MODEL_05.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_05_BSSKL;
			}
		} else {
			yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_01_AOP;
			if(EcsOrderConsts.ORDER_MODEL_02.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_02_AOP;
			}else if(EcsOrderConsts.ORDER_MODEL_03.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_03_AOP;
			}else if(EcsOrderConsts.ORDER_MODEL_04.equals(order_mode)){
				yjh_next=  EcsOrderConsts.YJH_NEXT_RULE_ID_04_AOP;
			}else if(EcsOrderConsts.ORDER_MODEL_05.equals(order_mode)){
				yjh_next = EcsOrderConsts.YJH_NEXT_RULE_ID_05_AOP;
			}
		}
		return yjh_next;
	}
	
	public void ruleExeLogArchive(String order_id){
		String sql = "insert into es_rule_exe_log_his(log_id, rule_id, create_time, exe_result, result_msg, source_from, plan_id,"+
			" obj_id, children_error, children_info, engine_path, flow_inst_id, fact_class, "+
			" time_num) select t.log_id, t.rule_id, t.create_time, t.exe_result, t.result_msg, t.source_from, "+
			" t.plan_id, t.obj_id, t.children_error, t.children_info, t.engine_path, "+
			" t.flow_inst_id, t.fact_class, t.time_num from es_rule_exe_log t where t.obj_id=?";
		String del = "delete from es_rule_exe_log t where t.obj_id=?";
		//****************** add by qin.yingxiong at 2016/10/20 start *******************
		boolean useOrgTable = RuleUtil.useOrgTable(order_id);
		if(useOrgTable) {
			sql = RuleUtil.replaceTableNameForOrgBySql(sql);
			del = RuleUtil.replaceTableNameForOrgBySql(del);
		}
		//****************** add by qin.yingxiong at 2016/10/20 end *******************
		this.baseDaoSupport.execute(sql, order_id);
		this.baseDaoSupport.execute(del, order_id);
	}
	
	@Override
	public BusiCompResponse startNewOrderFlow(String order_id) throws Exception {
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id", order_id);
		queryParams.put("start", true);//强制启动工作流
		queryParams.put("exe_plan", false);//启动完工作流后是否执行方案
		busi.setEnginePath("zteCommonTraceRule.startWorkFlow");//启动工作流业务组件
		busi.setOrder_id(order_id);
		busi.setQueryParams(queryParams);
		BusiCompResponse response = orderServices.execBusiComp(busi);
		return response;
	}
	@Override
	public BusiCompResponse reBackToPrepick(String order_id) throws Exception {
		startNewOrderFlow(order_id);//重启工作流
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String order_mode = orderExtBusiRequest.getOrder_model();
		String abnormal_type = orderExtBusiRequest.getAbnormal_type();
		orderExtBusiRequest.setOrder_id(order_id);
		if(EcsOrderConsts.OPER_MODE_ZD.equals(order_mode)){
			orderExtBusiRequest.setOld_order_model(order_mode);//记录旧的生产模式
			orderExtBusiRequest.setOrder_model(EcsOrderConsts.OPER_MODE_RG);//如果为自动化模式侧修改为人工集中模式
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ORDER_MODEL}, new String[]{EcsOrderConsts.OPER_MODE_RG});
		}
		if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)){//如果为自动化异常修改为伪自动化异常
			orderExtBusiRequest.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
		}
		orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);//订单设置为可见订单
		orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_1);//修改为异常单流程
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		
		/*if(!(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)||EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type))){
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put("order_id", order_id);
			queryParams.put("exception_type", EcsOrderConsts.ABNORMAL_TYPE_OPEN);
			queryParams.put("exception_remark", "写卡异常，回退到预捡货。");
			queryParams.put("write_exp_flag", EcsOrderConsts.BASE_YES_FLAG_1);
			busi.setEnginePath("zteCommonTraceRule.markedException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try {
				BusiCompResponse response = orderServices.execBusiComp(busi);
				if(response==null || !"0".equals(response.getError_code())){
					return response;
				}
			} catch (Exception e) {
				
			}
		}*/
		
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> workNo = dcPublicCache.getList(EcsOrderConsts.work_flow_staff_no);
		Map workNoMap = workNo.get(0);
		int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
		String staffName = (String) workNoMap.get("pname");
		String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		String flowStatus = Const.FLOW_DEAL_TYPE_AM;
		
		//环节流转到预拣货环节
		while (true) {
			//如果是预拣货环节侧跳出
			if(EcsOrderConsts.DIC_ORDER_NODE_C.equals(flow_trace_id))break ;
			UosDealReq ureq = new UosDealReq();
			ureq.setStaffId(staffId);
			ureq.setStaffName("");
			ureq.setProcessInstanceId(processInstanceId);
			ureq.setTacheCode(flow_trace_id);
			ureq.setDealType(flowStatus);// 环节处理状态
			UosDealResp flowresp = uosService.dealProcess(ureq);// 工作流返回下一个环节的信息
			ArrayList<WorkItem> workItems = flowresp.getWorkItems();
			// 修改环节为下一个环节
			// 修改订单处理状态为未处理
			if (workItems != null && workItems.size() > 0) {
				// 目前只有一个不节，所以只拿第一个就行了
				WorkItem wi = workItems.get(0);
				flow_trace_id = wi.getTacheCode();
				if (EcsOrderConsts.DIC_ORDER_NODE_C.equals(flow_trace_id)) {
					//跳转到预拣货环节
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
					ordext.setFlow_trace_id(flow_trace_id);
					ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					ordext.store();
					break;
				} else if (flowresp.isEnd()) {
					// 结束流程
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
					ordext.setFlow_trace_id(flow_trace_id);
					ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					ordext.store();
					break;
				}
			}
		}
		ruleExeLogArchive(order_id);//订单规则执行记录规则档
		updateExpStatus(order_id,"预捡货");
		//执行预拣货方案
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id",order_id);
		busi.setEnginePath("enterTraceRule.exec");
		busi.setOrder_id(order_id);
		queryParams.put("deal_from",EcsOrderConsts.DEAL_FROM_PAGE);
		busi.setQueryParams(queryParams);
		queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_0);
		BusiCompResponse response = orderServices.execBusiComp(busi);
		return response;
	}
	
	private void updateExpStatus(final String order_id,final String trace_name){
		try{
			ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
			String is_exception_run = cacheUtil.getConfigInfo(EcsOrderConsts.IS_EXCEPTION_RUN);//是否启用异常系统
			if(!StringUtil.isEmpty(is_exception_run) && new Integer(is_exception_run).intValue() != 0 ){//异常系统启动标识
				OrderExpMarkProcessedReq req = new OrderExpMarkProcessedReq();
				req.setRel_obj_id(order_id);
				req.setRel_obj_type("order");
				req.setDeal_result("订单回退到"+trace_name+"环节");
				req.setDeal_staff_no("-1");
				req.setRequest_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				String exe_type = MqEnvGroupConfigSetting.ORD_EXP_EXEC;//调用方式 m/d  m是走mq d是走dubbo
				if(ConstsCore.DECOUPLING_EXEC_D.equals(exe_type)){
					markExpProccedByDubbo(req);
				}else{
					markExpProccedByMq(req);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void markExpProccedByDubbo(OrderExpMarkProcessedReq req){
		ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
		OrderExpMarkProcessedResp response = client.execute(req, OrderExpMarkProcessedResp.class);
	}
	
	private ZteResponse markExpProccedByMq(OrderExpMarkProcessedReq req){
		AsynExecuteMsgWriteMqReq mqReq = new AsynExecuteMsgWriteMqReq();
		mqReq.setService_code(req.getApiMethodName());
		mqReq.setVersion(ConstsCore.DUBBO_DEFAULT_VERSION);
		mqReq.setZteRequest((ZteRequest)req);
		mqReq.setConsume_env_code(com.ztesoft.common.util.BeanUtils.getHostEnvCodeByEnvStatus(ConstsCore.MACHINE_EVN_CODE_ECSORD_EXP));
		IOrderQueueBaseManager  orderQueueBaseManager = SpringContextHolder.getBean("orderQueueBaseManager");
	    return orderQueueBaseManager.asynExecuteMsgWriteMq(mqReq);
	}
	
	public IOrderServices getOrderServices() {
		return orderServices;
	}
	public void setOrderServices(IOrderServices orderServices) {
		this.orderServices = orderServices;
	}
	
	@Override
	public NumberStateChangeZBResponse changePhone(String order_id,String occupiedFlag,String changeTag,String old_phone_num,String old_pro_key,String proKey,String resourceCode) throws ApiBusiException{
	    NumberStateChangeZBResponse  resp = new NumberStateChangeZBResponse();
	    //号码状态变更
	    ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
	    NumberStateChangeZBRequest req=new NumberStateChangeZBRequest();
	    req.setNotNeedReqStrOrderId(order_id);//订单外部单号   
	    List<ResourcesInfo> list = new ArrayList<ResourcesInfo>();
	    ResourcesInfo resourcesInfo = new ResourcesInfo();
	    resourcesInfo.setProKey(proKey);
	    resourcesInfo.setResourcesCode(resourceCode);
	    resourcesInfo.setOccupiedFlag(occupiedFlag);//0 不预占；1 预占；2 预订（未付费）；3 预订（付费）；4 释放资源【一键开户的才用此接口】
	    resourcesInfo.setSnChangeTag(changeTag);//0：号码不变更；1：号码变更
	    resourcesInfo.setOldKey(old_pro_key);//原资源预占关键字(修改时必传)
	    resourcesInfo.setOldResourcesCode(old_phone_num);//原资源唯一标识（ SnChangeTag=1生效）
	    list.add(resourcesInfo);
	    req.setResourcesInfo(list);
	    resp =  client.execute(req,NumberStateChangeZBResponse.class);
	    return resp;
	}
	@Override
	public BusiCompResponse reBackToVisit(String order_id) throws Exception {
		startNewOrderFlow(order_id);//重启工作流
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String order_mode = orderExtBusiRequest.getOrder_model();
		String abnormal_type = orderExtBusiRequest.getAbnormal_type();
		orderExtBusiRequest.setOrder_id(order_id);
		if(EcsOrderConsts.OPER_MODE_ZD.equals(order_mode)){
			orderExtBusiRequest.setOld_order_model(order_mode);//记录旧的生产模式
			orderExtBusiRequest.setOrder_model(EcsOrderConsts.OPER_MODE_RG);//如果为自动化模式侧修改为人工集中模式
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ORDER_MODEL}, new String[]{EcsOrderConsts.OPER_MODE_RG});
		}
		if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)
				&&!EcsOrderConsts.OPER_MODE_XK.equals(order_mode))		{//如果为自动化异常修改为伪自动化异常
			orderExtBusiRequest.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
		}
		orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);//订单设置为可见订单
		orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_1);//修改为异常单流程
		orderExtBusiRequest.setOpen_acc_mode(ConstsCore.NULL_VAL);//清除之前的线下开户设置
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		
		/*if(!(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)||EcsOrderConsts.ORDER_ABNORMAL_TYPE_2.equals(abnormal_type))){
			BusiCompRequest busi = new BusiCompRequest();
			Map queryParams = new HashMap();
			queryParams.put(EcsOrderConsts.EXCEPTION_FROM, EcsOrderConsts.EXCEPTION_FROM_ORD);
			queryParams.put("order_id", order_id);
			queryParams.put("exception_type", EcsOrderConsts.ABNORMAL_TYPE_OPEN);
			queryParams.put("exception_remark", "写卡异常，回退到订单审核。");
			queryParams.put("write_exp_flag", EcsOrderConsts.BASE_YES_FLAG_1);
			busi.setEnginePath("zteCommonTraceRule.markedException");
			busi.setOrder_id(order_id);
			busi.setQueryParams(queryParams);
			try {
				BusiCompResponse response = orderServices.execBusiComp(busi);
				if(response==null || !"0".equals(response.getError_code())){
					return response;
				}
			} catch (Exception e) {
				
			}
		}*/
		
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> workNo = dcPublicCache.getList(EcsOrderConsts.work_flow_staff_no);
		Map workNoMap = workNo.get(0);
		int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
		String staffName = (String) workNoMap.get("pname");
		String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		String flowStatus = Const.FLOW_DEAL_TYPE_AM;
		
		//环节流转到预拣货环节
		while (true) {
			//如果是预拣货环节侧跳出
			if(EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id))break ;
			UosDealReq ureq = new UosDealReq();
			ureq.setStaffId(staffId);
			ureq.setStaffName("");
			ureq.setProcessInstanceId(processInstanceId);
			ureq.setTacheCode(flow_trace_id);
			ureq.setDealType(flowStatus);// 环节处理状态
			UosDealResp flowresp = uosService.dealProcess(ureq);// 工作流返回下一个环节的信息
			ArrayList<WorkItem> workItems = flowresp.getWorkItems();
			// 修改环节为下一个环节
			// 修改订单处理状态为未处理
			if (workItems != null && workItems.size() > 0) {
				// 目前只有一个不节，所以只拿第一个就行了
				WorkItem wi = workItems.get(0);
				flow_trace_id = wi.getTacheCode();
				if (EcsOrderConsts.DIC_ORDER_NODE_B.equals(flow_trace_id)) {
					//跳转到预拣货环节
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
					ordext.setFlow_trace_id(flow_trace_id);
					ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					ordext.store();
					break;
				} else if (flowresp.isEnd()) {
					// 结束流程
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
					ordext.setFlow_trace_id(flow_trace_id);
					ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					ordext.store();
					break;
				}
			}
		}
		ruleExeLogArchive(order_id);//订单规则执行记录规则档
		updateExpStatus(order_id,"订单审核");
		//执行预拣货方案
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id",order_id);
		busi.setEnginePath("enterTraceRule.exec");
		busi.setOrder_id(order_id);
		queryParams.put("deal_from",EcsOrderConsts.DEAL_FROM_PAGE);
		busi.setQueryParams(queryParams);
		queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_0);
		BusiCompResponse response = orderServices.execBusiComp(busi);
		return response;
	}
	
	@Override
	public List<OrderExceptionLogsReq> queryExceptionLogsList(String order_id){
		String sql = "select * from es_order_exception_logs where abnormal_type in (2,3) and is_deal = 0 and order_id=? ";
		List<OrderExceptionLogsReq> exceptionLogs = this.baseDaoSupport.queryForList(sql, OrderExceptionLogsReq.class, order_id);
		return exceptionLogs;
	}

	//根据商城来源+证件号码+证件姓名查询未绑定的预售单
	public List<Iphone6sReq> getRelationOrders(String orderid,String orderFrom,String certName,String certNum){
		//查询同来源+同证件+未绑定过的预售订单
		String sql = "select distinct * from (select ta.out_tid,ta.order_id,ta.related_order_id,ta.wm_isreservation_from,td.phone_owner_name cert_card_name,tb.cert_card_num,td.development_code,td.development_name,te.paymoney " 
				+"from es_order_items_prod_ext tb "
				+"left join es_order_ext ta on ta.order_id=tb.order_id "
				+"left join es_order_extvtl td on ta.order_id=td.order_id "
				+"left join ES_ORDER te on ta.order_id=te.order_id "
				+"where ta.source_from=? and ta.related_order_id is null and ta.order_from=? and td.phone_owner_name=? and tb.cert_card_num=? and ta.wm_isreservation_from='1' and ta.flow_trace_id='B' order by ta.out_tid)";
		
		//查询结果
		List<Iphone6sReq> rsList = this.baseDaoSupport.queryForList(sql,Iphone6sReq.class,ManagerUtils.getSourceFrom(),orderFrom,certName,certNum);
		
		return rsList;
	}
	@Override
	public void insertOrderOutCallLog(Map req) {
		// TODO Auto-generated method stub
		String log_id=this.baseDaoSupport.getSequences("o_outcall_log");
		req.put("log_id",log_id);
//		this.baseDaoSupport.in
	    this.baseDaoSupport.insert("es_order_outcall_log", req);
	}
	@Override
	public void addOrderHandlerLog(String order_id,String comments,String handler_type) {
		OrderTreeBusiRequest ot = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderHandleLogsReq logReq = new OrderHandleLogsReq();
		logReq.setOrder_id(order_id);
		logReq.setFlow_id(ot.getOrderExtBusiRequest().getFlow_id());
		logReq.setFlow_trace_id(ot.getOrderExtBusiRequest().getFlow_trace_id());
		logReq.setComments(comments);
		logReq.setOp_id(ManagerUtils.getUserId());
		logReq.setOp_name(ManagerUtils.getAdminUser().getUsername());
		logReq.setHandler_type(handler_type);
	    this.insertOrderHandLog(logReq);
	}
	
	@Override
	public BusiCompResponse reBackToVisitNEW(String order_id) throws Exception {
		startNewOrderFlow(order_id);//重启工作流
		OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
		String order_mode = orderExtBusiRequest.getOrder_model();
		String abnormal_type = orderExtBusiRequest.getAbnormal_type();
		orderExtBusiRequest.setOrder_id(order_id);
		if(EcsOrderConsts.OPER_MODE_ZD.equals(order_mode)){
			orderExtBusiRequest.setOld_order_model(order_mode);//记录旧的生产模式
			orderExtBusiRequest.setOrder_model(EcsOrderConsts.OPER_MODE_RG);//如果为自动化模式侧修改为人工集中模式
			CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ORDER_MODEL}, new String[]{EcsOrderConsts.OPER_MODE_RG});
		}
		if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)
				&&!EcsOrderConsts.OPER_MODE_XK.equals(order_mode))		{//如果为自动化异常修改为伪自动化异常
			orderExtBusiRequest.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
		}
		orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);//订单设置为可见订单
		orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_1);//修改为异常单流程
		orderExtBusiRequest.setOpen_acc_mode(ConstsCore.NULL_VAL);//清除之前的线下开户设置
		orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
		orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
		orderExtBusiRequest.store();
		
		orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
		String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
		
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		List<Map> workNo = dcPublicCache.getList(EcsOrderConsts.work_flow_staff_no);
		Map workNoMap = workNo.get(0);
		int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
		String staffName = (String) workNoMap.get("pname");
		String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
		String flowStatus = Const.FLOW_DEAL_TYPE_AM;
		
		//环节流转到预拣货环节
		while (true) {
			//如果是预拣货环节侧跳出
			if(EcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id))break ;
			UosDealReq ureq = new UosDealReq();
			ureq.setStaffId(staffId);
			ureq.setStaffName("");
			ureq.setProcessInstanceId(processInstanceId);
			ureq.setTacheCode(flow_trace_id);
			ureq.setDealType(flowStatus);// 环节处理状态
			UosDealResp flowresp = uosService.dealProcess(ureq);// 工作流返回下一个环节的信息
			ArrayList<WorkItem> workItems = flowresp.getWorkItems();
			// 修改环节为下一个环节
			// 修改订单处理状态为未处理
			if (workItems != null && workItems.size() > 0) {
				// 目前只有一个不节，所以只拿第一个就行了
				WorkItem wi = workItems.get(0);
				flow_trace_id = wi.getTacheCode();
				if (EcsOrderConsts.DIC_ORDER_NODE_L.equals(flow_trace_id)) {
					//跳转到预拣货环节
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
					ordext.setFlow_trace_id(flow_trace_id);
					ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					ordext.store();
					break;
				} else if (flowresp.isEnd()) {
					// 结束流程
					orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
					OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
					ordext.setFlow_trace_id(flow_trace_id);
					ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
					ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
					ordext.store();
					break;
				}
			}
		}
		ruleExeLogArchive(order_id);//订单规则执行记录规则档
		updateExpStatus(order_id,"订单审核");
		//执行预拣货方案
		BusiCompRequest busi = new BusiCompRequest();
		Map queryParams = new HashMap();
		queryParams.put("order_id",order_id);
		busi.setEnginePath("enterTraceRule.exec");
		busi.setOrder_id(order_id);
		queryParams.put("deal_from",EcsOrderConsts.DEAL_FROM_PAGE);
		busi.setQueryParams(queryParams);
		queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_0);
		BusiCompResponse response = orderServices.execBusiComp(busi);
		return response;
	}
    @Override
    public List<Map<String, String>> getServiceCodeByObjectId(String out_tid) {
        String sql  = "select service_code from es_order_queue_his  where object_id=? and source_from = '" +ManagerUtils.getSourceFrom()+"'";
        List<Map<String, String>> list = this.baseDaoSupport.queryForList(sql, out_tid);
        return list;
    }
    @Override
    public List<Map> getTearminalObjectQuery(String order_id) {
        List<Map> mapList = new ArrayList<Map>();
        List<ObjectQryVO> object  = new ArrayList<ObjectQryVO>();
        ZteClient client = ClientFactory.getZteDubboClient(ManagerUtils.getSourceFrom());
        ObjectQryReq req = new ObjectQryReq();
        req.setNotNeedReqStrOrderId(order_id);
        req.setService_type("6116");
        EssOperatorInfoUtil essinfo = new EssOperatorInfoUtil();
        EmpOperInfoVo essOperInfo = null;
        ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
        String groupFlowKg = cacheUtil.getConfigInfo("groupFlowKg");
        String order_city_code = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_CITY_CODE);//地市// 4位
        String order_from = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.ORDER_FROM);//地市
        String cat_id = CommonDataFactory.getInstance().getAttrFieldValue(order_id, AttrConsts.GOODS_CAT_ID);//地市
        if(StringUtils.isNotEmpty(groupFlowKg) &&"1".equals(groupFlowKg)&&"10093".equals(order_from) && "221668199563784192".equals(cat_id)){
             essOperInfo = essinfo.getEmpInfoFromDataBase(null,"WFGROUPUPINTENT",order_city_code);//无线宽带线上取默认的渠道和工号信息
        }
        if(essOperInfo != null){
            req.setOperator_id(essOperInfo.getEss_emp_id());
        }
        ObjectQryResp infResp = client.execute(req, ObjectQryResp.class);
        if(!EcsOrderConsts.INF_RESP_CODE_00000.equals(infResp.getCode())){
        }else{
            object = infResp.getRespJson();
            for (int i = 0; i < object.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("value", object.get(i).getObject_id());
                map.put("value_desc", object.get(i).getObject_name());
                mapList.add(map);
            }
        }
        return mapList;
    }
	@Override
	public String getCountyName(String districtCode, String countyId) {

		String sql = "";
		if (!org.apache.commons.lang.StringUtils.isBlank(districtCode)) {
			sql = "select re.other_field_value_desc from es_dc_public_dict_relation re where re.field_value='ZJ"
					+ districtCode + "'";
		}

		if (!org.apache.commons.lang.StringUtils.isBlank(countyId)) {
			sql = "select re.other_field_value_desc from es_dc_public_dict_relation re where re.other_field_value='"
					+ countyId + "'";
		}

		logger.info("订单详情县份查询sql：" + sql);

		return this.baseDaoSupport.queryForString(sql);
	}
	
	@Override
    public BusiCompResponse reBackToInventoryKeeper(String order_id,String flow_tran_id) throws Exception {
        startNewOrderFlow(order_id);//重启工作流
        OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        OrderExtBusiRequest orderExtBusiRequest = orderTree.getOrderExtBusiRequest();
        String order_mode = orderExtBusiRequest.getOrder_model();
        String abnormal_type = orderExtBusiRequest.getAbnormal_type();
        orderExtBusiRequest.setOrder_id(order_id);
        if(EcsOrderConsts.OPER_MODE_ZD.equals(order_mode)){
            orderExtBusiRequest.setOld_order_model(order_mode);//记录旧的生产模式
            orderExtBusiRequest.setOrder_model(EcsOrderConsts.OPER_MODE_RG);//如果为自动化模式侧修改为人工集中模式
            CommonDataFactory.getInstance().updateAttrFieldValue(order_id, new String[]{AttrConsts.ORDER_MODEL}, new String[]{EcsOrderConsts.OPER_MODE_RG});
        }
        if(EcsOrderConsts.ORDER_ABNORMAL_TYPE_3.equals(abnormal_type)
                &&!EcsOrderConsts.OPER_MODE_XK.equals(order_mode))      {//如果为自动化异常修改为伪自动化异常
            orderExtBusiRequest.setAbnormal_type(EcsOrderConsts.ORDER_ABNORMAL_TYPE_2);
        }
        orderExtBusiRequest.setVisible_status(EcsOrderConsts.VISIBLE_STATUS_0);//订单设置为可见订单
        orderExtBusiRequest.setIs_exception_flow(EcsOrderConsts.IS_EXCEPTION_FLOW_1);//修改为异常单流程
        orderExtBusiRequest.setOpen_acc_mode(ConstsCore.NULL_VAL);//清除之前的线下开户设置
        orderExtBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
        orderExtBusiRequest.setDb_action(ConstsCore.DB_ACTION_UPDATE);
        orderExtBusiRequest.store();
        
        orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
        String flow_trace_id = orderTree.getOrderExtBusiRequest().getFlow_trace_id();
        
        DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
        List<Map> workNo = dcPublicCache.getList(EcsOrderConsts.work_flow_staff_no);
        Map workNoMap = workNo.get(0);
        int staffId = Integer.parseInt(workNoMap.get("pkey").toString());
        String staffName = (String) workNoMap.get("pname");
        String processInstanceId = orderTree.getOrderExtBusiRequest().getFlow_inst_id();
        String flowStatus = Const.FLOW_DEAL_TYPE_AM;
        
        //环节流转到预拣货环节
        while (true) {
            //如果是预拣货环节侧跳出
            if(EcsOrderConsts.DIC_ORDER_NODE_MK.equals(flow_trace_id))break ;
            UosDealReq ureq = new UosDealReq();
            ureq.setStaffId(staffId);
            ureq.setStaffName("");
            ureq.setProcessInstanceId(processInstanceId);
            ureq.setTacheCode(flow_trace_id);
            ureq.setDealType(flowStatus);// 环节处理状态
            UosDealResp flowresp = uosService.dealProcess(ureq);// 工作流返回下一个环节的信息
            ArrayList<WorkItem> workItems = flowresp.getWorkItems();
            // 修改环节为下一个环节
            // 修改订单处理状态为未处理
            if (workItems != null && workItems.size() > 0) {
                // 目前只有一个不节，所以只拿第一个就行了
                WorkItem wi = workItems.get(0);
                flow_trace_id = wi.getTacheCode();
                if (EcsOrderConsts.DIC_ORDER_NODE_MK.equals(flow_trace_id)) {
                    //跳转到预拣货环节
                    orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
                    OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
                    ordext.setFlow_trace_id(flow_trace_id);
                    ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                    ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                    ordext.store();
                    break;
                } else if (flowresp.isEnd()) {
                    // 结束流程
                    orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
                    OrderExtBusiRequest ordext = orderTree.getOrderExtBusiRequest();
                    ordext.setFlow_trace_id(flow_trace_id);
                    ordext.setIs_dirty(ConstsCore.IS_DIRTY_YES);
                    ordext.setDb_action(ConstsCore.DB_ACTION_UPDATE);
                    ordext.store();
                    break;
                }
            }
        }
        ruleExeLogArchive(order_id);//订单规则执行记录规则档
        updateExpStatus(order_id,"库管");
        //执行预拣货方案
        BusiCompRequest busi = new BusiCompRequest();
        Map queryParams = new HashMap();
        queryParams.put("order_id",order_id);
        busi.setEnginePath("enterTraceRule.exec");
        busi.setOrder_id(order_id);
        queryParams.put("deal_from",EcsOrderConsts.DEAL_FROM_PAGE);
        busi.setQueryParams(queryParams);
        queryParams.put("is_auto", EcsOrderConsts.RULE_EXE_0);
        BusiCompResponse response = orderServices.execBusiComp(busi);
        return response;
    }
	@Override
	public String flowGetNodeCode(String order_id) {
		String sql ="select ins.node_code from es_work_custom_node_ins ins where ins.order_id='"+order_id+"'  and ins.is_curr_step='1'";
		return this.baseDaoSupport.queryForString(sql);
	}
} 
