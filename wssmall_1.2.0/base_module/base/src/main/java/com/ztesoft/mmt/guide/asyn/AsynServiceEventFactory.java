package com.ztesoft.mmt.guide.asyn;

import com.lmax.disruptor.EventFactory;



public class AsynServiceEventFactory implements EventFactory<AsynServiceEvent>{

	@Override
	public AsynServiceEvent newInstance() {
		 return new AsynServiceEvent();
	}
}
