package com.ztesoft.net.cms.core.taglib;

import com.ztesoft.net.cms.core.model.DataField;
import com.ztesoft.net.eop.sdk.webapp.taglib.BaseTaglibSupport;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import java.util.List;
import java.util.Map;

public class ArticleFieldOutTaglib extends BaseTaglibSupport {
	
	
 
	@Override
	public int doStartTag() throws JspException {
		Map article =(Map) this.pageContext.getAttribute("article");
		if(article!=null){
			List<DataField> fieldList  =(List<DataField>)this.pageContext.getAttribute("fieldList");
			if(fieldList!=null){
			for(DataField field : fieldList){
				String name =  field.getEnglish_name();
				Object value = article.get(name);
				this.print("<td>" + value+"</td>");
			}
			}
			
		}
		
		return Tag.EVAL_BODY_INCLUDE;
	}

	@Override
	public int doAfterBody() {
	 

		return Tag.EVAL_PAGE;
	}
}
