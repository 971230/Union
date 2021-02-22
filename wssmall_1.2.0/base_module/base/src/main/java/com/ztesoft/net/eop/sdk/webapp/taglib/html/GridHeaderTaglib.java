package com.ztesoft.net.eop.sdk.webapp.taglib.html;

import com.ztesoft.net.eop.sdk.webapp.taglib.HtmlTaglib;

public class GridHeaderTaglib extends HtmlTaglib {
	
	
	@Override
	protected String postStart() {
		return "<thead><tr>";
	}
	
	
	@Override
	protected String postEnd() {
		return "</tr></thead>";
	}
}
