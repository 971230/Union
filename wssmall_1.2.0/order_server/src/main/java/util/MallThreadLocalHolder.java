package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.consts.OrderStatus;
import com.ztesoft.net.mall.core.model.AppSource;
import com.ztesoft.net.mall.core.model.Order;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.model.AttrInst;

/**
 * 线程变量接口
* @作者 MoChunrun 
* @创建日期 2013-10-12 
* @版本 V 1.0
 */
public class MallThreadLocalHolder {

	private static final MallThreadLocalHolder local = new MallThreadLocalHolder();
	//外系统订单
	private ThreadLocal<List<OrderOuter>> outerOrderLocals = new ThreadLocal<List<OrderOuter>>();
	
	//当前外系统订单
	private ThreadLocal<OrderOuter> outerOrderLocal = new ThreadLocal<OrderOuter>();
	
	private ThreadLocal<Map<String, String>> cartOuterLocal = new ThreadLocal<Map<String, String>>();
	
	//外系统订单属性
	private ThreadLocal<List<AttrInst>> attrInstLocal = new ThreadLocal<List<AttrInst>>();

    private ThreadLocal<AppSource> sourceThreadLocal=new ThreadLocal<AppSource>();//app来源
	
	
	
	//private ThreadLocal<String> sessionIdLocal = new ThreadLocal<String>();
	
	private ThreadLocal<Map<String, Order>> orderLocal = new ThreadLocal<Map<String, Order>>();
	
	
	//private ThreadLocal<ZteResponse> outEntryLocal = new ThreadLocal<ZteResponse>(); //外围参数
	
	private ThreadLocal<Map<String,String>> stringValue = new ThreadLocal<Map<String,String>>();

	
	private MallThreadLocalHolder(){}
	
	public static MallThreadLocalHolder getInstance(){
		return local;
	}

	public void setStringValue(String key,String value){
		if(stringValue.get()!=null){
			this.stringValue.get().put(key, value);
		}else{
			Map<String,String> map = new HashMap<String,String>();
			map.put(key, value);
			this.stringValue.set(map);
		}
	}
	public String getStringValue(String key){
		if(this.stringValue.get()!=null){
			return this.stringValue.get().get(key);
		}else{
			return null;
		}
	}
	
    public AppSource  getAppSource() {
        return sourceThreadLocal.get();
    }

    public void setAppSource(AppSource source) {
        this.sourceThreadLocal.set(source);
    }

    /**
	 * 获取外订单系统属性
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public  Map<String, Order> getOrderLocal(){
		return orderLocal.get();
	}
	
	/**
	 * 设置外订单系统属性
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @param list
	 */
	public void setOrderLocal(Order order){
		Map<String,Order> orders =orderLocal.get();
		if(orders ==null){
			orders = new HashMap<String,Order>();
			orderLocal.set(orders);
		}
		orders.put(order.getOrder_id(), order);
	}
	
	
	public  Map<String, String> getCartOuterLocal(){
		return cartOuterLocal.get();
	}
	
	
	public void setCartOuterLocal(String goods_id,String order_outer_id){
		Map<String,String> orders =cartOuterLocal.get();
		if(orders ==null){
			orders = new HashMap<String,String>();
			cartOuterLocal.set(orders);
		}
		orders.put(goods_id, order_outer_id);
	}
	
	
	
	
	/*public String getUUID(){
		String uuid = sessionIdLocal.get();
		if(uuid==null || "".equals(uuid)){
			uuid = UUID.randomUUID().toString().replace("-", "");
			sessionIdLocal.set(uuid);
		}
		return uuid;
	}*/
	
	public List<OrderOuter> getOrderOuters(){
		return outerOrderLocals.get();
	}
	
	
	/**
	 * 获取外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public OrderOuter getOrderOuter(){
		
		List<OrderOuter> orderOuters = outerOrderLocals.get();
		return orderOuters.get(0);
	}
	
	/**
	 * 设置外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @param order
	 */
	public void setOrderOuter(OrderOuter order){
		List<OrderOuter> orderOuters = outerOrderLocals.get();
		if(orderOuters == null)
			orderOuters = new ArrayList<OrderOuter>();
		outerOrderLocals.set(orderOuters);
		orderOuters.add(order);
	}
	
	
	/**
	 * 获取外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public OrderOuter getCurrOrderOuter(){
		return outerOrderLocal.get();
	}
	
	/**
	 * 设置外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @param order
	 */
	public void setCurrOrderOuter(OrderOuter order){
		outerOrderLocal.set(order);
	}
	
	/* @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	/*public ZteResponse  getOutEntryLocal(){
		return outEntryLocal.get();
	}*/
	
	/**
	 * 设置外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @param order
	 */
	/*public void setOutEntryLocal(ZteResponse order){
		outEntryLocal.set(order);
	}*/
	

	/**
	 * 获取外订单系统属性
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public List<AttrInst> getOuterAttrInstList(){
		return attrInstLocal.get();
	}
	
	/**
	 * 设置外订单系统属性
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @param list
	 */
	public void setOuterAttrInstList(List<AttrInst> list){
		attrInstLocal.set(list);
	}
	
	/**
	 * 获取外系统订单来源
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public String getOuterOrderSourceFrom(){
		return getCurrOrderOuter().getSource_from();
	}
	
	/**
	 * 是否外系统订单
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12 
	 * @return
	 */
	public boolean isOuterOrder(){
		String sourceForm = getOuterOrderSourceFrom();
		if(!OrderStatus.SOURCE_FROM_TAOBAO.equals(sourceForm) && !OrderStatus.SOURCE_FROM_FJ.equals(sourceForm) 
				&& !OrderStatus.SOURCE_FROM_WT.equals(sourceForm) && !OrderStatus.SOURCE_FROM_HN.equals(sourceForm)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 清空线程变量
	 * @作者 MoChunrun 
	 * @创建日期 2013-10-12
	 */
	public void clear(){
		attrInstLocal.remove();
		outerOrderLocals.remove();
		//sessionIdLocal.remove();
        sourceThreadLocal.remove();
	}
	
	
}
