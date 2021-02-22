package com.ztesoft.net.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import model.EsearchSpecvalues;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import params.adminuser.req.AdminUserReq;
import params.adminuser.resp.AdminUserResp;
import params.req.EsearchAddReq;
import params.req.EsearchUpdateReq;
import params.req.OrderExpMarkProcessedReq;
import params.req.OrderExpWriteForBusReq;
import params.req.OrderExpWriteForInfReq;
import params.resp.EsearchAddResp;
import params.resp.EsearchUpdateResp;
import params.resp.OrderExpMarkProcessedResp;
import params.resp.OrderExpWriteResp;
import services.AdminUserInf;
import zte.net.ecsord.common.AttrConsts;
import zte.net.ecsord.common.CommonDataFactory;
import zte.net.ecsord.params.base.resp.BusiCompResponse;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;
import zte.net.ord.params.busi.req.OrderQueueBusiRequest;
import zte.net.params.req.PlanRuleTreeExeReq;
import zte.net.params.req.RuleTreeExeReq;
import zte.net.params.resp.PlanRuleTreeExeResp;
import zte.net.params.resp.RuleTreeExeResp;

import com.alibaba.rocketmq.common.ThreadFactoryImpl;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.AppKeyEnum;
import com.ztesoft.api.ClientFactory;
import com.ztesoft.api.ZteClient;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.ibss.common.util.DateUtil;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.common.CommonFilterFactory;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.filter.request.ExpFilterRequest;
import com.ztesoft.net.filter.response.FilterResponse;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.consts.EcsOrderConsts;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.ESearchData;
import com.ztesoft.net.model.EsearchExpInst;
import com.ztesoft.net.model.EsearchExpInstExt;
import com.ztesoft.net.model.EsearchExpInstQuery;
import com.ztesoft.net.model.EsearchExpInstSolution;
import com.ztesoft.net.param.inner.EsearchCatalogInner;
import com.ztesoft.net.param.inner.EsearchExpInstSolutionInner;
import com.ztesoft.net.param.inner.ExpInstBatchProcessedInner;
import com.ztesoft.net.param.inner.ExpInstInner;
import com.ztesoft.net.param.inner.ExpInstProcessedInner;
import com.ztesoft.net.param.inner.ExpInstQueryInner;
import com.ztesoft.net.param.inner.ExpInstSpecInner;
import com.ztesoft.net.param.inner.OrderExpAlarmInner;
import com.ztesoft.net.param.inner.OrderExpMarkProcessedInner;
import com.ztesoft.net.param.inner.OrderExpWriteInner;
import com.ztesoft.net.param.inner.SpecvaluesCheckProcessedInner;
import com.ztesoft.net.param.inner.SpecvaluesQueryInner;
import com.ztesoft.net.param.outer.EsearchCatalogOuter;
import com.ztesoft.net.param.outer.EsearchExpInstSolutionOuter;
import com.ztesoft.net.param.outer.ExpInstBatchProcessedOuter;
import com.ztesoft.net.param.outer.ExpInstOuter;
import com.ztesoft.net.param.outer.ExpInstProcessedOuter;
import com.ztesoft.net.param.outer.ExpInstQueryOuter;
import com.ztesoft.net.param.outer.ExpInstSpecCatalogResp;
import com.ztesoft.net.param.outer.OrderExpAlarm;
import com.ztesoft.net.param.outer.OrderExpAlarmOuter;
import com.ztesoft.net.param.outer.OrderExpMarkProcessedOuter;
import com.ztesoft.net.param.outer.SpecvaluesCheckProcessedOuter;
import com.ztesoft.net.param.outer.SpecvaluesQueryOuter;
import com.ztesoft.net.param.outer.TopExpKey;
import com.ztesoft.net.search.conf.EsearchValues;
import com.ztesoft.net.service.IEsearchSpecvaluesManager;
import com.ztesoft.net.service.IExpConfigManager;
import com.ztesoft.net.service.IOrderExpManager;
import com.ztesoft.net.sqls.SF;
import com.ztesoft.net.utils.OrderExpUtils;
import com.ztesoft.net.vo.CatalogAndSolution;

import commons.CommonTools;
import consts.ConstsCore;
/**
 * 异常单管理类
 * @作者 shenqiyu
 * @创建日期 2015-11-23 
 */
public class OrderExpManager extends BaseSupport implements IOrderExpManager {
	protected final Logger logger = Logger.getLogger(getClass());
	private IEsearchSpecvaluesManager esearchSpecvaluesManager;
	private IExpConfigManager expConfigManager;
	private IDcPublicInfoManager dcPublicInfoManager;
	private AdminUserInf adminUserServ;
	
	private static ThreadPoolExecutor orderExpExecutor;
	
