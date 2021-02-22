package com.ztesoft.inf.extend.freemarker;

import java.util.Map;

import com.ztesoft.inf.communication.client.Invoker;

import freemarker.template.Template;

public interface ITplParam {
	public void addUtils(Map root);

	public Template createTemplate(String content) throws Exception;

	public void addInvokerInfo(Map root, Invoker invoker);
}
