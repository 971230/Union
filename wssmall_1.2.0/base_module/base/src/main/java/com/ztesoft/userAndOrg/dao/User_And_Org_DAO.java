package com.ztesoft.userAndOrg.dao;

import java.util.List;

import zte.net.ecsord.params.nd.vo.Organization;
import zte.net.ecsord.params.nd.vo.User;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.framework.database.SqlBuilderInterface;

public class User_And_Org_DAO {

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
	public List<User> qryUserList(User pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("es_adminuser", pojo, sqlBuilds);
	}
	
	@SuppressWarnings("unchecked")
	public List<Organization> qryOrganizationList(Organization pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception{
		return this.jdbcDao.queryPojoList("es_organization", pojo, sqlBuilds);
	}
}
