/*
 * Created on 2003-5-3 错误号段 229901xx
 */
package com.powerise.ibss.framework.servlet;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.Base64;
import com.powerise.ibss.util.SysSet;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class BackAgentServlet extends HttpServlet implements SingleThreadModel {

	private static final long	serialVersionUID	= -3053019932266188960L;

	private static Logger		m_Logger			= Logger.getLogger(BackAgentServlet.class);

	private static String		CONTENT_TYPE		= "text/xml;charset=GBK";

	private PrintWriter			m_cliWriter			= null;

	private PrintWriter			m_fileWriter		= null;

	private String				m_Msg				= null;

	private String				m_Data				= null;

	private int					m_RetCode			= 0;

	private String				m_RetMsg			= " ";

	private String				m_SysMsg			= " ";

	private int					m_Calc				= 0;

	private DynamicDict			m_Dict				= null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pr = response.getWriter();
		pr.println("you call me using get method successfull.");
		pr.flush();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		m_cliWriter = null;
		m_fileWriter = null;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("pragma", "no-cache");
		m_cliWriter = response.getWriter();
		try
		{
			Process(request);

		}
		catch (FrameException e)
		{
			if (m_Dict == null)
				m_Dict = new DynamicDict();
			m_Dict.exception = e.getSysMsg();
			m_Dict.flag = e.getErrorCode();
			m_Dict.msg = e.getErrorMsg();
		}
		m_cliWriter.print(Base64.encodeObject(m_Dict));
		m_Dict.destroy();
		m_Dict = null;
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		if (m_Logger == null)
		{
			m_Logger = Logger.getLogger(getClass().getName());
		}
		super.init();
	}

	// 处理业务，封装成方法主要是为了错误的处理，通过例外，比较方便
	public void Process(HttpServletRequest request) throws FrameException {
		String strtmp = null;
		StringBuffer strBuf;
		String strClassName = null;
		Document doc = null;
		BufferedReader curRead = null;

		// 接受客户端传来的XML数据
		m_Logger.debug("开始接收客户端数据");
		strBuf = new StringBuffer();
		try
		{
			// request.setCharacterEncoding("GBK");
			curRead = request.getReader();
			strtmp = curRead.readLine();
			while (strtmp != null)
			{
				strBuf.append(strtmp);
				strtmp = curRead.readLine();
			}
		}
		catch (IOException ioe)
		{
			m_Logger.info("读取客户端的数据截取到IOException:" + ioe.getClass().getName());
			m_Logger.info(SysSet.getStackMsg(ioe));
			m_Logger.info("当前读取的数据:" + strBuf.toString());
			throw new FrameException(-22994000, "读取客户端数据IO异常");
		}

		m_Data = strBuf.toString();
//		m_Logger.debug("客户端传来的数据:" + m_Data);
		if (m_Data == null)
		{
			throw new FrameException(-22994002, "客户端传来的参数为空");
		}
		try
		{
			Object o = Base64.decodeToObject(m_Data);
			if (o != null)
			{
				m_Dict = (DynamicDict) o;
				// m_Logger.debug("gen dict :" + m_Dict.toString());
				m_Dict = (DynamicDict) o;
			}
			else
				throw new FrameException(-22994003, "传入的对象为空");

		}
		catch (Exception be)
		{
			throw new FrameException(-22994004, "转换对象出现异常：" + be.getMessage(), SysSet.getStackMsg(be));
		}

		try
		{
			m_Dict = ActionDispatch.dispatch(m_Dict);
			processDict();
		}
		catch (java.lang.Throwable e)
		{
			e.printStackTrace();
			m_Dict.flag = -5010;
			m_Dict.msg = "系统调用出错:" + e.getMessage();
			m_Dict.exception = SysSet.getStackMsg(e);

		}
		finally
		{
			m_Data = null;
		}
	}

	private void processDict() throws FrameException {
		HashMap hmValue = null, hmData = null;
		Iterator it = null;
		Object obj = null;
		String strParam = null,strName = null, strValue = null;
		ArrayList al = null;
		ResultSet rsobj = null;
		
		hmValue = m_Dict.m_Values;
		it = hmValue.keySet().iterator();
		while (it.hasNext())
		{
			strParam = (String) it.next();
			obj = hmValue.get(strParam);
			if (obj.getClass().getName().indexOf("ResultSet") != -1) // 如果是ResultSet需要进行转换，转换成ArrayList
			{
				al = new ArrayList();
				rsobj = (ResultSet) obj;
				try
				{
					ResultSetMetaData v_Fld = rsobj.getMetaData(); // 字段定义
					int iFldCnt = v_Fld.getColumnCount(); // 字段数
					int j = 0;

					while ((rsobj.next()) && (j < 10000))
					{
						hmData = new HashMap();
						for (int i = 1; i <= iFldCnt; i++)
						{
							strValue = rsobj.getString(i);
							if (rsobj.wasNull() == false)
							{
								strName = v_Fld.getColumnName(i);
								hmData.put(strName, strValue);
							}
						}
						j++;
					}
				}
				catch (SQLException se)
				{
					throw new FrameException(-22994005, "转换数据出错:"
							+ se.getMessage(), SysSet.getStackMsg(se));
				}
				finally
				{
					if (rsobj != null)
					{
						try
						{
							Statement stTmp = rsobj.getStatement();
							rsobj.close();
							stTmp.close();
						}
						catch (SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
				if(al !=null)
				{					
					m_Dict.m_Values.put(strParam,al);
				}
			}
		}
	}
	
}
