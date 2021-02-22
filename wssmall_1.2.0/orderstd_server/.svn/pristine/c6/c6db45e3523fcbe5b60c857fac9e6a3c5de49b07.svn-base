package com.ztesoft.newstd.util;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.api.ApiBusiException;
import com.ztesoft.net.attr.hander.AttrSwitchParams;
import com.ztesoft.net.attr.hander.IAttrHandler;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.consts.OrderConsts;
import com.ztesoft.net.mall.core.model.AttrTypeRela;
import com.ztesoft.net.mall.core.model.BusinessType;
import com.ztesoft.net.mall.core.service.IAttrTypeRelaManager;
import com.ztesoft.net.mall.core.service.IBusinessTypeManager;
import com.ztesoft.newstd.common.CommonData;

import zte.net.ecsord.params.busi.req.AttrPackageBusiRequest;
import zte.net.ecsord.params.busi.req.AttrTmResourceInfoBusiRequest;
import zte.net.ecsord.params.busi.req.OrderDeliveryBusiRequest;
import zte.net.ecsord.params.busi.req.OrderInternetBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdBusiRequest;
import zte.net.ecsord.params.busi.req.OrderItemProdExtBusiRequest;
import zte.net.ecsord.params.busi.req.OrderPayBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class BusinessHandlerUtil  extends BaseSupport {

    public static final Map<String,String> CONFIGMAP; 
    private IDaoSupport baseDaoSupport ;
	private IBusinessTypeManager businessTypeManager;
	private IAttrTypeRelaManager attrTypeRelaManager;
	
    static {
    	Map config_map = new HashMap();
    	config_map.put(OrderConsts.ES_ORDER_EXTVTL, OrderConsts.ORDEREXTVTLHANDLER);
    	config_map.put(OrderConsts.ES_DELIVERY, OrderConsts.DELIVERYHANDLER);
    	config_map.put(OrderConsts.ES_PAYMENT_LOGS, OrderConsts.PAYMENTLOGSHANDLER);
    	config_map.put(OrderConsts.ES_ORDER, OrderConsts.ORDERHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_EXT, OrderConsts.ORDEREXTHANDLER);
    	config_map.put(OrderConsts.ES_ATTR_DISCOUNT_INFO, OrderConsts.ATTRDISCOUNTINFOHANDLER);
    	config_map.put(OrderConsts.ES_ATTR_FEE_INFO, OrderConsts.ATTRFEEINFOHANDLER);
    	config_map.put(OrderConsts.ES_ATTR_PACKAGE, OrderConsts.ATTRPACKAGEHANDLER);
    	config_map.put(OrderConsts.ES_ATTR_TERMINAL_EXT, OrderConsts.ATTRTERMINALEXTHANDLER);
    	config_map.put(OrderConsts.ES_HUASHENG_ORDER, OrderConsts.HUASHENGORDERHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_ITEMS_APT_PRINTS, OrderConsts.ORDERITEMSAPTPRINTSHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_PHONE_INFO, OrderConsts.ORDERPHONEINFOHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_SUB_OTHER_INFO, OrderConsts.ORDERSUBOTHERINFONEWHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_SUB_PROD_INFO, OrderConsts.ORDERSUBPRODINFONEWHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_ZHWQ_ADSL, OrderConsts.ORDERZHWQADSLHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_ITEMS_PROD_EXT, OrderConsts.ORDERITEMSPRODEXTHANDLER);
    	config_map.put(OrderConsts.ES_ATTR_GIFT_INFO, OrderConsts.ATTRGIFTINFOHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_ITEMS_EXT, OrderConsts.ORDERITEMSEXTHANDLER);
    	config_map.put(OrderConsts.ES_ORDER_REALNAME_INFO, OrderConsts.ORDERREALNAMEINFOHANDLER);
        config_map.put(OrderConsts.ES_ORDER_WMS_KEY_INFO, OrderConsts.ORDERWMSKEYINFOHANDLER);
        config_map.put(OrderConsts.ES_ORDER_INTENT_INFO, OrderConsts.ORDERINTENTINFOHANDLE);
    	CONFIGMAP = Collections.unmodifiableMap(config_map);
    	
    }

    
    public  void executeHandler(Map paramsl) throws ApiBusiException{
        baseDaoSupport = SpringContextHolder.getBean("baseDaoSupport");
        businessTypeManager = SpringContextHolder.getBean("businessTypeManager");
        attrTypeRelaManager = SpringContextHolder.getBean("attrTypeRelaManager");
        
		Long time1= System.currentTimeMillis();
		OrderTreeBusiRequest orderTree= CommonData.getData().getOrderTreeBusiRequest();
        String order_id = orderTree.getOrderBusiRequest().getOrder_id();
        String goods_id = orderTree.getOrderBusiRequest().getGoods_id();
        List<OrderItemProdBusiRequest>  orderItemProdBusiRequest = orderTree.getOrderItemBusiRequests().get(0).getOrderItemProdBusiRequests();
        String prod_goods_id ="";
        if(orderItemProdBusiRequest.size()>0){
           prod_goods_id = orderItemProdBusiRequest.get(0).getProd_spec_goods_id();
        }
        String order_from = orderTree.getOrderExtBusiRequest().getOrder_from();
        String good_type = orderTree.getOrderBusiRequest().getGoods_type();
        
        BusinessType businessType = businessTypeManager.getBusiType(good_type, order_from);
        List<AttrTypeRela> attrTypeRelaList = attrTypeRelaManager.getAttrTypeRela(businessType.getBusi_type_id());
		Long time2= System.currentTimeMillis();
        logger.info(Thread.currentThread().getId()+"==============executeHandler开始耗时:"+(time2-time1)+"====开始时间"+time1+"结束时间"+time2);
        for(AttrTypeRela attrRela:attrTypeRelaList){
            String tableName = attrRela.getTable_name();
            String handlerClass = attrRela.getHandler_class();
            if(isRightConfig(tableName,handlerClass))
            	executeHander(tableName,handlerClass,order_id,goods_id,prod_goods_id,order_from,good_type,paramsl);
			else
				throw new ApiBusiException("配置错误:请检查es_business_type_attr_rela中,table_name,handler_class是否配置正确");	
        }
		

            
        Long time3= System.currentTimeMillis();
        logger.info(Thread.currentThread().getId()+"==============executeHandler结束耗时:"+(time3-time2)+"====开始时间"+time2+"结束时间"+time3);
         
    }
    //执行订单树
    public  void executeHander(String tableName,String handlerClass,String order_id,String goods_id,String prod_goods_id,String order_from,String good_type,Map paramsl){
	  Long time1 = System.currentTimeMillis();
        if (OrderConsts.ES_ORDER_EXTVTL.equalsIgnoreCase(tableName))
        {
        	String inst_id = order_id;
        	Object busiRequest = CommonData.getData().getOrderExtvlBusiRequest();
        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
			Long time2= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderExtvtl属性处理器耗时:"+(time2-time1));
         }else if (OrderConsts.ES_DELIVERY.equalsIgnoreCase(tableName)) {
        	 List<OrderDeliveryBusiRequest> list  = CommonData.getData().getOrderTreeBusiRequest().getOrderDeliveryBusiRequests();
            if(list.size()>0){
            	for(int i=0;i<list.size();i++){
            		OrderDeliveryBusiRequest busiRequest =list.get(i);
            		String inst_id=busiRequest.getDelivery_id();
                	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
            	}
            	
            }
            Long time3= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================delevery属性处理器耗时:"+(time3-time1));
        }else if (OrderConsts.ES_PAYMENT_LOGS.equalsIgnoreCase(tableName)) {
        	List<OrderPayBusiRequest> list =CommonData.getData().getOrderTreeBusiRequest().getOrderPayBusiRequests();
        	if(list.size()>0){
            	for(int i=0;i<list.size();i++){
            		OrderPayBusiRequest busiRequest =list.get(i);
            		String inst_id=busiRequest.getPayment_id();
                	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
            	}
        	}
        	 Long time4= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================payment属性处理器耗时:"+(time4-time1));
        }else if (OrderConsts.ES_ORDER.equalsIgnoreCase(tableName)){    
            String inst_id = order_id;
            Object busiRequest = CommonData.getData().getOrderTreeBusiRequest().getOrderBusiRequest();
        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
			Long time6= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================order属性处理器耗时:"+(time6-time1));
        }else if (OrderConsts.ES_ORDER_EXT.equalsIgnoreCase(tableName)){         
            String inst_id = order_id;
            Object busiRequest = CommonData.getData().getOrderTreeBusiRequest().getOrderExtBusiRequest();
        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
			 Long time6= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderext属性处理器耗时:"+(time6-time1));
        }else if(OrderConsts.ES_ATTR_DISCOUNT_INFO.equalsIgnoreCase(tableName)) {            //浙江没有使用因此屏蔽调
//            String inst_id = order_id;
//            Object busiRequest = CommonData.getData().getAttrDiscountInfoBusiRequest();
//        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
//			  Long time7= System.currentTimeMillis();
//            logger.info(Thread.currentThread().getId()+"=================attrDiscountInfo属性处理器耗时:"+(time7-time1));
        }else if(OrderConsts.ES_ATTR_FEE_INFO.equalsIgnoreCase(tableName)) {
            String inst_id = order_id;
            List list = CommonData.getData().getOrderTreeBusiRequest().getFeeInfoBusiRequests();
             processHandler(order_id, goods_id, inst_id, list, paramsl, prod_goods_id, order_from, handlerClass);
			Long time8= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================attrFeeInfo属性处理器耗时:"+(time8-time1));
        }else if(OrderConsts.ES_ATTR_PACKAGE.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
            List<AttrPackageBusiRequest> list = CommonData.getData().getOrderTreeBusiRequest().getPackageBusiRequests();
            processHandler(order_id, goods_id, inst_id, list, paramsl, prod_goods_id, order_from, handlerClass);
            Long time9= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================attrpackage属性处理器耗时:"+(time9-time1));
        }else if(OrderConsts.ES_ATTR_TERMINAL_EXT.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
            List<AttrTmResourceInfoBusiRequest> list = CommonData.getData().getOrderTreeBusiRequest().getTmResourceInfoBusiRequests();
        	processHandler(order_id, goods_id, inst_id, list, paramsl, prod_goods_id, order_from, handlerClass);
			 Long time10= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================attrterminalext属性处理器耗时:"+(time10-time1));
        }else if(OrderConsts.ES_HUASHENG_ORDER.equalsIgnoreCase(tableName)) {
//        	无华盛业务屏蔽
//            String inst_id = order_id;
//            Object busiRequest = CommonData.getData().getHuashengOrderBusiRequests();
//        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
//			Long time11= System.currentTimeMillis();
//            logger.info(Thread.currentThread().getId()+"=================huasheng属性处理器耗时:"+(time11-time1));
        }else if(OrderConsts.ES_ORDER_ITEMS_APT_PRINTS.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
        	processHandler(order_id, goods_id, inst_id, null, paramsl, prod_goods_id, order_from, handlerClass);
			Long time12= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderItemsaptprints属性处理器耗时:"+(time12-time1));
        }else if(OrderConsts.ES_ORDER_PHONE_INFO.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
            Object busiRequest =  CommonData.getData().getOrderTreeBusiRequest().getPhoneInfoBusiRequest();
        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
			   Long time13= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderphomeinfo属性处理器耗时:"+(time13-time1));
        }else if(OrderConsts.ES_ORDER_SUB_OTHER_INFO.equalsIgnoreCase(tableName)) {
            String inst_id = order_id;
            Object busiRequest = CommonData.getData().getOrderTreeBusiRequest().getOrderSubOtherInfoBusiRequest();
        	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
			 Long time14= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================ordersubotherinfo属性处理器耗时:"+(time14-time1));
        }else if(OrderConsts.ES_ORDER_SUB_PROD_INFO.equalsIgnoreCase(tableName)){            
            String inst_id = order_id;
            List list = CommonData.getData().getOrderTreeBusiRequest().getOrderSubProdInfoBusiRequest();
        	processHandler(order_id, goods_id, inst_id, list, paramsl, prod_goods_id, order_from, handlerClass);
			   Long time15= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================ordersubprodinfo属性处理器耗时:"+(time15-time1));
        }else if(OrderConsts.ES_ORDER_ZHWQ_ADSL.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
            List list= CommonData.getData().getOrderTreeBusiRequest().getOrderAdslBusiRequest();
        	processHandler(order_id, goods_id, inst_id, list, paramsl, prod_goods_id, order_from, handlerClass);
			Long time16= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderzhwqadsl属性处理器耗时:"+(time16-time1));
        }else if(OrderConsts.ES_ORDER_ITEMS_PROD_EXT.equalsIgnoreCase(tableName)){
        	List<OrderItemBusiRequest> orderItems  = CommonData.getData().getOrderTreeBusiRequest().getOrderItemBusiRequests();
             for (OrderItemBusiRequest orderItemBusiRequest : orderItems) {
            	 List<OrderItemProdBusiRequest> reqs =orderItemBusiRequest.getOrderItemProdBusiRequests();
                 for (OrderItemProdBusiRequest orderItemProdBusiRequest : reqs) {
                	 OrderItemProdExtBusiRequest req= orderItemProdBusiRequest.getOrderItemProdExtBusiRequest();
                	 String inst_id = req.getItem_prod_inst_id();
                 	 processHandler(order_id, goods_id, inst_id, req, paramsl, prod_goods_id, order_from, handlerClass);
                 
				}
			}
			 Long time17= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderEitemsprodext属性处理器耗时:"+(time17-time1));
        }else if(OrderConsts.ES_ATTR_GIFT_INFO.equalsIgnoreCase(tableName)){
        	
//        	无用代码屏蔽
//            List<AttrGiftInfoBusiRequest> reqs = CommonData.getData().getAttrGiftInfoBusiRequests();
//            String inst_id = order_id;
//            Object busiRequest = reqs;
//            processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
//			 Long time18= System.currentTimeMillis();
//            logger.info(Thread.currentThread().getId()+"=================attrgiftinfo属性处理器耗时:"+(time18-time1));
        }
        if(OrderConsts.ES_ORDER_ITEMS_EXT.equalsIgnoreCase(tableName)){
            List<OrderItemBusiRequest> reqs = CommonData.getData().getOrderTreeBusiRequest().getOrderItemBusiRequests();
            for(OrderItemBusiRequest req :reqs){                
                String inst_id = req.getItem_id();
                OrderItemExtBusiRequest busiRequest = req.getOrderItemExtBusiRequest();
            	processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
            }
			  Long time19= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderitemext属性处理器耗时:"+(time19-time1));
        }
        if(OrderConsts.ES_ORDER_REALNAME_INFO.equalsIgnoreCase(tableName)){           
                String inst_id = order_id;
                Object busiRequest = CommonData.getData().getOrderTreeBusiRequest().getOrderRealNameInfoBusiRequest();
                processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
			  Long time20= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================orderrealnameinfo属性处理器耗时:"+(time20-time1));
        }
        if(OrderConsts.ES_ORDER_WMS_KEY_INFO.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
            Object busiRequest = CommonData.getData().getOrderTreeBusiRequest().getOrderWMSKeyInfoBusiRequest();
            processHandler(order_id, goods_id, inst_id, busiRequest, paramsl, prod_goods_id, order_from, handlerClass);
            Long time20= System.currentTimeMillis();
           logger.info(Thread.currentThread().getId()+"=================esorderwmskeyinfo属性处理器耗时:"+(time20-time1));
        }
        if(OrderConsts.ES_ORDER_INTENT_INFO.equalsIgnoreCase(tableName)){
            String inst_id = order_id;
            List<OrderInternetBusiRequest> list = CommonData.getData().getOrderTreeBusiRequest().getOrderInternetBusiRequest();
            processHandler(order_id, goods_id, inst_id, list, paramsl, prod_goods_id, order_from, handlerClass);
            Long time21= System.currentTimeMillis();
            logger.info(Thread.currentThread().getId()+"=================esorderinternetbusi属性处理器耗时:"+(time21-time1));
        }

    }
    
    
    public  void processHandler(String order_id,String goods_id, String inst_id, Object busiRequest, Map orderAttrValues, String pro_goods_id, String order_from, String handlerClass) {
    	   AttrSwitchParams params = new AttrSwitchParams();
           params.setOrder_id(order_id);
           params.setGoods_id(goods_id);
           params.setInst_id(inst_id);
           params.setBusiRequest(busiRequest);
           params.setOrderAttrValues(orderAttrValues);
           params.setPro_goods_id(pro_goods_id);
           params.setOrder_from(order_from);
           IAttrHandler handler = SpringContextHolder.getBean(handlerClass);
           //调用对应Handler
           handler.handler(params);
    }
    
    
    
    
    public  boolean isRightConfig(String tableName,String handlerClass) {
    	//tableName/handlerClass为空
    	if("null".equals(tableName)||"null".equals(handlerClass))
    		return false;
    	//配置错误
    	if(!handlerClass.equals(CONFIGMAP.get(tableName)))
    		return false;
    	return true;
    }
    
    
    
    
    

}
