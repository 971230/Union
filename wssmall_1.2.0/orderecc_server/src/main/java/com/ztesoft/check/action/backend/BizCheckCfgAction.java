package com.ztesoft.check.action.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zte.net.ecsord.common.CommonDataFactory;

import com.opensymphony.xwork2.Action;
import com.ztesoft.check.common.factory.BizDataFatory;
import com.ztesoft.check.common.factory.FunDataFatory;
import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizFunRel;
import com.ztesoft.check.model.BizRequirements;
import com.ztesoft.check.model.Fun;
import com.ztesoft.check.service.IBizCheckCfgManager;
import com.ztesoft.net.framework.action.WWAction;

public class BizCheckCfgAction extends WWAction {

	private IBizCheckCfgManager bizCheckCfgManager;
	
	private List bizFactorCfgList;
	private List<Fun> funList;

	private List DC_IS_OR_NO = new ArrayList();
	private List DC_NEW_USER_TYPE = new ArrayList();
	private List DC_MODE_GOODS_TYPE = new ArrayList();
	private List DC_BUSINESS_TYPE = new ArrayList();
	private List DC_MODE_NET_TYPE = new ArrayList();
	private List DC_IS_OR_NO_NEED;
	
	private Biz biz;
	private ArrayList<BizRequirements> bizRequirementsList;
	private ArrayList<BizFunRel> bizFunList;
	private ArrayList<BizFunRel> beforeBizFunList;
	
	private List editBizRequirementsList;
	
	private Map<String, String> params = new HashMap<String, String>();
	
	private String id;
	
	private String oldFunStr;
	private String newFunStr;
	private Map afterFunMap;
	private Map beforeFunMap;
	private Map afterExeMethodMap;
	private Map beforeExeMethodMap;
	
	private String refresh_type;
	
	public String bizCfgList(){
		this.webpage = this.bizCheckCfgManager.qryCfgPage(params, this.getPage(), this.getPageSize());
		return "list";
	}
	
	public String bizAdd(){
		init();
		return Action.INPUT;
	}

	public String saveAdd(){
		if("".equals(biz.getBiz_name())){
			this.urls.put("业务校验配置列表", "/core/admin/ecc/bizCheckCfgAdmin!bizCfgList.do");
	   		this.msgs.add("业务名称不能为空！");
	   		
			return WWAction.MESSAGE; 
		}
		if("".equals(biz.getFlow_id())){
			this.urls.put("业务校验配置列表", "/core/admin/ecc/bizCheckCfgAdmin!bizCfgList.do");
	   		this.msgs.add("业务编码不能为空！");
			return WWAction.MESSAGE; 
		}
		this.bizCheckCfgManager.saveBizCheckCfg(biz, bizRequirementsList, bizFunList, beforeBizFunList);
		
   		this.urls.put("业务校验配置列表", "/core/admin/ecc/bizCheckCfgAdmin!bizCfgList.do");
   		this.msgs.add("保存成功");
   		
		return WWAction.MESSAGE; 
	}
	
	private void init(){
		
		this.DC_IS_OR_NO = CommonDataFactory.getInstance().listDropDownData("DC_IS_OR_NO");
		this.DC_NEW_USER_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_NEW_USER_TYPE");
		this.DC_MODE_GOODS_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_MODE_GOODS_TYPE");
		this.DC_BUSINESS_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_BUSINESS_TYPE");
		this.DC_MODE_NET_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_MODE_NET_TYPE");
		
		this.funList = this.bizCheckCfgManager.funList();
		this.setBizFactorCfgList(this.bizCheckCfgManager.bizFactorCfgList());
	}
	
