package com.ztesoft.net.mall.core.timer;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.utils.IJSONUtil;
import com.ztesoft.net.mall.outter.inf.exe.ThreadExecuter;
import com.ztesoft.net.mall.outter.inf.exe.ThreadExecuter.OrderSyncThread;
import com.ztesoft.net.mall.outter.inf.iservice.IOutterECSTmplCtnManager;
import com.ztesoft.net.mall.outter.inf.model.OutterTmpl;

public class OuterInfCtnTimerService {
	
	private static Logger logger = Logger.getLogger(OuterInfCtnTimerService.class);
	
	public static final Object _SYNC_KEY = new Object();
	
	public OuterInfCtnTimerService(){
		//start();
	}
	
	public void exeOrderSync(){ 
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"exeOrderSync"))
	  			return ;
		
		logger.info("[OuterInfCtnTimerService] 淘宝抓单定时任务开始执行" );
		synchronized(_SYNC_KEY){
			IOutterECSTmplCtnManager outterECSTmplCtnManager = SpringContextHolder.getBean("outterECSTmplCtnManager");
			Map map = BeanUtils.getCurrHostEnv();
			//根据主机环境从数据库获取抓取订单参数 env_group为空是测试环境 env_status为00A是生产环境 env_status为00X是灰度环境
			String env_group = (String)map.get("env_group");
			String env_status = (String)map.get("env_status");
			logger.info("淘宝抓单环境参数"+env_group+"  "+env_status);
			int status = 0;
			if(StringUtil.isEmpty(env_group)){
				status = 2;
			}else if(env_status.toUpperCase().equals("00A")){
				status = 0;
			}else{
				status = 1;
			}
			List<OutterTmpl>  list = outterECSTmplCtnManager.listOuterTmpl(status);
			logger.info("[OuterInfCtnTimerService] 淘宝抓单定时任务开始执行,获取淘宝请求参数个数"+ list.size());
			for(OutterTmpl tmpl:list){
				try{
					logger.info("[OuterInfCtnTimerService] 淘宝抓单定时任务开始执行,当前执行参数:"+IJSONUtil.beanToJson(tmpl));
					outterECSTmplCtnManager.updateRunStatus("00R", tmpl.getTmpl_id());
					/*exmin = tmpl.getExecute_min();
					order_from = tmpl.getOrder_from();
					IOrderSyncService service = SpringContextHolder.getBean(tmpl.getBean_name());
					service.perform(tmpl);*/
					OrderSyncThread ot = new OrderSyncThread(tmpl);
					ThreadExecuter.executeOrderTask(ot);
				}catch(Exception ex){
					ex.printStackTrace();
					outterECSTmplCtnManager.updateRunStatus("00A", tmpl.getTmpl_id());
					continue ;
				}
			}
		}
	}
	
	@Deprecated
	private void start(){
		logger.info("=========正在开启外系统订单定时任务==================");
		try{
			new Thread(new Runnable() {
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(10*1000);
							exeOrderSync();
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
			}).start();
		logger.info("=========外系统订单定时任务开启成功==================");
		}catch(Exception ex){
			ex.printStackTrace();
			logger.info("=========外系统订单定时任务开启失败==================");
		}
	}
	
}
