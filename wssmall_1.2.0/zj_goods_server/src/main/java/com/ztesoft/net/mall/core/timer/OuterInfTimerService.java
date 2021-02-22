package com.ztesoft.net.mall.core.timer;

import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.outter.inf.exe.ThreadExecuter;
import com.ztesoft.net.outter.inf.exe.ThreadExecuter.OrderSyncThread;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.OutterTmpl;

/**
 * 订单同步定时任务
 * @作者 MoChunrun
 * @创建日期 2014-3-13 
 * @版本 V 1.0
 */
public class OuterInfTimerService {
	private static Logger logger = Logger.getLogger(OuterInfTimerService.class);
	public static final Object _SYNC_KEY = new Object();
	
	public OuterInfTimerService(){
		//start();
	}
	
	public void exeOrderSync(){ 
		
		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"exeOrderSync"))
	  			return ;
		logger.info("----------开始判断服务器IP是否可运行定时任务-----------");
		if(!CheckTaoBaoTimerServer.isJobRunInIp()){
			return;
		}
		synchronized(_SYNC_KEY){
			IOutterECSTmplManager outterTmplManager = SpringContextHolder.getBean("outterECSTmplManager");
			List<OutterTmpl>  list = outterTmplManager.listOuterTmpl(0);
			for(OutterTmpl tmpl:list){
				try{
					outterTmplManager.updateRunStatus("00R", tmpl.getTmpl_id());
					/*exmin = tmpl.getExecute_min();
					order_from = tmpl.getOrder_from();
					IOrderSyncService service = SpringContextHolder.getBean(tmpl.getBean_name());
					service.perform(tmpl);*/
					OrderSyncThread ot = new OrderSyncThread(tmpl);
					ThreadExecuter.executeOrderTask(ot);
				}catch(Exception ex){
					ex.printStackTrace();
					outterTmplManager.updateRunStatus("00A", tmpl.getTmpl_id());
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
