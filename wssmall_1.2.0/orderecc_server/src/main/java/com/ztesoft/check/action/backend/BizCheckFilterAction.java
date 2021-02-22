package com.ztesoft.check.action.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zte.net.ecsord.common.CommonDataFactory;

import com.ztesoft.check.common.Consts;
import com.ztesoft.check.model.Biz;
import com.ztesoft.check.model.BizCheckFilter;
import com.ztesoft.check.model.BizRequirements;
import com.ztesoft.check.service.impl.BizCheckFilterManager;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.database.Page;

public class BizCheckFilterAction extends WWAction {

	@Resource
	private BizCheckFilterManager bizCheckFilterManager;
	private Map<String, String> params = new HashMap<String, String>(0);	//页面传入参数
	private Map<String, String> filterView = new HashMap<String, String>(0);	//输出参数
	
	private List DC_IS_OR_NO;
	private List DC_NEW_USER_TYPE;
	private List DC_MODE_GOODS_TYPE;
	private List DC_BUSINESS_TYPE;
	private List DC_MODE_NET_TYPE;
	private List DC_IS_OR_NO_NEED;
	

	private List DC_MODE_SHIP_TYPE;
	private List DIC_ORDER_ORIGIN;
	
	private Biz biz;
	private List bizFactorCfgList;
	
	private ArrayList<BizRequirements> bizRequirementsList;
	
	public Map<String, String> getFilterView() {
		return filterView;
	}

	public void setFilterView(Map<String, String> filterView) {
		this.filterView = filterView;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	private void init(){
		this.DC_IS_OR_NO = CommonDataFactory.getInstance().listDropDownData("DC_IS_OR_NO");
		this.DC_NEW_USER_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_NEW_USER_TYPE");
		this.DC_MODE_GOODS_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_MODE_GOODS_TYPE");
		this.DC_BUSINESS_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_BUSINESS_TYPE");
		this.DC_MODE_NET_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_MODE_NET_TYPE");
		this.DC_MODE_SHIP_TYPE = CommonDataFactory.getInstance().listDropDownData("DC_MODE_SHIP_TYPE");
		this.DIC_ORDER_ORIGIN = CommonDataFactory.getInstance().listDropDownData("DIC_ORDER_ORIGIN");
		this.setBizFactorCfgList(this.bizCheckFilterManager.bizFactorCfgList());
	}
	
	public String toAdd(){
		init();
		return "auto_filter_add";
	}
	
	public String showCheckFilterList(){
		String type = "show";
		try{
			List<BizCheckFilter> filters = bizCheckFilterManager.queryFilterListByParam(params);
			Page page = new Page();
			page.setData(filters);
			page.setTotalCount(filters.size());
			this.webpage = page;
			if(null != params && params.size() > 0 && params.containsKey("type") 
					&& "qryEdit".equals(params.get("type"))){
				if(null != filters && filters.size() > 0){
					this.filterView = bizCheckFilterManager.parseBean2Map(filters.get(0));
				}
				type = "edit";
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
		if("edit".equals(type)){
			return "auto_filter_edit";
		}else{
			return "auto_filter_list";
		}
	}
	
	/**
	 * 新增单个过滤因子数据
	 * @return
	 */
	public String checkFilterAdd(){
		try{
			biz.setBiz_type(Consts.BIZ_TYPE_001);
			bizCheckFilterManager.saveCheckFilter(biz, bizRequirementsList);
		}catch(Exception e ){
			e.printStackTrace();
		}
		//查所有返回
		params = null;
		return showCheckFilterList();
	}
	
	/**
	 * 修改单个过滤因子数据
	 * @return
	 */
	public String checkFilterEdit(){
		try{
			BizCheckFilter filterDto = bizCheckFilterManager.parseMap2Bean(params);
			bizCheckFilterManager.modifyFilterItem(filterDto);
		}catch(Exception e ){
			e.printStackTrace();
		}
		//查所有返回
		params = null;
		return showCheckFilterList();
	}

	public BizCheckFilterManager getBizCheckFilterManager() {
		return bizCheckFilterManager;
	}

	public void setBizCheckFilterManager(BizCheckFilterManager bizCheckFilterManager) {
		this.bizCheckFilterManager = bizCheckFilterManager;
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

	public List getDC_IS_OR_NO_NEED() {
		return DC_IS_OR_NO_NEED;
	}

	public void setDC_IS_OR_NO_NEED(List dC_IS_OR_NO_NEED) {
		DC_IS_OR_NO_NEED = dC_IS_OR_NO_NEED;
	}

	public ArrayList<BizRequirements> getBizRequirementsList() {
		return bizRequirementsList;
	}

	public void setBizRequirementsList(
			ArrayList<BizRequirements> bizRequirementsList) {
		this.bizRequirementsList = bizRequirementsList;
	}

	public List getBizFactorCfgList() {
		return bizFactorCfgList;
	}

	public void setBizFactorCfgList(List bizFactorCfgList) {
		this.bizFactorCfgList = bizFactorCfgList;
	}

	public Biz getBiz() {
		return biz;
	}

	public void setBiz(Biz biz) {
		this.biz = biz;
	}

	public List getDC_MODE_SHIP_TYPE() {
		return DC_MODE_SHIP_TYPE;
	}

	public void setDC_MODE_SHIP_TYPE(List dC_MODE_SHIP_TYPE) {
		DC_MODE_SHIP_TYPE = dC_MODE_SHIP_TYPE;
	}

	public List getDIC_ORDER_ORIGIN() {
		return DIC_ORDER_ORIGIN;
	}

	public void setDIC_ORDER_ORIGIN(List dIC_ORDER_ORIGIN) {
		DIC_ORDER_ORIGIN = dIC_ORDER_ORIGIN;
	}
	
	
}