	/**
	 * 查询修改信息
	 * @return
	 */
	public String editBiz(){
		init();
		List needList =new ArrayList();
		needList.addAll(this.DC_IS_OR_NO);
		biz=this.bizCheckCfgManager.qryEditBiz(id);
		
		beforeFunMap = this.bizCheckCfgManager.qryBizFunList(funList, id, "before");
		afterFunMap = this.bizCheckCfgManager.qryBizFunList(funList, id, "after");
		
		afterExeMethodMap = this.bizCheckCfgManager.qryBizExeMethod(id, "after");
		beforeExeMethodMap = this.bizCheckCfgManager.qryBizExeMethod(id, "before");
		
		editBizRequirementsList = this.bizCheckCfgManager.qryBizRequirementsList(id);
		List new_DC_IS_OR_NO = this.bizCheckCfgManager.deal_DC_DATA(editBizRequirementsList ,DC_IS_OR_NO, "wm_isreservation_from");
		List need_DC_IS_OR_NO = this.bizCheckCfgManager.deal_DC_DATA(editBizRequirementsList ,DC_IS_OR_NO, "need_open_act");
		List new_DC_NEW_USER_TYPE=this.bizCheckCfgManager.deal_DC_DATA(editBizRequirementsList ,DC_NEW_USER_TYPE, "user_flag");
		List new_DC_MODE_GOODS_TYPE=this.bizCheckCfgManager.deal_DC_DATA(editBizRequirementsList ,DC_MODE_GOODS_TYPE, "goods_type");
		List new_DC_BUSINESS_TYPE = this.bizCheckCfgManager.deal_DC_DATA(editBizRequirementsList ,DC_BUSINESS_TYPE, "busi_type");
		List new_DC_MODE_NET_TYPE = this.bizCheckCfgManager.deal_DC_DATA(editBizRequirementsList ,DC_MODE_NET_TYPE, "net_type");
		List factorCfgList=this.bizCheckCfgManager.bizFactorCfgList();
		List newFactorCfgList= this.bizCheckCfgManager.deal_newFactorCfgList(editBizRequirementsList, factorCfgList);
		
		if(new_DC_IS_OR_NO.size()>0)
		    this.DC_IS_OR_NO=new_DC_IS_OR_NO;
		
		if(need_DC_IS_OR_NO.size()>0)
		    this.DC_IS_OR_NO_NEED=need_DC_IS_OR_NO;
		else
			this.DC_IS_OR_NO_NEED=needList;
		
		if(new_DC_NEW_USER_TYPE.size()>0)
			this.DC_NEW_USER_TYPE=new_DC_NEW_USER_TYPE;
		
		if(new_DC_MODE_GOODS_TYPE.size()>0)
			this.DC_MODE_GOODS_TYPE=new_DC_MODE_GOODS_TYPE;
		
		if(new_DC_BUSINESS_TYPE.size()>0)
			this.DC_BUSINESS_TYPE=new_DC_BUSINESS_TYPE;
		
		if(new_DC_MODE_NET_TYPE.size()>0)
			this.DC_MODE_NET_TYPE=new_DC_MODE_NET_TYPE;
		
		if(newFactorCfgList.size()>0)
			this.setBizFactorCfgList(newFactorCfgList);
		return "editBiz";
	}
	
