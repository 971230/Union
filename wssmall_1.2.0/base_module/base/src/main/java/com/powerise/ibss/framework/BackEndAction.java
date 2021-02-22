/*
 * Created on 2004-12-17

 */
package com.powerise.ibss.framework;

import com.powerise.ibss.util.SysSet;
import org.apache.log4j.Logger;

import javax.transaction.UserTransaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
/**
 * @author Administrator
 *
 * TODO 后台调用通用查询的处理 Window - Preferences - Java - Code Style - Code Templates
 */
public class BackEndAction {
    public static final int DATATYPE_INT = 1;
    public static final int DATETYPE_STRING = 2;
    public static final int DATATYPE_LONG = 3;
    public static final int DATATYPE_DOUBLE = 4;
    public static final int NORMAL_TYPE = 0; //正常模式下的通用查询
    public static final int TABLE_TYPE = 1; //查询静态表的方式，第一个字段作为value,第二个字段作为text，只考虑前面两个字段
    private UserTransaction m_usTran = null;
    private Connection m_Conn = null;
    private PreparedStatement m_prst = null;
    private ArrayList m_arResult = null;
    private String m_Id = null;
    public int m_QueryType = NORMAL_TYPE;
    private static Logger m_Logger = Logger.getLogger(BackEndAction.class);
    private boolean m_UseDict = false;

