package com.ztesoft.mall.core.action.backend;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.mall.core.exception.NameRepetirException;
import com.ztesoft.net.mall.core.model.NumeroRegla;
import com.ztesoft.net.mall.core.service.INumeroReglaManager;


public class NumeroReglaAction extends BaseAction {

	private INumeroReglaManager reglaManager;
	private NumeroRegla regla;
	private List tipos;
	private List estatos;
	
	public String list() {
		tipos = reglaManager.getTipos();
		webpage = reglaManager.getFormList(getPage(), getPageSize(), params);
		return INDEX;
	}

	public String add() {
		estatos = reglaManager.getEstatos();
		if (id != null) {
			regla = reglaManager.get(id);
		}
		return INPUT;
	}

	public String save() {
		String flag = "新增";
		try {
			if (StringUtils.isNotBlank(regla.getRule_id())){
				flag = "编辑";
			}
			reglaManager.saveOrUpdate(regla);
			msg.addResult(MESSAGE, flag + "成功");
			msg.addResult("url", "regla!list.do");
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
			reglaManager.del(ids, params);
			msg.addResult(MESSAGE,"删除成功");
			msg.addResult("url", "regla!list.do");
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

	public INumeroReglaManager getReglaManager() {
		return reglaManager;
	}

	public void setReglaManager(INumeroReglaManager reglaManager) {
		this.reglaManager = reglaManager;
	}

	public NumeroRegla getRegla() {
		return regla;
	}

	public void setRegla(NumeroRegla regla) {
		this.regla = regla;
	}

	public List getEstatos() {
		return estatos;
	}

	public void setEstatos(List estatos) {
		this.estatos = estatos;
	}

	public List getTipos() {
		return tipos;
	}

	public void setTipos(List tipos) {
		this.tipos = tipos;
	}

}
