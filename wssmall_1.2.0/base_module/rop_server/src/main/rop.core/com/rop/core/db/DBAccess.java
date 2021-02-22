package com.rop.core.db;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

public class DBAccess {
    private DBAccess(){

    }
    /**
     * DB保存操作
     * 1.入参
     * @param sql
     * @param paramsObj
     * @return
     */
    public static int save(String sql , Object paramsObj,Connection conn){
        Connection dbConnection = null;
        NamedParameterStatement stmt = null;
        try {
            dbConnection = conn;
            stmt = new NamedParameterStatement(dbConnection ,sql);

            Map paramMap = null ;
            if(paramsObj instanceof Map ){//Map直接映射处理
                paramMap = (Map) paramsObj ;
            }else if(null != paramsObj){//pojo,转成map后映射处理
                paramMap = BeanUtils.describe(paramsObj);

            }

            setStatement( stmt ,  paramMap) ;
            return stmt.executeUpdate() ;
        } catch (Exception se) {
            throw new DAOSystemException("SQLException while execSQL:" + sql + "\n", se);
        } finally {
            closeStatement(stmt);
        }
    }

    public static void closeStatement(NamedParameterStatement stmt) throws DAOSystemException {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException se) {
            throw new DAOSystemException("SQL Exception while closing " + "Statement : \n" + se);
        }
    }



    /**
     * 设置参数
     * @param stmt
     * @param paramMap
     * @throws java.sql.SQLException
     */
    public static void setStatement(NamedParameterStatement stmt , Map paramMap) throws SQLException{
        if(paramMap != null && !paramMap.isEmpty()){
            String key = null ;
            Object value = null ;
            for (Iterator it = stmt.indexMap.keySet().iterator() ; it.hasNext() ;) {
                key = (String)it.next() ;
                value = paramMap.get(key) ;
                if(value == null)
                    value="";
                if(value != null
                        && "java.lang.String".equals(value.getClass().getName()) ){
                    stmt.setString(key, (String)value) ;
                }else if (isDateType(value)) {
                    if(value instanceof java.sql.Date)
                        //stmt.setDate(key, (java.sql.Date)value) ;
                        stmt.setTimestamp(key, new Timestamp( ((java.sql.Date)value) .getTime()) ) ;
                    else
                        stmt.setTimestamp(key, new Timestamp( ((java.util.Date)value) .getTime()) ) ;
                    //stmt.setDate(key, new java.sql.Date(((java.util.Date) value).getTime() )) ;
                } else if (isTimestampType(value)) {
                    stmt.setTimestamp(key,(Timestamp) value) ;
                } else {
                    stmt.setString(key, (String)value) ;
                }
            }
        }
    }

    private static final boolean isDateType(Object o) {
        return o != null
                && ("java.util.Date".equals(o.getClass().getName()) || "java.sql.Date"
                .equals(o.getClass().getName()));
    }

    private static final boolean isTimestampType(Object o) {
        return o != null && "java.sql.Timestamp".equals(o.getClass().getName());
    }


}
