package com.ztesoft.mmt.guide.asyn;

import com.lmax.disruptor.RingBuffer;


public class AsynServiceProducer {

	private final RingBuffer<AsynServiceEvent> ringBuffer;

	public AsynServiceProducer(RingBuffer<AsynServiceEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void write(AsynServiceConfig confgiInfo) {
		if (confgiInfo == null) {
			return;
		}
		long sequence = ringBuffer.next(); // Grab the next sequence
		try {
			AsynServiceEvent event = ringBuffer.get(sequence);																	
			event.setConfgiInfo(confgiInfo);			
		} finally {
			ringBuffer.publish(sequence);
		}

	}
}
