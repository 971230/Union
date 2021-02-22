package com.ztesoft.mall.core.action.backend;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.exception.NameRepetirException;
import com.ztesoft.net.mall.core.model.Ciudad;
import com.ztesoft.net.mall.core.service.ICiudadManager;


public class CiudadAction extends BaseAction {
	
	private ICiudadManager cManager;
	private String uploadFileFileName;
	private Ciudad ciudad;
	private File uploadFile;

	
	public String list(){
		webpage = cManager.getFormList(getPage(), getPageSize(),params);
		return INDEX;
	}
	

	public String add() {
		if (id != null) {
			ciudad = cManager.get(id);
		}
		return INPUT;
	}

	public String save() {
		
		String flag = "新增";
		if (StringUtils.isNotBlank(ciudad.getSeg_id())) {
			flag = "编辑";
		}
		
		try {
			
			cManager.saveOrUpdate(ciudad);
			
			this.msgs.add(flag + "成功！");
			this.urls.put("号段列表", "ciudad!list.do");
			
		} catch (NameRepetirException e) {
			
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("非法操作！");
			return WWAction.MESSAGE;
		}
		
		return WWAction.MESSAGE;
	}

	public String del() {
		try {
			cManager.del(id);
			this.json = "{'result':0, 'message':'删除成功！'}";
		} catch (Exception e) {
			this.json = "{'result':1, 'message':'删除失败！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String importacion(){
		try {
			cManager.importacion(uploadFile,"ciudad");
			msgs.add("地市导入成功");
			urls.put("地市列表", "ciudad!list.do");
		} catch (Exception e) {
			e.printStackTrace();
			msgs.add("地市导入失败");
			urls.put("地市列表", "ciudad!list.do");
		}
		return MESSAGE;
	}


	public ICiudadManager getcManager() {
		return cManager;
	}


	public void setcManager(ICiudadManager cManager) {
		this.cManager = cManager;
	}


	public Ciudad getCiudad() {
		return ciudad;
	}


	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}


	public File getUploadFile() {
		return uploadFile;
	}


	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}


	public String getUploadFileFileName() {
		return uploadFileFileName;
	}


	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}


}