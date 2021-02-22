package com.ztesoft.net.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import params.ZteRequest;
import params.ZteResponse;
import zte.params.order.req.UnimallOrderDataReq;
import zte.params.order.resp.UnimallOrderQueryResp;

import com.lmax.disruptor.dsl.Disruptor;
import com.ztesoft.api.Task;
import com.ztesoft.api.TaskThreadPool;
import com.ztesoft.api.ThreadPoolFactory;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.mmt.guide.asyn.AsynServiceEvent;
import com.ztesoft.mmt.guide.asyn.DisruptorInstFactory;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.eop.sdk.database.BaseSupportGProduct;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.model.UnimallOrderInfo;
import com.ztesoft.net.service.IUnimallOrderLocalManager;
import com.ztesoft.net.service.IUnimallOrderLocalMangerT;
import com.ztesoft.net.service.IUnimallOrderQueryManager;
import com.ztesoft.net.sqls.SF;

import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantReadWriteLock;

public class UnimallOrderQueryManager extends BaseSupportGProduct implements IUnimallOrderQueryManager{

	
	UnimallOrderLocalManagerT UnimallOrderLocalManagerT;
	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	
	public UnimallOrderLocalManagerT getUnimallOrderLocalManagerT() {
		return UnimallOrderLocalManagerT;
	}

	public void setUnimallOrderLocalManagerT(
			UnimallOrderLocalManagerT unimallOrderLocalManagerT) {
		UnimallOrderLocalManagerT = unimallOrderLocalManagerT;
	}


	private final int NAMESPACE = 101;
	int time =60*24*60;
	static INetCache cache;
	static{
		cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
	}
	