    //通过设置标志来判断参数值是否直接从DynamicDict中来取
    private void useDict(boolean bDict)
    {
        m_UseDict = bDict;
    }
    public ArrayList getResultSet() {
        return m_arResult;
    }
    public void setQueryType(int iType) {
        m_QueryType = iType;
    }
    private ArrayList convertResultSet(ResultSet rs) throws FrameException {
        ArrayList arr = new ArrayList();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            String[] arr1 = new String[cols];
            for (int i = 1; i <= cols; i++) {
                arr1[i - 1] = rsmd.getColumnName(i);
            }
            if (m_QueryType == TABLE_TYPE && arr1.length < 2)
                throw new FrameException(-22990015, "静态表数据查询返回的字段数目小于2:" + m_Id);
            HashMap fields;
            while (rs.next()) {
                fields = new HashMap();
                if (m_QueryType == TABLE_TYPE) {
                    fields.put("value", rs.getString(1));
                    fields.put("text", rs.getString(2));
                } else {
                    for (int i = 1; i <= cols; i++) {
                        fields.put(arr1[i - 1], rs.getString(i));
                    }
                }
                arr.add(fields);
            }
            arr1 = null;
        } catch (SQLException sqle) {
            throw new FrameException(-12990013, "把ResultSet转换成ArrayList出错", sqle.getMessage());
        }
        return arr;
    }
    private void setArgVal(Vector vArg, String strArgName, String strArgVal) throws FrameException {
        ActionArg actArg;
        int iSize;
        iSize = vArg.size();
        strArgVal = strArgVal.trim();
        strArgName = strArgName.toUpperCase().trim();
        for (int i = 0; i < iSize; i++) {
            actArg = (ActionArg) vArg.get(i);
            if (actArg.m_Name.equals(strArgName)) {
                try {
                    switch (actArg.m_Type) {
                        case DATATYPE_INT:
                            m_prst.setInt(actArg.m_Seq, Integer.parseInt(strArgVal));
                            break;
                        case DATETYPE_STRING:
                            m_prst.setString(actArg.m_Seq, strArgVal);
                            break;
                        case DATATYPE_LONG:
                            m_prst.setLong(actArg.m_Seq, Long.parseLong(strArgVal));
                            break;
                        case DATATYPE_DOUBLE:
                            m_prst.setDouble(actArg.m_Seq, Double.parseDouble(strArgVal));
                            break;
                        default:
                            throw new FrameException(-22991002, "绑定参数时发现未定义的数据类型:" + m_Id);
                    }
                } catch (SQLException e) {
                    throw new FrameException(-22991003, "绑定参数时发现数据库异常:" + m_Id, e.getMessage());
                }
            }
        }
    }
    private ActionData filterArgs(String strId, ActionData actData, HashMap hmArgs) throws FrameException {
        int iSize;
        ActionArg actArg = null;
        Vector vArg = actData.m_Args;
        String strSQL = null;
        Vector vNewArg = null;
        int iStart = 0, iCurPos = 0;
        if (vArg != null) {
            iSize = vArg.size();
            strSQL = actData.m_SQL;
            for (int i = 0; i < iSize; i++) {
                actArg = (ActionArg) vArg.get(i);
                if (actArg.m_Flag == 3) //入参标志 ,为3 表示需要替换的参数
                {
                    //                  查找seq指定的?在字符串的位置
                    iStart = strSQL.indexOf('?', 0);
                    int iNewSeq = actArg.m_Seq - iCurPos;
                    for (int k = 1; k < iNewSeq; k++) {
                        iStart = strSQL.indexOf('?', iStart + 1);
                        if (iStart == -1)
                            throw new FrameException(-22991004, strId + "配置的可替换的参数设置的序号与实际不符" + actArg.m_Name);
                    }
                    //直接替换内部可以识别的字符串,在绑定入参值时比较好地替换
                    strSQL = strSQL.substring(0, iStart) + hmArgs.get(actArg.m_Name) + strSQL.substring(iStart + 1);
                    iCurPos++; //计算当前已经替换了的替换参数数目
                    continue; //跳过,继续下一个处理
                }
                actArg.m_Seq = actArg.m_Seq - iCurPos;
                if (vNewArg == null)
                    vNewArg = new Vector();
                vNewArg.add(actArg);
            }
            if (iCurPos > 0) {
                vArg.clear();
                vArg = null;
                //换成新的参数
                actData.m_SQL = strSQL;
                actData.m_Args = vNewArg;
            } else if (vNewArg != null) {
                vNewArg.clear();
                vNewArg = null;
            }
        }
        return actData;
    }
    private int execute(String strActionId, Connection conn, Object obj, int iType) throws FrameException {
        ActionData actData = null;
        HashMap hmArgs = null;
        Iterator it = null;
        int iSize = 0;
        int iRows = 0;
        String strArgName = null;
        int iCalc = 0;
        long lCalc = 0;
        //计算时间
        long lStart = 0;
        java.util.Date dStart, dEnd;
        lStart = System.currentTimeMillis();
        dStart = new java.util.Date();
        try {
            iCalc = Integer.parseInt(SysSet.getSystemSet("ConsumeSecs", "3000"));
        } catch (Exception e) {
            iCalc = 3000;
        }
        m_Id = strActionId;
        actData = SysSet.getActionData(strActionId, conn);//在多数据库模式,conn是会被忽略的
        //如果obj是DynamicDict类型，需要进行转换成HashMap
        if(m_UseDict == true)
        {
            if(actData.m_Args == null) //没有参数
                obj = null;
            else{
                ActionArg actArg = null;
                Object o =null;
                DynamicDict dict = (DynamicDict)obj;
                HashMap hmDictArg = new HashMap();
                for(int i = 0;i<actData.m_Args.size();i++)
                {
                    actArg =(ActionArg) actData.m_Args.get(i);
                    o = dict.getValueByName(actArg.m_Name);
                    if(o.getClass().getName().endsWith("String")==false)
                        throw new FrameException(-22991011,"后台通过Dict查询必须是String类型数据");
                    hmDictArg.put(actArg.m_Name,o);
                }
                obj = hmDictArg;
            }
        }
        //对于多数据库模式下，需要判断是否采用xa
        if (SysSet.isMultiDB()) {
            m_Conn = SysSet.getMultiConnection(strActionId, 1);
        } else {
            m_Conn = conn;
        }
        try {
            //判断参数是否为null,不为null才处理
            if (obj != null) {
                //处理是否存在替换的参数
                if (iType == 1 || iType == 2)
                    filterArgs(strActionId, actData, (HashMap) obj);
            }
            //只有过滤了可替换的参数之后，才能得到正确执行的sql
            m_prst = m_Conn.prepareStatement(actData.m_SQL);
            if (obj != null) {
                if (iType == 1 || iType == 2) //单条参数数据
                {
                    hmArgs = (HashMap) obj;
                    iSize = 1;
                } else if (iType == 3) {
                    iSize = ((ArrayList) obj).size();
                    if (iSize > 0)
                        hmArgs = (HashMap) ((ArrayList) obj).get(0);
                }
            } else
                iSize = 1; //只适用于查询,对于更新操作将会忽略
            //判定参数
            for (int i = 0; i < iSize; i++) {
                if (i > 0)
                    hmArgs = (HashMap) ((ArrayList) obj).get(i);
                if (hmArgs != null) {
                    it = hmArgs.keySet().iterator();
                    while (it.hasNext()) {
                        strArgName = (String) it.next();
                        setArgVal(actData.m_Args, strArgName, (String) hmArgs.get(strArgName));
                    }
                }
                //开始执行
                if (iType == 1) //查询处理
                {
                    ResultSet rs = m_prst.executeQuery();
                    //处理结果集
                    m_arResult = convertResultSet(rs);
                } else if (iType == 2 || iType == 3) {
                    iRows += m_prst.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new FrameException(-22991005, strActionId + "执行时出现数据库错误:" + e.getMessage(), SysSet.getStackMsg(e));
        } finally {
            if (m_prst != null) {
                try {
                    m_prst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m_prst = null;
            }
        }
        lCalc = System.currentTimeMillis() - lStart;

        if (lCalc >= iCalc) {
            dEnd = new java.util.Date();
            StringBuffer logBuf = new StringBuffer();
            logBuf.append("Service:");
            logBuf.append(strActionId);
            logBuf.append("/");
            logBuf.append(dStart);
            logBuf.append("/");
            logBuf.append(dEnd);
            logBuf.append("/");
            logBuf.append(lCalc);
            logBuf.append("/");
            logBuf.append(" ");
            m_Logger.warn(logBuf.toString());
            logBuf = null;
        }
        Monitor.setInfo("Service:"+strActionId, lCalc);
        return iRows;
    }
    /**
     * 通过传入DynamicDict自动匹配参数，因此Dict中的参数名必须与配置中的参数名同名，而且Dict中的参数值类型必须是String，如果是HashMap之类的则会报错
     * @param strActionId
     * @param dict
     * @return
     * @throws FrameException
     */
    public static ArrayList executeQuery(String strActionId, DynamicDict dict) throws FrameException {
        ArrayList arResult = null;
        BackEndAction act = new BackEndAction();
        act.useDict(true);
        act.setQueryType(NORMAL_TYPE);
        act.execute(strActionId, dict.GetConnection(), dict, 1);
        arResult = act.getResultSet();
        act = null;
        return arResult;
    }
    /**
     * 通过传入DynamicDict自动匹配参数，因此Dict中的参数名必须与配置中的参数名同名，而且Dict中的参数值类型必须是String，如果是HashMap之类的则会报错
     * @param strActionId
     * @param dict
     * @return
     * @throws FrameException
     */
    public static int executeUpdate(String strActionId, DynamicDict dict) throws FrameException {
        int iRows = 0;
        BackEndAction act = new BackEndAction();
        act.useDict(true);
        act.setQueryType(BackEndAction.NORMAL_TYPE);
        iRows = act.execute(strActionId, dict.GetConnection(), dict, 2);
        act = null;
        return iRows;
    }
    /**
     * 通用查询,返回的是ArrayList+HashMap的格式数据 对于多数据库模式下,conn会忽略,如果没有参数,hmArgs可以为null
     *
     * @param strActionId
     * @param hmArgs
     *            名称-值格式的入参数据,其中名称必须是大写
     * @param conn
     * @return
     * @throws FrameException
     */
    public static ArrayList executeQuery(String strActionId, HashMap hmArgs, Connection conn) throws FrameException {
        return executeQuery(strActionId, hmArgs, conn, NORMAL_TYPE);
    }
    /**
     * 通用查询,返回的是ArrayList+HashMap的格式数据 对于多数据库模式下,conn会忽略,如果没有参数,hmArgs可以为null
     *
     * @param strActionId
     * @param hmArgs
     * @param conn
     * @param iQryType
     *            iQryType=0时，为正常模式的查询，iQryType=1时，是针对处理两个字段的静态表查询，而且第一个字段在返回数据中取名为value,第二个字段为text
     * @return
     * @throws FrameException
     */
    public static ArrayList executeQuery(String strActionId, HashMap hmArgs, Connection conn, int iQryType) throws FrameException {
        ArrayList arResult = null;
        BackEndAction act = new BackEndAction();
        act.setQueryType(iQryType);
        act.execute(strActionId, conn, hmArgs, 1);
        arResult = act.getResultSet();
        act = null;
        return arResult;
    }
    /**
     * 执行单条数据的update操作 在多数据库模式下,conn会被忽略
     *
     * @param strActionId
     * @param arArgs
     *            名称-值格式的入参数据,其中名称必须是大写
     * @param conn
     * @return
     * @throws FrameException
     */
    public static int executeUpdate(String strActionId, HashMap hmArgs, Connection conn) throws FrameException {
        int iRows = 0;
        BackEndAction act = new BackEndAction();
        act.setQueryType(BackEndAction.NORMAL_TYPE);
        iRows = act.execute(strActionId, conn, hmArgs, 2);
        act = null;
        return iRows;
    }
    /**
     * 执行多条参数的update操作 在多数据库模式下,conn会被忽略
     *
     * @param strActionId
     * @param arArgs
     *            (名称-值)数组格式下的入参数据,多条,其中参数名称必须是大写
     * @param conn
     * @return
     * @throws FrameException
     */
    public static int executeUpdate(String strActionId, ArrayList arArgs, Connection conn) throws FrameException {
        int iRows = 0;
        BackEndAction act = new BackEndAction();
        act.setQueryType(BackEndAction.NORMAL_TYPE);
        iRows = act.execute(strActionId, conn, arArgs, 3);
        act = null;
        return iRows;
    }
}
