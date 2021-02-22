package com.ztesoft.net.mall.core.timer;

import javax.annotation.Resource;
import com.ztesoft.net.mall.core.service.INumeroManager;

public class NumeroImportTimer {
	
	@Resource
	private INumeroManager numeroManager;

	@SuppressWarnings("static-access")
	public void run(){
		
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		
		try {
			
			numeroManager.importNumero();
			
		} catch(Exception e) {
			
			throw new RuntimeException(e.getMessage());
		}
	}
}
