package com.ztesoft.check.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFunRel;
import com.ztesoft.check.model.BizRequirements;
import com.ztesoft.net.framework.database.Page;

public interface IBizCheckCfgManager {

	public Page qryCfgPage(Map param, int page, int pageSize);

	public List bizFactorCfgList();
	
	public List funList();
	
	public void saveBizCheckCfg(Biz biz, ArrayList<BizRequirements> list, ArrayList<BizFunRel> bizFunList, ArrayList<BizFunRel> beforeBizFunList);
	
	public Biz qryEditBiz(String id);
	
	public Map qryBizFunList(List funList, String id, String exe_time);
	
	public Map qryBizExeMethod(String id, String exe_time);
	
	public List qryBizRequirementsList(String id);
	
	public void saveUpdateBizCheckCfg(Biz biz, ArrayList<BizRequirements> list, ArrayList<BizFunRel> bizFunList, ArrayList<BizFunRel> beforeBizFunList);
	
	public List deal_DC_DATA(List editBizRequirementsList ,List DC_IS_OR_NO, String string);
	
	public List deal_newFactorCfgList(List editBizRequirementsList, List factorCfgList);
}
