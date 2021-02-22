package util;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.MD5Util;
import com.ztesoft.net.cache.common.INetCache;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public final class RefrenshDataUtil{
	private static Logger logger = Logger.getLogger(RefrenshDataUtil.class);
	 private static IDaoSupport baseDaoSupport ;
	 /** * 订单重复性校验NAMESPACE*/
	 public static final int ORDER_DUPLICATE_CHECK_NAMESPACE = 5000;
     public static void refrenshRepearOrder(String orderIds,String is_remove_outer_data,String source_from){
    	try {
    	 String[] orderIdArr = orderIds.split(",");
    	 String orderIdStr  = "";
    	 if(orderIdArr.length>0){
    		 for (String order_id : orderIdArr) {
				//删除缓存
    			INetCache cache = com.ztesoft.net.cache.common.CacheFactory.getCacheByType("");
 			    String key = new StringBuffer().append(source_from).append(order_id).toString();
 			    key = MD5Util.MD5(key);
				int NAMESPACE = ORDER_DUPLICATE_CHECK_NAMESPACE;
				logger.info("删除重复订单缓存前======"+cache.get(NAMESPACE, key));
				cache.delete(NAMESPACE, key);
				logger.info("删除重复订单缓存后======"+cache.get(NAMESPACE, key));
				orderIdStr+="'"+order_id+"',";
			 }
    		 orderIdStr =  orderIdStr.substring(0,orderIdStr.length()-1);
    	 }
    	 if("1".equals(is_remove_outer_data)){
    	 	 String sql = "delete from es_order_outer where source_from ='"+ManagerUtils.getSourceFrom()+"' and  old_sec_order_id in("+orderIdStr+") ";
    	 	 baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
    	 	 baseDaoSupport.execute(sql);
    	 }
    	} catch (Exception e) {
			e.printStackTrace();
		}
     }
}
