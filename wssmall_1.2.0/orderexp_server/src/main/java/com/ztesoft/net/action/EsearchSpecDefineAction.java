package com.ztesoft.net.action;

import model.EsearchSpec;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.param.inner.SpecDefineQueryInner;
import com.ztesoft.net.service.IEsearchSpecDefineManager;

/**
 * 规格定义管理
 * @author qin.yingxiong
 */
public class EsearchSpecDefineAction extends WWAction {
	private static final long serialVersionUID = 1L;
	private SpecDefineQueryInner sdqInner;
	private IEsearchSpecDefineManager esearchSpecDefineManager;
	private String show_type;
	
	public String list() {
		this.webpage = esearchSpecDefineManager.getSpecDefinePage(sdqInner, this.getPage(),this.getPageSize());
		if(show_type != null && show_type.equals("dialog")) {
			return "dialog";
		}else {
			return "index";
		}
	}
	
	public String toAdd(){
		
		return "add";
		
	}
	
	public String add(){
		try {
			this.esearchSpecDefineManager.addSpecDefine(sdqInner);
			super.showSuccessJson("关键字规格添加成功！");
		} catch (Exception e) {
			super.showErrorJson("关键字规格添加失败！");
			e.printStackTrace();
		}
		return super.JSON_MESSAGE;
	}
	
	public String toUpdate(){
		EsearchSpec esearchSpec = this.esearchSpecDefineManager.findEsearchSpec(sdqInner);
		super.getRequest().setAttribute("esearchSpec", esearchSpec);
		return "update";
				
	}
	
	public String update(){
		try {
			this.esearchSpecDefineManager.updateEsearchSpec(sdqInner);
			super.showSuccessJson("关键字规格修改成功！");
		} catch (Exception e) {
			super.showErrorJson("关键字规格修改失败！");
			e.printStackTrace();
		}
		return super.JSON_MESSAGE;
	}

	public SpecDefineQueryInner getSdqInner() {
		return sdqInner;
	}

	public void setSdqInner(SpecDefineQueryInner sdqInner) {
		this.sdqInner = sdqInner;
	}

	public IEsearchSpecDefineManager getEsearchSpecDefineManager() {
		return esearchSpecDefineManager;
	}

	public void setEsearchSpecDefineManager(
			IEsearchSpecDefineManager esearchSpecDefineManager) {
		this.esearchSpecDefineManager = esearchSpecDefineManager;
	}

	public String getShow_type() {
		return show_type;
	}

	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
}