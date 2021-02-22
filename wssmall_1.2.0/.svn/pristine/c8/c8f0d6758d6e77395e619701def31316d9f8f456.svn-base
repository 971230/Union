package com.ztesoft.common.application;

/**
 * @author AyaKoizumi 
 * @date 101109
 * @desc 发送AppCodeInterface类型的对象,获取该对象的代理类,并根据当前被代理对象定义的代码目录进行执行,执行期间可以动态修改执行代码目录
 * 
 * */
public interface AppCodeServerInterface {

	//发送需要的服务
	public Object getProxyServer(AppCodeInterface inst,String serviceName) throws Exception;
	//执行方法列表
	public Object execute();
	//获取代理操作类
	public AppProxy getAppProxy();
}
