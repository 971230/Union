package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_CFG;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_WORK_CUSTOM_CFG_DAO {

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
	public List<ES_WORK_CUSTOM_CFG> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_WORK_CUSTOM_CFG.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}

	public void save(String operate,ES_WORK_CUSTOM_CFG pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_WORK_CUSTOM_CFG", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_WORK_CUSTOM_CFG> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_WORK_CUSTOM_CFG", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_CFG> qryListWithPageNo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_WORK_CUSTOM_CFG", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("select * from es_work_custom_cfg a order by a.cfg_id", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public Page qryHisPageByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("select * from es_work_custom_cfg_h a order by a.cfg_id", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_CFG> qryPojoList(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_CFG", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_WORK_CUSTOM_CFG", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_WORK_CUSTOM_CFG", pojo, sqlBuilds);
	}
	
	public String getNewCfgId() throws Exception{
		String id = this.jdbcDao.getSequences("seq_es_work_cfg", "5", 0);
		
		return id;
	}
	
	public String getNewVersionId() throws Exception{
		String id = this.jdbcDao.getSequences("seq_es_work_version", "5", 0);
		
		return id;
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_CFG> qryPojoListFromAll(ES_WORK_CUSTOM_CFG pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		String sql = "SELECT * FROM es_work_custom_cfg union all SELECT * FROM es_work_custom_cfg_h";
		return this.jdbcDao.queryPojoList(sql, pojo, sqlBuilds);
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
