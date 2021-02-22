package com.ztesoft.net.cms.core.action;

import com.ztesoft.net.cms.core.model.DataCat;
import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.cms.core.service.IDataCatManager;
import com.ztesoft.net.cms.core.service.IDataFieldManager;
import com.ztesoft.net.cms.core.service.IDataManager;
import com.ztesoft.net.framework.action.WWAction;

import java.util.List;

/**
 * 数据选择器
 * @author kingapex
 *
 */
public class DataSelectorAction extends WWAction {
	
	private IDataManager dataManager;
	private IDataCatManager dataCatManager;
	private List<DataCat> catList;
	private List<DataField> fieldList;
	private IDataFieldManager dataFieldManager;
	private String catid;
	public String showDialog(){
		catList =dataCatManager.listAllChildren("0");
		return "dialog";
	}

	public String list(){
		this.fieldList = dataFieldManager.listByCatId(catid);
		this.webpage =dataManager.list(catid, this.getPage(), 15);
		return "list";
	}

	public IDataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	public IDataCatManager getDataCatManager() {
		return dataCatManager;
	}

	public void setDataCatManager(IDataCatManager dataCatManager) {
		this.dataCatManager = dataCatManager;
	}

	public List<DataCat> getCatList() {
		return catList;
	}

	public void setCatList(List<DataCat> catList) {
		this.catList = catList;
	}

	public String getCatid() {
		return catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	public IDataFieldManager getDataFieldManager() {
		return dataFieldManager;
	}

	public void setDataFieldManager(IDataFieldManager dataFieldManager) {
		this.dataFieldManager = dataFieldManager;
	}

	public List<DataField> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<DataField> fieldList) {
		this.fieldList = fieldList;
	}

	
}
