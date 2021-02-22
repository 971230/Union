package com.ztesoft.mmt.guide.asyn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lmax.disruptor.EventHandler;

public class AsynEventHandler implements EventHandler<AsynServiceEvent> {

	private String seq = "NULL";
	public AsynEventHandler(String seq){
		this.seq = seq;
	}
	private Log log = LogFactory.getLog(AsynEventHandler.class);

	@Override
	public void onEvent(AsynServiceEvent event, long sequence,
			boolean endOfBatch) throws Exception {
//		log.info("2.handler a LogServiceEvent and sort=" + sequence);
		
		AsynServiceConfig confgiInfo = event.getConfgiInfo();
		if (confgiInfo == null) {
			return;
		}
		if(confgiInfo.businessHandler ==null)
			return;
		try {
			if(this.seq =="NULL")
				confgiInfo.businessHandler.execute(confgiInfo.getZteRequest());
			else{
				if ((sequence % 10) == new Integer(this.seq))
					confgiInfo.businessHandler.execute(confgiInfo.getZteRequest());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
