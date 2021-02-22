package com.ztesoft.net.search.common;

import com.ztesoft.net.search.conf.EsearchValues;
import com.ztesoft.net.search.esearch.ESearch;

public class ESearchFactory {
	
	public static IESearch getSearchInst() throws Exception{
		return getSearchByType(EsearchValues.CORE_SEARCH_TYPE);//暂时默认为esearch
	}

	private static IESearch getSearchByType(String type) throws Exception {
		if(type==null||"".equals(type)){
			type = EsearchValues.CORE_SEARCH_TYPE;
		}
		
		IESearch search = null;
		if(type.equals(EsearchValues.SEARCH_TYPE_ESEARCH)){
			search = ESearch.getInst();
		}
		
		return search;
	}
	
}
