/**
错误号：1000005
getTsmStaff 取操作人信息
getRole 取操作人角色
isValidTerminal 验证终端
getFun  取可操作功能
getAllFun 取所有功能
getRoleFun 取角色包括的功能
*/
package com.powerise.ibss.system;

import com.powerise.ibss.framework.FrameException;

import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

public class TsmStaff{

    public Statement st;
    private String sql;
    private static boolean DEBUG = false;//调试状态开关
    private static Logger logger = Logger.getLogger(TsmStaff.class);
    public String getStaffNo(String staffCode, Connection connection) throws FrameException{
    	String staff_no = "";
        try{
			if (st == null) st = connection.createStatement();
            ResultSet _rs = st.executeQuery("SELECT staff_no FROM tsm_staff WHERE staff_code='"+staffCode+"'");
            if (_rs.next()) staff_no = _rs.getString(1);
            _rs.close();
        }catch(SQLException sqle){
            throw new FrameException(-1000003, "出现SQL异常", sqle.getMessage());
        }
        return staff_no;
    }
    
    public ResultSet getTsmStaff(String s, Connection connection) throws FrameException{
		String sql= "SELECT staff_code,site_code,a.staff_no,site_no,bureau_no,staff_name,password, "+
				   	"		title_type,auth_level,state,rela_phone,site_name,limit_machine_flag, "+
					"		bureau_name,city_no,title_name,title_id,area_code "+
					"  FROM (SELECT tsm_staff.staff_no,tsm_staff.staff_code, tsm_staff.site_no, tsm_staff.bureau_no, tsm_staff.staff_name, "+
					"			    tsm_staff.password,tsm_staff.title_type, tsm_staff.auth_level, "+
					"  			    tsm_staff.state, tsm_staff.rela_phone,tsm_site.site_code,tsm_site.site_name, tsm_site.limit_machine_flag, "+
					"			    tvlsm_bureau.bureau_name,tvlsm_bureau.city_no,tvlsm_bureau.area_code "+
					"	       FROM tsm_staff, tsm_site, tvlsm_bureau "+
					"	      WHERE tsm_staff.site_no=tsm_site.site_no "+
					"		    AND tsm_staff.bureau_no=tvlsm_bureau.bureau_no) a, "+
					"	    (SELECT title_name,a.title_type title_id,staff_no "+
					"	       FROM tsm_staff a,tvlsm_title b "+
					"		  WHERE a.title_type=b.title_id) b "+
					" WHERE rownum<2 and a.staff_no=b.staff_no(+) "+
					"   AND a.staff_no='"+s+"'";
		if(DEBUG)//logger.info("system.sql="+sql);
			logger.info("system.sql="+sql);
		try{
			st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			return st.executeQuery(sql);
		}catch(SQLException sqle){
			throw new FrameException(-1000001,"查询员工信息表出错！"+"\n SQL="+sql, sqle.getMessage());
		}
    }

    public HashMap getRole(String s, Connection connection) throws FrameException{
		sql =	"SELECT tsm_staff_role.role_id,tsm_role.role_name "+
				"  FROM tsm_staff_role,tsm_role " +
		  		" WHERE tsm_role.role_id = tsm_staff_role.role_id "+
		  		"	AND tsm_staff_role.staff_no='"+s+"'"; 		
		sql =	sql+"union SELECT a.role_id,b.role_name "+
				"  FROM tsm_site_role a,tsm_role b,tsm_staff c " +
		  		" WHERE b.role_id = a.role_id "+
		  		"	AND a.site_no=c.site_no and " +
		  		" c.staff_no='"+s+"'"; 		
		try{
			if (st != null) st = connection.createStatement();
			ResultSet resultset = st.executeQuery(sql);
			HashMap hashmap = new HashMap();
			while(resultset.next()){
				if (resultset.getObject("role_id") != null &&
					resultset.getObject("role_name") != null){
		        	hashmap.put(resultset.getObject("role_id"), resultset.getObject("role_name"));
				}
			}
			return hashmap;
		}catch(SQLException sqle){
			throw new FrameException(-1000002,"查询员工信息表出错！"+"\n SQL="+sql, sqle.getMessage());
		}
    }

    public boolean isValidTerminal(String s, Connection connection)
        throws FrameException
    {
        boolean flag = false;
        try
        {
            if(st == null)
            {
                st = connection.createStatement();
            }
            ResultSet resultset = st.executeQuery("SELECT count(*) nums FROM tsm_terminal WHERE terminal_ip='" + s + "'");
            resultset.next();
            if(resultset.getLong("nums") != 0L)
            {
                flag = true;
            }
            resultset.close();
        }catch(SQLException sqle){
            throw new FrameException(-1000003, "出现SQL异常", sqle.getMessage());
        }
        return flag;
    }
    
