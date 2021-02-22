package com.ztesoft.net.app.base.core.service.impl;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.ibss.common.util.CrmConstants;
import com.ztesoft.net.app.base.core.model.Org;
import com.ztesoft.net.app.base.core.service.IOrgAdminManager;
import com.ztesoft.net.eop.resource.model.AdminUser;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.model.OrgPart;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.mall.core.utils.ManagerUtils;
import com.ztesoft.net.sqls.SqlLikeBuilder;
import com.ztesoft.net.sqls.SqlUtil;

import commons.CommonTools;
import consts.ConstsCore;

import org.apache.commons.lang.StringUtils;

import params.orgAdmin.req.OrgAdminListReq;
import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.Organization;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrgAdminManager extends BaseSupport implements IOrgAdminManager{

	@Override
	public List getCurrOrg(String login_org_id) throws Exception {
		List org_list = new ArrayList();
		
		OrgPart pojo = new OrgPart();
		pojo.setParty_id(login_org_id);
		
		org_list = this.getOrgMapListByPojo(pojo,null);
		
		return org_list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getChildrenOrg(String org_id) throws Exception{
		List children_list  = new ArrayList();
		OrgPart pojo = new OrgPart();
		pojo.setParent_party_id(org_id);
		
		children_list = this.getOrgMapListByPojo(pojo,null);
		
		return children_list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getRootOrg() throws Exception {
		List org_list = new ArrayList();
		OrgPart pojo = new OrgPart();
		pojo.setParent_party_id("-1");
		
		org_list = this.getOrgMapListByPojo(pojo,null);
		
		return org_list;
	}

	@Override
	public int qryChildrenOrgCount(String org_id) {

		int cnt = 0;
		String sql = "select count(*) from es_organization a, es_organization_type b " +
				"where a.org_type_id = b.org_type_id and a.parent_party_id=? and " +
				"a.status_cd='00A' and a.source_from = b.source_from and a.source_from = '" + ManagerUtils.getSourceFrom() + "' ";
		
		 cnt = baseDaoSupport.queryForInt(sql, org_id);
		
		return cnt;
	}

	@Override
	public int addOrg(String parent_party_id, String org_code, String org_name,
			String org_type_id, String union_org_code, String org_content,String org_level,String lan_id,String region_id,String channel_type) {
		
		
		Map fields = new HashMap();
		String new_party_id = baseDaoSupport.getSequences("seq_es_organization", "5", 0);
		fields.put("party_id", new_party_id);
		fields.put("org_code", org_code);
		fields.put("parent_party_id", parent_party_id);
		fields.put("org_name", org_name);
		fields.put("status_date", DBTUtil.current());
		fields.put("org_type_id", org_type_id);
		fields.put("union_org_code", union_org_code);
		fields.put("org_content", org_content);
		fields.put("status_cd", "00A");
		fields.put("org_level", org_level);
		fields.put("lan_id", lan_id);
		fields.put("region_id", region_id);
		fields.put("channel_type", channel_type);
		
		try{
			baseDaoSupport.insert("es_organization", fields);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	@Override
	public int modOrg(String party_id, String org_code, String org_name, String org_type_id, String union_org_code, String org_content,String channel_type){
		
		Map fields = new HashMap();
		Map where = new HashMap();
		
//		fields.put("org_code", org_code);
		fields.put("org_name", org_name);
		fields.put("channel_type", channel_type);
//		fields.put("org_type_id", org_type_id);
//		fields.put("union_org_code", union_org_code);
		fields.put("org_content", org_content);
		where.put("party_id", party_id);
		where.put("status_cd", "00A");
		try{
			baseDaoSupport.update("es_organization", fields, where);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	@Override
	public int delOrg(String party_id) {
		
		Map fields = new HashMap();
		Map where = new HashMap();
		
	
		fields.put("status_cd", "00X");
		
		where.put("party_id", party_id);
		try{
			baseDaoSupport.update("es_organization", fields, where);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		return 0;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Page list(OrgAdminListReq orgAdminListReq) throws Exception {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" select a.party_id, ");
		sql.append(" a.parent_party_id, ");
		sql.append(" nvl(p.org_name, '') parent_org_name, ");
		sql.append(" a.org_code, ");
		sql.append(" a.org_name, ");
		sql.append(" a.org_level, ");
		sql.append(" a.org_type_id, ");
		sql.append(" nvl(b.org_type_name, '') org_type_name, ");
		sql.append(" a.org_content, ");
		sql.append(" a.union_org_code, ");
		sql.append(" a.lan_id, ");
		sql.append(" nvl(D.lan_name, '') lan_name ");
		sql.append(" from es_organization      a, ");
		sql.append(" es_organization_type b, ");
		sql.append(" es_organization      p, ");
		sql.append(" es_lan               d ");
		sql.append(" where a.org_type_id = b.org_type_id(+) ");
		sql.append(" and a.lan_id = D.Lan_Id(+) ");
		sql.append(" and a.parent_party_id = p.party_id(+) ");
		sql.append(" and a.status_cd = '00A' ");
		sql.append(" and a.source_from = b.source_from ");
		sql.append(" and a.source_from = 'ECS' ");
		
		if(StringUtils.isNotBlank(orgAdminListReq.getOrg_name())){
			sql.append(" and a.org_name like '%").append(orgAdminListReq.getOrg_name()).append("%'");
		}
		
		if(StringUtils.isNotBlank(orgAdminListReq.getParty_id())){
			sql.append(" and a.party_id ='").append(orgAdminListReq.getParty_id()).append("'");
		}
		
		if(StringUtils.isNotBlank(orgAdminListReq.getLan_id())){
			sql.append(" and a.lan_id ='").append(orgAdminListReq.getLan_id()).append("'");
		}else{
			AdminUser user = ManagerUtils.getAdminUser();
			
			if(!"1".equals(user.getPermission_level())){
				sql.append(SqlUtil.getSqlInStr("a.lan_id", user.getPermission_region(), false, true));
			}
		}
		
	    Page webpage = this.baseDaoSupport.queryForPageByMap(sql.toString(), 
	    		orgAdminListReq.getPageIndex(), orgAdminListReq.getPageSize(), Org.class,null);
	    
		return webpage;
	}


	@Override
	public boolean ifExistsOrg(String org_code) {
		String sql = " select count(*) from es_organization where org_code=? and status_cd='00A' ";
		int cnt = this.baseDaoSupport.queryForInt(sql, org_code);
		if(cnt > 0){
			return true;
		}
		return false;
	}


	@Override
	public Page listPart(int pageNo, int pageSize, String par_partId,
			String org_name) throws Exception {
		// TODO Auto-generated method stub
		
		String sql ="select a.party_id,a.org_name,a.org_code,a.org_content,b.org_type_name,a.union_org_code," +
		            " (select c.org_name from es_organization c where a.parent_party_id = c.party_id and c.source_from = a.source_from) parent_org_name,"+
				    " a.channel_type,(select ch.parent_channel_id from es_org_channel ch where ch.channel_id=a.channel_type and ch.source_from =a.source_from) parent_channel_type from   es_organization a inner join es_organization_type b on a.org_type_id =b.org_type_id where a.parent_party_id =? and a.status_cd='00A' and a.source_from =? and b.source_from =?";
		String countSql ="select count(*) from  es_organization a inner join es_organization_type b on a.org_type_id =b.org_type_id where a.parent_party_id =? and a.status_cd='00A' and a.source_from =? and b.source_from =?";
	
		
		if(org_name!=null&&!"".equals(org_name)){
			sql +=" and a.org_name like '%"+org_name+"%'";
		}
		sql +=" order by a.org_type_id, a.party_id";
		
		return this.baseDaoSupport.queryForCPage(sql, pageNo, pageSize, OrgPart.class,countSql, par_partId,CommonTools.getSourceForm(),CommonTools.getSourceForm());
		//return this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, par_partId,CommonTools.getSourceForm(),CommonTools.getSourceForm());
	}


	@Override
	public List listLanByProvId(String party_id) {
		// TODO Auto-generated method stub
		String sql = "";
		if(ConstsCore.SOURCE_FROM_ECS.equals(ManagerUtils.getSourceFrom())){
			sql = "select org_code lan_id,org_name lan_name from es_organization    where org_level = 3   and parent_party_id =? and source_from=? " ;
		}else{
			 sql ="select lan_id,lan_name from es_lan where prov_id =? and source_from =?";
		}
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql, party_id,CommonTools.getSourceForm());
		return list;
	}


	@Override
	public List listRegionByLanId(String lan_id) {
		// TODO Auto-generated method stub
		String sql = "select region_id,region_name from es_rr_region where lan_id=? and source_from ='"+CommonTools.getSourceForm()+"'";
	    List list = new ArrayList();
	    list = this.baseDaoSupport.queryForList(sql, lan_id);
		return list;
	}


	@Override
	public String getOrgTypeName(String org_level) {
		// TODO Auto-generated method stub
		String orgTypeName = "";
		String sql = "select org_type_name from es_organization_type  type where type.org_type_id =? and source_from ='"+CommonTools.getSourceForm()+"'";
		orgTypeName = this.baseDaoSupport.queryForString(sql,org_level);
		return orgTypeName;
	}


	@Override
	public List listOrgChannelByType(String channel_type_id) {
		// TODO Auto-generated method stub
		String sql ="select * from es_org_channel where parent_channel_id = ? and source_from =?";
		return this.baseDaoSupport.queryForList(sql, channel_type_id,CommonTools.getSourceForm());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> getHasChildOrg(List<String> orgIdList) throws Exception {
		Set<String> ret = new HashSet<String>();
		
		String sql = "SELECT a.party_id FROM es_organization a WHERE exists " + 
				"(SELECT b.party_id FROM es_organization b WHERE b.parent_party_id = a.party_id) {condition} ";
		
		String inSql = SqlUtil.getSqlInStr("a.party_id", orgIdList, false,true);
		
		sql = sql.replace("{condition}", inSql);
		
		List<Map> list = baseDaoSupport.queryForList(sql);
		
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map m = list.get(i);
				String partyId = String.valueOf(m.get("party_id"));
				ret.add(partyId);
			}
		}
		
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page searchOrgPage(int pageNo,int pageSize,OrgPart pojo) throws Exception {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String sql = cacheUtil.getConfigInfo("SYN_ORG_USER_QUERY_ORG");
		
		if(StringUtils.isBlank(sql))
			throw new Exception("组织查询SQL配置为空");
		
		List<SqlBuilderInterface> sqlBuildList = new ArrayList<SqlBuilderInterface>();
		SqlLikeBuilder orgNameBuilder = new SqlLikeBuilder("org_name", pojo.getOrg_name());
		sqlBuildList.add(orgNameBuilder);
		
		return this.baseDaoSupport.queryPageByPojo(sql, pojo, sqlBuildList , pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getOrgMapListByPojo(OrgPart pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		List org_list = new ArrayList();
		
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String sql = cacheUtil.getConfigInfo("SYN_ORG_USER_QUERY_ORG");
		
		if(StringUtils.isBlank(sql))
			throw new Exception("组织查询SQL配置为空");
		
		org_list = baseDaoSupport.queryForMapList(sql, pojo , sqlBuilds);
		
		return org_list;
	}

	@Override
	public List getAllLan() throws Exception {
		String sql = "SELECT a.lan_id,a.lan_name FROM es_lan a";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@Override
	public List getAllRegioin() throws Exception {
		String sql = "SELECT a.region_id,a.region_name,a.parent_region_id FROM es_common_region a";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@Override
	public List getAllOrgChannel() throws Exception {
		String sql = "SELECT a.channel_id,a.parent_channel_id,a.channel_name FROM es_org_channel a";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@Override
	public List getAllOrgType() throws Exception {
		String sql = "SELECT a.org_type_id,a.org_type_name FROM es_organization_type a WHERE a.source_from='ECS'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@Override
	public String addOrg(Organization org) throws Exception {
		if(org == null)
			throw new Exception("传入组织对象为空");
		
		if(StringUtils.isBlank(org.getParty_id()))
			throw new Exception("传入组织编号为空");
		
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		Date now = new Date();
		
		AdminUser user = ManagerUtils.getAdminUser();
		
		if(user!=null){
			org.setCreate_user_id(user.getUserid());
			org.setUpdate_user_id(user.getUserid());
		}
		
		org.setStatus_date(format.format(now));
		org.setUpdate_date(format.format(now));
		org.setSource_from("ECS");
		
		this.baseDaoSupport.save("es_organization", "insert", org, null);
		
		return org.getParty_id();
	}
	
	@Override
	public void modOrg(Organization org) throws Exception {
		if(org == null)
			throw new Exception("传入组织对象为空");
		
		if(StringUtils.isBlank(org.getParty_id()))
			throw new Exception("传入组织编号为空");
		
		SimpleDateFormat format = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
		Date now = new Date();
		
		AdminUser user = ManagerUtils.getAdminUser();
		
		if(user!=null){
			org.setUpdate_user_id(user.getUserid());
		}
		
		org.setUpdate_date(format.format(now));
		org.setSource_from("ECS");
		
		this.baseDaoSupport.save("es_organization", "update", org, new String[]{"party_id"});
	}

	@Override
	public String getNewId() throws Exception {
		String id = baseDaoSupport.getSequences("seq_es_organization", "5", 0);
		return id;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getShowRegioin() throws Exception {
		String sql = "SELECT a.field_value as region_id,a.field_value_desc as region_name,a.col1 as parent_region_id "
				+ " FROM es_dc_public_dict_relation a WHERE a.stype = 'county'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page searchOrgPage(int pageNo, int pageSize, OrgPart pojo,
			List<SqlBuilderInterface> sqlBuilds) throws Exception {
		ICacheUtil cacheUtil = SpringContextHolder.getBean("cacheUtil");
		String sql = cacheUtil.getConfigInfo("SYN_ORG_USER_QUERY_ORG");
		
		if(StringUtils.isBlank(sql))
			throw new Exception("组织查询SQL配置为空");
		
		return this.baseDaoSupport.queryPageByPojo(sql, pojo, sqlBuilds , pageNo, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ES_DC_PUBLIC_DICT_RELATION getCountyMap(String field_value) throws Exception{
		ES_DC_PUBLIC_DICT_RELATION param = new ES_DC_PUBLIC_DICT_RELATION();
		param.setStype("county");
		param.setSource_from("ECS");
		param.setField_value(field_value);
		
		List<ES_DC_PUBLIC_DICT_RELATION> retList = this.baseDaoSupport.
				queryPojoList("es_dc_public_dict_relation", param, null);
		
		if(retList!=null && retList.size()>0){
			return retList.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getBusiCounty() throws Exception {
		String sql = "SELECT a.other_field_value as region_id,a.field_value_desc as region_name,a.col1 as parent_region_id "
				+ " FROM es_dc_public_dict_relation a WHERE a.stype = 'county'";
		
		List list = new ArrayList();
		list = this.baseDaoSupport.queryForList(sql);
		
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCountyByIds(
			List<String> regionIdList, List<String> countyIdList)
			throws Exception {
		StringBuilder builder = new StringBuilder();
		
		builder.append(" SELECT a.field_value as region_id,a.field_value_desc as region_name,a.col1 as parent_region_id ");
		builder.append("  FROM es_dc_public_dict_relation a WHERE a.stype = 'county' ");
		
		if(regionIdList!=null && regionIdList.size()>0){
			builder.append(SqlUtil.getSqlInStr("a.col1", regionIdList, false, true));
		}
		
		if(countyIdList!=null && countyIdList.size()>0){
			builder.append(SqlUtil.getSqlInStr("a.field_value", countyIdList, false, true));
		}
		
		builder.append(" order by a.field_value ");
		
		List list = this.baseDaoSupport.queryForList(builder.toString());
		
		return list;
	}
}
