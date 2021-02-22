package com.powerise.ibss.cs;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.framework.IAction;
import com.powerise.ibss.util.SysSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Powerise</p>
 * @author Ryan Shen
 * @version 1.0
 */

public class Scsfun {
	
	private static Logger logger = Logger.getLogger(Scsfun.class);
  public Scsfun() {
  }

  /**
   * 调用接口，根据DynamicDict提供的组件名称和类实现信息建立实例并调用
   * @param oDict
   * @return int
   * @throws com.powerise.ibss.framework.FrameException
   * @roseuid 3EF7DDB00100
   */
  public static int callExternal(DynamicDict oDict) throws FrameException {
    Connection conn = oDict.GetConnection();
    Statement st = null;
    ResultSet rs = null;
    //get class name
    String classname = null;
    String servicename = oDict.getServiceName().toUpperCase().trim();
    try {
      st = conn.createStatement();
      rs = st.executeQuery("select class_name from tfm_services where ltrim(rtrim(upper(service_name))) = '" +
                           servicename + "'");
      rs.next();
      classname = rs.getString(1).trim();
      rs.close();
      st.close();
    }
    catch (SQLException e) {
      throw new FrameException( -22050221, "创建指定的类错误:系统服务号" + servicename);
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (st != null) {
          st.close();
        }
      }
      catch (Exception ee) {
    	  throw new FrameException( -22050222, "创建指定的类错误," + ee.getMessage());
      }
    }

