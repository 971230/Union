package com.ztesoft.net.mall.core.timer;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ztesoft.net.model.EsearchExpInst;
import com.ztesoft.net.service.IOrderExpManager;

public class OrderExpArchiveScheduler {
	private static Logger logger = Logger.getLogger(OrderExpArchiveScheduler.class);
	
	@Resource
	private IOrderExpManager orderExpManager;
	
	public void run(){
		//ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"run")){
  			return ;
  		}
		//获取所有的状态为已处理的异常单
		logger.info("[timer]已处理异常单归档[OrderExpArchiveScheduler.run] executing......");
		long start = System.currentTimeMillis();
		synchronized(OrderExpArchiveScheduler.class){
			List<EsearchExpInst> list = this.orderExpManager.getExpInsts();
			for(int i=0; i<list.size(); i++){
				EsearchExpInst expInst = list.get(i);
				try {
					this.orderExpManager.orderExpArchive(expInst);
				} catch (Exception e) {
					logger.info("异常单：" + expInst.getExcp_inst_id() + "归档失败！");
					e.printStackTrace();
				}
			}
		}
		long end = System.currentTimeMillis();
		logger.info("[timer]已处理异常单归档[OrderExpArchiveScheduler.run] executed" + "用时：" + (end-start) + " 毫秒");
	}

	public IOrderExpManager getOrderExpManager() {
		return orderExpManager;
	}

	public void setOrderExpManager(IOrderExpManager orderExpManager) {
		this.orderExpManager = orderExpManager;
	}
}