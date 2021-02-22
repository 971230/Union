package com.ztesoft.net.param.outer;

import java.io.Serializable;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.model.EsearchSpecvaluesRulesQuery;

public class KeyRuleOuter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Page page;//关键字规则分页对象
	
	EsearchSpecvaluesRulesQuery esrq;//关键字规则对象

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public EsearchSpecvaluesRulesQuery getEsrq() {
		return esrq;
	}

	public void setEsrq(EsearchSpecvaluesRulesQuery esrq) {
		this.esrq = esrq;
	}

	
}