    //execute
    try {
      return ( (IAction) Class.forName(classname).newInstance()).perform(oDict);
    }
    catch (ClassNotFoundException e) {
      throw new FrameException( -2205022, "指定的类不存在:" + classname);
    }
    catch (InstantiationException e) {
      throw new FrameException( -22050223, "创建指定的类错误:" + classname);
    }
    catch (IllegalAccessException e) {
      throw new FrameException( -22050224, "创建指定的类错误:" + classname);
    }
  }

  /**
   * 功能：以aDict为样板，建立新的dict并返回
   * 入参：aDict DynamicDict类型
   * 出参：dict  DynamicDict类型
   * @param aDict
   * @param actId
   * @return com.powerise.ibss.framework.DynamicDict
   * @throws com.powerise.ibss.framework.FrameException
   * @roseuid 3EF8F19E01B2
   */
  public static DynamicDict newDict(DynamicDict aDict, String actId) throws
      FrameException {
    DynamicDict dict = new DynamicDict();
    dict.m_ActionId = actId;
    dict.SetConnection(aDict.GetConnection());
    dict.setValueByName("SITE-NO", aDict.getValueByName("SITE-NO"));
    dict.setValueByName("SITE-NAME", aDict.getValueByName("SITE-NAME"));
    dict.setValueByName("BUREAU-NO", aDict.getValueByName("BUREAU-NO"));
    dict.setValueByName("BUREAU-NAME", aDict.getValueByName("BUREAU-NAME"));
    dict.setValueByName("STAFF-NO", aDict.getValueByName("STAFF-NO"));
    dict.setValueByName("STAFF-NAME", aDict.getValueByName("STAFF-NAME"));
    dict.setValueByName("AUTH-LEVEL",
                        aDict.getCountByName("AUTH-LEVEL") == 1 ?
                        aDict.getValueByName("AUTH-LEVEL") : "");
    dict.setValueByName("TITLE-NAME",
                        aDict.getCountByName("TITLE-NAME") == 1 ?
                        aDict.getValueByName("TITLE-NAME") : "");
    dict.setValueByName("TITLE-ID",
                        aDict.getCountByName("TITLE-ID") == 1 ?
                        aDict.getValueByName("TITLE-ID") : "");
    return dict;
  }

  /**
   * 参数转换接口
   * DynamicDict内的Hashmap->普通变量组
   * @param hash
   * @param oDict
   * @throws com.powerise.ibss.framework.FrameException
   * @roseuid 3EF7DD350278
   */
  public static void hashmap2dict(HashMap hash, DynamicDict oDict) throws
      FrameException {
    Set st = null;
    Iterator it = null;
    String nam, val;

    if (hash != null) {
      st = hash.keySet();
    }
    if (st != null) {
      it = st.iterator();
      while (it.hasNext()) {
        nam = (String) it.next();
        val = (String) hash.get(nam);
        oDict.setValueByName(nam, val, 1);
      }
    }
  }


	/**
	 * 功能：取ibss.xml配置参数
	 *
	 * @throws FrameException
	 */
	public static String getIBSS_Info() {
		// 获取远程的配置
		String str_province_code = "";
		try {
			str_province_code = SysSet.getSystemSet("System", "PROVINCE_CODE",
					null);
			if (str_province_code == null)
				throw new FrameException(-006001035, "需要在配置文件中配置PROVINCE_CODE参数");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str_province_code;
	}
	
	
	/************************************************************************************************
	* 函数功能：	获取唯一序列											*
	* 入口参数：	strSeqName		序列名称								*
	*		intSeqLength		获取序列的长度								*
	*		strSysTime		是否在序列前面加上系统时间 0加上8位年月日  1加上14位年月日时分秒 2不加	*		
	************************************************************************************************/
	public static String fucGetSeq(DynamicDict oDict,String strSeqName,int intSeqLength, String strSysDate){
		logger.info("start&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入了fucGetSeq");
		//logger.info("start&&&&&&&&&&&&&&&&&&&&&&&&&&&&&进入了fucGetSeq");
		Connection conn = oDict.GetConnection();
	    Statement st = null;
	    ResultSet rs = null;
		String StrDbtype="";
		
		String sql="";
		String seq="";
		try{
			
			//取出配置文件中，配置的数据库类型
			StrDbtype=SysSet.getSystemSet("System", "Dbtype", null);
			//logger.info("##################################StrDbtype="+StrDbtype);
			logger.info("##################################StrDbtype="+StrDbtype);
			if (StrDbtype == null)
				throw new FrameException(-006001035, "需要在配置文件中配置Dbtype参数");
			//logger.info("$$$$$$$$$$$$StrDbtype="+StrDbtype);
			logger.info("$$$$$$$$$$$$StrDbtype="+StrDbtype);
			if(StrDbtype.trim().toUpperCase().equals("ORACLE")){
				//ORACLE数据库
				if(strSysDate.trim().equals("0")){
					//加上6位年月日
				sql="SELECT to_char(sysdate,'yyyymmdd')||LPAD("+strSeqName+".NEXTVAL,"+intSeqLength+"-8,'0')  seq  FROM DUAL";
				
				}
				else if(strSysDate.trim().equals("1")){
				//加上12位年月日时分秒
				sql="select to_char(sysdate,'yyyymmddhhmmss')||LPAD("+strSeqName+".NEXTVAL,"+intSeqLength+"-14,'0') seq from dual ";
				
				}
				else if(strSysDate.trim().equals("2")){
				//不加年月日，直接取
				sql="select LPAD("+strSeqName+".NEXTVAL,"+intSeqLength+",'0') seq from dual";
				
				}
				else{
					sql="select "+strSeqName+".NEXTVAL from dual";
				}
				//logger.info("sql="+sql);
				logger.info("sql="+sql);
				//logger.info("conn="+conn);
				logger.info("conn="+conn);
				
				
				
				st = conn.createStatement();															
				rs =st.executeQuery(sql);
				if(rs.next()){
					 seq=rs.getString(1);
					 
				}
				else
					throw new FrameException("取序号出错!");
				
				if(st!=null)st.close();
				if(rs!=null)rs.close();	
			
			}
			else if(StrDbtype.trim().toUpperCase().equals("SYSBASE")){
				
			}
			logger.info("退出了fucGetSeq。。。。。。。。。");
			//logger.info("退出了fucGetSeq。。。。。。。。。");
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if(st!=null){
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return seq;
	}

	/**
	 * 功能：
	 *
	 * @throws FrameException
	 */
  	public static int fucJudge(DynamicDict oDict) throws FrameException {
  		return 1;
	}
	
		/**
	 * 功能：取开关信息
	 *
	 * @param CF_ID
	 *            开关参数ID
	 * @throws FrameException
	 */
	public static String getCF_Info(Connection conn, String strCF_ID) {
		PreparedStatement pst = null;
		String strCF_Value = "";
		try {
			pst = conn
					.prepareStatement("select CF_VALUE from TCC_CONFIG_INFO where CF_ID = ?");
			pst.setString(1, strCF_ID);
			ResultSet rs = pst.executeQuery();
			if (rs.next())
				strCF_Value = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pst != null)
					pst.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return strCF_Value;
	}
	
	 public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}