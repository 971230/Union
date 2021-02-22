package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_ORDER_LOCK;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_ORDER_LOCK_DAO {

	@SuppressWarnings("rawtypes")
	private IDaoSupport jdbcDao;

	@SuppressWarnings("rawtypes")
	public IDaoSupport getJdbcDao() {
		return jdbcDao;
	}

	@SuppressWarnings("rawtypes")
	public void setJdbcDao(IDaoSupport jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public void execute(String sql, Object... args) {
		this.jdbcDao.execute(sql, args);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_ORDER_LOCK> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_ORDER_LOCK.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}

	public void save(String operate,ES_ORDER_LOCK pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_ORDER_LOCK", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_ORDER_LOCK> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_ORDER_LOCK", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_ORDER_LOCK> qryListWithPageNo(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_ORDER_LOCK", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("select * from ES_ORDER_LOCK a order by a.lock_id", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_ORDER_LOCK> qryPojoList(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_ORDER_LOCK", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_ORDER_LOCK", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_ORDER_LOCK pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_ORDER_LOCK", pojo, sqlBuilds);
	}
	
	public String getNewId() throws Exception{
		String id = this.jdbcDao.getSequences("o_outcall_log", "5", 0);
		
		return id;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryMapList(String sql, Object... args) throws Exception{
		return this.jdbcDao.queryForList(sql, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List qryPojoList(String tableOrSql, Object pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList(tableOrSql, pojo,sqlBuilds);
	}

}
