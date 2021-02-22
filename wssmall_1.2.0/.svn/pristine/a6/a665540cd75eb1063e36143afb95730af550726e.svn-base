package com.ztesoft.net.eop.sdk.widget;

import java.util.Map;

/**
 * 挂件接口
 * @author kingapex
 * 2010-1-29上午10:09:22
 */
public interface IWidget {
	
	/**
	 * 解析挂件并返回解析后的html片段
	 * @param params 挂件参数Map
	 * @return 解析后的html片段
	 */
	public  String process(Map<String,String> params);
	
	/**
	 * 挂件设置
	 * @param params
	 * @return
	 */
	public String setting(Map<String,String> params);
	
	
	/**
	 * 挂件数据 更新操作
	 * @param params
	 */
	public void update(Map<String,String> params);
	
	
	
	/**
	 * 挂件是否缓存
	 * @return true:缓存 false不缓存
	 */
	public boolean cacheAble();
	
	/**
	 * 是否为静态模式
	 * @return true:静态模式  false 编辑、预览模式
	 */
	public boolean isStaticMode();
	
	/**
	 * 传送数据
	 */
	public void initParam();
	
}
