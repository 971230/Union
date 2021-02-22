package com.ztesoft.net.app.base.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.net.app.base.core.service.ICommonRegionManager;
import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.mall.core.consts.Consts;
import commons.CommonTools;

public class CommonRegionManagerImpl extends BaseSupport implements ICommonRegionManager{

	@Override
	public List getChildrenOrg(String region_id) {
		// TODO Auto-generated method stub
	    List children_list  = new ArrayList();
		
		String sql ="select a.region_id,a.parent_region_id,a.region_name,a.region_code,a.region_level,a.region_desc,(select b.region_name from es_common_region b where b.region_id=a.parent_region_id and b.source_from = a.source_from) parent_region_name from es_common_region a where a.source_from=? and a.default_flag='1' and a.parent_region_id=?";
		
		children_list = baseDaoSupport.queryForList(sql, CommonTools.getSourceForm(),region_id);
		
		return children_list;
		
	}

	@Override
	public int qryChildrenRegionCount(String region_id) {
		// TODO Auto-generated method stub
		String sql ="select count(*) from es_common_region where default_flag ='1' and source_from=? and parent_region_id=?";
	    int count = 0;
	    count = this.baseDaoSupport.queryForInt(sql, CommonTools.getSourceForm(),region_id);
		return count;
	}

	@Override
	public List getRootRegion() {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		String sql ="select a.region_id,a.parent_region_id,a.region_name,a.region_code,a.region_level,a.region_desc,(select b.region_name from es_common_region b where b.region_id=a.parent_region_id and b.source_from = a.source_from) parent_region_name from es_common_region a  where a.source_from=? and a.default_flag='1' and a.parent_region_id='-1'";
		list = this.baseDaoSupport.queryForList(sql, CommonTools.getSourceForm());
		return list;
	}

	@Override
	public void addRegion(String parent_region_id, String region_id,
			String region_code, String region_name, String region_desc,
			String region_level) {
		// TODO Auto-generated method stub
		Map fields = new HashMap();
		String new_party_id = baseDaoSupport.getSequences("seq_es_common_region", "5", 0);
		fields.put("region_id", new_party_id);
		fields.put("region_code", region_code);
		fields.put("parent_region_id", parent_region_id);
		fields.put("region_name", region_name);
		fields.put("region_desc", region_desc);
		fields.put("region_level", region_level);
		fields.put("default_flag", "1");
		fields.put("create_date", Consts.SYSDATE);
	
	    this.baseDaoSupport.insert("es_common_region", fields);
	}

	@Override
	public void modRegion(String region_id, String region_code,
			String region_name, String region_desc) {
		// TODO Auto-generated method stub
		Map fields = new HashMap();
		Map where = new HashMap();
		
		fields.put("region_name", region_name);
		fields.put("region_desc", region_desc);
		fields.put("region_code", region_code);
		
		where.put("region_id", region_id);
		where.put("default_flag", "1");
	
		this.baseDaoSupport.update("es_common_region", fields, where);
		
	}

	@Override
	public void delRegion(String region_id) {
		// TODO Auto-generated method stub
		Map fields = new HashMap();
		Map where = new HashMap();
		
	
		fields.put("default_flag", "2");
		
		where.put("region_id", region_id);
	    this.baseDaoSupport.update("es_common_region", fields, where);
		
	}



	@Override
	public boolean ifExistsRegion(String region_code) {
		// TODO Auto-generated method stub
		String sql = " select count(*) from es_common_region where region_code=? and default_flag='1' ";
		int cnt = this.baseDaoSupport.queryForInt(sql, region_code);
		if(cnt > 0){
			return true;
		}
		return false;
	}

}
