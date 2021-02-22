package com.ztesoft.net.cms.plugin;

import com.ztesoft.net.app.base.core.model.DataLog;
import com.ztesoft.net.cms.core.plugin.IDataSaveEvent;
import com.ztesoft.net.eop.resource.IDataLogManager;
import com.ztesoft.net.eop.sdk.context.EopSetting;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;

import java.util.Iterator;
import java.util.Map;

/**
 * cms数据日志记录插件
 * @author kingapex
 * 2010-10-19下午03:23:38
 */
public class CmsDataLogPlugin  extends AutoRegisterPlugin implements IDataSaveEvent{

	private IDataLogManager dataLogManager;
	
	/**
	 * 响应文章保存事件
	 * 取出data中所有的数据，形成文本为datalog的content<br>
	 * 其中以fs:开头的数据则认为是图片
	 */
	@Override
	public void onSave(Map data) {
		
		Iterator iter = data.keySet().iterator();
		StringBuffer content = new StringBuffer();
		StringBuffer pics = new StringBuffer();
		while(iter.hasNext()){
			String key = (String)iter.next();
			Object v = data.get(key);
			if(!(v instanceof String)) continue;
			
			String value  = (String)v;

			if(value!=null){
				if(value.startsWith(EopSetting.FILE_STORE_PREFIX)){
					if(pics.length()!=0){
						pics.append(",");
					}
					pics.append(value+"|"+value);
				}else{
					content.append(key+":"+value+"<br>");
				}
			}
			
			
		}
 
		DataLog datalog  = new DataLog();
		datalog.setContent(content.toString());
		datalog.setPics(pics.toString());
		datalog.setLogtype("文章");
		if(data.get("id")== null ) 
			datalog.setOptype("添加");
		else
			datalog.setOptype("修改");
		this.dataLogManager.add(datalog);
	}
	
	
	public IDataLogManager getDataLogManager() {
		return dataLogManager;
	}
	
	public void setDataLogManager(IDataLogManager dataLogManager) {
		this.dataLogManager = dataLogManager;
	}


	@Override
	public void register() {
		
	}


	@Override
	public String getAuthor() {
		return "kingapex";
	}


	@Override
	public String getId() {
		return "cmsdatalog";
	}


	@Override
	public String getName() {
		return "CMS数据日志记录插件";
	}


	@Override
	public String getType() {
		return "datalog";
	}


	@Override
	public String getVersion() {
		return "1.0";
	}


	@Override
	public void perform(Object... params) {
		
	}
	
	
}
