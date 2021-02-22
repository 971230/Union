package com.rop.core.database.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库操作运行期异常
 * @author kingapex
 * 2010-1-6下午06:16:47
 */
public class DBRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -368646349014580765L;

    private static final Logger loger = LoggerFactory.getLogger(DBRuntimeException.class);


    public DBRuntimeException(String message){
        super(message);
    }
    public DBRuntimeException(Exception e, String sql) {
        super("数据库运行期异常");
        e.printStackTrace();
        if(loger.isErrorEnabled()){
            loger.info("数据库运行期异常，相关sql语句为{}",sql);
        }
    }


    public DBRuntimeException(String message, String sql) {
        super(message);
        if(loger.isErrorEnabled()){
            loger.info("{}相关sql语句为",new Object[]{message,sql});
        }
    }


}