	private static ThreadPoolExecutor executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_NEW,62);
	
	@Override
	//订单查询
	public UnimallOrderQueryResp queryUnimallOrder(Map queryParams) {
		// TODO Auto-generated method stub
		UnimallOrderQueryResp resp= new UnimallOrderQueryResp();	
		if(queryParams.get("updateflag")!=null&&queryParams.get("updateflag").toString().equals("yes"))
		{
			this.updateUnimallOrder(queryParams);
			return resp;
		}
		
		List<UnimallOrderInfo> orderResp=new ArrayList<UnimallOrderInfo>();
		List params = new ArrayList();
		
		String sqlOrderidQuery=SF.orderSql("UNIMALL_ORDERID_QUERY");
		String sqlWhere="";
		String oper_id=null;
		
		if(queryParams.get("oper_id")!=null&&!queryParams.get("oper_id").toString().equals("")){
			oper_id=queryParams.get("oper_id").toString();
			sqlWhere+=" and (t.other_deal_user is null or t.other_deal_user=?)";
			for(int i=0;i<4;i++){
				params.add(oper_id);
			}		
		}
		if(queryParams.get("orderid")!=null&&!queryParams.get("orderid").toString().equals("")){
			String orderid=queryParams.get("orderid").toString();
			sqlWhere+=" and t.orderid like '%"+orderid+"%'";
//			params.add(orderid);
		}
		if(queryParams.get("exceptionorderflag")!=null&&!queryParams.get("exceptionorderflag").toString().equals("")){
			String exceptionorderflag=queryParams.get("exceptionorderflag").toString();
			if(exceptionorderflag.equals("01")){
			sqlWhere+=" and t.exceptionorderflag=?";
			params.add(exceptionorderflag);
			}
			if(exceptionorderflag.equals("02")){
				sqlWhere+=" and t.exceptionorderflag is null";
			}
			if(exceptionorderflag.equals("00")&&queryParams.get("flownode")!=null&&(queryParams.get("flownode").equals("D")||queryParams.get("flownode").equals("F"))){
				sqlWhere+=" and t.exceptionorderflag is null";
			}
		}
		if(queryParams.get("autoorderexceptionflag")!=null&&!queryParams.get("autoorderexceptionflag").toString().equals("")){
			String autoorderexceptionflag=queryParams.get("autoorderexceptionflag").toString();
			sqlWhere+=" and t.autoorderexceptionflag=?";
			params.add(autoorderexceptionflag);
		}
		if(queryParams.get("ordercreatestarttime")!=null&&queryParams.get("ordercreateendtime")!=null
		&&!queryParams.get("ordercreatestarttime").toString().equals("")&&!queryParams.get("ordercreateendtime").toString().equals("")){
			String ordercreatestrattime=queryParams.get("ordercreatestarttime").toString();
			String ordercreateendtime=queryParams.get("ordercreateendtime").toString();
			sqlWhere+=" and t.ordercreatetime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			sqlWhere+=" and t.ordercreatetime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			params.add(ordercreatestrattime);
			params.add(ordercreateendtime);
		}
//		if(queryParams.get("ordercreatetimestr")!=null){
//			String ordercreatetimestr=queryParams.get("ordercreatetimestr").toString();
//			sqlWhere+=" and ordercreatetimestr=?";
//			params.add(ordercreatetimestr);
//		}
		if(queryParams.get("orderorigin")!=null&&!queryParams.get("orderorigin").toString().equals("")){//订单来源多选
			String orderorigins=queryParams.get("orderorigin").toString();
			String[] orderorigin = orderorigins.split(",");
			for(int i=0;i<orderorigin.length;i++){
				if(i==0){
					sqlWhere+=" and (t.orderorigin=?";
				}
				else{
					sqlWhere+=" or t.orderorigin=?";
				}
				params.add(orderorigin[i]);
			}
			sqlWhere+=")";
		}
		if(queryParams.get("orderorginid")!=null&&!queryParams.get("orderorginid").toString().equals("")){
			String orderorginid=queryParams.get("orderorginid").toString();
			sqlWhere+=" and t.orderorginid like '%"+orderorginid+"%'";
//			params.add(orderorginid);
		}
		// 终端串号 
		if(queryParams.get("series_num")!=null&&!queryParams.get("series_num").toString().equals("")){
			String series_num=queryParams.get("series_num").toString();
			sqlWhere+=" and t.series_num like '%"+series_num+"%'";
		}
		// 商品号码
		if(queryParams.get("mobile_tel")!=null&&!queryParams.get("mobile_tel").toString().equals("")){
			String mobile_tel=queryParams.get("series_num").toString();
			sqlWhere+=" and t.mobile_tel like '%"+mobile_tel+"%'";
		}
		//买家昵称
		if(queryParams.get("pet_name")!=null&&!queryParams.get("pet_name").toString().equals("")){
			String pet_name=queryParams.get("pet_name").toString();
			sqlWhere+=" and t.pet_name like '%"+pet_name+"%'";
		}
		//开户人名
		if(queryParams.get("buyer_name")!=null&&!queryParams.get("buyer_name").toString().equals("")){
			String buyer_name=queryParams.get("buyer_name").toString();
			sqlWhere+=" and t.buyer_name like '%"+buyer_name+"%'";
		}
		//推广渠道
		if(queryParams.get("regist_type")!=null&&!queryParams.get("regist_type").toString().equals("")){//多选配送方式
			String regist_types=queryParams.get("regist_type").toString();
			String[] regist_type = regist_types.split(",");
			for(int i=0;i<regist_type.length;i++){
				if(i==0){
					sqlWhere+=" and (t.regist_type=?";
				}
				else{
					sqlWhere+=" or t.regist_type =?";
				}
				params.add(regist_type[i]);
			}
			sqlWhere+=")";
		}

		if(queryParams.get("paystarttime")!=null&&queryParams.get("payendtime")!=null
				&&!queryParams.get("paystarttime").toString().equals("")&&!queryParams.get("payendtime").toString().equals("")){
			String paystrattime=queryParams.get("paystarttime").toString();
			String payendtime=queryParams.get("payendtime").toString();
			sqlWhere+=" and t.paytime>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			sqlWhere+=" and t.paytime<=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			params.add(paystrattime);
			params.add(payendtime);
		}
		if(queryParams.get("carry_mode")!=null&&!queryParams.get("carry_mode").toString().equals("")){//多选配送方式
			String carry_modes=queryParams.get("carry_mode").toString();
			String[] carry_mode = carry_modes.split(",");
			for(int i=0;i<carry_mode.length;i++){
				if(i==0){
					sqlWhere+=" and (t.carry_mode=?";
				}
				else{
					sqlWhere+=" or t.carry_mode =?";
				}
				params.add(carry_mode[i]);
			}
			sqlWhere+=")";
		}
		if(queryParams.get("order_region")!=null&&!queryParams.get("order_region").toString().equals("")){//订单来源地市多选
			String order_regions=queryParams.get("order_region").toString();
			String[] order_region = order_regions.split(",");
			for(int i=0;i<order_region.length;i++){
				if(i==0){
					sqlWhere+=" and (t.order_region=?";
				}
				else{
					sqlWhere+=" or t.order_region=?";
				}
				params.add(order_region[i]);
			}
			sqlWhere+=")";
		}
		if(queryParams.get("order_son_type")!=null&&!queryParams.get("order_son_type").toString().equals("")){//订单子来源多选
			String order_son_types=queryParams.get("order_son_type").toString();
			String[] order_son_type = order_son_types.split(",");
			for(int i=0;i<order_son_type.length;i++){
				if(i==0){
					sqlWhere+=" and (t.order_son_type=?";
				}
				else{
					sqlWhere+=" or t.order_son_type=?";
				}
				params.add(order_son_type[i]);
			}
			sqlWhere+=")";
		}
		if(queryParams.get("pack_type")!=null&&!queryParams.get("pack_type").toString().equals("")){
			String pack_type=queryParams.get("pack_type").toString();
			sqlWhere+=" and t.pack_type=?";
			params.add(pack_type);
		}
		if(queryParams.get("order_deal_type")!=null&&!queryParams.get("order_deal_type").toString().equals("")){
			String order_deal_type=queryParams.get("order_deal_type").toString();
			sqlWhere+=" and t.order_deal_type=?";
			params.add(order_deal_type);
		}
		if(queryParams.get("develop_attribution")!=null&&!queryParams.get("develop_attribution").toString().equals("")){//订单发展归属多选
			String develop_attributions=queryParams.get("develop_attribution").toString();	
			String[] develop_attribution = develop_attributions.split(",");
			for(int i=0;i<develop_attribution.length;i++){
				if(i==0){
					sqlWhere+=" and (t.develop_attribution=?";
				}
				else{
					sqlWhere+=" or t.develop_attribution=?";
				}
				params.add(develop_attribution[i]);
			}
			sqlWhere+=")";
		}
		if(queryParams.get("pay_type")!=null&&!queryParams.get("pay_type").toString().equals("")){//支付方式多选
			String pay_types=queryParams.get("pay_type").toString();
			String[] pay_type = pay_types.split(",");
			for(int i=0;i<pay_type.length;i++){
				if(i==0){
					sqlWhere+=" and (t.pay_type=?";
				}
				else{
					sqlWhere+=" or t.pay_type=?";
				}
				params.add(pay_type[i]);
			}
			sqlWhere+=")";
		}
		if(queryParams.get("handset_type")!=null&&!queryParams.get("handset_type").toString().equals("")){
			String handset_type=queryParams.get("handset_type").toString();
			sqlWhere+=" and t.handset_type=?";
			params.add(handset_type);
		}
		
		if(queryParams.get("flownode")!=null&&!queryParams.get("flownode").toString().equals("")){
			String flownode=queryParams.get("flownode").toString();
			if(flownode.equals("Y"))
			{
				sqlWhere+=" and (t.flownode='Y' or t.is_need_bss=1)";
			}
			else if(flownode.equals("F")){
				sqlWhere+= " and ((t.flownode = 'F' and t.production_pattern != '01') or (t.flownode = 'F' and t.production_pattern = '01' and " +
						"t.AUTOORDEREXCEPTIONFLAG = '0' ))";
			}
			else{
			sqlWhere+=" and t.flownode=?";
			params.add(flownode);
			}
		}
		if(queryParams.get("flow_node_status")!=null&&!queryParams.get("flow_node_status").toString().equals("")){
			String flow_node_status=queryParams.get("flow_node_status").toString();
			if(flow_node_status.equals("02")){
				sqlWhere+=" and t.lockuser is not null ";
			}else if(flow_node_status.equals("01")){
        	   sqlWhere+=" and t.lockuser is null";
			}else{
        	   sqlWhere+=" and t.flow_node_status = '04' ";
			}
		}
		if(queryParams.get("light_ning_status")!=null&&!queryParams.get("light_ning_status").toString().equals("")){
			String light_ning_status=queryParams.get("light_ning_status").toString();
			sqlWhere+=" and t.light_ning_status=?";
			params.add(light_ning_status);
		}
//		if(queryParams.get("exception_type")!=null&&!queryParams.get("exception_type").toString().equals("")){
//			String exception_type=queryParams.get("exception_type").toString();
//			sqlWhere+=" and t.exception_type=?";
//			params.add(exception_type);
//		}
		if(queryParams.get("exception_type")!=null&&!queryParams.get("exception_type").toString().equals("")){//异常类型多选
			String exception_types=queryParams.get("exception_type").toString();
			String[] exception_type = exception_types.split(",");
 			for(int i=0;i<exception_type.length;i++){
				if(i==0){
					sqlWhere+=" and (t.exception_type=?";
				}
				else{
					sqlWhere+=" or t.exception_type=?";
				}
				params.add(exception_type[i]);
			}
 			
			sqlWhere+=")";
			sqlWhere+=" and t.deal_flag='0'";
		}
		if(queryParams.get("agent_id")!=null&&!queryParams.get("agent_id").toString().equals("")){
			String agent_id=queryParams.get("agent_id").toString();
			sqlWhere+=" and t.agent_id like '%"+agent_id+"%'";
//			params.add(agent_id);
		}
		if(queryParams.get("post_no")!=null&&!queryParams.get("post_no").toString().equals("")){
			String post_no=queryParams.get("post_no").toString();
			sqlWhere+=" and t.post_no like '%"+post_no+"%'";
		}
		if(queryParams.get("sds_comp")!=null&&!queryParams.get("sds_comp").toString().equals("")){
			String sds_comp=queryParams.get("sds_comp").toString();
			sqlWhere+=" and t.sds_comp=?";
			params.add(sds_comp);
		}		
//		外部系统订单处理
//		if(queryParams.get("isEssAccount")!=null&&!queryParams.get("isEssAccount").toString().equals("1")){
//			String sds_comp=queryParams.get("sds_comp").toString();
//			sqlWhere+=" and t.sds_comp=?";
//			params.add(sds_comp);
//		}
//		else{
//			
//		}
		if(queryParams.get("production_pattern")!=null&&!queryParams.get("production_pattern").toString().equals("")){
			String production_pattern=queryParams.get("production_pattern").toString();
			sqlWhere+=" and t.production_pattern=?";
			params.add(production_pattern);
		}
		if(queryParams.get("iswuliu")!=null&&queryParams.get("iswuliu").toString().equals("yes")&&
				queryParams.get("oper_id")!=null&&!queryParams.get("oper_id").toString().equals("")){
			oper_id=queryParams.get("oper_id").toString();
			sqlWhere+=" and t.other_deal_user =?";
			params.add(oper_id);
		}
		if(queryParams.get("orderby")!=null&&!queryParams.get("orderby").toString().equals("")
				&&queryParams.get("desc")!=null&&!queryParams.get("desc").toString().equals("")
				){
			String orderby=queryParams.get("orderby").toString();
			String desc = queryParams.get("desc").toString();
			if(orderby.equals("payTime")){
				if(desc.equals("0")){
					sqlWhere+=" order by t.paytime";
				}
				if(desc.equals("1")){
					sqlWhere+=" order by t.paytime desc";
				}
			}
			if(orderby.equals("orderCreateTime")){
				if(desc.equals("0")){
					sqlWhere+=" order by t.ordercreatetime";
				}
				if(desc.equals("1")){
					sqlWhere+=" order by t.ordercreatetime desc";
				}
			}
			
		}
		
		String defCode=null;
		if(queryParams.get("sql_def_code")!=null&&!queryParams.get("sql_def_code").toString().equals("")){
			defCode=queryParams.get("sql_def_code").toString();
		}
		else{
			defCode="ORDER_ALL";
		}
		
		int pageIndex = Integer.parseInt(queryParams.get("pageIndex").toString());
		int pageSize  = Integer.parseInt(queryParams.get("pageSize").toString());
		String sqlQueryD=sqlOrderidQuery;
		sqlOrderidQuery="select"+sqlOrderidQuery+sqlWhere+")";//相同订单ID只取一张
		
		int total_num=0;
		String sqlTotalNum="select count(*) from ("+sqlOrderidQuery+")";
		
		if(queryParams.get("flownode")!=null/*&&queryParams.get("flownode").toString().equals("D")*/&&(queryParams.get("oper_id")!=null&&!queryParams.get("oper_id").toString().equals(""))
				&&(queryParams.get("desc")==null||queryParams.get("desc").toString().equals(""))){
			String operid=queryParams.get("oper_id").toString();
			sqlOrderidQuery=" select case when lockuser = '"+operid+"' then 1 when lockuser is null then 2 else 3 end orderA, case when dealsort = '1' and lockuser = '"+operid+"' then 4 " +
					"when dealsort = '0' then 5 else 6 end orderB,ordercreatetime,"+sqlQueryD+sqlWhere+") order by orderA asc,orderB asc,ordercreatetime";
		}//D环节优先显示锁定单
		
//		UnimallOrderLocalManager unimallOrderLocalManager = SpringContextHolder.getBean("unimallOrderLocalManager");
		
//		total_num=UnimallOrderLocalManagerT.getBaseDaoSupport().queryForInt(sqlTotalNum,params.toArray());		
		Page page = UnimallOrderLocalManagerT.getBaseDaoSupport().queryForCPage(sqlOrderidQuery, pageIndex, pageSize,UnimallOrderInfo.class,sqlTotalNum,params.toArray());
				//(sqlOrderidQuery, pageIndex, pageSize, params.toArray());
		List<UnimallOrderInfo> orderIds = page.getResult();
		total_num=(int)page.getTotalCount();
		for(int i=0;i<orderIds.size();i++){
			UnimallOrderInfo unimallOrderInfo = new UnimallOrderInfo();
			String orderId=orderIds.get(i).getOrderid();		
			String key=defCode+orderId;	
//			if(this.cache.get(NAMESPACE, key)==null){
//				//缓存里面获取不到则从数据库查询
				String sql=SF.orderSql("ORDER_INFO");
				unimallOrderInfo=(UnimallOrderInfo) UnimallOrderLocalManagerT.getBaseDaoSupport().queryForObject(sql,UnimallOrderInfo.class,orderId);
				//把查询到的数据重新放入缓存
//				cache.set(NAMESPACE, key, unimallOrderInfo,time);
//			}
//			else{
//				unimallOrderInfo=(UnimallOrderInfo)this.cache.get(NAMESPACE, key);
//				//unimallOrderInfo=根据订单ID去缓存里面取数据
//			}
			orderResp.add(unimallOrderInfo);
		}
		resp.setOrderInfo(orderResp);
		resp.setOrderTotalNum(Integer.toString(total_num));
		resp.setPage_index(Integer.toString(pageIndex));
		resp.setPage_size(Integer.toString(pageSize));
		return resp;
	}

	private void updateUnimallOrder(Map queryParams) {
		

//		UnimallOrderLocalManager unimallOrderLocalManager = SpringContextHolder.getBean("unimallOrderLocalManager");
		// TODO Auto-generated method stub
		if(queryParams.get("orderid")!=null&&!queryParams.get("orderid").toString().equals("")){
			String orderid=queryParams.get("orderid").toString();
			String sql=SF.orderSql("UPDATE_UNIMALL_ORDER");
			String delSql="delete from es_unimall_order where orderid=?";
			String insertSql=SF.orderSql("UNIMALL_ORDER_INSERT");
			UnimallOrderInfo unimallOrderInfo = (UnimallOrderInfo)this.baseDaoSupportGProd.queryForObject(sql, UnimallOrderInfo.class, orderid);
			unimallOrderInfo.setSql_def_code("ORDER_ALL");
			unimallOrderInfo.setSource_from(ManagerUtils.getSourceFrom());
			if(!StringUtils.isEmpty(unimallOrderInfo.getPaytime())){
				unimallOrderInfo.setPaytime(unimallOrderInfo.getPaytime().substring(0, 19));
			}
			if(!StringUtils.isEmpty(unimallOrderInfo.getOrdercreatetime())){
				unimallOrderInfo.setOrdercreatetime(unimallOrderInfo.getOrdercreatetime().substring(0, 19));
			}
			if(!StringUtils.isEmpty(unimallOrderInfo.getLocktimestr())){
				unimallOrderInfo.setLocktimestr(unimallOrderInfo.getLocktimestr().substring(0, 19));
			}
			
//			String key=unimallOrderInfo.getSql_def_code()+unimallOrderInfo.getOrderid();
			UnimallOrderLocalManagerT.getBaseDaoSupport().execute(delSql, orderid);
			UnimallOrderLocalManagerT.getBaseDaoSupport().insert("es_unimall_order", unimallOrderInfo);
//			cache.set(NAMESPACE, key, unimallOrderInfo,time);
		}
		else{
			return;
		}
	}

	//定时任务更新缓存
	@Override
	public void orderCacheSet() {
		// TODO Auto-generated method stub		
		String sqlDefCode=SF.orderSql("DEF_CODE");
		String sqlLimit = SF.orderSql("LIMIT");		
		List<Map> orderDefs=this.baseDaoSupportGProd.queryForList(sqlDefCode);
		for (int i=0;i<orderDefs.size();i++){			
			String def_sql=orderDefs.get(i).get("sql_content").toString();//获取查询sql
			String def_code=orderDefs.get(i).get("sql_def_code").toString();
			String limit = this.baseDaoSupportGProd.queryForString(sqlLimit,def_code);
						
//			String sqlDel="truncate table es_unimall_order";//清空订单表
//			this.baseDaoSupportGProd.execute(sqlDel);
			
			String sqlDualNum = "select sysdate-add_months(sysdate,-'"+limit+"') from dual where 1=1";
			int dualNum=this.baseDaoSupportGProd.queryForInt(sqlDualNum);	
			String allCountSql="select count(*) from es_unimall_order";
			int allCount=UnimallOrderLocalManagerT.getBaseDaoSupport().queryForInt(allCountSql);
			if(allCount==0){
				for (int j = 0; j < dualNum+1; j++){
					cache.delete("update_time+"+j);
				}
			}
			/******************多线程处理******************************/
			
//			Disruptor<AsynServiceEvent> disruptor = DisruptorInstFactory.initDisruptor(executor);
			for (int j = 0; j < dualNum+1; j++) {
//				for (int j =0; j < 1; j++) {
				UnimallOrderDataReq req = new UnimallOrderDataReq();
				req.setThread_acount(new Integer(dualNum));
				req.setCurr_thread(new Integer(j));
				req.setDef_sql(def_sql);
				req.setDef_code(def_code);				
				TaskThreadPool taskThreadPool = new TaskThreadPool(new Task(req){
					public ZteResponse execute(ZteRequest zteRequest) {
					
						UnimallOrderDataReq req = (UnimallOrderDataReq)zteRequest;	
						logger.info("执行线程开始："+req.getCurr_thread()+"==========================================");
						int k=-req.getThread_acount()+req.getCurr_thread();//获取2月前的单子
						
						
						String sqlDayDateWhere=getSqlK(k);
						String sqlDayLocalDateWhere=getSqlLocalK(k);
						String dayDateSql=req.getDef_sql();
						/**
						 * 
						 * 按线程获取上一次更新时间
						 *如果为空，则update_time不做为查询条件，否则获取缓存的时间作为查询语句过滤条件
						 */
						String upadte_time = (String)cache.get("update_time+"+req.getCurr_thread());						
						if(!StringUtils.isEmpty(upadte_time)){
							upadte_time=updateTimeNew(upadte_time);
							dayDateSql+=" and o.update_time>=to_date('"+upadte_time+"','yyyy-mm-dd:hh24:mi:ss')";
						}
						dayDateSql+=sqlDayDateWhere;

						UnimallOrderQueryManager unimallOrderQueryManager = SpringContextHolder.getBean("unimallOrderQueryManager");
						List<UnimallOrderInfo> orderDayInfos=unimallOrderQueryManager.baseDaoSupportGProd.queryForList(dayDateSql,UnimallOrderInfo.class);
						String sqlInsert=SF.orderSql("UNIMALL_ORDER_INSERT");
						//批量入库
						List params =new ArrayList();
						String orderids="";
						try{
//							if(com.ztesoft.ibss.common.util.ListUtil.isEmpty(orderDayInfos))
//								return null;
							StringBuffer tmpOrderIdsBuffer =new StringBuffer();
							for(UnimallOrderInfo orderDayInfo:orderDayInfos){
								
								//数据库查询
//								String countStr = UnimallOrderLocalManagerT.getBaseDaoSupport().queryForString("select count(*) from es_unimall_order where orderid = ?",orderDayInfo.getOrderid());
//								if(!StringUtils.isEmpty(countStr) && new Integer(countStr).intValue()>0)
//									continue;
//								//当前批次去重
								if(tmpOrderIdsBuffer.toString().indexOf(orderDayInfo.getOrderid())>-1){
									continue;
								}
								tmpOrderIdsBuffer.append(orderDayInfo.getOrderid()).append(",");
								
//								String updatetime=UnimallOrderLocalManagerT.getBaseDaoSupport().queryForString("select updatetime from es_unimall_order where orderid = ?",orderDayInfo.getOrderid());
//								if(!StringUtils.isEmpty(updatetime)&&!StringUtils.isEmpty(orderDayInfo.getUpdatetime())){
//									if(DateFormatUtils.compareDate(updatetime,orderDayInfo.getUpdatetime(), "yyyy/MM/dd HH:mm:ss")==1){
//										continue;
//									}
//								}
								
								String regex="[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
								orderDayInfo.setSql_def_code(req.getDef_code());//inset seq.nextval
								if(orderDayInfo.getLocktimestr()!=null){
									orderDayInfo.setLocktimestr(orderDayInfo.getLocktimestr().substring(0, 19));
									if(!orderDayInfo.getLocktimestr().matches(regex)){
										orderDayInfo.setOrdercreatetime("1999-01-01 11:11:11");
									}
								}
								else{
									orderDayInfo.setLocktimestr("1999-01-01 11:11:11");
								}
								if(orderDayInfo.getOrdercreatetime()!=null){
									orderDayInfo.setOrdercreatetime(orderDayInfo.getOrdercreatetime().substring(0, 19));
									if(!orderDayInfo.getOrdercreatetime().matches(regex)){
										orderDayInfo.setOrdercreatetime("1999-01-01 11:11:11");
									}
								}
								else{
									orderDayInfo.setOrdercreatetime("1999-01-01 11:11:11");
								}
								if(orderDayInfo.getPaytime()!=null){
									orderDayInfo.setPaytime(orderDayInfo.getPaytime().substring(0, 19));
									if(!orderDayInfo.getPaytime().matches(regex)){
										orderDayInfo.setPaytime("1999-01-01 11:11:11");
									}
								}
								else{
									orderDayInfo.setPaytime("1999-01-01 11:11:11");
								}
								
								if(StringUtils.isEqual(orderDayInfo.getBuyer_name(),"tester1'/<>()**")){
									orderDayInfo.setBuyer_name("tester1");
								}//老订单系统的错误数据过滤
								
								
								String [] p=getP(orderDayInfo);
								if(p.length ==0)
									continue;
								params.add(p);
//								rwl.writeLock().lock();
//								String key = req.getDef_code()+orderDayInfo.getOrderid();
//								cache.set(NAMESPACE,key,orderDayInfo,time);	
//								rwl.writeLock().unlock();
								
								orderids+=",'"+orderDayInfo.getOrderid()+"'";
								
								//
							}
							String sqlLocalDayDel="delete from es_unimall_order o where 1=1"+sqlDayLocalDateWhere;		
							//更新部分订单
							if(!StringUtils.isEmpty(upadte_time)&&params.size()>0){
								sqlLocalDayDel="delete from es_unimall_order o where orderid in ("+orderids.substring(1)+")";
							}
//							unimallOrderLocalManager.getBaseDaoSupport().execute(sqlLocalDayDel);
//							unimallOrderLocalManager.getBaseDaoSupport().batchExecute(sqlInsert, params);
							/**
							 * 判断更新数据是否大于0，大于0 则设置已更新时间
							 * 
							 */
							if(params.size()>0){
								cache.set("update_time+"+req.getCurr_thread(), DateFormatUtils.getFormatedDateTime());
							}
							else{
								logger.info("执行线程结束："+req.getCurr_thread()+"=============无改动=====================");
//								cache.delete("update_time+"+req.getCurr_thread());
								return new ZteResponse();
							}
							IUnimallOrderLocalManager unimallOrderLocalManager = SpringContextHolder.getBean("unimallOrderLocalManager");							
							unimallOrderLocalManager.oper(sqlLocalDayDel,sqlInsert,params);	
//							String sqlLocalDay="select * from es_unimall_order o where 1=1"+sqlDayLocalDateWhere;	
//							List<UnimallOrderInfo> orderDayInfos2=UnimallOrderLocalManagerT.getBaseDaoSupport().queryForList(sqlLocalDay,UnimallOrderInfo.class);
//							for(UnimallOrderInfo orderDayInfo:orderDayInfos2){//除去重复订单
//								updateExistOrder(orderDayInfo);
//							}
							logger.info("执行线程结束："+req.getCurr_thread()+"=================有改动==================");
//							cache.delete("update_time+"+req.getCurr_thread());
						}
						catch(Exception e){
							e.printStackTrace();
						}
						return new ZteResponse();
					}
				});	
				ThreadPoolFactory.submit(taskThreadPool, executor);
			}
//			ThreadPoolFactory.closeThreadPool(executor);
			System.gc(); 
		}
	}
	
	/********************单线程处理****************************************/
