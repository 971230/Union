package com.ztesoft.net.mall.core.service.impl.batchimport;

import com.ztesoft.net.mall.core.model.ImportDataSource;
import com.ztesoft.net.mall.core.service.batchimport.IGoodsDataImporter;

import org.w3c.dom.Element;

import java.util.Map;

/**
 * 商品字段导入器
 * @author kingapex
 *
 */
public class GoodsFieldImporter implements IGoodsDataImporter{

	
	@Override
	public void imported(Object value,Element node, ImportDataSource importConfig,Map goods) {
		String fieldname = node.getAttribute("fieldname");
		if(importConfig.isNewGoods())
			goods.put(fieldname, value);
	}
 

	
	
}
