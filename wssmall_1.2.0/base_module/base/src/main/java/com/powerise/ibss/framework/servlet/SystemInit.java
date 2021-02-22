/***************************
 *  此程序在WEB服务器启动时执行，其作用就是初始化系统的初始变量。
 *  sysInit()：初始化系统运行的初始变量
 *  freeMemory()：在系统的指定时间定时对系统进行Java对象垃圾回收gc
 *
 *                                      李志成（创智科技－研发中心）
 */
package com.powerise.ibss.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DefCache;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.util.ContextHelper;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.ibss.common.util.StringUtils;
import com.ztesoft.ibss.ct.config.CTConfig;

public class SystemInit extends HttpServlet {
    private static Logger m_Logger = Logger.getLogger(SystemInit.class);
    private String m_Msg = null;
    public static boolean finshInit = false;
    public static final String DEF_CACHE_CLASS = "DefCache";

    @Override
	public void init(ServletConfig config) throws ServletException {
        initSystem("0");
        finshInit = true;
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //if (!checkAdminIp(request)) return;

        String type = request.getParameter("type");
        initSystem(type);
        PrintWriter pr = response.getWriter();
        pr.println(m_Msg);
        pr.flush();
    }


    /**
     * 校验管理员ip是否在ibss.xml的配置允许的ip段范围，两个ip之间用"|"竖线分割<br>
     * 例如：<ADMIN_IP>134.224.0.0|134.224.255.255</ADMIN_IP>
     *
     * @param request
     * @return
     */
    public static boolean checkAdminIp(HttpServletRequest request) {
        String adminIp = Const.getSystemInfo("ADMIN_IP");
        if (adminIp == null || adminIp.equals("") || adminIp.split("\\|").length != 2) {
            return true;
        }
        try {
            String ips[] = adminIp.split("\\|");
            long beginIp = StringUtils.translateIP(ips[0]);
            long endIp = StringUtils.translateIP(ips[1]);
            long userIp = StringUtils.translateIP(getIp(request));
            if (userIp > beginIp && userIp < endIp) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取用户IP
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        return ContextHelper.getRequestIp(request);
    }

    private void initSystem(String type) {
        if (type == null || type.equals("")) return;
        long lstart, lend, ltotal;
        lstart = System.currentTimeMillis();
        try {
            initCache(type);
            //初始化系统初始参数
            lend = System.currentTimeMillis();
            ltotal = (lend - lstart) / 1000;

            if (type == null || type.equals("")) {
                m_Msg = "You use wrong parameters!";
            } else {
                m_Msg = "System initialize successfully,and consumed " + ltotal + " seconds";
            }

            m_Logger.info(m_Msg);
        } catch (FrameException fre) {
            m_Msg = "系统初始化时出现错误：\n错误号：\n" + fre.getErrorCode() + "\n错误信息：\n" + fre.getErrorMsg() + "\n系统异常：\n"
                    + fre.getSysMsg();
            m_Logger.info(m_Msg);
        }
    }

    private void initCache(String type) throws FrameException {
        CTConfig.getInstance().init();
    }

    /* private void initCache(String type) throws FrameException {
         GlobalNames.CONFIG_LOADED = false;
         logger.info("GlobalNames.CONFIG_LOADED::::::::::::::" + GlobalNames.CONFIG_LOADED);
         SysSet.initSystem(3);//web + app
         this.doDefCache(type);
         logger.info("GlobalNames.CONFIG_LOADED::::::::::::::" + GlobalNames.CONFIG_LOADED);
     }
 */
    private void doDefCache(String type) throws FrameException {
        String cacheName = SysSet.getSystemSet(DEF_CACHE_CLASS);
        if (cacheName != null && !cacheName.equals("")) {
            try {
                DefCache defCache = (DefCache) Class.forName(cacheName).newInstance();
                defCache.doCache(type);
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}
