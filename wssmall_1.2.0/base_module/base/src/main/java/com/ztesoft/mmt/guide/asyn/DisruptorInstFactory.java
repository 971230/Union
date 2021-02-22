package com.ztesoft.mmt.guide.asyn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import params.ZteBusiRequest;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.ztesoft.api.ThreadPoolFactory;



public class DisruptorInstFactory {
	private static DisruptorInstFactory inst = null;
	private ExecutorService executor = null;
	private int bufferSize = 1024;
	private Disruptor<AsynServiceEvent> disruptor = null;
	private static Object keyObj = new Object();
	private AsynServiceEventFactory factory = null;
	
	/**
	 * 
	 */
	private DisruptorInstFactory() {
		factory = new AsynServiceEventFactory();
		executor = Executors.newCachedThreadPool();
	}
	
	/**
	 * 
	 * @return
	 */
	public static DisruptorInstFactory getInst() {
		if (inst == null) {
			synchronized (keyObj) {
				if (inst == null) {
					inst = new DisruptorInstFactory();
					inst.startDisruptor();
				}
			}
		}
		return inst;
	}
	
	
	private void startDisruptor() {
		disruptor = new Disruptor<AsynServiceEvent>(factory, bufferSize,executor);
		disruptor.handleEventsWith(new AsynEventHandler("NULL"));
		disruptor.handleExceptionsWith(new AsynServiceException());
		disruptor.start();
	}
	
	public void putEvent(AsynServiceConfig confgiInfo) {		
		RingBuffer<AsynServiceEvent> ringBuffer = disruptor.getRingBuffer();
		AsynServiceProducer producer = new AsynServiceProducer(ringBuffer);
		producer.write(confgiInfo);
	}
	

	//////////////////////////////////////////////add by wui调研处理逻辑
	public static Disruptor<AsynServiceEvent> initDisruptor(ExecutorService pexecutor,Object... args) {
		int bufferSize=1024;
		if(args !=null && args.length>0)
			bufferSize = new Integer((Integer) args[0]).intValue();
		Disruptor<AsynServiceEvent> disruptor = new Disruptor<AsynServiceEvent>(new AsynServiceEventFactory(), bufferSize,pexecutor,ProducerType.MULTI,new BlockingWaitStrategy());
		//开启10个线程，执行多线程
		disruptor.handleEventsWith(new AsynEventHandler("0"));
		disruptor.handleEventsWith(new AsynEventHandler("1"));
		disruptor.handleEventsWith(new AsynEventHandler("2"));
		disruptor.handleEventsWith(new AsynEventHandler("3"));
		disruptor.handleEventsWith(new AsynEventHandler("4"));
		disruptor.handleEventsWith(new AsynEventHandler("5"));
		disruptor.handleEventsWith(new AsynEventHandler("6"));
		disruptor.handleEventsWith(new AsynEventHandler("7"));
		disruptor.handleEventsWith(new AsynEventHandler("8"));
		disruptor.handleEventsWith(new AsynEventHandler("9"));
		disruptor.handleExceptionsWith(new AsynServiceException());
		disruptor.start();
		return disruptor;
	}
	
	
	public static void close(Disruptor<AsynServiceEvent> disruptor,ExecutorService pexecutor) {		
		disruptor.shutdown();
		pexecutor.shutdown();
	}

	/**
	 * 异步对象原理：
	 * 将有锁多线程转化为无锁异步单线程执行
	 * @param confgiInfo
	 * @param pdisruptor
	 */
	public static void submit(AsynServiceConfig confgiInfo,Disruptor<AsynServiceEvent> pdisruptor){
		RingBuffer<AsynServiceEvent> ringBuffer = pdisruptor.getRingBuffer();
		AsynServiceProducer producer = new AsynServiceProducer(ringBuffer);
		producer.write(confgiInfo);
	}
	
	//add by wui调用处理逻辑
	public static void main(String[] args) {
		
		//构造对象
		ExecutorService executor = ThreadPoolFactory.getExector(ThreadPoolFactory.EXECTOR_CACHE, 100);
		Disruptor<AsynServiceEvent> disruptor = DisruptorInstFactory.initDisruptor(executor);
		
		//数据执行
		for (int i = 0; i <100; i++) {
			AsynServiceConfig confgiInfo=new AsynServiceConfig(new BusinessHandler(){
				@Override
				public void execute(ZteBusiRequest zteRequest) {
					 //业务处理逻辑
				}
			});
			DisruptorInstFactory.submit(confgiInfo,disruptor);
		}
		
		//关闭处理
		DisruptorInstFactory.close(disruptor, executor);
	}
}
