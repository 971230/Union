/*
 * Created on 2003-5-3 错误号段 229901xx
 */
package com.powerise.ibss.framework.servlet;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.system.SessionOBJ;
import com.powerise.ibss.util.SysSet;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
public class AgentServlet extends HttpServlet implements SingleThreadModel {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2360412509912623114L;
    private static Logger m_Logger = Logger.getLogger(AgentServlet.class);
    private static String CONTENT_TYPE = "text/xml;charset=GBK";
    private PrintWriter m_cliWriter = null;
    private PrintWriter m_fileWriter = null;
    private String m_Msg = null;
    private String m_Data = null;
    private int m_RetCode = 0;
    private String m_RetMsg = " ";
    private String m_SysMsg = " ";
    private int m_Calc = 0;
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        m_cliWriter = response.getWriter();
        doPost(request, response); 
    }
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        m_cliWriter = null;
        m_fileWriter = null;
        response.setContentType(CONTENT_TYPE);
        response.setHeader("pragma", "no-cache");
        m_cliWriter = response.getWriter();
        try {
            Process(request);
            m_cliWriter.print(m_Msg); //成功直接返回信息
            m_Msg = null;
        } catch (FrameException e) {
            m_Msg = SysSet.doError(e.getErrorCode(), e.getErrorMsg(), e.getSysMsg());
            m_cliWriter.print(m_Msg);
        }
    }
    @Override
	public void destroy() {
        super.destroy();
    }
    @Override
	public void init() throws ServletException {
        if (m_Logger == null) {
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
        DynamicDict curDict = null;
        //		long lStart = 0;
        //		java.util.Date dStart,dEnd;
        //		lStart = System.currentTimeMillis();
        //		dStart = new java.util.Date();
        //		try{
        //		m_Calc = Integer.parseInt(SysSet.getSystemSet("ConsumeSecs","3000"));
        //		}catch(Exception e)
        //		{
        //		    m_Calc = 3000;
        //		}
        //Connection conn;
        //IAction curAction = null;
        //接受客户端传来的XML数据
        m_Logger.debug("开始接收客户端数据");
        strBuf = new StringBuffer();
        try {
            //	request.setCharacterEncoding("GBK");
            curRead = request.getReader();
            strtmp = curRead.readLine();
            while (strtmp != null) {
                strBuf.append(strtmp);
                strtmp = curRead.readLine();
            }
        } catch (IOException ioe) {
            m_Logger.info("读取客户端的数据截取到IOException:" + ioe.getClass().getName());
            m_Logger.info(SysSet.getStackMsg(ioe));
            m_Logger.info("当前读取的数据:" + strBuf.toString());
            throw new FrameException(-22994000, "读取客户端数据IO异常");
        }
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
        //			logger.info("读取客户端的数据截取到IOException:");
        //			e.printStackTrace();
        //			throw new FrameException(-22994001, "读取客户端的数据出错", e.getMessage());
        //		}
        //m_Data = SysSet.isoToGBK (strBuf.toString ());
        m_Data = strBuf.toString();
//       logger.info("接受的数据是:");
//       logger.info(m_Data);
        if (m_Data == null) {
            throw new FrameException(-22994002, "客户端传来的参数为空");
        }
        //读xml字符串
        try {
            curDict = new DynamicDict();
            SysSet.readInput(m_Data, curDict);
        } catch (Throwable e) {
            strBuf = null;
            m_Logger.info("读取客户端的数据截取到:" + e.getClass().getName());
            m_Logger.info(SysSet.getStackMsg(e));
            m_Logger.info("当前读取的数据:" + m_Data);
            throw new FrameException(-22994003, "读入客户端请求出错...", SysSet.getStackMsg(e));
        }
        //执行action.perform
        //如用户已经登录,把登陆信息打入DynamicDict 实例
        HttpSession session = request.getSession();
        if (session != null) {
            m_Logger.debug("获取Session的属性集名称:" + session.getAttributeNames());
            if (session.getAttribute("user") != null) {
            	if(session.getAttribute("user").getClass().getName().equals("com.powerise.ibss.system.SessionOBJ"))
            	{
                SessionOBJ user = (SessionOBJ) session.getAttribute("user");
                String site_no = user.getSiteNo();
                String site_name = user.getSiteName();
                String bureau_no = user.getBureauNo();
                String bureau_name = user.getBureauName();
                String staff_name = user.getStaffName();
                String city_no = user.getCityNo();
                String staff_no = user.getStaffNo();
                String auth_level = user.getAuthLevel();
                String title_name = user.geTitleName();
                String title_id = user.getTitleId();
                String ip_addr = null;
                try{
                	ip_addr = user.getLoginIp();
                }catch(Exception e){}
                HashMap role = user.getRole();
                curDict.setValueByName("SITE-NO", site_no);
                curDict.setValueByName("SITE-NAME", site_name);
                curDict.setValueByName("BUREAU-NO", bureau_no);
                curDict.setValueByName("BUREAU-NAME", bureau_name);
                curDict.setValueByName("CITY-NO", city_no);
                curDict.setValueByName("STAFF-NO", staff_no);
                curDict.setValueByName("STAFF-NAME", staff_name);
                curDict.setValueByName("AUTH-LEVEL", auth_level);
                curDict.setValueByName("TITLE-NAME", title_name);
                curDict.setValueByName("TITLE-ID", title_id);
                curDict.setValueByName("IP-ADDR",ip_addr);
                //				ArrayList arr = new ArrayList();
                //				arr.add(role);
                //				curDict.setValueByName("STAFF-ROLE", arr);
                m_Logger.debug("设置员工信息成功！");
            	}
            } else {
                strBuf = null;
                throw new FrameException(-22994005, "用户没有登录或登录信息过期，请重新登录！");
            }
        } else {
            strBuf = null;
            throw new FrameException(-22994006, "系统不支持用户会话信息！");
        }
        try {
            m_Msg = ActionDispatch.performServlet(curDict, strBuf);
        } catch (java.lang.Throwable e) {
            e.printStackTrace();
            curDict.flag = -5010;
            curDict.msg = "系统调用出错:" + e.getMessage();
            curDict.exception = SysSet.getStackMsg(e);
            m_Msg = SysSet.doError(curDict.flag, curDict.msg, curDict.exception);
        } finally {
            strBuf = null;
            curDict.destroy();
            curDict = null;
            m_Data = null;
        }
    }
}
