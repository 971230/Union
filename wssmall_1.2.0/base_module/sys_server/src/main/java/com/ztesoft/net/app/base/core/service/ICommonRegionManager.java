package com.ztesoft.net.app.base.core.service;

import java.util.List;

public interface ICommonRegionManager {
	    
		//当前登录人员下级组织
		public List getChildrenOrg(String region_id);
		//qryChildrenOrgCount
		public int qryChildrenRegionCount(String region_id);
		
		//得到管理员对应组织
		public List getRootRegion();
		
		public void addRegion(String parent_region_id,String region_id, String region_code, String region_name,String region_desc,String region_level);
		
		public void modRegion(String region_id, String region_code, String region_name,String region_desc);
		
		public void delRegion(String region_id);
		
		//当前org_code是否存在
		public boolean ifExistsRegion(String region_code);
}
