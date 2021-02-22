package com.ztesoft.net.mall.plugin.standard.datalog;

import com.ztesoft.net.app.base.core.model.DataLog;
import com.ztesoft.net.eop.resource.IDataLogManager;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.plugin.AutoRegisterPlugin;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.params.GoodsExtData;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterAddEventN;
import com.ztesoft.net.mall.core.plugin.goods.IGoodsAfterEditEventN;

import java.util.Map;

/**
 * 商品数据日志记录插件 
 * @author zou.qh
 * 2015-1-14
 */
public class GoodsDatalogPluginN extends AutoRegisterPlugin implements
		IGoodsAfterAddEventN, IGoodsAfterEditEventN {
	private IDataLogManager dataLogManager;
	
	@Override
	public void register() {
		
	}
	
	@Override
	public void onAfterGoodsAdd(Map goods, GoodsExtData goodsExtData)
			throws RuntimeException {
		DataLog datalog  = this.createDataLog(goods);
		datalog.setOptype("添加");
		this.dataLogManager.add(datalog);
		
	}
	
	@Override
	public void onAfterGoodsEdit(Map goods, GoodsExtData goodsExtData) {
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
		return "goodsdatalogN";
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
