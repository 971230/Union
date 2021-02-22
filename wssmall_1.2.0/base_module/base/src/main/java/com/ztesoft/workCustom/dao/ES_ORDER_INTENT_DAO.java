package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_ORDER_INTENT;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_ORDER_INTENT_DAO {
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
	
	public void save(String operate,ES_ORDER_INTENT pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_ORDER_INTENT", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_ORDER_INTENT> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_ORDER_INTENT", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_ORDER_INTENT> qryPojoList(ES_ORDER_INTENT pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_ORDER_INTENT", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_ORDER_INTENT pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_ORDER_INTENT", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_ORDER_INTENT pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_ORDER_INTENT", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public void insert(String table, Map<String,Object> fields, Object... args) throws Exception{
		this.jdbcDao.insert(table, fields, args);
	}
}
