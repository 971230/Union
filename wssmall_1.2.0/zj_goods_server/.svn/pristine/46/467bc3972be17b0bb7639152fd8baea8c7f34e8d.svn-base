package com.ztesoft.net.outter.inf.exe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.outter.inf.iservice.IOrderSyncService;
import com.ztesoft.net.outter.inf.iservice.IOutterECSTmplManager;
import com.ztesoft.net.outter.inf.model.OutterTmpl;

public class ThreadExecuter {

	//newFixedThreadPool
	//创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
	//newCachedThreadPool
	//创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，
	//那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
	
	//private ExecutorService orderExecutor = Executors.newCachedThreadPool();
	
	/**
	 * 创建固定大小为5的线程池
	 */
	private static final ExecutorService orderExecutor = Executors.newFixedThreadPool(5);
	
	public static void executeOrderTask(OrderSyncThread orderSync){
		orderExecutor.execute(orderSync);
	}
	
	public static class OrderSyncThread implements Runnable {

		private OutterTmpl tmpl;
		protected IOutterECSTmplManager outterECSTmplManager;
		
		public OrderSyncThread(OutterTmpl tmpl) {
			this.tmpl = tmpl;
		}

		@Override
		public void run() {
			try{
				outterECSTmplManager = SpringContextHolder.getBean("outterECSTmplManager");
				IOrderSyncService service = SpringContextHolder.getBean("outerOrderSync");
				service.perform(tmpl);
				if(-1!=tmpl.getExecute_min().intValue())
					outterECSTmplManager.updateRunStatus("00A", tmpl.getTmpl_id());
			}catch(Exception ex){
				if(-1==tmpl.getExecute_min().intValue()){
					outterECSTmplManager.updateRunStatus("00A", "同步失败["+ex.getMessage()+"]",0, tmpl.getTmpl_id());
				}else{
					outterECSTmplManager.updateRunStatus("00A", tmpl.getTmpl_id());
					outterECSTmplManager.updateTmplExecuteInfo(tmpl.getTmpl_id(), 0, 1, tmpl.getExecute_min(), null,"同步失败["+ex.getMessage()+"]");
				}
				ex.printStackTrace();
			}
		}

		public OutterTmpl getTmpl() {
			return tmpl;
		}

		public void setTmpl(OutterTmpl tmpl) {
			this.tmpl = tmpl;
		}
		
	}
	
}
