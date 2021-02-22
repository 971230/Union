package services;

import java.util.List;

import javax.annotation.Resource;

import params.orgAdmin.req.AddOrgReq;
import params.orgAdmin.req.ModOrgReq;
import params.orgAdmin.req.OrgAdminListReq;
import params.orgAdmin.req.OrgAdminReq;
import params.orgAdmin.resp.AddOrgResp;
import params.orgAdmin.resp.ModOrgResp;
import params.orgAdmin.resp.OrgAdminListResp;
import params.orgAdmin.resp.OrgAdminResp;
import zte.net.ecsord.params.nd.vo.ES_DC_PUBLIC_DICT_RELATION;

import com.ztesoft.net.app.base.core.service.IOrgAdminManager;
import com.ztesoft.net.framework.database.Page;

public class OrgAdminServ extends ServiceBase implements OrgAdminInf{
	
	@Resource
	private IOrgAdminManager orgAdminManager;
	
	@Override
	public OrgAdminResp getCurrOrg(OrgAdminReq orgAdminReq) throws Exception {
		OrgAdminResp orgAdminResp = new OrgAdminResp();
		
		List org_list = orgAdminManager.getCurrOrg(orgAdminReq.getLogin_org_id());
		orgAdminResp.setCurrOrgList(org_list);
		return orgAdminResp;
	}
	
	
	@Override
	public OrgAdminResp getChildrenOrg(OrgAdminReq orgAdminReq) throws Exception{
		OrgAdminResp orgAdminResp = new OrgAdminResp();
		
		List children_list = orgAdminManager.getChildrenOrg(orgAdminReq.getOrg_id());
		orgAdminResp.setChildrenOrgList(children_list);
		return orgAdminResp;
	}
	
	@Override
	public OrgAdminResp getRootOrg() throws Exception {
		OrgAdminResp orgAdminResp = new OrgAdminResp();
		
		List org_list = orgAdminManager.getRootOrg();
		orgAdminResp.setRootOrgList(org_list);
		return orgAdminResp;
	}

	@Override
	public OrgAdminResp qryChildrenOrgCount(OrgAdminReq orgAdminReq) {
		OrgAdminResp orgAdminResp = new OrgAdminResp();
		
		int cnt = orgAdminManager.qryChildrenOrgCount(orgAdminReq.getOrg_id());
		orgAdminResp.setChildrenOrgCount(cnt);
		return orgAdminResp;
	}

	@Override
	public AddOrgResp addOrg(AddOrgReq addOrgReq) {
		
		AddOrgResp addOrgResp = new AddOrgResp();
		int addOrgNum = orgAdminManager.addOrg(addOrgReq.getParent_party_id(), addOrgReq.getOrg_code(), 
				addOrgReq.getOrg_name(), addOrgReq.getOrg_type_id(), addOrgReq.getUnion_org_code(), addOrgReq.getOrg_content(),addOrgReq.getOrg_level(),addOrgReq.getLan_id(),addOrgReq.getRegion_id(),addOrgReq.getChannel_type());
		addOrgResp.setAddOrg(addOrgNum);
		return addOrgResp;
	}
	
	@Override
	public ModOrgResp modOrg(ModOrgReq modOrgReq){
		
		ModOrgResp modOrgResp = new ModOrgResp();
		int modOrgNum = orgAdminManager.modOrg(modOrgReq.getParty_id(), modOrgReq.getOrg_code(), 
				modOrgReq.getOrg_name(), modOrgReq.getOrg_type_id(), modOrgReq.getUnion_org_code(), modOrgReq.getOrg_content(),modOrgReq.getChannel_type());
		modOrgResp.setModOrg(modOrgNum);
		return modOrgResp;
	}

	@Override
	public OrgAdminResp delOrg(OrgAdminReq orgAdminReq) {
		OrgAdminResp orgAdminResp = new OrgAdminResp();
		int delOrgNum = orgAdminManager.delOrg(orgAdminReq.getParty_id());
		orgAdminResp.setDelOrgNum(delOrgNum);
		return orgAdminResp;
	}


	@Override
	public OrgAdminListResp list(OrgAdminListReq orgAdminListReq) throws Exception {
		OrgAdminListResp orgAdminListResp = new OrgAdminListResp();
		Page page = orgAdminManager.list(orgAdminListReq);
	    orgAdminListResp.setPage(page);
		return orgAdminListResp;
	}


	@Override
	public OrgAdminResp ifExistsOrg(OrgAdminReq orgAdminReq) {
		OrgAdminResp orgAdminResp = new OrgAdminResp();
		orgAdminManager.ifExistsOrg(orgAdminReq.getOrg_code());
		orgAdminResp.setIfExistsOrg(false);
		return orgAdminResp;
	}


	@Override
	public ES_DC_PUBLIC_DICT_RELATION getCountyMap(String field_value)
			throws Exception {
		return this.orgAdminManager.getCountyMap(field_value);
	}
}