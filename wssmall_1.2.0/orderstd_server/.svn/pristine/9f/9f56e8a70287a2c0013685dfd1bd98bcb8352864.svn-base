package com.ztesoft.newstd.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.DateFormatUtils;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.utils.BusiUtils;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.newstd.common.CommonData;
import com.ztesoft.newstd.dao.ICommonDataDao;

import commons.CommonTools;
import consts.ConstsCore;
import edu.emory.mathcs.backport.java.util.Arrays;
import params.ZteBusiRequest;
import zte.net.common.annontion.context.request.RequestBeanDefinition;
import zte.net.common.annontion.context.request.RequestFieldDefinition;
import zte.net.ecsord.params.busi.req.OrderDeliveryItemBusiRequest;
import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

public class CommonDataDao implements ICommonDataDao {
	private static Logger logger = Logger.getLogger(CommonDataDao.class);
    // 订单树入库
    @Override
    public void insertNoCache() throws Exception {
        Long time1 = System.currentTimeMillis();
        insert(getInsertList());
        Long time2 = System.currentTimeMillis();
        logger.info(Thread.currentThread().getId() + "=======================订单对象入库耗时:" + (time2 - time1) + "====开始时间" + time1 + "结束时间" + time2);
    }

    private void insert(List<InsertBody> insertBodys) throws Exception {
        DataSource dataSource = SpringContextHolder.getBean("dataSource");
        Connection conn = null;
        List<PreparedStatement> preparedStatements = new ArrayList<PreparedStatement>();
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            for (InsertBody insertBody : insertBodys) {
            	logger.error("CommonDataDao insert sql:"+insertBody.getSql());
            	
                PreparedStatement pstmt = conn.prepareStatement(insertBody.getSql());
                Object[] values = insertBody.getValues();
                
                StringBuilder params = new StringBuilder();
                
                for (int i = 0; i < values.length; i++) {
                    Object value = values[i];
                    
                    if(params.length()==0)
                    	params.append(value);
                    else
                    	params.append(",").append(value);
                    
                    if (value instanceof java.sql.Date) {
                        pstmt.setDate(i + 1, (java.sql.Date)value);
                    } else if (value instanceof java.sql.Timestamp) {
                        pstmt.setTimestamp(i + 1, (java.sql.Timestamp)value);
                    } else if (value instanceof java.util.Date) {
                        Date date = (Date)value;
                        pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
                    } else {
                        pstmt.setObject(i + 1, value);
                    }
                }
                
                logger.error("CommonDataDao insert params:"+params.toString());
                
                pstmt.executeUpdate();
                preparedStatements.add(pstmt);
            }
            conn.commit();
        } catch (Exception e) {
        	logger.error("CommonDataDao insert error:"+e.getMessage(),e);
        	
            try {
                conn.rollback();
            } catch (SQLException e1) {
            	logger.error("CommonDataDao rollback error:"+e1.getMessage(),e1);
            }
            
            throw e;
        } finally {
            for (PreparedStatement preparedStatement : preparedStatements) {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                    	logger.error("CommonDataDao preparedStatement close error:"+e.getMessage(),e);
                    }
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                	logger.error("CommonDataDao connect close error:"+e.getMessage(),e);
                }
            }
        }
    }

    private List<InsertBody> getInsertList() {
        Long time1 = System.currentTimeMillis();
        CommonData commonData = CommonData.getData();
        List<InsertBody> insertBodies = new ArrayList<InsertBody>();
        getInsertBody(insertBodies,commonData.getOrderTreeBusiRequest());
        getInsertBody(insertBodies, commonData.getMemberBusiRequest());
        for (OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest : commonData.getOrderDeliveryItemBusiRequests()) {
            getInsertBody(insertBodies,orderDeliveryItemBusiRequest);
        }
        getInsertBody(insertBodies,commonData.getOrderExtvlBusiRequest());
        
//        insertBodies.add(getInsertBody(commonData.getOrderTreeBusiRequest()));
//            insertBodies.add(getInsertBody(commonData.getOrderBusiRequest()));
//            insertBodies.add(getInsertBody(commonData.getOrderExtBusiRequest()));
//            insertBodies.add(getInsertBody(commonData.getOrderPayBusiRequest()));
//            insertBodies.add(getInsertBody(commonData.getOrderDeliveryBusiRequest()));

//            for (OrderItemBusiRequest orderItemBusiRequest : commonData.getOrderItemBusiRequests()) {
//                insertBodies.add(getInsertBody(orderItemBusiRequest));
//            }
//            for (OrderItemExtBusiRequest orderItemExtBusiRequest : commonData.getOrderItemExtBusiRequests()) {
//                insertBodies.add(getInsertBody(orderItemExtBusiRequest));
//            }
//            for (OrderItemProdBusiRequest orderItemProdBusiRequest : commonData.getOrderItemProdBusiRequests()) {
//                insertBodies.add(getInsertBody(orderItemProdBusiRequest));
//            }
//            for (OrderItemProdExtBusiRequest orderItemProdExtBusiRequest : commonData.getOrderItemProdExtBusiRequests()) {
//                insertBodies.add(getInsertBody(orderItemProdExtBusiRequest));
//            }
//            for (OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest : commonData.getOrderDeliveryItemBusiRequests()) {
//                insertBodies.add(getInsertBody(orderDeliveryItemBusiRequest));
//            }
//
//            /** ---------------------------属性处理器相关表入库---------------------- **/
//
//            for (AttrGiftInfoBusiRequest attrGiftInfoBusiRequest : commonData.getAttrGiftInfoBusiRequests()) {
//                insertBodies.add(getInsertBody(attrGiftInfoBusiRequest));
//            }
//            for (AttrFeeInfoBusiRequest attrFeeInfoBusiRequest : commonData.getAttrFeeInfoBusiRequests()) {
//                insertBodies.add(getInsertBody(attrFeeInfoBusiRequest));
//            }
//            for (AttrGiftParamBusiRequest attrGiftParamBusiRequest : commonData.getAttrGiftParamBusiRequests()) {
//                insertBodies.add(getInsertBody(attrGiftParamBusiRequest));
//            }
//            for (AttrPackageBusiRequest attrPackageBusiRequest : commonData.getAttrPackageBusiRequests()) {
//                insertBodies.add(getInsertBody(attrPackageBusiRequest));
//            }
//            for (HuashengOrderBusiRequest huashengOrderBusiRequest : commonData.getHuashengOrderBusiRequests()) {
//                insertBodies.add(getInsertBody(huashengOrderBusiRequest));
//            }
//            for (HuashengOrderItemBusiRequest huashengOrderItemBusiRequest : commonData.getHuashengOrderItemBusiRequests()) {
//                insertBodies.add(getInsertBody(huashengOrderItemBusiRequest));
//            }
//            if (commonData.getOrderExtvlBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getOrderExtvlBusiRequest()));
//            if (commonData.getOrderSpProductBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getOrderSpProductBusiRequest()));
//            if (commonData.getOrderSubProductBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getOrderSubProductBusiRequest()));
//            if (commonData.getAttrPackageSubProdBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getAttrPackageSubProdBusiRequest()));
//            if (commonData.getAttrDiscountInfoBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getAttrDiscountInfoBusiRequest()));
//            if (commonData.getAttrTmResourceInfoBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getAttrTmResourceInfoBusiRequest()));
//            if (commonData.getOrderItemsAptPrintsBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getOrderItemsAptPrintsBusiRequest()));
//            if (commonData.getOrderPhoneInfoBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getOrderPhoneInfoBusiRequest()));
//            if (commonData.getOrderAdslBusiRequest() != null)
//                insertBodies.add(getInsertBody(commonData.getOrderAdslBusiRequest()));
            Long time2 = System.currentTimeMillis();
            logger.info(Thread.currentThread().getId() + "=======================订单对象入库前sql组装耗时:" + (time2 - time1) + "====开始时间" + time1 + "结束时间" + time2);
        return insertBodies;
    }

    private void getInsertBody(List<InsertBody> insertBodyList,ZteBusiRequest request) {
        //获取订单字段及字段数据
        try {
            List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
            
            RequestBeanDefinition<ZteBusiRequest>  requestBean=BusiUtils.getRequestServiceDefinition(request);
            //判断逐渐是否有值，无值时不插入
            String key=requestBean.getPrimary_keys();
            
            String method_name = "get"+key.substring(0,1).toUpperCase()+key.substring(1);
            Object obj = null;
            
            try{
            	obj = MethodUtils.invokeMethod(request, method_name, null);
            }catch(Exception e){
            	if(e instanceof NoSuchMethodException){
            		method_name = key.toLowerCase();
            		
            		method_name = "get"+method_name.substring(0,1).toUpperCase()+method_name.substring(1);
            		
            		obj = MethodUtils.invokeMethod(request, method_name, null);
            	}
            }
            
            if(obj==null||"".equals(obj)){
            	return ;
            }

            if(null!=fieldDefinitions&&fieldDefinitions.size()>0){
                //字段封装
                HashMap<String, Object> insertsMaps = new HashMap<String, Object>();
                for (int i = 0; i < fieldDefinitions.size(); i++) {
                    RequestFieldDefinition  fieldDefine = fieldDefinitions.get(i);
                    String type = fieldDefine.getField().getType().getName(); //字段类型
                    String fname = fieldDefine.getDname();
                    String needInsert = fieldDefine.getNeed_insert();
                    if (type.indexOf("com.") > -1 || type.indexOf("zte.") > -1 || type.indexOf("service.") > -1) {
                		ZteBusiRequest zteBusiRequest=(ZteBusiRequest) fieldDefine.getField().get(request);
                		 getInsertBody(insertBodyList,zteBusiRequest);
                		 continue;
                    } else if ("java.util.List".equals(type)) {
                    	List<ZteBusiRequest> list =  (List<ZteBusiRequest>)fieldDefine.getField().get(request);;
                    	if(list!=null&&list.size()>0){
                    		for (ZteBusiRequest zteBusiRequest : list) {
                    			 getInsertBody(insertBodyList,zteBusiRequest);
							}
                    	}
                    	continue;
                    }
                    if(!"no".equalsIgnoreCase(needInsert)){
                        Object value =  fieldDefine.getField().get(request);
                        insertsMaps.put(fname,value); //获取字段值,优化后处理
                    }
                }
                RequestBeanDefinition<ZteBusiRequest> serviceDefinition =BusiUtils.getRequestServiceDefinition(request);
                insertBodyList.add(insertByMap(serviceDefinition.getTable_name(),insertsMaps));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonTools.addFailError("RequestStoreUtils.dbstore出错"+e.getLocalizedMessage());
        }
    }
    
    //不考虑大字段
    private InsertBody insertByMap(String table, Map<String, Object> fields) { //
        if (fields.isEmpty() || fields.size() <= 0) {
            return null;
        }
        String sql = "";
        if (fields.containsKey("source_from")) {
            fields.put("source_from", ManagerUtils.getSourceFrom());
        }

        Object[] cols = fields.keySet().toArray();
        int length = cols.length;
        List values = new ArrayList();
        StringBuffer colsBuffer = new StringBuffer();
        StringBuffer valuesBuffer = new StringBuffer();

        int j = 0;
        for (int i = 0; i < length; i++) {
            String key_cols = (String) cols[i];
            Object value = fields.get(key_cols);
            if (null != value) {
                if (value instanceof String) {
                    String p_value = (String) value;
                    if (ManagerUtils.timeMatch(p_value)) {
                        if (p_value.length() == 10) {
                            p_value = value + " 00:00:00";
                        }
                        value = DateFormatUtils.formatStringToDate(p_value);
                    }
                }
                colsBuffer.append(key_cols).append(",");
                if (Consts.SYSDATE.equals(value)) {
                    valuesBuffer.append(DBTUtil.current()).append(",");
                } else {
                    values.add(value);
                    valuesBuffer.append("?").append(",");
                }
            }

        }
        sql = "INSERT INTO " + table + " (" + colsBuffer.substring(0, colsBuffer.length() - 1) + ") VALUES ("
                + valuesBuffer.substring(0, valuesBuffer.length() - 1) ;
        String regCheck = ".*(?i)\\bsource_from\\b.*";
        
        if(!sql.matches(regCheck)){
            sql = sql.replace(")", ", source_from)");
            sql += ",?";
            values.add(ManagerUtils.getSourceFrom()) ;
        }
        sql = sql + ")";
        // dbRouterPublic.getJdbcTemplate(sql, jdbcTemplate).update(sql, values.toArray());
        InsertBody insertBody = new InsertBody();
        insertBody.setSql(sql);
        insertBody.setValues(values.toArray());
        return insertBody;
    }
    
    public class InsertBody {
        private String sql;
        private Object[] values;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Object[] getValues() {
            return values;
        }

        public void setValues(Object[] values) {
            this.values = values;
        }
    }
    
    public static void main(String[] args) {
        OrderTreeBusiRequest orderTreeBusiRequest = CommonData.getData().getOrderTreeBusiRequest();
        orderTreeBusiRequest.setDb_action(ConstsCore.DB_ACTION_INSERT);
        orderTreeBusiRequest.setIs_dirty(ConstsCore.IS_DIRTY_YES);
        orderTreeBusiRequest.setCreate_time(Consts.SYSDATE);
        orderTreeBusiRequest.setUpdate_time(Consts.SYSDATE);
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(orderTreeBusiRequest);
        try {
            insertObject(objectList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void insertObject(List<?> objectList) throws IllegalAccessException {
        /**
         * 比如：
         * SQL 语句：insert into person(name, age, info) values ('...', '...', '...');
         * 其中 values 改为占位符：
         * SQL 语句：insert into person(name, age, info) values (?, ?, ?);
         * 再获取所有的数据值 Object 数组
         */
        // 确定占位符的个数（即对象中不为 null 的字段个数）
        int columnNum = 0;
        // 插入数据的列名
        StringBuilder columns = new StringBuilder("(");
        // 填充占位符的值（即对象中不为null的字段的值）
        List<Object> valuesList = new ArrayList<Object>();
        // 如果为空则不执行
        if (objectList==null || objectList.isEmpty()) {
            return;
        }
        // 通过 List 中的第一个 Object，确定插入对象的字段
        Object object = objectList.get(0);
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(object) != null) {
                columnNum++;
                columns.append(field.getName()).append(", ");
                valuesList.add(field.get(object));
            }
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");

        // 获取所有的值
        for (int i = 1; i < objectList.size(); i++) {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.get(objectList.get(i)) != null) {
                    valuesList.add(field.get(objectList.get(i)));
                }
            }
        }
        // 确定一个 Object 的占位符 '?'
        StringBuilder zhanweifuColumn = new StringBuilder("(");
        for (int i = 0; i < columnNum; i++) {
            zhanweifuColumn.append("?, ");
        }
        zhanweifuColumn.replace(zhanweifuColumn.lastIndexOf(", "), zhanweifuColumn.length(), ")");

        // 确定所有的占位符
        int objectNum = objectList.size();
        StringBuilder zhanweifu = new StringBuilder();
        for (int j = 0; j < objectNum; j++) {
            zhanweifu.append(zhanweifuColumn.toString()).append(", ");
        }
        zhanweifu.replace(zhanweifu.lastIndexOf(", "), zhanweifu.length(), "");

        // 生成最终 SQL
        String sql = "INSERT INTO " + object.getClass().getSimpleName().toLowerCase() + " " + columns + " VALUES " + zhanweifu.toString();
        logger.info(sql);
        logger.info(valuesList);
    }

	@Override
	public void updateNoCache() throws Exception {
		this.update(this.getUpdateList());
	}
	
	private void update(List<InsertBody> insertBodys) throws Exception {
        DataSource dataSource = SpringContextHolder.getBean("dataSource");
        Connection conn = null;
        List<PreparedStatement> preparedStatements = new ArrayList<PreparedStatement>();
        
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            
            for (InsertBody insertBody : insertBodys) {
            	logger.error("CommonDataDao update sql:"+insertBody.getSql());
            	
                PreparedStatement pstmt = conn.prepareStatement(insertBody.getSql());
                Object[] values = insertBody.getValues();
                
                StringBuilder params = new StringBuilder();
                
                for (int i = 0; i < values.length; i++) {
                    Object value = values[i];
                    
                    if(params.length()==0)
                    	params.append(value);
                    else
                    	params.append(",").append(value);
                    
                    if (value instanceof java.sql.Date) {
                        pstmt.setDate(i + 1, (java.sql.Date)value);
                    } else if (value instanceof java.sql.Timestamp) {
                        pstmt.setTimestamp(i + 1, (java.sql.Timestamp)value);
                    } else if (value instanceof java.util.Date) {
                        Date date = (Date)value;
                        pstmt.setTimestamp(i + 1, new Timestamp(date.getTime()));
                    } else {
                        pstmt.setObject(i + 1, value);
                    }
                }
                
                logger.error("CommonDataDao update params:"+params.toString());
                
                pstmt.executeUpdate();
                preparedStatements.add(pstmt);
            }
            
            conn.commit();
            
        } catch (Exception e) {
        	logger.error("CommonDataDao update error:"+e.getMessage(),e);
        	
            try {
                conn.rollback();
            } catch (SQLException e1) {
            	logger.error("CommonDataDao update rollback error:"+e1.getMessage(),e1);
            }
            
            throw e;
        } finally {
            for (PreparedStatement preparedStatement : preparedStatements) {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                    	logger.error("CommonDataDao update preparedStatement close error:"+e.getMessage(),e);
                    }
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                	logger.error("CommonDataDao update connect close error:"+e.getMessage(),e);
                }
            }
        }
    }
	
	private List<InsertBody> getUpdateList() {
        CommonData commonData = CommonData.getData();
        List<InsertBody> insertBodies = new ArrayList<InsertBody>();
        
        //获取订单树更新Sql
        this.getUpdateBody(insertBodies,commonData.getOrderTreeBusiRequest());
        
        //获取member更新SQL
        this.getUpdateBody(insertBodies, commonData.getMemberBusiRequest());
        
        
        //获取Delivery更新SQL
        for (OrderDeliveryItemBusiRequest orderDeliveryItemBusiRequest : commonData.getOrderDeliveryItemBusiRequests()) {
        	this.getUpdateBody(insertBodies,orderDeliveryItemBusiRequest);
        }
        
        //获取OrderExtvl更新SQL
        this.getUpdateBody(insertBodies,commonData.getOrderExtvlBusiRequest());
        
        return insertBodies;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getUpdateBody(List<InsertBody> insertBodyList,ZteBusiRequest request) {
        //获取订单字段及字段数据
        try {
        	//获取字段定义
            List<RequestFieldDefinition> fieldDefinitions = BusiUtils.getRequestFieldsDefinition(request);
            
            //获取表定义
            RequestBeanDefinition<ZteBusiRequest>  requestBean = BusiUtils.getRequestServiceDefinition(request);
            
            //判断逐渐是否有值，无值时不插入
            String key = requestBean.getPrimary_keys();
            
            String method_name = "get"+key.substring(0,1).toUpperCase()+key.substring(1);
            Object obj = null;
            
            try{
            	obj = MethodUtils.invokeMethod(request, method_name, null);
            }catch(Exception e){
            	if(e instanceof NoSuchMethodException){
            		key = key.toLowerCase();
            		
            		method_name = "get"+key.substring(0,1).toUpperCase()+key.substring(1);
            		
            		obj = MethodUtils.invokeMethod(request, method_name, null);
            	}
            }
            
            if(obj==null||"".equals(obj)){
            	return ;
            }

            if(null!=fieldDefinitions && fieldDefinitions.size()>0){
                //字段封装
                HashMap<String, Object> insertsMaps = new HashMap<String, Object>();
                
                for (int i = 0; i < fieldDefinitions.size(); i++) {
                	
                    RequestFieldDefinition  fieldDefine = fieldDefinitions.get(i);
                    
                    String type = fieldDefine.getField().getType().getName(); //字段类型
                    String fname = fieldDefine.getDname();
                    String needInsert = fieldDefine.getNeed_insert();
                    
                    if(type.indexOf("com.") > -1 || type.indexOf("zte.") > -1 || type.indexOf("service.") > -1) {
                    	//如果属性是继承自ZteBusiRequest的类
                    	ZteBusiRequest zteBusiRequest = (ZteBusiRequest) fieldDefine.getField().get(request);
                    	this.getUpdateBody(insertBodyList,zteBusiRequest);
                    }else if("java.util.List".equals(type)) {
                    	//如果属性是是List，当做ZteBusiRequest的List处理
                    	List<ZteBusiRequest> list =  (List<ZteBusiRequest>)fieldDefine.getField().get(request);
                    	if(list!=null&&list.size()>0){
                    		for (ZteBusiRequest zteBusiRequest : list) {
                    			this.getUpdateBody(insertBodyList,zteBusiRequest);
							}
                    	}
                    }else if(!"no".equalsIgnoreCase(needInsert)){
                    	//其它类型的属性
                        Object value =  fieldDefine.getField().get(request);
                        insertsMaps.put(fname,value); //获取字段值,优化后处理
                    }
                }
                
                insertBodyList.add(this.buildUpdateBody(requestBean.getTable_name(),insertsMaps,key));
            }
        } catch (Exception e) {
            e.printStackTrace();
            CommonTools.addFailError("RequestStoreUtils.dbstore出错"+e.getLocalizedMessage());
        }
    }
	
	/**
	 * 根据表名判断是否是需要插入的表
	 * @param table
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean isNeedInsert(String table) throws Exception{
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String cfg = cacheUtil.getConfigInfo("ORDER_UPDATE_RE_INSERT_TABLE");
		
		if(StringUtils.isNotBlank(cfg)){
			String[] arr = cfg.split(",");
			
			List<String> table_list = Arrays.asList(arr);
			Set<String> table_set = new HashSet<String>(table_list);
			
			if(table_set.contains(table))
				return true;
		}
		
		return false;
	}
	
	private InsertBody buildUpdateBody(String table, Map<String, Object> fields,String primary_key) throws Exception {
		if (fields.isEmpty() || fields.size() <= 0) {
            return null;
        }
		
		boolean is_need_insert = this.isNeedInsert(table);
		
		if(is_need_insert){
			//调用生成插入SQL的方法
			return this.insertByMap(table, fields);
		}
		
		if(StringUtils.isBlank(primary_key))
			throw new Exception("更新表"+table+"需要主键");
		
		if(!fields.containsKey(primary_key))
			throw new Exception("更新表"+table+"传入字段中不包含主键"+primary_key);
		
		if(fields.get(primary_key)==null)
			throw new Exception("更新表"+table+"主键"+primary_key+"值为空");
		
		//拼装SQL
		StringBuilder builder = new StringBuilder();
		
		builder.append(" update ").append(table).append(" set ");
		
		Iterator<Entry<String, Object>> it = fields.entrySet().iterator();
		int count = 0;
		
		List<Object> params_list = new ArrayList<Object>();
		
		for(;it.hasNext();){
			Entry<String, Object> entry = it.next();
			String col_name = entry.getKey();
			Object value = entry.getValue();
			
			if(null != value){
				if(count != 0)
					builder.append(",");
				
				builder.append(col_name).append("=? ");
				
				if (value instanceof String) {
                    String p_value = (String) value;
                    if (ManagerUtils.timeMatch(p_value)) {
                        if (p_value.length() == 10) {
                            p_value = value + " 00:00:00";
                        }
                        value = DateFormatUtils.formatStringToDate(p_value);
                    }
                }
                
                if (Consts.SYSDATE.equals(value)) {
                	value = DBTUtil.current();
                }
                
                params_list.add(value);
				
				count++;
			}
		}
		
		if(count == 0)
			return null;
		
		//更新条件
		builder.append(" where ").append(primary_key).append("=? ");
		
		Object primary_value = fields.get(primary_key);
		if(primary_value instanceof String){
			String str = (String)primary_value;
			
			if(StringUtils.isBlank(str))
				throw new Exception("更新表"+table+"主键"+primary_key+"的字符串为空");
		}
		
		params_list.add(primary_value);
		
        InsertBody insertBody = new InsertBody();
        insertBody.setSql(builder.toString());
        insertBody.setValues(params_list.toArray());
        
        return insertBody;
    }
}
