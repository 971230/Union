package com.powerise.ibss.framework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.dao.DAOSQLUtils;
import com.ztesoft.ibss.common.dao.DAOUtils;
import com.ztesoft.ibss.common.dao.QueryFilter;
import com.ztesoft.ibss.common.dao.QueryFilterFactory;
import com.ztesoft.ibss.common.util.CrmConstants;

public class QueryHelper {
	
	private static Logger logger = Logger.getLogger(QueryHelper.class);
	/*
	 * 执行选项，0-3bit用于报错条件，第4位以上用于选项，例如：
	 * 
	 * WHEN_NO_DATA_FOUND | OPTION_RETURN_STRUCT 表示 无记录报错，否则返回查询结果和结果集结构
	 * 
	 * WHEN_NO_DATA_FOUND | OPTION_RETURN_COLUMN 表示 无记录报错，否则以C1, C2, C3
	 * ..列名方式返回结果
	 * 
	 */
	public static final int WHEN = 15, // WHEN的屏蔽字
			WHEN_NO_DATA_FOUND = 1, // 用于检查查询语句
			WHEN_DATA_FOUND = 2, // 用于检查查询语句
			WHEN_COUNT_IS_0 = 3, // 用于检查修改语句
			WHEN_COUNT_IS_NOT_0 = 4, // 用于检查修改语句
			WHEN_COUNT_IS_NOT_1 = 5, // 用于检查修改语句
			WHEN_WRITE_BACK = 6, // 只对ACTION有效，查询结果回写到包中
			WHEN_WRITE_RECORDSET = 7, // 只对ACTION有效，查询结果回加入到RECORDSET包中
			OPTION_RETURN_COLUMN = 16, // 以C1,C2,..的列名返回结果集
			OPTION_RETURN_STRUCT = 32; // 同时返回结果集结构，__field_name, __field_type

	private static final int OPTION_RETURN_FIRST1 = 64, // 只返回第1条记录
			OPTION_LOGGED = 128; // 执行前打印日志
	public QueryHelper(){
		
	}
	


	
	//执行SELECT语句，返回DynamicDict对象：追加或直接返回方式；正常或HashMap方式
	public  static DynamicDict queryPage(String jndiName ,String querySQL,String countSQL,
			String[] para,int pageIndex, int pageSize ,  
				DynamicDict dto, String name,
				int ecode, int etype, String edesc)
			throws FrameException {
//		jndiName = jndiName.toUpperCase() ;
		if (dto == null) // 装入对象
			dto = new DynamicDict();
		PageModel pageModel = new PageModel() ;
		
		name = (name == null || "".equals(name) ) ?Const.ACTION_RESULT : name.toUpperCase() ;
		
//		if (querySQL.indexOf('$') >= 0) // 增加支持ASQL
//			querySQL = SQLResolver.asql__parse(querySQL);
//		
//		if (querySQL.indexOf('$') >= 0) // 增加支持ASQL
//			countSQL = SQLResolver.asql__parse(countSQL);
		
		ResultSet rs = null;

		try {
			rs = sql__query(ConnectionContext.getContext().getConnection(jndiName),
					ecode, countSQL, para, etype, edesc);
			int totalCount =0 ;
			if(rs.next())
				totalCount = rs.getInt(1) ;
			
			pageModel.setPageCount(totalCount) ;
			DAOUtils.closeResultSet(rs) ;
			
			if(totalCount == 0 ){
				dto.setValueByName(name, pageModel) ;
				return dto ;
			}
			setPageModel( pageModel , totalCount ,  pageSize ,  pageIndex  ) ;
			
			QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
			querySQL = queryFilter.doPreFilter(querySQL);
			
			rs = sql__query(ConnectionContext.getContext().getConnection(jndiName),
					ecode, querySQL, para ,pageIndex, pageSize, etype, edesc);

			int cnt = 0; // 装入条数
			if ((etype & OPTION_RETURN_FIRST1) > 0)
				cnt = 1;
//			logger.info("cnt="+cnt +"\n etype & OPTION_RETURN_FIRST1="+(etype & OPTION_RETURN_FIRST1)+
//					"\n etype & OPTION_RETURN_COLUMN="+(etype & OPTION_RETURN_COLUMN)+
//					"\n name="+name+
//					"\n etype & OPTION_RETURN_STRUCT="+(etype & OPTION_RETURN_STRUCT)) ;
			
			if ((etype & OPTION_RETURN_COLUMN) > 0)
				dict__append_from_rs_column(dto, rs, cnt);
			else 
//				if ((name == null) || name.equals(""))
//				dict__append_from_rs(dto, rs, cnt);
//			else
				dict__append_from_rs(dto, name, rs, cnt);

//			if ((etype & OPTION_RETURN_STRUCT) > 0)
//				dict__append_from_rs_struct(dto, rs);

			Object obj = dto.m_Values.get(name) ;
			List list = (obj == null ) ? null : (List)obj  ;
			pageModel.setList(list) ;
			dto.m_Values.put(name, pageModel) ;
			
		} catch (SQLException e) {
			throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.getStatement().close();
				} catch (Throwable ee) {
				}
		}

