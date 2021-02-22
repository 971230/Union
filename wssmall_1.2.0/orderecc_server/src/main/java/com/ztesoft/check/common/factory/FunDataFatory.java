package com.ztesoft.check.common.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.ztesoft.check.common.Consts;
import com.ztesoft.check.fun.ProcCheck;
import com.ztesoft.check.fun.SqlCheck;
import com.ztesoft.check.inf.AbstractCheckHander;
import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFunRel;
import com.ztesoft.check.model.Fun;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

public class FunDataFatory{
	private final ConcurrentMap<String, ArrayList<Fun>> bizFunRelMap = new ConcurrentHashMap<String, ArrayList<Fun>>();
	private final ConcurrentMap<String, String> bizExeMethodMap = new ConcurrentHashMap<String, String>();
	private final ConcurrentMap<String, AbstractCheckHander> funHandlerMap = new ConcurrentHashMap<String, AbstractCheckHander>();
	private final ConcurrentMap<String, Fun> funMap = new ConcurrentHashMap<String, Fun>();
	
	public static FunDataFatory getInstance(){
		return BizDataFatoryLoader.instance;
	}
	
	private static class BizDataFatoryLoader {
		public static FunDataFatory instance = new FunDataFatory();
	}
	
	public FunDataFatory(){
		init();
	}

	private void init(){
		bizFunRelMap.clear();
		bizExeMethodMap.clear();
		funHandlerMap.clear();
		funMap.clear();
		IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
		
		//对应功能处理类
		String funHandlerListsql = "select * from es_ecc_fun i where i.status='00A'";
		List<Fun> funList = baseDaoSupport.queryForList(funHandlerListsql, Fun.class);
		for(Fun fun : funList){
			String clazz = fun.getClazz();
			try {
				String impl = fun.getImpl();
				if(Consts.IMPL_01.equals(impl)){
					this.funHandlerMap.put(fun.getFun_id(), (AbstractCheckHander)(Class.forName(clazz)).newInstance());
				}else if(Consts.IMPL_02.equals(impl)){
					this.funHandlerMap.put(fun.getFun_id(), SqlCheck.class.newInstance());
				}else if(Consts.IMPL_03.equals(impl)){
					this.funHandlerMap.put(fun.getFun_id(), ProcCheck.class.newInstance());
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}
			funMap.put(fun.getFun_id(), fun);
		}
		
		//业务环节处理对应功能处理类
		String sql = "select * from es_ecc_biz i where i.status='00A'";
		List<Biz> bizList = baseDaoSupport.queryForList(sql, Biz.class);
		for(Biz biz : bizList){//遍历es_ecc_biz 匹配biz
			String tache_code_sql = "select i.* from es_ecc_biz_fun_rel i where i.biz_id=? and i.status='00A'";
			List<BizFunRel> bizFunRellist = baseDaoSupport.queryForList(tache_code_sql, BizFunRel.class, biz.getBiz_id());
			for(BizFunRel bizFunRel : bizFunRellist){
				String key = bizFunRel.getBiz_id() + "#" + bizFunRel.getTache_code() + "#" + bizFunRel.getExe_time();
				if(bizFunRelMap.containsKey(key)){
					ArrayList list = bizFunRelMap.get(key);
					if(funMap.containsKey(bizFunRel.getFun_id())){
						list.add(funMap.get(bizFunRel.getFun_id()));
					}
				}else{
					ArrayList list = new ArrayList();
					if(funMap.containsKey(bizFunRel.getFun_id())){
						list.add(funMap.get(bizFunRel.getFun_id()));
					}
					bizFunRelMap.put(key, list);
				}
				bizExeMethodMap.put(key, bizFunRel.getExe_method());
			}
			
		}	
		

	
	}
	/**
	 * 查处理类:通过flow_id[业务的流程id]+"#"+tache_code[订单环节]+"#"+exe_time[执行时间:先或后]获取执行校验类列表
	 * @param bizFator
	 * @return
	 */
	public List<Fun> getBizTacheFunList(String key){
		if(null == bizFunRelMap || bizFunRelMap.isEmpty()){
			this.init();
		}
		if(bizFunRelMap.containsKey(key)){
			return bizFunRelMap.get(key);
		}
		return null;
	}
	/**
	 * 查处理类:通过flow_id[业务的流程id]+"#"+tache_code[订单环节]+"#"+exe_time[执行时间:先或后]获取执行校验类列表的执行方式
	 * @param bizFator
	 * @return
	 */
	public String getBizTacheExeMethod(String key){
		if(null == bizExeMethodMap || bizExeMethodMap.isEmpty()){
			this.init();
		}
		if(bizExeMethodMap.containsKey(key)){
			return bizExeMethodMap.get(key);
		}
		return null;
	}	
	public <T> T getHandler(Fun fun){
		if(null == funHandlerMap || funHandlerMap.isEmpty()){
			init();
		}
		if(this.funHandlerMap.containsKey(fun.getFun_id())){
			return (T) this.funHandlerMap.get(fun.getFun_id());
		}
		
		return null;
	}

	public ConcurrentMap<String, String> getBizExeMethodMap() {
		return bizExeMethodMap;
	}
	
	public Fun getFun(String funId) {
		return funMap.get(funId);
	}
	
	public void refreshData(){
		this.init();
	}
}
