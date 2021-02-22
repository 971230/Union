package com.ztesoft.workCustom.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import zte.net.ecsord.params.workCustom.po.ES_WORK_CUSTOM_NODE_INS;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class ES_WORK_CUSTOM_NODE_INS_DAO {

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
	public List<ES_WORK_CUSTOM_NODE_INS> qryForListByArgs(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, ES_WORK_CUSTOM_NODE_INS.class, args);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(String sql, Object... args){
		return this.jdbcDao.queryForList(sql, args);
	}
	
	private void setTime(ES_WORK_CUSTOM_NODE_INS pojo) throws Exception{
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		String date = format.format(now);
		
		if("1".equals(pojo.getIs_curr_step()) ||
				"1".equals(pojo.getIs_done())){
			
			//是当前环节或者是已完成的环节，说明环节已经执行或正在执行
			
			//如果创建时间为空，则设置创建时间
			if(StringUtils.isBlank(pojo.getCreate_date()))
				pojo.setCreate_date(date);
			
			//环节出于完成或者异常状态的必须加更新时间
			if("error".equals(pojo.getState_code()) ||
					"skip".equals(pojo.getState_code()) ||
					"finish".equals(pojo.getState_code()) ||
					"fail".equals(pojo.getState_code())
					){
				if(StringUtils.isBlank(pojo.getUpdate_date()))
					pojo.setUpdate_date(date);
			}
		}
	}

	public void save(String operate,ES_WORK_CUSTOM_NODE_INS pojo,String[] conditionCol) throws Exception{
		this.setTime(pojo);
		
		this.jdbcDao.save("ES_WORK_CUSTOM_NODE_INS", operate, pojo, conditionCol);
	}
	
	public void saveBatch(String operate,List<ES_WORK_CUSTOM_NODE_INS> pojoList,
			String[] conditionCol) throws Exception{
		
		if(pojoList!=null && pojoList.size()>0){
			for(int i=0;i<pojoList.size();i++){
				this.setTime(pojoList.get(i));
			}
		}
		
		this.jdbcDao.saveBatch("ES_WORK_CUSTOM_NODE_INS", operate, pojoList, conditionCol);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_NODE_INS> qryListWithPageNo(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPojoListWithPageNo("ES_WORK_CUSTOM_NODE_INS", pojo, 
				sqlBuilds, pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	public Page qryPageByPojo(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds,int pageNo,int pageSize) throws Exception{
		return this.jdbcDao.queryPageByPojo("ES_WORK_CUSTOM_NODE_INS", pojo, sqlBuilds, pageNo, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_NODE_INS> qryPojoList(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_NODE_INS", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_NODE_INS> qryPojoListFromAll(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		String sql = "SELECT * FROM es_work_custom_node_ins union all SELECT * FROM es_work_custom_node_ins_f";
		return this.jdbcDao.queryPojoList(sql, pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public List<ES_WORK_CUSTOM_NODE_INS> qryPojoListFromHis(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("ES_WORK_CUSTOM_NODE_INS_H", pojo, sqlBuilds);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> qryForMapList(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryForMapList("ES_WORK_CUSTOM_NODE_INS", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public int qryCountByPojo(ES_WORK_CUSTOM_NODE_INS pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryCountByPojo("ES_WORK_CUSTOM_NODE_INS", pojo, sqlBuilds);
	}
	
	public String getNewInstanceId() throws Exception{
		String id = this.jdbcDao.getSequences("seq_es_work_instance", "5", 0);
		
		return id;
	}
}
