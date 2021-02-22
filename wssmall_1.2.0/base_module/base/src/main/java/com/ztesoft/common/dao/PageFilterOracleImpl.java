package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.ibss.common.dao.QueryFilter;

public class PageFilterOracleImpl implements QueryFilter{
	
	private PageModel pageModel ; //当前分页信息模板
	
	/**
	 * 
	 * PageFilterInformixImpl.java构建器
	 */
	public PageFilterOracleImpl() {
		this.pageModel = new PageModel();	
	}
	
	/**
	 * 
	 * PageFilterInformixImpl.java构建器
	 */
	public PageFilterOracleImpl(PageModel pageModel) {
		
		this.pageModel = pageModel;
		
	}
	
	@Override
	public String doPreFilterWithoutFilterSQL(String sql) {
		int startIndex = (this.pageModel.getPageIndex() - 1) * this.pageModel.getPageSize()+1;
		int endIndex = startIndex + this.pageModel.getPageSize() ;
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql +
				" ) my_table where rownum< " + endIndex + 
				") where my_rownum>= " + startIndex ;
		return sql;
	}

	/**  
	 * 预过滤，主要是为oracle一类的数据库使用rownum等组装sql分页预留。
	 */
	@Override
	public String doPreFilter(String sql) {
		//crmdb,proddb,custdb,acctdb...
		String[] dbusers = System.getProperty("SYSTEM_DATABASE_USER").split(",");
		boolean needFilter = true ;
		for( int i = 0 ; i < dbusers.length ; i ++ ){
			if( sql.indexOf(dbusers[i]+".") != -1 ){
				needFilter = false ;
				break ;
			}
		}
		if( needFilter ){
			sql = DAOSQLUtils.getFilterSQL(sql) ;
		}
		int startIndex = (this.pageModel.getPageIndex() - 1) * this.pageModel.getPageSize()+1;
		int endIndex = startIndex + this.pageModel.getPageSize() ;
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql + 
				" ) my_table where rownum< " + endIndex + 
				") where my_rownum>= " + startIndex ;

		//sql = "PAGEMODEL" + sql ;
		return sql;
	}

	/**
	 * 后过滤，在结果集中，过滤出当前分页设置的页面数据。
	 * 
	 * @param resultSet
	 * @param dao
	 * @return
	 * @throws SQLException
	 */
	public ArrayList doPostFilter(ResultSet resultSet, DAO dao) throws SQLException {
		ArrayList list = new ArrayList();
		try {
			
			if (this.pageModel.getPageSize() == -1)
				return list;
			/*if (!resultSet.next())
				return list;
			 
			int count = 0;
			while (resultSet.next()){
				VO vo = dao.populateCurrRecord(resultSet);
				list.add(vo);
				count++;
			}*/
				while( resultSet.next() ){
					VO vo = dao.populateCurrRecord(resultSet);
					list.add(vo);
				}
		} catch (SQLException e) {
			throw e;
		}
		//设置数据列表
		this.pageModel.setList(list);
		
		return list;
	}
	
	
/*	public ArrayList doPostFilter(ResultSet resultSet ) throws SQLException {
		
		

		
		ArrayList list = new ArrayList();

			
		if (this.pageModel.getPageSize() == -1)
			return list;
	

		//设置数据列表
		this.pageModel.setList(SQLUtils.getInst().convert(resultSet));
		
		return list;
	}*/
	

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	@Override
	public ArrayList doPostFilter(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
