package com.ztesoft.mall.core.action.backend;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.mall.core.exception.NameRepetirException;
import com.ztesoft.net.mall.core.model.NumeroGrupo;
import com.ztesoft.net.mall.core.service.INumeroGrupoManager;

public class NumeroGrupoAction extends BaseAction {

	private INumeroGrupoManager ngManager;
	private NumeroGrupo ng;
	private List estatos;

	public String list() {
		webpage = ngManager.getFormList(getPage(), getPageSize(), params);
		
		if(StringUtils.isNotBlank(params.get("flag"))){
			return "list";
		}
		
		return INDEX;
	}

	public String add() {
		estatos = ngManager.getEstatos();
		if (id != null) {
			ng = ngManager.get(id);
		}
		return INPUT;
	}

	public String save() {
		String flag = "新增";
		try {
			if (StringUtils.isNotBlank(ng.getGroup_id())){
				flag = "编辑";
			}
			ngManager.saveOrUpdate(ng);
			msg.addResult(MESSAGE, flag + "成功");
			msg.addResult("url", "grupo!list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (NameRepetirException e) {
			msg.addResult(MESSAGE, e.getMessage());
			msg.setSuccess(false);
			msg.setResult(1);
		} catch (Exception e) {
			e.printStackTrace();
			msg.addResult(MESSAGE, flag + "失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}

	public String del() {
		try {
			ngManager.del(ids, params);
			msg.addResult(MESSAGE,"删除成功");
			msg.addResult("url", "grupo!list.do");
			msg.setSuccess(true);
			msg.setResult(0);
		} catch (Exception e) {
			msg.addResult(MESSAGE,"删除失败");
			msg.setSuccess(false);
			msg.setResult(1);
		}
		json = JSON.toJSONString(msg).toString();
		return JSON_MESSAGE;
	}

	public NumeroGrupo getNg() {
		return ng;
	}

	public void setNg(NumeroGrupo ng) {
		this.ng = ng;
	}

	public INumeroGrupoManager getNgManager() {
		return ngManager;
	}

	public void setNgManager(INumeroGrupoManager ngManager) {
		this.ngManager = ngManager;
	}

	public List<String> getEstatos() {
		return estatos;
	}

	public void setEstatos(List<String> estatos) {
		this.estatos = estatos;
	}
	
}
