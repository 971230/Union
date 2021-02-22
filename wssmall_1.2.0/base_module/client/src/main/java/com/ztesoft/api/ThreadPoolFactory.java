package com.ztesoft.api;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import params.ZteResponse;

/**
 * 多线程处理器对象
 * 
 * @author wui
 * @since 1.0, 2013-3-5
 * 
 * 
 *        文章学习： http://www.iteye.com/topic/1118660
 * 
 *        corePoolSize： 线程池维护线程的最少数量 maximumPoolSize：线程池维护线程的最大数量 keepAliveTime：
 *        线程池维护线程所允许的空闲时间 unit： 线程池维护线程所允许的空闲时间的单位 workQueue： 线程池所使用的缓冲队列
 *        handler： 线程池对拒绝任务的处理策略
 * 
 * 
 *        当线程池中的线程数量大于
 *        corePoolSize时，如果某线程空闲时间超过keepAliveTime，线程将被终止。这样，线程池可以动态的调整池中的线程数。
 *        unit可选的参数为java.util.concurrent.TimeUnit中的几个静态属性：
 *        NANOSECONDS、MICROSECONDS、MILLISECONDS、SECONDS。
 * 
 * 
 *        handler有四个选择： ThreadPoolExecutor.AbortPolicy()
 *        抛出java.util.concurrent.RejectedExecutionException异常
 *        ThreadPoolExecutor.CallerRunsPolicy() 重试添加当前的任务，他会自动重复调用execute()方法 //达到列队的最大上限、同时达到线程的最大数量时，交给主线程执行此程序
 *        ThreadPoolExecutor.DiscardOldestPolicy() 抛弃旧的任务
 *        ThreadPoolExecutor.DiscardPolicy() 抛弃当前的任务
 * 
 * 
 *        ///////////////////////////////////////////////=====================
 *        开辟多线程处理器========================================= 举例说明： TaskThreadPool
 *        taskThreadPool = new TaskThreadPool(new Task(dataLog){ public
 *        ZteResponse execute(ZteRequest zteRequest) { try{ DataLog dataLog =
 *        (DataLog)zteRequest; IDataLogManager dataLogManager =
 *        SpringContextHolder.getBean("datasLogManager");
 *        dataLogManager.insertDataLog(dataLog); }catch(Exception e){
 *        e.printStackTrace(); } return new ZteResponse(); } });
 *        ThreadPoolFactory.singleExecute(taskThreadPool); //异步单线程执行
 *        
 *        
 *        直接提交。工作队列的默认选项是 SynchronousQueue，它将任务直接提交给线程而不保持它们。在此，如果不存在可用于立即运行任务的线程，则试图把任务加入队列将失败，因此会构造一个新的线程。此策略可以避免在处理可能具有内部依赖性的请求集时出现锁。直接提交通常要求无界 maximumPoolSizes 以避免拒绝新提交的任务。当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
		   无界队列。使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize 线程都忙时新任务在队列中等待。这样，创建的线程就不会超过 corePoolSize。（因此，maximumPoolSize 的值也就无效了。）当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列；例如，在 Web 页服务器中。这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
		   有界队列。当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。队列大小和最大池大小可能需要相互折衷：使用大型队列和小型池可以最大限度地降低 CPU 使用率、操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。如果任务频繁阻塞（例如，如果它们是 I/O 边界），则系统可能为超过您许可的更多线程安排时间。使用小型队列通常要求较大的池大小，CPU 使用率较高，但是可能遇到不可接受的调度开销，这样也会降低吞吐量。


 */
public class ThreadPoolFactory {
	private static Logger logger = Logger.getLogger(ThreadPoolFactory.class);
	private static int coreSize = 5;
	private static int orderstandingcoreSize = 2;
	private static int orderstandingmaxPoolSize =5;
	private static int maxPoolSize = 25;
	private static int seconds = 10;
	private static int quenesize = 10;
	private static ThreadPoolExecutor singgeExecutor;
	private static ThreadPoolExecutor defExecutor;
	private static ThreadPoolExecutor orderExecutor;
	private static ThreadPoolExecutor orderExceExecutor;
	private static ThreadPoolExecutor roleDataExecutor;
	private static ThreadPoolExecutor infServerExecutor;
	private static ThreadPoolExecutor orderStandingExecutor;
	private static ThreadPoolExecutor writeCardExecutor; 
	private static ThreadPoolExecutor ThreadPool;
	private static ExecutorService newFixedThreadPool;
	private static ExecutorService schedulePool;

