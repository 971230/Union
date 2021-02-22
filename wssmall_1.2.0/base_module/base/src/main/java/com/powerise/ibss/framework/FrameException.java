//Source file: D:\\WLSApp\\Eclipse\\workspace\\ibss\\src\\com\\powerise\\ibss\\framework\\FrameException.java

package com.powerise.ibss.framework;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 业务系统自行定义的意外。
 */
public class FrameException extends Exception {
	int m_ErrorCode = 0;

	String m_ErrorMsg = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
	    
		return  m_ErrorMsg;
	}

	String m_SysMsg = null;

	static private Logger m_Logger = null;

	private static void getLogger() {
		if (m_Logger == null)
			m_Logger = Logger
					.getLogger("com.powerise.ibss.framework.FrameException");

	}

	/**
	 * @since 2002
	 */
	public FrameException() {

	}

	/**
	 * @param ErrorCode
	 * @param ErrorMsg
	 * @param SysMsg
	 * @since 2002
	 */
	public FrameException(int ErrorCode, String ErrorMsg, String SysMsg) {
		getLogger();
		m_ErrorCode = ErrorCode;
		m_ErrorMsg = ErrorMsg;
		m_SysMsg = SysMsg;

		m_Logger.debug("[错误代码:" + m_ErrorCode + "][错误信息:" + m_ErrorMsg
				+ "][系统错误:" + m_SysMsg + "]");
		m_Logger.debug(getStackInfo());
	}

	/**
	 * @param ErrorMsg
	 * @since 2002
	 */
	public FrameException(String ErrorMsg) {
		getLogger();
		m_ErrorMsg = ErrorMsg;
		m_Logger.debug("[错误信息:" + m_ErrorMsg + "]");
		m_Logger.debug(getStackInfo());

	}

	/**
	 * @param ErrorCode
	 * @param ErrorMsg
	 * @since 2002
	 */
	public FrameException(int ErrorCode, String ErrorMsg) {
		getLogger();
		m_ErrorCode = ErrorCode;
		m_ErrorMsg = ErrorMsg;
		m_Logger.debug("[错误代码:" + m_ErrorCode + "][错误信息:" + m_ErrorMsg + "]");
		m_Logger.debug(getStackInfo());

	}

	@Override
	public void printStackTrace() {
		//logger.info(" 错误代码 :" + m_ErrorCode);
		m_Logger.info(" 错误代码 :" + m_ErrorCode);
		//logger.info(" 错误信息 :" + m_ErrorMsg);
		m_Logger.info(" 错误信息 :" + m_ErrorMsg);
		if (m_SysMsg != null) m_Logger.info(" 系统错误 :" + m_SysMsg);
			//logger.info(" 系统错误 :" + m_SysMsg);
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		s.println(" 错误代码 :" + m_ErrorCode);
		s.println(" 错误信息 :" + m_ErrorMsg);
		if (m_SysMsg != null)
			s.println(" 系统错误 :" + m_SysMsg);
		super.printStackTrace(s);
	}

	public int getErrorCode() {
		return m_ErrorCode;
	}

	public String getErrorMsg() {
		return m_ErrorMsg;
	}

	public String getSysMsg() {
		return m_SysMsg;
	}

	private String getStackInfo() {
		StringWriter sw = null;
		PrintWriter pw = null;
		String strMsg = "";

		sw = new StringWriter();
		pw = new PrintWriter(sw);
		try {
			this.printStackTrace(pw);
			sw.flush();
			strMsg = sw.toString();
			sw.close();
			pw.close();
		} catch (java.io.IOException le) {
			strMsg = "待定处理";
		}
		return strMsg;
	}

    public FrameException(int ErrorCode, String ErrorMsg,Throwable cause) {
        //super(cause);
        super.setStackTrace(cause.getStackTrace());
        getLogger();
        m_ErrorCode = ErrorCode;
        m_ErrorMsg = ErrorMsg;
        m_Logger.debug("[错误代码:" + m_ErrorCode + "][错误信息:" + m_ErrorMsg + "]");
        m_Logger.debug(getStackInfo());
    }

}
