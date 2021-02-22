package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_WORK_ORDER;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_WORK_ORDER_DAO {
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
	
	public void save(String operate,ES_WORK_ORDER pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_WORK_ORDER", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_WORK_ORDER> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_WORK_ORDER", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_ORDER> qryPojoList(ES_WORK_ORDER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_ORDER", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_WORK_ORDER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_WORK_ORDER", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_WORK_ORDER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_WORK_ORDER", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public void insert(String table, Map<String,Object> fields, Object... args) throws Exception{
		this.jdbcDao.insert(table, fields, args);
	}
}
