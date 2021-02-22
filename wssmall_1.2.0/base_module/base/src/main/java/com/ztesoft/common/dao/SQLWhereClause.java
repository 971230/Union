package com.ztesoft.common.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;

public class SQLWhereClause {
	
	private StringBuffer whereCond = null ;
	private List para = null ;
	
	public static final boolean PAGING = true ;
	public static final boolean NO_PAGING = false ;
	
	private int pageSize = -1 ;
	private int pageIndex = -1 ;
	
	
	
	public SQLWhereClause(){
		
	}
	
	/**
	 * 传递whereColumn对象，map字段全部拼凑为条件
	 * @param whereColumn
	 */
	public SQLWhereClause(Map whereColumn){
		wrapSQL(whereColumn , null, null ) ;
	}
	
	/**
	 * 根据过滤条件拼条件
	 * @param whereColumn 条件map
	 * @param filterStr 过滤字段，值格式为c1,c2,c3...,每个值与条件map的key对应，否则过滤不起作用
	 */
	public SQLWhereClause(Map whereColumn , String filterStr){
		wrapSQL( whereColumn ,  filterStr , null ) ;
	}
	
	/**
	 * 
	 * @param whereColumn 条件map
	 * @param filterStr 过滤字段，值格式为c1,c2,c3...,每个值与条件map的key对应，否则过滤不起作用
	 * @param changeName 根据有些需求，把原先whereColumn 里面的key 换成changeName 同key对应的 value替代
	 */
	public SQLWhereClause(Map whereColumn , String filterStr , Map changeName ){
		wrapSQL( whereColumn ,  filterStr , changeName) ;
	}
	
	
	/**
	 * 
	 * 分页条件处理，推荐使用该方法
	 * @param dto
	 * @param filterStr 过滤字段，值格式为c1,c2,c3...,每个值与条件map的key对应，否则过滤不起作用
	 * @param changeName 根据有些需求，把原先whereColumn 里面的key 换成changeName 同key对应的 value替代
	 * 
	 */
	public  SQLWhereClause(DynamicDict dto , String filterStr , Map changeName , boolean paging ){
		Map whereColumn = Const.getParam(dto) ;
		//分页处理
		if(paging == SQLWhereClause.PAGING){
			this.pageSize = Const.getPageSize(whereColumn) ;
			this.pageIndex = Const.getPageIndex(whereColumn) ;
		}
		
		wrapSQL( whereColumn ,  filterStr,  changeName ) ;
	}
	
	/**
	 * 
	 * 分页条件处理，推荐使用该方法
	 * @param dto
	 * @param filterStr 过滤字段，值格式为c1,c2,c3...,每个值与条件map的key对应，否则过滤不起作用
	 * @param changeName 根据有些需求，把原先whereColumn 里面的key 换成changeName 同key对应的 value替代
	 * 
	 */
	public  SQLWhereClause(DynamicDict dto , String filterStr , Map changeName ){
		Map whereColumn = Const.getParam(dto) ;
		
		this.pageSize = Const.getPageSize(whereColumn) ;
		this.pageIndex = Const.getPageIndex(whereColumn) ;
		wrapSQL( whereColumn ,  filterStr,  changeName ) ;
	}
	private void wrapSQL(Map whereColumn , String filterStr, Map changeName ){
		if(whereColumn != null && !whereColumn.isEmpty()){
			//转化过滤条件为字符数组
			String[] filterColumn = null ;
			if(filterStr != null && !"".equals(filterStr.trim()))
				filterColumn = filterStr.split(",") ;
			//初始化变量
			whereCond = getEmptyStringBuffer() ;
			para = getEmptyList() ;
			//组织条件
			Iterator it = whereColumn.keySet().iterator() ;
			while( it.hasNext()) {
				String cname = (String)it.next() ;
				//判断是否需要过滤
				if(contains(filterColumn ,  cname ))
					continue ;
				
				String cvalue = (String) whereColumn.get(cname) ;
				
				//专名操作
				String haveChangeName = getChangeName( changeName ,  cname ) ;
				if(!"".equals(haveChangeName))
					cname = (String)it.next() ;
				
				whereCond.append(" and ").append(cname).append("=? ") ;
				para.add(cvalue) ;
			}
			
		}
	}
	
	/**
	 * 专名操作
	 * @param changeName 专名集合
	 * @param cname 被转名
	 * @return
	 */
	private static String getChangeName(Map changeName , String cname ){
		if(changeName == null || changeName.isEmpty())
			return getEmptyStr() ;
		return changeName.containsKey(cname) ? 
				(String)changeName.get(cname) : getEmptyStr() ;
	}
	
	/**
	 * 判定数据是否存在值为cname，存在为true
	 * @param filterColumn
	 * @param cname
	 * @return
	 */
	private static boolean contains(String[] filterColumn , String cname ){
		//不需要过滤
		
		if(filterColumn == null || filterColumn.length == 0 ||
				cname == null || "".equals(cname.trim()))
			return false ;
		
		//遍历、比较
		boolean result = false ;
		for(int i = 0 ,j=filterColumn.length ; i < j ; i++){
			if(cname.equals(filterColumn[i])){
				result = true ;
				break ;
			}
				
		}
		return result ;
	}

	
	public List getPara() {
		if(this.para == null )
			return getEmptyList() ;
		return para;
	}
	
	public void setPara(List para) {
		this.para = para;
	}
	
	public String getWhereCond() {
		if(this.whereCond == null )
			return getEmptyStr() ;
		return whereCond.toString();
	}
	
	public void setWhereCond(StringBuffer whereCond) {
		this.whereCond = whereCond;
	}
	
	public static String getEmptyStr(){
		return "" ;
	}
	
	public static StringBuffer getEmptyStringBuffer(){
		return new StringBuffer() ;
	}
	
	public static List getEmptyList(){
		return new ArrayList() ;
	}
	public int getPageIndex() throws FrameException {
		if(pageIndex == -1)
			throw new FrameException("pageIndex未被初始化，请确认!") ;
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() throws FrameException {
		if(pageSize == -1)
			throw new FrameException("pageSize未被初始化，请确认!") ;
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
