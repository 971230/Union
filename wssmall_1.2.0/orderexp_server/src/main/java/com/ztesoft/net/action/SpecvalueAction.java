package com.ztesoft.net.action;

import java.util.List;

import model.EsearchSpecvalues;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.EsearchCatalog;
import com.ztesoft.net.param.inner.ChangeSpecvalueUpdateInner;
import com.ztesoft.net.param.inner.EsearchCatalogInner;
import com.ztesoft.net.param.inner.SpecvalueInner;
import com.ztesoft.net.param.inner.SpecvaluesClassifyInner;
import com.ztesoft.net.param.inner.UnCheckSpecvalueInner;
import com.ztesoft.net.service.IEsearchSpecvaluesManager;
import com.ztesoft.net.service.IExpConfigManager;

public class SpecvalueAction extends WWAction{
	private IEsearchSpecvaluesManager esearchSpecvaluesManager;
	private IExpConfigManager expConfigManager;
	private SpecvalueInner inner;
	private List<EsearchCatalog> catalogList;
	private EsearchCatalog catalog;
	private EsearchSpecvalues esearchSpecvalues;
	
	public String list(){
		try {
			if(null == inner){
				inner = new SpecvalueInner();
			}
			inner.setPageIndex(this.getPage());
			inner.setPageSize(this.getPageSize());
			webpage = this.esearchSpecvaluesManager.querySpecvaluesPage(inner);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}
	
	public String catalogList(){
		try {
			EsearchCatalogInner eci = new EsearchCatalogInner();
			catalogList = this.expConfigManager.queryEsearchCatalog(eci);
			eci.setKey_id(inner.getKey_id());
			List<EsearchCatalog> catalogs = this.expConfigManager.queryEsearchCatalogBySpecv(eci).getList();
			/*getRequest().setAttribute("catalogList", catalogList);
			if(null != catalog && catalog.size() >0){
				getRequest().setAttribute("catalog", catalog.get(0));
			}else{
				getRequest().setAttribute("catalog", null);
			}
			getRequest().setAttribute("catalog", catalog);*/
			if(null != catalogs && catalogs.size() > 0){
				catalog = catalogs.get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "specvaluecatalogList";
	}
	
	public String specvaluesClassify(){
		try {
			//调用关键字归类API
			SpecvaluesClassifyInner sci = new SpecvaluesClassifyInner();
			sci.setKey_id(inner.getKey_id());
			sci.setCatalog_id(inner.getCatalog_id());
			this.esearchSpecvaluesManager.specvaluesClassify(sci);
			
			this.json = "{'result':0, 'message':'归类成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'"+e.getMessage()+"'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	public String changeSpecvalueUpdate(){
		
		try {
			
			//调用关键字修改更新API
			ChangeSpecvalueUpdateInner csui = new ChangeSpecvalueUpdateInner();
			csui.setKey_id(inner.getKey_id());
			csui.setSearch_id(inner.getSearch_id());
			this.esearchSpecvaluesManager.changeSpecvalueUpdateExpInst(csui);
			
			this.json = "{'result':0, 'message':'操作完成！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'"+e.getMessage()+"'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}
	
	public String unCheckSpecvalueUpdate(){
		try {
			//未匹配关键字异常更新API
			UnCheckSpecvalueInner ucsi = new UnCheckSpecvalueInner();
			ucsi.setSearch_id(inner.getSearch_id());
			ucsi.setKey_id(inner.getKey_id());
			ucsi.setSearch_code(inner.getSearch_code());
			//ucsi.setRequest(super.getRequest());
			this.esearchSpecvaluesManager.unCheckSpecvalueUpdate(ucsi);
			
			this.json = "{'result':0, 'message':'更新成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'"+e.getMessage()+"'}";
			e.printStackTrace();
		}
		super.getRequest().getSession().setAttribute("ORDEREXP_PROGRESS", new Integer(100));//移除session中的进度
		return JSON_MESSAGE;
	}
	
	//获取未匹配异常更新进度
	public String unCheckSpecvalueUpdateProgress(){
		Integer progress = new Integer(0);
		try {
			progress = (Integer)super.getRequest().getSession().getAttribute("ORDEREXP_PROGRESS");
			if(null == progress){
				progress = new Integer(0);
			}
		} catch (Exception e) {
		}
		if(100 == progress.intValue()){
			super.getRequest().getSession().removeAttribute("ORDEREXP_PROGRESS");
		}
		this.json = "{'result':0, 'message':'成功！','percent':'"+progress.intValue()+"'}";
		return JSON_MESSAGE;
	}
	
	public String toUpdate(){
		//根据关键字id查询关键字信息
		esearchSpecvalues = this.esearchSpecvaluesManager.findSpecvalues(inner);
		
		return "update";
	}
	
	public String update(){
		try {
			this.esearchSpecvaluesManager.updateSpecvalues(esearchSpecvalues);
			this.json = "{'result':0, 'message':'更新成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'更新失败！'}";
			e.printStackTrace();
		}
		return JSON_MESSAGE;
	}

	public IEsearchSpecvaluesManager getEsearchSpecvaluesManager() {
		return esearchSpecvaluesManager;
	}

	public void setEsearchSpecvaluesManager(
			IEsearchSpecvaluesManager esearchSpecvaluesManager) {
		this.esearchSpecvaluesManager = esearchSpecvaluesManager;
	}

	public SpecvalueInner getInner() {
		return inner;
	}

	public void setInner(SpecvalueInner inner) {
		this.inner = inner;
	}

	public IExpConfigManager getExpConfigManager() {
		return expConfigManager;
	}

	public void setExpConfigManager(IExpConfigManager expConfigManager) {
		this.expConfigManager = expConfigManager;
	}

	public List<EsearchCatalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(List<EsearchCatalog> catalogList) {
		this.catalogList = catalogList;
	}

	public EsearchCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(EsearchCatalog catalog) {
		this.catalog = catalog;
	}

	public EsearchSpecvalues getEsearchSpecvalues() {
		return esearchSpecvalues;
	}

	public void setEsearchSpecvalues(EsearchSpecvalues esearchSpecvalues) {
		this.esearchSpecvalues = esearchSpecvalues;
	}
}
