package com.ztesoft.net.framework.context.webcontext;

import org.apache.log4j.Logger;

import com.ztesoft.net.mall.core.action.order.OrderParams;


/**
 *  用ThreadLocal来存储Session,以便实现Session any where 
 * @author kingapex
 * <p>2009-12-17 下午03:10:09</p>
 * @version 1.1
 * 新增request any where
 */
public class ThreadOrderHolder  {
	
	protected static final Logger logger = Logger.getLogger(ThreadOrderHolder.class);
	private static ThreadLocal<OrderParams> orderParamsHolder = new ThreadLocal<OrderParams>();
	
	public static   OrderParams  getOrderParams() {
		if (orderParamsHolder.get() == null) {
			orderParamsHolder.set(new OrderParams());
		}else{
			
		}
		return orderParamsHolder.get();
	}
}