	private void poolInit() {
		if (OrderExpManager.orderExpExecutor == null) {
			 synchronized(OrderExpManager.class){
				 ICacheUtil cacheUtil =SpringContextHolder.getBean("cacheUtil");
				 String ORDEREXP_PROCESS_COREPOOLSIZE = cacheUtil.getConfigInfo("ORDEREXP_PROCESS_COREPOOLSIZE");
				 if(StringUtils.isEmpty(ORDEREXP_PROCESS_COREPOOLSIZE))
					 ORDEREXP_PROCESS_COREPOOLSIZE ="50";
				 	OrderExpManager.orderExpExecutor = new ThreadPoolExecutor(new Integer(ORDEREXP_PROCESS_COREPOOLSIZE).intValue(),Integer.MAX_VALUE, 5 * 60, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),new ThreadFactoryImpl("ThreadRopExector"));
			 }
	   }
	}
	
	public List<EsearchExpInst> getEsearchExpInst() {
		String sql = SF.orderExpSql("ExpInstByRecordStatus");
		List<EsearchExpInst> list = this.baseDaoSupport.queryForList(sql, EsearchExpInst.class);
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public OrderExpMarkProcessedOuter orderExpMarkProcessed(OrderExpMarkProcessedInner inner) {
		OrderExpMarkProcessedOuter outer = new OrderExpMarkProcessedOuter();
		String sql = SF.orderExpSql("UpdateExpInstByInstId");
		if(null != inner.getExcepInsts() && inner.getExcepInsts().size() > 0) {
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			String deal_staff_no = inner.getDeal_staff_no() == null ? "":inner.getDeal_staff_no();
			for(EsearchExpInst eei:inner.getExcepInsts()) {
				String instId = eei.getExcp_inst_id();
				String record_status = eei.getRecord_status();
				String deal_result = eei.getDeal_result() == null ? "":eei.getDeal_result();
				String[] args = new String[]{
						record_status,deal_result,deal_staff_no,instId
				};
				batchArgs.add(args);
				logger.info("OrderExpManager.orderExpMarkProcessed  instId:" + instId);
			}
			this.baseDaoSupport.batchExecute(sql, batchArgs);
		}
		outer.setOuter_status(ConstsCore.ERROR_SUCC);
		outer.setOuter_msg("成功");
		return outer;
	}
	
	@Override
	public OrderExpMarkProcessedResp orderExpMarkProcessedForOuterSys(OrderExpMarkProcessedReq req) {
		OrderExpMarkProcessedResp resp = new OrderExpMarkProcessedResp();
		String sql = SF.orderExpSql("ExpInstAndExtList");
		//只查询订单实例的
		sql += " and eei.record_status = '"+EcsOrderConsts.EXPINST_RECORD_STATUS_0+"' ";

		if(StringUtils.isNotEmpty(req.getRequest_time())) {
			sql += " and eei.excp_create_time <= to_date('"+req.getRequest_time()+"','yyyy-MM-dd HH24:mi:ss')";//只标记请求时间以前的异常单
		}
		
		if(StringUtils.isEmpty(req.getExp_inst_id())
				&& StringUtils.isEmpty(req.getRel_obj_id())) {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("异常实例ID和关联实例ID不能同时为空");
			return resp;
		}
		
		if(StringUtils.isNotEmpty(req.getExp_inst_id())) {
			sql += " and eei.excp_inst_id = '"+req.getExp_inst_id()+"' ";
		}else {
			sql += " and eei.rel_obj_id = '"+req.getRel_obj_id()+"' ";
			sql += " and eei.rel_obj_type = '"+req.getRel_obj_type()+"' ";
		}
		
		List<Map> list = this.baseDaoSupport.queryForList(sql);
		
		if(list != null && list.size() > 0) {
			OrderExpMarkProcessedInner inner = new OrderExpMarkProcessedInner();
			List<EsearchExpInst> eeis = new ArrayList<EsearchExpInst>();
			for(Map inst : list) {
				String inst_id = inst.get("excp_inst_id").toString();//异常实例id
				EsearchExpInst eei = new EsearchExpInst();
				eei.setExcp_inst_id(inst_id);
				eei.setDeal_result(req.getDeal_result());
				eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
				eeis.add(eei);
			}
			inner.setExcepInsts(eeis);
			inner.setDeal_staff_no(req.getDeal_staff_no());//定时任务处理没有工号，默认-1
			OrderExpMarkProcessedOuter outer = this.orderExpMarkProcessed(inner);
			resp.setError_code(outer.getOuter_status());
			resp.setError_msg(outer.getOuter_msg());
		}else {
			resp.setError_code(ConstsCore.ERROR_FAIL);
			resp.setError_msg("查询异常实例失败");
		}
		return resp;
	}
	
	@Override
	public void orderExpMarkProcessedTimerExc(int maxRecords) {
		String sql = SF.orderExpSql("ExpInstAndExtList");
		//已退单的订单或者环节不一致的订单需要标记已处理
		sql += " and eei.record_status = '"+EcsOrderConsts.EXPINST_RECORD_STATUS_0+"' "
				+ " and (nvl(eeie.tache_code,'NULL') != nvl(oe.flow_trace_id,'NULL') or nvl(oe.refund_deal_type,'NULL') = '"+EcsOrderConsts.REFUND_DEAL_TYPE_02+"')"
				+ " and eei.excp_create_time > sysdate-30";  //只处理30天内的异常单
		
		long start = System.currentTimeMillis();
		List<Map> list = this.baseDaoSupport.queryForList(sql);
		logger.info("orderExpMarkProcessedTimerExc [已退单或者环节不一致查询]  消耗时间："+(System.currentTimeMillis()-start)+"ms sql:"+sql);
		
		OrderExpMarkProcessedInner inner = new OrderExpMarkProcessedInner();
		List<EsearchExpInst> eeis = new ArrayList<EsearchExpInst>();
		
		if(list != null && list.size() > 0) {
			for(Map inst : list) {
				EsearchExpInst eei = new EsearchExpInst();
				String inst_id = inst.get("excp_inst_id").toString();//异常实例id
				eei.setExcp_inst_id(inst_id);
				eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_0);//默认为未处理
				String rel_obj_id = inst.get("rel_obj_id").toString();//关联实例ID
				String rel_obj_type = inst.get("rel_obj_type").toString();
				String expinst_tache_code = inst.get("tache_code").toString();//异常单环节
				String ext_order_id = inst.get("ext_order_id").toString();//订单扩展表order_id
				String flow_trace_id = inst.get("flow_trace_id").toString();//订单当前环节编码
				String refund_deal_type = inst.get("refund_deal_type").toString();//订单当前退单状态
				
				start = System.currentTimeMillis();
				if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER_QUEUE.equals(rel_obj_type)) {
					if(StringUtils.isNotEmpty(ext_order_id)) {
						eei.setDeal_result("已处理 orderExpMarkProcessedTimerExc  [obj_type:order_queue] 已标准化成功  rel_obj_id:"+rel_obj_id+"  ext_order_id:"+ext_order_id);
						eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
						eeis.add(eei);
						logger.info(eei.getDeal_result());
					}
				}else {
					if(StringUtils.isNotEmpty(refund_deal_type)
							&& EcsOrderConsts.REFUND_DEAL_TYPE_02.equals(refund_deal_type)) {//已退单标记为已处理
						eei.setDeal_result("已处理 orderExpMarkProcessedTimerExc [obj_type:order] 已退单  rel_obj_id:"+rel_obj_id+"  refund_status:"+refund_deal_type);
						eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
						eeis.add(eei);
						logger.info(eei.getDeal_result());
					}
					
					if(StringUtils.isEmpty(expinst_tache_code) 
							&& StringUtils.isNotEmpty(flow_trace_id) 
							&& !flow_trace_id.equals("NULL")) {
						eei.setDeal_result("已处理 orderExpMarkProcessedTimerExc [obj_type:order] 环节不一致 expinst_tache_code:"+expinst_tache_code+"      flow_trace_id:"+flow_trace_id);
						eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
						eeis.add(eei);
						logger.info(eei.getDeal_result());
					}
					
					if(StringUtils.isNotEmpty(expinst_tache_code) 
							&& StringUtils.isNotEmpty(flow_trace_id) 
							&& !flow_trace_id.equals("NULL")
							&& !expinst_tache_code.equals(flow_trace_id)) {
						eei.setDeal_result("已处理 orderExpMarkProcessedTimerExc [obj_type:order] 环节不一致  expinst_tache_code:"+expinst_tache_code+"      flow_trace_id:"+flow_trace_id);
						eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
						eeis.add(eei);
						logger.info(eei.getDeal_result());
					}
				}
			}
		}
		
		//已归档的订单需要标记已处理
		sql = SF.orderExpSql("ExpInstListForLOrder");
		sql += " and eei.record_status = '"+EcsOrderConsts.EXPINST_RECORD_STATUS_0+"' "
				+ " and ( exists (select 1 from es_order_ext where order_id = eei.rel_obj_id and flow_trace_id = 'L')"
				+ " or exists (select 1 from es_order_his e where order_id = eei.rel_obj_id) ) "
				+ " and eei.excp_create_time > sysdate-30";  //只处理30天内的异常单
		
		start = System.currentTimeMillis();
		list = this.baseDaoSupport.queryForList(sql);
		logger.info("orderExpMarkProcessedTimerExc [已归档查询] 消耗时间："+(System.currentTimeMillis()-start)+"ms sql:"+sql);
		
		if(list != null && list.size() > 0) {
			for(Map inst : list) {
				EsearchExpInst eei = new EsearchExpInst();
				String inst_id = inst.get("excp_inst_id").toString();//异常实例id
				eei.setExcp_inst_id(inst_id);
				String rel_obj_id = inst.get("rel_obj_id").toString();//关联实例ID
				
				eei.setDeal_result("已处理 orderExpMarkProcessedTimerExc [obj_type:order] 已归档  rel_obj_id:"+rel_obj_id);
				eei.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
				eeis.add(eei);
				logger.info(eei.getDeal_result());
			}
		}
		
		if(eeis.size() > 0) {
			inner.setExcepInsts(eeis);
			inner.setDeal_staff_no("-1");//定时任务处理没有工号，默认-1
			this.orderExpMarkProcessed(inner);
		}
	}

	/**
	 * 根据异常单ID查询异常单
	 * @param exp_inst_id
	 */
	private EsearchExpInst getExpInstById(String exp_inst_id) {
		String sql = SF.orderExpSql("ExpInstByInstId");
		EsearchExpInst expInst = (EsearchExpInst)this.baseDaoSupport.queryForObject(sql, EsearchExpInst.class, exp_inst_id);
		return expInst;
	}
	
	private void addProcessTimes(String exp_inst_id) {
		String sql = SF.orderExpSql("AddProcessTimesByInstId");
		this.baseDaoSupport.execute(sql, exp_inst_id);
	}
	
	@Override
	public List<EsearchExpInst> getExpInstByProcessModeAList() {
		String sql = SF.orderExpSql("ExpInstByProcessModeA");
		List<EsearchExpInst> expInsts = this.baseDaoSupport.queryForList(sql, EsearchExpInst.class, 
				EcsOrderConsts.EXPINST_RECORD_STATUS_0, 3, EcsOrderConsts.EXPINST_CATALOG_PROCESS_MODE_A);
		return expInsts;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ExpInstProcessedOuter expInstProcess(ExpInstProcessedInner inner) {
		ExpInstProcessedOuter processedOuter = new ExpInstProcessedOuter();
		
		//1、根据异常ID查询异常单
		EsearchExpInst expInst = this.getExpInstById(inner.getExp_inst_id());
		if(expInst == null) {
			logger.info("OrderExpManager.expInstProcessed "+inner.getExp_inst_id()+" 异常单不存在");
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg(inner.getExp_inst_id()+" 异常单不存在");
			return processedOuter;
		}
		//封装部分结果数据
		processedOuter.setExp_inst_id(expInst.getExcp_inst_id());
		processedOuter.setRel_obj_type(expInst.getRel_obj_type());
		processedOuter.setRel_obj_id(expInst.getRel_obj_id());
		processedOuter.setDeal_status(expInst.getRecord_status());
		
		if(expInst.getRecord_status().equals(EcsOrderConsts.EXPINST_RECORD_STATUS_1)) {
			logger.info("OrderExpManager.expInstProcessed "+inner.getExp_inst_id()+" 该异常单已被处理");
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg(inner.getExp_inst_id()+"该异常单已被处理");
			return processedOuter;
		}
		
		if(StringUtils.isEmpty(inner.getDeal_result())) {
			logger.info("OrderExpManager.expInstProcessed "+inner.getExp_inst_id()+" 传入异常处理结果为空");
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg(inner.getExp_inst_id()+"传入异常处理结果为空");
			return processedOuter;
		}
		
		if(StringUtils.isEmpty(inner.getStaff_no())) {
			logger.info("OrderExpManager.expInstProcessed "+inner.getExp_inst_id()+" 处理工号不能为空");
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg(inner.getExp_inst_id()+"处理工号不能为空");
			return processedOuter;
		}
		
		//2、根据关键字查询归类信息
		EsearchCatalogInner cataLogInner = new EsearchCatalogInner();
		cataLogInner.setKey_id(expInst.getKey_id());
		EsearchCatalogOuter cataLogOuter = expConfigManager.queryEsearchCatalogBySpecv(cataLogInner);
		if(cataLogOuter == null || 
				cataLogOuter.getList() == null || 
				cataLogOuter.getList().size() < 1) {
			logger.info("OrderExpManager.expInstProcessed 关键字ID:"+expInst.getKey_id()+"未找到归类信息");
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg("关键字ID"+expInst.getKey_id()+"未找到归类信息");
			return processedOuter;
		}
		
		//3、根据归类查询方案信息
		EsearchExpInstSolutionInner eesInner = new EsearchExpInstSolutionInner();
		eesInner.setCatalog_id(cataLogOuter.getList().get(0).getCatalog_id());
		EsearchExpInstSolutionOuter eesOuter = expConfigManager.queryEesByCatalog(eesInner);
		if(eesOuter == null || 
				eesOuter.getList() == null || 
						eesOuter.getList().size() < 1) {
			logger.info("OrderExpManager.expInstProcessed 归类ID:"+eesInner.getCatalog_id()+"未找到解决方案");
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg("归类ID"+eesInner.getCatalog_id()+"未找到解决方案");
			return processedOuter;
		}
		EsearchExpInstSolution solution = eesOuter.getList().get(0);
		processedOuter.setSolution_type(solution.getSolution_type());
		processedOuter.setSolution_value(solution.getSolution_value());
		
		//4、处理次数+1
		this.addProcessTimes(expInst.getExcp_inst_id());
		//5、执行解决方案
		processedOuter = this.excuteSolution(solution, expInst, processedOuter);
		if(processedOuter.getOuter_status().equals(ConstsCore.ERROR_SUCC)) {//执行成功
			//拼装处理结果
			processedOuter.setDeal_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
			processedOuter.setDeal_result(inner.getDeal_result()+"<br/>"+processedOuter.getDeal_result());
			
			expInst.setDeal_staff_no(inner.getStaff_no());
			expInst.setDeal_result(processedOuter.getDeal_result());
			//6、调用【异常订单环节/不一致标记已处理】
			OrderExpMarkProcessedOuter oempOuter = this.markProcessedByEsearchExpInst(expInst);
			if(oempOuter.getOuter_status().equals(ConstsCore.ERROR_SUCC)) {
				processedOuter.setOuter_status(ConstsCore.ERROR_SUCC);
				processedOuter.setOuter_msg("成功");
				return processedOuter;
			}
		}
		return processedOuter;
	}
	
	/**
	 * 异常实例批量处理
	 * @param inner
	 * @return
	 */
	@Override
	public ExpInstBatchProcessedOuter expInstBatchProcessed(ExpInstBatchProcessedInner inner) {
		ExpInstBatchProcessedOuter batchOuter = new ExpInstBatchProcessedOuter();
		List<ExpInstProcessedOuter> singleOuters = new ArrayList<ExpInstProcessedOuter>();
		//根据关联单号、搜索ID、关键字ID去重异常单ID
		Object[] distinctedInstIds = distinctEsearchExpInst(inner.getExcepInstIds());
		//初始化线程池
		poolInit();
		
		List<Future> futures = new ArrayList<Future>();
		List<OrderExpProcessThread> processThreads = new ArrayList<OrderExpProcessThread>();
		for(Object expInstId:distinctedInstIds) {
			ExpInstProcessedInner processInner = new ExpInstProcessedInner();
			processInner.setExp_inst_id((String)expInstId);
			processInner.setDeal_result(inner.getDeal_result());
			processInner.setStaff_no(inner.getStaff_no());
			
			OrderExpProcessThread processThread = new OrderExpProcessThread(processInner);
			Thread thread = new Thread(processThread);
			thread.setName("异常单处理线程");
			processThreads.add(processThread);
			Future<?> future = orderExpExecutor.submit(processThread);
			futures.add(future);
		}
		//等待所有线程执行完毕
		while(futures.size() > 0) {
	    	for(Future<?> future:futures) {
	    		if(future.isDone()) {
	    			futures.remove(future);
	    			logger.info(futures.size());
	    			break;
	    		}
	    	}
	    }
		//获取处理结果
		for (OrderExpProcessThread processThread : processThreads) {
			singleOuters.add(processThread.getSingleOuter());
	    }
		
		batchOuter.setOuters(singleOuters);
		batchOuter.setOuter_status("0");
		batchOuter.setOuter_msg("处理完成，请查看处理结果");
		return batchOuter;
	}
	
	/**
	 * 根据当前异常实例，将与当前异常实例同单、同编码、同关键字的所有异常实例都标记为已处理
	 * @param expInst
	 * @return
	 */
	private OrderExpMarkProcessedOuter markProcessedByEsearchExpInst(EsearchExpInst expInst) {
		String sql = SF.orderExpSql("ExpInstList");
		sql += "  and search_id = ? and key_id = ?  and rel_obj_id = ? and record_status = ?";
		List<EsearchExpInst> expInsts = this.baseDaoSupport.queryForList(sql, 
				EsearchExpInst.class, expInst.getSearch_id(), expInst.getKey_id(), 
				expInst.getRel_obj_id(), EcsOrderConsts.EXPINST_RECORD_STATUS_0);
		
		List<EsearchExpInst> eeis = new ArrayList<EsearchExpInst>();
		//将搜索id、关键字id、订单id与本次处理异常单一样的其它异常单都标记为已处理
		for(EsearchExpInst eei:expInsts) {
			if(!expInst.getExcp_inst_id().equals(eei.getExcp_inst_id())) {
				EsearchExpInst eeiMark = new EsearchExpInst();
				eeiMark.setExcp_inst_id(eei.getExcp_inst_id());
				eeiMark.setDeal_result(expInst.getDeal_result());
				eeiMark.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
				eeis.add(eeiMark);
			}
		}
		EsearchExpInst eeiMark = new EsearchExpInst();
		eeiMark.setExcp_inst_id(expInst.getExcp_inst_id());//本次处理的异常单
		eeiMark.setDeal_result(expInst.getDeal_result());
		eeiMark.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_1);
		eeis.add(eeiMark);
		
		OrderExpMarkProcessedInner oempInner = new OrderExpMarkProcessedInner();
		oempInner.setExcepInsts(eeis);
		oempInner.setDeal_staff_no(expInst.getDeal_staff_no());//处理工号
		OrderExpMarkProcessedOuter oempOuter = this.orderExpMarkProcessed(oempInner);
		return oempOuter;
	}
	
	/**
	 * 根据关联单号、搜索ID、关键字ID去重异常单ID
	 * @param expInstIds
	 * @return
	 */
	@Override
	public Object[] distinctEsearchExpInst(String[] expInstIds) {
		String ids_in = "";
		for(int i = 0; i < expInstIds.length; i++) {
			if(i==0)
				ids_in += "'"+expInstIds[i]+"'";
			else 
				ids_in += ",'"+expInstIds[i]+"'";
		}
		
		if(ids_in.endsWith(",")) {
			ids_in = ids_in.substring(0,ids_in.length()-1);
		}
		
		String sql = SF.orderExpSql("ExpInstList");
		sql += "  and excp_inst_id in ("+ids_in+")";
		List<EsearchExpInst> expInsts = this.baseDaoSupport.queryForList(sql, EsearchExpInst.class);
		
		List<String> result = new ArrayList<String>();
		if(expInsts != null && expInsts.size() > 0) {
			Map<String,String> temp = new HashMap<String,String>();
			for(EsearchExpInst expInst:expInsts) {
				String key = expInst.getRel_obj_id()+"_"+expInst.getSearch_id()+"_"+expInst.getKey_id();
				temp.put(key, expInst.getExcp_inst_id());
			}
			
			for (Map.Entry<String, String> entry : temp.entrySet()) {  
				result.add(entry.getValue());
			}
		}
		return result.toArray();
	}
	
	private Object invokeMethod(Object obj, String method, Object[] args) {
		try {
			return MethodUtils.invokeMethod(obj, method, args);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ExpInstProcessedOuter excuteSolution(EsearchExpInstSolution solution, EsearchExpInst expInst, ExpInstProcessedOuter processedOuter) {
		logger.info("OrderExpManager.expInstProcessed  type:"+solution.getSolution_type() + "   solution_value:"+solution.getSolution_value() +"   rel_obj_id:"+expInst.getRel_obj_id());
		if(EcsOrderConsts.EXPINST_SOLUTION_TYPE_PLAN
				.equals(solution.getSolution_type())) {//方案     需要设置强制执行
			ExpInstProcessedOuter outer = this.excutePlan(solution,expInst);
			if(ConstsCore.ERROR_SUCC.equals(outer.getOuter_status())) {//成功
				processedOuter.setDeal_result(outer.getOuter_msg());
			}else {
				processedOuter.setOuter_status(outer.getOuter_status());
				processedOuter.setOuter_msg(outer.getOuter_msg());
				return processedOuter;
			}
		}else if(EcsOrderConsts.EXPINST_SOLUTION_TYPE_RULE
				.equals(solution.getSolution_type())) {//规则
			ExpInstProcessedOuter outer = this.excuteRule(solution,expInst);
			if(ConstsCore.ERROR_SUCC.equals(outer.getOuter_status())) {//成功
				processedOuter.setDeal_result(outer.getOuter_msg());
			}else {
				processedOuter.setOuter_status(outer.getOuter_status());
				processedOuter.setOuter_msg(outer.getOuter_msg());
				return processedOuter;
			}
		}else if(EcsOrderConsts.EXPINST_SOLUTION_TYPE_SQL
				.equals(solution.getSolution_type())) {//SQL
			int result = OrderExpUtils.excuteSolutionSql(solution.getSolution_value(), expInst.getRel_obj_id());
			if(result < 1) {
				processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
				processedOuter.setOuter_msg("SQL语句执行失败");
				return processedOuter;
			}else {
				processedOuter.setOuter_status(ConstsCore.ERROR_SUCC);
				processedOuter.setDeal_result("SQL:"+solution.getSolution_value()+"   id:"+expInst.getRel_obj_id());
			}
		}
//		AdminUser admin = ManagerUtils.getAdminUser();
//		else if(EcsOrderConsts.EXPINST_SOLUTION_TYPE_URL
//				.equals(solution.getSolution_type())) {//URL
//			String url = BeanUtils.urlAddToken(solution.getSolution_value(), admin.getUsername());
//			url = url.replace("{rel_obj_id}", expInst.getRel_obj_id());
//			processedOuter.setSolution_value(url);
//			processedOuter.setDeal_result("URL:"+solution.getSolution_type());
//			
//			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
//			processedOuter.setOuter_msg("该解决方案无需处理");
//			return processedOuter;
//		}else if(EcsOrderConsts.EXPINST_SOLUTION_TYPE_DEFAULT
//				.equals(solution.getSolution_type())) {//DEFAULT  其实也是一个URL
//			String url = BeanUtils.urlAddToken(solution.getSolution_value(), admin.getUsername());
//			url = url.replace("{rel_obj_id}", expInst.getRel_obj_id());
//			processedOuter.setSolution_value(url);
//			processedOuter.setDeal_result("DEFAULT:"+solution.getSolution_type());
//			
//			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
//			processedOuter.setOuter_msg("该解决方案无需处理");
//			return processedOuter;
//		}
		processedOuter.setOuter_status(ConstsCore.ERROR_SUCC);
		processedOuter.setOuter_msg("成功");
		return processedOuter;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ExpInstOuter insertExpInst(ExpInstInner inner) {
		ExpInstOuter outer = new ExpInstOuter();
		EsearchExpInst esearchExpInst = new EsearchExpInst();
		String key_id = inner.getKey_id();
		String rel_obj_id = inner.getRel_obj_id();
		//主键id
		String excp_inst_id = SequenceTools.getdefualt22PrimaryKey();
		//异常实例对象
		esearchExpInst.setSearch_id(inner.getSearch_id());
		esearchExpInst.setKey_id(key_id);
		esearchExpInst.setLog_id(inner.getEsearch_id());
		esearchExpInst.setRel_obj_id(rel_obj_id);
		esearchExpInst.setRel_obj_type(inner.getRel_obj_type());
		esearchExpInst.setSeq(getSeq(rel_obj_id,inner.getSearch_id(),key_id));
		esearchExpInst.setCatalog_id((String) getCataLogId(key_id).get("catalog_id"));
		esearchExpInst.setExcp_inst_id(excp_inst_id);
		esearchExpInst.setRecord_status(EcsOrderConsts.EXPINST_RECORD_STATUS_0);
		esearchExpInst.setOut_tid(getExtObjId(rel_obj_id,inner.getOut_tid()));
		esearchExpInst.setIs_visible(inner.getIs_visible());
		try{
			esearchExpInst.setExcp_create_time(DateUtil.getTime2());
		}catch (FrameException e){
			e.printStackTrace();
		}
		esearchExpInst.setRel_obj_create_time(getObjCreateTime(rel_obj_id,inner.getRel_obj_type()));
		this.baseDaoSupport.insert("es_esearch_expinst", esearchExpInst);
		//异常实例扩展对象
		insertExpInstExt(excp_inst_id,rel_obj_id,inner.getRel_obj_type());
		return outer;
	}
	
	public void updateKeyToEearch(final String log_id,final String key_id,final String key_word,final String catalog_id,final String catalog_name){
		try{
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					if(StringUtils.equals("1", getEsearchFlag())){
						//更新esearch
						ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
						EsearchUpdateReq req = new EsearchUpdateReq();
						ESearchData esearch = new ESearchData();
						req.setUpdateDelay(true);
						esearch.setKeyword_id(key_id);
						esearch.setLog_id(log_id);
						esearch.setKeyword_name(key_word);
						esearch.setClass_id(catalog_id);
						esearch.setClass_value(catalog_name);
						req.setEsData(esearch);
						EsearchUpdateResp resp = client.execute(req, EsearchUpdateResp.class);
						if (ConstsCore.ERROR_SUCC.equals(resp.getError_code())) {
							logger.info("logid:" + log_id + ",更新esearch成功!");
						} else {
							logger.info("logid:" + log_id + ",更新esearch失败!");
						}
					}
				}
			});
			thread.setName("LOGID"+log_id+",文件系统线程更新!");
			thread.start();
		}catch(Exception e){
			logger.info(e.getMessage());
		}

	}
	
	public String getExtObjId(String obj_id,String out_tid){
		if(StringUtils.isEmpty(out_tid)||CommonDataFactory.getInstance().getOrderTree(obj_id)!=null){
			try {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(obj_id);
				if(orderTree != null)
					out_tid = orderTree.getOrderExtBusiRequest().getOut_tid();
			} catch (Exception e) {
			}
			return out_tid;
		}else{
			return out_tid;
		}
	}
	
	/**
	 * 获取异常对象创建时间
	 */
	public String getObjCreateTime(String obj_id,String obj_type){
		if(StringUtils.equals("order", obj_type)){
			try {
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(obj_id);
				if(orderTree != null)
					return orderTree.getOrderBusiRequest().getCreate_time();
			} catch (Exception e) {
				return null;
			}
		}
		if(StringUtils.equals("order_queue", obj_type)){
			OrderQueueBusiRequest req= CommonDataFactory.getInstance().getOrderQueue("", obj_id);
			if(req != null)
				return req.getCreated_date();
			return null;
		}
		return null;
	}
	
	/**
	 * 获取序列号
	 * @param rel_obj_id
	 * @param search_id
	 * @param key_id
	 * @return
	 */
	public Integer getSeq(String rel_obj_id,String search_id,String key_id){
		String getSeqSql = SF.orderExpSql("Seq");
		Integer seq = 0;
		try{
			seq = this.baseDaoSupport.queryForInt(getSeqSql,new String[]{rel_obj_id,search_id,key_id})+1;
		}catch(RuntimeException e){
			seq = 0;
			e.printStackTrace();
		}
		return seq;
	}
	
	/**
	 * 获取归类id
	 * @param key_id
	 * @return
	 */
	@Override
	public Map getCataLogId(String key_id){
		Map map = new HashMap();
		String getCatalogSql = SF.orderExpSql("CatalogId");
		List<Map> list = this.baseDaoSupport.queryForList(getCatalogSql,key_id);
		if(list != null && list.size() > 0){
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 异常实例扩展对象插入
	 * @param excp_inst_id
	 * @param rel_obj_id
	 * @param rel_obj_type
	 */
	public void insertExpInstExt(String excp_inst_id,String rel_obj_id,String rel_obj_type){
		EsearchExpInstExt expinstExt = new EsearchExpInstExt();
		expinstExt.setExcp_inst_id(excp_inst_id);
		if(StringUtils.equals("order", rel_obj_type)){
			try{
				OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(rel_obj_id);
				if(orderTree != null) {
					OrderExtBusiRequest orderExt = orderTree.getOrderExtBusiRequest();
					expinstExt.setTache_code(orderExt == null ? "":orderExt.getFlow_trace_id());
				}
				expinstExt.setOrder_mode(CommonDataFactory.getInstance().getAttrFieldValue(rel_obj_id, "order_model"));
				expinstExt.setOrder_from(CommonDataFactory.getInstance().getAttrFieldValue(rel_obj_id, "order_from"));
				expinstExt.setLan_id(CommonDataFactory.getInstance().getAttrFieldValue(rel_obj_id, "order_city_code"));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		this.baseDaoSupport.insert("es_esearch_expinst_ext", expinstExt);
	}

	@Override
	public ExpInstOuter updateExpInst(ExpInstInner inner) {
		ExpInstOuter outer = new ExpInstOuter();
		String sql = SF.orderExpSql("ExpInstById");
		EsearchExpInst expInst = (EsearchExpInst) this.baseDaoSupport.queryForObject(sql, EsearchExpInst.class, inner.getExcp_inst_id());		
		if(expInst!=null){
			if(null != inner.getCatalog_id()){
				expInst.setCatalog_id(inner.getCatalog_id());
			}
			if(null != inner.getExcp_update_time()){
				expInst.setExcp_update_time(inner.getExcp_update_time());
			}
			if(null != inner.getKey_id()){
				expInst.setKey_id(inner.getKey_id());
			}
			if(!StringUtil.isEmpty(inner.getRecord_status())){
				expInst.setRecord_status(inner.getRecord_status());
			}
			String where = "excp_inst_id = "+inner.getExcp_inst_id();
			this.baseDaoSupport.update("es_esearch_expinst", expInst, where);
		}
		return outer;
	}
	
	private String getOrderIDByOutTID(String out_tid) {
		String sql = "select order_id from es_order_ext where out_tid=?";
		List<Map> orders = this.baseDaoSupport.queryForList(sql, out_tid);
		if(orders != null && orders.size() > 0) {
			return (String)orders.get(0).get("order_id");
		}
		return null;
	}
	
	public OrderExpWriteResp orderExpWrite(OrderExpWriteInner inner){
		OrderExpWriteResp resp = new OrderExpWriteResp();
		logger.info("订单编码:"+inner.getObj_id()+";日志id:"+inner.getLog_id());
		if(StringUtils.isEmpty(inner.getObj_id())){
			resp.setError_code(ConstsCore.ERROR_SUCC);
			resp.setError_msg("过滤成功");
			return resp;
		}
		//单号转换  如果传过来的订单号是外补单号，则需要进行转换
		String order_id = getOrderIDByOutTID(inner.getObj_id());
		if(StringUtils.isNotEmpty(order_id)) {
			inner.setObj_id(order_id);
			inner.setOut_tid(inner.getObj_id());
		}
		
		//调用过滤器获取异常写入方式
		String write_flag = filter(inner);
		
		//只有写异常才能执行后续操作
		if(!EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_NOT_WRITE.equals(write_flag)) {
			//调用[规格关键字校验、写入、更新]API
			SpecvaluesCheckProcessedInner checkInner = new SpecvaluesCheckProcessedInner();
			checkInner.setLog_id(inner.getLog_id());
			checkInner.setExp_type(inner.getExp_type());
			checkInner.setParam(inner.getError_msg());
			checkInner.setSearch_id(inner.getSearch_id());
			checkInner.setSearch_code(inner.getSearch_code());
			checkInner.setError_stack_msg(inner.getError_stack_msg());
			SpecvaluesCheckProcessedOuter checkOuter = esearchSpecvaluesManager.specvaluesCheckProcessed(checkInner);
			logger.info("日志id:"+inner.getLog_id()+";是否需要写入异常is_write:"+checkOuter.getIs_wirte());
			if(StringUtils.equals(EcsOrderConsts.IS_WRITE_EXCEPTION_1, checkOuter.getIs_wirte())){
				//根据返回值调用[异常实例插入]API
				ExpInstInner expInstInner = new ExpInstInner();
				expInstInner.setIs_visible(EcsOrderConsts.EXP_INST_IS_VISIBLE_Y);//默认可见
				
				if(EcsOrderConsts.EXP_INST_FILTER_RESP_FLAG_INVISIBLE.equals(write_flag)) {
					expInstInner.setIs_visible(EcsOrderConsts.EXP_INST_IS_VISIBLE_N);//置为不可见
				}
				
				expInstInner.setEsearch_id(inner.getLog_id());
				expInstInner.setKey_id(checkOuter.getKey_id());
				expInstInner.setCatalog_id((String) getCataLogId(checkOuter.getKey_id()).get("catalog_id"));
				expInstInner.setRel_obj_id(inner.getObj_id());
				expInstInner.setRel_obj_type(inner.getObj_type());
				expInstInner.setSearch_id(checkOuter.getSearch_id());
				expInstInner.setOut_tid(getOutId(inner.getObj_id(), inner.getObj_type()));
				insertExpInst(expInstInner);
				logger.info("异常写入成功："+inner.getObj_id());
				String key_id = checkOuter.getKey_id();
				//更新esearch
				updateKeyToEearch(inner.getLog_id(),key_id,checkOuter.getKey_word(),
						(String) getCataLogId(key_id).get("catalog_id"),(String) getCataLogId(key_id).get("catalog_name"));
			}
		}
		
		resp.setError_code(ConstsCore.ERROR_SUCC);
		resp.setError_msg("操作成功");
		return resp;
	}
	
	
	/**
	 * 查询外部单号
	 * @param rel_obj_id
	 * @param rel_obj_type
	 * @return
	 */
	private String getOutId(String rel_obj_id, String rel_obj_type) {
		if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER_QUEUE.equals(rel_obj_type)) {
			OrderQueueBusiRequest queue = CommonDataFactory.getInstance().getOrderQueueFor(null, rel_obj_id);
			if(queue != null)
				return queue.getObject_id();
			return "";
		}else if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER.equals(rel_obj_type)) {
			OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(rel_obj_id);
			if(orderTree != null && orderTree.getOrderExtBusiRequest() != null)
				return orderTree.getOrderExtBusiRequest().getOut_tid();
			return "";
		}
		return "";
	}
	
	/**
	 * 是否需要写入异常系统
	 * @return
	 */
	public boolean filter_a(OrderExpWriteInner inner){
		logger.info("filter_a(OrderExpWriteInner inner) logid:"+inner.getLog_id() + "   Obj_id:"+inner.getObj_id()+ "   obj_type:"+inner.getObj_type());
		//转发商城的接口：此时订单系统并没有订单生成；需要过滤
		if(StringUtils.equals(EcsOrderConsts.EXP_INF, inner.getExp_type())){
			List dcList = CommonDataFactory.getInstance().listDcPublicData("JKZF_MARK");
			for(int i=0;i<dcList.size();i++){
				Map map = (Map) dcList.get(i);
				if(StringUtils.equals((String)map.get("pname"),inner.getSearch_code())
						&&StringUtils.isEmpty(inner.getObj_id())){
					logger.info("filter_a(OrderExpWriteInner inner) 转发商城的接口：此时订单系统并没有订单生成；需要过滤");
					return true;
				}
			}
		}
		//报文为空：过滤
		if(StringUtils.isEmpty(inner.getError_msg())){
			logger.info("filter_a(OrderExpWriteInner inner) 报文为空：过滤");
			return true;
		}
		return false;
	}
	
	/**
	 * 是否需要写入异常系统
	 * @return
	 */
	public String filter(OrderExpWriteInner inner){
		logger.info("filter(OrderExpWriteInner inner) logid:"+inner.getLog_id() + "   Obj_id:"+inner.getObj_id()+ "   obj_type:"+inner.getObj_type());
		
		ExpFilterRequest request = new ExpFilterRequest();
		request.setSearch_id(inner.getSearch_id());
		request.setRel_obj_id(inner.getObj_id());
		request.setRel_obj_type(inner.getObj_type());
		request.setOut_tid(inner.getOut_tid());
		request.setSearch_code(inner.getSearch_code());
		request.setWrite_flag(inner.getWrite_flag());
		request.setParam(inner.getError_msg());
		//过滤器实现
		ArrayList<FilterResponse> filterResps = CommonFilterFactory.getInstance().doExpFilter(request);
		String write_flag = CommonFilterFactory.getInstance().readExpFilterResonse(filterResps);
		logger.info("write_flag:"+write_flag);
		return write_flag;
	}
	
	@Override
	public OrderExpWriteResp orderExpWrite(OrderExpWriteForInfReq req) {
		OrderExpWriteResp resp = new OrderExpWriteResp();		
		Map map = OrderExpUtils.getEsearchParam(req.getSearch_code(),req.getOut_param(),req.getIn_param());
		//调用异常能力写入api
		OrderExpWriteInner inner = new OrderExpWriteInner();
		inner.setLog_id(req.getLog_id());
		inner.setObj_id(req.getObj_id());
		inner.setObj_type(req.getObj_type());
		inner.setSearch_code(req.getSearch_code());
		inner.setWrite_flag((String) map.get("flag"));
		inner.setSearch_id((String) map.get("search_id"));
		inner.setError_msg((String) map.get("param"));
		inner.setExp_type(EcsOrderConsts.EXP_INF);
		resp = orderExpWrite(inner);
		return resp;
	}
	
	@Override
	public OrderExpWriteResp orderExpWrite(OrderExpWriteForBusReq req) {
		OrderExpWriteResp resp = new OrderExpWriteResp();
		//写入文件系统
		String excp_inst_id = SequenceTools.getdefualt22PrimaryKey();
		writeToEsearch(excp_inst_id,req);
		//调用异常能力写入api
		OrderExpWriteInner inner = new OrderExpWriteInner();
		inner.setObj_id(req.getObj_id());
		inner.setLog_id(excp_inst_id);
		inner.setObj_type(req.getObj_type());
		inner.setSearch_id(req.getSearch_id());
		inner.setSearch_code(req.getSearch_code());
		inner.setError_stack_msg(req.getError_stack_msg());
		inner.setExp_type(EcsOrderConsts.EXP_BUS);
		inner.setError_msg(req.getError_msg());
		inner.setOut_tid(req.getOut_tid());
		resp = orderExpWrite(inner);
		return resp;
	}
	
	public String getEsearchFlag(){
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String is_esearch_write = cacheUtil.getConfigInfo(EsearchValues.IS_ESEARCH_WRITE);
		return is_esearch_write;
	}
	
	public void writeToEsearch(final String excp_inst_id,final OrderExpWriteForBusReq req){
		try{
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					if(StringUtils.equals("1", getEsearchFlag())){
						ESearchData esData =new ESearchData();
						esData.setIndex("es_busi_exception_idx");
						esData.setType("es_busi_exception");
						esData.setOperation_code(req.getSearch_code());
						esData.setLog_id(excp_inst_id);
						String out_id =req.getOut_tid();
						if(StringUtil.isEmpty(out_id))
							out_id = req.getObj_id();
						esData.setObj_id(out_id);
						esData.setOut_param(req.getError_stack_msg());
						EsearchAddReq addReq = new EsearchAddReq();
						addReq.setEsData(esData);
						ZteClient client = ClientFactory.getZteRopClient(AppKeyEnum.APP_KEY_WSSMALL_ESEARCH);
						EsearchAddResp addResp = client.execute(addReq, EsearchAddResp.class);
						if (ConstsCore.ERROR_SUCC.equals(addResp.getError_code())) {
							logger.info("logid:" + excp_inst_id + ",esearch写入成功!");
						} else {
							logger.info("logid:" + excp_inst_id + ",esearch写入失败!");
						}
					}
				}
			});
			thread.setName("LOGID"+excp_inst_id+",文件系统线程写入!");
			thread.start();
		}catch(Exception e){
			logger.info(e.getMessage());
		}
	}
	
	private Object[] sqlExpInstList(ExpInstQueryInner inner, AdminUser admin) {
		List<Object> sqlParams = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		
		String es_esearch_expinst_table = "es_esearch_expinst";
		String es_esearch_expinst_ext_table = "es_esearch_expinst_ext";
		//是否历史数据
		if(inner != null && EcsOrderConsts.EXP_IS_HIS.equals(inner.getIs_history())){//是
			es_esearch_expinst_table = "es_esearch_expinst_his";
			es_esearch_expinst_ext_table = "es_esearch_expinst_ext_his";
		}
		sb.append("select a.excp_inst_id,a.rel_obj_id,a.rel_obj_type,a.seq,a.excp_create_time,a.log_id,a.search_id,a.key_id,a.catalog_id as catalog_id, ");
		sb.append("a.record_status,a.deal_result,a.deal_staff_no,a.deal_time,a.rel_obj_create_time,a.out_tid,a.excp_update_time,");
		sb.append("b.tache_code,b.lan_id,b.staff_code,b.dispatch_time,");
		sb.append("es.search_code,es.search_name,es.search_field,esp.match_content,esp.warming_flag,eoe.flow_trace_id,eoe.order_from,eoe.order_city_code order_city,eoe.order_model order_mode,");
		sb.append("esp.warming_limit,esp.alarm_flag,esp.alarm_limit,esp.alarm_time_interval,ec.solution_id,ees.solution_type,ees.solution_value,ees.is_batch_process,");
		sb.append("round(-(a.excp_create_time-(trunc(sysdate,'HH24')+esp.warming_limit/24))*24,1) as warming_time ");
		sb.append("from (");
		
		sb.append("select MAX(excp_inst_id) as excp_inst_id");
		sb.append(" from (select excp_inst_id,key_id, rel_obj_id from ").append(es_esearch_expinst_table);
		sb.append(" where 1=1");
		if (inner != null)
			if(!StringUtil.isEmpty(inner.getStart_excp_time())){
	    	sb.append(" and excp_create_time>=").append(DBTUtil.to_sql_date("?", 2));
	        sqlParams.add(inner.getStart_excp_time());
	        
	        if (!StringUtil.isEmpty(inner.getEnd_excp_time())){
		    	sb.append(" and excp_create_time<=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getEnd_excp_time());
		    }
	        
	        if ((!StringUtil.isEmpty(inner.getSearch_id())) && (!"-1".equals(inner.getSearch_id()))){
				sb.append(" and search_id = ? ");
				sqlParams.add(inner.getSearch_id());
			}
			if ((!StringUtil.isEmpty(inner.getKey_id())) && (!"-1".equals(inner.getKey_id()))){
				sb.append(" and key_id = ? ");
		        sqlParams.add(inner.getKey_id());
			}
		    if (!StringUtil.isEmpty(inner.getCatalog_id())){
		    	sb.append(" and catalog_id = ? ");
		        sqlParams.add(inner.getCatalog_id());
		    }
		    if ((!StringUtil.isEmpty(inner.getRel_obj_type()))){
		    	sb.append(" and rel_obj_type = ? ");
		        sqlParams.add(inner.getRel_obj_type());
		    }
		    if ((!StringUtil.isEmpty(inner.getOut_tid()))){
		    	sb.append(" and out_tid = ? ");
		        sqlParams.add(inner.getOut_tid().trim());
		    }
		    if (!StringUtil.isEmpty(inner.getStart_obj_create_time())){
		    	sb.append(" and rel_obj_create_time>=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getStart_obj_create_time());
		    }
		    if (!StringUtil.isEmpty(inner.getEnd_obj_create_time())){
		    	sb.append(" and rel_obj_create_time<=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getEnd_obj_create_time());
		    }
		    if ((!StringUtil.isEmpty(inner.getRel_obj_id())) && (!"-1".equals(inner.getRel_obj_id()))){
		    	sb.append(" and rel_obj_id = ? ");
			    sqlParams.add(inner.getRel_obj_id().trim());
		    }
		    if ((!StringUtil.isEmpty(inner.getExcp_inst_id())) && (!"-1".equals(inner.getExcp_inst_id()))){
		    	sb.append(" and excp_inst_id = ? ");
		        sqlParams.add(inner.getExcp_inst_id());
		    }
		    if ((!StringUtil.isEmpty(inner.getRecord_status())) && (!"-1".equals(inner.getRecord_status()))){
		    	sb.append(" and record_status = ? ");
		        sqlParams.add(inner.getRecord_status());
		    }
		    //人工标记异常关键字
		    if((!StringUtil.isEmpty(inner.getArtificial_exception_type())) && (!"-1".equals(inner.getArtificial_exception_type()))){
		    	sb.append(" and key_id = ? ");
		    	sqlParams.add(inner.getArtificial_exception_type());
		    }
		    //开户号码
			if(!StringUtil.isEmpty(inner.getPhone_num())){
				sb.append(" and exists(select 1 from es_order_items_ext t where t.order_id=a.rel_obj_id and t.phone_num=?)");
				sqlParams.add(inner.getPhone_num());
			}
	    }
		sb.append(" and source_from = '").append(ManagerUtils.getSourceFrom()).append("'");
	    
		sb.append(") group by  rel_obj_id) a2 ");
		sb.append("left join ").append(es_esearch_expinst_table).append(" a on a.excp_inst_id = a2.excp_inst_id ");
		sb.append("left join ").append(es_esearch_expinst_ext_table).append(" b on a.excp_inst_id = b.excp_inst_id ");
		sb.append("left join es_order_ext eoe on a.rel_obj_id = eoe.order_id ");
		sb.append("left join es_esearch_spec es on a.search_id = es.search_id ");
		sb.append("left join es_esearch_specvalues esp on a.key_id = esp.key_id ");
		sb.append("left join es_esearch_catalog ec on a.catalog_id = ec.catalog_id ");
		sb.append("left join es_esearch_expinst_solution ees on ec.solution_id = ees.solution_id ");
		sb.append("where a.source_from = '").append(ManagerUtils.getSourceFrom()).append("' ");
		
		if (inner != null){
			if (!StringUtil.isEmpty(inner.getSearch_code())){
				sb.append(" and esp.search_code = ? ");
				sqlParams.add(inner.getSearch_code());
			}
		    if ((!StringUtil.isEmpty(inner.getMatch_content())) && (!"-1".equals(inner.getMatch_content()))){
		    	sb.append(" and esp.match_content = ? ");
		        sqlParams.add(inner.getMatch_content());
		    }
		    if (!StringUtil.isEmpty(inner.getShortcut_solution_id())){
		    	sb.append(" and exists (select 1 from es_esearch_catalog ec ");
		    	sb.append(" where a.catalog_id = ec.catalog_id and ec.solution_id = ?)");
		        sqlParams.add(inner.getShortcut_solution_id());
		    }
			//订单来源
			if(!StringUtil.isEmpty(inner.getOrder_from()) && !"-1".equals(inner.getOrder_from())){
				sb.append(" and eoe.order_from in('"+inner.getOrder_from().replace(",", "','")+"')");
			}
			//地市
			if(!StringUtil.isEmpty(inner.getOrder_city_code()) && !"-1".equals(inner.getOrder_city_code())){
				sb.append(" and eoe.order_city_code in('"+inner.getOrder_city_code().replace(",", "','")+"')");
			}
			//订单异常环节
			if(!StringUtil.isEmpty(inner.getFlow_id()) && !"-1".equals(inner.getFlow_id())){
				if(inner.getFlow_id().contains(EcsOrderConsts.FLOW_TRACE_GJ)){
					sb.append(" and (b.tache_code in ('"+inner.getFlow_id().replace(",", "','")+"') or b.tache_code is null)");
				}else{
					sb.append(" and b.tache_code in ('"+inner.getFlow_id().replace(",", "','")+"') ");					
				}
			}
		}
		
		if(admin != null && admin.getFounder() != 1) {//如果不是超级管理员需要进行权限控制
			sb.append(" and exists( select 1 ");
			sb.append(" from es_user_role ur,es_role_auth ra, es_role_data rd");
			sb.append(" where ur.roleid=ra.roleid and ra.authid=rd.id");
			sb.append(" and ur.source_from = rd.source_from and ur.source_from = ra.source_from");
			sb.append(" and (regexp_like(rd.orderexp_catalog, ',' || a.catalog_id || ',', 'i')");
			sb.append(" or regexp_like(rd.orderexp_catalog, ',' ||").append(EcsOrderConsts.EXP_INST_LIST_ALL).append("|| ',', 'i'))");
			sb.append(" and ur.userid = ?)");
			sqlParams.add(admin.getUserid());
		}
		
		if(admin != null){
			Map map = BeanUtils.getCurrHostEnv();//获取当前的主机环境
			if(map != null&& map.size() > 0) {
				String busi_version = (String)map.get("BUSI_VERSION");//
				String env_group = (String)map.get("ENV_GROUP");//
				String env_status = (String)map.get("ENV_STATUS");
				sb.append(" and ");
				if(("A".equals(env_group)||"B".equals(env_group))&&EcsOrderConsts.EXP_00A.equals(env_status)){
					sb.append(" (");
				}
				
				sb.append(" exists( select 1 from es_abgray_ord_env_rel aorl, es_abgray_hostenv ah");
				sb.append(" where aorl.env_code = ah.env_code and aorl.out_tid = a.out_tid and ah.busi_version = ");
				sb.append("'").append(busi_version).append("'");
				sb.append(" and ah.env_group = ");
				sb.append("'").append(env_group).append("') ");
				if(("A".equals(env_group)||"B".equals(env_group))&&EcsOrderConsts.EXP_00A.equals(env_status)){
					sb.append(" or not exists (select 1 from es_abgray_ord_env_rel aorl where aorl.out_tid = a.out_tid) )");
				}
			}			
			sb.append(" and a.is_visible = '"+EcsOrderConsts.EXP_INST_IS_VISIBLE_Y+"' ");
		}
		
		Object[] objs = new Object[2];
		objs[0] = sb.toString();
		objs[1] = sqlParams;
		return objs;
	}
	
	@Override
	public Page queryExpInstPage(ExpInstQueryInner inner, AdminUser admin, int pageNo, int pageSize) {
		ExpInstQueryOuter outer = new ExpInstQueryOuter();
		Object[] objs = this.sqlExpInstList(inner, admin);
		String sql = objs[0].toString();
		String countSql = "select count(*) from ("+sql+")";
		sql += " order by a.excp_create_time desc";
		List sqlParams = (List)objs[1];
		long start1 = System.currentTimeMillis();
		long start = System.currentTimeMillis();
		Page page = this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, EsearchExpInstQuery.class, countSql, sqlParams.toArray());
		logger.info("queryExpInstSql:"+sql);
		String action_url = "";
		
		if(page.getResult() != null && page.getResult().size() > 0) {
			//获取流程列表数据
			DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
	        List<Map> flowTraceList = dcPublicCache.getList(EcsOrderConsts.FLOW_TRACE_CACHE_KEY);
	        
			for(int i=0; i<page.getResult().size(); i++) {
				EsearchExpInstQuery eeiq = (EsearchExpInstQuery)page.getResult().get(i);
				if(eeiq.getRel_obj_type().equals(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER)) {
					eeiq.setAction_url(action_url);
					try {
						start = System.currentTimeMillis();
						String city = eeiq.getOrder_city();
						logger.info("city:"+(System.currentTimeMillis()-start)+"ms");
						if(StringUtils.isNotEmpty(city)) {
							String city_name = OrderExpUtils.getCacheName("DC_MODE_REGION", city);
							eeiq.setOrder_city(city_name == null ? "":city_name);
						}
						start = System.currentTimeMillis();
						String order_from = eeiq.getOrder_from();
						logger.info("order_from:"+(System.currentTimeMillis()-start)+"ms");
						if(StringUtils.isNotEmpty(order_from)) {
							String order_from_name = OrderExpUtils.getCacheName("ORDER_FROM", order_from);
							eeiq.setOrder_from_name(order_from_name == null ? "":order_from_name);
						}
						start = System.currentTimeMillis();
						String order_model = eeiq.getOrder_mode();
						logger.info("order_model:"+(System.currentTimeMillis()-start)+"ms");
						if(StringUtils.isNotEmpty(order_model)) {
							String order_model_name = OrderExpUtils.getCacheName("DC_MODE_OPER_MODE", order_model);
							eeiq.setOrder_mode_name(order_model_name == null ? "":order_model_name);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
				//判断锁定状态，设置锁单样式,只有状态为order的才有订单
				if(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER.equals(eeiq.getRel_obj_type())){			
					OrderTreeBusiRequest orderTree= null;
					try {
						orderTree = CommonDataFactory.getInstance().getOrderTree(eeiq.getRel_obj_id());//内部订单号
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(null != orderTree){
						//设置样式
						if(EcsOrderConsts.LOCK_STATUS.equals(orderTree.getOrderExtBusiRequest().getLock_status())){
							if(ManagerUtils.getAdminUser().getUserid().equals(orderTree.getOrderExtBusiRequest().getLock_user_id())){
								eeiq.setLock_clazz("unlock");
							}else{
								eeiq.setLock_clazz("otherlock");
							}
						}else{
							eeiq.setLock_clazz("lock");
						}
						//设置锁单人
						eeiq.setLock_user_name(orderTree.getOrderExtBusiRequest().getLock_user_name());
						eeiq.setPhone_num(CommonDataFactory.getInstance().getAttrFieldValueHis(eeiq.getRel_obj_id(), AttrConsts.PHONE_NUM));
					}
				}
				
				//翻译订单环节
				if(StringUtil.isEmpty(eeiq.getTache_code())){
					eeiq.setTache_code_c(EcsOrderConsts.FLOW_TRACE_GJ_C);
					eeiq.setTache_code(EcsOrderConsts.FLOW_TRACE_GJ);
				}else{
					for(Map map : flowTraceList){
						if(map.get("pkey").equals(eeiq.getTache_code())){
							eeiq.setTache_code_c((String)map.get("pname"));
							eeiq.setTache_code((String)map.get("pkey"));
							break;
						}
					}
				}
				
			}
		}
		logger.info("all time:"+(System.currentTimeMillis()-start1)+"ms");
		return page;
	}
	
	@Override
	public EsearchExpInstQuery getExpInstDetailById(String expInstId) {
		String sql = SF.orderExpSql("ExpInstDetailById");
		if(StringUtils.isEmpty(expInstId)) {return null;}
		EsearchExpInstQuery expInst = (EsearchExpInstQuery)this.baseDaoSupport.queryForObject(sql, EsearchExpInstQuery.class, expInstId);
		if(expInst == null) {return null;}
		
		try{
			String city = CommonDataFactory.getInstance()
					.getAttrFieldValue(expInst.getRel_obj_id(), AttrConsts.ORDER_CITY_CODE);
			if(StringUtils.isNotEmpty(city)) {
				String city_name = OrderExpUtils.getCacheName("DC_MODE_REGION", city);
				expInst.setOrder_city(city_name == null ? "":city_name);
			}
			String order_from = CommonDataFactory.getInstance()
							.getAttrFieldValue(expInst.getRel_obj_id(), AttrConsts.ORDER_FROM);
			if(StringUtils.isNotEmpty(order_from)) {
				String order_from_name = OrderExpUtils.getCacheName("ORDER_FROM", order_from);
				expInst.setOrder_from_name(order_from_name == null ? "":order_from_name);
			}
			String order_model = CommonDataFactory.getInstance()
							.getAttrFieldValue(expInst.getRel_obj_id(), AttrConsts.ORDER_MODEL);
			if(StringUtils.isNotEmpty(order_model)) {
				String order_model_name = OrderExpUtils.getCacheName("DC_MODE_OPER_MODE", order_model);
				expInst.setOrder_mode_name(order_model_name == null ? "":order_model_name);
			}
			
			if(StringUtils.isNotEmpty(expInst.getDeal_staff_no())) {
				if("-1".equals(expInst.getDeal_staff_no())) {
					expInst.setDeal_staff_realname("系统");
				}else {
					AdminUserReq adminUserReq = new AdminUserReq();
			        adminUserReq.setUser_id(expInst.getDeal_staff_no());
			        AdminUserResp adminUserResp = adminUserServ.getAdminUserById(adminUserReq);
				    AdminUser adminUser = new AdminUser();
					if(adminUserResp != null){
						adminUser = adminUserResp.getAdminUser();
						expInst.setDeal_staff_realname(adminUser.getRealname());
						expInst.setDeal_staff_phoneno(adminUser.getPhone_num());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return expInst;
	}
	
	@Override
	public ExpInstQueryOuter queryExpInstList(ExpInstQueryInner inner) {
		ExpInstQueryOuter outer = new ExpInstQueryOuter();
		List<Object> sqlParams = new ArrayList<Object>();
		StringBuffer sql = this.getOrderExpListSql(inner, sqlParams);
		sql.append(" order by a.excp_create_time desc");
		List<EsearchExpInstQuery> esearchExpInstList = this.baseDaoSupport.queryForList(sql.toString(), EsearchExpInstQuery.class, sqlParams.toArray());
		outer.setEsearchExpInstList(esearchExpInstList);
		return outer;
	}

	@Override
	public OrderExpAlarmOuter orderExpAlarm(OrderExpAlarmInner inner) {
		OrderExpAlarmOuter outer = new OrderExpAlarmOuter();
		//按时间维度统计异常单的数量
		List sqlParams = new ArrayList();
		String sql = "select count(1) as counts,key_id from es_esearch_expinst where 1=1";
		if(StringUtils.isNotEmpty(inner.getCreate_start_time())) {
			sql += " excp_create_time <= "+DBTUtil.to_sql_date("?", 2);
			sqlParams.add(inner.getCreate_start_time());
		}
		if(StringUtils.isNotEmpty(inner.getCreate_end_time())) {
			sql += " excp_create_time <= "+DBTUtil.to_sql_date("?", 2);
			sqlParams.add(inner.getCreate_end_time());
		}
		sql += " group by key_id;";
		List<Map> orderExpCountsList = this.baseDaoSupport.queryForList(sql,sqlParams);
		if(orderExpCountsList != null && orderExpCountsList.size() > 0) {
			List<OrderExpAlarm> oeaList = new ArrayList<OrderExpAlarm>();
			for(Map map:orderExpCountsList) {
				OrderExpAlarm oea = new OrderExpAlarm();
				oea.setKey_id(map.get("key_id").toString());
				oea.setCounts(map.get("counts") == null ? 0L:Long.parseLong(map.get("counts").toString()));
				
				SpecvaluesQueryInner svqInner = new SpecvaluesQueryInner();
				svqInner.setKey_id(map.get("key_id").toString());
				SpecvaluesQueryOuter svqOuter = this.esearchSpecvaluesManager.querySpecvalues(svqInner);
				EsearchSpecvalues esv = svqOuter.getSpecvalues();
				
				if(esv != null) {
					oea.setMatch_content(esv.getMatch_content());
					oea.setAlarm_flag(esv.getAlarm_flag());
					oea.setAlarm_limit(esv.getAlarm_limit());
					oea.setWarming_flag(esv.getWarming_flag());
					oea.setWarming_limit(esv.getWarming_limit());
				}
				oeaList.add(oea);
			}
			outer.setOrderExpStats(oeaList);
		}
		return outer;
	}
	
	@Override
	public Page topExpKey(String startTime, String endTime, int pageNo, int pageSize) {
		List<Object> sqlParams = new ArrayList<Object>();
		StringBuffer sqlsb = new StringBuffer();
		sqlsb.append("select ee.key_id,es.search_id, es.match_content, esc.search_name, count(ee.excp_inst_id) as counts ");
		sqlsb.append(" from es_esearch_expinst ee left join es_esearch_specvalues es on ee.key_id = es.key_id");
		sqlsb.append(" left join es_esearch_spec esc on es.search_id = esc.search_id");
		sqlsb.append(" where ee.source_from ='").append(ManagerUtils.getSourceFrom()).append("' ");
		sqlsb.append(" and ee.is_visible = '").append(EcsOrderConsts.EXP_INST_IS_VISIBLE_Y).append("' ");
		
		sqlsb.append(" and es.match_content != '").append(EcsOrderConsts.EXP_DEFAULT_MATCH_CONTENT).append("' ");
		if(StringUtils.isNotEmpty(startTime)) {
			sqlsb.append(" and ee.excp_create_time >=").append(DBTUtil.to_sql_date("?", 2));
	        sqlParams.add(startTime);
		}
		
		if(StringUtils.isNotEmpty(endTime)) {
			sqlsb.append(" and ee.excp_create_time <=").append(DBTUtil.to_sql_date("?", 2));
	        sqlParams.add(endTime);
		}
		
		Map map = BeanUtils.getCurrHostEnv();//获取当前的主机环境
		if(map != null&& map.size() > 0) {
			String busi_version = (String)map.get("BUSI_VERSION");//
			String env_group = (String)map.get("ENV_GROUP");//
			logger.info("busi_version:"+busi_version);
			sqlsb.append(" and exists( select 1 from es_abgray_ord_env_rel_log aorl, es_abgray_hostenv ah");
			sqlsb.append(" where aorl.env_code = ah.env_code and aorl.out_tid = ee.out_tid and ah.busi_version = ");
			sqlsb.append("'").append(busi_version).append("'");
			sqlsb.append(" and ah.env_group = ");
			sqlsb.append("'").append(env_group).append("')");
		}
		sqlsb.append(" group by ee.key_id,es.search_id,es.search_id, esc.search_name, es.match_content");
		sqlsb.append(" order by counts desc");
		Page page = this.baseDaoSupport.queryForCPage(sqlsb.toString(), pageNo, pageSize, TopExpKey.class, "select count(1) from ("+sqlsb.toString()+") ", sqlParams.toArray());
		return page;
	}
	
	@Override
	public List<CatalogAndSolution> qryCatalogAndSolution() {
		String sql = "SELECT ees.solution_id, ees.solution_name, ees.solution_desc"
				+ " FROM es_esearch_expinst_solution ees where ees.source_from ='"+ManagerUtils.getSourceFrom()+"' ";
		List<CatalogAndSolution> list = this.baseDaoSupport.queryForList(sql, CatalogAndSolution.class);
		return list;
	}
	
	/**
	 * 执行方案
	 * @param solution
	 * @param expInst
	 * @return
	 */
	private ExpInstProcessedOuter excutePlan(EsearchExpInstSolution solution, EsearchExpInst expInst) {
		PlanRuleTreeExeReq plan = new PlanRuleTreeExeReq();
		ExpInstProcessedOuter processedOuter = new ExpInstProcessedOuter();
		Object fact = null;
		try {
			fact = Class.forName(solution.getHander_fact()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(fact == null) {
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg("方案执行失败：创建"+solution.getHander_fact()+"实例失败");
			return processedOuter;
		}
		
		if(expInst.getRel_obj_type().equals(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER)) {
			this.invokeMethod(fact, "setOrder_id", new Object[]{expInst.getRel_obj_id()});
		}
		this.invokeMethod(fact, "setObj_id", new Object[]{expInst.getRel_obj_id()});
		if(EcsOrderConsts.EXP_SOLUTION_IS_DELETE_RULE_LOG_Y
				.equals(solution.getIs_delete_rule_log())) {
			plan.setDeleteLogs(true);
		}
		plan.setFact((AutoFact)fact);
		plan.setPlan_id(solution.getSolution_value());
		plan.setReCurrRule(true);
		try {
			PlanRuleTreeExeResp rsp = CommonDataFactory.getInstance().exePlanRuleTree(plan);
			BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(rsp);
			logger.info("expInst_id:"+expInst.getExcp_inst_id()+"   getError_code:"+busiResp.getError_code()+"  getError_msg:"+busiResp.getError_msg());
			if(busiResp == null || !busiResp.getError_code().equals(ConstsCore.ERROR_SUCC)) {//方案执行非成功
				processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
				processedOuter.setOuter_msg("方案执行失败：erro_code:"+busiResp.getError_code()+"   erro_msg:"+busiResp.getError_msg());
				return processedOuter;
			}else {
				processedOuter.setOuter_status(ConstsCore.ERROR_SUCC);
				processedOuter.setOuter_msg("执行方案"+solution.getSolution_value()+"  error_code:"+busiResp.getError_code()+"  error_msg:"+busiResp.getError_msg());
				return processedOuter;
			}
		} catch (Exception e) {
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg("方案执行失败："+e.getMessage());
			return processedOuter;
		}
	}
	
	/**
	 * 执行规则
	 * @param solution
	 * @param expInst
	 * @return
	 */
	private ExpInstProcessedOuter excuteRule(EsearchExpInstSolution solution, EsearchExpInst expInst) {
		ExpInstProcessedOuter processedOuter = new ExpInstProcessedOuter();
		Object fact = null;
		try {
			fact = Class.forName(solution.getHander_fact()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(fact == null) {
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg("方案执行失败：创建"+solution.getHander_fact()+"实例失败");
			return processedOuter;
		}
		
		if(expInst.getRel_obj_type().equals(EcsOrderConsts.EXPINST_REL_OBJ_TYPE_ORDER)) {
			this.invokeMethod(fact, "setOrder_id", new Object[]{expInst.getRel_obj_id()});
		}
		this.invokeMethod(fact, "setObj_id", new Object[]{expInst.getRel_obj_id()});
		RuleTreeExeReq  req=new RuleTreeExeReq();
		req.setRule_id(solution.getSolution_value());
		req.setReCurrRule(true);
		req.setExePeerAfRules(false);
		req.setExeParentsPeerAfRules(false);
		req.setFact((AutoFact)fact);
		RuleTreeExeResp ruleResp = CommonDataFactory.getInstance().exeRuleTree(req);
		BusiCompResponse busiResp = CommonDataFactory.getInstance().getRuleTreeresult(ruleResp);
		if(busiResp == null || !busiResp.getError_code().equals(ConstsCore.ERROR_SUCC)) {//规则执行非成功
			processedOuter.setOuter_status(ConstsCore.ERROR_FAIL);
			processedOuter.setOuter_msg("规则执行失败：erro_code:"+busiResp.getError_code()+"   erro_msg:"+busiResp.getError_msg());
			return processedOuter;
		}else {
			processedOuter.setOuter_status(ConstsCore.ERROR_SUCC);
			processedOuter.setOuter_msg("执行规则"+solution.getSolution_value()+"  error_code:"+busiResp.getError_code()+"  error_msg:"+busiResp.getError_msg());
			return processedOuter;
		}
	}
	
	/**
	 * 批量执行解决方案中的SQL
	 * @param sqls
	 * @param order_id
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int excuteSolutionSql(String sqls, String order_id) {
		if(StringUtils.isEmpty(sqls)) 
			return -1;
		String[] sqlArray = sqls.split(";");
		if(sqlArray == null || sqlArray.length < 1)
			return -1;
		
		int counts = 0;
		try {
			for(String solution_sql:sqlArray) {
				if(solution_sql.indexOf("?") != -1){
					solution_sql = solution_sql.replaceAll("\\?", "'"+order_id+"'");
			    }
				this.baseDaoSupport.execute(solution_sql);
				counts ++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addError(ConstsCore.ERROR_FAIL, "SQL语句执行失败");
		}
		return counts;
	}
	
	@Override
	public List<EsearchExpInst> getExpInsts() {
		String sql = SF.orderExpSql("AllExpInsts");
		List<EsearchExpInst> result = this.baseDaoSupport.queryForList(sql, EsearchExpInst.class, EcsOrderConsts.EXPINST_RECORD_STATUS_1);
		return result;
	}
	
	@Override
	public List<EsearchExpInst> getExpHisInsts() {
		String sql = SF.orderExpSql("AllExpHisInsts");
		//小于3个月的时间
		Calendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, -3);
		String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date.getTime());
		List<EsearchExpInst> result = this.baseDaoSupport.queryForList(sql, EsearchExpInst.class, dateTime);
		return result;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void orderExpArchive(EsearchExpInst expInst) {
		String archiveExpHis = SF.orderExpSql("ArchiveExpHis");
		String archiveExpExtHis = SF.orderExpSql("ArchiveExpExtHis");
		String delExp = SF.orderExpSql("DelExp");
		String delExpExt = SF.orderExpSql("DelExpExt");
		try {
			//插入异常实例历史表
			this.baseDaoSupport.execute(archiveExpHis, expInst.getExcp_inst_id());
			this.baseDaoSupport.execute(archiveExpExtHis, expInst.getExcp_inst_id());
			//删除异常实例
			this.baseDaoSupport.execute(delExp, expInst.getExcp_inst_id());
			this.baseDaoSupport.execute(delExpExt, expInst.getExcp_inst_id());
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addError(ConstsCore.ERROR_FAIL,"异常单:"+expInst.getExcp_inst_id()+" 归档失败"+e.getMessage());	
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void orderExpHisArchive(EsearchExpInst expInst) {
		String archiveExpHis = SF.orderExpSql("ArchiveExpHisHis");
		String archiveExpExtHis = SF.orderExpSql("ArchiveExpExtHisHis");
		String delExp = SF.orderExpSql("DelExpHis");
		String delExpExt = SF.orderExpSql("DelExpExtHis");
		try {
			//插入异常实例历史表
			this.baseDaoSupport.execute(archiveExpHis, expInst.getExcp_inst_id());
			this.baseDaoSupport.execute(archiveExpExtHis, expInst.getExcp_inst_id());
			//删除异常实例
			this.baseDaoSupport.execute(delExp, expInst.getExcp_inst_id());
			this.baseDaoSupport.execute(delExpExt, expInst.getExcp_inst_id());
		} catch (Exception e) {
			e.printStackTrace();
			CommonTools.addError(ConstsCore.ERROR_FAIL,"异常历史单:"+expInst.getExcp_inst_id()+" 归档失败"+e.getMessage());	
		}
	}
	
	@Override
	public Page queryExpInstSpecList(ExpInstSpecInner eisInner,int pageNo,int pageSize){
		//String table = "es_esearch_expinst";
		String sql = "select a.key_id,a.rel_obj_id from es_esearch_expinst a where a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		sql = sql + " and a.excp_create_time >= to_date('"+eisInner.getStart_time()+"','yyyy-MM-dd')";
		sql = sql +" and a.excp_create_time <= to_date('"+eisInner.getEnd_time()+"','yyyy-MM-dd') ";
		String sql2 = sql.replace("es_esearch_expinst", "es_esearch_expinst_his");
		sql = "select aa.key_id,count(1) c from ("+sql+" union  "+sql2+")aa group by aa.key_id ";
		sql = " select t.key_id, t.c,b.match_content,s.search_code,s.search_name from ("+sql+") t , es_esearch_specvalues b , es_esearch_spec s";
		sql = sql + " where t.key_id = b.key_id and b.search_id = s.search_id and b.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		if(!StringUtil.isEmpty(eisInner.getMatch_content())){
			sql = sql + " and b.match_content like '%"+eisInner.getMatch_content()+"%'";
		}
		sql = sql + " order by t.c desc";
		logger.info(sql);
		//String countsql = "select count(1) from ("+sql+") t1";
		Page page = baseDaoSupport.queryForPage(sql, pageNo, pageSize);
		return page;
	}
	
	@Override
	public List queryInstSpecOnDayList(String start_time,String end_time,String key_id){
		String sql = "select to_char(a.excp_create_time,'yyyy-MM-dd') cday, a.key_id,a.rel_obj_id ";
		sql = sql + " from es_esearch_expinst a where a.source_from='"+ManagerUtils.getSourceFrom()+"'";
		sql = sql +" and a.excp_create_time >= to_date('"+start_time+"','yyyy-MM-dd')";
		sql = sql + " and a.excp_create_time <= to_date('"+end_time+"','yyyy-MM-dd')";
		sql = sql + " and a.key_id = '"+key_id+"' ";
		//sql = sql + " group by to_char(a.excp_create_time,'yyyy-MM-dd'), b.key_id  ";
		String sql2 = sql.replace("es_esearch_expinst", "es_esearch_expinst_his");
		sql = "select aa.cday,aa.key_id,count(1) c from ("+sql+" union  "+sql2+") aa group by aa.cday, aa.key_id ";
		sql = "select t.cday, t.key_id, t.c from ("+sql+") t order by t.cday ";
		logger.info(sql);
		List list = baseDaoSupport.queryForList(sql);
		return list;
	}
	
	@Override
	public String getContentBykeyid(String key_id){
		String sql = "select match_content from es_esearch_specvalues where key_id=?";
		String match_content = baseDaoSupport.queryForString(sql, key_id);
		return match_content;
	}
	
	public IEsearchSpecvaluesManager getEsearchSpecvaluesManager() {
		return esearchSpecvaluesManager;
	}

	public void setEsearchSpecvaluesManager(
			IEsearchSpecvaluesManager esearchSpecvaluesManager) {
		this.esearchSpecvaluesManager = esearchSpecvaluesManager;
	}

	public IExpConfigManager getExpConfigManager() {
		return expConfigManager;
	}

	public void setExpConfigManager(IExpConfigManager expConfigManager) {
		this.expConfigManager = expConfigManager;
	}
	
	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public AdminUserInf getAdminUserServ() {
		return adminUserServ;
	}

	public void setAdminUserServ(AdminUserInf adminUserServ) {
		this.adminUserServ = adminUserServ;
	}

	@Override
	public Page queryExpInstPage(ExpInstQueryInner inner, int pageNo,
			int pageSize) {
		List<Object> sqlParams = new ArrayList<Object>();
		StringBuffer sql = this.getOrderExpListSql(inner, sqlParams);
		String countSql = "select count(*) from ("+sql+")";
		sql.append(" order by a.excp_inst_id desc");
		Page page = this.baseDaoSupport.queryForCPage(sql.toString(), pageNo, pageSize, EsearchExpInstQuery.class, countSql, sqlParams.toArray());
		return page;
	}
	
	//异常实例查询sql
	private StringBuffer getOrderExpListSql(ExpInstQueryInner inner, List<Object> sqlParams){
		StringBuffer sql = new StringBuffer(SF.orderExpSql("OrderExpList"));
		
		if (inner != null){
			if ((!StringUtil.isEmpty(inner.getSearch_id())) && (!"-1".equals(inner.getSearch_id()))){
				sql.append(" and a.search_id = ? ");
				sqlParams.add(inner.getSearch_id());
			}
			if (!StringUtil.isEmpty(inner.getSearch_code())){
				sql.append(" and esp.search_code = ? ");
				sqlParams.add(inner.getSearch_code());
			}
			if ((!StringUtil.isEmpty(inner.getKey_id())) && (!"-1".equals(inner.getKey_id()))){
				sql.append(" and a.key_id = ? ");
		        sqlParams.add(inner.getKey_id());
			}
		    if (!StringUtil.isEmpty(inner.getCatalog_id())){
		    	sql.append(" and a.catalog_id = ? ");
		        sqlParams.add(inner.getCatalog_id());
		    }
		    if ((!StringUtil.isEmpty(inner.getRel_obj_type()))){
		    	sql.append(" and a.rel_obj_type = ? ");
		        sqlParams.add(inner.getRel_obj_type());
		    }
		    if ((!StringUtil.isEmpty(inner.getOut_tid()))){
		    	sql.append(" and a.out_tid = ? ");
		        sqlParams.add(inner.getOut_tid().trim());
		    }
		    if (!StringUtil.isEmpty(inner.getStart_excp_time())){
		    	sql.append(" and a.excp_create_time>=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getStart_excp_time());
		    }
		    if (!StringUtil.isEmpty(inner.getEnd_excp_time())){
		    	sql.append(" and a.excp_create_time<=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getEnd_excp_time());
		    }
		    if (!StringUtil.isEmpty(inner.getStart_obj_create_time())){
		    	sql.append(" and a.rel_obj_create_time>=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getStart_obj_create_time());
		    }
		    if (!StringUtil.isEmpty(inner.getEnd_obj_create_time())){
		    	sql.append(" and a.rel_obj_create_time<=").append(DBTUtil.to_sql_date("?", 2));
		        sqlParams.add(inner.getEnd_obj_create_time());
		    }
		    if ((!StringUtil.isEmpty(inner.getRel_obj_id())) && (!"-1".equals(inner.getRel_obj_id()))){
		    	sql.append(" and a.rel_obj_id = ? ");
			    sqlParams.add(inner.getRel_obj_id().trim());
		    }
		    if ((!StringUtil.isEmpty(inner.getExcp_inst_id())) && (!"-1".equals(inner.getExcp_inst_id()))){
		    	sql.append(" and a.excp_inst_id = ? ");
		        sqlParams.add(inner.getExcp_inst_id().trim());
		    }
		    if("N".equals(inner.getHas_match_content())){
		    	sql.append(" and esp.match_content is null");
		    }else{
		    	sql.append(" and esp.match_content is not null");
		    }
		    if ((!StringUtil.isEmpty(inner.getMatch_content())) && (!"-1".equals(inner.getMatch_content()))){
		    	sql.append(" and esp.match_content = ? ");
		        sqlParams.add(inner.getMatch_content().trim());
		    }
		    if ((!StringUtil.isEmpty(inner.getRecord_status())) && (!"-1".equals(inner.getRecord_status()))){
		    	sql.append(" and a.record_status = ? ");
		        sqlParams.add(inner.getRecord_status().trim());
		    }
		}
		
		return sql;
	}
 
	@Override
	public Page querySpecCatalogList(String catalog_id,String order_model,int pageNo,int pageSize,String start_time,String end_time){
		String sql = "select t.catalog_id,(select ee.match_content from es_esearch_specvalues ee where ee.key_id = t.key_id) match_content,t.key_id,count(1) catalog_count from (";
		sql = sql + " select distinct b.catalog_id, a.key_id,a.rel_obj_id from es_esearch_expinst a , es_esearch_specvalues_relc b ,es_order_ext c";
		sql = sql + " where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.is_visible='Y' and a.key_id = b.key_id and a.rel_obj_id = c.order_id  and b.catalog_id = '"+catalog_id+"'";
		if(!StringUtil.isEmpty(order_model)&&!"-1".equals(order_model)){
			sql = sql + " and c.order_model = '"+order_model+"'";
		}
		if(!StringUtil.isEmpty(start_time)&&!StringUtil.isEmpty(end_time)){
			sql = sql +" and a.excp_create_time >= to_date('"+start_time+"','yyyy-MM-dd HH24:mi:ss')";
			sql = sql +" and a.excp_create_time < to_date('"+end_time+"','yyyy-MM-dd HH24:mi:ss')";
		}
		try{
			Map mapEnv = BeanUtils.getCurrHostEnv();//获取当前的主机环境
			if(mapEnv != null&& mapEnv.size() > 0) {
				String busi_version = (String)mapEnv.get("BUSI_VERSION");//
				String env_group = (String)mapEnv.get("ENV_GROUP");//
				logger.info("busi_version:"+busi_version);
				StringBuffer sqlBuff = new StringBuffer();
				sqlBuff.append(" and exists( select 1 from es_abgray_ord_env_rel_log aorl, es_abgray_hostenv ah");
				sqlBuff.append(" where aorl.env_code = ah.env_code and aorl.out_tid = c.out_tid and ah.busi_version = ");
				sqlBuff.append("'").append(busi_version).append("'");
				sqlBuff.append(" and ah.env_group = ");
				sqlBuff.append("'").append(env_group).append("')");
				sql = sql + sqlBuff.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("查询主机环境失败");
		}
		AdminUser admin = ManagerUtils.getAdminUser();
		if(admin != null && admin.getFounder() != 1) {//如果不是超级管理员需要进行权限控制
			StringBuffer sb = new StringBuffer();
			sb.append(" and exists( select 1 ");
			sb.append(" from es_user_role ur,es_role_auth ra, es_role_data rd");
			sb.append(" where ur.roleid=ra.roleid and ra.authid=rd.id");
			sb.append(" and ur.source_from = rd.source_from and ur.source_from = ra.source_from");
			sb.append(" and (regexp_like(rd.orderexp_catalog, ',' || a.catalog_id || ',', 'i')");
			sb.append(" or regexp_like(rd.orderexp_catalog, ',' ||").append(EcsOrderConsts.EXP_INST_LIST_ALL).append("|| ',', 'i'))");
			sb.append(" and ur.userid = '"+admin.getUserid()+"')");
			sql = sql + sb.toString();
		}
		sql = sql + " ) t  group by t.catalog_id, t.key_id";
		String countsql = "select count(1) from ("+sql+") tt";
		Page page = baseDaoSupport.queryForCPage(sql, pageNo, pageSize, ExpInstSpecCatalogResp.class, countsql);
		return page;
	}
	@Override
	public int querySpecCatalogCount(String catalog_id,String start_time,String end_time){
		String sql = "select count(1) catalog_count from (";
		sql = sql + " select distinct b.catalog_id, a.key_id,a.rel_obj_id from es_esearch_expinst a , es_esearch_specvalues_relc b ,es_order_ext c";
		sql = sql + " where a.source_from='"+ManagerUtils.getSourceFrom()+"' and a.key_id = b.key_id and a.rel_obj_id = c.order_id  and b.catalog_id = '"+catalog_id+"'";
		if(!StringUtil.isEmpty(start_time)&&!StringUtil.isEmpty(end_time)){
			sql = sql +" and a.excp_create_time >= to_date('"+start_time+"','yyyy-MM-dd HH24:mi:ss')";
			sql = sql +" and a.excp_create_time < to_date('"+end_time+"','yyyy-MM-dd HH24:mi:ss')";
		}
		try{
			Map mapEnv = BeanUtils.getCurrHostEnv();//获取当前的主机环境
			if(mapEnv != null&& mapEnv.size() > 0) {
				String busi_version = (String)mapEnv.get("BUSI_VERSION");//
				String env_group = (String)mapEnv.get("ENV_GROUP");//
				logger.info("busi_version:"+busi_version);
				StringBuffer sqlBuff = new StringBuffer();
				sqlBuff.append(" and exists( select 1 from es_abgray_ord_env_rel_log aorl, es_abgray_hostenv ah");
				sqlBuff.append(" where aorl.env_code = ah.env_code and aorl.out_tid = c.out_tid and ah.busi_version = ");
				sqlBuff.append("'").append(busi_version).append("'");
				sqlBuff.append(" and ah.env_group = ");
				sqlBuff.append("'").append(env_group).append("')");
				sql = sql + sqlBuff.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.info("查询主机环境失败");
		}
		
		sql = sql + " ) t  group by t.catalog_id, t.key_id";
		
		String countsql = "select sum(tt.catalog_count) from ("+sql+") tt";
		logger.info("countsql:"+countsql);
		int c = baseDaoSupport.queryForInt(countsql);
		return c;
	}
	@Override
	public int queryExpInstCount(String start_time,String end_time){
		String sql = "select count(1) c from (select distinct a.key_id,a.rel_obj_id from es_esearch_expinst a where a.source_from = '"+ManagerUtils.getSourceFrom()+"'";
		sql = sql +" and a.excp_create_time >= to_date('"+start_time+"','yyyy-MM-dd HH24:mi:ss')";
		sql = sql +" and a.excp_create_time < to_date('"+end_time+"','yyyy-MM-dd HH24:mi:ss')";
		sql = sql +" ) t";
		int c = baseDaoSupport.queryForInt(sql);
		return c;
	}
}