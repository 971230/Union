package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_DEALER;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_WORK_CUSTOM_DEALER_DAO {

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
	public List<ES_WORK_CUSTOM_DEALER> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_WORK_CUSTOM_DEALER.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}

	public void save(String operate,ES_WORK_CUSTOM_DEALER pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_WORK_CUSTOM_DEALER", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_WORK_CUSTOM_DEALER> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_WORK_CUSTOM_DEALER", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_DEALER> qryListWithPageNo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_WORK_CUSTOM_DEALER", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("select * from es_work_custom_dealer a order by a.deal_id", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public Page qryHisPageByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("select * from es_work_custom_dealer_h a order by a.deal_id", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_DEALER> qryPojoList(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_DEALER", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_WORK_CUSTOM_DEALER", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_WORK_CUSTOM_DEALER", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_DEALER> qryPojoListFromAll(ES_WORK_CUSTOM_DEALER pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		String sql = "SELECT * FROM ES_WORK_CUSTOM_DEALER union all SELECT * FROM ES_WORK_CUSTOM_DEALER_H";
		return this.jdbcDao.queryPojoList(sql, pojo, sqlBuilds);
	}
}
