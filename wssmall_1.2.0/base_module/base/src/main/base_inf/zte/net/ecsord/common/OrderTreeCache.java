package zte.net.ecsord.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zte.net.common.params.resp.QueryResponse;
import zte.net.ecsord.params.busi.req.OrderBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderExtvlBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderRealNameInfoBusiRequest;
import zte.net.treeInst.RequestStoreExector;

import com.ztesoft.net.cache.common.CacheFactory;
import com.ztesoft.net.cache.common.INetCache;

public class OrderTreeCache {
	private INetCache cache;
	private int orderTreeNameSpace=100;
	private static int time =60*24*60*5;
	public static OrderTreeCache dataFactory;
	private void initCache(){
		if(cache==null){
			cache= CacheFactory.getCache();
		}
	}
	public static OrderTreeCache getInstance(){
		if(dataFactory ==null)
			dataFactory = new OrderTreeCache();
		return dataFactory;
	}
	public OrderExtBusiRequest getOrderExtBusiRequest(String order_id){
		initCache();
		String key="es_order_ext_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderExtBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				OrderExtBusiRequest result = (OrderExtBusiRequest)((List)object).get(0);
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
//			if(!(retObject instanceof List)){ //返回列表
//				return (T)((List)resp.getQueryResults()).get(0);
//			}
		}else{
			return (OrderExtBusiRequest)obj;
		}
	}
	public void removeOrderExtBusiRequest(String order_id){
		String key="es_order_ext_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
	public OrderExtvlBusiRequest getOrderExtvlBusiRequest(String order_id){
		initCache();
		String key="es_order_extvtl_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderExtvlBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				OrderExtvlBusiRequest result = (OrderExtvlBusiRequest)((List)object).get(0);
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
		}else{
			return (OrderExtvlBusiRequest)obj;
		}
	}
	public void removeOrderExtvlBusiRequest(String order_id){
		String key="es_order_extvtl_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
	public OrderBusiRequest getOrderBusiRequest(String order_id){
		initCache();
		String key="es_order_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				OrderBusiRequest result = (OrderBusiRequest)((List)object).get(0);
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
		}else{
			return (OrderBusiRequest)obj;
		}
	}
	public void removeOrderBusiRequest(String order_id){
		String key="es_order_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
	public List<OrderDeliveryBusiRequest> getOrderDeliveryBusiRequest(String order_id){
		initCache();
		String key="es_delivery_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderDeliveryBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				ArrayList<OrderDeliveryBusiRequest> result =(ArrayList<OrderDeliveryBusiRequest>) object;
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
		}else{
			return (List<OrderDeliveryBusiRequest>)obj;
		}
	}
	public void removeOrderDeliveryBusiRequest(String order_id){
		String key="es_delivery_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
	public OrderRealNameInfoBusiRequest getOrderRealNameInfoBusiRequest(String order_id){
		initCache();
		String key="es_order_realname_info_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderRealNameInfoBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				OrderRealNameInfoBusiRequest result = (OrderRealNameInfoBusiRequest)((List)object).get(0);
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
		}else{
			return (OrderRealNameInfoBusiRequest)obj;
		}
	}
	public void removeOrderRealNameInfoBusiRequest(String order_id){
		String key="es_order_realname_info_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
	public  List<OrderItemExtBusiRequest> getOrderItemExtBusiRequest(String order_id){
		initCache();
		String key="es_order_items_ext_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderItemExtBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				ArrayList<OrderItemExtBusiRequest> result =(ArrayList<OrderItemExtBusiRequest>) object;
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
		}else{
			return (List<OrderItemExtBusiRequest>)obj;
		}
	}
	public void removeOrderItemExtBusiRequest(String order_id){
		String key="es_order_items_ext_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
	public  List<OrderItemProdExtBusiRequest> getOrderItemProdExtBusiRequest(String order_id){
		initCache();
		String key="es_order_items_prod_ext_"+order_id;
		Serializable obj=cache.get(orderTreeNameSpace,key);
		if(null==obj){//从数据库中查询
			QueryResponse resp=RequestStoreExector.getInstance().loadZteBusiRequestByDb(new OrderItemProdExtBusiRequest(),false,"order_id='"+order_id+"'");
			Object  object = resp.getQueryResults();
			if(object ==null){
				return null;
			}else{
				ArrayList<OrderItemProdExtBusiRequest> result =(ArrayList<OrderItemProdExtBusiRequest>) object;
				cache.set(orderTreeNameSpace, key,result,time);
				return result;
			}
		}else{
			return (List<OrderItemProdExtBusiRequest>)obj;
		}
	}
	public void removeOrderItemProdExtBusiRequest(String order_id){
		String key="es_order_items_prod_ext_"+order_id;
		cache.set(orderTreeNameSpace, key,null,time);
	}
}
