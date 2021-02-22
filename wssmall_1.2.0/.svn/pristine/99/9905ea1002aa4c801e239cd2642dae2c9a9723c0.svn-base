package com.powerise.ibss.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.query.SqlMapExe;
import com.ztesoft.common.util.BaseTools;
import com.ztesoft.ibss.common.dao.DAOSQLUtils;
import com.ztesoft.ibss.common.dao.DAOUtils;
import com.ztesoft.ibss.common.util.StrTools;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;

public class DBTUtil {
	private static Logger logger = Logger.getLogger(DBTUtil.class);
	public static String databaseType = BaseTools.getDatabaseType();
	
	public static final String DB_TYPE_MYSQL = "1";
	public static final String DB_TYPE_ORACLE = "2";	
	public static final String DB_TYPE_INFORMIX = "3";
	
	public static String to_char(String val , int format){
		String returnValue = "";

		if (format == 1) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "date_format("+val+",'%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_char("+val+", '%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_char("+val+",'yyyy-mm-dd')";
			}
		} else if (format == 2) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "date_format("+val+",'%Y-%m-%d %T')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_char("+val+", '%Y-%m-%d %H:%M:%S')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_char("+val+",'yyyy-mm-dd hh24:mi:ss')";
			}
		} else if (format == 3) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "date_format("+val+",'%Y-%m-%d %H:%i')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_char("+val+", '%Y-%m-%d %H:%M')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_char("+val+",'yyyy-mm-dd hh24:mi')";
			}
		} else if (format == 4) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "date_format("+val+",'%Y-%m-%d %H')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_char("+val+", '%Y-%m-%d %H')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_char("+val+",'yyyy-mm-dd hh24')";
			}
		}else if(format==5){
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "date_format("+val+",'%Y%m%d')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_char("+val+", '%Y%m%d')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_char("+val+",'yyyymmdd')";
			}
		}else if(format==6){
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "date_format("+val+",'%Y%m')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_char("+val+", '%Y%m')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_char("+val+",'yyyymm')";
			}
		}

		return returnValue;
	}
	
	/**
	 * 获取数据库时间
	 * 
	 * @return
	 */
	public static String getDBCurrentTime() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			//add by wui
			conn = DataSourceUtils.getConnection((DataSource)SpringContextHolder.getBean("dataSource"));
			
			//conn = ConnectionContext.getContext().getConnection(JNDINames.DEFAULT_DATASOURCE);
			String selStr = " select to_char(current,'%Y-%m-%d %H:%M:%S') dbTime from dual ";
			if( EopSetting.DBTYPE.equals(DB_TYPE_INFORMIX)){
				selStr = " select to_char(current,'%Y-%m-%d %H:%M:%S') dbTime from dual ";
			}else if( EopSetting.DBTYPE.equals(DB_TYPE_ORACLE)){
				selStr = " select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') dbTime from dual ";
			}
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(selStr));
			result = stmt.executeQuery();
			String dbTime = "";
			if (result.next()) {
				dbTime = result.getString("dbTime");
			}

			return dbTime;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new DAOSystemException(" 查询数据库的当前时间报错 :\n" + e.getMessage(), e);
		} finally {
			DAOUtils.closeResultSet(result);
			DAOUtils.closeStatement(stmt);
			//add by wui
			try {
				DataSourceUtils.doReleaseConnection(conn,(DataSource)SpringContextHolder.getBean("dataSource"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// conn 由框架自己关闭。
		}
	}
	
	public static String current() {
		String returnValue = null ;
		if (StrTools.isEqual(databaseType, DB_TYPE_INFORMIX)) {
			returnValue = "current";
		} else if (StrTools.isEqual(databaseType, DB_TYPE_ORACLE)) {
			returnValue = "sysdate";
		} else if (StrTools.isEqual(databaseType, DB_TYPE_MYSQL)) {
			returnValue = "now()";
		}
		return returnValue;
	}

	public static String current(int days) {
		String returnValue = null ;
		if (StrTools.isEqual(databaseType, DB_TYPE_INFORMIX)) {
			returnValue = "current";
		} else if (StrTools.isEqual(databaseType, DB_TYPE_ORACLE)) {
			returnValue = "sysdate + "+days;
		} else if (StrTools.isEqual(databaseType, DB_TYPE_MYSQL)) {
			returnValue = "now()- "+(days);
		}
		return returnValue;
	}

	/**
	 * 当前日期之前、之后时间
	 * @param days
	 * @return
	 */
	public static String currentDate(int days) {
		String returnValue = null ;
		if (StrTools.isEqual(databaseType, DB_TYPE_ORACLE)) {
			returnValue = " trunc(sysdate +"+days+") ";
		} else if (StrTools.isEqual(databaseType, DB_TYPE_MYSQL)) {
			returnValue = " DATE_SUB(CURDATE(),INTERVAL -"+(days)+" DAY) ";
		}
		
		return returnValue;
	}
	
	public static String truncBeforeDayMonthFormat(int days){
		if (databaseType.equals(DB_TYPE_ORACLE)) {
			return " TRUNC(SYSDATE-"+days+", 'MM') " ;
		}else {
			return " DATE_SUB(CURDATE(),INTERVAL DAY(CURDATE())+"+days+" DAY) " ;	
		}
	}
	
	/**
	 * 指定日期之前、之后的月份
	 * @param pMonth
	 * @param month
	 * @return
	 */
	public static String currentMonth(String pMonth,int month){
		String returnValue = null ;
		if (StrTools.isEqual(databaseType, DB_TYPE_ORACLE)) {
			returnValue = " trunc(add_months(" + pMonth + " ,"+month+"),'dd') ";
		} else if (StrTools.isEqual(databaseType, DB_TYPE_MYSQL)) {
			returnValue = " DATE_SUB(" + pMonth + ",INTERVAL -"+(month)+" MONTH) ";
		}
		
		return returnValue;
	}
	
	
	/**
	 * 返回to_date(),只返回日期，不包括时间
	 * 
	 * @return
	 */
	public static String to_date(String value) {
		if (value == null || "".equals(value)) {
			return "";
		}
		String returnValue = "";
		// value : '2007-01-01 01:01:01' OR value : '2007-01-01'
		String[] datetime = value.split(" ");

		if (datetime.length == 1) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date('" + value + "','%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date('" + value + "', '%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date('" + value + "','yyyy-mm-dd')";
			}
		} else if (datetime.length == 2) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date('" + value + "','%Y-%m-%d %T')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date('" + value + "', '%Y-%m-%d %H:%M:%S')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date('" + value
						+ "','yyyy-mm-dd hh24:mi:ss')";
			}
		}

		return returnValue;
	}
	
	public static String to_date(String val , int format) {
		String returnValue = "";

		if (format == 1) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date("+val+",'%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date("+val+", '%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date('"+val+"','yyyy-mm-dd')";
			}
		} else if (format == 2) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date("+val+",'%Y-%m-%d %T')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date("+val+", '%Y-%m-%d %H:%M:%S')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date('"+val+"','yyyy-mm-dd hh24:mi:ss')";
			}
		}else if(format == 3){
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date("+val+",'%Y-%m')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date("+val+", '%Y-%m')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date('"+val+"','yyyy-mm')";
			}
		}

		return returnValue;
	}
	
	
	/**
	 * 有些数据库字段传进来，不加引号，用此方法
	 * @param val
	 * @param format
	 * @return
	 */
	public static String to_sql_date(String val , int format) {
		String returnValue = "";

		if (format == 1) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date("+val+",'%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date("+val+", '%Y-%m-%d')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date("+val+",'yyyy-mm-dd')";
			}
		} else if (format == 2) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date("+val+",'%Y-%m-%d %T')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date("+val+", '%Y-%m-%d %H:%M:%S')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date("+val+",'yyyy-mm-dd hh24:mi:ss')";
			}
		}else if(format == 3){
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = "str_to_date("+val+",'%Y-%m')";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = "to_date("+val+", '%Y-%m')";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = "to_date("+val+",'yyyy-mm')";
			}
		}

		return returnValue;
	}
	
	
	public static String to_date(int format) {
		String returnValue = "";

		if (format == 1) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = " str_to_date(?,'%Y-%m-%d') ";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = " to_date(?, '%Y-%m-%d') ";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = " to_date(?,'yyyy-mm-dd') ";
			}
		} else if (format == 2) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = " str_to_date(?,'%Y-%m-%d %T') ";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = " to_date(?, '%Y-%m-%d %H:%M:%S') ";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = " to_date(?,'yyyy-mm-dd hh24:mi:ss') ";
			}
		}else if (format == 3) {
			if (databaseType.equals(DB_TYPE_MYSQL)) {
				returnValue = " str_to_date(?,'%Y%m%d') ";
			} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
				returnValue = " to_date(?, '%Y%m%d') ";
			} else if (databaseType.equals(DB_TYPE_ORACLE)) {
				returnValue = " to_date(?,'yyyymmdd') ";
			}
		}

		return returnValue;
	}
	
	private static int getDatePatternType(String pattern){
		return pattern.split(" ").length ;
	}
	
	public static String date_pattern(String pattern) {
		if (databaseType.equals(DB_TYPE_MYSQL)) {
			if (!pattern.startsWith("%"))
				return pattern;
			
			if (1 == getDatePatternType(pattern))
				return "%Y-%m-%d";
			
			return "%Y-%m-%d %T";
			
		} else if (databaseType.equals(DB_TYPE_INFORMIX)) {
			if (pattern.startsWith("%"))
				return pattern;

			if (1 == getDatePatternType(pattern))
				return "%Y-%m-%d";

			return "%Y-%m-%d %H:%M:%S";
		} else {
			if (!pattern.startsWith("%"))
				return pattern;
			
			if (1 == getDatePatternType(pattern))
				return "yyyy-mm-dd";

			return "yyyy-mm-dd hh24:mi:ss";
		}
	}
	
	public static String compareSysTime(String time){
//		Connection conn = DataSourceUtils.getConnection((DataSource)SpringContextHolder.getBean("dataSource"));
		String sql = null ;
		if (databaseType.equals(DB_TYPE_MYSQL)) 
			sql = "select datediff(SYSDATE(), ?)";
		else
			sql =" select round(sysdate - to_date(?,'yyyy-MM-dd hh24 mi ss'),2) from dual ";
		SqlMapExe sqlMapExe=  SqlMapExe.getInstance();
		return sqlMapExe.querySingleValue(sql,new String [] {time} );
	}
	
	/**
	 * 
	 *1.MySQL采用地址方式处理主键,主键字段不用显示出来
	 *2.Oracle采用字段显式方式字段与序列关联处理
	 *
	 * @param field 数据库字段
	 * @return
	 */
	public static String seqF(String field){
		if (databaseType.equals(DB_TYPE_MYSQL)) 
			return "" ;
		else
			return field+"," ;
	}

	/**
	 * 
	 *1.MySQL采用地址方式处理主键,主键字段不用显示出来
	 *2.Oracle采用字段显式方式字段与序列关联处理
	 *
	 * @param field 数据库字段
	 * @return
	 */
	public static String seqV(String val){
		if (databaseType.equals(DB_TYPE_MYSQL)) 
			return "" ;
		else
			return val+".nextval ," ;
	}
	
	
	public static String andRownum(String row) {
		String sql = "";
		// boolean isNumberic = DataUtil.isNumeric(row) ;
		if (databaseType.equals(DB_TYPE_ORACLE)) {
			sql = " and rownum<" + row;
		} else if (databaseType.equals(DB_TYPE_MYSQL)) {
			sql = " limit " + row;
		}
		return sql;
	}
	
	public static String andEqRownum(String row) {
		String sql = "";
		// boolean isNumberic = DataUtil.isNumeric(row) ;
		if (databaseType.equals(DB_TYPE_ORACLE)) {
			sql = " and rownum=" + row;
		} else if (databaseType.equals(DB_TYPE_MYSQL)) {
			sql = " limit " + row;
		}
		return sql;
	}
	
	
	public static String month1stDate(){
		if (databaseType.equals(DB_TYPE_ORACLE)) {
			return " TRUNC(SYSDATE, 'MM') " ;
		}else {
			return " DATE_SUB(CURDATE(),INTERVAL DAY(CURDATE())-1 DAY) " ;	
		}
	}
	
	public static String timeCmp(String f1 , String opt , String f2){
		if (databaseType.equals(DB_TYPE_ORACLE)) {
			return f1+ opt + f2 ;
		}else{
			return "UNIX_TIMESTAMP("+f1+")"+ opt + "UNIX_TIMESTAMP("+f2+")";
		}
	}
	
	public static String timesub(String end_date,String start_date){
		if(databaseType.equals(DB_TYPE_MYSQL)){
			return "timestampdiff(second,"+start_date+","+end_date+")";
		}else{
			return "ceil(("+end_date+"-"+start_date+") * 24 * 60 * 60)";
		}
	}
	

	/**
	 * decode转换
	 * @param field
	 * @param cond1
	 * @param v1
	 * @param strings
	 * @return
	 */
	public static String decode(String field ,String cond1 , String v1 ,String ...strings ){
		StringBuilder sql = new StringBuilder() ;
		if (databaseType.equals(DB_TYPE_ORACLE)) {
			sql.append("decode(").append(field).append(",").append(cond1).append(",").append(v1) ;
			if(strings != null){
				for(int i= 0 ; i < strings.length ; i++){
					sql.append(",").append(strings[i]) ;
				}
			}
			sql.append(")") ;
		}else {
			sql.append("if(").append(field) ;	
			
			if(cond1.equalsIgnoreCase("null")){
				sql.append(" is null ").append(",").append(v1) ;
			}else{
				sql.append("=").append(cond1).append(",").append(v1) ;
			}
			if(strings != null){
				if(strings.length == 1){
					sql.append(",").append(strings[0]) ;
				}else {
					int ifNum = 0 ;
					for(int i= 0 ; i < strings.length ; i++){
						ifNum ++ ;
						sql.append(",").append("if(").append(field) ;	
						cond1 = strings[i] ;
						i++ ;
						v1 = strings[i] ;
						if(cond1.equalsIgnoreCase("null")){
							sql.append(" is null ").append(",").append(v1) ;
						}else{
							sql.append("=").append(cond1).append(",").append(v1) ;
						}
						if(i == strings.length-1){
							sql.append(",").append("null") ;
						}else if(i+2 == strings.length-1){
							i+=2 ;
							sql.append(",").append(strings[i]) ;
						}else if(i ==strings.length-2 ){
							i++ ;
							sql.append(",").append(strings[i]) ;
						}
					}
					for(int i = 0 ; i<ifNum ; i++){
						sql.append(")") ;
					}
				}
			}
			
			sql.append(")") ;
		}
		return sql.toString() ;
	}
	
	public static String addDays(String val,int days) {
		String returnValue = null ;
		if (StrTools.isEqual(databaseType, DB_TYPE_ORACLE)) {
			returnValue = " "+ val+days;
		} else if (StrTools.isEqual(databaseType, DB_TYPE_MYSQL)) {
			returnValue = " DATE_SUB("+val+",INTERVAL -"+(days)+" DAY) ";
		}
		
		return returnValue;
	}
	
	
	public static void main(String[] a ){
		logger.info(decode("hj_bz", "1", "'总库入'", "2", "'总库出'","3","'分库出'","4","'已开票'","5","'已安装'","6","'已收款'")) ;
		logger.info(decode("hj_bz", "1", "'总库入'", "2", "'总库出'")) ;
		logger.info(decode("hj_bz", "1", "'总库入'", "'总库出'")) ;
	}

    /*替换nvl函数*/
    public static String nvl(){
        if (databaseType.equals(DB_TYPE_ORACLE)){
           return "NVL";
        }
        if(databaseType.equals(DB_TYPE_MYSQL)){
            return "IFNULL";
        }

        return null;
    }
	
}
