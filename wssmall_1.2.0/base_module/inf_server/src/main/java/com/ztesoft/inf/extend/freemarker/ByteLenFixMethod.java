package com.ztesoft.inf.extend.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class ByteLenFixMethod implements TemplateMethodModel {

	public ByteLenFixMethod() {
	}

	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 2) {
			throw new TemplateModelException("调用ByteLenFixMethod参数个数错误，应为2");
		}
		String str = (String) args.get(0);
		Integer len = Integer.parseInt((String) args.get(1));
		if (str.getBytes().length != len) {
			throw new TemplateModelException("字符串字节长度必须为" + len + ",{" + str
					+ "}不符合!");
		}
		return str;
	}
}
