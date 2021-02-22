package com.powerise.ibss.util;

/**
 * 错误号码段: 229910xx
 * 框架和各个业务模块使用的公共方法,能够使用的都是静态(static)方法
 */

import com.powerise.ibss.framework.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.*;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.*;
public class SysSet {

    // private static boolean m_Inited = false;
    private static String NAMESPACE = "http://www.ztesoft.com/ns/PowerIBSS";

    private static Context ctx = null;

    private static String m_UseEncryptPwd = null;

    static final String m_encrypPrefix = "{3DES}";

    static final int m_encrypPrefixLen = m_encrypPrefix.length();

    static final byte m_encryptKey[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2,
            3, 4, 5, 6, 7, 8, 9, 8, 8, 8, 8 };

    static final String m_PWDKey[] = { "PASSWORD", "PASSWD" };

    static HashMap action_cache; // 缓存已经实例化的类实例

    public static int ACTION_QUERY = 1, ACTION_SERVICE = 2;

    static final String CONFIG_DIR = ""; // 配置文件的目录，$IBSS_HOME/config

    static final String CONFIG_FILE = "ibss.xml"; // 配置文件名，$IBSS_HOME/config/ibss.xml

    public static HashMap m_ActData = null;

    private static HashMap m_ActionConns = null; // 针对每个线程使用的Connection，其中Connection可能存在多个，因此对应的存储变量是MultiDBUtil类

    private static HashMap m_DBLink = null; // 数据库的连接配置,因为存在多个的数据库配置，因此其结构是二级hashmap

    //private static String m_DefaultDBName = "BackGround"; // 缺省的数据库连接名称，考虑同时连接多个数据库，所以此值可变（测试库）
    //private static String m_DefaultDBName = "ZhengShi"; // 缺省的数据库连接名称，考虑同时连接多个数据库，所以此值可变（正式库）
    //private static String m_DefaultDBName = "cs";
    private static String m_DefaultDBName = "Default";
    //缓存设置
    private static boolean m_FrameDataInMem = false;

    private static Logger m_Logger = Logger.getLogger(SysSet.class);

    private static boolean m_MultiDB = false;

    // or HashMap)
    private static HashMap m_ServData = null;

    private static HashMap m_System = null; // 其它配置，区别于DBLink的其它配置，其结构是HashMap->HashMap->(String

    private static int m_XATranTimeOut = 60;

    public static boolean isAjax=false;
    // public static void callService(DynamicDict aDict) throws FrameException {
    // callService(aDict, null);
    // }
    /**
     * 内部调用服务的统一接口,只有一个参数,Connection是来自aDict中的GetConnection
     *
     * @param aDict
     * @param conn
     * @throws FrameException
     */
    public static void callService(DynamicDict aDict) throws FrameException {
        Connection newConn = null;
        Connection oldConn = null;
        long lStart = 0, iCalc = 0;
        java.util.Date dStart, dEnd;
        dStart = new java.util.Date();
        try {
            iCalc = Integer
                    .parseInt(SysSet.getSystemSet("ConsumeSecs", "3000"));
        } catch (Exception e) {
            iCalc = 3000;
        }
        lStart = System.currentTimeMillis();
        oldConn = aDict.GetConnection();
        if (m_MultiDB) {
            newConn = getMultiConnection(aDict.getServiceName(), ACTION_SERVICE);
            aDict.SetConnection(newConn);
        }
        IAction action = getActionInstance(getActionClassName(aDict.m_ActionId,
                aDict.GetConnection()));
        action.perform(aDict);
        aDict.SetConnection(oldConn);
        long lCalc = System.currentTimeMillis() - lStart;
        Monitor.setInfo("Service:" + aDict.m_ActionId, lCalc);
        if (lCalc >= iCalc) {
            dEnd = new java.util.Date();
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("Service:");
            logBuf.append(aDict.m_ActionId);
            logBuf.append("/");
            logBuf.append(dStart);
            logBuf.append("/");
            logBuf.append(dEnd);
            logBuf.append("/");
            logBuf.append(lCalc);
            logBuf.append("/");
            logBuf.append(aDict.toString());
            m_Logger.warn(logBuf.toString());
            logBuf = null;
        }
    }

