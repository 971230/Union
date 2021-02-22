package com.ztesoft.rule.core.test;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ztesoft.rule.core.exe.runtime.IRuntime;
import com.ztesoft.rule.core.exe.runtime.JitParam;


/**
 * TODO
 * @author easonwu 
 * @creation Dec 18, 2013
 * 
 */
public class Main {
 private static final Logger logger = Logger.getLogger(Main.class);

	public static void main(String[] p1) {
//		logger.info("test") ;
//		logger.debug("t1est") ;
		long l1 = System.currentTimeMillis() ;
		JitParam jitParam = new JitParam() ;

		final ApplicationContext context = new ClassPathXmlApplicationContext(new
		 String[] {"spring/dataAccessContext-jdbc.xml","spring/ruleContext.xml","spring/ruleServiceContext.xml"});
		 //,"spring/sysServiceContext.xml"
		 /*IRuntime timingRunner = (IRuntime)context.getBean("directCaller") ;
		 Map map = new HashMap();
		 map.put("order_id", "201403077544050722");
		 map.put("plan_code", "P0088");
		 jitParam.setData(map);
		 timingRunner.setJitParam(jitParam) ;
		 timingRunner.run() ;*/
		 
		 new Thread(new Runnable() {
			@Override
			public void run() {
				IRuntime timingRunner = (IRuntime)context.getBean("timingRunner") ;
				 timingRunner.run() ;
			}
		 }).start();
		/* try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 TimingRunner.timerFlag = false;
		 logger.info("stop====");*/
		 long l2 = System.currentTimeMillis() ;
		 logger.info("COST=="+(l2-l1)/1000) ;
		 
		 int jjx = 0;
	}
}
