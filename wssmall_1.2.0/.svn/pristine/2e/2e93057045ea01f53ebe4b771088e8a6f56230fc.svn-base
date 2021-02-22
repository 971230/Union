package com.ztesoft.net.eop.resource.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.eop.resource.IOrgManager;
import com.ztesoft.net.eop.resource.model.Org;
import com.ztesoft.net.eop.sdk.database.BaseSupport;

public class OrgManagerImpl  extends BaseSupport<Org> implements IOrgManager {

	@Override
	public String add(Org org) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) throws RuntimeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Org org) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Org get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Org> getOrgList() {
//		return this.baseDaoSupport.queryForList("select o.*,level,rownum from es_org o start  with org_level='1' connect by prior id=parent_id", Org.class);
		return this.baseDaoSupport.queryForList("select o.*,'1' \"LEVEL\","+DBTUtil.andRownum("?")+" from es_org o where org_level='1'", Org.class);
	}
	
	@Override
	public List<Org> getOrgTree(String id) {
		List<Org> orgList = this.getOrgList();
		return orgList;
	}

	@Override
	public void updateSort(String[] ids, String[] sorts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Org> getOrgListByParentId(String parent_id, String level) {
		Map param = new HashMap();
		param.put("parent_id", parent_id);
		return this.baseDaoSupport.queryForListByMap("select o.*, '"+level+"' \"LEVEL\","+DBTUtil.andRownum("?")+" from es_org o where level=1 start  with parent_id=:parent_id connect by prior parent_id=id ", Org.class, param);

	}

}
