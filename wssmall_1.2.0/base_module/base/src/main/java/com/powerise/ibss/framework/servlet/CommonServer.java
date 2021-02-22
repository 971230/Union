package com.powerise.ibss.framework.servlet;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.system.SessionOBJ;
import com.powerise.ibss.util.SysSet;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;

public class CommonServer extends HttpServlet implements SingleThreadModel {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2621454636139039889L;

    private static String CONTENT_TYPE = "text/xml;charset=GBK";

    private PrintWriter m_cliWriter = null;

    private PrintWriter m_fileWriter = null;

    private String m_Msg = null;

    private String m_Data = null;



    private static Logger m_Logger = Logger.getLogger(CommonServer.class);

    /**
     *  
     */
    public CommonServer() {
        super();
        if (m_Logger == null) {
            m_Logger = Logger.getLogger(getClass().getName());
        }
        //TODO Auto-generated constructor stub
    }

    /**
     * 实现doGet方法，用来测试Servlet是否正确配置
     */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        m_cliWriter = response.getWriter();
        m_cliWriter.println("You called me using get method.");
    }

    private void ReadArgs(HttpServletRequest request) throws FrameException {
        BufferedReader curRead = null;
        StringBuffer strBuf = null;
        String strtmp;

        //		try {
        //			byte[] cBuffer = new byte[10240];
        //			int iLen;
        //			strBuf = new StringBuffer();
        //			ServletInputStream curInput = request.getInputStream();
        //			iLen = 0;
        //			while (iLen != -1) {
        //				iLen = curInput.read(cBuffer);
        //				if (iLen != -1) {
        //					strBuf.append(new String(cBuffer, 0, iLen));
        //				}
        //			}
        //		} catch (IOException e) {
        //			e.printStackTrace();
        //			throw new FrameException(-22994001, "读取客户端的数据出错", e.getMessage());
        //		}
        //		m_Msg = strBuf.toString();
        //		m_Logger.debug("客户端送来:"+m_Msg);
        //		if (m_Msg == null)
        //			throw new FrameException(-22994002, "客户端传来的参数为空");

        strBuf = new StringBuffer();
        try {

            curRead = request.getReader();
            strtmp = curRead.readLine();

            while (strtmp != null) {
                strBuf.append(strtmp);
                strtmp = curRead.readLine();
            }
        } catch (IOException e) {
            m_Logger.info("读取客户端的数据截取到IOException:" + e.getClass().getName());
            m_Logger.info("当前读取的数据:" + strBuf.toString());
            throw new FrameException(-900002, "读取客户端的数据出错", SysSet.getStackMsg(e));
        }

        m_Data = strBuf.toString();
        strBuf = null;
        if (m_Data == null) {
            throw new FrameException(-900005, "客户端传来的参数为空");
        } else
            m_Msg = m_Data;
        m_Data = null;
    }

    /**
     * 处理客户端通过Post传递过来的数据，分析验证的类型
     */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String strType;
        int iRetCode = 0;
        Connection conn = null;
        long lErrSeq = 0;
        String strSysName = null;
        boolean commit = false;
       
        boolean bSingle = false;
        UserTransaction usTran = null;
        Context usContext = null;
        String strId = null;
        boolean bXAType = false; //缺省不采用XA方式,对于通用查询来说,绝大部分都是Query操作，而且不方便判断获取action_id，因此直接就是使用不采用XA
        response.setContentType(CONTENT_TYPE);
        response.setHeader("pragma", "no-cache");

     //   request.setCharacterEncoding("GBK");

        //接受客户端传来的XML数据
        m_Logger.debug("开始接收通用查询的客户端数据");
     
        m_cliWriter = response.getWriter();
        try {
            ReadArgs(request);
            m_Logger.debug("客户端传入的数据：\n");
            m_Logger.debug(m_Msg);
            HashMap staff_info = null;
            HttpSession session = request.getSession();
            if (session != null) {
                if (session.getAttribute("user") != null) {
                    SessionOBJ user = (SessionOBJ) session.getAttribute("user");
                    staff_info = user.getStaffInfo();
                    if(staff_info == null)
                    	m_Logger.warn("获取到Session,但是Session中的值为空");
                }else{
                	m_Logger.warn("没有获取到Session,非法登录!");
                }
            }
               m_Msg = ActionDispatch.performServletDynamicAction(m_Msg, staff_info);

        } catch (FrameException e) {
            m_Msg = SysSet.doError(e.getErrorCode(), e.getErrorMsg(), e.getSysMsg(), 2);
        }
        m_Logger.debug("返回的数据:");
        m_Logger.debug(m_Msg);
        m_cliWriter.print(m_Msg);
        m_Msg = null;
    }
}
