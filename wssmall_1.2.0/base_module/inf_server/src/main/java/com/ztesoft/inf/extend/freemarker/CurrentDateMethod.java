package com.ztesoft.inf.extend.freemarker;

import java.util.List;

import com.ztesoft.common.util.date.DateUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class CurrentDateMethod implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		return DateUtil.current();
	}
}
