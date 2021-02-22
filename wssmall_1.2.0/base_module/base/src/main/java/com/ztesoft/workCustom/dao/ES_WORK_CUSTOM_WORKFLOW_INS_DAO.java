package com.ztesoft.workCustom.dao;

import java.util.List;
import java.util.Map;

import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_WORKFLOW_INS;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_WORK_CUSTOM_WORKFLOW_INS_DAO {

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
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_WORK_CUSTOM_WORKFLOW_INS.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}

	public void save(String operate,ES_WORK_CUSTOM_WORKFLOW_INS pojo,String[] conditionCol) throws Exception{
		this.jdbcDao.save("ES_WORK_CUSTOM_WORKFLOW_INS", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_WORK_CUSTOM_WORKFLOW_INS> pojoList,
			String[] conditionCol) throws Exception{
		this.jdbcDao.saveBatch("ES_WORK_CUSTOM_WORKFLOW_INS", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryListWithPageNo(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_WORK_CUSTOM_WORKFLOW_INS", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("ES_WORK_CUSTOM_WORKFLOW_INS", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryPojoList(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_WORKFLOW_INS", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryPojoListFromAll(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		String sql = "SELECT * FROM ES_WORK_CUSTOM_WORKFLOW_INS union all SELECT * FROM ES_WORK_CUSTOM_WORKFLOW_INS_f";
		return this.jdbcDao.queryPojoList(sql, pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_WORKFLOW_INS> qryPojoListFromHis(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_WORKFLOW_INS_H", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_WORK_CUSTOM_WORKFLOW_INS", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_WORK_CUSTOM_WORKFLOW_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_WORK_CUSTOM_WORKFLOW_INS", pojo, sqlBuilds);
	}
}