//	ExecutorService executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_NEW, 500);
//	Disruptor<AsynServiceEvent> disruptor = DisruptorInstFactory.initDisruptor(executor);
//	for (int j = 0; j <dualNum+1; j++) {	
//				AsynServiceConfig confgiInfo=new AsynServiceConfig(new BusinessHandler(){			
//					public void execute(ZteBusiRequest zteRequest) {
//						UnimallOrderDataReq req = (UnimallOrderDataReq)zteRequest;						
//						int k=-req.getThread_acount()+req.getCurr_thread();//获取2月前的单子
//						String sqlDayDateWhere=getSqlK(k);
//						String sqlDayLocalDateWhere=getSqlLocalK(k);
//						String dayDateSql=req.getDef_sql()+sqlDayDateWhere;
//						UnimallOrderQueryManager unimallOrderQueryManager = SpringContextHolder.getBean("unimallOrderQueryManager");
//						List<UnimallOrderInfo> orderDayInfos=unimallOrderQueryManager.baseDaoSupportGProd.queryForList(dayDateSql,UnimallOrderInfo.class);
//						String sqlInsert=SF.orderSql("UNIMALL_ORDER_INSERT");
//						//批量入库
//						List params =new ArrayList();
//						try{
//							if(com.ztesoft.ibss.common.util.ListUtil.isEmpty(orderDayInfos))
//								return;
//							for(UnimallOrderInfo orderDayInfo:orderDayInfos){
//								String regex="[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";
//								orderDayInfo.setSql_def_code(req.getDef_code());//inset seq.nextval
//								if(orderDayInfo.getLocktimestr()!=null){
//									orderDayInfo.setLocktimestr(orderDayInfo.getLocktimestr().substring(0, 19));
//									if(!orderDayInfo.getLocktimestr().matches(regex)){
//										orderDayInfo.setOrdercreatetime("1999-01-01 11:11:11");
//									}
//								}
//								else{
//									orderDayInfo.setLocktimestr("1999-01-01 11:11:11");
//								}
//								if(orderDayInfo.getOrdercreatetime()!=null){
//									orderDayInfo.setOrdercreatetime(orderDayInfo.getOrdercreatetime().substring(0, 19));
//									if(!orderDayInfo.getOrdercreatetime().matches(regex)){
//										orderDayInfo.setOrdercreatetime("1999-01-01 11:11:11");
//									}
//								}
//								else{
//									orderDayInfo.setOrdercreatetime("1999-01-01 11:11:11");
//								}
//								if(orderDayInfo.getPaytime()!=null){
//									orderDayInfo.setPaytime(orderDayInfo.getPaytime().substring(0, 19));
//									if(!orderDayInfo.getPaytime().matches(regex)){
//										orderDayInfo.setPaytime("1999-01-01 11:11:11");
//									}
//								}
//								else{
//									orderDayInfo.setPaytime("1999-01-01 11:11:11");
//								}
//								String [] p=getP(orderDayInfo);
//								params.add(p);
//								String key = req.getDef_code()+orderDayInfo.getOrderid();
//								cache.set(NAMESPACE,key,orderDayInfo,time);	
//							}
//							String sqlLocalDayDel="delete from es_unimall_order o where 1=1"+sqlDayLocalDateWhere;
//							unimallOrderLocalManager.getBaseDaoSupport().execute(sqlLocalDayDel);
//							unimallOrderLocalManager.getBaseDaoSupport().batchExecute(sqlInsert, params);
//						}catch(Exception e){
//							e.printStackTrace();
//						}
//					}
//				});
//				UnimallOrderDataReq req = new UnimallOrderDataReq();
//				req.setThread_acount(new Integer(dualNum));
//				req.setCurr_thread(new Integer(j));
//				req.setDef_sql(def_sql);
//				req.setDef_code(def_code);
//				confgiInfo.setZteRequest(req);
//				DisruptorInstFactory.submit(confgiInfo,disruptor);
//			}
//			DisruptorInstFactory.close(disruptor, executor);
//		}
		
