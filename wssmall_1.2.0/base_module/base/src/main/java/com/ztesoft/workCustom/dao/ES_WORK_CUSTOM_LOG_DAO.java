package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_LOG;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_WORK_CUSTOM_LOG_DAO {

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
	
	public String getSequence(String seq_name) throws Exception{
		return this.jdbcDao.getSequences(seq_name);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_LOG> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_WORK_CUSTOM_LOG.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}

	public void save(String operate,ES_WORK_CUSTOM_LOG pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_WORK_CUSTOM_LOG", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_WORK_CUSTOM_LOG> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_WORK_CUSTOM_LOG", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_LOG> qryListWithPageNo(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_WORK_CUSTOM_LOG", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("select * from ES_WORK_CUSTOM_LOG a ", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_LOG> qryPojoList(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_LOG", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_WORK_CUSTOM_LOG", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_WORK_CUSTOM_LOG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_WORK_CUSTOM_LOG", pojo, sqlBuilds);
	}
	
	public String getNewCfgId() throws Exception{
		String id = this.jdbcDao.getSequences("seq_es_work_cfg", "5", 0);
		
		return id;
	}
	
	public String getNewVersionId() throws Exception{
		String id = this.jdbcDao.getSequences("seq_es_work_version", "5", 0);
		
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
