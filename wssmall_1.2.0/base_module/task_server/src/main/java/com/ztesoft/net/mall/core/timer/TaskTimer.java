package com.ztesoft.net.mall.core.timer;

import com.ztesoft.net.mall.core.service.ITaskJobManager;

public class TaskTimer {

	private ITaskJobManager taskJobManager;
	
	public void runTask(){
		//ip验证处理 add by wui所有定时任务都需要加上限制条件
  		if(!CheckTimerServer.isMatchServer(this.getClass().getName(),"runTask"))
  			return ;
		taskJobManager.dealTask();
	}

	public ITaskJobManager getTaskJobManager() {
		return taskJobManager;
	}

	public void setTaskJobManager(ITaskJobManager taskJobManager) {
		this.taskJobManager = taskJobManager;
	}
	
	
}
