package com.powerise.ibss.framework.servlet;

import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.XMLTool;
import com.powerise.ibss.system.LogonAction;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

public class SysSecurity extends HttpServlet 
    implements SingleThreadModel
{

    private static String CONTENT_TYPE = "text/xml";
    private XMLTool m_XMLDoc;
    private PrintWriter m_cliWriter;
    private PrintWriter m_fileWriter;
    private String m_Msg;
    private String m_Data;
    private static Logger logger = Logger.getLogger(SysSecurity.class);
    public SysSecurity()
    {
        m_cliWriter = null;
        m_fileWriter = null;
        m_Msg = null;
        m_Data = null;
    }

    private void Login(HttpServletRequest request)
        throws FrameException
    {
    	logger.info("Login Called");
       // logger.info("Login Called");
    }

    private void Logout()
        throws FrameException
    {
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        m_cliWriter = response.getWriter();
        m_cliWriter.println("You called me using get method. haha");
    }

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        LogonAction lAction = new LogonAction();
        response.setContentType(CONTENT_TYPE);
        response.setHeader("pragma", "no-cache");
        m_cliWriter = response.getWriter();
        String strStaffNo = request.getParameter("staff_no");
        String strPwd = request.getParameter("password");
        if(strStaffNo == null || strPwd == null)
            m_Msg = "-1";
        else
        if(lAction.Logon(strStaffNo, strPwd, "", request) >= 0)
            m_Msg = "1";
        else
            m_Msg = "-1";
        m_cliWriter.println(m_Msg);
    }

}
