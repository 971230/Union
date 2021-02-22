package com.ztesoft.rule.core.exe.def;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.rule.core.bo.IRuleDBAccess;
import com.ztesoft.rule.core.exe.IApplyPlan;
import com.ztesoft.rule.core.exe.IRuleConfigs;
import com.ztesoft.rule.core.module.cfg.BizPlan;
import com.ztesoft.rule.core.module.cfg.ConfigData;
import com.ztesoft.rule.core.module.cfg.RetryInfo;
import com.ztesoft.rule.core.util.Const;


/**
 * 默认规则配置数据加载
 * @author easonwu 
 * @creation Dec 13, 2013
 * 
 */
public class DefRuleConfigs implements IRuleConfigs {
	private static Logger logger = Logger.getLogger(DefRuleConfigs.class);
	
	private IRuleDBAccess ruleDBAccess ;//DB访问
	

	private ConfigData configData ;//当前执行配置信息
	
	private IApplyPlan applyPlan ;//方案申请

	@Override
	public ConfigData getConfigData() {
		return this.configData;
	}


	@Override
	public void setConfigData(ConfigData configData) {
		this.configData = configData ;
	}


	@Override
	public ConfigData getConfigData(String planCode) {
		return ruleDBAccess.loadConfigData(planCode);
	}


	@Override
	public List<RetryInfo> loadRetryInfo() {
		return ruleDBAccess.loadRetryInfo() ;
	}



	@Override
	public ConfigData apply(long resId) {
		return getApplyPlan().apply(resId);
	}
	
	@Override
	public void updateConfigData() {
		//FIXME 确认后增加处理
		BizPlan plan = configData.getBizPlan() ;
		if(Const.RUN_TIME_TIMMING.equals(plan.getRun_type())){
			//更新为下一个账期/下个账期执行时间
			calNextCycle( plan );
			ruleDBAccess.updateBizPlan(plan ) ;
			Map logData = new HashMap() ;
			logData.put("log_id", configData.getLogId()) ;
			logData.put("status_cd", "00F") ;
			ruleDBAccess.updatePlanLog(logData) ;
		}
	}
	
	
	
	private void calNextCycle(BizPlan plan){
		/*if(plan.getCycle_id().equals("1")){//年
			
		}else if(plan.getCycle_id().equals("2")){//季度
			
		}else if(plan.getCycle_id().equals("3")){//月
			
		}else if(plan.getCycle_id().equals("4")){//周
			
		}else if(plan.getCycle_id().equals("5")){//日
			
		}*/
		//修改为exce_cycle  2014-2-26 mochunrun
		if("MI".equals(plan.getExec_cycle())){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
			plan.setExec_time(df.format(new Date(System.currentTimeMillis()+1000*60*Integer.parseInt(plan.getCycle_id()))));
		}else{
			GregorianCalendar car = new GregorianCalendar();
			if("DD".equals(plan.getExec_cycle())){//日
				car.add(Calendar.DAY_OF_YEAR, 1);
			}else if("WW".equals(plan.getExec_cycle())){//周
				car.add(Calendar.WEEK_OF_MONTH, 1);
			}else if("MM".equals(plan.getExec_cycle())){//月
				car.add(Calendar.MONTH, 1);
			}else if("QQ".equals(plan.getExec_cycle())){//季度
				car.add(Calendar.MONTH, 3);
			}else if("YY".equals(plan.getExec_cycle())){//年
				car.add(Calendar.YEAR, 1);
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			plan.setExec_time(df.format(car.getTime()));
		}
	}

	public static void main(String[] args) {
		GregorianCalendar car = new GregorianCalendar();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		logger.info(df.format(car.getTime()));
		//加一天
		//car.add(Calendar.DAY_OF_YEAR, 1);
		//加一个月
		//car.add(Calendar.MONTH, 1);
		//加一年
		//car.add(Calendar.YEAR, 1);
		//加一周
		//car.add(Calendar.WEEK_OF_MONTH, 1);
		//加一个季度
		car.add(Calendar.MONTH, 3);
		logger.info(df.format(car.getTime()));
	}
	

	public IApplyPlan getApplyPlan() {
		if( applyPlan == null )
			applyPlan = SpringContextHolder.getBean("defApplyPlan");
		
		return applyPlan;
	}


	public void setApplyPlan(IApplyPlan applyPlan) {
		this.applyPlan = applyPlan;
	}
	

	
	public IRuleDBAccess getRuleDBAccess() {
		return ruleDBAccess;
	}


	public void setRuleDBAccess(IRuleDBAccess ruleDBAccess) {
		this.ruleDBAccess = ruleDBAccess;
	}



}
