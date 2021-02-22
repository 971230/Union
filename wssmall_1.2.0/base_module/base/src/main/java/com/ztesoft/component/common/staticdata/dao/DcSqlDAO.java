package com.ztesoft.component.common.staticdata.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;


public class DcSqlDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select dc_name , dc_desc , c.dc_sql from ( "+
								"	select d.dc_name,d.dc_desc,d.dc_sql  from dc_sql d  "+
								"		union all "+
								"	select a.attr_code ,a.attr_name , a.attr_desc   from attr_spec a where attr_code is not null and a.module_id in('PPM','PCM') ) c where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ( "+
								"	select d.dc_name,d.dc_desc,d.dc_sql  from dc_sql d  "+
								"		union all "+
								"	select a.attr_code ,a.attr_name , a.attr_desc   from attr_spec a where attr_code is not null and a.module_id in('PPM','PCM') ) where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into DC_SQL (dc_name, dc_sql,dc_desc) values (?,?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update DC_SQL set dc_name=?, dc_sql=?,dc_desc=?  where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from DC_SQL where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from DC_SQL where dc_name=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update DC_SQL set dc_name=?, dc_sql=?,dc_desc=?  where dc_name=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select d.dc_name,d.dc_sql,d.dc_desc from DC_SQL d where d.dc_name=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public DcSqlDAO() {

	}
	

	/**
	 * Insert参数设置
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	@Override
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
				
		params.add(map.get("dc_name" ));
				
		params.add(map.get("dc_sql" ));
			
		params.add(map.get("dc_desc" ));
		
		return params ;
	}
	

	/**
	 * update 参数设置
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	@Override
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
		params.add(vo.get("dc_name" ));
				
		params.add(vo.get("dc_sql" ));
			
		params.add(vo.get("dc_desc" ));
		
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * 根据主键更新参数设置
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	@Override
	public List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
				
		params.add(vo.get("dc_name" ));
				
		params.add(vo.get("dc_sql" ));
				
		params.add(vo.get("dc_desc" ));
		
		params.add(keyCondMap.get("dc_name")) ;
				
		return params ;
	}
		
		/**
	 * 主键条件参数设置
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	@Override
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
				
		params.add(keyCondMap.get("dc_name")) ;
				
		return params  ;
	}
	
	
	@Override
	public String getDbName(){
		return this.dbName ;
	}
	
	@Override
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE_KEY ;
				
	}
	
	@Override
	public String getUpdateSQLByKey() throws FrameException {
					
		return this.SQL_UPDATE_KEY ;
				
	}
	
	@Override
	public String getSelectSQL(){
		return this.SQL_SELECT ;
	}
	
	@Override
	public String getSelectCountSQL(){
		return this.SQL_SELECT_COUNT ;
	}
	
	@Override
	public String getInsertSQL(){
		return this.SQL_INSERT ;
	}
	
	@Override
	public String getUpdateSQL(){
		return this.SQL_UPDATE ;
	}
	
	@Override
	public String getDeleteSQL(){
		return this.SQL_DELETE ;
	}
	
	@Override
	public String getSQLSQLByKey() throws FrameException {
					
		return this.SQL_SELECT_KEY ;
				
	}
	
}
