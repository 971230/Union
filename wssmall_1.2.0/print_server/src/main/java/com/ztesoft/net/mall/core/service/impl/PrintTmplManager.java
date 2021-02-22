package com.ztesoft.net.mall.core.service.impl;

import java.util.List;

import com.ztesoft.net.eop.sdk.database.BaseSupport;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.model.PrintTmpl;
import com.ztesoft.net.mall.core.service.IPrintTmplManager;

public class PrintTmplManager extends BaseSupport<PrintTmpl> implements
		IPrintTmplManager {

	
	@Override
	public void add(PrintTmpl printTmpl) {
		this.baseDaoSupport.insert("print_tmpl", printTmpl);

	}

	
	@Override
	public void clean(String[] id) {
		if(id== null  || id.length==0  ) return ;
		String ids = StringUtil.arrayToString(id, ",");
		this.baseDaoSupport.execute("delete from print_tmpl where prt_tmpl_id in (" + ids + ")");

	}

	
	@Override
	public void delete(String[] id) {
		if(id== null  || id.length==0  ) return ;
		String ids = StringUtil.arrayToString(id, ",");
		this.baseDaoSupport.execute("update print_tmpl set disabled = 'true' where prt_tmpl_id in (" + ids + ")");

	}

	
	@Override
	public void edit(PrintTmpl printTmpl) {
		this.baseDaoSupport.update("print_tmpl", printTmpl, "prt_tmpl_id = " + printTmpl.getPrt_tmpl_id());
	}

	
	@Override
	public List list() {
		return this.baseDaoSupport.queryForList("select * from print_tmpl where disabled = 'false'", PrintTmpl.class);
	}

	
	@Override
	public void revert(String[] id) {
		if(id== null  || id.length==0  ) return ;
		String ids = StringUtil.arrayToString(id, ",");
		this.baseDaoSupport.execute("update print_tmpl set disabled = 'false' where prt_tmpl_id in (" + ids + ")");

	}

	
	@Override
	public List trash() {
		return this.baseDaoSupport.queryForList("select * from print_tmpl where disabled = 'true'", PrintTmpl.class);
	}

	
	@Override
	public PrintTmpl get(String prt_tmpl_id) {
		return this.baseDaoSupport.queryForObject("select * from es_print_tmpl where prt_tmpl_id = ?", PrintTmpl.class, prt_tmpl_id);
	}

	
	@Override
	public List listCanUse() {
		return this.baseDaoSupport.queryForList("select * from print_tmpl where disabled = 'false' and shortcut = 'true'", PrintTmpl.class);
	}

}
