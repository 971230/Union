package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.ibss.common.dao.QueryFilter;

/**
 * 
 * @Classname : PageFilterInformixImpl
 * @Description : 查询过滤器QueryFilter informix下的实现。
 * @Copyright ? 2006 ZTEsoft.
 * @Author : Mr. fjy
 * @Create Date : 2006-3-28
 * 
 * @Last Modified :
 * @Modified by :5
 * @Version : 1.0
 */
public class PageFilterJdbcImpl implements QueryFilter {
	
	private PageModel pageModel ; //当前分页信息模板
	
	/**
	 * 
	 * PageFilterInformixImpl.java构建器
	 */
	public PageFilterJdbcImpl() {
				
	}
	
	/**
	 * 
	 * PageFilterInformixImpl.java构建器
	 */
	public PageFilterJdbcImpl(PageModel pageModel) {
		
		this.pageModel = pageModel;
		
	}

	/**
	 * 预过滤，主要是为oracle一类的数据库使用rownum等组装sql分页预留。
	 */
	@Override
	public String doPreFilter(String sql) {
		if (DAOUtils.isInformixDB()) {
			String[] dbusers = System.getProperty("SYSTEM_DATABASE_USER").split(",");
			boolean needFilter = true;
			for (int i = 0; i < dbusers.length; i++) {
				if (sql.indexOf(dbusers[i] + ".") != -1) {
					needFilter = false;
					break;
				}
			}
			if (needFilter) {
				//TODO 不在这里做添加表名前缀的操作
				//			sql = DAOSQLUtils.getFilterSQL(sql) ;
			}
			int startIndex = (this.pageModel.getPageIndex() - 1)
					* this.pageModel.getPageSize();
			int endIndex = this.pageModel.getPageSize();
			sql = "select skip " + startIndex + " first " + endIndex
					+ " * from (" + sql + ")";
		}        
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
			if (!resultSet.next())
				return list;
			
			 int currentSize = 0;
	         if (this.pageModel.getPageIndex() >= this.pageModel.getPageCount()) {
	             int startIndex = this.pageModel.getPageCount();
	             currentSize = this.pageModel.getTotalCount() - (startIndex - 1) * this.pageModel.getPageSize();
	         }
	         else {
	             currentSize = this.pageModel.getPageSize();
	         }
	         
			int locationInt = (this.pageModel.getPageIndex() - 1) * this.pageModel.getPageSize()+1;
			resultSet.absolute(locationInt);
			int count = 0;
			do {
				VO vo = dao.populateCurrRecord(resultSet);
				list.add(vo);
				count++;
			}while (resultSet.next() && count < currentSize);

		} catch (SQLException e) {
			throw e;
		}
		//设置数据列表
		this.pageModel.setList(list);
		
		return list;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	/* (non-Javadoc)
	 * @see com.ztesoft.common.dao.QueryFilter#doPreFilterWithoutFilterSQL(java.lang.String)
	 */
	@Override
	public String doPreFilterWithoutFilterSQL(String sql) {
		return sql;
	}
	
	@Override
	public ArrayList doPostFilter(ResultSet resultSet ) throws SQLException {
		return new ArrayList();
		}

}
