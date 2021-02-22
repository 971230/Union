package com.ztesoft.common.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.ibss.common.dao.QueryFilter;
import com.ztesoft.ibss.common.dao.QueryFilterFactory;

/**
 * 
 * @Classname : PageManager
 * @Description : 分页管理工具类
 * @Copyright ? 2006 ZTEsoft.
 * @Author : fjy
 * @Create Date : 2006-3-30
 * 
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public class PageHelper {

	/**
	 * 从DAO中根据查询条件，和分页的设置获取当前的分页的PageModel（包含了数据列表）
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(DAO dao, String countCond, String whereCond, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// 计算 totalCount
		int totalCount = new Long(dao.countByCond(countCond)).intValue(); // long  to  int
		pageModel.setTotalCount(totalCount);

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
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

		// 通过QueryFilter在DAO中过滤出当前的分页信息。
		// pageModel的信息直接在输入参数pageModel中获取。
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}
	
	/**
	 * 从DAO中根据查询条件，和分页的设置获取当前的分页的PageModel（包含了数据列表）
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(DAO dao, String whereCond, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// 计算 totalCount
		int totalCount = new Long(dao.countByCond(whereCond)).intValue(); // long  to  int
		pageModel.setTotalCount(totalCount);

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
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

		// 通过QueryFilter在DAO中过滤出当前的分页信息。
		// pageModel的信息直接在输入参数pageModel中获取。
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}
	
	/**
	 * 从DAO中根据查询条件，和分页的设置获取当前的分页的PageModel（包含了数据列表）
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModelNoCount(DAO dao, String whereCond, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// 计算 totalCount
		int totalCount = 10000; // long  to  int
		pageModel.setTotalCount(totalCount);

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
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

		// 通过QueryFilter在DAO中过滤出当前的分页信息。
		// pageModel的信息直接在输入参数pageModel中获取。
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		dao.findByCond(whereCond, queryFilter);

		return pageModel;
	}
	
	/*
	*//**
	 * 从DAO中根据查询条件，和分页的设置获取当前的分页的PageModel（包含了数据列表）
	 * 
	 * @param dao
	 * @param whereCond
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 *//*
	public static PageModel popupPageModel(SQLQry sqlQry, String countSql,String sql, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();
		// 计算 totalCount
		int totalCount = new Long(sqlQry.selectCount(countSql)).intValue(); // long  to  int
		pageModel.setTotalCount(totalCount);

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
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

		// 通过QueryFilter在DAO中过滤出当前的分页信息。
		// pageModel的信息直接在输入参数pageModel中获取。
		QueryFilter queryFilter = QueryFilterFactory.getPageQueryFilter(pageModel);
		
		
		sqlQry.select(sql, queryFilter);

		return pageModel;
	}
	*/
	
	/**
	 * 从list中根据查询条件，和分页的设置获取当前的分页的PageModel（包含了数据列表）
	 * 
	 * @param list
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static PageModel popupPageModel(List list, int pageIndex, int pageSize) {
		PageModel pageModel = new PageModel();

		// 计算 totalCount
		if(list==null) return pageModel;
		
		int totalCount = list.size(); // long  to  int
		pageModel.setTotalCount(totalCount);

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
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
		
		int index = 0;
		int count = 0;
		int locationInt = (pageModel.getPageIndex() - 1) * pageModel.getPageSize();
		//logger.debug(list.size());
		Iterator iter = list.iterator();
		ArrayList dataList = new ArrayList();
		while(iter.hasNext() && count < pageSize){			
			index++;
			if(index > locationInt){
				count++;
				dataList.add(iter.next());
			}
			else
			  iter.next();			
		}
		//logger.debug(dataList.size());
		pageModel.setList(dataList);
        
		//pageModel.setTotalCount(dataList.size());
	
		return pageModel;
	}

	public static void setPageModel(PageModel pageModel, int totalCount, int pageSize, int pageIndex) {
		// pageCount
		if (totalCount % pageSize > 0) {
			pageModel.setPageCount(totalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(totalCount / pageSize);
		}

		// 边界的处理
		if (pageIndex < 0) {
			pageModel.setPageIndex(1);
		} else if (pageIndex > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
	}

	/**  
	 * 预过滤，主要是为oracle一类的数据库使用rownum等组装sql分页预留。
	 */
	public static String doPreFilter(PageModel pageModel, String sql) {

		int startIndex = (pageModel.getPageIndex() - 1) * pageModel.getPageSize() + 1;
		int endIndex = startIndex + pageModel.getPageSize();
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql + 
				" ) my_table where rownum< " + endIndex + 
				") where my_rownum>= " + startIndex ;

		//log.info("PageHelper.doPreFilter.startIndex=>" + startIndex + "; endIndex=>" + endIndex);
//		log.debug("PageHelper.doPreFilter.sql=>" + sql);
		
		return sql;
	}	
}