		return dto;
	}
//	执行SELECT语句，返回DynamicDict对象：追加或直接返回方式；正常或HashMap方式
	public  static DynamicDict queryPage(String jndiName ,String querySQL,String countSQL,
			List para,int pageIndex, int pageSize ,  
				DynamicDict dto, String name,
				int ecode, int etype, String edesc)
			throws FrameException {
//		jndiName = jndiName.toUpperCase() ;
		if (dto == null) // 装入对象
			dto = new DynamicDict();
		PageModel pageModel = new PageModel() ;
		
		name = (name == null || "".equals(name) ) ?Const.ACTION_RESULT : name.toUpperCase() ;
		
//		if (querySQL.indexOf('$') >= 0) // 增加支持ASQL
//			querySQL = SQLResolver.asql__parse(querySQL);
//		
//		if (querySQL.indexOf('$') >= 0) // 增加支持ASQL
//			countSQL = SQLResolver.asql__parse(countSQL);
		
		ResultSet rs = null;

		try {
			rs = sql__query(ConnectionContext.getContext().getConnection(jndiName),
					ecode, countSQL, para, etype, edesc);
			int totalCount =0 ;
			if(rs.next())
				totalCount = rs.getInt(1) ;
			
//			pageModel.setPageCount(totalCount) ;
			pageModel.setTotalCount(totalCount) ;
			DAOUtils.closeResultSet(rs) ;
			
			if(totalCount == 0 ){
				dto.setValueByName(name, pageModel) ;
				return dto ;
			}
			setPageModel( pageModel , totalCount ,  pageSize ,  pageIndex  ) ;
			
			QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
			querySQL = queryFilter.doPreFilter(querySQL);
			
			rs = sql__query(ConnectionContext.getContext().getConnection(jndiName),
					ecode, querySQL, para ,pageIndex, pageSize, etype, edesc);

			int cnt = 0; // 装入条数
			if ((etype & OPTION_RETURN_FIRST1) > 0)
				cnt = 1;
//			logger.info("cnt="+cnt +"\n etype & OPTION_RETURN_FIRST1="+(etype & OPTION_RETURN_FIRST1)+
//					"\n etype & OPTION_RETURN_COLUMN="+(etype & OPTION_RETURN_COLUMN)+
//					"\n name="+name+
//					"\n etype & OPTION_RETURN_STRUCT="+(etype & OPTION_RETURN_STRUCT)) ;
			
			if ((etype & OPTION_RETURN_COLUMN) > 0)
				dict__append_from_rs_column(dto, rs, cnt);
			else 
//				if ((name == null) || name.equals(""))
//				dict__append_from_rs(dto, rs, cnt);
//			else
				dict__append_from_rs(dto, name, rs, cnt);

//			if ((etype & OPTION_RETURN_STRUCT) > 0)
//				dict__append_from_rs_struct(dto, rs);

			Object obj = dto.m_Values.get(name) ;
			List list = (obj == null ) ? null : (List)obj  ;
			pageModel.setList(list) ;
			dto.m_Values.put(name, pageModel) ;
			
		} catch (SQLException e) {
			throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.getStatement().close();
				} catch (Throwable ee) {
				}
		}

		return dto;
	}
