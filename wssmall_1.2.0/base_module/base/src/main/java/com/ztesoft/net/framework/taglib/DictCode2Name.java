package com.ztesoft.net.framework.taglib;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;

/**
 * 字段的code转名称
 * @author Z
 *
 */
public class DictCode2Name extends EnationTagSupport {

	private static final long serialVersionUID = -7231553152505371042L;
	private String attr_code;
	private String pkey;
	@Override
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException { //
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List<Map> staticDatas=dc.loadData(attr_code);
		
		if (!staticDatas.isEmpty()) {
			for (Map date : staticDatas) {
				String value = (String) date.get("value");
				if (value.equals(pkey)) {
					this.print((String) date.get("value_desc"));
					break;
				}
			}
		}
		return Tag.EVAL_BODY_INCLUDE;
	}

	@Override
	public int doStartTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	public String getAttr_code() {
		return attr_code;
	}

	public void setAttr_code(String attr_code) {
		this.attr_code = attr_code;
	}

	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	

}
