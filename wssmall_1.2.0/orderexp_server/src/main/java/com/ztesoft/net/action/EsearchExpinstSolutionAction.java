package com.ztesoft.net.action;

import java.util.List;
import org.drools.core.util.StringUtils;

import zte.net.ecsord.common.CommonDataFactory;
import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.EsearchExpInstSolution;
import com.ztesoft.net.param.inner.EsearchExpInstSolutionInner;
import com.ztesoft.net.param.outer.EsearchExpInstSolutionOuter;
import com.ztesoft.net.service.IExpConfigManager;

public class EsearchExpinstSolutionAction extends WWAction {

	private EsearchExpInstSolutionInner solutionInner;
	
	private IExpConfigManager expConfigManager;
	
	private String catalog_id;
	private String solution_id;//解决方案IDc
	private String solution_type;//解决方案类型
	private String solution_value;//解决方案名称
	private String solution_name;//解决方案名称
	private List<EsearchExpInstSolution> catalogSolutionList;//归类解决方案
	private List solutionTypeList;//解决方案类型静态数据列表
	
	
	public String getCatalogSolution(){
		if(StringUtils.isEmpty(solution_id)){
			//catalogSolutionList = new ArrayList<EsearchExpInstSolution>();
			logger.info("solution_id is null");
			return "catalog_solution";
		}
		EsearchExpInstSolutionInner inner = new EsearchExpInstSolutionInner();
		inner.setSolution_id(solution_id);
		inner.setPageIndex(this.getPage());
		inner.setPageSize(this.getPageSize());
		EsearchExpInstSolutionOuter outer = expConfigManager.queryEsearchExpInstSolution(inner);
		catalogSolutionList = outer.getPage().getResult();
		if(null != catalogSolutionList && catalogSolutionList.size()>0)
			logger.info(JSON.toJSON(catalogSolutionList.get(0)));
		return "catalog_solution";
	}
	
	public String qryExpInstSolutionSelect(){
		solutionTypeList = CommonDataFactory.getInstance().listDcPublicData("solution_type");
		if(solutionInner==null){
			solutionInner = new EsearchExpInstSolutionInner();
			solutionInner.setSolution_type(solution_type);
			solutionInner.setSolution_value(solution_value);
			solutionInner.setSolution_name(solution_name);
		}
		this.webpage = expConfigManager.queryEsearchExpInstSolutionPage(solutionInner, this.getPage(), this.getPageSize());
		return "solution_select";
	}

	public EsearchExpInstSolutionInner getSolutionInner() {
		return solutionInner;
	}

	public void setSolutionInner(EsearchExpInstSolutionInner solutionInner) {
		this.solutionInner = solutionInner;
	}

	public IExpConfigManager getExpConfigManager() {
		return expConfigManager;
	}

	public void setExpConfigManager(IExpConfigManager expConfigManager) {
		this.expConfigManager = expConfigManager;
	}

	public String getCatalog_id() {
		return catalog_id;
	}

	public void setCatalog_id(String catalog_id) {
		this.catalog_id = catalog_id;
	}

	public String getSolution_id() {
		return solution_id;
	}

	public void setSolution_id(String solution_id) {
		this.solution_id = solution_id;
	}

	public String getSolution_type() {
		return solution_type;
	}

	public void setSolution_type(String solution_type) {
		this.solution_type = solution_type;
	}

	public String getSolution_value() {
		return solution_value;
	}

	public void setSolution_value(String solution_value) {
		this.solution_value = solution_value;
	}

	public String getSolution_name() {
		return solution_name;
	}

	public void setSolution_name(String solution_name) {
		this.solution_name = solution_name;
	}

	public List<EsearchExpInstSolution> getCatalogSolutionList() {
		return catalogSolutionList;
	}

	public void setCatalogSolutionList(
			List<EsearchExpInstSolution> catalogSolutionList) {
		this.catalogSolutionList = catalogSolutionList;
	}

	public List getSolutionTypeList() {
		return solutionTypeList;
	}

	public void setSolutionTypeList(List solutionTypeList) {
		this.solutionTypeList = solutionTypeList;
	}
}
