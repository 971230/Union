package com.ztesoft.ibss.common.dao;


/**
 * DAOSystemException is thrown by a DAO component when there is
 * some irrecoverable error (like SQLException)
 */
public class DAOSystemExceptionBak extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DAOSystemExceptionBak() {}
    public DAOSystemExceptionBak(String msg) { super(msg); }
    public DAOSystemExceptionBak(String msg, Throwable cause) {
        super(msg+"\n  nest exception:"+cause.getMessage(), cause);
    }
    public DAOSystemExceptionBak(Throwable cause) {
        super(cause);
    }
}
