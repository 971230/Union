package com.ztesoft.net.mall.core.service.impl.batchimport;

import com.ztesoft.net.framework.database.IDaoSupport;
import com.ztesoft.net.mall.core.model.ImportDataSource;
import com.ztesoft.net.mall.core.service.batchimport.IGoodsDataImporter;
import com.ztesoft.net.mall.core.service.impl.batchimport.util.GoodsImageReader;
import com.ztesoft.net.sqls.SF;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * 商品图片导入器
 * @author kingapex
 *
 */
public class GoodsImageImporter implements IGoodsDataImporter {
	protected final Logger logger = Logger.getLogger(getClass());
	private IDaoSupport daoSupport;
	private GoodsImageReader goodsImageReader;
 
	@Override
	public void imported(Object value, Element node, ImportDataSource importDs,
			Map goods) {
		Integer goodsid  = (Integer)goods.get("goods_id");
		String excludeStr = node.getAttribute("exclude");
		
		if(this.logger.isDebugEnabled()){
			logger.debug("开始导入商品["+goodsid+"]图片...");
		}
		
		String[] images= goodsImageReader.read(importDs.getDatafolder(), goodsid, excludeStr);
		if(images!=null){
			String sql = SF.goodsSql("GOODS_UPDATE_IMG_INFO");
			this.daoSupport.execute(sql, images[0],images[1],goodsid);
		}
		
		if(this.logger.isDebugEnabled()){
			logger.debug(" 商品["+goodsid+"]图片导入完成");
		}
	}

	public IDaoSupport getDaoSupport() {
		return daoSupport;
	}

	public void setDaoSupport(IDaoSupport daoSupport) {
		this.daoSupport = daoSupport;
	}

	public GoodsImageReader getGoodsImageReader() {
		return goodsImageReader;
	}

	public void setGoodsImageReader(GoodsImageReader goodsImageReader) {
		this.goodsImageReader = goodsImageReader;
	}
	
	 
}