	public String refreshData(){
		try {
			if("0".equals(refresh_type)){
				BizDataFatory.getInstance().refreshData();
			}else if("1".equals(refresh_type)){
				FunDataFatory.getInstance().refreshData();
			}else{
				BizDataFatory.getInstance().refreshData();
				FunDataFatory.getInstance().refreshData();
			}
			this.json = "{result:1,message:'刷新成功'}";
		} catch (Exception e) {
			e.printStackTrace();
			this.json = "{result:0,message:'刷新失败:"+e.getMessage()+"'}";
		}

		return  WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 保存修改
	 * @return
	 */
	public String saveEditBiz(){
		if("".equals(biz.getBiz_name())){
			 this.json = "{result:1,message:'业务名称不能为空！'}";
			 return WWAction.JSON_MESSAGE;
		}
		if("".equals(biz.getFlow_id())){
			this.json = "{result:1,message:'业务编码不能为空！'}";
			 return WWAction.JSON_MESSAGE;
		}
		this.bizCheckCfgManager.saveUpdateBizCheckCfg(biz, bizRequirementsList, bizFunList, beforeBizFunList);

		
   		this.urls.put("业务校验配置列表", "/core/admin/ecc/bizCheckCfgAdmin!bizCfgList.do");
   		this.msgs.add("保存成功");
   		
		return WWAction.MESSAGE; 
	}
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public IBizCheckCfgManager getBizCheckCfgManager() {
		return bizCheckCfgManager;
	}

	public void setBizCheckCfgManager(IBizCheckCfgManager bizCheckCfgManager) {
		this.bizCheckCfgManager = bizCheckCfgManager;
	}

	public List getBizFactorCfgList() {
		return bizFactorCfgList;
	}

	public void setBizFactorCfgList(List bizFactorCfgList) {
		this.bizFactorCfgList = bizFactorCfgList;
	}

	public List getDC_IS_OR_NO() {
		return DC_IS_OR_NO;
	}

	public void setDC_IS_OR_NO(List dC_IS_OR_NO) {
		DC_IS_OR_NO = dC_IS_OR_NO;
	}

	public List getDC_NEW_USER_TYPE() {
		return DC_NEW_USER_TYPE;
	}

	public void setDC_NEW_USER_TYPE(List dC_NEW_USER_TYPE) {
		DC_NEW_USER_TYPE = dC_NEW_USER_TYPE;
	}

	public List getDC_MODE_GOODS_TYPE() {
		return DC_MODE_GOODS_TYPE;
	}

	public void setDC_MODE_GOODS_TYPE(List dC_MODE_GOODS_TYPE) {
		DC_MODE_GOODS_TYPE = dC_MODE_GOODS_TYPE;
	}

	public List getDC_BUSINESS_TYPE() {
		return DC_BUSINESS_TYPE;
	}

	public void setDC_BUSINESS_TYPE(List dC_BUSINESS_TYPE) {
		DC_BUSINESS_TYPE = dC_BUSINESS_TYPE;
	}

	public List getDC_MODE_NET_TYPE() {
		return DC_MODE_NET_TYPE;
	}

	public void setDC_MODE_NET_TYPE(List dC_MODE_NET_TYPE) {
		DC_MODE_NET_TYPE = dC_MODE_NET_TYPE;
	}

	public Biz getBiz() {
		return biz;
	}

	public void setBiz(Biz biz) {
		this.biz = biz;
	}

	public ArrayList<BizRequirements> getBizRequirementsList() {
		return bizRequirementsList;
	}

	public void setBizRequirementsList(ArrayList<BizRequirements> bizRequirementsList) {
		this.bizRequirementsList = bizRequirementsList;
	}
	
	public List<Fun> getFunList() {
		return funList;
	}

	public void setFunList(List<Fun> funList) {
		this.funList = funList;
	}

	public ArrayList<BizFunRel> getBizFunList() {
		return bizFunList;
	}

	public void setBizFunList(ArrayList<BizFunRel> bizFunList) {
		this.bizFunList = bizFunList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOldFunStr() {
		return oldFunStr;
	}

	public void setOldFunStr(String oldFunStr) {
		this.oldFunStr = oldFunStr;
	}

	public String getNewFunStr() {
		return newFunStr;
	}

	public void setNewFunStr(String newFunStr) {
		this.newFunStr = newFunStr;
	}


	public List getEditBizRequirementsList() {
		return editBizRequirementsList;
	}

	public void setEditBizRequirementsList(List editBizRequirementsList) {
		this.editBizRequirementsList = editBizRequirementsList;
	}

	public ArrayList<BizFunRel> getBeforeBizFunList() {
		return beforeBizFunList;
	}

	public void setBeforeBizFunList(ArrayList<BizFunRel> beforeBizFunList) {
		this.beforeBizFunList = beforeBizFunList;
	}

	public List getDC_IS_OR_NO_NEED() {
		return DC_IS_OR_NO_NEED;
	}

	public void setDC_IS_OR_NO_NEED(List dC_IS_OR_NO_NEED) {
		DC_IS_OR_NO_NEED = dC_IS_OR_NO_NEED;
	}

	public String getRefresh_type() {
		return refresh_type;
	}

	public void setRefresh_type(String refresh_type) {
		this.refresh_type = refresh_type;
	}

	public Map getAfterFunMap() {
		return afterFunMap;
	}

	public void setAfterFunMap(Map afterFunMap) {
		this.afterFunMap = afterFunMap;
	}

	public Map getBeforeFunMap() {
		return beforeFunMap;
	}

	public void setBeforeFunMap(Map beforeFunMap) {
		this.beforeFunMap = beforeFunMap;
	}

	public Map getAfterExeMethodMap() {
		return afterExeMethodMap;
	}

	public void setAfterExeMethodMap(Map afterExeMethodMap) {
		this.afterExeMethodMap = afterExeMethodMap;
	}

	public Map getBeforeExeMethodMap() {
		return beforeExeMethodMap;
	}

	public void setBeforeExeMethodMap(Map beforeExeMethodMap) {
		this.beforeExeMethodMap = beforeExeMethodMap;
	}
	
	
}
