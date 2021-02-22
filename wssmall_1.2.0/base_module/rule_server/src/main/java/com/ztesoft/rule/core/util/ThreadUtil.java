package com.ztesoft.rule.core.util;


/**
 * TODO
 * @author easonwu 
 * @creation Dec 23, 2013
 * 
 */
public class ThreadUtil {
	
	public static void sleepTimes(long times){
		try {  
  			 Thread.sleep(times);  //
         } catch (InterruptedException e1) {  
             e1.printStackTrace();  
         }
	}
	
	public static void randSleepTimes(){
		sleepTimes((long) (2000 * Math.random())) ;
	}
}
