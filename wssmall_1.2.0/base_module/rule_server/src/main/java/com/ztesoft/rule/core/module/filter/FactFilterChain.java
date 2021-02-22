package com.ztesoft.rule.core.module.filter;

import java.util.ArrayList;
import java.util.List;

import com.ztesoft.rule.core.module.fact.DefFact;


public class FactFilterChain implements IFactFilter {

	private List<IFactFilter> filters = new ArrayList<IFactFilter>();

	private int index = 0;

	public void add(IFactFilter filter) {
		this.filters.add(filter);
	}

	public List<IFactFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<IFactFilter> filters) {
		this.filters = filters;
	}

	@Override
	public void doFilter(DefFact fact, FactFilterChain chain) {

		if (index == filters.size())
			return;
		
		//打断流程?
		if(!fact.isValidFlag())
			return ;
		
		IFactFilter filter = filters.get(index);
		index++;

		filter.doFilter(fact, chain);
	}

}
