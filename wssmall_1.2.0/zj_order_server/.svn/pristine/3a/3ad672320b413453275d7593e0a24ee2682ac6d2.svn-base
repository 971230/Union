package com.ztesoft.net.mall.core.timer;

import java.util.concurrent.Executors;

import com.ztesoft.net.service.impl.KafKaManager;

public class KafkaKingCardStateTimer {

	public void run(){
		if (!CheckTimerServer.isMatchServer(this.getClass().getName(), "run")) {
  			return;
		}
		//System.out.println("KdyydGetTimer-----begin----------");
		try {
			//a.shutdown();
			Executors.newFixedThreadPool(3).submit(new Runnable() {
				@Override
				public void run() {
					KafKaManager a = new KafKaManager();
					a.status();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