    public Vector getFun(String s, Connection connection)
        throws SQLException
    {
        sql = " SELECT distinct a.fun_id, a.fun_name, a.up_fun_id, a.menu_url, a.param,a.dis_order, a.state" 
+"   FROM tsm_fun a"
+"   WHERE state='1'"
+"  START WITH fun_id IN ( " 
+"                SELECT b.fun_id "
+"                  FROM tsm_role_fun b, tsm_staff_role c"
+"                 WHERE b.TYPE = '0' "
+"                   AND b.role_id = c.role_id "
+"                   AND c.staff_no = '" +s+"' "
+"                   AND NOT EXISTS (SELECT 1 FROM tsm_staff_fun x WHERE x.staff_no=c.staff_no AND x.fun_type='0' AND x.priv_type='0' AND x.fun_id=b.fun_id)"
+"                UNION"
+" 			   SELECT b.fun_id "
+"                  FROM tsm_role_fun b, tsm_site_role c,tsm_staff a  "
+"                 WHERE a.site_no=c.site_no"
+" 				  AND a.staff_no = '" +s+"' "
+"                   AND b.TYPE = '0' "
+"                   AND b.role_id = c.role_id "
+"                   AND NOT EXISTS (SELECT 1 FROM tsm_staff_fun x WHERE x.staff_no=a.staff_no AND x.fun_type='0' AND x.priv_type='0' AND x.fun_id=b.fun_id)"
+" 				  )"
+" CONNECT BY fun_id = PRIOR up_fun_id "
+" order by a.up_fun_id, a.dis_order ";
        if(st == null)
        {
            st = connection.createStatement();
        }
        ResultSet resultset = st.executeQuery(sql);
        ResultSetMetaData resultsetmetadata = resultset.getMetaData();
        String as[] = new String[resultsetmetadata.getColumnCount()];
        for(int i = 1; i <= resultsetmetadata.getColumnCount(); i++)
        {
            as[i - 1] = resultsetmetadata.getColumnName(i);
        }

        Vector vector = new Vector();
        //性能修改，
        String strVal,strName;
        HashMap hashmap;
        for(; resultset.next(); vector.add(hashmap))
        {
            hashmap = new HashMap();
           
            for(int j = 0; j < as.length; j++)
            {
            	strName = as[j].toLowerCase();
            	strVal = resultset.getString(strName);
                if(resultset.wasNull()== false)
                {                   
                    hashmap.put(strName,strVal);
                }
            }

        }

        resultset.getStatement().close();
        if(resultset != null)
        {
            resultset.close();
        }
        return vector;
    }

    public Vector getAllFun(Connection connection)
        throws FrameException
    {
        Object obj = null;
        try
        {
            sql = "SELECT distinct tsm_fun.fun_id, tsm_fun.fun_type, tsm_fun.up_fun_id, tsm_fun.fun" +
            "_name, tsm_fun.menu_name, tsm_fun.param, tsm_fun.fun_desc, tsm_fun.leveal_index," +
            " tsm_fun.state, tsm_fun.quick_tab, tsm_fun.main_window, tsm_fun.function_name, t" +
            "sm_fun.child_count, tsm_fun.file_name, tsm_fun.type, tsm_fun.menu_url, up_tsm_fu" +
            "n.fun_name AS up_fun_name, up_tsm_fun.up_fun_id AS upper_fun_id, upper_tsm_fun.f" +
            "un_name AS upper_fun_name, tsm_fun.dis_order  FROM tsm_fun, tsm_fun up_tsm_fun, " +
            "tsm_fun upper_tsm_fun WHERE tsm_fun.up_fun_id = up_tsm_fun.fun_id(+) AND up_tsm_" +
            "fun.up_fun_id = upper_tsm_fun.fun_id(+) AND tsm_fun.type = '1' order by tsm_fun." +
            "up_fun_id , tsm_fun.dis_order"
            ;
            if(st == null)
            {
                st = connection.createStatement();
            }
            ResultSet resultset = st.executeQuery(sql);
            ResultSetMetaData resultsetmetadata = resultset.getMetaData();
            String as[] = new String[resultsetmetadata.getColumnCount()];
            for(int i = 1; i <= resultsetmetadata.getColumnCount(); i++)
            {
                as[i - 1] = resultsetmetadata.getColumnName(i);
            }

            Vector vector = new Vector();
            HashMap hashmap;
            for(; resultset.next(); vector.add(hashmap))
            {
                hashmap = new HashMap();
                for(int j = 0; j < as.length; j++)
                {
                    if(resultset.getObject(as[j].toLowerCase()) != null)
                    {
                        Object obj1 = resultset.getObject(as[j].toLowerCase());
                        hashmap.put(as[j].toLowerCase(), obj1.toString());
                    }
                }

            }

            if(resultset != null)
            {
                resultset.close();
            }
            return vector;
      } catch(SQLException ex) {
         throw new FrameException(-1000004, "执行SQL出错！\nSQL="+sql, ex.getMessage());
      }
    }
    public Vector getRoleFun(String role_id, Connection connection)
        throws SQLException
    {
        sql = "select distinct a.fun_id , a.fun_name, a.up_fun_id, a.menu_url, a.state " +
              " from tsm_fun a, tsm_role_fun b " +
              " where a.type=1 " +
              " and a.fun_id=b.fun_id " +
              " and b.role_id ='"+role_id+
              "' order by a.fun_id";
        if(st == null)
        {
            st = connection.createStatement();
        }
        ResultSet resultset = st.executeQuery(sql);
        ResultSetMetaData resultsetmetadata = resultset.getMetaData();
        String as[] = new String[resultsetmetadata.getColumnCount()];
        for(int i = 1; i <= resultsetmetadata.getColumnCount(); i++)
        {
            as[i - 1] = resultsetmetadata.getColumnName(i);
        }

        Vector vector = new Vector();
        HashMap hashmap;
        for(; resultset.next(); vector.add(hashmap))
        {
            hashmap = new HashMap();
            for(int j = 0; j < as.length; j++)
            {
                if(resultset.getObject(as[j].toLowerCase()) != null)
                {
                    Object obj = resultset.getObject(as[j].toLowerCase());
                    hashmap.put(as[j].toLowerCase(), obj.toString());
                }
            }

        }

        resultset.getStatement().close();
        if(resultset != null)
        {
            resultset.close();
        }
        return vector;
    }
}
