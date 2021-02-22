package com.ztesoft.net.auto.rule.exe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.apache.log4j.Logger;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.auto.rule.fact.AutoFact;
import com.ztesoft.net.auto.rule.i.IAutoRule;
import com.ztesoft.net.auto.rule.vo.Plan;
import com.ztesoft.net.auto.rule.vo.PlanRule;

public class RuleFutureExecutor {
	private static Logger logger = Logger.getLogger(RuleFutureExecutor.class);
	//public static final ExecutorService exec = Executors.newFixedThreadPool(80);
	
	//多线程执行方案规则
	public static void execPlanRules(List<Plan> planList,AutoFact fact,IAutoRuleCaller caller,IAutoRule autoRuleImpl,RuleThreadStatus threadStatus)throws ApiBusiException{
		if(planList!=null && planList.size()>0){
			ExecutorService exec = Executors.newFixedThreadPool(planList.size());
			try{
				List<FutureTask> list = new ArrayList<FutureTask>();
				for(Plan plan:planList){
					PlanTask rt = new PlanTask(plan, fact, caller, autoRuleImpl);
					FutureTask<Boolean> ft = new FutureTask<Boolean>(rt);
					list.add(ft);
					exec.submit(ft);
				}
				
				for(FutureTask ft:list){
					try{
						long start = System.currentTimeMillis();
						//futureTask的get方法会阻塞，知道可以取得结果为止
						Boolean result = (Boolean) ft.get();
						long end = System.currentTimeMillis();
						logger.info("异步方案执行时间===="+(end-start));
						if(result!=null && result)break ;
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}finally{
				exec.shutdown();
			}
		}
	}
	
	/**
	 * 多线程执行规则
	 * @作者 MoChunrun
	 * @创建日期 2014-11-27 
	 * @param ruleList
	 * @param fact
	 * @param caller
	 * @return
	 * @throws ApiBusiException
	 */
	public static boolean execRules(List<PlanRule> ruleList,AutoFact fact,IAutoRuleCaller caller,IAutoRule autoRuleImpl,RuleThreadStatus threadStatus)throws ApiBusiException{
		logger.info("==============================rulestart=====================================");
		if(ruleList!=null && ruleList.size()>0){
			List<FutureTask> list = new ArrayList<FutureTask>();
			ExecutorService exec = Executors.newFixedThreadPool(ruleList.size());
			try{
				for(PlanRule rule:ruleList){
					RuleTask rt = new RuleTask(rule,fact,caller,autoRuleImpl);
					threadStatus.setAllmutexrule(false);
					rt.setThreadStatus(threadStatus);
					FutureTask<Boolean> ft = new FutureTask<Boolean>(rt);
					list.add(ft);
					exec.submit(ft);
				}
				int i = 0;
				for(FutureTask ft:list){
					try{
						logger.info("=====规则侧start============");
						long start = System.currentTimeMillis();
						//futureTask的get方法会阻塞，知道可以取得结果为止
						Boolean result = (Boolean) ft.get();
						//如果有一个是执行成功的就算是执行成功
						long end = System.currentTimeMillis();
						PlanRule pr = ruleList.get(i);
						logger.info("["+pr.getRule_id()+pr.getRule_name()+pr.getExe_path()+"]异步规则执行时间===="+(end-start));
						i++;
						logger.info("=====规则侧end============");
						if(result!=null && result)return true;
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				logger.info("=============================ruleend======================================");
			}finally{
				exec.shutdown();
			}
			//全部执行失败
			return false;
		}else{
			//没有规则算执行成功
			return true;
		}
	}
	
}
