package com.ztesoft.mq.example.rocketmq.bench;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import com.ztesoft.mq.client.rocketMQ.RocketMQEventTemplateGeneral;
import com.ztesoft.mq.client.rocketMQ.RocketSendEventTemplate;

public class SendExample extends BenchExample {
	private static Logger logger = Logger.getLogger(SendExample.class);
	public void run(String[] args) throws Exception {
		this.initSend();

		final int threadCount = args.length >= 1 ? Integer.parseInt(args[0])
				: 32;
		final int messageSize = args.length >= 2 ? Integer.parseInt(args[1])
				: 256;
		
		System.out.printf("threadCount %d messageSize %d\n", threadCount, messageSize);

		final ExecutorService sendThreadPool = Executors
				.newFixedThreadPool(threadCount);
		final String msg = buildMessage(messageSize);
		final StatsBenchmarkProducer statsBenchmark = new StatsBenchmarkProducer();
		final Timer timer = new Timer("BenchmarkTimerThread", true);
		final LinkedList<Long[]> snapshotList = new LinkedList<Long[]>();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				snapshotList.addLast(statsBenchmark.createSnapshot());
				if (snapshotList.size() > 10) {
					snapshotList.removeFirst();
				}
			}
		}, 1000, 1000);

		timer.scheduleAtFixedRate(new TimerTask() {
			private void printStats() {
				if (snapshotList.size() >= 10) {
					Long[] begin = snapshotList.getFirst();
					Long[] end = snapshotList.getLast();

					final long sendTps = (long) (((end[3] - begin[3]) / (double) (end[0] - begin[0])) * 1000L);
					final double averageRT = ((end[5] - begin[5]) / (double) (end[3] - begin[3]));

					System.out
							.printf("Send TPS: %d Max RT: %d Average RT: %7.3f Send Failed: %d Response Failed: %d\n"//
							, sendTps//
									, statsBenchmark.getSendMessageMaxRT()
											.get()//
									, averageRT//
									, end[2]//
									, end[4]//
							);
				}
			}

			@Override
			public void run() {
				try {
					this.printStats();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 10000, 10000);

		final RocketSendEventTemplate template = RocketMQEventTemplateGeneral.createRocketMQSendTemplate(TEST_PRODUCER_POOL);
		final MessageQueueSelector selector = new SelectMessageQueueByHash();
		
		for (int i = 0; i < threadCount; i++) {
			sendThreadPool.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int i = 0;
					while (true) {
						try {
							final long beginTimestamp = System.currentTimeMillis();
							template.send(topic, "benchTest", i++, msg, null, selector, i);
							statsBenchmark.getSendRequestSuccessCount().incrementAndGet();
                            statsBenchmark.getReceiveResponseSuccessCount().incrementAndGet();
                            final long currentRT = System.currentTimeMillis() - beginTimestamp;
                            statsBenchmark.getSendMessageSuccessTimeTotal().addAndGet(currentRT);
                            long prevMaxRT = statsBenchmark.getSendMessageMaxRT().get();
                            while (currentRT > prevMaxRT) {
                                boolean updated =
                                        statsBenchmark.getSendMessageMaxRT().compareAndSet(prevMaxRT,
                                            currentRT);
                                if (updated)
                                    break;

                                prevMaxRT = statsBenchmark.getSendMessageMaxRT().get();
                            }	
						} catch (Exception e) {
							// TODO Auto-generated catch block
							statsBenchmark.getSendRequestFailedCount().incrementAndGet();
                            e.printStackTrace();
						} 
					}
				}

			});
		}
	}
	
	private String buildMessage(final int messageSize) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageSize; i += 8) {
            sb.append("hello baby" + i);
        }
        return sb.toString();
    }
	
	public static void main(String[] args) {
		SendExample example = new SendExample();
		
		logger.info("begin.......");
		try {
			example.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("end.......");
	}
}

class StatsBenchmarkProducer {
	// 1
	private final AtomicLong sendRequestSuccessCount = new AtomicLong(0L);
	// 2
	private final AtomicLong sendRequestFailedCount = new AtomicLong(0L);
	// 3
	private final AtomicLong receiveResponseSuccessCount = new AtomicLong(0L);
	// 4
	private final AtomicLong receiveResponseFailedCount = new AtomicLong(0L);
	// 5
	private final AtomicLong sendMessageSuccessTimeTotal = new AtomicLong(0L);
	// 6
	private final AtomicLong sendMessageMaxRT = new AtomicLong(0L);

	public Long[] createSnapshot() {
		Long[] snap = new Long[] {//
		System.currentTimeMillis(),//
				this.sendRequestSuccessCount.get(),//
				this.sendRequestFailedCount.get(),//
				this.receiveResponseSuccessCount.get(),//
				this.receiveResponseFailedCount.get(),//
				this.sendMessageSuccessTimeTotal.get(), //
		};

		return snap;
	}

	public AtomicLong getSendRequestSuccessCount() {
		return sendRequestSuccessCount;
	}

	public AtomicLong getSendRequestFailedCount() {
		return sendRequestFailedCount;
	}

	public AtomicLong getReceiveResponseSuccessCount() {
		return receiveResponseSuccessCount;
	}

	public AtomicLong getReceiveResponseFailedCount() {
		return receiveResponseFailedCount;
	}

	public AtomicLong getSendMessageSuccessTimeTotal() {
		return sendMessageSuccessTimeTotal;
	}

	public AtomicLong getSendMessageMaxRT() {
		return sendMessageMaxRT;
	}
}
