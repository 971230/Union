package com.ztesoft.mall.core.action.backend;

import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.service.batchimport.IGoodsDataBatchManager;

/**
 * 商品导入
 * @author kingapex
 *
 */
public class GoodsImportAction extends WWAction {
	private IGoodsDataBatchManager goodsDataBatchManager ;
	private String path;
	@Override
	public String execute(){
		
		return "input";
	}
	
	public String imported(){
		try{
			goodsDataBatchManager.batchImport(path);
			this.json="{result:1}";
		}catch(RuntimeException e){
			
			this.logger.info("商品导入",e);
			this.json="{result:0,message:'"+e.getMessage()+"'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	public IGoodsDataBatchManager getGoodsDataBatchManager() {
		return goodsDataBatchManager;
	}

	public void setGoodsDataBatchManager(
			IGoodsDataBatchManager goodsDataBatchManager) {
		this.goodsDataBatchManager = goodsDataBatchManager;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
