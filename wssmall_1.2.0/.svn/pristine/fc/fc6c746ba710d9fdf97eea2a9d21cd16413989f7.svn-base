package com.ztesoft.lucence;

import java.util.List;

import com.ztesoft.lucence.SearchExecuter.SearchInitTask;


public class LucenceTimer {
	
	private ILucenceManager lucenceManager;
	
	public void exe(){
		List<LucenceConfig> list = lucenceManager.listConfigByStatus("00A");
		for(LucenceConfig config:list){
			SearchExecuter.executeOrderTask(new SearchInitTask(config));
		}
	}

	public ILucenceManager getLucenceManager() {
		return lucenceManager;
	}

	public void setLucenceManager(ILucenceManager lucenceManager) {
		this.lucenceManager = lucenceManager;
	}
	
}
