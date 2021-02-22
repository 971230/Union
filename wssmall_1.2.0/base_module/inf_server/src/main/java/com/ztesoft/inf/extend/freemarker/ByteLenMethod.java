package com.ztesoft.inf.extend.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class ByteLenMethod implements TemplateMethodModel {

	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("调用ByteLenMethod参数个数错误，应为1");
		}
		return new Integer(((String) args.get(0)).getBytes().length);
	}
}
