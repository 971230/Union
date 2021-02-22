package com.ztesoft.net.mall.plugin.standard.datalog;

import com.ztesoft.net.app.base.core.model.DataLog;
import com.ztesoft.net.eop.resource.IDataLogManager;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterAddEvent;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterEditEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 商品数据日志记录插件 
 * @author kingapex
 * 2010-10-19下午05:03:29
 */
public class GoodsDatalogPlugin extends AutoRegisterPlugin implements
		IGoodsAfterAddEvent, IGoodsAfterEditEvent {
	private IDataLogManager dataLogManager;
	
	@Override
	public void register() {
		
	}

	
	@Override
	public void onAfterGoodsAdd(Map goods, HttpServletRequest request)
			throws RuntimeException {
		
		DataLog datalog  = this.createDataLog(goods);
		datalog.setOptype("添加");
		this.dataLogManager.add(datalog);
		
	}

	
	@Override
	public void onAfterGoodsEdit(Map goods, HttpServletRequest request) {
//		DataLog datalog  = this.createDataLog(goods);
//		datalog.setOptype("修改");
//		this.dataLogManager.add(datalog);
	}
	
	
	private DataLog createDataLog(Map goods){
		
		DataLog datalog  = new DataLog();
		datalog.setContent("商品名:"+goods.get("name")+"<br>"+"描述:"+goods.get("intro"));
		String  image_file =(String) goods.get("image_file");
		
		StringBuffer pics  = new StringBuffer();
		if( !StringUtil.isEmpty(image_file)) {
			String[] files = image_file.split(",");
			for(String file:files){
				if(pics.length()>0)
					pics.append(",");
				pics.append( UploadUtil.getThumbPath(file, "_thumbnail") );
				pics.append("|");
				pics.append(file );
			}
		}
		
		datalog.setPics(pics.toString());
		datalog.setLogtype("商品");
		datalog.setOptype("添加");
		datalog.setUrl("/wssdetails-"+goods.get("goods_id")+".html");
		
		return datalog;
		
		
	}

	
	@Override
	public String getAuthor() {
		
		return "kingapex";
	}

	
	@Override
	public String getId() {
		
		return "goodsdatalog";
	}

	
	@Override
	public String getName() {
		
		return "商品数据日志记录插件";
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


	public IDataLogManager getDataLogManager() {
		return dataLogManager;
	}


	public void setDataLogManager(IDataLogManager dataLogManager) {
		this.dataLogManager = dataLogManager;
	}

}
