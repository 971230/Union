package com.rop.core.db;


/**
 * DAOSystemException is thrown by a DAO component when there is
 * some irrecoverable error (like SQLException)
 */
public class DAOSystemException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DAOSystemException() {}
    public DAOSystemException(String msg) { super(msg); }
    public DAOSystemException(String msg, Throwable cause) {
        super(msg+"\n  nest exception:"+cause.getMessage(), cause);
    }
    public DAOSystemException(Throwable cause) {
        super(cause);
    }
}