	static {
		singgeExecutor = new ThreadPoolExecutor(1, 5, seconds,
				TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000),
				new ThreadPoolExecutor.CallerRunsPolicy());
		defExecutor = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		orderExecutor = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		orderExceExecutor = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());		
		roleDataExecutor = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		infServerExecutor = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		writeCardExecutor = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		orderStandingExecutor = new ThreadPoolExecutor(orderstandingcoreSize,
				orderstandingmaxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		ThreadPool = new ThreadPoolExecutor(coreSize,
				maxPoolSize, seconds, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(quenesize),
				new ThreadPoolExecutor.CallerRunsPolicy());
		newFixedThreadPool = Executors.newFixedThreadPool(16);
		schedulePool = Executors.newScheduledThreadPool(1);

	}
	public static final String EXECTOR_NEW = "newFixedThreadPool";
	public static final String EXECTOR_CACHE = "newCachedThreadPool";
	public static final String EXECTOR_DEF = "defCachedThreadPool";
	public static final String EXECTOR_SINGLE = "singCachedThreadPool";
	
	private static int corePoolsize = 10;


	private static long keepAliveTime = 60;

	
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、如果是类的静态属性方式创建,可以不用关闭,最好关闭;<br>
	 * 2、如果是非静态属性方式创建,在使用完线程池的时候一定要关闭,否则不会释放线程资源;<br>
	 * =======================================================<br>
	 * 
	 * 使用例
	 * 
	 * 1、定义ExecutorService getExector
	 * 2、for循环执行业务
	 * 3、执行关闭处理
	 * @param type
	 * @param args
	 * @return
	 */
	// 定义执行器
	public static ThreadPoolExecutor getExector(String type, Object... args) {
		ThreadPoolExecutor executorService = null;
		int thread_count =corePoolsize;
		if(args !=null && args.length>0)
			thread_count = new Integer((Integer) args[0]).intValue();
		if (EXECTOR_NEW.equals(type)) { // 创建一个可重用固定线程集合的线程池，以共享的无界队列方式来运行这些线程，按此总方式执行，利用缓冲执行优化，能支持最大线程数+任务列队数
			return (ThreadPoolExecutor) Executors.newFixedThreadPool(thread_count); //列队大小为整数最大值、默认启动固定的线程，不会根据失效时间自动销毁，任务执行完成调用关闭按钮后则线程销毁、列队大小为整数最大值（一次执行的记录不能超过整数最大值）。
		} else if (EXECTOR_CACHE.equals(type)) { //创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们，按此种方式执行，性能高，能支持最大线程数的任务数执行
			executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
			executorService.setKeepAliveTime(keepAliveTime,TimeUnit.MILLISECONDS); //使用的缓存队列为SynchronousQueue ，对列为阻塞
			return executorService;
		}else if (EXECTOR_DEF.equals(type)) {
			executorService = new ThreadPoolExecutor(200,Integer.MAX_VALUE, 5 * 60, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>());//MAX_VALUE在列队满后才会开最大线程池
			return executorService;
		}
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(1); //单线程模式
	}
	
	/**
	 * 执行线程对象
	 * 
	 * @param taskThreadPool
	 */
	public static ZteResponse submit(TaskThreadPool taskThreadPool,ThreadPoolExecutor executorService) {
		executorService.execute(taskThreadPool);
		return taskThreadPool.getZteResponse();
	}
	
	// 关闭执行器
	public static void closeThreadPool(ExecutorService executor) {
		executor.shutdown();
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getSingleThreadPoolExector() {
		return singgeExecutor;
	}

	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getDefaultThreadPoolExector() {
		return defExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getOrderThreadPoolExector() {
		return orderExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getOrderExceThreadPoolExector() {
		logger.info("orderExceExecutor:MaximumPoolSize("+orderExceExecutor.getMaximumPoolSize()+")"
				+"PoolSize("+orderExceExecutor.getPoolSize()+")"+"CorePoolSize("+orderExceExecutor.getCorePoolSize()+")"
				+"TaskCount("+orderExceExecutor.getTaskCount()+")"+"ActiveCount("+orderExceExecutor.getActiveCount()+")");
		return orderExceExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getRoleDataThreadPoolExector() {
		return roleDataExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getInfServerThreadPoolExector() {
		return infServerExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getWriteCardThreadPoolExector() {
		logger.info("writeCardExecutor:MaximumPoolSize("+writeCardExecutor.getMaximumPoolSize()+")"
				+"PoolSize("+writeCardExecutor.getPoolSize()+")"+"CorePoolSize("+writeCardExecutor.getCorePoolSize()+")"
				+"TaskCount("+writeCardExecutor.getTaskCount()+")"+"ActiveCount("+writeCardExecutor.getActiveCount()+")");
		return writeCardExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getOrderStandingExecutor() {
		return orderStandingExecutor;
	}
	
	/**
	 * =======================================================<br>
	 * 使用该方式创建的线程池，一定要注意：<br>
	 * 1、使用完后，一定不要关闭线程池；<br>
	 * =======================================================<br>
	 * @return
	 */
	public static ThreadPoolExecutor getThreadPoolExector(int coreSize,
			int maxPoolSize, int seconds, int quenesize) {
		return ThreadPool;
	}

	/**
	 * 执行线程对象
	 * 
	 * @param taskThreadPool
	 */
	public static ZteResponse singleExecute(TaskThreadPool taskThreadPool) {
		singgeExecutor.execute(taskThreadPool);
		return taskThreadPool.getZteResponse();
	}

	/**
	 * 执行线程对象
	 * 
	 * @param taskThreadPool
	 */
	public static ZteResponse fixedExecute(TaskThreadPool taskThreadPool) {
		newFixedThreadPool.execute(taskThreadPool);
		
		return taskThreadPool.getZteResponse();
	}

//	/**
//	 * 执行线程对象
//	 * 
//	 * @param taskThreadPool
//	 */
//	public static ZteResponse cacheExecute(TaskThreadPool taskThreadPool) {
//
//		newCachedThreadPool.execute(taskThreadPool);
//		return taskThreadPool.getZteResponse();
//	}

	/**
	 * 执行线程对象
	 * 
	 * @param taskThreadPool
	 */
	public static ZteResponse defExecute(TaskThreadPool taskThreadPool) {
		defExecutor.execute(taskThreadPool);
		return new ZteResponse();
	}

	/**
	 * 订单执行线程对象
	 * 
	 * @param taskThreadPool
	 */
	public static ZteResponse orderExecute(TaskThreadPool taskThreadPool) {
		orderExecutor.execute(taskThreadPool);
		return taskThreadPool.getZteResponse();
	}
}
