package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.framework.database.Page;

public interface IGoodsOrgManager {
	    //当前登录人员所在组织
		public List getCurrOrgs(String login_org_id);
		//当前登录人员下级组织
		public List getChildrenOrgs(String org_id);
		//qryChildrenOrgCount
		public int qryChildrenOrgCounts(String org_id);
		
		//得到管理员对应组织
		public List getRootOrgs();
		
		public void publish(String ids,String type,String goodsId);
		
		public List qryTree();
		
		public List saleQryTree();
		public List alreadyPublish(String goodsId);
		
		public int addOrgs(String parent_party_id, String org_code, String org_name, String org_type_id, String union_org_code, String org_content);
		
		public int updateOrgs(String party_id, String org_code,  String org_name, String org_type_id, String union_org_code, String org_content);
		
		public int delOrgs(String party_id);
		
		public Page list(String login_org_id, String party_id, String org_name, String org_type_id, String org_content, 
				String  union_org_code, String lan_id, int pageIndex, int pageSize);
		
		//当前org_code是否存在
		public boolean ifExistsOrg(String org_code);
}
