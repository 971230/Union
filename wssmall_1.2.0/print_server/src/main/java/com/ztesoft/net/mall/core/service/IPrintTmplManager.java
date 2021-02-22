package com.ztesoft.net.mall.core.service;

import java.util.List;

import com.ztesoft.net.mall.core.model.PrintTmpl;

public interface IPrintTmplManager {
	
	public List list();
	
	public List trash();
	
	/**
	 * 启用模板列表
	 * @return
	 */
	public List listCanUse();
	
	public void add(PrintTmpl printTmpl);
	
	public void edit(PrintTmpl printTmpl);
	
	public PrintTmpl get(String prt_tmpl_id);
	
	public void delete(String[] id);
	
	public void revert(String[] id);
	
	public void clean(String[] id);

}
