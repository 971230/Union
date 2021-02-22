package com.ztesoft.dao;

import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;



import org.apache.log4j.Logger;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.ibss.common.dao.DAOUtils;

public class DBCon {  
	private static Logger logger = Logger.getLogger(DBCon.class);
    //数据库驱动对象  
    public static final String DRIVER="oracle.jdbc.driver.OracleDriver";  
    //数据库连接地址(数据库名)  
//    public static final String URL="jdbc:oracle:thin:@10.123.99.68:1521:DB11G";  
    public static final String URL="jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS=(PROTOCOL=TCP)(HOST=10.123.180.111)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=10.123.180.113)(PORT=1521)))(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=ecs)))";  
    //登陆名  
    public static final String USER="wssmall_ecs";  
    //登陆密码  
    public static final String PWD="wssmall_ecs";  
    //创建数据库连接对象  
    private Connection con=null;  
    //创建数据库预编译对象  
    private PreparedStatement ps=null;  
    //创建结果集  
    private ResultSet rs=null;  
    //创建数据源对象  
    public static DataSource source=null;  
  
//  //静态代码块  
//  static{  
//  
//      //初始化配置文件context  
//      try {  
//          Context context=new InitialContext();  
//          source=(DataSource)context.lookup("java:comp/env/jdbc/webmessage");  
//      } catch (Exception e) {  
//          // TODO Auto-generated catch block  
//          e.printStackTrace();  
//      }  
//  
//  
//  }  
  
        /**  
         * 获取数据库连接  
         */  
        public Connection getCon(){  
            try {  
                Class.forName(DRIVER);  
            } catch (ClassNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            try {  
                con=DriverManager.getConnection(URL,USER,PWD);  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
              
            return con;  
        }  
//  /**  
//   * 获取数据库连接  
//   */  
//  public Connection getCon(){  
//  
//      try {  
//          con=source.getConnection();  
//      } catch (SQLException e) {  
//          // TODO Auto-generated catch block  
//          e.printStackTrace();  
//      }  
//  
//      return con;  
//  }  
  
  
    /**  
     * 关闭所有资源  
     */  
    public void closeAll(){  
        if(rs!=null)  
            try {  
                rs.close();  
            } catch (SQLException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            if(ps!=null)  
                try {  
                    ps.close();  
                } catch (SQLException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                if(con!=null)  
                    try {  
                        con.close();  
                    } catch (SQLException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    }  
  
  
    }  
    /**  
     * @param sql数据库更新(增、删、改) 语句      
     * @param pras参数列表（可传，可不传，不传为NULL，以数组形式存在）  
     * @return 返回受影响都行数  
     */  
    public int update(String sql,String... pras){  
        int resu=0;  
        con=getCon();  
        try {  
            ps=con.prepareStatement(sql);  
            for(int i=0;i<pras.length;i++){  
                ps.setString(i+1,pras[i]);  
            }  
            resu=ps.executeUpdate();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        finally{  
            closeAll();  
        }  
        return resu;  
    }  
  
    /**  
     * @param sql数据库查询语句  
     * @param pras参数列表（可传，可不传，不传为NULL，以数组形式存在）  
     * @return 返回结果集  
     */  
    public ResultSet query(String sql,String... pras){  
        con=getCon();  
        try {  
            ps=con.prepareStatement(sql);  
  
            if(pras!=null)  
                for(int i=0;i<pras.length;i++){  
                    ps.setString(i+1, pras[i]);  
                }  
            rs=ps.executeQuery();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return rs;  
    }  
  
    
	/**
	 * 查询返回Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public HashMap queryForMap(String sql) throws FrameException, SQLException {
		getCon();
		HashMap retMap = new HashMap();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMap = convertToMap(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeAll();
		}
		return retMap;
	}
	

	public static HashMap convertToMap(ResultSet rs) throws SQLException {
		HashMap retMap = new HashMap();
		ResultSetMetaData meatData = rs.getMetaData();
		for (int i = 1; i <= meatData.getColumnCount(); i++) {
			String name = meatData.getColumnLabel(i).toLowerCase();
			int type = meatData.getColumnType(i);
			if (Types.TIME == type || Types.TIMESTAMP == type) {
				String value = DAOUtils.getFormatedDateTime(rs.getTimestamp(name));
				retMap.put(name, value);
			} else if (Types.VARCHAR == type) {
				retMap.put(name, rs.getString(name));
			} else if (Types.INTEGER == type || Types.BIGINT == type) {
				retMap.put(name, String.valueOf(rs.getInt(name)));
			} else if (Types.BIGINT == type || Types.NUMERIC == type) {
				retMap.put(name, rs.getBigDecimal(name));
			} else if (Types.BLOB == type) {
				Blob blob = rs.getBlob(name);
				byte[] values = null;
				if (blob != null && blob.length() > 0)
					values = blob.getBytes(1, (int) blob.length());
				retMap.put(name, values);
			} else if (Types.CLOB == type) {
				Clob  clob =  rs.getClob(name);
				String values = null;
				if (clob != null && clob.length() > 0)
					values = Clob2String(clob);
				retMap.put(name, values);
			} else {
				Object value = rs.getObject(name);
				retMap.put(name, value);
			}
		}
		return retMap;
	}
	
	public static String Clob2String(Clob clob) {// Clob转换成String 的方法
		  String content = null;
		  StringBuffer stringBuf = new StringBuffer();
		  try {
		   int length = 0;
		   Reader inStream = clob.getCharacterStream(); // 取得大字侧段对象数据输出流
		   char[] buffer = new char[10];
		   while ((length = inStream.read(buffer)) != -1) // 读取数据库 //每10个10个读取
		   {
		    for (int i = 0; i < length; i++) {
		     stringBuf.append(buffer[i]);
		    }
		   }

		   inStream.close();
		   content = stringBuf.toString();
		  } catch (Exception ex) {
		   logger.info("ClobUtil.Clob2String:" + ex.getMessage());
		  }
		  return content;
		 }

	
	/**
	 * 查询返回String
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public String queryForString(String sql) throws FrameException,
			SQLException {
		getCon();
		String retMsg = "";
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs != null && rs.next()) {
				retMsg = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeAll();
		}
		return retMsg;
	}
  
	
	/**
	 * 查询返回list list中装载着Map
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws FrameException
	 * @throws SQLException
	 */
	public List queryForList(String sql) throws FrameException, SQLException {
		getCon();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			List results = new ArrayList();
			while (rs != null && rs.next()) {
				results.add(convertToMap(rs));
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
				rs=null;
			}
			if (pst != null) {
				pst.close();
				pst=null;
			}
			closeAll();
		}
	}
}  
