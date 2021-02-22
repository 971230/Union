package com.ztesoft.net.filter;

import com.ztesoft.net.filter.request.FilterRequest;
import com.ztesoft.net.filter.response.FilterResponse;

public interface IFilter {
	
	public FilterResponse doFilter(FilterRequest request);
}
