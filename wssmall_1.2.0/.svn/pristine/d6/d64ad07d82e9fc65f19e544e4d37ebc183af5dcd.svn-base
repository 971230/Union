package com.ztesoft.net.app.base.core.service;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.database.SqlBuilderInterface;
import com.ztesoft.net.mall.core.model.OrgPart;

import java.util.List;
import java.util.Set;

import params.orgAdmin.req.OrgAdminListReq;
import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;
import zte.net.ecsord.params.nd.vo.Organization;


public interface IOrgAdminManager {
	
	//当前登录人员所在组织
	public List getCurrOrg(String login_org_id) throws Exception;
	//当前登录人员下级组织
	public List getChildrenOrg(String org_id) throws Exception;
	//qryChildrenOrgCount
	public int qryChildrenOrgCount(String org_id);
	
	//得到管理员对应组织
	public List getRootOrg() throws Exception;
	
	public int addOrg(String parent_party_id, String org_code, String org_name, String org_type_id, String union_org_code, String org_content,String org_level,String lan_id,String region_id,String channel_type);
	
	public int modOrg(String party_id, String org_code,  String org_name, String org_type_id, String union_org_code, String org_content,String channel_type);
	
	public int delOrg(String party_id);
	
	public Page list(OrgAdminListReq orgAdminListReq) throws Exception;
	
	//当前org_code是否存在
	public boolean ifExistsOrg(String org_code);
	
	public Page listPart(int pageNo,int pageSize,String par_partId,String org_name) throws Exception ;
	
	/**
	 * 
	 * @param party_id  省组织的组织Id 对应es_lan表的prov_id 
	 * @return
	 */
	public List listLanByProvId(String party_id);
	/**
	 * 
	 * @param lan_id 本地网Id 对用组织表的lan_id 
	 * @return
	 */
	public List listRegionByLanId(String lan_id);
	
    public String getOrgTypeName(String org_level);
    
    public List listOrgChannelByType(String channel_type_id);
    
    /**
     * 有下级组织的组织编号Set
     * @param getHasChildOrg
     * @return
     * @throws Exception
     */
    public Set<String> getHasChildOrg(List<String> orgIdList) throws Exception;
    
    /**
     * 查询组织PAGE
     * @param pageNo
     * @param pageSize
     * @param pojo
     * @return
     */
    public Page searchOrgPage(int pageNo,int pageSize,OrgPart pojo) throws Exception;
    
    /**
     * 根据查pojo询组织MAP对象的LIST
     * @param pojo	POJO参数对象
     * @param sqlBuilds	SQL构造器
     * @return	
     * @throws Exception
     */
    public List getOrgMapListByPojo(OrgPart pojo,
    		List<SqlBuilderInterface> sqlBuilds) throws Exception;
    
    /**
     * 获取所有地市
     * @return
     * @throws Exception
     */
    public List getAllLan() throws Exception;
    
    /**
     * 获取所有县区
     * @return
     * @throws Exception
     */
    public List getAllRegioin() throws Exception;
    
    /**
     * 获取管理的渠道类型列表
     * @return
     * @throws Exception
     */
    public List getAllOrgChannel() throws Exception;
    
    /**
     * 获取所有组织类型
     * @return
     * @throws Exception
     */
    public List getAllOrgType() throws Exception;
    
    /**
     * 新增组织
     * @param org
     * @return
     * @throws Exception
     */
    public String addOrg(Organization org) throws Exception;
    
    /**
     * 修改组织
     * @param org
     * @throws Exception
     */
    public void modOrg(Organization org) throws Exception;
    
    /**
     * 获取组织编号
     * @return
     * @throws Exception
     */
    public String getNewId() throws Exception;
    
    /**
     * 获取所有行政县区
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public List getShowRegioin() throws Exception;
    
    /**
     * 查询组织PAGE
     * @param pageNo
     * @param pageSize
     * @param pojo
     * @param sqlBuilds
     * @return
     * @throws Exception
     */
    public Page searchOrgPage(int pageNo,int pageSize,OrgPart pojo,
    		List<SqlBuilderInterface> sqlBuilds) throws Exception;
    
    /**
     * 获取行政县分、营业县分映射
     * @return
     * @throws Exception
     */
    public ES_DC_PUBLIC_DICT_RELATION getCountyMap(String field_value) throws Exception;
    
    /**
     * 获取所有营业县区
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public List getBusiCounty() throws Exception;
    
    /**
	 * 根据营业县分编号查询营业县分信息
	 * @param regionIdList
	 * @param countyIdList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getCountyByIds(List<String> regionIdList,
			List<String> countyIdList) throws Exception;
}
