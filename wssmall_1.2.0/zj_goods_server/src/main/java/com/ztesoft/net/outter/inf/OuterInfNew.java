package com.ztesoft.net.outter.inf;

import com.ztesoft.net.outter.inf.model.OutterTmplNew;
/**
 * 定时任务
 * @author Rapon
 *
 */
public interface OuterInfNew {
	/**
	 * 仅返回成功与失败，亦支持抛异常方式传递失败原因
	 * @param tmpl
	 * @return
	 * @throws Exception
	 */
	public boolean executeInfService(OutterTmplNew tmpl) throws Exception;

}
