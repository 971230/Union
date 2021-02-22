package util;

import com.ztesoft.net.mall.core.model.OrderCommonData;

/**
 * 线程变量接口
* @作者 MoChunrun 
* @创建日期 2013-10-12 
* @版本 V 1.0
* 
* @说明：此线程变量只提供给订单内部使用，外部不允许使用
 */
public class OrderThreadLocalHolder {

	
	private ThreadLocal<OrderCommonData> orderCommonData = new ThreadLocal<OrderCommonData>();
	
	private static final OrderThreadLocalHolder local = new OrderThreadLocalHolder();
	
	
	public static OrderThreadLocalHolder getInstance(){
		return local;
	}
	
	public OrderCommonData getOrderCommonData() {
		OrderCommonData orderCommonDataInst =orderCommonData.get(); 
		if(orderCommonDataInst ==null){
			orderCommonDataInst = new OrderCommonData();
			orderCommonData.set(orderCommonDataInst);
		}
		return orderCommonData.get();
	}

	public void clear(){
		orderCommonData.remove();
	}
	
	
}
