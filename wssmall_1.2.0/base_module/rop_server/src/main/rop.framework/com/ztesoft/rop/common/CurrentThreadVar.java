package com.ztesoft.rop.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ztesoft.net.framework.util.StringUtil;

import java.util.Date;
import java.util.Map;

/**
 * 
 * 线程变量，存储当前处理线程常用数据，包括应用数据、企业数据、账号数据、权限数据等信息
 * @author easonwu 2013-10-17
 *
 */
public class CurrentThreadVar {
    private Logger logger= LoggerFactory.getLogger(getClass());
	
	private static ThreadLocal<CurrentThreadVar> MyCurrentThreadVar = new ThreadLocal<CurrentThreadVar>() ; 
	private CurrentThreadVar(){
		
	}
	
	/**
	 * 获取当前线程变量对象
	 * @return
	 */
	public static final CurrentThreadVar getVar(){
//		logger.info(Thread.currentThread().getId()+"===============================");
		CurrentThreadVar var = MyCurrentThreadVar.get() ;
		if(var == null ){
			var = new CurrentThreadVar() ;
			MyCurrentThreadVar.set(var) ;
		}	
		return var ;
	}
	
	public void setExceptionStat(String expMsg ){
		this.failure = 1 ;
		this.exceptionMsg = expMsg ;
	}
	
	public void setSuccessStat(){
		this.success = 1 ;
	}
	
	public static void setParentLocalVar(CurrentThreadVar var ){
		MyCurrentThreadVar.set(var) ;
	}
	
	/**
	 * 
	 * 清空操作
	 * 
	 */
	public static final void clear(){
		CurrentThreadVar var = MyCurrentThreadVar.get() ;
		if(var != null )
			var = null ;
		MyCurrentThreadVar.remove() ;
	}
	
	public static final void remove(){
//		logger.info(Thread.currentThread().getId()+"=============remove");
		MyCurrentThreadVar.remove() ;
	}
	
	/*-----------------应用相关信息-------------------*/
	private String appKey ;//应用编码
	private String appName ;//应用名称
	private String appSecret ;//应用密钥
	private String appLevel ;//应用级别
	private int validityPeriod ;//应用密钥有效期
	private int appId ;//应用标识
    private String sourceFrom;//来源

	/*----------------账号相关信息----------------*/
	private String acctName ; //账号名称
	private String acctCode ;//账号编码
	private String acctType ;//账号类型
	private String ownerName ;//属主名称
	private String ownerCode ;//属主编码
	private int ownerId ;//属主标识
	

	/*----------------服务相关信息----------------*/
	private int funId ;//服务标识
	private String funCode ; //服务标识
	private String funName ;//服务名称
	private String funVersion ; //服务编码

	private Date reqDate ;

	private int failure = 0 ;
	private int success = 0 ;
	private int elapsed = 0 ;
	
	private String exceptionMsg ;


	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getElapsed() {
		return elapsed;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public Date getReqDate() {
		return reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

	public void setByMap(Map<String,String> data){
		try{
            this.appId = Integer.parseInt( data.get("app_id") );
            this.appKey = data.get("app_key") ;
            this.appName = data.get("app_name") ;
            this.appSecret = data.get("app_secret") ;
            this.validityPeriod = Integer.parseInt(data.get("validity_period") );
            this.appLevel = data.get("app_level") ;
            this.ownerCode = data.get("owner_code") ;
            this.ownerName = data.get("owner_name") ;
            this.ownerId = Integer.parseInt(data.get("owner_id") ) ;
            this.acctName = data.get("acct_name") ;
            this.acctCode = data.get("acct_code") ;
            this.acctType = data.get("acct_type") ;
            if(StringUtil.isEmpty(data.get("fun_id"))) //add by wui
            	data.put("fun_id", "0");
            this.funId =  Integer.parseInt(data.get("fun_id") );
            
            this.funCode = data.get("fun_code") ;
            this.funName = data.get("fun_name") ;
            this.funVersion = data.get("fun_version");
            this.sourceFrom=data.get("source_from");
            
            this.reqDate = new Date(); //wui 设置请求时间
            
        }catch (RuntimeException e){
           logger.debug("初始化线程变量出错!");
           throw e;
        }
	}

	
	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getFunId() {
		return funId;
	}

	public void setFunId(int funId) {
		this.funId = funId;
	}

	public String getFunCode() {
		return funCode;
	}

	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getFunVersion() {
		return funVersion;
	}

	public void setFunVersion(String funVersion) {
		this.funVersion = funVersion;
	}


	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAppLevel() {
		return appLevel;
	}
	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}
	public int getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(int validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

    public String getSourceFrom() {
        return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom;
    }
}