//	public  static DynamicDict query(String jndiName ,String sql, List para, 
//			int ecode, int etype, String edesc)
//		throws FrameException {
////		String[] arrayPara =( para != null ? (String[])para.toArray() : null ) ;
//		return query( jndiName , sql,  para,  
//			 dictr,  name,
//			 ecode,  etype,  edesc) ;
//	} 
//	 执行SELECT语句，返回DynamicDict对象：追加或直接返回方式；正常或HashMap方式
	public  static DynamicDict query(String jndiName ,String sql, List para, 
				int ecode, int etype, String edesc)
			throws FrameException {
		return query( jndiName , sql,  para,  
				 null,  null,
				 ecode,  etype,  edesc) ;
	}

	// 执行SELECT语句，返回DynamicDict对象：追加或直接返回方式；正常或HashMap方式
	public  static DynamicDict query(String jndiName ,String sql, List para,  
				DynamicDict dictr, String name,
				int ecode, int etype, String edesc)
			throws FrameException {
//		jndiName = jndiName.toUpperCase() ;
//		if (sql.indexOf('$') >= 0) // 增加支持ASQL
//			sql = SQLResolver.asql__parse(sql);

		ResultSet rs = null;

		try {
			Connection con = ConnectionContext.getContext().getConnection(jndiName) ;
//			boolean isc = con.isClosed();
			rs = sql__query(con,
					ecode, sql, para, etype, edesc);

			if (dictr == null) // 装入对象
				dictr = new DynamicDict();

			int cnt = 0; // 装入条数
			if ((etype & OPTION_RETURN_FIRST1) > 0)
				cnt = 1;
//			logger.info("cnt="+cnt +"\n etype & OPTION_RETURN_FIRST1="+(etype & OPTION_RETURN_FIRST1)+
//					"\n etype & OPTION_RETURN_COLUMN="+(etype & OPTION_RETURN_COLUMN)+
//					"\n name="+name+
//					"\n etype & OPTION_RETURN_STRUCT="+(etype & OPTION_RETURN_STRUCT)) ;
			
			if ((etype & OPTION_RETURN_COLUMN) > 0)
				dict__append_from_rs_column(dictr, rs, cnt);
			else if ((name == null) || name.equals(""))
				dict__append_from_rs(dictr, rs, cnt);
			else
				dict__append_from_rs(dictr, name, rs, cnt);

			if ((etype & OPTION_RETURN_STRUCT) > 0)
				dict__append_from_rs_struct(dictr, rs);

		} catch (SQLException e) {
			throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
		} finally {
//			DAOUtils.closeResultSet(rs) ;
			if (rs != null)
				try {
					rs.getStatement().close();
				} catch (Throwable ee) {
				}
		}

		return dictr;
	}
	
	
	// 执行SELECT语句，返回DynamicDict对象：追加或直接返回方式；正常或HashMap方式
	public  static DynamicDict query(String jndiName ,String sql, String[] para,  
				DynamicDict dictr, String name,
				int ecode, int etype, String edesc)
			throws FrameException {
//		jndiName = jndiName.toUpperCase() ;
//		if (sql.indexOf('$') >= 0) // 增加支持ASQL
//			sql = SQLResolver.asql__parse(sql);

		ResultSet rs = null;

		try {
			rs = sql__query(ConnectionContext.getContext().getConnection(jndiName),
					ecode, sql, para, etype, edesc);

			if (dictr == null) // 装入对象
				dictr = new DynamicDict();

			int cnt = 0; // 装入条数
			if ((etype & OPTION_RETURN_FIRST1) > 0)
				cnt = 1;
//			logger.info("cnt="+cnt +"\n etype & OPTION_RETURN_FIRST1="+(etype & OPTION_RETURN_FIRST1)+
//					"\n etype & OPTION_RETURN_COLUMN="+(etype & OPTION_RETURN_COLUMN)+
//					"\n name="+name+
//					"\n etype & OPTION_RETURN_STRUCT="+(etype & OPTION_RETURN_STRUCT)) ;
			
			if ((etype & OPTION_RETURN_COLUMN) > 0)
				dict__append_from_rs_column(dictr, rs, cnt);
			else if ((name == null) || name.equals(""))
				dict__append_from_rs(dictr, rs, cnt);
			else
				dict__append_from_rs(dictr, name, rs, cnt);

			if ((etype & OPTION_RETURN_STRUCT) > 0)
				dict__append_from_rs_struct(dictr, rs);

		} catch (SQLException e) {
			throw new FrameException(ecode, "执行SQL时异常: " + e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.getStatement().close();
				} catch (Throwable ee) {
				}
		}

		return dictr;
	}
	
	

	/**
	 * SQL SELECT -> ResultSet
	 */
	private static ResultSet sql__query(Connection conn, int ecode, String sql,
			String[] para, int etype, String edesc) throws SQLException,
			FrameException {
		toLog(sql , para , ecode) ;

		PreparedStatement st = null;
		ResultSet rs = null;
		boolean ok = false;
		try {
			st = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			if (para != null)
				for (int i = 0; i < para.length; i++)
					st.setString(i + 1, para[i]);

			rs = st.executeQuery();

			if (edesc == null)
				edesc = String.valueOf(ecode);
			switch (etype & WHEN) {
			case WHEN_NO_DATA_FOUND:
				if (!rs.isBeforeFirst())
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_DATA_FOUND:
				if (rs.isBeforeFirst())
					throw new FrameException(ecode, edesc);

			default:
			}

			ok = true;
		} catch (SQLException e) {
			throw new FrameException(ecode, "执行SQL时异常: SQL=" + sql + "\nPARA=" + para + "\n" + e.getMessage());
		} finally {
			if (!ok) {
				if (rs != null)
					try {
						rs.close();
					} catch (Throwable ee) {
					}
				if (st != null)
					try {
						st.close();
					} catch (Throwable ee) {
					}
			}
		}

		return rs;
	}
	

	/**
	 * SQL SELECT -> ResultSet
	 */
	private static ResultSet sql__query(Connection conn, int ecode, String sql,
			List para, int etype, String edesc) throws SQLException,
			FrameException {
		toLog(sql , para , ecode) ;

		PreparedStatement st = null;
		ResultSet rs = null;
		boolean ok = false;
		try {
//            if(DAOSQLUtils.READPROPERTIES.equals(edesc)){
//                sql = DAOSQLUtils.getFilterSQL(sql,edesc);
//            }else{
            	sql = DAOSQLUtils.getFilterSQL(sql) ;
//            }
			st = conn.prepareStatement(sql);
			if (para != null)
				for (int i = 0 , j = para.size(); i < j ; i++)
					setObject2Statement(para.get(i) ,  st , i+1 );

			rs = st.executeQuery();

			if (edesc == null)
				edesc = String.valueOf(ecode);
			int ti = etype & WHEN ;
			
			//如果不等于-999999999才需要判断是否没有数据。
			if(etype != -999999999 && ecode != -999999999){
			
				switch ((etype & WHEN )) {
				case WHEN_NO_DATA_FOUND:
					if (!rs.isBeforeFirst())
						throw new FrameException(ecode, edesc);
					break;
	
				case WHEN_DATA_FOUND:
					if (rs.isBeforeFirst())
						throw new FrameException(ecode, edesc);
	
				default:
				}
			}

			ok = true;
		} catch (SQLException e) {
			throw new FrameException(ecode, "执行SQL时异常: SQL=" + sql + "\nPARA=" + para + "\n" + e.getMessage());
		}
		finally {
			if (!ok) {
				if (rs != null)
					try {
						rs.close();
					} catch (Throwable ee) {
					}
				if (st != null)
					try {
						st.close();
					} catch (Throwable ee) {
					}
			}
		}

		return rs;
	}
	
	private static boolean isDateType(Object o ){
		return o != null &&( 
				"java.util.Date".equals(o.getClass().getName()) ||
				"java.sql.Date".equals(o.getClass().getName())) ;
	}
	private static void setObject2Statement(Object o , PreparedStatement s , int i ) throws SQLException{
		if(isDateType( o )){
			long time = ((java.sql.Date)o).getTime();
			s.setTimestamp(i, new Timestamp(time));
			//s.setDate(i, (java.sql.Date)o) ;
		}else if(o instanceof String){
			s.setString(i, (String) o);
		}else{
			s.setObject(i, o) ;
		}
	}
	/**
	 * SQL SELECT -> ResultSet
	 */
	private static ResultSet sql__query(Connection conn, int ecode, String sql,
			String[] para,int pageIndex, int pageSize ,  int etype, String edesc) throws SQLException,
			FrameException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean ok = false;
		try {
			st = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			if (para != null)
				for (int i = 0; i < para.length; i++)
					st.setString(i + 1, para[i]);

			rs = st.executeQuery();

			if (edesc == null)
				edesc = String.valueOf(ecode);
			switch (etype & WHEN) {
			case WHEN_NO_DATA_FOUND:
				if (!rs.isBeforeFirst())
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_DATA_FOUND:
				if (rs.isBeforeFirst())
					throw new FrameException(ecode, edesc);

			default:
			}
			ok = true;
		} finally {
			if(!ok){
				DAOUtils.closeResultSet(rs);
				DAOUtils.closeStatement(st);
			}
		}

		return rs;
	}
	
	
	/**
	 * SQL SELECT -> ResultSet
	 */
	private static ResultSet sql__query(Connection conn, int ecode, String sql,
			List para,int pageIndex, int pageSize ,  int etype, String edesc) throws SQLException,
			FrameException {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		boolean ok = false;
		try {
//			if(DAOSQLUtils.READPROPERTIES.equals(edesc)){
//                sql = DAOSQLUtils.getFilterSQL(sql,edesc);
//            }else{
                sql = DAOSQLUtils.getFilterSQL(sql) ;
//            }
			
			st = conn.prepareStatement(sql);
			if (para != null)
				for (int i = 0 , j = para.size(); i < j; i++)
					setObject2Statement(para.get(i) ,  st , i+1 );

			rs = st.executeQuery();

			if (edesc == null)
				edesc = String.valueOf(ecode);
			switch (etype & WHEN) {
			case WHEN_NO_DATA_FOUND:
				if (!rs.isBeforeFirst())
					throw new FrameException(ecode, edesc);
				break;

			case WHEN_DATA_FOUND:
				if (rs.isBeforeFirst())
					throw new FrameException(ecode, edesc);

			default:
			}
			ok = true;
		} finally {
			if(!ok){
				DAOUtils.closeResultSet(rs);
				DAOUtils.closeStatement(st);
			}
		}

		return rs;
	}
	/*
	 * 从结果集加入DynamicDict（column方式,列名为C1,C2,...)，取前cnt条，cnt=0则全部装入
	 */
	private static DynamicDict dict__append_from_rs_column(DynamicDict dictr,
			ResultSet rs, int cnt) throws SQLException, FrameException {
		int c = 0;
		ResultSetMetaData rsm = rs.getMetaData();
		while (rs.next()) {
			for (int j = 1; j <= rsm.getColumnCount(); j++) {
				String val = ((rs.getString(j) == null) ? "" : rs.getString(j));
				dictr.setValueByName("C" + String.valueOf(j), val, 1);
			}

			if ((cnt > 0) && (++c >= cnt))
				break;
		}

		return dictr;
	}

	
	private static void toLog(String sql , String[] para , int etype ){
		if ((etype & OPTION_LOGGED) > 0) // 要记录日志
		{
			String spara = "";
			if (para != null)
				for (int i = 0; i < para.length; i++)
					spara += "," + para[i];

			if (!spara.equals(""))
				spara = spara.substring(1);

//			LogHelper.log("SQL=" + DAOSQLUtils.getFilterSQL(sql) + "\nPARA=" + spara);
		}

	}
	private static void toLog(String sql , List para , int etype ){
		if ((etype & OPTION_LOGGED) > 0) // 要记录日志
		{
			String spara = "";
			if (para != null)
				for (int i = 0, j=para.size(); i < j; i++)
					spara += "," + para.get(i);

			if (!spara.equals(""))
				spara = spara.substring(1);

//			LogHelper.log("SQL=" + DAOSQLUtils.getFilterSQL(sql) + "\nPARA=" + spara);
		}

	}
	/*
	 * 从结果集加入DynamicDict，取前cnt条，cnt=0则全部装入
	 */
	private static DynamicDict dict__append_from_rs(DynamicDict dictr,
			ResultSet rs, int cnt) throws SQLException, FrameException {
		int c = 0;
		ResultSetMetaData rsm = rs.getMetaData();
		while (rs.next()) {
			for (int j = 1; j <= rsm.getColumnCount(); j++) {
				String nam = rsm.getColumnName(j);
				String val = ((rs.getString(j) == null) ? "" : (rs.getString(j)).trim());
				dictr.setValueByName(nam, val, 1);
			}
			if ((cnt > 0) && (++c >= cnt))
				break;
		}

		return dictr;
	}

	/*
	 * 从结果集以HashMap方式加入DynamicDict的name变量，取前cnt条，cnt=0则全部装入
	 */
	private static DynamicDict dict__append_from_rs(DynamicDict dictr,
			String name, ResultSet rs, int cnt) throws SQLException,
			FrameException {
		int c = 0;
		ResultSetMetaData rsm = rs.getMetaData();
		while (rs.next()) {
			HashMap h = new HashMap();
			for (int j = 1; j <= rsm.getColumnCount(); j++) {
//				D
//				rsm.getColumnName(j).
				String nam = rsm.getColumnName(j).toLowerCase();//转换为小写，与代码生成器字段小写相匹配 easonwu 2010-01-15
				String columnType = rsm.getColumnTypeName(j) ;
				String val =  "" ;
				if("DATE".equals(columnType)){
					val = DAOUtils.getFormatedDateTime(rs.getTimestamp(j));
					if(val.indexOf("00:00:00")>0){//"只有日期"
						val = DAOUtils.getFormatedDate(rs.getDate(j));
					}
					//val = DAOUtils.getFormatedDateTime(rs.getDate(j));
				}else if("TIMESTAMP".equals(columnType)){
					 val = DAOUtils.getFormatedDateTime(rs.getTimestamp(j));
				}else{
					 val = ((rs.getString(j) == null) ? "" : (rs.getString(j)).trim());
				}
//				logger.info("TT+==================="+columnType) ;
				h.put(nam, val);
			}
			if (dictr.getCountByName(name) <= 0)
				dictr.setValueByName(name, new ArrayList());
			dictr.setValueByName(name, h, 1);

			if ((cnt > 0) && (++c >= cnt))
				break;
		}

		return dictr;
	}
	/*
	 * 将结果集结构加入DynamicDict，（__FIELD_NAME, __FIELD_TYPE）
	 */
	private static DynamicDict dict__append_from_rs_struct(DynamicDict dictr,
			ResultSet rs) throws SQLException, FrameException {
		ResultSetMetaData rsm = rs.getMetaData();
		for (int j = 1; j <= rsm.getColumnCount(); j++) {
			String nam = rsm.getColumnName(j);
			String typ = String.valueOf(rsm.getColumnType(j));
			dictr.setValueByName("__FIELD_NAME", nam, 1);
			dictr.setValueByName("__FIELD_TYPE", typ, 1);
		}

		return dictr;
	}

	public static void setPageModel(PageModel pageModel ,int totalCount , int pageSize , int pageIndex  ){
//		 pageCount
		if (totalCount % pageSize > 0) {
			pageModel.setPageCount(totalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(totalCount / pageSize);
		}

		// 边界的处理
		if (pageIndex < 0) {
			pageModel.setPageIndex(1);
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		if(pageSize > CrmConstants.MAX_PAGE_SIZE){
			//throw new CommonException(new CommonError(CommonError.PAGE_TOO_LARGE_ERROR));
		}
		
	}
	
	public static void main(String[] args ){
		String[] para = new String[2] ;
		para[0] = "100001" ;
		para[1] = "00" ;
		int pageIndex = 2 ;
		int pageSize = 1 ;
		
		
		try {
			long s1 = System.currentTimeMillis() ;
			for(int i=0 ; i<100 ; i++){

				DynamicDict dto = QueryHelper.queryPage("CRM", "select p.stype,p.pkey,p.pcode,p.pname,p.comments from dc_public p where stype=? and pkey like '%'||?||'%' ", "select count(1) from dc_public p where  stype=? and pkey like '%'||?||'%'", para, 
						pageIndex, pageSize, null, null, 123, 0, "测试") ;
				try {
					ConnectionContext.getContext().closeConnection("CRM") ;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PageModel pageModel = (PageModel)dto.m_Values.get(Const.ACTION_RESULT) ;
			}
			long s2 = System.currentTimeMillis() ;
			//logger.info("S==="+(s2-s1)) ;
			logger.info("S==="+(s2-s1));
			long s3 = System.currentTimeMillis() ;
			for(int i=0 ; i<100 ; i++){
//				DcPublicDAOImpl dao = new DcPublicDAOImpl() ;
//				String whereCond = " 1=1 and stype='"+para[0]+"' and pkey like '%'||'"+para[1]+"'||'%' " ;
//				PageModel pageModel2 =  PageHelper.popupPageModel(dao, whereCond, pageIndex, pageSize);  
			}
			long s4 = System.currentTimeMillis() ;
			//logger.info("E==="+(s4-s3)) ;
			logger.info("E==="+(s4-s3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
