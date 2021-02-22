package services;

import params.orgAdmin.req.AddOrgReq;
import params.orgAdmin.req.ModOrgReq;
import params.orgAdmin.req.OrgAdminListReq;
import params.orgAdmin.req.OrgAdminReq;
import params.orgAdmin.resp.AddOrgResp;
import params.orgAdmin.resp.ModOrgResp;
import params.orgAdmin.resp.OrgAdminListResp;
import params.orgAdmin.resp.OrgAdminResp;
import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;


public interface OrgAdminInf {
	
	//当前登录人员所在组织
	public OrgAdminResp getCurrOrg(OrgAdminReq orgAdminReq) throws Exception;
	//当前登录人员下级组织
	public OrgAdminResp getChildrenOrg(OrgAdminReq orgAdminReq) throws Exception;
	//qryChildrenOrgCount
	public OrgAdminResp qryChildrenOrgCount(OrgAdminReq orgAdminReq);
	
	//得到管理员对应组织
	public OrgAdminResp getRootOrg() throws Exception;
	
	public AddOrgResp addOrg(AddOrgReq addOrgReq);
	
	public ModOrgResp modOrg(ModOrgReq modOrgReq);
	
	public OrgAdminResp delOrg(OrgAdminReq orgAdminReq);
	
	public OrgAdminListResp list(OrgAdminListReq orgAdminListReq) throws Exception;
	
	//当前org_code是否存在
	public OrgAdminResp ifExistsOrg(OrgAdminReq orgAdminReq);
	
	/**
     * 获取行政县分、营业县分映射
     * @return
     * @throws Exception
     */
    public ES_DC_PUBLIC_DICT_RELATION getCountyMap(String field_value) throws Exception;
}