//	}
	

	
	public static String[] getP(UnimallOrderInfo orderDayInfo) {
				   String[] p= new String[]{
				   orderDayInfo.getSql_def_code(),
				   orderDayInfo.getOrderid(),
				   orderDayInfo.getLockuser(),
				   orderDayInfo.getLockname(),
				   orderDayInfo.getDealsort(),
				   orderDayInfo.getExceptionorderflag(),
				   orderDayInfo.getAutoorderexceptionflag(),
				   orderDayInfo.getOrdercreatetimestr(),
				   orderDayInfo.getOrderpri(),
				   orderDayInfo.getRecovertytype(),
				   orderDayInfo.getFirstsaveflag(),
				   orderDayInfo.getSocietyflag(),
				   orderDayInfo.getBespokeflag(),
				   orderDayInfo.getSendzbflag(),
				   orderDayInfo.getOrderorigin(),
				   orderDayInfo.getOrderorginid(),
				   orderDayInfo.getFlownode(),
				   orderDayInfo.getPost_type(),
				   orderDayInfo.getCarry_mode(),
				   orderDayInfo.getProduct_type(),
				   orderDayInfo.getEnd_agent(),
				   orderDayInfo.getLock_status(),
				   orderDayInfo.getExt_order_status(),
				   orderDayInfo.getSp_prod_type(),
				   orderDayInfo.getOrder_region(),
				   orderDayInfo.getOrder_son_type(),
				   orderDayInfo.getPack_type(),
				   orderDayInfo.getOrder_deal_type(),
				   orderDayInfo.getPackage_type(),
				   orderDayInfo.getDevelop_attribution(),
				   orderDayInfo.getBrand_code(),
				   orderDayInfo.getEss_account_flag(),
				   orderDayInfo.getOrder_type(),
				   orderDayInfo.getOther_deal_user(),
				   orderDayInfo.getOuter_order_id(),
				   orderDayInfo.getPay_type(),
				   orderDayInfo.getPet_name(),
				   orderDayInfo.getMobile_tel(),
				   orderDayInfo.getHandset_type(),
				   orderDayInfo.getBuyer_name(),
				   orderDayInfo.getSeries_num(),
				   orderDayInfo.getIs_need_bss(),
				   orderDayInfo.getFlow_node_status(),
				   orderDayInfo.getOther_deal_status(),
				   orderDayInfo.getDeal_flag(),
				   orderDayInfo.getException_type(),
				   orderDayInfo.getProduction_pattern(),
				   orderDayInfo.getOrder_status(),
				   orderDayInfo.getAgent_id(),
				   orderDayInfo.getLight_ning_status(),
				   orderDayInfo.getSds_comp(),
				   orderDayInfo.getDict_code(),
				   orderDayInfo.getProduct_name(),
				   orderDayInfo.getFee(),
				   orderDayInfo.getEssinfo(),
				   orderDayInfo.getBssinfo(),
				   orderDayInfo.getProduction_pattern_first(),
				   orderDayInfo.getLocktimestr(),
				   orderDayInfo.getOrdercreatetime(),
				   orderDayInfo.getPaytime()
		};
		return p;
	}
	
	
	
	@Override
	//查询订单数量
	public int queryTotalNum(Map queryParams) {
//		// TODO Auto-generated method stub
//		
//		total_num=this.unimallOrderLocalManager.getDaoSupport().queryForInt(sql)
//		return total_num;
		return 0;
	}

	public String getSqlK(int k){
		if(k<=-1)
		{
			return " and o.order_create_time>=trunc(sysdate-'"+Math.abs(k)+"') and o.order_create_time<= trunc(sysdate-'"+Math.abs(k+1)+"')";
		}
		else{
			return " and o.order_create_time>=trunc(sysdate+'"+Math.abs(k)+"') and o.order_create_time<= trunc(sysdate+'"+Math.abs(k+1)+"')";
		}
		
	}
	

	public String getSqlLocalK(int k){
		if(k<=-1)
		{
			return " and o.ordercreatetime>=trunc(sysdate-'"+Math.abs(k)+"') and o.ordercreatetime<= trunc(sysdate-'"+Math.abs(k+1)+"')";
		}
		else{
			return " and o.ordercreatetime>=trunc(sysdate+'"+Math.abs(k)+"') and o.ordercreatetime<= trunc(sysdate+'"+Math.abs(k+1)+"')";
		}
		
	}
	
	public void updateExistOrder(UnimallOrderInfo unimallOrderInfo){
		int count=0;
		String sql="select count(*) from es_unimall_order where orderid='"+unimallOrderInfo.getOrderid()+"'";
		count=UnimallOrderLocalManagerT.getBaseDaoSupport().queryForInt(sql);
		if(count>1)			
		{
			
			String sqlDel="delete from es_unimall_order where orderid='"+unimallOrderInfo.getOrderid()+"' and rowid not in(select min(rowid) from es_unimall_order where orderid='"+unimallOrderInfo.getOrderid()+"')";
//			String sqlUpdate="update es_unimall_order set product_type='01,03',buyer_name='"+unimallOrderInfo.getBuyer_name()+"' where orderid='"+unimallOrderInfo.getOrderid()+"' and product_type='01'";
			try{
			IUnimallOrderLocalManager unimallOrderLocalManager = SpringContextHolder.getBean("unimallOrderLocalManager");
			unimallOrderLocalManager.oper(sqlDel);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public String updateTimeNew(String update_time){
		
//		Calendar calendar  = DateFormatUtils.parseStrToCalendar(update_time);
//		calendar.getTime();
//		calendar.add(Calendar.MINUTE,-10);// 上次更新的前5分钟,防止系统有时间差	
//		String updateTimeNew=DateFormatUtils.formatDate(calendar.getTime(),CrmConstants.DATE_TIME_FORMAT);
//		return updateTimeNew;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dtTime;
		try {
			dtTime = sdf.parse(update_time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dtTime);
			calendar.add(Calendar.MINUTE, -10);// 上次更新的前10分钟,防止系统有时间差
			Date dtTime2 = calendar.getTime();
			return sdf.format(dtTime2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("全量更新");
		return "1999-01-01 11:11:11";
	}
}
