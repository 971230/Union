package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_USER_TEAM;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_USER_TEAM_DAO {

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
	public List<ES_USER_TEAM> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_USER_TEAM.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}

	public void save(String operate,ES_USER_TEAM pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_USER_TEAM", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_USER_TEAM> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_USER_TEAM", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_USER_TEAM> qryListWithPageNo(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_USER_TEAM", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("ES_USER_TEAM", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_USER_TEAM> qryPojoList(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_USER_TEAM", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_USER_TEAM", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_USER_TEAM pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_USER_TEAM", pojo, sqlBuilds);
	}
	
	public String getNewId() throws Exception{
		String id = this.jdbcDao.getSequences("seq_es_user_team", "5", 0);
		
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
