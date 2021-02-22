package com.ztesoft.autoprocess.base.exception;

/**
 * 系统异常类
 * @author tanghaoyang
 *
 */
public class SystemException extends Exception{
	private static final long serialVersionUID = 1L;
    private Short type;
    private String msg;

	public SystemException(Exception e) {
	    super(e);
	}
    
    public SystemException(String msg) {
        super(msg);
        this.msg = msg;
    }
	
	public SystemException(Short type, String msg) {
	    super(msg);
        this.type = type;
        this.msg = msg;
	}

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
