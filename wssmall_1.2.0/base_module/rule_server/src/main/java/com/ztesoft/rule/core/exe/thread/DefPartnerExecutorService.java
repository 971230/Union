package com.ztesoft.rule.core.exe.thread;


/**
 * 多线程分配处理器
 * @author easonwu 
 * @creation Dec 20, 2013
 * 
 */
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ztesoft.rule.core.exe.IRuleExecutor;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.util.Const;
  
/** 
 *  
 * @author HardPass 
 *  
 */  
public class DefPartnerExecutorService implements IPartnerExecutorService{  
	
	private ConfigData config ;
	private int partnerNum ;
	private IRuleExecutor ruleExecutor ;
	
	
	public DefPartnerExecutorService(){
		
	}
	
//	public PartnerExecutorService(IRuleExecutor ruleExecutor  , ConfigData config , int partnerNum){
//		this.ruleExecutor = ruleExecutor ;
//		this.config = config ;
//		this.partnerNum = partnerNum ;
//	}

	
	@Override
	public void multiProcess(){
		int threadNum = config.getBizPlan().getThread_num() ;
		//添加如果没有配置参与者时只开启一个线程    2014-2-26 mochunrun
		if(config.getPlanEntityNum()==0){
			threadNum=1;
			config.getBizPlan().setThread_num(threadNum);
		}
		
		 ExecutorService exec = Executors.newCachedThreadPool();       
	        final CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {  
	            @Override  
	            public void run() {  
	            	try{
	            		ruleExecutor.getRuleFactDatas().expireCache(config.getBizPlan().getPlan_code()) ;
		            	ruleExecutor.getRuleConfigs().updateConfigData() ;
		                //logger.info("好了，大家可以去吃饭了……"  );  
	    			}catch(Exception ex){
	    				ex.printStackTrace();
	    			}finally{
	    				config.setFinshed(true) ;
	    			}
	            	
	            }  
	        });       
	        
	       // logger.info("要吃饭，必须所有人都到终点，oK?");                  
	       // logger.info("不放弃不抛弃！");  
	        if(config.getPlanEntityNum()==0){
	        	//添加如果没有配置参与者时只开启一个线程    2014-2-26 mochunrun
	        	exec.execute(new PartnerThread(Const.NO_MOLD , barrier , ruleExecutor , config  ));  
	        }else{
	        	for (int i = 0; i < threadNum; i++) {  
		        	exec.execute(new PartnerThread(i , barrier , ruleExecutor , config  ));  
		        }
	        }
	        exec.shutdown();  
	}
	

	public ConfigData getConfig() {
		return config;
	}

	@Override
	public void setConfig(ConfigData config) {
		this.config = config;
	}

	public int getPartnerNum() {
		return partnerNum;
	}

	@Override
	public void setPartnerNum(int partnerNum) {
		this.partnerNum = partnerNum;
	}

	public IRuleExecutor getRuleExecutor() {
		return ruleExecutor;
	}

	@Override
	public void setRuleExecutor(IRuleExecutor ruleExecutor) {
		this.ruleExecutor = ruleExecutor;
	}

}  