    /**
     * 此方法用来调试使用。类似SQLPLUS,当每个服务被调执行之后，可以通过此方法在未提交之前查看数据，由于可以回滚
     * 对于同一批测试数据，可以重复用来测试。 支持SQL语句，查询，插入，建表，提交，回滚，不支持desc等非标准SQL语句
     *
     * @param conn
     *            数据库连接
     *            建议将服务使用的Connection传入，这样能够确保是同一个连接和Session.看到的数据就是之前操作过的,如果做测试的话,必须在测试结束之前进行回滚
     */
    public static void check(Connection conn) {
        Statement st = null;
        ResultSet rs = null;
        StringBuffer strBuffer = new StringBuffer();
        String strInput = null;
        byte[] byteData = null;
        int iRead = 0, iLen = 0;
        boolean bStart = true, bExec = false;
        m_Logger.info("如果需要检查执行结果，键入SQL，如想退出，键入exit或者quit");
        while (true) {
            if (bStart) {
                System.out.print("SQL > ");
            }
            try {
                byteData = new byte[10];
                iRead = System.in.read(byteData);
                if (iRead > 1) {
                    if (byteData[iRead - 2] == 13) {
                        byteData[iRead - 2] = 0;
                        byteData[iRead - 1] = 0;
                        iRead = iRead - 2;
                    } else if (byteData[iRead - 1] == 13) {
                        byteData[iRead - 1] = 0;
                        iRead--;
                    }
                } else if (iRead == 1) {
                    byteData[0] = 0;
                    iRead--;
                }
                bStart = false;
                if (iRead > 0)
                    strInput = new String(byteData, 0, iRead);
                else
                    strInput = "";
                byteData = null;
                strInput.trim();
                strBuffer.append(strInput);
                if (System.in.available() == 0) // 读取完毕
                {
                    strInput = strBuffer.toString();
                    strInput = strInput.trim();
                    bStart = true;
                    if (strInput.equals("") == false) {
                        if (strInput.length() < 10)
                            if (strInput.toUpperCase().equals("EXIT")
                                    || strInput.toUpperCase().equals("QUIT")) {
                                break;
                            }
                        iLen = strInput.length();
                        if (strInput.substring(iLen - 1, iLen).equals(";")
                                || strInput.substring(iLen - 1, iLen).equals(
                                "/")) {
                            // 执行SQL语句
                            strInput = strInput.substring(0,
                                    strInput.length() - 1);
                            strBuffer.setLength(0);
                            bExec = false;
                            try {
                                st = conn.createStatement();
                                bExec = st.execute(strInput);
                                if (bExec) // 返回结果集
                                {
                                    int iLoop, j;
                                    Object obj;
                                    rs = st.getResultSet();
                                    rs.setFetchSize(30);
                                    ResultSetMetaData rsdm = rs.getMetaData();
                                    int iFields = rsdm.getColumnCount();
                                    for (iLoop = 1; iLoop <= iFields; iLoop++) {
                                        System.out.print(rsdm
                                                .getColumnName(iLoop));
                                        // 字段名
                                        System.out.print("          ");
                                    }
                                    System.out.print("\n");
                                    j = 0;
                                    while (rs.next()) {
                                        for (iLoop = 1; iLoop <= iFields; iLoop++) {
                                            obj = rs.getObject(iLoop);
                                            if (rs.wasNull())
                                                System.out.print("");
                                            else
                                                System.out.print(obj);
                                            System.out.print("          ");
                                        }
                                        j++;
                                        System.out.print("\n");
                                    }
                                    m_Logger.info("结果集行数: " + j);
                                } else {
                                    m_Logger.info("影响行数: "
                                            + st.getUpdateCount());
                                }
                                if (st != null) {
                                    st.close();
                                    st = null;
                                }
                            } catch (SQLException e) {
                                m_Logger.info(e.getLocalizedMessage());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        byteData = null;
        strBuffer = null;
        strInput = null;
    }

    /**
     * 关闭创建的多个数据库连接，在支持多个数据库连接情况下,单数据库模式下不需要
     *
     */
    public static void closeDBs() {
        if (m_MultiDB) {
            String strId = Thread.currentThread().toString();
            m_Logger.debug("开始关闭指定线程的数据库:" + strId);
            Object obj = m_ActionConns.get(strId);
            if (obj != null) {
                MultiDBUtil db = (MultiDBUtil) obj;
                db.close();
                m_Logger.debug("已关闭指定线程的数据库:" + strId);
                m_ActionConns.remove(strId);
            } else {
                m_Logger.warn("没有找到" + strId + "对应的数据库连接");
            }
        }
    }

    /**
     * 不使用XA，在多数据库模式下自行直接显式通过commit来提交事务
     *
     * @param strId
     * @return
     * @throws FrameException
     */
    public static void commitMulti(String strId) throws FrameException {
        String strThreadName = null;
        MultiDBUtil curMultiDB = null;
        // 判断是否采用的支持多个数据库的连接，如果是，还需要采用xa的数据库接口，也就是采用jta方案
        if (m_MultiDB) {
            // 根据Thread名称获取对应的数据库连接
            strThreadName = Thread.currentThread().toString();
            Object obj = m_ActionConns.get(strThreadName);
            if (obj == null) {
                throw new FrameException(-22991033,
                        "在多数据库模式下,没有创建MultiDB,无法处理事务,action_id:" + strId);
            } else {
                curMultiDB = (MultiDBUtil) obj;
            }
            curMultiDB.commit();
        }
    }

    /**
     * 通过错误号、错误信息、异常信息打具有一定格式的XML字符串
     *
     * @param p_error_code
     *            错误号
     * @param p_error_msg
     *            错误信息
     * @param p_sys_msg
     *            异常信息
     */
    public static String doError(int p_error_code, String p_error_msg,
                                 String p_sys_msg) {
        return doError(p_error_code, p_error_msg, p_sys_msg, 1);
    }

    /**
     * 通过错误号、错误信息、异常信息打具有一定格式的XML字符串
     *
     * @param p_error_code
     *            错误号
     * @param p_error_msg
     *            错误信息
     * @param p_sys_msg
     *            异常信息
     * @param flag
     *            标志， 1：AgentServer 2：CommonServer
     */
    public static String doError(int p_error_code, String p_error_msg,
                                 String p_sys_msg, int flag) {
        XMLTool curDoc;
        Element curElem;
        Attr curAttr;
        String msg = "";
        try {
            curDoc = new XMLTool();
            curDoc.New("PoweriseIBSS");
            curElem = (Element) curDoc.CreateElement("Definition",
                    curDoc.GetRootElem(), null);
            curAttr = curDoc.FindAttrByName("Type", curElem, true);
            curAttr.setNodeValue(String.valueOf(flag));
            curElem = (Element) curDoc.CreateElement("Return", curDoc
                    .GetRootElem(), null);
            curAttr = curDoc.FindAttrByName("Code", curElem, true);
            curAttr.setNodeValue(String.valueOf(p_error_code));
            curAttr = curDoc.FindAttrByName("Msg", curElem, true);
            curAttr.setNodeValue(Utility.isoToGBK(String.valueOf(p_error_msg)));
			/*
			 * if (p_sys_msg != null) { curAttr =
			 * curDoc.FindAttrByName("SysMsg", curElem, true);
			 * curAttr.setNodeValue(String.valueOf(p_sys_msg)); }
			 */
            msg = curDoc.GetXML();
        } catch (FrameException e1) {
            msg = "<?xml version='1.0 ?><PowerIBSS><Return Code = '-9999' Msg='系统错误' /></PowerIBSS>";
        }
        return msg;
    }

    /**
     * 处理返回包的数据(XML格式),由框架内部调用
     *
     * @param retCodeBuf
     *            返回的执行码buffer
     * @param retMsgBuf
     *            返回的数据串buffer
     * @param aDict
     *            出参数据存储体
     * @return 返回的数据串
     * @throws FrameException
     */
    public static String doReturn(StringBuffer retCodeBuf,
                                  StringBuffer retMsgBuf, DynamicDict aDict) throws FrameException {
        String retMsg = retMsgBuf.toString();
        if ((retMsg == null) || retMsg.trim().equals(""))
            retMsg = "执行成功";
        int retCode = Integer.parseInt(retCodeBuf.toString());
        String retData = null;
        Object obj, objx;
        ArrayList alobj;
        HashMap hmobj;
        Set hmset;
        String hmnam;
        Object hmvalue;
        ResultSet rsobj;
        String nam;
        Iterator it;
        XmlSerializer m_serializer = null;
        ByteArrayOutputStream m_bsOut = null;

        Vector vName = new Vector();
        Vector vValue = new Vector();
        String strName, strValue;
        int iSize;
        String strActionId = aDict.m_ActionId;
        retMsg = ((retMsg == null) || retMsg.equals("")) ? " " : retMsg;
        // 建立新的XML 处理对象及其初始化
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME),
                    null);
            m_serializer = factory.newSerializer();
            m_bsOut = new ByteArrayOutputStream();
            m_serializer.setOutput(new PrintWriter(m_bsOut, true));
            m_serializer.startDocument("UTF-8", null);
            m_serializer.ignorableWhitespace("\n\n");
            if(isAjax)
                NAMESPACE="";
            m_serializer.setPrefix("", NAMESPACE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameException(-22993001, "创建返回xml文档出错");
        }
        try {
            Connection conn = aDict.GetConnection();
            HashMap hmValues = aDict.m_Values;
            //刘武修改
            String xmlHead="PowerIBSSReturn";
            if(isAjax)
                xmlHead="PoweriseIBSS";
            m_serializer.startTag(NAMESPACE, xmlHead);
            m_serializer.startTag(NAMESPACE, "Definition");
            if(aDict.isBOMode())
                m_serializer.attribute(null, "Type", "3"); //BO模式
            else
                m_serializer.attribute(null, "Type", "1");
            m_serializer.endTag(NAMESPACE, "Definition");
            // 建立definition
            // 建立actionid
            m_serializer.startTag(NAMESPACE, "ActionId");
            m_serializer
                    .text(((strActionId == null) || strActionId.equals("")) ? " "
                            : strActionId);
            m_serializer.endTag(NAMESPACE, "ActionId");
            // 建立data-parameter
            m_serializer.startTag(NAMESPACE, "Data");

            // 返回结果参数节点
            // 只对retcode>=0的返回结果参数
            // 遍历m_values参数表，分三种情况处理：
            // 1。普通参数，输出到parameters=DEFAULT节点中；
            // 2。arraylist，输出到parameters=变量名的节点中；其中，如果arraylist中元素是hashmap类型，则将hashmap内容依次输出。
            // 3。resultset，输出到parameters=变量名的节点中。
            if (retCode >= 0) {
                // rowElem = (Element) m_XMLDoc.CreateElement("Row", paraElem,
                // null);
                Vector vArg = null;
                ServiceData servData = getServData(strActionId, conn);//

                vArg = servData.m_Args;

                //刘武增加，支持action 嵌套
                if(strActionId.equals("MFM_EXEC_ACTION")) {
                    //throw new FrameException(-1,hmValues.toString());
                    vArg=(Vector)aDict.getValueByName("/action/outArgs");
                }
                //m_Logger.info(vArg);
                if (aDict.isBOMode()) {
                    GeneBOXML(m_serializer, hmValues, vArg, 0);


                } else {
                    // m_Logger.debug(strActionId+"参数:"+vArg);
                    if (vArg != null) {
                        // try {
                        int iArgSize = vArg.size();
                        for (int k = 0; k < iArgSize; k++) // 遍历m_values所有参数
                        {
                            nam = (String) vArg.get(k);
                            obj = hmValues.get(nam);
                            if (obj == null) // 出参为空则等同空串
                                obj = "";
                            if (obj.getClass().getName().equals(
                                    "java.util.ArrayList")) { // 数组需要新建parameters
                                alobj = (ArrayList) obj;
                                m_serializer.startTag(NAMESPACE, "Parameters");
                                m_serializer.attribute(null, "Name", nam);
                                for (int i = 0; (i < alobj.size())
                                        && (i < 10000); i++) {
                                    m_serializer.startTag(NAMESPACE, "Row");
                                    objx = alobj.get(i);
                                    if (objx == null)
                                        objx = "";
                                    if (objx.getClass().getName().equals(
                                            "java.util.HashMap")) { // 输出多列arraylist
                                        hmobj = (HashMap) objx;
                                        hmset = hmobj.keySet();
                                        if (hmset != null) { // hashmap有内容
                                            it = hmset.iterator();
                                            while (it.hasNext()) {
                                                hmnam = (String) it.next();
                                                hmvalue = hmobj.get(hmnam);
                                                if (hmvalue == null)
                                                    hmvalue = "";
                                                m_serializer.startTag(
                                                        NAMESPACE, hmnam
                                                        .toUpperCase());
                                                m_serializer.text(hmvalue
                                                        .toString());
                                                m_serializer.endTag(NAMESPACE,
                                                        hmnam.toUpperCase());
                                            }
                                        } else
                                        // hashmap无内容，要输出空row
                                        {
                                            m_serializer.startTag(NAMESPACE,
                                                    nam);
                                            m_serializer.endTag(NAMESPACE, nam);
                                        }
                                    } else
                                    // 输出单列arraylist
                                    {
                                        m_serializer.startTag(NAMESPACE, nam);
                                        m_serializer.text(objx.toString());
                                        m_serializer.endTag(NAMESPACE, nam);
                                    }
                                    m_serializer.endTag(NAMESPACE, "Row");
                                }// end for
                                m_serializer.endTag(NAMESPACE, "Parameters");
                            } else if (obj.getClass().getName().indexOf(
                                    "ResultSet") != -1) { // 结果集需要新建parameters
                                m_serializer.startTag(NAMESPACE, "Parameters");
                                m_serializer.attribute(null, "Name", nam);
                                rsobj = (ResultSet) obj;
                                ResultSetMetaData v_Fld = rsobj.getMetaData(); // 字段定义
                                int iFldCnt = v_Fld.getColumnCount(); // 字段数
                                int j = 0;
                                while ((rsobj.next()) && (j < 10000)) {
                                    m_serializer.startTag(NAMESPACE, "Row");
                                    for (int i = 1; i <= iFldCnt; i++) {
                                        strValue = rsobj.getString(i);
                                        if (rsobj.wasNull() == false) {
                                            strName = v_Fld.getColumnName(i);
                                            m_serializer.startTag(NAMESPACE,
                                                    strName);
                                            m_serializer.text(strValue);
                                            m_serializer.endTag(NAMESPACE,
                                                    strName);
                                        }
                                    }
                                    m_serializer.endTag(NAMESPACE, "Row");
                                    j++;
                                }
                                Statement stTmp = rsobj.getStatement();
                                rsobj.close();
                                stTmp.close();
                                m_serializer.endTag(NAMESPACE, "Parameters");
                            } else {
                                vName.add(nam);
                                vValue.add(obj.toString());
                            }
                        }
                        // 对需要后续输出的arraylist类型，继续输出从1号开始的元素
                        // } catch (Exception e) {
                        // retCode = -22991365;
                        // retMsg = m_ActionId + "处理出参数据时发生异常!" +
                        // e.getMessage();
                        // m_m_Logger.info(retMsg + getStackMsg(e));
                        // throw new FrameException(-22991365, "查询出参配置时发生异常:" +
                        // getStackMsg(e));
                        //
                        // }
                    }
                }
            } // end of if(retCode>=0)
            // 由于xml pull 的机制，最后来处理Default的Parameter

            if (aDict.isBOMode() == false) {
                m_serializer.startTag(NAMESPACE, "Parameters");
                m_serializer.attribute(null, "Name", "DEFAULT");
                m_serializer.startTag(NAMESPACE, "Row");
                iSize = vName.size();
                for (int i = 0; i < iSize; i++) {
                    strName = (String) vName.get(i);
                    strValue = (String) vValue.get(i);
                    if (strValue != null) {
                        m_serializer.startTag(NAMESPACE, strName);
                        m_serializer.text(strValue);
                        m_serializer.endTag(NAMESPACE, strName);
                    }
                }
                m_serializer.endTag(NAMESPACE, "Row");
                m_serializer.endTag(NAMESPACE, "Parameters");
            }

            m_serializer.endTag(NAMESPACE, "Data");
            // 建立return节点
            m_serializer.startTag(NAMESPACE, "Return");
            m_serializer.attribute(null, "Code", String.valueOf(retCode));
            m_serializer.attribute(null, "Msg", retMsg);
            m_serializer.endTag(NAMESPACE, "Return");
            m_serializer.endTag(NAMESPACE, xmlHead);
            m_serializer.endDocument();
            // 生成XML串
            retData = m_bsOut.toString();

            m_bsOut.close();
            m_bsOut = null;
            String strTemp = "xmlns=\"" + NAMESPACE + "\"";
            int iPos = retData.indexOf(strTemp);
            if (iPos > 0) {
                StringBuffer strBufTemp = new StringBuffer();
                strBufTemp.append(retData.substring(0, iPos - 1));
                strBufTemp.append(retData.substring(iPos + strTemp.length()));
            }

            // retData = m_XMLDoc.GetXML();
        } catch (Exception e) {
            m_Logger.info(strActionId + getStackMsg(e));
            throw new FrameException(-22995102, "处理出参数据时发生异常:"
                    + e.getClass().getName(), getStackMsg(e));
        } finally {
            if (m_bsOut != null) {
                try {
                    m_bsOut.close();
                } catch (Exception e) {
                }
                m_bsOut = null;
            }
        }
        // 返回值
        // retdata=null 如果入参>0，但打包失败，则改动retcode.
        if ((Integer.parseInt(retCodeBuf.toString()) >= 0) && (retCode < 0)) {
            retCodeBuf.replace(0, retCodeBuf.toString().length(), String
                    .valueOf(retCode));
            retMsgBuf.replace(0, retMsgBuf.toString().length(), retMsg);
        }
        // m_Logger.debug(strActionId+"返回数据:"+retData);
        return retData;
    }

    /**
     * 使用通用查询的API,根据ActionId来获取对应的参数,建议不使用此API,而是直接使用com.powerise.ibss.framework.BackEndAction来获取数据
     *
     * @param strActionId
     * @return 参数集,是Vector类型,其成员是com.powerise.ibss.framework.ActionArg
     * @throws FrameException
     */
    public static Vector getActionArgs(String strActionId)
            throws FrameException {
        Vector vArgs = null;
        if (m_MultiDB == false && m_FrameDataInMem == false) {
            Connection conn = null;
            try {
                conn = DBUtil.getConnection();
                vArgs = getActionArgs(strActionId, conn);
            } finally {
                if (conn != null) {
                    try {
                        conn.rollback();
                        conn.close();
                        conn = null;
                    } catch (Exception e) {
                    }
                }
            }
        } else
            return getActionArgs(strActionId, null);
        return vArgs;
    }

    /**
     * 使用通用查询的API,根据ActionId来获取对应的参数,建议不使用此API,而是直接使用com.powerise.ibss.framework.BackEndAction来获取数据
     *
     * @param strActionId
     * @param conn
     *            后台调用应该传入的Connection,从而省去重新获取连接,在多数据库模式下,此连接会忽略
     * @return 参数集,是Vector类型,其成员是com.powerise.ibss.framework.ActionArg
     * @throws FrameException
     */
    public static Vector getActionArgs(String strActionId, Connection conn)
            throws FrameException {
        Vector vArgs = null;
        ActionData act = getActionData(strActionId, conn);
        if (act != null)
            vArgs = act.m_Args;
        return vArgs;
    }

    /**
     * 后台服务使用的API,根据服务名称来获取对应的Class Name,
     *
     * @param aActionName
     *            tfm_services中的service_name
     * @return 类名称
     * @throws FrameException
     */
    public static String getActionClassName(String aActionName)
            throws FrameException {
        String strClassName = null;
        if (m_MultiDB == false && m_FrameDataInMem == false) {
            Connection conn = null;
            try {
                conn = DBUtil.getConnection();
                strClassName = getActionClassName(aActionName, conn);
            } finally {
                if (conn != null) {
                    try {
                        conn.rollback();
                        conn.close();
                        conn = null;
                    } catch (Exception e) {
                    }
                }
            }
        }
        return strClassName;
    }

    /**
     * 后台服务使用的API,根据服务名称来获取对应的Class Name,
     *
     * @param aActionName
     *            tfm_services中的service_name
     * @param conn
     *            传入的Connection ,从而省去重新建立的连接,在多数据库模式下,此连接会忽略
     * @return
     * @throws FrameException
     */
    public static String getActionClassName(String aActionName, Connection conn)
            throws FrameException {
        String strClassName = null;
        String strAction = aActionName.toUpperCase().trim();
        if (m_FrameDataInMem == true) {
            Object obj = m_ServData.get(strAction);
            if (obj == null)
                throw new FrameException(-100001102, "根据动作码获取类名出错:" + strAction);
            ServiceData servData = (ServiceData) obj;
            strClassName = servData.m_ClassName;
        } else {
            // 从数据库中读取
            if (conn == null) {
                throw new FrameException(-22990017,
                        "获取ClassName时传入的Connection为空，不能获取");
            }
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn
                        .prepareStatement("select class_name from tfm_services where upper(service_name)=?");
                st.setString(1, strAction);
                rs = st.executeQuery();
                if (rs.next()) {
                    strClassName = rs.getString(1);
                } else {
                    throw new FrameException(-22990011, strAction
                            + "没有在框架services中配置");
                }
            } catch (SQLException sqle) {
                throw new FrameException(-22990018, "根据动作码获取类名出现数据库错误:"
                        + aActionName, getStackMsg(sqle));
            } finally {
                try {
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException e) {
                    throw new FrameException(-100001191,
                            "SysSet.getActionClassName 方法关闭数据库资源时，出现异常！", e
                            .getMessage());
                }
            }
        }
        return strClassName;
    }

    /**
     * 通用查询使用的API,根据ActionID来获取对应的通用查询数据,包括执行的SQL,对应的参数配置
     *
     * modified by qin.guoquan 增加strCountSQL 分页用，查询总数
     *
     * @param strActionId
     * @param conn
     *            传入的连接,从而省去重新建立的连接,在多数据库模式下,此连接会忽略
     * @return com.powerise.ibss.framework.ActionData类
     * @throws FrameException
     */
    public static ActionData getActionData(String strActionId, Connection conn)
            throws FrameException {
        ActionData actData = null;
        //物理分页增加strCountSQL属性,add by qin.guoquan 2011-09
        String strSQL = null, strType = null, strCacheFlag = null, strCountSQL = null;
        Vector vArgs = null;
        ActionArg actArg = null;
        // strActionId = strActionId.toUpperCase();
        if (m_FrameDataInMem) {
            Object obj = m_ActData.get(strActionId);
            if (obj != null)
                actData = (ActionData) obj;
            else
                throw new FrameException(-22990021, "指定的查询Action ID不存在:"
                        + strActionId);
        } else {
            actData = new ActionData();
            // 从数据库中读取
            if (conn == null) {
                throw new FrameException(-22990019,
                        "获取Action SQL时传入的Connection为空，不能获取");
            }
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareStatement("select action_clause,action_type,cache_flag from tfm_action_list where action_id=? order by seq");
                st.setString(1, strActionId);
                rs = st.executeQuery();
                strSQL = "";
                while (rs.next()) {
                    strSQL = strSQL + rs.getString(1);
                    strType = rs.getString(2);
                    strCacheFlag = rs.getString(3);
                    //物理分页增加strCountSQL属性,add by qin.guoquan 2011-09
                    strCountSQL = "select count(*) from (" + strSQL + ")";
                }
                st.close();
                st = null;
                if (strSQL.equals("")) throw new FrameException(-22990021, "指定的查询Action ID不存在:" + strActionId);
                st = conn.prepareStatement("select arg_name,arg_data_type,in_out_flag,arg_length,arg_seq from tfm_action_args where action_id=? order by arg_seq");
                actData.m_SQL = strSQL;
                actData.m_Type = strType;
                actData.m_CacheFlag = strCacheFlag;
                //物理分页增加strCountSQL属性,add by qin.guoquan 2011-09
                actData.m_CountSQL = strCountSQL;
                // 获取参数
                st.setString(1, strActionId);
                rs = st.executeQuery();
                while (rs.next()) {
                    actArg = new ActionArg();
                    actArg.m_Name = rs.getString(1);
                    if (actArg.m_Name != null)
                        actArg.m_Name = actArg.m_Name.toUpperCase().trim();
                    actArg.m_Flag = rs.getInt(3);
                    actArg.m_Type = rs.getInt(2);
                    actArg.m_Length = rs.getInt(4);
                    actArg.m_Seq = rs.getInt(5);
                    if (vArgs == null)
                        vArgs = new Vector();
                    vArgs.add(actArg);
                }
                actData.m_Args = vArgs;
            } catch (Exception sqle) {
                sqle.printStackTrace();
                throw new FrameException(-22990020, "根据ActionId获取SQL语句出现数据库错误:"
                        + strActionId, sqle.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (st != null) {
                        st.close();
                        st = null;
                    }
                } catch (SQLException e) {
                    throw new FrameException(-22990023,
                            "获取动态SQL关闭数据库资源时，出现异常！", e.getMessage());
                }
            }
        }
        return actData;
    }

    /**
     * 根据服务名称(service name)来获取实现了IAction接口的类的实例
     *
     * @param p_action_name
     *            服务名称(service name)
     * @return IAction实例
     * @throws FrameException
     */
    public static IAction getActionInstance(String p_action_name)
            throws FrameException {
        try {
            IAction action = null;
            if (GlobalNames.PROJECT_MODE == 1) {// 产品模式
                m_Logger.debug("动态调用类:" + p_action_name);
                action = (IAction) Class.forName(p_action_name).newInstance();
            } else {// 开发模式
                m_Logger.info("项目处于开发模式。。。。。");
                IbssClassLoader class_loader = new IbssClassLoader();
                Object o = (class_loader.loadClass(p_action_name))
                        .newInstance();
                action = (IAction) o;
            }
            return action;
        } catch (ClassNotFoundException ce) {
            throw new FrameException(-5001, "类(" + p_action_name + ")没有找到。", ce
                    .getMessage());
        } catch (InstantiationException e) {
            throw new FrameException(-5002,
                    "类(" + p_action_name + ")实例化时出现异常。", e.getMessage());
        } catch (IllegalAccessException e) {
            throw new FrameException(-5003,
                    "类(" + p_action_name + ")被访问时出现异常。", e.getMessage());
        } catch (Throwable th) {
            m_Logger.info(getStackMsg(th));
            throw new FrameException(-5004, "类(" + p_action_name + ")调用出现系统异常"
                    + th.getClass().getName(), getStackMsg(th));
        }
    }

    /**
     * 通用查询的API,根据ActionId来获取通用查询对应的SQL语句,建议不使用此API,而是直接使用com.powerise.ibss.framework.BackEndAction来获取数据
     *
     * @param strActionId
     * @return SQL内容
     * @throws FrameException
     */
    public static String getActionSQL(String strActionId) throws FrameException {
        String strSQL = null;
        if (m_MultiDB == false && m_FrameDataInMem == false) {
            Connection conn = null;
            try {
                conn = DBUtil.getConnection();
                strSQL = getActionSQL(strActionId, conn);
            } finally {
                if (conn != null) {
                    try {
                        conn.rollback();
                        conn.close();
                        conn = null;
                    } catch (Exception e) {
                    }
                }
            }
        } else
            return getActionSQL(strActionId, null);
        return strSQL;
    }

    /**
     * 通用查询的API,根据ActionId来获取通用查询对应的SQL语句,建议不使用此API,而是直接使用com.powerise.ibss.framework.BackEndAction来获取数据
     *
     * @param strActionId
     * @param conn
     *            传入的Connection ,从而省去重新建立的连接,在多数据库模式下,此连接会忽略
     * @return SQL内容
     * @throws FrameException
     */
    public static String getActionSQL(String strActionId, Connection conn)
            throws FrameException {
        String strSQL = null;
        m_Logger.debug("查询的ActionId:" + strActionId);
        ActionData act = getActionData(strActionId, conn);
        if (act != null)
            strSQL = act.m_SQL;
        return strSQL;
    }

    /**
     * 获取配置文件目录名，相对路径，在HOME目录下
     *
     * @return String
     */
    public static String getConfigDirectory() {
        return getHomeDirectory();
//		return (getHomeDirectory() + System.getProperty("file.separator") + CONFIG_DIR);
    }

    /**
     * 获取配置文件名，在配置目录下，约定为ibss.xml
     *
     * @return String 带全路经的配置文件名
     */
    public static String getConfigFile() {
        //URL fileUrl=SysSet.class.getResource("/ibss.xml");

        //if(fileUrl!=null)
        //  return fileUrl.getPath();
        return (getConfigDirectory() + CONFIG_FILE);
    }

    /**
     * 根据配置文件的DB Name来获取数据的配置情况
     *
     * @param strDBName
     * @return 名称-值配对的数据库配置参数,例如Url,Driver等等
     * @throws FrameException
     */
    public static HashMap getDBInfoByName(String strDBName)
            throws FrameException {
        HashMap hmDB = null;
        if (m_DBLink != null) {
            if (strDBName == null)
                strDBName = m_DefaultDBName;
            Object obj = m_DBLink.get(strDBName);
            if (obj != null)
                hmDB = (HashMap) obj;
        }
        if (hmDB == null)
            throw new FrameException(-129922, "根据" + strDBName + "获取数据库配置失败");
        return hmDB;
    }

    /**
     * 根据应用服务ID(Env_ID)来查询对应的数据库,应用服务ID包括通用查询处理和专门的服务调用
     *
     * @param strId
     * @param iType
     *            iType=ACTION_QUERY 通用查询 iType = ACTION_SERVICE 专门的服务调用
     * @return
     */
    public static String getDBNameById(String strId, int iType)
            throws FrameException {
        String strDBName = null;
        Object obj = null;
        if (strId == null)
            return m_DefaultDBName;
        String strSearch = strId.trim();
        if (iType == ACTION_QUERY) // 通用查询
        {
            obj = m_ActData.get(strSearch);
            if (obj == null)
                throw new FrameException(-22990101, strId + "在action配置表没有配置");
            ActionData d = (ActionData) obj;
            strDBName = d.m_EnvId;
        } else if (iType == ACTION_SERVICE) // 专门的应用服务
        {
            obj = m_ServData.get(strSearch);
            if (obj == null)
                throw new FrameException(-22990101, strId + "在Services配置表没有配置");
            ServiceData c = (ServiceData) obj;
            strDBName = c.m_EnvId;
            m_Logger.debug(strSearch + "配置的数据库:" + strDBName);
        }
        return strDBName;
    }

    /**
     * 从配置文件中获取数据库的配置信息,框架使用
     *
     * @throws FrameException
     */
    private static void getDBSet() throws FrameException {
        String strDBName = null;
        String strDBXAType = null;
        Element eleDBs = null, eleAttr = null, eleDBServer = null;
        NodeList nlElem = null;
        Node nElem = null;
        HashMap hmAttr = null;
        String strName = null, strValue = null, strEncrypt = null;
        XMLTool xtDoc;
        boolean bModify = false;
        int iAttrSize = 0, iDBCnt = 0;
        if (m_DBLink != null) {
            m_DBLink.clear();
            m_DBLink = null;
        }
        if (m_UseEncryptPwd == null) {
            m_UseEncryptPwd = System.getProperty("USE_ENCRYPT_PWD");
            if (m_UseEncryptPwd == null)
                m_UseEncryptPwd = "0";
        }
        m_DBLink = new HashMap();
        xtDoc = new XMLTool();
        try {
            m_Logger.info("IBSS.XML路径:"+getConfigFile()+"::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
            xtDoc.ReadXMLFile(getConfigFile());

            nElem = xtDoc.FindChildByName("DBLink", xtDoc.GetRootElem(), false);
            if (nElem == null)
                return;
            eleDBs = (Element) nElem;
            NodeList list = eleDBs.getChildNodes();// 数据库配置主节点
            iDBCnt = list.getLength();
            for (int i = 0; i < iDBCnt; i++) {
                nElem = list.item(i);
                if (nElem.getNodeType() != Node.ELEMENT_NODE) // 必须是Element才可以处理
                    continue;
                eleDBServer = (Element) nElem;
                strDBName = eleDBServer.getAttribute("Name");// 获取数据库连接的名称
                if (strDBName == null)
                    continue;
                // 获取数据库连接是否采用的XA Driver
                strDBXAType = eleDBServer.getAttribute("XAType");
                if (strDBXAType == null || strDBXAType.equals(""))
                    strDBXAType = "false"; // 缺省不使用XA Driver
                nlElem = eleDBServer.getChildNodes();
                iAttrSize = nlElem.getLength();
                // 取子节点
                hmAttr = new HashMap();
                hmAttr.put("XAType", strDBXAType); // 因为XAType不属于数据库连接属性，所以单独考虑，直接写入
                for (int k = 0; k < iAttrSize; k++) {
                    nElem = eleDBServer.getChildNodes().item(k);
                    if (nElem.getNodeType() == Node.ELEMENT_NODE) {
                        if (nElem.getFirstChild() != null) {
                            // 判断是否需要加密的口令,通过关键字来判断
                            strName = nElem.getNodeName();
                            strValue = nElem.getFirstChild().getNodeValue();
                            if (m_UseEncryptPwd.equals("1")) {
                                for (int n = 0; n < m_PWDKey.length; n++) {
                                    if (m_PWDKey[n].equals(strName
                                            .toUpperCase())) {
                                        // 属于加密字段
                                        if (strValue.length() > m_encrypPrefixLen
                                                && strValue.substring(0,
                                                m_encrypPrefixLen)
                                                .equalsIgnoreCase(
                                                        m_encrypPrefix)) {
                                            // 需要解密
                                            strValue = decryptString(strValue
                                                    .substring(m_encrypPrefixLen));
                                        } else {

                                            // 未加密,需要加密
                                            bModify = true;
                                            strEncrypt = encryptString(strValue);
                                            nElem
                                                    .getFirstChild()
                                                    .setNodeValue(
                                                            m_encrypPrefix
                                                                    + strEncrypt);

                                        }
                                    }
                                }
                            }
                            hmAttr.put(strName, strValue);
                        }
                    }
                }
                if (hmAttr != null && hmAttr.size() > 0) {
                    m_DBLink.put(strDBName, hmAttr);
                    hmAttr = null;
                }
            }
            m_Logger.info("DB map:"+m_DBLink);
            if (bModify) {
                xtDoc.WriteXMLFile(getConfigFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameException(-5011, "获取数据库配置信息出错！", e.getMessage());
        } finally {
            if (xtDoc != null) {
                xtDoc.Reset();
                xtDoc = null;
            }
        }
    }

    /**
     * 针对DBUtil.getConnection()所取的缺省数据库名,可通过设置java
     * 命令行参数-DDEFAULT_DB=配置文件中的数据库配置名来改变缺省的数据库配置名
     *
     * @return
     */
    public static String getDefaultDBName() {
        return m_DefaultDBName;
    }

    private static void getFrameSetData() {
        String strDBName;
        Connection conn = null;
        Statement st = null;
        PreparedStatement prst = null, prstAction = null;
        ResultSet rs = null, rsArg = null, rsAction = null;
        ServiceData servData = null;
        ActionData actData = null;
        ActionArg actArg = null;
        String strActionSQL, strServName = null, strClassName = null, strArgName = null;
        String strActionId = null, strLastActionId = null, strClause = null, strActionType = null, strLastActionType = null;
        String strEnvId = null, strLastEnvId = null;
        String strCacheFlag = null, strLastCacheFlag = null;
        StringBuffer sbClause = null;
        Vector servArgs = null;
        Vector actArgs = null;
        String strSQL = null;
        boolean bXAType = false;
        UserTransaction usTran = null;
        if (m_ServData != null) {
            m_ServData.clear();
            m_ServData = null;
        }
        if (m_ActData != null) {
            m_ActData.clear();
            m_ActData = null;
        }
        m_ServData = new HashMap();
        m_ActData = new HashMap();
        Object obj = null;
        obj = System.getProperty("FRAME_DB");// 通过java 命令行参数带入 -DFRAME_DB=xxx
        if (obj == null)
            strDBName = m_DefaultDBName;
        else
            strDBName = (String) obj;
        // 获取数据库连接
        try {
            if (m_MultiDB) {
                strSQL = "select service_name,class_name,cache_flag,env_id from tfm_services";
                if (bXAType == true) {
                    usTran = getUserTransaction();
                    usTran.begin();
                }
            } else
                strSQL = "select service_name,class_name,cache_flag from tfm_services";
            conn = DBUtil.getConnection(strDBName);
            // 获取服务数据
            st = conn.createStatement();
            st.setFetchSize(300);
            rs = st.executeQuery(strSQL);
            prst = conn
                    .prepareStatement("select arg_name from tfm_service_args where service_name=?");
            prst.setFetchSize(10);
            while (rs.next()) {
                servData = new ServiceData();
                servArgs = new Vector();
                strServName = rs.getString(1);
                strClassName = rs.getString(2);
                strCacheFlag = rs.getString(3);

                prst.setString(1, strServName);
                rsArg = prst.executeQuery();
                while (rsArg.next()) {
                    strArgName = rsArg.getString(1);
                    if (strArgName != null)
                        servArgs.add(strArgName.trim().toUpperCase());
                }
                servData.m_Args = servArgs;
                servData.m_ClassName = strClassName;
                servData.m_CacheFlag = strCacheFlag;
                if (m_MultiDB)
                    servData.m_EnvId = rs.getString(4);
                m_ServData.put(strServName.trim().toUpperCase(), servData);
                servData = null;
            }
            st.close();
            st = null;
            prst.close();
            prst = null;

            // 获取查询数据,其中ActionId大小写敏感
            st = conn.createStatement();
            st.setFetchSize(300);
            strActionSQL = "select distinct action_id from tfm_action_list";
            if (m_MultiDB)
                strSQL = "select action_type,action_clause ,cache_flag,env_id from tfm_action_list where action_id=? order by action_id,seq";
            else
                strSQL = "select action_type,action_clause,cache_flag from tfm_action_list where action_id=? order by action_id,seq";
            rs = st.executeQuery(strActionSQL);
            prstAction = conn.prepareStatement(strSQL);
            prst = conn
                    .prepareStatement("select arg_seq,arg_data_type,arg_length,arg_name,in_out_flag from tfm_action_args where action_id=? order by arg_seq");
            prst.setFetchSize(10);
            sbClause = new StringBuffer();
            strLastActionId = null;
            while (rs.next()) {
                strActionId = rs.getString(1);
                prstAction.setString(1, strActionId); // 主要是获取sql语句
                rsAction = prstAction.executeQuery();
                while (rsAction.next()) {
                    strActionType = rsAction.getString(1);
                    strCacheFlag = rsAction.getString(3);
                    if (m_MultiDB)
                        strEnvId = rsAction.getString(4);
                    sbClause.append(rsAction.getString(2));
                }
                // 新建对象

                actData = new ActionData();
                actData.m_SQL = sbClause.toString();
                actData.m_Type = strActionType;
                actData.m_CacheFlag = strCacheFlag;
                actData.m_CountSQL = "select count(1) from (" + actData.m_SQL + ")";
                // sbClause = null;sbClause = new StringBuffer();
                sbClause.setLength(0);// clear the data
                // 开始获取查询参数
                prst.setString(1, strActionId);
                rsArg = prst.executeQuery();
                actArgs = new Vector();
                while (rsArg.next()) {
                    actArg = new ActionArg();
                    actArg.m_Seq = rsArg.getInt(1);
                    actArg.m_Type = rsArg.getInt(2);
                    actArg.m_Length = rsArg.getInt(3);
                    actArg.m_Name = rsArg.getString(4);
                    if (actArg.m_Name != null)
                        actArg.m_Name = actArg.m_Name.toUpperCase().trim();
                    actArg.m_Flag = rsArg.getInt(5);
                    actArgs.add(actArg);
                }
                actData.m_Args = actArgs;
                if (m_MultiDB)
                    actData.m_EnvId = strEnvId;
                m_ActData.put(strActionId, actData);
                actArgs = null;
                actData = null;

            }
            sbClause = null;
            st.close();
            prst.close();
            prstAction.close();
            prstAction = null;
            st = null;
            prst = null;

        } catch (Exception e) {
            m_Logger.info("获取框架配置错误:" + getStackMsg(e));
        } finally {
            if (st != null) {
                try {
                    st.close();
                    st = null;
                } catch (Exception e) {
                }
            }
            if (prst != null) {
                try {
                    prst.close();
                    prst = null;
                } catch (Exception e) {
                }
            }
            if (prstAction != null) {
                try {
                    prst.close();
                    prst = null;
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    conn.rollback();
                    conn.close();
                    conn = null;
                } catch (Exception e) {
                }
            }
            if (m_MultiDB) {
                try {
                    if (usTran != null)
                        usTran.rollback();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取系统的HOME目录，首先从系统变量IBSS_HOME中获取，如果没有，则直接获取当前用户的 HOME环境变量
     *
     * @return java.lang.String
     */
    public static String getHomeDirectory() {
        String s = System.getProperty("IBSS_HOME");
        if (s != null)
            return s;
        else{
//			return System.getProperty("user.home");
            URL fileUrl=SysSet.class.getClassLoader().getResource("/");
            return fileUrl.getPath();
        }
    }

    /**
     * 判断是否系统已经初始化
     *
     * @return
     */
    public static boolean getInitFlag() {
        return GlobalNames.CONFIG_LOADED;
    }

    /**
     * 从JNDI树中得到 javax.naming.InitialContext</code> 对象
     *
     * @return javax.transaction.InitialContext
     */
    public static Context getInitialContext() throws FrameException {
        return getInitialContext(getSystemSet("RMI", "UrlProvider", "未知url"));
    }

    /**
     * 通过指定 url 从JNDI树中得到 javax.naming.InitialContext</code> 对象
     *
     * @param url
     * @return javax.transaction.InitialContext
     */
    public static Context getInitialContext(String url) throws FrameException {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, getSystemSet("RMI",
                    "InitialFactory", "未知Provider"));
            properties.put(Context.PROVIDER_URL, url);
            m_Logger.debug(properties);
            return new InitialContext(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameException("不能连接到服务器：" + url + "请确认服务器正在运行。\n"
                    + getStackMsg(e));
        }
    }

    /**
     * 在多数据库模式下来获取数据库连接,框架使用
     *
     * @param strId
     *            可以是服务名称,也可以是查询的ActionId,通过type 来区分
     * @param iType
     *            iType=1代表通用查询,iType=2代表服务调用
     * @return
     * @throws FrameException
     */
    public static Connection getMultiConnection(String strId, int iType)
            throws FrameException {
        Connection conn = null;
        String strActionDBName = null;
        String strThreadName = null;
        MultiDBUtil curMultiDB = null;
        // 判断是否采用的支持多个数据库的连接，如果是，还需要采用xa的数据库接口，也就是采用jta方案
        if (m_MultiDB) {
            // 根据Thread名称获取对应的数据库连接
            strThreadName = Thread.currentThread().toString();
            m_Logger.debug("当前线程:" + strThreadName);
            Object obj = m_ActionConns.get(strThreadName);
            if (obj == null) {
                m_Logger.debug("创建新的MultiDB");
                curMultiDB = new MultiDBUtil();
                m_ActionConns.put(strThreadName, curMultiDB);
            } else {
                curMultiDB = (MultiDBUtil) obj;
            }
            strActionDBName = getDBNameById(strId, iType);
            if (strActionDBName == null || strActionDBName.trim().equals(""))
                strActionDBName = m_DefaultDBName; // 给出缺省数据库名
            conn = curMultiDB.getConnection(strActionDBName);
        }
        return conn;
    }

    /**
     * 根据调用的服务名称(service_name)来获取调用服务的信息,以ServiceData类返回,其成员中包括了类名,和参数集(Vector类型)
     *
     * @param strServiceName
     *            服务名成
     * @param conn
     *            数据库连接,在多数据库模式下,此连接会忽略,为了保持版本统一,此连接需要
     * @return ServiceData类
     * @throws FrameException
     */
    public static ServiceData getServData(String strServiceName, Connection conn)
            throws FrameException {
        ServiceData servData = null;
        Vector vArgs = null;
        strServiceName = strServiceName.toUpperCase();
        if (m_FrameDataInMem) {
            Object obj = m_ServData.get(strServiceName);
            if (obj != null)
                servData = (ServiceData) obj;
        } else {
            servData = new ServiceData();
            // 从数据库中读取
            if (conn == null) {
                throw new FrameException(-22990019,
                        "获取service 时传入的Connection为空，不能获取" + strServiceName);
            }
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn
                        .prepareStatement("select class_name,cache_flag,env_id from tfm_services where upper(service_name)=? ");
                st.setString(1, strServiceName);
                rs = st.executeQuery();
                if (rs.next()) {
                    servData.m_ClassName = rs.getString(1);
                    servData.m_CacheFlag = rs.getString(2);
                    servData.m_EnvId = rs.getString(3);
                }
                st.close();
                st = null;
                st = conn
                        .prepareStatement("select arg_name from tfm_service_args where upper(service_name)=?");
                // 获取参数
                st.setString(1, strServiceName);
                rs = st.executeQuery();
                vArgs = new Vector();
                while (rs.next()) {
                    vArgs.add(rs.getString(1).toUpperCase().trim());
                }
                servData.m_Args = vArgs;
            } catch (SQLException e) {
                if (st != null) {
                    try {
                        st.close();
                        st = null;
                    } catch (SQLException e1) {
                    }
                }
            }
        }
        return servData;
    }

    /**
     * 获取异常的堆栈信息,便于调试
     *
     * @param e
     * @return
     */
    public static String getStackMsg(Error e) {
        // StringWriter sw = null;
        // PrintWriter pw = null;
        // String strMsg = "";
        //
        // sw = new StringWriter();
        // pw = new PrintWriter(sw);
        // try {
        // e.printStackTrace(pw);
        // sw.flush();
        // strMsg = sw.toString();
        // sw.close();
        // pw.close();
        // } catch (java.io.IOException le) {
        // strMsg = "待定处理";
        // }
        return getStackMsg((Throwable) e);
    }

    /**
     * 获取异常的堆栈信息,便于调试
     *
     * @param e
     * @return
     */
    public static String getStackMsg(Exception e) {
        return getStackMsg((Throwable) e);
    }

    /**
     * 获取异常的堆栈信息，便于调试
     *
     * @param e
     * @return String
     */
    public static String getStackMsg(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        String strMsg = "";
        sw = new StringWriter();
        pw = new PrintWriter(sw);
        try {
            e.printStackTrace(pw);
            sw.flush();
            strMsg = sw.toString();
            sw.close();
            pw.close();
        } catch (java.io.IOException le) {
            strMsg = "待定处理";
        }
        return strMsg;
    }

    /**
     * 获取系统的所有非DBLink的配置，存在于ibss.xml中
     *
     * @throws FrameException
     */
    private static void getSystemSet() throws FrameException {
        String strNodeName = null, strChildNodeName = null;
        Element rootElem = null, eleDBs = null, eleAttr = null, eleDBServer = null;
        NodeList nlAttr = null, nlElem = null, nlNode = null;
        Node nElem = null, nChildNode = null, nAttrNode = null;
        HashMap hmAttr = null, hmChildAttr = null;
        ArrayList arAttr = null;
        NamedNodeMap nmAttr = null;
        Object obj = null;
        XMLTool xtDoc;
        int iAttrSize = 0, iSize = 0, iNodeSize = 0;
        m_Logger.debug("开始装载系统配置");
        if (m_System != null) {
            m_System.clear();
            m_System = null;
        }
        m_System = new HashMap();
        xtDoc = new XMLTool();
        try {
            xtDoc.ReadXMLFile(getConfigFile());
            rootElem = xtDoc.GetRootElem();
            nlElem = rootElem.getChildNodes();
            iSize = nlElem.getLength();
            for (int i = 0; i < iSize; i++) {
                nElem = nlElem.item(i);
                // 必须是Element type类型
                if (nElem.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                // DBLink,有专门的方法来处理数据库连接
                strNodeName = nElem.getNodeName().trim();
                if (strNodeName.equals("DBLink"))
                    continue;
                // 获取子结点的内容，存在两种情况，一种是直接的Element+TextNode,一种是Element+Attr
                hmAttr = new HashMap();
                nlNode = nElem.getChildNodes();
                iNodeSize = nlNode.getLength();
                for (int j = 0; j < iNodeSize; j++) {
                    nChildNode = nlNode.item(j);
                    if (nChildNode.getNodeType() != Node.ELEMENT_NODE)
                        continue;
                    // 结点必须是TextNode或者Attribute的组合
                    hmChildAttr = null;
                    nmAttr = nChildNode.getAttributes();
                    strChildNodeName = nChildNode.getNodeName();
                    iAttrSize = nmAttr.getLength();
                    if (iAttrSize > 0) {
                        arAttr = new ArrayList(); // 考虑到有重名的节点，所以针对包含属性的统一用ArrayList＋HashMap来处理
                        hmChildAttr = new HashMap();
                        for (int k = 0; k < iAttrSize; k++) {
                            nAttrNode = nmAttr.item(k);
                            if (nAttrNode.getNodeType() != Node.ATTRIBUTE_NODE)
                                continue;
                            hmChildAttr.put(nAttrNode.getNodeName(), nAttrNode
                                    .getNodeValue());
                        }
                        // 从hmAttr中首先查找是否存在，如果存在则是ArrayList,否则创建新的ArrayList，将hmChildAttr打入到其中
                        obj = hmAttr.get(strChildNodeName);
                        if (obj != null)
                            arAttr = (ArrayList) obj;
                        else
                            arAttr = new ArrayList();
                        arAttr.add(hmChildAttr);
                        hmAttr.put(strChildNodeName, arAttr);
                    } else {
                        if (nChildNode.getFirstChild() != null)
                            hmAttr.put(strChildNodeName, nChildNode
                                    .getFirstChild().getNodeValue());
                    }
                    arAttr = null;
                }
                m_System.put(strNodeName, hmAttr);
            }
        } catch (Exception e) {
            throw new FrameException(-229901001, "", getStackMsg(e));
        }
    }

    /**
     * 获取配置文件中System节中的指定属性的配置内容
     *
     * @param strSetName
     * @return
     * @throws FrameException
     */
    public static String getSystemSet(String strSetName) throws FrameException {
        return getSystemSet("System", strSetName, null);
    }

    /**
     * 获取配置文件中System节中的指定属性的配置内容,可以指定当查找不到时的缺省值
     *
     * @param strSetName
     *            参数名称
     *
     * @return
     * @throws FrameException
     */
    public static String getSystemSet(String strSetName, String strDefaultValue)
            throws FrameException {
        return getSystemSet("System", strSetName, strDefaultValue);
    }

    /**
     * 获取配置文件中指定节中的指定属性的配置内容,可以指定当查找不到时的缺省值,此APi只适用于类似System节的结构
     *
     * @param strTypeName
     *            配置类型名称
     * @param strSetName
     *            参数名称
     * @return
     * @throws FrameException
     */
    public static String getSystemSet(String strTypeName, String strSetName,
                                      String strDefaultValue) throws FrameException {
        String strValue = null;
        Object obj = null;
        HashMap hmSet = null;
        if (m_System != null) {
            obj = m_System.get(strTypeName);
            if (obj == null) {
                if (strDefaultValue != null)
                    strValue = strDefaultValue;
                else
                    throw new FrameException(-22991002, strTypeName
                            + "没有在配置文件中找到");
            } else {
                hmSet = (HashMap) obj;
                obj = hmSet.get(strSetName);
                if (obj != null)
                    strValue = (String) obj;
                else if (strDefaultValue != null)
                    strValue = strDefaultValue;
            }
        } else {
            //throw new FrameException(-22991002, "没有初始化系统变量m_System");
        }
        return strValue;
    }

    /**
     * 获取配置文件中指定节中的指定属性的配置内容集,没有找到返回null,此API适用于MQ节的配置结构,如果存在多值(属性名称相同)的话,则返回第一个
     *
     * @param strTypeName
     *            类型节的名称
     * @param strSetName
     *            属性名称
     * @return 名称-值对的配置内容
     * @throws FrameException
     */
    public static HashMap getSystemSets(String strTypeName, String strSetName)
            throws FrameException {
        return getSystemSets(strTypeName, strSetName, null, null);
    }

    /**
     * 获取配置文件中指定节中的指定属性的配置内容集,没有找到返回null,此API适用于MQ节的配置结构,并且针对存在多个属性名称相同的配置,因此如果需要定位到具体的值,还需要指定查询的子属性名称和子属性值,例如MQ配置中Id子属性
     *
     * @param strTypeName
     * @param strSetName
     * @param strSearchName
     * @param strSearchValue
     * @return
     * @throws FrameException
     */
    public static HashMap getSystemSets(String strTypeName, String strSetName,
                                        String strSearchName, String strSearchValue) throws FrameException {
        HashMap hmValue = null, hmObj = null;
        HashMap hmSet = null;
        Object obj = null;
        int iSize;
        ArrayList arAttr = null;
        // 代码中没有对类的类型进行判断，因为这是基于建立这些参数数据来考虑的
        if (m_System != null) {
            hmSet = (HashMap) m_System.get(strTypeName);
            if (hmSet != null) {
                arAttr = (ArrayList) hmSet.get(strSetName);
                iSize = arAttr.size();
                if (iSize > 0) {
                    if (strSearchName != null && strSearchValue != null) // 这样就可以查找了
                    {
                        for (int i = 0; i < iSize; i++) {
                            hmObj = (HashMap) arAttr.get(i);
                            obj = hmObj.get(strSearchName);
                            if (obj != null)
                                if (((String) obj).equals(strSearchValue)) {
                                    hmValue = hmObj;
                                    break;
                                }
                        }
                    } else {
                        hmValue = (HashMap) arAttr.get(0);
                    }
                } else {
                    throw new FrameException(-22991004, strTypeName + "."
                            + strSetName + "没有在配置文件找到");
                }
            }
        } else {
            throw new FrameException(-22991003, strTypeName + "没有在配置文件中找到");
        }
        hmObj = null;
        return hmValue;
    }

    /**
     * 在配置文件中得到指定Tag的第一个子节点或属性
     *
     * @param p_note_name
     *            Tag的名称
     * @return 返回字节点列表或属性类别
     */
    public static HashMap getTagProps(String p_note_name) throws FrameException {
        return getTagProps(p_note_name, 0);
    }

    /**
     * 在配置文件中得到指定Tag的第一个子节点或属性
     *
     * @param p_note_name
     *            Tag的名称
     * @param type
     *            类别 0：子节点 1：属性
     * @return 返回字节点列表或属性类别
     */
    public static HashMap getTagProps(String p_note_name, int type)
            throws FrameException {
        return getTagProps(p_note_name, null, null, type);
    }

    /**
     * 在配置文件中得到指定Tag的子节点或属性,存在多个同名节点时,取属性名为p_name值为p_value的节点
     *
     * @param p_note_name
     *            Tag的名称
     * @param p_name
     *            属性名
     * @param p_name
     *            属性值
     * @param type
     *            类别 0：子节点 1：属性
     * @return 返回字节点列表或属性类别
     */
    public static HashMap getTagProps(String p_note_name, String p_name,
                                      String p_value, int type) throws FrameException {
        HashMap hash = null;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = dbfactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getConfigFile()));
            Element root = doc.getDocumentElement();
            NodeList list = root.getElementsByTagName(p_note_name);
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                if (p_name != null
                        && (!element.getAttribute(p_name).equalsIgnoreCase(
                        p_value)))
                    continue;
                if (type == 0) {
                    // 取子节点
                    for (int k = 0; k < element.getChildNodes().getLength(); k++) {
                        Node node = element.getChildNodes().item(k);
                        if (hash == null)
                            hash = new HashMap();
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if (node.getFirstChild() != null)
                                hash.put(node.getNodeName(), node
                                        .getFirstChild().getNodeValue());
                        }
                    }
                } else {
                    // 取属性
                    for (int k = 0; k < element.getAttributes().getLength(); k++) {
                        if (hash == null)
                            hash = new HashMap();
                        Node node = element.getAttributes().item(k);
                        hash.put(node.getNodeName(), element.getAttribute(node
                                .getNodeName()));
                    }
                }
                break;
            }
            doc = null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameException(-5011,
                    "SysSet.getTagProps()取配置文件信息时，出现异常！", e.getMessage());
        }
        return hash;
    }

    /**
     * 在XA环境下,获取JTA的UserTransaction
     *
     * @return
     * @throws FrameException
     */
    public static UserTransaction getUserTransaction() throws FrameException {
        UserTransaction usTran = null;
        Context usContext = null;
        try {
            usContext = new InitialContext();
            usTran = (UserTransaction) usContext
                    .lookup("java:comp/UserTransaction");
            // Set the transaction timeout to 30 seconds
            usTran.setTransactionTimeout(m_XATranTimeOut);
        } catch (Exception e) {
            throw new FrameException(-22990013, "创建UserTransaction异常",
                    getStackMsg(e));
        }
        return usTran;
    }

    /**
     * 初始化多数据库模式下的连接池,同时获取配置中的XA事务Timeout值,配置文件中System节可以通过配置XATransactionTime来设置时长
     *
     * @throws FrameException
     */
    public static void initConnContainer() throws FrameException {
        if (m_ActionConns == null) {
            if (SysSet.getSystemSet("MultiDB", "false").equals("false"))
                m_MultiDB = false;
            else {
                m_MultiDB = true;
                m_ActionConns = new HashMap();
                // 获取XA事务的时间
                m_XATranTimeOut = Integer.parseInt(SysSet.getSystemSet(
                        "XATransactionTime", "60"));
            }
        }
    }

    /**
     * 初始化系统配置
     *
     * @throws FrameException
     */
    public static void initSystem() throws FrameException {
        initSystem(3);// 缺省按照统一部署配置
    }

    /**
     *
     * @param iFlag
     *            1- Web Server init 2 - App Server init 3 Web Server and App
     *            Server
     * @throws FrameException
     */
    public static synchronized void initSystem(int iFlag) throws FrameException {
        // 设置log4j 的配置
        String strTemp = null;
        if (!GlobalNames.CONFIG_LOADED) {
            strTemp = System.getProperty("LOG4J_FILE");
            if (strTemp == null)
                strTemp = "ibss.log.properties";
            else {
                // 判断是否存在log4j的配置文件
                File f = new File(SysSet.getConfigDirectory() + strTemp);
                if (f.exists() == false) // 不存在还是采用缺省的配置
                    strTemp = "ibss.log.properties";
            }
            PropertyConfigurator.configure(SysSet.getConfigDirectory()+ strTemp);
            strTemp = null;
            HashMap sys_conf = getTagProps("System");
            if (sys_conf != null) {
                m_Logger.info("开始初始化应用系统环境...");
                GlobalNames.SYSTEM_NAME = sys_conf.get("SystemName") == null ? Utility
                        .isoToGBK("创智电信新97", true)
                        : sys_conf.get("SystemName").toString();
                GlobalNames.SOFTWARE_NAME = sys_conf.get("SystemName") == null ? "创智科技"
                        : sys_conf.get("DevCompany").toString();
                GlobalNames.PROJECT_MODE = sys_conf.get("ProjectMode") == null ? 0
                        : Integer.parseInt(sys_conf.get("ProjectMode")
                        .toString());
                GlobalNames.ISO_TO_GBK = sys_conf.get("IsoToGBK") == null ? false
                        : Boolean.getBoolean(sys_conf.get("IsoToGBK")
                        .toString());
                GlobalNames.GBK_TO_ISO = sys_conf.get("GbkToISO") == null ? false
                        : Boolean.getBoolean(sys_conf.get("GbkToISO")
                        .toString());
                GlobalNames.PRINT_TERMINAL = sys_conf.get("PrintTerminal") == null ? false
                        : (new Boolean(sys_conf.get("PrintTerminal").toString()
                        .trim())).booleanValue();
                GlobalNames.ERR_TO_DB = sys_conf.get("ErrToDB") == null ? false
                        : (new Boolean(sys_conf.get("ErrToDB").toString()
                        .trim())).booleanValue();
                GlobalNames.EAI_ENCODING = sys_conf.get("EaiEncoding") == null ? "gb2312"
                        : sys_conf.get("EaiEncoding").toString();
                GlobalNames.USE_LDAP = sys_conf.get("UseLDAP") == null ? false
                        : (new Boolean(sys_conf.get("UseLDAP").toString()
                        .trim())).booleanValue();
                GlobalNames.USE_EAI = sys_conf.get("UseEAI") == null ? false
                        : (new Boolean(sys_conf.get("UseEAI").toString().trim()))
                        .booleanValue();
                GlobalNames.LOG_EAI_MSG = sys_conf.get("LogEAIMsg") == null ? false
                        : (new Boolean(sys_conf.get("LogEAIMsg").toString()
                        .trim())).booleanValue();
                HashMap queryDefault = SysSet.getTagProps("MQManager", "id",
                        "DEFAULT", 1);
                HashMap queryOM = SysSet
                        .getTagProps("MQManager", "id", "OM", 1);
                ArrayList querys = new ArrayList();
                if (queryDefault != null && queryDefault.size() > 0)
                    querys.add(queryDefault);
                if (queryOM != null && queryOM.size() > 0)
                    querys.add(queryDefault);
                if (querys.size() > 0)
                    GlobalNames.EAI_QUERYS = querys;
            }
            // 获取数据库连接配置
            getDBSet();
            getSystemSet();
            // 读取环境变量来设置Default DB连接的名称
            strTemp = System.getProperty("DEFAULT_DB");// 如果需要设置，则在java命令行增加参数
            // -DDEFAULT_DB=xxxx
            if (strTemp != null)
                m_DefaultDBName = strTemp;
            else
                m_DefaultDBName = "Default";// 缺省的数据库连接名称
            // 获取框架是否装入内存的配置
            strTemp = getSystemSet("FrameDataInMem");
            if (strTemp != null)
                if (strTemp.equalsIgnoreCase("true"))
                    m_FrameDataInMem = true;
                else
                    m_FrameDataInMem = false;
            else
                m_FrameDataInMem = false;
            m_Logger.debug("缺省数据库是 " + m_DefaultDBName);
            initConnContainer();
            m_Logger.info("初始应用系统环境完成");
            GlobalNames.CONFIG_LOADED = true;
            // 因为以下涉及到数据库的处理，而在获取连接时在判断是否初始化了系统，如果在config_loaded=true来做的话，就会造成死循环
            if (m_FrameDataInMem) {
                m_Logger.info("开始装载框架数据到系统...");
                getFrameSetData();
                m_Logger.info("装载框架数据到系统完成");
            }
            // 启动监控
            if (getSystemSet("Monitor", "true").equals("true"))
                Monitor.start();
        }
    }

    /**
     * 获取框架数据是否载入到内存
     *
     * @return
     */
    public static boolean isFrameDataInMem() {
        return m_FrameDataInMem;
    }

    /**
     * 判断是否多数据库模式
     *
     * @return
     */
    public static boolean isMultiDB() {
        return m_MultiDB;
    }

    /**
     * 通过GlobalNames.ISO_TO_GBK定义对字符串进行ISO8859-1 对GBK的转换
     *
     * @param p_in -
     *            要转换的字符串
     * @return String
     */
    public static String isoToGBK(String p_in) {
        String os = System.getProperty("os.name");
        if (os.lastIndexOf("Windows") < 0) { // for others platform
            if (!(p_in == null || p_in.equals(""))) {
                try {
                    p_in = new String(p_in.getBytes("ISO-8859-1"), "GBK");
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return p_in;
    }

    // 需要判断是否采用的XA Driver，XA Driver 处理事务与非XA的代码是不一样的
    public static boolean isXAType(String strDBName) throws FrameException {
        boolean bXA = false;
        HashMap ht = getDBInfoByName(strDBName);
        String s = (String) ht.get("XAType");
        if (s.equals("false"))
            bXA = false;
        else
            bXA = true;
        return bXA;
    }

    /**
     * 读取入参并解析
     *
     * @param aInStr -
     *            传入的XML参数内容
     * @return int
     * @throws com.powerise.ibss.framework.FrameException
     */
    public static int readInput(String aInStr, DynamicDict aDict)
            throws FrameException {
        return readInput(aInStr, 1, aDict);
    }

    /**
     * 处理多层嵌套Dict的输入参数
     *
     * @param aDoc
     * @param aDict
     * @param aElem
     * @return
     * @throws FrameException
     */
    private static boolean readDynamic(XMLTool aDoc, DynamicDict aDict,
                                       Element aElem) throws FrameException {
        return false;
    }

    /**
     * 读取入参并解析，需要指定入参XML类型
     *
     * @param aInStr -
     *            传入的XML参数内容
     * @param aType -
     *            传入的参数格式类型，缺省是1，为系统内部使用
     * @return int
     * @throws com.powerise.ibss.framework.FrameException
     */
    public static int readInput(String aInStr, int aType, DynamicDict aDict)
            throws FrameException {
        String strDefType = null;
        // 只处理第一个parameters
        XMLTool m_XMLDoc = new XMLTool();
        // 从字符串读入到m_xmldoc

        if (m_XMLDoc.ReadXML(aInStr) < 0)
            return -1;
        // 读各节点
        Node eRoot = m_XMLDoc.GetRootElem();
        Node eDef = m_XMLDoc.FindChildByName("Definition", eRoot, false); // Definition节点
        Node eActId = m_XMLDoc.FindChildByName("ActionId", eRoot, false); // ActionID节点
        strDefType = m_XMLDoc.FindAttrByName("Type", (Element) eDef, false)
                .getNodeValue();
        aDict.m_ActionId = m_XMLDoc.GetElemValue(eRoot, "ActionId", null)
                .trim();
        Node eDat = m_XMLDoc.FindChildByName("Data", eRoot, false); // Data节点
        if (strDefType.equals("3")) {
            GeneDict(aDict, (Element) eDat);
            return 0;
        }
        // 目前只读parameters=DEFAULT的节点，其余parameters节点忽略
        NodeList nl1 = eDat.getChildNodes();
        for (int n = 0; n < nl1.getLength(); n++) {
            Node ePara = nl1.item(n);
            if (ePara == null || ePara.getNodeType() != Node.ELEMENT_NODE)
                continue;
            String paraName = m_XMLDoc.FindAttrByName("Name", (Element) ePara,
                    false).getNodeValue();
            // Node ePara = m_XMLDoc.GetNodeByAttr("Parameters", (Element) eDat,
            // "Name", "DEFAULT"); //Parameters节点
            if (paraName.equalsIgnoreCase("DEFAULT")) {
                // 默认节点
                NodeList eRowList = ePara.getChildNodes();
                int iRLen = eRowList.getLength();
                for (int j = 0; j < iRLen; j++) // 遍历parameters的子节点(row)
                {
                    Node eRow = eRowList.item(j);
                    if (eRow.getNodeType() != Node.ELEMENT_NODE)
                        continue;
                    // scan args
                    NodeList eArgList = eRow.getChildNodes();
                    int iALen = eArgList.getLength();
                    for (int k = 0; k < iALen; k++) // 遍历row中的子节点(args)
                    {
                        Node eArg = eArgList.item(k); // arg
                        String sArgName;
                        String sArgValue = null;
                        if (eArg.getNodeType() != Node.ELEMENT_NODE)
                            continue;
                        sArgName = eArg.getNodeName().toUpperCase().trim();
                        try {
                            sArgValue = eArg.getFirstChild().getNodeValue();
                        } catch (DOMException e) {
                            sArgValue = "";
                        } catch (NullPointerException e) {
                            sArgValue = "";
                        }
                        aDict.setValueByName(sArgName, sArgValue, 1); // insert
                        // the arg
                    }
                }
            } else {
                NodeList eRowList = ePara.getChildNodes();
                int iRLen = eRowList.getLength();
                for (int j = 0; j < iRLen; j++) { // 遍历parameters的子节点(row)
                    Node eRow = eRowList.item(j);
                    if (eRow.getNodeType() != Node.ELEMENT_NODE)
                        continue;
                    // scan args
                    NodeList eArgList = eRow.getChildNodes();
                    int iALen = eArgList.getLength();
                    HashMap h = new HashMap();
                    for (int k = 0; k < iALen; k++) { // 遍历row中的子节点(args)
                        Node eArg = eArgList.item(k); // arg
                        String sArgName;
                        String sArgValue = null;
                        if (eArg.getNodeType() != Node.ELEMENT_NODE)
                            continue;
                        sArgName = eArg.getNodeName().toUpperCase().trim();
                        try {
                            sArgValue = eArg.getFirstChild().getNodeValue();
                        } catch (DOMException e) {
                            sArgValue = "";
                        } catch (NullPointerException e) {
                            sArgValue = "";
                        }
                        h.put(sArgName, sArgValue);
                    }
                    aDict.setValueByName(paraName, h, 1); // insert the arg
                }
            }
        }
        return 0;
    }

    /**
     * 不使用XA，在多数据库模式下自行直接显式通过rollback来回滚事务
     *
     * @param strId
     * @throws FrameException
     */
    public static void rollbackMulti(String strId) throws FrameException {
        String strThreadName = null;
        MultiDBUtil curMultiDB = null;
        // 判断是否采用的支持多个数据库的连接，如果是，还需要采用xa的数据库接口，也就是采用jta方案
        if (m_MultiDB) {
            // 根据Thread名称获取对应的数据库连接
            strThreadName = Thread.currentThread().toString();
            Object obj = m_ActionConns.get(strThreadName);
            if (obj == null) {
                throw new FrameException(-22991033,
                        "在多数据库模式下,没有创建MultiDB,无法处理事务,action_id:" + strId);
            } else {
                curMultiDB = (MultiDBUtil) obj;
            }
            curMultiDB.rollback();
        }
    }

    public static void setDBInfo(String strDBName, HashMap hmDB)
            throws FrameException {
        if (m_DBLink != null && hmDB != null) {
            m_DBLink.put(strDBName, hmDB);
        }
    }

    public static void setInitFlag(boolean bFlag) {
        GlobalNames.CONFIG_LOADED = bFlag;
    }

    /**
     * xa环境下的启动事务
     *
     * @param usTran
     * @throws FrameException
     */
    public static void tpBegin(UserTransaction usTran) throws FrameException {
        // // Use JNDI to locate the UserTransaction object
        try {
            // ...
            // Begin a transaction
            usTran.begin();
        } catch (Exception e) {
            throw new FrameException(-22990010, "xa初始化出错:"
                    + e.getClass().getName(), SysSet.getStackMsg(e));
        }
    }

    /**
     * XA环境下的提交事务
     *
     * @param usTran
     * @throws FrameException
     */
    public static void tpCommit(UserTransaction usTran) throws FrameException {
        if (usTran != null) {
            try {
                usTran.commit();
                usTran = null;
            } catch (Exception e) {
                throw new FrameException(-22990010, "xa提交数据出错", SysSet
                        .getStackMsg(e));
            }
        }
    }

    /**
     * xa环境下的回滚事务
     *
     * @param usTran
     * @throws FrameException
     */
    public static void tpRollback(UserTransaction usTran) throws FrameException {
        if (usTran != null) {
            try {
                usTran.rollback();
                usTran = null;
            } catch (Exception e) {
                throw new FrameException(-22990010, "xa回滚数据出错", SysSet
                        .getStackMsg(e));
            }
        }
    }

    /**
     * 记录程序错误日志
     *
     * @param sname
     *            服务名
     * @param ecode
     *            错误号
     * @param emsg
     *            错误信息
     * @param eexp
     *            异常信息
     */
    public static long writeLog(String sname, int ecode, String emsg,
                                String eexp) {
	/*	Connection conn = null;// 写日志文件数据库连接
		PreparedStatement st = null;
		Statement stSeq = null;
		HashMap hmDB = null;
		String strDBProd = null;
		Object obj = null;
		String error_log = null;
		long lSeq = 0;
		try {
			if (GlobalNames.ERR_TO_DB) {
				HashMap insert = new HashMap();
				if (emsg == null)
					emsg = "";
				if (eexp == null)
					eexp = "";
				error_log = "业务错误信息:" + emsg + "\n系统日志:\n" + eexp;
				if (error_log.length() > 1500) {
					error_log = error_log.substring(0, 1500);
				}
				m_Logger.debug("系统开始写入日志(" + ecode + "):" + error_log);
				conn = DBUtil.getConnection("dataSource");
				try {
					stSeq = conn.createStatement();
          String dbType=getSystemSet("System","DBType","Oracle");
          m_Logger.info("错误日志信息:"+error_log);
          ResultSet rs;
          if(!dbType.equals("Oracle")) {
            *//*rs = stSeq
                .executeQuery("select max(error_seq)+1 from tfm_error_log");*//*
            lSeq=System.currentTimeMillis();
          } else {
            rs = stSeq
                .executeQuery("select tfm_err_log_seq.nextval from dual");
            if (rs.next())
              lSeq = rs.getLong(1);
            rs.close();
          }
					st = conn
							.prepareStatement("insert into tfm_error_log(error_seq,occur_time,service_name,error_code,error_log) values(?,?,?,?,?)");
					st.setLong(1, lSeq);
          st.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
					st.setString(3, sname);
					st.setString(4, String.valueOf(ecode));
					st.setString(5, error_log);
					st.execute();
					conn.commit();
					m_Logger.debug("系统写入日志(" + ecode + ")完成");
				} catch (Exception e1) {
					m_Logger.warn("写日志失败:" + error_log);
					m_Logger.warn(getStackMsg(e1));
					conn.rollback();
				}
			}
		} catch (Exception e) {
			m_Logger.warn("写日志失败:" + eexp);
			m_Logger.warn(getStackMsg(e));
		} finally {
			try {
				if (stSeq != null) {
					stSeq.close();
					stSeq = null;
				}
				if (st != null) {
					st.close();
					st = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException sqle) {
				m_Logger.info("写入日志关闭数据库出现异常:");
				sqle.printStackTrace();
			}
		}*/
        return 0;
    }

    public SysSet() {
        super();
        if (m_Logger == null)
            m_Logger = Logger.getLogger(getClass().getName());
        // TODO Auto-generated constructor stub
    }

    /**
     * 将字典转换成调用的xml入参格式字符串
     *
     * @param dict
     * @return
     * @throws FrameException
     */
    public static String getInXMLFromDict(DynamicDict dict)
            throws FrameException {
        String strXML = null;
        HashMap hmValue = null;
        Iterator it;
        XmlSerializer m_serializer = null;
        ByteArrayOutputStream m_bsOut = null;
        String NAMESPACE = "http://www.powerise.com.cn/ns/PowerIBSS";
        String strName, strParam, strVal;
        ArrayList al = null;
        HashMap hmParam = null;
        Vector vOthers = new Vector(); // 存储不是Default的变量
        HashMap hmTempArray = new HashMap(); // 针对是HashMap的变量，需要临时创建ArrayList
        Object obj;
        if (dict == null)
            throw new FrameException(-22990020, "传入的Dict为null");
        hmValue = dict.m_Values;
        String strActionId = dict.m_ActionId;

        int iSize;
        // 建立新的XML 处理对象及其初始化
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
                    System.getProperty(XmlPullParserFactory.PROPERTY_NAME),
                    null);
            m_serializer = factory.newSerializer();
            m_bsOut = new ByteArrayOutputStream();
            m_serializer.setOutput(new PrintWriter(m_bsOut, true));
            m_serializer.startDocument("GBK", null);
            m_serializer.ignorableWhitespace("\n\n");
            m_serializer.setPrefix("", NAMESPACE);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FrameException(-22990021, "创建返回xml文档出错");
        }
        try {

            m_serializer.startTag(NAMESPACE, "Powerise_IBSS");
            m_serializer.startTag(NAMESPACE, "Definition");
            m_serializer.attribute(null, "Type", "1");
            m_serializer.endTag(NAMESPACE, "Definition");
            // 建立definition
            // 建立actionid
            m_serializer.startTag(NAMESPACE, "ActionId");
            m_serializer
                    .text(((strActionId == null) || strActionId.equals("")) ? " "
                            : strActionId);
            m_serializer.endTag(NAMESPACE, "ActionId");
            // 建立data-parameter
            m_serializer.startTag(NAMESPACE, "Data");
            // 遍历m_values参数表，分三种情况处理：
            // 1。普通参数，输出到parameters=DEFAULT节点中；
            // 2。arraylist，输出到parameters=变量名的节点中；其中，如果arraylist中元素是hashmap类型，则将hashmap内容依次输出。
            // 3。resultset，输出到parameters=变量名的节点中。
            // m_Logger.debug(strActionId+"参数:"+vArg);
            it = hmValue.keySet().iterator();
            m_serializer.startTag(NAMESPACE, "Parameters");
            m_serializer.attribute(null, "Name", "DEFAULT");
            while (it.hasNext()) {
                strName = (String) it.next();
                obj = hmValue.get(strName);
                if (obj == null)
                    continue;
                if (obj.getClass().getName().indexOf("java.util.HashMap") >= 0) {
                    al = new ArrayList();
                    al.add(obj);
                    hmTempArray.put(strName, al);
                    continue;
                }

                if (obj.getClass().getName().indexOf("java.util.ArrayList") >= 0) {
                    vOthers.add(strName);
                    continue;
                }
                // 针对普通参数直接打包
                m_serializer.startTag(NAMESPACE, strName);
                m_serializer.text((String) obj);
                m_serializer.endTag(NAMESPACE, strName);

            }
            m_serializer.endTag(NAMESPACE, "Parameters");
            // 处理为ArrayList的参数集
            for (int i = 0; i < vOthers.size(); i++) {
                strParam = (String) vOthers.get(i);
                m_serializer.startTag(NAMESPACE, "Parameters");
                m_serializer.attribute(NAMESPACE, "Name", strParam);
                al = (ArrayList) hmValue.get(strParam);
                for (int j = 0; j < al.size(); j++) {
                    obj = al.get(j);
                    if (obj.getClass().getName().indexOf("java.util.HashMap") >= 0) {
                        hmParam = (HashMap) obj;
                        it = hmParam.keySet().iterator();
                        while (it.hasNext()) {
                            strName = (String) it.next();
                            strVal = (String) hmParam.get(strName);
                            m_serializer.startTag(NAMESPACE, strName);
                            m_serializer.text(strVal);
                            m_serializer.endTag(NAMESPACE, strName);
                        }
                    } else {
                        m_serializer.startTag(NAMESPACE, strParam);
                        m_serializer.text((String) obj);
                        m_serializer.endTag(NAMESPACE, strParam);

                    }
                }
                m_serializer.endTag(NAMESPACE, "Parameters"); // 结束Parameter
            }
            // 处理临时创建的ArrayList的处理
            Iterator itArr = hmTempArray.keySet().iterator();
            while (itArr.hasNext()) {
                strParam = (String) itArr.next();
                m_serializer.startTag(NAMESPACE, "Parameters");
                m_serializer.attribute(NAMESPACE, "Name", strParam);
                al = (ArrayList) hmTempArray.get(strParam);
                for (int j = 0; j < al.size(); j++) {
                    obj = al.get(j);
                    if (obj.getClass().getName().indexOf("java.util.HashMap") >= 0) {
                        hmParam = (HashMap) obj;
                        it = hmParam.keySet().iterator();
                        while (it.hasNext()) {
                            strName = (String) it.next();
                            strVal = (String) hmParam.get(strName);
                            m_serializer.startTag(NAMESPACE, strName);
                            m_serializer.text(strVal);
                            m_serializer.endTag(NAMESPACE, strName);
                        }
                    } else {
                        m_serializer.startTag(NAMESPACE, strParam);
                        m_serializer.text((String) obj);
                        m_serializer.endTag(NAMESPACE, strParam);

                    }
                }
                m_serializer.endTag(NAMESPACE, "Parameters"); // 结束Parameter
            }
            // 文档结束处理
            m_serializer.endTag(NAMESPACE, "Data");
            m_serializer.endTag(NAMESPACE, "Powerise_IBSS");
            strXML = m_bsOut.toString();
        } catch (Exception e) {
            m_Logger.info(strActionId + getStackMsg(e));
            throw new FrameException(-22995102, "处理Dict转换xml入参参数据时发生异常:"
                    + e.getClass().getName(), getStackMsg(e));
        } finally {
            if (m_bsOut != null) {
                try {
                    m_bsOut.close();
                } catch (Exception e) {
                }
                m_bsOut = null;
            }
        }
        return strXML;

    }

    public static DynamicDict callRemoteEJB(DynamicDict dict)
            throws FrameException {
        Object obj = null;
        DynamicDict outDict = null;
//		String strEJBName = "DispatchEJB";
//		com.powerise.ibss.framework.ejb.DispatchEJB disp = null;
//		try {
//			if (ctx == null)
//				ctx = getInitialContext();
//			m_Logger.debug("查找JNDI对象");
//			obj = ctx.lookup(strEJBName);
//			com.powerise.ibss.framework.ejb.DispatchEJBHome dispatchEJBHome = (com.powerise.ibss.framework.ejb.DispatchEJBHome) javax.rmi.PortableRemoteObject
//					.narrow(
//							obj,
//							com.powerise.ibss.framework.ejb.DispatchEJBHome.class);
//			m_Logger.debug("创建EJB本地接口");
//			disp = dispatchEJBHome.create();
//			if (dict.flag == 1)
//				outDict = disp.performWEBDynamicAction(dict);
//			else
//				outDict = disp.perform(dict);
//		} catch (java.rmi.RemoteException re) {
//			throw new FrameException(-100002003, "EJBUtil.DispatchEJB()出现异常！",
//					re.getMessage());
//		} catch (javax.ejb.CreateException re1) {
//			throw new FrameException(-100002002, "EJBUtil.DispatchEJB()出现异常！",
//					re1.getMessage());
//		} catch (Exception ce) {
//			ce.printStackTrace();
//			throw new FrameException(-100002001, "定位JNDI(" + strEJBName
//					+ ")出错！", ce.getMessage());
//		} finally {
//			if (disp != null) {
//				try {
//					disp.remove();
//				} catch (Exception e) {
//				}
//			}
//
//		}

        return outDict;

    }

    /**
     * 远程调用servlet AgentServlet的方法,通过将Dict序列化进行数据的交互
     *
     * @param dict
     * @return
     * @throws FrameException
     */
    public static DynamicDict callRemoteService(DynamicDict dict)
            throws FrameException {
        DynamicDict outDict = null;

        // 获取远程的配置
        String strServ, strPort, strServlet, strData, strEJB;
        String strStaffNo, strSiteNo;
        strServ = getSystemSet("RemoteService", "Server", null);
        strPort = getSystemSet("RemoteService", "Port", null);
        strServlet = getSystemSet("RemoteService", "AgentServlet", null);
        strSiteNo = getSystemSet("RemoteService", "SiteNo", null);
        strStaffNo = getSystemSet("RemoteService", "StaffNo", null);
        strEJB = getSystemSet("RemoteService", "UseEJB", "0");// 缺省不使用EJB
        if (strServ == null || strPort == null || strServlet == null
                || strSiteNo == null || strStaffNo == null)
            throw new FrameException(-22995105, "需要在配置文件中配置RemoteService等参数");
        dict.setValueByName("SITE-NO", strSiteNo, 2);
        dict.setValueByName("STAFF-NO", strStaffNo, 2);
        dict.SetConnection(null);
        if (strEJB.equals("1")) {
            return callRemoteEJB(dict);

        }
        try {
            strData = Base64.encodeObject(dict);
            strData = HttpTool.CallService(strServ, Integer.parseInt(strPort),
                    strServlet, strData);
            outDict = (DynamicDict) Base64.decodeToObject(strData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outDict;
    }

    public static String encryptString(String strSrc) throws FrameException {
        String strDes = null;
        int iLen = 0;
        byte btSrc[] = null;
        byte btTemp[] = strSrc.getBytes();
        iLen = btTemp.length;
        int iPad = iLen % 8;
        if (iPad > 0)
            iLen = iLen + (8 - iPad);
        btSrc = new byte[iLen];

        for (int i = 0; i < btTemp.length; i++) {
            btSrc[i] = btTemp[i];
        }

        try {
            byte btResult[] = DesTool.RunDES(DesTool.ENCRYPT, DesTool.CBC,
                    m_encryptKey, btSrc);
            strDes = DesTool.byteArr2HexStr(btResult);
        } catch (Exception e) {
            throw new FrameException(-22990041, "加解密字符串失败", getStackMsg(e));
        }

        return strDes;
    }

    public static String decryptString(String strSrc) throws FrameException {
        String strDes = null;
        int iLen = 0;
        try {
            byte btSrc[] = DesTool.hexStr2ByteArr(strSrc);
            byte btResult[] = DesTool.RunDES(DesTool.DECRYPT, DesTool.CBC,
                    m_encryptKey, btSrc);
            while (btResult[iLen] != 0)
                iLen++;
            strDes = new String(btResult, 0, iLen);
        } catch (Exception e) {
            throw new FrameException(-22990041, "加解密字符串失败", getStackMsg(e));
        }

        return strDes;
    }

    public static int GeneDict(DynamicDict aDict, Element aElem)
            throws FrameException {
        String strNodeName, strNodeValue;
        ArrayList alList = new ArrayList();
        NodeList nlChild = null; // 子节点
        Element elBO = null;
        DynamicDict newBO = null;
        // 只处理第一个parameters

        // 目前只读parameters=DEFAULT的节点，其余parameters节点忽略
        NodeList nl1 = aElem.getChildNodes();
        // 首先获取各个Element，如果没有子结点则为当前BO的值，根BO只有一个实例，其它子BO可以存在多个
        for (int n = 0; n < nl1.getLength(); n++) {
            Node ePara = nl1.item(n);
            // 必须是Element才进行处理
            if (ePara == null || ePara.getNodeType() != Node.ELEMENT_NODE)
                continue;
            // 检查是否存在Child Element，有的话则是单独的BO:新建Dict
            nlChild = ePara.getChildNodes();
            if (nlChild.getLength() > 1) {
                alList.add(ePara);
                continue;
            }
            //刘武修改
            if (nlChild.getLength() == 1 && nlChild.item(0).hasChildNodes()) {
                alList.add(ePara);
                continue;
            }

            strNodeName = ePara.getNodeName();
            if(nlChild.getLength()==0)
                strNodeValue="";
            else
                strNodeValue = ePara.getFirstChild().getNodeValue();
            aDict.setValueByName(strNodeName, strNodeValue);
        }
        for (int n = 0; n < alList.size(); n++) {
            elBO = (Element) alList.get(n);
            strNodeName = elBO.getNodeName();
            newBO = new DynamicDict();
            newBO.m_ActionId = aDict.m_ActionId;
            GeneDict(newBO, elBO);
            aDict.setValueByName(strNodeName, newBO, 1);
        }

        return 0;
    }

    public static void GeneBOXML(XmlSerializer aSerializer, HashMap hmValue,
                                 Vector vArg, int iLevel) throws FrameException {
        ArrayList alObj = null;
        String strName, strValue = null;
        Object obj = null;
        Set hmset = null;
        DynamicDict newDict = null;
        boolean bGo = false;
        try {
            hmset = hmValue.keySet();
            if (hmset != null) { // hashmap有内容
                Iterator it = hmset.iterator();
                while (it.hasNext()) {
                    strName = (String) it.next();
                    // 对于首层的数据，需要检查是否需要送到前台
                    if (iLevel == 0 && vArg != null) {
                        bGo = false;
                        for (int j = 0; j < vArg.size(); j++) {
                            if (strName.equals(vArg.get(j))) {
                                bGo = true;
                                break;
                            }
                        }
                        if (bGo == false) //没有找到，说明不需要打到前台
                            continue;
                    }
                    obj = hmValue.get(strName);

                    if (obj == null) {
                        continue;
                        //strValue = "";
                    }
                    String objClass=obj.getClass().getName();
                    if (objClass.indexOf("ArrayList") != -1) // 为BO类型
                    {
                        alObj = (ArrayList) obj;
                        //没有内容需要打一个空节点到前台 刘武修改
                        if(isAjax && alObj.size()==0) {
                            aSerializer.startTag(NAMESPACE, strName
                                    .toUpperCase());
                            //aSerializer.attribute(null, "type", "Array");
                            aSerializer
                                    .endTag(NAMESPACE, strName.toUpperCase());
                        }
                        for (int i = 0; (i < alObj.size()) && (i < 10000); i++) {
                            //刘武修改2007-03-21 支持ArrayList中放HashMap
                            Object itemObj=alObj.get(i);
                            HashMap itemMap;
                            String className=itemObj.getClass().getName();
                            if(className.indexOf("HashMap") >=0) {
                                itemMap=(HashMap)itemObj;
                            } else if(className.indexOf("DynamicDict") >=0) {
                                newDict = (DynamicDict) alObj.get(i);
                                itemMap=newDict.m_Values;
                            } else {
                                throw new FrameException(-22990110, strName+"对象不符合要求,数组中只能存放HashMap或者DynamicDict");
                            }

                            aSerializer.startTag(NAMESPACE, strName
                                    .toUpperCase());
                            //aSerializer.attribute(null, "type", "Array");
                            GeneBOXML(aSerializer, itemMap, vArg,
                                    iLevel + 1);
              /*GeneBOXML(aSerializer, newDict.m_Values, vArg,
                       iLevel + 1);*/
                            aSerializer
                                    .endTag(NAMESPACE, strName.toUpperCase());
                        }

                    } else if(objClass.indexOf("HashMap") >=0 || objClass.indexOf("DynamicDict") >=0) {  //刘武增加
                        HashMap itemMap;
                        if(objClass.indexOf("HashMap") >=0)
                            itemMap=(HashMap)obj;
                        else
                            itemMap=((DynamicDict)obj).m_Values;
                        aSerializer.startTag(NAMESPACE, strName
                                .toUpperCase());
                        GeneBOXML(aSerializer, itemMap, vArg,
                                iLevel + 1);
                        aSerializer
                                .endTag(NAMESPACE, strName.toUpperCase());
                    } else {
                        aSerializer.startTag(NAMESPACE, strName.toUpperCase());
                        //m_Logger.info("class name class nameclass nameclass nameclass name:"+obj.getClass());
                        strValue = (String) obj;
                        aSerializer.text(strValue.toString());
                        aSerializer.endTag(NAMESPACE, strName.toUpperCase());
                    }
                }
            }

        } catch (IOException e) {
            throw new FrameException(-22990110, "处理出参数据时出现异常" + e.getMessage(),
                    getStackMsg(e));
        }
    }

    public static boolean isM_FrameDataInMem() {
        return m_FrameDataInMem;
    }

}

