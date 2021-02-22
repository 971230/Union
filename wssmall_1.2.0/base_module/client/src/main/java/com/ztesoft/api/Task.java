package com.ztesoft.api;

import params.ZteRequest;
import params.ZteResponse;




/**
 * 多线程处理器对象
 * 
 * @author wui
 * @since 1.0, 2013-12-25
 * 
 * 
 *  corePoolSize： 线程池维护线程的最少数量
	maximumPoolSize：线程池维护线程的最大数量
	keepAliveTime： 线程池维护线程所允许的空闲时间
	unit： 线程池维护线程所允许的空闲时间的单位
	workQueue： 线程池所使用的缓冲队列
	handler： 线程池对拒绝任务的处理策略
	
	
	当线程池中的线程数量大于 corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。 
	unit可选的参数为java.util.concurrent.TimeUnit中的几个静态属性：
	NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS。 
	
	
	handler有四个选择：
	ThreadPoolExecutor.AbortPolicy()
	抛出java.util.concurrent.RejectedExecutionException异常
	ThreadPoolExecutor.CallerRunsPolicy()
	重试添加当前的任务，他会自动重复调用execute()方法
	ThreadPoolExecutor.DiscardOldestPolicy()
	抛弃旧的任务
	ThreadPoolExecutor.DiscardPolicy()
	抛弃当前的任务 


 */
public abstract class Task    {
	ZteRequest zteRequest = null;
	public Task(ZteRequest zteRequest){
		this.zteRequest = zteRequest;
	}
	
	public ZteRequest<ZteResponse> getZteRequest() {
		return zteRequest;
	}

	public void setZteRequest(ZteRequest<ZteResponse> zteRequest) {
		this.zteRequest = zteRequest;
	}

	public abstract ZteResponse execute(ZteRequest zteRequest) throws Exception ;
}




