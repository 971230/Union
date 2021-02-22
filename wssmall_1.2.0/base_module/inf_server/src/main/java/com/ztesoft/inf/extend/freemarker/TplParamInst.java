package com.ztesoft.inf.extend.freemarker;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.inf.communication.client.Invoker;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("unchecked")
public class TplParamInst implements ITplParam {

	private static Configuration conf;
	private static int tempSeq = 1;
	private static Map utils = new HashMap();

	static {
		conf = new Configuration();
		conf.setNumberFormat("0");
		utils.put("blen", new ByteLenMethod());
		utils.put("curdate", new CurrentDateMethod());
		utils.put("nextseq", new ExchangeIdGenMethod(""));
		utils.put("blimit", new ByteLenlimitMethod(0));
		utils.put("blimit_lpad", new ByteLenlimitMethod(
				ByteLenlimitMethod.LEFT_PAD));
		utils.put("blimit_rpad", new ByteLenlimitMethod(
				ByteLenlimitMethod.RIGHT_PAD));
		utils.put("bfix", new ByteLenFixMethod());
		utils.put("base64md5", new Base64MD5Method());
		utils.put("exchangeidgen", new ExchangeIdGenMethod());
	}

	@Override
	public Template createTemplate(String content) throws Exception {
		return new Template("ID:" + (tempSeq++), new StringReader(content),
				conf);
	}

	public static Map getUtilMethods() {
		return utils;
	}

	@Override
	public void addUtils(Map root) {
		root.put("ut", utils);
	}

	// 增加调用信息 add by xiaof 120210
	@Override
	public void addInvokerInfo(Map root, Invoker invoker) {
		Map invoke = new HashMap();
		invoke.put("user_code", invoker.getReqUser().getUser_code());
		invoke.put("user_pwd", invoker.getReqUser().getUser_pwd());
		invoke.put("user_name", invoker.getReqUser().getUser_name());
		invoke.put("user_param", invoker.getReqUser().getUser_param());
		invoke.put("exchangeidgen", new ExchangeIdGenMethod(invoker
				.getReqUser().getUser_code()));
		root.put("inf", invoke);
	}
}
