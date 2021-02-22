package com.ztesoft.check.common.factory;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.check.common.IdentifyReq;
import com.ztesoft.check.common.IdentifyResp;
import com.ztesoft.check.model.FunDealLog;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;

import com.ztesoft.common.util.StringUtils;

public class LogDataFatory {
	
	IDaoSupport baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");

	private static LogDataFatory instance = new LogDataFatory();
	
	public static LogDataFatory getInstance(){
		return instance;
	}

	public void log(final FunDealLog log){
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				//日志id
				String log_id =  baseDaoSupport.getSequences("S_ES_ECC_LOG_ID");
				log.setLog_id(log_id);
				try{
					baseDaoSupport.insert("ES_ECC_FUN_DEAL_LOG", log);			
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		t.setName("校验日志插入");
		t.start();
	}
	
	public FunDealLog initBLog(IdentifyReq req){
		FunDealLog log = new FunDealLog();
		log.setLog_id(req.getInf_log_id());
		log.setBiz_id(req.getBiz_id());
		log.setFun_id(req.getFun_id());
		log.setClazz(req.getClazz());
		log.setExe_time(req.getExe_time());
		log.setImpl(req.getImpl());
		log.setOrder_id(req.getOrder_id());
		log.setTache_code(req.getTache_code());
		log.setReq_time(new Timestamp(System.currentTimeMillis()));
		return log;
	}

	public FunDealLog initALog(FunDealLog log, IdentifyResp resp){
		log.setResp_time(new Timestamp(System.currentTimeMillis()));
		log.setResult_code(resp.getCode());
		log.setResult_msg(StringUtils.substr(resp.getMsg(), 2000));
		return log;
	}
	
	public void before(final IdentifyReq req) {
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				//日志id
				String log_id =  baseDaoSupport.getSequences("S_ES_ECC_LOG_ID");
				req.setInf_log_id(log_id);
				this.insertInfLog(req);
			}

			private void insertInfLog(IdentifyReq req) {
				try{
					FunDealLog log = new FunDealLog();
					log.setLog_id(req.getInf_log_id());
					log.setBiz_id(req.getBiz_id());
					log.setFun_id(req.getFun_id());
					log.setClazz(req.getClazz());
					log.setExe_time(req.getExe_time());
					log.setImpl(req.getImpl());
					log.setOrder_id(req.getOrder_id());
					log.setTache_code(req.getTache_code());
					log.setReq_time(new Timestamp(System.currentTimeMillis()));
					baseDaoSupport.insert("ES_ECC_FUN_DEAL_LOG", log);			
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
		t.setName("校验日志插入");
		t.start();
		
	}

	public void after(final IdentifyReq req, final IdentifyResp retVal) {
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				String log_id = req.getInf_log_id();
				this.updateInfLog(log_id, retVal);	
			}

			private void updateInfLog(String log_id, IdentifyResp resp) {
				try{
					String sql = " update ES_ECC_FUN_DEAL_LOG set resp_time=:resp_time , result_code=:result_code,result_msg=:result_msg where log_id=:log_id" ;
					Map map = new HashMap();
					map.put("log_id", log_id);
					map.put("resp_time", new Timestamp(System.currentTimeMillis()));
					map.put("result_code", resp.getCode());
					map.put("result_msg", StringUtils.substr(resp.getMsg(), 1000));
					baseDaoSupport.update(sql, map);
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		});
		t.setName("校验日志修改");
		t.start();

	}
}
